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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yi-Chen Tsai
 */
public class JUnitBatchTestClassGroup extends BatchTestClassGroup {

	protected JUnitBatchTestClassGroup(
		String batchName, PortalGitWorkingDirectory portalGitWorkingDirectory,
		String testSuiteName) {

		super(batchName, portalGitWorkingDirectory, testSuiteName);

		_setTestClassNamesExcludesRelativeGlobs();
		_setTestClassNamesIncludesRelativeGlobs();

		setTestClassFiles();

		setAxisTestClassGroups();
	}

	protected List<String> getRelevantTestClassNamesRelativeGlobs(
		List<String> testClassNamesRelativeGlobs) {

		List<String> relevantTestClassNameRelativeGlobs = new ArrayList<>();

		List<File> moduleDirsList = null;

		try {
			moduleDirsList = portalGitWorkingDirectory.getModuleDirsList();
		}
		catch (IOException ioe) {
			File workingDirectory =
				portalGitWorkingDirectory.getWorkingDirectory();

			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to get module directories in ",
					workingDirectory.getPath()),
				ioe);
		}

		List<File> modifiedFilesList =
			portalGitWorkingDirectory.getModifiedFilesList();

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

	protected void setTestClassFiles() {
		File workingDirectory = portalGitWorkingDirectory.getWorkingDirectory();

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
							path, testClassNamesExcludesPathMatchers);
					}

					private boolean _pathIncluded(Path path) {
						return _pathMatches(
							path, testClassNamesIncludesPathMatchers);
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

	protected final List<PathMatcher> testClassNamesExcludesPathMatchers =
		new ArrayList<>();
	protected final List<PathMatcher> testClassNamesIncludesPathMatchers =
		new ArrayList<>();

	private String _getTestClassNamesExcludesPropertyValue() {
		String propertyValue = getFirstPropertyValue(
			"test.batch.class.names.excludes");

		if (propertyValue != null) {
			return propertyValue;
		}

		return portalTestProperties.getProperty("test.class.names.excludes");
	}

	private String _getTestClassNamesIncludesPropertyValue() {
		String propertyValue = getFirstPropertyValue(
			"test.batch.class.names.includes");

		if (propertyValue != null) {
			return propertyValue;
		}

		return portalTestProperties.getProperty("test.class.names.includes");
	}

	private List<PathMatcher> _getTestClassNamesPathMatchers(
		List<String> testClassNamesRelativeGlobs) {

		List<PathMatcher> pathMatchers = new ArrayList<>();

		File workingDirectory = portalGitWorkingDirectory.getWorkingDirectory();

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

	private void _setTestClassNamesExcludesRelativeGlobs() {
		String testClassNamesExcludesPropertyValue =
			_getTestClassNamesExcludesPropertyValue();

		if ((testClassNamesExcludesPropertyValue == null) ||
			testClassNamesExcludesPropertyValue.isEmpty()) {

			return;
		}

		List<String> testClassNamesExcludesRelativeGlobs = Arrays.asList(
			testClassNamesExcludesPropertyValue.split(","));

		if (testRelevantChanges) {
			testClassNamesExcludesRelativeGlobs =
				getRelevantTestClassNamesRelativeGlobs(
					testClassNamesExcludesRelativeGlobs);
		}

		testClassNamesExcludesPathMatchers.addAll(
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

		if (testRelevantChanges) {
			testClassNamesIncludesRelativeGlobs =
				getRelevantTestClassNamesRelativeGlobs(
					testClassNamesIncludesRelativeGlobs);
		}

		testClassNamesIncludesPathMatchers.addAll(
			_getTestClassNamesPathMatchers(
				testClassNamesIncludesRelativeGlobs));
	}

	private final Pattern _packagePathPattern = Pattern.compile(
		".*/(?<packagePath>com/.*)");

}