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

import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yi-Chen Tsai
 */
public class JUnitBatchTestClassGroup extends BatchTestClassGroup {

	protected JUnitBatchTestClassGroup(
		String batchName, GitWorkingDirectory gitWorkingDirectory,
		String testSuiteName) {

		super(batchName, gitWorkingDirectory, testSuiteName);

		_setTestRelevantChanges();

		_setTestClassNamesExcludesRelativeGlobs();
		_setTestClassNamesIncludesRelativeGlobs();

		_setTestClassFiles();

		_setAxisTestClassGroups();
	}

	protected List<String> getRelevantTestClassNamesRelativeGlobs(
		List<String> testClassNamesRelativeGlobs) {

		List<String> relevantTestClassNameRelativeGlobs = new ArrayList<>();

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			(PortalGitWorkingDirectory)gitWorkingDirectory;

		List<File> moduleDirsList = null;

		try {
			moduleDirsList = portalGitWorkingDirectory.getModuleDirsList();
		}
		catch (IOException ioe) {
			File workingDirectory = gitWorkingDirectory.getWorkingDirectory();

			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to get module directories in ",
					workingDirectory.getPath()),
				ioe);
		}

		List<File> modifiedFilesList =
			gitWorkingDirectory.getModifiedFilesList();

		for (File modifiedFile : modifiedFilesList) {
			boolean foundModuleFile = false;

			for (File moduleDir : moduleDirsList) {
				if (JenkinsResultsParserUtil.isFileInDirectory(
						moduleDir, modifiedFile)) {

					foundModuleFile = true;

					break;
				}
			}

			if (foundModuleFile) {
				continue;
			}

			relevantTestClassNameRelativeGlobs.addAll(
				testClassNamesRelativeGlobs);

			return relevantTestClassNameRelativeGlobs;
		}

		return relevantTestClassNameRelativeGlobs;
	}

	private int _getAxisMaxSize() {
		String axisMaxSize = _getAxisMaxSizePropertyValue();

		if (axisMaxSize != null) {
			return Integer.parseInt(axisMaxSize);
		}

		return _DEFAULT_AXIS_MAX_SIZE;
	}

	private String _getAxisMaxSizePropertyValue() {
		List<String> propertyNames = new ArrayList<>();

		if (testSuiteName != null) {
			propertyNames.add(
				JenkinsResultsParserUtil.combine(
					"test.batch.axis.max.size[", batchName, "][", testSuiteName,
					"]"));

			propertyNames.add(
				_getWildcardPropertyName(
					portalTestProperties, "test.batch.axis.max.size",
					testSuiteName));

			propertyNames.add(
				JenkinsResultsParserUtil.combine(
					"test.batch.axis.max.size[", testSuiteName, "]"));
		}

		propertyNames.add(
			JenkinsResultsParserUtil.combine(
				"test.batch.axis.max.size[", batchName, "]"));

		propertyNames.add(
			_getWildcardPropertyName(
				portalTestProperties, "test.batch.axis.max.size"));

		propertyNames.add("test.batch.axis.max.size");

		return _getFirstPropertyValue(portalTestProperties, propertyNames);
	}

	private String _getFirstPropertyValue(
		Properties properties, List<String> propertyNames) {

		for (String propertyName : propertyNames) {
			if (propertyName == null) {
				continue;
			}

			if (properties.containsKey(propertyName)) {
				String propertyValue = properties.getProperty(propertyName);

				if ((propertyValue != null) && !propertyValue.isEmpty()) {
					return propertyValue;
				}
			}
		}

		return null;
	}

	private String _getTestClassNamesExcludesPropertyValue() {
		List<String> propertyNames = new ArrayList<>();

		if (testSuiteName != null) {
			propertyNames.add(
				JenkinsResultsParserUtil.combine(
					"test.batch.class.names.excludes[", batchName, "][",
					testSuiteName, "]"));

			propertyNames.add(
				_getWildcardPropertyName(
					portalTestProperties, "test.batch.class.names.excludes",
					testSuiteName));

			propertyNames.add(
				JenkinsResultsParserUtil.combine(
					"test.batch.class.names.excludes[", testSuiteName, "]"));
		}

		propertyNames.add(
			_getWildcardPropertyName(
				portalTestProperties, "test.batch.class.names.excludes"));

		propertyNames.add(
			JenkinsResultsParserUtil.combine(
				"test.batch.class.names.excludes[", batchName, "]"));

		propertyNames.add("test.batch.class.names.excludes");

		propertyNames.add("test.class.names.excludes");

		return _getFirstPropertyValue(portalTestProperties, propertyNames);
	}

	private String _getTestClassNamesIncludesPropertyValue() {
		List<String> propertyNames = new ArrayList<>();

		if (testSuiteName != null) {
			propertyNames.add(
				JenkinsResultsParserUtil.combine(
					"test.batch.class.names.includes[", batchName, "][",
					testSuiteName, "]"));

			propertyNames.add(
				_getWildcardPropertyName(
					portalTestProperties, "test.batch.class.names.includes",
					testSuiteName));

			propertyNames.add(
				JenkinsResultsParserUtil.combine(
					"test.batch.class.names.includes[", testSuiteName, "]"));
		}

		propertyNames.add(
			JenkinsResultsParserUtil.combine(
				"test.batch.class.names.includes[", batchName, "]"));

		propertyNames.add(
			_getWildcardPropertyName(
				portalTestProperties, "test.batch.class.names.includes"));

		propertyNames.add("test.batch.class.names.includes");

		propertyNames.add("test.class.names.includes");

		return _getFirstPropertyValue(portalTestProperties, propertyNames);
	}

	private List<PathMatcher> _getTestClassNamesPathMatchers(
		List<String> testClassNamesRelativeGlobs) {

		List<PathMatcher> pathMatchers = new ArrayList<>();

		File workingDirectory = gitWorkingDirectory.getWorkingDirectory();

		String workingDirectoryPath = workingDirectory.getAbsolutePath();

		for (String testClassNamesRelativeGlob : testClassNamesRelativeGlobs) {
			FileSystem fileSystem = FileSystems.getDefault();

			pathMatchers.add(
				fileSystem.getPathMatcher(
					JenkinsResultsParserUtil.combine(
						"glob:", workingDirectoryPath, "/",
						testClassNamesRelativeGlob)));
		}

		return pathMatchers;
	}

	private String _getWildcardPropertyName(
		Properties properties, String propertyName) {

		return _getWildcardPropertyName(properties, propertyName, null);
	}

	private String _getWildcardPropertyName(
		Properties properties, String propertyName, String testSuiteName) {

		for (String wildcardPropertyName : properties.stringPropertyNames()) {
			if (!wildcardPropertyName.startsWith(propertyName)) {
				continue;
			}

			Matcher matcher = _propertyNamePattern.matcher(
				wildcardPropertyName);

			if (matcher.find()) {
				String batchNameMatcher = matcher.group("batchName");

				batchNameMatcher = batchNameMatcher.replace("*", ".+");

				if (!batchName.matches(batchNameMatcher)) {
					continue;
				}

				String testSuiteNameMatcher = matcher.group("testSuiteName");

				if (testSuiteName != testSuiteNameMatcher) {
					continue;
				}

				return wildcardPropertyName;
			}
		}

		return null;
	}

	private void _setAxisTestClassGroups() {
		int testClassFileCount = testClassFiles.size();

		if (testClassFileCount == 0) {
			return;
		}

		int axisMaxSize = _getAxisMaxSize();

		int axisCount = (int)Math.ceil(
			(double)testClassFileCount / axisMaxSize);

		int axisSize = (int)Math.ceil((double)testClassFileCount / axisCount);

		int id = 0;

		for (List<File> axisTestClassFiles :
				Lists.partition(testClassFiles, axisSize)) {

			AxisTestClassGroup axisTestClassGroup = new AxisTestClassGroup(
				this, id);

			axisTestClassGroups.put(id, axisTestClassGroup);

			for (File axisTestClassFile : axisTestClassFiles) {
				axisTestClassGroup.addTestClassFile(axisTestClassFile);
			}

			id++;
		}
	}

	private void _setTestClassFiles() {
		File workingDirectory = gitWorkingDirectory.getWorkingDirectory();

		try {
			Files.walkFileTree(
				workingDirectory.toPath(),
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult preVisitDirectory(
							Path filePath, BasicFileAttributes attrs)
						throws IOException {

						if (_pathExcluded(filePath)) {
							return FileVisitResult.SKIP_SUBTREE;
						}

						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFile(
							Path filePath, BasicFileAttributes attrs)
						throws IOException {

						if (_pathIncluded(filePath) &&
							!_pathExcluded(filePath)) {

							testClassFiles.add(
								_getPackagePathClassFile(filePath));
						}

						return FileVisitResult.CONTINUE;
					}

					private File _getPackagePathClassFile(Path path) {
						String filePath = path.toString();

						Matcher matcher = _packagePathPattern.matcher(filePath);

						if (matcher.find()) {
							String packagePath = matcher.group("packagePath");

							packagePath = packagePath.replace(
								".java", ".class");

							return new File(packagePath);
						}

						return new File(filePath.replace(".java", ".class"));
					}

					private boolean _pathExcluded(Path path) {
						return _pathMatches(
							path, _testClassNamesExcludesPathMatchers);
					}

					private boolean _pathIncluded(Path path) {
						return _pathMatches(
							path, _testClassNamesIncludesPathMatchers);
					}

					private boolean _pathMatches(
						Path path, List<PathMatcher> pathMatchers) {

						for (PathMatcher pathMatcher : pathMatchers) {
							if (pathMatcher.matches(path)) {
								return true;
							}
						}

						return false;
					}

				});
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to search for test file names in " +
					workingDirectory.getPath(),
				ioe);
		}

		Collections.sort(testClassFiles);
	}

	private void _setTestClassNamesExcludesRelativeGlobs() {
		String testClassNamesExcludesPropertyValue =
			_getTestClassNamesExcludesPropertyValue();

		if ((testClassNamesExcludesPropertyValue == null) ||
			testClassNamesExcludesPropertyValue.isEmpty()) {

			return;
		}

		List<String> testClassNamesExcludesRelativeGlobs = Arrays.asList(
			testClassNamesExcludesPropertyValue.split(","));

		if (_testRelevantChanges) {
			testClassNamesExcludesRelativeGlobs =
				getRelevantTestClassNamesRelativeGlobs(
					testClassNamesExcludesRelativeGlobs);
		}

		_testClassNamesExcludesPathMatchers.addAll(
			_getTestClassNamesPathMatchers(
				testClassNamesExcludesRelativeGlobs));
	}

	private void _setTestClassNamesIncludesRelativeGlobs() {
		String testClassNamesIncludesPropertyValue =
			_getTestClassNamesIncludesPropertyValue();

		if ((testClassNamesIncludesPropertyValue == null) ||
			testClassNamesIncludesPropertyValue.isEmpty()) {

			return;
		}

		List<String> testClassNamesIncludesRelativeGlobs = Arrays.asList(
			testClassNamesIncludesPropertyValue.split(","));

		if (_testRelevantChanges) {
			testClassNamesIncludesRelativeGlobs =
				getRelevantTestClassNamesRelativeGlobs(
					testClassNamesIncludesRelativeGlobs);
		}

		_testClassNamesIncludesPathMatchers.addAll(
			_getTestClassNamesPathMatchers(
				testClassNamesIncludesRelativeGlobs));
	}

	private void _setTestRelevantChanges() {
		List<String> propertyNames = new ArrayList<>();

		if (testSuiteName != null) {
			propertyNames.add(
				JenkinsResultsParserUtil.combine(
					"test.relevant.changes[", testSuiteName, "]"));
		}

		propertyNames.add("test.relevant.changes");

		String propertyValue = _getFirstPropertyValue(
			portalTestProperties, propertyNames);

		if (propertyValue != null) {
			_testRelevantChanges = Boolean.parseBoolean(propertyValue);

			return;
		}

		_testRelevantChanges = _DEFAULT_TEST_RELEVANT_CHANGES;
	}

	private static final int _DEFAULT_AXIS_MAX_SIZE = 5000;

	private static final boolean _DEFAULT_TEST_RELEVANT_CHANGES = false;

	private final Pattern _packagePathPattern = Pattern.compile(
		".*/(?<packagePath>com/.*)");
	private final Pattern _propertyNamePattern = Pattern.compile(
		"[^\\]]+\\[(?<batchName>[^\\]]+)\\](\\[(?<testSuiteName>[^\\]]+)\\])?");
	private final List<PathMatcher> _testClassNamesExcludesPathMatchers =
		new ArrayList<>();
	private final List<PathMatcher> _testClassNamesIncludesPathMatchers =
		new ArrayList<>();
	private boolean _testRelevantChanges;

}