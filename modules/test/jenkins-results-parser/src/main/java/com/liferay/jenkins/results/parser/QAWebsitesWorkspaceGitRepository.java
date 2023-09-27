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
public class QAWebsitesWorkspaceGitRepository
	extends BaseWorkspaceGitRepository {

	@Override
	public void writePropertiesFiles() {
		_writeTestPropertiesFile();
	}

	protected QAWebsitesWorkspaceGitRepository(JSONObject jsonObject) {
		super(jsonObject);
	}

	protected QAWebsitesWorkspaceGitRepository(
		PullRequest pullRequest, String upstreamBranchName) {

		super(pullRequest, upstreamBranchName);
	}

	protected QAWebsitesWorkspaceGitRepository(
		RemoteGitRef remoteGitRef, String upstreamBranchName) {

		super(remoteGitRef, upstreamBranchName);
	}

	private void _writeTestPropertiesFile() {
		JenkinsResultsParserUtil.writePropertiesFile(
			new File(
				getDirectory(),
				JenkinsResultsParserUtil.combine(
					"test.", System.getenv("HOSTNAME"), ".properties")),
			getProperties("qa.websites.test.properties"), true);
	}

}