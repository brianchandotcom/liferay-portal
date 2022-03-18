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
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SegmentTestClassGroup extends BaseTestClassGroup {

	public void addAxisTestClassGroup(AxisTestClassGroup axisTestClassGroup) {
		_axisTestClassGroups.add(axisTestClassGroup);

		axisTestClassGroup.setSegmentTestClassGroup(this);
	}

	public int getAxisCount() {
		return _axisTestClassGroups.size();
	}

	public AxisTestClassGroup getAxisTestClassGroup(int segmentIndex) {
		return _axisTestClassGroups.get(segmentIndex);
	}

	public List<AxisTestClassGroup> getAxisTestClassGroups() {
		return new ArrayList<>(_axisTestClassGroups);
	}

	public int getBatchIndex() {
		List<SegmentTestClassGroup> segmentTestClassGroups =
			_batchTestClassGroup.getSegmentTestClassGroups();

		return segmentTestClassGroups.indexOf(this);
	}

	public String getBatchJobName() {
		return _batchTestClassGroup.getBatchJobName();
	}

	public String getBatchName() {
		return _batchTestClassGroup.getBatchName();
	}

	public BatchTestClassGroup getBatchTestClassGroup() {
		return _batchTestClassGroup;
	}

	public String getCohortName() {
		return _batchTestClassGroup.getCohortName();
	}

	@Override
	public Job getJob() {
		return _batchTestClassGroup.getJob();
	}

	public JSONObject getJSONObject() {
		JSONObject jsonObject = new JSONObject();

		JSONArray axesJSONArray = new JSONArray();

		for (AxisTestClassGroup axisTestClassGroup : getAxisTestClassGroups()) {
			axesJSONArray.put(axisTestClassGroup.getJSONObject());
		}

		jsonObject.put("axes", axesJSONArray);

		jsonObject.put("segment_name", getSegmentName());

		return jsonObject;
	}

	public Integer getMaximumSlavesPerHost() {
		return _batchTestClassGroup.getMaximumSlavesPerHost();
	}

	public Integer getMinimumSlaveRAM() {
		return _batchTestClassGroup.getMinimumSlaveRAM();
	}

	public String getSegmentName() {
		return JenkinsResultsParserUtil.combine(
			getBatchName(), "/", String.valueOf(getBatchIndex()));
	}

	public String getSlaveLabel() {
		BatchTestClassGroup batchTestClassGroup = getBatchTestClassGroup();

		return batchTestClassGroup.getSlaveLabel();
	}

	public File getTestBaseDir() {
		List<AxisTestClassGroup> axisTestClassGroups = getAxisTestClassGroups();

		if ((axisTestClassGroups == null) || axisTestClassGroups.isEmpty()) {
			return null;
		}

		AxisTestClassGroup axisTestClassGroup = axisTestClassGroups.get(0);

		return axisTestClassGroup.getTestBaseDir();
	}

	public String getTestCasePropertiesContent() {
		StringBuilder sb = new StringBuilder();

		File testBaseDir = getTestBaseDir();

		if ((testBaseDir != null) && testBaseDir.exists()) {
			sb.append("TEST_BASE_DIR_NAME=");
			sb.append(JenkinsResultsParserUtil.getCanonicalPath(testBaseDir));
			sb.append("\n");
		}

		return sb.toString();
	}

	@Override
	public List<TestClass> getTestClasses() {
		List<TestClass> testClasses = new ArrayList<>();

		for (AxisTestClassGroup axisTestClassGroup : getAxisTestClassGroups()) {
			testClasses.addAll(axisTestClassGroup.getTestClasses());
		}

		return testClasses;
	}

	protected SegmentTestClassGroup(
		BatchTestClassGroup parentBatchTestClassGroup) {

		_batchTestClassGroup = parentBatchTestClassGroup;
	}

	protected SegmentTestClassGroup(
		BatchTestClassGroup parentBatchTestClassGroup, JSONObject jsonObject) {

		_batchTestClassGroup = parentBatchTestClassGroup;

		JSONArray axesJSONArray = jsonObject.getJSONArray("axes");

		if ((axesJSONArray == null) || axesJSONArray.isEmpty()) {
			return;
		}

		for (int i = 0; i < axesJSONArray.length(); i++) {
			JSONObject axisJSONObject = axesJSONArray.getJSONObject(i);

			if (axisJSONObject == null) {
				continue;
			}

			_axisTestClassGroups.add(
				TestClassGroupFactory.newAxisTestClassGroup(
					axisJSONObject, this));
		}
	}

	private final List<AxisTestClassGroup> _axisTestClassGroups =
		new ArrayList<>();
	private final BatchTestClassGroup _batchTestClassGroup;

}