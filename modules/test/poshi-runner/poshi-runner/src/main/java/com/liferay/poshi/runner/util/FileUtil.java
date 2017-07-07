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

package com.liferay.poshi.runner.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.URI;
import java.net.URL;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class FileUtil {

	public static void copyDirectory(File sourceDir, File destinationDir)
		throws IOException {

		FileUtils.copyDirectory(sourceDir, destinationDir);
	}

	public static void copyDirectory(
			String sourceDirName, String destinationDirName)
		throws IOException {

		copyDirectory(new File(sourceDirName), new File(destinationDirName));
	}

	public static boolean exists(File file) {
		return file.exists();
	}

	public static boolean exists(String fileName) {
		File file = new File(fileName);

		return exists(file);
	}

	public static List<URL> getIncludedResourceURLs(
			FileSystem fileSystem, String[] includes, String baseDirName)
		throws IOException {

		final List<PathMatcher> pathMatchers = new ArrayList<>();

		for (String include : includes) {
			pathMatchers.add(fileSystem.getPathMatcher("glob:" + include));
		}

		final List<URL> filePaths = new ArrayList<>();

		if (Validator.isNull(baseDirName)) {
			return filePaths;
		}

		Path path = fileSystem.getPath(baseDirName);

		if (!Files.exists(path)) {
			System.out.println("Directory " + baseDirName + " does not exist.");

			return filePaths;
		}

		Files.walkFileTree(
			path,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					for (PathMatcher pathMatcher : pathMatchers) {
						URI uri = filePath.toUri();

						if (pathMatcher.matches(filePath)) {
							filePaths.add(uri.toURL());

							break;
						}
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return filePaths;
	}

	public static List<URL> getIncludedResourceURLs(
			String[] includes, String baseDirName)
		throws IOException {

		return getIncludedResourceURLs(
			FileSystems.getDefault(), includes, baseDirName);
	}

	public static String getSeparator() {
		return File.separator;
	}

	public static String read(File file) throws IOException {
		return FileUtils.readFileToString(file);
	}

	public static String read(String fileName) throws IOException {
		File file = new File(fileName);

		return read(file);
	}

	public static String read(URL url) throws IOException {
		StringBuilder sb = new StringBuilder();

		BufferedReader bufferedReader = new BufferedReader(
			new InputStreamReader(url.openStream()));

		String line = null;

		while ((line = bufferedReader.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}

		return sb.toString();
	}

	public static void write(File file, byte[] bytes) throws IOException {
		FileUtils.writeByteArrayToFile(file, bytes);
	}

	public static void write(File file, String string) throws IOException {
		FileUtils.writeStringToFile(file, string);
	}

	public static void write(String fileName, byte[] bytes) throws IOException {
		File file = new File(fileName);

		write(file, bytes);
	}

	public static void write(String fileName, String string)
		throws IOException {

		File file = new File(fileName);

		write(file, string);
	}

}