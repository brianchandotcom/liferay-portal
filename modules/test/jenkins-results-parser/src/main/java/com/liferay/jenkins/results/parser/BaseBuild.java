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

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import org.dom4j.Element;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Kevin Yen
 */
public abstract class BaseBuild implements Build {

	@Override
	public void addDownstreamBuilds(String... urls) {
		final Build thisBuild = this;

		List<Callable<Build>> callables = new ArrayList<>(urls.length);

		for (String url : urls) {
			try {
				url = JenkinsResultsParserUtil.getLocalURL(
					JenkinsResultsParserUtil.decode(url));
			}
			catch (UnsupportedEncodingException uee) {
				throw new IllegalArgumentException(
					"Unable to decode " + url, uee);
			}

			if (!hasBuildURL(url)) {
				final String buildURL = url;

				Callable<Build> callable = new Callable<Build>() {

					@Override
					public Build call() {
						return BuildFactory.newBuild(buildURL, thisBuild);
					}

				};

				callables.add(callable);
			}
		}

		ParallelExecutor<Build> parallelExecutor = new ParallelExecutor<>(
			callables, getExecutorService());

		downstreamBuilds.addAll(parallelExecutor.execute());
	}

	public abstract void addTimelineData(BaseBuild.TimelineData timelineData);

	@Override
	public void archive(final String archiveName) {
		if (!_status.equals("completed")) {
			throw new RuntimeException("Invalid build status: " + _status);
		}

		this.archiveName = archiveName;

		File archiveDir = new File(getArchivePath());

		if (archiveDir.exists()) {
			archiveDir.delete();
		}

		if (downstreamBuilds != null) {
			List<Callable<Object>> callables = new ArrayList<>(
				downstreamBuilds.size());

			for (final Build downstreamBuild : downstreamBuilds) {
				Callable<Object> callable = new Callable<Object>() {

					@Override
					public Object call() {
						downstreamBuild.archive(archiveName);

						return null;
					}

				};

				callables.add(callable);
			}

			ParallelExecutor<Object> parallelExecutor = new ParallelExecutor<>(
				callables, getExecutorService());

			parallelExecutor.execute();
		}

		try {
			writeArchiveFile(
				String.valueOf(System.currentTimeMillis()),
				getArchivePath() + "/archive-marker");
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to to write archive-marker file", ioe);
		}

		archiveConsoleLog();
		archiveJSON();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BaseBuild)) {
			return false;
		}

		BaseBuild baseBuild = (BaseBuild)obj;

		if (Objects.equals(getBuildURL(), baseBuild.getBuildURL())) {
			return true;
		}

		return false;
	}

	@Override
	public String getAppServer() {
		return null;
	}

	@Override
	public String getArchivePath() {
		StringBuilder sb = new StringBuilder(archiveName);

		if (!archiveName.endsWith("/")) {
			sb.append("/");
		}

		sb.append(_jenkinsMaster.getName());
		sb.append("/");
		sb.append(getJobName());
		sb.append("/");
		sb.append(getBuildNumber());

		return sb.toString();
	}

	@Override
	public long getAverageDelayTime() {
		if (getDownstreamBuildCount(null) == 0) {
			return 0;
		}

		List<Build> allDownstreamBuilds = JenkinsResultsParserUtil.flatten(
			getDownstreamBuilds(null));

		if (allDownstreamBuilds.isEmpty()) {
			return 0;
		}

		long totalDelayTime = 0;

		for (Build downstreamBuild : allDownstreamBuilds) {
			totalDelayTime += downstreamBuild.getDelayTime();
		}

		return totalDelayTime / allDownstreamBuilds.size();
	}

	@Override
	public List<String> getBadBuildURLs() {
		List<String> badBuildURLs = new ArrayList<>();

		String jobURL = getJobURL();

		for (Integer badBuildNumber : badBuildNumbers) {
			badBuildURLs.add(
				JenkinsResultsParserUtil.combine(
					jobURL, "/", String.valueOf(badBuildNumber), "/"));
		}

		return badBuildURLs;
	}

	@Override
	public String getBaseGitRepositoryName() {
		if (gitRepositoryName == null) {
			Properties buildProperties = null;

			try {
				buildProperties = JenkinsResultsParserUtil.getBuildProperties();
			}
			catch (IOException ioe) {
				throw new RuntimeException(
					"Unable to get build.properties", ioe);
			}

			TopLevelBuild topLevelBuild = getTopLevelBuild();

			gitRepositoryName = topLevelBuild.getParameterValue(
				"REPOSITORY_NAME");

			if ((gitRepositoryName != null) && !gitRepositoryName.isEmpty()) {
				return gitRepositoryName;
			}

			gitRepositoryName = buildProperties.getProperty(
				JenkinsResultsParserUtil.combine(
					"repository[", topLevelBuild.getJobName(), "]"));

			if (gitRepositoryName == null) {
				throw new RuntimeException(
					"Unable to get Git repository name for job " +
						topLevelBuild.getJobName());
			}
		}

		return gitRepositoryName;
	}

	@Override
	public String getBaseGitRepositorySHA(String gitRepositoryName) {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		if (gitRepositoryName.equals("liferay-jenkins-ee")) {
			Map<String, String> topLevelBuildStartPropertiesTempMap =
				topLevelBuild.getStartPropertiesTempMap();

			return topLevelBuildStartPropertiesTempMap.get(
				"JENKINS_GITHUB_UPSTREAM_BRANCH_SHA");
		}

		Map<String, String> gitRepositoryGitDetailsTempMap =
			topLevelBuild.getBaseGitRepositoryDetailsTempMap();

		return gitRepositoryGitDetailsTempMap.get("github.upstream.branch.sha");
	}

	@Override
	public String getBranchName() {
		return branchName;
	}

	@Override
	public String getBrowser() {
		return null;
	}

	@Override
	public String getBuildDescription() {
		if ((_buildDescription == null) && (getBuildURL() != null)) {
			JSONObject descriptionJSONObject = getBuildJSONObject(
				"description");

			String description = descriptionJSONObject.optString("description");

			if (description.equals("")) {
				description = null;
			}

			_buildDescription = description;
		}

		return _buildDescription;
	}

	@Override
	public JSONObject getBuildJSONObject() {
		try {
			return JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.getLocalURL(
					getBuildURL() + "api/json"),
				false);
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get build JSON object", ioe);
		}
	}

	@Override
	public int getBuildNumber() {
		return _buildNumber;
	}

	@Override
	public String getBuildURL() {
		String jobURL = getJobURL();

		if ((jobURL == null) || (_buildNumber == -1)) {
			return null;
		}

		if (fromArchive) {
			return jobURL + "/" + _buildNumber + "/";
		}

		try {
			jobURL = JenkinsResultsParserUtil.decode(jobURL);

			return JenkinsResultsParserUtil.encode(
				jobURL + "/" + _buildNumber + "/");
		}
		catch (MalformedURLException | URISyntaxException e) {
			throw new RuntimeException("Unable to encode build URL", e);
		}
		catch (UnsupportedEncodingException uee) {
			throw new RuntimeException(
				"Unable to decode job URL " + jobURL, uee);
		}
	}

	@Override
	public String getBuildURLRegex() {
		StringBuffer sb = new StringBuffer();

		sb.append("http[s]*:\\/\\/");
		sb.append(
			JenkinsResultsParserUtil.getRegexLiteral(_jenkinsMaster.getName()));
		sb.append("[^\\/]*");
		sb.append("[\\/]+job[\\/]+");

		String jobNameRegexLiteral = JenkinsResultsParserUtil.getRegexLiteral(
			getJobName());

		jobNameRegexLiteral = jobNameRegexLiteral.replace("\\(", "(\\(|%28)");
		jobNameRegexLiteral = jobNameRegexLiteral.replace("\\)", "(\\)|%29)");

		sb.append(jobNameRegexLiteral);

		sb.append("[\\/]+");
		sb.append(getBuildNumber());
		sb.append("[\\/]*");

		return sb.toString();
	}

	@Override
	public String getConsoleText() {
		String consoleText = JenkinsResultsParserUtil.getCachedText(
			_PREFIX_CONSOLE_TEXT_CACHE + getBuildURL());

		if (consoleText != null) {
			return consoleText;
		}

		String buildURL = getBuildURL();

		if (buildURL != null) {
			String status = getStatus();

			JenkinsConsoleTextLoader jenkinsConsoleTextLoader =
				new JenkinsConsoleTextLoader(
					getBuildURL(), status.equals("completed"));

			consoleText = jenkinsConsoleTextLoader.getConsoleText();

			if (consoleText.contains("\nFinished:")) {
				JenkinsResultsParserUtil.saveToCacheFile(
					_PREFIX_CONSOLE_TEXT_CACHE + getBuildURL(), consoleText);
			}

			return consoleText;
		}

		return "";
	}

	@Override
	public String getDatabase() {
		return null;
	}

	@Override
	public Long getDelayTime() {
		Long startTime = getStartTime();

		long currentTime = System.currentTimeMillis();

		if (startTime == null) {
			startTime = currentTime;
		}

		Long invokedTime = getInvokedTime();

		if (invokedTime == null) {
			invokedTime = currentTime;
		}

		return startTime - invokedTime;
	}

	@Override
	public int getDepth() {
		Build parentBuild = getParentBuild();

		if (parentBuild == null) {
			return 0;
		}

		return parentBuild.getDepth() + 1;
	}

	@Override
	public String getDisplayName() {
		StringBuilder sb = new StringBuilder();

		sb.append(getJobName());

		String jobVariant = getParameterValue("JOB_VARIANT");

		if ((jobVariant != null) && !jobVariant.isEmpty()) {
			sb.append("/");
			sb.append(jobVariant);
		}

		return sb.toString();
	}

	@Override
	public int getDownstreamBuildCount(String status) {
		return getDownstreamBuildCount(null, status);
	}

	@Override
	public int getDownstreamBuildCount(String result, String status) {
		List<Build> downstreamBuilds = getDownstreamBuilds(result, status);

		return downstreamBuilds.size();
	}

	@Override
	public List<Build> getDownstreamBuilds(String status) {
		return getDownstreamBuilds(null, status);
	}

	@Override
	public List<Build> getDownstreamBuilds(String result, String status) {
		if ((result == null) && (status == null)) {
			return downstreamBuilds;
		}

		List<Build> filteredDownstreamBuilds = new ArrayList<>();

		for (Build downstreamBuild : downstreamBuilds) {
			if (((status == null) ||
				 status.equals(downstreamBuild.getStatus())) &&
				((result == null) ||
				 result.equals(downstreamBuild.getResult()))) {

				filteredDownstreamBuilds.add(downstreamBuild);
			}
		}

		return filteredDownstreamBuilds;
	}

	@Override
	public long getDuration() {
		JSONObject buildJSONObject = getBuildJSONObject("duration,timestamp");

		if (buildJSONObject == null) {
			return 0;
		}

		long duration = buildJSONObject.getLong("duration");

		if (duration == 0) {
			long timestamp = buildJSONObject.getLong("timestamp");

			duration = System.currentTimeMillis() - timestamp;
		}

		return duration;
	}

	@Override
	public Element getGitHubMessageBuildAnchorElement() {
		getResult();

		int i = 0;

		String result = getResult();

		while (result == null) {
			if (i == 20) {
				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"Unable to create build anchor element. The process ",
						"timed out while waiting for a build result for ",
						getBuildURL(), "."));
			}

			JenkinsResultsParserUtil.sleep(1000 * 30);

			result = getResult();

			i++;
		}

		if (_result.equals("SUCCESS")) {
			return Dom4JUtil.getNewAnchorElement(
				getBuildURL(), getDisplayName());
		}

		return Dom4JUtil.getNewAnchorElement(
			getBuildURL(), null,
			Dom4JUtil.getNewElement(
				"strike", null,
				Dom4JUtil.getNewElement("strong", null, getDisplayName())));
	}

	@Override
	public Element getGitHubMessageElement() {
		return getGitHubMessageElement(false);
	}

	public Element getGitHubMessageElement(boolean showCommonFailuresCount) {
		String status = getStatus();

		if (!status.equals("completed") && (getParentBuild() != null)) {
			return null;
		}

		String result = getResult();

		if (result.equals("SUCCESS")) {
			return null;
		}

		Element messageElement = Dom4JUtil.getNewElement("div");

		Dom4JUtil.addToElement(
			messageElement,
			Dom4JUtil.getNewElement(
				"h5", null,
				Dom4JUtil.getNewAnchorElement(
					getBuildURL(), getDisplayName())));

		if (showCommonFailuresCount) {
			Dom4JUtil.addToElement(
				messageElement,
				getGitHubMessageJobResultsElement(showCommonFailuresCount));
		}
		else {
			Dom4JUtil.addToElement(
				messageElement, getGitHubMessageJobResultsElement());
		}

		if (result.equals("ABORTED") && (getDownstreamBuildCount(null) == 0)) {
			messageElement.add(
				Dom4JUtil.toCodeSnippetElement("Build was aborted"));

			return messageElement;
		}

		Element failureMessageElement = getFailureMessageElement();

		if (failureMessageElement != null) {
			messageElement.add(failureMessageElement);
		}

		return messageElement;
	}

	@Override
	public Element getGitHubMessageUpstreamJobFailureElement() {
		return upstreamJobFailureMessageElement;
	}

	@Override
	public String getInvocationURL() {
		String jobURL = getJobURL();

		if (jobURL == null) {
			return null;
		}

		StringBuffer sb = new StringBuffer(jobURL);

		sb.append("/buildWithParameters?");

		Map<String, String> parameters = new HashMap<>(getParameters());

		try {
			parameters.put(
				"token",
				JenkinsResultsParserUtil.getBuildProperty(
					"jenkins.authentication.token"));
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to get jenkins authentication token", ioe);
		}

		for (Map.Entry<String, String> parameter : parameters.entrySet()) {
			sb.append(parameter.getKey());
			sb.append("=");
			sb.append(parameter.getValue());
			sb.append("&");
		}

		sb.deleteCharAt(sb.length() - 1);

		try {
			return JenkinsResultsParserUtil.encode(sb.toString());
		}
		catch (MalformedURLException | URISyntaxException e) {
			throw new RuntimeException(
				"Unable to encode URL " + sb.toString(), e);
		}
	}

	@Override
	public Long getInvokedTime() {
		if (invokedTime != null) {
			return invokedTime;
		}

		invokedTime = getStartTime();

		return invokedTime;
	}

	@Override
	public String getJDK() {
		return null;
	}

	@Override
	public JenkinsMaster getJenkinsMaster() {
		return _jenkinsMaster;
	}

	@Override
	public JenkinsSlave getJenkinsSlave() {
		if (_jenkinsSlave != null) {
			return _jenkinsSlave;
		}

		String buildURL = getBuildURL();

		if ((buildURL == null) || (_jenkinsMaster == null)) {
			return null;
		}

		JSONObject builtOnJSONObject = getBuildJSONObject("builtOn");

		String slaveName = builtOnJSONObject.optString("builtOn");

		if (slaveName.equals("")) {
			slaveName = "master";
		}

		_jenkinsSlave = new JenkinsSlave(_jenkinsMaster, slaveName);

		return _jenkinsSlave;
	}

	@Override
	public String getJobName() {
		return jobName;
	}

	@Override
	public String getJobURL() {
		if ((_jenkinsMaster == null) || (jobName == null)) {
			return null;
		}

		if (fromArchive) {
			return JenkinsResultsParserUtil.combine(
				"${dependencies.url}/", archiveName, "/",
				_jenkinsMaster.getName(), "/", jobName);
		}

		String jobURL = JenkinsResultsParserUtil.combine(
			"https://", _jenkinsMaster.getName(), ".liferay.com/job/", jobName);

		try {
			return JenkinsResultsParserUtil.encode(jobURL);
		}
		catch (MalformedURLException | URISyntaxException e) {
			throw new RuntimeException("Unable to encode job URL " + jobURL, e);
		}
	}

	@Override
	public String getJobVariant() {
		String jobVariant = getParameterValue("JOB_VARIANT");

		if ((jobVariant == null) || jobVariant.isEmpty()) {
			jobVariant = getParameterValue("JENKINS_JOB_VARIANT");
		}

		return jobVariant;
	}

	@Override
	public int getJobVariantsDownstreamBuildCount(
		List<String> jobVariants, String result, String status) {

		List<Build> jobVariantsDownstreamBuilds =
			getJobVariantsDownstreamBuilds(jobVariants, result, status);

		return jobVariantsDownstreamBuilds.size();
	}

	@Override
	public List<Build> getJobVariantsDownstreamBuilds(
		Iterable<String> jobVariants, String result, String status) {

		List<Build> jobVariantsDownstreamBuilds = new ArrayList<>();

		List<Build> downstreamBuilds = getDownstreamBuilds(result, status);

		for (Build downstreamBuild : downstreamBuilds) {
			String downstreamBuildJobVariant = downstreamBuild.getJobVariant();

			for (String jobVariant : jobVariants) {
				if (downstreamBuildJobVariant.startsWith(jobVariant)) {
					jobVariantsDownstreamBuilds.add(downstreamBuild);

					break;
				}
			}
		}

		return jobVariantsDownstreamBuilds;
	}

	@Override
	public Long getLatestStartTimestamp() {
		Long latestStartTimestamp = getStartTime();

		if (latestStartTimestamp == null) {
			return null;
		}

		for (Build downstreamBuild : getDownstreamBuilds(null)) {
			Long downstreamBuildLatestStartTimestamp =
				downstreamBuild.getLatestStartTimestamp();

			if (downstreamBuildLatestStartTimestamp == null) {
				return null;
			}

			latestStartTimestamp = Math.max(
				latestStartTimestamp,
				downstreamBuild.getLatestStartTimestamp());
		}

		return latestStartTimestamp;
	}

	@Override
	public Build getLongestDelayedDownstreamBuild() {
		List<Build> downstreamBuilds = getDownstreamBuilds(null);

		if (downstreamBuilds.isEmpty()) {
			return this;
		}

		Build longestDelayedBuild = downstreamBuilds.get(0);

		for (Build downstreamBuild : downstreamBuilds) {
			Build longestDelayedDownstreamBuild =
				downstreamBuild.getLongestDelayedDownstreamBuild();

			if (downstreamBuild.getDelayTime() >
					longestDelayedDownstreamBuild.getDelayTime()) {

				longestDelayedDownstreamBuild = downstreamBuild;
			}

			if (longestDelayedDownstreamBuild.getDelayTime() >
					longestDelayedBuild.getDelayTime()) {

				longestDelayedBuild = longestDelayedDownstreamBuild;
			}
		}

		return longestDelayedBuild;
	}

	@Override
	public Build getLongestRunningDownstreamBuild() {
		Build longestRunningDownstreamBuild = null;

		for (Build downstreamBuild : getDownstreamBuilds(null)) {
			if ((longestRunningDownstreamBuild == null) ||
				(downstreamBuild.getDuration() >
					longestRunningDownstreamBuild.getDuration())) {

				longestRunningDownstreamBuild = downstreamBuild;
			}
		}

		return longestRunningDownstreamBuild;
	}

	@Override
	public TestResult getLongestRunningTest() {
		List<TestResult> testResults = getTestResults(null);

		long longestTestDuration = 0;

		TestResult longestRunningTest = null;

		for (TestResult testResult : testResults) {
			long testDuration = testResult.getDuration();

			if (testDuration > longestTestDuration) {
				longestTestDuration = testDuration;

				longestRunningTest = testResult;
			}
		}

		return longestRunningTest;
	}

	public Map<String, String> getMetricLabels() {
		if (_parentBuild != null) {
			return _parentBuild.getMetricLabels();
		}

		return new TreeMap<>();
	}

	@Override
	public List<Build> getModifiedDownstreamBuilds() {
		return getModifiedDownstreamBuildsByStatus(null);
	}

	@Override
	public List<Build> getModifiedDownstreamBuildsByStatus(String status) {
		List<Build> modifiedDownstreamBuilds = new ArrayList<>();

		for (Build downstreamBuild : downstreamBuilds) {
			if (downstreamBuild.isBuildModified() ||
				downstreamBuild.hasModifiedDownstreamBuilds()) {

				modifiedDownstreamBuilds.add(downstreamBuild);
			}
		}

		if (status != null) {
			modifiedDownstreamBuilds.retainAll(getDownstreamBuilds(status));
		}

		return modifiedDownstreamBuilds;
	}

	@Override
	public String getOperatingSystem() {
		return null;
	}

	@Override
	public Map<String, String> getParameters() {
		return new HashMap<>(_parameters);
	}

	@Override
	public String getParameterValue(String name) {
		return _parameters.get(name);
	}

	@Override
	public Build getParentBuild() {
		return _parentBuild;
	}

	@Override
	public String getResult() {
		if ((_result == null) && (getBuildURL() != null)) {
			JSONObject resultJSONObject = getBuildJSONObject("result");

			String result = resultJSONObject.optString("result");

			if (result.equals("")) {
				result = null;
			}

			setResult(result);
		}

		return _result;
	}

	@Override
	public Map<String, String> getStartPropertiesTempMap() {
		return getTempMap("start.properties");
	}

	@Override
	public Long getStartTime() {
		if (startTime != null) {
			return startTime;
		}

		JSONObject buildJSONObject = getBuildJSONObject("timestamp");

		if (buildJSONObject == null) {
			return null;
		}

		long timestamp = buildJSONObject.getLong("timestamp");

		if (timestamp != 0) {
			startTime = timestamp;
		}

		return startTime;
	}

	@Override
	public String getStatus() {
		return _status;
	}

	@Override
	public long getStatusAge() {
		return System.currentTimeMillis() - statusModifiedTime;
	}

	@Override
	public long getStatusDuration(String status) {
		if (statusDurations.containsKey(status)) {
			return statusDurations.get(status);
		}

		return 0;
	}

	@Override
	public String getStatusReport() {
		return getStatusReport(0);
	}

	@Override
	public String getStatusReport(int indentSize) {
		StringBuffer indentStringBuffer = new StringBuffer();

		for (int i = 0; i < indentSize; i++) {
			indentStringBuffer.append(" ");
		}

		StringBuilder sb = new StringBuilder();

		sb.append(indentStringBuffer);
		sb.append("Build \"");
		sb.append(jobName);
		sb.append("\"");

		String status = getStatus();

		if (status.equals("completed")) {
			sb.append(" completed at ");
			sb.append(getBuildURL());
			sb.append(". ");
			sb.append(getResult());

			return sb.toString();
		}

		if (status.equals("missing")) {
			sb.append(" is missing ");
			sb.append(getJobURL());
			sb.append(".");

			return sb.toString();
		}

		if (status.equals("queued")) {
			sb.append(" is queued at ");
			sb.append(getJobURL());
			sb.append(".");

			return sb.toString();
		}

		if (status.equals("running")) {
			sb.append(" running at ");
			sb.append(getBuildURL());
			sb.append(".\n");

			if (getDownstreamBuildCount(null) > 0) {
				sb.append("\n");

				for (Build downstreamBuild : getDownstreamBuilds("running")) {
					sb.append(downstreamBuild.getStatusReport(indentSize + 4));
				}

				sb.append("\n");
				sb.append(indentStringBuffer);
				sb.append(getStatusSummary());
				sb.append("\n");
			}

			return sb.toString();
		}

		if (status.equals("starting")) {
			sb.append(" invoked at ");
			sb.append(getJobURL());
			sb.append(".");

			return sb.toString();
		}

		throw new RuntimeException("Unknown status: " + status);
	}

	@Override
	public String getStatusSummary() {
		return JenkinsResultsParserUtil.combine(
			String.valueOf(getDownstreamBuildCount("starting")), " Starting  ",
			"/ ", String.valueOf(getDownstreamBuildCount("missing")),
			" Missing  ", "/ ",
			String.valueOf(getDownstreamBuildCount("queued")), " Queued  ",
			"/ ", String.valueOf(getDownstreamBuildCount("running")),
			" Running  ", "/ ",
			String.valueOf(getDownstreamBuildCount("completed")),
			" Completed  ", "/ ", String.valueOf(getDownstreamBuildCount(null)),
			" Total ");
	}

	@Override
	public Map<String, String> getStopPropertiesTempMap() {
		return getTempMap("stop.properties");
	}

	@Override
	public JSONObject getTestReportJSONObject() {
		try {
			return JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.getLocalURL(
					getBuildURL() + "testReport/api/json"),
				false);
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to get test report JSON object", ioe);
		}
	}

	public List<TestResult> getTestResults(
		Build build, JSONArray suitesJSONArray, String testStatus) {

		List<TestResult> testResults = new ArrayList<>();

		for (int i = 0; i < suitesJSONArray.length(); i++) {
			JSONObject suiteJSONObject = suitesJSONArray.getJSONObject(i);

			JSONArray casesJSONArray = suiteJSONObject.getJSONArray("cases");

			for (int j = 0; j < casesJSONArray.length(); j++) {
				TestResult testResult = TestResultFactory.newTestResult(
					build, casesJSONArray.getJSONObject(j));

				if ((testStatus == null) ||
					testStatus.equals(testResult.getStatus())) {

					testResults.add(testResult);
				}
			}
		}

		return testResults;
	}

	@Override
	public List<TestResult> getTestResults(String testStatus) {
		List<TestResult> testResults = new ArrayList<>();

		for (Build downstreamBuild : getDownstreamBuilds(null)) {
			List<TestResult> downstreamTestResults =
				downstreamBuild.getTestResults(testStatus);

			if (!(downstreamTestResults == null)) {
				testResults.addAll(downstreamTestResults);
			}
		}

		return testResults;
	}

	@Override
	public TopLevelBuild getTopLevelBuild() {
		Build topLevelBuild = this;

		while ((topLevelBuild != null) &&
			   !(topLevelBuild instanceof TopLevelBuild)) {

			topLevelBuild = topLevelBuild.getParentBuild();
		}

		return (TopLevelBuild)topLevelBuild;
	}

	@Override
	public long getTotalDuration() {
		long totalDuration = getDuration();

		for (Build downstreamBuild : getDownstreamBuilds(null)) {
			totalDuration += downstreamBuild.getTotalDuration();
		}

		return totalDuration;
	}

	@Override
	public int getTotalSlavesUsedCount() {
		return getTotalSlavesUsedCount(null, false);
	}

	@Override
	public int getTotalSlavesUsedCount(
		String status, boolean modifiedBuildsOnly) {

		return getTotalSlavesUsedCount(status, modifiedBuildsOnly, false);
	}

	@Override
	public int getTotalSlavesUsedCount(
		String status, boolean modifiedBuildsOnly, boolean ignoreCurrentBuild) {

		int totalSlavesUsedCount = 1;

		if (ignoreCurrentBuild || (modifiedBuildsOnly && !isBuildModified()) ||
			((status != null) && !_status.equals(status))) {

			totalSlavesUsedCount = 0;
		}

		List<Build> downstreamBuilds;

		if (modifiedBuildsOnly) {
			downstreamBuilds = getModifiedDownstreamBuildsByStatus(status);
		}
		else {
			downstreamBuilds = getDownstreamBuilds(status);
		}

		for (Build downstreamBuild : downstreamBuilds) {
			totalSlavesUsedCount += downstreamBuild.getTotalSlavesUsedCount(
				status, modifiedBuildsOnly);
		}

		return totalSlavesUsedCount;
	}

	@Override
	public boolean hasBuildURL(String buildURL) {
		try {
			buildURL = JenkinsResultsParserUtil.decode(buildURL);
		}
		catch (UnsupportedEncodingException uee) {
			throw new RuntimeException("Unable to decode " + buildURL, uee);
		}

		buildURL = JenkinsResultsParserUtil.getLocalURL(buildURL);

		String thisBuildURL = getBuildURL();

		if (thisBuildURL != null) {
			thisBuildURL = JenkinsResultsParserUtil.getLocalURL(thisBuildURL);

			try {
				if (URLCompareUtil.matches(
						new URL(buildURL), new URL(thisBuildURL))) {

					return true;
				}
			}
			catch (MalformedURLException murle) {
				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"Unable to compare urls ", buildURL, " and ",
						thisBuildURL),
					murle);
			}
		}

		for (Build downstreamBuild : downstreamBuilds) {
			if (downstreamBuild.hasBuildURL(buildURL)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean hasGenericCIFailure() {
		for (FailureMessageGenerator failureMessageGenerator :
				getFailureMessageGenerators()) {

			Element failureMessage = failureMessageGenerator.getMessageElement(
				this);

			if (failureMessage != null) {
				return failureMessageGenerator.isGenericCIFailure();
			}
		}

		return false;
	}

	@Override
	public int hashCode() {
		String key = getBuildURL();

		if (key != null) {
			return key.hashCode();
		}

		return super.hashCode();
	}

	@Override
	public boolean hasModifiedDownstreamBuilds() {
		for (Build downstreamBuild : downstreamBuilds) {
			if (downstreamBuild.isBuildModified() ||
				downstreamBuild.hasModifiedDownstreamBuilds()) {

				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isBuildModified() {
		return !_status.equals(_previousStatus);
	}

	@Override
	public boolean isFromArchive() {
		return fromArchive;
	}

	@Override
	public boolean isFromCompletedBuild() {
		return fromCompletedBuild;
	}

	@Override
	public void reinvoke() {
		reinvoke(null);
	}

	@Override
	public void reinvoke(ReinvokeRule reinvokeRule) {
		Build parentBuild = getParentBuild();

		String parentBuildStatus = parentBuild.getStatus();

		if (!parentBuildStatus.equals("running") ||
			!JenkinsResultsParserUtil.isCINode()) {

			return;
		}

		if ((reinvokeRule != null) && !fromArchive) {
			String message = JenkinsResultsParserUtil.combine(
				reinvokeRule.getName(), " failure detected at ", getBuildURL(),
				". This build will be reinvoked.\n\n", reinvokeRule.toString(),
				"\n\n");

			System.out.println(message);

			TopLevelBuild topLevelBuild = getTopLevelBuild();

			if (topLevelBuild != null) {
				message = JenkinsResultsParserUtil.combine(
					message, "Top Level Build URL: ",
					topLevelBuild.getBuildURL());
			}

			String notificationRecipients =
				reinvokeRule.getNotificationRecipients();

			if ((notificationRecipients != null) &&
				!notificationRecipients.isEmpty()) {

				NotificationUtil.sendEmail(
					message, "jenkins", "Build Reinvoked",
					reinvokeRule.notificationRecipients);
			}
		}

		String invocationURL = getInvocationURL();

		try {
			JenkinsResultsParserUtil.toString(
				JenkinsResultsParserUtil.getLocalURL(invocationURL));
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		System.out.println(getReinvokedMessage());

		reset();
	}

	@Override
	public String replaceBuildURL(String text) {
		if ((text == null) || text.isEmpty()) {
			return text;
		}

		if (downstreamBuilds != null) {
			for (Build downstreamBuild : downstreamBuilds) {
				Build downstreamBaseBuild = downstreamBuild;

				text = downstreamBaseBuild.replaceBuildURL(text);
			}
		}

		text = text.replaceAll(
			getBuildURLRegex(),
			Matcher.quoteReplacement(
				"${dependencies.url}/" + getArchivePath()));

		Build parentBuild = getParentBuild();

		while (parentBuild != null) {
			text = text.replaceAll(
				parentBuild.getBuildURLRegex(),
				Matcher.quoteReplacement(
					"${dependencies.url}/" + parentBuild.getArchivePath()));

			parentBuild = parentBuild.getParentBuild();
		}

		return text;
	}

	@Override
	public void setCompareToUpstream(boolean compareToUpstream) {
	}

	@Override
	public void takeSlaveOffline(SlaveOfflineRule slaveOfflineRule) {
		if ((slaveOfflineRule == null) || fromArchive) {
			return;
		}

		JenkinsSlave jenkinsSlave = getJenkinsSlave();

		if (jenkinsSlave == null) {
			return;
		}

		boolean slaveOffline = false;

		try {
			slaveOffline = jenkinsSlave.isOffline();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}

		if (slaveOffline) {
			return;
		}

		String message = JenkinsResultsParserUtil.combine(
			slaveOfflineRule.getName(), " failure detected at ", getBuildURL(),
			". ", jenkinsSlave.getName(), " will be taken offline.\n\n",
			slaveOfflineRule.toString(), "\n\n\nOffline Slave URL: https://",
			_jenkinsMaster.getName(), ".liferay.com/computer/",
			jenkinsSlave.getName(), "\n");

		System.out.println(message);

		TopLevelBuild topLevelBuild = getTopLevelBuild();

		if (topLevelBuild != null) {
			message = JenkinsResultsParserUtil.combine(
				message, "Top Level Build URL: ", topLevelBuild.getBuildURL());
		}

		jenkinsSlave.takeSlavesOffline(message);

		String notificationRecipients =
			slaveOfflineRule.getNotificationRecipients();

		if ((notificationRecipients != null) &&
			!notificationRecipients.isEmpty()) {

			NotificationUtil.sendEmail(
				message, "jenkins", "Slave Offline",
				slaveOfflineRule.notificationRecipients);
		}
	}

	@Override
	public void update() {
		String status = getStatus();

		if ((status.equals("completed") &&
			 (isBuildModified() || hasModifiedDownstreamBuilds())) ||
			!status.equals("completed")) {

			_previousStatus = _status;

			try {
				if (status.equals("missing") || status.equals("queued") ||
					status.equals("starting")) {

					JSONObject runningBuildJSONObject =
						getRunningBuildJSONObject();

					if (runningBuildJSONObject != null) {
						setBuildNumber(runningBuildJSONObject.getInt("number"));
					}
					else {
						JSONObject queueItemJSONObject =
							getQueueItemJSONObject();

						if (status.equals("starting") &&
							(queueItemJSONObject != null)) {

							setStatus("queued");
						}
						else if (status.equals("queued") &&
								 (queueItemJSONObject == null)) {

							setStatus("missing");
						}
					}
				}

				if (downstreamBuilds != null) {
					List<Callable<Object>> callables = new ArrayList<>();

					for (final Build downstreamBuild : downstreamBuilds) {
						Callable<Object> callable = new Callable<Object>() {

							@Override
							public Object call() {
								downstreamBuild.update();

								return null;
							}

						};

						callables.add(callable);
					}

					ParallelExecutor<Object> parallelExecutor =
						new ParallelExecutor<>(callables, getExecutorService());

					parallelExecutor.execute();

					String result = getResult();

					if ((downstreamBuilds.size() == getDownstreamBuildCount(
							"completed")) &&
						(result != null)) {

						setResult(result);
					}

					findDownstreamBuilds();

					if ((result == null) || result.equals("SUCCESS")) {
						return;
					}

					if (!fromArchive) {
						for (SlaveOfflineRule slaveOfflineRule :
								slaveOfflineRules) {

							if (!slaveOfflineRule.matches(this)) {
								continue;
							}

							takeSlaveOffline(slaveOfflineRule);

							break;
						}
					}

					if (this instanceof AxisBuild ||
						this instanceof BatchBuild ||
						this instanceof TopLevelBuild || fromArchive ||
						(badBuildNumbers.size() >= REINVOCATIONS_SIZE_MAX)) {

						return;
					}

					for (ReinvokeRule reinvokeRule : reinvokeRules) {
						if (!reinvokeRule.matches(this)) {
							continue;
						}

						reinvoke(reinvokeRule);

						break;
					}
				}
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		}
	}

	public static class BuildDisplayNameComparator
		implements Comparator<Build> {

		@Override
		public int compare(Build build1, Build build2) {
			String displayName1 = build1.getDisplayName();
			String displayName2 = build2.getDisplayName();

			return displayName1.compareTo(displayName2);
		}

	}

	public static class StopWatchRecord implements Comparable<StopWatchRecord> {

		public StopWatchRecord(
			String name, long startTimestamp, BaseBuild baseBuild) {

			_name = name;
			_startTimestamp = startTimestamp;
			_baseBuild = baseBuild;
		}

		public void addChildStopWatchRecord(
			StopWatchRecord newChildStopWatchRecord) {

			if (_childStopWatchRecords == null) {
				_childStopWatchRecords = new TreeSet<>();
			}

			for (StopWatchRecord childStopWatchRecord :
					_childStopWatchRecords) {

				if (childStopWatchRecord.isParentOf(newChildStopWatchRecord)) {
					childStopWatchRecord.addChildStopWatchRecord(
						newChildStopWatchRecord);

					return;
				}
			}

			newChildStopWatchRecord.setParentStopWatchRecord(this);

			_childStopWatchRecords.add(newChildStopWatchRecord);
		}

		@Override
		public int compareTo(StopWatchRecord stopWatchRecord) {
			int compareToValue = _startTimestamp.compareTo(
				stopWatchRecord.getStartTimestamp());

			if (compareToValue != 0) {
				return compareToValue;
			}

			Long duration = getDuration();
			Long stopWatchRecordDuration = stopWatchRecord.getDuration();

			if ((duration == null) && (stopWatchRecordDuration != null)) {
				return -1;
			}

			if ((duration != null) && (stopWatchRecordDuration == null)) {
				return 1;
			}

			if ((duration != null) && (stopWatchRecordDuration != null)) {
				compareToValue =
					-1 * duration.compareTo(stopWatchRecordDuration);
			}

			if (compareToValue != 0) {
				return compareToValue;
			}

			return _name.compareTo(stopWatchRecord.getName());
		}

		public int getDepth() {
			if (_parentStopWatchRecord == null) {
				if (_baseBuild == null) {
					return 0;
				}

				return _baseBuild.getDepth() + 1;
			}

			return _parentStopWatchRecord.getDepth() + 1;
		}

		public Long getDuration() {
			return _duration;
		}

		public String getName() {
			return _name;
		}

		public StopWatchRecord getParentStopWatchRecord() {
			return _parentStopWatchRecord;
		}

		public String getShortName() {
			String shortName = getName();

			StopWatchRecord parentStopWatchRecord = getParentStopWatchRecord();

			if (parentStopWatchRecord == null) {
				return shortName;
			}

			return shortName.replace(parentStopWatchRecord.getName(), "");
		}

		public Long getStartTimestamp() {
			return _startTimestamp;
		}

		public boolean isParentOf(StopWatchRecord stopWatchRecord) {
			if (this == stopWatchRecord) {
				return false;
			}

			Long duration = getDuration();
			Long stopWatchRecordDuration = stopWatchRecord.getDuration();

			if ((duration != null) && (stopWatchRecordDuration == null)) {
				return false;
			}

			Long startTimestamp = getStartTimestamp();
			Long stopWatchRecordStartTimestamp =
				stopWatchRecord.getStartTimestamp();

			if (startTimestamp <= stopWatchRecordStartTimestamp) {
				if (duration == null) {
					return true;
				}

				Long endTimestamp = startTimestamp + duration;
				Long stopWatchRecordEndTimestamp =
					stopWatchRecordStartTimestamp + stopWatchRecordDuration;

				if (endTimestamp >= stopWatchRecordEndTimestamp) {
					return true;
				}
			}

			return false;
		}

		public void setDuration(long duration) {
			_duration = duration;
		}

		@Override
		public String toString() {
			return JenkinsResultsParserUtil.combine(
				getName(), " started at ",
				JenkinsResultsParserUtil.toDateString(
					new Date(getStartTimestamp()), "America/Los_Angeles"),
				" and ran for ",
				JenkinsResultsParserUtil.toDurationString(getDuration()), ".");
		}

		protected Element getExpanderAnchorElement(String namespace) {
			if (_childStopWatchRecords == null) {
				return null;
			}

			Element expanderAnchorElement = Dom4JUtil.getNewAnchorElement(
				"", "+ ");

			expanderAnchorElement.addAttribute(
				"id",
				JenkinsResultsParserUtil.combine(
					namespace, "-expander-anchor-", getName()));
			expanderAnchorElement.addAttribute(
				"onClick",
				JenkinsResultsParserUtil.combine(
					"return toggleStopWatchRecordExpander(\'", namespace,
					"\', \'", getName(), "\')"));
			expanderAnchorElement.addAttribute(
				"style",
				"font-family: monospace, monospace; text-decoration: none");

			return expanderAnchorElement;
		}

		protected List<Element> getJenkinsReportTableRowElements() {
			Element buildInfoElement = Dom4JUtil.getNewElement("tr", null);

			String baseBuildHashCode = "";

			if (_baseBuild != null) {
				baseBuildHashCode = String.valueOf(_baseBuild.hashCode());
			}

			buildInfoElement.addAttribute(
				"id", baseBuildHashCode + "-" + getName());
			buildInfoElement.addAttribute("style", "display: none");

			Element expanderAnchorElement = getExpanderAnchorElement(
				baseBuildHashCode);

			Element nameElement = Dom4JUtil.getNewElement(
				"td", buildInfoElement, expanderAnchorElement, getShortName());

			int indent = getDepth() * _PIXELS_WIDTH_INDENT;

			if (expanderAnchorElement != null) {
				indent -= _PIXELS_WIDTH_EXPANDER;
			}

			nameElement.addAttribute(
				"style",
				JenkinsResultsParserUtil.combine(
					"text-indent: ", String.valueOf(indent), "px"));

			Dom4JUtil.getNewElement("td", buildInfoElement, "&nbsp;");

			Dom4JUtil.getNewElement("td", buildInfoElement, "&nbsp;");

			Dom4JUtil.getNewElement(
				"td", buildInfoElement,
				_baseBuild.toJenkinsReportDateString(
					new Date(getStartTimestamp()),
					_baseBuild.getJenkinsReportTimeZoneName()));

			if (getDuration() == null) {
				Dom4JUtil.getNewElement("td", buildInfoElement, "&nbsp;");
			}
			else {
				Dom4JUtil.getNewElement(
					"td", buildInfoElement,
					JenkinsResultsParserUtil.toDurationString(getDuration()));
			}

			Dom4JUtil.getNewElement("td", buildInfoElement, "&nbsp;");

			Dom4JUtil.getNewElement("td", buildInfoElement, "&nbsp;");

			List<Element> jenkinsReportTableRowElements = new ArrayList<>();

			jenkinsReportTableRowElements.add(buildInfoElement);

			if (_childStopWatchRecords != null) {
				List<String> childStopWatchRecordNames = new ArrayList<>(
					_childStopWatchRecords.size());

				for (StopWatchRecord childStopWatchRecord :
						_childStopWatchRecords) {

					childStopWatchRecordNames.add(
						childStopWatchRecord.getName());

					List<Element> childJenkinsReportTableRowElements =
						childStopWatchRecord.getJenkinsReportTableRowElements();

					for (Element childJenkinsReportTableRowElement :
							childJenkinsReportTableRowElements) {

						childJenkinsReportTableRowElement.addAttribute(
							"style", "display: none");
					}

					jenkinsReportTableRowElements.addAll(
						childJenkinsReportTableRowElements);
				}

				buildInfoElement.addAttribute(
					"child-stopwatch-rows",
					JenkinsResultsParserUtil.join(
						",", childStopWatchRecordNames));
			}

			return jenkinsReportTableRowElements;
		}

		protected void setParentStopWatchRecord(
			StopWatchRecord stopWatchRecord) {

			_parentStopWatchRecord = stopWatchRecord;
		}

		private final BaseBuild _baseBuild;
		private Set<StopWatchRecord> _childStopWatchRecords;
		private Long _duration;
		private final String _name;
		private StopWatchRecord _parentStopWatchRecord;
		private final Long _startTimestamp;

	}

	public static class StopWatchRecordsGroup
		implements Iterable<StopWatchRecord> {

		public void add(StopWatchRecord newStopWatchRecord) {
			_stopWatchRecordsMap.put(
				newStopWatchRecord.getName(), newStopWatchRecord);
		}

		public StopWatchRecord get(String name) {
			return _stopWatchRecordsMap.get(name);
		}

		public List<StopWatchRecord> getStopWatchRecords() {
			List<StopWatchRecord> allStopWatchRecords = new ArrayList<>(
				_stopWatchRecordsMap.values());

			Collections.sort(allStopWatchRecords);

			List<StopWatchRecord> parentStopWatchRecords = new ArrayList<>();

			for (StopWatchRecord stopWatchRecord : allStopWatchRecords) {
				for (StopWatchRecord parentStopWatchRecord :
						parentStopWatchRecords) {

					if (parentStopWatchRecord.isParentOf(stopWatchRecord)) {
						parentStopWatchRecord.addChildStopWatchRecord(
							stopWatchRecord);

						break;
					}
				}

				if (stopWatchRecord.getParentStopWatchRecord() == null) {
					parentStopWatchRecords.add(stopWatchRecord);
				}
			}

			return parentStopWatchRecords;
		}

		public boolean isEmpty() {
			return _stopWatchRecordsMap.isEmpty();
		}

		@Override
		public Iterator<StopWatchRecord> iterator() {
			List<StopWatchRecord> list = getStopWatchRecords();

			return list.iterator();
		}

		public int size() {
			List<StopWatchRecord> list = getStopWatchRecords();

			return list.size();
		}

		private final Map<String, StopWatchRecord> _stopWatchRecordsMap =
			new HashMap<>();

	}

	protected static boolean isHighPriorityBuildFailureElement(
		Element gitHubMessage) {

		String content = null;

		try {
			content = Dom4JUtil.format(gitHubMessage, false);
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to format github message", ioe);
		}

		for (String highPriorityContentToken : _TOKENS_HIGH_PRIORITY_CONTENT) {
			if (content.contains(highPriorityContentToken)) {
				return true;
			}
		}

		return false;
	}

	protected BaseBuild(String url) {
		this(url, null);
	}

	protected BaseBuild(String url, Build parentBuild) {
		_parentBuild = parentBuild;

		if (url.contains("buildWithParameters")) {
			setInvocationURL(url);
		}
		else {
			setBuildURL(url);
		}

		if (fromArchive || fromCompletedBuild) {
			update();
		}
	}

	protected void addDownstreamBuildsTimelineData(
		BaseBuild.TimelineData timelineData) {

		for (Build downstreamBuild : getDownstreamBuilds(null)) {
			if (downstreamBuild instanceof BaseBuild) {
				BaseBuild downstreamBaseBuild = (BaseBuild)downstreamBuild;

				downstreamBaseBuild.addTimelineData(timelineData);
			}
		}
	}

	protected void archiveConsoleLog() {
		downloadSampleURL(
			getArchivePath(), true, getBuildURL(), "/consoleText");
	}

	protected void archiveJSON() {
		downloadSampleURL(getArchivePath(), true, getBuildURL(), "api/json");
		downloadSampleURL(
			getArchivePath(), false, getBuildURL(), "testReport/api/json");

		if (!getStartPropertiesTempMap().isEmpty()) {
			try {
				JSONObject startPropertiesTempMapJSONObject =
					JenkinsResultsParserUtil.toJSONObject(
						getStartPropertiesTempMapURL());

				writeArchiveFile(
					startPropertiesTempMapJSONObject.toString(4),
					getArchivePath() + "/start.properties.json");
			}
			catch (IOException ioe) {
				throw new RuntimeException(
					"Unable to create start.properties.json", ioe);
			}
		}

		if (!getStopPropertiesTempMap().isEmpty()) {
			try {
				JSONObject stopPropertiesTempMapJSONObject =
					JenkinsResultsParserUtil.toJSONObject(
						getStopPropertiesTempMapURL());

				writeArchiveFile(
					stopPropertiesTempMapJSONObject.toString(4),
					getArchivePath() + "/stop.properties.json");
			}
			catch (IOException ioe) {
				throw new RuntimeException(
					"Unable to create stop.properties.json", ioe);
			}
		}
	}

	protected void checkForReinvocation(String consoleText) {
		if ((consoleText == null) || consoleText.isEmpty()) {
			return;
		}

		TopLevelBuild topLevelBuild = getTopLevelBuild();

		if ((topLevelBuild == null) || topLevelBuild.fromArchive) {
			return;
		}

		if (consoleText.contains(getReinvokedMessage())) {
			reset();

			update();
		}
	}

	protected void downloadSampleURL(
		String path, boolean required, String url, String urlSuffix) {

		String urlString = url + urlSuffix;

		if (urlString.endsWith("json")) {
			urlString += "?pretty";
		}

		urlSuffix = JenkinsResultsParserUtil.fixFileName(urlSuffix);

		String content = null;

		try {
			content = JenkinsResultsParserUtil.toString(
				JenkinsResultsParserUtil.getLocalURL(urlString), false, 0, 0,
				0);
		}
		catch (IOException ioe) {
			if (required) {
				throw new RuntimeException(
					"Unable to download sample " + urlString, ioe);
			}

			return;
		}

		try {
			writeArchiveFile(content, path + "/" + urlSuffix);
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to write file", ioe);
		}
	}

	protected void extractBuildURLComponents(Matcher matcher) {
		_buildNumber = Integer.parseInt(matcher.group("buildNumber"));
		setJenkinsMaster(new JenkinsMaster(matcher.group("master")));
		setJobName(matcher.group("jobName"));
	}

	protected void findDownstreamBuilds() {
		List<String> foundDownstreamBuildURLs = new ArrayList<>(
			findDownstreamBuildsInConsoleText());

		JSONObject buildJSONObject;

		buildJSONObject = getBuildJSONObject("runs[number,url]");

		if ((buildJSONObject != null) && buildJSONObject.has("runs")) {
			JSONArray runsJSONArray = buildJSONObject.getJSONArray("runs");

			if (runsJSONArray != null) {
				for (int i = 0; i < runsJSONArray.length(); i++) {
					JSONObject runJSONObject = runsJSONArray.getJSONObject(i);

					if (runJSONObject.getInt("number") == _buildNumber) {
						String url = runJSONObject.getString("url");

						if (!hasBuildURL(url) &&
							!foundDownstreamBuildURLs.contains(url)) {

							foundDownstreamBuildURLs.add(url);
						}
					}
				}
			}
		}

		addDownstreamBuilds(foundDownstreamBuildURLs.toArray(new String[0]));
	}

	protected List<String> findDownstreamBuildsInConsoleText() {
		return Collections.emptyList();
	}

	protected Pattern getArchiveBuildURLPattern() {
		return _archiveBuildURLPattern;
	}

	protected String getBaseGitRepositoryType() {
		if (jobName.startsWith("test-subrepository-acceptance-pullrequest")) {
			return getBaseGitRepositoryName();
		}

		if (jobName.contains("portal")) {
			return "portal";
		}

		if (jobName.contains("plugins")) {
			return "plugins";
		}

		return "jenkins";
	}

	protected JSONObject getBuildJSONObject(String tree) {
		if (getBuildURL() == null) {
			return null;
		}

		StringBuffer sb = new StringBuffer();

		sb.append(JenkinsResultsParserUtil.getLocalURL(getBuildURL()));
		sb.append("/api/json?pretty");

		if (tree != null) {
			sb.append("&tree=");
			sb.append(tree);
		}

		try {
			return JenkinsResultsParserUtil.toJSONObject(sb.toString(), false);
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get build JSON", ioe);
		}
	}

	protected String getBuildMessage() {
		if (jobName != null) {
			String status = getStatus();

			StringBuilder sb = new StringBuilder();

			sb.append("Build \"");
			sb.append(jobName);
			sb.append("\"");

			if (status.equals("completed")) {
				sb.append(" completed at ");
				sb.append(getBuildURL());
				sb.append(". ");
				sb.append(getResult());

				return sb.toString();
			}

			if (status.equals("missing")) {
				sb.append(" is missing ");
				sb.append(getJobURL());
				sb.append(".");

				return sb.toString();
			}

			if (status.equals("queued")) {
				sb.append(" is queued at ");
				sb.append(getJobURL());
				sb.append(".");

				return sb.toString();
			}

			if (status.equals("running")) {
				if (badBuildNumbers.size() > 0) {
					sb.append(" ");

					List<String> badBuildURLs = getBadBuildURLs();

					sb.append(badBuildURLs.get(badBuildNumbers.size() - 1));

					sb.append(" restarted at ");
				}
				else {
					sb.append(" started at ");
				}

				sb.append(getBuildURL());
				sb.append(".\n");

				return sb.toString();
			}

			if (status.equals("starting")) {
				sb.append(" invoked at ");
				sb.append(getJobURL());
				sb.append(".");

				return sb.toString();
			}

			throw new RuntimeException("Unknown status: " + status);
		}

		return "";
	}

	protected JSONArray getBuildsJSONArray(int page) throws IOException {
		JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
			JenkinsResultsParserUtil.getLocalURL(
				JenkinsResultsParserUtil.combine(
					getJobURL(), "/api/json?tree=allBuilds[actions[parameters",
					"[name,type,value]],building,duration,number,result,url]{",
					String.valueOf(page * 100), ",",
					String.valueOf((page + 1) * 100), "}")),
			false);

		return jsonObject.getJSONArray("allBuilds");
	}

	protected Element getBuildTimeElement() {
		return Dom4JUtil.getNewElement(
			"p", null, "Build Time: ",
			JenkinsResultsParserUtil.toDurationString(getDuration()));
	}

	protected MultiPattern getBuildURLMultiPattern() {
		return _buildURLMultiPattern;
	}

	protected int getDownstreamBuildCountByResult(String result) {
		List<Build> downstreamBuilds = getDownstreamBuilds(null);

		if (result == null) {
			return downstreamBuilds.size();
		}

		int count = 0;

		for (Build downstreamBuild : downstreamBuilds) {
			String downstreamBuildResult = downstreamBuild.getResult();

			if (downstreamBuildResult.equals(result)) {
				count++;
			}
		}

		return count;
	}

	protected Map<Build, Element> getDownstreamBuildMessages(
		String... results) {

		List<String> resultList = Arrays.asList(results);
		List<Build> matchingBuilds = new ArrayList<>();
		List<Callable<Element>> callables = new ArrayList<>();

		for (final Build downstreamBuild : getDownstreamBuilds(null)) {
			String downstreamBuildResult = downstreamBuild.getResult();

			if (resultList.contains(downstreamBuildResult)) {
				matchingBuilds.add(downstreamBuild);

				Callable<Element> callable = new Callable<Element>() {

					public Element call() {
						return downstreamBuild.getGitHubMessageElement();
					}

				};

				callables.add(callable);
			}
		}

		ParallelExecutor<Element> parallelExecutor = new ParallelExecutor<>(
			callables, getExecutorService());

		List<Element> elements = parallelExecutor.execute();

		Map<Build, Element> elementsMap = new LinkedHashMap<>();

		for (int i = 0; i < elements.size(); i++) {
			elementsMap.put(matchingBuilds.get(i), elements.get(i));
		}

		return elementsMap;
	}

	protected ExecutorService getExecutorService() {
		return null;
	}

	protected Element getFailureMessageElement() {
		for (FailureMessageGenerator failureMessageGenerator :
				getFailureMessageGenerators()) {

			Element failureMessage = failureMessageGenerator.getMessageElement(
				this);

			if (failureMessage != null) {
				return failureMessage;
			}
		}

		return null;
	}

	protected FailureMessageGenerator[] getFailureMessageGenerators() {
		return _FAILURE_MESSAGE_GENERATORS;
	}

	protected Element getFullConsoleClickHereElement() {
		return Dom4JUtil.getNewElement(
			"h5", null, "For full console, click ",
			Dom4JUtil.getNewAnchorElement(
				getBuildURL() + "/consoleText", "here"),
			".");
	}

	protected abstract Element getGitHubMessageJobResultsElement();

	protected Element getGitHubMessageJobResultsElement(
		boolean showCommonFailuresCount) {

		return getGitHubMessageJobResultsElement();
	}

	protected Map<String, String> getInjectedEnvironmentVariablesMap()
		throws IOException {

		Map<String, String> injectedEnvironmentVariablesMap;

		String localBuildURL = JenkinsResultsParserUtil.getLocalURL(
			getBuildURL());

		JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
			localBuildURL + "/injectedEnvVars/api/json", false);

		JSONObject envMapJSONObject = jsonObject.getJSONObject("envMap");

		Set<String> envMapJSONObjectKeySet = envMapJSONObject.keySet();

		injectedEnvironmentVariablesMap = new HashMap<>();

		for (String key : envMapJSONObjectKeySet) {
			injectedEnvironmentVariablesMap.put(
				key, envMapJSONObject.getString(key));
		}

		return injectedEnvironmentVariablesMap;
	}

	protected String getJenkinsReportBuildInfoCellElementTagName() {
		return "td";
	}

	protected List<Element> getJenkinsReportStopWatchRecordElements() {
		List<Element> jenkinsReportStopWatchRecordTableRowElements =
			new ArrayList<>();

		for (StopWatchRecord stopWatchRecord : getStopWatchRecordsGroup()) {
			jenkinsReportStopWatchRecordTableRowElements.addAll(
				stopWatchRecord.getJenkinsReportTableRowElements());
		}

		return jenkinsReportStopWatchRecordTableRowElements;
	}

	protected Element getJenkinsReportTableRowElement() {
		String cellElementTagName =
			getJenkinsReportBuildInfoCellElementTagName();

		Element stopWatchRecordsExpanderAnchorElement =
			getStopWatchRecordsExpanderAnchorElement();

		Element nameCellElement = Dom4JUtil.getNewElement(
			cellElementTagName, null, stopWatchRecordsExpanderAnchorElement,
			Dom4JUtil.getNewAnchorElement(
				getBuildURL(), null, getDisplayName()));

		int indent = getDepth() * _PIXELS_WIDTH_INDENT;

		if (stopWatchRecordsExpanderAnchorElement != null) {
			indent -= _PIXELS_WIDTH_EXPANDER;
		}

		nameCellElement.addAttribute("style", "text-indent: " + indent);

		Element buildInfoElement = Dom4JUtil.getNewElement(
			"tr", null, nameCellElement,
			Dom4JUtil.getNewElement(
				cellElementTagName, null,
				Dom4JUtil.getNewAnchorElement(
					getBuildURL() + "console", null, "Console")),
			Dom4JUtil.getNewElement(
				cellElementTagName, null,
				Dom4JUtil.getNewAnchorElement(
					getBuildURL() + "testReport", "Test Report")));

		StopWatchRecordsGroup stopWatchRecordsGroup =
			getStopWatchRecordsGroup();

		if (!stopWatchRecordsGroup.isEmpty()) {
			List<String> childStopWatchRecordNames = new ArrayList<>(
				stopWatchRecordsGroup.size());

			for (StopWatchRecord stopWatchRecord : stopWatchRecordsGroup) {
				childStopWatchRecordNames.add(stopWatchRecord.getName());
			}

			buildInfoElement.addAttribute(
				"child-stopwatch-rows",
				JenkinsResultsParserUtil.join(",", childStopWatchRecordNames));
		}

		buildInfoElement.addAttribute("id", String.valueOf(hashCode()) + "-");

		getStartTime();

		if (startTime == null) {
			Dom4JUtil.addToElement(
				buildInfoElement,
				Dom4JUtil.getNewElement(
					cellElementTagName, null, "",
					getJenkinsReportTimeZoneName()));
		}
		else {
			Dom4JUtil.addToElement(
				buildInfoElement,
				Dom4JUtil.getNewElement(
					cellElementTagName, null,
					toJenkinsReportDateString(
						new Date(startTime), getJenkinsReportTimeZoneName())));
		}

		Dom4JUtil.addToElement(
			buildInfoElement,
			Dom4JUtil.getNewElement(
				cellElementTagName, null,
				JenkinsResultsParserUtil.toDurationString(getDuration())));

		String status = getStatus();

		if (status != null) {
			status = StringUtils.upperCase(status);
		}
		else {
			status = "";
		}

		Dom4JUtil.getNewElement(cellElementTagName, buildInfoElement, status);

		String result = getResult();

		if (result == null) {
			result = "";
		}

		Dom4JUtil.getNewElement(cellElementTagName, buildInfoElement, result);

		return buildInfoElement;
	}

	protected List<Element> getJenkinsReportTableRowElements(
		String result, String status) {

		List<Element> tableRowElements = new ArrayList<>();

		if ((getParentBuild() != null) &&
			((result == null) || result.equals(getResult())) &&
			((status == null) || status.equals(getStatus()))) {

			tableRowElements.add(getJenkinsReportTableRowElement());

			tableRowElements.addAll(getJenkinsReportStopWatchRecordElements());
		}

		List<Build> downstreamBuilds = getDownstreamBuilds(result, status);

		Collections.sort(
			downstreamBuilds, new BaseBuild.BuildDisplayNameComparator());

		for (Build downstreamBuild : downstreamBuilds) {
			if (!(downstreamBuild instanceof BaseBuild)) {
				continue;
			}

			BaseBuild downstreamBaseBuild = (BaseBuild)downstreamBuild;

			tableRowElements.addAll(
				downstreamBaseBuild.getJenkinsReportTableRowElements(
					result, status));
		}

		return tableRowElements;
	}

	protected String getJenkinsReportTimeZoneName() {
		return _NAME_JENKINS_REPORT_TIME_ZONE;
	}

	protected Set<String> getJobParameterNames() {
		JSONObject jsonObject;

		try {
			jsonObject = JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.getLocalURL(
					JenkinsResultsParserUtil.combine(
						getJobURL(), "/api/json?tree=actions[",
						"parameterDefinitions[name,type,value]]")));
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get build JSON", ioe);
		}

		JSONArray actionsJSONArray = jsonObject.getJSONArray("actions");

		JSONObject firstActionJSONObject = actionsJSONArray.getJSONObject(0);

		JSONArray parameterDefinitionsJSONArray =
			firstActionJSONObject.getJSONArray("parameterDefinitions");

		Set<String> parameterNames = new HashSet<>(
			parameterDefinitionsJSONArray.length());

		for (int i = 0; i < parameterDefinitionsJSONArray.length(); i++) {
			JSONObject parameterDefinitionJSONObject =
				parameterDefinitionsJSONArray.getJSONObject(i);

			String type = parameterDefinitionJSONObject.getString("type");

			if (type.equals("StringParameterDefinition")) {
				parameterNames.add(
					parameterDefinitionJSONObject.getString("name"));
			}
		}

		return parameterNames;
	}

	protected Map<String, String> getParameters(JSONArray jsonArray) {
		Map<String, String> parameters = new HashMap<>(jsonArray.length());

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			parameters.put(
				jsonObject.getString("name"), jsonObject.optString("value"));
		}

		return parameters;
	}

	protected Map<String, String> getParameters(JSONObject buildJSONObject) {
		JSONArray actionsJSONArray = buildJSONObject.getJSONArray("actions");

		if (actionsJSONArray.length() == 0) {
			return new HashMap<>();
		}

		JSONObject jsonObject = actionsJSONArray.getJSONObject(0);

		if (jsonObject.has("parameters")) {
			JSONArray parametersJSONArray = jsonObject.getJSONArray(
				"parameters");

			return getParameters(parametersJSONArray);
		}

		return new HashMap<>();
	}

	protected JSONObject getQueueItemJSONObject() throws IOException {
		JSONArray queueItemsJSONArray = getQueueItemsJSONArray();

		for (int i = 0; i < queueItemsJSONArray.length(); i++) {
			JSONObject queueItemJSONObject = queueItemsJSONArray.getJSONObject(
				i);

			JSONObject taskJSONObject = queueItemJSONObject.getJSONObject(
				"task");

			String queueItemName = taskJSONObject.getString("name");

			if (!queueItemName.equals(jobName)) {
				continue;
			}

			if (_parameters.equals(getParameters(queueItemJSONObject))) {
				return queueItemJSONObject;
			}
		}

		return null;
	}

	protected JSONArray getQueueItemsJSONArray() throws IOException {
		JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
			JenkinsResultsParserUtil.combine(
				"http://", _jenkinsMaster.getName(),
				"/queue/api/json?tree=items[actions[parameters",
				"[name,value]],task[name,url]]"),
			false);

		return jsonObject.getJSONArray("items");
	}

	protected String getReinvokedMessage() {
		StringBuffer sb = new StringBuffer();

		sb.append("Reinvoked: ");
		sb.append(getBuildURL());
		sb.append(" at ");
		sb.append(getInvocationURL());

		return sb.toString();
	}

	protected JSONObject getRunningBuildJSONObject() throws IOException {
		int page = 0;

		while (true) {
			JSONArray buildsJSONArray = getBuildsJSONArray(page);

			if (buildsJSONArray.length() == 0) {
				break;
			}

			for (int i = 0; i < buildsJSONArray.length(); i++) {
				JSONObject buildJSONObject = buildsJSONArray.getJSONObject(i);

				Map<String, String> parameters = getParameters();

				if (parameters.equals(getParameters(buildJSONObject)) &&
					!badBuildNumbers.contains(
						buildJSONObject.getInt("number"))) {

					return buildJSONObject;
				}
			}

			page++;
		}

		return null;
	}

	protected String getStartPropertiesTempMapURL() {
		if (fromArchive) {
			return getBuildURL() + "/start.properties.json";
		}

		return getParameterValue("JSON_MAP_URL");
	}

	protected String getStopPropertiesTempMapURL() {
		return null;
	}

	protected Element getStopWatchRecordsExpanderAnchorElement() {
		StopWatchRecordsGroup stopWatchRecordsGroup =
			getStopWatchRecordsGroup();

		if (stopWatchRecordsGroup.isEmpty()) {
			return null;
		}

		Element stopWatchRecordsExpanderAnchorElement =
			Dom4JUtil.getNewAnchorElement("", "+ ");

		String hashCode = String.valueOf(hashCode());

		stopWatchRecordsExpanderAnchorElement.addAttribute(
			"id",
			JenkinsResultsParserUtil.combine(hashCode, "-expander-anchor-"));

		stopWatchRecordsExpanderAnchorElement.addAttribute(
			"onClick",
			JenkinsResultsParserUtil.combine(
				"return toggleStopWatchRecordExpander(\'", hashCode,
				"\', \'\')"));

		stopWatchRecordsExpanderAnchorElement.addAttribute(
			"style",
			"font-family: monospace, monospace; text-decoration: none");

		return stopWatchRecordsExpanderAnchorElement;
	}

	protected StopWatchRecordsGroup getStopWatchRecordsGroup() {
		String consoleText = null;
		int consoleTextLength = 0;
		int retries = 0;

		while (true) {
			try {
				consoleText = getConsoleText();

				consoleTextLength = consoleText.length();

				if (stopWatchRecordConsoleReadCursor > 0) {
					consoleText = consoleText.substring(
						stopWatchRecordConsoleReadCursor);
				}
			}
			catch (StringIndexOutOfBoundsException sioobe) {
				if (retries == 2) {
					throw sioobe;
				}

				System.out.println(
					JenkinsResultsParserUtil.combine(
						"Retrying. Console log length (",
						String.valueOf(consoleTextLength),
						") is shorter than previous (",
						String.valueOf(stopWatchRecordConsoleReadCursor),
						")."));

				retries++;

				JenkinsResultsParserUtil.sleep(1000 * 5);
			}

			break;
		}

		for (String line : consoleText.split("\n")) {
			Matcher matcher = stopWatchStartTimestampPattern.matcher(line);

			if (matcher.matches()) {
				Date timestamp = null;

				try {
					timestamp = stopWatchTimestampSimpleDateFormat.parse(
						matcher.group("timestamp"));
				}
				catch (ParseException pe) {
					throw new RuntimeException(
						"Unable to parse timestamp in " + line, pe);
				}

				String stopWatchName = matcher.group("name");

				stopWatchRecordsGroup.add(
					new StopWatchRecord(
						stopWatchName, timestamp.getTime(), this));

				continue;
			}

			matcher = stopWatchPattern.matcher(line);

			if (matcher.matches()) {
				long duration = Long.parseLong(matcher.group("milliseconds"));

				String seconds = matcher.group("seconds");

				if (seconds != null) {
					duration += Long.parseLong(seconds) * 1000L;
				}

				String minutes = matcher.group("minutes");

				if (minutes != null) {
					duration += Long.parseLong(minutes) * 60L * 1000L;
				}

				String stopWatchName = matcher.group("name");

				StopWatchRecord stopWatchRecord = stopWatchRecordsGroup.get(
					stopWatchName);

				if (stopWatchRecord != null) {
					stopWatchRecord.setDuration(duration);
				}
			}
		}

		stopWatchRecordConsoleReadCursor = consoleTextLength;

		return stopWatchRecordsGroup;
	}

	protected Map<String, String> getTempMap(String tempMapName) {
		String tempMapURL = getTempMapURL(tempMapName);

		if (tempMapURL == null) {
			return getTempMapFromBuildDatabase(tempMapName);
		}

		JSONObject tempMapJSONObject = null;

		try {
			tempMapJSONObject = JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.getLocalURL(tempMapURL), false, 0, 0,
				0);
		}
		catch (IOException ioe) {
		}

		if ((tempMapJSONObject == null) ||
			!tempMapJSONObject.has("properties")) {

			return getTempMapFromBuildDatabase(tempMapName);
		}

		JSONArray propertiesJSONArray = tempMapJSONObject.getJSONArray(
			"properties");

		Map<String, String> tempMap = new HashMap<>(
			propertiesJSONArray.length());

		for (int i = 0; i < propertiesJSONArray.length(); i++) {
			JSONObject propertyJSONObject = propertiesJSONArray.getJSONObject(
				i);

			String key = propertyJSONObject.getString("name");
			String value = propertyJSONObject.optString("value");

			if ((value != null) && !value.isEmpty()) {
				tempMap.put(key, value);
			}
		}

		return tempMap;
	}

	protected Map<String, String> getTempMapFromBuildDatabase(
		String tempMapName) {

		Map<String, String> tempMap = new HashMap<>();

		if (!fromArchive) {
			BuildDatabase buildDatabase = BuildDatabaseUtil.getBuildDatabase();

			Properties properties = buildDatabase.getProperties(tempMapName);

			for (String propertyName : properties.stringPropertyNames()) {
				tempMap.put(propertyName, properties.getProperty(propertyName));
			}
		}

		return tempMap;
	}

	protected String getTempMapURL(String tempMapName) {
		if (tempMapName.equals("start.properties")) {
			return getStartPropertiesTempMapURL();
		}

		if (tempMapName.equals("stop.properties")) {
			return getStopPropertiesTempMapURL();
		}

		return null;
	}

	protected int getTestCountByStatus(String status) {
		JSONObject testReportJSONObject = getTestReportJSONObject();

		if (testReportJSONObject == null) {
			return 0;
		}

		if (status.equals("FAILURE")) {
			return testReportJSONObject.getInt("failCount");
		}

		if (status.equals("SUCCESS")) {
			return testReportJSONObject.getInt("passCount");
		}

		throw new IllegalArgumentException("Invalid status: " + status);
	}

	protected boolean isCompareToUpstream() {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		return topLevelBuild.isCompareToUpstream();
	}

	protected boolean isParentBuildRoot() {
		if (_parentBuild == null) {
			return false;
		}

		if ((_parentBuild.getParentBuild() == null) &&
			(_parentBuild instanceof TopLevelBuild)) {

			return true;
		}

		return false;
	}

	protected void loadParametersFromBuildJSONObject() {
		if (getBuildURL() == null) {
			return;
		}

		JSONObject buildJSONObject = getBuildJSONObject(
			"actions[parameters[*]]");

		JSONArray actionsJSONArray = buildJSONObject.getJSONArray("actions");

		if (actionsJSONArray.length() == 0) {
			_parameters = new HashMap<>();

			return;
		}

		for (int i = 0; i < actionsJSONArray.length(); i++) {
			JSONObject actionJSONObject = actionsJSONArray.getJSONObject(i);

			if (!actionJSONObject.has("parameters")) {
				continue;
			}

			JSONArray parametersJSONArray = actionJSONObject.getJSONArray(
				"parameters");

			_parameters = new HashMap<>(parametersJSONArray.length());

			for (int j = 0; j < parametersJSONArray.length(); j++) {
				JSONObject parameterJSONObject =
					parametersJSONArray.getJSONObject(j);

				Object value = parameterJSONObject.opt("value");

				if (value instanceof String) {
					String valueString = value.toString();

					if (!valueString.isEmpty()) {
						_parameters.put(
							parameterJSONObject.getString("name"),
							value.toString());
					}
				}
			}

			return;
		}

		_parameters = Collections.emptyMap();
	}

	protected void loadParametersFromQueryString(String queryString) {
		Map<String, String> defaultJobParameters = _getDefaultJobParameters();

		_parameters.putAll(defaultJobParameters);

		for (String parameter : queryString.split("&")) {
			if (!parameter.contains("=")) {
				continue;
			}

			String[] nameValueArray = parameter.split("=");

			if (!defaultJobParameters.containsKey(nameValueArray[0])) {
				continue;
			}

			if (nameValueArray.length == 2) {
				_parameters.put(nameValueArray[0], nameValueArray[1]);
			}
			else if (nameValueArray.length == 1) {
				_parameters.put(nameValueArray[0], "");
			}
		}
	}

	protected void reset() {
		setResult(null);

		badBuildNumbers.add(getBuildNumber());

		setBuildNumber(-1);

		downstreamBuilds.clear();
	}

	protected void setBuildNumber(int buildNumber) {
		if (_buildNumber != buildNumber) {
			_buildNumber = buildNumber;

			consoleReadCursor = 0;

			if (_buildNumber == -1) {
				setStatus("starting");
			}
			else {
				setStatus("running");
			}
		}
	}

	protected void setBuildURL(String buildURL) {
		try {
			buildURL = JenkinsResultsParserUtil.decode(buildURL);
		}
		catch (UnsupportedEncodingException uee) {
			throw new IllegalArgumentException(
				"Unable to decode " + buildURL, uee);
		}

		Build parentBuild = getParentBuild();

		try {
			if (parentBuild != null) {
				fromArchive = parentBuild.isFromArchive();
			}
			else {
				String archiveMarkerContent = JenkinsResultsParserUtil.toString(
					buildURL + "/archive-marker", false, 0, 0, 0);

				fromArchive =
					(archiveMarkerContent != null) &&
					!archiveMarkerContent.isEmpty();
			}
		}
		catch (IOException ioe) {
			fromArchive = false;
		}

		MultiPattern buildURLMultiPattern = getBuildURLMultiPattern();

		Matcher matcher = buildURLMultiPattern.find(buildURL);

		if (matcher == null) {
			Pattern archiveBuildURLPattern = getArchiveBuildURLPattern();

			matcher = archiveBuildURLPattern.matcher(buildURL);

			if (!matcher.find()) {
				throw new IllegalArgumentException(
					"Invalid build URL " + buildURL);
			}

			archiveName = matcher.group("archiveName");
		}

		extractBuildURLComponents(matcher);

		loadParametersFromBuildJSONObject();

		consoleReadCursor = 0;

		setStatus("running");

		if (parentBuild != null) {
			fromCompletedBuild = parentBuild.isFromCompletedBuild();
		}
		else {
			String consoleText = getConsoleText();

			fromCompletedBuild = consoleText.contains("stop-current-job:");
		}
	}

	protected void setInvocationURL(String invocationURL) {
		if (getBuildURL() == null) {
			try {
				invocationURL = JenkinsResultsParserUtil.decode(invocationURL);
			}
			catch (UnsupportedEncodingException uee) {
				throw new IllegalArgumentException(
					"Unable to decode " + invocationURL, uee);
			}

			Matcher invocationURLMatcher = invocationURLPattern.matcher(
				invocationURL);

			if (!invocationURLMatcher.find()) {
				throw new RuntimeException("Invalid invocation URL");
			}

			setJobName(invocationURLMatcher.group("jobName"));
			setJenkinsMaster(
				new JenkinsMaster(invocationURLMatcher.group("master")));

			loadParametersFromQueryString(invocationURL);

			setStatus("starting");

			try {
				JenkinsResultsParserUtil.toString(getInvocationURL(), false);
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		}
	}

	protected void setJenkinsMaster(JenkinsMaster jenkinsMaster) {
		_jenkinsMaster = jenkinsMaster;
	}

	protected void setJobName(String jobName) {
		this.jobName = jobName;

		Matcher matcher = jobNamePattern.matcher(jobName);

		if (matcher.find()) {
			branchName = matcher.group("branchName");

			return;
		}

		branchName = "master";
	}

	protected void setResult(String result) {
		_result = result;

		if ((_result == null) ||
			(getDownstreamBuildCount("completed") < getDownstreamBuildCount(
				null))) {

			setStatus("running");
		}
		else {
			setStatus("completed");
		}
	}

	protected void setStatus(String status) {
		if (_isDifferent(status, _status)) {
			_status = status;

			long previousStatusModifiedTime = statusModifiedTime;

			statusModifiedTime = System.currentTimeMillis();

			statusDurations.put(
				_previousStatus,
				statusModifiedTime - previousStatusModifiedTime);

			if (isParentBuildRoot()) {
				System.out.println(getBuildMessage());
			}
		}
	}

	protected String toJenkinsReportDateString(Date date, String timeZoneName) {
		Properties buildProperties = null;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get build properties", ioe);
		}

		return JenkinsResultsParserUtil.toDateString(
			date, buildProperties.getProperty("jenkins.report.date.format"),
			timeZoneName);
	}

	protected void writeArchiveFile(String content, String path)
		throws IOException {

		JenkinsResultsParserUtil.write(
			JenkinsResultsParserUtil.combine(
				JenkinsResultsParserUtil.URL_DEPENDENCIES_FILE.substring(
					"file:".length()),
				"/", path),
			JenkinsResultsParserUtil.redact(replaceBuildURL(content)));
	}

	protected static final int REINVOCATIONS_SIZE_MAX = 1;

	protected static final String URL_BASE_FAILURES_JOB_UPSTREAM =
		"https://test-1-0.liferay.com/userContent/testResults/";

	protected static final String URL_BASE_TEMP_MAP =
		"http://cloud-10-0-0-31.lax.liferay.com/osb-jenkins-web/map/";

	protected static final Pattern downstreamBuildURLPattern = Pattern.compile(
		"[\\'\\\"].*[\\'\\\"] started at (?<url>.+)\\.");
	protected static final Pattern invocationURLPattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"\\w+://(?<master>[^/]+)/+job/+(?<jobName>[^/]+).*/",
			"buildWithParameters\\?(?<queryString>.*)"));
	protected static final Pattern jobNamePattern = Pattern.compile(
		"(?<baseJob>[^\\(]+)\\((?<branchName>[^\\)]+)\\)");
	protected static final Pattern stopWatchPattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"\\s*\\[stopwatch\\]\\s*\\[(?<name>[^:]+): ",
			"((?<minutes>\\d+):)?((?<seconds>\\d+))?\\.",
			"(?<milliseconds>\\d+) sec\\]"));
	protected static final Pattern stopWatchStartTimestampPattern =
		Pattern.compile(
			JenkinsResultsParserUtil.combine(
				"\\s*\\[echo\\] (?<name>.*)\\.start\\.timestamp: ",
				"(?<timestamp>.*)$"));
	protected static final SimpleDateFormat stopWatchTimestampSimpleDateFormat =
		new SimpleDateFormat("MM-dd-yyyy HH:mm:ss:SSS z");

	protected String archiveName;
	protected List<Integer> badBuildNumbers = new ArrayList<>();
	protected String branchName;
	protected int consoleReadCursor;
	protected List<Build> downstreamBuilds = new ArrayList<>();
	protected boolean fromArchive;
	protected boolean fromCompletedBuild;
	protected String gitRepositoryName;
	protected Long invokedTime;
	protected String jobName;
	protected List<ReinvokeRule> reinvokeRules =
		ReinvokeRule.getReinvokeRules();
	protected List<SlaveOfflineRule> slaveOfflineRules =
		SlaveOfflineRule.getSlaveOfflineRules();
	protected Long startTime;
	protected Map<String, Long> statusDurations = new HashMap<>();
	protected long statusModifiedTime;
	protected int stopWatchRecordConsoleReadCursor;
	protected StopWatchRecordsGroup stopWatchRecordsGroup =
		new StopWatchRecordsGroup();
	protected Element upstreamJobFailureMessageElement;

	protected static class TimelineData {

		protected TimelineData(int size, TopLevelBuild topLevelBuild) {
			if (topLevelBuild != topLevelBuild.getTopLevelBuild()) {
				throw new IllegalArgumentException(
					"Nested TopLevelBuild objects are invalid");
			}

			if (size < 1) {
				throw new IllegalArgumentException("Invalid size " + size);
			}

			_duration = topLevelBuild.getDuration();
			_startTime = topLevelBuild.getStartTime();

			_timeline = new TimelineDataPoint[size];

			for (int i = 0; i < size; i++) {
				_timeline[i] = new TimelineDataPoint(
					(int)(i * (_duration / _timeline.length)));
			}

			topLevelBuild.addTimelineData(this);
		}

		protected void addTimelineData(BaseBuild build) {
			Long buildInvokedTime = build.getInvokedTime();

			if (buildInvokedTime == null) {
				return;
			}

			_timeline[_getIndex(buildInvokedTime)]._invocationsCount++;

			Long buildStartTime = build.getStartTime();

			if (buildStartTime == null) {
				return;
			}

			int endIndex = _getIndex(buildStartTime + build.getDuration());
			int startIndex = _getIndex(buildStartTime);

			for (int i = startIndex; i <= endIndex; i++) {
				_timeline[i]._slaveUsageCount++;
			}
		}

		protected int[] getIndexData() {
			int[] indexes = new int[_timeline.length];

			for (int i = 0; i < _timeline.length; i++) {
				indexes[i] = _timeline[i]._index;
			}

			return indexes;
		}

		protected int[] getInvocationsData() {
			int[] invocationsData = new int[_timeline.length];

			for (int i = 0; i < _timeline.length; i++) {
				invocationsData[i] = _timeline[i]._invocationsCount;
			}

			return invocationsData;
		}

		protected int[] getSlaveUsageData() {
			int[] slaveUsageData = new int[_timeline.length];

			for (int i = 0; i < _timeline.length; i++) {
				slaveUsageData[i] = _timeline[i]._slaveUsageCount;
			}

			return slaveUsageData;
		}

		private int _getIndex(long timestamp) {
			int index =
				(int)((timestamp - _startTime) * _timeline.length / _duration);

			if (index >= _timeline.length) {
				return _timeline.length - 1;
			}

			if (index < 0) {
				return 0;
			}

			return index;
		}

		private final long _duration;
		private final long _startTime;
		private final TimelineDataPoint[] _timeline;

		private static class TimelineDataPoint {

			private TimelineDataPoint(int index) {
				_index = index;
			}

			private final int _index;
			private int _invocationsCount;
			private int _slaveUsageCount;

		}

	}

	private Map<String, String> _getDefaultJobParameters() {
		Map<String, String> jobParameters = new HashMap<>();

		JSONObject actionsJSONObject = null;

		JSONObject jobJSONObject = null;

		try {
			jobJSONObject = JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.combine(
					getJobURL(), "/api/json?tree=actions[parameterDefinitions[",
					"defaultParameterValue[value],name]]"));
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		JSONArray actionsJSONArray = jobJSONObject.getJSONArray("actions");

		for (int i = 0; i < actionsJSONArray.length(); i++) {
			JSONObject jsonObject = actionsJSONArray.getJSONObject(i);

			if (jsonObject.has("parameterDefinitions")) {
				actionsJSONObject = jsonObject;

				break;
			}
		}

		if (actionsJSONObject == null) {
			return jobParameters;
		}

		JSONArray parameterDefinitionsJSONArray =
			actionsJSONObject.getJSONArray("parameterDefinitions");

		for (int i = 0; i < parameterDefinitionsJSONArray.length(); i++) {
			JSONObject parameterJSONObject =
				parameterDefinitionsJSONArray.getJSONObject(i);

			JSONObject defaultParameterValueJSONObject =
				parameterJSONObject.getJSONObject("defaultParameterValue");

			jobParameters.put(
				parameterJSONObject.getString("name"),
				defaultParameterValueJSONObject.getString("value"));
		}

		return jobParameters;
	}

	private boolean _isDifferent(String newValue, String oldValue) {
		if (oldValue == null) {
			if (newValue != null) {
				return true;
			}

			return false;
		}

		if (oldValue.equals(newValue)) {
			return false;
		}

		return true;
	}

	private static final FailureMessageGenerator[] _FAILURE_MESSAGE_GENERATORS =
		{new GenericFailureMessageGenerator()};

	private static final String _NAME_JENKINS_REPORT_TIME_ZONE;

	private static final int _PIXELS_WIDTH_EXPANDER = 20;

	private static final int _PIXELS_WIDTH_INDENT = 35;

	private static final String _PREFIX_CONSOLE_TEXT_CACHE = "console-text-";

	private static final String[] _TOKENS_HIGH_PRIORITY_CONTENT = {
		"compileJSP", "SourceFormatter.format", "Unable to compile JSPs"
	};

	private static final Pattern _archiveBuildURLPattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"(", Pattern.quote("${dependencies.url}"), "|",
			Pattern.quote(JenkinsResultsParserUtil.URL_DEPENDENCIES_FILE), "|",
			Pattern.quote(JenkinsResultsParserUtil.URL_DEPENDENCIES_HTTP),
			")/*(?<archiveName>.*)/(?<master>[^/]+)/+(?<jobName>[^/]+)",
			".*/(?<buildNumber>\\d+)/?"));
	private static final MultiPattern _buildURLMultiPattern = new MultiPattern(
		JenkinsResultsParserUtil.combine(
			"\\w+://(?<master>[^/]+)/+job/+(?<jobName>[^/]+).*/(?<buildNumber>",
			"\\d+)/?"));

	static {
		Properties properties = null;

		try {
			properties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get build properties", ioe);
		}

		_NAME_JENKINS_REPORT_TIME_ZONE = properties.getProperty(
			"jenkins.report.time.zone");
	}

	private String _buildDescription;
	private int _buildNumber = -1;
	private JenkinsMaster _jenkinsMaster;
	private JenkinsSlave _jenkinsSlave;
	private Map<String, String> _parameters = new HashMap<>();
	private final Build _parentBuild;
	private String _previousStatus;
	private String _result;
	private String _status;

}