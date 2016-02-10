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

package com.liferay.gradle.plugins.cache.util;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.internal.hash.HashUtil;
import org.gradle.internal.hash.HashValue;

/**
 * @author Andrea Di Giorgi
 */
public class FileUtil extends com.liferay.gradle.util.FileUtil {

	public static String getDigest(File file) {
		String digest;

		try {

			// Ignore EOL character differences between operating systems

			List<String> lines = Files.readAllLines(
				file.toPath(), StandardCharsets.UTF_8);

			digest = Integer.toHexString(lines.hashCode());
		}
		catch (IOException ioe) {

			// File is not a text file

			if (_logger.isDebugEnabled()) {
				_logger.debug(file + " is not a text file", ioe);
			}

			HashValue hashValue = HashUtil.sha1(file);

			digest = hashValue.asHexString();
		}

		if (_logger.isDebugEnabled()) {
			_logger.debug("Digest of " + file + " is " + digest);
		}

		return digest;
	}

	public static Iterable<File> getFiles(File dir) throws IOException {
		final Set<File> files = new TreeSet<>(new FileComparator(dir));

		Files.walkFileTree(
			dir.toPath(),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					files.add(path.toFile());

					return FileVisitResult.CONTINUE;
				}

			});

		return files;
	}

	public static String getRelativePath(File file, File startFile) {
		String relativePath = relativize(file, startFile);

		if (File.separatorChar != '/') {
			relativePath = relativePath.replace(File.separatorChar, '/');
		}

		return relativePath;
	}

	private static final Logger _logger = Logging.getLogger(FileUtil.class);

	private static class FileComparator implements Comparator<File> {

		public FileComparator(File rootDir) {
			_rootDir = rootDir;
		}

		@Override
		public int compare(File file1, File file2) {
			String relativePath1 = FileUtil.getRelativePath(file1, _rootDir);
			String relativePath2 = FileUtil.getRelativePath(file2, _rootDir);

			return relativePath1.compareTo(relativePath2);
		}

		private final File _rootDir;

	}

}