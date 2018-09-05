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