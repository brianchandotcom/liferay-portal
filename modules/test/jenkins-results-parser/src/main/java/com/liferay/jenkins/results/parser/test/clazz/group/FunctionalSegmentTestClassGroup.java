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

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.job.property.JobProperty;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class FunctionalSegmentTestClassGroup extends SegmentTestClassGroup {

	public FunctionalAxisTestClassGroup getFunctionalAxisTestClassGroup(
		int segmentIndex) {

		List<FunctionalAxisTestClassGroup> functionalAxisTestClassGroups =
			getFunctionalAxisTestClassGroups();

		return functionalAxisTestClassGroups.get(segmentIndex);
	}

	public List<FunctionalAxisTestClassGroup>
		getFunctionalAxisTestClassGroups() {

		List<FunctionalAxisTestClassGroup> functionalAxisTestClassGroups =
			new ArrayList<>();

		for (AxisTestClassGroup axisTestClassGroup : getAxisTestClassGroups()) {
			if (!(axisTestClassGroup instanceof FunctionalAxisTestClassGroup)) {
				continue;
			}

			FunctionalAxisTestClassGroup functionalAxisTestClassGroup =
				(FunctionalAxisTestClassGroup)axisTestClassGroup;

			functionalAxisTestClassGroups.add(functionalAxisTestClassGroup);
		}

		return functionalAxisTestClassGroups;
	}

	@Override
	public Integer getMinimumSlaveRAM() {
		Properties poshiProperties = getPoshiProperties();

		String minimumSlaveRAM = poshiProperties.getProperty(
			"minimum.slave.ram");

		if ((minimumSlaveRAM != null) && minimumSlaveRAM.matches("\\d+")) {
			return Integer.valueOf(minimumSlaveRAM);
		}

		return super.getMinimumSlaveRAM();
	}

	public Properties getPoshiProperties() {
		List<FunctionalAxisTestClassGroup> functionalAxisTestClassGroups =
			getFunctionalAxisTestClassGroups();

		FunctionalAxisTestClassGroup functionalAxisTestClassGroup =
			functionalAxisTestClassGroups.get(0);

		return functionalAxisTestClassGroup.getPoshiProperties();
	}

	@Override
	public String getSlaveLabel() {
		Properties poshiProperties = getPoshiProperties();

		String slaveLabel = poshiProperties.getProperty("slave.label");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(slaveLabel)) {
			return slaveLabel;
		}

		return super.getSlaveLabel();
	}

	@Override
	public String getTestCasePropertiesContent() {
		StringBuilder sb = new StringBuilder();

		sb.append(super.getTestCasePropertiesContent());

		List<String> axisGroupNames = new ArrayList<>();

		int batchIndex = getBatchIndex();

		for (int axisIndex = 0; axisIndex < getAxisCount(); axisIndex++) {
			axisGroupNames.add(batchIndex + "_" + axisIndex);

			sb.append("RUN_TEST_CASE_METHOD_GROUP_");
			sb.append(batchIndex);
			sb.append("_");
			sb.append(axisIndex);
			sb.append("=");

			FunctionalAxisTestClassGroup functionalAxisTestClassGroup =
				getFunctionalAxisTestClassGroup(axisIndex);

			sb.append(
				JenkinsResultsParserUtil.join(
					",",
					functionalAxisTestClassGroup.getTestClassMethodNames()));

			sb.append("\n");
		}

		sb.append("RUN_TEST_CASE_METHOD_GROUP_");
		sb.append(batchIndex);
		sb.append("=");
		sb.append(JenkinsResultsParserUtil.join(" ", axisGroupNames));
		sb.append("\n");

		return sb.toString();
	}

	protected FunctionalSegmentTestClassGroup(
		BatchTestClassGroup batchTestClassGroup) {

		super(batchTestClassGroup);

		_batchTestClassGroup = batchTestClassGroup;
	}

	protected FunctionalSegmentTestClassGroup(
		BatchTestClassGroup batchTestClassGroup, JSONObject jsonObject) {

		super(batchTestClassGroup, jsonObject);

		_batchTestClassGroup = batchTestClassGroup;
	}

	protected Map.Entry<String, String> getEnvironmentVariableEntry(
		String key, String name) {

		if (JenkinsResultsParserUtil.isNullOrEmpty(key) ||
			JenkinsResultsParserUtil.isNullOrEmpty(name)) {

			return null;
		}

		JobProperty jobProperty = _batchTestClassGroup.getJobProperty(name);

		String value = jobProperty.getValue();

		if (JenkinsResultsParserUtil.isNullOrEmpty(value)) {
			return null;
		}

		return new AbstractMap.SimpleEntry<>(key, value);
	}

	private final BatchTestClassGroup _batchTestClassGroup;

}