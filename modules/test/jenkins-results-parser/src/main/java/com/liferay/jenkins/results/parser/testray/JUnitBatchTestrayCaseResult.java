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

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TestClassResult;
import com.liferay.jenkins.results.parser.TestResult;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.test.clazz.JUnitTestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class JUnitBatchTestrayCaseResult extends BatchTestrayCaseResult {

	public JUnitBatchTestrayCaseResult(
		TestrayBuild testrayBuild, TopLevelBuild topLevelBuild,
		AxisTestClassGroup axisTestClassGroup, TestClass testClass) {

		super(testrayBuild, topLevelBuild, axisTestClassGroup);

		_jUnitTestClass = (JUnitTestClass)testClass;
	}

	@Override
	public String getComponentName() {
		String componentName = JenkinsResultsParserUtil.getProperty(
			_jUnitTestClass.getTestProperties(), "testray.main.component.name");

		if ((componentName == null) || componentName.isEmpty()) {
			return super.getComponentName();
		}

		return componentName;
	}

	@Override
	public String getErrors() {
		List<TestClassResult> testClassResults = _getTestClassResults();

		if ((testClassResults == null) || testClassResults.isEmpty()) {
			Build build = getBuild();

			if (build == null) {
				return "Failed to run build on CI";
			}

			String result = build.getResult();

			if (result == null) {
				return "Failed to finish build on CI";
			}

			if (result.equals("ABORTED")) {
				return "Aborted prior to running test";
			}

			if (result.equals("SUCCESS") || result.equals("UNSTABLE")) {
				return "Failed to run test on CI";
			}

			return "Failed prior to running test";
		}

		if (!_isTestClassResultsFailing()) {
			return null;
		}

		Map<String, String> errorMessages = new HashMap<>();

		for (TestResult testResult : _getTestResults()) {
			if ((testResult == null) || !testResult.isFailing()) {
				continue;
			}

			String errorMessage = testResult.getErrorDetails();

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

			String testName = testResult.getDisplayName();

			errorMessages.put(
				testName,
				JenkinsResultsParserUtil.combine(testName, ": ", errorMessage));
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
		String testClassName = JenkinsResultsParserUtil.getCanonicalPath(
			_jUnitTestClass.getTestClassFile());

		testClassName = testClassName.replaceAll(".*/(com/.*)\\.java", "$1");

		return testClassName.replace("/", ".");
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

			if ((result == null) || result.equals("SUCCESS") ||
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

		Build build = getBuild();

		if (build == null) {
			return null;
		}

		_testClassResults = new ArrayList<>();

		for (TestClassResult testClassResult : build.getTestClassResults()) {
			String testClassName = testClassResult.getClassName();

			if (testClassName.equals(getName()) ||
				testClassName.startsWith(getName() + "$")) {

				_testClassResults.add(testClassResult);
			}
		}

		return _testClassResults;
	}

	private List<TestResult> _getTestResults() {
		List<TestResult> testResults = new ArrayList<>();

		for (TestClassResult testClassResult : _getTestClassResults()) {
			testResults.addAll(testClassResult.getTestResults());
		}

		return testResults;
	}

	private boolean _isTestClassResultsFailing() {
		for (TestClassResult testClassResult : _getTestClassResults()) {
			if (testClassResult.isFailing()) {
				return true;
			}
		}

		return false;
	}

	private final JUnitTestClass _jUnitTestClass;
	private List<TestClassResult> _testClassResults;

}