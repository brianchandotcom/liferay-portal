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

package com.liferay.css.builder.internal.util;

import com.liferay.css.builder.CSSBuilder;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.nio.file.FileSystem;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class FileUtil {

	public static void deltree(Path dirPath) throws IOException {
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

	public static String[] getFilesFromDirectory(
			String baseDir, String[] includes, String[] excludes)
		throws IOException {

		final List<String> fileNames = new ArrayList<>();

		final Path baseDirPath = Paths.get(baseDir);

		FileSystem fileSystem = baseDirPath.getFileSystem();

		final List<PathMatcher> includePathMatchers = _getPathMatchers(
			fileSystem, includes);
		final List<PathMatcher> excludePathMatchers = _getPathMatchers(
			fileSystem, excludes);

		Files.walkFileTree(
			baseDirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
					Path path, BasicFileAttributes basicFileAttributes) {

					for (PathMatcher pathMatcher : excludePathMatchers) {
						if (pathMatcher.matches(path)) {
							return FileVisitResult.CONTINUE;
						}
					}

					for (PathMatcher pathMatcher : includePathMatchers) {
						if (pathMatcher.matches(path)) {
							String fileName = String.valueOf(
								baseDirPath.relativize(path));

							fileNames.add(fileName);

							break;
						}
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return fileNames.toArray(new String[0]);
	}

	public static File getJarFile() throws Exception {
		ProtectionDomain protectionDomain =
			CSSBuilder.class.getProtectionDomain();

		CodeSource codeSource = protectionDomain.getCodeSource();

		URL url = codeSource.getLocation();

		return new File(url.toURI());
	}

	public static long getLastModifiedTime(Path path) {
		try {
			FileTime fileTime = Files.getLastModifiedTime(path);

			return fileTime.toMillis();
		}
		catch (IOException ioe) {
			return -1;
		}
	}

	public static boolean isAbsolute(String fileName) {
		Path path = Paths.get(fileName);

		return path.isAbsolute();
	}

	public static void write(File file, String content) throws IOException {
		File parentFile = file.getParentFile();

		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}

		Path path = Paths.get(file.toURI());

		Files.write(path, content.getBytes(StringPool.UTF8));
	}

	private static List<PathMatcher> _getPathMatchers(
		FileSystem fileSystem, String... patterns) {

		List<PathMatcher> pathMatchers = new ArrayList<>(patterns.length);

		for (String pattern : patterns) {
			if (File.separatorChar == CharPool.BACK_SLASH) {
				pattern = StringUtil.replace(
					pattern, CharPool.SLASH, StringPool.DOUBLE_BACK_SLASH);
			}

			PathMatcher pathMatcher = fileSystem.getPathMatcher(
				"glob:" + pattern);

			pathMatchers.add(pathMatcher);
		}

		return pathMatchers;
	}

}