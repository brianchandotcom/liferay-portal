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

import java.util.List;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseBatchBuildData
	extends BaseBuildData implements BatchBuildData {

	@Override
	public String getBatchName() {
		return getString("batch_name");
	}

	@Override
	public List<String> getTestList() {
		return getList("test_list");
	}

	public TopLevelBuildData getTopLevelBuildData() {
		if (_topLevelBuildData != null) {
			return _topLevelBuildData;
		}

		String topLevelJobName = getJobName();

		topLevelJobName = topLevelJobName.replace("-batch", "");

		TopLevelBuildData topLevelBuildData =
			BuildDataFactory.newTopLevelBuildData(
				topLevelJobName, getTopLevelRunID());

		_topLevelBuildData = topLevelBuildData;

		return _topLevelBuildData;
	}

	@Override
	public Integer getTopLevelBuildNumber() {
		TopLevelBuildData topLevelBuildData = getTopLevelBuildData();

		return topLevelBuildData.getBuildNumber();
	}

	@Override
	public String getTopLevelJobName() {
		TopLevelBuildData topLevelBuildData = getTopLevelBuildData();

		return topLevelBuildData.getJobName();
	}

	@Override
	public String getTopLevelMasterHostname() {
		TopLevelBuildData topLevelBuildData = getTopLevelBuildData();

		return topLevelBuildData.getMasterHostname();
	}

	@Override
	public String getTopLevelRunID() {
		return optString("top_level_run_id");
	}

	@Override
	public void setBatchName(String batchName) {
		put("batch_name", batchName);
	}

	@Override
	public void setTestList(List<String> testList) {
		put("test_list", testList);
	}

	protected BaseBatchBuildData() {
		this(null, null);
	}

	protected BaseBatchBuildData(String runID) {
		this(runID, null);
	}

	protected BaseBatchBuildData(String runID, String buildURL) {
		super(_getDefaultRunID(runID), buildURL);

		if (buildURL == null) {
			return;
		}

		_setTopLevelRunID();

		validateKeys(_REQUIRED_KEYS);
	}

	private static String _getDefaultRunID(String runID) {
		if (runID != null) {
			return runID;
		}

		return "batch_" + JenkinsResultsParserUtil.getDistinctTimeStamp();
	}

	private void _setTopLevelRunID() {
		String topLevelRunID = System.getenv("TOP_LEVEL_RUN_ID");

		if (topLevelRunID == null) {
			throw new RuntimeException("Please set TOP_LEVEL_RUN_ID");
		}

		put("top_level_run_id", topLevelRunID);
	}

	private static final String[] _REQUIRED_KEYS =
		{"batch_name", "top_level_run_id", "test_list"};

	private TopLevelBuildData _topLevelBuildData;

}