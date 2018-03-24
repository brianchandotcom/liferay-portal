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

		_setCurrentBranch();

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

	public int getTestBatchGroupSize() {
		return _testClassGroups.size();
	}

	public List<String> getTestClassGroup(int i) throws Exception {
		return _testClassGroups.get(i);
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
			if (properties.containsKey(propertyName)) {
				String propertyValue = properties.getProperty(propertyName);

				if ((propertyValue != null) && !propertyValue.isEmpty()) {
					return propertyValue;
				}
			}
		}

		return null;
	}

	private Set<String> _getTestClassFileNames() throws Exception {
		File workingDirectory = _gitWorkingDirectory.getWorkingDirectory();

		final Set<String> testClassFileNames = new HashSet<>();

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

					if (filePath.toFile().isDirectory()) {
						visitFile(filePath, attrs);
					}
					else if (_pathIncluded(filePath) &&
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

						packagePath = packagePath.replace(".java", ".class");

						return packagePath;
					}

					return filePath.replace(".java", ".class");
				}

			});

		return testClassFileNames;
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

	private void _setCurrentBranch() {
		List<String> orderedPropertyNames = new ArrayList<>();

		if (_testSuiteName != null) {
			orderedPropertyNames.add(
				JenkinsResultsParserUtil.combine(
					"test.batch.current.branch[", _testSuiteName, "]"));
		}

		orderedPropertyNames.add("test.batch.current.branch");

		String propertyValue = _getPropertyValueFromOrderedPropertyNames(
			_portalTestProperties, orderedPropertyNames);

		if (propertyValue != null) {
			_testBatchCurrentBranch = Boolean.parseBoolean(propertyValue);
		}

		_testBatchCurrentBranch = false;
	}

	private void _setTestClassGroups() throws Exception {
		final List<String> testClassFileNames = new ArrayList<>(
			_getTestClassFileNames());

		Collections.sort(testClassFileNames);

		int maxClassGroupSize = _getMaxClassGroupSize();
		int testClassFileNamesCount = testClassFileNames.size();

		int testBatchGroupSize = testClassFileNamesCount / maxClassGroupSize;

		/* Add guava and clean this up */

		if ((testClassFileNamesCount % maxClassGroupSize) != 0) {
			testBatchGroupSize++;
		}

		int balancedTestBatchClassesPerGroup =
			testClassFileNamesCount / testBatchGroupSize;

		if ((testClassFileNamesCount % testBatchGroupSize) != 0) {
			balancedTestBatchClassesPerGroup++;
		}

		for (int i = 0; i < testBatchGroupSize; i++) {
			_testClassGroups.add(
				testClassFileNames.subList(
					i * balancedTestBatchClassesPerGroup,
					Math.min(
						testClassFileNamesCount,
						i * balancedTestBatchClassesPerGroup +
							balancedTestBatchClassesPerGroup)));
		}
	}

	private List<String> _getCurrentBranchTestClassGlobs(
			List<String> testClassGlobs)
		throws IOException {

		List<String> currentBranchTestClassGlobs = new ArrayList<>();

		File workingDirectory = _gitWorkingDirectory.getWorkingDirectory();

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			(PortalGitWorkingDirectory)_gitWorkingDirectory;

		List<File> moduleGroupDirs =
			portalGitWorkingDirectory.getCurrentBranchModuleGroupDirs();

		for (File moduleGroupDir : moduleGroupDirs) {
			String moduleGroupDirPath = moduleGroupDir.getCanonicalPath();

			moduleGroupDirPath = moduleGroupDirPath.replace(
				workingDirectory.getCanonicalPath() + "/", "");

			for (String testClassGlob : testClassGlobs) {
				currentBranchTestClassGlobs.add(
					moduleGroupDirPath + "/" + testClassGlob);

				if (testClassGlob.startsWith("**/")) {
					currentBranchTestClassGlobs.add(
						moduleGroupDirPath + "/" + testClassGlob.substring(3));
				}
			}
		}

		return currentBranchTestClassGlobs;
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

				String testSuiteNameMatcher = matcher.group("testSuiteName");

				if (testSuiteName == null) {
					if (testSuiteNameMatcher != null) {
						continue;
					}

					if (!batchName.matches(batchNameMatcher)) {
						continue;
					}
				}
				else {
					if (testSuiteNameMatcher == null) {
						continue;
					}

					if (!testSuiteName.equals(testSuiteNameMatcher)) {
						continue;
					}

					if (!batchName.matches(batchNameMatcher)) {
						continue;
					}
				}

				return wildcardPropertyName;
			}
		}

		return null;
	}

	private void _setTestClassNamesExcludes() throws IOException {
		List<String> orderedPropertyNames = new ArrayList<>();

		if (_testSuiteName != null) {
			orderedPropertyNames.add(
				JenkinsResultsParserUtil.combine(
					"test.batch.class.names.excludes[", _batchName, "][",
					_testSuiteName, "]"));
			orderedPropertyNames.add(
				_getWildcardPropertyName(
					_portalTestProperties, "test.batch.class.names.excludes",
					_batchName, _testSuiteName));
			orderedPropertyNames.add(
				JenkinsResultsParserUtil.combine(
					"test.batch.class.names.excludes[", _testSuiteName, "]"));
		}

		orderedPropertyNames.add(
			_getWildcardPropertyName(
				_portalTestProperties, "test.batch.class.names.excludes",
				_batchName));
		orderedPropertyNames.add(
			JenkinsResultsParserUtil.combine(
				"test.batch.class.names.excludes[", _batchName, "]"));
		orderedPropertyNames.add("test.batch.class.names.excludes");
		orderedPropertyNames.add("test.class.names.excludes");

		String testClassNamesExcludes =
			_getPropertyValueFromOrderedPropertyNames(
				_portalTestProperties, orderedPropertyNames);

		if ((testClassNamesExcludes != null) &&
			!testClassNamesExcludes.isEmpty()) {

			List<String> testClassExcludeGlobs = Arrays.asList(
				testClassNamesExcludes.split(","));

			if (_testBatchCurrentBranch) {
				_testClassNamesExcludes.addAll(
					_getCurrentBranchTestClassGlobs(testClassExcludeGlobs));
			}
			else {
				_testClassNamesExcludes.addAll(testClassExcludeGlobs);
			}
		}
	}

	private List<PathMatcher> _getTestClassNamesPathMatchers(
			List<String> testClassGlobs) {

		List<PathMatcher> pathMatchers = new ArrayList<>();

		File workingDirectory = _gitWorkingDirectory.getWorkingDirectory();

		String workingDirectoryPath = workingDirectory.getAbsolutePath();

		for (String testClassGlob : testClassGlobs) {
			FileSystem fileSystem = FileSystems.getDefault();

			pathMatchers.add(
				fileSystem.getPathMatcher(
					"glob:" + workingDirectoryPath + "/" + testClassGlob));
		}

		return pathMatchers;
	}

	private void _setTestClassNamesExcludesPathMatchers() {
		_testClassNamesExcludesPathMatchers.addAll(
			_getTestClassNamesPathMatchers(_testClassNamesExcludes));
	}

	private void _setTestClassNamesIncludes() throws IOException {
		List<String> orderedPropertyNames = new ArrayList<>();

		if (_testSuiteName != null) {
			orderedPropertyNames.add(
				JenkinsResultsParserUtil.combine(
					"test.batch.class.names.includes[", _batchName, "][",
					_testSuiteName, "]"));
			orderedPropertyNames.add(
				_getWildcardPropertyName(
					_portalTestProperties, "test.batch.class.names.includes",
					_batchName, _testSuiteName));
			orderedPropertyNames.add(
				JenkinsResultsParserUtil.combine(
					"test.batch.class.names.includes[", _testSuiteName, "]"));
		}

		orderedPropertyNames.add(
			JenkinsResultsParserUtil.combine(
				"test.batch.class.names.includes[", _batchName, "]"));
		orderedPropertyNames.add(
			_getWildcardPropertyName(
				_portalTestProperties, "test.batch.class.names.includes",
				_batchName));
		orderedPropertyNames.add("test.batch.class.names.includes");
		orderedPropertyNames.add("test.class.names.includes");

		String testClassNamesIncludes =
			_getPropertyValueFromOrderedPropertyNames(
				_portalTestProperties, orderedPropertyNames);

		if ((testClassNamesIncludes != null) &&
			!testClassNamesIncludes.isEmpty()) {

			List<String> testClassIncludeGlobs = Arrays.asList(
				testClassNamesIncludes.split(","));

			if (_testBatchCurrentBranch) {
				_testClassNamesIncludes.addAll(
					_getCurrentBranchTestClassGlobs(testClassIncludeGlobs));
			}
			else {
				_testClassNamesIncludes.addAll(testClassIncludeGlobs);
			}
		}
	}

	private void _setTestClassNamesIncludesPathMatchers() {
		_testClassNamesIncludesPathMatchers.addAll(
			_getTestClassNamesPathMatchers(_testClassNamesIncludes));
	}

	private static final int _DEFAULT_MAX_CLASS_GROUP_SIZE = 5000;

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
	private final Pattern _packagePathPattern = Pattern.compile(
		".*/(?<packagePath>com/.*)");
	private final Pattern _propertyNamePattern = Pattern.compile(
		"[^\\]]+\\[(?<batchName>[^\\]]+)\\](\\[(?<testSuiteName>[^\\]]+)\\])?");

	private final String _testSuiteName;

}