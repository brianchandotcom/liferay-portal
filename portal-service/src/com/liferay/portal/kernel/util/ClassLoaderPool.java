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

package com.liferay.portal.kernel.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Mapping Servlet context name to/from ServletContext ClassLoader.<br>
 * For unknown context name, map to PortalClassLoader.<br>
 * For unknown ClassLoader, map to ROOT contextName, StringPool.BLANK.
 *
 * @author Shuyang Zhou
 */
public class ClassLoaderPool {

	public static ClassLoader getClassLoaderByContextName(String contextName) {
		ClassLoader classLoader = _contextNameToClassLoaderMap.get(contextName);

		if (classLoader == null) {
			classLoader = PortalClassLoaderUtil.getClassLoader();
		}

		return classLoader;
	}

	public static String getContextNameByClassLoader(ClassLoader classLoader) {
		if (classLoader == null) {
			return StringPool.BLANK;
		}

		String contextName = _classLoaderToContextNameMap.get(classLoader);

		if (contextName == null) {
			contextName = StringPool.BLANK;
		}

		return contextName;
	}

	public static void register(String contextName, ClassLoader classLoader) {
		_contextNameToClassLoaderMap.put(contextName, classLoader);
		_classLoaderToContextNameMap.put(classLoader, contextName);
	}

	public static void unregisterByClassLoader(ClassLoader classLoader) {
		String contextName = _classLoaderToContextNameMap.remove(classLoader);

		if (contextName != null) {
			_contextNameToClassLoaderMap.remove(contextName);
		}
	}

	public static void unregisterByName(String contextName) {
		ClassLoader classLoader = _contextNameToClassLoaderMap.remove(
			contextName);

		if (classLoader != null) {
			_classLoaderToContextNameMap.remove(classLoader);
		}
	}

	private static Map<ClassLoader, String> _classLoaderToContextNameMap =
		new ConcurrentHashMap<ClassLoader, String>();

	private static Map<String, ClassLoader> _contextNameToClassLoaderMap =
		new ConcurrentHashMap<String, ClassLoader>();

}