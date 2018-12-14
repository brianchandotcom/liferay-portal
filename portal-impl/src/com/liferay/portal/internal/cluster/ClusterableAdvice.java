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

import com.liferay.portal.kernel.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutorUtil;
import com.liferay.portal.kernel.cluster.Clusterable;
import com.liferay.portal.kernel.cluster.ClusterableInvokerUtil;
import com.liferay.portal.spring.aop.ChainableMethodAdvice;
import com.liferay.portal.spring.aop.ServiceBeanMethodInvocation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class ClusterableAdvice extends ChainableMethodAdvice {

	@Override
	public void afterReturning(
			ServiceBeanMethodInvocation serviceBeanMethodInvocation,
			Object[] arguments, Object result)
		throws Throwable {

		if (!ClusterInvokeThreadLocal.isEnabled()) {
			return;
		}

		Clusterable clusterable =
			serviceBeanMethodInvocation.getAdviceMethodContext();

		ClusterableInvokerUtil.invokeOnCluster(
			clusterable.acceptor(), serviceBeanMethodInvocation.getThis(),
			serviceBeanMethodInvocation.getMethod(), arguments);
	}

	@Override
	public Object before(
			ServiceBeanMethodInvocation serviceBeanMethodInvocation,
			Object[] arguments)
		throws Throwable {

		if (!ClusterInvokeThreadLocal.isEnabled()) {
			return null;
		}

		Clusterable clusterable =
			serviceBeanMethodInvocation.getAdviceMethodContext();

		if (!clusterable.onMaster()) {
			return null;
		}

		Object result = null;

		if (ClusterMasterExecutorUtil.isMaster()) {
			result = serviceBeanMethodInvocation.proceed(arguments);
		}
		else {
			result = ClusterableInvokerUtil.invokeOnMaster(
				clusterable.acceptor(), serviceBeanMethodInvocation.getThis(),
				serviceBeanMethodInvocation.getMethod(), arguments);
		}

		Method method = serviceBeanMethodInvocation.getMethod();

		Class<?> returnType = method.getReturnType();

		if (returnType == void.class) {
			result = nullResult;
		}

		return result;
	}

	@Override
	public Object createMethodContext(
		Class<?> targetClass, Method method,
		Map<Class<? extends Annotation>, Annotation> annotations) {

		return annotations.get(Clusterable.class);
	}

}