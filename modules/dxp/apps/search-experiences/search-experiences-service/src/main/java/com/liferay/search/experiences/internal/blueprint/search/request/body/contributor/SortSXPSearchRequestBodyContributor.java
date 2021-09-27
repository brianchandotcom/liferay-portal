/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.blueprint.search.request.body.contributor;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.geolocation.DistanceUnit;
import com.liferay.portal.search.geolocation.GeoBuilders;
import com.liferay.portal.search.geolocation.GeoDistanceType;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.script.ScriptBuilder;
import com.liferay.portal.search.script.ScriptType;
import com.liferay.portal.search.script.Scripts;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.GeoDistanceSort;
import com.liferay.portal.search.sort.NestedSort;
import com.liferay.portal.search.sort.ScriptSort;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.sort.SortMode;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterData;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterParser;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;
import com.liferay.search.experiences.rest.dto.v1_0.SortConfiguration;

import java.util.Iterator;
import java.util.Objects;

/**
 * @author Petteri Karttunen
 */
public class SortSXPSearchRequestBodyContributor
	implements SXPSearchRequestBodyContributor {

	public SortSXPSearchRequestBodyContributor(
		GeoBuilders geoBuilders, Queries queries, Scripts scripts,
		Sorts sorts) {

		_geoBuilders = geoBuilders;
		_queries = queries;
		_scripts = scripts;
		_sorts = sorts;
	}

	@Override
	public void contribute(
		SearchRequestBuilder searchRequestBuilder, SXPBlueprint sxpBlueprint,
		SXPParameterData sxpParameterData) {

		if (_hasSorts(searchRequestBuilder)) {
			return;
		}

		Configuration configuration = sxpBlueprint.getConfiguration();

		SortConfiguration sortConfiguration =
			configuration.getSortConfiguration();

		if (sortConfiguration == null) {
			return;
		}

		JSONArray jsonArray = (JSONArray)sortConfiguration.getSortsJSONArray();

		for (int i = 0; i < jsonArray.length(); i++) {
			searchRequestBuilder.addSort(
				_toSort(jsonArray.get(i), sxpParameterData));
		}
	}

	@Override
	public String getName() {
		return "sort";
	}

	private void _addGeoLocationPoint(
		GeoDistanceSort geoDistanceSort, JSONArray jsonArray) {

		geoDistanceSort.addGeoLocationPoints(
			_geoBuilders.geoLocationPoint(
				jsonArray.getDouble(0), jsonArray.getDouble(1)));
	}

	private Script _getScript(JSONObject jsonObject) {
		Object object = jsonObject.get("script");

		if (object instanceof JSONObject) {
			return _toScript((JSONObject)object);
		}
		else if (object instanceof String) {
			return _toScript((String)object);
		}

		throw new IllegalArgumentException();
	}

	private boolean _hasSorts(SearchRequestBuilder searchRequestBuilder) {
		return searchRequestBuilder.withSearchContextGet(
			searchContext -> {
				SearchRequest searchRequest =
					(SearchRequest)searchContext.getAttribute("search.request");

				return !ListUtil.isEmpty(searchRequest.getSorts());
			});
	}

	private void _processGeoDistanceType(
		GeoDistanceSort geoDistanceSort, JSONObject jsonObject) {

		if (!jsonObject.has("distance_type")) {
			return;
		}

		geoDistanceSort.setGeoDistanceType(
			GeoDistanceType.valueOf(
				StringUtil.toUpperCase(jsonObject.getString("distance_type"))));
	}

	private void _processGeoDistanceUnit(
		GeoDistanceSort geoDistanceSort, JSONObject jsonObject) {

		if (!jsonObject.has("unit")) {
			return;
		}

		String unit = StringUtil.toLowerCase(jsonObject.getString("unit"));

		for (DistanceUnit distanceUnit : DistanceUnit.values()) {
			if (Objects.equals(distanceUnit.getUnit(), unit)) {
				geoDistanceSort.setDistanceUnit(distanceUnit);

				return;
			}
		}
	}

	private void _processGeoLocationPoints(
		GeoDistanceSort geoDistanceSort, Object object) {

		if (object instanceof JSONObject) {
			JSONObject jsonObject = (JSONObject)object;

			geoDistanceSort.addGeoLocationPoints(
				_geoBuilders.geoLocationPoint(
					jsonObject.getInt("lat"), jsonObject.getInt("lon")));
		}
		else if (object instanceof JSONArray) {
			JSONArray jsonArray = (JSONArray)object;

			if ((jsonArray.length() != 2) ||
				(jsonArray.get(0) instanceof JSONArray)) {

				for (int i = 0; i < jsonArray.length(); i++) {
					_addGeoLocationPoint(
						geoDistanceSort, jsonArray.getJSONArray(i));
				}
			}
			else {
				_addGeoLocationPoint(geoDistanceSort, jsonArray);
			}
		}
		else if (object instanceof String) {
			geoDistanceSort.addGeoLocationPoints(
				_geoBuilders.geoLocationPoint((String)object));
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	private void _processGeoSortMode(
		GeoDistanceSort geoDistanceSort, JSONObject jsonObject) {

		String mode = jsonObject.getString("mode");

		if (Validator.isNotNull(mode)) {
			geoDistanceSort.setSortMode(
				SortMode.valueOf(StringUtil.toUpperCase(mode)));
		}
	}

	private void _processScriptOptions(
		JSONObject jsonObject, ScriptBuilder scriptBuilder) {

		if (jsonObject != null) {
			for (String key : jsonObject.keySet()) {
				scriptBuilder.putOption(key, jsonObject.getString(key));
			}
		}
	}

	private void _processScriptParams(
		JSONObject jsonObject, ScriptBuilder scriptBuilder) {

		if (jsonObject != null) {
			for (String key : jsonObject.keySet()) {
				scriptBuilder.putParameter(key, jsonObject.get(key));
			}
		}
	}

	private void _processSortOrder(JSONObject jsonObject, Sort sort) {
		if (jsonObject.has("order")) {
			sort.setSortOrder(_toSortOrder(jsonObject.getString("order")));
		}
	}

	private Sort _toFieldSort(String field) {
		if (field.equals("_score")) {
			return _sorts.score();
		}

		return _sorts.field(field);
	}

	private Sort _toFieldSort(String field, JSONObject jsonObject) {
		FieldSort fieldSort = _sorts.field(field);

		if (jsonObject.has("missing")) {
			fieldSort.setMissing(jsonObject.getString("missing"));
		}

		if (jsonObject.has("mode")) {
			fieldSort.setSortMode(
				SortMode.valueOf(
					StringUtil.toUpperCase(jsonObject.getString("mode"))));
		}

		if (jsonObject.has("nested")) {
			fieldSort.setNestedSort(
				_toNestedSort(jsonObject.getJSONObject("nested")));
		}

		_processSortOrder(jsonObject, fieldSort);

		return fieldSort;
	}

	private Sort _toFieldSort(String field, String string) {
		Sort sort = _toFieldSort(field);

		sort.setSortOrder(_toSortOrder(string));

		return sort;
	}

	private GeoDistanceSort _toGeoDistanceSort(JSONObject jsonObject) {
		Iterator<String> iterator = jsonObject.keys();

		String field = iterator.next();

		GeoDistanceSort geoDistanceSort = _sorts.geoDistance(field);

		_processGeoDistanceUnit(geoDistanceSort, jsonObject);
		_processGeoDistanceType(geoDistanceSort, jsonObject);
		_processGeoLocationPoints(geoDistanceSort, jsonObject.get(field));
		_processGeoSortMode(geoDistanceSort, jsonObject);
		_processSortOrder(jsonObject, geoDistanceSort);

		return geoDistanceSort;
	}

	private NestedSort _toNestedSort(JSONObject jsonObject) {
		NestedSort nestedSort = _sorts.nested(jsonObject.getString("path"));

		if (jsonObject.has("filter")) {
			nestedSort.setFilterQuery(
				_queries.wrapper(
					String.valueOf(jsonObject.getJSONObject("filter"))));
		}

		if (jsonObject.has("nested")) {
			nestedSort.setNestedSort(
				_toNestedSort(jsonObject.getJSONObject("nested")));
		}

		return nestedSort;
	}

	private Script _toScript(JSONObject jsonObject) {
		ScriptBuilder scriptBuilder = _scripts.builder();

		if (jsonObject.has("id")) {
			scriptBuilder.idOrCode(
				jsonObject.getString("id")
			).scriptType(
				ScriptType.STORED
			);
		}
		else if (jsonObject.has("source")) {
			scriptBuilder.idOrCode(
				jsonObject.getString("source")
			).scriptType(
				ScriptType.INLINE
			);
		}

		if (jsonObject.has("lang")) {
			scriptBuilder.language(jsonObject.getString("lang"));
		}

		_processScriptOptions(
			jsonObject.getJSONObject("options"), scriptBuilder);
		_processScriptParams(jsonObject.getJSONObject("params"), scriptBuilder);

		return scriptBuilder.build();
	}

	private Script _toScript(String string) {
		return _scripts.builder(
		).idOrCode(
			string
		).language(
			"painless"
		).scriptType(
			ScriptType.INLINE
		).build();
	}

	private Sort _toScriptSort(JSONObject jsonObject) {
		ScriptSort scriptSort = _sorts.script(
			_getScript(jsonObject),
			ScriptSort.ScriptSortType.valueOf(
				StringUtil.toUpperCase(jsonObject.getString("type"))));

		_processSortOrder(jsonObject, scriptSort);

		return scriptSort;
	}

	private Sort _toSort(JSONObject jsonObject) {
		Iterator<String> iterator = jsonObject.keys();

		String key = iterator.next();

		Object object = jsonObject.get(key);

		if (Objects.equals(key, "_geo_distance")) {
			return _toGeoDistanceSort((JSONObject)object);
		}
		else if (Objects.equals(key, "_script")) {
			return _toScriptSort((JSONObject)object);
		}
		else if (object instanceof JSONObject) {
			return _toFieldSort(key, (JSONObject)object);
		}
		else if (object instanceof String) {
			return _toFieldSort(key, (String)object);
		}

		throw new IllegalArgumentException();
	}

	private Sort _toSort(Object object, SXPParameterData sxpParameterData) {
		if (object instanceof JSONObject) {
			return _toSort(
				SXPParameterParser.parse((JSONObject)object, sxpParameterData));
		}
		else if (object instanceof String) {
			return _toFieldSort((String)object);
		}

		throw new IllegalArgumentException();
	}

	private SortOrder _toSortOrder(String string) {
		return SortOrder.valueOf(StringUtil.toUpperCase(string));
	}

	private final GeoBuilders _geoBuilders;
	private final Queries _queries;
	private final Scripts _scripts;
	private final Sorts _sorts;

}