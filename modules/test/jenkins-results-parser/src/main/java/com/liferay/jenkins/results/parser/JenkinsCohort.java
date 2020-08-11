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

import java.text.DecimalFormat;

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

		List<Callable<Void>> callables = new ArrayList<>();
		final List<String> jobURLs = new ArrayList<>();

		List<JenkinsMaster> jenkinsMasters =
			JenkinsResultsParserUtil.getJenkinsMasters(
				buildProperties, 16, getName());

		for (final JenkinsMaster jenkinsMaster : jenkinsMasters) {
			Callable<Void> callable = new Callable<Void>() {

				@Override
				public Void call() {
					jenkinsMaster.update();

					jobURLs.addAll(jenkinsMaster.getBuildURLs());
					jobURLs.addAll(jenkinsMaster.getQueuedBuildURLs());

					return null;
				}

			};

			callables.add(callable);
		}

		ThreadPoolExecutor threadPoolExecutor =
			JenkinsResultsParserUtil.getNewThreadPoolExecutor(
				jenkinsMasters.size(), true);

		ParallelExecutor<Void> parallelExecutor = new ParallelExecutor<>(
			callables, threadPoolExecutor);

		parallelExecutor.execute();

		for (String jobURL : jobURLs) {
			_loadJobURL(jobURL);
		}
	}

	public void writeDataJavaScriptFile(String filePath) throws IOException {
		StringBuilder sb = new StringBuilder();

		sb.append("var date = new Date(");
		sb.append(System.currentTimeMillis());
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

			List<String> topLevelBuildURLs =
				jenkinsCohortJob.getTopLevelBuildURLs();

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
					topLevelBuildURLs.size()));
		}

		sb.append(buildLoadDataTableJSONArray.toString());

		sb.append(";");

		JenkinsResultsParserUtil.write(filePath + "/data.js", sb.toString());
	}

	private static JSONArray _createJSONArray(Object... items) {
		JSONArray jsonArray = new JSONArray();

		for (Object item : items) {
			jsonArray.put(item);
		}

		return jsonArray;
	}

	private static String _getPercentage(Integer dividend, Integer divisor) {
		double quotient = (double)dividend / (double)divisor;

		DecimalFormat decimalFormat = new DecimalFormat("###.##%");

		return decimalFormat.format(quotient);
	}

	private String _formatBuildCountText(
		int buildCount, String buildPercentage) {

		return buildCount + " (" + buildPercentage + ")";
	}

	private void _loadJobURL(String jobURL) {
		Matcher jobNameMatcher = _jobNamePattern.matcher(jobURL);

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

		Matcher buildNumberMatcher = _buildNumberPattern.matcher(jobURL);

		if (buildNumberMatcher.find()) {
			jenkinsCohortJob.incrementRunningJobCount();
		}
		else {
			jenkinsCohortJob.incrementQueuedJobCount();
		}

		if (batchJobName == null) {
			jenkinsCohortJob.addTopLevelBuildURL(jobURL);
		}
	}

	private static final Pattern _buildNumberPattern = Pattern.compile(
		".*\\/([0-9]+)");
	private static final Pattern _jobNamePattern = Pattern.compile(
		"https?:.*job\\/(.*?)\\/");

	private final Map<String, JenkinsCohortJob> _jenkinsCohortJobsMap =
		new HashMap<>();
	private final String _name;

	private class JenkinsCohortJob {

		public JenkinsCohortJob(String jenkinsCohortJobName) {
			_jenkinsCohortJobName = jenkinsCohortJobName;
		}

		public void addTopLevelBuildURL(String topLevelBuildURL) {
			_topLevelBuildURLs.add(topLevelBuildURL);
		}

		public String getJobName() {
			return _jenkinsCohortJobName;
		}

		public int getQueuedBuildCount() {
			return _queuedBuildCount;
		}

		public String getQueuedBuildPercentage() {
			return _getPercentage(
				_queuedBuildCount, JenkinsCohort.this.getQueuedBuildCount());
		}

		public int getRunningBuildCount() {
			return _runningBuildCount;
		}

		public String getRunningBuildPercentage() {
			return _getPercentage(
				_runningBuildCount, JenkinsCohort.this.getRunningBuildCount());
		}

		public List<String> getTopLevelBuildURLs() {
			return _topLevelBuildURLs;
		}

		public int getTotalBuildCount() {
			return _queuedBuildCount + _runningBuildCount;
		}

		public String getTotalBuildPercentage() {
			return _getPercentage(
				getTotalBuildCount(),
				JenkinsCohort.this.getRunningBuildCount() +
					JenkinsCohort.this.getQueuedBuildCount());
		}

		public void incrementQueuedJobCount() {
			_queuedBuildCount = _queuedBuildCount + 1;
		}

		public void incrementRunningJobCount() {
			_runningBuildCount = _runningBuildCount + 1;
		}

		private final String _jenkinsCohortJobName;
		private int _queuedBuildCount;
		private int _runningBuildCount;
		private List<String> _topLevelBuildURLs = new ArrayList<>();

	}

}