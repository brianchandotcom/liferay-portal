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
public abstract class BaseBuild implements Build {

	public BaseBuild(String url) throws Exception {
		setStatus("starting");

		if (url.contains("buildWithParameters")) {
			setInvocationURL(url);
			
			return;
		}

		setBuildURL(buildURL);
	}
	
	public JSONObject getBuildJSONObject() throws Exception {
		return getBuildJSONObject(null);
	}
	
	public JSONObject getBuildJSONObject(String tree) throws Exception {
		if (tree == null) {
			tree = "actions[parameters[*]],number,result,runs[number,url]";
		}
		
		return JenkinsResultsParserUtil.toJSONObject(
			JenkinsResultsParserUtil.getLocalURL(
				buildURL + "/api/json?pretty&tree=" + tree));
	}

	@Override
	public int getBuildNumber() {
		return buildNumber;
	}

	@Override
	public String getBuildURL() {
		String jobURL = getJobURL();

		if ((jobURL == null) || (buildNumber == -1)) {
			return null;
		}

		return jobURL + buildNumber + "/";
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

		return "http://" + master + "/job/" + jobName + "/";
	}

	@Override
	public String getMaster() {
		return master;
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

	protected static String decodeURL(String url) {
		url = url.replace("%28", "(");
		url = url.replace("%29", ")");
		url = url.replace("%5B", "[");
		url = url.replace("%5D", "]");

		return url;
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

	protected void loadParameters()
		throws Exception {
		
		if (buildURL == null) {
			Matcher invocationURLMatcher = invocationURLPattern.matcher(
				this.invocationURL);
	
			if (!invocationURLMatcher.find()) {
				throw new IllegalArgumentException("Invalid invocation URL");
			}
	
			String queryString = invocationURLMatcher.group("queryString");
			
			Set<String> jobParameterNames = getJobParameterNames();
	
			for (String parameter : queryString.split("&")) {
				String[] nameValueArray = parameter.split("=");
				if ((nameValueArray.length == 2) && jobParameterNames.contains(nameValueArray[0])) {
					parameters.put(nameValueArray[0], nameValueArray[1]);
				}
			}
			
			return;
		}		
		
		JSONObject buildJSONObject = getBuildJSONObject();

		JSONArray actionsJSONArray = buildJSONObject.getJSONArray("actions");

		if (actionsJSONArray.length() == 0) {
			parameters = new HashMap<>(0);

			return;
		}

		JSONObject actionJSONObject = actionsJSONArray.getJSONObject(0);

		if (actionJSONObject.has("parameters")) {
			JSONArray parametersJSONArray =
				actionJSONObject.getJSONArray("parameters");

			parameters = new HashMap<>(parametersJSONArray.length());

			for (int i = 0; i < parametersJSONArray.length(); i++) {
				JSONObject parameterJSONObject =
					parametersJSONArray.getJSONObject(i);

				Object value = parameterJSONObject.opt("value");

				if (value instanceof String) {
					if (!value.toString().isEmpty()) {
						parameters.put(
							parameterJSONObject.getString("name"),
							value.toString());
					}
				}
			}

			return;
		}

		parameters = Collections.emptyMap();
	}

	protected void setBuildURL(String buildURL) throws Exception {
		this.buildURL = decodeURL(buildURL);

		Matcher matcher = buildURLPattern.matcher(this.buildURL);

		if (!matcher.find()) {
			throw new IllegalArgumentException("Invalid build URL " + this.buildURL);
		}

		buildNumber = Integer.parseInt(matcher.group("buildNumber"));
		jobName = matcher.group("jobName");
		master = matcher.group("master");
		
		loadParameters();

		update();
	}

	protected void setInvocationURL(String invocationURL) throws Exception {
		this.invocationURL = decodeURL(invocationURL);

		if (buildURL == null) {
			Matcher invocationURLMatcher = invocationURLPattern.matcher(
				this.invocationURL);
	
			if (!invocationURLMatcher.find()) {
				throw new IllegalArgumentException("Invalid invocation URL");
			}
	
			jobName = invocationURLMatcher.group("jobName");
			master = invocationURLMatcher.group("master");
			
			loadParameters();
			
			update();
		}

	}

	protected void setStatus(String status) {
		if (((status == null) && (_status != null)) ||
			!status.equals(_status)) {

			_status = status;

			statusModifiedTime = System.currentTimeMillis();
		}
	}

	protected int buildNumber = -1;
	protected String buildURL;
	protected final Pattern buildURLPattern = Pattern.compile(
		"\\w+://(?<master>[^/]+)/+job/+(?<jobName>[^/]+).*/(?<buildNumber>" +
			"\\d+)/?");
	protected String invocationURL;
	protected final Pattern invocationURLPattern = Pattern.compile(
		"\\w+://(?<master>[^/]+)/+job/+(?<jobName>[^/]+).*/" +
			"buildWithParameters\\?(?<queryString>.*)");
	protected String jobName;
	protected String master;
	protected Map<String, String> parameters = new HashMap<>();
	protected String result;
	protected long statusModifiedTime;


	private String _status;

}