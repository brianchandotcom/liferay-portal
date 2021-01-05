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

package com.liferay.jenkins.results.parser.spira.result;

import com.liferay.jenkins.results.parser.AnalyticsCloudBranchInformationBuild;
import com.liferay.jenkins.results.parser.AxisBuild;
import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.ParallelExecutor;
import com.liferay.jenkins.results.parser.PluginsBranchInformationBuild;
import com.liferay.jenkins.results.parser.PortalBranchInformationBuild;
import com.liferay.jenkins.results.parser.PortalFixpackRelease;
import com.liferay.jenkins.results.parser.PortalRelease;
import com.liferay.jenkins.results.parser.PullRequest;
import com.liferay.jenkins.results.parser.QAWebsitesBranchInformationBuild;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.spira.SpiraProject;
import com.liferay.jenkins.results.parser.spira.SpiraRelease;
import com.liferay.jenkins.results.parser.spira.SpiraReleaseBuild;

import java.io.IOException;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseSpiraTestResultDetails
	implements SpiraTestResultDetails {

	@Override
	public String getDetails() {
		ParallelExecutor<Map.Entry<String, String>> parallelExecutor =
			new ParallelExecutor<>(getCallables(), _executorService);

		Map<String, String> summaries = new TreeMap<>();

		for (Map.Entry<String, String> entry : parallelExecutor.execute()) {
			summaries.put(entry.getKey(), entry.getValue());
		}

		StringBuilder sb = new StringBuilder();

		for (String summary : summaries.values()) {
			if (summary == null) {
				continue;
			}

			sb.append(summary);
		}

		return sb.toString();
	}

	protected BaseSpiraTestResultDetails(SpiraTestResult spiraTestResult) {
		_spiraTestResult = spiraTestResult;

		_spiraBuildResult = _spiraTestResult.getSpiraBuildResult();
	}

	protected String getArtifactBaseURL() {
		Build build = _spiraTestResult.getBuild();

		if (build == null) {
			return null;
		}

		return String.valueOf(build.getArtifactsBaseURL());
	}

	protected String getArtifactBaseURLContent() {
		if (_artifactBaseURLContent != null) {
			return _artifactBaseURLContent;
		}

		String artifactBaseURL = getArtifactBaseURL();

		if (artifactBaseURL == null) {
			return _artifactBaseURLContent = "";
		}

		try {
			_artifactBaseURLContent = JenkinsResultsParserUtil.toString(
				getArtifactBaseURL() + "/", true, 0, 0, 0);
		}
		catch (IOException ioException) {
			_artifactBaseURLContent = "";
		}

		return _artifactBaseURLContent;
	}

	protected List<Callable<Map.Entry<String, String>>> getCallables() {
		List<Callable<Map.Entry<String, String>>> callables = new ArrayList<>();

		callables.add(
			new Callable() {

				@Override
				public Map.Entry<String, String> call() {
					return new AbstractMap.SimpleEntry<>(
						"GitHubSummary", _getGitHubSummary());
				}

			});
		callables.add(
			new Callable() {

				@Override
				public Map.Entry<String, String> call() {
					return new AbstractMap.SimpleEntry<>(
						"JenkinsSummary", _getJenkinsSummary());
				}

			});
		callables.add(
			new Callable() {

				@Override
				public Map.Entry<String, String> call() {
					return new AbstractMap.SimpleEntry<>(
						"PortalReleaseSummary", _getPortalReleaseSummary());
				}

			});
		callables.add(
			new Callable() {

				@Override
				public Map.Entry<String, String> call() {
					return new AbstractMap.SimpleEntry<>(
						"SpiraSummary", _getSpiraSummary());
				}

			});
		callables.add(
			new Callable() {

				@Override
				public Map.Entry<String, String> call() {
					return new AbstractMap.SimpleEntry<>(
						"TestraySummary", _getTestraySummary());
				}

			});
		callables.add(
			new Callable() {

				@Override
				public Map.Entry<String, String> call() {
					return new AbstractMap.SimpleEntry<>(
						"TestFailuresSummary", getTestFailuresSummary());
				}

			});
		callables.add(
			new Callable() {

				@Override
				public Map.Entry<String, String> call() {
					return new AbstractMap.SimpleEntry<>(
						"TestMethodsSummary", getTestMethodsSummary());
				}

			});
		callables.add(
			new Callable() {

				@Override
				public Map.Entry<String, String> call() {
					return new AbstractMap.SimpleEntry<>(
						"TestWarningsSummary", getTestWarningsSummary());
				}

			});

		return callables;
	}

	protected String getTestFailuresSummary() {
		return "";
	}

	protected String getTestMethodsSummary() {
		return "";
	}

	protected List<String> getTestrayListItems() {
		return new ArrayList<>();
	}

	protected String getTestWarningsSummary() {
		return "";
	}

	private String _getGitHubBranchInformation(
		String repositoryType, Build.BranchInformation branchInformation) {

		if (branchInformation == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("<li>");
		sb.append("<details>");
		sb.append("<summary>");
		sb.append(repositoryType);
		sb.append(": ");
		sb.append(branchInformation.getRepositoryName());
		sb.append("/");
		sb.append(branchInformation.getUpstreamBranchName());

		PullRequest pullRequest = null;

		if (repositoryType.equals("Portal Branch")) {
			pullRequest = _spiraBuildResult.getPullRequest();
		}

		if (pullRequest != null) {
			sb.append(" (<a href=\"");
			sb.append(pullRequest.getHtmlURL());
			sb.append("\">");
			sb.append(pullRequest.getReceiverUsername());
			sb.append("#");
			sb.append(pullRequest.getNumber());
			sb.append("</a>)");
		}
		else {
			sb.append(" (<a href=\"https://github.com/");
			sb.append(branchInformation.getSenderUsername());
			sb.append("/");
			sb.append(branchInformation.getRepositoryName());
			sb.append("/tree/");
			sb.append(branchInformation.getSenderBranchName());
			sb.append("\">");
			sb.append(branchInformation.getRepositoryName());
			sb.append("/");
			sb.append(branchInformation.getSenderBranchName());
			sb.append("</a>)");
		}

		sb.append("</summary>");

		sb.append("<ul>");

		sb.append("<li>Receiver Username: ");
		sb.append(branchInformation.getReceiverUsername());
		sb.append("</li>");

		sb.append("<li>Sender Branch Name: ");
		sb.append(branchInformation.getSenderBranchName());
		sb.append("</li>");

		sb.append("<li>Sender Branch SHA: ");
		sb.append(branchInformation.getSenderBranchSHA());
		sb.append("</li>");

		sb.append("<li>Sender Username: ");
		sb.append(branchInformation.getSenderUsername());
		sb.append("</li>");

		sb.append("<li>Upstream Branch Name: ");
		sb.append(branchInformation.getUpstreamBranchName());
		sb.append("</li>");

		sb.append("<li>Upstream Branch SAH: ");
		sb.append(branchInformation.getUpstreamBranchSHA());
		sb.append("</li>");

		sb.append("</ul>");

		sb.append("</details>");
		sb.append("</li>");

		return sb.toString();
	}

	private String _getGitHubSummary() {
		StringBuilder sb = new StringBuilder();

		sb.append("<h4>GitHub</h4><ul>");

		TopLevelBuild topLevelBuild = _spiraBuildResult.getTopLevelBuild();

		if (topLevelBuild instanceof PortalBranchInformationBuild) {
			PortalBranchInformationBuild portalBranchInformationBuild =
				(PortalBranchInformationBuild)topLevelBuild;

			sb.append(
				_getGitHubBranchInformation(
					"Portal Branch",
					portalBranchInformationBuild.getPortalBranchInformation()));

			sb.append(
				_getGitHubBranchInformation(
					"Portal Base Branch",
					portalBranchInformationBuild.
						getPortalBaseBranchInformation()));
		}

		if (topLevelBuild instanceof AnalyticsCloudBranchInformationBuild) {
			AnalyticsCloudBranchInformationBuild
				analyticsCloudBranchInformationBuild =
					(AnalyticsCloudBranchInformationBuild)topLevelBuild;

			sb.append(
				_getGitHubBranchInformation(
					"OSB Asah Branch",
					analyticsCloudBranchInformationBuild.
						getOSBAsahBranchInformation()));

			sb.append(
				_getGitHubBranchInformation(
					"OSB Faro Branch",
					analyticsCloudBranchInformationBuild.
						getOSBFaroBranchInformation()));
		}

		if (topLevelBuild instanceof PluginsBranchInformationBuild) {
			PluginsBranchInformationBuild pluginsBranchInformationBuild =
				(PluginsBranchInformationBuild)topLevelBuild;

			sb.append(
				_getGitHubBranchInformation(
					"Plugins Branch",
					pluginsBranchInformationBuild.
						getPluginsBranchInformation()));
		}

		if (topLevelBuild instanceof QAWebsitesBranchInformationBuild) {
			QAWebsitesBranchInformationBuild qaWebsitesBranchInformationBuild =
				(QAWebsitesBranchInformationBuild)topLevelBuild;

			sb.append(
				_getGitHubBranchInformation(
					"QA Websites Branch",
					qaWebsitesBranchInformationBuild.
						getQAWebsitesBranchInformation()));
		}

		sb.append("</ul>");

		return sb.toString();
	}

	private String _getJenkinsSummary() {
		StringBuilder sb = new StringBuilder();

		TopLevelBuild topLevelBuild = _spiraBuildResult.getTopLevelBuild();

		sb.append("<h4>Jenkins</h4><ul>");

		Build build = _spiraTestResult.getBuild();

		if (build != null) {
			String buildName = JenkinsResultsParserUtil.combine(
				build.getJobName(), "/",
				String.valueOf(build.getBuildNumber()));

			if (build instanceof AxisBuild) {
				AxisBuild axisBuild = (AxisBuild)build;

				buildName += "/" + axisBuild.getAxisNumber();
			}

			sb.append("<li>Jenkins Build: <a href=\"");
			sb.append(build.getBuildURL());
			sb.append("\" target=\"_blank\">");
			sb.append(buildName);
			sb.append("</a></li>");

			String artifactBaseURLContent = getArtifactBaseURLContent();

			if (artifactBaseURLContent.contains("jenkins-console.txt.gz")) {
				sb.append("<li>Jenkins Build Console: <a href=\"");
				sb.append(getArtifactBaseURL());
				sb.append("/jenkins-console.txt.gz\" target=\"_blank\">");
				sb.append(buildName);
				sb.append(" (console)</a></li>");
			}
		}

		sb.append("<li>Jenkins Report: <a href=\"");
		sb.append(topLevelBuild.getJenkinsReportURL());
		sb.append("\" target=\"_blank\">jenkins-report.html</a></li>");

		Build controllerBuild = topLevelBuild.getControllerBuild();

		if (controllerBuild != null) {
			sb.append("<li>Jenkins Controller Build: <a href=\"");
			sb.append(controllerBuild.getBuildURL());
			sb.append("\" target=\"_blank\">");
			sb.append(controllerBuild.getJobName());
			sb.append("/");
			sb.append(controllerBuild.getBuildNumber());
			sb.append("</a></li>");
		}

		sb.append("<li>Jenkins Top Level Build: <a href=\"");
		sb.append(topLevelBuild.getBuildURL());
		sb.append("\" target=\"_blank\">");
		sb.append(topLevelBuild.getJobName());
		sb.append("/");
		sb.append(topLevelBuild.getBuildNumber());
		sb.append("</a></li>");

		String buildURL = System.getenv("BUILD_URL");

		if (buildURL != null) {
			sb.append("<li>Jenkins Publish URL: <a href=\"");
			sb.append(buildURL);
			sb.append("\">");
			sb.append(System.getenv("JOB_NAME"));
			sb.append("/");
			sb.append(System.getenv("BUILD_NUMBER"));
			sb.append("</a></li>");
		}

		sb.append("</ul>");

		return sb.toString();
	}

	private String _getPortalReleaseSummary() {
		StringBuilder sb = new StringBuilder();

		PortalRelease portalRelease = _spiraBuildResult.getPortalRelease();

		if (portalRelease != null) {
			sb.append("<h4>Portal Release (");
			sb.append(portalRelease.getPortalVersion());
			sb.append(")</h4>");
			sb.append(portalRelease.getHTMLReport());
		}

		PortalFixpackRelease portalFixpackRelease =
			_spiraBuildResult.getPortalFixpackRelease();

		if (portalFixpackRelease != null) {
			sb.append("<h4>Portal Fixpack Release (");
			sb.append(portalFixpackRelease.getPortalFixpackVersion());
			sb.append(")</h4>");
			sb.append(portalFixpackRelease.getHTMLReport());
		}

		return sb.toString();
	}

	private String _getSpiraSummary() {
		StringBuilder sb = new StringBuilder();

		SpiraProject spiraProject = _spiraBuildResult.getSpiraProject();

		sb.append("<h4>Spira</h4><ul>");

		sb.append("<li>Project: <a href=\"");
		sb.append(spiraProject.getURL());
		sb.append("\" target=\"_blank\">");
		sb.append(spiraProject.getName());
		sb.append("</a></li>");

		SpiraRelease spiraRelease = _spiraBuildResult.getSpiraRelease();

		if (spiraRelease != null) {
			sb.append("<li>Release: <a href=\"");
			sb.append(spiraRelease.getURL());
			sb.append("\" target=\"_blank\">");
			sb.append(spiraRelease.getPath());
			sb.append("</a></li>");
		}

		SpiraReleaseBuild spiraReleaseBuild =
			_spiraBuildResult.getSpiraReleaseBuild();

		if (spiraReleaseBuild != null) {
			sb.append("<li>Release Build: <a href=\"");
			sb.append(spiraReleaseBuild.getURL());
			sb.append("\" target=\"_blank\">");
			sb.append(spiraReleaseBuild.getName());
			sb.append("</a></li>");
		}

		sb.append("</ul>");

		return sb.toString();
	}

	private String _getTestraySummary() {
		List<String> testrayListItems = getTestrayListItems();

		if (testrayListItems.isEmpty()) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("<h4>Testray</h4><ul>");

		for (String testrayListItem : testrayListItems) {
			sb.append(testrayListItem);
		}

		sb.append("</ul>");

		return sb.toString();
	}

	private static final Integer _THREAD_COUNT = 100;

	private static final ExecutorService _executorService =
		JenkinsResultsParserUtil.getNewThreadPoolExecutor(_THREAD_COUNT, true);

	private String _artifactBaseURLContent;
	private final SpiraBuildResult _spiraBuildResult;
	private final SpiraTestResult _spiraTestResult;

}