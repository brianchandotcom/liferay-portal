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

import java.util.Arrays;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class QAWebsitesTopLevelBuild
	extends DefaultTopLevelBuild
	implements QAWebsitesBranchInformationBuild, WorkspaceBuild {

	public QAWebsitesTopLevelBuild(String url, TopLevelBuild topLevelBuild) {
		super(url, topLevelBuild);

		findDownstreamBuilds();
	}

	@Override
	public String getBaseGitRepositoryName() {
		return "liferay-qa-websites-ee";
	}

	@Override
	public List<String> getProjectNames() {
		String projectNames = getParameterValue("PROJECT_NAMES");

		return Arrays.asList(projectNames.split(","));
	}

	@Override
	public BranchInformation getQAWebsitesBranchInformation() {
		Workspace workspace = getWorkspace();

		if (workspace == null) {
			return null;
		}

		WorkspaceGitRepository workspaceGitRepository =
			workspace.getPrimaryWorkspaceGitRepository();

		if (workspaceGitRepository == null) {
			return null;
		}

		return new WorkspaceBranchInformation(workspaceGitRepository);
	}

	@Override
	public Workspace getWorkspace() {
		Workspace workspace = WorkspaceFactory.newWorkspace(
			getBaseGitRepositoryName(), getBranchName(), getJobName());

		WorkspaceGitRepository workspaceGitRepository =
			workspace.getPrimaryWorkspaceGitRepository();

		String qaWebsitesGitHubURL = _getQAWebsitesGitHubURL();

		if (!JenkinsResultsParserUtil.isNullOrEmpty(qaWebsitesGitHubURL)) {
			workspaceGitRepository.setGitHubURL(qaWebsitesGitHubURL);
		}

		String qaWebsitesBranchSHA = _getQAWebsitesBranchSHA();

		if (!JenkinsResultsParserUtil.isNullOrEmpty(qaWebsitesBranchSHA)) {
			workspaceGitRepository.setSenderBranchSHA(qaWebsitesBranchSHA);
		}

		return workspace;
	}

	private String _getQAWebsitesBranchSHA() {
		return getParameterValue("TEST_QA_WEBSITES_GIT_ID");
	}

	private String _getQAWebsitesGitHubURL() {
		String qaWebsitesBranchName = getParameterValue(
			"TEST_QA_WEBSITES_BRANCH_NAME");

		if (JenkinsResultsParserUtil.isNullOrEmpty(qaWebsitesBranchName)) {
			qaWebsitesBranchName = "master";
		}

		String qaWebsitesBranchUsername = getParameterValue(
			"TEST_QA_WEBSITES_BRANCH_USERNAME");

		if (JenkinsResultsParserUtil.isNullOrEmpty(qaWebsitesBranchUsername)) {
			qaWebsitesBranchUsername = "liferay";
		}

		return JenkinsResultsParserUtil.combine(
			"https://github.com/", qaWebsitesBranchUsername,
			"/" + getBaseGitRepositoryName() + "/tree/", qaWebsitesBranchName);
	}

}