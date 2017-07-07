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

package com.liferay.source.formatter.util;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Spasic
 * @author Brian Wing Shun Chan
 * @author Hugo Huijser
 */
public class SourceFormatterUtil {

	public static List<String> filterFileNames(
		List<String> allFileNames, String[] excludes, String[] includes) {

		List<String> excludesRegex = new ArrayList<>();
		List<String> includesRegex = new ArrayList<>();

		for (String exclude : excludes) {
			if (!exclude.contains(StringPool.DOLLAR)) {
				excludesRegex.add(_createRegex(exclude));
			}
		}

		for (String include : includes) {
			if (!include.contains(StringPool.DOLLAR)) {
				includesRegex.add(_createRegex(include));
			}
		}

		List<String> fileNames = new ArrayList<>();

		outerLoop:
		for (String fileName : allFileNames) {
			String encodedFileName = SourceUtil.getAbsolutePath(fileName);

			encodedFileName = StringUtil.replace(
				encodedFileName, CharPool.BACK_SLASH, CharPool.SLASH);

			for (String includeRegex : includesRegex) {
				if (encodedFileName.matches(includeRegex)) {
					for (String excludeRegex : excludesRegex) {
						if (encodedFileName.matches(excludeRegex)) {
							continue outerLoop;
						}
					}

					fileNames.add(fileName);

					continue outerLoop;
				}
			}
		}

		return fileNames;
	}

	public static List<String> filterRecentChangesFileNames(
			String baseDir, List<String> recentChangesFileNames,
			String[] excludes, String[] includes,
			boolean includeSubrepositories)
		throws Exception {

		if (ArrayUtil.isEmpty(includes)) {
			return new ArrayList<>();
		}

		List<PathMatcher> excludeDirPathMatchers = new ArrayList<>();
		List<PathMatcher> excludeFilePathMatchers = new ArrayList<>();
		List<PathMatcher> includeFilePathMatchers = new ArrayList<>();

		FileSystem fileSystem = FileSystems.getDefault();

		for (String exclude : excludes) {
			if (!exclude.startsWith("**/")) {
				exclude = "**/" + exclude;
			}

			if (exclude.endsWith("/**")) {
				exclude = exclude.substring(0, exclude.length() - 3);

				excludeDirPathMatchers.add(
					fileSystem.getPathMatcher("glob:" + exclude));
			}
			else {
				excludeFilePathMatchers.add(
					fileSystem.getPathMatcher("glob:" + exclude));
			}
		}

		for (String include : includes) {
			includeFilePathMatchers.add(
				fileSystem.getPathMatcher("glob:" + include));
		}

		return _filterRecentChangesFileNames(
			baseDir, recentChangesFileNames, excludeDirPathMatchers,
			excludeFilePathMatchers, includeFilePathMatchers);
	}

	public static File getFile(String baseDir, String fileName, int level) {
		for (int i = 0; i < level; i++) {
			File file = new File(baseDir + fileName);

			if (file.exists()) {
				return file;
			}

			fileName = "../" + fileName;
		}

		return null;
	}

	public static void printError(String fileName, File file) {
		printError(fileName, file.toString());
	}

	public static void printError(String fileName, String message) {
		System.out.println(message);
	}

	public static List<String> scanForFiles(
			String baseDir, String[] excludes, String[] includes,
			boolean includeSubrepositories)
		throws Exception {

		if (ArrayUtil.isEmpty(includes)) {
			return new ArrayList<>();
		}

		List<PathMatcher> excludeDirPathMatchers = new ArrayList<>();
		List<PathMatcher> excludeFilePathMatchers = new ArrayList<>();
		List<PathMatcher> includeFilePathMatchers = new ArrayList<>();

		FileSystem fileSystem = FileSystems.getDefault();

		for (String exclude : excludes) {
			if (!exclude.startsWith("**/")) {
				exclude = "**/" + exclude;
			}

			if (exclude.endsWith("/**")) {
				exclude = exclude.substring(0, exclude.length() - 3);

				excludeDirPathMatchers.add(
					fileSystem.getPathMatcher("glob:" + exclude));
			}
			else {
				excludeFilePathMatchers.add(
					fileSystem.getPathMatcher("glob:" + exclude));
			}
		}

		for (String include : includes) {
			includeFilePathMatchers.add(
				fileSystem.getPathMatcher("glob:" + include));
		}

		return _scanForFiles(
			baseDir, excludeDirPathMatchers, excludeFilePathMatchers,
			includeFilePathMatchers, includeSubrepositories);
	}

	private static String _createRegex(String s) {
		if (!s.startsWith("**/")) {
			s = "**/" + s;
		}

		s = StringUtil.replace(s, CharPool.PERIOD, "\\.");

		StringBundler sb = new StringBundler();

		for (int i = 0; i < s.length(); i++) {
			char c1 = s.charAt(i);

			if (c1 != CharPool.STAR) {
				sb.append(c1);

				continue;
			}

			if (i == (s.length() - 1)) {
				sb.append("[^/]*");

				continue;
			}

			char c2 = s.charAt(i + 1);

			if (c2 == CharPool.STAR) {
				sb.append(".*");

				i++;

				continue;
			}

			sb.append("[^/]*");
		}

		return sb.toString();
	}

	private static List<String> _filterRecentChangesFileNames(
			String baseDir, List<String> recentChangesFileNames,
			List<PathMatcher> excludeDirPathMatchers,
			List<PathMatcher> excludeFilePathMatchers,
			List<PathMatcher> includeFilePathMatchers)
		throws Exception {

		List<String> fileNames = new ArrayList<>();

		recentChangesFileNamesLoop:
		for (String fileName : recentChangesFileNames) {
			fileName = baseDir.concat(fileName);

			File file = new File(fileName);

			File canonicalFile = file.getCanonicalFile();

			Path filePath = canonicalFile.toPath();

			for (PathMatcher pathMatcher : excludeFilePathMatchers) {
				if (pathMatcher.matches(filePath)) {
					continue recentChangesFileNamesLoop;
				}
			}

			File dir = file.getParentFile();

			while (true) {
				File canonicalDir = dir.getCanonicalFile();

				Path dirPath = canonicalDir.toPath();

				for (PathMatcher pathMatcher : excludeDirPathMatchers) {
					if (pathMatcher.matches(dirPath)) {
						continue recentChangesFileNamesLoop;
					}
				}

				if (Files.exists(dirPath.resolve("source_formatter.ignore"))) {
					continue recentChangesFileNamesLoop;
				}

				dir = dir.getParentFile();

				if (dir == null) {
					break;
				}
			}

			for (PathMatcher pathMatcher : includeFilePathMatchers) {
				if (pathMatcher.matches(filePath)) {
					fileName = StringUtil.replace(
						fileName, CharPool.SLASH, CharPool.BACK_SLASH);

					fileNames.add(fileName);

					continue recentChangesFileNamesLoop;
				}
			}
		}

		return fileNames;
	}

	private static Path _getCanonicalPath(Path path) {
		try {
			File file = path.toFile();

			File canonicalFile = file.getCanonicalFile();

			return canonicalFile.toPath();
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private static List<String> _scanForFiles(
			String baseDir, final List<PathMatcher> excludeDirPathMatchers,
			final List<PathMatcher> excludeFilePathMatchers,
			final List<PathMatcher> includeFilePathMatchers,
			final boolean includeSubrepositories)
		throws Exception {

		final List<String> fileNames = new ArrayList<>();

		Files.walkFileTree(
			Paths.get(baseDir),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
					Path dirPath, BasicFileAttributes basicFileAttributes) {

					if (Files.exists(
							dirPath.resolve("source_formatter.ignore"))) {

						return FileVisitResult.SKIP_SUBTREE;
					}

					if (!includeSubrepositories) {
						Path gitRepoPath = dirPath.resolve(".gitrepo");

						if (Files.exists(gitRepoPath)) {
							try {
								String content = FileUtil.read(
									gitRepoPath.toFile());

								if (content.contains("mode = pull")) {
									return FileVisitResult.SKIP_SUBTREE;
								}
							}
							catch (Exception e) {
							}
						}
					}

					dirPath = _getCanonicalPath(dirPath);

					for (PathMatcher pathMatcher : excludeDirPathMatchers) {
						if (pathMatcher.matches(dirPath)) {
							return FileVisitResult.SKIP_SUBTREE;
						}
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
					Path filePath, BasicFileAttributes basicFileAttributes) {

					Path canonicalPath = _getCanonicalPath(filePath);

					for (PathMatcher pathMatcher : excludeFilePathMatchers) {
						if (pathMatcher.matches(canonicalPath)) {
							return FileVisitResult.CONTINUE;
						}
					}

					for (PathMatcher pathMatcher : includeFilePathMatchers) {
						if (!pathMatcher.matches(canonicalPath)) {
							continue;
						}

						fileNames.add(filePath.toString());

						return FileVisitResult.CONTINUE;
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return fileNames;
	}

}