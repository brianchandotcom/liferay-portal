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
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TestResult;
import com.liferay.jenkins.results.parser.spira.BaseSpiraArtifact;
import com.liferay.jenkins.results.parser.spira.SpiraCustomProperty;
import com.liferay.jenkins.results.parser.spira.SpiraCustomPropertyValue;
import com.liferay.jenkins.results.parser.spira.SpiraProject;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseRun;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

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

		spiraCustomPropertyValues.addAll(getBatchCustomPropertyValues());

		spiraCustomPropertyValues.removeAll(Collections.singleton(null));

		return spiraCustomPropertyValues;
	}

	protected BaseAxisSpiraTestResultValues(
		AxisSpiraTestResult axisSpiraTestResult) {

		super(axisSpiraTestResult);

		_axisSpiraTestResult = axisSpiraTestResult;
	}

	protected List<SpiraCustomPropertyValue> getBatchCustomPropertyValues() {
		List<SpiraCustomPropertyValue> spiraCustomPropertyValues =
			new ArrayList<>();

		SpiraBuildResult spiraBuildResult = getSpiraBuildResult();

		SpiraProject spiraProject = spiraBuildResult.getSpiraProject();

		for (String batchPropertyType : getBatchPropertyTypes()) {
			String propertyName = getBatchPropertyName(batchPropertyType);

			if ((propertyName == null) || propertyName.isEmpty()) {
				continue;
			}

			String propertyValue = getBatchPropertyValue(batchPropertyType);

			if ((propertyValue == null) || propertyValue.isEmpty()) {
				continue;
			}

			SpiraCustomProperty spiraCustomProperty =
				SpiraCustomProperty.createSpiraCustomProperty(
					spiraProject, SpiraTestCaseRun.class, propertyName,
					SpiraCustomProperty.Type.MULTILIST);

			for (String propertyValueItem : propertyValue.split(",")) {
				spiraCustomPropertyValues.add(
					SpiraCustomPropertyValue.createSpiraCustomPropertyValue(
						spiraCustomProperty, propertyValueItem));
			}
		}

		return spiraCustomPropertyValues;
	}

	protected String getBatchPropertyName(String batchPropertyType) {
		SpiraBuildResult spiraBuildResult = getSpiraBuildResult();

		return JenkinsResultsParserUtil.getProperty(
			spiraBuildResult.getPortalTestProperties(),
			"test.batch.spira.property.name", batchPropertyType);
	}

	protected List<String> getBatchPropertyTypes() {
		SpiraBuildResult spiraBuildResult = getSpiraBuildResult();

		String spiraPropertyTypes = JenkinsResultsParserUtil.getProperty(
			spiraBuildResult.getPortalTestProperties(),
			"test.batch.spira.property.types",
			BaseSpiraArtifact.getArtifactTypeName(SpiraTestCaseRun.class));

		return Arrays.asList(spiraPropertyTypes.split(","));
	}

	protected String getBatchPropertyValue(String batchPropertyType) {
		String batchName = _axisSpiraTestResult.getBatchName();

		if ((batchName == null) || batchName.isEmpty()) {
			return null;
		}

		SpiraBuildResult spiraBuildResult = getSpiraBuildResult();

		Properties portalTestProperties =
			spiraBuildResult.getPortalTestProperties();

		String batchPropertyName = JenkinsResultsParserUtil.combine(
			"test.batch.spira.property.value[", batchPropertyType, "]");

		Set<String> propertyNameOpts = new HashSet<>();

		for (Object portalTestPropertyName : portalTestProperties.keySet()) {
			if (!(portalTestPropertyName instanceof String)) {
				continue;
			}

			String propertyName = (String)portalTestPropertyName;

			String propertyNameRegex =
				Pattern.quote(batchPropertyName) + "\\[([^\\]]+)\\]";

			if (!propertyName.matches(propertyNameRegex)) {
				continue;
			}

			String propertyNameOpt = propertyName.replaceAll(
				propertyNameRegex, "$1");

			if (batchName.contains(propertyNameOpt)) {
				propertyNameOpts.add(propertyNameOpt);
			}
		}

		if (propertyNameOpts.isEmpty()) {
			return null;
		}

		String batchPropertyNameOpt = "";

		for (String propertyNameOpt : propertyNameOpts) {
			if (batchPropertyNameOpt.length() < propertyNameOpt.length()) {
				batchPropertyNameOpt = propertyNameOpt;
			}
		}

		return JenkinsResultsParserUtil.getProperty(
			portalTestProperties,
			JenkinsResultsParserUtil.combine(
				batchPropertyName, "[", batchPropertyNameOpt, "]"));
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