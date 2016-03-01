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

	public static Map<String, String> getParametersFromString(
		String parametersString) {

		Map<String, String> parameters = new HashMap<>();

		Matcher parameterMatcher = _parameterPattern.matcher(parametersString);

		if (!parameterMatcher.find()) {
			return parameters;
		}

		parameterMatcher.reset();

		while (parameterMatcher.find()) {
			String parameter = parameterMatcher.group("parameter");

			Matcher keyValueMatcher = _keyValuePattern.matcher(parameter);

			if (keyValueMatcher.find()) {
				String key = keyValueMatcher.group("key");
				String value = keyValueMatcher.group("value");

				parameters.put(key, value);
			}

			parametersString = parameterMatcher.group("remainder");

			parameterMatcher = _parameterPattern.matcher(parametersString);
		}

		Matcher keyValueMatcher = _keyValuePattern.matcher(parametersString);

		if (keyValueMatcher.find()) {
			String key = keyValueMatcher.group("key");
			String value = keyValueMatcher.group("value");

			parameters.put(key, value);
		}

		return parameters;
	}

	public DownstreamJob(String invocationURL, TopLevelJob topLevelJob)
		throws Exception {

		this.topLevelJob = topLevelJob;
		this.topLevelJob.add(this);

		Matcher invocationURLMatcher = _invocationURLPattern.matcher(
			invocationURL);

		if (!invocationURLMatcher.find()) {
			throw new IllegalArgumentException("Invalid invocation URL");
		}

		masterURL = invocationURLMatcher.group("masterURL");
		name = invocationURLMatcher.group("name");

		String parametersString = invocationURLMatcher.group(
			"parametersString");

		Map<String, String> invokedParameters = getParametersFromString(
			parametersString);

		Set<String> availableParameters = getParameterNames(getJobURL());

		parameters = new HashMap<>();

		for (String parameterName : availableParameters) {
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
		return this.parameters;
	}

	public TopLevelJob getTopLevelJob() {
		return topLevelJob;
	}

	public void update() throws Exception {
		if (status.equals("completed") || status.equals("invalid")) {
			return;
		}

		if (status.equals("starting")) {
			JSONArray queueItems = getQueueItemsJSONArray(masterURL);

			for (int i = 0; i < queueItems.length(); i++) {
				JSONObject queueItem = queueItems.getJSONObject(i);

				String queueItemName = queueItem.getJSONObject(
					"task").getString("name");
				Map<String, String> jobParameters = getParameters(queueItem);

				if (queueItemName.equals(name) &&
					jobParameters.equals(parameters)) {

					status = "queued";
				}
			}
		}

		if (status.equals("starting") || status.equals("queued")) {
			JSONArray builds = getBuildsJSONArray(getJobURL());

			for (int i = 0; i < builds.length(); i++) {
				JSONObject build = builds.getJSONObject(i);

				if (parameters.equals(getParameters(build))) {
					setNumber(build.getInt("number"));

					System.out.println(getBuildMessage());

					break;
				}
			}
		}

		if (status.equals("running")) {
			JSONArray builds = getBuildsJSONArray(getJobURL());

			for (int i = 0; i < builds.length(); i++) {
				JSONObject build = builds.getJSONObject(i);

				if ((number == build.getInt("number")) &&
					(build.get("result") != null) &&
					!build.getBoolean("building")) {

					setCompleted(build.getString("result"));

					break;
				}
			}
		}
	}

	protected String invocationURL;
	protected Map<String, String> parameters;
	protected TopLevelJob topLevelJob;

	private static JSONArray getBuildsJSONArray(String jobURL)
		throws Exception {

		JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
			jobURL + "/api/json?tree=builds[actions[parameters" +
				"[name,type,value]],building,duration,number,result,url]",
			false);

		return jsonObject.getJSONArray("builds");
	}

	private static Set<String> getParameterNames(String jobURL)
		throws Exception {

		Set<String> parameterNames = new HashSet<>();

		JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
			jobURL + "/api/json?tree=actions[parameterDefinitions" +
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

	private static Map<String, String> getParameters(
			JSONArray parametersJSONArray)
		throws Exception {

		Map<String, String> parameters = new HashMap<>();

		for (int i = 0; i < parametersJSONArray.length(); i++) {
			JSONObject parameter = parametersJSONArray.getJSONObject(i);

			if (parameter.has("value")) {
				String name = parameter.getString("name");
				String value = parameter.getString("value");

				parameters.put(name, value);
			}
		}

		return parameters;
	}

	private static Map<String, String> getParameters(JSONObject buildJSONObject)
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

	private static JSONArray getQueueItemsJSONArray(String masterURL)
		throws Exception {

		JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
			masterURL + "/queue/api/json?tree=items[actions[parameters" +
				"[name,value]],task[name,url]]",
			false);

		return jsonObject.getJSONArray("items");
	}

	private String getBuildMessage() {
		StringBuilder sb = new StringBuilder();

		sb.append("Build '");
		sb.append(name);
		sb.append("'");

		if (status.equals("completed")) {
			sb.append(" completed at ");
			sb.append(getBuildURL());
			sb.append(". ");
			sb.append(getResult());
			return sb.toString();
		}

		if (status.equals("queued")) {
			sb.append(" is queued ");
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

		if (status.equals("invalid")) {
			sb.append(" is invalid ");
			sb.append(getJobURL());
			sb.append(".");
			return sb.toString();
		}

		throw new RuntimeException("Unknown status: " + status + ".");
	}

	private static final Pattern _invocationURLPattern = Pattern.compile(
		"(?<masterURL>\\w+://[^/]+)/+job/+(?<name>[^/]+).*/" +
			"buildWithParameters\\?(?<parametersString>.*)");
	private static final Pattern _keyValuePattern = Pattern.compile(
		"(?<key>[^=]+)=(?<value>.*)");
	private static final Pattern _parameterPattern = Pattern.compile(
		"(?<parameter>[^&]*)&(?<remainder>.*)");

}