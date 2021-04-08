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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class JenkinsCohort {

	public JenkinsCohort(String name) {
		_name = name;

		update();
	}

	public int getIdleJenkinsSlaveCount() {
		int idleJenkinsSlaveCount = 0;

		for (JenkinsMaster jenkinsMaster : _jenkinsMastersMap.values()) {
			idleJenkinsSlaveCount += jenkinsMaster.getIdleJenkinsSlavesCount();
		}

		return idleJenkinsSlaveCount;
	}

	public String getName() {
		return _name;
	}

	public int getOfflineJenkinsSlaveCount() {
		int offlineJenkinsSlaveCount = 0;

		for (JenkinsMaster jenkinsMaster : _jenkinsMastersMap.values()) {
			offlineJenkinsSlaveCount +=
				jenkinsMaster.getOfflineJenkinsSlavesCount();
		}

		return offlineJenkinsSlaveCount;
	}

	public int getOnlineJenkinsSlaveCount() {
		int onlineJenkinsSlaveCount = 0;

		for (JenkinsMaster jenkinsMaster : _jenkinsMastersMap.values()) {
			onlineJenkinsSlaveCount +=
				jenkinsMaster.getOnlineJenkinsSlavesCount();
		}

		return onlineJenkinsSlaveCount;
	}

	public int getQueuedBuildCount() {
		int queuedBuildCount = 0;

		for (JenkinsCohortJob jenkinsCohortJob :
				_jenkinsCohortJobsMap.values()) {

			queuedBuildCount =
				queuedBuildCount + jenkinsCohortJob.getQueuedBuildCount();
		}

		return queuedBuildCount;
	}

	public int getRunningBuildCount() {
		int runningBuildCount = 0;

		for (JenkinsCohortJob jenkinsCohortJob :
				_jenkinsCohortJobsMap.values()) {

			runningBuildCount =
				runningBuildCount + jenkinsCohortJob.getRunningBuildCount();
		}

		return runningBuildCount;
	}

	public void update() {
		Properties buildProperties = null;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get Jenkins properties", ioException);
		}

		if (_jenkinsMastersMap.isEmpty()) {
			List<JenkinsMaster> jenkinsMasters =
				JenkinsResultsParserUtil.getJenkinsMasters(
					buildProperties, JenkinsMaster.getSlaveRAMMinimumDefault(),
					JenkinsMaster.getSlavesPerHostDefault(), getName());

			for (JenkinsMaster jenkinsMaster : jenkinsMasters) {
				_jenkinsMastersMap.put(jenkinsMaster.getName(), jenkinsMaster);
			}
		}

		List<Callable<Void>> callables = new ArrayList<>();
		final List<String> buildURLs = Collections.synchronizedList(
			new ArrayList<String>());
		final Map<String, JSONObject> queuedBuildURLs =
			Collections.synchronizedMap(new HashMap<String, JSONObject>());

		for (final JenkinsMaster jenkinsMaster : _jenkinsMastersMap.values()) {
			Callable<Void> callable = new Callable<Void>() {

				@Override
				public Void call() {
					jenkinsMaster.update(false);

					buildURLs.addAll(jenkinsMaster.getBuildURLs());
					queuedBuildURLs.putAll(jenkinsMaster.getQueuedBuildURLs());

					return null;
				}

			};

			callables.add(callable);
		}

		ThreadPoolExecutor threadPoolExecutor =
			JenkinsResultsParserUtil.getNewThreadPoolExecutor(
				_jenkinsMastersMap.size(), true);

		ParallelExecutor<Void> parallelExecutor = new ParallelExecutor<>(
			callables, threadPoolExecutor);

		parallelExecutor.execute();

		for (String buildURL : buildURLs) {
			_loadBuildURL(buildURL);
		}

		for (Map.Entry<String, JSONObject> entry : queuedBuildURLs.entrySet()) {
			_loadQueuedBuildURL(entry);
		}
	}

	public void writeDataJavaScriptFile(String filePath) throws IOException {
		StringBuilder sb = new StringBuilder();

		sb.append("var jenkinsDataGeneratedDate = new Date(");
		sb.append(JenkinsResultsParserUtil.getCurrentTimeMillis());
		sb.append(");\nvar nodeData = ");

		JSONArray nodeDataTableJSONArray = new JSONArray();

		nodeDataTableJSONArray.put(
			Arrays.asList(
				"Occupied Nodes", "Online Nodes", "Queued Builds",
				"Offline Nodes", "Idle Nodes"));

		nodeDataTableJSONArray.put(
			Arrays.asList(
				getRunningBuildCount(), getOnlineJenkinsSlaveCount(),
				getQueuedBuildCount(), getOfflineJenkinsSlaveCount(),
				getIdleJenkinsSlaveCount()));

		sb.append(nodeDataTableJSONArray.toString());

		sb.append(";\nvar buildLoadData = ");

		JSONArray buildLoadDataTableJSONArray = new JSONArray();

		buildLoadDataTableJSONArray.put(
			Arrays.asList(
				"Name", "Total Builds", "Current Builds", "Queued Builds",
				"Top Level Builds"));

		for (JenkinsCohortJob jenkinsCohortJob :
				_jenkinsCohortJobsMap.values()) {

			buildLoadDataTableJSONArray.put(
				Arrays.asList(
					jenkinsCohortJob.getJobName(),
					_createJSONArray(
						jenkinsCohortJob.getTotalBuildCount(),
						_formatBuildCountText(
							jenkinsCohortJob.getTotalBuildCount(),
							jenkinsCohortJob.getTotalBuildPercentage())),
					_createJSONArray(
						jenkinsCohortJob.getRunningBuildCount(),
						_formatBuildCountText(
							jenkinsCohortJob.getRunningBuildCount(),
							jenkinsCohortJob.getRunningBuildPercentage())),
					_createJSONArray(
						jenkinsCohortJob.getQueuedBuildCount(),
						_formatBuildCountText(
							jenkinsCohortJob.getQueuedBuildCount(),
							jenkinsCohortJob.getQueuedBuildPercentage())),
					jenkinsCohortJob.getTopLevelBuildCount()));
		}

		sb.append(buildLoadDataTableJSONArray.toString());

		sb.append(";\nvar pullRequestData = ");

		JSONArray pullRequestDataTableJSONArray = new JSONArray();

		pullRequestDataTableJSONArray.put(
			Arrays.asList(
				"Pull Request URL", "Sender Username", "Branch Name",
				"Test Suite", "Status", "Queued Duration", "Duration"));

		for (JenkinsCohortJob jenkinsCohortJob :
				_jenkinsCohortJobsMap.values()) {

			String jobName = jenkinsCohortJob.getJobName();

			if (jobName.contains("test-portal-acceptance-pullrequest")) {
				for (String buildURL :
						jenkinsCohortJob.getTopLevelBuildURLs()) {

					JSONObject jsonObject = JenkinsAPIUtil.getAPIJSONObject(
						buildURL);

					long queuedDuration = 0;

					JSONArray actionsJSONArray = jsonObject.getJSONArray(
						"actions");

					for (int i = 0; i < actionsJSONArray.length(); i++) {
						Object actions = actionsJSONArray.get(i);

						if (actions == JSONObject.NULL) {
							continue;
						}

						JSONObject actionJSONObject =
							actionsJSONArray.getJSONObject(i);

						if (actionJSONObject.has("_class")) {
							String clazz = actionJSONObject.getString("_class");

							if (clazz.equals(
									"jenkins.metrics.impl.TimeInQueueAction")) {

								queuedDuration = actionJSONObject.getLong(
									"buildableDurationMillis");

								break;
							}
						}
					}

					long duration =
						JenkinsResultsParserUtil.getCurrentTimeMillis() -
							jsonObject.getLong("timestamp");

					pullRequestDataTableJSONArray.put(
						_createpullRequestDataTableRow(
							buildURL,
							JenkinsAPIUtil.getBuildParameters(jsonObject),
							queuedDuration, duration));
				}

				Map<String, JSONObject> queuedTopLevelBuildsJsonMap =
					jenkinsCohortJob.getQueuedTopLevelBuildsJsonMap();

				for (JSONObject jsonObject :
						queuedTopLevelBuildsJsonMap.values()) {

					try {
						Map<String, String> buildParameters =
							JenkinsAPIUtil.getBuildParameters(jsonObject);

						JSONObject taskJSONObject = jsonObject.getJSONObject(
							"task");

						String jobURL = taskJSONObject.getString("url");

						long queueDuration =
							JenkinsResultsParserUtil.getCurrentTimeMillis() -
								jsonObject.optLong("inQueueSince");

						pullRequestDataTableJSONArray.put(
							_createpullRequestDataTableRow(
								jobURL, buildParameters, queueDuration, 0));
					}
					catch (JSONException jsonException) {
						System.out.println(jsonObject.toString());

						throw new RuntimeException(jsonException);
					}
				}
			}
		}

		sb.append(pullRequestDataTableJSONArray.toString());

		sb.append(";");

		JenkinsResultsParserUtil.write(filePath, sb.toString());
	}

	private JSONArray _createJSONArray(Object... items) {
		JSONArray jsonArray = new JSONArray();

		for (Object item : items) {
			jsonArray.put(item);
		}

		return jsonArray;
	}

	private List<Object> _createpullRequestDataTableRow(
		String buildURL, Map<String, String> buildParameters,
		long queueDuration, long duration) {

		String githubReceiverUsername = buildParameters.get(
			"GITHUB_RECEIVER_USERNAME");

		String repositoryName = "liferay-portal";

		String githubUpstreamBranchName = buildParameters.get(
			"GITHUB_UPSTREAM_BRANCH_NAME");

		if ((githubUpstreamBranchName != null) &&
			!githubUpstreamBranchName.equals("master")) {

			repositoryName = repositoryName + "-ee";
		}

		String githubPullRequestNumber = buildParameters.get(
			"GITHUB_PULL_REQUEST_NUMBER");

		String githubSenderUsername = buildParameters.get(
			"GITHUB_SENDER_USERNAME");

		String ciTestSuite = buildParameters.get("CI_TEST_SUITE");

		Matcher matcher = _buildNumberPattern.matcher(buildURL);

		String status = "Queued";

		if (matcher.find()) {
			status = "Running";
		}

		return Arrays.asList(
			_createJSONArray(
				JenkinsResultsParserUtil.combine(
					repositoryName, "/", githubReceiverUsername, "#",
					githubPullRequestNumber),
				PullRequest.getURL(
					githubReceiverUsername, repositoryName,
					githubPullRequestNumber)),
			githubSenderUsername, githubUpstreamBranchName, ciTestSuite,
			_createJSONArray(status, buildURL),
			_createJSONArray(
				queueDuration,
				JenkinsResultsParserUtil.toDurationString(queueDuration)),
			_createJSONArray(
				duration, JenkinsResultsParserUtil.toDurationString(duration)));
	}

	private String _formatBuildCountText(
		int buildCount, String buildPercentage) {

		return buildCount + " (" + buildPercentage + ")";
	}

	private void _loadBuildURL(String buildURL) {
		Matcher jobNameMatcher = _jobNamePattern.matcher(buildURL);

		jobNameMatcher.find();

		String jobName = jobNameMatcher.group(1);

		String batchJobName = null;

		if (jobName.contains("-batch")) {
			batchJobName = jobName;

			jobName = jobName.replace("-batch", "");
		}

		if (!_jenkinsCohortJobsMap.containsKey(jobName)) {
			_jenkinsCohortJobsMap.put(jobName, new JenkinsCohortJob(jobName));
		}

		JenkinsCohortJob jenkinsCohortJob = _jenkinsCohortJobsMap.get(jobName);

		if (batchJobName == null) {
			jenkinsCohortJob.addTopLevelBuildURL(buildURL);
		}
		else {
			jenkinsCohortJob.addOtherBuildURL(buildURL);
		}
	}

	private void _loadQueuedBuildURL(
		Map.Entry<String, JSONObject> queuedBuildURL) {

		JSONObject jsonObject = queuedBuildURL.getValue();

		if (jsonObject.has("task")) {
			JSONObject taskJSONObject = jsonObject.getJSONObject("task");

			if (taskJSONObject.has("url")) {
				Matcher jobNameMatcher = _jobNamePattern.matcher(
					taskJSONObject.getString("url"));

				jobNameMatcher.find();

				String jobName = jobNameMatcher.group(1);

				String batchJobName = null;

				if (jobName.contains("-batch")) {
					batchJobName = jobName;

					jobName = jobName.replace("-batch", "");
				}

				if (!_jenkinsCohortJobsMap.containsKey(jobName)) {
					_jenkinsCohortJobsMap.put(
						jobName, new JenkinsCohortJob(jobName));
				}

				JenkinsCohortJob jenkinsCohortJob = _jenkinsCohortJobsMap.get(
					jobName);

				if (batchJobName == null) {
					jenkinsCohortJob.addQueuedTopLevelBuildJsonMapEntry(
						queuedBuildURL);
				}
				else {
					jenkinsCohortJob.addQueuedOtherBuildJsonMapEntry(
						queuedBuildURL);
				}
			}
		}
	}

	private static final Pattern _buildNumberPattern = Pattern.compile(
		".*\\/([0-9]+)");
	private static final Pattern _jobNamePattern = Pattern.compile(
		"https?:.*job\\/(.*?)\\/");

	private final Map<String, JenkinsCohortJob> _jenkinsCohortJobsMap =
		new HashMap<>();
	private final Map<String, JenkinsMaster> _jenkinsMastersMap =
		new HashMap<>();
	private final String _name;

	private class JenkinsCohortJob {

		public JenkinsCohortJob(String jenkinsCohortJobName) {
			_jenkinsCohortJobName = jenkinsCohortJobName;
		}

		public void addOtherBuildURL(String buildURL) {
			_otherBuildURLs.add(buildURL);
		}

		public void addQueuedOtherBuildJsonMapEntry(
			Map.Entry<String, JSONObject> queuedBuildsJsonMapEntry) {

			_queuedOtherBuildsJsonMap.put(
				queuedBuildsJsonMapEntry.getKey(),
				queuedBuildsJsonMapEntry.getValue());
		}

		public void addQueuedTopLevelBuildJsonMapEntry(
			Map.Entry<String, JSONObject> queuedTopLevelBuildMapEntry) {

			_queuedTopLevelBuildsJsonMap.put(
				queuedTopLevelBuildMapEntry.getKey(),
				queuedTopLevelBuildMapEntry.getValue());
		}

		public void addTopLevelBuildURL(String topLevelBuildURL) {
			_topLevelBuildURLs.add(topLevelBuildURL);
		}

		public String getJobName() {
			return _jenkinsCohortJobName;
		}

		public int getQueuedBuildCount() {
			return _queuedTopLevelBuildsJsonMap.size() +
				_queuedOtherBuildsJsonMap.size();
		}

		public String getQueuedBuildPercentage() {
			return CISystemStatusReportUtil.getPercentage(
				getQueuedBuildCount(),
				JenkinsCohort.this.getQueuedBuildCount());
		}

		public Map<String, JSONObject> getQueuedTopLevelBuildsJsonMap() {
			return _queuedTopLevelBuildsJsonMap;
		}

		public int getRunningBuildCount() {
			return _topLevelBuildURLs.size() + _otherBuildURLs.size();
		}

		public String getRunningBuildPercentage() {
			return CISystemStatusReportUtil.getPercentage(
				getRunningBuildCount(),
				JenkinsCohort.this.getRunningBuildCount());
		}

		public int getTopLevelBuildCount() {
			return _topLevelBuildURLs.size() +
				_queuedTopLevelBuildsJsonMap.size();
		}

		public List<String> getTopLevelBuildURLs() {
			return _topLevelBuildURLs;
		}

		public int getTotalBuildCount() {
			return getQueuedBuildCount() + getRunningBuildCount();
		}

		public String getTotalBuildPercentage() {
			return CISystemStatusReportUtil.getPercentage(
				getTotalBuildCount(),
				JenkinsCohort.this.getRunningBuildCount() +
					JenkinsCohort.this.getQueuedBuildCount());
		}

		private final String _jenkinsCohortJobName;
		private List<String> _otherBuildURLs = new ArrayList<>();
		private Map<String, JSONObject> _queuedOtherBuildsJsonMap =
			new HashMap<>();
		private Map<String, JSONObject> _queuedTopLevelBuildsJsonMap =
			new HashMap<>();
		private List<String> _topLevelBuildURLs = new ArrayList<>();

	}

}