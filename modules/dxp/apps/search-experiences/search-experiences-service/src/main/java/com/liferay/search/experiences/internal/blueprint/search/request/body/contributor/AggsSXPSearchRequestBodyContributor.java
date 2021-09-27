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

import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.search.experiences.internal.blueprint.aggregation.AggregationWrapper;
import com.liferay.search.experiences.internal.blueprint.aggregation.translator.AggregationTranslator;
import com.liferay.search.experiences.internal.blueprint.aggregation.translator.AggregationTranslatorFactory;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterData;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterParser;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

/**
 * @author Petteri Karttunen
 */
public class AggsSXPSearchRequestBodyContributor
	implements SXPSearchRequestBodyContributor {

	public AggsSXPSearchRequestBodyContributor(
		AggregationTranslatorFactory aggregationTranslatorFactory) {

		_aggregationTranslatorFactory = aggregationTranslatorFactory;
	}

	@Override
	public void contribute(
		SearchRequestBuilder searchRequestBuilder, SXPBlueprint sxpBlueprint,
		SXPParameterData sxpParameterData) {

		// TODO: AggsConfiguration

		// Configuration configuration = sxpBlueprint.getConfiguration();

		JSONObject aggsConfigurationJSONObject = new JSONObjectImpl();

		if (aggsConfigurationJSONObject == null) {
			return;
		}

		_processAggregations(
			searchRequestBuilder, null, aggsConfigurationJSONObject,
			sxpParameterData);
	}

	@Override
	public String getName() {
		return "aggs";
	}

	private void _addAggregation(
		SearchRequestBuilder searchRequestBuilder,
		AggregationWrapper aggregationWrapper) {

		if (aggregationWrapper.isPipeline()) {
			searchRequestBuilder.addPipelineAggregation(
				aggregationWrapper.getPipelineAggregation());
		}
		else {
			searchRequestBuilder.addAggregation(
				aggregationWrapper.getAggregation());
		}
	}

	private void _addChildAggregation(
		AggregationWrapper childAggregationWrapper,
		AggregationWrapper parentAggregationWrapper) {

		if (!parentAggregationWrapper.isPipeline()) {
			Aggregation aggregation = parentAggregationWrapper.getAggregation();

			if (childAggregationWrapper.isPipeline()) {
				aggregation.addPipelineAggregation(
					childAggregationWrapper.getPipelineAggregation());
			}
			else {
				aggregation.addChildAggregation(
					childAggregationWrapper.getAggregation());
			}
		}
	}

	private Optional<AggregationWrapper> _getAggregationOptional(
		JSONObject jsonObject, String name, String type,
		SXPParameterData sxpParameterData) {

		if (!_isEnabled(jsonObject)) {
			return Optional.empty();
		}

		JSONObject parsedJSONObject = SXPParameterParser.parse(
			jsonObject, sxpParameterData);

		if (parsedJSONObject == null) {
			return Optional.empty();
		}

		AggregationTranslator aggregationTranslator =
			_aggregationTranslatorFactory.getTranslator(type);

		return aggregationTranslator.translate(
			name, parsedJSONObject, sxpParameterData);
	}

	private String _getFirstKey(JSONObject jsonObject) {
		Iterator<String> iterator = jsonObject.keys();

		if (iterator.hasNext()) {
			return iterator.next();
		}

		return null;
	}

	private boolean _isEnabled(JSONObject jsonObject) {
		return jsonObject.getBoolean("enabled", true);
	}

	private void _processAggregation(
		SearchRequestBuilder searchRequestBuilder,
		JSONObject aggregationJSONObject, String aggregationName,
		AggregationWrapper parentAggregationWrapper,
		SXPParameterData sxpParameterData) {

		JSONObject nameJSONObject = aggregationJSONObject.getJSONObject(
			aggregationName);

		String type = _getFirstKey(nameJSONObject);

		JSONObject typeJSONObject = nameJSONObject.getJSONObject(
			_getFirstKey(nameJSONObject));

		AggregationWrapper aggregationWrapper;

		try {
			Optional<AggregationWrapper> aggregationWrapperOptional =
				_getAggregationOptional(
					typeJSONObject, aggregationName, type, sxpParameterData);

			if (!aggregationWrapperOptional.isPresent()) {
				return;
			}

			aggregationWrapper = aggregationWrapperOptional.get();
		}
		catch (IllegalArgumentException illegalArgumentException) {
			_log.error(illegalArgumentException);

			return;
		}

		if (!aggregationWrapper.isPipeline()) {
			JSONObject aggsJSONObject = nameJSONObject.getJSONObject("aggs");

			if (aggsJSONObject != null) {
				_processAggregations(
					searchRequestBuilder, aggregationWrapper, aggsJSONObject,
					sxpParameterData);
			}
		}

		if (parentAggregationWrapper == null) {
			_addAggregation(searchRequestBuilder, aggregationWrapper);
		}
		else {
			_addChildAggregation(aggregationWrapper, parentAggregationWrapper);
		}
	}

	private void _processAggregations(
		SearchRequestBuilder searchRequestBuilder,
		AggregationWrapper parentAggregationWrapper,
		JSONObject aggregationJSONObject, SXPParameterData sxpParameterData) {

		Set<String> keySet = aggregationJSONObject.keySet();

		keySet.forEach(
			aggregationName -> _processAggregation(
				searchRequestBuilder, aggregationJSONObject, aggregationName,
				parentAggregationWrapper, sxpParameterData));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AggsSXPSearchRequestBodyContributor.class);

	private final AggregationTranslatorFactory _aggregationTranslatorFactory;

}