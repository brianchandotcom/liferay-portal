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
import com.liferay.jenkins.results.parser.TestResult;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseRun;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class BatchAxisSpiraTestResult extends BaseAxisSpiraTestResult {

	@Override
	public List<TestResult> getFailedTestResults() {
		List<TestResult> failedTestResults = new ArrayList<>();

		Build build = getBuild();

		for (TestResult testResult : build.getTestResults(null)) {
			if (testResult.isFailing() && !isIgnorableTestResult(testResult)) {
				failedTestResults.add(testResult);
			}
		}

		return failedTestResults;
	}

	@Override
	public SpiraTestCaseRun.Status getSpiraTestCaseRunStatus() {
		AxisBuild axisBuild = getAxisBuild();

		String result = axisBuild.getResult();

		if (!result.equals("SUCCESS")) {
			return SpiraTestCaseRun.Status.FAILED;
		}

		return SpiraTestCaseRun.Status.PASSED;
	}

	@Override
	public String getTestName() {
		return getAxisName();
	}

	protected BatchAxisSpiraTestResult(
		SpiraBuildResult spiraBuildResult,
		AxisTestClassGroup axisTestClassGroup) {

		super(spiraBuildResult, axisTestClassGroup);
	}

}