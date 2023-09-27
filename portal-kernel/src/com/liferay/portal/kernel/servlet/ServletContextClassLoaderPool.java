/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.servlet;

import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shuyang Zhou
 */
public class ServletContextClassLoaderPool {

	public static ClassLoader getClassLoader(String servletContextName) {
		if (servletContextName != null) {
			ClassLoader classLoader = _classLoaders.get(servletContextName);

			if (classLoader != null) {
				return classLoader;
			}
		}

		if (_SERVLET_CONTEXT_CLASS_LOADER_POOL_FALLBACK) {
			return ClassLoaderPool.getClassLoader(servletContextName);
		}

		return null;
	}

	public static String getServletContextName(ClassLoader classLoader) {
		if (classLoader != null) {
			String servletContextName = _servletContextNames.get(classLoader);

			if (servletContextName != null) {
				return servletContextName;
			}
		}

		if (_SERVLET_CONTEXT_CLASS_LOADER_POOL_FALLBACK) {
			return ClassLoaderPool.getContextName(classLoader);
		}

		return null;
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

	private static final boolean _SERVLET_CONTEXT_CLASS_LOADER_POOL_FALLBACK =
		GetterUtil.getBoolean(
			PropsUtil.get(
				PropsKeys.SERVLET_CONTEXT_CLASS_LOADER_POOL_FALLBACK));

	private static final Map<String, ClassLoader> _classLoaders =
		new ConcurrentHashMap<>();
	private static final Map<ClassLoader, String> _servletContextNames =
		new ConcurrentHashMap<>();

}