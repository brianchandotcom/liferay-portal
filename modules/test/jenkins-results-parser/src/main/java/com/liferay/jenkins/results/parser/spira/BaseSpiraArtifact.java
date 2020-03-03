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

	protected static <S extends SpiraArtifact> List<S> getSpiraArtifacts(
		Class<S> spiraArtifactClass,
		Supplier<List<JSONObject>> spiraArtifactRequest,
		Function<JSONObject, S> spiraArtifactCreator,
		SearchQuery.SearchParameter... searchParameters) {

		SearchQuery<S> searchQuery = SearchQuery.getCachedSearchQuery(
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
					if (distinctSpiraArtifact != null) {
						throw new RuntimeException(
							"Too many results for distinct search");
					}

					distinctSpiraArtifact = cachedSpiraArtifact;
				}
			}

			if (distinctSpiraArtifact == null) {
				JSONObject distinctJSONObject = null;

				for (JSONObject jsonObject : spiraArtifactRequest.get()) {
					if (searchQuery.matches(spiraArtifactClass, jsonObject)) {
						if (distinctJSONObject != null) {
							throw new RuntimeException(
								"Too many results for distinct search");
						}

						distinctJSONObject = jsonObject;
					}
				}

				if (distinctJSONObject != null) {
					distinctSpiraArtifact = spiraArtifactCreator.apply(
						distinctJSONObject);
				}
			}

			cachedSpiraArtifacts.add(distinctSpiraArtifact);

			searchQuery.addSpiraArtifact(distinctSpiraArtifact);

			SearchQuery.cachedSearchQuery(searchQuery);

			return searchQuery.getSpiraArtifacts();
		}

		for (JSONObject jsonObject : spiraArtifactRequest.get()) {
			S spiraArtifact = _getCachedSpiraArtifact(
				spiraArtifactClass, jsonObject);

			if (spiraArtifact == null) {
				spiraArtifact = spiraArtifactCreator.apply(jsonObject);

				cachedSpiraArtifacts.add(spiraArtifact);
			}

			if (searchQuery.matches(spiraArtifact)) {
				searchQuery.addSpiraArtifact(spiraArtifact);
			}
		}

		SearchQuery.cachedSearchQuery(searchQuery);

		return searchQuery.getSpiraArtifacts();
	}

	protected static void removeCachedSpiraArtifacts(
		List<? extends SpiraArtifact> spiraArtifacts) {

		for (SpiraArtifact spiraArtifact : spiraArtifacts) {
			List<SpiraArtifact> cachedSpiraArtifacts =
				_spiraArtifactMap.computeIfAbsent(
					spiraArtifact.getClass(), T -> new ArrayList<>());

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

		List<SpiraArtifact> spiraArtifacts = _spiraArtifactMap.computeIfAbsent(
			spiraArtifactClass, T -> new ArrayList<>());

		List<S> cachedSpiraArtifacts = new ArrayList<>();

		for (SpiraArtifact spiraArtifact : spiraArtifacts) {
			if (!(spiraArtifact.getClass() == spiraArtifactClass)) {
				continue;
			}

			cachedSpiraArtifacts.add((S)spiraArtifact);
		}

		return cachedSpiraArtifacts;
	}

	private static final Map<Class, List<SpiraArtifact>> _spiraArtifactMap =
		new HashMap<>();

}