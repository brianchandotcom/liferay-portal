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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.lang.reflect.Field;

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

	protected static <S extends SpiraArtifact> void cacheSpiraArtifact(
		Class<S> spiraArtifactClass, S spiraArtifact) {

		_putIDSpiraArtifact(spiraArtifactClass, spiraArtifact);

		if (spiraArtifact instanceof PathSpiraArtifact) {
			PathSpiraArtifact pathSpiraArtifact =
				(PathSpiraArtifact)spiraArtifact;

			_putPathSpiraArtifact(spiraArtifactClass, pathSpiraArtifact);
		}

		if (spiraArtifact instanceof IndentLevelSpiraArtifact) {
			IndentLevelSpiraArtifact indentLevelSpiraArtifact =
				(IndentLevelSpiraArtifact)spiraArtifact;

			_putIndentLevelSpiraArtifact(
				spiraArtifactClass, indentLevelSpiraArtifact);
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
			Map<Integer, SpiraArtifact> idSpiraArtifactsMap =
				_getIDSpiraArtifactsMap(spiraArtifactClass);

			SearchQuery.SearchParameter searchParameter =
				searchQuery.getSearchParameter(idKey);

			Integer id = (Integer)searchParameter.getValue();

			if (!idSpiraArtifactsMap.containsKey(id)) {
				for (JSONObject responseJSONObject :
						spiraArtifactSupplier.get()) {

					spiraArtifactCreator.apply(responseJSONObject);
				}
			}

			if (idSpiraArtifactsMap.containsKey(id)) {
				S spiraArtifact = (S)idSpiraArtifactsMap.get(id);

				searchQuery.addSpiraArtifact(spiraArtifact);

				SearchQuery.cacheSearchQuery(searchQuery);
			}

			return searchQuery.getSpiraArtifacts();
		}

		if (searchQuery.hasSearchParameter("IndentLevel")) {
			SearchQuery.SearchParameter searchParameter =
				searchQuery.getSearchParameter("IndentLevel");

			String indentLevel = (String)searchParameter.getValue();

			Map<String, IndentLevelSpiraArtifact> indentLevelSpiraArtifactsMap =
				_getIndentLevelSpiraArtifactsMap(spiraArtifactClass);

			if (!indentLevelSpiraArtifactsMap.containsKey(indentLevel)) {
				for (JSONObject responseJSONObject :
						spiraArtifactSupplier.get()) {

					spiraArtifactCreator.apply(responseJSONObject);
				}
			}

			if (indentLevelSpiraArtifactsMap.containsKey(indentLevel)) {
				S spiraArtifact = (S)indentLevelSpiraArtifactsMap.get(
					indentLevel);

				searchQuery.addSpiraArtifact(spiraArtifact);

				SearchQuery.cacheSearchQuery(searchQuery);
			}

			return searchQuery.getSpiraArtifacts();
		}

		if (searchQuery.hasSearchParameter("Path")) {
			SearchQuery.SearchParameter searchParameter =
				searchQuery.getSearchParameter("Path");

			String path = (String)searchParameter.getValue();

			Map<String, PathSpiraArtifact> pathSpiraArtifactsMap =
				_getPathSpiraArtifactsMap(spiraArtifactClass);

			if (pathSpiraArtifactsMap.containsKey(path)) {
				S spiraArtifact = (S)pathSpiraArtifactsMap.get(path);

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

		for (S spiraArtifact : spiraArtifacts) {
			_removeIDSpiraArtifact(spiraArtifactClass, spiraArtifact);

			if (spiraArtifact instanceof PathSpiraArtifact) {
				_removePathSpiraArtifact(
					spiraArtifactClass, (PathSpiraArtifact)spiraArtifact);
			}

			if (spiraArtifact instanceof IndentLevelSpiraArtifact) {
				_removeIndentLevelSpiraArtifact(
					spiraArtifactClass,
					(IndentLevelSpiraArtifact)spiraArtifact);
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

	protected transient JSONObject jsonObject;

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

	private static Map<Integer, SpiraArtifact> _getIDSpiraArtifactsMap(
		Class<? extends SpiraArtifact> spiraArtifactClass) {

		synchronized (_idSpiraArtifactsMap) {
			Map<Integer, SpiraArtifact> spiraArtifactsMap =
				_idSpiraArtifactsMap.get(spiraArtifactClass);

			if (spiraArtifactsMap == null) {
				spiraArtifactsMap = Collections.synchronizedMap(
					new HashMap<Integer, SpiraArtifact>());

				_idSpiraArtifactsMap.put(spiraArtifactClass, spiraArtifactsMap);
			}

			return spiraArtifactsMap;
		}
	}

	private static Map<String, IndentLevelSpiraArtifact>
		_getIndentLevelSpiraArtifactsMap(
			Class<? extends SpiraArtifact> spiraArtifactClass) {

		synchronized (_indentLevelSpiraArtifactsMap) {
			Map<String, IndentLevelSpiraArtifact> spiraArtifactsMap =
				_indentLevelSpiraArtifactsMap.get(spiraArtifactClass);

			if (spiraArtifactsMap == null) {
				spiraArtifactsMap = Collections.synchronizedMap(
					new HashMap<String, IndentLevelSpiraArtifact>());

				_indentLevelSpiraArtifactsMap.put(
					spiraArtifactClass, spiraArtifactsMap);
			}

			return spiraArtifactsMap;
		}
	}

	private static Map<String, PathSpiraArtifact> _getPathSpiraArtifactsMap(
		Class<? extends SpiraArtifact> spiraArtifactClass) {

		synchronized (_pathSpiraArtifactsMap) {
			Map<String, PathSpiraArtifact> spiraArtifactsMap =
				_pathSpiraArtifactsMap.get(spiraArtifactClass);

			if (spiraArtifactsMap == null) {
				spiraArtifactsMap = Collections.synchronizedMap(
					new HashMap<String, PathSpiraArtifact>());

				_pathSpiraArtifactsMap.put(
					spiraArtifactClass, spiraArtifactsMap);
			}

			return spiraArtifactsMap;
		}
	}

	private static void _putIDSpiraArtifact(
		Class<? extends SpiraArtifact> spiraArtifactClass,
		SpiraArtifact spiraArtifact) {

		Map<Integer, SpiraArtifact> idSpiraArtifactsMap =
			_getIDSpiraArtifactsMap(spiraArtifactClass);

		idSpiraArtifactsMap.put(spiraArtifact.getID(), spiraArtifact);
	}

	private static void _putIndentLevelSpiraArtifact(
		Class<? extends SpiraArtifact> spiraArtifactClass,
		IndentLevelSpiraArtifact indentLevelSpiraArtifact) {

		Map<String, IndentLevelSpiraArtifact> indentLevelSpiraArtifactsMap =
			_getIndentLevelSpiraArtifactsMap(spiraArtifactClass);

		indentLevelSpiraArtifactsMap.put(
			indentLevelSpiraArtifact.getIndentLevel(),
			indentLevelSpiraArtifact);
	}

	private static void _putPathSpiraArtifact(
		Class<? extends SpiraArtifact> spiraArtifactClass,
		PathSpiraArtifact pathSpiraArtifact) {

		Map<String, PathSpiraArtifact> pathSpiraArtifactsMap =
			_getPathSpiraArtifactsMap(spiraArtifactClass);

		pathSpiraArtifactsMap.put(
			pathSpiraArtifact.getPath(), pathSpiraArtifact);
	}

	private static void _removeIDSpiraArtifact(
		Class<? extends SpiraArtifact> spiraArtifactClass,
		SpiraArtifact spiraArtifact) {

		Map<Integer, SpiraArtifact> idSpiraArtifactsMap =
			_getIDSpiraArtifactsMap(spiraArtifactClass);

		idSpiraArtifactsMap.remove(spiraArtifact.getID());
	}

	private static void _removeIndentLevelSpiraArtifact(
		Class<? extends SpiraArtifact> spiraArtifactClass,
		IndentLevelSpiraArtifact indentLevelSpiraArtifact) {

		Map<String, IndentLevelSpiraArtifact> indentLevelSpiraArtifactsMap =
			_getIndentLevelSpiraArtifactsMap(spiraArtifactClass);

		indentLevelSpiraArtifactsMap.remove(
			indentLevelSpiraArtifact.getIndentLevel());
	}

	private static void _removePathSpiraArtifact(
		Class<? extends SpiraArtifact> spiraArtifactClass,
		PathSpiraArtifact pathSpiraArtifact) {

		Map<String, PathSpiraArtifact> pathSpiraArtifactsMap =
			_getPathSpiraArtifactsMap(spiraArtifactClass);

		pathSpiraArtifactsMap.remove(pathSpiraArtifact.getPath());
	}

	private void readObject(ObjectInputStream in)
		throws ClassNotFoundException, IOException {

		in.defaultReadObject();

		jsonObject = new JSONObject(in.readUTF());
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();

		out.writeUTF(jsonObject.toString());
	}

	private static final Map<Class<?>, Map<Integer, SpiraArtifact>>
		_idSpiraArtifactsMap = Collections.synchronizedMap(
			new HashMap<Class<?>, Map<Integer, SpiraArtifact>>());
	private static final Map<Class<?>, Map<String, IndentLevelSpiraArtifact>>
		_indentLevelSpiraArtifactsMap = Collections.synchronizedMap(
			new HashMap<Class<?>, Map<String, IndentLevelSpiraArtifact>>());
	private static final Map<Class<?>, Map<String, PathSpiraArtifact>>
		_pathSpiraArtifactsMap = Collections.synchronizedMap(
			new HashMap<Class<?>, Map<String, PathSpiraArtifact>>());

}