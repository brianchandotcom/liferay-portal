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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Kevin Yen
 */
public class DownstreamJob extends BaseJob {

	protected Map<String, String> getParameters(String queryString) {
		if (!queryString.contains("=")) {
			return Collections.emptyMap();
		}

		Map<String, String> parameters = new HashMap<>();

		for (String parameter : queryString.split("&")) {
			if (parameter.contains("=")) {
				String[] parameterParts = parameter.split("=");

				parameters.put(parameterParts[0], parameterParts[1]);
			}
		}

		return parameters;
	}

	public DownstreamJob(String invocationURL, TopLevelJob topLevelJob)
		throws Exception {

		this.topLevelJob = topLevelJob;

		Matcher invocationURLMatcher = _invocationURLPattern.matcher(
			invocationURL);

		if (!invocationURLMatcher.find()) {
			throw new IllegalArgumentException("Invalid invocation URL");
		}

		master = invocationURLMatcher.group("master");
		name = invocationURLMatcher.group("name");

		String parametersString = invocationURLMatcher.group("queryString");

		Map<String, String> invokedParameters = getParameters(
			parametersString);

		Set<String> parameterNames = getParameterNames();

		parameters = new HashMap<>();

		for (String parameterName : parameterNames) {
			if (invokedParameters.keySet().contains(parameterName)) {
				parameters.put(
					parameterName, invokedParameters.get(parameterName));
			}
			else {
				parameters.put(parameterName, "");
			}
		}

		this.invocationURL = invocationURL;
	}

	public String getInvocationURL() {
		return invocationURL;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public TopLevelJob getTopLevelJob() {
		return topLevelJob;
	}

	@Override
	public void update() throws Exception {
		if (getStatus().equals("completed") || getStatus().equals("invalid")) {
			return;
		}

		if (getStatus().equals("queued") || getStatus().equals("starting") ||
			getStatus().equals("missing")) {

			JSONObject build = getRunningBuildJSONObject();

			if (build != null) {
				number = build.getInt("number");

				setStatus("running");

				System.out.println(getBuildMessage());
			}
			else {
				JSONObject queueItem = getQueueItemJSONObject();

				if (getStatus().equals("started") && (queueItem != null)) {
					setStatus("queued");
				}
				else if (getStatus().equals("queued") && (queueItem == null)) {
					setStatus("missing");

					System.out.println(getBuildMessage());
				}
			}
		}

		if (getStatus().equals("running")) {
			JSONObject build = getCompletedBuildJSONObject();

			if (build != null) {
				result = build.getString("result");

				setStatus("completed");
			}
		}
	}

	protected JSONArray getBuildsJSONArray() throws Exception {
		JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
			"http://" + master + "/job/" + name +
				"/api/json?tree=builds[actions[parameters" +
					"[name,type,value]],building,duration,number,result,url]",
			false);

		return jsonObject.getJSONArray("builds");
	}

	protected JSONObject getCompletedBuildJSONObject() throws Exception {
		JSONArray builds = getBuildsJSONArray();

		for (int i = 0; i < builds.length(); i++) {
			JSONObject build = builds.getJSONObject(i);

			if ((number == build.getInt("number")) &&
				(build.get("result") != null) &&
				!build.getBoolean("building")) {

				return build;
			}
		}

		return null;
	}

	protected Set<String> getParameterNames() throws Exception {
		Set<String> parameterNames = new HashSet<>();

		JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
			"http://" + master + "/job/" + name +
				"/api/json?tree=actions[parameterDefinitions" +
					"[name,type,value]]");

		JSONArray parameterDefinitions = jsonObject.getJSONArray(
			"actions").getJSONObject(0).getJSONArray("parameterDefinitions");

		for (int i = 0; i < parameterDefinitions.length(); i++) {
			JSONObject parameterDefinition = parameterDefinitions.getJSONObject(
				i);

			if (parameterDefinition.getString(
					"type").equals("StringParameterDefinition")) {

				parameterNames.add(parameterDefinition.getString("name"));
			}
		}

		return parameterNames;
	}

	protected JSONObject getQueueItemJSONObject() throws Exception {
		JSONArray queueItems = getQueueItemsJSONArray();

		for (int i = 0; i < queueItems.length(); i++) {
			JSONObject queueItem = queueItems.getJSONObject(i);

			String queueItemName = queueItem.getJSONObject(
				"task").getString("name");
			Map<String, String> jobParameters = getParameters(queueItem);

			if (queueItemName.equals(name) &&
				jobParameters.equals(parameters)) {

				return queueItem;
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
		JSONArray builds = getBuildsJSONArray();

		for (int i = 0; i < builds.length(); i++) {
			JSONObject build = builds.getJSONObject(i);

			if (parameters.equals(getParameters(build))) {
				return build;
			}
		}

		return null;
	}

	protected String invocationURL;
	protected Map<String, String> parameters;
	protected TopLevelJob topLevelJob;

	private Map<String, String> getParameters(
			JSONArray parametersJSONArray)
		throws Exception {

		Map<String, String> parameters = new HashMap<>();

		for (int i = 0; i < parametersJSONArray.length(); i++) {
			JSONObject parameter = parametersJSONArray.getJSONObject(i);

			if (parameter.opt("value") instanceof String) {
				String name = parameter.getString("name");
				String value = parameter.getString("value");

				parameters.put(name, value);
			}
		}

		return parameters;
	}

	private Map<String, String> getParameters(JSONObject buildJSONObject)
		throws Exception {

		JSONArray actionsJSONArray = buildJSONObject.getJSONArray("actions");

		if ((actionsJSONArray.length() > 0) &&
			actionsJSONArray.getJSONObject(0).has("parameters")) {

			JSONArray parametersJSONArray = actionsJSONArray.getJSONObject(
				0).getJSONArray("parameters");

			return getParameters(parametersJSONArray);
		}

		return new HashMap<>();
	}

	private String getBuildMessage() {
		StringBuilder sb = new StringBuilder();

		sb.append("Build '");
		sb.append(name);
		sb.append("'");

		if (getStatus().equals("completed")) {
			sb.append(" completed at ");
			sb.append(getURL());
			sb.append(". ");
			sb.append(getResult());
			return sb.toString();
		}

		if (getStatus().equals("queued")) {
			sb.append(" is queued at ");
			sb.append("http://");
			sb.append(master);
			sb.append("/job/");
			sb.append(name);
			sb.append(".");
			return sb.toString();
		}

		if (getStatus().equals("running")) {
			sb.append(" started at ");
			sb.append(getURL());
			sb.append(".");
			return sb.toString();
		}

		if (getStatus().equals("starting")) {
			sb.append(" invoked at ");
			sb.append("http://");
			sb.append(master);
			sb.append("/job/");
			sb.append(name);
			sb.append(".");
			return sb.toString();
		}

		if (getStatus().equals("invalid")) {
			sb.append(" is invalid ");
			sb.append("http://");
			sb.append(master);
			sb.append("/job/");
			sb.append(name);
			sb.append(".");
			return sb.toString();
		}

		if (getStatus().equals("missing")) {
			sb.append(" is missing ");
			sb.append("http://");
			sb.append(master);
			sb.append("/job/");
			sb.append(name);
			sb.append(".");
			return sb.toString();
		}

		throw new RuntimeException("Unknown status: " + getStatus() + ".");
	}

	private static final Pattern _invocationURLPattern = Pattern.compile(
		"\\w+://(?<master>[^/]+)/+job/+(?<name>[^/]+).*/" +
			"buildWithParameters\\?(?<queryString>.*)");

}