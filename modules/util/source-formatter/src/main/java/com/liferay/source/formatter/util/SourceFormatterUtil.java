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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.ExcludeSyntax;
import com.liferay.source.formatter.ExcludeSyntaxPattern;
import com.liferay.source.formatter.SourceFormatterExcludes;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.checkstyle.util.CheckstyleUtil;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Igor Spasic
 * @author Brian Wing Shun Chan
 * @author Hugo Huijser
 */
public class SourceFormatterUtil {

	public static List<String> filterFileNames(
		List<String> allFileNames, String[] excludes, String[] includes,
		SourceFormatterExcludes sourceFormatterExcludes,
		boolean forceIncludeAllFiles) {

		List<String> excludeRegexList = new ArrayList<>();
		Map<String, List<String>> excludeRegexMap = new HashMap<>();
		List<String> includeRegexList = new ArrayList<>();

		for (String exclude : excludes) {
			if (!exclude.contains(StringPool.DOLLAR)) {
				excludeRegexList.add(_createRegex(exclude));
			}
		}

		for (ExcludeSyntaxPattern excludeSyntaxPattern :
				sourceFormatterExcludes.getDefaultExcludeSyntaxPatterns()) {

			String excludePattern = excludeSyntaxPattern.getExcludePattern();
			ExcludeSyntax excludeSyntax =
				excludeSyntaxPattern.getExcludeSyntax();

			if (excludeSyntax.equals(ExcludeSyntax.REGEX)) {
				excludeRegexList.add(excludePattern);
			}
			else if (!excludePattern.contains(StringPool.DOLLAR)) {
				excludeRegexList.add(_createRegex(excludePattern));
			}
		}

		if (!forceIncludeAllFiles) {
			Map<String, List<ExcludeSyntaxPattern>> excludeSyntaxPatternsMap =
				sourceFormatterExcludes.getExcludeSyntaxPatternsMap();

			for (Map.Entry<String, List<ExcludeSyntaxPattern>> entry :
					excludeSyntaxPatternsMap.entrySet()) {

				List<ExcludeSyntaxPattern> excludeSyntaxPatterns =
					entry.getValue();

				List<String> regexList = new ArrayList<>();

				for (ExcludeSyntaxPattern excludeSyntaxPattern :
						excludeSyntaxPatterns) {

					String excludePattern =
						excludeSyntaxPattern.getExcludePattern();
					ExcludeSyntax excludeSyntax =
						excludeSyntaxPattern.getExcludeSyntax();

					if (excludeSyntax.equals(ExcludeSyntax.REGEX)) {
						regexList.add(excludePattern);
					}
					else if (!excludePattern.contains(StringPool.DOLLAR)) {
						regexList.add(_createRegex(excludePattern));
					}
				}

				excludeRegexMap.put(entry.getKey(), regexList);
			}
		}

		for (String include : includes) {
			if (!include.contains(StringPool.DOLLAR)) {
				includeRegexList.add(_createRegex(include));
			}
		}

		List<String> fileNames = new ArrayList<>();

		outerLoop:
		for (String fileName : allFileNames) {
			String encodedFileName = SourceUtil.getAbsolutePath(fileName);

			for (String includeRegex : includeRegexList) {
				if (encodedFileName.matches(includeRegex)) {
					for (String excludeRegex : excludeRegexList) {
						if (encodedFileName.matches(excludeRegex)) {
							continue outerLoop;
						}
					}

					for (Map.Entry<String, List<String>> entry :
							excludeRegexMap.entrySet()) {

						String propertiesFileLocation = entry.getKey();

						if (encodedFileName.startsWith(
								propertiesFileLocation)) {

							for (String excludeRegex : entry.getValue()) {
								if (encodedFileName.matches(excludeRegex)) {
									continue outerLoop;
								}
							}
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
			SourceFormatterExcludes sourceFormatterExcludes,
			boolean includeSubrepositories)
		throws Exception {

		if (ArrayUtil.isEmpty(includes)) {
			return new ArrayList<>();
		}

		PathMatchers pathMatchers = _getPathMatchers(
			excludes, includes, sourceFormatterExcludes);

		return _filterRecentChangesFileNames(
			baseDir, recentChangesFileNames, pathMatchers);
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

	public static List<File> getSuppressionsFiles(
			String basedir, List<String> allFileNames,
			SourceFormatterExcludes sourceFormatterExcludes,
			String... fileNames)
		throws Exception {

		List<File> suppressionsFiles = new ArrayList<>();

		String[] includes = new String[fileNames.length];

		for (int i = 0; i < fileNames.length; i++) {
			String fileName = fileNames[i];

			includes[i] = "**/" + fileName;

			// Find suppressions files in any parent directory

			String parentDirName = basedir;

			for (int j = 0; j < ToolsUtil.PORTAL_MAX_DIR_LEVEL; j++) {
				File suppressionsFile = new File(parentDirName + fileName);

				if (suppressionsFile.exists()) {
					suppressionsFiles.add(suppressionsFile);
				}

				parentDirName += "../";
			}
		}

		// Find suppressions files in any child directory

		List<String> moduleSuppressionsFileNames = filterFileNames(
			allFileNames, new String[0], includes, sourceFormatterExcludes,
			true);

		for (String moduleSuppressionsFileName : moduleSuppressionsFileNames) {
			moduleSuppressionsFileName = StringUtil.replace(
				moduleSuppressionsFileName, CharPool.BACK_SLASH,
				CharPool.SLASH);

			suppressionsFiles.add(new File(moduleSuppressionsFileName));
		}

		suppressionsFiles.addAll(
			CheckstyleUtil.getSuppressionsFiles(
				suppressionsFiles.toArray(new File[suppressionsFiles.size()])));

		return suppressionsFiles;
	}

	public static void printError(String fileName, File file) {
		printError(fileName, file.toString());
	}

	public static void printError(String fileName, String message) {
		System.out.println(message);
	}

	public static List<String> scanForFiles(
			String baseDir, String[] excludes, String[] includes,
			SourceFormatterExcludes sourceFormatterExcludes,
			boolean includeSubrepositories)
		throws Exception {

		if (ArrayUtil.isEmpty(includes)) {
			return new ArrayList<>();
		}

		PathMatchers pathMatchers = _getPathMatchers(
			excludes, includes, sourceFormatterExcludes);

		return _scanForFiles(baseDir, pathMatchers, includeSubrepositories);
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
			PathMatchers pathMatchers)
		throws Exception {

		List<String> fileNames = new ArrayList<>();

		recentChangesFileNamesLoop:
		for (String fileName : recentChangesFileNames) {
			fileName = baseDir.concat(fileName);

			File file = new File(fileName);

			File canonicalFile = file.getCanonicalFile();

			Path filePath = canonicalFile.toPath();

			for (PathMatcher pathMatcher :
					pathMatchers.getExcludeFilePathMatchers()) {

				if (pathMatcher.matches(filePath)) {
					continue recentChangesFileNamesLoop;
				}
			}

			String currentFilePath = SourceUtil.getAbsolutePath(filePath);

			Map<String, List<PathMatcher>> excludeFilePathMatchersMap =
				pathMatchers.getExcludeFilePathMatchersMap();

			for (Map.Entry<String, List<PathMatcher>> entry :
					excludeFilePathMatchersMap.entrySet()) {

				String propertiesFileLocation = entry.getKey();

				if (currentFilePath.startsWith(propertiesFileLocation)) {
					for (PathMatcher pathMatcher : entry.getValue()) {
						if (pathMatcher.matches(filePath)) {
							continue recentChangesFileNamesLoop;
						}
					}
				}
			}

			File dir = file.getParentFile();

			while (true) {
				File canonicalDir = dir.getCanonicalFile();

				Path dirPath = canonicalDir.toPath();

				for (PathMatcher pathMatcher :
						pathMatchers.getExcludeDirPathMatchers()) {

					if (pathMatcher.matches(dirPath)) {
						continue recentChangesFileNamesLoop;
					}
				}

				String currentDirPath = SourceUtil.getAbsolutePath(dirPath);

				Map<String, List<PathMatcher>> excludeDirPathMatchersMap =
					pathMatchers.getExcludeDirPathMatchersMap();

				for (Map.Entry<String, List<PathMatcher>> entry :
						excludeDirPathMatchersMap.entrySet()) {

					String propertiesFileLocation = entry.getKey();

					if (currentDirPath.startsWith(propertiesFileLocation)) {
						for (PathMatcher pathMatcher : entry.getValue()) {
							if (pathMatcher.matches(dirPath)) {
								continue recentChangesFileNamesLoop;
							}
						}
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

			for (PathMatcher pathMatcher :
					pathMatchers.getIncludeFilePathMatchers()) {

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

	private static PathMatchers _getPathMatchers(
		String[] excludes, String[] includes,
		SourceFormatterExcludes sourceFormatterExcludes) {

		PathMatchers pathMatchers = new PathMatchers(FileSystems.getDefault());

		for (String exclude : excludes) {
			pathMatchers.addExcludeSyntaxPattern(
				new ExcludeSyntaxPattern(ExcludeSyntax.GLOB, exclude));
		}

		for (ExcludeSyntaxPattern excludeSyntaxPattern :
				sourceFormatterExcludes.getDefaultExcludeSyntaxPatterns()) {

			pathMatchers.addExcludeSyntaxPattern(excludeSyntaxPattern);
		}

		Map<String, List<ExcludeSyntaxPattern>> excludeSyntaxPatternsMap =
			sourceFormatterExcludes.getExcludeSyntaxPatternsMap();

		for (Map.Entry<String, List<ExcludeSyntaxPattern>> entry :
				excludeSyntaxPatternsMap.entrySet()) {

			pathMatchers.addExcludeSyntaxPatterns(
				entry.getKey(), entry.getValue());
		}

		for (String include : includes) {
			pathMatchers.addInclude(include);
		}

		return pathMatchers;
	}

	private static List<String> _scanForFiles(
			final String baseDir, final PathMatchers pathMatchers,
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

					String currentDirPath = SourceUtil.getAbsolutePath(dirPath);

					if (!includeSubrepositories) {
						String baseDirPath = SourceUtil.getAbsolutePath(
							baseDir);

						if (!baseDirPath.equals(currentDirPath)) {
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
					}

					dirPath = _getCanonicalPath(dirPath);

					for (PathMatcher pathMatcher :
							pathMatchers.getExcludeDirPathMatchers()) {

						if (pathMatcher.matches(dirPath)) {
							return FileVisitResult.SKIP_SUBTREE;
						}
					}

					Map<String, List<PathMatcher>> excludeDirPathMatchersMap =
						pathMatchers.getExcludeDirPathMatchersMap();

					for (Map.Entry<String, List<PathMatcher>> entry :
							excludeDirPathMatchersMap.entrySet()) {

						String propertiesFileLocation = entry.getKey();

						if (currentDirPath.startsWith(propertiesFileLocation)) {
							for (PathMatcher pathMatcher : entry.getValue()) {
								if (pathMatcher.matches(dirPath)) {
									return FileVisitResult.SKIP_SUBTREE;
								}
							}
						}
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
					Path filePath, BasicFileAttributes basicFileAttributes) {

					Path canonicalPath = _getCanonicalPath(filePath);

					for (PathMatcher pathMatcher :
							pathMatchers.getExcludeFilePathMatchers()) {

						if (pathMatcher.matches(canonicalPath)) {
							return FileVisitResult.CONTINUE;
						}
					}

					String currentFilePath = SourceUtil.getAbsolutePath(
						filePath);

					Map<String, List<PathMatcher>> excludeFilePathMatchersMap =
						pathMatchers.getExcludeFilePathMatchersMap();

					for (Map.Entry<String, List<PathMatcher>> entry :
							excludeFilePathMatchersMap.entrySet()) {

						String propertiesFileLocation = entry.getKey();

						if (currentFilePath.startsWith(
								propertiesFileLocation)) {

							for (PathMatcher pathMatcher : entry.getValue()) {
								if (pathMatcher.matches(canonicalPath)) {
									return FileVisitResult.CONTINUE;
								}
							}
						}
					}

					for (PathMatcher pathMatcher :
							pathMatchers.getIncludeFilePathMatchers()) {

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

	private static class PathMatchers {

		public PathMatchers(FileSystem fileSystem) {
			_fileSystem = fileSystem;
		}

		public void addExcludeSyntaxPattern(
			ExcludeSyntaxPattern excludeSyntaxPattern) {

			String excludePattern = excludeSyntaxPattern.getExcludePattern();
			ExcludeSyntax excludeSyntax =
				excludeSyntaxPattern.getExcludeSyntax();

			if (excludeSyntax.equals(ExcludeSyntax.GLOB) &&
				!excludePattern.startsWith("**/")) {

				excludePattern = "**/" + excludePattern;
			}

			if (excludeSyntax.equals(ExcludeSyntax.GLOB) &&
				excludePattern.endsWith("/**")) {

				excludePattern = excludePattern.substring(
					0, excludePattern.length() - 3);

				_excludeDirPathMatchers.add(
					_fileSystem.getPathMatcher(
						excludeSyntax.getValue() + ":" + excludePattern));
			}
			else if (excludeSyntax.equals(ExcludeSyntax.REGEX) &&
					 excludePattern.endsWith(
						 Pattern.quote(File.separator) + ".*")) {

				excludePattern = StringUtil.replaceLast(
					excludePattern, Pattern.quote(File.separator) + ".*",
					StringPool.BLANK);

				_excludeDirPathMatchers.add(
					_fileSystem.getPathMatcher(
						excludeSyntax.getValue() + ":" + excludePattern));
			}
			else {
				_excludeFilePathMatchers.add(
					_fileSystem.getPathMatcher(
						excludeSyntax.getValue() + ":" + excludePattern));
			}
		}

		public void addExcludeSyntaxPatterns(
			String propertiesFileLocation,
			List<ExcludeSyntaxPattern> excludeSyntaxPatterns) {

			List<PathMatcher> excludeDirPathMatcherList = new ArrayList<>();
			List<PathMatcher> excludeFilePathMatcherList = new ArrayList<>();

			for (ExcludeSyntaxPattern excludeSyntaxPattern :
					excludeSyntaxPatterns) {

				String excludePattern =
					excludeSyntaxPattern.getExcludePattern();
				ExcludeSyntax excludeSyntax =
					excludeSyntaxPattern.getExcludeSyntax();

				if (excludeSyntax.equals(ExcludeSyntax.GLOB) &&
					!excludePattern.startsWith("**/")) {

					excludePattern = "**/" + excludePattern;
				}

				if (excludeSyntax.equals(ExcludeSyntax.GLOB) &&
					excludePattern.endsWith("/**")) {

					excludePattern = excludePattern.substring(
						0, excludePattern.length() - 3);

					excludeDirPathMatcherList.add(
						_fileSystem.getPathMatcher(
							excludeSyntax.getValue() + ":" + excludePattern));
				}
				else if (excludeSyntax.equals(ExcludeSyntax.REGEX) &&
						 excludePattern.endsWith(
							 Pattern.quote(File.separator) + ".*")) {

					excludePattern = StringUtil.replaceLast(
						excludePattern, Pattern.quote(File.separator) + ".*",
						StringPool.BLANK);

					excludeDirPathMatcherList.add(
						_fileSystem.getPathMatcher(
							excludeSyntax.getValue() + ":" + excludePattern));
				}
				else {
					excludeFilePathMatcherList.add(
						_fileSystem.getPathMatcher(
							excludeSyntax.getValue() + ":" + excludePattern));
				}
			}

			_excludeDirPathMatchersMap.put(
				propertiesFileLocation, excludeDirPathMatcherList);
			_excludeFilePathMatchersMap.put(
				propertiesFileLocation, excludeFilePathMatcherList);
		}

		public void addInclude(String include) {
			_includeFilePathMatchers.add(
				_fileSystem.getPathMatcher("glob:" + include));
		}

		public List<PathMatcher> getExcludeDirPathMatchers() {
			return _excludeDirPathMatchers;
		}

		public Map<String, List<PathMatcher>> getExcludeDirPathMatchersMap() {
			return _excludeDirPathMatchersMap;
		}

		public List<PathMatcher> getExcludeFilePathMatchers() {
			return _excludeFilePathMatchers;
		}

		public Map<String, List<PathMatcher>> getExcludeFilePathMatchersMap() {
			return _excludeFilePathMatchersMap;
		}

		public List<PathMatcher> getIncludeFilePathMatchers() {
			return _includeFilePathMatchers;
		}

		private List<PathMatcher> _excludeDirPathMatchers = new ArrayList<>();
		private Map<String, List<PathMatcher>> _excludeDirPathMatchersMap =
			new HashMap<>();
		private List<PathMatcher> _excludeFilePathMatchers = new ArrayList<>();
		private Map<String, List<PathMatcher>> _excludeFilePathMatchersMap =
			new HashMap<>();
		private final FileSystem _fileSystem;
		private List<PathMatcher> _includeFilePathMatchers = new ArrayList<>();

	}

}