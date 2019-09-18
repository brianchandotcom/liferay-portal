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

package com.liferay.gradle.plugins.node.internal.util;

import com.liferay.gradle.util.Validator;

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
import java.util.SortedSet;
import java.util.TreeSet;

import org.gradle.api.GradleException;
import org.gradle.api.UncheckedIOException;
import org.gradle.internal.hash.HashUtil;
import org.gradle.internal.hash.HashValue;

/**
 * @author Hugo Huijser
 * @author Andrea Di Giorgi
 */
public class DigestUtil {

	public static String getDigest(File digestFile) {
		if (!digestFile.exists()) {
			return null;
		}

		byte[] bytes = null;

		try {
			bytes = Files.readAllBytes(digestFile.toPath());
		}
		catch (IOException ioe) {
			throw new UncheckedIOException(ioe);
		}

		return new String(bytes, StandardCharsets.UTF_8);
	}

	public static String getDigest(Iterable<File> files) {
		StringBuilder sb = new StringBuilder();

		SortedSet<File> sortedFiles = null;

		try {
			sortedFiles = _flattenAndSort(files);
		}
		catch (IOException ioe) {
			throw new GradleException("Unable to flatten files", ioe);
		}

		for (File file : sortedFiles) {
			if (!file.exists()) {
				continue;
			}

			try {
				List<String> lines = Files.readAllLines(
					file.toPath(), StandardCharsets.UTF_8);

				sb.append(Integer.toHexString(lines.hashCode()));
			}
			catch (IOException ioe) {
				HashValue hashValue = HashUtil.sha1(file);

				sb.append(hashValue.asHexString());
			}

			sb.append('-');
		}

		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}

		return sb.toString();
	}

	public static String getDigest(String... array) {
		StringBuilder sb = new StringBuilder();

		for (String s : array) {
			if (Validator.isNotNull(s)) {
				sb.append(Integer.toHexString(s.hashCode()));
				sb.append('-');
			}
		}

		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}

		return sb.toString();
	}

	private static SortedSet<File> _flattenAndSort(Iterable<File> files)
		throws IOException {

		final SortedSet<File> sortedFiles = new TreeSet<>(new FileComparator());

		if (files == null) {
			return sortedFiles;
		}

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

	private static class FileComparator implements Comparator<File> {

		@Override
		public int compare(File file1, File file2) {
			String canonicalPath1 = _getCanonicalPath(file1);
			String canonicalPath2 = _getCanonicalPath(file2);

			return canonicalPath1.compareTo(canonicalPath2);
		}

		private static String _getCanonicalPath(File file) {
			String canonicalPath = null;

			try {
				canonicalPath = file.getCanonicalPath();
			}
			catch (IOException ioe) {
				String message = "Unable to get canonical path of " + file;

				throw new UncheckedIOException(message, ioe);
			}

			if (File.separatorChar != '/') {
				canonicalPath = canonicalPath.replace(File.separatorChar, '/');
			}

			return canonicalPath;
		}

	}

}