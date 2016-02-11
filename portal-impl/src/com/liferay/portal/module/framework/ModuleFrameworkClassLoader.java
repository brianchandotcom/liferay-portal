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

import com.liferay.portal.kernel.util.ReflectionUtil;

import java.io.IOException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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

		Field tomcat8StateField = null;
		Object tomcat8StartedState = null;

		try {
			Class<?> clazz = parent.getClass();
			ClassLoader classLoader = clazz.getClassLoader();

			Class<?> lifecycleStateClass = classLoader.loadClass(
				"org.apache.catalina.LifecycleState");

			Method valueOfMethod = lifecycleStateClass.getMethod(
				"valueOf", String.class);

			tomcat8StartedState = valueOfMethod.invoke(null, "STARTED");

			Class<?> WebappClassLoaderBaseClass = classLoader.loadClass(
				"org.apache.catalina.loader.WebappClassLoaderBase");

			tomcat8StateField = ReflectionUtil.getDeclaredField(
				WebappClassLoaderBaseClass, "state");
		}
		catch (Exception e) {
			tomcat8StartedState = null;
			tomcat8StateField = null;
		}

		_tomcat8StartedState = tomcat8StartedState;
		_tomcat8StateField = tomcat8StateField;
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

			final Iterator<URL> iterator = urls.iterator();

			@Override
			public boolean hasMoreElements() {
				return iterator.hasNext();
			}

			@Override
			public URL nextElement() {
				return iterator.next();
			}

		};
	}

	@Override
	protected Class<?> loadClass(String name, boolean resolve)
		throws ClassNotFoundException {

		Object lock = getClassLoadingLock(name);

		synchronized (lock) {
			Class<?> clazz = findLoadedClass(name);

			if (clazz == null) {
				try {
					clazz = findClass(name);
				}
				catch (ClassNotFoundException cnfe) {
					if (_tomcat8StateField == null) {
						clazz = super.loadClass(name, resolve);
					}
					else {
						clazz = _loadForTomcat8(cnfe, name, resolve);
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

	private Class<?> _loadForTomcat8(
			ClassNotFoundException cnfe, String name, boolean resolve)
		throws ClassNotFoundException {

		ClassLoader classLoader = getParent();

		try {
			Object state = _tomcat8StateField.get(classLoader);

			if (state == _tomcat8StartedState) {
				return super.loadClass(name, resolve);
			}

			_tomcat8StateField.set(classLoader, _tomcat8StartedState);

			try {
				return super.loadClass(name, resolve);
			}
			finally {
				_tomcat8StateField.set(classLoader, state);
			}
		}
		catch (ReflectiveOperationException roe) {
			cnfe.addSuppressed(roe);

			throw cnfe;
		}
	}

	static {
		ClassLoader.registerAsParallelCapable();
	}

	private final Object _tomcat8StartedState;
	private final Field _tomcat8StateField;

}