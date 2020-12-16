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
import com.liferay.jenkins.results.parser.TestResult;
import com.liferay.jenkins.results.parser.spira.SpiraCustomProperty;
import com.liferay.jenkins.results.parser.spira.SpiraCustomPropertyValue;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseRun;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseType;

import java.util.Collections;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseAxisSpiraTestResultValues
	extends BaseSpiraTestResultValues {

	@Override
	public List<SpiraCustomPropertyValue> getSpiraCustomPropertyValues() {
		List<SpiraCustomPropertyValue> spiraCustomPropertyValues =
			super.getSpiraCustomPropertyValues();

		spiraCustomPropertyValues.add(_getBatchNameValue());
		spiraCustomPropertyValues.add(_getTestTypeValue());
		spiraCustomPropertyValues.add(_getWarningsValue());

		spiraCustomPropertyValues.removeAll(Collections.singleton(null));

		return spiraCustomPropertyValues;
	}

	protected BaseAxisSpiraTestResultValues(
		AxisSpiraTestResult axisSpiraTestResult) {

		super(axisSpiraTestResult);

		_axisSpiraTestResult = axisSpiraTestResult;
	}

	private SpiraCustomPropertyValue _getBatchNameValue() {
		String batchName = _axisSpiraTestResult.getBatchName();

		if ((batchName == null) || !batchName.isEmpty()) {
			return null;
		}

		SpiraBuildResult spiraBuildResult = getSpiraBuildResult();

		SpiraCustomProperty spiraCustomProperty =
			SpiraCustomProperty.createSpiraCustomProperty(
				spiraBuildResult.getSpiraProject(), SpiraTestCaseRun.class,
				"Batch Name", SpiraCustomProperty.Type.TEXT);

		return SpiraCustomPropertyValue.createSpiraCustomPropertyValue(
			spiraCustomProperty, batchName);
	}

	private SpiraCustomPropertyValue _getTestTypeValue() {
		SpiraBuildResult spiraBuildResult = getSpiraBuildResult();

		SpiraCustomProperty spiraCustomProperty =
			SpiraCustomProperty.createSpiraCustomProperty(
				spiraBuildResult.getSpiraProject(), SpiraTestCaseRun.class,
				"Test Type", SpiraCustomProperty.Type.MULTILIST);

		SpiraTestCaseType spiraTestCaseType =
			_axisSpiraTestResult.getSpiraTestCaseType();

		return SpiraCustomPropertyValue.createSpiraCustomPropertyValue(
			spiraCustomProperty, spiraTestCaseType.getName());
	}

	private SpiraCustomPropertyValue _getWarningsValue() {
		int warnings = 0;

		AxisBuild axisBuild = _axisSpiraTestResult.getAxisBuild();

		if (axisBuild != null) {
			List<String> warningMessages = axisBuild.getWarningMessages();

			warnings = warningMessages.size();
		}

		List<TestResult> commonFailedTestResults =
			_axisSpiraTestResult.getCommonFailedTestResults();

		if (commonFailedTestResults != null) {
			warnings += commonFailedTestResults.size();
		}

		SpiraBuildResult spiraBuildResult =
			_axisSpiraTestResult.getSpiraBuildResult();

		SpiraCustomProperty spiraCustomProperty =
			SpiraCustomProperty.createSpiraCustomProperty(
				spiraBuildResult.getSpiraProject(), SpiraTestCaseRun.class,
				"Warnings", SpiraCustomProperty.Type.TEXT, true);

		return SpiraCustomPropertyValue.createSpiraCustomPropertyValue(
			spiraCustomProperty, String.valueOf(warnings));
	}

	private final AxisSpiraTestResult _axisSpiraTestResult;

}