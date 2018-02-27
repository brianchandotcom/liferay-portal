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

package com.liferay.portal.configuration.metatype.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Map;

/**
 * @author Jorge Ferrer
 */
public class ParameterMapInvocationHandler<S> implements InvocationHandler {

	public ParameterMapInvocationHandler(
		Class clazz, Object bean, Map<String, String[]> parameterMap) {

		this(clazz, bean, parameterMap, StringPool.BLANK, StringPool.BLANK);
	}

	public ParameterMapInvocationHandler(
		Class clazz, Object bean, Map<String, String[]> parameterMap,
		String parameterPrefix, String parameterSuffix) {

		_clazz = clazz;
		_bean = bean;
		_parameterMap = parameterMap;
		_parameterPrefix = parameterPrefix;
		_parameterSuffix = parameterSuffix;
	}

	public S createProxy() {
		return (S)ProxyUtil.newProxyInstance(
			_clazz.getClassLoader(), new Class<?>[] {_clazz}, this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] arguments)
		throws InvocationTargetException {

		Object result = null;

		try {
			result = _invokeMap(method);
		}
		catch (Exception e) {
		}

		if (result != null) {
			return result;
		}

		try {
			return _invokeConfigurationInstance(method, arguments);
		}
		catch (Exception e) {
			return null;
		}
	}

	private String _getMapValue(String name) {
		String[] values = _getMapValueArray(name);

		if (values == null) {
			return null;
		}

		return values[0];
	}

	private String[] _getMapValueArray(String name) {
		String[] values = _parameterMap.get(
			_parameterPrefix + name + _parameterSuffix);

		if ((values == null) || (values.length == 0)) {
			return null;
		}

		return values;
	}

	private Object _invokeConfigurationInstance(
			Method method, Object[] arguments)
		throws IllegalAccessException, InvocationTargetException,
			   NoSuchMethodException {

		Class<?> clazz = _bean.getClass();

		method = clazz.getMethod(method.getName(), method.getParameterTypes());

		return method.invoke(_bean, arguments);
	}

	private Object _invokeMap(Method method)
		throws IllegalAccessException, InstantiationException,
			   InvocationTargetException, NoSuchMethodException {

		Class<?> returnType = method.getReturnType();

		if (returnType.equals(boolean.class)) {
			String value = _getMapValue(method.getName());

			if (value == null) {
				return null;
			}

			return Boolean.valueOf(value);
		}
		else if (returnType.equals(double.class)) {
			String value = _getMapValue(method.getName());

			if (value == null) {
				return null;
			}

			return Double.valueOf(_getMapValue(method.getName()));
		}
		else if (returnType.equals(float.class)) {
			String value = _getMapValue(method.getName());

			if (value == null) {
				return null;
			}

			return Float.valueOf(_getMapValue(method.getName()));
		}
		else if (returnType.equals(int.class)) {
			String value = _getMapValue(method.getName());

			if (value == null) {
				return null;
			}

			return Integer.valueOf(_getMapValue(method.getName()));
		}
		else if (returnType.equals(LocalizedValuesMap.class)) {
			return _getMapValue(method.getName());
		}
		else if (returnType.equals(long.class)) {
			String value = _getMapValue(method.getName());

			if (value == null) {
				return null;
			}

			return Long.valueOf(_getMapValue(method.getName()));
		}
		else if (returnType.equals(String.class)) {
			return _getMapValue(method.getName());
		}
		else if (returnType.equals(String[].class)) {
			return _getMapValueArray(method.getName());
		}
		else if (returnType.isEnum()) {
			Method valueOfMethod = returnType.getDeclaredMethod(
				"valueOf", String.class);

			return valueOfMethod.invoke(
				returnType, _parameterMap.get(method.getName()));
		}

		Constructor<?> constructor = returnType.getConstructor(String.class);

		return constructor.newInstance(_parameterMap.get(method.getName()));
	}

	private final Object _bean;
	private final Class _clazz;
	private final Map<String, String[]> _parameterMap;
	private final String _parameterPrefix;
	private final String _parameterSuffix;

}