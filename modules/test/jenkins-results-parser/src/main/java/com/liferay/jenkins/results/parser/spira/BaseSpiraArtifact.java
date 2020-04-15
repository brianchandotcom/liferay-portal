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

		Map<Integer, SpiraArtifact> idMap = _getIDMap(spiraArtifactClass);
		Map<String, PathSpiraArtifact> pathMap = _getPathMap(
			spiraArtifactClass);
		Map<String, IndentLevelSpiraArtifact> indentLevelMap =
			_getIndentLevelMap(spiraArtifactClass);

		idMap.put(spiraArtifact.getID(), spiraArtifact);

		if (spiraArtifact instanceof PathSpiraArtifact) {
			PathSpiraArtifact pathSpiraArtifact =
				(PathSpiraArtifact)spiraArtifact;

			pathMap.put(pathSpiraArtifact.getPath(), pathSpiraArtifact);
		}

		if (spiraArtifact instanceof IndentLevelSpiraArtifact) {
			IndentLevelSpiraArtifact indentLevelSpiraArtifact =
				(IndentLevelSpiraArtifact)spiraArtifact;

			indentLevelMap.put(
				indentLevelSpiraArtifact.getIndentLevel(),
				indentLevelSpiraArtifact);
		}
	}

	protected static <S extends SpiraArtifact> List<S> getSpiraArtifacts(
		Class<S> spiraArtifactClass,
		Supplier<List<JSONObject>> spiraArtifactSupplier,
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

		String idKey = getIDKey(spiraArtifactClass);

		if (searchQuery.hasSearchParameter(idKey)) {
			Map<Integer, SpiraArtifact> idMap = _getIDMap(spiraArtifactClass);

			SearchQuery.SearchParameter searchParameter =
				searchQuery.getSearchParameter(idKey);

			Integer id = (Integer)searchParameter.getValue();

			if (!idMap.containsKey(id)) {
				for (JSONObject responseJSONObject :
						spiraArtifactSupplier.get()) {

					spiraArtifactCreator.apply(responseJSONObject);
				}
			}

			if (idMap.containsKey(id)) {
				S spiraArtifact = (S)idMap.get(id);

				searchQuery.addSpiraArtifact(spiraArtifact);

				SearchQuery.cacheSearchQuery(searchQuery);
			}

			return searchQuery.getSpiraArtifacts();
		}

		if (searchQuery.hasSearchParameter("IndentLevel")) {
			SearchQuery.SearchParameter searchParameter =
				searchQuery.getSearchParameter("IndentLevel");

			String indentLevel = (String)searchParameter.getValue();

			Map<String, IndentLevelSpiraArtifact> indentLevelMap =
				_getIndentLevelMap(spiraArtifactClass);

			if (!indentLevelMap.containsKey(indentLevel)) {
				for (JSONObject responseJSONObject :
						spiraArtifactSupplier.get()) {

					spiraArtifactCreator.apply(responseJSONObject);
				}
			}

			if (indentLevelMap.containsKey(indentLevel)) {
				S spiraArtifact = (S)indentLevelMap.get(indentLevel);

				searchQuery.addSpiraArtifact(spiraArtifact);

				SearchQuery.cacheSearchQuery(searchQuery);
			}

			return searchQuery.getSpiraArtifacts();
		}

		if (searchQuery.hasSearchParameter("Path")) {
			SearchQuery.SearchParameter searchParameter =
				searchQuery.getSearchParameter("Path");

			String path = (String)searchParameter.getValue();

			Map<String, PathSpiraArtifact> pathMap = _getPathMap(
				spiraArtifactClass);

			if (pathMap.containsKey(path)) {
				S spiraArtifact = (S)pathMap.get(path);

				searchQuery.addSpiraArtifact(spiraArtifact);

				SearchQuery.cacheSearchQuery(searchQuery);
			}

			return searchQuery.getSpiraArtifacts();
		}

		for (JSONObject responseJSONObject : spiraArtifactSupplier.get()) {
			S spiraArtifact = spiraArtifactCreator.apply(responseJSONObject);

			if (searchQuery.matches(spiraArtifact)) {
				searchQuery.addSpiraArtifact(spiraArtifact);
			}
		}

		if (!searchQuery.isEmpty()) {
			SearchQuery.cacheSearchQuery(searchQuery);
		}

		return searchQuery.getSpiraArtifacts();
	}

	protected static <S extends SpiraArtifact> void removeCachedSpiraArtifacts(
		Class<S> spiraArtifactClass, List<S> spiraArtifacts) {

		Map<Integer, SpiraArtifact> idSpiraArtifactMap = _getIDMap(
			spiraArtifactClass);
		Map<String, PathSpiraArtifact> pathSpiraArtifactMap = _getPathMap(
			spiraArtifactClass);
		Map<String, IndentLevelSpiraArtifact> indentLevelSpiraArtifactMap =
			_getIndentLevelMap(spiraArtifactClass);

		for (S spiraArtifact : spiraArtifacts) {
			idSpiraArtifactMap.remove(spiraArtifact.getID());

			if (spiraArtifact instanceof PathSpiraArtifact) {
				PathSpiraArtifact pathSpiraArtifact =
					(PathSpiraArtifact)spiraArtifact;

				pathSpiraArtifactMap.remove(pathSpiraArtifact.getPath());
			}

			if (spiraArtifact instanceof IndentLevelSpiraArtifact) {
				IndentLevelSpiraArtifact indentLevelSpiraArtifact =
					(IndentLevelSpiraArtifact)spiraArtifact;

				indentLevelSpiraArtifactMap.remove(
					indentLevelSpiraArtifact.getIndentLevel());
			}
		}
	}

	protected static String toDateString(Calendar calendar) {
		return JenkinsResultsParserUtil.combine(
			"/Date(", String.valueOf(calendar.getTimeInMillis()), ")/");
	}

	protected BaseSpiraArtifact(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	protected final JSONObject jsonObject;

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

	private static Map<Integer, SpiraArtifact> _getIDMap(
		Class<? extends SpiraArtifact> spiraArtifactClass) {

		Map<Integer, SpiraArtifact> spiraArtifactMap = _spiraArtifactIDMap.get(
			spiraArtifactClass);

		if (spiraArtifactMap == null) {
			spiraArtifactMap = new HashMap<>();

			_spiraArtifactIDMap.put(spiraArtifactClass, spiraArtifactMap);
		}

		return spiraArtifactMap;
	}

	private static Map<String, IndentLevelSpiraArtifact> _getIndentLevelMap(
		Class<? extends SpiraArtifact> spiraArtifactClass) {

		Map<String, IndentLevelSpiraArtifact> spiraArtifacts =
			_spiraArtifactIndentLevelMap.get(spiraArtifactClass);

		if (spiraArtifacts == null) {
			spiraArtifacts = new HashMap<>();

			_spiraArtifactIndentLevelMap.put(
				spiraArtifactClass, spiraArtifacts);
		}

		return spiraArtifacts;
	}

	private static Map<String, PathSpiraArtifact> _getPathMap(
		Class<? extends SpiraArtifact> spiraArtifactClass) {

		Map<String, PathSpiraArtifact> spiraArtifactJSONObjects =
			_spiraArtifactPathMap.get(spiraArtifactClass);

		if (spiraArtifactJSONObjects == null) {
			spiraArtifactJSONObjects = new HashMap<>();

			_spiraArtifactPathMap.put(
				spiraArtifactClass, spiraArtifactJSONObjects);
		}

		return spiraArtifactJSONObjects;
	}

	private static final Map<Class<?>, Map<Integer, SpiraArtifact>>
		_spiraArtifactIDMap = new HashMap<>();
	private static final Map<Class<?>, Map<String, IndentLevelSpiraArtifact>>
		_spiraArtifactIndentLevelMap = new HashMap<>();
	private static final Map<Class<?>, Map<String, PathSpiraArtifact>>
		_spiraArtifactPathMap = new HashMap<>();

}