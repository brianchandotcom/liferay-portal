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

package com.liferay.poshi.runner.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author Kevin Yen
 */
public class ExternalMethod {

	public static String execute(
			Object object, String methodName, String[] parameters)
		throws Exception {

		Class<?> clazz = object.getClass();

		Method method = getMethod(clazz, methodName, parameters);

		Object returnObject = method.invoke(object, (Object[])parameters);

		if (returnObject == null) {
			returnObject = "";
		}

		return returnObject.toString();
	}

	public static String execute(String className, String methodName)
		throws Exception {

		String[] parameters = {};

		return execute(className, methodName, parameters);
	}

	public static String execute(
			String className, String methodName, String[] parameters)
		throws Exception {

		Class<?> clazz = Class.forName(className);

		Method method = getMethod(clazz, methodName, parameters);

		int modifiers = method.getModifiers();

		Object object = null;

		if (!Modifier.isStatic(modifiers)) {
			object = clazz.newInstance();
		}

		Object returnObject = method.invoke(object, (Object[])parameters);

		if (returnObject == null) {
			returnObject = "";
		}

		return returnObject.toString();
	}

	public static Method getMethod(
			Class clazz, String methodName, Object[] parameters)
		throws Exception {

		Class<?>[] parameterTypes = _getTypes(parameters);

		return clazz.getMethod(methodName, parameterTypes);
	}

	private static Class<?>[] _getTypes(Object[] objects) {
		if (objects == null) {
			return new Class<?>[0];
		}

		Class<?>[] objectTypes = new Class<?>[objects.length];

		for (int i = 0; i < objects.length; i++) {
			objectTypes[i] = objects[i].getClass();
		}

		return objectTypes;
	}

}