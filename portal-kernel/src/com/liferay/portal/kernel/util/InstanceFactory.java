/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import java.lang.reflect.Constructor;

/**
 * @author Brian Wing Shun Chan
 */
public class InstanceFactory {

	public static Object newInstance(ClassLoader classLoader, String className)
		throws Exception {

		return newInstance(
			classLoader, className, (Class<?>[])null, (Object[])null);
	}

	public static Object newInstance(
			ClassLoader classLoader, String className, Class<?> parameterType,
			Object argument)
		throws Exception {

		return newInstance(
			classLoader, className, new Class<?>[] {parameterType},
			new Object[] {argument});
	}

	public static Object newInstance(
			ClassLoader classLoader, String className,
			Class<?>[] parameterTypes, Object[] arguments)
		throws Exception {

		if (classLoader == null) {
			Thread currentThread = Thread.currentThread();

			classLoader = currentThread.getContextClassLoader();

			if (classLoader == null) {
				classLoader = ClassLoader.getSystemClassLoader();
			}
		}

		Class<?> clazz = classLoader.loadClass(className);

		if ((parameterTypes != null) && (arguments != null) &&
			(parameterTypes.length > 0) && (arguments.length > 0) &&
			(parameterTypes.length == arguments.length)) {

			Constructor<?> constructor = clazz.getConstructor(parameterTypes);

			return constructor.newInstance(arguments);
		}

		return clazz.newInstance();
	}

	public static Object newInstance(String className) throws Exception {
		return newInstance(null, className);
	}

	public static Object newInstance(
			String className, Class<?> parameterType, Object argument)
		throws Exception {

		return newInstance(
			null, className, new Class<?>[] {parameterType},
			new Object[] {argument});
	}

	public static Object newInstance(
			String className, Class<?>[] parameterTypes, Object[] arguments)
		throws Exception {

		return newInstance(null, className, parameterTypes, arguments);
	}

}