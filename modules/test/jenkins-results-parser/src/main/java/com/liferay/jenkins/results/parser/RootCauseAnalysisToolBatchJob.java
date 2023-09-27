/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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