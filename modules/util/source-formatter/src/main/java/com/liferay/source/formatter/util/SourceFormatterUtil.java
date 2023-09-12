/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.GitUtil;
import com.liferay.source.formatter.ExcludeSyntax;
import com.liferay.source.formatter.ExcludeSyntaxPattern;
import com.liferay.source.formatter.SourceFormatterExcludes;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * @author Igor Spasic
 * @author Brian Wing Shun Chan
 * @author Hugo Huijser
 */
public class SourceFormatterUtil {

	public static final String CHECKSTYLE_DOCUMENTATION_URL_BASE =
		"https://checkstyle.sourceforge.io/";

	public static final String GIT_LIFERAY_PORTAL_BRANCH =
		"git.liferay.portal.branch";

	public static final String GIT_LIFERAY_PORTAL_URL =
		"https://raw.githubusercontent.com/liferay/liferay-portal/";

	public static final String SOURCE_FORMATTER_TEST_PATH =
		"/source/formatter/dependencies/";

	public static final String UPGRADE_FROM_VERSION = "upgrade.from.version";

	public static final String UPGRADE_INPUT_DATA_DIRECTORY_NAME =
		"upgrade-to-7.4-input-data";

	public static final String UPGRADE_TO_VERSION = "upgrade.to.version";

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
				if (!encodedFileName.matches(includeRegex)) {
					continue;
				}

				for (String excludeRegex : excludeRegexList) {
					if (encodedFileName.matches(excludeRegex)) {
						continue outerLoop;
					}
				}

				for (Map.Entry<String, List<String>> entry :
						excludeRegexMap.entrySet()) {

					String propertiesFileLocation = entry.getKey();

					if (encodedFileName.startsWith(propertiesFileLocation)) {
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

		return fileNames;
	}

	public static List<String> filterRecentChangesFileNames(
			Set<String> recentChangesFileNames, String[] excludes,
			String[] includes, SourceFormatterExcludes sourceFormatterExcludes)
		throws IOException {

		if (ArrayUtil.isEmpty(includes)) {
			return new ArrayList<>();
		}

		return _filterRecentChangesFileNames(
			recentChangesFileNames,
			_getPathMatchers(excludes, includes, sourceFormatterExcludes));
	}

	public static String getDocumentationURLString(Class<?> checkClass) {
		String documentationURLString = _getDocumentationURLString(
			checkClass.getSimpleName());

		if (documentationURLString != null) {
			return documentationURLString;
		}

		Class<?> superclass = checkClass.getSuperclass();

		String className = superclass.getSimpleName();

		documentationURLString = _getDocumentationURLString(className);

		if ((documentationURLString != null) || !className.startsWith("Base")) {
			return documentationURLString;
		}

		return _getDocumentationURLString(className.substring(4));
	}

	public static File getFile(String baseDirName, String fileName, int level) {
		for (int i = 0; i < level; i++) {
			File file = new File(baseDirName + fileName);

			if (file.exists()) {
				return file;
			}

			fileName = "../" + fileName;
		}

		return null;
	}

	public static String getGitContent(String fileName, String branchName) {
		URL url = getPortalGitURL(fileName, branchName);

		if (url == null) {
			return null;
		}

		try {
			return StringUtil.read(url.openStream());
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ioException);
			}

			return null;
		}
	}

	public static String getMarkdownFileName(String camelCaseName) {
		camelCaseName = StringUtil.replace(camelCaseName, "OSGi", "OSGI");

		camelCaseName = camelCaseName.replaceAll("([A-Z])s([A-Z])", "$1S$2");

		String markdownFileName = TextFormatter.format(
			camelCaseName, TextFormatter.K);

		markdownFileName = TextFormatter.format(
			markdownFileName, TextFormatter.N);

		return markdownFileName + ".markdown";
	}

	public static File getPortalDir(String baseDirName, int maxDirLevel) {
		File portalImplDir = getFile(baseDirName, "portal-impl", maxDirLevel);

		if (portalImplDir == null) {
			return null;
		}

		return portalImplDir.getParentFile();
	}

	public static URL getPortalGitURL(
		String fileName, String portalBranchName) {

		if (Validator.isNull(portalBranchName)) {
			return null;
		}

		try {
			return new URL(
				StringBundler.concat(
					SourceFormatterUtil.GIT_LIFERAY_PORTAL_URL,
					portalBranchName, StringPool.SLASH, fileName));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return null;
		}
	}

	public static String getSimpleName(String name) {
		int pos = name.lastIndexOf(CharPool.PERIOD);

		if (pos != -1) {
			return name.substring(pos + 1);
		}

		return name;
	}

	public static List<File> getSuppressionsFiles(
		String baseDirName, List<String> allFileNames,
		SourceFormatterExcludes sourceFormatterExcludes, int maxDirLevel) {

		List<File> suppressionsFiles = new ArrayList<>();

		// Find suppressions files in any parent directory

		String parentDirName = baseDirName;

		for (int j = 0; j < maxDirLevel; j++) {
			File suppressionsFile = new File(
				parentDirName + _SUPPRESSIONS_FILE_NAME);

			if (suppressionsFile.exists()) {
				suppressionsFiles.add(suppressionsFile);
			}

			parentDirName += "../";
		}

		// Find suppressions files in any child directory

		List<String> moduleSuppressionsFileNames = filterFileNames(
			allFileNames, new String[0],
			new String[] {"**/" + _SUPPRESSIONS_FILE_NAME},
			sourceFormatterExcludes, true);

		for (String moduleSuppressionsFileName : moduleSuppressionsFileNames) {
			moduleSuppressionsFileName = StringUtil.replace(
				moduleSuppressionsFileName, CharPool.BACK_SLASH,
				CharPool.SLASH);

			suppressionsFiles.add(new File(moduleSuppressionsFileName));
		}

		return suppressionsFiles;
	}

	public static List<String> git(
		List<String> args, String baseDirName, PathMatchers pathMatchers,
		boolean includeSubrepositories) {

		List<String> result = new ArrayList<>();

		git(
			args, baseDirName, pathMatchers, includeSubrepositories,
			result::add);

		return result;
	}

	public static void git(
		List<String> args, String baseDirName, PathMatchers pathMatchers,
		boolean includeSubrepositories, Consumer<String> consumer) {

		List<String> allArgs = new ArrayList<>();

		allArgs.add("git");

		allArgs.addAll(args);

		// Path Filtering

		List<String> filters = new ArrayList<>();

		String excludePrefix = ":(exclude,glob)";

		if (pathMatchers != null) {
			ListUtil.isNotEmptyForEach(
				pathMatchers.getExcludeDirGlobs(),
				excludeGlob -> filters.add(excludePrefix + excludeGlob));
			ListUtil.isNotEmptyForEach(
				pathMatchers.getExcludeFileGlobs(),
				excludeGlob -> filters.add(excludePrefix + excludeGlob));

			Map<String, List<String>> excludeDirGlobsMap =
				pathMatchers.getExcludeDirGlobsMap();

			for (List<String> excludeDirGlobs : excludeDirGlobsMap.values()) {
				ListUtil.isNotEmptyForEach(
					excludeDirGlobs,
					excludeDirGlob -> filters.add(
						excludePrefix + excludeDirGlob));
			}

			Map<String, List<String>> excludeFileGlobsMap =
				pathMatchers.getExcludeFileGlobsMap();

			for (List<String> excludeFileGlobs : excludeFileGlobsMap.values()) {
				ListUtil.isNotEmptyForEach(
					excludeFileGlobs,
					excludeFileGlob -> filters.add(
						excludePrefix + excludeFileGlob));
			}

			ListUtil.isNotEmptyForEach(
				pathMatchers.getIncludeFileGlobs(),
				includeGlob -> filters.add(":(glob)" + includeGlob));
		}

		if (_sfIgnoreDirectories != null) {
			for (String sfIgnoreDirectory : _sfIgnoreDirectories) {
				filters.add(excludePrefix + sfIgnoreDirectory);
			}
		}

		if ((_subrepoIgnoreDirectories != null) && !includeSubrepositories) {
			for (String subrepoIgnoreDirectory : _subrepoIgnoreDirectories) {
				filters.add(excludePrefix + subrepoIgnoreDirectory);
			}
		}

		if (ListUtil.isNotEmpty(filters)) {
			allArgs.add("--");

			allArgs.addAll(filters);
		}

		ProcessBuilder processBuilder = new ProcessBuilder(allArgs);

		if (baseDirName != null) {
			processBuilder.directory(new File(baseDirName));
		}

		try {
			Process process = processBuilder.start();

			Scanner scanner = new Scanner(process.getInputStream());

			if (allArgs.contains("ls-files") && allArgs.contains("-z")) {
				scanner.useDelimiter("\0");
			}

			while (scanner.hasNext()) {
				consumer.accept(scanner.next());
			}

			scanner.close();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public static void printError(String fileName, File file) {
		printError(fileName, file.toString());
	}

	public static void printError(String fileName, String message) {
		System.out.println(message);
	}

	public static List<String> scanForFileNames(
			String baseDirName, String[] excludes, String[] includes,
			SourceFormatterExcludes sourceFormatterExcludes,
			boolean includeSubrepositories)
		throws IOException {

		if (ArrayUtil.isEmpty(includes)) {
			return new ArrayList<>();
		}

		return _scanForFileNames(
			baseDirName,
			_getPathMatchers(excludes, includes, sourceFormatterExcludes),
			includeSubrepositories);
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
			Set<String> recentChangesFileNames, PathMatchers pathMatchers)
		throws IOException {

		List<String> fileNames = new ArrayList<>();

		recentChangesFileNamesLoop:
		for (String fileName : recentChangesFileNames) {
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
					Path curFilePath = Paths.get(fileName);

					fileNames.add(curFilePath.toString());

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
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static List<String> _getDeletedFileNames(String baseDirName) {
		return git(
			Arrays.asList("ls-files", "-d", "-z", "--full-name"), baseDirName,
			null, false);
	}

	private static String _getDocumentationURLString(String checkName) {
		String markdownFileName = getMarkdownFileName(checkName);

		ClassLoader classLoader = SourceFormatterUtil.class.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"documentation/check/" + markdownFileName);

		if (inputStream != null) {
			return _DOCUMENTATION_URL + markdownFileName;
		}

		return null;
	}

	private static PathMatchers _getPathMatchers(
		String[] excludes, String[] includes,
		SourceFormatterExcludes sourceFormatterExcludes) {

		PathMatchers pathMatchers = new PathMatchers();

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

	private static void _populateIgnoreDirectories(String baseDirName) {
		_sfIgnoreDirectories = new ArrayList<>();
		_subrepoIgnoreDirectories = new ArrayList<>();

		git(
			Arrays.asList(
				"ls-files", "-z", "--", "**/source_formatter.ignore",
				"**/.gitrepo"),
			baseDirName, null, false,
			filePath -> {
				if (filePath.endsWith("/source_formatter.ignore")) {
					File file = new File(baseDirName, filePath);

					if (file.exists()) {
						_sfIgnoreDirectories.add(
							StringUtil.replace(
								file.getParent(), CharPool.BACK_SLASH,
								CharPool.SLASH));
					}
				}

				if (filePath.endsWith("/.gitrepo")) {
					String content = null;

					File file = new File(baseDirName, filePath);

					try {
						content = FileUtil.read(file);
					}
					catch (IOException ioException) {
						throw new RuntimeException(ioException);
					}

					if ((content != null) &&
						content.contains("autopull = true")) {

						_subrepoIgnoreDirectories.add(
							StringUtil.replace(
								file.getParent(), CharPool.BACK_SLASH,
								CharPool.SLASH));
					}
				}
			});
	}

	private static List<String> _scanForFileNames(
			final String baseDirName, final PathMatchers pathMatchers,
			final boolean includeSubrepositories)
		throws IOException {

		try {
			if (!baseDirName.contains("gradle-plugins-source-formatter") &&
				(GitUtil.getLatestCommitId() != null)) {

				if ((_sfIgnoreDirectories == null) ||
					(_subrepoIgnoreDirectories == null)) {

					_populateIgnoreDirectories(baseDirName);
				}

				if (_gitTopLevelFolder == null) {
					List<String> lines = git(
						Arrays.asList("rev-parse", "--show-toplevel"), null,
						null, false);

					_gitTopLevelFolder = new File(lines.get(0));
				}

				List<String> deletedFileNames = _getDeletedFileNames(
					baseDirName);
				List<String> gitFileNames = new ArrayList<>();

				git(
					Arrays.asList("ls-files", "-z", "--full-name"), baseDirName,
					pathMatchers, includeSubrepositories,
					line -> {
						if (deletedFileNames.contains(line)) {
							return;
						}

						gitFileNames.add(
							StringBundler.concat(
								StringUtil.replace(
									_gitTopLevelFolder.getPath(),
									CharPool.BACK_SLASH, CharPool.SLASH),
								StringPool.FORWARD_SLASH, line));
					});

				return gitFileNames;
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		final List<String> fileNames = new ArrayList<>();

		Files.walkFileTree(
			Paths.get(baseDirName),
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
							baseDirName);

						if (!baseDirPath.equals(currentDirPath)) {
							Path gitRepoPath = dirPath.resolve(".gitrepo");

							if (Files.exists(gitRepoPath)) {
								try {
									String content = FileUtil.read(
										gitRepoPath.toFile());

									if (content.contains("autopull = true")) {
										return FileVisitResult.SKIP_SUBTREE;
									}
								}
								catch (Exception exception) {
									if (_log.isDebugEnabled()) {
										_log.debug(exception);
									}
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

						String fileName = StringUtil.replace(
							filePath.toString(), CharPool.BACK_SLASH,
							CharPool.SLASH);

						fileNames.add(fileName);

						return FileVisitResult.CONTINUE;
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return fileNames;
	}

	private static final String _DOCUMENTATION_URL =
		"https://github.com/liferay/liferay-portal/blob/master/modules/util" +
			"/source-formatter/src/main/resources/documentation/check/";

	private static final String _SUPPRESSIONS_FILE_NAME =
		"source-formatter-suppressions.xml";

	private static final Log _log = LogFactoryUtil.getLog(
		SourceFormatterUtil.class);

	private static final FileSystem _fileSystem = FileSystems.getDefault();
	private static File _gitTopLevelFolder;
	private static List<String> _sfIgnoreDirectories;
	private static List<String> _subrepoIgnoreDirectories;

	private static class PathMatchers {

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

				_excludeDirGlobs.add(excludePattern);

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

				if (excludeSyntax.equals(ExcludeSyntax.GLOB)) {
					_excludeFileGlobs.add(excludePattern);
				}
			}
		}

		public void addExcludeSyntaxPatterns(
			String propertiesFileLocation,
			List<ExcludeSyntaxPattern> excludeSyntaxPatterns) {

			List<String> excludeDirPathMatcherGlobsList = new ArrayList<>();
			List<PathMatcher> excludeDirPathMatcherList = new ArrayList<>();
			List<String> excludeFilePathMatcherGlobsList = new ArrayList<>();
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

					excludeDirPathMatcherGlobsList.add(excludePattern);

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

					if (excludeSyntax.equals(ExcludeSyntax.GLOB)) {
						excludeFilePathMatcherGlobsList.add(excludePattern);
					}
				}
			}

			_excludeDirGlobsMap.put(
				propertiesFileLocation, excludeDirPathMatcherGlobsList);
			_excludeDirPathMatchersMap.put(
				propertiesFileLocation, excludeDirPathMatcherList);
			_excludeFileGlobsMap.put(
				propertiesFileLocation, excludeFilePathMatcherGlobsList);
			_excludeFilePathMatchersMap.put(
				propertiesFileLocation, excludeFilePathMatcherList);
		}

		public void addInclude(String include) {
			_includeFilePathMatchers.add(
				_fileSystem.getPathMatcher("glob:" + include));
			_includeFileGlobs.add(include);
		}

		public List<String> getExcludeDirGlobs() {
			return _excludeDirGlobs;
		}

		public Map<String, List<String>> getExcludeDirGlobsMap() {
			return _excludeDirGlobsMap;
		}

		public List<PathMatcher> getExcludeDirPathMatchers() {
			return _excludeDirPathMatchers;
		}

		public Map<String, List<PathMatcher>> getExcludeDirPathMatchersMap() {
			return _excludeDirPathMatchersMap;
		}

		public List<String> getExcludeFileGlobs() {
			return _excludeFileGlobs;
		}

		public Map<String, List<String>> getExcludeFileGlobsMap() {
			return _excludeFileGlobsMap;
		}

		public List<PathMatcher> getExcludeFilePathMatchers() {
			return _excludeFilePathMatchers;
		}

		public Map<String, List<PathMatcher>> getExcludeFilePathMatchersMap() {
			return _excludeFilePathMatchersMap;
		}

		public List<String> getIncludeFileGlobs() {
			return _includeFileGlobs;
		}

		public List<PathMatcher> getIncludeFilePathMatchers() {
			return _includeFilePathMatchers;
		}

		private final List<String> _excludeDirGlobs = new ArrayList<>();
		private final Map<String, List<String>> _excludeDirGlobsMap =
			new HashMap<>();
		private final List<PathMatcher> _excludeDirPathMatchers =
			new ArrayList<>();
		private final Map<String, List<PathMatcher>>
			_excludeDirPathMatchersMap = new HashMap<>();
		private final List<String> _excludeFileGlobs = new ArrayList<>();
		private final Map<String, List<String>> _excludeFileGlobsMap =
			new HashMap<>();
		private final List<PathMatcher> _excludeFilePathMatchers =
			new ArrayList<>();
		private final Map<String, List<PathMatcher>>
			_excludeFilePathMatchersMap = new HashMap<>();
		private final List<String> _includeFileGlobs = new ArrayList<>();
		private final List<PathMatcher> _includeFilePathMatchers =
			new ArrayList<>();

	}

}