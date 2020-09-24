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

package com.liferay.poshi.core.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Kevin Yen
 */
public class ExternalMethod {

	public static Object execute(
			Method method, Object object, Object[] parameters)
		throws Exception {

		Object returnObject = null;

		parameters = _transformParameters(parameters);

		try {
			returnObject = method.invoke(object, parameters);
		}
		catch (Exception exception) {
			Throwable throwable = exception.getCause();

			if ((throwable != null) && (throwable.getMessage() != null)) {
				throw new Exception(throwable.getMessage(), exception);
			}

			throw exception;
		}

		if (returnObject == null) {
			return "";
		}

		return returnObject;
	}

	public static Object execute(
			String methodName, Object object, Object[] parameters)
		throws Exception {

		Class<?> clazz = object.getClass();

		return execute(
			getMethod(clazz, methodName, parameters), object, parameters);
	}

	public static Object execute(String className, String methodName)
		throws Exception {

		return execute(className, methodName, new Object[0]);
	}

	public static Object execute(
			String className, String methodName, Object[] parameters)
		throws Exception {

		Class<?> clazz = Class.forName(className);

		Method method = getMethod(clazz, methodName, parameters);

		int modifiers = method.getModifiers();

		Object object = null;

		if (!Modifier.isStatic(modifiers)) {
			object = clazz.newInstance();
		}

		return execute(method, object, parameters);
	}

	public static Method getMethod(
		Class<?> clazz, String methodName, Object[] parameters) {

		List<Method> filteredMethods = new ArrayList<>();

		for (Method method : clazz.getMethods()) {
			if (!methodName.equals(method.getName())) {
				continue;
			}

			filteredMethods.add(method);

			Class<?>[] methodParameterTypes = method.getParameterTypes();

			if (methodParameterTypes.length != parameters.length) {
				continue;
			}

			boolean parameterTypesMatch = true;

			for (int i = 0; i < methodParameterTypes.length; i++) {
				Object parameter = parameters[i];

				if (Objects.equals(parameter, _POSHI_NULL_NOTATION)) {
					continue;
				}

				if (!methodParameterTypes[i].isAssignableFrom(
						parameter.getClass())) {

					parameterTypesMatch = false;

					break;
				}
			}

			if (parameterTypesMatch) {
				return method;
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append("Unable to find method '");
		sb.append(methodName);
		sb.append("' of class '");
		sb.append(clazz.getCanonicalName());
		sb.append("'");

		if ((parameters != null) && (parameters.length != 0)) {
			sb.append(" with parameter types: (");

			String[] parameterClassNames = new String[parameters.length];

			int i = 0;

			for (Object parameter : parameters) {
				Class<?> parameterClass = parameter.getClass();

				parameterClassNames[i] = parameterClass.toString();

				i++;
			}

			sb.append(StringUtils.join(parameterClassNames, ", "));

			sb.append(")");
		}

		sb.append("\nValid methods with the same name:\n");

		if (filteredMethods.isEmpty()) {
			sb.append("NONE\n");
		}

		for (Method filteredMethod : filteredMethods) {
			sb.append("* ");
			sb.append(methodName);
			sb.append("(");
			sb.append(
				StringUtils.join(filteredMethod.getParameterTypes(), ", "));
			sb.append(")\n");
		}

		throw new IllegalArgumentException(sb.toString());
	}

	private static Object[] _transformParameters(Object[] parameters) {
		for (int i = 0; i < parameters.length; i++) {
			if (Objects.equals(parameters[i], _POSHI_NULL_NOTATION)) {
				parameters[i] = null;
			}
		}

		return parameters;
	}

	private static final String _POSHI_NULL_NOTATION = "Poshi.NULL";

}