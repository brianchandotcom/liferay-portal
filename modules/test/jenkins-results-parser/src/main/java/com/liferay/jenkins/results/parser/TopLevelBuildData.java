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

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class TopLevelBuildData extends BaseBuildData {

	@Override
	public List<String> getDistNodes() {
		return _distNodes;
	}

	@Override
	public String getDistPath() {
		return _distPath;
	}

	@Override
	public void updateBuildData() {
		super.updateBuildData();

		put("dist_nodes", StringUtils.join(getDistNodes(), ","));
		put("dist_path", getDistPath());
	}

	protected TopLevelBuildData(Map<String, String> buildParameters) {
		super(buildParameters, TOP_LEVEL_RUN_ID);

		_init();
	}

	protected TopLevelBuildData(
		String jsonString, Map<String, String> buildParameters) {

		super(jsonString, buildParameters, TOP_LEVEL_RUN_ID);

		_init();
	}

	private List<String> _getDistNodes() {
		if (!JenkinsResultsParserUtil.isCINode()) {
			return Collections.emptyList();
		}

		Properties buildProperties;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		String cohortName = getCohortName();

		List<JenkinsMaster> jenkinsMasters =
			JenkinsResultsParserUtil.getJenkinsMasters(
				buildProperties, cohortName);

		List<String> slaves = JenkinsResultsParserUtil.getSlaves(
			buildProperties, cohortName + "-[1-9]{1}[0-9]?");

		return JenkinsResultsParserUtil.getRandomList(
			slaves, jenkinsMasters.size());
	}

	private String _getDistPath() {
		return JenkinsResultsParserUtil.combine(
			BaseBuildRunner.DIST_ROOT_PATH, "/", getMasterHostname(), "/",
			getJobName(), "/", String.valueOf(getBuildNumber()), "/dist");
	}

	private void _init() {
		String runID = getRunID();

		if (has(runID)) {
			JSONObject jsonObject = getJSONObject(runID);

			String distNodes = jsonObject.getString("dist_nodes");

			Collections.addAll(_distNodes, distNodes.split(","));

			_distPath = jsonObject.getString("dist_path");

			return;
		}

		_distNodes.addAll(_getDistNodes());
		_distPath = _getDistPath();

		updateBuildData();
	}

	private List<String> _distNodes = new ArrayList<>();
	private String _distPath;

}