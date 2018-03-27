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

	public String getBatchName() {
		return _batchName;
	}

	public GitWorkingDirectory getGitWorkingDirectory() {
		return _gitWorkingDirectory;
	}

	public Properties getPortalTestProperties() {
		return _portalTestProperties;
	}

	public TestBatchAxis getTestBatchAxis(int testBatchAxisId) {
		return _testBatchAxes.get(testBatchAxisId);
	}

	public int getTestBatchGroupSize() {
		return _testBatchAxes.size();
	}

	public static class TestBatchAxis {

		public void addTestClassFile(File testClassFile) {
			_testClassFiles.add(testClassFile);
		}

		public int getId() {
			return _id;
		}

		public List<File> getTestClassFiles() {
			return _testClassFiles;
		}

		private TestBatchAxis(int id) {
			_id = id;

			_testClassFiles = new ArrayList<>();
		}

		private final int _id;
		private final List<File> _testClassFiles;

	}

	protected TestBatchGroup(
		GitWorkingDirectory gitWorkingDirectory, String batchName) {

		this(gitWorkingDirectory, batchName, null);
	}

	protected TestBatchGroup(
		GitWorkingDirectory gitWorkingDirectory, String batchName,
		String testSuiteName) {

		_gitWorkingDirectory = gitWorkingDirectory;

		_batchName = batchName;

		_testSuiteName = testSuiteName;

		_portalTestProperties = JenkinsResultsParserUtil.getProperties(
			new File(
				_gitWorkingDirectory.getWorkingDirectory(), "test.properties"));

		_setTestBatchCurrentBranch();

		_setTestClassNamesExcludes();
		_setTestClassNamesIncludes();

		_setTestClassGroups();
	}

	private List<String> _getCurrentBranchTestClassNamesGlobs(
		List<String> testClassNamesGlobs) {

		List<String> currentBranchTestClassNameGlobs = new ArrayList<>();

		File workingDirectory = _gitWorkingDirectory.getWorkingDirectory();

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			(PortalGitWorkingDirectory)_gitWorkingDirectory;

		List<File> moduleGroupDirs = null;

		try {
			moduleGroupDirs =
				portalGitWorkingDirectory.getCurrentBranchModuleGroupDirs();
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to get current branch module group directories ",
					"under ", workingDirectory.getPath()),
				ioe);
		}

		for (File moduleGroupDir : moduleGroupDirs) {
			String moduleGroupDirPath = moduleGroupDir.getAbsolutePath();

			moduleGroupDirPath = moduleGroupDirPath.replace(
				workingDirectory.getAbsolutePath() + "/", "");

			for (String testClassNamesGlob : testClassNamesGlobs) {
				currentBranchTestClassNameGlobs.add(
					moduleGroupDirPath + "/" + testClassNamesGlob);

				if (testClassNamesGlob.startsWith("**/")) {
					currentBranchTestClassNameGlobs.add(
						moduleGroupDirPath + "/" +
							testClassNamesGlob.substring(3));
				}
			}
		}

		return currentBranchTestClassNameGlobs;
	}

	private int _getMaxClassGroupSize() {
		List<String> orderedPropertyNames = new ArrayList<>();

		if (_testSuiteName != null) {
			orderedPropertyNames.add(
				JenkinsResultsParserUtil.combine(
					"test.batch.max.class.group.size[", _batchName, "][",
					_testSuiteName, "]"));

			orderedPropertyNames.add(
				_getWildcardPropertyName(
					_portalTestProperties, "test.batch.max.class.group.size",
					_batchName, _testSuiteName));

			orderedPropertyNames.add(
				JenkinsResultsParserUtil.combine(
					"test.batch.max.class.group.size[", _testSuiteName, "]"));
		}

		orderedPropertyNames.add(
			JenkinsResultsParserUtil.combine(
				"test.batch.max.class.group.size[", _batchName, "]"));

		orderedPropertyNames.add(
			_getWildcardPropertyName(
				_portalTestProperties, "test.batch.max.class.group.size",
				_batchName));

		orderedPropertyNames.add("test.batch.max.class.group.size");

		String propertyValue = _getPropertyValueFromOrderedPropertyNames(
			_portalTestProperties, orderedPropertyNames);

		if (propertyValue != null) {
			return Integer.parseInt(propertyValue);
		}

		return _DEFAULT_MAX_CLASS_GROUP_SIZE;
	}

	private String _getPropertyValueFromOrderedPropertyNames(
		Properties properties, List<String> orderedPropertyNames) {

		for (String propertyName : orderedPropertyNames) {
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

	private Set<String> _getTestClassFileNames() {
		File workingDirectory = _gitWorkingDirectory.getWorkingDirectory();

		final Set<String> testClassFileNames = new HashSet<>();

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

							testClassFileNames.add(
								_getPackagePath(filePath.toString()));
						}

						return FileVisitResult.CONTINUE;
					}

					private String _getPackagePath(String filePath) {
						Matcher matcher = _packagePathPattern.matcher(filePath);

						if (matcher.find()) {
							String packagePath = matcher.group("packagePath");

							packagePath = packagePath.replace(
								".java", ".class");

							return packagePath;
						}

						return filePath.replace(".java", ".class");
					}

				});
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to search for test file names under" +
					workingDirectory.getPath(),
				ioe);
		}

		return testClassFileNames;
	}

	private List<PathMatcher> _getTestClassNamesPathMatchers(
		List<String> testClassNamesGlobs) {

		List<PathMatcher> pathMatchers = new ArrayList<>();

		File workingDirectory = _gitWorkingDirectory.getWorkingDirectory();

		String workingDirectoryPath = workingDirectory.getAbsolutePath();

		for (String testClassNamesGlob : testClassNamesGlobs) {
			FileSystem fileSystem = FileSystems.getDefault();

			pathMatchers.add(
				fileSystem.getPathMatcher(
					"glob:" + workingDirectoryPath + "/" + testClassNamesGlob));
		}

		return pathMatchers;
	}

	private String _getWildcardPropertyName(
		Properties properties, String propertyName, String batchName) {

		return _getWildcardPropertyName(
			properties, propertyName, batchName, null);
	}

	private String _getWildcardPropertyName(
		Properties properties, String propertyName, String batchName,
		String testSuiteName) {

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

	private boolean _pathExcluded(Path path) {
		return _pathMatches(path, _testClassNamesExcludesPathMatchers);
	}

	private boolean _pathIncluded(Path path) {
		return _pathMatches(path, _testClassNamesIncludesPathMatchers);
	}

	private boolean _pathMatches(Path path, List<PathMatcher> pathMatchers) {
		for (PathMatcher pathMatcher : pathMatchers) {
			if (pathMatcher.matches(path)) {
				return true;
			}
		}

		return false;
	}

	private void _setTestBatchCurrentBranch() {
		List<String> propertyNames = new ArrayList<>();

		if (_testSuiteName != null) {
			propertyNames.add(
				JenkinsResultsParserUtil.combine(
					"test.batch.current.branch[", _testSuiteName, "]"));
		}

		propertyNames.add("test.batch.current.branch");

		String propertyValue = _getPropertyValueFromOrderedPropertyNames(
			_portalTestProperties, propertyNames);

		if (propertyValue != null) {
			_testBatchCurrentBranch = Boolean.parseBoolean(propertyValue);

			return;
		}

		_testBatchCurrentBranch = _DEFAULT_TEST_BATCH_CURRENT_BRANCH;
	}

	private void _setTestClassGroups() {
		List<String> testClassFileNames = new ArrayList<>(
			_getTestClassFileNames());

		Collections.sort(testClassFileNames);

		int testClassFileNamesCount = testClassFileNames.size();

		if (testClassFileNamesCount == 0) {
			return;
		}

		int maxClassGroupSize = _getMaxClassGroupSize();

		int testBatchGroupSize = (int)Math.ceil(
			(double)testClassFileNamesCount / maxClassGroupSize);

		int classGroupSize = (int)Math.ceil(
			(double)testClassFileNamesCount / testBatchGroupSize);

		int id = 0;

		for (List<String> axisTestClassFileNames :
				Lists.partition(testClassFileNames, classGroupSize)) {

			TestBatchAxis testBatchAxis = new TestBatchAxis(id);

			_testBatchAxes.add(testBatchAxis);

			for (String axisTestBatchFileName : axisTestClassFileNames) {
				testBatchAxis.addTestClassFile(new File(axisTestBatchFileName));
			}

			id++;
		}
	}

	private void _setTestClassNamesExcludes() {
		List<String> propertyNames = new ArrayList<>();

		if (_testSuiteName != null) {
			propertyNames.add(
				JenkinsResultsParserUtil.combine(
					"test.batch.class.names.excludes[", _batchName, "][",
					_testSuiteName, "]"));

			propertyNames.add(
				_getWildcardPropertyName(
					_portalTestProperties, "test.batch.class.names.excludes",
					_batchName, _testSuiteName));

			propertyNames.add(
				JenkinsResultsParserUtil.combine(
					"test.batch.class.names.excludes[", _testSuiteName, "]"));
		}

		propertyNames.add(
			_getWildcardPropertyName(
				_portalTestProperties, "test.batch.class.names.excludes",
				_batchName));

		propertyNames.add(
			JenkinsResultsParserUtil.combine(
				"test.batch.class.names.excludes[", _batchName, "]"));

		propertyNames.add("test.batch.class.names.excludes");

		propertyNames.add("test.class.names.excludes");

		String testClassNamesExcludes =
			_getPropertyValueFromOrderedPropertyNames(
				_portalTestProperties, propertyNames);

		if ((testClassNamesExcludes != null) &&
			!testClassNamesExcludes.isEmpty()) {

			List<String> testClassNamesExcludesGlobs = Arrays.asList(
				testClassNamesExcludes.split(","));

			if (_testBatchCurrentBranch) {
				_testClassNamesExcludesGlobs.addAll(
					_getCurrentBranchTestClassNamesGlobs(
						testClassNamesExcludesGlobs));
			}
			else {
				_testClassNamesExcludesGlobs.addAll(
					testClassNamesExcludesGlobs);
			}
		}

		_testClassNamesExcludesPathMatchers.addAll(
			_getTestClassNamesPathMatchers(_testClassNamesExcludesGlobs));
	}

	private void _setTestClassNamesIncludes() {
		List<String> propertyNames = new ArrayList<>();

		if (_testSuiteName != null) {
			propertyNames.add(
				JenkinsResultsParserUtil.combine(
					"test.batch.class.names.includes[", _batchName, "][",
					_testSuiteName, "]"));

			propertyNames.add(
				_getWildcardPropertyName(
					_portalTestProperties, "test.batch.class.names.includes",
					_batchName, _testSuiteName));

			propertyNames.add(
				JenkinsResultsParserUtil.combine(
					"test.batch.class.names.includes[", _testSuiteName, "]"));
		}

		propertyNames.add(
			JenkinsResultsParserUtil.combine(
				"test.batch.class.names.includes[", _batchName, "]"));

		propertyNames.add(
			_getWildcardPropertyName(
				_portalTestProperties, "test.batch.class.names.includes",
				_batchName));

		propertyNames.add("test.batch.class.names.includes");

		propertyNames.add("test.class.names.includes");

		String testClassNamesIncludes =
			_getPropertyValueFromOrderedPropertyNames(
				_portalTestProperties, propertyNames);

		if ((testClassNamesIncludes != null) &&
			!testClassNamesIncludes.isEmpty()) {

			List<String> testClassNamesIncludesGlobs = Arrays.asList(
				testClassNamesIncludes.split(","));

			if (_testBatchCurrentBranch) {
				_testClassNamesIncludesGlobs.addAll(
					_getCurrentBranchTestClassNamesGlobs(
						testClassNamesIncludesGlobs));
			}
			else {
				_testClassNamesIncludesGlobs.addAll(
					testClassNamesIncludesGlobs);
			}
		}

		_testClassNamesIncludesPathMatchers.addAll(
			_getTestClassNamesPathMatchers(_testClassNamesIncludesGlobs));
	}

	private static final int _DEFAULT_MAX_CLASS_GROUP_SIZE = 5000;

	private static final boolean _DEFAULT_TEST_BATCH_CURRENT_BRANCH = false;

	private final String _batchName;
	private final GitWorkingDirectory _gitWorkingDirectory;
	private final Pattern _packagePathPattern = Pattern.compile(
		".*/(?<packagePath>com/.*)");
	private final Properties _portalTestProperties;
	private final Pattern _propertyNamePattern = Pattern.compile(
		"[^\\]]+\\[(?<batchName>[^\\]]+)\\](\\[(?<testSuiteName>[^\\]]+)\\])?");
	private final List<TestBatchAxis> _testBatchAxes = new ArrayList<>();
	private boolean _testBatchCurrentBranch;
	private final List<String> _testClassNamesExcludesGlobs = new ArrayList<>();
	private final List<PathMatcher> _testClassNamesExcludesPathMatchers =
		new ArrayList<>();
	private final List<String> _testClassNamesIncludesGlobs = new ArrayList<>();
	private final List<PathMatcher> _testClassNamesIncludesPathMatchers =
		new ArrayList<>();
	private final String _testSuiteName;

}