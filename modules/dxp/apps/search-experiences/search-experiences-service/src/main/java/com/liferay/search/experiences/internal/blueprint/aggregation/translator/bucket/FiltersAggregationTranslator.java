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
import com.liferay.portal.search.aggregation.bucket.FiltersAggregation;
import com.liferay.portal.search.query.Query;
import com.liferay.search.experiences.internal.blueprint.aggregation.AggregationWrapper;
import com.liferay.search.experiences.internal.blueprint.aggregation.translator.AggregationTranslator;
import com.liferay.search.experiences.internal.blueprint.aggregation.util.AggregationHelper;
import com.liferay.search.experiences.internal.blueprint.clause.util.ClauseHelper;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterData;
import com.liferay.search.experiences.internal.blueprint.util.SetterHelper;

import java.util.Optional;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=filters",
	service = AggregationTranslator.class
)
public class FiltersAggregationTranslator implements AggregationTranslator {

	@Override
	public Optional<AggregationWrapper> translate(
		String aggregationName, JSONObject jsonObject,
		SXPParameterData sxpParameterData) {

		FiltersAggregation aggregation = _aggregations.filters(
			aggregationName, jsonObject.getString("field"));

		_addQueries(aggregation, jsonObject, sxpParameterData);

		_setterHelper.setBooleanValue(
			jsonObject, "other_bucket", aggregation::setOtherBucket);

		_setterHelper.setStringValue(
			jsonObject, "other_bucket_key", aggregation::setOtherBucketKey);

		return _aggregationHelper.wrap(aggregation);
	}

	private void _addQueries(
		FiltersAggregation aggregation, JSONObject jsonObject,
		SXPParameterData sxpParameterData) {

		JSONObject filtersJSONObject = jsonObject.getJSONObject("filters");

		if (filtersJSONObject == null) {
			return;
		}

		Set<String> keySet = filtersJSONObject.keySet();

		keySet.forEach(
			key -> _addQuery(
				aggregation, filtersJSONObject.getJSONObject(key), key,
				sxpParameterData));
	}

	private void _addQuery(
		FiltersAggregation aggregation, JSONObject jsonObject, String key,
		SXPParameterData sxpParameterData) {

		Optional<Query> optional = _clauseHelper.getQueryOptional(
			jsonObject, sxpParameterData);

		if (optional.isPresent()) {
			aggregation.addKeyedQuery(key, optional.get());
		}
	}

	@Reference
	private AggregationHelper _aggregationHelper;

	@Reference
	private Aggregations _aggregations;

	@Reference
	private ClauseHelper _clauseHelper;

	@Reference
	private SetterHelper _setterHelper;

}