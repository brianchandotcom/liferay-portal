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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class CISystemStatus {

	public CISystemStatus(String cohortName) {
		_cohortName = cohortName;

		update();
	}

	public String getCohortName() {
		return _cohortName;
	}

	public int getIdleCINodeCount() {
		return _idleCINodeCount;
	}

	public int getOnlineCINodeCount() {
		return _onlineCINodeCount;
	}

	public int getQueuedBuildCount() {
		int queuedBuildCount = 0;

		for (JobDatum jobDatum : _jobData.values()) {
			queuedBuildCount =
				queuedBuildCount + jobDatum.getQueuedBuildCount();
		}

		return queuedBuildCount;
	}

	public int getRunningBuildCount() {
		int runningBuildCount = 0;

		for (JobDatum jobDatum : _jobData.values()) {
			runningBuildCount =
				runningBuildCount + jobDatum.getRunningBuildCount();
		}

		return runningBuildCount;
	}

	public void update() {
		Properties buildProperties;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get Jenkins properties", ioException);
		}

		List<JenkinsMaster> jenkinsMasters =
			JenkinsResultsParserUtil.getJenkinsMasters(
				buildProperties, 16, getCohortName());

		final List<String> jobURLs = new ArrayList<>();

		List<Callable<Void>> callables = new ArrayList<>(jenkinsMasters.size());

		for (final JenkinsMaster jenkinsMaster : jenkinsMasters) {
			Callable<Void> callable = new Callable<Void>() {

				@Override
				public Void call() {
					jenkinsMaster.update();

					jobURLs.addAll(jenkinsMaster.getRunningJobURLs());
					jobURLs.addAll(jenkinsMaster.getQueuedJobURLs());

					_onlineCINodeCount =
						_onlineCINodeCount +
							jenkinsMaster.getOnlineJenkinsSlavesCount();

					_idleCINodeCount =
						_idleCINodeCount + jenkinsMaster.getIdleSlavesCount();

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

		JSONArray nodeDataJSONArray = new JSONArray();
		JSONObject nodeDataJSONObject = new JSONObject();

		nodeDataJSONObject.put("CI Node Capacity", getOnlineCINodeCount());
		nodeDataJSONObject.put("Idle Nodes", getIdleCINodeCount());
		nodeDataJSONObject.put(
			"Total CI Load", getRunningBuildCount() + getQueuedBuildCount());

		nodeDataJSONArray.put(nodeDataJSONObject);

		sb.append(nodeDataJSONArray.toString());

		sb.append(";\nvar buildLoadData = ");

		JSONArray buildLoadDataJSONArray = new JSONArray();

		for (JobDatum jobDatum : _jobData.values()) {
			JSONObject jsonObject = new JSONObject();

			jsonObject.put("Name", jobDatum.getJobName());

			DecimalFormat decimalFormat = new DecimalFormat("###.###%");

			jsonObject.put(
				"Load %", decimalFormat.format(jobDatum.getLoadPercentage()));

			jsonObject.put("Current Builds", jobDatum.getRunningBuildCount());
			jsonObject.put("Queued Builds", jobDatum.getQueuedBuildCount());
			jsonObject.put("Total Builds", jobDatum.getTotalBuildCount());

			List<String> topLevelBuildURLs = jobDatum.getTopLevelBuildURLs();

			jsonObject.put("Top Level Builds", topLevelBuildURLs.size());

			buildLoadDataJSONArray.put(jsonObject);
		}

		sb.append(buildLoadDataJSONArray.toString());

		sb.append(";");

		JenkinsResultsParserUtil.write(
			filePath + "/ci-system-status-data.js", sb.toString());
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

		if (!_jobData.containsKey(jobName)) {
			_jobData.put(jobName, new JobDatum(jobName));
		}

		JobDatum jobDatum = _jobData.get(jobName);

		Matcher buildNumberMatcher = _buildNumberPattern.matcher(jobURL);

		if (buildNumberMatcher.find()) {
			jobDatum.incrementRunningJobCount();
		}
		else {
			jobDatum.incrementQueuedJobCount();
		}

		if (batchJobName == null) {
			jobDatum.addTopLevelBuildURL(jobURL);
		}
	}

	private static final Pattern _buildNumberPattern = Pattern.compile(
		".*\\/([0-9]+)");
	private static final Pattern _jobNamePattern = Pattern.compile(
		"https?:.*job\\/(.*?)\\/");

	private final String _cohortName;
	private int _idleCINodeCount;
	private final Map<String, JobDatum> _jobData = new HashMap<>();
	private int _onlineCINodeCount;

	private class JobDatum {

		public JobDatum(String jobName) {
			_jobName = jobName;
		}

		public void addTopLevelBuildURL(String topLevelBuildURL) {
			_topLevelBuildURLs.add(topLevelBuildURL);
		}

		public String getJobName() {
			return _jobName;
		}

		public double getLoadPercentage() {
			return (double)getTotalBuildCount() /
				(double)(CISystemStatus.this.getRunningBuildCount() +
					CISystemStatus.this.getQueuedBuildCount());
		}

		public int getQueuedBuildCount() {
			return _queuedBuildCount;
		}

		public int getRunningBuildCount() {
			return _runningBuildCount;
		}

		public List<String> getTopLevelBuildURLs() {
			return _topLevelBuildURLs;
		}

		public int getTotalBuildCount() {
			return _queuedBuildCount + _runningBuildCount;
		}

		public void incrementQueuedJobCount() {
			_queuedBuildCount = _queuedBuildCount + 1;
		}

		public void incrementRunningJobCount() {
			_runningBuildCount = _runningBuildCount + 1;
		}

		private final String _jobName;
		private int _queuedBuildCount;
		private int _runningBuildCount;
		private List<String> _topLevelBuildURLs = new ArrayList<>();

	}

}