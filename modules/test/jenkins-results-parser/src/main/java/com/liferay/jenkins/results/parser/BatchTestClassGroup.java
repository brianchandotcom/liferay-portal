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

/**
 * @author Michael Hashimoto
 */
public class TestBatchGroup {

	public TestBatchGroup(
		GitWorkingDirectory gitWorkingDirectory, String batchName) {

		_gitWorkingDirectory = gitWorkingDirectory;

		_batchName = batchName;

		_portalTestProperties =
			_gitWorkingDirectory.getGitWorkingDirectoryProperties(
				"test.properties");

		_setTestClassNamesIncludes();
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
		final List<String> list = new ArrayList<>();

		list.addAll(_getTestClassFileNamesSet());

		Collections.sort(list);

		return list;
	}

	public int getTestClassListCount() {
		return 3;
	}

	private Set<String> _getTestClassFileNamesSet() throws Exception {
		File workingDirectory = _gitWorkingDirectory.getWorkingDirectory();

		final Set<String> testClassFileNamesSet = new HashSet<>();

		for (String testClassNamesInclude : _testClassNamesIncludes) {
			final String filePattern =
				workingDirectory.getAbsolutePath() + "/" +
					testClassNamesInclude;

			final PathMatcher pathMatcher =
				FileSystems.getDefault().getPathMatcher("glob:" + filePattern);

			Files.walkFileTree(
				workingDirectory.toPath(),
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult visitFile(
							Path filePath, BasicFileAttributes attrs)
						throws IOException {

						if (filePath.toFile().isDirectory()) {
							visitFile(filePath, attrs);
						}
						else if (pathMatcher.matches(
									filePath.toAbsolutePath())) {

							testClassFileNamesSet.add(filePath.toString());
						}

						return FileVisitResult.CONTINUE;
					}

				});
		}

		return testClassFileNamesSet;
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

	private static final String _TEST_CLASS_NAMES_EXCLUDES_PROPERTY_NAME =
		"test.class.names.excludes";

	private static final String _TEST_CLASS_NAMES_INCLUDES_PROPERTY_NAME =
		"test.class.names.includes";

	private final String _batchName;
	private final GitWorkingDirectory _gitWorkingDirectory;
	private final Properties _portalTestProperties;
	private final List<String> _testClassNamesExcludes = new ArrayList<>();
	private final List<String> _testClassNamesIncludes = new ArrayList<>();

}