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

package com.liferay.jenkins.results.parser.spira.result;

import com.liferay.jenkins.results.parser.AxisBuild;
import com.liferay.jenkins.results.parser.TestClassResult;
import com.liferay.jenkins.results.parser.TestResult;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseRun;
import com.liferay.jenkins.results.parser.test.clazz.group.JUnitAxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.TestClassGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class JUnitAxisSpiraTestResult extends BaseAxisSpiraTestResult {

	@Override
	public Integer getDuration() {
		TestClassResult testClassResult = getTestClassResult();

		if (testClassResult != null) {
			return (int)testClassResult.getDuration();
		}

		return super.getDuration();
	}

	@Override
	public List<TestResult> getFailedTestResults() {
		List<TestResult> failedTestResults = new ArrayList<>();

		TestClassResult testClassResult = getTestClassResult();

		if (testClassResult == null) {
			return failedTestResults;
		}

		List<TestResult> testResults = testClassResult.getTestResults();

		if ((testResults == null) || testResults.isEmpty()) {
			return failedTestResults;
		}

		for (TestResult testResult : testResults) {
			if ((testResult == null) || !testResult.isFailing()) {
				continue;
			}

			failedTestResults.add(testResult);
		}

		return failedTestResults;
	}

	@Override
	public SpiraTestCaseRun.Status getSpiraTestCaseRunStatus() {
		TestClassResult testClassResult = getTestClassResult();

		if ((testClassResult == null) || testClassResult.isFailing()) {
			return SpiraTestCaseRun.Status.FAILED;
		}

		return SpiraTestCaseRun.Status.PASSED;
	}

	public TestClassGroup.TestClass getTestClass() {
		return _testClass;
	}

	public TestClassResult getTestClassResult() {
		return _testClassResult;
	}

	@Override
	public String getTestName() {
		String testName = String.valueOf(_testClass.getTestClassFile());

		testName = testName.replaceAll(".*(com/.*)", "$1");

		testName = testName.replaceAll("\\/", ".");
		testName = testName.replaceAll(".class", "");

		return testName;
	}

	protected JUnitAxisSpiraTestResult(
		SpiraBuildResult spiraBuildResult,
		JUnitAxisTestClassGroup jUnitAxisTestClassGroup,
		TestClassGroup.TestClass testClass) {

		super(spiraBuildResult, jUnitAxisTestClassGroup);

		_testClass = testClass;

		TestClassResult testClassResult = null;

		AxisBuild axisBuild = getAxisBuild();

		if (axisBuild != null) {
			try {
				testClassResult = axisBuild.getTestClassResult(getTestName());
			}
			catch (Exception exception) {
				exception.printStackTrace();
			}
		}

		_testClassResult = testClassResult;
	}

	private final TestClassGroup.TestClass _testClass;
	private final TestClassResult _testClassResult;

}