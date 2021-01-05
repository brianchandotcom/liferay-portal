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
import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TestResult;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.spira.SpiraProject;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseType;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseAxisSpiraTestResult
	extends BaseSpiraTestResult implements AxisSpiraTestResult {

	@Override
	public AxisBuild getAxisBuild() {
		TopLevelBuild topLevelBuild = spiraBuildResult.getTopLevelBuild();

		return topLevelBuild.getDownstreamAxisBuild(getAxisName());
	}

	@Override
	public String getAxisName() {
		return _axisTestClassGroup.getAxisName();
	}

	@Override
	public String getBatchName() {
		return _axisTestClassGroup.getBatchName();
	}

	@Override
	public Build getBuild() {
		return getAxisBuild();
	}

	@Override
	public List<TestResult> getCommonFailedTestResults() {
		List<TestResult> commonFailedTestResults = new ArrayList<>();

		AxisBuild axisBuild = getAxisBuild();

		if (axisBuild == null) {
			return commonFailedTestResults;
		}

		String result = axisBuild.getResult();

		if (!result.equals("SUCCESS") && !result.equals("UNSTABLE")) {
			return commonFailedTestResults;
		}

		for (TestResult testResult : axisBuild.getTestResults(null)) {
			if (testResult.isFailing() && isIgnorableTestResult(testResult)) {
				commonFailedTestResults.add(testResult);
			}
		}

		return commonFailedTestResults;
	}

	@Override
	public SpiraTestCaseType getSpiraTestCaseType() {
		if (_spiraTestCaseType != null) {
			return _spiraTestCaseType;
		}

		SpiraBuildResult spiraBuildResult = getSpiraBuildResult();

		String testCaseTypeName = JenkinsResultsParserUtil.getProperty(
			spiraBuildResult.getPortalTestProperties(),
			"test.batch.spira.test.case.type", getBatchName());

		if (testCaseTypeName == null) {
			testCaseTypeName = "Batch";
		}

		SpiraProject spiraProject = spiraBuildResult.getSpiraProject();

		_spiraTestCaseType = spiraProject.getSpiraTestCaseTypeByName(
			testCaseTypeName);

		return _spiraTestCaseType;
	}

	protected BaseAxisSpiraTestResult(
		SpiraBuildResult spiraBuildResult,
		AxisTestClassGroup axisTestClassGroup) {

		super(spiraBuildResult);

		_axisTestClassGroup = axisTestClassGroup;
	}

	protected boolean isIgnorableTestResult(TestResult testResult) {
		if (testResult == null) {
			return true;
		}

		String packageName = testResult.getPackageName();

		if (!packageName.startsWith("com.liferay")) {
			return true;
		}

		if (packageName.matches(
				"com\\.liferay\\.(jenkins|portal\\.log\\.assertor)")) {

			return true;
		}

		String testName = testResult.getTestName();

		if (packageName.equals("com.liferay.poshi.runner") &&
			testName.equals("initializationError")) {

			return true;
		}

		return false;
	}

	protected boolean isSpiraPropertyUpdateEnabled() {
		SpiraBuildResult spiraBuildResult = getSpiraBuildResult();

		TopLevelBuild topLevelBuild = spiraBuildResult.getTopLevelBuild();

		String spiraPropertyUpdate = JenkinsResultsParserUtil.getProperty(
			spiraBuildResult.getPortalTestProperties(),
			"test.batch.spira.property.update", topLevelBuild.getJobName(),
			topLevelBuild.getTestSuiteName());

		if ((spiraPropertyUpdate != null) &&
			spiraPropertyUpdate.equals("true")) {

			return true;
		}

		return false;
	}

	private final AxisTestClassGroup _axisTestClassGroup;
	private SpiraTestCaseType _spiraTestCaseType;

}