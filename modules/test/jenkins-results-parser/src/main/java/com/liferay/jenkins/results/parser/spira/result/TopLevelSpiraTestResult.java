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

import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.spira.SpiraProject;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseType;

/**
 * @author Michael Hashimoto
 */
public class TopLevelSpiraTestResult extends BaseSpiraTestResult {

	@Override
	public Build getBuild() {
		return spiraBuildResult.getTopLevelBuild();
	}

	@Override
	public SpiraTestCaseType getSpiraTestCaseType() {
		if (_spiraTestCaseType != null) {
			return _spiraTestCaseType;
		}

		SpiraBuildResult spiraBuildResult = getSpiraBuildResult();

		String testCaseTypeName = JenkinsResultsParserUtil.getProperty(
			spiraBuildResult.getPortalTestProperties(),
			"test.batch.spira.test.case.type");

		if (testCaseTypeName == null) {
			testCaseTypeName = "Batch";
		}

		SpiraProject spiraProject = spiraBuildResult.getSpiraProject();

		_spiraTestCaseType = spiraProject.getSpiraTestCaseTypeByName(
			testCaseTypeName);

		return _spiraTestCaseType;
	}

	@Override
	public String getTestName() {
		return "top-level-job";
	}

	protected TopLevelSpiraTestResult(SpiraBuildResult spiraBuildResult) {
		super(spiraBuildResult);
	}

	private SpiraTestCaseType _spiraTestCaseType;

}