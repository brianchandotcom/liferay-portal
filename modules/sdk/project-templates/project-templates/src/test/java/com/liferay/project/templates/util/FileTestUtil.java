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

package com.liferay.project.templates.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;

import java.nio.file.DirectoryStream;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.FileSystem;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Andrea Di Giorgi
 */
public class FileTestUtil {

	public static final String PROJECT_TEMPLATE_DIR_PREFIX =
		"project-templates-";

	public static boolean containsFile(Path rootDirPath, String pattern)
		throws IOException {

		Path path = getFile(rootDirPath, pattern);

		if (path != null) {
			return true;
		}

		return false;
	}

	public static String getExtension(String fileName) {
		int pos = fileName.lastIndexOf('.');

		if (pos == -1) {
			return "";
		}

		return fileName.substring(pos + 1);
	}

	public static Path getFile(Path rootDirPath, String pattern)
		throws IOException {

		final AtomicReference<Path> foundPath = new AtomicReference<>();

		FileSystem fileSystem = rootDirPath.getFileSystem();

		final PathMatcher pathMatcher = fileSystem.getPathMatcher(
			"glob:" + pattern);

		Files.walkFileTree(
			rootDirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
					Path path, BasicFileAttributes basicFileAttributes) {

					if (pathMatcher.matches(path.getFileName())) {
						foundPath.set(path);

						return FileVisitResult.TERMINATE;
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return foundPath.get();
	}

	public static DirectoryStream<Path> getProjectTemplatesDirectoryStream()
		throws IOException {

		return Files.newDirectoryStream(
			Paths.get("../"),
			new Filter<Path>() {

				@Override
				public boolean accept(Path path) throws IOException {
					if (!Files.isDirectory(path)) {
						return false;
					}

					Path fileNamePath = path.getFileName();

					String fileName = fileNamePath.toString();

					if (fileName.startsWith(PROJECT_TEMPLATE_DIR_PREFIX)) {
						return true;
					}

					return false;
				}

			});
	}

	public static String read(String name) throws IOException {
		StringBuilder sb = new StringBuilder();

		ClassLoader classLoader = FileTestUtil.class.getClassLoader();

		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(classLoader.getResourceAsStream(name)))) {

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				if (sb.length() > 0) {
					sb.append('\n');
				}

				sb.append(line);
			}
		}

		return sb.toString();
	}

	public static Properties readProperties(String name) throws IOException {
		Properties properties = new Properties();

		ClassLoader classLoader = FileTestUtil.class.getClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(name)) {
			properties.load(inputStream);
		}

		return properties;
	}

	public static void write(Writer writer, String... lines)
		throws IOException {

		for (String line : lines) {
			writer.write(line);
			writer.write(System.lineSeparator());
		}
	}

}