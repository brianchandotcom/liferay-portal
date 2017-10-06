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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 */
public class InstancePool {

	public static boolean contains(String className) {
		return _instances.containsKey(className.trim());
	}

	public static Object get(String className) {
		return get(className, true);
	}

	public static Object get(String className, boolean logErrors) {
		className = className.trim();

		Object instance = _instances.get(className);

		if (instance != null) {
			return instance;
		}

		ClassLoader portalClassLoader = PortalClassLoaderUtil.getClassLoader();

		try {
			Class<?> clazz = portalClassLoader.loadClass(className);

			instance = clazz.newInstance();

			_instances.put(className, instance);
		}
		catch (Exception e1) {
			if (logErrors && _log.isWarnEnabled()) {
				_log.warn(
					"Unable to load " + className +
						" with the portal class loader",
					e1);
			}

			Thread currentThread = Thread.currentThread();

			ClassLoader contextClassLoader =
				currentThread.getContextClassLoader();

			try {
				Class<?> clazz = contextClassLoader.loadClass(className);

				instance = clazz.newInstance();

				_instances.put(className, instance);
			}
			catch (Exception e2) {
				if (logErrors) {
					_log.error(
						StringBundler.concat(
							"Unable to load ", className,
							" with the portal class loader or the current ",
							"context class loader"),
						e2);
				}
			}
		}

		return instance;
	}

	public static void put(String className, Object obj) {
		_instances.put(className.trim(), obj);
	}

	public static void reset() {
		_instances.clear();
	}

	private InstancePool() {
	}

	private static final Log _log = LogFactoryUtil.getLog(InstancePool.class);

	private static final Map<String, Object> _instances =
		new ConcurrentHashMap<>();

}