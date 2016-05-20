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

	public static Set<File> flattenAndSort(Iterable<File> files, File rootDir)
		throws IOException {

		final Set<File> sortedFiles = new TreeSet<>(
			new FileComparator(rootDir));

		for (File file : files) {
			if (file.isDirectory()) {
				Files.walkFileTree(
					file.toPath(),
					new SimpleFileVisitor<Path>() {

						@Override
						public FileVisitResult visitFile(
								Path path,
								BasicFileAttributes basicFileAttributes)
							throws IOException {

							sortedFiles.add(path.toFile());

							return FileVisitResult.CONTINUE;
						}

					});
			}
			else {
				sortedFiles.add(file);
			}
		}

		return sortedFiles;
	}

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

			if (_logger.isInfoEnabled()) {
				_logger.info(file + " is not a text file", ioe);
			}

			HashValue hashValue = HashUtil.sha1(file);

			digest = hashValue.asHexString();
		}

		if (_logger.isInfoEnabled()) {
			_logger.info("Digest of " + file + " is " + digest);
		}

		return digest;
	}

	private static final Logger _logger = Logging.getLogger(FileUtil.class);

	private static class FileComparator implements Comparator<File> {

		public FileComparator(File rootDir) {
			_rootDir = rootDir;
		}

		@Override
		public int compare(File file1, File file2) {
			String relativePath1 = _getRelativePath(file1);
			String relativePath2 = _getRelativePath(file2);

			return relativePath1.compareTo(relativePath2);
		}

		private String _getRelativePath(File file) {
			String relativePath = relativize(file, _rootDir);

			if (File.separatorChar != '/') {
				relativePath = relativePath.replace(File.separatorChar, '/');
			}

			return relativePath;
		}

		private final File _rootDir;

	}

}