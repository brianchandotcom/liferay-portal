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

import com.google.common.collect.Lists;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.RootCauseAnalysisToolJob;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClassFactory;
import com.liferay.jenkins.results.parser.test.clazz.TestClassMethod;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class JUnitRCABatchTestClassGroup extends RCABatchTestClassGroup {

	protected JUnitRCABatchTestClassGroup(
		JSONObject jsonObject,
		RootCauseAnalysisToolJob rootCauseAnalysisToolJob) {

		super(jsonObject, rootCauseAnalysisToolJob);
	}

	protected JUnitRCABatchTestClassGroup(
		String batchName, RootCauseAnalysisToolJob rootCauseAnalysisToolJob) {

		super(batchName, rootCauseAnalysisToolJob);

		_setPathMatchers();

		_setTestClasses();

		setAxisTestClassGroups();

		setSegmentTestClassGroups();
	}

	@Override
	protected void setAxisTestClassGroups() {
		int axisCount = getAxisCount();

		if (axisCount == 0) {
			return;
		}

		int testClassCount = testClasses.size();

		if (testClassCount == 0) {
			axisTestClassGroups.add(
				0, TestClassGroupFactory.newAxisTestClassGroup(this));

			return;
		}

		int axisSize = (int)Math.ceil((double)testClassCount / axisCount);

		for (List<TestClass> axisTestClasses :
				Lists.partition(testClasses, axisSize)) {

			AxisTestClassGroup axisTestClassGroup =
				TestClassGroupFactory.newAxisTestClassGroup(this);

			for (TestClass axisTestClass : axisTestClasses) {
				axisTestClassGroup.addTestClass(axisTestClass);
			}

			axisTestClassGroups.add(axisTestClassGroup);
		}
	}

	private void _setPathMatchers() {
		List<String> includeGlobs = new ArrayList<>();

		String portalBatchTestSelector = System.getenv(
			"PORTAL_BATCH_TEST_SELECTOR");

		if (JenkinsResultsParserUtil.isNullOrEmpty(portalBatchTestSelector)) {
			portalBatchTestSelector = getBuildStartProperty(
				"PORTAL_BATCH_TEST_SELECTOR");
		}

		if (!JenkinsResultsParserUtil.isNullOrEmpty(portalBatchTestSelector)) {
			Collections.addAll(
				includeGlobs,
				JenkinsResultsParserUtil.getGlobsFromProperty(
					portalBatchTestSelector));
		}

		_pathMatchers.addAll(
			JenkinsResultsParserUtil.toPathMatchers(
				JenkinsResultsParserUtil.combine(
					JenkinsResultsParserUtil.getCanonicalPath(
						portalGitWorkingDirectory.getWorkingDirectory()),
					File.separator),
				includeGlobs.toArray(new String[0])));
	}

	private void _setTestClasses() {
		final BatchTestClassGroup batchTestClassGroup = this;

		File portalWorkingDirectory =
			portalGitWorkingDirectory.getWorkingDirectory();

		try {
			Files.walkFileTree(
				portalWorkingDirectory.toPath(),
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult preVisitDirectory(
							Path filePath,
							BasicFileAttributes basicFileAttributes)
						throws IOException {

						if (JenkinsResultsParserUtil.isFileExcluded(
								new ArrayList<PathMatcher>(),
								filePath.toFile())) {

							return FileVisitResult.SKIP_SUBTREE;
						}

						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFile(
							Path filePath,
							BasicFileAttributes basicFileAttributes)
						throws IOException {

						if (JenkinsResultsParserUtil.isFileIncluded(
								new ArrayList<PathMatcher>(), _pathMatchers,
								filePath.toFile())) {

							TestClass testClass = _getPackagePathClassFile(
								filePath);

							List<TestClassMethod> testClassMethods =
								testClass.getTestClassMethods();

							if (!testClassMethods.isEmpty()) {
								testClasses.add(testClass);
							}
						}

						return FileVisitResult.CONTINUE;
					}

					private TestClass _getPackagePathClassFile(Path path) {
						return TestClassFactory.newTestClass(
							batchTestClassGroup, path.toFile());
					}

				});
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to search for test file names in " +
					portalWorkingDirectory.getPath(),
				ioException);
		}

		Collections.sort(testClasses);
	}

	private final List<PathMatcher> _pathMatchers = new ArrayList<>();

}