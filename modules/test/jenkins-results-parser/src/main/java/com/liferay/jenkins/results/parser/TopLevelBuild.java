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

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

import org.json.JSONObject;

/**
 * @author Kevin Yen
 */
public class TopLevelBuild extends BaseBuild {

	@Override
	public void archive(String archiveName) {
		super.archive(archiveName);

		try {
			writeArchiveFile(
				getJenkinsReport(), getArchivePath() + "/jenkins-report.html");
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to archive Jenkins report", ioe);
		}
	}

	@Override
	public String getDisplayName() {
		String displayName = super.getDisplayName();

		if (getParentBuild() != null) {
			displayName += "/" + getParameterValue("JENKINS_JOB_VARIANT");
		}

		return displayName;
	}

	@Override
	public String getGitHubMessage() {
		if (getParentBuild() == null) {
			try {
				return JenkinsResultsParserUtil.format(
					getGitHubMessageHeader());
			}
			catch (IOException ioe) {
				throw new RuntimeException(
					"Unable to format GitHubMessage HTML", ioe);
			}
		}

		return "";
	}

	public Map<String, String> getGitRepositoryDetailsTempMap(
		String repositoryName) {

		String repositoryType = getRepositoryType(repositoryName);

		String tempMapName = "git." + repositoryType + ".properties";

		return getTempMap(tempMapName);
	}

	public String getJenkinsReport() {
		try {
			return JenkinsResultsParserUtil.toString(
				JenkinsResultsParserUtil.getLocalURL(getJenkinsReportURL()));
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get Jenkins report", ioe);
		}
	}

	public String getJenkinsReportURL() {
		if (fromArchive) {
			return getBuildURL() + "/jenkins-report.html";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("https://");
		sb.append(getMaster());
		sb.append(".liferay.com/");
		sb.append("userContent/jobs/");
		sb.append(getJobName());
		sb.append("/builds/");
		sb.append(getBuildNumber());
		sb.append("/jenkins-report.html");

		return sb.toString();
	}

	@Override
	public String getStatusReport(int indentSize) {
		String statusReport = super.getStatusReport(indentSize);

		if (getDownstreamBuildCount(null) > 0) {
			while (statusReport.endsWith("\n")) {
				statusReport = statusReport.substring(
					0, statusReport.length() - 1);
			}

			statusReport += " / ";
		}

		return statusReport + "Update took " + _updateDuration +
			" milliseconds.\n";
	}

	@Override
	public JSONObject getTestReportJSONObject() {
		return null;
	}

	@Override
	public void update() {
		long start = System.currentTimeMillis();

		super.update();

		_updateDuration = System.currentTimeMillis() - start;
	}

	protected TopLevelBuild(String url) {
		this(url, null);
	}

	protected TopLevelBuild(String url, TopLevelBuild topLevelBuild) {
		super(url, topLevelBuild);
	}

	@Override
	protected void archiveJSON() {
		super.archiveJSON();

		try {
			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties();

			String repositoryTypes = buildProperties.getProperty(
				"repository.types");

			for (String repositoryType : repositoryTypes.split(",")) {
				try {
					JSONObject gitRepositoryDetailsJSONObject =
						JenkinsResultsParserUtil.toJSONObject(
							getGitRepositoryDetailsPropertiesTempMapURL(
								repositoryType));

					Set<?> keySet = gitRepositoryDetailsJSONObject.keySet();

					if (keySet.isEmpty()) {
						continue;
					}

					writeArchiveFile(
						gitRepositoryDetailsJSONObject.toString(4),
						getArchivePath() + "/git." + repositoryType +
							".properties.json");
				}
				catch (IOException ioe) {
					throw new RuntimeException(
						"Unable to create git." + repositoryType +
							".properties.json",
						ioe);
				}
			}
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get build properties", ioe);
		}
	}

	@Override
	protected ExecutorService getExecutorService() {
		return Executors.newFixedThreadPool(20);
	}

	@Override
	protected FailureMessageGenerator[] getFailureMessageGenerators() {
		if (getParentBuild() == null) {
			return _failureMessageGenerators;
		}

		return super.getFailureMessageGenerators();
	}

	protected Element getGitHubMessageHeader() {
		update();

		Element rootElement = new DefaultElement("html");

		Element resultElement = new DefaultElement("h1");

		rootElement.add(resultElement);

		getResult();

		if (!result.equals("SUCCESS")) {
			resultElement.addText("Some tests FAILED.");
		}
		else {
			resultElement.addText("All tests PASSED.");
		}

		Element buildTimeElement = new DefaultElement("p");

		buildTimeElement.addText(
			"Build Time: " +
				JenkinsResultsParserUtil.toDurationString(getDuration()));

		rootElement.add(buildTimeElement);

		Element baseBranchHeadingElement = new DefaultElement("h4");

		baseBranchHeadingElement.addText("Base Branch:");
		rootElement.add(baseBranchHeadingElement);

		Element branchDetailsElement = new DefaultElement("p");

		branchDetailsElement.addText("BranchName: ");

		Element branchAnchorElement = new DefaultElement("a");

		branchAnchorElement.addAttribute(
			"href",
			"https://github.com/liferay/" + getRepositoryName() + "/" +
				getBranchName());
		branchAnchorElement.addText(getBranchName());
		branchDetailsElement.add(branchAnchorElement);

		branchDetailsElement.add(new DefaultElement("br"));

		branchDetailsElement.addText("Branch GIT ID: ");

		Element gitIDAnchorElement = new DefaultElement("a");

		String repositorySHA = getRepositorySHA(getRepositoryName());

		gitIDAnchorElement.addAttribute(
			"href",
			"https://github.com/liferay/" + getRepositoryName() + "/commit/" +
				repositorySHA);

		gitIDAnchorElement.addText(repositorySHA);

		branchDetailsElement.add(gitIDAnchorElement);

		rootElement.add(branchDetailsElement);

		Element jobSummaryHeadingElement = new DefaultElement("h4");

		rootElement.add(jobSummaryHeadingElement);

		jobSummaryHeadingElement.addText("Job Summary:");

		Element jobSummaryListElement = new DefaultElement("ul");

		rootElement.add(jobSummaryListElement);

		List<Build> builds = new ArrayList<>();

		builds.add(this);

		builds.addAll(getDownstreamBuilds(null));

		for (Build downstreamBuild : builds) {
			Element jobSummaryListItemElement = new DefaultElement("li");

			jobSummaryListElement.add(jobSummaryListItemElement);

			jobSummaryListItemElement.add(
				downstreamBuild.getGitHubMessageBuildLink());
		}

		Element moreDetailsElement = new DefaultElement("h5");

		rootElement.add(moreDetailsElement);

		moreDetailsElement.addText("For more details click ");

		Element jenkinsReportLink = new DefaultElement("a");

		jenkinsReportLink.addAttribute("href", "jenkins.report.html");

		jenkinsReportLink.addText("here");

		moreDetailsElement.add(jenkinsReportLink);

		moreDetailsElement.addText(".");

		return rootElement;
	}

	protected String getGitRepositoryDetailsPropertiesTempMapURL(
		String repositoryType) {

		if (fromArchive) {
			StringBuilder sb = new StringBuilder();

			sb.append(getBuildURL());
			sb.append("git.");
			sb.append(repositoryType);
			sb.append(".properties.json");

			return sb.toString();
		}

		StringBuilder sb = new StringBuilder();

		sb.append(tempMapBaseURL);

		TopLevelBuild topLevelBuild = getTopLevelBuild();

		sb.append(topLevelBuild.getMaster());

		sb.append("/");
		sb.append(topLevelBuild.getJobName());

		sb.append("/");
		sb.append(topLevelBuild.getBuildNumber());

		sb.append("/");
		sb.append(topLevelBuild.getJobName());

		sb.append("/git.");
		sb.append(repositoryType);
		sb.append(".properties");

		return sb.toString();
	}

	@Override
	protected String getStartPropertiesTempMapURL() {
		if (fromArchive) {
			return getBuildURL() + "/start.properties.json";
		}

		StringBuilder sb = new StringBuilder();

		sb.append(tempMapBaseURL);
		sb.append(getMaster());
		sb.append("/");
		sb.append(getJobName());
		sb.append("/");
		sb.append(getBuildNumber());
		sb.append("/");
		sb.append(getJobName());
		sb.append("/");
		sb.append("start.properties");

		return sb.toString();
	}

	@Override
	protected String getStopPropertiesTempMapURL() {
		if (fromArchive) {
			return getBuildURL() + "/stop.properties.json";
		}

		StringBuilder sb = new StringBuilder();

		sb.append(tempMapBaseURL);
		sb.append(getMaster());
		sb.append("/");
		sb.append(getJobName());
		sb.append("/");
		sb.append(getBuildNumber());
		sb.append("/");
		sb.append(getJobName());
		sb.append("/");
		sb.append("stop.properties");

		return sb.toString();
	}

	@Override
	protected String getTempMapURL(String tempMapName) {
		String tempMapURL = super.getTempMapURL(tempMapName);

		if (tempMapURL != null) {
			return tempMapURL;
		}

		Matcher matcher = gitRepositoryTempMapNamePattern.matcher(tempMapName);

		if (matcher.find()) {
			return getGitRepositoryDetailsPropertiesTempMapURL(
				matcher.group("repositoryType"));
		}

		return null;
	}

	protected String getUpstreamBranchSHA() {
		String upstreamBranchSHA = getParameterValue(
			"GITHUB_UPSTREAM_BRANCH_SHA");

		if ((upstreamBranchSHA == null) || upstreamBranchSHA.isEmpty()) {
			Map<String, String> startPropertiesTempMap =
				getStartPropertiesTempMap();

			upstreamBranchSHA = startPropertiesTempMap.get(
				"GITHUB_UPSTREAM_BRANCH_SHA");
		}

		return upstreamBranchSHA;
	}

	protected static final Pattern gitRepositoryTempMapNamePattern =
		Pattern.compile("git\\.(?<repositoryType>.*)\\.properties");

	private static final FailureMessageGenerator[] _failureMessageGenerators = {
		new PoshiValidationFailureMessageGenerator(),
		new DownstreamFailureMessageGenerator(),

		new GenericFailureMessageGenerator()
	};

	private long _updateDuration;

}