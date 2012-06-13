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

package com.liferay.portal.kernel.test;

import java.io.File;

import java.lang.reflect.Constructor;

import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Shuyang Zhou
 */
public class NewClassLoaderTestCase extends TestCase {

	@Override
	public void setUp() throws Exception {
		String pathsString = System.getProperty("java.class.path");

		String[] paths = pathsString.split(File.pathSeparator);

		List<URL> urlsList = new ArrayList<URL>();

		for (String path : paths) {
			File file = new File(path);

			URI uri = file.toURI();

			urlsList.add(uri.toURL());
		}

		urls = urlsList.toArray(new URL[urlsList.size()]);
	}

	protected <T> T runInNewClassLoader(Class<? extends Callable<T>> clazz)
		throws Exception {

		assertSame(
			clazz + " is not enclosed by " + getClass(), getClass(),
			clazz.getEnclosingClass());

		Constructor<? extends Callable<T>> constructor = null;

		try {
			constructor = clazz.getDeclaredConstructor();
		}
		catch (NoSuchMethodException nsme1) {
			try {
				constructor = clazz.getDeclaredConstructor(getClass());
			}
			catch (NoSuchMethodException nsme2) {
				throw new Exception(
					clazz.getName() + " does not have a default constructor");
			}
		}

		return runInNewClassLoader(constructor);
	}

	protected <T> T runInNewClassLoader(
			Constructor<? extends Callable<T>> constructor, Object... arguments)
		throws Exception {

		// Prepare new class loader

		URLClassLoader urlClassLoader = new URLClassLoader(urls, null);

		// Get loaded class

		Class<? extends Callable<T>> callableClass =
			constructor.getDeclaringClass();

		assertSame(
			callableClass + " is not enclosed by " + getClass(), getClass(),
			callableClass.getEnclosingClass());

		// Reload class with new class loader

		callableClass = (Class<? extends Callable<T>>)urlClassLoader.loadClass(
			callableClass.getName());

		// Reload constructor paramter types

		Class<?>[] parameterTypes = constructor.getParameterTypes();

		for (int i = 0; i < parameterTypes.length; i++) {
			parameterTypes[i] = urlClassLoader.loadClass(
				parameterTypes[i].getName());
		}

		// Refetch constructor

		constructor = callableClass.getDeclaredConstructor(parameterTypes);

		// Inner class requires outter reference

		if (parameterTypes.length > arguments.length) {

			// Reload outter class with new class loader

			Class<?> outterClass = urlClassLoader.loadClass(
				getClass().getName());

			// Create outter object for the inner class instance

			Object outterObject = outterClass.newInstance();

			Object[] newArguments = new Object[arguments.length + 1];

			newArguments[0] = outterObject;

			System.arraycopy(arguments, 0, newArguments, 1, arguments.length);

			arguments = newArguments;
		}

		constructor.setAccessible(true);

		// Create callable instance that is fully loaded by the new class loader

		Callable<T> callable = constructor.newInstance(arguments);

		// Run callable with new class loader

		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(urlClassLoader);

			return callable.call();
		}
		finally {
			currentThread.setContextClassLoader(classLoader);
		}
	}

	protected URL[] urls;

}