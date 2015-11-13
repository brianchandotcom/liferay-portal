/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutorUtil;
import com.liferay.portal.kernel.cluster.Clusterable;
import com.liferay.portal.kernel.cluster.ClusterableInvokerUtil;
import com.liferay.portal.kernel.cluster.NullClusterable;
import com.liferay.portal.spring.aop.AnnotationChainableMethodAdvice;

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

		if (clusterable == NullClusterable.NULL_CLUSTERABLE) {
			return;
		}

		ClusterableInvokerUtil.invokeOnCluster(
			clusterable.acceptor(), methodInvocation.getThis(),
			methodInvocation.getMethod(), methodInvocation.getArguments());
	}

	@Override
	public Object before(MethodInvocation methodInvocation) throws Throwable {
		if (!ClusterInvokeThreadLocal.isEnabled()) {
			return null;
		}

		Clusterable clusterable = findAnnotation(methodInvocation);

		if (clusterable == NullClusterable.NULL_CLUSTERABLE) {
			return null;
		}

		if (!clusterable.onMaster()) {
			return null;
		}

		Object result = null;

		if (ClusterMasterExecutorUtil.isMaster()) {
			result = methodInvocation.proceed();
		}
		else {
			result = ClusterableInvokerUtil.invokeOnMaster(
				clusterable.acceptor(), methodInvocation.getThis(),
				methodInvocation.getMethod(), methodInvocation.getArguments());
		}

		Method method = methodInvocation.getMethod();

		Class<?> returnType = method.getReturnType();

		if (returnType == void.class) {
			result = nullResult;
		}

		return result;
	}

	@Override
	public Clusterable getNullAnnotation() {
		return NullClusterable.NULL_CLUSTERABLE;
	}

}