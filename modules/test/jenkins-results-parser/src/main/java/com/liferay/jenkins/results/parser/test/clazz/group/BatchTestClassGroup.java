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
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;
import com.liferay.jenkins.results.parser.PortalTestClassJob;
import com.liferay.jenkins.results.parser.TestSuiteJob;

import java.io.File;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public abstract class BatchTestClassGroup extends BaseTestClassGroup {

	public int getAxisCount() {
		String axisCount = getFirstPropertyValue("test.batch.axis.count");

		if (axisCount != null) {
			return Integer.parseInt(axisCount);
		}

		int testClassCount = testClasses.size();

		if (testClassCount == 0) {
			return 0;
		}

		int axisMaxSize = getAxisMaxSize();

		if (axisMaxSize <= 0) {
			throw new RuntimeException(
				"'test.batch.axis.max.size' cannot be 0 or less");
		}

		return (int)Math.ceil((double)testClassCount / axisMaxSize);
	}

	public AxisTestClassGroup getAxisTestClassGroup(int axisId) {
		return axisTestClassGroups.get(axisId);
	}

	public String getBatchName() {
		return batchName;
	}

	public PortalGitWorkingDirectory getPortalGitWorkingDirectory() {
		return portalGitWorkingDirectory;
	}

	public static class BatchTestClass extends BaseTestClass {

		protected static BatchTestClass getInstance(
			String batchName,
			PortalGitWorkingDirectory portalGitWorkingDirectory) {

			File file = new File(
				portalGitWorkingDirectory.getWorkingDirectory(),
				"build-test-batch.xml");

			return new BatchTestClass(batchName, file);
		}

		protected BatchTestClass(String batchName, File file) {
			super(file);

			addTestMethod(batchName);
		}

	}

	protected BatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		this.batchName = batchName;

		portalGitWorkingDirectory =
			portalTestClassJob.getPortalGitWorkingDirectory();

		if (portalTestClassJob instanceof TestSuiteJob) {
			TestSuiteJob testSuiteJob = (TestSuiteJob)portalTestClassJob;

			testSuiteName = testSuiteJob.getTestSuiteName();
		}
		else {
			testSuiteName = null;
		}

		jobProperties = portalTestClassJob.getJobProperties();

		_setTestReleaseBundle();
		_setTestRelevantChanges();
	}

	protected int getAxisMaxSize() {
		String axisMaxSize = getFirstPropertyValue("test.batch.axis.max.size");

		if (axisMaxSize != null) {
			return Integer.parseInt(axisMaxSize);
		}

		return _DEFAULT_AXIS_MAX_SIZE;
	}

	protected String getFirstMatchingPropertyName(
		String basePropertyName, Properties properties) {

		return getFirstMatchingPropertyName(basePropertyName, properties, null);
	}

	protected String getFirstMatchingPropertyName(
		String basePropertyName, Properties properties, String testSuiteName) {

		if (basePropertyName.contains("[") || basePropertyName.contains("]")) {
			throw new RuntimeException(
				"Invalid base property name " + basePropertyName);
		}

		Pattern pattern = Pattern.compile(
			JenkinsResultsParserUtil.combine(
				basePropertyName, "\\[(?<batchName>[^\\]]+)\\]",
				"(\\[(?<testSuiteName>[^\\]]+)\\])?"));

		for (String propertyName : properties.stringPropertyNames()) {
			if (!propertyName.startsWith(basePropertyName)) {
				continue;
			}

			Matcher matcher = pattern.matcher(propertyName);

			if (matcher.find()) {
				String batchNameRegex = matcher.group("batchName");

				batchNameRegex = batchNameRegex.replace("*", ".+");

				if (!batchName.matches(batchNameRegex)) {
					continue;
				}

				String targetTestSuiteName = matcher.group("testSuiteName");

				if (Objects.equals(testSuiteName, targetTestSuiteName)) {
					return propertyName;
				}

				continue;
			}
		}

		return null;
	}

	protected String getFirstPropertyValue(String basePropertyName) {
		if (basePropertyName.contains("[") || basePropertyName.contains("]")) {
			throw new RuntimeException(
				"Invalid base property name " + basePropertyName);
		}

		List<String> propertyNames = new ArrayList<>();

		if (testSuiteName != null) {
			propertyNames.add(
				JenkinsResultsParserUtil.combine(
					basePropertyName, "[", batchName, "][", testSuiteName,
					"]"));

			propertyNames.add(
				getFirstMatchingPropertyName(
					basePropertyName, jobProperties, testSuiteName));

			propertyNames.add(
				JenkinsResultsParserUtil.combine(
					basePropertyName, "[", testSuiteName, "]"));
		}

		propertyNames.add(
			JenkinsResultsParserUtil.combine(
				basePropertyName, "[", batchName, "]"));

		propertyNames.add(
			getFirstMatchingPropertyName(basePropertyName, jobProperties));

		propertyNames.add(basePropertyName);

		for (String propertyName : propertyNames) {
			if (propertyName == null) {
				continue;
			}

			if (jobProperties.containsKey(propertyName)) {
				String propertyValue = JenkinsResultsParserUtil.getProperty(
					jobProperties, propertyName);

				if ((propertyValue != null) && !propertyValue.isEmpty()) {
					return propertyValue;
				}
			}
		}

		return null;
	}

	protected List<PathMatcher> getPathMatchers(
		String relativeGlobs, File workingDirectory) {

		List<PathMatcher> pathMatchers = new ArrayList<>();

		if ((relativeGlobs == null) || relativeGlobs.isEmpty()) {
			return pathMatchers;
		}

		for (String relativeGlob : relativeGlobs.split(",")) {
			FileSystem fileSystem = FileSystems.getDefault();

			pathMatchers.add(
				fileSystem.getPathMatcher(
					JenkinsResultsParserUtil.combine(
						"glob:", workingDirectory.getAbsolutePath(), "/",
						relativeGlob)));
		}

		return pathMatchers;
	}

	protected void setAxisTestClassGroups() {
		int testClassCount = testClasses.size();

		if (testClassCount == 0) {
			return;
		}

		int axisCount = getAxisCount();

		int axisSize = (int)Math.ceil((double)testClassCount / axisCount);

		int id = 0;

		for (List<BaseTestClass> axisTestClasses :
				Lists.partition(testClasses, axisSize)) {

			AxisTestClassGroup axisTestClassGroup = new AxisTestClassGroup(
				this, id);

			for (BaseTestClass axisTestClass : axisTestClasses) {
				axisTestClassGroup.addTestClass(axisTestClass);
			}

			axisTestClassGroups.put(id, axisTestClassGroup);

			id++;
		}
	}

	protected final Map<Integer, AxisTestClassGroup> axisTestClassGroups =
		new HashMap<>();
	protected final String batchName;
	protected final List<PathMatcher> excludesPathMatchers = new ArrayList<>();
	protected final List<PathMatcher> includesPathMatchers = new ArrayList<>();
	protected final Properties jobProperties;
	protected final PortalGitWorkingDirectory portalGitWorkingDirectory;
	protected boolean testReleaseBundle;
	protected boolean testRelevantChanges;
	protected final String testSuiteName;

	private void _setTestReleaseBundle() {
		String propertyValue = getFirstPropertyValue("test.release.bundle");

		if (propertyValue != null) {
			testReleaseBundle = Boolean.parseBoolean(propertyValue);

			return;
		}

		testReleaseBundle = _DEFAULT_TEST_RELEASE_BUNDLE;
	}

	private void _setTestRelevantChanges() {
		String propertyValue = getFirstPropertyValue("test.relevant.changes");

		if (propertyValue != null) {
			testRelevantChanges = Boolean.parseBoolean(propertyValue);

			return;
		}

		testRelevantChanges = _DEFAULT_TEST_RELEVANT_CHANGES;
	}

	private static final int _DEFAULT_AXIS_MAX_SIZE = 5000;

	private static final boolean _DEFAULT_TEST_RELEASE_BUNDLE = false;

	private static final boolean _DEFAULT_TEST_RELEVANT_CHANGES = false;

}