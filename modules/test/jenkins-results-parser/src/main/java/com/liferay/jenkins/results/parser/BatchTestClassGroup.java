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

		_gitWorkingDirectory = gitWorkingDirectory;

		_batchName = batchName;

		_portalTestProperties = JenkinsResultsParserUtil.getProperties(
			new File(
				_gitWorkingDirectory.getWorkingDirectory(), "test.properties"));

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
		String testBatchClassesPerGroup = _portalTestProperties.getProperty(
			_TEST_BATCH_CLASSES_PER_GROUP_PROPERTY_NAME + "[" + _batchName +
				"]");

		if (testBatchClassesPerGroup == null) {
			testBatchClassesPerGroup = _portalTestProperties.getProperty(
				_TEST_BATCH_CLASSES_PER_GROUP_PROPERTY_NAME);
		}

		if (testBatchClassesPerGroup != null) {
			return Integer.parseInt(testBatchClassesPerGroup);
		}

		return _DEFAULT_TEST_BATCH_CLASSES_PER_GROUP;
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

	private void _setTestClassNamesExcludes() {
		String testClassNamesExcludes = _portalTestProperties.getProperty(
			_TEST_CLASS_NAMES_EXCLUDES_PROPERTY_NAME + "[" + _batchName + "]");

		if (testClassNamesExcludes == null) {
			testClassNamesExcludes = _portalTestProperties.getProperty(
				_TEST_CLASS_NAMES_EXCLUDES_PROPERTY_NAME);
		}

		if (testClassNamesExcludes != null) {
			Collections.addAll(
				_testClassNamesExcludes, testClassNamesExcludes.split(","));
		}
	}

	private void _setTestClassNamesExcludesPathMatchers() {
		File workingDirectory = _gitWorkingDirectory.getWorkingDirectory();

		String workingDirectoryPath = workingDirectory.getAbsolutePath();

		for (String testClassNamesExclude : _testClassNamesExcludes) {
			String filePattern =
				workingDirectoryPath + "/" + testClassNamesExclude;

			_testClassNamesExcludesPathMatchers.add(
				FileSystems.getDefault().getPathMatcher("glob:" + filePattern));
		}
	}

	private void _setTestClassNamesIncludes() {
		String testClassNamesIncludes = _portalTestProperties.getProperty(
			_TEST_CLASS_NAMES_INCLUDES_PROPERTY_NAME + "[" + _batchName + "]");

		if (testClassNamesIncludes == null) {
			testClassNamesIncludes = _portalTestProperties.getProperty(
				_TEST_CLASS_NAMES_INCLUDES_PROPERTY_NAME);
		}

		if (testClassNamesIncludes != null) {
			Collections.addAll(
				_testClassNamesIncludes, testClassNamesIncludes.split(","));
		}
	}

	private void _setTestClassNamesIncludesPathMatchers() {
		File workingDirectory = _gitWorkingDirectory.getWorkingDirectory();

		String workingDirectoryPath = workingDirectory.getAbsolutePath();

		for (String testClassNamesInclude : _testClassNamesIncludes) {
			String filePattern =
				workingDirectoryPath + "/" + testClassNamesInclude;

			_testClassNamesIncludesPathMatchers.add(
				FileSystems.getDefault().getPathMatcher("glob:" + filePattern));
		}
	}

	private static final int _DEFAULT_TEST_BATCH_CLASSES_PER_GROUP = 5000;

	private static final String _TEST_BATCH_CLASSES_PER_GROUP_PROPERTY_NAME =
		"test.batch.classes.per.group";

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
	private final Pattern _testClassPackagePathPattern = Pattern.compile(
		"(.*)(?<packagePath>com/.*)");

}