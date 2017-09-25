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

package com.liferay.gradle.plugins.workspace.internal.util;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.gradle.api.UncheckedIOException;

/**
 * @author Andrea Di Giorgi
 */
public class FileUtil extends com.liferay.gradle.util.FileUtil {

	public static void moveTree(File sourceRootDir, File destinationRootDir) {
		try {
			_moveTree(sourceRootDir.toPath(), destinationRootDir.toPath());
		}
		catch (IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}

	public static String read(File file) {
		try {
			return new String(
				Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
		}
		catch (IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}

	private static void _moveTree(
			final Path sourceRootDirPath, final Path destinationRootDirPath)
		throws IOException {

		Files.walkFileTree(
			sourceRootDirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult postVisitDirectory(
						Path dirPath, IOException ioe)
					throws IOException {

					Files.delete(dirPath);

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Path relativePath = sourceRootDirPath.relativize(dirPath);

					Path destinationDirPath = destinationRootDirPath.resolve(
						relativePath);

					Files.createDirectories(destinationDirPath);

					return FileVisitResult.CONTINUE;
				}

			});
	}

}