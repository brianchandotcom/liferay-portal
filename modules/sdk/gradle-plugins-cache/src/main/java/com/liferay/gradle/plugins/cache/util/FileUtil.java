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

import com.liferay.gradle.util.Validator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.UncheckedIOException;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.internal.hash.HashUtil;
import org.gradle.internal.hash.HashValue;
import org.gradle.process.ExecSpec;
import org.gradle.process.internal.ExecException;

/**
 * @author Andrea Di Giorgi
 */
public class FileUtil extends com.liferay.gradle.util.FileUtil {

	public static SortedSet<File> flattenAndSort(Iterable<File> files)
		throws IOException {

		final SortedSet<File> sortedFiles = new TreeSet<>(new FileComparator());

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

			if (_logger.isDebugEnabled()) {
				_logger.debug(file + " is not a text file", ioe);
			}

			HashValue hashValue = HashUtil.sha1(file);

			digest = hashValue.asHexString();
		}

		if (_logger.isInfoEnabled()) {
			_logger.info("Digest of " + file + " is " + digest);
		}

		return digest;
	}

	public static boolean removeIgnoredFiles(
		Project project, SortedSet<File> files) {

		if (files.isEmpty()) {
			return false;
		}

		File rootDir = null;

		File firstFile = files.first();

		if (files.size() == 1) {
			rootDir = firstFile.getParentFile();
		}
		else {
			String dirName = StringUtil.getCommonPrefix(
				'/', _getCanonicalPath(firstFile),
				_getCanonicalPath(files.last()));

			if (Validator.isNotNull(dirName)) {
				rootDir = new File(dirName);
			}
		}

		if (rootDir == null) {
			if (_logger.isWarnEnabled()) {
				_logger.warn(
					"Unable to remove ignored files, common parent directory " +
						"cannot be found");
			}

			return false;
		}

		String result = _getGitResult(
			project, rootDir, "ls-files", "--cached", "--deleted",
			"--exclude-standard", "--modified", "--others", "-z");

		if (Validator.isNull(result)) {
			if (_logger.isWarnEnabled()) {
				_logger.warn(
					"Unable to remove ignored files, Git returned an empty " +
						"result");
			}

			return false;
		}

		String[] committedFileNames = result.split("\\000");

		Set<File> committedFiles = new HashSet<>();

		for (String fileName : committedFileNames) {
			committedFiles.add(new File(rootDir, fileName));
		}

		return files.retainAll(committedFiles);
	}

	private static String _getCanonicalPath(File file) {
		try {
			String canonicalPath = file.getCanonicalPath();

			if (File.separatorChar != '/') {
				canonicalPath = canonicalPath.replace(File.separatorChar, '/');
			}

			return canonicalPath;
		}
		catch (IOException ioe) {
			throw new UncheckedIOException(
				"Unable to get canonical path of " + file, ioe);
		}
	}

	private static String _getGitResult(
		Project project, final File workingDir, final String... args) {

		final ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		try {
			project.exec(
				new Action<ExecSpec>() {

					@Override
					public void execute(ExecSpec execSpec) {
						execSpec.setArgs(Arrays.asList(args));
						execSpec.setExecutable("git");
						execSpec.setStandardOutput(byteArrayOutputStream);
						execSpec.setWorkingDir(workingDir);
					}

				});
		}
		catch (ExecException ee) {
			if (_logger.isInfoEnabled()) {
				_logger.info(ee.getMessage(), ee);
			}
		}

		return byteArrayOutputStream.toString();
	}

	private static final Logger _logger = Logging.getLogger(FileUtil.class);

	private static class FileComparator implements Comparator<File> {

		@Override
		public int compare(File file1, File file2) {
			String canonicalPath1 = _getCanonicalPath(file1);
			String canonicalPath2 = _getCanonicalPath(file2);

			return canonicalPath1.compareTo(canonicalPath2);
		}

	}

}