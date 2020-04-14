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

	protected static <S extends SpiraArtifact> void cacheSpiraArtifact(
		Class<S> spiraArtifactClass, S spiraArtifact) {

		List<S> spiraArtifacts = new ArrayList<>();

		spiraArtifacts.add(spiraArtifact);

		cacheSpiraArtifacts(spiraArtifactClass, spiraArtifacts);
	}

	protected static <S extends SpiraArtifact> void cacheSpiraArtifacts(
		Class<S> spiraArtifactClass, List<S> spiraArtifacts) {

		List<JSONObject> spiraArtifactJSONObjects = new ArrayList<>();

		for (S spiraArtifact : spiraArtifacts) {
			spiraArtifactJSONObjects.add(spiraArtifact.toJSONObject());
		}

		_cacheSpiraArtifactJSONObjects(
			spiraArtifactClass, spiraArtifactJSONObjects);
	}

	protected static <S extends SpiraArtifact> List<S> getSpiraArtifacts(
		Class<S> spiraArtifactClass,
		Supplier<List<JSONObject>> spiraArtifactRequest,
		Function<JSONObject, S> spiraArtifactCreator,
		SearchQuery.SearchParameter... searchParameters) {

		SearchQuery<S> cachedSearchQuery =
			(SearchQuery<S>)SearchQuery.getCachedSearchQuery(
				spiraArtifactClass, searchParameters);

		if (cachedSearchQuery != null) {
			return cachedSearchQuery.getSpiraArtifacts();
		}

		SearchQuery<S> searchQuery = new SearchQuery<>(
			spiraArtifactClass, searchParameters);

		if (searchQuery.hasSearchParameter(getIDKey(spiraArtifactClass)) ||
			searchQuery.hasSearchParameter("IndentLevel")) {

			List<JSONObject> cachedSpiraArtifactJSONObjects =
				_getCachedSpiraArtifactJSONObjects(spiraArtifactClass);

			for (JSONObject jsonObject : cachedSpiraArtifactJSONObjects) {
				if (!searchQuery.matches(spiraArtifactClass, jsonObject)) {
					continue;
				}

				S spiraArtifact = spiraArtifactCreator.apply(jsonObject);

				searchQuery.addSpiraArtifact(spiraArtifact);

				SearchQuery.cacheSearchQuery(searchQuery);

				return searchQuery.getSpiraArtifacts();
			}

			List<JSONObject> spiraArtifactJSONObjects =
				spiraArtifactRequest.get();

			_cacheSpiraArtifactJSONObjects(
				spiraArtifactClass, spiraArtifactJSONObjects);

			for (JSONObject spiraArtifactJSONObject :
					spiraArtifactJSONObjects) {

				if (!searchQuery.matches(
						spiraArtifactClass, spiraArtifactJSONObject)) {

					continue;
				}

				S spiraArtifact = spiraArtifactCreator.apply(
					spiraArtifactJSONObject);

				searchQuery.addSpiraArtifact(spiraArtifact);

				SearchQuery.cacheSearchQuery(searchQuery);

				return searchQuery.getSpiraArtifacts();
			}

			return new ArrayList<>();
		}

		if (!searchQuery.hasSearchParameter("Path")) {
			_cacheSpiraArtifactJSONObjects(
				spiraArtifactClass, spiraArtifactRequest.get());
		}

		List<S> spiraArtifacts = _getCachedSpiraArtifacts(
			spiraArtifactClass, spiraArtifactCreator);

		for (S spiraArtifact : spiraArtifacts) {
			if (searchQuery.matches(spiraArtifact)) {
				searchQuery.addSpiraArtifact(spiraArtifact);
			}
		}

		if (!searchQuery.isEmpty()) {
			SearchQuery.cacheSearchQuery(searchQuery);
		}

		return searchQuery.getSpiraArtifacts();
	}

	protected static void removeCachedSpiraArtifactJSONObjects(
		Class<? extends SpiraArtifact> spiraArtifactClass,
		List<JSONObject> spiraArtifactJSONObjects) {

		Map<Integer, JSONObject> cachedSpiraArtifactJSONObjects =
			_getCachedSpiraArtifactJSONObjectMap(spiraArtifactClass);

		String idKey = getIDKey(spiraArtifactClass);

		for (JSONObject spiraArtifactJSONObject : spiraArtifactJSONObjects) {
			cachedSpiraArtifactJSONObjects.remove(
				spiraArtifactJSONObject.getInt(idKey));
		}
	}

	protected static <S extends SpiraArtifact> void removeCachedSpiraArtifacts(
		Class<S> spiraArtifactClass, List<S> spiraArtifacts) {

		List<JSONObject> spiraArtifactJSONObjects = new ArrayList<>();

		for (S spiraArtifact : spiraArtifacts) {
			spiraArtifactJSONObjects.add(spiraArtifact.toJSONObject());
		}

		removeCachedSpiraArtifactJSONObjects(
			spiraArtifactClass, spiraArtifactJSONObjects);
	}

	protected static String toDateString(Calendar calendar) {
		return JenkinsResultsParserUtil.combine(
			"/Date(", String.valueOf(calendar.getTimeInMillis()), ")/");
	}

	protected BaseSpiraArtifact(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	protected final JSONObject jsonObject;

	private static void _cacheSpiraArtifactJSONObjects(
		Class<? extends SpiraArtifact> spiraArtifactClass,
		List<JSONObject> spiraArtifactJSONObjects) {

		Map<Integer, JSONObject> cachedSpiraArtifactJSONObjects =
			_getCachedSpiraArtifactJSONObjectMap(spiraArtifactClass);

		String idKey = getIDKey(spiraArtifactClass);

		for (JSONObject spiraArtifactJSONObject : spiraArtifactJSONObjects) {
			cachedSpiraArtifactJSONObjects.put(
				spiraArtifactJSONObject.getInt(idKey), spiraArtifactJSONObject);
		}
	}

	private static Map<Integer, JSONObject>
		_getCachedSpiraArtifactJSONObjectMap(
			Class<? extends SpiraArtifact> spiraArtifactClass) {

		Map<Integer, JSONObject> spiraArtifactJSONObjects =
			_spiraArtifactJSONObjectsMap.get(spiraArtifactClass);

		if (spiraArtifactJSONObjects == null) {
			spiraArtifactJSONObjects = new HashMap<>();

			_spiraArtifactJSONObjectsMap.put(
				spiraArtifactClass, spiraArtifactJSONObjects);
		}

		return spiraArtifactJSONObjects;
	}

	private static List<JSONObject> _getCachedSpiraArtifactJSONObjects(
		Class<? extends SpiraArtifact> spiraArtifactClass) {

		Map<Integer, JSONObject> spiraArtifactJSONObjects =
			_getCachedSpiraArtifactJSONObjectMap(spiraArtifactClass);

		return new ArrayList<>(spiraArtifactJSONObjects.values());
	}

	private static <S extends SpiraArtifact> List<S> _getCachedSpiraArtifacts(
		Class<S> spiraArtifactClass,
		Function<JSONObject, S> spiraArtifactCreator) {

		List<S> cachedSpiraArtifacts = new ArrayList<>();

		for (JSONObject jsonObject :
				_getCachedSpiraArtifactJSONObjects(spiraArtifactClass)) {

			cachedSpiraArtifacts.add(spiraArtifactCreator.apply(jsonObject));
		}

		return cachedSpiraArtifacts;
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

	private static final Map<Class<?>, Map<Integer, JSONObject>>
		_spiraArtifactJSONObjectsMap = new HashMap<>();

}