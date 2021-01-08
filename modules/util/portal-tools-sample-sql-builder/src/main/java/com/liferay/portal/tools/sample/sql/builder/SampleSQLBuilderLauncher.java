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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.SystemProperties;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.lang.reflect.Method;

import java.net.JarURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Lily Chi
 */
public class SampleSQLBuilderLauncher {

	public static void main(String[] args) throws Exception {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		ClassLoader classLoader = new URLClassLoader(
			_getDependencies(contextClassLoader), null);

		Class<?> clazz = classLoader.loadClass(
			"com.liferay.portal.tools.sample.sql.builder.SampleSQLBuilder");

		Method method = clazz.getMethod("main", String[].class);

		currentThread.setContextClassLoader(classLoader);

		try {
			method.invoke(null, new Object[] {args});
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	private static URL[] _getDependencies(ClassLoader classLoader)
		throws Exception {

		Set<URL> urls = new LinkedHashSet<>();

		URL[] javaClassPathURLs = ClassPathUtil.getClassPathURLs(
			ClassPathUtil.getJVMClassPath(true));

		Collections.addAll(urls, javaClassPathURLs);

		File tempDir = new File(SystemProperties.get(SystemProperties.TMP_DIR));

		URL libDirURL = classLoader.getResource("lib");

		JarURLConnection jarURLConnection =
			(JarURLConnection)libDirURL.openConnection();

		JarFile jarFile = jarURLConnection.getJarFile();

		_unJar(jarFile, tempDir);

		jarFile.close();

		for (String jar : _getLibs(new File(tempDir, "lib"))) {
			File file = new File(jar);

			URI uri = file.toURI();

			urls.add(uri.toURL());
		}

		return urls.toArray(new URL[0]);
	}

	private static List<String> _getLibs(File baseDir) throws Exception {
		List<String> libs = new ArrayList<>();

		Files.walkFileTree(
			baseDir.toPath(),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String fileName = String.valueOf(path.getFileName());

					if (fileName.endsWith(".jar")) {
						libs.add(String.valueOf(path.toAbsolutePath()));
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return libs;
	}

	private static void _unJar(JarFile jarFile, File destDir) throws Exception {
		Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();

		if (!destDir.exists()) {
			destDir.mkdirs();
		}

		byte[] bytes = new byte[1024];

		while (jarEntryEnumeration.hasMoreElements()) {
			JarEntry jarEntry = jarEntryEnumeration.nextElement();

			String name = jarEntry.getName();

			if (name.endsWith(".jar")) {
				File destFile = new File(
					StringBundler.concat(
						destDir.getAbsoluteFile(), File.separator,
						jarEntry.getName()));

				if (jarEntry.isDirectory() && !destFile.exists()) {
					destFile.mkdirs();
				}
				else {
					File destFileParent = destFile.getParentFile();

					if (!destFileParent.exists()) {
						destFileParent.mkdirs();
					}

					try (BufferedInputStream bufferedInputStream =
							new BufferedInputStream(
								jarFile.getInputStream(jarEntry));
						BufferedOutputStream bufferedOutputStream =
							new BufferedOutputStream(
								new FileOutputStream(destFile))) {

						int length = bufferedInputStream.read(
							bytes, 0, bytes.length);

						while (length != -1) {
							bufferedOutputStream.write(bytes, 0, length);
							length = bufferedInputStream.read(
								bytes, 0, bytes.length);
						}
					}
				}
			}
		}
	}

}