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

import com.liferay.jenkins.results.parser.failure.message.generator.FailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.GenericFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.PoshiValidationFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.RebaseFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.SourceFormatFailureMessageGenerator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;

/**
 * @author Cesar Polanco
 */
public class SourceFormatBuild
	extends DefaultTopLevelBuild
	implements PortalBranchInformationBuild, PullRequestBuild, WorkspaceBuild {

	public boolean bypassCITestRelevant() {
		Workspace workspace = getWorkspace();

		WorkspaceGitRepository workspaceGitRepository =
			workspace.getPrimaryWorkspaceGitRepository();

		if (!(workspaceGitRepository instanceof PortalWorkspaceGitRepository)) {
			return false;
		}

		PortalWorkspaceGitRepository portalWorkspaceGitRepository =
			(PortalWorkspaceGitRepository)workspaceGitRepository;

		return portalWorkspaceGitRepository.bypassCITestRelevant();
	}

	@Override
	public String getBaseGitRepositoryName() {
		PullRequest pullRequest = getPullRequest();

		return pullRequest.getGitHubRemoteGitRepositoryName();
	}

	@Override
	public String getBaseGitRepositorySHA(String gitRepositoryName) {
		if (_baseGitRepositorySHA != null) {
			return _baseGitRepositorySHA;
		}

		if (!fromArchive) {
			Workspace workspace = getWorkspace();

			WorkspaceGitRepository primaryWorkspaceGitRepository =
				workspace.getPrimaryWorkspaceGitRepository();

			_baseGitRepositorySHA =
				primaryWorkspaceGitRepository.getBaseBranchSHA();

			return _baseGitRepositorySHA;
		}

		String consoleText = getConsoleText();

		for (String line : consoleText.split("\\s*\\n\\s*")) {
			Matcher matcher = _gitHubUpstreamBranchShaPattern.matcher(line);

			if (matcher.find()) {
				_baseGitRepositorySHA = matcher.group("sha");

				return _baseGitRepositorySHA;
			}
		}

		throw new RuntimeException(
			"Unable to find Source Format Base Git Repository SHA");
	}

	@Override
	public String getBranchName() {
		PullRequest pullRequest = getPullRequest();

		return pullRequest.getUpstreamRemoteGitBranchName();
	}

	@Override
	public Element[] getBuildFailureElements() {
		return new Element[] {getFailureMessageElement()};
	}

	@Override
	public Job.BuildProfile getBuildProfile() {
		return Job.BuildProfile.DXP;
	}

	@Override
	public BranchInformation getPortalBaseBranchInformation() {
		return null;
	}

	@Override
	public BranchInformation getPortalBranchInformation() {
		Workspace workspace = getWorkspace();

		return new WorkspaceBranchInformation(
			workspace.getPrimaryWorkspaceGitRepository());
	}

	@Override
	public PullRequest getPullRequest() {
		if (_pullRequest != null) {
			return _pullRequest;
		}

		_pullRequest = PullRequestFactory.newPullRequest(
			getParameterValue("PULL_REQUEST_URL"));

		return _pullRequest;
	}

	@Override
	public String getTestSuiteName() {
		return _NAME_TEST_SUITE;
	}

	@Override
	public Element getTopGitHubMessageElement() {
		update();

		Element detailsElement = Dom4JUtil.getNewElement(
			"details", null,
			Dom4JUtil.getNewElement(
				"summary", null, "Click here for more details."),
			Dom4JUtil.getNewElement("h4", null, "Base Branch:"),
			getBaseBranchDetailsElement(),
			Dom4JUtil.getNewElement("h4", null, "Sender Branch:"),
			getSenderBranchDetailsElement());

		String result = getResult();
		int successCount = 0;

		if (result.equals("SUCCESS")) {
			successCount++;
		}

		Dom4JUtil.addToElement(
			detailsElement, String.valueOf(successCount), " out of ",
			String.valueOf(getDownstreamBuildCountByResult(null) + 1),
			"jobs PASSED");

		if (result.equals("SUCCESS")) {
			Dom4JUtil.addToElement(
				detailsElement, getSuccessfulJobSummaryElement());
		}
		else {
			Dom4JUtil.addToElement(
				detailsElement, getFailedJobSummaryElement());
		}

		Dom4JUtil.addToElement(detailsElement, getMoreDetailsElement());

		if (!result.equals("SUCCESS")) {
			Dom4JUtil.addToElement(
				detailsElement, (Object[])getBuildFailureElements());
		}

		return Dom4JUtil.getNewElement(
			"html", null, getResultElement(), detailsElement);
	}

	@Override
	public Workspace getWorkspace() {
		PullRequest pullRequest = getPullRequest();

		Workspace workspace = WorkspaceFactory.newWorkspace(
			pullRequest.getGitRepositoryName(),
			pullRequest.getUpstreamRemoteGitBranchName(), getJobName());

		WorkspaceGitRepository workspaceGitRepository =
			workspace.getPrimaryWorkspaceGitRepository();

		workspaceGitRepository.setGitHubURL(pullRequest.getHtmlURL());

		String senderBranchSHA = getParameterValue("GITHUB_SENDER_BRANCH_SHA");

		if (JenkinsResultsParserUtil.isSHA(senderBranchSHA)) {
			workspaceGitRepository.setSenderBranchSHA(senderBranchSHA);
		}

		String upstreamBranchSHA = getParameterValue(
			"GITHUB_UPSTREAM_BRANCH_SHA");

		if (JenkinsResultsParserUtil.isSHA(upstreamBranchSHA)) {
			workspaceGitRepository.setBaseBranchSHA(upstreamBranchSHA);
		}

		return workspace;
	}

	protected SourceFormatBuild(String url) {
		this(url, null);
	}

	protected SourceFormatBuild(String url, TopLevelBuild topLevelBuild) {
		super(url, topLevelBuild);
	}

	@Override
	protected FailureMessageGenerator[] getFailureMessageGenerators() {
		return new FailureMessageGenerator[] {
			new PoshiValidationFailureMessageGenerator(),
			new RebaseFailureMessageGenerator(),
			new SourceFormatFailureMessageGenerator(),
			//
			new GenericFailureMessageGenerator()
		};
	}

	protected Element getSenderBranchDetailsElement() {
		PullRequest pullRequest = getPullRequest();

		String gitHubRemoteGitRepositoryName =
			pullRequest.getGitHubRemoteGitRepositoryName();
		String senderBranchName = pullRequest.getSenderBranchName();
		String senderUsername = pullRequest.getSenderUsername();

		String senderBranchURL = JenkinsResultsParserUtil.combine(
			"https://github.com/", senderUsername, "/",
			gitHubRemoteGitRepositoryName, "/tree/", senderBranchName);

		Workspace workspace = getWorkspace();

		WorkspaceGitRepository primaryWorkspaceGitRepository =
			workspace.getPrimaryWorkspaceGitRepository();

		String senderSHA = primaryWorkspaceGitRepository.getSenderBranchSHA();

		String senderCommitURL = JenkinsResultsParserUtil.combine(
			"https://github.com/", senderUsername, "/",
			gitHubRemoteGitRepositoryName, "/commit/", senderSHA);

		return Dom4JUtil.getNewElement(
			"p", null, "Branch Name: ",
			Dom4JUtil.getNewAnchorElement(senderBranchURL, senderBranchName),
			Dom4JUtil.getNewElement("br"), "Branch GIT ID: ",
			Dom4JUtil.getNewAnchorElement(senderCommitURL, senderSHA));
	}

	private static final String _NAME_TEST_SUITE = "sf";

	private static final Pattern _gitHubUpstreamBranchShaPattern =
		Pattern.compile(
			"\\[beanshell\\] GITHUB_UPSTREAM_BRANCH_SHA=" +
				"(?<sha>[0-9a-f]{7,40})");

	private String _baseGitRepositorySHA;
	private PullRequest _pullRequest;

}