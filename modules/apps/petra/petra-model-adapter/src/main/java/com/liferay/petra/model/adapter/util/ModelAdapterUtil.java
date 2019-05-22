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

package com.liferay.petra.model.adapter.util;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.util.ComparatorAdapter;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorAdapter;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * @author Shuyang Zhou
 */
public class ModelAdapterUtil {

	public static <T> List<T> adapt(Class<T> clazz, List<?> delegateObjects) {
		List<T> adaptedObjects = new ArrayList<>(delegateObjects.size());

		for (Object delegateObject : delegateObjects) {
			adaptedObjects.add(adapt(clazz, delegateObject));
		}

		return adaptedObjects;
	}

	public static <T> T adapt(Class<T> clazz, Object delegateObject) {
		if (delegateObject == null) {
			return null;
		}

		return (T)ProxyUtil.newProxyInstance(
			clazz.getClassLoader(), new Class<?>[] {clazz, ModelWrapper.class},
			new DelegateInvocationHandler(delegateObject));
	}

	public static <T> T[] adapt(Class<T> clazz, Object[] delegateObjects) {
		T[] adaptedObjects = (T[])Array.newInstance(
			clazz, delegateObjects.length);

		for (int i = 0; i < delegateObjects.length; i++) {
			adaptedObjects[i] = adapt(clazz, delegateObjects[i]);
		}

		return adaptedObjects;
	}

	public static <T, V> Comparator<T> adapt(
		Class<V> clazz, Comparator<V> comparator) {

		if (comparator == null) {
			return null;
		}

		return new ComparatorAdapter<T, V>(comparator) {

			@Override
			public V adapt(T t) {
				return ModelAdapterUtil.adapt(clazz, t);
			}

		};
	}

	public static <T, V> OrderByComparator<T> adapt(
		Class<V> clazz, OrderByComparator<V> orderByComparator) {

		if (orderByComparator == null) {
			return null;
		}

		return new OrderByComparatorAdapter<T, V>(orderByComparator) {

			@Override
			public V adapt(T t) {
				return ModelAdapterUtil.adapt(clazz, t);
			}

		};
	}

	public static <T, V> QueryDefinition<T> adapt(
		Class<V> clazz, QueryDefinition<V> queryDefinition) {

		if (queryDefinition == null) {
			return null;
		}

		QueryDefinition<T> adaptedQueryDefinition = new QueryDefinition<>(
			queryDefinition.getStatus(), queryDefinition.isExcludeStatus(),
			queryDefinition.getOwnerUserId(), queryDefinition.isIncludeOwner(),
			queryDefinition.getStart(), queryDefinition.getEnd(),
			adapt(clazz, queryDefinition.getOrderByComparator()));

		adaptedQueryDefinition.setAttributes(queryDefinition.getAttributes());

		return adaptedQueryDefinition;
	}

	public static <T> T adapt(
		Function<InvocationHandler, T> proxyProviderFunction,
		Object delegateObject) {

		if (delegateObject == null) {
			return null;
		}

		return proxyProviderFunction.apply(
			new DelegateInvocationHandler(delegateObject));
	}

	private static class DelegateInvocationHandler
		implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws ReflectiveOperationException {

			String methodName = method.getName();

			if (methodName.equals("getWrappedModel")) {
				return _delegateObject;
			}

			Class<?> delegateClass = _delegateObject.getClass();

			method = delegateClass.getMethod(
				method.getName(), method.getParameterTypes());

			if (args == null) {
				return method.invoke(_delegateObject);
			}

			for (int i = 0; i < args.length; i++) {
				if (!ProxyUtil.isProxyClass(args[i].getClass())) {
					continue;
				}

				InvocationHandler invocationHandler =
					ProxyUtil.getInvocationHandler(args[i]);

				if (invocationHandler instanceof DelegateInvocationHandler) {
					DelegateInvocationHandler delegateInvocationHandler =
						(DelegateInvocationHandler)invocationHandler;

					args[i] = delegateInvocationHandler._delegateObject;
				}
			}

			return method.invoke(_delegateObject, args);
		}

		private DelegateInvocationHandler(Object delegateObject) {
			_delegateObject = delegateObject;
		}

		private final Object _delegateObject;

	}

}