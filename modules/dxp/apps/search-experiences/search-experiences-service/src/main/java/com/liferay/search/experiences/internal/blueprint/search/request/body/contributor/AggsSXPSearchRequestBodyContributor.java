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

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.geolocation.GeoBuilders;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.significance.SignificanceHeuristics;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.search.experiences.internal.blueprint.aggregation.AggregationWrapper;
import com.liferay.search.experiences.internal.blueprint.aggregation.AggregationWrapperConverter;
import com.liferay.search.experiences.internal.blueprint.highlight.HighlightConverter;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterData;
import com.liferay.search.experiences.internal.blueprint.query.QueryConverter;
import com.liferay.search.experiences.internal.blueprint.script.ScriptConverter;
import com.liferay.search.experiences.rest.dto.v1_0.AggregationConfiguration;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;

import java.util.Iterator;

/**
 * @author Petteri Karttunen
 */
public class AggsSXPSearchRequestBodyContributor
	implements SXPSearchRequestBodyContributor {

	public AggsSXPSearchRequestBodyContributor(
		Aggregations aggregations, GeoBuilders geoBuilders,
		HighlightConverter highlightConverter, QueryConverter queryConverter,
		ScriptConverter scriptConverter,
		SignificanceHeuristics significanceHeuristics, Sorts sorts) {

		_aggregationWrapperConverter = new AggregationWrapperConverter(
			aggregations, geoBuilders, highlightConverter, queryConverter,
			scriptConverter, significanceHeuristics, sorts);
	}

	@Override
	public void contribute(
		SearchRequestBuilder searchRequestBuilder, SXPBlueprint sxpBlueprint,
		SXPParameterData sxpParameterData) {

		Configuration configuration = sxpBlueprint.getConfiguration();

		AggregationConfiguration aggregationConfiguration =
			configuration.getAggregationConfiguration();

		if (aggregationConfiguration == null) {
			return;
		}

		_processAggregations(
			(JSONObject)aggregationConfiguration.getAggs(), null,
			searchRequestBuilder);
	}

	@Override
	public String getName() {
		return "aggs";
	}

	private void _addAggregation(
		AggregationWrapper aggregationWrapper,
		SearchRequestBuilder searchRequestBuilder) {

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

		if (parentAggregationWrapper.isPipeline()) {
			return;
		}

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

	private void _processAggregation(
		JSONObject jsonObject, String name,
		AggregationWrapper parentAggregationWrapper,
		SearchRequestBuilder searchRequestBuilder) {

		Iterator<String> iterator = jsonObject.keys();

		String type = iterator.next();

		AggregationWrapper aggregationWrapper = _toAggregationWrapper(
			jsonObject.getJSONObject(type), name, type);

		if (aggregationWrapper == null) {
			return;
		}

		if (!aggregationWrapper.isPipeline()) {
			JSONObject aggsJSONObject = jsonObject.getJSONObject("aggs");

			if (aggsJSONObject != null) {
				_processAggregations(
					aggsJSONObject, aggregationWrapper, searchRequestBuilder);
			}
		}

		if (parentAggregationWrapper == null) {
			_addAggregation(aggregationWrapper, searchRequestBuilder);
		}
		else {
			_addChildAggregation(aggregationWrapper, parentAggregationWrapper);
		}
	}

	private void _processAggregations(
		JSONObject jsonObject, AggregationWrapper parentAggregationWrapper,
		SearchRequestBuilder searchRequestBuilder) {

		for (String name : jsonObject.keySet()) {
			_processAggregation(
				jsonObject.getJSONObject(name), name, parentAggregationWrapper,
				searchRequestBuilder);
		}
	}

	private AggregationWrapper _toAggregationWrapper(
		JSONObject jsonObject, String name, String type) {

		if (!jsonObject.getBoolean("enabled", true)) {
			return null;
		}

		return _aggregationWrapperConverter.toAggregationWrapper(
			jsonObject, name, type);
	}

	private final AggregationWrapperConverter _aggregationWrapperConverter;

}