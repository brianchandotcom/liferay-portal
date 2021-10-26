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

package com.liferay.jenkins.results.parser.failure.message.generator;

import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.Dom4JUtil;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.PullRequest;
import com.liferay.jenkins.results.parser.SourceFormatBuild;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.Workspace;
import com.liferay.jenkins.results.parser.WorkspaceBuild;
import com.liferay.jenkins.results.parser.WorkspaceGitRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;

/**
 * @author Peter Yoo
 */
public abstract class BaseFailureMessageGenerator
	implements FailureMessageGenerator {

	@Override
	public Element getMessageElement(Build build) {
		return getMessageElement(build.getConsoleText());
	}

	@Override
	public Element getMessageElement(String consoleText) {
		return null;
	}

	@Override
	public boolean isGenericCIFailure() {
		return false;
	}

	protected Element getBaseBranchAnchorElement(TopLevelBuild topLevelBuild) {
		StringBuilder sb = new StringBuilder();

		sb.append("https://github.com/");

		if (!topLevelBuild.isFromArchive() &&
			(topLevelBuild instanceof WorkspaceBuild)) {

			WorkspaceBuild workspaceBuild = (WorkspaceBuild)topLevelBuild;

			Workspace workspace = workspaceBuild.getWorkspace();

			WorkspaceGitRepository workspaceGitRepository =
				workspace.getPrimaryWorkspaceGitRepository();

			return Dom4JUtil.getNewAnchorElement(
				workspaceGitRepository.getGitHubURL(),
				JenkinsResultsParserUtil.combine(
					workspaceGitRepository.getSenderBranchUsername(), "/",
					workspaceGitRepository.getSenderBranchName()));
		}

		Map<String, String> pullRequestDetailsMap = null;

		if (topLevelBuild instanceof SourceFormatBuild) {
			SourceFormatBuild sourceFormatBuild =
				(SourceFormatBuild)topLevelBuild;

			pullRequestDetailsMap = getDetailsMapFromPullRequest(
				sourceFormatBuild.getPullRequest());
		}
		else {
			pullRequestDetailsMap =
				topLevelBuild.getBaseGitRepositoryDetailsTempMap();
		}

		String baseGitRepositoryName = topLevelBuild.getBaseGitRepositoryName();

		sb.append(pullRequestDetailsMap.get("github.origin.name"));

		sb.append("/");
		sb.append(baseGitRepositoryName);
		sb.append("/tree/");
		sb.append(pullRequestDetailsMap.get("github.sender.branch.name"));

		String url = sb.toString();

		sb = new StringBuilder();

		sb.append(pullRequestDetailsMap.get("github.origin.name"));
		sb.append("/");
		sb.append(pullRequestDetailsMap.get("github.sender.branch.name"));

		return Dom4JUtil.getNewAnchorElement(url, sb.toString());
	}

	protected String getConsoleTextSnippet(
		String consoleText, boolean truncateTop, int end) {

		if (end == -1) {
			end = consoleText.length();
		}

		int start = getSnippetStart(consoleText, end);

		return getConsoleTextSnippet(consoleText, truncateTop, start, end);
	}

	protected String getConsoleTextSnippet(
		String consoleText, boolean truncateTop, int start, int end) {

		return "<pre><code>" +
			_getConsoleTextSnippet(consoleText, truncateTop, start, end) +
				"</code></pre>";
	}

	protected Element getConsoleTextSnippetElement(
		String consoleText, boolean truncateTop, int start, int end) {

		return Dom4JUtil.toCodeSnippetElement(
			_getConsoleTextSnippet(consoleText, truncateTop, start, end));
	}

	protected Element getConsoleTextSnippetElementByEnd(
		String consoleText, boolean truncateTop, int end) {

		if (end == -1) {
			end = consoleText.length();
		}

		int start = getSnippetStart(consoleText, end);

		return getConsoleTextSnippetElement(
			consoleText, truncateTop, start, end);
	}

	protected Element getConsoleTextSnippetElementByStart(
		String consoleText, int start) {

		return Dom4JUtil.toCodeSnippetElement(
			_getConsoleTextSnippet(
				consoleText, false, start, consoleText.length() - 1));
	}

	protected Map<String, String> getDetailsMapFromPullRequest(
		PullRequest pullRequest) {

		Map<String, String> detailsMap = new HashMap<>();

		detailsMap.put("github.origin.name", pullRequest.getSenderUsername());
		detailsMap.put(
			"github.sender.branch.name", pullRequest.getSenderBranchName());

		return detailsMap;
	}

	protected Element getGitCommitPluginsAnchorElement(
		TopLevelBuild topLevelBuild) {

		String portalGitRepositoryName =
			topLevelBuild.getBaseGitRepositoryName();

		String portalSenderBranchUsername;
		String portalSenderBranchName;

		if (!topLevelBuild.isFromArchive() &&
			(topLevelBuild instanceof WorkspaceBuild)) {

			WorkspaceBuild workspaceBuild = (WorkspaceBuild)topLevelBuild;

			Workspace workspace = workspaceBuild.getWorkspace();

			WorkspaceGitRepository workspaceGitRepository =
				workspace.getPrimaryWorkspaceGitRepository();

			portalSenderBranchUsername =
				workspaceGitRepository.getSenderBranchUsername();
			portalSenderBranchName =
				workspaceGitRepository.getSenderBranchName();
		}
		else {
			Map<String, String> portalGitRepositoryGitDetailsTempMap =
				topLevelBuild.getBaseGitRepositoryDetailsTempMap();

			portalSenderBranchUsername =
				portalGitRepositoryGitDetailsTempMap.get("github.origin.name");
			portalSenderBranchName = portalGitRepositoryGitDetailsTempMap.get(
				"github.sender.branch.name");
		}

		Element gitCommitPluginsAnchorElement = Dom4JUtil.getNewElement("a");

		StringBuilder sb = new StringBuilder();

		sb.append("https://github.com/");
		sb.append(portalSenderBranchUsername);
		sb.append("/");
		sb.append(portalGitRepositoryName);
		sb.append("/blob/");
		sb.append(portalSenderBranchName);
		sb.append("/git-commit-plugins");

		gitCommitPluginsAnchorElement.addAttribute("href", sb.toString());

		gitCommitPluginsAnchorElement.addText("git-commit-plugins");

		return gitCommitPluginsAnchorElement;
	}

	protected int getSnippetStart(String consoleText, int end) {
		int start = 0;

		Matcher matcher = _targetOutputStartPattern.matcher(consoleText);

		while (matcher.find()) {
			int x = matcher.start() + 1;

			if (x >= end) {
				return start;
			}

			start = x;
		}

		return start;
	}

	protected static final int CHARS_CONSOLE_TEXT_SNIPPET_SIZE_MAX = 2500;

	private String _getConsoleTextSnippet(
		String consoleText, boolean truncateTop, int start, int end) {

		if ((end - start) > CHARS_CONSOLE_TEXT_SNIPPET_SIZE_MAX) {
			if (truncateTop) {
				start = end - CHARS_CONSOLE_TEXT_SNIPPET_SIZE_MAX;

				start = consoleText.indexOf("\n", start);
			}
			else {
				end = start + CHARS_CONSOLE_TEXT_SNIPPET_SIZE_MAX;

				int newlineEnd = consoleText.lastIndexOf("\n", end);

				if (newlineEnd != -1) {
					end = newlineEnd;
				}
			}
		}

		consoleText = consoleText.substring(start, end);

		consoleText = consoleText.replaceFirst("^\\s*\\n", "");
		consoleText = consoleText.replaceFirst("\\n\\s*$", "");

		return consoleText;
	}

	private static final Pattern _targetOutputStartPattern = Pattern.compile(
		"\\n[a-z\\-\\.]+\\:\\n");

}