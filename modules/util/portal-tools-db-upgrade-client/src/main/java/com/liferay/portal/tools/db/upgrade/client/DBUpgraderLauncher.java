/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.upgrade.client;

import java.io.File;
import java.io.ObjectInputStream;

import java.lang.reflect.Method;

import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Dante Wang
 */
public class DBUpgraderLauncher {

	public static void main(String[] args) throws Exception {
		ObjectInputStream objectInputStream = new ObjectInputStream(System.in);

		String classPath = (String)objectInputStream.readObject();

		ClassLoader classLoader = new URLClassLoader(
			_getClassPathURLs(classPath));

		Class<?> clazz = classLoader.loadClass(
			"com.liferay.portal.tools.DBUpgrader");

		Method method = clazz.getMethod("main", String[].class);

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(classLoader);

		try {
			method.invoke(null, new Object[] {args});
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	private static URL[] _getClassPathURLs(String classPath) throws Exception {
		String[] paths = classPath.split(File.pathSeparator);

		Set<URL> urls = new LinkedHashSet<>();

		for (String path : paths) {
			File file = new File(path);

			URI uri = file.toURI();

			urls.add(uri.toURL());
		}

		return urls.toArray(new URL[0]);
	}

}