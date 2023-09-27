/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.template.freemarker.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;

import java.net.URL;
import java.net.URLClassLoader;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Miguel Pastor
 * @author Raymond Augé
 */
public class FreeMarkerBundleClassloader extends URLClassLoader {

	public FreeMarkerBundleClassloader(Set<ClassLoader> classLoaders) {
		super(new URL[0]);

		_classLoaders = new HashSet<>(classLoaders);

		Class<?> clazz = FreeMarkerBundleClassloader.class;

		_classLoaders.add(clazz.getClassLoader());
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

	static {
		ClassLoader.registerAsParallelCapable();
	}

	private final Set<ClassLoader> _classLoaders;

}