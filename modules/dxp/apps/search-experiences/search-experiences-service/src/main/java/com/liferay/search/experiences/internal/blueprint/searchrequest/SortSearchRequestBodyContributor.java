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

package com.liferay.search.experiences.internal.blueprint.searchrequest;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.sort.ScoreSort;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.blueprint.parameter.SXPParameterData;
import com.liferay.search.experiences.blueprint.searchrequest.SearchRequestBodyContributor;
import com.liferay.search.experiences.internal.blueprint.parser.SXPTemplateVariableParser;
import com.liferay.search.experiences.internal.blueprint.sort.SortTranslatorFactory;
import com.liferay.search.experiences.internal.blueprint.sort.translator.SortTranslator;
import com.liferay.search.experiences.internal.blueprint.util.SXPJSONUtil;
import com.liferay.search.experiences.model.SXPBlueprint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=sort",
	service = SearchRequestBodyContributor.class
)
public class SortSearchRequestBodyContributor
	implements SearchRequestBodyContributor {

	@Override
	public void contribute(
		SearchRequestBuilder searchRequestBuilder, SXPBlueprint sxpBlueprint,
		SXPParameterData sxpParameterData) {

		if (_sortsExist(searchRequestBuilder)) {
			return;
		}

		// TODO (remove): first try to get sorts from parameter
		// depend on Blueprint sort parameter configuration

		List<Sort> sorts = _getSortsFromParameters(
			sxpBlueprint, sxpParameterData);

		if (sorts.isEmpty()) {
			sorts = _getDefaultSorts(sxpBlueprint, sxpParameterData);
		}

		if (!sorts.isEmpty()) {
			Stream<Sort> stream = sorts.stream();

			stream.forEach(sort -> searchRequestBuilder.addSort(sort));
		}
	}

	private Optional<Sort> _getDefaultSort(
		Object object, SXPParameterData sxpParameterData) {

		if (object instanceof String) {
			return _sortFromString((String)object, null);
		}

		JSONObject jsonObject = _sxpTemplateVariableParser.parse(
			(JSONObject)object, sxpParameterData);

		if (Objects.isNull(jsonObject)) {
			return Optional.empty();
		}

		return _sortFromObject(jsonObject);
	}

	private List<Sort> _getDefaultSorts(
		SXPBlueprint sxpBlueprint, SXPParameterData sxpParameterData) {

		List<Sort> sorts = new ArrayList<>();

		// TODO get default sort configuration from DTO

		Optional<JSONArray> optional1 = Optional.empty();

		if (!optional1.isPresent()) {
			return sorts;
		}

		JSONArray jsonArray = optional1.get();

		for (int i = 0; i < jsonArray.length(); i++) {
			Optional<Sort> optional2 = _getDefaultSort(
				jsonArray.get(i), sxpParameterData);

			if (optional2.isPresent()) {
				sorts.add(optional2.get());
			}
		}

		return sorts;
	}

	private Optional<Sort> _getSort(
		JSONObject jsonObject, String key, SortOrder sortOrder) {

		try {
			SortTranslator sortTranslator;
			String field;

			if (_fixedTypes.contains(key)) {
				sortTranslator = _sortTranslatorFactory.getTranslator(key);
				field = null;
			}
			else {
				sortTranslator = _sortTranslatorFactory.getTranslator("field");
				field = key;
			}

			return sortTranslator.translate(field, jsonObject, sortOrder);
		}
		catch (IllegalArgumentException illegalArgumentException) {
			_log.error(illegalArgumentException);
		}

		return Optional.empty();
	}

	private Optional<Sort> _getSortFromParameter(
		JSONObject jsonObject, String key, SXPParameterData sxpParameterData) {

		JSONObject configurationJSONObject = jsonObject.getJSONObject(key);

		JSONObject parsedJSONObject = _sxpTemplateVariableParser.parse(
			configurationJSONObject, sxpParameterData);

		if (Objects.isNull(parsedJSONObject)) {
			return Optional.empty();
		}

		SortOrder sortOrder = _getSortOrderFromParameter(
			parsedJSONObject, sxpParameterData);

		if (sortOrder == null) {
			return Optional.empty();
		}

		return _getSort(parsedJSONObject, key, sortOrder);
	}

	private SortOrder _getSortOrder(String s) {
		try {
			return SortOrder.valueOf(StringUtil.toUpperCase(s));
		}
		catch (IllegalArgumentException illegalArgumentException) {
			_log.error(
				illegalArgumentException.getMessage(),
				illegalArgumentException);
		}

		return null;
	}

	private SortOrder _getSortOrderFromParameter(
		JSONObject jsonObject, SXPParameterData sxpParameterData) {

		String parameterName = jsonObject.getString("parameter_name");

		if (Validator.isBlank(parameterName)) {
			return null;
		}

		SXPParameter sxpParameter = sxpParameterData.getSXPParameterByName(
			parameterName);

		if (Objects.isNull(sxpParameter)) {
			return null;
		}

		return _getSortOrder(GetterUtil.getString(sxpParameter.getValue()));
	}

	private List<Sort> _getSortsFromParameters(
		SXPBlueprint sxpBlueprint, SXPParameterData sxpParameterData) {

		List<Sort> sorts = new ArrayList<>();

		// TODO get sort parameter configuration from DTO

		Optional<JSONObject> optional1 = Optional.empty();

		if (!optional1.isPresent()) {
			return sorts;
		}

		JSONObject jsonObject = optional1.get();

		Set<String> keySet = jsonObject.keySet();

		keySet.forEach(
			key -> {
				Optional<Sort> optional2 = _getSortFromParameter(
					jsonObject, key, sxpParameterData);

				if (optional2.isPresent()) {
					sorts.add(optional2.get());
				}
			});

		return sorts;
	}

	private Optional<Sort> _sortFromObject(JSONObject jsonObject) {
		Optional<String> optional = SXPJSONUtil.getFirstKeyOptional(jsonObject);

		if (!optional.isPresent()) {
			return Optional.empty();
		}

		String key = optional.get();

		Object object = jsonObject.get(key);

		if (object instanceof String) {
			return _sortFromString(key, _getSortOrder((String)object));
		}

		JSONObject configurationJSONObject = (JSONObject)object;

		return _getSort(
			jsonObject, key,
			_getSortOrder(configurationJSONObject.getString("order")));
	}

	private Optional<Sort> _sortFromString(String s, SortOrder sortOrder) {
		if (s.equals("_score")) {
			ScoreSort sort = _sorts.score();

			if (sortOrder != null) {
				sort.setSortOrder(sortOrder);
			}

			return Optional.of(sort);
		}

		Sort sort = _sorts.field(s);

		if (sortOrder != null) {
			sort.setSortOrder(sortOrder);
		}

		return Optional.of(sort);
	}

	// TODO (remove) if sorts already exists (set in a widget), don't overwrite

	private boolean _sortsExist(SearchRequestBuilder searchRequestBuilder) {
		return searchRequestBuilder.withSearchContextGet(
			searchContext -> {
				SearchRequest searchRequest =
					(SearchRequest)searchContext.getAttribute("search.request");

				List<Sort> sorts = searchRequest.getSorts();

				return !sorts.isEmpty();
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SortSearchRequestBodyContributor.class);

	private static final List<String> _fixedTypes = new ArrayList<>(
		Arrays.asList("_geo_distance", "_script", "_score"));

	@Reference
	private Sorts _sorts;

	@Reference
	private SortTranslatorFactory _sortTranslatorFactory;

	@Reference
	private SXPTemplateVariableParser _sxpTemplateVariableParser;

}