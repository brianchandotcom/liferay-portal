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
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil.HttpRequestMethod;

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
public class SpiraTestCaseType extends BaseSpiraArtifact {

	@Override
	public String getURL() {
		SpiraProject spiraProject = getSpiraProject();

		return JenkinsResultsParserUtil.combine(
			SPIRA_BASE_URL, "/pt/",
			String.valueOf(spiraProject.getProjectTemplateID()),
			"/Administration/TestCaseTypes.aspx");
	}

	protected static List<SpiraTestCaseType> getSpiraTestCaseTypes(
		final SpiraProject spiraProject,
		SearchQuery.SearchParameter... searchParameters) {

		return getSpiraArtifacts(
			SpiraTestCaseType.class,
			new Supplier<List<JSONObject>>() {

				@Override
				public List<JSONObject> get() {
					return _requestSpiraTestCaseTypes(spiraProject);
				}

			},
			new Function<JSONObject, SpiraTestCaseType>() {

				@Override
				public SpiraTestCaseType apply(JSONObject jsonObject) {
					return new SpiraTestCaseType(jsonObject);
				}

			},
			searchParameters);
	}

	protected static final String ARTIFACT_TYPE_NAME = "testcasetype";

	protected static final String ID_KEY = "TestCaseTypeId";

	private static List<JSONObject> _requestSpiraTestCaseTypes(
		SpiraProject spiraProject) {

		List<JSONObject> spiraTestCaseTypes = new ArrayList<>();

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_template_id",
			String.valueOf(spiraProject.getProjectTemplateID()));

		try {
			JSONArray responseJSONArray = SpiraRestAPIUtil.requestJSONArray(
				"project-templates/{project_template_id}/test-cases/types",
				null, urlPathReplacements, HttpRequestMethod.GET, null);

			for (int i = 0; i < responseJSONArray.length(); i++) {
				JSONObject responseJSONObject = responseJSONArray.getJSONObject(
					i);

				responseJSONObject.put(
					SpiraProject.ID_KEY, spiraProject.getID());

				spiraTestCaseTypes.add(responseJSONObject);
			}

			return spiraTestCaseTypes;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private SpiraTestCaseType(JSONObject jsonObject) {
		super(jsonObject);

		cacheSpiraArtifact(SpiraTestCaseType.class, this);
	}

}