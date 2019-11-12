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

package com.liferay.gradle.plugins.soy.internal.util;

import java.io.File;

import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author     Andrea Di Giorgi
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class GradleUtil extends com.liferay.gradle.util.GradleUtil {

	public static <T> T withClasspath(
			Iterable<File> classpath, Callable<T> callable)
		throws Exception {

		Thread currentThread = Thread.currentThread();

		URLClassLoader contextURLClassLoader =
			(URLClassLoader)currentThread.getContextClassLoader();

		List<URL> urls = new ArrayList<>();

		for (File file : classpath) {
			URI uri = file.toURI();

			urls.add(uri.toURL());
		}

		Collections.addAll(urls, contextURLClassLoader.getURLs());

		try (URLClassLoader urlClassLoader = new URLClassLoader(
				urls.toArray(new URL[0]), null)) {

			currentThread.setContextClassLoader(urlClassLoader);

			return callable.call();
		}
		finally {
			currentThread.setContextClassLoader(contextURLClassLoader);
		}
	}

}