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

import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.Clusterable;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.spring.aop.AnnotationChainableMethodAdvice;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.Method;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

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

		MethodHandler methodHandler =
			ClusterableInvokerUtil.createMethodHandler(
				clusterable.acceptor(), methodInvocation.getThis(),
				methodInvocation.getMethod(), methodInvocation.getArguments());

		ClusterRequest clusterRequest = ClusterRequest.createMulticastRequest(
			methodHandler, true);

		Method method = methodInvocation.getMethod();

		Class<?> returnType = method.getReturnType();

		if (returnType == void.class) {
			clusterRequest.setFireAndForget(true);
		}

		ClusterExecutorUtil.execute(clusterRequest);
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

		Method method = methodInvocation.getMethod();

		Class<?> returnType = method.getReturnType();

		if (ClusterMasterExecutorUtil.isMaster()) {
			Object result = methodInvocation.proceed();

			if (returnType == void.class) {
				result = nullResult;
			}

			return result;
		}

		MethodHandler methodHandler =
			ClusterableInvokerUtil.createMethodHandler(
				clusterable.acceptor(), methodInvocation.getThis(),
				methodInvocation.getMethod(), methodInvocation.getArguments());

		Future<Object> futureResult = ClusterMasterExecutorUtil.executeOnMaster(
			methodHandler);

		Object result = futureResult.get(
			PropsValues.CLUSTERABLE_ADVICE_CALL_MASTER_TIMEOUT,
			TimeUnit.SECONDS);

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