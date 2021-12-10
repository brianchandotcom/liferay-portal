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

import com.liferay.jenkins.results.parser.CentralMergePullRequestJob;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;
import com.liferay.jenkins.results.parser.PortalTestClassJob;
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

			String testClassFileRelativePath = _getRelativePath(
				testClassFile, portalGitWorkingDirectory.getWorkingDirectory());

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
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);

		if (portalTestClassJob instanceof CentralMergePullRequestJob) {
			_includeUnstagedTestClassFiles = true;
		}
		else {
			_includeUnstagedTestClassFiles = false;
		}

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			portalTestClassJob.getPortalGitWorkingDirectory();

		_rootWorkingDirectory = portalGitWorkingDirectory.getWorkingDirectory();

		_setAutoBalanceTestFiles();

		setTestClassNamesExcludesRelativeGlobs();
		_setTestClassNamesIncludesRelativeGlobs();

		setTestClasses();

		_setIncludeAutoBalanceTests();

		setAxisTestClassGroups();

		setSegmentTestClassGroups();
	}

	protected List<String> getReleaseTestClassNamesRelativeIncludesGlobs(
		List<String> testClassNamesRelativeIncludesGlobs) {

		return testClassNamesRelativeIncludesGlobs;
	}

	protected List<String> getRelevantTestClassNamesRelativeExcludesGlobs() {
		return new ArrayList();
	}

	protected List<String> getRelevantTestClassNamesRelativeIncludesGlobs(
		List<String> testClassNamesRelativeIncludesGlobs) {

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

		List<String> relevantTestClassNameRelativeIncludesGlobs =
			new ArrayList<>();

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

			relevantTestClassNameRelativeIncludesGlobs.addAll(
				testClassNamesRelativeIncludesGlobs);

			return relevantTestClassNameRelativeIncludesGlobs;
		}

		return relevantTestClassNameRelativeIncludesGlobs;
	}

	protected boolean isValidTestClass(TestClass testClass) {
		return true;
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

				filePath = filePath.replace(".java", ".class");

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
		if (testClassNamesIncludesPathMatchers.isEmpty()) {
			return;
		}

		final BatchTestClassGroup batchTestClassGroup = this;

		try {
			Files.walkFileTree(
				_rootWorkingDirectory.toPath(),
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult preVisitDirectory(
							Path filePath,
							BasicFileAttributes basicFileAttributes)
						throws IOException {

						if (JenkinsResultsParserUtil.isFileExcluded(
								testClassNamesExcludesPathMatchers,
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
								testClassNamesExcludesPathMatchers,
								testClassNamesIncludesPathMatchers,
								filePath.toFile())) {

							TestClass testClass = TestClassFactory.newTestClass(
								batchTestClassGroup, filePath.toFile());

							if (testClass.isIgnored() ||
								!testClass.hasTestClassMethods() ||
								!isValidTestClass(testClass)) {

								return FileVisitResult.CONTINUE;
							}

							testClasses.add(testClass);
						}

						return FileVisitResult.CONTINUE;
					}

				});
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to search for test file names in " +
					_rootWorkingDirectory.getPath(),
				ioException);
		}

		Collections.sort(testClasses);
	}

	protected void setTestClassNamesExcludesRelativeGlobs() {
		String testClassNamesExcludesPropertyValue =
			_getTestClassNamesExcludesPropertyValue(testSuiteName, false);

		List<String> testClassNamesExcludesRelativeGlobs = new ArrayList<>();

		if ((testClassNamesExcludesPropertyValue != null) &&
			!testClassNamesExcludesPropertyValue.isEmpty()) {

			Collections.addAll(
				testClassNamesExcludesRelativeGlobs,
				JenkinsResultsParserUtil.getGlobsFromProperty(
					testClassNamesExcludesPropertyValue));
		}

		if (testRelevantChanges) {
			testClassNamesExcludesRelativeGlobs.addAll(
				getRelevantTestClassNamesRelativeExcludesGlobs());
		}

		if (includeStableTestSuite && isStableTestSuiteBatch()) {
			String stableTestClassNamesExcludesPropertyValue =
				_getTestClassNamesExcludesPropertyValue(
					NAME_STABLE_TEST_SUITE, false);

			if ((stableTestClassNamesExcludesPropertyValue != null) &&
				!stableTestClassNamesExcludesPropertyValue.isEmpty()) {

				Collections.addAll(
					testClassNamesExcludesRelativeGlobs,
					JenkinsResultsParserUtil.getGlobsFromProperty(
						stableTestClassNamesExcludesPropertyValue));
			}
		}

		testClassNamesExcludesPathMatchers.addAll(
			JenkinsResultsParserUtil.toPathMatchers(
				JenkinsResultsParserUtil.combine(
					JenkinsResultsParserUtil.getCanonicalPath(
						_rootWorkingDirectory),
					File.separator),
				testClassNamesExcludesRelativeGlobs.toArray(new String[0])));
	}

	protected final List<PathMatcher> testClassNamesExcludesPathMatchers =
		new ArrayList<>();
	protected final List<PathMatcher> testClassNamesIncludesPathMatchers =
		new ArrayList<>();

	private String _getRelativePath(File file, File parentFile) {
		String filePath = JenkinsResultsParserUtil.getCanonicalPath(file);
		String parentFilePath = JenkinsResultsParserUtil.getCanonicalPath(
			parentFile);

		if (!filePath.startsWith(parentFilePath)) {
			throw new IllegalArgumentException(
				"Working directory does not contain this file");
		}

		return filePath.replaceAll(parentFilePath, "");
	}

	private String _getTestClassNamesExcludesPropertyValue(
		String testSuiteName, boolean useRequiredVariant) {

		String propertyName = "test.batch.class.names.excludes";

		if (useRequiredVariant) {
			propertyName += ".required";
		}

		List<String> propertyValues = new ArrayList<>();

		String propertyValue = getFirstPropertyValue(
			propertyName, batchName, testSuiteName);

		if (propertyValue != null) {
			propertyValues.add(propertyValue);
		}
		else {
			propertyValues.add(
				JenkinsResultsParserUtil.getProperty(
					jobProperties, propertyName));
		}

		if (!testPrivatePortalBranch) {
			propertyValues.add(_GLOB_MODULES_PRIVATE);
		}

		return JenkinsResultsParserUtil.join(",", propertyValues);
	}

	private String _getTestClassNamesIncludesPropertyValue(
		String testSuiteName, boolean useRequiredVariant) {

		return _getTestClassNamesIncludesPropertyValue(
			testSuiteName, useRequiredVariant, null);
	}

	private String _getTestClassNamesIncludesPropertyValue(
		String testSuiteName, boolean useRequiredVariant, String batchName) {

		String propertyName = "test.batch.class.names.includes";

		if (useRequiredVariant) {
			propertyName += ".required";
		}

		if (JenkinsResultsParserUtil.isNullOrEmpty(batchName)) {
			batchName = this.batchName;
		}

		List<String> propertyValues = new ArrayList<>();

		String propertyValue = JenkinsResultsParserUtil.getProperty(
			getJobProperties(), propertyName, testSuiteName, batchName,
			getJobName());

		if (propertyValue != null) {
			propertyValues.add(propertyValue);
		}
		else {
			propertyValues.add(
				JenkinsResultsParserUtil.getProperty(
					jobProperties, propertyName));
		}

		return JenkinsResultsParserUtil.join(",", propertyValues);
	}

	private void _setAutoBalanceTestFiles() {
		String propertyName = "test.class.names.auto.balance";

		String autoBalanceTestNames = getFirstPropertyValue(propertyName);

		if ((autoBalanceTestNames != null) &&
			!autoBalanceTestNames.equals("")) {

			for (String autoBalanceTestName : autoBalanceTestNames.split(",")) {
				_autoBalanceTestFiles.add(new File(autoBalanceTestName));
			}
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

		_includeAutoBalanceTests = _ENABLE_INCLUDE_AUTO_BALANCE_TESTS_DEFAULT;
	}

	private void _setTestClassNamesIncludesRelativeGlobs() {
		String testClassNamesIncludesPropertyValue =
			_getTestClassNamesIncludesPropertyValue(testSuiteName, false);

		if ((testClassNamesIncludesPropertyValue == null) ||
			testClassNamesIncludesPropertyValue.isEmpty()) {

			return;
		}

		List<String> testClassNamesIncludesRelativeGlobs = new ArrayList<>();

		Collections.addAll(
			testClassNamesIncludesRelativeGlobs,
			JenkinsResultsParserUtil.getGlobsFromProperty(
				testClassNamesIncludesPropertyValue));

		if (testReleaseBundle) {
			testClassNamesIncludesRelativeGlobs =
				getReleaseTestClassNamesRelativeIncludesGlobs(
					testClassNamesIncludesRelativeGlobs);
		}
		else if (testRelevantChanges) {
			testClassNamesIncludesRelativeGlobs =
				getRelevantTestClassNamesRelativeIncludesGlobs(
					testClassNamesIncludesRelativeGlobs);
		}

		String testBatchClassNamesIncludesRequiredPropertyValue =
			_getTestClassNamesIncludesPropertyValue(testSuiteName, true);

		if ((testBatchClassNamesIncludesRequiredPropertyValue != null) &&
			!testBatchClassNamesIncludesRequiredPropertyValue.isEmpty()) {

			Collections.addAll(
				testClassNamesIncludesRelativeGlobs,
				JenkinsResultsParserUtil.getGlobsFromProperty(
					testBatchClassNamesIncludesRequiredPropertyValue));
		}

		if (includeStableTestSuite && isStableTestSuiteBatch()) {
			String stableBatchName = batchName;

			if (!batchName.endsWith("_stable")) {
				stableBatchName = stableBatchName + "_stable";
			}

			Collections.addAll(
				testClassNamesIncludesRelativeGlobs,
				JenkinsResultsParserUtil.getGlobsFromProperty(
					_getTestClassNamesIncludesPropertyValue(
						NAME_STABLE_TEST_SUITE, false, stableBatchName)));

			testBatchClassNamesIncludesRequiredPropertyValue =
				_getTestClassNamesIncludesPropertyValue(
					NAME_STABLE_TEST_SUITE, true, stableBatchName);

			if ((testBatchClassNamesIncludesRequiredPropertyValue != null) &&
				!testBatchClassNamesIncludesRequiredPropertyValue.isEmpty()) {

				Collections.addAll(
					testClassNamesIncludesRelativeGlobs,
					JenkinsResultsParserUtil.getGlobsFromProperty(
						testBatchClassNamesIncludesRequiredPropertyValue));
			}
		}

		testClassNamesIncludesPathMatchers.addAll(
			JenkinsResultsParserUtil.toPathMatchers(
				JenkinsResultsParserUtil.combine(
					JenkinsResultsParserUtil.getCanonicalPath(
						_rootWorkingDirectory),
					File.separator),
				testClassNamesIncludesRelativeGlobs.toArray(new String[0])));
	}

	private static final boolean _ENABLE_INCLUDE_AUTO_BALANCE_TESTS_DEFAULT =
		false;

	private static final String _GLOB_MODULES_PRIVATE = "modules/private/**";

	private final List<File> _autoBalanceTestFiles = new ArrayList<>();
	private boolean _includeAutoBalanceTests;
	private final boolean _includeUnstagedTestClassFiles;
	private final File _rootWorkingDirectory;

}