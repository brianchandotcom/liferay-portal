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

package com.liferay.bnd.util;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shuyang Zhou
 */
public class ConfigurableUtil {

	public static <T> T createConfigurable(
		Class<T> clazz, Dictionary<?, ?> properties) {

		Object proxy = ProxyUtil.newProxyInstance(
			clazz.getClassLoader(), new Class<?>[] {clazz},
			new CachedConfigurableInvocationHandler<>(
				Configurable.createConfigurable(clazz, properties)));

		return clazz.cast(proxy);
	}

	public static <T> T createConfigurable(
		Class<T> clazz, Map<?, ?> properties) {

		Object proxy = ProxyUtil.newProxyInstance(
			clazz.getClassLoader(), new Class<?>[] {clazz},
			new CachedConfigurableInvocationHandler<>(
				Configurable.createConfigurable(clazz, properties)));

		return clazz.cast(proxy);
	}

	private static class CachedConfigurableInvocationHandler<T>
		implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

			String methodName = method.getName();

			Object returnValue = _returnValues.get(methodName);

			if (returnValue == null) {
				method.setAccessible(true);

				returnValue = method.invoke(_configurable, args);

				if (returnValue == null) {
					returnValue = _NULL_OBJECT;
				}

				_returnValues.put(methodName, returnValue);
			}

			if (returnValue == _NULL_OBJECT) {
				return null;
			}

			return returnValue;
		}

		private CachedConfigurableInvocationHandler(T configurable) {
			_configurable = configurable;
		}

		private static final Object _NULL_OBJECT = new Object();

		private final T _configurable;
		private final Map<String, Object> _returnValues =
			new ConcurrentHashMap<>();

	}

}