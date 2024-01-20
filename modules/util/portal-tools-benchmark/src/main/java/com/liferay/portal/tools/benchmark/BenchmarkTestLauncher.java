/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.benchmark;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.lang.ThreadContextClassLoaderUtil;

import java.io.File;

import java.lang.reflect.Method;

import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Tina Tian
 */
public class BenchmarkTestLauncher {

	public static void main(String[] args) throws Exception {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		ClassLoader classLoader = new URLClassLoader(
			_getClassPathURLs(contextClassLoader), null);

		Class<?> clazz = classLoader.loadClass(
			"com.liferay.portal.tools.benchmark.BenchmarkTest");

		Method method = clazz.getMethod("main", String[].class);

		try (SafeCloseable safeCloseable = ThreadContextClassLoaderUtil.swap(
				classLoader)) {

			method.invoke(null, new Object[] {args});
		}
	}

	private static URL[] _getClassPathURLs(ClassLoader classLoader)
		throws Exception {

		Set<URL> classPathURLs = new LinkedHashSet<>();

		String classPath =
			System.getProperty("java.class.path") + File.pathSeparator +
				System.getProperty("sun.boot.class.path", "");

		for (String path : classPath.split(File.pathSeparator)) {
			File file = new File(path);

			URI uri = file.toURI();

			classPathURLs.add(uri.toURL());
		}

		URL url = classLoader.getResource("lib");

		try (FileSystem fileSystem = FileSystems.newFileSystem(
				url.toURI(), Collections.emptyMap());
			DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				fileSystem.getPath("/lib"), "*.jar")) {

			File tempDir = new File(
				System.getProperty("java.io.tmpdir"),
				String.valueOf(System.currentTimeMillis()));

			tempDir.mkdir();

			tempDir.deleteOnExit();

			Path tempDirPath = tempDir.toPath();

			Iterator<Path> iterator = directoryStream.iterator();

			while (iterator.hasNext()) {
				Path path = iterator.next();

				Path targetPath = tempDirPath.resolve(
					String.valueOf(path.getFileName()));

				Files.copy(path, targetPath);

				URI uri = targetPath.toUri();

				classPathURLs.add(uri.toURL());

				File file = targetPath.toFile();

				file.deleteOnExit();
			}
		}

		return classPathURLs.toArray(new URL[0]);
	}

}