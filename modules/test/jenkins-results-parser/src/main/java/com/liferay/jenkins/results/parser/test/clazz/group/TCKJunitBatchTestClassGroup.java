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
import com.liferay.jenkins.results.parser.PortalTestClassJob;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Collections;

/**
 * @author Michael Hashimoto
 */
public class TCKJunitBatchTestClassGroup extends BatchTestClassGroup {

	@Override
	public int getAxisCount() {
		if (!isStableTestSuiteBatch() && testRelevantIntegrationUnitOnly) {
			return 0;
		}

		return super.getAxisCount();
	}

	public static class TCKBatchTestClass extends BaseTestClass {

		protected static TCKBatchTestClass getInstance(
			String batchName, File warFile) {

			return new TCKBatchTestClass(
				batchName,
				new File(JenkinsResultsParserUtil.getCanonicalPath(warFile)));
		}

		protected TCKBatchTestClass(String batchName, File testClassFile) {
			super(testClassFile);

			addTestClassMethod(batchName);
		}

	}

	protected TCKJunitBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);

		_tckHomeDirectory = new File(
			JenkinsResultsParserUtil.getProperty(jobProperties, "tck.home"));

		excludesPathMatchers.addAll(
			getPathMatchers(
				getFirstPropertyValue("test.batch.class.names.excludes"),
				_tckHomeDirectory));

		includesPathMatchers.addAll(
			getPathMatchers(
				getFirstPropertyValue("test.batch.class.names.includes"),
				_tckHomeDirectory));

		if (includeStableTestSuite && isStableTestSuiteBatch()) {
			excludesPathMatchers.addAll(
				getPathMatchers(
					getFirstPropertyValue(
						"test.batch.class.names.excludes", batchName,
						NAME_STABLE_TEST_SUITE),
					_tckHomeDirectory));

			includesPathMatchers.addAll(
				getPathMatchers(
					getFirstPropertyValue(
						"test.batch.class.names.includes", batchName,
						NAME_STABLE_TEST_SUITE),
					_tckHomeDirectory));
		}

		setTestClasses();

		setAxisTestClassGroups();

		setSegmentTestClassGroups();
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

						if (JenkinsResultsParserUtil.isFileExcluded(
								excludesPathMatchers, filePath.toFile())) {

							return FileVisitResult.SKIP_SUBTREE;
						}

						if (JenkinsResultsParserUtil.isFileIncluded(
								excludesPathMatchers, includesPathMatchers,
								filePath.toFile())) {

							testClasses.add(
								TCKBatchTestClass.getInstance(
									batchName, filePath.toFile()));
						}

						return FileVisitResult.CONTINUE;
					}

				});
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to search for test file names in " +
					_tckHomeDirectory.getPath(),
				ioException);
		}

		Collections.sort(testClasses);
	}

	private final File _tckHomeDirectory;

}