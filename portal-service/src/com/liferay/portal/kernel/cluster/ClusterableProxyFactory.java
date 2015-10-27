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

package com.liferay.portal.kernel.cluster;

import com.liferay.portal.kernel.annotation.AnnotationLocator;
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

		public ClusterableInvocationHandler(T targetObject) {
			_targetObject = targetObject;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

			if (!ClusterInvokeThreadLocal.isEnabled()) {
				return method.invoke(_targetObject, args);
			}

			Clusterable clusterable = _getClusterable(
				method, _targetObject.getClass());

			if (clusterable == null) {
				return method.invoke(_targetObject, args);
			}
			else if (clusterable.onMaster()) {
				if (ClusterMasterExecutorUtil.isMaster()) {
					return method.invoke(_targetObject, args);
				}

				return ClusterableInvokerUtil.invokeOnMaster(
					clusterable.acceptor(), _targetObject, method, args);
			}

			Object result = method.invoke(_targetObject, args);

			ClusterableInvokerUtil.invokeOnCluster(
				clusterable.acceptor(), _targetObject, method, args);

			return result;
		}

		private Clusterable _getClusterable(
			Method method, Class<?> targetClass) {

			MethodKey methodKey = new MethodKey(
				targetClass, method.getName(), method.getParameterTypes());

			Clusterable clusterable = _clusterables.get(methodKey);

			if (clusterable != null) {
				if (clusterable == NullClusterable.NULL_CLUSTERABLE) {
					return null;
				}

				return clusterable;
			}

			clusterable = AnnotationLocator.locate(
				method, _targetObject.getClass(), Clusterable.class);

			if (clusterable == null) {
				_clusterables.put(methodKey, NullClusterable.NULL_CLUSTERABLE);
			}
			else {
				_clusterables.put(methodKey, clusterable);
			}

			return clusterable;
		}

		private static final Map<MethodKey, Clusterable> _clusterables =
			new ConcurrentHashMap<>();

		private final T _targetObject;

	}

}