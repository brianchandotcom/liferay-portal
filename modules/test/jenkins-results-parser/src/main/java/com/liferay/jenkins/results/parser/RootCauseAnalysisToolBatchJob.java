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

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class RootCauseAnalysisToolBatchJob extends RootCauseAnalysisToolJob {

	protected RootCauseAnalysisToolBatchJob(
		BuildProfile buildProfile, String jobName, String upstreamBranchName) {

		super(buildProfile, jobName, upstreamBranchName);

		_initialize();
	}

	protected RootCauseAnalysisToolBatchJob(JSONObject jsonObject) {
		super(jsonObject);

		_initialize();
	}

	private void _initialize() {
		GitWorkingDirectory jenkinsGitWorkingDirectory =
			getJenkinsGitWorkingDirectory();

		jobPropertiesFiles.add(
			new File(
				jenkinsGitWorkingDirectory.getWorkingDirectory(),
				JenkinsResultsParserUtil.combine(
					"commands/dependencies/",
					"root-cause-analysis-tool-batch.properties")));

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			getPortalGitWorkingDirectory();

		jobPropertiesFiles.add(
			new File(
				portalGitWorkingDirectory.getWorkingDirectory(),
				"test.properties"));
	}

}