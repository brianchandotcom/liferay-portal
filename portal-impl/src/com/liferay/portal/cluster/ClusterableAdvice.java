/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterInvokeAcceptor;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.Clusterable;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ClassLoaderPool;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.aop.AnnotationChainableMethodAdvice;
import com.liferay.portal.util.ClassLoaderUtil;

import java.io.Serializable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

import java.util.Map;

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

		MethodHandler methodHandler = createMethodHandler(
			clusterable.acceptor(), methodInvocation);

		ClusterRequest clusterRequest = ClusterRequest.createMulticastRequest(
			methodHandler, true);

		ClusterExecutorUtil.execute(clusterRequest);
	}

	@Override
	public Clusterable getNullAnnotation() {
		return _nullClusterable;
	}

	protected MethodHandler createMethodHandler(
		Class<? extends ClusterInvokeAcceptor> clusterInvokeAcceptorClass,
		MethodInvocation methodInvocation) {

		if (clusterInvokeAcceptorClass == ClusterInvokeAcceptor.class) {
			clusterInvokeAcceptorClass = null;
		}

		MethodHandler methodHandler = new MethodHandler(
			methodInvocation.getMethod(), methodInvocation.getArguments());

		Object thisObject = methodInvocation.getThis();

		IdentifiableBean identifiableBean = (IdentifiableBean)thisObject;

		String beanIdentifier = identifiableBean.getBeanIdentifier();

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		String servletContextName = ClassLoaderPool.getContextName(
			contextClassLoader);

		Map<String, Serializable> context =
			ClusterableContextThreadLocal.collectThreadLocalContext();

		return new MethodHandler(
			_invokeMethodKey, methodHandler, beanIdentifier, servletContextName,
			clusterInvokeAcceptorClass, context);
	}

	@SuppressWarnings("unused")
	private static Object _invoke(
			MethodHandler methodHandler, String servletContextName,
			String beanIdentifier,
			Class<? extends ClusterInvokeAcceptor> clusterInvokeAcceptorClass,
			Map<String, Serializable> context)
		throws Exception {

		if (clusterInvokeAcceptorClass != null) {
			Constructor<? extends ClusterInvokeAcceptor> constructor =
				clusterInvokeAcceptorClass.getDeclaredConstructor();

			if (!constructor.isAccessible()) {
				constructor.setAccessible(true);
			}

			ClusterInvokeAcceptor clusterInvokeAcceptor =
				constructor.newInstance();

			if (!clusterInvokeAcceptor.accept(context)) {
				return null;
			}
		}

		if (Validator.isNull(servletContextName)) {
			if (Validator.isNull(beanIdentifier)) {
				return methodHandler.invoke(true);
			}
			else {
				Object bean = PortalBeanLocatorUtil.locate(beanIdentifier);

				return methodHandler.invoke(bean);
			}
		}

		ClassLoader contextClassLoader =
			ClassLoaderUtil.getContextClassLoader();

		try {
			ClassLoader classLoader = ClassLoaderPool.getClassLoader(
				servletContextName);

			ClassLoaderUtil.setContextClassLoader(classLoader);

			if (Validator.isNull(beanIdentifier)) {
				return methodHandler.invoke(true);
			}
			else {
				Object bean = PortletBeanLocatorUtil.locate(
					servletContextName, beanIdentifier);

				return methodHandler.invoke(bean);
			}
		}
		finally {
			ClassLoaderUtil.setContextClassLoader(contextClassLoader);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ClusterableAdvice.class);

	private static MethodKey _invokeMethodKey = new MethodKey(
		ClusterableAdvice.class, "_invoke", MethodHandler.class, String.class,
		String.class, Class.class, Map.class);

	private static Clusterable _nullClusterable = new Clusterable() {

			@Override
			public Class<? extends ClusterInvokeAcceptor> acceptor() {
				return null;
			}

			@Override
			public Class<? extends Annotation> annotationType() {
				return Clusterable.class;
			}

		};

}