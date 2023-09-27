/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
						Path dirPath, IOException ioException)
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