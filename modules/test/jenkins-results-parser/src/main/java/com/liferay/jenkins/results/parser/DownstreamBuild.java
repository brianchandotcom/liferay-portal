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

import java.io.UnsupportedEncodingException;

import java.net.URLDecoder;

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
public class DownstreamBuild extends BaseBuild {

	public DownstreamBuild(String invocationURL, TopLevelBuild topLevelBuild)
		throws Exception {

		this.topLevelBuild = topLevelBuild;

		Matcher invocationURLMatcher = _invocationURLPattern.matcher(
			invocationURL);

		if (!invocationURLMatcher.find()) {
			throw new IllegalArgumentException("Invalid invocation URL");
		}

		jobName = invocationURLMatcher.group("jobName");
		master = invocationURLMatcher.group("master");
		String queryString = invocationURLMatcher.group("queryString");

		Map<String, String> invokedParameters = getParameters(queryString);

		Set<String> parameterNames = getParameterNames();

		parameters = new HashMap<>();

		for (String parameterName : parameterNames) {
			if (invokedParameters.containsKey(parameterName)) {
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

	public TopLevelBuild getTopLevelBuild() {
		return topLevelBuild;
	}

	@Override
	public void update() throws Exception {
		String status = getStatus();

		if (status.equals("completed")) {
			return;
		}

		if (status.equals("missing") || status.equals("queued") ||
			status.equals("starting")) {

			JSONObject buildJSONObject = getRunningBuildJSONObject();

			if (buildJSONObject != null) {
				buildNumber = buildJSONObject.getInt("number");

				setStatus("running");

				System.out.println(_getBuildMessage());
			}
			else {
				JSONObject queueItemJSONObject = getQueueItemJSONObject();

				if (status.equals("started") && (queueItemJSONObject != null)) {
					setStatus("queued");
				}
				else if (status.equals("queued") &&
						 (queueItemJSONObject == null)) {

					setStatus("missing");

					System.out.println(_getBuildMessage());
				}
			}
		}

		status = getStatus();

		if (status.equals("running")) {
			JSONObject completedBuildJSONObject = getCompletedBuildJSONObject();

			if (completedBuildJSONObject != null) {
				result = completedBuildJSONObject.getString("result");

				setStatus("completed");
			}
		}
	}

	protected JSONArray getBuildsJSONArray() throws Exception {
		JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
			getJobURL() + "/api/json?tree=builds[actions[parameters" +
				"[name,type,value]],building,duration,number,result,url]",
			false);

		return jsonObject.getJSONArray("builds");
	}

	protected JSONObject getCompletedBuildJSONObject() throws Exception {
		JSONArray buildsJSONArray = getBuildsJSONArray();

		for (int i = 0; i < buildsJSONArray.length(); i++) {
			JSONObject buildJSONObject = buildsJSONArray.getJSONObject(i);

			if ((buildNumber == buildJSONObject.getInt("number")) &&
				(buildJSONObject.get("result") != null) &&
				!buildJSONObject.getBoolean("building")) {

				return buildJSONObject;
			}
		}

		return null;
	}

	protected Set<String> getParameterNames() throws Exception {
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

	protected Map<String, String> getParameters(JSONArray jsonArray)
		throws Exception {

		Map<String, String> parameters = new HashMap<>(jsonArray.length());

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			if (jsonObject.opt("value") instanceof String) {
				String name = jsonObject.getString("name");
				String value = jsonObject.getString("value");

				parameters.put(name, value);
			}
		}

		return parameters;
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

	protected Map<String, String> getParameters(String queryString)
		throws UnsupportedEncodingException {

		if (!queryString.contains("=")) {
			return Collections.emptyMap();
		}

		Map<String, String> parameters = new HashMap<>();

		for (String parameter : queryString.split("&")) {
			if (parameter.contains("=")) {
				String[] parameterParts = parameter.split("=");

				String name = URLDecoder.decode(parameterParts[0], "UTF-8");
				String value = URLDecoder.decode(parameterParts[1], "UTF-8");

				parameters.put(name, value);
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

			if (parameters.equals(getParameters(queueItemJSONObject))) {
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

			if (parameters.equals(getParameters(buildJSONObject))) {
				return buildJSONObject;
			}
		}

		return null;
	}

	protected String invocationURL;
	protected Map<String, String> parameters;
	protected TopLevelBuild topLevelBuild;

	private String _getBuildMessage() {
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

	private static final Pattern _invocationURLPattern = Pattern.compile(
		"\\w+://(?<master>[^/]+)/+job/+(?<jobName>[^/]+).*/" +
			"buildWithParameters\\?(?<queryString>.*)");

}