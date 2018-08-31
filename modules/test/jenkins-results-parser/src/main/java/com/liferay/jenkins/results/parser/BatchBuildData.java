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

/**
 * @author Michael Hashimoto
 */
public class BatchBuildData extends BaseBuildData {

	public String getBatchName() {
		return getString("batch_name");
	}

	public TopLevelBuildData getTopLevelBuildData() {
		return _topLevelBuildData;
	}

	public String getTopLevelRunID() {
		return optString("top_level_run_id", null);
	}

	protected BatchBuildData(
		Map<String, String> buildParameters,
		JenkinsJSONObject jenkinsJSONObject, String runID) {

		super(buildParameters, jenkinsJSONObject, runID);

		if (!has("batch_name")) {
			if (!buildParameters.containsKey("BATCH_NAME")) {
				throw new RuntimeException("Please set BATCH_NAME");
			}

			put("batch_name", buildParameters.get("BATCH_NAME"));
		}

		if (!has("dist_nodes")) {
			if (!buildParameters.containsKey("DIST_NODES")) {
				throw new RuntimeException("Please set DIST_NODES");
			}

			put("dist_nodes", buildParameters.get("DIST_NODES"));
		}

		if (!has("dist_path")) {
			if (!buildParameters.containsKey("DIST_PATH")) {
				throw new RuntimeException("Please set DIST_PATH");
			}

			put("dist_nodes", buildParameters.get("DIST_PATH"));
		}

		if (!has("top_level_run_id") &&
			buildParameters.containsKey("TOP_LEVEL_RUN_ID")) {

			put("top_level_run_id", buildParameters.get("TOP_LEVEL_RUN_ID"));
		}

		_topLevelBuildData = _getTopLevelBuildData(
			buildParameters, jenkinsJSONObject);
	}

	private TopLevelBuildData _getTopLevelBuildData(
		Map<String, String> buildParameters,
		JenkinsJSONObject jenkinsJSONObject) {

		String topLevelRunID = getTopLevelRunID();

		if (topLevelRunID == null) {
			return null;
		}

		return BuildDataFactory.newTopLevelBuildData(
			buildParameters, jenkinsJSONObject, topLevelRunID);
	}

	private final TopLevelBuildData _topLevelBuildData;

}