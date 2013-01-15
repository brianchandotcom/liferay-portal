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

package com.liferay.portal.module.framework;

import java.net.URL;
import java.net.URLClassLoader;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

/**
 * @author Miguel Pastor
 */
public class ModuleFrameworkClassLoader extends URLClassLoader {

	public ModuleFrameworkClassLoader(URL[] urls, ClassLoader parent) {
		super(urls, parent);

		// On some app servers we could find their own OSGI framework in the
		// boostrap classloader

		// _system = getSystemClassLoader();

	}

	@Override
	public URL getResource(String name) {
		URL url = null;

		if (_system != null) {
			url = _system.getResource(name);
		}

		if (url == null) {
			url = findResource(name);

			if (url == null) {
				url = super.getResource(name);
			}
		}

		return url;
	}

	@Override
	public Enumeration<URL> getResources(String name)
		throws java.io.IOException {

		Enumeration<URL> systemURLs = null;

		if (_system != null) {
			systemURLs = _system.getResources(name);
		}

		Enumeration<URL> localURLs = findResources(name);

		Enumeration<URL> parentURLs = null;

		if (getParent() != null) {
			parentURLs = getParent().getResources(name);
		}

		final List<URL> urls = new ArrayList<URL>();

		// the order of this operations is important

		urls.addAll(_buildURLs(systemURLs));

		urls.addAll(_buildURLs(localURLs));

		urls.addAll(_buildURLs(parentURLs));

		return new Enumeration<URL>() {
			final Iterator<URL> iterator = urls.iterator();

			public boolean hasMoreElements() {
				return iterator.hasNext();
			}

			public URL nextElement() {
				return iterator.next();
			}
		};
	}

	@Override
	protected synchronized Class<?> loadClass(
		String name, boolean resolve) throws ClassNotFoundException {

		Class<?> clazz = findLoadedClass(name);

		if (clazz == null) {
			if (_system != null) {
				try {
					clazz = _system.loadClass(name);
				}
				catch (ClassNotFoundException classNotFoundException) {

					// Ignore it!

				}
			}

			if (clazz == null) {
				try {
					clazz = findClass(name);
				}
				catch (ClassNotFoundException classNotFoundException) {
					clazz = super.loadClass(name, resolve);
				}
			}
		}

		if (resolve) {
			resolveClass(clazz);
		}

		return clazz;
	}

	private List<URL> _buildURLs(Enumeration<URL> url) {
		if (url == null) {
			return new ArrayList<URL>();
		}

		List<URL> urls = new ArrayList<URL>();

		while (url.hasMoreElements()) {
			urls.add(url.nextElement());
		}

		return urls;
	}

	private ClassLoader _system;

}