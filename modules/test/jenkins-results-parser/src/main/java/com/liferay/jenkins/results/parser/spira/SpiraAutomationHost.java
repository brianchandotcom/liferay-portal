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

package com.liferay.jenkins.results.parser.spira;

import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.JenkinsNode;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.commons.lang.StringEscapeUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SpiraAutomationHost extends BaseSpiraArtifact {

	public static SpiraAutomationHost createSpiraAutomationHost(
		SpiraProject spiraProject, JenkinsNode jenkinsNode) {

		String automationHostName = jenkinsNode.getName();

		List<SpiraAutomationHost> spiraAutomationHosts =
			getSpiraAutomationHosts(
				spiraProject,
				new SearchQuery.SearchParameter("Name", automationHostName));

		if (!spiraAutomationHosts.isEmpty()) {
			return spiraAutomationHosts.get(0);
		}

		String urlPath = "projects/{project_id}/automation-hosts";

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put("Active", true);
		requestJSONObject.put(
			"Name", StringEscapeUtils.unescapeJava(automationHostName));
		requestJSONObject.put(
			"Token", JenkinsResultsParserUtil.getDistinctTimeStamp());

		JSONArray customPropertiesJSONArray = new JSONArray();

		JenkinsMaster jenkinsMaster = jenkinsNode.getJenkinsMaster();

		SpiraCustomProperty jenkinsMasterSpiraCustomProperty =
			SpiraCustomProperty.createSpiraCustomProperty(
				spiraProject, SpiraAutomationHost.class, "Jenkins Master",
				SpiraCustomProperty.Type.LIST);

		SpiraCustomPropertyValue jenkinsMasterSpiraCustomPropertyValue =
			SpiraCustomPropertyValue.createSpiraCustomPropertyValue(
				jenkinsMasterSpiraCustomProperty, jenkinsMaster.getName());

		customPropertiesJSONArray.put(
			jenkinsMasterSpiraCustomPropertyValue.
				getCustomPropertyJSONObject());

		requestJSONObject.put("CustomProperties", customPropertiesJSONArray);

		try {
			SpiraRestAPIUtil.requestJSONObject(
				urlPath, null, urlPathReplacements,
				JenkinsResultsParserUtil.HttpRequestMethod.POST,
				requestJSONObject.toString());

			return spiraProject.getSpiraAutomationHostByName(
				automationHostName);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Override
	public String getURL() {
		SpiraProject spiraProject = getSpiraProject();

		return JenkinsResultsParserUtil.combine(
			SPIRA_BASE_URL, String.valueOf(spiraProject.getID()),
			"/AutomationHost/", String.valueOf(getID()), ".aspx");
	}

	protected static List<SpiraAutomationHost> getSpiraAutomationHosts(
		final SpiraProject spiraProject,
		final SearchQuery.SearchParameter... searchParameters) {

		return getSpiraArtifacts(
			SpiraAutomationHost.class,
			new Supplier<List<JSONObject>>() {

				@Override
				public List<JSONObject> get() {
					return _requestSpiraAutomationHosts(spiraProject);
				}

			},
			new Function<JSONObject, SpiraAutomationHost>() {

				@Override
				public SpiraAutomationHost apply(JSONObject jsonObject) {
					return new SpiraAutomationHost(jsonObject);
				}

			},
			searchParameters);
	}

	protected static final Integer ARTIFACT_TYPE_ID = 9;

	protected static final String ARTIFACT_TYPE_NAME = "automationhost";

	protected static final String KEY_ID = "AutomationHostId";

	private static List<JSONObject> _requestSpiraAutomationHosts(
		SpiraProject spiraProject) {

		List<JSONObject> spiraTestCaseTypes = new ArrayList<>();

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));

		Map<String, String> urlParameters = new HashMap<>();

		urlParameters.put("number_of_rows", "2000");
		urlParameters.put("sort_direction", "ASC");
		urlParameters.put("sort_field", "Name");
		urlParameters.put("starting_row", "0");

		JSONArray requestJSONArray = new JSONArray();

		try {
			JSONArray responseJSONArray = SpiraRestAPIUtil.requestJSONArray(
				"projects/{project_id}/automation-hosts/search", urlParameters,
				urlPathReplacements,
				JenkinsResultsParserUtil.HttpRequestMethod.POST,
				requestJSONArray.toString());

			for (int i = 0; i < responseJSONArray.length(); i++) {
				JSONObject responseJSONObject = responseJSONArray.getJSONObject(
					i);

				responseJSONObject.put(
					SpiraProject.KEY_ID, spiraProject.getID());

				spiraTestCaseTypes.add(responseJSONObject);
			}

			return spiraTestCaseTypes;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private SpiraAutomationHost(JSONObject jsonObject) {
		super(jsonObject);

		cacheSpiraArtifact(SpiraAutomationHost.class, this);
	}

}