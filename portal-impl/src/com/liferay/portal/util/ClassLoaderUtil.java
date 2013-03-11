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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

/**
 * @author Raymond Augé
 */
public class ClassLoaderUtil {

	public static ClassLoader getClassLoader(Class<?> clazz) {
		return clazz.getClassLoader();
	}

	public static ClassLoader getContextClassLoader() {
		Thread thread = Thread.currentThread();

		return thread.getContextClassLoader();
	}

	public static ClassLoader getPortalClassLoader() {
		return PortalClassLoaderUtil.getClassLoader();
	}

	public static void setContextClassLoader(ClassLoader classLoader) {
		Thread thread = Thread.currentThread();

		thread.setContextClassLoader(classLoader);
	}

}