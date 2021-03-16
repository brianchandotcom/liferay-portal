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

package com.liferay.portal.search.tuning.blueprints.engine.internal.clause.translator;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.query.Operator;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.SimpleStringQuery;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.query.SimpleQueryStringConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterData;
import com.liferay.portal.search.tuning.blueprints.engine.spi.clause.ClauseTranslator;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "type=simple_query_string",
	service = ClauseTranslator.class
)
public class SimpleQueryStringClauseTranslator implements ClauseTranslator {

	@Override
	public Optional<Query> translate(
		JSONObject configurationJSONObject, Blueprint blueprint,
		ParameterData parameterData, Messages messages) {

		String keywords = configurationJSONObject.getString(
			SimpleQueryStringConfigurationKeys.QUERY.getJsonKey(),
			parameterData.getKeywords());

		SimpleStringQuery simpleStringQuery = _queries.simpleString(keywords);

		if (configurationJSONObject.has(
				SimpleQueryStringConfigurationKeys.ANALYZE_WILDCARD.
					getJsonKey())) {

			simpleStringQuery.setAnalyzeWildcard(
				configurationJSONObject.getBoolean(
					SimpleQueryStringConfigurationKeys.ANALYZE_WILDCARD.
						getJsonKey()));
		}

		if (configurationJSONObject.has(
				SimpleQueryStringConfigurationKeys.ANALYZER.getJsonKey())) {

			simpleStringQuery.setAnalyzer(
				SimpleQueryStringConfigurationKeys.ANALYZER.getJsonKey());
		}

		if (configurationJSONObject.has(
				SimpleQueryStringConfigurationKeys.
					AUTO_GENERATE_SYNONYMS_PHRASE_QUERY.getJsonKey())) {

			simpleStringQuery.setAutoGenerateSynonymsPhraseQuery(
				configurationJSONObject.getBoolean(
					SimpleQueryStringConfigurationKeys.
						AUTO_GENERATE_SYNONYMS_PHRASE_QUERY.getJsonKey()));
		}

		_setOperator(simpleStringQuery, configurationJSONObject);

		_setFields(simpleStringQuery, configurationJSONObject);

		if (configurationJSONObject.has(
				SimpleQueryStringConfigurationKeys.FUZZY_MAX_EXPANSIONS.
					getJsonKey())) {

			simpleStringQuery.setFuzzyMaxExpansions(
				configurationJSONObject.getInt(
					SimpleQueryStringConfigurationKeys.FUZZY_MAX_EXPANSIONS.
						getJsonKey()));
		}

		if (configurationJSONObject.has(
				SimpleQueryStringConfigurationKeys.FUZZY_PREFIX_LENGTH.
					getJsonKey())) {

			simpleStringQuery.setFuzzyPrefixLength(
				configurationJSONObject.getInt(
					SimpleQueryStringConfigurationKeys.FUZZY_PREFIX_LENGTH.
						getJsonKey()));
		}

		if (configurationJSONObject.has(
				SimpleQueryStringConfigurationKeys.FUZZY_TRANSPOSITIONS.
					getJsonKey())) {

			simpleStringQuery.setFuzzyTranspositions(
				configurationJSONObject.getBoolean(
					SimpleQueryStringConfigurationKeys.FUZZY_TRANSPOSITIONS.
						getJsonKey()));
		}

		if (configurationJSONObject.has(
				SimpleQueryStringConfigurationKeys.LENIENT.getJsonKey())) {

			simpleStringQuery.setLenient(
				configurationJSONObject.getBoolean(
					SimpleQueryStringConfigurationKeys.LENIENT.getJsonKey()));
		}

		if (configurationJSONObject.has(
				SimpleQueryStringConfigurationKeys.QUOTE_FIELD_SUFFIX.
					getJsonKey())) {

			simpleStringQuery.setQuoteFieldSuffix(
				configurationJSONObject.getString(
					SimpleQueryStringConfigurationKeys.QUOTE_FIELD_SUFFIX.
						getJsonKey()));
		}

		return Optional.of(simpleStringQuery);
	}

	private void _setFields(
		SimpleStringQuery simpleStringQuery,
		JSONObject configurationJSONObject) {

		if (configurationJSONObject.has(
				SimpleQueryStringConfigurationKeys.FIELDS.getJsonKey())) {

			JSONArray fieldsJSONArray = configurationJSONObject.getJSONArray(
				SimpleQueryStringConfigurationKeys.FIELDS.getJsonKey());

			for (int i = 0; i < fieldsJSONArray.length(); i++) {
				JSONObject fieldJSONObject = fieldsJSONArray.getJSONObject(i);

				String fieldName = fieldJSONObject.getString(
					SimpleQueryStringConfigurationKeys.FIELD.getJsonKey());

				float boost = GetterUtil.getFloat(
					fieldJSONObject.getString(
						SimpleQueryStringConfigurationKeys.BOOST.getJsonKey()),
					1.0F);

				simpleStringQuery.addField(fieldName, boost);
			}
		}
	}

	private void _setOperator(
		SimpleStringQuery simpleStringQuery,
		JSONObject configurationJSONObject) {

		if (configurationJSONObject.has(
				SimpleQueryStringConfigurationKeys.DEFAULT_OPERATOR.
					getJsonKey())) {

			String operator = configurationJSONObject.getString(
				SimpleQueryStringConfigurationKeys.DEFAULT_OPERATOR.
					getJsonKey(),
				Operator.AND.name());

			if (StringUtil.equalsIgnoreCase(operator, Operator.AND.name())) {
				simpleStringQuery.setDefaultOperator(Operator.AND);
			}
			else {
				simpleStringQuery.setDefaultOperator(Operator.OR);
			}
		}
	}

	@Reference
	private Queries _queries;

}