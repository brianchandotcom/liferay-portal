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

package com.liferay.portal.template.freemarker.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;

import java.net.URL;
import java.net.URLClassLoader;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Miguel Pastor
 * @author Raymond Augé
 */
public class FreeMarkerBundleClassloader extends URLClassLoader {

	public FreeMarkerBundleClassloader(ClassLoader... classLoaders) {
		super(new URL[0]);

		if (classLoaders.length == 0) {
			throw new IllegalArgumentException("Bundles are empty");
		}

		Collections.addAll(_classLoaders, classLoaders);
	}

	public void addclassLoader(ClassLoader classLoader) {
		_classLoaders.add(classLoader);
	}

	@Override
	public URL findResource(String name) {
		for (ClassLoader classLoader : _classLoaders) {
			URL url = classLoader.getResource(name);

			if (url != null) {
				return url;
			}
		}

		return null;
	}

	@Override
	public Enumeration<URL> findResources(String name) {
		for (ClassLoader classLoader : _classLoaders) {
			try {
				Enumeration<URL> enumeration = classLoader.getResources(name);

				if ((enumeration != null) && enumeration.hasMoreElements()) {
					return enumeration;
				}
			}
			catch (IOException ioException) {
				if (_log.isDebugEnabled()) {
					_log.debug(ioException);
				}
			}
		}

		return Collections.enumeration(Collections.<URL>emptyList());
	}

	@Override
	public URL getResource(String name) {
		return findResource(name);
	}

	@Override
	public Enumeration<URL> getResources(String name) {
		return findResources(name);
	}

	public void removeClassLoader(ClassLoader classLoader) {
		_classLoaders.remove(classLoader);
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		for (ClassLoader classLoader : _classLoaders) {
			try {
				return classLoader.loadClass(name);
			}
			catch (ClassNotFoundException classNotFoundException) {
				if (_log.isDebugEnabled()) {
					_log.debug(classNotFoundException);
				}
			}
		}

		throw new ClassNotFoundException(name);
	}

	@Override
	protected Class<?> loadClass(String name, boolean resolve)
		throws ClassNotFoundException {

		Class<?> clazz = findClass(name);

		if (resolve) {
			resolveClass(clazz);
		}

		return clazz;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FreeMarkerBundleClassloader.class);

	private final Set<ClassLoader> _classLoaders =
		ConcurrentHashMap.newKeySet();

}