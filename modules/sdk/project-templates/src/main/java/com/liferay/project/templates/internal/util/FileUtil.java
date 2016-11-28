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

package com.liferay.project.templates.internal.util;

import com.liferay.project.templates.ProjectTemplates;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Andrea Di Giorgi
 */
public class FileUtil {

	public static void deleteDir(Path dirPath) throws IOException {
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

	public static void extractDirectory(
			String dirName, final Path destinationDirPath)
		throws Exception {

		File file = getJarFile();

		if (file.isDirectory()) {
			Path jarDirPath = file.toPath();

			final Path rootDirPath = jarDirPath.resolve(dirName);

			Files.walkFileTree(
				rootDirPath,
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult visitFile(
							Path path, BasicFileAttributes basicFileAttributes)
						throws IOException {

						Path relativePath = rootDirPath.relativize(path);

						String fileName = relativePath.toString();

						Path destinationPath = destinationDirPath.resolve(
							fileName);

						Files.createDirectories(destinationPath.getParent());

						Files.copy(
							path, destinationPath,
							StandardCopyOption.REPLACE_EXISTING);

						return FileVisitResult.CONTINUE;
					}

				});
		}
		else {
			try (JarFile jarFile = new JarFile(file)) {
				Enumeration<JarEntry> enumeration = jarFile.entries();

				while (enumeration.hasMoreElements()) {
					JarEntry jarEntry = enumeration.nextElement();

					if (jarEntry.isDirectory()) {
						continue;
					}

					String name = jarEntry.getName();

					if (!name.startsWith(dirName + "/")) {
						continue;
					}

					String fileName = name.substring(dirName.length() + 1);

					Path destinationPath = destinationDirPath.resolve(fileName);

					Files.createDirectories(destinationPath.getParent());

					try (InputStream inputStream = jarFile.getInputStream(
							jarEntry)) {

						Files.copy(
							inputStream, destinationPath,
							StandardCopyOption.REPLACE_EXISTING);
					}
				}
			}
		}
	}

	public static Path getFile(Path dirPath, String glob) throws IOException {
		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				dirPath, glob)) {

			Iterator<Path> iterator = directoryStream.iterator();

			if (iterator.hasNext()) {
				return iterator.next();
			}
		}

		return null;
	}

	public static File getJarFile() throws Exception {
		ProtectionDomain protectionDomain =
			ProjectTemplates.class.getProtectionDomain();

		CodeSource codeSource = protectionDomain.getCodeSource();

		URL url = codeSource.getLocation();

		return new File(url.toURI());
	}

	public static Path getRootDir(Path dirPath, String markerFileName) {
		while (true) {
			if (Files.exists(dirPath.resolve(markerFileName))) {
				return dirPath;
			}

			dirPath = dirPath.getParent();

			if (dirPath == null) {
				return null;
			}
		}
	}

	public static String read(Path path) throws IOException {
		String content = new String(
			Files.readAllBytes(path), StandardCharsets.UTF_8);

		return content.replace("\r\n", "\n");
	}

}