/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import com.liferay.jenkins.results.parser.aws.AWSFactory;
import com.liferay.jenkins.results.parser.aws.AWSFleetCloud;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public class JenkinsMaster implements JenkinsNode<JenkinsMaster> {

	public static final Integer SLAVE_RAM_DEFAULT = 12;

	public static final Integer SLAVES_PER_HOST_DEFAULT = 2;

	public static synchronized JenkinsMaster getInstance(String masterName) {
		if (!_jenkinsMasters.containsKey(masterName)) {
			_jenkinsMasters.put(masterName, new JenkinsMaster(masterName));
		}

		return _jenkinsMasters.get(masterName);
	}

	public static Integer getSlaveRAMMinimumDefault() {
		try {
			String propertyValue = JenkinsResultsParserUtil.getBuildProperty(
				"slave.ram.minimum.default");

			if (propertyValue == null) {
				return SLAVE_RAM_DEFAULT;
			}

			return Integer.valueOf(propertyValue);
		}
		catch (Exception exception) {
			StringBuilder sb = new StringBuilder();

			sb.append("Unable to get property '");
			sb.append("slave.ram.minimum.default");
			sb.append("', defaulting to '");
			sb.append(SLAVE_RAM_DEFAULT);
			sb.append("'");

			System.out.println(sb.toString());

			exception.printStackTrace();

			return SLAVE_RAM_DEFAULT;
		}
	}

	public static Integer getSlavesPerHostDefault() {
		try {
			String propertyValue = JenkinsResultsParserUtil.getBuildProperty(
				"slaves.per.host.default");

			if (propertyValue == null) {
				return SLAVES_PER_HOST_DEFAULT;
			}

			return Integer.valueOf(propertyValue);
		}
		catch (Exception exception) {
			StringBuilder sb = new StringBuilder();

			sb.append("Unable to get property '");
			sb.append("slaves.per.host.default");
			sb.append("', defaulting to '");
			sb.append(SLAVES_PER_HOST_DEFAULT);
			sb.append("'");

			System.out.println(sb.toString());

			exception.printStackTrace();

			return SLAVES_PER_HOST_DEFAULT;
		}
	}

	public synchronized void addRecentBatch(int batchSize) {
		addRecentBatch(batchSize, null);
	}

	public synchronized void addRecentBatch(
		int batchSize, String labelExpression) {

		if (JenkinsResultsParserUtil.isNullOrEmpty(labelExpression)) {
			labelExpression = null;
		}

		Map<Long, Integer> batchSizes = _labelBatchSizes.get(labelExpression);

		if (batchSizes == null) {
			batchSizes = new TreeMap<>();

			_labelBatchSizes.put(labelExpression, batchSizes);
		}

		batchSizes.put(
			JenkinsResultsParserUtil.getCurrentTimeMillis() + maxRecentBatchAge,
			batchSize);

		_labelBatchSizes.put(labelExpression, batchSizes);
	}

	@Override
	public int compareTo(JenkinsMaster jenkinsMaster) {
		Integer value = null;

		Integer availableSlavesCount = getAvailableSlavesCount();
		Integer otherAvailableSlavesCount =
			jenkinsMaster.getAvailableSlavesCount();

		if ((availableSlavesCount > 0) || (otherAvailableSlavesCount > 0)) {
			value = availableSlavesCount.compareTo(otherAvailableSlavesCount);
		}

		if ((value == null) || (value == 0)) {
			Float averageQueueLength = getAverageQueueLength();
			Float otherAverageQueueLength =
				jenkinsMaster.getAverageQueueLength();

			value = -1 * averageQueueLength.compareTo(otherAverageQueueLength);
		}

		if (value != 0) {
			return -value;
		}

		Random random = new Random();

		while (true) {
			int result = random.nextInt(3) - 1;

			if (result != 0) {
				return result;
			}
		}
	}

	@Override
	public List<String> getAssignedLabels() {
		return _assignedLabels;
	}

	public int getAvailableSlavesCount() {
		return getAvailableSlavesCount(null);
	}

	public int getAvailableSlavesCount(String labelExpression) {
		int idleNodeCount = _getIdleNodeCount(labelExpression);
		int queueCount = _getQueueCount(labelExpression);
		int recentBatchSizesTotal = _getRecentBatchSizesTotal(labelExpression);

		return idleNodeCount - queueCount - recentBatchSizesTotal;
	}

	public float getAverageQueueLength() {
		return getAverageQueueLength(null);
	}

	public float getAverageQueueLength(String labelExpression) {
		int busyNodeCount = _getBusyNodeCount(labelExpression);
		int queueCount = _getQueueCount(labelExpression);
		int recentBatchSizesTotal = _getRecentBatchSizesTotal(labelExpression);
		int usableNodeCount = _getUsableNodeCount(labelExpression);

		return ((float)busyNodeCount + queueCount + recentBatchSizesTotal) /
			usableNodeCount;
	}

	public List<AWSFleetCloud> getAWSFleetClouds() {
		long currentTimestamp = JenkinsResultsParserUtil.getCurrentTimeMillis();

		long timeSinceLastUpdate =
			currentTimestamp - _awsFleetCloudLastUpdateTimestamp;

		if ((_awsFleetClouds != null) &&
			(timeSinceLastUpdate <= _AWS_FLEET_CLOUD_UPDATE_DURATION)) {

			return _awsFleetClouds;
		}

		_awsFleetClouds = AWSFactory.getAWSFleetClouds(this);

		_awsFleetCloudLastUpdateTimestamp = currentTimestamp;

		return _awsFleetClouds;
	}

	public List<JSONObject> getBuildJSONObjects(String jobName) {
		synchronized (_buildJSONObjectsMap) {
			List<JSONObject> buildsJSONObjects = _buildJSONObjectsMap.get(
				jobName);
			Long buildsUpdateTime = _buildsUpdateTimes.get(jobName);

			if ((buildsJSONObjects != null) && (buildsUpdateTime != null)) {
				long currentTime =
					JenkinsResultsParserUtil.getCurrentTimeMillis();

				long buildUpdateDuration = currentTime - buildsUpdateTime;

				if (buildUpdateDuration <= _MAXIMUM_BUILD_UPDATE_DURATION) {
					return buildsJSONObjects;
				}
			}

			buildsJSONObjects = new ArrayList<>();

			int page = 0;

			while (true) {
				JSONArray buildsJSONArray = _getBuildsJSONArray(jobName, page);

				if (buildsJSONArray.length() == 0) {
					break;
				}

				boolean findNextBuild = true;

				for (int i = 0; i < buildsJSONArray.length(); i++) {
					JSONObject buildsJSONObject = buildsJSONArray.getJSONObject(
						i);

					buildsJSONObjects.add(buildsJSONObject);

					long buildAge =
						JenkinsResultsParserUtil.getCurrentTimeMillis() -
							buildsJSONObject.getLong("timestamp");

					if (buildAge >= _MAXIMUM_BUILD_AGE) {
						findNextBuild = false;

						break;
					}
				}

				if (!findNextBuild) {
					break;
				}

				page++;
			}

			_buildJSONObjectsMap.put(jobName, buildsJSONObjects);
			_buildsUpdateTimes.put(
				jobName, JenkinsResultsParserUtil.getCurrentTimeMillis());

			return buildsJSONObjects;
		}
	}

	public List<String> getBuildURLs() {
		return new ArrayList<>(_buildURLs);
	}

	public List<DefaultBuild> getDefaultBuilds() {
		List<String> buildURLs = getBuildURLs();

		List<DefaultBuild> oldDefaultBuilds = new ArrayList<>();

		for (DefaultBuild defaultBuild : _defaultBuilds) {
			if (!buildURLs.contains(defaultBuild.getBuildURL())) {
				oldDefaultBuilds.add(defaultBuild);
			}
			else {
				buildURLs.remove(defaultBuild.getBuildURL());
			}
		}

		_defaultBuilds.removeAll(oldDefaultBuilds);

		for (String buildURL : buildURLs) {
			_defaultBuilds.add(BuildFactory.newDefaultBuild(buildURL));
		}

		return _defaultBuilds;
	}

	public Map<String, String> getGlobalEnvironmentVariables() {
		if (_globalEnvironmentVariables != null) {
			return _globalEnvironmentVariables;
		}

		if (!isAvailable()) {
			return new HashMap<>();
		}

		StringBuilder sb = new StringBuilder();

		sb.append("import jenkins.model.Jenkins;\n");

		sb.append("def globalNodeProperties = ");
		sb.append("Jenkins.instance.getGlobalNodeProperties();\n");

		sb.append("def envVars = globalNodeProperties[0].getEnvVars();\n");

		sb.append("def sb = new StringBuilder();\n");

		sb.append("sb.append(\"{\");\n");

		sb.append("if (!envVars.isEmpty()) {\n");

		sb.append("for (def envVar : envVars.entrySet()) {\n");
		sb.append("sb.append('\"');");
		sb.append("sb.append(envVar.key);");
		sb.append("sb.append('\":\"');");
		sb.append("sb.append(envVar.value.replaceAll('\"', '\\\\\\\\\"'));");
		sb.append("sb.append('\",');");
		sb.append("}\n");

		sb.append("sb.setLength(sb.length() - 1);");
		sb.append("}\n");

		sb.append("sb.append('}');");

		sb.append("println sb;");

		_globalEnvironmentVariables = new HashMap<>();

		try {
			String results = JenkinsResultsParserUtil.executeJenkinsScript(
				getName(), sb.toString());

			Matcher globalEnvironmentVariablesMatcher =
				_globalEnvironmentVariablesPattern.matcher(results);

			if (!globalEnvironmentVariablesMatcher.find()) {
				return _globalEnvironmentVariables;
			}

			JSONObject jsonObject = new JSONObject(
				globalEnvironmentVariablesMatcher.group("json"));

			for (String key : jsonObject.keySet()) {
				_globalEnvironmentVariables.put(key, jsonObject.getString(key));
			}

			return _globalEnvironmentVariables;
		}
		catch (Exception exception) {
			return _globalEnvironmentVariables;
		}
	}

	public int getIdleJenkinsSlavesCount() {
		int idleSlavesCount = 0;

		for (JenkinsSlave jenkinsSlave : _jenkinsSlavesMap.values()) {
			if (jenkinsSlave.isOffline()) {
				continue;
			}

			if (jenkinsSlave.isIdle()) {
				idleSlavesCount++;
			}
		}

		return idleSlavesCount;
	}

	@Override
	public JenkinsCohort getJenkinsCohort() {
		if (_jenkinsCohort != null) {
			return _jenkinsCohort;
		}

		Matcher matcher = _masterNamePattern.matcher(getName());

		if (!matcher.find()) {
			return null;
		}

		String cohortName = matcher.group("cohortName");

		_jenkinsCohort = JenkinsCohort.getInstance(cohortName);

		return _jenkinsCohort;
	}

	@Override
	public JenkinsMaster getJenkinsMaster() {
		return this;
	}

	public JenkinsSlave getJenkinsSlave(String jenkinsSlaveName) {
		if (_jenkinsSlavesMap.isEmpty()) {
			update();
		}

		return _jenkinsSlavesMap.get(jenkinsSlaveName);
	}

	public List<String> getJenkinsSlaveNames() {
		List<JenkinsSlave> jenkinsSlaves = getJenkinsSlaves();

		List<String> jenkinsSlaveNames = new ArrayList<>(jenkinsSlaves.size());

		for (JenkinsSlave jenkinsSlave : jenkinsSlaves) {
			jenkinsSlaveNames.add(jenkinsSlave.getName());
		}

		return jenkinsSlaveNames;
	}

	public List<JenkinsSlave> getJenkinsSlaves() {
		if (_jenkinsSlavesMap.isEmpty()) {
			update();
		}

		return new ArrayList<>(_jenkinsSlavesMap.values());
	}

	@Override
	public String getName() {
		return _masterName;
	}

	public String getNetworkName() {
		Map<String, String> globalEnvironmentVariables =
			getGlobalEnvironmentVariables();

		String networkName = globalEnvironmentVariables.get(
			"MASTER_NETWORK_NAME");

		if (JenkinsResultsParserUtil.isNullOrEmpty(networkName)) {
			return null;
		}

		return networkName;
	}

	public int getOfflineJenkinsSlavesCount() {
		int offlineJenkinsSlavesCount = 0;

		for (JenkinsSlave jenkinsSlave : _jenkinsSlavesMap.values()) {
			if (jenkinsSlave.isOffline()) {
				offlineJenkinsSlavesCount++;
			}
		}

		return offlineJenkinsSlavesCount;
	}

	public List<JenkinsSlave> getOnlineJenkinsSlaves() {
		List<JenkinsSlave> onlineJenkinsSlaves = new ArrayList<>();

		for (JenkinsSlave jenkinsSlave : _jenkinsSlavesMap.values()) {
			if (!jenkinsSlave.isOffline()) {
				onlineJenkinsSlaves.add(jenkinsSlave);
			}
		}

		return onlineJenkinsSlaves;
	}

	public int getOnlineJenkinsSlavesCount() {
		int onlineJenkinsSlavesCount = 0;

		for (JenkinsSlave jenkinsSlave : _jenkinsSlavesMap.values()) {
			if (!jenkinsSlave.isOffline()) {
				onlineJenkinsSlavesCount++;
			}
		}

		return onlineJenkinsSlavesCount;
	}

	public Map<String, JSONObject> getQueuedBuildURLs() {
		return new HashMap<>(_queuedBuildURLs);
	}

	public List<JSONObject> getQueueItemJSONObjects() {
		synchronized (_queueItemJSONObjects) {
			if (_queueUpdateTime != null) {
				long currentTime =
					JenkinsResultsParserUtil.getCurrentTimeMillis();

				long queueUpdateDuration = currentTime - _queueUpdateTime;

				if (queueUpdateDuration <= _MAXIMUM_QUEUE_UPDATE_DURATION) {
					return _queueItemJSONObjects;
				}
			}

			_queueItemJSONObjects.clear();

			try {
				JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
					JenkinsResultsParserUtil.combine(
						String.valueOf(getURL()), "/queue/api/json?tree=",
						"items[actions[parameters[name,value]],id,task[url]]"),
					false, 5000);

				JSONArray queueItemsJSONArray = jsonObject.getJSONArray(
					"items");

				if (queueItemsJSONArray == null) {
					_queueUpdateTime =
						JenkinsResultsParserUtil.getCurrentTimeMillis();

					return _queueItemJSONObjects;
				}

				for (int i = 0; i < queueItemsJSONArray.length(); i++) {
					_queueItemJSONObjects.add(
						queueItemsJSONArray.getJSONObject(i));
				}
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}

			_queueUpdateTime = JenkinsResultsParserUtil.getCurrentTimeMillis();

			return _queueItemJSONObjects;
		}
	}

	public synchronized List<QueueItem> getQueueItems() {
		try {
			JSONObject queueAPIJSONObject =
				JenkinsResultsParserUtil.toJSONObject(
					JenkinsResultsParserUtil.combine(
						getRemoteURL(), "/queue/api/json?tree=",
						"items[actions[parameters[name,value]],id,",
						"inQueueSince,task[name,url],url,why]"),
					false, 5000);

			_queueItems.clear();

			if (!queueAPIJSONObject.has("items")) {
				return _queueItems;
			}

			JSONArray itemsJSONArray = queueAPIJSONObject.getJSONArray("items");

			for (int i = 0; i < itemsJSONArray.length(); i++) {
				_queueItems.add(
					new QueueItem(this, itemsJSONArray.getJSONObject(i)));
			}

			return _queueItems;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public JenkinsSlave getRandomJenkinsSlave() {
		List<JenkinsSlave> jenkinsSlaves = new ArrayList<>(getJenkinsSlaves());

		while (!jenkinsSlaves.isEmpty()) {
			JenkinsSlave jenkinsSlave =
				JenkinsResultsParserUtil.getRandomListItem(jenkinsSlaves);

			if (!jenkinsSlave.isOffline() && jenkinsSlave.isReachable()) {
				return jenkinsSlave;
			}

			jenkinsSlaves.remove(jenkinsSlave);
		}

		return null;
	}

	public String getRemoteURL() {
		return _masterRemoteURL;
	}

	public Integer getSlaveRAM() {
		return _slaveRAM;
	}

	public Integer getSlavesPerHost() {
		return _slavesPerHost;
	}

	public String getURL() {
		return _masterURL;
	}

	public synchronized boolean isAvailable() {
		if ((_availableTimestamp == -1) ||
			((System.currentTimeMillis() - _availableTimestamp) >
				_AVAILABLE_TIMEOUT)) {

			try {
				if (!isBlackListed()) {
					JenkinsResultsParserUtil.toJSONObject(
						getURL() + "/api/json?tree=mode", false, 1, 1, 1000);

					_available = true;
				}
			}
			catch (Exception exception) {
				System.out.println(getName() + " is unreachable.");

				_available = false;
			}
			finally {
				_availableTimestamp = System.currentTimeMillis();
			}
		}

		return _available;
	}

	public boolean isBlackListed() {
		if (_jenkinsMastersBlacklist.contains(getName())) {
			_blacklisted = true;
		}

		return _blacklisted;
	}

	public boolean isBuildInProgress(
		String jobName, Map<String, String> buildParameters) {

		try {
			JSONObject jobJSONObject = JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.combine(
					getURL(), "/job/", jobName, "/api/json?",
					"tree=builds[actions[parameters[name,value]],result,url]"),
				false, 5000);

			JSONArray buildsJSONArray = jobJSONObject.optJSONArray("builds");

			for (int i = 0; i < buildsJSONArray.length(); i++) {
				JSONObject buildJSONObject = buildsJSONArray.optJSONObject(i);

				if ((buildJSONObject == JSONObject.NULL) ||
					!JenkinsResultsParserUtil.isNullOrEmpty(
						buildJSONObject.optString("result"))) {

					continue;
				}

				Map<String, String> parameters = _getParameters(
					buildJSONObject);

				boolean matchingBuildParameters = true;

				for (Map.Entry<String, String> buildParameter :
						buildParameters.entrySet()) {

					String parameterValue = parameters.get(
						buildParameter.getKey());

					if (!Objects.equals(
							buildParameter.getValue(), parameterValue)) {

						matchingBuildParameters = false;

						break;
					}
				}

				if (matchingBuildParameters) {
					return true;
				}
			}
		}
		catch (Exception exception) {
			return false;
		}

		return false;
	}

	public boolean isBuildQueued(
		String jobName, Map<String, String> buildParameters) {

		try {
			JSONObject queueJSONObject = JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.combine(
					getURL(), "/queue/api/json?",
					"tree=items[actions[parameters[name,value]],task[url]]"),
				false, 5000);

			JSONArray itemsJSONArray = queueJSONObject.optJSONArray("items");

			for (int i = 0; i < itemsJSONArray.length(); i++) {
				JSONObject itemJSONObject = itemsJSONArray.optJSONObject(i);

				if (itemJSONObject == JSONObject.NULL) {
					continue;
				}

				JSONObject taskJSONObject = itemJSONObject.optJSONObject(
					"task");

				String taskURL = taskJSONObject.optString("url", "");

				if (!taskURL.contains("/" + jobName + "/")) {
					continue;
				}

				Map<String, String> parameters = _getParameters(itemJSONObject);

				boolean matchingBuildParameters = true;

				for (Map.Entry<String, String> buildParameter :
						buildParameters.entrySet()) {

					String parameterValue = parameters.get(
						buildParameter.getKey());

					if (!Objects.equals(
							buildParameter.getValue(), parameterValue)) {

						matchingBuildParameters = false;

						break;
					}
				}

				if (matchingBuildParameters) {
					return true;
				}
			}
		}
		catch (Exception exception) {
			return false;
		}

		return false;
	}

	@Override
	public boolean isIdle() {
		return _idle;
	}

	@Override
	public boolean isOffline() {
		return _offline;
	}

	@Override
	public String toString() {
		return JenkinsResultsParserUtil.combine(
			"{availableSlavesCount=", String.valueOf(getAvailableSlavesCount()),
			", masterURL=", _masterURL, ", recentBatchSizesTotal=",
			String.valueOf(_getRecentBatchSizesTotal()), "}");
	}

	public synchronized void update() {
		update(true);
	}

	public synchronized void update(boolean minimal) {
		if (!isAvailable()) {
			_assignedLabels.clear();
			_buildURLs.clear();
			_jenkinsSlavesMap.clear();
			_labelBatchSizes.clear();
			_queuedBuildURLs.clear();
			_queueItems.clear();

			return;
		}

		JSONObject computerAPIJSONObject = null;
		JSONObject queueAPIJSONObject = null;

		try {
			computerAPIJSONObject = JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.getLocalURL(
					JenkinsResultsParserUtil.combine(
						_masterURL, "/computer/api/json?tree=computer",
						"[assignedLabels[name],displayName,",
						"executors[currentExecutable[url]],idle,offline,",
						"offlineCauseReason]")),
				false, 5000);

			String queueAPIQuery =
				"tree=items[actions[parameters[name,value]]," +
					"inQueueSince,task[name,url],url,why]";

			queueAPIJSONObject = JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.combine(
					getRemoteURL(), "/queue/api/json?" + queueAPIQuery),
				false, 5000);
		}
		catch (Exception exception) {
			_assignedLabels.clear();
			_buildURLs.clear();
			_jenkinsSlavesMap.clear();
			_labelBatchSizes.clear();
			_queuedBuildURLs.clear();
			_queueItems.clear();

			System.out.println("Unable to read " + _masterURL);

			return;
		}

		List<String> buildURLs = new ArrayList<>();

		JSONArray computerJSONArray = computerAPIJSONObject.getJSONArray(
			"computer");

		for (int i = 0; i < computerJSONArray.length(); i++) {
			JSONObject computerJSONObject = computerJSONArray.getJSONObject(i);

			String jenkinsSlaveName = computerJSONObject.getString(
				"displayName");

			if (jenkinsSlaveName.equals("Built-In Node") ||
				jenkinsSlaveName.equals("master")) {

				JSONArray assignedLabelsJSONArray =
					computerJSONObject.optJSONArray("assignedLabels");

				if (assignedLabelsJSONArray == null) {
					continue;
				}

				for (int j = 0; j < assignedLabelsJSONArray.length(); j++) {
					JSONObject assignedLabelJSONObject =
						assignedLabelsJSONArray.getJSONObject(j);

					String assignedLabelName =
						assignedLabelJSONObject.optString("name");

					if (JenkinsResultsParserUtil.isNullOrEmpty(
							assignedLabelName)) {

						continue;
					}

					_assignedLabels.add(assignedLabelName);
				}

				_idle = computerJSONObject.optBoolean("idle", true);
				_offline = computerJSONObject.optBoolean("offline", true);

				continue;
			}

			JenkinsSlave jenkinsSlave = _jenkinsSlavesMap.get(jenkinsSlaveName);

			if (jenkinsSlave != null) {
				jenkinsSlave.update(computerJSONObject);
			}
			else {
				jenkinsSlave = new JenkinsSlave(this, computerJSONObject);

				_jenkinsSlavesMap.put(jenkinsSlave.getName(), jenkinsSlave);
			}

			String computerClassName = computerJSONObject.getString("_class");

			if (computerClassName.contains("hudson.slaves.SlaveComputer")) {
				JSONArray executorsJSONArray = computerJSONObject.getJSONArray(
					"executors");

				for (int j = 0; j < executorsJSONArray.length(); j++) {
					JSONObject executorJSONObject =
						executorsJSONArray.getJSONObject(j);

					if (executorJSONObject.has("currentExecutable") &&
						(executorJSONObject.get("currentExecutable") !=
							JSONObject.NULL)) {

						JSONObject currentExecutableJSONObject =
							executorJSONObject.getJSONObject(
								"currentExecutable");

						if (currentExecutableJSONObject.has("url")) {
							buildURLs.add(
								currentExecutableJSONObject.getString("url"));
						}
					}
				}
			}
		}

		_buildURLs.clear();

		_buildURLs.addAll(buildURLs);

		if (!queueAPIJSONObject.has("items")) {
			return;
		}

		Map<String, JSONObject> queuedBuildURLs = new HashMap<>();

		JSONArray itemsJSONArray = queueAPIJSONObject.getJSONArray("items");

		for (int i = 0; i < itemsJSONArray.length(); i++) {
			JSONObject itemJSONObject = itemsJSONArray.getJSONObject(i);

			_queueItems.add(new QueueItem(this, itemJSONObject));

			if (!itemJSONObject.has("task")) {
				continue;
			}

			JSONObject taskJSONObject = itemJSONObject.getJSONObject("task");

			String taskName = taskJSONObject.getString("name");

			if (taskName.equals("verification-node")) {
				continue;
			}

			if (itemJSONObject.has("why")) {
				String why = itemJSONObject.optString("why");

				if (taskName.startsWith("label=")) {
					String offlineSlaveWhy = JenkinsResultsParserUtil.combine(
						"‘", taskName.substring("label=".length()),
						"’ is offline");

					if (why.contains(offlineSlaveWhy)) {
						continue;
					}
				}

				if (why.startsWith("There are no nodes") ||
					why.contains("already in progress")) {

					continue;
				}

				if (itemJSONObject.has("url")) {
					queuedBuildURLs.put(
						getURL() + "/" + itemJSONObject.getString("url"),
						itemJSONObject);
				}
			}
		}

		_queuedBuildURLs.clear();

		_queuedBuildURLs.putAll(queuedBuildURLs);
	}

	public static class QueueItem {

		public long getId() {
			return _jsonObject.getLong("id");
		}

		public long getInQueueSince() {
			return _jsonObject.getLong("inQueueSince");
		}

		public JSONObject getJSONObject() {
			return _jsonObject;
		}

		public String getLabelExpression() {
			Map<String, String> parameters = getParameters();

			String label = parameters.get("SLAVE_LABEL");

			if (JenkinsResultsParserUtil.isNullOrEmpty(label)) {
				label = parameters.get("NODE_NAME");
			}

			if (JenkinsResultsParserUtil.isNullOrEmpty(label)) {
				String taskName = getTaskName();

				if (!JenkinsResultsParserUtil.isNullOrEmpty(taskName) &&
					taskName.startsWith("label=")) {

					label = taskName.substring("label=".length());
				}
			}

			return label;
		}

		public Map<String, String> getParameters() {
			return _getParameters(_jsonObject);
		}

		public String getTaskName() {
			JSONObject taskJSONObject = _jsonObject.optJSONObject("task");

			if (taskJSONObject == null) {
				return null;
			}

			return taskJSONObject.optString("name");
		}

		public String getTaskURL() {
			JSONObject taskJSONObject = _jsonObject.optJSONObject("task");

			if (taskJSONObject == null) {
				return null;
			}

			return taskJSONObject.optString("url");
		}

		public String getURL() {
			if (!_jsonObject.has("url")) {
				return null;
			}

			return JenkinsResultsParserUtil.combine(
				_jenkinsMaster.getRemoteURL(), "/",
				_jsonObject.getString("url"));
		}

		public String getWhy() {
			return _jsonObject.optString("why");
		}

		public boolean isValidQueueItem() {
			String taskName = getTaskName();

			if (taskName.equals("verification-node")) {
				return false;
			}

			String why = getWhy();

			if (!JenkinsResultsParserUtil.isNullOrEmpty(why)) {
				if (taskName.startsWith("label=")) {
					String offlineSlaveWhy = JenkinsResultsParserUtil.combine(
						"‘", taskName.substring("label=".length()),
						"’ is offline");

					if (why.contains(offlineSlaveWhy)) {
						return false;
					}
				}

				if (why.startsWith("There are no nodes") ||
					why.contains("already in progress")) {

					return false;
				}
			}

			return true;
		}

		protected QueueItem(
			JenkinsMaster jenkinsMaster, JSONObject jsonObject) {

			_jenkinsMaster = jenkinsMaster;
			_jsonObject = jsonObject;
		}

		private final JenkinsMaster _jenkinsMaster;
		private final JSONObject _jsonObject;

	}

	protected static long maxRecentBatchAge = 120 * 1000;

	private JenkinsMaster(String masterName) {
		if (masterName.contains(".")) {
			_masterName = masterName.substring(0, masterName.indexOf("."));
		}
		else {
			_masterName = masterName;
		}

		try {
			Properties properties =
				JenkinsResultsParserUtil.getBuildProperties();

			_masterURL = properties.getProperty(
				JenkinsResultsParserUtil.combine(
					"jenkins.local.url[", _masterName, "]"));

			_masterRemoteURL = properties.getProperty(
				JenkinsResultsParserUtil.combine(
					"jenkins.remote.url[", _masterName, "]"));

			if (JenkinsResultsParserUtil.isNullOrEmpty(_masterRemoteURL) ||
				JenkinsResultsParserUtil.isNullOrEmpty(_masterURL)) {

				throw new IllegalArgumentException(masterName + " is unknown");
			}

			Integer slaveRAM = getSlaveRAMMinimumDefault();

			String slaveRAMString = JenkinsResultsParserUtil.getProperty(
				properties,
				JenkinsResultsParserUtil.combine(
					"master.property(", _masterName, "/slave.ram)"));

			if ((slaveRAMString != null) && slaveRAMString.matches("\\d+")) {
				slaveRAM = Integer.valueOf(slaveRAMString);
			}

			_slaveRAM = slaveRAM;

			Integer slavesPerHost = getSlavesPerHostDefault();

			String slavesPerHostString = JenkinsResultsParserUtil.getProperty(
				properties,
				JenkinsResultsParserUtil.combine(
					"master.property(", _masterName, "/slaves.per.host)"));

			if ((slavesPerHostString != null) &&
				slavesPerHostString.matches("\\d+")) {

				slavesPerHost = Integer.valueOf(slavesPerHostString);
			}

			_slavesPerHost = slavesPerHost;
		}
		catch (Exception exception) {
			throw new RuntimeException(
				"Unable to determine URL for master " + _masterName, exception);
		}
	}

	private JSONArray _getBuildsJSONArray(
		final String jobName, final int page) {

		Retryable<JSONArray> retryable = new Retryable<JSONArray>(
			true, 2, 10, true) {

			@Override
			public JSONArray execute() {
				String url = JenkinsResultsParserUtil.getLocalURL(
					JenkinsResultsParserUtil.combine(
						String.valueOf(getURL()), "/job/", jobName,
						"/api/json?tree=allBuilds[actions[parameters",
						"[name,value]],queueId,timestamp,url]{",
						String.valueOf(page * 100), ",",
						String.valueOf((page + 1) * 100), "}"));

				try {
					JSONObject jsonObject =
						JenkinsResultsParserUtil.toJSONObject(url, false, 5000);

					return jsonObject.getJSONArray("allBuilds");
				}
				catch (IOException ioException) {
					throw new RuntimeException(ioException);
				}
			}

		};

		return retryable.executeWithRetries();
	}

	private int _getBusyNodeCount(String labelExpression) {
		int busyNodeCount = 0;

		List<JenkinsNode> jenkinsNodes = new ArrayList<>();

		jenkinsNodes.addAll(getJenkinsSlaves());

		jenkinsNodes.add(this);

		for (JenkinsNode jenkinsNode : jenkinsNodes) {
			if (JenkinsResultsParserUtil.matchesLabels(
					labelExpression, jenkinsNode.getAssignedLabels()) &&
				!jenkinsNode.isIdle() && !jenkinsNode.isOffline()) {

				busyNodeCount++;
			}
		}

		return busyNodeCount;
	}

	private int _getIdleNodeCount(String labelExpression) {
		int idleNodeCount = 0;

		List<JenkinsNode> jenkinsNodes = new ArrayList<>();

		jenkinsNodes.addAll(getJenkinsSlaves());

		jenkinsNodes.add(this);

		for (JenkinsNode jenkinsNode : jenkinsNodes) {
			if (jenkinsNode instanceof JenkinsSlave) {
				JenkinsSlave jenkinsSlave = (JenkinsSlave)jenkinsNode;

				if (jenkinsSlave.isEC2FleetNodeComputer()) {
					continue;
				}
			}

			if (JenkinsResultsParserUtil.matchesLabels(
					labelExpression, jenkinsNode.getAssignedLabels()) &&
				jenkinsNode.isIdle() && !jenkinsNode.isOffline()) {

				idleNodeCount++;
			}
		}

		List<AWSFleetCloud> awsFleetClouds = getAWSFleetClouds();

		if (awsFleetClouds.isEmpty()) {
			return idleNodeCount;
		}

		for (AWSFleetCloud awsFleetCloud : awsFleetClouds) {
			if (!JenkinsResultsParserUtil.matchesLabels(
					labelExpression, awsFleetCloud.getLabels())) {

				continue;
			}

			int idleAWSFleetCloudSlaveCount = awsFleetCloud.getMaxSize();

			for (JenkinsSlave jenkinsSlave : awsFleetCloud.getJenkinsSlaves()) {
				if (!jenkinsSlave.isIdle() || jenkinsSlave.isOffline()) {
					idleAWSFleetCloudSlaveCount--;
				}
			}

			idleNodeCount += idleAWSFleetCloudSlaveCount;
		}

		return idleNodeCount;
	}

	private List<String> _getLabels(String labelExpression) {
		Set<String> labels = new HashSet<>();

		labels.addAll(getAssignedLabels());

		for (JenkinsSlave jenkinsSlave : getJenkinsSlaves()) {
			if (jenkinsSlave.isEC2FleetNodeComputer()) {
				continue;
			}

			labels.addAll(jenkinsSlave.getAssignedLabels());
		}

		for (AWSFleetCloud awsFleetCloud : getAWSFleetClouds()) {
			labels.addAll(awsFleetCloud.getLabels());
		}

		List<String> matchingLabels = new ArrayList<>();

		for (String label : labels) {
			if (JenkinsResultsParserUtil.matchesLabels(
					labelExpression, Arrays.asList(label))) {

				matchingLabels.add(label);
			}
		}

		return matchingLabels;
	}

	private static Map<String, String> _getParameters(JSONObject jsonObject) {
		Map<String, String> parameters = new HashMap<>();

		if (jsonObject == null) {
			return parameters;
		}

		JSONArray actionsJSONArray = jsonObject.optJSONArray("actions");

		if (actionsJSONArray == null) {
			return parameters;
		}

		for (int i = 0; i < actionsJSONArray.length(); i++) {
			JSONObject actionJSONObject = actionsJSONArray.optJSONObject(i);

			if ((actionJSONObject == JSONObject.NULL) ||
				!Objects.equals(
					actionJSONObject.optString("_class"),
					"hudson.model.ParametersAction")) {

				continue;
			}

			JSONArray parametersJSONArray = actionJSONObject.optJSONArray(
				"parameters");

			if (parametersJSONArray == JSONObject.NULL) {
				continue;
			}

			for (int k = 0; k < parametersJSONArray.length(); k++) {
				JSONObject parameterJSONObject =
					parametersJSONArray.optJSONObject(k);

				if (parameterJSONObject == JSONObject.NULL) {
					continue;
				}

				parameters.put(
					parameterJSONObject.getString("name"),
					parameterJSONObject.getString("value"));
			}

			break;
		}

		return parameters;
	}

	private int _getQueueCount(String labelExpression) {
		int queueCount = 0;

		List<String> labels = _getLabels(labelExpression);

		for (QueueItem queueItem : getQueueItems()) {
			if (JenkinsResultsParserUtil.matchesLabels(
					queueItem.getLabelExpression(), labels)) {

				queueCount++;
			}
		}

		return queueCount;
	}

	private synchronized int _getRecentBatchSizesTotal() {
		return _getRecentBatchSizesTotal(null);
	}

	private synchronized int _getRecentBatchSizesTotal(String labelExpression) {
		int recentBatchSizesTotal = 0;

		if (JenkinsResultsParserUtil.isNullOrEmpty(labelExpression)) {
			labelExpression = null;
		}

		long currentTimestamp = JenkinsResultsParserUtil.getCurrentTimeMillis();

		for (Map.Entry<String, Map<Long, Integer>> labelBatchSizesEntry :
				_labelBatchSizes.entrySet()) {

			String currentLabel = labelBatchSizesEntry.getKey();

			if ((labelExpression != null) &&
				!labelExpression.equals(currentLabel)) {

				continue;
			}

			Map<Long, Integer> batchSizes = labelBatchSizesEntry.getValue();

			if (batchSizes == null) {
				batchSizes = new HashMap<>();
			}

			List<Long> expiredTimestamps = new ArrayList<>(batchSizes.size());

			for (Map.Entry<Long, Integer> entry : batchSizes.entrySet()) {
				Long expirationTimestamp = entry.getKey();

				if (expirationTimestamp < currentTimestamp) {
					expiredTimestamps.add(expirationTimestamp);

					continue;
				}

				recentBatchSizesTotal += entry.getValue();
			}

			for (Long expiredTimestamp : expiredTimestamps) {
				batchSizes.remove(expiredTimestamp);
			}

			_labelBatchSizes.put(currentLabel, batchSizes);
		}

		return recentBatchSizesTotal;
	}

	private int _getUsableNodeCount(String labelExpression) {
		int usableNodeCount = 0;

		if (JenkinsResultsParserUtil.matchesLabels(
				labelExpression, getAssignedLabels())) {

			usableNodeCount++;
		}

		for (JenkinsSlave jenkinsSlave : getJenkinsSlaves()) {
			if (!jenkinsSlave.isEC2FleetNodeComputer() &&
				!jenkinsSlave.isOffline() &&
				JenkinsResultsParserUtil.matchesLabels(
					labelExpression, jenkinsSlave.getAssignedLabels())) {

				usableNodeCount++;
			}
		}

		for (AWSFleetCloud awsFleetCloud : getAWSFleetClouds()) {
			if (JenkinsResultsParserUtil.matchesLabels(
					labelExpression, awsFleetCloud.getLabels())) {

				usableNodeCount += awsFleetCloud.getMaxSize();
			}
		}

		return usableNodeCount;
	}

	private static final long _AVAILABLE_TIMEOUT = 1000 * 60 * 5;

	private static final long _AWS_FLEET_CLOUD_UPDATE_DURATION =
		15 * 1000 * 1000;

	private static final long _MAXIMUM_BUILD_AGE = 24 * 60 * 60 * 1000;

	private static final long _MAXIMUM_BUILD_UPDATE_DURATION = 30 * 1000;

	private static final long _MAXIMUM_QUEUE_UPDATE_DURATION = 15 * 1000;

	private static final Pattern _globalEnvironmentVariablesPattern =
		Pattern.compile("[^\\{]+(?<json>\\{.*\\})\\s+");
	private static final Map<String, JenkinsMaster> _jenkinsMasters =
		Collections.synchronizedMap(new HashMap<String, JenkinsMaster>());
	private static final List<String> _jenkinsMastersBlacklist =
		new ArrayList<>();
	private static final Pattern _masterNamePattern = Pattern.compile(
		"(?<cohortName>test-\\d+)-\\d+");

	static {
		try {
			String jenkinsMastersBlacklist =
				JenkinsResultsParserUtil.getBuildProperty(
					"jenkins.load.balancer.blacklist");

			if (!JenkinsResultsParserUtil.isNullOrEmpty(
					jenkinsMastersBlacklist)) {

				Collections.addAll(
					_jenkinsMastersBlacklist,
					jenkinsMastersBlacklist.split(","));
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private final List<String> _assignedLabels = new ArrayList<>();
	private boolean _available;
	private long _availableTimestamp = -1;
	private long _awsFleetCloudLastUpdateTimestamp;
	private List<AWSFleetCloud> _awsFleetClouds;
	private boolean _blacklisted;
	private final Map<String, List<JSONObject>> _buildJSONObjectsMap =
		new HashMap<>();
	private final Map<String, Long> _buildsUpdateTimes = new HashMap<>();
	private final List<String> _buildURLs = new CopyOnWriteArrayList<>();
	private final List<DefaultBuild> _defaultBuilds = new ArrayList<>();
	private Map<String, String> _globalEnvironmentVariables;
	private boolean _idle;
	private JenkinsCohort _jenkinsCohort;
	private final Map<String, JenkinsSlave> _jenkinsSlavesMap =
		Collections.synchronizedMap(new HashMap<String, JenkinsSlave>());
	private final Map<String, Map<Long, Integer>> _labelBatchSizes =
		new HashMap<>();
	private final String _masterName;
	private final String _masterRemoteURL;
	private final String _masterURL;
	private boolean _offline;
	private final Map<String, JSONObject> _queuedBuildURLs =
		Collections.synchronizedMap(new HashMap<String, JSONObject>());
	private final List<JSONObject> _queueItemJSONObjects = new ArrayList<>();
	private final List<QueueItem> _queueItems = new ArrayList<>();
	private Long _queueUpdateTime;
	private final Integer _slaveRAM;
	private final Integer _slavesPerHost;

}