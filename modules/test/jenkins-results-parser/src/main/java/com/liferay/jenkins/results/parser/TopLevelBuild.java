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

import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

					Set<?> set = gitRepositoryDetailsJSONObject.keySet();

					if (set.isEmpty()) {
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
			Map<String, String> startPropertiesMap = getStartPropertiesMap();

			upstreamBranchSHA = startPropertiesMap.get(
				"GITHUB_UPSTREAM_BRANCH_SHA");
		}

		return upstreamBranchSHA;
	}

	protected static final Pattern gitRepositoryTempMapNamePattern =
		Pattern.compile("git\\.(?<repositoryType>.*)\\.properties");

	private long _updateDuration;

}