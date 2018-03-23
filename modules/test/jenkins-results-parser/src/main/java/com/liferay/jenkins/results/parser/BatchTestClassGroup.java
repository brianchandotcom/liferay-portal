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

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class TestBatchGroup {

	public TestBatchGroup(
			GitWorkingDirectory gitWorkingDirectory, String batchName)
		throws Exception {

		this(gitWorkingDirectory, batchName, null);
	}

	public TestBatchGroup(
			GitWorkingDirectory gitWorkingDirectory, String batchName,
			String testSuiteName)
		throws Exception {

		_gitWorkingDirectory = gitWorkingDirectory;

		_batchName = batchName;

		_testSuiteName = testSuiteName;

		_portalTestProperties = JenkinsResultsParserUtil.getProperties(
				new File(
						_gitWorkingDirectory.getWorkingDirectory(), "test.properties"));

		_setTestBatchCurrentBranch();

		_setTestClassNamesExcludes();
		_setTestClassNamesIncludes();

		_setTestClassNamesExcludesPathMatchers();
		_setTestClassNamesIncludesPathMatchers();

		_setTestClassGroups();
	}

	public String getBatchName() {
		return _batchName;
	}

	public GitWorkingDirectory getGitWorkingDirectory() {
		return _gitWorkingDirectory;
	}

	public Properties getPortalTestProperties() {
		return _portalTestProperties;
	}

	public List<String> getTestClassList(int i) throws Exception {
		return _testClassGroups.get(i);
	}

	public int getTestClassListCount() {
		return _testClassGroups.size();
	}

	private boolean _filePathExcluded(Path filePath) {
		for (PathMatcher excludePathMatcher :
				_testClassNamesExcludesPathMatchers) {

			if (excludePathMatcher.matches(filePath)) {
				return true;
			}
		}

		return false;
	}

	private boolean _filePathIncluded(Path filePath) {
		for (PathMatcher includePathMatcher :
				_testClassNamesIncludesPathMatchers) {

			if (includePathMatcher.matches(filePath)) {
				return true;
			}
		}

		return false;
	}

	private int _getTestBatchClassesPerGroup() {
		String[] propertyNames = {
			JenkinsResultsParserUtil.combine(_TEST_BATCH_CLASSES_PER_GROUP_PROPERTY_NAME, "[", _batchName, "][", _testSuiteName, "]"),
			JenkinsResultsParserUtil.combine(_TEST_BATCH_CLASSES_PER_GROUP_PROPERTY_NAME, "[", _batchName, "]"),
			JenkinsResultsParserUtil.combine(_TEST_BATCH_CLASSES_PER_GROUP_PROPERTY_NAME, "[", _testSuiteName, "]"),
			_TEST_BATCH_CLASSES_PER_GROUP_PROPERTY_NAME
		};

		for (String propertyName : propertyNames) {
			if (_portalTestProperties.containsKey(propertyName)) {
				String propertyValue = _portalTestProperties.getProperty(
						propertyName);

				if ((propertyValue != null) && !propertyValue.isEmpty()) {
					System.out.println(propertyName + "=" + propertyValue);

					return Integer.parseInt(propertyValue);
				}
			}
		}

		return _DEFAULT_TEST_BATCH_CLASSES_PER_GROUP;
	}

	private void _setTestBatchCurrentBranch() {
		String[] propertyNames = {
			JenkinsResultsParserUtil.combine(_TEST_BATCH_CURRENT_BRANCH_PROPERTY_NAME, "[", _testSuiteName, "]"),
			_TEST_BATCH_CURRENT_BRANCH_PROPERTY_NAME
		};

		for (String propertyName : propertyNames) {
			if (_portalTestProperties.containsKey(propertyName)) {
				String propertyValue = _portalTestProperties.getProperty(
					propertyName);

				if ((propertyValue != null) && !propertyValue.isEmpty()) {
					_testBatchCurrentBranch = Boolean.parseBoolean(
						propertyValue);

					System.out.println(propertyName + "=" + propertyValue);

					return;
				}
			}
		}

		_testBatchCurrentBranch = false;
	}

	private Set<String> _getTestClassFileNamesSet() throws Exception {
		File workingDirectory = _gitWorkingDirectory.getWorkingDirectory();

		final Set<String> testClassFileNamesSet = new HashSet<>();

		Files.walkFileTree(
				workingDirectory.toPath(),
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult preVisitDirectory(
							Path filePath, BasicFileAttributes attrs)
							throws IOException {

						if (_filePathExcluded(filePath)) {
							return FileVisitResult.SKIP_SUBTREE;
						}

						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFile(
							Path filePath, BasicFileAttributes attrs)
							throws IOException {

						if (filePath.toFile().isDirectory()) {
							visitFile(filePath, attrs);
						}
						else if (_filePathIncluded(filePath) &&
								!_filePathExcluded(filePath)) {

							testClassFileNamesSet.add(
									_getTestClassPackagePath(filePath.toString()));
						}

						return FileVisitResult.CONTINUE;
					}

				});

		return testClassFileNamesSet;
	}

	private String _getTestClassPackagePath(String filePath) {
		Matcher matcher = _testClassPackagePathPattern.matcher(filePath);

		if (matcher.find()) {
			String packagePath = matcher.group("packagePath");

			packagePath = packagePath.replace(".java", ".class");

			return packagePath;
		}

		return filePath.replace(".java", ".class");
	}

	private void _setTestClassGroups() throws Exception {
		final List<String> testClassFileNames = new ArrayList<>();

		testClassFileNames.addAll(_getTestClassFileNamesSet());

		Collections.sort(testClassFileNames);

		int testBatchClassesPerGroup = _getTestBatchClassesPerGroup();

		for (int i = 0; i < testClassFileNames.size();
			 i += testBatchClassesPerGroup) {

			_testClassGroups.add(
					testClassFileNames.subList(
							i,
							Math.min(
									testClassFileNames.size(),
									i + testBatchClassesPerGroup)));
		}
	}

	private void _setTestClassNamesExcludes() throws IOException {
		String[] propertyNames = {
			JenkinsResultsParserUtil.combine("test.batch.class.names.excludes[", _batchName, "][", _testSuiteName, "]"),
			JenkinsResultsParserUtil.combine("test.batch.class.names.excludes[", _batchName, "]"),
			JenkinsResultsParserUtil.combine("test.batch.class.names.excludes[", _testSuiteName, "]"),
			_TEST_CLASS_NAMES_EXCLUDES_PROPERTY_NAME
		};

		String testClassNamesExcludes = null;

		for (String propertyName : propertyNames) {
			if (_portalTestProperties.containsKey(propertyName)) {
				String propertyValue = _portalTestProperties.getProperty(
						propertyName);

				if (propertyValue != null) {
					testClassNamesExcludes = propertyValue;

					break;
				}
			}
		}

		if (testClassNamesExcludes != null && !testClassNamesExcludes.isEmpty()) {
			if (_testBatchCurrentBranch) {
				PortalGitWorkingDirectory portalGitWorkingDirectory =
					(PortalGitWorkingDirectory)_gitWorkingDirectory;

				File workingDirectory =
					portalGitWorkingDirectory.getWorkingDirectory();

				String workingDirectoryPath =
					workingDirectory.getCanonicalPath();

				List<File> moduleGroupDirs =
					portalGitWorkingDirectory.getCurrentBranchModuleGroupDirs();

				for (File moduleGroupDir : moduleGroupDirs) {
					String moduleGroupDirPath =
						moduleGroupDir.getCanonicalPath();

					moduleGroupDirPath = moduleGroupDirPath.replace(
							workingDirectoryPath + "/", "");

					for (String testClassNames :
							testClassNamesExcludes.split(",")) {

						_testClassNamesExcludes.add(
							moduleGroupDirPath + "/" + testClassNames);

						if (testClassNames.startsWith("**/")) {
							_testClassNamesExcludes.add(
								moduleGroupDirPath + "/" + testClassNames.substring(3));
						}
					}
				}

				System.out.println(_testClassNamesExcludes);
			}
			else {
				Collections.addAll(
					_testClassNamesExcludes, testClassNamesExcludes.split(","));
			}
		}
	}

	private void _setTestClassNamesExcludesPathMatchers() {
		File workingDirectory = _gitWorkingDirectory.getWorkingDirectory();

		String workingDirectoryPath = workingDirectory.getAbsolutePath();

		System.out.println("\nEXCLUDES\n");

		for (String testClassNamesExclude : _testClassNamesExcludes) {
			String filePattern =
				workingDirectoryPath + "/" + testClassNamesExclude;

			System.out.println("\tglob: " + filePattern);

			_testClassNamesExcludesPathMatchers.add(
				FileSystems.getDefault().getPathMatcher("glob:" + filePattern));
		}

		System.out.println();
	}

	private void _setTestClassNamesIncludes() throws IOException {
		String[] propertyNames = {
			JenkinsResultsParserUtil.combine("test.batch.class.names.includes[", _batchName, "][", _testSuiteName, "]"),
			JenkinsResultsParserUtil.combine("test.batch.class.names.includes[", _batchName, "]"),
			JenkinsResultsParserUtil.combine("test.batch.class.names.includes[", _testSuiteName, "]"),
			_TEST_CLASS_NAMES_INCLUDES_PROPERTY_NAME
		};

		String testClassNamesIncludes = null;

		for (String propertyName : propertyNames) {
			if (_portalTestProperties.containsKey(propertyName)) {
				String propertyValue = _portalTestProperties.getProperty(
						propertyName);

				if ((propertyValue != null) && !propertyValue.isEmpty()) {
					testClassNamesIncludes = propertyValue;

					break;
				}
			}
		}

		if (testClassNamesIncludes != null) {
			if (_testBatchCurrentBranch) {
				PortalGitWorkingDirectory portalGitWorkingDirectory =
					(PortalGitWorkingDirectory)_gitWorkingDirectory;

				File workingDirectory =
					portalGitWorkingDirectory.getWorkingDirectory();

				String workingDirectoryPath =
					workingDirectory.getCanonicalPath();

				List<File> moduleGroupDirs =
					portalGitWorkingDirectory.getCurrentBranchModuleGroupDirs();

				for (File moduleGroupDir : moduleGroupDirs) {
					String moduleGroupDirPath =
						moduleGroupDir.getCanonicalPath();

					moduleGroupDirPath = moduleGroupDirPath.replace(
						workingDirectoryPath + "/", "");

					for (String testClassNames :
							testClassNamesIncludes.split(",")) {

						_testClassNamesIncludes.add(
							moduleGroupDirPath + "/" + testClassNames);

						if (testClassNames.startsWith("**/")) {
							_testClassNamesIncludes.add(
								moduleGroupDirPath + "/" + testClassNames.substring(3));
						}
					}
				}

				System.out.println(_testClassNamesIncludes);
			}
			else {
				Collections.addAll(
					_testClassNamesIncludes, testClassNamesIncludes.split(","));
			}
		}
	}

	private void _setTestClassNamesIncludesPathMatchers() {
		File workingDirectory = _gitWorkingDirectory.getWorkingDirectory();

		String workingDirectoryPath = workingDirectory.getAbsolutePath();

		System.out.println("\nINCLUDES\n");

		for (String testClassNamesInclude : _testClassNamesIncludes) {
			String filePattern =
					workingDirectoryPath + "/" + testClassNamesInclude;

			System.out.println("\tglob: " + filePattern);

			_testClassNamesIncludesPathMatchers.add(
					FileSystems.getDefault().getPathMatcher("glob:" + filePattern));
		}

		System.out.println();
	}

	private static final int _DEFAULT_TEST_BATCH_CLASSES_PER_GROUP = 5000;

	private static final String _TEST_BATCH_CLASSES_PER_GROUP_PROPERTY_NAME =
		"test.batch.classes.per.group";

	private static final String _TEST_BATCH_CURRENT_BRANCH_PROPERTY_NAME =
		"test.batch.current.branch";

	private static final String _TEST_CLASS_NAMES_EXCLUDES_PROPERTY_NAME =
		"test.class.names.excludes";

	private static final String _TEST_CLASS_NAMES_INCLUDES_PROPERTY_NAME =
		"test.class.names.includes";

	private final String _batchName;
	private final GitWorkingDirectory _gitWorkingDirectory;
	private final Properties _portalTestProperties;
	private final List<List<String>> _testClassGroups = new ArrayList<>();
	private final List<String> _testClassNamesExcludes = new ArrayList<>();
	private final List<PathMatcher> _testClassNamesExcludesPathMatchers =
		new ArrayList<>();
	private final List<String> _testClassNamesIncludes = new ArrayList<>();
	private final List<PathMatcher> _testClassNamesIncludesPathMatchers =
		new ArrayList<>();
	private boolean _testBatchCurrentBranch;
	private final Pattern _testClassPackagePathPattern = Pattern.compile(
		"(.*)(?<packagePath>com/.*)");
	private final String _testSuiteName;

}