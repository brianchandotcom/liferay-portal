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

package com.liferay.portal.kernel.test;

import com.liferay.portal.kernel.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.util.Arrays;

/**
 * @author Shuyang Zhou
 */
public class ReflectionTestUtil {

	public static Method getBridgeMethod(
			Class<?> clazz, String methodName, Class<?>... parameterTypes)
		throws NoSuchMethodException {

		Method method = getMethod(clazz, methodName, parameterTypes);

		if (method.isBridge()) {
			return method;
		}

		Method bridgeMethod = _findBridgeMethod(clazz.getMethods(), method);

		if (bridgeMethod != null) {
			return bridgeMethod;
		}

		while (clazz != null) {
			bridgeMethod = _findBridgeMethod(
				clazz.getDeclaredMethods(), method);

			if (bridgeMethod != null) {
				return bridgeMethod;
			}

			clazz =  clazz.getSuperclass();
		}

		throw new NoSuchMethodException(
			"No bridge method on " + clazz + " with name " + methodName +
				" and parameter types " + Arrays.toString(parameterTypes));
	}

	public static Field getField(Class<?> clazz, String fieldName)
		throws Exception {

		try {
			Field field = clazz.getField(fieldName);

			field.setAccessible(true);

			_unfinalField(field);

			return field;
		}
		catch (NoSuchFieldException nsfe) {
		}

		while (clazz != null) {
			try {
				Field field = clazz.getDeclaredField(fieldName);

				field.setAccessible(true);

				_unfinalField(field);

				return field;
			}
			catch (NoSuchFieldException nsfe) {
				clazz = clazz.getSuperclass();
			}
		}

		throw new NoSuchFieldException(
			"No field on " + clazz + " with name " + fieldName);
	}

	public static Object getFieldValue(Class<?> clazz, String fieldName)
		throws Exception {

		Field field = getField(clazz, fieldName);

		return field.get(null);
	}

	public static Object getFieldValue(Object instance, String fieldName)
		throws Exception {

		Field field = getField(instance.getClass(), fieldName);

		return field.get(instance);
	}

	public static Method getMethod(
			Class<?> clazz, String methodName, Class<?>... parameterTypes)
		throws NoSuchMethodException {

		try {
			Method method = clazz.getMethod(methodName, parameterTypes);

			method.setAccessible(true);

			return method;
		}
		catch (NoSuchMethodException nsme) {
		}

		while (clazz != null) {
			try {
				Method method = clazz.getDeclaredMethod(
					methodName, parameterTypes);

				method.setAccessible(true);

				return method;
			}
			catch (NoSuchMethodException nsme) {
				clazz = clazz.getSuperclass();
			}
		}

		throw new NoSuchMethodException(
			"No method on " + clazz + " with name " + methodName +
				" and parameter types " + Arrays.toString(parameterTypes));
	}

	public static Object invoke(
			Class<?> clazz, String methodName, Class<?>[] parameterTypes,
			Object... parameters)
		throws Exception {

		Method method = getMethod(clazz, methodName, parameterTypes);

		return method.invoke(null, parameters);
	}

	public static Object invoke(
			Object instance, String methodName, Class<?>[] parameterTypes,
			Object... parameters)
		throws Exception {

		Method method = getMethod(
			instance.getClass(), methodName, parameterTypes);

		return method.invoke(instance, parameters);
	}

	public static Object invokeBridge(
			Object instance, String methodName, Class<?>[] parameterTypes,
			Object... parameters)
		throws Exception {

		Method method = getBridgeMethod(
			instance.getClass(), methodName, parameterTypes);

		return method.invoke(instance, parameters);
	}

	public static void setFieldValue(
			Class<?> clazz, String fieldName, Object value)
		throws Exception {

		Field field = getField(clazz, fieldName);

		field.set(null, value);
	}

	public static void setFieldValue(
			Object instance, String fieldName, Object value)
		throws Exception {

		Field field = getField(instance.getClass(), fieldName);

		field.set(instance, value);
	}

	private static Method _findBridgeMethod(Method[] methods, Method method) {
		String name = method.getName();
		Class<?>[] parameterTypes = method.getParameterTypes();

		bridge:
		for (Method currentMethod : methods) {
			if (!currentMethod.isBridge() ||
				!name.equals(currentMethod.getName())) {

				continue;
			}

			Class<?>[] currentParameterTypes =
				currentMethod.getParameterTypes();

			if (currentParameterTypes.length != parameterTypes.length) {
				continue;
			}

			for (int i = 0; i < currentParameterTypes.length; i++) {
				if (!currentParameterTypes[i].isAssignableFrom(
						parameterTypes[i])) {

					continue bridge;
				}
			}

			currentMethod.setAccessible(true);

			return currentMethod;
		}

		return null;
	}

	private static void _unfinalField(Field field) throws Exception {
		int modifiers = field.getModifiers();

		Field modifiersField = ReflectionUtil.getDeclaredField(
			Field.class, "modifiers");

		modifiersField.setInt(field, modifiers & ~Modifier.FINAL);
	}

}