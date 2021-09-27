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

package com.liferay.search.experiences.internal.blueprint.aggregation.translator.bucket;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.SignificantTextAggregation;
import com.liferay.search.experiences.internal.blueprint.aggregation.AggregationWrapper;
import com.liferay.search.experiences.internal.blueprint.aggregation.translator.AggregationTranslator;
import com.liferay.search.experiences.internal.blueprint.aggregation.util.AggregationHelper;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterData;
import com.liferay.search.experiences.internal.blueprint.util.SetterHelper;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=significant_text",
	service = AggregationTranslator.class
)
public class SignificantTextAggregationTranslator
	implements AggregationTranslator {

	@Override
	public Optional<AggregationWrapper> translate(
		String aggregationName, JSONObject jsonObject,
		SXPParameterData sxpParameterData) {

		SignificantTextAggregation aggregation = _aggregations.significantText(
			aggregationName, jsonObject.getString("field"));

		_aggregationHelper.setBackgroundFilter(
			jsonObject, aggregation::setBackgroundFilterQuery,
			sxpParameterData);

		_setterHelper.setStringValue(
			jsonObject, "execution_hint", aggregation::setExecutionHint);

		_setterHelper.setBooleanValue(
			jsonObject, "filter_duplicate_text",
			aggregation::setFilterDuplicateText);

		_aggregationHelper.setIncludeExcludeClause(
			jsonObject, aggregation::setIncludeExcludeClause);

		_setterHelper.setLongValue(
			jsonObject, "min_doc_count", aggregation::setMinDocCount);

		_setterHelper.setStringValue(
			jsonObject, "missing", aggregation::setMissing);

		_aggregationHelper.setScript(jsonObject, aggregation::setScript);

		_setterHelper.setLongValue(
			jsonObject, "shard_min_doc_count",
			aggregation::setShardMinDocCount);

		_setterHelper.setIntegerValue(
			jsonObject, "shard_size", aggregation::setShardSize);

		_aggregationHelper.setSignificanceHeuristics(
			jsonObject, aggregation::setSignificanceHeuristic);

		_setterHelper.setIntegerValue(jsonObject, "size", aggregation::setSize);

		return _aggregationHelper.wrap(aggregation);
	}

	@Reference
	private AggregationHelper _aggregationHelper;

	@Reference
	private Aggregations _aggregations;

	@Reference
	private SetterHelper _setterHelper;

}