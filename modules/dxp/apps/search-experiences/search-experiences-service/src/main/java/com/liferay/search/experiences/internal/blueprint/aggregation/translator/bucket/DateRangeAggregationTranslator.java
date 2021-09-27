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

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.DateRangeAggregation;
import com.liferay.portal.search.aggregation.bucket.Range;
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
	immediate = true, property = "name=date_range",
	service = AggregationTranslator.class
)
public class DateRangeAggregationTranslator implements AggregationTranslator {

	@Override
	public Optional<AggregationWrapper> translate(
		String aggregationName, JSONObject jsonObject,
		SXPParameterData sxpParameterData) {

		DateRangeAggregation aggregation = _aggregations.dateRange(
			aggregationName, jsonObject.getString("field"));

		_setterHelper.setStringValue(
			jsonObject, "format", aggregation::setFormat);

		_setterHelper.setBooleanValue(
			jsonObject, "keyed", aggregation::setKeyed);

		_setterHelper.setStringValue(
			jsonObject, "missing", aggregation::setMissing);

		_setRanges(aggregation, jsonObject);

		_aggregationHelper.setScript(jsonObject, aggregation::setScript);

		return _aggregationHelper.wrap(aggregation);
	}

	private void _setRanges(
		DateRangeAggregation aggregation, JSONObject jsonObject) {

		JSONArray rangesJSONArray = jsonObject.getJSONArray("ranges");

		if (rangesJSONArray == null) {
			return;
		}

		for (int i = 0; i < rangesJSONArray.length(); i++) {
			JSONObject rangeJSONObject = rangesJSONArray.getJSONObject(i);

			if (rangeJSONObject == null) {
				continue;
			}

			aggregation.addRange(
				new Range(
					rangeJSONObject.getString("key"),
					rangeJSONObject.getString("from"),
					rangeJSONObject.getString("to")));
		}
	}

	@Reference
	private AggregationHelper _aggregationHelper;

	@Reference
	private Aggregations _aggregations;

	@Reference
	private SetterHelper _setterHelper;

}