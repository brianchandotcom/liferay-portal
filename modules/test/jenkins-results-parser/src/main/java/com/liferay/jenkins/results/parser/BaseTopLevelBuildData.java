/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
	public List<BuildData> getDownstreamBuildDataList() {
		List<BuildData> downstreamBuildDataList = new ArrayList<>();

		String downstreamRunIDs = optString("downstream_run_ids");

		if (downstreamRunIDs == null) {
			return downstreamBuildDataList;
		}

		for (String downstreamRunID : downstreamRunIDs.split(",")) {
			if ((downstreamRunID == null) || downstreamRunID.isEmpty()) {
				continue;
			}

			downstreamBuildDataList.add(
				BuildDataFactory.newBatchBuildData(
					downstreamRunID, getJobName() + "-batch", null));
		}

		return downstreamBuildDataList;
	}

	@Override
	public TopLevelBuildData getTopLevelBuildData() {
		return this;
	}

	@Override
	public Integer getTopLevelBuildNumber() {
		return getBuildNumber();
	}

	@Override
	public Map<String, String> getTopLevelBuildParameters() {
		return getBuildParameters();
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

	@Override
	public void setDistNodes(List<String> distNodes) {
		if (distNodes == null) {
			throw new RuntimeException("Dist nodes is null");
		}

		put("dist_nodes", JenkinsResultsParserUtil.join(",", distNodes));
	}

	protected BaseTopLevelBuildData(
		String runID, String jobName, String buildURL) {

		super(_getDefaultRunID(runID), jobName, buildURL);

		put("dist_nodes", _getDistNodes());
		put("dist_path", _getDistPath());
		put("top_level_run_id", getRunID());

		validateKeys(_KEYS_REQUIRED);
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
			buildProperties = JenkinsResultsParserUtil.getBuildProperties(
				false);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		List<JenkinsMaster> jenkinsMasters =
			JenkinsResultsParserUtil.getJenkinsMasters(
				buildProperties, JenkinsMaster.getSlaveRAMMinimumDefault(),
				JenkinsMaster.getSlavesPerHostDefault(), getCohortName());

		List<String> distNodes = new ArrayList<>(jenkinsMasters.size());

		for (JenkinsMaster jenkinsMaster : jenkinsMasters) {
			int retries = 0;

			while (true) {
				if (retries > jenkinsMaster.getOnlineJenkinsSlavesCount()) {
					break;
				}

				JenkinsSlave randomJenkinsSlave =
					jenkinsMaster.getRandomJenkinsSlave();

				if ((randomJenkinsSlave != null) &&
					!randomJenkinsSlave.isOffline() &&
					randomJenkinsSlave.isReachable()) {

					distNodes.add(randomJenkinsSlave.getName());

					break;
				}

				retries++;
			}
		}

		return StringUtils.join(distNodes, ",");
	}

	private String _getDistPath() {
		return JenkinsResultsParserUtil.combine(
			JenkinsResultsParserUtil.getJenkinsDistRootPath(), "/",
			getMasterHostname(), "/", getJobName(), "/",
			String.valueOf(getBuildNumber()), "/dist");
	}

	private static final String[] _KEYS_REQUIRED = {
		"dist_nodes", "dist_path", "top_level_run_id"
	};

}