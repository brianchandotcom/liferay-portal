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

package com.liferay.portal.spring.extender.internal.loader;

import com.liferay.petra.reflect.ReflectionUtil;

import java.io.IOException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

/**
 * @author Shuyang Zhou
 */
public class ModuleAggregareClassLoader extends ClassLoader {

	public ModuleAggregareClassLoader(
		ClassLoader moduleClassLoader, String symbolicName) {

		super(null);

		_moduleClassLoader = moduleClassLoader;

		int index = symbolicName.lastIndexOf('.');

		if (index == -1) {
			_namespace = symbolicName;
		}
		else {
			_namespace = symbolicName.substring(0, index);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ModuleAggregareClassLoader)) {
			return false;
		}

		ModuleAggregareClassLoader moduleAggregareClassLoader =
			(ModuleAggregareClassLoader)obj;

		if (Objects.equals(
				_moduleClassLoader,
				moduleAggregareClassLoader._moduleClassLoader)) {

			return true;
		}

		return false;
	}

	@Override
	public URL getResource(String name) {
		URL url = _moduleClassLoader.getResource(name);

		if (url != null) {
			return url;
		}

		return _extenderClassLoader.getResource(name);
	}

	@Override
	public Enumeration<URL> getResources(String name) throws IOException {
		List<URL> urls = new ArrayList<>();

		urls.addAll(Collections.list(_moduleClassLoader.getResources(name)));

		urls.addAll(Collections.list(_extenderClassLoader.getResources(name)));

		return Collections.enumeration(urls);
	}

	@Override
	public int hashCode() {
		return _moduleClassLoader.hashCode();
	}

	@Override
	public Class<?> loadClass(String name, boolean resolve)
		throws ClassNotFoundException {

		if (name.startsWith(_namespace)) {
			return _moduleClassLoader.loadClass(name);
		}

		try {
			return _extenderClassLoader.loadClass(name);
		}
		catch (ClassNotFoundException cnfe) {
			return _moduleClassLoader.loadClass(name);
		}
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		try {
			return (Class<?>)_FIND_CLASS_METHOD.invoke(
				_moduleClassLoader, name);
		}
		catch (InvocationTargetException ite) {
			throw new ClassNotFoundException(
				"Unable to find class " + name, ite.getTargetException());
		}
		catch (Exception e) {
			throw new ClassNotFoundException("Unable to find class " + name, e);
		}
	}

	private static final Method _FIND_CLASS_METHOD;

	private static final ClassLoader _extenderClassLoader =
		ModuleAggregareClassLoader.class.getClassLoader();

	static {
		try {
			_FIND_CLASS_METHOD = ReflectionUtil.getDeclaredMethod(
				ClassLoader.class, "findClass", String.class);
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	private final ClassLoader _moduleClassLoader;
	private final String _namespace;

}