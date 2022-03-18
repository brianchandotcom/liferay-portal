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
import com.liferay.jenkins.results.parser.PortalAcceptancePullRequestJob;
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;
import com.liferay.jenkins.results.parser.PortalTestClassJob;
import com.liferay.jenkins.results.parser.job.property.JobProperty;
import com.liferay.jenkins.results.parser.test.clazz.JUnitTestClass;
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

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Yi-Chen Tsai
 */
public class JUnitBatchTestClassGroup extends BatchTestClassGroup {

	@Override
	public int getAxisCount() {
		int axisCount = super.getAxisCount();

		if ((axisCount == 0) && _includeAutoBalanceTests) {
			return 1;
		}

		return axisCount;
	}

	public List<JobProperty> getExcludesJobProperties() {
		List<JobProperty> excludesJobProperties = new ArrayList<>();

		excludesJobProperties.addAll(getRequiredExcludesJobProperties());

		if (testReleaseBundle) {
			excludesJobProperties.addAll(getReleaseExcludesJobProperties());
		}
		else if (testRelevantChanges) {
			excludesJobProperties.addAll(getRelevantExcludesJobProperties());
		}
		else {
			excludesJobProperties.addAll(getDefaultExcludesJobProperties());
		}

		if (includeStableTestSuite && isStableTestSuiteBatch()) {
			excludesJobProperties.addAll(
				getStableDefaultExcludesJobProperties());
			excludesJobProperties.addAll(
				getStableRequiredExcludesJobProperties());
		}

		excludesJobProperties.removeAll(Collections.singleton(null));

		recordJobProperties(excludesJobProperties);

		return excludesJobProperties;
	}

	public List<JobProperty> getFilterJobProperties() {
		List<JobProperty> filterJobProperties = new ArrayList<>();

		filterJobProperties.add(
			getJobProperty(
				"test.batch.class.names.filter", JobProperty.Type.FILTER_GLOB));

		recordJobProperties(filterJobProperties);

		return filterJobProperties;
	}

	public List<JobProperty> getIncludesJobProperties() {
		List<JobProperty> includesJobProperties = new ArrayList<>();

		includesJobProperties.addAll(getRequiredIncludesJobProperties());

		if (testReleaseBundle) {
			includesJobProperties.addAll(getReleaseIncludesJobProperties());
		}
		else if (testRelevantChanges) {
			includesJobProperties.addAll(getRelevantIncludesJobProperties());
		}
		else {
			includesJobProperties.addAll(getDefaultIncludesJobProperties());
		}

		if (includeStableTestSuite && isStableTestSuiteBatch()) {
			includesJobProperties.addAll(
				getStableDefaultIncludesJobProperties());
			includesJobProperties.addAll(
				getStableRequiredIncludesJobProperties());
		}

		includesJobProperties.removeAll(Collections.singleton(null));

		recordJobProperties(includesJobProperties);

		return includesJobProperties;
	}

	@Override
	public JSONObject getJSONObject() {
		if (jsonObject != null) {
			return jsonObject;
		}

		jsonObject = super.getJSONObject();

		jsonObject.put("auto_balance_test_files", _autoBalanceTestFiles);
		jsonObject.put("exclude_globs", getGlobs(getExcludesJobProperties()));
		jsonObject.put("filter_globs", getGlobs(getFilterJobProperties()));
		jsonObject.put("include_auto_balance_tests", _includeAutoBalanceTests);
		jsonObject.put("include_globs", getGlobs(getIncludesJobProperties()));
		jsonObject.put(
			"include_unstaged_test_class_files",
			_includeUnstagedTestClassFiles);

		return jsonObject;
	}

	public List<JUnitTestClass> getJUnitTestClasses() {
		List<JUnitTestClass> junitTestClasses = new ArrayList<>();

		for (TestClass testClass : TestClassFactory.getTestClasses()) {
			if (!(testClass instanceof JUnitTestClass)) {
				continue;
			}

			junitTestClasses.add((JUnitTestClass)testClass);
		}

		return junitTestClasses;
	}

	public void writeTestCSVReportFile() throws Exception {
		CSVReport csvReport = new CSVReport(
			new CSVReport.Row(
				"Class Name", "Method Name", "Ignored", "File Path"));

		for (JUnitTestClass jUnitTestClass : getJUnitTestClasses()) {
			File testClassFile = jUnitTestClass.getTestClassFile();

			String testClassFileRelativePath =
				JenkinsResultsParserUtil.getPathRelativeTo(
					testClassFile,
					portalGitWorkingDirectory.getWorkingDirectory());

			String className = testClassFile.getName();

			className = className.replace(".class", "");

			List<TestClassMethod> testClassMethods =
				jUnitTestClass.getTestClassMethods();

			for (TestClassMethod testClassMethod : testClassMethods) {
				CSVReport.Row csvReportRow = new CSVReport.Row();

				csvReportRow.add(className);
				csvReportRow.add(testClassMethod.getName());

				if (testClassMethod.isIgnored()) {
					csvReportRow.add("TRUE");
				}
				else {
					csvReportRow.add("");
				}

				csvReportRow.add(testClassFileRelativePath);

				csvReport.addRow(csvReportRow);
			}
		}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");

		File csvReportFile = new File(
			JenkinsResultsParserUtil.combine(
				"Report_junit_", simpleDateFormat.format(new Date()), ".csv"));

		try {
			JenkinsResultsParserUtil.write(csvReportFile, csvReport.toString());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	protected JUnitBatchTestClassGroup(
		JSONObject jsonObject, PortalTestClassJob portalTestClassJob) {

		super(jsonObject, portalTestClassJob);

		JSONArray autoBalanceTestFilesJSONArray = jsonObject.getJSONArray(
			"auto_balance_test_files");

		if ((autoBalanceTestFilesJSONArray != null) &&
			!autoBalanceTestFilesJSONArray.isEmpty()) {

			for (int i = 0; i < autoBalanceTestFilesJSONArray.length(); i++) {
				String autoBalanceTestFilePath =
					autoBalanceTestFilesJSONArray.getString(i);

				if (JenkinsResultsParserUtil.isNullOrEmpty(
						autoBalanceTestFilePath)) {

					continue;
				}

				_autoBalanceTestFiles.add(new File(autoBalanceTestFilePath));
			}
		}

		_includeAutoBalanceTests = jsonObject.getBoolean(
			"include_auto_balance_tests");
		_includeUnstagedTestClassFiles = jsonObject.getBoolean(
			"include_unstaged_test_class_files");
	}

	protected JUnitBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);

		if (portalTestClassJob instanceof PortalAcceptancePullRequestJob) {
			PortalAcceptancePullRequestJob portalAcceptancePullRequestJob =
				(PortalAcceptancePullRequestJob)portalTestClassJob;

			_includeUnstagedTestClassFiles =
				portalAcceptancePullRequestJob.isCentralMergePullRequest();
		}
		else {
			_includeUnstagedTestClassFiles = false;
		}

		setTestClasses();

		_setAutoBalanceTestFiles();

		_setIncludeAutoBalanceTests();

		setAxisTestClassGroups();

		setSegmentTestClassGroups();
	}

	protected List<JobProperty> getDefaultExcludesJobProperties() {
		List<JobProperty> excludesJobProperties = new ArrayList<>();

		excludesJobProperties.add(
			getJobProperty(
				"test.batch.class.names.excludes",
				JobProperty.Type.EXCLUDE_GLOB));

		return excludesJobProperties;
	}

	protected List<JobProperty> getDefaultIncludesJobProperties() {
		List<JobProperty> includesJobProperties = new ArrayList<>();

		includesJobProperties.add(
			getJobProperty(
				"test.batch.class.names.includes",
				JobProperty.Type.INCLUDE_GLOB));

		return includesJobProperties;
	}

	protected List<JobProperty> getReleaseExcludesJobProperties() {
		List<JobProperty> excludesJobProperties = new ArrayList<>();

		excludesJobProperties.addAll(getDefaultExcludesJobProperties());

		excludesJobProperties.add(
			getJobProperty(
				"test.batch.class.names.excludes.release",
				JobProperty.Type.EXCLUDE_GLOB));

		return excludesJobProperties;
	}

	protected List<JobProperty> getReleaseIncludesJobProperties() {
		return getDefaultIncludesJobProperties();
	}

	protected List<JobProperty> getRelevantExcludesJobProperties() {
		List<JobProperty> excludesJobProperties = new ArrayList<>();

		excludesJobProperties.addAll(getDefaultExcludesJobProperties());

		excludesJobProperties.add(
			getJobProperty(
				"test.batch.class.names.excludes.relevant",
				JobProperty.Type.EXCLUDE_GLOB));

		return excludesJobProperties;
	}

	protected List<JobProperty> getRelevantIncludesJobProperties() {
		List<File> moduleDirsList = null;

		try {
			moduleDirsList = portalGitWorkingDirectory.getModuleDirsList();
		}
		catch (IOException ioException) {
			File workingDirectory =
				portalGitWorkingDirectory.getWorkingDirectory();

			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to get module directories in ",
					workingDirectory.getPath()),
				ioException);
		}

		List<JobProperty> includesJobProperties = new ArrayList<>();

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

			includesJobProperties.addAll(getDefaultIncludesJobProperties());

			break;
		}

		return includesJobProperties;
	}

	protected List<JobProperty> getRequiredExcludesJobProperties() {
		List<JobProperty> excludesJobProperties = new ArrayList<>();

		excludesJobProperties.add(
			getJobProperty(
				"test.batch.class.names.excludes.required",
				JobProperty.Type.EXCLUDE_GLOB));

		return excludesJobProperties;
	}

	protected List<JobProperty> getRequiredIncludesJobProperties() {
		List<JobProperty> includesJobProperties = new ArrayList<>();

		includesJobProperties.add(
			getJobProperty(
				"test.batch.class.names.includes.required",
				JobProperty.Type.INCLUDE_GLOB));

		return includesJobProperties;
	}

	protected List<JobProperty> getStableDefaultExcludesJobProperties() {
		List<JobProperty> excludesJobProperties = new ArrayList<>();

		String batchName = getBatchName();

		if (!batchName.endsWith("_stable")) {
			batchName += "_stable";
		}

		excludesJobProperties.add(
			getJobProperty(
				"test.batch.class.names.excludes", NAME_STABLE_TEST_SUITE,
				batchName, JobProperty.Type.EXCLUDE_GLOB));

		return excludesJobProperties;
	}

	protected List<JobProperty> getStableDefaultIncludesJobProperties() {
		List<JobProperty> includesJobProperties = new ArrayList<>();

		String batchName = getBatchName();

		if (!batchName.endsWith("_stable")) {
			batchName += "_stable";
		}

		includesJobProperties.add(
			getJobProperty(
				"test.batch.class.names.includes", NAME_STABLE_TEST_SUITE,
				batchName, JobProperty.Type.INCLUDE_GLOB));

		return includesJobProperties;
	}

	protected List<JobProperty> getStableRequiredExcludesJobProperties() {
		List<JobProperty> excludesJobProperties = new ArrayList<>();

		String batchName = getBatchName();

		if (!batchName.endsWith("_stable")) {
			batchName += "_stable";
		}

		excludesJobProperties.add(
			getJobProperty(
				"test.batch.class.names.excludes.required",
				NAME_STABLE_TEST_SUITE, batchName,
				JobProperty.Type.EXCLUDE_GLOB));

		return excludesJobProperties;
	}

	protected List<JobProperty> getStableRequiredIncludesJobProperties() {
		List<JobProperty> includesJobProperties = new ArrayList<>();

		String batchName = getBatchName();

		if (!batchName.endsWith("_stable")) {
			batchName += "_stable";
		}

		includesJobProperties.add(
			getJobProperty(
				"test.batch.class.names.includes.required",
				NAME_STABLE_TEST_SUITE, batchName,
				JobProperty.Type.INCLUDE_GLOB));

		return includesJobProperties;
	}

	@Override
	protected void setAxisTestClassGroups() {
		int axisCount = getAxisCount();

		if (axisCount == 0) {
			return;
		}

		int testClassCount = testClasses.size();

		if (testClassCount == 0) {
			if (!_includeAutoBalanceTests) {
				return;
			}

			axisTestClassGroups.add(
				0, TestClassGroupFactory.newAxisTestClassGroup(this));
		}
		else {
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

		if (!_includeAutoBalanceTests) {
			return;
		}

		for (int i = 0; i < axisCount; i++) {
			AxisTestClassGroup axisTestClassGroup = axisTestClassGroups.get(i);

			for (File autoBalanceTestFile : _autoBalanceTestFiles) {
				String filePath = autoBalanceTestFile.getPath();

				filePath = filePath.replace(".class", ".java");

				TestClass testClass = TestClassFactory.newTestClass(
					this, new File(filePath));

				if (!testClass.hasTestClassMethods()) {
					continue;
				}

				axisTestClassGroup.addTestClass(testClass);
			}
		}
	}

	protected void setTestClasses() {
		final List<PathMatcher> includesPathMatchers = getPathMatchers(
			getIncludesJobProperties());

		if (includesPathMatchers.isEmpty()) {
			return;
		}

		final List<PathMatcher> filterPathMatchers = getPathMatchers(
			getFilterJobProperties());
		final List<PathMatcher> excludesPathMatchers = getPathMatchers(
			getExcludesJobProperties());

		final BatchTestClassGroup batchTestClassGroup = this;

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			getPortalGitWorkingDirectory();

		File workingDirectory = portalGitWorkingDirectory.getWorkingDirectory();

		try {
			Files.walkFileTree(
				workingDirectory.toPath(),
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult preVisitDirectory(
							Path filePath,
							BasicFileAttributes basicFileAttributes)
						throws IOException {

						if (JenkinsResultsParserUtil.isFileExcluded(
								excludesPathMatchers, filePath.toFile())) {

							return FileVisitResult.SKIP_SUBTREE;
						}

						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFile(
							Path filePath,
							BasicFileAttributes basicFileAttributes)
						throws IOException {

						if (!JenkinsResultsParserUtil.isFileIncluded(
								excludesPathMatchers, includesPathMatchers,
								filePath) ||
							!JenkinsResultsParserUtil.isFileIncluded(
								null, filterPathMatchers, filePath)) {

							return FileVisitResult.CONTINUE;
						}

						TestClass testClass = TestClassFactory.newTestClass(
							batchTestClassGroup, filePath.toFile());

						if ((testClass != null) && !testClass.isIgnored() &&
							testClass.hasTestClassMethods()) {

							testClasses.add(testClass);
						}

						return FileVisitResult.CONTINUE;
					}

				});
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to search for test file names in " +
					workingDirectory.getPath(),
				ioException);
		}

		Collections.sort(testClasses);
	}

	private void _setAutoBalanceTestFiles() {
		JobProperty jobProperty = getJobProperty(
			"test.class.names.auto.balance");

		String jobPropertyValue = jobProperty.getValue();

		if (JenkinsResultsParserUtil.isNullOrEmpty(jobPropertyValue)) {
			return;
		}

		recordJobProperty(jobProperty);

		for (String autoBalanceTestName : jobPropertyValue.split(",")) {
			String fullClassName = autoBalanceTestName.replaceAll(
				".*\\/?(com\\/.*)\\.(class|java)", "$1");

			fullClassName = fullClassName.replaceAll("/", "\\.");

			File javaTestClassFile =
				portalGitWorkingDirectory.getJavaFileFromFullClassName(
					fullClassName);

			if (!JenkinsResultsParserUtil.isFileIncluded(
					null, getPathMatchers(getFilterJobProperties()),
					javaTestClassFile)) {

				continue;
			}

			_autoBalanceTestFiles.add(javaTestClassFile);
		}
	}

	private void _setIncludeAutoBalanceTests() {
		if (!testClasses.isEmpty()) {
			_includeAutoBalanceTests = true;

			return;
		}

		List<File> modifiedJavaFilesList =
			portalGitWorkingDirectory.getModifiedFilesList(
				_includeUnstagedTestClassFiles, null,
				JenkinsResultsParserUtil.toPathMatchers(
					JenkinsResultsParserUtil.combine(
						"**", File.separator, "*.java")));

		if (!_autoBalanceTestFiles.isEmpty() &&
			!modifiedJavaFilesList.isEmpty()) {

			_includeAutoBalanceTests = true;

			return;
		}

		_includeAutoBalanceTests = false;
	}

	private final List<File> _autoBalanceTestFiles = new ArrayList<>();
	private boolean _includeAutoBalanceTests;
	private final boolean _includeUnstagedTestClassFiles;

}