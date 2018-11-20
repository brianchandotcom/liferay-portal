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

package com.liferay.portal.cluster.multiple.internal.io;

import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.osgi.framework.Version;

/**
 * @author Lance Ji
 */
public class ClusterClassLoaderPool {

	public static ClassLoader getClassLoader(String contextName) {
		ClassLoader classLoader = null;

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		if ((contextName != null) && !contextName.equals(StringPool.NULL)) {
			try {
				currentThread.setContextClassLoader(null);

				classLoader = ClassLoaderPool.getClassLoader(contextName);
			}
			finally {
				currentThread.setContextClassLoader(contextClassLoader);
			}

			if (classLoader == null) {
				int pos = contextName.indexOf(CharPool.UNDERLINE);

				if (pos > 0) {
					String symbolicName = contextName.substring(0, pos);

					ConcurrentNavigableMap<Version, ClassLoader> classLoaders =
						_fallbackClassLoaders.get(symbolicName);

					if (classLoaders != null) {
						Map.Entry<Version, ClassLoader> entry =
							classLoaders.lastEntry();

						if (entry != null) {
							classLoader = entry.getValue();

							if (_log.isWarnEnabled()) {
								_log.warn(
									StringBundler.concat(
										"Unable to find class loader for ",
										contextName, ", class loader ",
										symbolicName, StringPool.UNDERLINE,
										entry.getKey(),
										" is provided instead"));
							}
						}
					}
				}
			}

			if ((classLoader == null) && _log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Unable to find class loader for ", contextName,
						", fall back to current thread's context class ",
						"loader"));
			}
		}

		if (classLoader == null) {
			classLoader = contextClassLoader;
		}

		return classLoader;
	}

	public static String getContextName(ClassLoader classLoader) {
		String contextName = ClassLoaderPool.getContextName(classLoader);

		if ((classLoader != null) && contextName.equals(StringPool.NULL)) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Unable to find context name for ",
						classLoader.toString(),
						", send 'null' as context name instead"));
			}
		}

		return contextName;
	}

	public static void registerFallback(
		String symbolicName, Version version, ClassLoader classLoader) {

		_fallbackClassLoaders.compute(
			symbolicName,
			(key, classLoaders) -> {
				if (classLoaders == null) {
					classLoaders = new ConcurrentSkipListMap<>();
				}

				classLoaders.put(version, classLoader);

				return classLoaders;
			});
	}

	public static void unregisterFallback(
		String symbolicName, Version version) {

		_fallbackClassLoaders.computeIfPresent(
			symbolicName,
			(key, classLoaders) -> {
				classLoaders.remove(version);

				if (classLoaders.isEmpty()) {
					return null;
				}

				return classLoaders;
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterClassLoaderPool.class);

	private static final Map
		<String, ConcurrentNavigableMap<Version, ClassLoader>>
			_fallbackClassLoaders = new ConcurrentHashMap<>();

}