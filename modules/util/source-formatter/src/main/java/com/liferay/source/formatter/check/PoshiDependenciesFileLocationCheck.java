/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Alan Huang
 */
public class PoshiDependenciesFileLocationCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!fileName.endsWith(".testcase")) {
			return content;
		}

		_getTestCaseFileAndDependenciesFileLocations();
		_getTestCaseGlobalDependenciesFileLocations();
		_getDependenciesFileLocationsMap();

		_checkDependenciesFileReferences(absolutePath, fileName);
		_checkGlobalDependenciesFileReferences(absolutePath, fileName);

		return content;
	}

	private void _checkDependenciesFileReferences(
		String absolutePath, String fileName) {

		for (Map.Entry<String, Set<String>> entry :
				_dependenciesFileLocationsMap.entrySet()) {

			Set<String> referencesFiles = entry.getValue();

			Set<String> removedDuplicatedFilePaths = new HashSet<>();

			for (String referencesFile : referencesFiles) {
				String referencesFilePath = referencesFile.substring(
					0, referencesFile.lastIndexOf("/"));

				removedDuplicatedFilePaths.add(referencesFilePath);
			}

			if (removedDuplicatedFilePaths.size() <= 1) {
				continue;
			}

			if (referencesFiles.size() > 1) {
				for (String referencesFile : referencesFiles) {
					if (referencesFile.equals(absolutePath)) {
						addMessage(
							fileName,
							StringBundler.concat(
								"Test dependencies file '", entry.getKey(),
								"' is referenced by multiple modules, move it ",
								"to global dependencies directory"));

						break;
					}
				}
			}
		}
	}

	private synchronized void _checkGlobalDependenciesFileReferences(
		String absolutePath, String fileName) {

		for (Map.Entry<String, Set<String>> entry :
				_dependenciesGlobalFileLocationsMap.entrySet()) {

			Set<String> referencesFiles = entry.getValue();

			if (referencesFiles.size() == 1) {
				for (String referencesFile : referencesFiles) {
					if (referencesFile.equals(absolutePath)) {
						addMessage(
							fileName,
							StringBundler.concat(
								"Test dependencies file '", entry.getKey(),
								"' is only referenced by one module, move it ",
								"to module dependencies directory"));

						break;
					}
				}
			}
		}
	}

	private boolean _containsFileName(
		String content, String dependenciesFileName) {

		int x = -1;

		while (true) {
			x = content.indexOf(dependenciesFileName, x + 1);

			if (x == -1) {
				break;
			}

			char previousChar = content.charAt(x - 1);

			if ((previousChar != CharPool.QUOTE) &&
				(previousChar != CharPool.COMMA)) {

				continue;
			}

			if ((x + dependenciesFileName.length()) >= content.length()) {
				break;
			}

			char nextChar = content.charAt(x + dependenciesFileName.length());

			if ((nextChar != CharPool.QUOTE) && (nextChar != CharPool.COMMA)) {
				continue;
			}

			return true;
		}

		return false;
	}

	private synchronized void _getDependenciesFileLocationsMap()
		throws IOException {

		if (_dependenciesFileLocationsMapIsReady) {
			return;
		}

		for (String testCaseFileName : _testCaseFileNames) {
			File testCaseFile = new File(testCaseFileName);

			String testCaseFileContent = FileUtil.read(testCaseFile);

			for (Map.Entry<String, Set<String>> entry :
					_dependenciesFileLocationsMap.entrySet()) {

				String dependenciesFileLocation = entry.getKey();

				String dependenciesFileName =
					dependenciesFileLocation.replaceFirst(".*/(.+)", "$1");

				if (_containsFileName(
						testCaseFileContent, dependenciesFileName)) {

					Set<String> referencesFiles = entry.getValue();

					referencesFiles.add(testCaseFileName);

					_dependenciesFileLocationsMap.put(
						dependenciesFileLocation, referencesFiles);
				}
			}

			for (Map.Entry<String, Set<String>> entry :
					_dependenciesGlobalFileLocationsMap.entrySet()) {

				String dependenciesFileLocation = entry.getKey();

				String dependenciesFileName =
					dependenciesFileLocation.replaceFirst(".*/(.+)", "$1");

				if (_containsFileName(
						testCaseFileContent, dependenciesFileName)) {

					Set<String> referencesFiles = entry.getValue();

					referencesFiles.add(testCaseFileName);

					_dependenciesGlobalFileLocationsMap.put(
						dependenciesFileLocation, referencesFiles);
				}
			}
		}

		_dependenciesFileLocationsMapIsReady = true;
	}

	private synchronized void _getTestCaseFileAndDependenciesFileLocations()
		throws IOException {

		if (!_testCaseFileNames.isEmpty()) {
			return;
		}

		for (String testCaseFileLocation : _TEST_FILE_LOCATIONS) {
			File directory = new File(getPortalDir(), testCaseFileLocation);

			List<String> checkedPath = new ArrayList<>();

			List<String> fileNames = SourceFormatterUtil.scanForFileNames(
				directory.getCanonicalPath(), new String[0]);

			outerLoop:
			for (String fileName : fileNames) {
				for (String skipDirName : _SKIP_DIR_NAMES) {
					if (fileName.contains("/" + skipDirName + "/")) {
						continue outerLoop;
					}
				}

				String absolutePath = fileName.substring(
					0, fileName.lastIndexOf("/"));

				if (fileName.endsWith(".testcase") &&
					(absolutePath.contains("/portal-web/") ||
					 absolutePath.matches(
						 ".+/modules/.+-test/src/testFunctional(/.*)?"))) {

					_testCaseFileNames.add(fileName);
				}

				if (absolutePath.contains("/test/") ||
					absolutePath.contains("/tests/")) {

					if (absolutePath.endsWith("/dependencies")) {
						_dependenciesFileLocationsMap.put(
							fileName, new TreeSet<>());

						continue;
					}

					int start = absolutePath.indexOf("/dependencies/");

					if (start == -1) {
						continue;
					}

					start = start + "/dependencies/".length();

					while (true) {
						int end = absolutePath.indexOf("/", start + 1);

						if (end == -1) {
							end = absolutePath.length();
						}

						String pathName = absolutePath.substring(0, end);

						if (checkedPath.contains(pathName)) {
							continue outerLoop;
						}

						if (pathName.matches(".+/dependencies/.+\\..+")) {
							_dependenciesFileLocationsMap.put(
								pathName, new TreeSet<>());

							checkedPath.add(pathName);

							continue outerLoop;
						}

						if (end == absolutePath.length()) {
							continue outerLoop;
						}

						start = end;
					}
				}
			}
		}
	}

	private synchronized void _getTestCaseGlobalDependenciesFileLocations()
		throws IOException {

		if (!_dependenciesGlobalFileLocationsMap.isEmpty()) {
			return;
		}

		File directory = new File(
			getPortalDir(), _GLOBAL_DEPENDENCIES_DIRECTORY);

		Path dirPath = directory.toPath();

		Files.walkFileTree(
			dirPath, EnumSet.noneOf(FileVisitOption.class), 25,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
					Path dirPath, BasicFileAttributes basicFileAttributes) {

					if (ArrayUtil.contains(
							_SKIP_DIR_NAMES,
							String.valueOf(dirPath.getFileName()))) {

						return FileVisitResult.SKIP_SUBTREE;
					}

					String absolutePath = SourceUtil.getAbsolutePath(dirPath);

					if (absolutePath.matches(".+/dependencies/.+\\..+")) {
						_dependenciesGlobalFileLocationsMap.put(
							SourceUtil.getAbsolutePath(absolutePath),
							new TreeSet<>());

						return FileVisitResult.SKIP_SUBTREE;
					}

					File dirFile = dirPath.toFile();

					File[] dependenciesFiles = dirFile.listFiles(
						file -> {
							if (!file.isFile()) {
								return false;
							}

							return true;
						});

					if (dependenciesFiles == null) {
						return FileVisitResult.CONTINUE;
					}

					for (File dependenciesFile : dependenciesFiles) {
						_dependenciesGlobalFileLocationsMap.put(
							SourceUtil.getAbsolutePath(
								dependenciesFile.getPath()),
							new TreeSet<>());
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private static final String _GLOBAL_DEPENDENCIES_DIRECTORY =
		"portal-web/test/functional/com/liferay/portalweb/dependencies";

	private static final String[] _SKIP_DIR_NAMES = {
		"poshi", "private", "source-formatter"
	};

	private static final String[] _TEST_FILE_LOCATIONS = {
		"modules", "portal-web/test/functional/com/liferay/portalweb/tests"
	};

	private static final Map<String, Set<String>>
		_dependenciesFileLocationsMap = new HashMap<>();
	private static boolean _dependenciesFileLocationsMapIsReady;
	private static final Map<String, Set<String>>
		_dependenciesGlobalFileLocationsMap = new HashMap<>();
	private static final List<String> _testCaseFileNames = new ArrayList<>();

}