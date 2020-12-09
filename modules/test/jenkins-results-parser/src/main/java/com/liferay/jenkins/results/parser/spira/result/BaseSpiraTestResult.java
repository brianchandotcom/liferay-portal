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

import com.google.common.collect.Lists;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.spira.SpiraCustomPropertyValue;
import com.liferay.jenkins.results.parser.spira.SpiraProject;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseFolder;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseObject;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseType;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

import java.io.IOException;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseSpiraTestResult implements SpiraTestResult {

	@Override
	public String getAxisName() {
		return _axisTestClassGroup.getAxisName();
	}

	@Override
	public SpiraBuildResult getSpiraBuildResult() {
		return _spiraBuildResult;
	}

	@Override
	public SpiraTestCaseObject getSpiraTestCaseObject() {
		if (_spiraTestCaseObject != null) {
			return _spiraTestCaseObject;
		}

		StringBuilder sb = new StringBuilder();

		SpiraBuildResult spiraBuildResult = getSpiraBuildResult();

		SpiraTestCaseFolder spiraTestCaseFolder =
			spiraBuildResult.getSpiraTestCaseFolder();

		if (spiraTestCaseFolder != null) {
			sb.append(spiraTestCaseFolder.getPath());
		}

		sb.append("/");
		sb.append(_escapeStringForJSON(getTestName()));

		_spiraTestCaseObject =
			SpiraTestCaseObject.createSpiraTestCaseObjectByPath(
				spiraBuildResult.getSpiraProject(), sb.toString(),
				getSpiraTestCaseType(),
				Lists.<SpiraCustomPropertyValue>newArrayList(
					spiraBuildResult.getSpiraTestCaseProductVersion()));

		return _spiraTestCaseObject;
	}

	@Override
	public SpiraTestCaseType getSpiraTestCaseType() {
		if (_spiraTestCaseType != null) {
			return _spiraTestCaseType;
		}

		SpiraBuildResult spiraBuildResult = getSpiraBuildResult();

		String testCaseTypeName = JenkinsResultsParserUtil.getProperty(
			spiraBuildResult.getPortalTestProperties(),
			"test.batch.spira.test.case.type",
			_axisTestClassGroup.getBatchName());

		if (testCaseTypeName == null) {
			testCaseTypeName = "Batch";
		}

		SpiraProject spiraProject = spiraBuildResult.getSpiraProject();

		_spiraTestCaseType = spiraProject.getSpiraTestCaseTypeByName(
			testCaseTypeName);

		return _spiraTestCaseType;
	}

	@Override
	public void record() {
		long start = System.currentTimeMillis();

		SpiraTestCaseObject spiraTestCaseObject = getSpiraTestCaseObject();

		System.out.println(
			JenkinsResultsParserUtil.combine(
				getAxisName(), " ", spiraTestCaseObject.getPath(), " ",
				spiraTestCaseObject.getURL(), " in ",
				JenkinsResultsParserUtil.toDurationString(
					System.currentTimeMillis() - start)));
	}

	protected BaseSpiraTestResult(
		SpiraBuildResult spiraBuildResult,
		AxisTestClassGroup axisTestClassGroup) {

		_spiraBuildResult = spiraBuildResult;
		_axisTestClassGroup = axisTestClassGroup;
	}

	private String _escapeStringForJSON(String string) {
		if (string == null) {
			return "";
		}

		String maxJSONStringSizeString;

		try {
			maxJSONStringSizeString = JenkinsResultsParserUtil.getBuildProperty(
				"spira.max.json.string.size");
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		Integer maxJSONStringSize = Integer.valueOf(maxJSONStringSizeString);

		if (string.length() > maxJSONStringSize) {
			string = string.substring(0, maxJSONStringSize);
		}

		string = string.replace("/", "\\/");
		string = string.replace("\"", "\\\"");

		return string;
	}

	private final AxisTestClassGroup _axisTestClassGroup;
	private final SpiraBuildResult _spiraBuildResult;
	private SpiraTestCaseObject _spiraTestCaseObject;
	private SpiraTestCaseType _spiraTestCaseType;

}