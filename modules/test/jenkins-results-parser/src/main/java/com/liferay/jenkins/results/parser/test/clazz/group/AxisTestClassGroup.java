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
import com.liferay.jenkins.results.parser.test.clazz.TestClassFactory;

import java.io.File;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class AxisTestClassGroup extends BaseTestClassGroup {

	public String getAxisName() {
		if (_segmentTestClassGroup != null) {
			List<AxisTestClassGroup> axisTestClassGroups =
				_segmentTestClassGroup.getAxisTestClassGroups();

			return JenkinsResultsParserUtil.combine(
				_segmentTestClassGroup.getSegmentName(), "/",
				String.valueOf(axisTestClassGroups.indexOf(this)));
		}

		List<AxisTestClassGroup> axisTestClassGroups =
			_batchTestClassGroup.getAxisTestClassGroups();

		return JenkinsResultsParserUtil.combine(
			_batchTestClassGroup.getBatchName(), "/",
			String.valueOf(axisTestClassGroups.indexOf(this)));
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

	@Override
	public Job getJob() {
		return _batchTestClassGroup.getJob();
	}

	public JSONObject getJSONObject() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("axis_name", getAxisName());

		JSONArray testClassesJSONArray = new JSONArray();

		jsonObject.put("test_classes", testClassesJSONArray);

		for (TestClass testClass : getTestClasses()) {
			if (testClass == null) {
				throw new RuntimeException(
					"Unable to not find test class in " + getAxisName());
			}

			testClassesJSONArray.put(testClass.getJSONObject());
		}

		return jsonObject;
	}

	public Integer getMinimumSlaveRAM() {
		if (_segmentTestClassGroup != null) {
			return _segmentTestClassGroup.getMinimumSlaveRAM();
		}

		return _batchTestClassGroup.getMinimumSlaveRAM();
	}

	public String getSegmentName() {
		if (_segmentTestClassGroup != null) {
			return _segmentTestClassGroup.getSegmentName();
		}

		return null;
	}

	public SegmentTestClassGroup getSegmentTestClassGroup() {
		return _segmentTestClassGroup;
	}

	public String getSlaveLabel() {
		if (_segmentTestClassGroup != null) {
			return _segmentTestClassGroup.getSlaveLabel();
		}

		return _batchTestClassGroup.getSlaveLabel();
	}

	public File getTestBaseDir() {
		return null;
	}

	protected AxisTestClassGroup(BatchTestClassGroup batchTestClassGroup) {
		setBatchTestClassGroup(batchTestClassGroup);
	}

	protected AxisTestClassGroup(
		JSONObject jsonObject, SegmentTestClassGroup segmentTestClassGroup) {

		BatchTestClassGroup batchTestClassGroup =
			segmentTestClassGroup.getBatchTestClassGroup();

		setBatchTestClassGroup(batchTestClassGroup);

		setSegmentTestClassGroup(segmentTestClassGroup);

		JSONArray testClassesJSONArray = jsonObject.getJSONArray(
			"test_classes");

		if ((testClassesJSONArray == null) || testClassesJSONArray.isEmpty()) {
			return;
		}

		for (int i = 0; i < testClassesJSONArray.length(); i++) {
			JSONObject testClassJSONObject = testClassesJSONArray.getJSONObject(
				i);

			if (testClassJSONObject == null) {
				continue;
			}

			testClasses.add(
				TestClassFactory.newTestClass(
					batchTestClassGroup, testClassJSONObject));
		}
	}

	protected void setBatchTestClassGroup(
		BatchTestClassGroup batchTestClassGroup) {

		_batchTestClassGroup = batchTestClassGroup;
	}

	protected void setSegmentTestClassGroup(
		SegmentTestClassGroup segmentTestClassGroup) {

		_segmentTestClassGroup = segmentTestClassGroup;
	}

	private BatchTestClassGroup _batchTestClassGroup;
	private SegmentTestClassGroup _segmentTestClassGroup;

}