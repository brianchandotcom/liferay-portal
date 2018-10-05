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

import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BatchBuildData extends BaseBuildData {

	public String getBatchName() {
		return getString("batch_name");
	}

	public TopLevelBuildData getTopLevelBuildData() {
		if (_topLevelBuildData != null) {
			return _topLevelBuildData;
		}

		BuildDatabase buildDatabase = BuildDatabaseUtil.getBuildDatabase();

		BuildData buildData = buildDatabase.getBuildData(getTopLevelRunID());

		if (!(buildData instanceof TopLevelBuildData)) {
			throw new RuntimeException(
				"Invalid build data " + buildData.toString());
		}

		_topLevelBuildData = (TopLevelBuildData)buildData;

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

	public String getTopLevelRunID() {
		return optString("top_level_run_id", null);
	}

	protected BatchBuildData(JSONObject jsonObject) {
		super(jsonObject);

		validateKeys(_REQUIRED_KEYS);
	}

	protected BatchBuildData(Map<String, String> buildParameters) {
		super(buildParameters);

		put("batch_name", _getBatchName(buildParameters));
		put("dist_nodes", _getDistNodes(buildParameters));
		put("dist_path", _getDistPath(buildParameters));
		put("top_level_run_id", _getTopLevelRunID(buildParameters));

		validateKeys(_REQUIRED_KEYS);
	}

	private String _getBatchName(Map<String, String> buildParameters) {
		if (!buildParameters.containsKey("BATCH_NAME")) {
			throw new RuntimeException("Please set BATCH_NAME");
		}

		return buildParameters.get("BATCH_NAME");
	}

	private String _getDistNodes(Map<String, String> buildParameters) {
		if (!buildParameters.containsKey("DIST_NODES")) {
			throw new RuntimeException("Please set DIST_NODES");
		}

		return buildParameters.get("DIST_NODES");
	}

	private String _getDistPath(Map<String, String> buildParameters) {
		if (!buildParameters.containsKey("DIST_PATH")) {
			throw new RuntimeException("Please set DIST_PATH");
		}

		return buildParameters.get("DIST_PATH");
	}

	private String _getTopLevelRunID(Map<String, String> buildParameters) {
		if (!buildParameters.containsKey("TOP_LEVEL_RUN_ID")) {
			throw new RuntimeException("Please set TOP_LEVEL_RUN_ID");
		}

		return buildParameters.get("TOP_LEVEL_RUN_ID");
	}

	private static final String[] _REQUIRED_KEYS =
		{"batch_name", "dist_nodes", "dist_path", "top_level_run_id"};

	private TopLevelBuildData _topLevelBuildData;

}