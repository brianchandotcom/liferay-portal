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

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseSpiraArtifact implements SpiraArtifact {

	@Override
	public int getID() {
		return jsonObject.getInt(getIDKey(getClass()));
	}

	@Override
	public String getName() {
		return jsonObject.getString("Name");
	}

	@Override
	public SpiraProject getSpiraProject() {
		if (this instanceof SpiraProject) {
			return (SpiraProject)this;
		}

		return SpiraProject.getSpiraProjectByID(
			jsonObject.getInt(SpiraProject.ID_KEY));
	}

	@Override
	public JSONObject toJSONObject() {
		return jsonObject;
	}

	@Override
	public String toString() {
		return jsonObject.toString();
	}

	protected static void addPreviousSearchParameters(
		Class<? extends BaseSpiraArtifact> baseSpiraArtifactClass,
		SearchResult.SearchParameter[] searchParameters) {

		List<List<SearchResult.SearchParameter>> previousSearchParameters =
			previousSearchParametersMap.get(baseSpiraArtifactClass);

		if (previousSearchParameters == null) {
			previousSearchParameters = Collections.synchronizedList(
				new ArrayList<>());

			previousSearchParametersMap.put(
				baseSpiraArtifactClass, previousSearchParameters);
		}

		previousSearchParameters.add(Arrays.asList(searchParameters));
	}

	protected static String getIDKey(
		Class<? extends SpiraArtifact> spiraArtifactClass) {

		try {
			Field field = spiraArtifactClass.getDeclaredField("ID_KEY");

			return (String)field.get("ID_KEY");
		}
		catch (IllegalAccessException | IllegalArgumentException |
			   NoSuchFieldException exception) {

			throw new RuntimeException(
				"Missing field ID_KEY in " + spiraArtifactClass.getName(),
				exception);
		}
	}

	protected static boolean isPreviousSearch(
		Class<? extends BaseSpiraArtifact> baseSpiraArtifactClass,
		SearchResult.SearchParameter... searchParameters) {

		List<List<SearchResult.SearchParameter>> previousSearchParametersList =
			previousSearchParametersMap.get(baseSpiraArtifactClass);

		if (previousSearchParametersList == null) {
			return false;
		}

		for (List<SearchResult.SearchParameter> previousSearchParameters :
				previousSearchParametersList) {

			if (previousSearchParameters.size() != searchParameters.length) {
				continue;
			}

			for (SearchResult.SearchParameter searchParameter :
					searchParameters) {

				if (!previousSearchParameters.contains(searchParameter)) {
					break;
				}
			}

			return true;
		}

		return false;
	}

	protected static String toDateString(Calendar calendar) {
		return JenkinsResultsParserUtil.combine(
			"/Date(", String.valueOf(calendar.getTimeInMillis()), ")/");
	}

	protected BaseSpiraArtifact(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	protected boolean matches(
		SearchResult.SearchParameter... searchParameters) {

		for (SearchResult.SearchParameter searchParameter : searchParameters) {
			if (!searchParameter.matches(jsonObject)) {
				return false;
			}
		}

		return true;
	}

	protected static final Map
		<Class<? extends BaseSpiraArtifact>,
		 List<List<SearchResult.SearchParameter>>> previousSearchParametersMap =
			Collections.synchronizedMap(new HashMap<>());

	protected final JSONObject jsonObject;

}