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

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseTopLevelBuildData
	extends BaseBuildData implements TopLevelBuildData {

	@Override
	public void addDownstreamBuildData(BuildData buildData) {
		String downstreamRunIDs = optString("downstream_run_ids");

		if (downstreamRunIDs == null) {
			downstreamRunIDs = buildData.getRunID();
		}
		else {
			downstreamRunIDs += "," + buildData.getRunID();
		}

		put("downstream_run_ids", downstreamRunIDs);
	}

	@Override
	public List<String> getDistNodes() {
		String distNodes = optString("dist_nodes");

		if (distNodes == null) {
			return null;
		}

		return Arrays.asList(distNodes.split(","));
	}

	@Override
	public String getDistPath() {
		return optString("dist_path");
	}

	@Override
	public Integer getTopLevelBuildNumber() {
		return getBuildNumber();
	}

	@Override
	public String getTopLevelJobName() {
		return getJobName();
	}

	@Override
	public String getTopLevelMasterHostname() {
		return getMasterHostname();
	}

	@Override
	public String getTopLevelRunID() {
		return getRunID();
	}

	protected BaseTopLevelBuildData(
		String runID, String jobName, String buildURL) {

		super(_getDefaultRunID(runID), jobName, buildURL);

		put("dist_nodes", _getDistNodes());
		put("dist_path", _getDistPath());
		put("top_level_run_id", getRunID());

		validateKeys(_REQUIRED_KEYS);
	}

	private static String _getDefaultRunID(String runID) {
		if (runID != null) {
			return runID;
		}

		return "top_level_" + JenkinsResultsParserUtil.getDistinctTimeStamp();
	}

	private String _getDistNodes() {
		if (!JenkinsResultsParserUtil.isCINode()) {
			return "";
		}

		Properties buildProperties = null;

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

		List<String> distNodes = JenkinsResultsParserUtil.getRandomList(
			slaves, jenkinsMasters.size());

		return StringUtils.join(distNodes, ",");
	}

	private String _getDistPath() {
		return JenkinsResultsParserUtil.combine(
			BuildData.DIST_ROOT_PATH, "/", getMasterHostname(), "/",
			getJobName(), "/", String.valueOf(getBuildNumber()), "/dist");
	}

	private static final String[] _REQUIRED_KEYS =
		{"dist_nodes", "dist_path", "top_level_run_id"};

}