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

import java.util.ArrayList;
import java.util.List;

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
			_parentBatchTestClassGroup.getSegmentTestClassGroups();

		return segmentTestClassGroups.indexOf(this);
	}

	public String getBatchJobName() {
		return _parentBatchTestClassGroup.getBatchJobName();
	}

	public String getBatchName() {
		return _parentBatchTestClassGroup.getBatchName();
	}

	@Override
	public Job getJob() {
		return _parentBatchTestClassGroup.getJob();
	}

	public Integer getMaximumSlavesPerHost() {
		return _parentBatchTestClassGroup.getMaximumSlavesPerHost();
	}

	public Integer getMinimumSlaveRAM() {
		return _parentBatchTestClassGroup.getMinimumSlaveRAM();
	}

	public BatchTestClassGroup getParentBatchTestClassGroup() {
		return _parentBatchTestClassGroup;
	}

	public String getSegmentName() {
		return JenkinsResultsParserUtil.combine(
			getBatchName(), "/", String.valueOf(getBatchIndex()));
	}

	public String getTestCasePropertiesContent() {
		return null;
	}

	protected SegmentTestClassGroup(
		BatchTestClassGroup parentBatchTestClassGroup) {

		_parentBatchTestClassGroup = parentBatchTestClassGroup;
	}

	private final List<AxisTestClassGroup> _axisTestClassGroups =
		new ArrayList<>();
	private final BatchTestClassGroup _parentBatchTestClassGroup;

}