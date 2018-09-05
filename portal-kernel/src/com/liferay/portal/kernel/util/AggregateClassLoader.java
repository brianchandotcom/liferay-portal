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

import com.liferay.petra.memory.EqualityWeakReference;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.LoggedExceptionInInitializerError;

import java.io.IOException;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public class AggregateClassLoader extends ClassLoader {

	public static ClassLoader getAggregateClassLoader(
		ClassLoader parentClassLoader, ClassLoader... classLoaders) {

		if (ArrayUtil.isEmpty(classLoaders)) {
			return parentClassLoader;
		}

		AggregateClassLoader aggregateClassLoader = null;

		if (parentClassLoader instanceof AggregateClassLoader) {
			aggregateClassLoader = (AggregateClassLoader)parentClassLoader;

			List<ClassLoader> existingClassLoaders =
				aggregateClassLoader.getClassLoaders();

			boolean requiresNew = false;

			for (ClassLoader classLoader : classLoaders) {
				if (!classLoader.equals(parentClassLoader) &&
					!existingClassLoaders.contains(classLoader)) {

					requiresNew = true;

					break;
				}
			}

			if (!requiresNew) {
				return aggregateClassLoader;
			}

			aggregateClassLoader = new AggregateClassLoader(
				parentClassLoader.getParent());

			aggregateClassLoader.addClassLoader(parentClassLoader);
		}
		else {
			aggregateClassLoader = new AggregateClassLoader(parentClassLoader);
		}

		for (ClassLoader classLoader : classLoaders) {
			aggregateClassLoader.addClassLoader(classLoader);
		}

		return aggregateClassLoader;
	}

	public static ClassLoader getAggregateClassLoader(
		ClassLoader[] classLoaders) {

		if (ArrayUtil.isEmpty(classLoaders)) {
			return null;
		}

		return getAggregateClassLoader(classLoaders[0], classLoaders);
	}

	public AggregateClassLoader(ClassLoader classLoader) {
		super(classLoader);
	}

	public void addClassLoader(ClassLoader classLoader) {
		if (classLoader.equals(getParent())) {
			return;
		}

		List<ClassLoader> classLoaders = getClassLoaders();

		if (classLoaders.contains(classLoader)) {
			return;
		}

		if (classLoader instanceof AggregateClassLoader) {
			AggregateClassLoader aggregateClassLoader =
				(AggregateClassLoader)classLoader;

			addClassLoader(aggregateClassLoader.getParent());

			for (ClassLoader curClassLoader :
					aggregateClassLoader.getClassLoaders()) {

				addClassLoader(curClassLoader);
			}

			return;
		}

		_classLoaderReferences.add(new EqualityWeakReference<>(classLoader));
	}

	public void addClassLoader(ClassLoader... classLoaders) {
		for (ClassLoader classLoader : classLoaders) {
			addClassLoader(classLoader);
		}
	}

	public void addClassLoader(Collection<ClassLoader> classLoaders) {
		for (ClassLoader classLoader : classLoaders) {
			addClassLoader(classLoader);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AggregateClassLoader)) {
			return false;
		}

		AggregateClassLoader aggregateClassLoader = (AggregateClassLoader)obj;

		if (_classLoaderReferences.equals(
				aggregateClassLoader._classLoaderReferences) &&
			(((getParent() == null) &&
			  (aggregateClassLoader.getParent() == null)) ||
			 ((getParent() != null) &&
			  getParent().equals(aggregateClassLoader.getParent())))) {

			return true;
		}

		return false;
	}

	public List<ClassLoader> getClassLoaders() {
		List<ClassLoader> classLoaders = new ArrayList<>(
			_classLoaderReferences.size());

		Iterator<EqualityWeakReference<ClassLoader>> itr =
			_classLoaderReferences.iterator();

		while (itr.hasNext()) {
			WeakReference<ClassLoader> weakReference = itr.next();

			ClassLoader classLoader = weakReference.get();

			if (classLoader == null) {
				itr.remove();
			}
			else {
				classLoaders.add(classLoader);
			}
		}

		return classLoaders;
	}

	@Override
	public URL getResource(String name) {
		for (ClassLoader classLoader : getClassLoaders()) {
			URL url = _getResource(classLoader, name);

			if (url != null) {
				return url;
			}
		}

		return super.getResource(name);
	}

	@Override
	public Enumeration<URL> getResources(String name) throws IOException {
		List<URL> urls = new ArrayList<>();

		for (ClassLoader classLoader : getClassLoaders()) {
			urls.addAll(Collections.list(_getResources(classLoader, name)));
		}

		urls.addAll(Collections.list(_getResources(getParent(), name)));

		return Collections.enumeration(urls);
	}

	@Override
	public int hashCode() {
		if (_classLoaderReferences != null) {
			return _classLoaderReferences.hashCode();
		}

		return 0;
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		for (ClassLoader classLoader : getClassLoaders()) {
			try {
				return _findClass(classLoader, name);
			}
			catch (ClassNotFoundException cnfe) {
			}
		}

		throw new ClassNotFoundException("Unable to find class " + name);
	}

	@Override
	protected synchronized Class<?> loadClass(String name, boolean resolve)
		throws ClassNotFoundException {

		Class<?> loadedClass = null;

		for (ClassLoader classLoader : getClassLoaders()) {
			try {
				loadedClass = _loadClass(classLoader, name, resolve);

				break;
			}
			catch (ClassNotFoundException cnfe) {
			}
		}

		if (loadedClass == null) {
			loadedClass = _loadClass(getParent(), name, resolve);
		}
		else if (resolve) {
			resolveClass(loadedClass);
		}

		return loadedClass;
	}

	private static Class<?> _findClass(ClassLoader classLoader, String name)
		throws ClassNotFoundException {

		try {
			return (Class<?>)_FIND_CLASS_METHOD.invoke(classLoader, name);
		}
		catch (InvocationTargetException ite) {
			throw new ClassNotFoundException(
				"Unable to find class " + name, ite.getTargetException());
		}
		catch (Exception e) {
			throw new ClassNotFoundException("Unable to find class " + name, e);
		}
	}

	private static URL _getResource(ClassLoader classLoader, String name) {
		try {
			return (URL)_GET_RESOURCE_METHOD.invoke(classLoader, name);
		}
		catch (InvocationTargetException ite) {
			return null;
		}
		catch (Exception e) {
			return null;
		}
	}

	private static Enumeration<URL> _getResources(
			ClassLoader classLoader, String name)
		throws IOException {

		try {
			return (Enumeration<URL>)_GET_RESOURCES_METHOD.invoke(
				classLoader, name);
		}
		catch (InvocationTargetException ite) {
			Throwable t = ite.getTargetException();

			throw new IOException(t);
		}
		catch (Exception e) {
			throw new IOException(e);
		}
	}

	private static Class<?> _loadClass(
			ClassLoader classLoader, String name, boolean resolve)
		throws ClassNotFoundException {

		try {
			return (Class<?>)_LOAD_CLASS_METHOD.invoke(
				classLoader, name, resolve);
		}
		catch (InvocationTargetException ite) {
			throw new ClassNotFoundException(
				"Unable to load class " + name, ite.getTargetException());
		}
		catch (Exception e) {
			throw new ClassNotFoundException("Unable to load class " + name, e);
		}
	}

	private static final Method _FIND_CLASS_METHOD;

	private static final Method _GET_RESOURCE_METHOD;

	private static final Method _GET_RESOURCES_METHOD;

	private static final Method _LOAD_CLASS_METHOD;

	static {
		try {
			_FIND_CLASS_METHOD = ReflectionUtil.getDeclaredMethod(
				ClassLoader.class, "findClass", String.class);

			_GET_RESOURCE_METHOD = ReflectionUtil.getDeclaredMethod(
				ClassLoader.class, "getResource", String.class);

			_GET_RESOURCES_METHOD = ReflectionUtil.getDeclaredMethod(
				ClassLoader.class, "getResources", String.class);

			_LOAD_CLASS_METHOD = ReflectionUtil.getDeclaredMethod(
				ClassLoader.class, "loadClass", String.class, boolean.class);
		}
		catch (Exception e) {
			throw new LoggedExceptionInInitializerError(e);
		}
	}

	private final List<EqualityWeakReference<ClassLoader>>
		_classLoaderReferences = new ArrayList<>();

}