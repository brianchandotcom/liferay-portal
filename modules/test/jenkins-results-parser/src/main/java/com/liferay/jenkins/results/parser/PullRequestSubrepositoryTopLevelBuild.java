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

/**
 * @author Michael Hashimoto
 */
public class PullRequestSubrepositoryTopLevelBuild
	extends DefaultTopLevelBuild
	implements AnalyticsCloudBranchInformationBuild,
			   PluginsBranchInformationBuild, PortalBranchInformationBuild,
			   PullRequestBuild, WorkspaceBuild {

	public PullRequestSubrepositoryTopLevelBuild(
		String url, TopLevelBuild topLevelBuild) {

		super(url, topLevelBuild);
	}

	@Override
	public BranchInformation getOSBAsahBranchInformation() {
		Workspace workspace = getWorkspace();

		return new WorkspaceBranchInformation(
			workspace.getWorkspaceGitRepository(
				"com-liferay-osb-asah-private"));
	}

	@Override
	public BranchInformation getOSBFaroBranchInformation() {
		Workspace workspace = getWorkspace();

		return new WorkspaceBranchInformation(
			workspace.getWorkspaceGitRepository(
				"com-liferay-osb-faro-private"));
	}

	@Override
	public BranchInformation getPluginsBranchInformation() {
		Workspace workspace = getWorkspace();

		if (!(workspace instanceof SubrepositoryWorkspace)) {
			return null;
		}

		SubrepositoryWorkspace subrepositoryWorkspace =
			(SubrepositoryWorkspace)workspace;

		return new WorkspaceBranchInformation(
			subrepositoryWorkspace.getPluginsWorkspaceGitRepository());
	}

	@Override
	public BranchInformation getPortalBaseBranchInformation() {
		return null;
	}

	@Override
	public BranchInformation getPortalBranchInformation() {
		Workspace workspace = getWorkspace();

		if (!(workspace instanceof SubrepositoryWorkspace)) {
			return null;
		}

		SubrepositoryWorkspace subrepositoryWorkspace =
			(SubrepositoryWorkspace)workspace;

		return new WorkspaceBranchInformation(
			subrepositoryWorkspace.getPortalWorkspaceGitRepository());
	}

	@Override
	public PullRequest getPullRequest() {
		if (_pullRequest != null) {
			return _pullRequest;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("https://github.com/");
		sb.append(getParameterValue("GITHUB_RECEIVER_USERNAME"));
		sb.append("/");
		sb.append(getParameterValue("REPOSITORY_NAME"));
		sb.append("/pull/");
		sb.append(getParameterValue("GITHUB_PULL_REQUEST_NUMBER"));

		_pullRequest = PullRequestFactory.newPullRequest(sb.toString());

		return _pullRequest;
	}

	@Override
	public Workspace getWorkspace() {
		PullRequest pullRequest = getPullRequest();

		Workspace workspace = WorkspaceFactory.newWorkspace(
			pullRequest.getGitRepositoryName(),
			pullRequest.getUpstreamRemoteGitBranchName(), getJobName());

		if (workspace instanceof SubrepositoryWorkspace) {
			SubrepositoryWorkspace subrepositoryWorkspace =
				(SubrepositoryWorkspace)workspace;

			subrepositoryWorkspace.setBuildProfile(getBuildProfile());
		}

		WorkspaceGitRepository workspaceGitRepository =
			workspace.getPrimaryWorkspaceGitRepository();

		workspaceGitRepository.setGitHubURL(pullRequest.getHtmlURL());

		String senderBranchSHA = _getSenderBranchSHA();

		if (JenkinsResultsParserUtil.isSHA(senderBranchSHA)) {
			workspaceGitRepository.setSenderBranchSHA(senderBranchSHA);
		}

		String upstreamBranchSHA = _getUpstreamBranchSHA();

		if (JenkinsResultsParserUtil.isSHA(upstreamBranchSHA)) {
			workspaceGitRepository.setBaseBranchSHA(upstreamBranchSHA);
		}

		return workspace;
	}

	private String _getSenderBranchSHA() {
		String senderBranchSHA = getParameterValue("GITHUB_SENDER_BRANCH_SHA");

		if (JenkinsResultsParserUtil.isSHA(senderBranchSHA)) {
			return senderBranchSHA;
		}

		return null;
	}

	private String _getUpstreamBranchSHA() {
		String upstreamBranchSHA = getParameterValue(
			"GITHUB_UPSTREAM_BRANCH_SHA");

		if (JenkinsResultsParserUtil.isSHA(upstreamBranchSHA)) {
			return upstreamBranchSHA;
		}

		return null;
	}

	private PullRequest _pullRequest;

}