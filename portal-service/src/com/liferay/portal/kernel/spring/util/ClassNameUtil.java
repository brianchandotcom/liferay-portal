/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.spring.util;

/**
 * Spring xml configuration helper class.
 * Use a type safe way to get class name.
 * This can prevent typo and refactor renaming bug.
 *
 * @author Shuyang Zhou
 */
public class ClassNameUtil {

	public static String getClassName(String className)
		throws ClassNotFoundException {

		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		return getClassName(className, classLoader);
	}

	public static String getClassName(String className, ClassLoader classLoader)
		throws ClassNotFoundException {

		Class<?> clazz = Class.forName(className, false, classLoader);

		return clazz.getName();
	}

}