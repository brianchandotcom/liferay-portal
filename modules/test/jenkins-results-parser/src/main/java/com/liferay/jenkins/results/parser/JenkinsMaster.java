/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;
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
		_batchSizes.put(
			JenkinsResultsParserUtil.getCurrentTimeMillis() + maxRecentBatchAge,
			batchSize);

		getAvailableSlavesCount();
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

	public int getAvailableSlavesCount() {
		return getIdleJenkinsSlavesCount() - _queueCount -
			_getRecentBatchSizesTotal();
	}

	public float getAverageQueueLength() {
		return ((float)_queueCount + _getRecentBatchSizesTotal()) /
			getOnlineJenkinsSlavesCount();
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

	public Integer getSlaveRAM() {
		return _slaveRAM;
	}

	public Integer getSlavesPerHost() {
		return _slavesPerHost;
	}

	public String getURL() {
		return _masterURL;
	}

	public boolean isAvailable() {
		if ((_availableTimestamp == -1) ||
			((System.currentTimeMillis() - _availableTimestamp) >
				_AVAILABLE_TIMEOUT)) {

			try {
				if (!isBlackListed()) {
					JenkinsResultsParserUtil.toString(
						"http://" + getName(), false, 0, 0, 0);

					_available = true;
				}
			}
			catch (IOException ioException) {
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
					"tree=builds[actions[parameters[name,value]],result,url]"));

			JSONArray buildsJSONArray = jobJSONObject.optJSONArray("builds");

			for (int i = 0; i < buildsJSONArray.length(); i++) {
				JSONObject buildJSONObject = buildsJSONArray.optJSONObject(i);

				if ((buildJSONObject == JSONObject.NULL) ||
					!JenkinsResultsParserUtil.isNullOrEmpty(
						buildJSONObject.optString("result"))) {

					continue;
				}

				JSONArray actionsJSONArray = buildJSONObject.optJSONArray(
					"actions");

				if (actionsJSONArray == JSONObject.NULL) {
					continue;
				}

				for (int j = 0; j < actionsJSONArray.length(); j++) {
					JSONObject actionJSONObject =
						actionsJSONArray.optJSONObject(j);

					if ((actionJSONObject == JSONObject.NULL) ||
						!Objects.equals(
							actionJSONObject.optString("_class"),
							"hudson.model.ParametersAction")) {

						continue;
					}

					JSONArray parametersJSONArray =
						actionJSONObject.optJSONArray("parameters");

					if (parametersJSONArray == JSONObject.NULL) {
						continue;
					}

					Map<String, String> parameters = new HashMap<>();

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
					"tree=items[actions[parameters[name,value]],task[url]]"));

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

				JSONArray actionsJSONArray = itemJSONObject.optJSONArray(
					"actions");

				if (actionsJSONArray == JSONObject.NULL) {
					continue;
				}

				for (int j = 0; j < actionsJSONArray.length(); j++) {
					JSONObject actionJSONObject =
						actionsJSONArray.optJSONObject(j);

					if ((actionJSONObject == JSONObject.NULL) ||
						!Objects.equals(
							actionJSONObject.optString("_class"),
							"hudson.model.ParametersAction")) {

						continue;
					}

					JSONArray parametersJSONArray =
						actionJSONObject.optJSONArray("parameters");

					if (parametersJSONArray == JSONObject.NULL) {
						continue;
					}

					Map<String, String> parameters = new HashMap<>();

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
		}
		catch (Exception exception) {
			return false;
		}

		return false;
	}

	@Override
	public String toString() {
		return JenkinsResultsParserUtil.combine(
			"{availableSlavesCount=", String.valueOf(getAvailableSlavesCount()),
			", masterURL=", _masterURL, ", recentBatchSizesTotal=",
			String.valueOf(_getRecentBatchSizesTotal()),
			", reportedAvailableSlavesCount=",
			String.valueOf(_reportedAvailableSlavesCount), "}");
	}

	public synchronized void update() {
		update(true);
	}

	public synchronized void update(boolean minimal) {
		if (!isAvailable()) {
			_batchSizes.clear();
			_buildURLs.clear();
			_jenkinsSlavesMap.clear();
			_queueCount = 0;
			_queuedBuildURLs.clear();
			_reportedAvailableSlavesCount = 0;

			return;
		}

		JSONObject computerAPIJSONObject = null;
		JSONObject queueAPIJSONObject = null;

		try {
			computerAPIJSONObject = JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.getLocalURL(
					JenkinsResultsParserUtil.combine(
						_masterURL,
						"/computer/api/json?tree=computer[displayName,",
						"executors[currentExecutable[url]],idle,offline]")),
				false, 5000);

			String queueAPIQuery = "tree=items[task[name,url],url,why]";

			if (!minimal) {
				queueAPIQuery =
					"tree=items[actions[parameters[name,value]]," +
						"inQueueSince,task[name,url],url,why]";
			}

			queueAPIJSONObject = JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.getLocalURL(
					JenkinsResultsParserUtil.combine(
						_masterURL, "/queue/api/json?" + queueAPIQuery)),
				false, 5000);
		}
		catch (Exception exception) {
			_batchSizes.clear();
			_buildURLs.clear();
			_jenkinsSlavesMap.clear();
			_queueCount = 0;
			_queuedBuildURLs.clear();
			_reportedAvailableSlavesCount = 0;

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

			if (jenkinsSlaveName.equals("master")) {
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

		_queueCount = 0;

		if (!queueAPIJSONObject.has("items")) {
			return;
		}

		Map<String, JSONObject> queuedBuildURLs = new HashMap<>();

		JSONArray itemsJSONArray = queueAPIJSONObject.getJSONArray("items");

		for (int i = 0; i < itemsJSONArray.length(); i++) {
			JSONObject itemJSONObject = itemsJSONArray.getJSONObject(i);

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

			_queueCount++;
		}

		_queuedBuildURLs.clear();

		_queuedBuildURLs.putAll(queuedBuildURLs);
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

	private synchronized int _getRecentBatchSizesTotal() {
		long currentTimestamp = JenkinsResultsParserUtil.getCurrentTimeMillis();
		int recentBatchSizesTotal = 0;

		List<Long> expiredTimestamps = new ArrayList<>(_batchSizes.size());

		for (Map.Entry<Long, Integer> entry : _batchSizes.entrySet()) {
			Long expirationTimestamp = entry.getKey();

			if (expirationTimestamp < currentTimestamp) {
				expiredTimestamps.add(expirationTimestamp);

				continue;
			}

			recentBatchSizesTotal += entry.getValue();
		}

		for (Long expiredTimestamp : expiredTimestamps) {
			_batchSizes.remove(expiredTimestamp);
		}

		return recentBatchSizesTotal;
	}

	private static final long _AVAILABLE_TIMEOUT = 1000 * 60 * 5;

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

			Collections.addAll(
				_jenkinsMastersBlacklist, jenkinsMastersBlacklist.split(","));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private boolean _available;
	private long _availableTimestamp = -1;
	private final Map<Long, Integer> _batchSizes = new TreeMap<>();
	private boolean _blacklisted;
	private final List<String> _buildURLs = new CopyOnWriteArrayList<>();
	private final List<DefaultBuild> _defaultBuilds = new ArrayList<>();
	private JenkinsCohort _jenkinsCohort;
	private final Map<String, JenkinsSlave> _jenkinsSlavesMap =
		Collections.synchronizedMap(new HashMap<String, JenkinsSlave>());
	private final String _masterName;
	private final String _masterURL;
	private int _queueCount;
	private final Map<String, JSONObject> _queuedBuildURLs =
		Collections.synchronizedMap(new HashMap<String, JSONObject>());
	private int _reportedAvailableSlavesCount;
	private final Integer _slaveRAM;
	private final Integer _slavesPerHost;

}