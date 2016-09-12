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

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Kevin Yen
 */
public abstract class BaseBuild implements Build {

	public JSONObject getBuildJSONObject() throws Exception {
		return getBuildJSONObject(null);
	}

	public JSONObject getBuildJSONObject(String tree) throws Exception {
		if (getBuildURL() == null) {
			return null;
		}

		if ((tree == null) || tree.isEmpty()) {
			tree = "actions[parameters[*]],number,result,runs[number,url]";
		}

		String url = JenkinsResultsParserUtil.getLocalURL(
			getBuildURL() + "/api/json?pretty&tree=" + tree);

		return JenkinsResultsParserUtil.toJSONObject(url, false);
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

		return jobURL + "/" + _buildNumber + "/";
	}

	@Override
	public String getConsoleText()
	{
		try {
			return JenkinsResultsParserUtil.toString(
				JenkinsResultsParserUtil.getLocalURL(
					getBuildURL() + "/consoleText"),
				false);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void addDownstreamBuilds(String... urls) {
		boolean downstreamBuildAdded = false;

		List<URL> downstreamBuildURLs = new ArrayList<>(
			downstreamBuilds.size());

		try {
			for (Build downstreamBuild : downstreamBuilds) {
				URL url = new URL(downstreamBuild.getBuildURL());

				downstreamBuildURLs.add(url);
			}

			for (String url : urls) {
				URL addURL = new URL(
					JenkinsResultsParserUtil.getLocalURL(decodeURL(url)));

				if (!downstreamBuildURLs.contains(addURL)) {
					downstreamBuildAdded = true;

					downstreamBuilds.add(
						BuildFactory.newBuild(addURL.toString(), this));
				}
			}

			if (downstreamBuildAdded) {
				setStatus("running");
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int getDownstreamBuildCount(String status) {
		List<Build> downstreamBuilds = getDownstreamBuilds(status);

		return downstreamBuilds.size();
	}

	@Override
	public List<Build> getDownstreamBuilds(String status) {
		if (status == null) {
			return downstreamBuilds;
		}

		List<Build> filteredDownstreamBuilds = new ArrayList<>();

		for (Build downstreamBuild : downstreamBuilds) {
			if (status.equals(downstreamBuild.getStatus())) {
				filteredDownstreamBuilds.add(downstreamBuild);
			}
		}

		return filteredDownstreamBuilds;
	}

	@Override
	public String getInvocationURL() {

		String jobURL = getJobURL();

		if (jobURL == null) {
			return null;
		}

		StringBuffer sb = new StringBuffer(jobURL);

		sb.append("/buildWithParameters?");

		Map<String, String> parameters = getParameters();

		for (Map.Entry<String, String> parameter : parameters.entrySet()) {
			String value = parameter.getValue();

			if ((value != null) && !value.isEmpty()) {
				sb.append(parameter.getKey());
				sb.append("=");
				sb.append(parameter.getValue());
				sb.append("&");
			}
		}

		sb.deleteCharAt(sb.length() - 1);

		return sb.toString();
	}

	@Override
	public String getJobName() {
		return jobName;
	}

	@Override
	public String getJobURL() {
		if ((master == null) || (jobName == null)) {
			return null;
		}

		return "http://" + master + "/job/" + jobName;
	}

	@Override
	public String getMaster() {
		return master;
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
	public Build getParent() {
		return _parent;
	}

	@Override
	public String getResult() {
		if (!_status.equals("completed")) {
			throw new IllegalStateException("Build not completed");
		}

		String buildURL = getBuildURL();

		if ((result == null) && (buildURL != null)) {
			try {
				JSONObject resultJSONObject =
					JenkinsResultsParserUtil.toJSONObject(
						buildURL + "api/json?tree=result");

				result = resultJSONObject.optString("result");

				if (result.equals("")) {
					result = null;
				}
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return result;
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
	public String getStatusReport() {
		return getStatusReport(0);
	}

	@Override
	public String getStatusReport(int indentSize) {
		StringBuffer indentStringBuffer = new StringBuffer();

		for (int i = 0; i < indentSize; i++) {
			indentStringBuffer.append(" ");
		}

		String status = getStatus();

		StringBuilder sb = new StringBuilder();

		sb.append(indentStringBuffer);
		sb.append("Build '");
		sb.append(jobName);
		sb.append("'");

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
				for (Build downstreamBuild : getDownstreamBuilds("running")) {
					sb.append(indentStringBuffer);
					sb.append(downstreamBuild.getStatusReport(indentSize + 4));
					sb.append("\n\n");
				}

				sb.append(indentStringBuffer);
				sb.append("missing: ");
				sb.append(getDownstreamBuildCount("missing"));
				sb.append(" / ");

				sb.append("queued: ");
				sb.append(getDownstreamBuildCount("queued"));
				sb.append(" / ");

				sb.append("running: ");
				sb.append(getDownstreamBuildCount("running"));
				sb.append(" / ");

				sb.append("completed: ");
				sb.append(getDownstreamBuildCount("completed"));
				sb.append(" / ");

				sb.append("total: ");
				sb.append(getDownstreamBuildCount(null));

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

		throw new RuntimeException("Unknown status: " + status + ".");
	}

	@Override
	public void reinvoke() {
		result = null;

		String invocationURL = getInvocationURL();

		badBuildNumbers.add(getBuildNumber());

		setBuildNumber(-1);

		try {
			JenkinsResultsParserUtil.toString(
				JenkinsResultsParserUtil.getLocalURL(invocationURL));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		setStatus("starting");

		System.out.println("Reinvoked: " + invocationURL);
	}

	@Override
	public void update() {
		String status = getStatus();

		if (status.equals("completed")) {
			return;
		}

		try {
			if (status.equals("missing") || status.equals("queued") ||
				status.equals("starting")) {

				JSONObject runningBuildJSONObject = getRunningBuildJSONObject();

				if (runningBuildJSONObject != null) {
					setBuildNumber(runningBuildJSONObject.getInt("number"));

					setStatus("running");

					System.out.println(getBuildMessage());
				}
				else {
					JSONObject queueItemJSONObject = getQueueItemJSONObject();

					if (status.equals("starting") &&
						(queueItemJSONObject != null)) {

						setStatus("queued");
					}
					else if (status.equals("queued") &&
							 (queueItemJSONObject == null)) {

						setStatus("missing");

						System.out.println(getBuildMessage());
					}
				}
			}

			status = getStatus();

			JSONObject buildJSONObject = getBuildJSONObject("result");

			if (downstreamBuilds != null) {
				ExecutorService executorService = Executors.newFixedThreadPool(
					100);

				for (final Build downstreamBuild : downstreamBuilds) {
					Runnable runnable = new Runnable() {

						public void run() {
							downstreamBuild.update();
						}

					};

					executorService.execute(runnable);
				}

				executorService.shutdown();

				while (!executorService.isTerminated()) {
					JenkinsResultsParserUtil.sleep(100);
				}

				String result = buildJSONObject.optString("result");

				if ((downstreamBuilds.size() ==
						getDownstreamBuildCount("completed")) &&
					(result.length() > 0)) {

					setStatus("completed");

					return;
				}

				if (getDownstreamBuildCount("missing") > 0) {
					System.out.println(
						"missing: " + getDownstreamBuildCount("missing"));

					setStatus("missing");

					return;
				}

				if (getDownstreamBuildCount("starting") > 0) {
					System.out.println(
						"starting: " + getDownstreamBuildCount("starting"));

					setStatus("starting");

					return;
				}
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		findDownstreamBuilds();
	}

	protected BaseBuild(String url) throws Exception {
		this(url, null);
	}

	protected BaseBuild(String url, Build parent) throws Exception {
		this._parent = parent;

		if (url.contains("buildWithParameters")) {
			setInvocationURL(url);
		}
		else {
			setBuildURL(url);
		}

		setStatus("starting");

		update();
	}

	protected static String decodeURL(String url) {
		url = url.replace("%28", "(");
		url = url.replace("%29", ")");
		url = url.replace("%5B", "[");
		url = url.replace("%5D", "]");

		return url;
	}

	protected void findDownstreamBuilds() {
		Set<String> downstreamBuildURLs = new HashSet<>(
			findDownstreamBuildsInConsoleText());

		JSONObject buildJSONObject;

		try {
			buildJSONObject = getBuildJSONObject("runs[number,url]");
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		if (buildJSONObject.has("runs")) {
			JSONArray runsJSONArray = buildJSONObject.getJSONArray("runs");

			if (runsJSONArray != null) {
				for (int i = 0; i < runsJSONArray.length(); i++) {
					JSONObject runJSONObject = runsJSONArray.getJSONObject(i);

					if (runJSONObject.getInt("number") == _buildNumber) {
						downstreamBuildURLs.add(runJSONObject.getString("url"));
					}
				}
			}
		}

		addDownstreamBuilds(
			downstreamBuildURLs.toArray(
				new String[downstreamBuildURLs.size()]));
	}

	protected Set<String> findDownstreamBuildsInConsoleText() {
		Matcher downstreamBuildURLMatcher = downstreamBuildURLPattern.matcher(
			getConsoleText());

		Set<String> downstreamBuildURLs = new HashSet<>();

		while (downstreamBuildURLMatcher.find()) {
			downstreamBuildURLs.add(downstreamBuildURLMatcher.group("url"));
		}

		return downstreamBuildURLs;
	}

	protected JSONArray getBuildsJSONArray() throws Exception {
		JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
			getJobURL() + "/api/json?tree=builds[actions[parameters" +
				"[name,type,value]],building,duration,number,result,url]",
			false);

		return jsonObject.getJSONArray("builds");
	}

	protected String getBuildMessage() {
		if (jobName != null) {
			String status = getStatus();

			StringBuilder sb = new StringBuilder();

			sb.append("Build '");
			sb.append(jobName);
			sb.append("'");

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
				sb.append(" started at ");
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

			throw new RuntimeException("Unknown status: " + status + ".");
		}

		return "";
	}

	protected Set<String> getJobParameterNames() throws Exception {
		JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
			getJobURL() + "/api/json?tree=actions[parameterDefinitions" +
				"[name,type,value]]");

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

	protected Map<String, String> getParameters(JSONObject buildJSONObject)
		throws Exception {

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

	protected Map<String, String> getParameters(JSONArray jsonArray)
		throws Exception {

		Map<String, String> parameters = new HashMap<>(jsonArray.length());

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			if (jsonObject.opt("value") instanceof String) {
				String name = jsonObject.getString("name");
				String value = jsonObject.getString("value");

				if (!value.isEmpty()) {
					parameters.put(name, value);
				}
			}
		}

		return parameters;
	}

	protected JSONObject getQueueItemJSONObject() throws Exception {
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

	protected JSONArray getQueueItemsJSONArray() throws Exception {
		JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
			"http://" + master +
				"/queue/api/json?tree=items[actions[parameters" +
				"[name,value]],task[name,url]]",
			false);

		return jsonObject.getJSONArray("items");
	}

	protected JSONObject getRunningBuildJSONObject() throws Exception {
		JSONArray buildsJSONArray = getBuildsJSONArray();

		for (int i = 0; i < buildsJSONArray.length(); i++) {
			JSONObject buildJSONObject = buildsJSONArray.getJSONObject(i);

			Map<String, String> parameters = getParameters();

			if (parameters.equals(getParameters(buildJSONObject)) &&
				!badBuildNumbers.contains(buildJSONObject.getInt("number"))) {

				return buildJSONObject;
			}
		}

		return null;
	}

	protected void loadParameters() throws Exception {

		if (getBuildURL() == null) {
			return;
		}

		JSONObject buildJSONObject = getBuildJSONObject();

		JSONArray actionsJSONArray = buildJSONObject.getJSONArray("actions");

		if (actionsJSONArray.length() == 0) {
			_parameters = new HashMap<>(0);

			return;
		}

		JSONObject actionJSONObject = actionsJSONArray.getJSONObject(0);

		if (actionJSONObject.has("parameters")) {
			JSONArray parametersJSONArray = actionJSONObject.getJSONArray(
				"parameters");

			_parameters = new HashMap<>(parametersJSONArray.length());

			for (int i = 0; i < parametersJSONArray.length(); i++) {
				JSONObject parameterJSONObject =
					parametersJSONArray.getJSONObject(i);

				Object value = parameterJSONObject.opt("value");

				if (value instanceof String) {
					if (!value.toString().isEmpty()) {
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

	protected void loadParameters(String queryString) throws Exception {
		Set<String> jobParameterNames = getJobParameterNames();

		for (String parameter : queryString.split("&")) {
			String[] nameValueArray = parameter.split("=");

			if ((nameValueArray.length == 2) &&
				jobParameterNames.contains(nameValueArray[0])) {

				_parameters.put(nameValueArray[0], nameValueArray[1]);
			}
		}

		return;
	}

	protected void setBuildNumber(int buildNumber) {
		_buildNumber = buildNumber;
	}

	protected void setBuildURL(String buildURL) throws Exception {
		buildURL = decodeURL(buildURL);

		Matcher matcher = buildURLPattern.matcher(buildURL);

		if (!matcher.find()) {
			throw new IllegalArgumentException("Invalid build URL " + buildURL);
		}

		_buildNumber = Integer.parseInt(matcher.group("buildNumber"));
		jobName = matcher.group("jobName");
		master = matcher.group("master");

		loadParameters();

		setStatus("running");
	}

	protected void setInvocationURL(String invocationURL) throws Exception {
		invocationURL = decodeURL(invocationURL);

		if (getBuildURL() == null) {
			Matcher invocationURLMatcher = invocationURLPattern.matcher(
				invocationURL);

			if (!invocationURLMatcher.find()) {
				throw new IllegalArgumentException("Invalid invocation URL");
			}

			jobName = invocationURLMatcher.group("jobName");
			master = invocationURLMatcher.group("master");

			loadParameters(invocationURL);
		}
	}

	protected void setStatus(String status) {
		if (((status == null) && (_status != null)) ||
			!status.equals(_status)) {

			_status = status;

			statusModifiedTime = System.currentTimeMillis();

			System.out.println(getBuildMessage());
		}
	}

	protected List<Integer> badBuildNumbers = new ArrayList<>();
	protected static final Pattern buildURLPattern = Pattern.compile(
		"\\w+://(?<master>[^/]+)/+job/+(?<jobName>[^/]+).*/(?<buildNumber>" +
			"\\d+)/?");
	protected List<Build> downstreamBuilds = new ArrayList<>();
	protected static final Pattern downstreamBuildURLPattern = Pattern.compile(
		"\\'.*\\' started at (?<url>.+)\\.");
	protected static final Pattern invocationURLPattern = Pattern.compile(
		"\\w+://(?<master>[^/]+)/+job/+(?<jobName>[^/]+).*/" +
			"buildWithParameters\\?(?<queryString>.*)");
	protected String jobName;
	protected String master;
	protected String result;
	protected long statusModifiedTime;

	private int _buildNumber = -1;
	private Map<String, String> _parameters = new HashMap<>();
	private Build _parent;
	private String _status;

}