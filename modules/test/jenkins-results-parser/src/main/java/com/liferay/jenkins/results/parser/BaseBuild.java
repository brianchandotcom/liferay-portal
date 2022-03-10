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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
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
			catch (UnsupportedEncodingException unsupportedEncodingException) {
				throw new IllegalArgumentException(
					"Unable to decode " + url, unsupportedEncodingException);
			}

			if (!hasBuildURL(url)) {
				final String buildURL = url;

				Callable<Build> callable = new Callable<Build>() {

					@Override
					public Build call() {
						try {
							return BuildFactory.newBuild(buildURL, thisBuild);
						}
						catch (RuntimeException runtimeException) {
							if (!isFromArchive()) {
								NotificationUtil.sendSlackNotification(
									runtimeException.getMessage() +
										"\nBuild URL: " + buildURL,
									"ci-notifications", "Build Object Failure");
							}

							return null;
						}
					}

				};

				callables.add(callable);
			}
		}

		ParallelExecutor<Build> parallelExecutor = new ParallelExecutor<>(
			callables, true, getExecutorService());

		downstreamBuilds.addAll(parallelExecutor.execute());
	}

	public abstract void addTimelineData(BaseBuild.TimelineData timelineData);

	@Override
	public void archive(final String archiveName) {
		setArchiveName(archiveName);

		if (!_status.equals("completed")) {
			return;
		}

		File archiveDir = new File(getArchiveRootDir(), getArchivePath());

		if (archiveDir.exists()) {
			if (!JenkinsResultsParserUtil.isCINode()) {
				archiveDir.delete();
			}
		}
		else {
			archiveDir.mkdirs();
		}

		try {
			writeArchiveFile(
				String.valueOf(JenkinsResultsParserUtil.getCurrentTimeMillis()),
				getArchivePath() + "/archive-marker");
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to to write archive-marker", ioException);
		}

		archiveConsoleLog();
		archiveJSON();

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
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof BaseBuild)) {
			return false;
		}

		BaseBuild baseBuild = (BaseBuild)object;

		if (Objects.equals(getBuildURL(), baseBuild.getBuildURL())) {
			return true;
		}

		return false;
	}

	@Override
	public String getArchiveName() {
		if (getParentBuild() == null) {
			return _archiveName;
		}

		Build topLevelBuild = getTopLevelBuild();

		if (this == topLevelBuild) {
			return _archiveName;
		}

		return topLevelBuild.getArchiveName();
	}

	@Override
	public String getArchivePath() {
		String archiveName = getArchiveName();

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
	public File getArchiveRootDir() {
		Build parentBuild = getParentBuild();

		if (parentBuild == null) {
			return _archiveRootDir;
		}

		if (equals(parentBuild)) {
			System.out.println("STACKOVERFLOW CATCH");

			return _archiveRootDir;
		}

		return parentBuild.getArchiveRootDir();
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
			catch (IOException ioException) {
				throw new RuntimeException(
					"Unable to get build.properties", ioException);
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

		if ((topLevelBuild instanceof WorkspaceBuild) && !fromArchive) {
			WorkspaceBuild workspaceBuild = (WorkspaceBuild)topLevelBuild;

			Workspace workspace = workspaceBuild.getWorkspace();

			WorkspaceGitRepository workspaceGitRepository =
				workspace.getPrimaryWorkspaceGitRepository();

			return workspaceGitRepository.getBaseBranchSHA();
		}

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
	public String getBuildDirPath() {
		StringBuilder sb = new StringBuilder();

		if (JenkinsResultsParserUtil.isWindows()) {
			sb.append("C:");
		}

		sb.append("/tmp/jenkins/");

		JenkinsMaster jenkinsMaster = getJenkinsMaster();

		sb.append(jenkinsMaster.getName());

		sb.append("/");
		sb.append(getJobName());

		if (this instanceof AxisBuild) {
			sb.append("/");

			AxisBuild axisBuild = (AxisBuild)this;

			sb.append(axisBuild.getAxisNumber());
		}

		sb.append("/");
		sb.append(getBuildNumber());

		return sb.toString();
	}

	@Override
	public JSONObject getBuildJSONObject() {
		String archiveFileContent = getArchiveFileContent("api/json");

		if (JenkinsResultsParserUtil.isJSONObject(archiveFileContent)) {
			return new JSONObject(archiveFileContent);
		}

		try {
			return JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.getLocalURL(
					getBuildURL() + "api/json"),
				false);
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get build JSON object", ioException);
		}
	}

	@Override
	public int getBuildNumber() {
		return _buildNumber;
	}

	@Override
	public Job.BuildProfile getBuildProfile() {
		String buildProfile = getParameterValue("TEST_PORTAL_BUILD_PROFILE");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(buildProfile)) {
			if (buildProfile.equals("dxp")) {
				return Job.BuildProfile.DXP;
			}

			return Job.BuildProfile.PORTAL;
		}

		String branchName = getBranchName();

		if (!branchName.equals("master") && !branchName.startsWith("ee-")) {
			return Job.BuildProfile.DXP;
		}

		return Job.BuildProfile.PORTAL;
	}

	@Override
	public JSONObject getBuildResultsJSONObject(
		String[] buildResults, String[] testStatuses, String[] dataTypes) {

		JSONObject buildResultsJSONObject = new JSONObject();

		if (buildResults != null) {
			List<String> buildResultsList = Arrays.asList(buildResults);

			if (!buildResultsList.contains(getResult())) {
				return buildResultsJSONObject;
			}
		}

		List<TestResult> testResults = new ArrayList<>();

		if (testStatuses == null) {
			testResults = getTestResults(null);
		}
		else {
			for (String testStatus : testStatuses) {
				try {
					testResults.addAll(getTestResults(testStatus));
				}
				catch (RuntimeException runtimeException) {
					System.out.println(runtimeException.getMessage());
				}
			}
		}

		if (dataTypes == null) {
			dataTypes = new String[] {"name", "status"};
		}

		List<String> dataTypesList = Arrays.asList(dataTypes);

		if (dataTypesList.contains("buildResults") &&
			(this instanceof BatchBuild)) {

			JSONArray buildResultsJSONArray = new JSONArray();

			for (Build downstreamBuild : getDownstreamBuilds(null)) {
				JSONObject buildResultJSONObject = new JSONObject();

				if (downstreamBuild instanceof AxisBuild) {
					AxisBuild downstreamAxisBuild = (AxisBuild)downstreamBuild;

					buildResultJSONObject.put(
						"axisName", downstreamAxisBuild.getAxisName());
				}

				if (dataTypesList.contains("buildURL")) {
					buildResultJSONObject.put(
						"buildURL", downstreamBuild.getBuildURL());
				}

				if (dataTypesList.contains("duration")) {
					buildResultJSONObject.put(
						"duration", downstreamBuild.getDuration());
				}

				buildResultJSONObject.put(
					"result", downstreamBuild.getResult());

				if ((downstreamBuild instanceof AxisBuild) &&
					dataTypesList.contains("stopWatchRecords")) {

					AxisBuild downstreamAxisBuild = (AxisBuild)downstreamBuild;

					StopWatchRecordsGroup stopWatchRecordsGroup =
						downstreamAxisBuild.getStopWatchRecordsGroup();

					JSONArray stopWatchRecordsGroupJSONArray =
						stopWatchRecordsGroup.getJSONArray();

					if (stopWatchRecordsGroupJSONArray.length() > 0) {
						buildResultJSONObject.put(
							"stopWatchRecords", stopWatchRecordsGroupJSONArray);
					}
				}

				buildResultsJSONArray.put(buildResultJSONObject);
			}

			buildResultsJSONObject.put("buildResults", buildResultsJSONArray);
		}

		if (dataTypesList.contains("testResults")) {
			JSONArray testResultsJSONArray = new JSONArray();

			for (TestResult testResult : testResults) {
				JSONObject testResultJSONObject = new JSONObject();

				if (dataTypesList.contains("buildURL")) {
					Build build = testResult.getBuild();

					testResultJSONObject.put("buildURL", build.getBuildURL());
				}

				if (dataTypesList.contains("duration")) {
					testResultJSONObject.put(
						"duration", testResult.getDuration());
				}

				if (dataTypesList.contains("errorDetails")) {
					String errorDetails = testResult.getErrorDetails();

					if (errorDetails != null) {
						if (errorDetails.contains("\n")) {
							int index = errorDetails.indexOf("\n");

							errorDetails = errorDetails.substring(0, index);
						}

						if (errorDetails.length() > 200) {
							errorDetails = errorDetails.substring(0, 200);
						}
					}

					testResultJSONObject.put("errorDetails", errorDetails);
				}

				if (dataTypesList.contains("name")) {
					testResultJSONObject.put(
						"name", testResult.getDisplayName());
				}

				if (dataTypesList.contains("status")) {
					testResultJSONObject.put("status", testResult.getStatus());
				}

				testResultsJSONArray.put(testResultJSONObject);
			}

			buildResultsJSONObject.put("testResults", testResultsJSONArray);
		}

		buildResultsJSONObject.put("jobVariant", getJobVariant());
		buildResultsJSONObject.put("result", getResult());

		return buildResultsJSONObject;
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
		catch (MalformedURLException | URISyntaxException exception) {
			throw new RuntimeException("Unable to encode build URL", exception);
		}
		catch (UnsupportedEncodingException unsupportedEncodingException) {
			throw new RuntimeException(
				"Unable to decode job URL " + jobURL,
				unsupportedEncodingException);
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
		String archiveFileContent = getArchiveFileContent("consoleText");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(archiveFileContent)) {
			return archiveFileContent;
		}

		String buildURL = getBuildURL();

		if (buildURL == null) {
			return "";
		}

		if (_jenkinsConsoleTextLoader == null) {
			_jenkinsConsoleTextLoader = new JenkinsConsoleTextLoader(
				getBuildURL());
		}

		return _jenkinsConsoleTextLoader.getConsoleText();
	}

	@Override
	public Long getDelayTime() {
		Long startTime = getStartTime();

		long currentTime = JenkinsResultsParserUtil.getCurrentTimeMillis();

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
		List<Build> filteredDownstreamBuilds = Collections.synchronizedList(
			new ArrayList<Build>());

		if ((result == null) && (status == null)) {
			filteredDownstreamBuilds.addAll(downstreamBuilds);

			return filteredDownstreamBuilds;
		}

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

			duration =
				JenkinsResultsParserUtil.getCurrentTimeMillis() - timestamp;
		}

		return duration;
	}

	@Override
	public String getFailureMessage() {
		Element failureMessageElement = getFailureMessageElement();

		if (failureMessageElement == null) {
			return null;
		}

		Element codeElement = failureMessageElement.element("code");

		if (codeElement == null) {
			return null;
		}

		return codeElement.getText();
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
	public Map<String, String> getInjectedEnvironmentVariablesMap()
		throws IOException {

		String localBuildURL = JenkinsResultsParserUtil.getLocalURL(
			getBuildURL());

		JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
			localBuildURL + "/injectedEnvVars/api/json", false);

		JSONObject envMapJSONObject = jsonObject.getJSONObject("envMap");

		Set<String> envMapJSONObjectKeySet = envMapJSONObject.keySet();

		Map<String, String> injectedEnvironmentVariablesMap = new HashMap<>();

		for (String key : envMapJSONObjectKeySet) {
			injectedEnvironmentVariablesMap.put(
				key, envMapJSONObject.getString(key));
		}

		return injectedEnvironmentVariablesMap;
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
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get Jenkins authentication token", ioException);
		}

		for (Map.Entry<String, String> parameter : parameters.entrySet()) {
			sb.append(parameter.getKey());
			sb.append("=");
			sb.append(parameter.getValue());
			sb.append("&");
		}

		sb.deleteCharAt(sb.length() - 1);

		return JenkinsResultsParserUtil.fixURL(sb.toString());
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

		_jenkinsSlave = _jenkinsMaster.getJenkinsSlave(slaveName);

		return _jenkinsSlave;
	}

	@Override
	public Job getJob() {
		if (_job != null) {
			return _job;
		}

		_job = JobFactory.newJob(this);

		return _job;
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
				Build.DEPENDENCIES_URL_TOKEN, "/", getArchiveName(), "/",
				_jenkinsMaster.getName(), "/", jobName);
		}

		String jobURL = JenkinsResultsParserUtil.combine(
			"https://", _jenkinsMaster.getName(), ".liferay.com/job/", jobName);

		try {
			return JenkinsResultsParserUtil.encode(jobURL);
		}
		catch (MalformedURLException | URISyntaxException exception) {
			throw new RuntimeException(
				"Unable to encode job URL " + jobURL, exception);
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

	@Override
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

	public long getQueuingDuration() {
		JSONObject buildJSONObject = getBuildJSONObject(
			"actions[queuingDurationMillis]");

		JSONArray actionsJSONArray = buildJSONObject.getJSONArray("actions");

		for (int i = 0; i < actionsJSONArray.length(); i++) {
			Object actions = actionsJSONArray.get(i);

			if (actions == JSONObject.NULL) {
				continue;
			}

			JSONObject actionJSONObject = actionsJSONArray.getJSONObject(i);

			if (actionJSONObject.has("queuingDurationMillis")) {
				return actionJSONObject.getLong("queuingDurationMillis");
			}
		}

		return 0;
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
		return JenkinsResultsParserUtil.getCurrentTimeMillis() -
			statusModifiedTime;
	}

	@Override
	public long getStatusDuration(String status) {
		if (statusDurations.containsKey(status)) {
			return statusDurations.get(status);
		}

		return 0;
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
	public TestClassResult getTestClassResult(String testClassName) {
		if (!isCompleted()) {
			return null;
		}

		_initTestClassResults();

		if (_testClassResults == null) {
			return null;
		}

		return _testClassResults.get(testClassName);
	}

	@Override
	public List<TestClassResult> getTestClassResults() {
		if (!isCompleted()) {
			return new ArrayList<>();
		}

		_initTestClassResults();

		if (_testClassResults == null) {
			return new ArrayList<>();
		}

		return new ArrayList<>(_testClassResults.values());
	}

	@Override
	public synchronized List<URL> getTestrayAttachmentURLs() {
		if (_testrayAttachmentURLs != null) {
			return _testrayAttachmentURLs;
		}

		_testrayAttachmentURLs = new ArrayList<>();

		String consoleText = getConsoleText();

		for (String line : consoleText.split("\\n")) {
			Matcher matcher = _testrayAttachmentURLPattern.matcher(line);

			if (!matcher.find()) {
				continue;
			}

			try {
				_testrayAttachmentURLs.add(new URL(matcher.group("url")));
			}
			catch (MalformedURLException malformedURLException) {
				throw new RuntimeException(malformedURLException);
			}
		}

		return _testrayAttachmentURLs;
	}

	@Override
	public synchronized List<URL> getTestrayS3AttachmentURLs() {
		if (_testrayS3AttachmentURLs != null) {
			return _testrayS3AttachmentURLs;
		}

		_testrayS3AttachmentURLs = new ArrayList<>();

		String consoleText = getConsoleText();

		for (String line : consoleText.split("\\n")) {
			Matcher matcher = _testrayS3ObjectURLPattern.matcher(line);

			if (!matcher.find()) {
				continue;
			}

			try {
				_testrayS3AttachmentURLs.add(new URL(matcher.group("url")));
			}
			catch (MalformedURLException malformedURLException) {
				throw new RuntimeException(malformedURLException);
			}
		}

		return _testrayS3AttachmentURLs;
	}

	@Override
	public JSONObject getTestReportJSONObject(boolean checkCache) {
		String archiveFileContent = getArchiveFileContent(
			"testReport/api/json");

		if (JenkinsResultsParserUtil.isJSONObject(archiveFileContent)) {
			return new JSONObject(archiveFileContent);
		}

		try {
			return JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.getLocalURL(
					getBuildURL() + "testReport/api/json"),
				checkCache);
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get test report JSON object", ioException);
		}
	}

	@Override
	public List<TestResult> getTestResults() {
		if (!isCompleted()) {
			return new ArrayList<>();
		}

		List<TestResult> testResults = new ArrayList<>();

		for (TestClassResult testClassResult : getTestClassResults()) {
			testResults.addAll(testClassResult.getTestResults());
		}

		return testResults;
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
	public String getTestSuiteName() {
		Build parentBuild = getParentBuild();

		if (parentBuild == null) {
			return "default";
		}

		return parentBuild.getTestSuiteName();
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
	public List<TestResult> getUniqueFailureTestResults() {
		List<TestResult> uniqueFailureTestResults = new ArrayList<>();

		for (Build downstreamBuild : getFailedDownstreamBuilds()) {
			uniqueFailureTestResults.addAll(
				downstreamBuild.getUniqueFailureTestResults());
		}

		return uniqueFailureTestResults;
	}

	@Override
	public List<TestResult> getUpstreamJobFailureTestResults() {
		List<TestResult> upstreamFailureTestResults = new ArrayList<>();

		for (Build downstreamBuild : getFailedDownstreamBuilds()) {
			upstreamFailureTestResults.addAll(
				downstreamBuild.getUpstreamJobFailureTestResults());
		}

		return upstreamFailureTestResults;
	}

	@Override
	public boolean hasBuildURL(String buildURL) {
		try {
			buildURL = JenkinsResultsParserUtil.decode(buildURL);
		}
		catch (UnsupportedEncodingException unsupportedEncodingException) {
			throw new RuntimeException(
				"Unable to decode " + buildURL, unsupportedEncodingException);
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
			catch (MalformedURLException malformedURLException) {
				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"Unable to compare urls ", buildURL, " and ",
						thisBuildURL),
					malformedURLException);
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
	public boolean isCompareToUpstream() {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		return topLevelBuild.isCompareToUpstream();
	}

	@Override
	public boolean isCompleted() {
		String result = getResult();
		String status = getStatus();

		if ((result == null) || (status == null)) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isFailing() {
		if (!isCompleted()) {
			return true;
		}

		String result = getResult();

		if ((result == null) || !result.equals("SUCCESS")) {
			return true;
		}

		return false;
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
	public boolean isUniqueFailure() {
		return !UpstreamFailureUtil.isBuildFailingInUpstreamJob(this);
	}

	@Override
	public void reinvoke() {
		reinvoke(null);
	}

	@Override
	public void reinvoke(ReinvokeRule reinvokeRule) {
		if (badBuildNumbers.size() >= REINVOCATIONS_SIZE_MAX) {
			return;
		}

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
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		System.out.println(getReinvokedMessage());

		reset();
	}

	@Override
	public void removeDownstreamBuild(Build build) {
		downstreamBuilds.remove(build);
	}

	@Override
	public String replaceBuildURL(String text) {
		if ((text == null) || text.isEmpty()) {
			return text;
		}

		if (downstreamBuilds != null) {
			for (Build downstreamBuild : getDownstreamBuilds("complete")) {
				Build downstreamBaseBuild = downstreamBuild;

				text = downstreamBaseBuild.replaceBuildURL(text);
			}
		}

		text = text.replaceAll(
			getBuildURLRegex(),
			Matcher.quoteReplacement(
				JenkinsResultsParserUtil.combine(
					Build.DEPENDENCIES_URL_TOKEN, "/", getArchivePath())));

		Build parentBuild = getParentBuild();

		while (parentBuild != null) {
			text = text.replaceAll(
				parentBuild.getBuildURLRegex(),
				Matcher.quoteReplacement(
					Build.DEPENDENCIES_URL_TOKEN +
						parentBuild.getArchivePath()));

			parentBuild = parentBuild.getParentBuild();
		}

		return text;
	}

	@Override
	public void setArchiveName(String archiveName) {
		_archiveName = archiveName;
	}

	@Override
	public void setArchiveRootDir(File archiveRootDir) {
		if (archiveRootDir == null) {
			archiveRootDir = new File(
				JenkinsResultsParserUtil.urlDependenciesFile.substring(
					"file:".length()));
		}

		if (!archiveRootDir.exists()) {
			throw new IllegalArgumentException(
				archiveRootDir.getPath() + " does not exist");
		}

		_archiveRootDir = archiveRootDir;
	}

	@Override
	public void setCompareToUpstream(boolean compareToUpstream) {
	}

	@Override
	public void takeSlaveOffline(SlaveOfflineRule slaveOfflineRule) {
		if ((slaveOfflineRule == null) || fromArchive) {
			return;
		}

		String pinnedMessage = "";

		if (!slaveOfflineRule.shutdown) {
			pinnedMessage = "PINNED\n";
		}

		JenkinsSlave jenkinsSlave = getJenkinsSlave();

		String message = JenkinsResultsParserUtil.combine(
			pinnedMessage, slaveOfflineRule.getName(), " failure detected at ",
			getBuildURL(), ". ", jenkinsSlave.getName(),
			" will be taken offline.\n\n", slaveOfflineRule.toString(),
			"\n\n\nOffline Slave URL: https://", _jenkinsMaster.getName(),
			".liferay.com/computer/", jenkinsSlave.getName(), "\n");

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
	public synchronized void update() {
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

					JenkinsSlave jenkinsSlave = getJenkinsSlave();

					if (jenkinsSlave != null) {
						jenkinsSlave.update();

						if (!fromArchive && !jenkinsSlave.isOffline()) {
							for (SlaveOfflineRule slaveOfflineRule :
									slaveOfflineRules) {

								if (!slaveOfflineRule.matches(this)) {
									continue;
								}

								takeSlaveOffline(slaveOfflineRule);

								break;
							}
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
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}
		}

		if (!fromArchive && JenkinsResultsParserUtil.isCINode()) {
			archive(getArchiveName());
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

	public static class DefaultBranchInformation implements BranchInformation {

		@Override
		public String getCachedRemoteGitRefName() {
			return JenkinsResultsParserUtil.combine(
				"cache-", getReceiverUsername(), "-", getUpstreamBranchSHA(),
				"-", getOriginName(), "-", getSenderBranchSHA());
		}

		@Override
		public String getOriginName() {
			String branchInformationString = _getBranchInformationString();

			String regex = "[\\S\\s]*github.origin.name=(.+)\\n[\\S\\s]*";

			if (branchInformationString.matches(regex)) {
				return branchInformationString.replaceAll(regex, "$1");
			}

			return null;
		}

		@Override
		public Integer getPullRequestNumber() {
			String branchInformationString = _getBranchInformationString();

			String regex =
				"[\\S\\s]*github.pull.request.number=(\\d+)\\n[\\S\\s]*";

			if (branchInformationString.matches(regex)) {
				return Integer.valueOf(
					branchInformationString.replaceAll(regex, "$1"));
			}

			return 0;
		}

		@Override
		public String getReceiverUsername() {
			String branchInformationString = _getBranchInformationString();

			String regex = "[\\S\\s]*github.receiver.username=(.+)\\n[\\S\\s]*";

			if (branchInformationString.matches(regex)) {
				return branchInformationString.replaceAll(regex, "$1");
			}

			return null;
		}

		@Override
		public String getRepositoryName() {
			Properties buildProperties;

			try {
				buildProperties = JenkinsResultsParserUtil.getBuildProperties();
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}

			String repositoryType = _repositoryType;

			if (repositoryType.equals("portal.base") ||
				repositoryType.equals("portal.ee")) {

				repositoryType = "portal";
			}

			return JenkinsResultsParserUtil.getProperty(
				buildProperties, repositoryType + ".repository",
				getUpstreamBranchName());
		}

		@Override
		public String getSenderBranchName() {
			String branchInformationString = _getBranchInformationString();

			String regex =
				"[\\S\\s]*github.sender.branch.name=(.+)\\n[\\S\\s]*";

			if (branchInformationString.matches(regex)) {
				return branchInformationString.replaceAll(regex, "$1");
			}

			return null;
		}

		@Override
		public String getSenderBranchSHA() {
			String branchInformationString = _getBranchInformationString();

			String regex = "[\\S\\s]*github.sender.branch.sha=(.+)\\n[\\S\\s]*";

			if (branchInformationString.matches(regex)) {
				return branchInformationString.replaceAll(regex, "$1");
			}

			return null;
		}

		@Override
		public RemoteGitRef getSenderRemoteGitRef() {
			String remoteURL = JenkinsResultsParserUtil.combine(
				"git@github.com:", getSenderUsername(), "/",
				getRepositoryName(), ".git");

			return GitUtil.getRemoteGitRef(
				getSenderBranchName(), new File("."), remoteURL);
		}

		@Override
		public String getSenderUsername() {
			String branchInformationString = _getBranchInformationString();

			String regex = "[\\S\\s]*github.sender.username=(.+)\\n[\\S\\s]*";

			if (branchInformationString.matches(regex)) {
				return branchInformationString.replaceAll(regex, "$1");
			}

			return null;
		}

		@Override
		public String getUpstreamBranchName() {
			String branchInformationString = _getBranchInformationString();

			String regex =
				"[\\S\\s]*github.upstream.branch.name=(.+)\\n[\\S\\s]*";

			if (branchInformationString.matches(regex)) {
				return branchInformationString.replaceAll(regex, "$1");
			}

			return null;
		}

		@Override
		public String getUpstreamBranchSHA() {
			String branchInformationString = _getBranchInformationString();

			String regex =
				"[\\S\\s]*github.upstream.branch.sha=(.+)\\n[\\S\\s]*";

			if (branchInformationString.matches(regex)) {
				return branchInformationString.replaceAll(regex, "$1");
			}

			return null;
		}

		protected DefaultBranchInformation(Build build, String repositoryType) {
			_build = build;
			_repositoryType = repositoryType;
		}

		private String _getBranchInformationString() {
			if (_branchInformationString != null) {
				return _branchInformationString;
			}

			String consoleText = _build.getConsoleText();

			int x = -1;

			Pattern pattern = Pattern.compile(
				JenkinsResultsParserUtil.combine(
					"## (http://cloud-.*/)?git.", _repositoryType,
					".properties"));

			Matcher matcher = pattern.matcher(consoleText);

			if (matcher.find()) {
				x = matcher.start();
			}

			if (x == -1) {
				return "";
			}

			int y = consoleText.indexOf("prepare.repositories.", x);

			if (y == -1) {
				y = consoleText.indexOf("Deleting:", x);
			}

			y = consoleText.indexOf("\n", y);

			if (y == -1) {
				return "";
			}

			_branchInformationString = consoleText.substring(x, y);

			return _branchInformationString;
		}

		private String _branchInformationString;
		private final Build _build;
		private final String _repositoryType;

	}

	protected static boolean isHighPriorityBuildFailureElement(
		Element gitHubMessage) {

		String content = null;

		try {
			content = Dom4JUtil.format(gitHubMessage, false);
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to format github message", ioException);
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

		if (!fromArchive && JenkinsResultsParserUtil.isCINode()) {
			TopLevelBuild topLevelBuild = getTopLevelBuild();

			if (topLevelBuild != null) {
				_archiveRootDir = new File(topLevelBuild.getBuildDirPath());
			}
			else {
				_archiveRootDir = new File(getBuildDirPath());
			}
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
		if (!JenkinsResultsParserUtil.isNullOrEmpty(
				getArchiveFileContent("consoleText"))) {

			return;
		}

		try {
			writeArchiveFile(
				JenkinsResultsParserUtil.redact(getConsoleText()),
				getArchivePath() + "/consoleText");
		}
		catch (IOException ioException) {
			throw new RuntimeException("Unable to write file", ioException);
		}
	}

	protected void archiveJSON() {
		downloadSampleURL(true, "api/json");
		downloadSampleURL(false, "testReport/api/json");
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

	protected void downloadSampleURL(boolean required, String urlSuffix) {
		if (!JenkinsResultsParserUtil.isNullOrEmpty(
				getArchiveFileContent(urlSuffix))) {

			return;
		}

		String urlString = getBuildURL() + urlSuffix;

		if (urlString.endsWith("json")) {
			urlString += "?pretty";
		}

		urlSuffix = JenkinsResultsParserUtil.fixFileName(urlSuffix);

		String content = null;

		try {
			content = JenkinsResultsParserUtil.toString(
				JenkinsResultsParserUtil.getLocalURL(urlString), false, 0, 0, 0,
				true);
		}
		catch (IOException ioException) {
			if (required) {
				throw new RuntimeException(
					"Unable to download sample " + urlString, ioException);
			}

			return;
		}

		try {
			writeArchiveFile(content, getArchivePath() + "/" + urlSuffix);
		}
		catch (IOException ioException) {
			throw new RuntimeException("Unable to write file", ioException);
		}
	}

	protected void extractBuildURLComponents(Matcher matcher) {
		_buildNumber = Integer.parseInt(matcher.group("buildNumber"));
		setJenkinsMaster(JenkinsMaster.getInstance(matcher.group("master")));
		setJobName(matcher.group("jobName"));
	}

	protected void findDownstreamBuilds() {
		List<String> foundDownstreamBuildURLs = new ArrayList<>(
			findDownstreamBuildsInConsoleText());

		JSONObject buildJSONObject = getBuildJSONObject("runs[number,url]");

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
		return Pattern.compile(
			JenkinsResultsParserUtil.combine(
				"(", Pattern.quote(Build.DEPENDENCIES_URL_TOKEN), "|",
				Pattern.quote(JenkinsResultsParserUtil.urlDependenciesFile),
				"|",
				Pattern.quote(JenkinsResultsParserUtil.urlDependenciesHttp),
				")/*(?<archiveName>.*)/(?<master>[^/]+)/+(?<jobName>[^/]+)",
				".*/(?<buildNumber>\\d+)/?"));
	}

	protected String getArchiveFileContent(String urlSuffix) {
		File archiveFile = new File(
			getArchiveRootDir(), getArchivePath() + "/" + urlSuffix);

		if (!archiveFile.exists()) {
			return null;
		}

		try {
			return JenkinsResultsParserUtil.read(archiveFile);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
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

	protected BranchInformation getBranchInformation(String repositoryType) {
		BranchInformation branchInformation = _branchInformationMap.get(
			repositoryType);

		if (branchInformation == null) {
			branchInformation = new DefaultBranchInformation(
				this, repositoryType);

			String repositoryName = branchInformation.getRepositoryName();

			if (repositoryName == null) {
				return null;
			}

			_branchInformationMap.put(repositoryType, branchInformation);
		}

		return _branchInformationMap.get(repositoryType);
	}

	protected JSONObject getBuildJSONObject(String tree) {
		return JenkinsAPIUtil.getAPIJSONObject(getBuildURL(), tree);
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
				sb.append(".");

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

			if (Objects.equals(downstreamBuildResult, result)) {
				count++;
			}
		}

		return count;
	}

	protected Map<Build, Element> getDownstreamBuildMessages(
		List<Build> downstreamBuilds) {

		List<Callable<Element>> callables = new ArrayList<>();

		for (final Build downstreamBuild : downstreamBuilds) {
			Callable<Element> callable = new Callable<Element>() {

				public Element call() {
					return downstreamBuild.getGitHubMessageElement();
				}

			};

			callables.add(callable);
		}

		ParallelExecutor<Element> parallelExecutor = new ParallelExecutor<>(
			callables, getExecutorService());

		List<Element> elements = parallelExecutor.execute();

		Map<Build, Element> elementsMap = new LinkedHashMap<>();

		for (int i = 0; i < elements.size(); i++) {
			elementsMap.put(downstreamBuilds.get(i), elements.get(i));
		}

		return elementsMap;
	}

	protected ExecutorService getExecutorService() {
		return null;
	}

	protected List<Build> getFailedDownstreamBuilds() {
		List<Build> failedDownstreamBuilds = new ArrayList<>();

		failedDownstreamBuilds.addAll(getDownstreamBuilds("ABORTED", null));
		failedDownstreamBuilds.addAll(getDownstreamBuilds("FAILURE", null));
		failedDownstreamBuilds.addAll(getDownstreamBuilds("UNSTABLE", null));

		return failedDownstreamBuilds;
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

	protected String getJenkinsReportBuildInfoCellElementTagName() {
		return "td";
	}

	protected List<Element> getJenkinsReportStopWatchRecordElements() {
		List<Element> jenkinsReportStopWatchRecordTableRowElements =
			new ArrayList<>();

		for (StopWatchRecord stopWatchRecord : getStopWatchRecordsGroup()) {
			jenkinsReportStopWatchRecordTableRowElements.addAll(
				_getStopWatchRecordTableRowElements(stopWatchRecord));
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
		catch (IOException ioException) {
			throw new RuntimeException("Unable to get build JSON", ioException);
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
		return "Reinvoked: " + getBuildURL();
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

	protected Element getStopWatchRecordExpanderAnchorElement(
		StopWatchRecord stopWatchRecord, String namespace) {

		Set<StopWatchRecord> childStopWatchRecords =
			stopWatchRecord.getChildStopWatchRecords();

		if (childStopWatchRecords == null) {
			return null;
		}

		Element expanderAnchorElement = Dom4JUtil.getNewAnchorElement("", "+ ");

		expanderAnchorElement.addAttribute(
			"id",
			JenkinsResultsParserUtil.combine(
				namespace, "-expander-anchor-", stopWatchRecord.getName()));
		expanderAnchorElement.addAttribute(
			"onClick",
			JenkinsResultsParserUtil.combine(
				"return toggleStopWatchRecordExpander(\'", namespace, "\', \'",
				stopWatchRecord.getName(), "\')"));
		expanderAnchorElement.addAttribute(
			"style",
			"font-family: monospace, monospace; text-decoration: none");

		return expanderAnchorElement;
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
		StopWatchRecordsGroup stopWatchRecordsGroup =
			new StopWatchRecordsGroup();

		String consoleText = getConsoleText();

		for (String line : consoleText.split("\n")) {
			Matcher matcher = stopWatchStartTimestampPattern.matcher(line);

			if (matcher.matches()) {
				Date timestamp = null;

				try {
					timestamp = stopWatchTimestampSimpleDateFormat.parse(
						matcher.group("timestamp"));
				}
				catch (ParseException parseException) {
					throw new RuntimeException(
						"Unable to parse timestamp in " + line, parseException);
				}

				String stopWatchName = matcher.group("name");

				stopWatchRecordsGroup.add(
					new StopWatchRecord(stopWatchName, timestamp.getTime()));

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
		catch (IOException ioException) {
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
			BuildDatabase buildDatabase = BuildDatabaseUtil.getBuildDatabase(
				this);

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
		JSONObject testReportJSONObject = getTestReportJSONObject(false);

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
		badBuildNumbers.add(getBuildNumber());

		setResult(null);

		setBuildNumber(-1);

		downstreamBuilds.clear();
	}

	protected void setBuildNumber(int buildNumber) {
		if (_buildNumber != buildNumber) {
			int previousBuildNumber = _buildNumber;

			_buildNumber = buildNumber;

			consoleReadCursor = 0;

			if (_buildNumber == -1) {
				setStatus("starting");
			}
			else if (!badBuildNumbers.contains(previousBuildNumber)) {
				setStatus("running");
			}
		}
	}

	protected void setBuildURL(String buildURL) {
		try {
			buildURL = JenkinsResultsParserUtil.decode(buildURL);
		}
		catch (UnsupportedEncodingException unsupportedEncodingException) {
			throw new IllegalArgumentException(
				"Unable to decode " + buildURL, unsupportedEncodingException);
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
		catch (IOException ioException) {
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

			setArchiveName(matcher.group("archiveName"));
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
		if (getBuildURL() != null) {
			return;
		}

		try {
			invocationURL = JenkinsResultsParserUtil.decode(invocationURL);
		}
		catch (UnsupportedEncodingException unsupportedEncodingException) {
			throw new IllegalArgumentException(
				"Unable to decode " + invocationURL,
				unsupportedEncodingException);
		}

		Matcher invocationURLMatcher = invocationURLPattern.matcher(
			invocationURL);

		if (!invocationURLMatcher.find()) {
			throw new RuntimeException("Invalid invocation URL");
		}

		setJobName(invocationURLMatcher.group("jobName"));
		setJenkinsMaster(
			JenkinsMaster.getInstance(invocationURLMatcher.group("master")));

		loadParametersFromQueryString(invocationURL);

		setStatus("starting");

		if (JenkinsResultsParserUtil.isCINode()) {
			invocationURL = JenkinsResultsParserUtil.getLocalURL(invocationURL);
		}

		try {
			JenkinsResultsParserUtil.toString(invocationURL, false);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
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

			statusModifiedTime =
				JenkinsResultsParserUtil.getCurrentTimeMillis();

			statusDurations.put(
				_previousStatus,
				statusModifiedTime - previousStatusModifiedTime);

			if (isParentBuildRoot() &&
				!badBuildNumbers.contains(_buildNumber)) {

				System.out.println(getBuildMessage());
			}
		}
	}

	protected String toJenkinsReportDateString(Date date, String timeZoneName) {
		Properties buildProperties = null;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get build properties", ioException);
		}

		return JenkinsResultsParserUtil.toDateString(
			date, buildProperties.getProperty("jenkins.report.date.format"),
			timeZoneName);
	}

	protected void writeArchiveFile(String content, String path)
		throws IOException {

		JenkinsResultsParserUtil.write(
			new File(getArchiveRootDir(), path),
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
		JSONObject jobJSONObject = null;

		String jobURL = getJobURL();

		if (JenkinsResultsParserUtil.isCINode()) {
			jobURL = JenkinsResultsParserUtil.getLocalURL(jobURL);
		}

		try {
			jobJSONObject = JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.combine(
					jobURL, "/api/json?tree=actions[parameterDefinitions[",
					"defaultParameterValue[value],name]]"));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		JSONObject actionsJSONObject = null;

		JSONArray actionsJSONArray = jobJSONObject.getJSONArray("actions");

		for (int i = 0; i < actionsJSONArray.length(); i++) {
			JSONObject jsonObject = actionsJSONArray.getJSONObject(i);

			if (jsonObject.has("parameterDefinitions")) {
				actionsJSONObject = jsonObject;

				break;
			}
		}

		Map<String, String> jobParameters = new HashMap<>();

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

	private List<Element> _getStopWatchRecordTableRowElements(
		StopWatchRecord stopWatchRecord) {

		Element buildInfoElement = Dom4JUtil.getNewElement("tr", null);

		String buildHashCode = String.valueOf(hashCode());

		buildInfoElement.addAttribute(
			"id", buildHashCode + "-" + stopWatchRecord.getName());

		buildInfoElement.addAttribute("style", "display: none");

		Element expanderAnchorElement = getStopWatchRecordExpanderAnchorElement(
			stopWatchRecord, buildHashCode);

		Element nameElement = Dom4JUtil.getNewElement(
			"td", buildInfoElement, expanderAnchorElement,
			stopWatchRecord.getShortName());

		int indent =
			(getDepth() + stopWatchRecord.getDepth() + 1) *
				_PIXELS_WIDTH_INDENT;

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
			toJenkinsReportDateString(
				new Date(stopWatchRecord.getStartTimestamp()),
				getJenkinsReportTimeZoneName()));

		Long duration = stopWatchRecord.getDuration();

		if (duration == null) {
			Dom4JUtil.getNewElement("td", buildInfoElement, "&nbsp;");
		}
		else {
			Dom4JUtil.getNewElement(
				"td", buildInfoElement,
				JenkinsResultsParserUtil.toDurationString(
					stopWatchRecord.getDuration()));
		}

		Dom4JUtil.getNewElement("td", buildInfoElement, "&nbsp;");

		Dom4JUtil.getNewElement("td", buildInfoElement, "&nbsp;");

		List<Element> jenkinsReportTableRowElements = new ArrayList<>();

		jenkinsReportTableRowElements.add(buildInfoElement);

		Set<StopWatchRecord> childStopWatchRecords =
			stopWatchRecord.getChildStopWatchRecords();

		if (childStopWatchRecords != null) {
			List<String> childStopWatchRecordNames = new ArrayList<>(
				childStopWatchRecords.size());

			for (StopWatchRecord childStopWatchRecord : childStopWatchRecords) {
				childStopWatchRecordNames.add(childStopWatchRecord.getName());

				List<Element> childJenkinsReportTableRowElements =
					_getStopWatchRecordTableRowElements(childStopWatchRecord);

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
				JenkinsResultsParserUtil.join(",", childStopWatchRecordNames));
		}

		return jenkinsReportTableRowElements;
	}

	private synchronized void _initTestClassResults() {
		if (!isCompleted() || (_testClassResults != null)) {
			return;
		}

		JSONObject testReportJSONObject = null;

		try {
			testReportJSONObject = getTestReportJSONObject(true);
		}
		catch (RuntimeException runtimeException) {
			_testClassResults = new ConcurrentHashMap<>();

			return;
		}

		_testClassResults = new ConcurrentHashMap<>();

		if (testReportJSONObject == null) {
			return;
		}

		List<JSONArray> suitesJSONArrays = new ArrayList<>();

		if (testReportJSONObject.has("suites")) {
			suitesJSONArrays.add(testReportJSONObject.getJSONArray("suites"));
		}
		else if (testReportJSONObject.has("childReports")) {
			JSONArray childReportsJSONArray = testReportJSONObject.getJSONArray(
				"childReports");

			for (int i = 0; i < childReportsJSONArray.length(); i++) {
				JSONObject childReportJSONObject =
					childReportsJSONArray.getJSONObject(i);

				if (!childReportJSONObject.has("result")) {
					continue;
				}

				JSONObject resultJSONObject =
					childReportJSONObject.getJSONObject("result");

				if (!resultJSONObject.has("suites")) {
					continue;
				}

				suitesJSONArrays.add(resultJSONObject.getJSONArray("suites"));
			}
		}

		for (JSONArray suitesJSONArray : suitesJSONArrays) {
			for (int i = 0; i < suitesJSONArray.length(); i++) {
				JSONObject suiteJSONObject = suitesJSONArray.getJSONObject(i);

				TestClassResult testClassResult =
					TestClassResultFactory.newTestClassResult(
						this, suiteJSONObject);

				_testClassResults.put(
					testClassResult.getClassName(), testClassResult);
			}
		}
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

	private static final String[] _TOKENS_HIGH_PRIORITY_CONTENT = {
		"compileJSP", "SourceFormatter.format", "Unable to compile JSPs"
	};

	private static final MultiPattern _buildURLMultiPattern = new MultiPattern(
		JenkinsResultsParserUtil.combine(
			"\\w+://(?<master>[^/]+)/+job/+(?<jobName>[^/]+).*/(?<buildNumber>",
			"\\d+)/?"));
	private static final Pattern _testrayAttachmentURLPattern = Pattern.compile(
		"\\[beanshell\\] Uploaded (?<url>https://testray.liferay.com/[^\\s]+)");
	private static final Pattern _testrayS3ObjectURLPattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"\\[beanshell\\] Created S3 Object (?<url>",
			"https://storage.cloud.google.com/[^\\s?]+).*"));

	static {
		Properties properties = null;

		try {
			properties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get build properties", ioException);
		}

		_NAME_JENKINS_REPORT_TIME_ZONE = properties.getProperty(
			"jenkins.report.time.zone");
	}

	private String _archiveName = "archive";
	private File _archiveRootDir = new File(
		JenkinsResultsParserUtil.urlDependenciesFile.substring(
			"file:".length()));
	private final Map<String, BranchInformation> _branchInformationMap =
		new HashMap<>();
	private String _buildDescription;
	private int _buildNumber = -1;
	private JenkinsConsoleTextLoader _jenkinsConsoleTextLoader;
	private JenkinsMaster _jenkinsMaster;
	private JenkinsSlave _jenkinsSlave;
	private Job _job;
	private Map<String, String> _parameters = new HashMap<>();
	private final Build _parentBuild;
	private String _previousStatus;
	private String _result;
	private String _status;
	private Map<String, TestClassResult> _testClassResults;
	private List<URL> _testrayAttachmentURLs;
	private List<URL> _testrayS3AttachmentURLs;

}