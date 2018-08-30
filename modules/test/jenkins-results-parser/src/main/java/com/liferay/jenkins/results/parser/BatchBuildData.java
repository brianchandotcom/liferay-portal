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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BatchBuildData extends BaseBuildData {

	public String getBatchName() {
		return _batchName;
	}

	@Override
	public List<String> getDistNodes() {
		return _distNodes;
	}

	@Override
	public String getDistPath() {
		return _distPath;
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject jsonObject = super.toJSONObject();

		jsonObject.put("batch_name", getBatchName());
		jsonObject.put("dist_nodes", StringUtils.join(getDistNodes(), ","));
		jsonObject.put("dist_path", getDistPath());
		jsonObject.put("run_id", getRunID());

		return jsonObject;
	}

	protected BatchBuildData(
		Map<String, String> buildParameters, String runID) {

		super(buildParameters, runID);

		_init(buildParameters, runID);
	}

	protected BatchBuildData(
		String jsonString, Map<String, String> buildParameters, String runID) {

		super(jsonString, buildParameters, runID);

		_init(buildParameters, runID);
	}

	private void _init(Map<String, String> buildParameters, String runID) {
		if (has(runID)) {
			JSONObject jsonObject = getJSONObject(runID);

			if (jsonObject.has("batch_name")) {
				_batchName = jsonObject.getString("batch_name");

				String distNodes = jsonObject.getString("dist_nodes");

				Collections.addAll(_distNodes, distNodes.split(","));

				_distPath = jsonObject.getString("dist_path");

				return;
			}
		}

		if (!buildParameters.containsKey("BATCH_NAME")) {
			throw new RuntimeException("Please set BATCH_NAME");
		}

		_batchName = buildParameters.get("BATCH_NAME");

		if (!buildParameters.containsKey("DIST_NODES")) {
			throw new RuntimeException("Please set DIST_NODES");
		}

		String distNodes = buildParameters.get("DIST_NODES");

		Collections.addAll(_distNodes, distNodes.split(","));

		if (!buildParameters.containsKey("DIST_PATH")) {
			throw new RuntimeException("Please set DIST_PATH");
		}

		_distPath = buildParameters.get("DIST_PATH");

		updateBuildData();
	}

	private String _batchName;
	private List<String> _distNodes = new ArrayList<>();
	private String _distPath;

}