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

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;

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
import java.util.Collections;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class TCKJunitBatchTestClassGroup extends BatchTestClassGroup {

	public static class TCKBatchTestClass extends BaseTestClass {

		protected static TCKBatchTestClass getInstance(
			String batchName, File warFile) {

			return new TCKBatchTestClass(batchName, warFile);
		}

		protected TCKBatchTestClass(String batchName, File file) {
			super(file);

			addTestMethod(batchName);
		}

	}

	protected TCKJunitBatchTestClassGroup(
		String batchName, PortalGitWorkingDirectory portalGitWorkingDirectory,
		String testSuiteName) {

		super(batchName, portalGitWorkingDirectory, testSuiteName);

		File workingDirectory = portalGitWorkingDirectory.getWorkingDirectory();

		File tckHomeDirectory = new File(workingDirectory, "tools/tck");

		if (!tckHomeDirectory.exists()) {
			tckHomeDirectory = new File(
				JenkinsResultsParserUtil.getProperty(
					portalTestProperties, "tck.home"));
		}

		_tckHomeDirectory = tckHomeDirectory;

		_testClassNameExcludePathMatchers = _getTestClassNamesPathMatchers(
			"test.batch.class.names.excludes");
		_testClassNameIncludePathMatchers = _getTestClassNamesPathMatchers(
			"test.batch.class.names.includes");

		setTestClasses();

		setAxisTestClassGroups();
	}

	protected void setTestClasses() {
		try {
			Files.walkFileTree(
				_tckHomeDirectory.toPath(),
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult visitFile(
							Path filePath, BasicFileAttributes attrs)
						throws IOException {

						if (_pathExcluded(filePath)) {
							return FileVisitResult.SKIP_SUBTREE;
						}

						if (_pathIncluded(filePath)) {
							testClasses.add(
								TCKBatchTestClass.getInstance(
									batchName, filePath.toFile()));
						}

						return FileVisitResult.CONTINUE;
					}

					private boolean _pathExcluded(Path path) {
						return _pathMatches(
							path, _testClassNameExcludePathMatchers);
					}

					private boolean _pathIncluded(Path path) {
						return _pathMatches(
							path, _testClassNameIncludePathMatchers);
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
					_tckHomeDirectory.getPath(),
				ioe);
		}

		Collections.sort(testClasses);
	}

	private List<PathMatcher> _getTestClassNamesPathMatchers(
		String propertyName) {

		String testClassNamesRelativeGlobs = getFirstPropertyValue(
			propertyName);

		if ((testClassNamesRelativeGlobs == null) ||
			testClassNamesRelativeGlobs.isEmpty()) {

			return Collections.emptyList();
		}

		List<PathMatcher> pathMatchers = new ArrayList<>();

		String workingDirectoryPath = _tckHomeDirectory.getAbsolutePath();

		FileSystem fileSystem = FileSystems.getDefault();

		for (String testClassNamesRelativeGlob :
				testClassNamesRelativeGlobs.split(",")) {

			pathMatchers.add(
				fileSystem.getPathMatcher(
					JenkinsResultsParserUtil.combine(
						"glob:", workingDirectoryPath, "/",
						testClassNamesRelativeGlob)));
		}

		return pathMatchers;
	}

	private final File _tckHomeDirectory;
	private final List<PathMatcher> _testClassNameExcludePathMatchers;
	private final List<PathMatcher> _testClassNameIncludePathMatchers;

}