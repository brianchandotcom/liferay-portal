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

package com.liferay.portal.tools.sample.sql.builder;

import com.liferay.petra.process.ClassPathUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.tools.ToolDependencies;

import java.io.File;
import java.io.IOException;

import java.lang.reflect.Method;

import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Lily Chi
 */
public class SampleSQLBuilderLauncher {

	public static void main(String[] args) throws Exception {
		ToolDependencies.wireBasic();

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		File tempDir = FileUtil.createTempFolder();

		ClassLoader classLoader = new URLClassLoader(
			_getDependencies(contextClassLoader, tempDir.toPath()), null);

		Class<?> clazz = classLoader.loadClass(
			"com.liferay.portal.tools.sample.sql.builder.SampleSQLBuilder");

		Method method = clazz.getMethod("main", String[].class);

		currentThread.setContextClassLoader(classLoader);

		try {
			method.invoke(null, new Object[] {args});
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);

			FileUtil.deltree(tempDir);
		}
	}

	private static URL[] _getDependencies(
			ClassLoader classLoader, Path tempDirPath)
		throws Exception {

		Set<URL> urls = SetUtil.fromArray(
			ClassPathUtil.getClassPathURLs(
				ClassPathUtil.getJVMClassPath(true)));

		URL url = classLoader.getResource("lib");

		try (FileSystem fileSystem = FileSystems.newFileSystem(
				url.toURI(), Collections.emptyMap())) {

			Stream<Path> pathStream = Files.list(fileSystem.getPath("/lib"));

			pathStream.forEach(
				path -> {
					Path fileNamePath = path.getFileName();

					Path targetPath = Paths.get(
						tempDirPath.toString(), fileNamePath.toString());

					try {
						Files.copy(path, targetPath);

						URI uri = targetPath.toUri();

						urls.add(uri.toURL());
					}
					catch (IOException ioException) {
					}
				});
		}

		return urls.toArray(new URL[0]);
	}

}