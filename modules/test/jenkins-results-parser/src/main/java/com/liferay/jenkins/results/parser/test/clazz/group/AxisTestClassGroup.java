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

import java.util.List;

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

	public String getBatchName() {
		return _batchTestClassGroup.getBatchName();
	}

	public BatchTestClassGroup getBatchTestClassGroup() {
		return _batchTestClassGroup;
	}

	public Integer getMinimumSlaveRAM() {
		if (_segmentTestClassGroup != null) {
			return _segmentTestClassGroup.getMinimumSlaveRAM();
		}

		return _batchTestClassGroup.getMinimumSlaveRAM();
	}

	public SegmentTestClassGroup getSegmentTestClassGroup() {
		return _segmentTestClassGroup;
	}

	protected AxisTestClassGroup(BatchTestClassGroup batchTestClassGroup) {
		setBatchTestClassGroup(batchTestClassGroup);
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