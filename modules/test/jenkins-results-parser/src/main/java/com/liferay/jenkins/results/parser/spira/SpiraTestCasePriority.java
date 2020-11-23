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

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SpiraTestCasePriority extends BaseSpiraArtifact {

	public Integer getScore() {
		return jsonObject.getInt("Score");
	}

	@Override
	public String getURL() {
		SpiraProject spiraProject = getSpiraProject();

		return JenkinsResultsParserUtil.combine(
			SPIRA_BASE_URL, "/pt/",
			String.valueOf(spiraProject.getProjectTemplateID()),
			"/Administration/TestCasePriorities.aspx");
	}

	protected static List<SpiraTestCasePriority> getSpiraTestCasePriorities(
		final SpiraProject spiraProject,
		SearchQuery.SearchParameter... searchParameters) {

		return getSpiraArtifacts(
			SpiraTestCasePriority.class,
			new Supplier<List<JSONObject>>() {

				@Override
				public List<JSONObject> get() {
					return _requestSpiraTestCasePriorities(spiraProject);
				}

			},
			new Function<JSONObject, SpiraTestCasePriority>() {

				@Override
				public SpiraTestCasePriority apply(JSONObject jsonObject) {
					return new SpiraTestCasePriority(spiraProject, jsonObject);
				}

			},
			searchParameters);
	}

	protected static final String ARTIFACT_TYPE_NAME = "priority";

	protected static final String KEY_ID = "PriorityId";

	private static List<JSONObject> _requestSpiraTestCasePriorities(
		SpiraProject spiraProject) {

		List<JSONObject> spiraTestCaseTypes = new ArrayList<>();

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_template_id",
			String.valueOf(spiraProject.getProjectTemplateID()));

		try {
			JSONArray responseJSONArray = SpiraRestAPIUtil.requestJSONArray(
				"project-templates/{project_template_id}/test-cases/priorities",
				null, urlPathReplacements,
				JenkinsResultsParserUtil.HttpRequestMethod.GET, null);

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

	private SpiraTestCasePriority(
		SpiraProject spiraProject, JSONObject jsonObject) {

		super(jsonObject);

		jsonObject.put(SpiraProject.KEY_ID, spiraProject.getID());

		cacheSpiraArtifact(SpiraTestCasePriority.class, this);
	}

}