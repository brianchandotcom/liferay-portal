/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.cluster;

import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.Clusterable;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.spring.aop.Swallowable;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.spring.aop.AnnotationChainableMethodAdvice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Shuyang Zhou
 */
public class ClusterableAdvice
	extends AnnotationChainableMethodAdvice<Clusterable> {

	@Override
	public void afterReturning(MethodInvocation methodInvocation, Object result)
		throws Throwable {

		if (!ClusterInvokeThreadLocal.isEnabled()) {
			return;
		}

		Clusterable clusterable = findAnnotation(methodInvocation);

		if (clusterable == _nullClusterable) {
			return;
		}

		Object thisObject = methodInvocation.getThis();

		if (!(thisObject instanceof IdentifiableBean)) {
			_log.error(
				"Not clustering calls for " + thisObject.getClass().getName() +
					" because it does not implement " +
						IdentifiableBean.class.getName());

			return;
		}

		Method method = methodInvocation.getMethod();

		MethodHandler methodHandler = new MethodHandler(
			method, methodInvocation.getArguments());

		ClusterRequest clusterRequest = ClusterRequest.createMulticastRequest(
			methodHandler, true);

		IdentifiableBean identifiableBean = (IdentifiableBean)thisObject;

		clusterRequest.setBeanIdentifier(identifiableBean.getBeanIdentifier());

		clusterRequest.setServletContextName(_servletContextName);

		ClusterExecutorUtil.execute(clusterRequest);
	}

	@Override
	public boolean afterThrowing(
			MethodInvocation methodInvocation, Throwable throwable)
		throws Throwable {

		if (!(throwable instanceof Swallowable)) {
			return true;
		}

		Swallowable swallowable = (Swallowable)throwable;

		if (swallowable.isSwallowable()) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public Clusterable getNullAnnotation() {
		return _nullClusterable;
	}

	public void setServletContextName(String servletContextName) {
		_servletContextName = servletContextName;
	}

	private static Log _log = LogFactoryUtil.getLog(ClusterableAdvice.class);

	private static Clusterable _nullClusterable =
		new Clusterable() {

			public Class<? extends Annotation> annotationType() {
				return Clusterable.class;
			}

		};

	private String _servletContextName;

}