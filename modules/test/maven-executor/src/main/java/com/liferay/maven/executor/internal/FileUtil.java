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

package com.liferay.maven.executor.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Andrea Di Giorgi
 */
public class FileUtil {

	public static void deleteDirectory(Path dirPath) throws IOException {
		Files.walkFileTree(
			dirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult postVisitDirectory(
						Path dirPath, IOException ioe)
					throws IOException {

					Files.delete(dirPath);

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Files.delete(path);

					return FileVisitResult.CONTINUE;
				}

			});
	}

	public static String getAbsolutePath(Path path) {
		String absolutePath = String.valueOf(path.toAbsolutePath());

		if (File.separatorChar != '/') {
			absolutePath = absolutePath.replace(File.separatorChar, '/');
		}

		return absolutePath;
	}

	public static String read(Class<?> clazz, String name) throws IOException {
		StringBuilder sb = new StringBuilder();

		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(clazz.getResourceAsStream(name)))) {

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				if (sb.length() > 0) {
					sb.append(System.lineSeparator());
				}

				sb.append(line);
			}
		}

		return sb.toString();
	}

	public static void unzip(
			String fileName, Path outputDirPath, boolean skipRoot)
		throws IOException {

		try (ZipFile zipFile = new ZipFile(fileName)) {
			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			while (enumeration.hasMoreElements()) {
				ZipEntry zipEntry = enumeration.nextElement();

				String name = zipEntry.getName();

				if (name.endsWith("/")) {
					continue;
				}

				if (skipRoot) {
					int pos = name.indexOf('/');

					if (pos == -1) {
						continue;
					}

					name = name.substring(pos + 1);
				}

				Path path = outputDirPath.resolve(name);

				Files.createDirectories(path.getParent());

				Files.copy(zipFile.getInputStream(zipEntry), path);
			}
		}
	}

}