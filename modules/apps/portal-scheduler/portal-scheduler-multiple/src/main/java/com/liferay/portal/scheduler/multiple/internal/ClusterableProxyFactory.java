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

package com.liferay.portal.scheduler.multiple.internal;

import com.liferay.petra.reflect.AnnotationLocator;
import com.liferay.portal.kernel.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutorUtil;
import com.liferay.portal.kernel.cluster.Clusterable;
import com.liferay.portal.kernel.cluster.ClusterableInvokerUtil;
import com.liferay.portal.kernel.cluster.NullClusterable;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tina Tian
 */
public class ClusterableProxyFactory {

	public static <T> T createClusterableProxy(T targetObject) {
		Class<?> targetClass = targetObject.getClass();

		return (T)ProxyUtil.newProxyInstance(
			targetClass.getClassLoader(), targetClass.getInterfaces(),
			new ClusterableInvocationHandler<>(targetObject));
	}

	private static class ClusterableInvocationHandler<T>
		implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] arguments)
			throws Throwable {

			if (!ClusterInvokeThreadLocal.isEnabled()) {
				return method.invoke(_targetObject, arguments);
			}

			Clusterable clusterable = _getClusterable(
				method, _targetObject.getClass());

			if (clusterable == NullClusterable.NULL_CLUSTERABLE) {
				return method.invoke(_targetObject, arguments);
			}

			if (clusterable.onMaster()) {
				if (ClusterMasterExecutorUtil.isMaster()) {
					return method.invoke(_targetObject, arguments);
				}

				return ClusterableInvokerUtil.invokeOnMaster(
					clusterable.acceptor(), _targetObject, method, arguments);
			}

			Object result = method.invoke(_targetObject, arguments);

			ClusterableInvokerUtil.invokeOnCluster(
				clusterable.acceptor(), _targetObject, method, arguments);

			return result;
		}

		private ClusterableInvocationHandler(T targetObject) {
			_targetObject = targetObject;
		}

		private Clusterable _getClusterable(
			Method method, Class<?> targetClass) {

			MethodKey methodKey = new MethodKey(
				targetClass, method.getName(), method.getParameterTypes());

			Clusterable clusterable = _clusterables.get(methodKey);

			if (clusterable != null) {
				return clusterable;
			}

			clusterable = AnnotationLocator.locate(
				method, _targetObject.getClass(), Clusterable.class);

			if (clusterable == null) {
				clusterable = NullClusterable.NULL_CLUSTERABLE;
			}

			_clusterables.put(methodKey, clusterable);

			return clusterable;
		}

		private static final Map<MethodKey, Clusterable> _clusterables =
			new ConcurrentHashMap<>();

		private final T _targetObject;

	}

}