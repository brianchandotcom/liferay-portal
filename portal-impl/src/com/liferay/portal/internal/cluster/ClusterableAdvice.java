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

package com.liferay.portal.internal.cluster;

import com.liferay.portal.kernel.aop.AopMethodInvocation;
import com.liferay.portal.kernel.aop.ChainableMethodAdvice;
import com.liferay.portal.kernel.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutorUtil;
import com.liferay.portal.kernel.cluster.Clusterable;
import com.liferay.portal.kernel.cluster.ClusterableInvokerUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class ClusterableAdvice extends ChainableMethodAdvice {

	@Override
	public Object createMethodContext(
		Class<?> targetClass, Method method,
		Map<Class<? extends Annotation>, Annotation> annotations) {

		return annotations.get(Clusterable.class);
	}

	@Override
	protected void afterReturning(
			AopMethodInvocation aopMethodInvocation, Object[] arguments,
			Object result)
		throws Throwable {

		if (!ClusterInvokeThreadLocal.isEnabled()) {
			return;
		}

		Clusterable clusterable = aopMethodInvocation.getAdviceMethodContext();

		ClusterableInvokerUtil.invokeOnCluster(
			clusterable.acceptor(), aopMethodInvocation.getThis(),
			aopMethodInvocation.getMethod(), arguments);
	}

	@Override
	protected Object before(
			AopMethodInvocation aopMethodInvocation, Object[] arguments)
		throws Throwable {

		if (!ClusterInvokeThreadLocal.isEnabled()) {
			return null;
		}

		Clusterable clusterable = aopMethodInvocation.getAdviceMethodContext();

		if (!clusterable.onMaster()) {
			return null;
		}

		Object result = null;

		if (ClusterMasterExecutorUtil.isMaster()) {
			result = aopMethodInvocation.proceed(arguments);
		}
		else {
			result = ClusterableInvokerUtil.invokeOnMaster(
				clusterable.acceptor(), aopMethodInvocation.getThis(),
				aopMethodInvocation.getMethod(), arguments);
		}

		Method method = aopMethodInvocation.getMethod();

		Class<?> returnType = method.getReturnType();

		if (returnType == void.class) {
			result = nullResult;
		}

		return result;
	}

}