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
import com.liferay.jenkins.results.parser.test.clazz.JSUnitModulesTestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClassMethod;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class JSUnitBatchBuildTestrayCaseResult
	extends BatchBuildTestrayCaseResult {

	public JSUnitBatchBuildTestrayCaseResult(
		TestrayBuild testrayBuild, TopLevelBuild topLevelBuild,
		AxisTestClassGroup axisTestClassGroup,
		TestClassMethod testClassMethod) {

		super(testrayBuild, topLevelBuild, axisTestClassGroup);

		_testClassMethod = testClassMethod;

		_jsUnitModulesTestClass =
			(JSUnitModulesTestClass)testClassMethod.getTestClass();
	}

	@Override
	public String getComponentName() {
		String componentName =
			_jsUnitModulesTestClass.getTestrayMainComponentName();

		if (JenkinsResultsParserUtil.isNullOrEmpty(componentName)) {
			return super.getComponentName();
		}

		return componentName;
	}

	@Override
	public long getDuration() {
		List<TestClassResult> testClassResults = _getTestClassResults();

		if (testClassResults == null) {
			return 0;
		}

		long duration = 0;

		for (TestClassResult testClassResult : testClassResults) {
			duration += testClassResult.getDuration();
		}

		return duration;
	}

	@Override
	public String getErrors() {
		Build build = getBuild();

		List<TestClassResult> testClassResults = _getTestClassResults();

		if ((testClassResults == null) || testClassResults.isEmpty()) {
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

		if (!_isTestClassResultsFailing()) {
			return null;
		}

		Map<String, String> errorMessages = new HashMap<>();

		for (TestClassResult testClassResult : testClassResults) {
			if ((testClassResult == null) || !testClassResult.isFailing()) {
				continue;
			}

			for (TestResult testResult : testClassResult.getTestResults()) {
				if (!testResult.isFailing()) {
					continue;
				}

				String errorMessage = testResult.getErrorDetails();

				if (JenkinsResultsParserUtil.isNullOrEmpty(errorMessage)) {
					errorMessage = build.getFailureMessage();
				}

				if (JenkinsResultsParserUtil.isNullOrEmpty(errorMessage)) {
					errorMessage = "Failed for unknown reason";
				}

				if (errorMessage.contains("\n")) {
					errorMessage = errorMessage.substring(
						0, errorMessage.indexOf("\n"));
				}

				errorMessage = errorMessage.trim();

				if (JenkinsResultsParserUtil.isNullOrEmpty(errorMessage)) {
					errorMessage = "Failed for unknown reason";
				}

				String testName = testResult.getTestName();

				errorMessages.put(
					testName,
					JenkinsResultsParserUtil.combine(
						testName, ": ", errorMessage));
			}
		}

		if (errorMessages.size() > 1) {
			return JenkinsResultsParserUtil.combine(
				"Failed tests: ",
				JenkinsResultsParserUtil.join(
					", ", new ArrayList<>(errorMessages.keySet())));
		}
		else if (errorMessages.size() == 1) {
			List<String> values = new ArrayList<>(errorMessages.values());

			return values.get(0);
		}

		return "Failed for unknown reason";
	}

	@Override
	public String getName() {
		return _testClassMethod.getName();
	}

	@Override
	public Status getStatus() {
		Build build = getBuild();

		if (build == null) {
			return Status.UNTESTED;
		}

		List<TestClassResult> testClassResults = _getTestClassResults();

		if ((testClassResults == null) || testClassResults.isEmpty()) {
			String result = build.getResult();

			if ((result == null) || result.equals("ABORTED") ||
				result.equals("FAILURE") || result.equals("SUCCESS") ||
				result.equals("UNSTABLE")) {

				return Status.UNTESTED;
			}

			return Status.FAILED;
		}

		if (_isTestClassResultsFailing()) {
			return Status.FAILED;
		}

		return Status.PASSED;
	}

	private List<TestClassResult> _getTestClassResults() {
		if (_testClassResults != null) {
			return _testClassResults;
		}

		_testClassResults = new ArrayList<>();

		Build build = getBuild();

		if (build == null) {
			return _testClassResults;
		}

		String taskDirectoryName = getName();

		taskDirectoryName = taskDirectoryName.replace(":packageRunTest", "");

		for (TestClassResult testClassResult : build.getTestClassResults()) {
			String testResultTaskName = _getTestResultTaskName(testClassResult);

			if (testResultTaskName.startsWith(taskDirectoryName)) {
				_testClassResults.add(testClassResult);
			}
		}

		return _testClassResults;
	}

	private String _getTestResultTaskName(TestClassResult testClassResult) {
		String testClassName = testClassResult.getClassName();

		if (testClassName.contains(".modules.")) {
			testClassName = testClassName.replaceAll(
				".*\\.modules(\\..+)", "$1");
		}
		else {
			testClassName = ".apps." + testClassName;
		}

		return testClassName.replaceAll("\\.", ":");
	}

	private boolean _isTestClassResultsFailing() {
		for (TestClassResult testClassResult : _getTestClassResults()) {
			if (testClassResult.isFailing()) {
				return true;
			}
		}

		return false;
	}

	private final JSUnitModulesTestClass _jsUnitModulesTestClass;
	private final TestClassMethod _testClassMethod;
	private List<TestClassResult> _testClassResults;

}