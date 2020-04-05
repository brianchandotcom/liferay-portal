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
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseSpiraArtifact implements SpiraArtifact {

	public static int getArtifactTypeID(
		Class<? extends SpiraArtifact> spiraArtifactClass) {

		return (Integer)_getClassField(spiraArtifactClass, "ARTIFACT_TYPE_ID");
	}

	public static String getArtifactTypeName(
		Class<? extends SpiraArtifact> spiraArtifactClass) {

		return (String)_getClassField(spiraArtifactClass, "ARTIFACT_TYPE_NAME");
	}

	public static String getIDKey(
		Class<? extends SpiraArtifact> spiraArtifactClass) {

		return (String)_getClassField(spiraArtifactClass, "ID_KEY");
	}

	@Override
	public boolean equals(Object o) {
		if (!Objects.equals(getClass(), o.getClass())) {
			return false;
		}

		SpiraArtifact spiraArtifact = (SpiraArtifact)o;

		if (!(o instanceof SpiraProject)) {
			SpiraProject spiraProject = spiraArtifact.getSpiraProject();

			if (!spiraProject.equals(getSpiraProject())) {
				return false;
			}
		}

		if (spiraArtifact.getID() != getID()) {
			return false;
		}

		return true;
	}

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
	public int hashCode() {
		JSONObject jsonObject = toJSONObject();

		return jsonObject.hashCode();
	}

	@Override
	public JSONObject toJSONObject() {
		return jsonObject;
	}

	@Override
	public String toString() {
		return jsonObject.toString();
	}

	protected static <S extends SpiraArtifact> void cacheSpiraArtifacts(
		List<S> spiraArtifacts, Class<S> spiraArtifactClass) {

		List<S> cachedSpiraArtifacts = _getCachedSpiraArtifacts(
			spiraArtifactClass);

		for (S spiraArtifact : spiraArtifacts) {
			if (cachedSpiraArtifacts.contains(spiraArtifact)) {
				continue;
			}

			cachedSpiraArtifacts.add(spiraArtifact);
		}
	}

	protected static <S extends SpiraArtifact> List<S> getSpiraArtifacts(
		Class<S> spiraArtifactClass,
		Supplier<List<JSONObject>> spiraArtifactRequest,
		Function<JSONObject, S> spiraArtifactCreator,
		SearchQuery.SearchParameter... searchParameters) {

		SearchQuery<S> searchQuery =
			(SearchQuery<S>)SearchQuery.getCachedSearchQuery(
				spiraArtifactClass, searchParameters);

		if (searchQuery != null) {
			return searchQuery.getSpiraArtifacts();
		}

		searchQuery = new SearchQuery<>(spiraArtifactClass, searchParameters);

		List<S> cachedSpiraArtifacts = _getCachedSpiraArtifacts(
			spiraArtifactClass);

		if (searchQuery.hasDistinctResult()) {
			S distinctSpiraArtifact = null;

			for (S cachedSpiraArtifact : cachedSpiraArtifacts) {
				if (searchQuery.matches(cachedSpiraArtifact)) {
					distinctSpiraArtifact = cachedSpiraArtifact;

					break;
				}
			}

			if (distinctSpiraArtifact == null) {
				JSONObject distinctJSONObject = null;

				for (JSONObject jsonObject : spiraArtifactRequest.get()) {
					if (searchQuery.matches(spiraArtifactClass, jsonObject)) {
						distinctJSONObject = jsonObject;

						break;
					}
				}

				if (distinctJSONObject != null) {
					distinctSpiraArtifact = spiraArtifactCreator.apply(
						distinctJSONObject);
				}
			}

			if (distinctSpiraArtifact == null) {
				return new ArrayList<>();
			}

			searchQuery.addSpiraArtifact(distinctSpiraArtifact);

			cacheSpiraArtifacts(
				Collections.singletonList(distinctSpiraArtifact),
				spiraArtifactClass);

			List<S> searchQuerySpiraArtifacts = searchQuery.getSpiraArtifacts();

			if (!searchQuerySpiraArtifacts.isEmpty()) {
				SearchQuery.cacheSearchQuery(searchQuery);
			}

			return searchQuery.getSpiraArtifacts();
		}

		for (JSONObject jsonObject : spiraArtifactRequest.get()) {
			S spiraArtifact = _getCachedSpiraArtifact(
				spiraArtifactClass, jsonObject);

			if (spiraArtifact == null) {
				spiraArtifact = spiraArtifactCreator.apply(jsonObject);

				cacheSpiraArtifacts(
					Collections.singletonList(spiraArtifact),
					spiraArtifactClass);
			}

			if (searchQuery.matches(spiraArtifact)) {
				searchQuery.addSpiraArtifact(spiraArtifact);
			}
		}

		List<S> searchQuerySpiraArtifacts = searchQuery.getSpiraArtifacts();

		if (!searchQuerySpiraArtifacts.isEmpty()) {
			SearchQuery.cacheSearchQuery(searchQuery);
		}

		cacheSpiraArtifacts(searchQuerySpiraArtifacts, spiraArtifactClass);

		return searchQuerySpiraArtifacts;
	}

	protected static <S extends SpiraArtifact> void removeCachedSpiraArtifacts(
		List<S> spiraArtifacts, Class<S> spiraArtifactClass) {

		List<S> cachedSpiraArtifacts = _getCachedSpiraArtifacts(
			spiraArtifactClass);

		for (SpiraArtifact spiraArtifact : spiraArtifacts) {
			List<SpiraArtifact> foundSpiraArtifacts = new ArrayList<>();

			for (SpiraArtifact cachedSpiraArtifact : cachedSpiraArtifacts) {
				if (spiraArtifact.equals(cachedSpiraArtifact)) {
					foundSpiraArtifacts.add(cachedSpiraArtifact);
				}
			}

			if (foundSpiraArtifacts.isEmpty()) {
				return;
			}

			cachedSpiraArtifacts.removeAll(foundSpiraArtifacts);
		}
	}

	protected static String toDateString(Calendar calendar) {
		return JenkinsResultsParserUtil.combine(
			"/Date(", String.valueOf(calendar.getTimeInMillis()), ")/");
	}

	protected BaseSpiraArtifact(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	protected boolean matches(SearchQuery.SearchParameter... searchParameters) {
		for (SearchQuery.SearchParameter searchParameter : searchParameters) {
			if (!searchParameter.matches(jsonObject)) {
				return false;
			}
		}

		return true;
	}

	protected final JSONObject jsonObject;

	private static <S extends SpiraArtifact> S _getCachedSpiraArtifact(
		Class<S> spiraArtifactClass, JSONObject jsonObject) {

		List<S> cachedSpiraArtifacts = _getCachedSpiraArtifacts(
			spiraArtifactClass);

		for (S cachedSpiraArtifact : cachedSpiraArtifacts) {
			if (!jsonObject.similar(cachedSpiraArtifact.toJSONObject())) {
				continue;
			}

			return cachedSpiraArtifact;
		}

		return null;
	}

	private static <S extends SpiraArtifact> List<S> _getCachedSpiraArtifacts(
		Class<S> spiraArtifactClass) {

		List<SpiraArtifact> spiraArtifacts = _spiraArtifactMap.get(
			spiraArtifactClass);

		if (spiraArtifacts == null) {
			spiraArtifacts = new ArrayList<>();

			_spiraArtifactMap.put(spiraArtifactClass, spiraArtifacts);
		}

		return (List<S>)spiraArtifacts;
	}

	private static Object _getClassField(
		Class<? extends SpiraArtifact> spiraArtifactClass, String fieldName) {

		try {
			Field field = spiraArtifactClass.getDeclaredField(fieldName);

			return field.get(fieldName);
		}
		catch (IllegalAccessException | IllegalArgumentException |
			   NoSuchFieldException exception) {

			throw new RuntimeException(
				"Missing field " + fieldName + " in " +
					spiraArtifactClass.getName(),
				exception);
		}
	}

	private static final Map<Class<?>, List<SpiraArtifact>> _spiraArtifactMap =
		new HashMap<>();

}