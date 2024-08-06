/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class JenkinsCohort {

	public static synchronized JenkinsCohort getInstance(String cohortName) {
		if (!_jenkinsCohorts.containsKey(cohortName)) {
			_jenkinsCohorts.put(cohortName, new JenkinsCohort(cohortName));
		}

		return _jenkinsCohorts.get(cohortName);
	}

	public int getIdleJenkinsSlaveCount() {
		int idleJenkinsSlaveCount = 0;

		if (_jenkinsCohortJobsMap.isEmpty()) {
			update();
		}

		for (JenkinsMaster jenkinsMaster : _jenkinsMastersMap.values()) {
			idleJenkinsSlaveCount += jenkinsMaster.getIdleJenkinsSlavesCount();
		}

		return idleJenkinsSlaveCount;
	}

	public List<JenkinsMaster> getJenkinsMasters() {
		synchronized (_jenkinsMastersMap) {
			if (!_jenkinsMastersMap.isEmpty()) {
				return new ArrayList<>(_jenkinsMastersMap.values());
			}

			try {
				List<JenkinsMaster> jenkinsMasters =
					JenkinsResultsParserUtil.getJenkinsMasters(
						JenkinsResultsParserUtil.getBuildProperties(),
						JenkinsMaster.getSlaveRAMMinimumDefault(),
						JenkinsMaster.getSlavesPerHostDefault(), getName());

				for (JenkinsMaster jenkinsMaster : jenkinsMasters) {
					_jenkinsMastersMap.put(
						jenkinsMaster.getName(), jenkinsMaster);
				}
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}

			return new ArrayList<>(_jenkinsMastersMap.values());
		}
	}

	public JenkinsMaster getMostAvailableJenkinsMaster(
		int invokedBatchSize, int minimumRAM, int maximumSlavesPerHost) {

		String mostAvailableMasterURL =
			JenkinsResultsParserUtil.getMostAvailableMasterURL(
				JenkinsResultsParserUtil.combine(
					"http://", getName(), ".liferay.com"),
				JenkinsResultsParserUtil.join(",", _jenkinsMastersBlacklist),
				invokedBatchSize, minimumRAM, maximumSlavesPerHost);

		return JenkinsMaster.getInstance(
			mostAvailableMasterURL.replaceAll("http://(.+)", "$1"));
	}

	public String getName() {
		return _name;
	}

	public Set<String> getNetworkNames() {
		Set<String> networkNames = new HashSet<>();

		for (JenkinsMaster jenkinsMaster : getJenkinsMasters()) {
			networkNames.add(jenkinsMaster.getNetworkName());
		}

		return networkNames;
	}

	public int getOfflineJenkinsSlaveCount() {
		int offlineJenkinsSlaveCount = 0;

		if (_jenkinsCohortJobsMap.isEmpty()) {
			update();
		}

		for (JenkinsMaster jenkinsMaster : _jenkinsMastersMap.values()) {
			offlineJenkinsSlaveCount +=
				jenkinsMaster.getOfflineJenkinsSlavesCount();
		}

		return offlineJenkinsSlaveCount;
	}

	public int getOnlineJenkinsSlaveCount() {
		int onlineJenkinsSlaveCount = 0;

		if (_jenkinsCohortJobsMap.isEmpty()) {
			update();
		}

		for (JenkinsMaster jenkinsMaster : _jenkinsMastersMap.values()) {
			onlineJenkinsSlaveCount +=
				jenkinsMaster.getOnlineJenkinsSlavesCount();
		}

		return onlineJenkinsSlaveCount;
	}

	public int getQueuedBuildCount() {
		int queuedBuildCount = 0;

		if (_jenkinsCohortJobsMap.isEmpty()) {
			update();
		}

		for (JenkinsCohortJob jenkinsCohortJob :
				_jenkinsCohortJobsMap.values()) {

			queuedBuildCount =
				queuedBuildCount + jenkinsCohortJob.getQueuedBuildCount();
		}

		return queuedBuildCount;
	}

	public int getRunningBuildCount() {
		int runningBuildCount = 0;

		if (_jenkinsCohortJobsMap.isEmpty()) {
			update();
		}

		for (JenkinsCohortJob jenkinsCohortJob :
				_jenkinsCohortJobsMap.values()) {

			runningBuildCount =
				runningBuildCount + jenkinsCohortJob.getRunningBuildCount();
		}

		return runningBuildCount;
	}

	public void update() {
		synchronized (_jenkinsCohortJobsMap) {
			if (!_jenkinsCohortJobsMap.isEmpty()) {
				return;
			}

			List<Callable<Void>> callables = new ArrayList<>();
			final List<String> buildURLs = Collections.synchronizedList(
				new ArrayList<String>());
			final Map<String, JSONObject> queuedBuildURLs =
				Collections.synchronizedMap(new HashMap<String, JSONObject>());

			for (final JenkinsMaster jenkinsMaster : getJenkinsMasters()) {
				Callable<Void> callable = new Callable<Void>() {

					@Override
					public Void call() {
						jenkinsMaster.update(false);

						buildURLs.addAll(jenkinsMaster.getBuildURLs());
						queuedBuildURLs.putAll(
							jenkinsMaster.getQueuedBuildURLs());

						return null;
					}

				};

				callables.add(callable);
			}

			if (!_jenkinsMastersMap.isEmpty()) {
				ThreadPoolExecutor threadPoolExecutor =
					JenkinsResultsParserUtil.getNewThreadPoolExecutor(
						_jenkinsMastersMap.size(), true);

				ParallelExecutor<Void> parallelExecutor =
					new ParallelExecutor<>(
						callables, threadPoolExecutor, "update");

				try {
					parallelExecutor.execute();
				}
				catch (TimeoutException timeoutException) {
					throw new RuntimeException(timeoutException);
				}
			}

			for (String buildURL : buildURLs) {
				_loadBuildURL(buildURL);
			}

			for (Map.Entry<String, JSONObject> entry :
					queuedBuildURLs.entrySet()) {

				_loadQueuedBuildURL(entry);
			}
		}
	}

	public void writeDataJavaScriptFile(String filePath) throws IOException {
		if (_jenkinsCohortJobsMap.isEmpty()) {
			update();
		}

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

	public void writeNodeDataJSONFile(String filePath) throws IOException {
		File file = new File(filePath);

		JSONObject jsonObject = null;

		if (file.exists()) {
			String fileContent = JenkinsResultsParserUtil.read(file);

			jsonObject = new JSONObject(fileContent);
		}
		else {
			jsonObject = new JSONObject();

			jsonObject.put(
				"idle_nodes", new JSONArray()
			).put(
				"occupied_nodes", new JSONArray()
			).put(
				"offline_nodes", new JSONArray()
			).put(
				"online_nodes", new JSONArray()
			).put(
				"queued_builds", new JSONArray()
			).put(
				"timestamps", new JSONArray()
			);
		}

		JSONArray idleNodesJSONArray = jsonObject.getJSONArray("idle_nodes");

		idleNodesJSONArray.put(getIdleJenkinsSlaveCount());

		JSONArray occupiedNodesJSONArray = jsonObject.getJSONArray(
			"occupied_nodes");

		occupiedNodesJSONArray.put(getRunningBuildCount());

		JSONArray offlineNodesJSONArray = jsonObject.getJSONArray(
			"offline_nodes");

		offlineNodesJSONArray.put(getOfflineJenkinsSlaveCount());

		JSONArray onlineNodesJSONArray = jsonObject.getJSONArray(
			"online_nodes");

		onlineNodesJSONArray.put(getOnlineJenkinsSlaveCount());

		JSONArray queuedBuildsJSONArray = jsonObject.getJSONArray(
			"queued_builds");

		queuedBuildsJSONArray.put(getQueuedBuildCount());

		JSONArray timestampsJSONArray = jsonObject.getJSONArray("timestamps");

		timestampsJSONArray.put(System.currentTimeMillis());

		JenkinsResultsParserUtil.write(filePath, jsonObject.toString());
	}

	protected JenkinsCohort(String name) {
		_name = name;
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

		String downstreamJobName = null;

		if (jobName.contains("-batch")) {
			downstreamJobName = jobName;

			jobName = jobName.replace("-batch", "");
		}

		if (jobName.contains("-downstream")) {
			downstreamJobName = jobName;

			jobName = jobName.replace("-downstream", "");
		}

		if (!_jenkinsCohortJobsMap.containsKey(jobName)) {
			_jenkinsCohortJobsMap.put(jobName, new JenkinsCohortJob(jobName));
		}

		JenkinsCohortJob jenkinsCohortJob = _jenkinsCohortJobsMap.get(jobName);

		if (downstreamJobName == null) {
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

				String downstreamJobName = null;

				if (jobName.contains("-batch")) {
					downstreamJobName = jobName;

					jobName = jobName.replace("-batch", "");
				}

				if (jobName.contains("-downstream")) {
					downstreamJobName = jobName;

					jobName = jobName.replace("-downstream", "");
				}

				if (!_jenkinsCohortJobsMap.containsKey(jobName)) {
					_jenkinsCohortJobsMap.put(
						jobName, new JenkinsCohortJob(jobName));
				}

				JenkinsCohortJob jenkinsCohortJob = _jenkinsCohortJobsMap.get(
					jobName);

				if (downstreamJobName == null) {
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
	private static final Map<String, JenkinsCohort> _jenkinsCohorts =
		new HashMap<>();
	private static final List<String> _jenkinsMastersBlacklist =
		new ArrayList<>();
	private static final Pattern _jobNamePattern = Pattern.compile(
		"https?:.*job\\/(.*?)\\/");

	static {
		try {
			String jenkinsMastersBlacklist =
				JenkinsResultsParserUtil.getBuildProperty(
					"jenkins.load.balancer.blacklist");

			Collections.addAll(
				_jenkinsMastersBlacklist, jenkinsMastersBlacklist.split(","));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

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