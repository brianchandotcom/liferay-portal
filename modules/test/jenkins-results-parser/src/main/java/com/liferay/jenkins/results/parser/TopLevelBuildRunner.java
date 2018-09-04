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

import java.io.File;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * @author Michael Hashimoto
 */
public abstract class TopLevelBuildRunner<T extends TopLevelBuildData>
	extends BaseBuildRunner<T> {

	@Override
	public void run() {
		super.run();

		propagateDistFilesToDistNodes();

		invokeBatchJobs();
	}

	protected TopLevelBuildRunner(T topLevelBuildData) {
		super(topLevelBuildData);
	}

	protected Set<String> getBatchNames() {
		Job job = getJob();

		return job.getBatchNames();
	}

	protected String[] getDistFileNames() {
		return new String[] {BuildData.JENKINS_BUILD_DATA_FILE_NAME};
	}

	protected void invokeBatchJob(String batchName) {
		BuildData buildData = getBuildData();

		Map<String, String> invocationParameters = new HashMap<>();

		invocationParameters.put("BATCH_NAME", batchName);
		invocationParameters.put(
			"DIST_NODES", StringUtils.join(buildData.getDistNodes(), ","));
		invocationParameters.put("DIST_PATH", buildData.getDistPath());
		invocationParameters.put("JENKINS_GITHUB_URL", _getJenkinsGitHubURL());
		invocationParameters.put(
			"RUN_ID",
			"batch_" + JenkinsResultsParserUtil.getDistinctTimeStamp());
		invocationParameters.put("TOP_LEVEL_RUN_ID", buildData.getRunID());

		JenkinsResultsParserUtil.invokeJob(
			buildData.getCohortName(), buildData.getJobName() + "-batch",
			invocationParameters);
	}

	protected void invokeBatchJobs() {
		for (String batchName : getBatchNames()) {
			invokeBatchJob(batchName);
		}
	}

	protected void propagateDistFilesToDistNodes() {
		if (!JenkinsResultsParserUtil.isCINode()) {
			return;
		}

		writeJenkinsJSONObjectToFile();

		BuildData buildData = getBuildData();

		File workspaceDir = buildData.getWorkspaceDir();

		FilePropagator filePropagator = new FilePropagator(
			getDistFileNames(),
			JenkinsResultsParserUtil.combine(
				buildData.getHostname(), ":", workspaceDir.toString()),
			buildData.getDistPath(), buildData.getDistNodes());

		filePropagator.setCleanUpCommand(_FILE_PROPAGATOR_CLEAN_UP_COMMAND);

		filePropagator.start(_FILE_PROPAGATOR_THREAD_COUNT);
	}

	private String _getJenkinsGitHubURL() {
		String jenkinsCachedBranchName = workspace.getJenkinsCachedBranchName();

		if (jenkinsCachedBranchName != null) {
			return "https://github-dev.liferay.com/liferay/liferay-jenkins-ee" +
				"/tree/" + jenkinsCachedBranchName;
		}

		BuildData buildData = getBuildData();

		return buildData.getJenkinsGitHubURL();
	}

	private static final String _FILE_PROPAGATOR_CLEAN_UP_COMMAND =
		JenkinsResultsParserUtil.combine(
			"find ", BuildData.DIST_ROOT_PATH,
			"/*/* -maxdepth 1 -type d -mmin +",
			String.valueOf(TopLevelBuildRunner._FILE_PROPAGATOR_EXPIRATION),
			" -exec rm -frv {} \\;");

	private static final int _FILE_PROPAGATOR_EXPIRATION = 180;

	private static final int _FILE_PROPAGATOR_THREAD_COUNT = 1;

}