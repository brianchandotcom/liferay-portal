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

package com.liferay.portal.search.tuning.blueprints.facets.internal.searchrequest;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.DateRangeAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.filter.ComplexQueryPartBuilderFactory;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.TermQuery;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.tuning.blueprints.constants.json.values.FilterMode;
import com.liferay.portal.search.tuning.blueprints.constants.json.values.Operator;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.Parameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterData;
import com.liferay.portal.search.tuning.blueprints.engine.spi.searchrequest.SearchRequestBodyContributor;
import com.liferay.portal.search.tuning.blueprints.engine.util.BlueprintTemplateVariableParser;
import com.liferay.portal.search.tuning.blueprints.facets.constants.FacetConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.facets.constants.FacetsBlueprintContributorKeys;
import com.liferay.portal.search.tuning.blueprints.message.Message;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.message.Severity;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.util.BlueprintHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=facets",
	service = SearchRequestBodyContributor.class
)
public class FacetsSearchRequestBodyContributor
	implements SearchRequestBodyContributor {

	@Override
	public void contribute(
		SearchRequestBuilder searchRequestBuilder, Blueprint blueprint,
		ParameterData parameterData, Messages messages) {

		Optional<JSONArray> configurationJSONArrayOptional =
			_blueprintHelper.getJSONArrayConfigurationOptional(
				blueprint,
				"JSONArray/" +
					FacetsBlueprintContributorKeys.CONFIGURATION_SECTION);

		if (!configurationJSONArrayOptional.isPresent()) {
			return;
		}

		_contribute(
			searchRequestBuilder, configurationJSONArrayOptional.get(),
			parameterData, messages);
	}

	private void _addAggregation(
		SearchRequestBuilder searchRequestBuilder,
		JSONObject configurationJSONObject) {

		String aggregationType = configurationJSONObject.getString(
			FacetConfigurationKeys.AGGREGATION_TYPE.getJsonKey(), "terms");

		if (aggregationType.contentEquals("terms")) {
			_addTermsAggregation(searchRequestBuilder, configurationJSONObject);
		}
		else if (aggregationType.contentEquals("date_range")) {
			_addDateRangeAggregation(
				searchRequestBuilder, configurationJSONObject);
		}
	}

	private void _addDateRangeAggregation(
		SearchRequestBuilder searchRequestBuilder,
		JSONObject configurationJSONObject) {

		JSONObject handlerParametersJSONObject =
			configurationJSONObject.getJSONObject(
				FacetConfigurationKeys.HANDLER_PARAMETERS.getJsonKey());

		if ((handlerParametersJSONObject == null) ||
			!handlerParametersJSONObject.has("ranges")) {

			return;
		}

		JSONArray rangesJSONArray = handlerParametersJSONObject.getJSONArray(
			"ranges");

		String dateFormat = handlerParametersJSONObject.getString(
			"date_format");

		String aggregationName = _getAggregationName(configurationJSONObject);

		String field = _getFieldName(configurationJSONObject);

		DateRangeAggregation dateRangeAggregation = _aggregations.dateRange(
			aggregationName, field);

		for (int i = 0; i < rangesJSONArray.length(); i++) {
			JSONObject rangeJSONObject = rangesJSONArray.getJSONObject(i);

			String from = _getDateRangeString(
				rangeJSONObject.getString("from"), dateFormat, true);
			String label = rangeJSONObject.getString("label", "label");
			String to = _getDateRangeString(
				rangeJSONObject.getString("to"), dateFormat, false);

			dateRangeAggregation.addRange(label, from, to);
		}

		searchRequestBuilder.addAggregation(dateRangeAggregation);
	}

	private void _addDateRangeFacetFilter(
		SearchRequestBuilder searchRequestBuilder, Messages messages,
		JSONObject configurationJSONObject, Object value) {

		Optional<FilterMode> filterModeOptional = _getFilterMode(
			configurationJSONObject, messages);

		if (!filterModeOptional.isPresent()) {
			return;
		}

		BooleanQuery query = _getDateRangeFilterQuery(
			configurationJSONObject, GetterUtil.getString(value));

		if (query.hasClauses()) {
			if (FilterMode.PRE.equals(filterModeOptional.get())) {
				searchRequestBuilder.addComplexQueryPart(
					_complexQueryPartBuilderFactory.builder(
					).query(
						query
					).occur(
						"filter"
					).build());
			}
			else {
				searchRequestBuilder.addPostFilterQueryPart(
					_complexQueryPartBuilderFactory.builder(
					).query(
						query
					).occur(
						"must"
					).build());
			}
		}
	}

	private void _addFilter(
		SearchRequestBuilder searchRequestBuilder, ParameterData parameterData,
		Messages messages, JSONObject configurationJSONObject) {

		Optional<Parameter> parameterOptional = parameterData.getByNameOptional(
			configurationJSONObject.getString(
				FacetConfigurationKeys.PARAMETER_NAME.getJsonKey()));

		if (!parameterOptional.isPresent()) {
			return;
		}

		Parameter parameter = parameterOptional.get();

		String handlerName = configurationJSONObject.getString(
			FacetConfigurationKeys.HANDLER.getJsonKey());

		try {
			if (handlerName.equals("date_range")) {
				_addDateRangeFacetFilter(
					searchRequestBuilder, messages, configurationJSONObject,
					parameter.getValue());
			}
			else {
				_addTermsFacetFilter(
					searchRequestBuilder, messages, configurationJSONObject,
					parameter.getValue());
			}
		}
		catch (Exception exception) {
			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"facets.error.unknown-error-in-creating-filter"
				).msg(
					exception.getMessage()
				).rootObject(
					configurationJSONObject
				).severity(
					Severity.ERROR
				).throwable(
					exception
				).build());

			_log.error(exception.getMessage(), exception);
		}
	}

	private void _addTermsAggregation(
		SearchRequestBuilder searchRequestBuilder,
		JSONObject configurationJSONObject) {

		String aggregationName = _getAggregationName(configurationJSONObject);

		String field = _getFieldName(configurationJSONObject);

		int size = configurationJSONObject.getInt(
			FacetConfigurationKeys.SIZE.getJsonKey(), 50);

		TermsAggregation aggregation = _aggregations.terms(
			aggregationName, field);

		aggregation.setSize(size);

		searchRequestBuilder.addAggregation(aggregation);
	}

	private void _addTermsFacetFilter(
		SearchRequestBuilder searchRequestBuilder, Messages messages,
		JSONObject configurationJSONObject, Object value) {

		String field = _getFieldName(configurationJSONObject);

		Optional<FilterMode> filterModeOptional = _getFilterMode(
			configurationJSONObject, messages);

		if (!filterModeOptional.isPresent()) {
			return;
		}

		Optional<Operator> operatorOptional = _getOperator(
			configurationJSONObject, messages);

		if (!operatorOptional.isPresent()) {
			return;
		}

		BooleanQuery query = _getTermFilterQuery(
			operatorOptional.get(), field, value);

		if (query.hasClauses()) {
			if (FilterMode.PRE.equals(filterModeOptional.get())) {
				searchRequestBuilder.addComplexQueryPart(
					_complexQueryPartBuilderFactory.builder(
					).query(
						query
					).occur(
						"filter"
					).build());
			}
			else {
				searchRequestBuilder.addPostFilterQueryPart(
					_complexQueryPartBuilderFactory.builder(
					).query(
						query
					).occur(
						"must"
					).build());
			}
		}
	}

	private void _contribute(
		SearchRequestBuilder searchRequestBuilder,
		JSONArray configurationJSONArray, ParameterData parameterData,
		Messages messages) {

		for (int i = 0; i < configurationJSONArray.length(); i++) {
			JSONObject configurationJSONObject =
				configurationJSONArray.getJSONObject(i);

			Optional<JSONObject> parsedConfigurationJSONObjectOptional =
				_blueprintTemplateVariableParser.parse(
					configurationJSONObject, parameterData, messages);

			if (!parsedConfigurationJSONObjectOptional.isPresent()) {
				continue;
			}

			JSONObject parsedConfigurationJSONObject =
				parsedConfigurationJSONObjectOptional.get();

			boolean enabled = parsedConfigurationJSONObject.getBoolean(
				FacetConfigurationKeys.ENABLED.getJsonKey(), true);

			if (!enabled) {
				continue;
			}

			_addAggregation(
				searchRequestBuilder, parsedConfigurationJSONObject);

			_addFilter(
				searchRequestBuilder, parameterData, messages,
				parsedConfigurationJSONObject);
		}
	}

	private String _getAggregationName(JSONObject configurationJSONObject) {
		String aggregationName = configurationJSONObject.getString(
			FacetConfigurationKeys.AGGREGATION_NAME.getJsonKey());

		if (Validator.isBlank(aggregationName)) {
			aggregationName = _getFieldName(configurationJSONObject);
		}

		return aggregationName;
	}

	private BooleanQuery _getDateRangeFilterQuery(
		JSONObject configurationJSONObject, String value) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		String field = _getFieldName(configurationJSONObject);

		JSONObject handlerParametersJSONObject =
			configurationJSONObject.getJSONObject(
				FacetConfigurationKeys.HANDLER_PARAMETERS.getJsonKey());

		if ((handlerParametersJSONObject == null) ||
			!handlerParametersJSONObject.has("ranges")) {

			return booleanQuery;
		}

		JSONArray rangesJSONArray = handlerParametersJSONObject.getJSONArray(
			"ranges");

		String dateFormatString = handlerParametersJSONObject.getString(
			"date_format");

		for (int i = 0; i < rangesJSONArray.length(); i++) {
			JSONObject jsonObject = rangesJSONArray.getJSONObject(i);

			String label = jsonObject.getString("label");

			if (value.equals(label)) {
				String startDate = _getDateRangeString(
					jsonObject.getString("from"), dateFormatString, false);

				String endDate = _getDateRangeString(
					jsonObject.getString("to"), dateFormatString, true);

				booleanQuery.addMustQueryClauses(
					_queries.dateRangeTerm(
						field, true, true, startDate, endDate));

				break;
			}
		}

		return booleanQuery;
	}

	private String _getDateRangeString(
		String str, String dateFormatString, boolean future) {

		try {
			Date date = null;

			if (str.equals("*")) {
				if (future) {
					date = new Date(Long.MAX_VALUE);
				}
				else {
					date = new Date(Long.MIN_VALUE);
				}
			}
			else if (Validator.isBlank(str)) {
				date = new Date();
			}

			if (date != null) {
				DateFormat dateFormat = new SimpleDateFormat(dateFormatString);

				return dateFormat.format(date);
			}
		}
		catch (Exception exception) {
			_log.error(exception.getMessage(), exception);
		}

		return str;
	}

	private String _getFieldName(JSONObject configurationJSONObject) {
		String field = configurationJSONObject.getString(
			FacetConfigurationKeys.FIELD.getJsonKey());

		if (Validator.isBlank(field)) {
			field = configurationJSONObject.getString(
				FacetConfigurationKeys.PARAMETER_NAME.getJsonKey());
		}

		return field;
	}

	private Optional<FilterMode> _getFilterMode(
		JSONObject configurationJSONObject, Messages messages) {

		String filterModeString = configurationJSONObject.getString(
			FacetConfigurationKeys.FILTER_MODE.getJsonKey(),
			FilterMode.PRE.getjsonValue());

		try {
			FilterMode filterMode = FilterMode.valueOf(
				StringUtil.toUpperCase(filterModeString));

			return Optional.of(filterMode);
		}
		catch (IllegalArgumentException illegalArgumentException) {
			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"facets.error.unknown-filter-mode"
				).msg(
					illegalArgumentException.getMessage()
				).rootObject(
					configurationJSONObject
				).rootProperty(
					FacetConfigurationKeys.FILTER_MODE.getJsonKey()
				).rootValue(
					filterModeString
				).severity(
					Severity.ERROR
				).throwable(
					illegalArgumentException
				).build());

			_log.error(
				illegalArgumentException.getMessage(),
				illegalArgumentException);
		}

		return Optional.empty();
	}

	private Optional<Operator> _getOperator(
		JSONObject configurationJSONObject, Messages messages) {

		String operatorString = configurationJSONObject.getString(
			FacetConfigurationKeys.MULTI_VALUE_OPERATOR.getJsonKey(),
			Operator.AND.getjsonValue());

		try {
			Operator operator = Operator.valueOf(
				StringUtil.toUpperCase(operatorString));

			return Optional.of(operator);
		}
		catch (IllegalArgumentException illegalArgumentException) {
			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"facets.error.unknown-multi-value-operator"
				).msg(
					illegalArgumentException.getMessage()
				).rootObject(
					configurationJSONObject
				).rootProperty(
					FacetConfigurationKeys.MULTI_VALUE_OPERATOR.getJsonKey()
				).rootValue(
					operatorString
				).severity(
					Severity.ERROR
				).throwable(
					illegalArgumentException
				).build());

			_log.error(
				illegalArgumentException.getMessage(),
				illegalArgumentException);
		}

		return Optional.empty();
	}

	private BooleanQuery _getTermFilterQuery(
		Operator operator, String field, Object value) {

		BooleanQuery query = _queries.booleanQuery();

		if (value instanceof String) {
			query.addMustQueryClauses(_queries.term(field, value));
		}
		else if (value instanceof String[]) {
			String[] values = (String[])value;

			for (String val : values) {
				TermQuery condition = _queries.term(field, val);

				if (values.length > 1) {
					if (operator.equals(Operator.AND)) {
						query.addMustQueryClauses(condition);
					}
					else {
						query.addShouldQueryClauses(condition);
					}
				}
				else {
					query.addMustQueryClauses(condition);
				}
			}
		}

		return query;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FacetsSearchRequestBodyContributor.class);

	@Reference
	private Aggregations _aggregations;

	@Reference
	private BlueprintHelper _blueprintHelper;

	@Reference
	private BlueprintTemplateVariableParser _blueprintTemplateVariableParser;

	@Reference
	private ComplexQueryPartBuilderFactory _complexQueryPartBuilderFactory;

	@Reference
	private Queries _queries;

}