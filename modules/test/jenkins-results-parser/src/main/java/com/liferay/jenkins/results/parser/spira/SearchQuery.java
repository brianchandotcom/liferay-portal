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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SearchQuery<T extends SpiraArtifact> {

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof SearchQuery)) {
			return false;
		}

		SearchQuery searchQuery = (SearchQuery)object;

		return matches(searchQuery.getSearchParameters());
	}

	public SearchParameter[] getSearchParameters() {
		return _searchParameters;
	}

	public boolean matches(SearchParameter[] searchParameters) {
		JSONArray jsonArray = new JSONArray();

		for (SearchParameter searchParameter : searchParameters) {
			jsonArray.put(searchParameter.toJSONObject());
		}

		if (jsonArray.similar(toJSONArray())) {
			return true;
		}

		return false;
	}

	public static class SearchParameter {

		public SearchParameter(
			SpiraCustomPropertyValue spiraCustomPropertyValue) {

			SpiraCustomProperty spiraCustomProperty =
				spiraCustomPropertyValue.getSpiraCustomProperty();

			_name = spiraCustomProperty.getName();

			_value = spiraCustomPropertyValue;
		}

		public SearchParameter(String name, Object value) {
			_name = name;
			_value = value;
		}

		@Override
		public boolean equals(Object object) {
			if (!(object instanceof SearchParameter)) {
				return false;
			}

			SearchParameter searchParameter = (SearchParameter)object;

			JSONObject jsonObject = searchParameter.toJSONObject();

			if (jsonObject.similar(toJSONObject())) {
				return true;
			}

			return false;
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
			if (_value instanceof SpiraCustomPropertyValue) {
				SpiraCustomPropertyValue spiraCustomPropertyValue =
					(SpiraCustomPropertyValue)_value;

				int propertyNumber =
					spiraCustomPropertyValue.getPropertyNumber();

				JSONArray customPropertiesJSONArray = jsonObject.getJSONArray(
					"CustomProperties");

				for (int i = 0; i < customPropertiesJSONArray.length(); i++) {
					JSONObject customPropertyJSONObject =
						customPropertiesJSONArray.getJSONObject(i);

					if (propertyNumber != customPropertyJSONObject.getInt(
							"PropertyNumber")) {

						continue;
					}

					if (spiraCustomPropertyValue.matchesJSONObject(
							customPropertyJSONObject)) {

						return true;
					}
				}

				return false;
			}

			if (!Objects.equals(getValue(), jsonObject.get(getName()))) {
				return false;
			}

			return true;
		}

		public JSONObject toFilterJSONObject() {
			if ((_value instanceof String) && _name.equals("Path")) {
				String stringValue = (String)_value;

				String[] stringValues = stringValue.split("(?<!\\\\)/");

				stringValue = stringValues[stringValues.length - 1];

				stringValue = stringValue.replaceAll("\\[", "[[]");

				JSONObject filterJSONObject = new JSONObject();

				filterJSONObject.put("PropertyName", "Name");
				filterJSONObject.put("StringValue", stringValue);

				return filterJSONObject;
			}

			return toJSONObject();
		}

		public JSONObject toJSONObject() {
			if (_value instanceof Integer) {
				Integer intValue = (Integer)_value;

				JSONObject jsonObject = new JSONObject();

				jsonObject.put("IntValue", intValue);
				jsonObject.put("PropertyName", _name);

				return jsonObject;
			}

			if (_value instanceof SpiraCustomPropertyValue) {
				SpiraCustomPropertyValue spiraCustomPropertyValue =
					(SpiraCustomPropertyValue)_value;

				return spiraCustomPropertyValue.getFilterJSONObject();
			}

			if (_value instanceof String) {
				JSONObject jsonObject = new JSONObject();

				String stringValue = (String)_value;

				stringValue = stringValue.replaceAll("\\[", "[[]");

				jsonObject.put("PropertyName", _name);
				jsonObject.put("StringValue", stringValue);

				return jsonObject;
			}

			throw new RuntimeException("Invalid value type");
		}

		private final String _name;
		private final Object _value;

	}

	protected static void cacheSearchQuery(SearchQuery<?> searchQuery) {
		List<SearchQuery<?>> cachedSearchQueries = _getCachedSearchQueries(
			searchQuery._spiraArtifactClass);

		cachedSearchQueries.add(searchQuery);
	}

	protected static void clearSearchQueries(
		Class<? extends SpiraArtifact> spiraArtifactClass) {

		List<SearchQuery<?>> cachedSearchQueries = _getCachedSearchQueries(
			spiraArtifactClass);

		cachedSearchQueries.clear();
	}

	protected static SearchQuery<?> getCachedSearchQuery(
		Class<?> spiraArtifactClass, SearchParameter... searchParameters) {

		List<SearchQuery<?>> cachedSearchQueries = _getCachedSearchQueries(
			spiraArtifactClass);

		synchronized (cachedSearchQueries) {
			for (SearchQuery<?> cachedSearchQuery : cachedSearchQueries) {
				if (cachedSearchQuery.matches(searchParameters)) {
					return cachedSearchQuery;
				}
			}
		}

		return null;
	}

	protected SearchQuery(
		Class<T> spiraArtifactClass, SearchParameter... searchParameters) {

		_spiraArtifactClass = spiraArtifactClass;
		_searchParameters = searchParameters;
	}

	protected void addSpiraArtifact(T spiraArtifact) {
		_spiraArtifacts.add(spiraArtifact);
	}

	protected SearchParameter getSearchParameter(String searchParameterName) {
		for (SearchParameter searchParameter : _searchParameters) {
			if (searchParameterName.equals(searchParameter.getName())) {
				return searchParameter;
			}
		}

		return null;
	}

	protected List<T> getSpiraArtifacts() {
		return _spiraArtifacts;
	}

	protected boolean hasSearchParameter(String searchParameterName) {
		for (SearchParameter searchParameter : _searchParameters) {
			if (searchParameterName.equals(searchParameter.getName())) {
				return true;
			}
		}

		return false;
	}

	protected boolean isEmpty() {
		List<T> spiraArtifacts = getSpiraArtifacts();

		return spiraArtifacts.isEmpty();
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

	protected boolean matches(SpiraArtifact spiraArtifact) {
		return matches(spiraArtifact.getClass(), spiraArtifact.toJSONObject());
	}

	protected JSONArray toFilterJSONArray() {
		JSONArray filterJSONArray = new JSONArray();

		for (SearchParameter searchParameter : _searchParameters) {
			filterJSONArray.put(searchParameter.toFilterJSONObject());
		}

		return filterJSONArray;
	}

	protected JSONArray toJSONArray() {
		JSONArray jsonArray = new JSONArray();

		for (SearchParameter searchParameter : _searchParameters) {
			jsonArray.put(searchParameter.toJSONObject());
		}

		return jsonArray;
	}

	private static List<SearchQuery<?>> _getCachedSearchQueries(
		Class<?> spiraArtifactClass) {

		synchronized (_searchQueriesMap) {
			List<SearchQuery<?>> cachedSearchQueries = _searchQueriesMap.get(
				spiraArtifactClass);

			if (cachedSearchQueries == null) {
				cachedSearchQueries = Collections.synchronizedList(
					new ArrayList<SearchQuery<?>>());

				_searchQueriesMap.put(spiraArtifactClass, cachedSearchQueries);
			}

			return cachedSearchQueries;
		}
	}

	private static final Map<Class<?>, List<SearchQuery<?>>> _searchQueriesMap =
		Collections.synchronizedMap(
			new HashMap<Class<?>, List<SearchQuery<?>>>());

	private final SearchParameter[] _searchParameters;
	private final Class<T> _spiraArtifactClass;
	private final List<T> _spiraArtifacts = new ArrayList<>();

}