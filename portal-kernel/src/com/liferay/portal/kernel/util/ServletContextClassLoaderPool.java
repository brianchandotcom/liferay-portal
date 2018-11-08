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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shuyang Zhou
 */
public class ServletContextClassLoaderPool {

	public static ClassLoader getClassLoader(String servletContextName) {
		if (servletContextName == null) {
			return null;
		}

		return _classLoaders.get(servletContextName);
	}

	public static String getServletContextName(ClassLoader classLoader) {
		if (classLoader == null) {
			return null;
		}

		return _servletContextNames.get(classLoader);
	}

	public static void register(
		String servletContextName, ClassLoader classLoader) {

		_classLoaders.put(servletContextName, classLoader);
		_servletContextNames.put(classLoader, servletContextName);
	}

	public static void unregister(String servletContextName) {
		ClassLoader classLoader = _classLoaders.remove(servletContextName);

		if (classLoader != null) {
			_servletContextNames.remove(classLoader);
		}
	}

	private static final Map<String, ClassLoader> _classLoaders =
		new ConcurrentHashMap<>();
	private static final Map<ClassLoader, String> _servletContextNames =
		new ConcurrentHashMap<>();

}