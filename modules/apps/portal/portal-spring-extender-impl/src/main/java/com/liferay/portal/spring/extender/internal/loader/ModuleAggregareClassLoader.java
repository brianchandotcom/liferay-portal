/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.spring.extender.internal.loader;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ModuleAggregareClassLoader)) {
			return false;
		}

		ModuleAggregareClassLoader moduleAggregareClassLoader =
			(ModuleAggregareClassLoader)object;

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
		catch (ClassNotFoundException classNotFoundException) {
			if (_log.isDebugEnabled()) {
				_log.debug(classNotFoundException);
			}

			return _moduleClassLoader.loadClass(name);
		}
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		try {
			return (Class<?>)_FIND_CLASS_METHOD.invoke(
				_moduleClassLoader, name);
		}
		catch (InvocationTargetException invocationTargetException) {
			throw new ClassNotFoundException(
				"Unable to find class " + name,
				invocationTargetException.getTargetException());
		}
		catch (Exception exception) {
			throw new ClassNotFoundException(
				"Unable to find class " + name, exception);
		}
	}

	private static final Method _FIND_CLASS_METHOD;

	private static final Log _log = LogFactoryUtil.getLog(
		ModuleAggregareClassLoader.class);

	private static final ClassLoader _extenderClassLoader =
		ModuleAggregareClassLoader.class.getClassLoader();

	static {
		try {
			_FIND_CLASS_METHOD = ReflectionUtil.getDeclaredMethod(
				ClassLoader.class, "findClass", String.class);
		}
		catch (Exception exception) {
			throw new ExceptionInInitializerError(exception);
		}
	}

	private final ClassLoader _moduleClassLoader;
	private final String _namespace;

}