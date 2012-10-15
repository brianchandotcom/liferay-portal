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

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Maps servlet context names to/from the ServletContext's ClassLoader.
 *
 * <ul>
 * <li>
 * For an unknown context names are mapped to the PortalClassLoader.
 * </li>
 * <li>
 * For an unknown ClassLoaders are mapped to the ROOT context name
 * {@link com.liferay.portal.kernel.util.StringPool#BLANK}
 * </li>
 * </ul>
 *
 * @author Shuyang Zhou
 */
public class ClassLoaderPool {

	public static ClassLoader getClassLoader(String contextName) {
		PortalRuntimePermission.checkGetBeanProperty(ClassLoaderPool.class);

		ClassLoader classLoader = _classLoaders.get(contextName);

		if (classLoader == null) {
			classLoader = PortalClassLoaderUtil.getClassLoader();
		}

		return classLoader;
	}

	public static String getContextName(ClassLoader classLoader) {
		if (classLoader == null) {
			return StringPool.BLANK;
		}

		String contextName = _contextNames.get(classLoader);

		if (contextName == null) {
			contextName = StringPool.BLANK;
		}

		return contextName;
	}

	public static void register(String contextName, ClassLoader classLoader) {
		PortalRuntimePermission.checkGetBeanProperty(ClassLoaderPool.class);

		_classLoaders.put(contextName, classLoader);
		_contextNames.put(classLoader, contextName);
	}

	public static void unregister(ClassLoader classLoader) {
		PortalRuntimePermission.checkGetBeanProperty(ClassLoaderPool.class);

		String contextName = _contextNames.remove(classLoader);

		if (contextName != null) {
			_classLoaders.remove(contextName);
		}
	}

	public static void unregister(String contextName) {
		PortalRuntimePermission.checkGetBeanProperty(ClassLoaderPool.class);

		ClassLoader classLoader = _classLoaders.remove(contextName);

		if (classLoader != null) {
			_contextNames.remove(classLoader);
		}
	}

	private static Map<String, ClassLoader> _classLoaders =
		new ConcurrentHashMap<String, ClassLoader>();
	private static Map<ClassLoader, String> _contextNames =
		new ConcurrentHashMap<ClassLoader, String>();

}