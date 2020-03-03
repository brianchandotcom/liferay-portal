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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SearchResult<T extends SpiraArtifact> {

	public static class SearchParameter {

		public SearchParameter(String name, Object value) {
			_name = name;
			_value = value;
		}

		@Override
		public boolean equals(Object object) {
			if (object instanceof SearchParameter) {
				SearchParameter otherSearchParameter = (SearchParameter)object;

				if (_name.equals(otherSearchParameter.getName()) &&
					_value.equals(otherSearchParameter.getValue())) {

					return true;
				}

				return false;
			}

			return super.equals(object);
		}

		public String getName() {
			return _name;
		}

		public Object getValue() {
			return _value;
		}

		@Override
		public int hashCode() {
			JSONObject filterJSONObject = toFilterJSONObject();

			return filterJSONObject.hashCode();
		}

		public boolean matches(JSONObject jsonObject) {
			if (!Objects.equals(getValue(), jsonObject.get(getName()))) {
				return false;
			}

			return true;
		}

		public JSONObject toFilterJSONObject() {
			JSONObject filterJSONObject = new JSONObject();

			filterJSONObject.put("PropertyName", _name);

			if (_value instanceof Integer) {
				Integer intValue = (Integer)_value;

				filterJSONObject.put("IntValue", intValue);
			}
			else if (_value instanceof String) {
				String stringValue = (String)_value;

				stringValue = stringValue.replaceAll("\\[", "[[]");

				filterJSONObject.put("StringValue", stringValue);
			}
			else {
				throw new RuntimeException("Invalid value type");
			}

			return filterJSONObject;
		}

		private final String _name;
		private final Object _value;

	}

	protected static void cachedSearchResult(SearchResult searchResult) {
		List<SearchResult> cachedSearchResults =
			_searchResultsMap.computeIfAbsent(
				searchResult._spiraArtifactClass, T -> new ArrayList<>());

		cachedSearchResults.add(searchResult);
	}

	protected static <T extends SpiraArtifact> SearchResult<T>
		getCachedSearchResult(
			Class<T> spiraArtifactClass, SearchParameter... searchParameters) {

		List<SearchResult> cachedSearchResults =
			_searchResultsMap.computeIfAbsent(
				spiraArtifactClass, T -> new ArrayList<>());

		for (SearchResult cachedSearchResult : cachedSearchResults) {
			JSONArray filterJSONArray = new JSONArray();

			for (SearchParameter searchParameter : searchParameters) {
				filterJSONArray.put(searchParameter.toFilterJSONObject());
			}

			if (filterJSONArray.similar(
					cachedSearchResult.toFilterJSONArray())) {

				return cachedSearchResult;
			}
		}

		return null;
	}

	protected SearchResult(
		Class<T> spiraArtifactClass, SearchParameter... searchParameters) {

		_spiraArtifactClass = spiraArtifactClass;
		_searchParameters = searchParameters;
	}

	protected void addSpiraArtifact(T spiraArtifact) {
		_spiraArtifacts.add(spiraArtifact);
	}

	protected List<T> getSpiraArtifacts() {
		return _spiraArtifacts;
	}

	protected boolean hasDistinctResults() {
		for (SearchParameter searchParameter : _searchParameters) {
			String searchParameterName = searchParameter.getName();

			if (searchParameterName.equals(
					BaseSpiraArtifact.getIDKey(_spiraArtifactClass))) {

				return true;
			}

			if (searchParameterName.equals("IndentLevel")) {
				return true;
			}
		}

		return false;
	}

	protected boolean matches(SpiraArtifact spiraArtifact) {
		return matches(spiraArtifact.getClass(), spiraArtifact.toJSONObject());
	}

	protected boolean matches(
		Class<? extends SpiraArtifact> spiraArtifactClass,
		JSONObject jsonObject) {

		if (spiraArtifactClass != _spiraArtifactClass) {
			return false;
		}

		for (SearchParameter searchParameter : _searchParameters) {
			if (!searchParameter.matches(jsonObject)) {
				return false;
			}
		}

		return true;
	}

	protected JSONArray toFilterJSONArray() {
		JSONArray filterJSONArray = new JSONArray();

		for (SearchParameter searchParameter : _searchParameters) {
			filterJSONArray.put(searchParameter.toFilterJSONObject());
		}

		return filterJSONArray;
	}

	private static final Map<Class, List<SearchResult>> _searchResultsMap =
		new HashMap<>();

	private final SearchParameter[] _searchParameters;
	private final Class<T> _spiraArtifactClass;
	private final List<T> _spiraArtifacts = new ArrayList<>();

}