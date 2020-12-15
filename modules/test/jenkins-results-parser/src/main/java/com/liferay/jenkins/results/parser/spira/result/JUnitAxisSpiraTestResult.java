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

		for (TestResult testClassTestResult :
				testClassResult.getTestResults()) {

			if ((testClassTestResult == null) ||
				!testClassTestResult.isFailing()) {

				continue;
			}

			failedTestResults.add(testClassTestResult);
		}

		return failedTestResults;
	}

	@Override
	public SpiraTestCaseRun.Status getSpiraTestCaseRunStatus() {
		TestClassResult testClassResult = getTestClassResult();

		if (testClassResult != null) {
			if (testClassResult.isFailing()) {
				return SpiraTestCaseRun.Status.FAILED;
			}

			return SpiraTestCaseRun.Status.PASSED;
		}

		return super.getSpiraTestCaseRunStatus();
	}

	public TestClassResult getTestClassResult() {
		if (_testClassResult != null) {
			return _testClassResult;
		}

		AxisBuild axisBuild = getAxisBuild();

		_testClassResult = axisBuild.getTestClassResult(getTestName());

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
	}

	private final TestClassGroup.TestClass _testClass;
	private TestClassResult _testClassResult;

}