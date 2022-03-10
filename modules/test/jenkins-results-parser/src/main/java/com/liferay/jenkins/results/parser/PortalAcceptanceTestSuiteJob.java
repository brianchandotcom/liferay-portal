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

package com.liferay.jenkins.results.parser;

import com.liferay.jenkins.results.parser.job.property.JobProperty;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.BatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.SegmentTestClassGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Yi-Chen Tsai
 */
public abstract class PortalAcceptanceTestSuiteJob
	extends PortalGitRepositoryJob implements BatchDependentJob, TestSuiteJob {

	@Override
	public List<AxisTestClassGroup> getDependentAxisTestClassGroups() {
		List<AxisTestClassGroup> axisTestClassGroups = new ArrayList<>();

		for (BatchTestClassGroup batchTestClassGroup :
				getDependentBatchTestClassGroups()) {

			axisTestClassGroups.addAll(
				batchTestClassGroup.getAxisTestClassGroups());
		}

		return axisTestClassGroups;
	}

	@Override
	public Set<String> getDependentBatchNames() {
		return getFilteredBatchNames(getRawDependentBatchNames());
	}

	@Override
	public List<BatchTestClassGroup> getDependentBatchTestClassGroups() {
		return getBatchTestClassGroups(getRawDependentBatchNames());
	}

	@Override
	public Set<String> getDependentSegmentNames() {
		return getFilteredSegmentNames(getRawDependentBatchNames());
	}

	@Override
	public List<SegmentTestClassGroup> getDependentSegmentTestClassGroups() {
		return getSegmentTestClassGroups(getRawDependentBatchNames());
	}

	@Override
	public DistType getDistType() {
		JobProperty jobProperty = getJobProperty("dist.type");

		String distType = jobProperty.getValue();

		if (!JenkinsResultsParserUtil.isNullOrEmpty(distType)) {
			for (DistType distTypeValue : DistType.values()) {
				if (distType.equals(distTypeValue.toString())) {
					recordJobProperty(jobProperty);

					return distTypeValue;
				}
			}
		}

		return DistType.CI;
	}

	@Override
	public Set<String> getDistTypes() {
		Set<String> distTypes = super.getDistTypes();

		if (!_testSuiteName.equals("relevant")) {
			return distTypes;
		}

		JobProperty jobProperty = getJobProperty(
			"test.batch.dist.app.servers[stable]");

		distTypes.addAll(getSetFromString(jobProperty.getValue()));

		return distTypes;
	}

	@Override
	public JSONObject getJSONObject() {
		if (jsonObject != null) {
			return jsonObject;
		}

		jsonObject = super.getJSONObject();

		jsonObject.put("test_suite_name", _testSuiteName);

		return jsonObject;
	}

	@Override
	public String getTestSuiteName() {
		return _testSuiteName;
	}

	protected PortalAcceptanceTestSuiteJob(
		BuildProfile buildProfile, String jobName,
		PortalGitWorkingDirectory portalGitWorkingDirectory,
		String testSuiteName, String upstreamBranchName) {

		super(
			buildProfile, jobName, portalGitWorkingDirectory,
			upstreamBranchName);

		if (JenkinsResultsParserUtil.isNullOrEmpty(testSuiteName)) {
			testSuiteName = "default";
		}

		_testSuiteName = testSuiteName;
	}

	protected PortalAcceptanceTestSuiteJob(JSONObject jsonObject) {
		super(jsonObject);

		_testSuiteName = jsonObject.getString("test_suite_name");
	}

	@Override
	protected Set<String> getRawBatchNames() {
		Set<String> rawBatchNames = super.getRawBatchNames();

		if (!testRelevantChanges()) {
			return rawBatchNames;
		}

		JobProperty jobProperty = getJobProperty("test.batch.names[stable]");

		recordJobProperty(jobProperty);

		rawBatchNames.addAll(getSetFromString(jobProperty.getValue()));

		return rawBatchNames;
	}

	protected Set<String> getRawDependentBatchNames() {
		JobProperty jobProperty = getJobProperty("test.batch.names.smoke");

		recordJobProperty(jobProperty);

		return getSetFromString(jobProperty.getValue());
	}

	private final String _testSuiteName;

}