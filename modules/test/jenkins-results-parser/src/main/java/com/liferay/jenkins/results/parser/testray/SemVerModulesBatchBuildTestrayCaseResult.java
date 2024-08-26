/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TestClassResult;
import com.liferay.jenkins.results.parser.TestResult;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.test.clazz.SemVerModulesTestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kenji Heigel
 */
public class SemVerModulesBatchBuildTestrayCaseResult
	extends BatchBuildTestrayCaseResult {

	public SemVerModulesBatchBuildTestrayCaseResult(
		TestrayBuild testrayBuild, TopLevelBuild topLevelBuild,
		AxisTestClassGroup axisTestClassGroup, TestClass testClass) {

		super(testrayBuild, topLevelBuild, axisTestClassGroup);

		_semVerModulesTestClass = (SemVerModulesTestClass)testClass;
	}

	@Override
	public String getComponentName() {
		String componentName =
			_semVerModulesTestClass.getTestrayMainComponentName();

		if (JenkinsResultsParserUtil.isNullOrEmpty(componentName)) {
			return super.getComponentName();
		}

		return componentName;
	}

	@Override
	public long getDuration() {
		List<TestResult> testResults = getTestResults();

		if (testResults.isEmpty()) {
			return super.getDuration();
		}

		long duration = 0;

		for (TestResult testResult : testResults) {
			duration += testResult.getDuration();
		}

		return duration;
	}

	@Override
	public String getErrors() {
		List<TestResult> testResults = getTestResults();

		Build build = getBuild();

		if (testResults.isEmpty()) {
			if (build == null) {
				return "Unable to run build on CI";
			}

			String result = build.getResult();

			if (result == null) {
				return "Unable to finish build on CI";
			}

			if (result.equals("ABORTED")) {
				return build.getJobName() + " timed out after 2 hours";
			}

			if (result.equals("SUCCESS") || result.equals("UNSTABLE")) {
				return "Unable to run test on CI";
			}

			return "Failed prior to running test";
		}

		StringBuilder sb = new StringBuilder();

		for (TestResult testResult : testResults) {
			if (testResult.isFailing()) {
				sb.append(testResult.getTestName());
				sb.append(": ");
				sb.append(testResult.getErrorDetails());
				sb.append("\n");
			}
		}

		if (sb.length() == 0) {
			return null;
		}

		sb.setLength(sb.length() - 1);

		String errorMessage = sb.toString();

		if (JenkinsResultsParserUtil.isNullOrEmpty(errorMessage)) {
			errorMessage = build.getFailureMessage();
		}

		if (JenkinsResultsParserUtil.isNullOrEmpty(errorMessage)) {
			return "Failed for unknown reason";
		}

		if (errorMessage.contains("\n")) {
			errorMessage = errorMessage.substring(
				0, errorMessage.indexOf("\n"));
		}

		errorMessage = errorMessage.trim();

		if (JenkinsResultsParserUtil.isNullOrEmpty(errorMessage)) {
			return "Failed for unknown reason";
		}

		return errorMessage;
	}

	@Override
	public String getName() {
		if (_semVerModulesTestClass == null) {
			return super.getName();
		}

		return getBatchName() + "[" + _semVerModulesTestClass.getName() + "]";
	}

	@Override
	public Status getStatus() {
		Build build = getBuild();

		if (build == null) {
			return Status.UNTESTED;
		}

		List<TestResult> testResults = getTestResults();

		if (testResults.isEmpty()) {
			String result = build.getResult();

			if ((result == null) || result.equals("SUCCESS") ||
				result.equals("UNSTABLE")) {

				return Status.UNTESTED;
			}

			return Status.FAILED;
		}

		for (TestResult testResult : testResults) {
			if (testResult.isFailing()) {
				return Status.FAILED;
			}
		}

		return Status.PASSED;
	}

	public List<TestResult> getTestResults() {
		if (_testResults != null) {
			return _testResults;
		}

		_testResults = new ArrayList<>();

		Build build = getBuild();

		if (build == null) {
			return _testResults;
		}

		TestClassResult testClassResult = build.getTestClassResult(
			_TEST_CLASS_NAME);

		if (testClassResult == null) {
			return _testResults;
		}

		for (TestResult testResult : testClassResult.getTestResults()) {
			Matcher matcher = _modulePathPattern.matcher(
				testResult.getTestName());

			if (matcher.find()) {
				String modulePath = matcher.group("modulePath");

				if (modulePath.startsWith(_getModulePath())) {
					_testResults.add(testResult);
				}
			}
		}

		return _testResults;
	}

	private String _getModulePath() {
		String modulePath = _semVerModulesTestClass.getName();

		if (modulePath.startsWith("modules")) {
			modulePath = modulePath.substring(7);
		}

		return modulePath;
	}

	private static final String _TEST_CLASS_NAME =
		"com.liferay.semantic.versioning.SemanticVersioningTest";

	private static final Pattern _modulePathPattern = Pattern.compile(
		"testSemanticVersioning\\[(?<modulePath>[\\w\\/-]+)\\]");

	private final SemVerModulesTestClass _semVerModulesTestClass;
	private List<TestResult> _testResults;

}