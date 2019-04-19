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

package com.liferay.portal.module.framework;

import com.liferay.petra.string.CharPool;

import java.io.IOException;

import java.net.URL;
import java.net.URLClassLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

/**
 * @author Miguel Pastor
 */
public class ModuleFrameworkClassLoader extends URLClassLoader {

	public ModuleFrameworkClassLoader(
		URL[] urls, ClassLoader parent, String[] packageNames) {

		super(urls, parent);

		_packageNames = packageNames;
	}

	@Override
	public URL getResource(String name) {
		URL url = findResource(name);

		if (url == null) {
			url = super.getResource(name);
		}

		return url;
	}

	@Override
	public Enumeration<URL> getResources(String name) throws IOException {
		final List<URL> urls = new ArrayList<>();

		urls.addAll(_buildURLs(null));

		Enumeration<URL> localURLs = findResources(name);

		urls.addAll(_buildURLs(localURLs));

		Enumeration<URL> parentURLs = null;

		ClassLoader parentClassLoader = getParent();

		if (parentClassLoader != null) {
			parentURLs = parentClassLoader.getResources(name);
		}

		urls.addAll(_buildURLs(parentURLs));

		return new Enumeration<URL>() {

			@Override
			public boolean hasMoreElements() {
				return _iterator.hasNext();
			}

			@Override
			public URL nextElement() {
				return _iterator.next();
			}

			private final Iterator<URL> _iterator = urls.iterator();

		};
	}

	@Override
	protected Class<?> loadClass(String name, boolean resolve)
		throws ClassNotFoundException {

		Object lock = getClassLoadingLock(name);

		synchronized (lock) {
			Class<?> clazz = findLoadedClass(name);

			if (clazz == null) {
				if (_hasPackageName(name)) {
					try {
						clazz = findClass(name);
					}
					catch (ClassNotFoundException cnfe) {
						clazz = super.loadClass(name, resolve);
					}
				}
				else {
					try {
						clazz = super.loadClass(name, resolve);
					}
					catch (ClassNotFoundException cnfe) {
						clazz = findClass(name);
					}
				}
			}

			if (resolve) {
				resolveClass(clazz);
			}

			return clazz;
		}
	}

	private List<URL> _buildURLs(Enumeration<URL> url) {
		if (url == null) {
			return new ArrayList<>();
		}

		List<URL> urls = new ArrayList<>();

		while (url.hasMoreElements()) {
			urls.add(url.nextElement());
		}

		return urls;
	}

	private boolean _hasPackageName(String name) {
		String packageName = name;

		int index = name.lastIndexOf(CharPool.PERIOD);

		if (index != -1) {
			packageName = name.substring(0, index);
		}

		index = Arrays.binarySearch(_packageNames, packageName);

		if (index >= 0) {
			return true;
		}

		index = -index - 1;

		if ((index == 0) || !packageName.startsWith(_packageNames[index - 1])) {
			return false;
		}

		return true;
	}

	private final String[] _packageNames;

	static {
		ClassLoader.registerAsParallelCapable();
	}

}