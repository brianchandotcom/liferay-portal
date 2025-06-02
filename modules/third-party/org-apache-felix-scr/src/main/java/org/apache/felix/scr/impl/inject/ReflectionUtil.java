/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package org.apache.felix.scr.impl.inject;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Shuyang Zhou
 */
public class ReflectionUtil {

	public static Field fetchDeclaredField(Class<?> clazz, String name)
		throws Throwable {

		if (_fetchDeclaredFieldMethodHandle == null) {
			try {
				return clazz.getDeclaredField(name);
			}
			catch (NoSuchFieldException noSuchFieldException) {
				return null;
			}
		}

		return (Field)_fetchDeclaredFieldMethodHandle.invokeExact(clazz, name);
	}

	public static Method fetchDeclaredMethod(
			Class<?> clazz, String name, Class<?>... parameterTypes)
		throws Throwable {

		if (_fetchDeclaredMethodMethodHandle == null) {
			try {
				return clazz.getDeclaredMethod(name, parameterTypes);
			}
			catch (NoSuchMethodException noSuchMethodException) {
				return null;
			}
		}

		return (Method)_fetchDeclaredMethodMethodHandle.invokeExact(
			clazz, name, parameterTypes);
	}

	private static final MethodHandle _fetchDeclaredFieldMethodHandle;
	private static final MethodHandle _fetchDeclaredMethodMethodHandle;

	static {
		ClassLoader classLoader = ReflectionUtil.class.getClassLoader();

		classLoader = classLoader.getParent();

		MethodHandle fetchDeclaredFieldMethodHandle;
		MethodHandle fetchDeclaredMethodMethodHandle;

		try {
			Class<?> clazz = classLoader.loadClass(
				"com.liferay.petra.reflect.ReflectionUtil");

			MethodHandles.Lookup lookup = MethodHandles.lookup();

			fetchDeclaredFieldMethodHandle = lookup.findStatic(
				clazz, "fetchDeclaredField",
				MethodType.methodType(Field.class, Class.class, String.class));
			fetchDeclaredMethodMethodHandle = lookup.findStatic(
				clazz, "fetchDeclaredMethod",
				MethodType.methodType(
					Method.class, Class.class, String.class, Class[].class));
		}
		catch (Exception exception) {
			fetchDeclaredFieldMethodHandle = null;
			fetchDeclaredMethodMethodHandle = null;
		}

		_fetchDeclaredFieldMethodHandle = fetchDeclaredFieldMethodHandle;
		_fetchDeclaredMethodMethodHandle = fetchDeclaredMethodMethodHandle;
	}

}
/* @generated */