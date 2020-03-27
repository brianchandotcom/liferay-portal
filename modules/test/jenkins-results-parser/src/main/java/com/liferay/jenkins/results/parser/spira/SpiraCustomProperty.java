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

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil.HttpRequestMethod;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
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
public class SpiraCustomProperty extends BaseSpiraArtifact {

	protected static List<SpiraCustomProperty> getSpiraCustomProperties(
		final SpiraProject spiraProject,
		final Class<? extends SpiraArtifact> spiraArtifactClass,
		SearchQuery.SearchParameter... searchParameters) {

		SearchQuery.SearchParameter[] customSearchParameters =
			new SearchQuery.SearchParameter[searchParameters.length + 2];

		customSearchParameters[0] = new SearchQuery.SearchParameter(
			SpiraProject.ID_KEY, spiraProject.getID());
		customSearchParameters[1] = new SearchQuery.SearchParameter(
			"ArtifactTypeName", getArtifactTypeName(spiraArtifactClass));

		for (int i = 0; i < searchParameters.length; i++) {
			customSearchParameters[i + 2] = searchParameters[i];
		}

		return getSpiraArtifacts(
			SpiraCustomProperty.class,
			new Supplier<List<JSONObject>>() {

				@Override
				public List<JSONObject> get() {
					return _requestSpiraCustomProperties(
						spiraProject, spiraArtifactClass);
				}

			},
			new Function<JSONObject, SpiraCustomProperty>() {

				@Override
				public SpiraCustomProperty apply(JSONObject jsonObject) {
					return new SpiraCustomProperty(
						jsonObject, spiraArtifactClass);
				}

			},
			customSearchParameters);
	}

	protected static final String ARTIFACT_TYPE_NAME = "customproperty";

	protected static final String ID_KEY = "CustomPropertyId";

	private static List<JSONObject> _requestSpiraCustomProperties(
		SpiraProject spiraProject,
		Class<? extends SpiraArtifact> spiraArtifactClass) {

		List<JSONObject> spiraCustomProperties = new ArrayList<>();

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"artifact_type_name", getArtifactTypeName(spiraArtifactClass));
		urlPathReplacements.put(
			"project_template_id",
			String.valueOf(spiraProject.getProjectTemplateID()));

		try {
			JSONArray responseJSONArray = SpiraRestAPIUtil.requestJSONArray(
				"project-templates/{project_template_id}/custom-properties" +
					"/{artifact_type_name}",
				null, urlPathReplacements, HttpRequestMethod.GET, null);

			for (int i = 0; i < responseJSONArray.length(); i++) {
				JSONObject responseJSONObject = responseJSONArray.getJSONObject(
					i);

				responseJSONObject.put(
					SpiraProject.ID_KEY, spiraProject.getID());

				spiraCustomProperties.add(responseJSONObject);
			}

			return spiraCustomProperties;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private SpiraCustomProperty(
		JSONObject jsonObject,
		Class<? extends SpiraArtifact> spiraArtifactClass) {

		super(jsonObject);

		jsonObject.put(
			"ArtifactTypeName", getArtifactTypeName(spiraArtifactClass));
	}

}