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

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Maps servlet context names to/from the servlet context's class loader.
 *
 * @author Shuyang Zhou
 */
public class ClassLoaderPool {

	/**
	 * Returns the class loader associated with the context name.
	 *
	 * <p>
	 * If no class loader is found for the context name, this method checks for
	 * an initialized portal class loader to return. In cases where this method
	 * is invoked from outside of a running portal, such as from a unit test or
	 * from an external tool, no portal class loader will be found. If no portal
	 * class loader is found, the thread's context class loader is returned as a
	 * fallback.
	 * </p>
	 *
	 * @param  contextName the servlet context's name
	 * @return the class loader associated with the context name
	 */
	public static ClassLoader getClassLoader(String contextName) {
		PortalRuntimePermission.checkGetBeanProperty(ClassLoaderPool.class);

		ClassLoader classLoader = _classLoaders.get(contextName);

		if (classLoader == null) {
			classLoader = PortalClassLoaderUtil.getClassLoader();
		}

		if (classLoader == null) {
			Thread currentThread = Thread.currentThread();

			classLoader = currentThread.getContextClassLoader();
		}

		return classLoader;
	}

	/**
	 * Returns the context name associated with the class loader.
	 *
	 * <p>
	 * If the class loader is <code>null</code> or if no context name is
	 * associated with the class loader, {@link
	 * com.liferay.portal.kernel.util.StringPool#BLANK} is returned.
	 * </p>
	 *
	 * @param  classLoader the class loader
	 * @return the context name associated with the class loader
	 */
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

	private static final Map<String, ClassLoader> _classLoaders =
		new ConcurrentHashMap<String, ClassLoader>();
	private static final Map<ClassLoader, String> _contextNames =
		new ConcurrentHashMap<ClassLoader, String>();

}