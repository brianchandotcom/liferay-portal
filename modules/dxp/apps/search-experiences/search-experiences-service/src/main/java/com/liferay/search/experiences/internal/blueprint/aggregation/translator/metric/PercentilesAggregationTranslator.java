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

package com.liferay.search.experiences.internal.blueprint.aggregation.translator.metric;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.metrics.PercentilesAggregation;
import com.liferay.portal.search.aggregation.metrics.PercentilesMethod;
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
	immediate = true, property = "name=percentiles",
	service = AggregationTranslator.class
)
public class PercentilesAggregationTranslator implements AggregationTranslator {

	@Override
	public Optional<AggregationWrapper> translate(
		String aggregationName, JSONObject jsonObject,
		SXPParameterData sxpParameterData) {

		PercentilesAggregation aggregation = _aggregations.percentiles(
			aggregationName, jsonObject.getString("field"));

		_setterHelper.setBooleanValue(
			jsonObject, "keyed", aggregation::setKeyed);

		_setMethod(aggregation, jsonObject);

		_setterHelper.setStringValue(
			jsonObject, "missing", aggregation::setMissing);

		_setterHelper.setDoubleArrayValue(
			jsonObject, "percents", aggregation::setPercents);

		_aggregationHelper.setScript(jsonObject, aggregation::setScript);

		return _aggregationHelper.wrap(aggregation);
	}

	private void _setMethod(
		PercentilesAggregation aggregation, JSONObject jsonObject) {

		Integer digits = _aggregationHelper.getHdrSignificantValueDigits(
			jsonObject);

		if (digits != null) {
			aggregation.setPercentilesMethod(PercentilesMethod.HDR);
			aggregation.setHdrSignificantValueDigits(
				jsonObject.getInt("number_of_significant_value_digits"));
		}
		else {
			Integer compression = _aggregationHelper.getTDigestCompression(
				jsonObject);

			if (compression != null) {
				aggregation.setPercentilesMethod(PercentilesMethod.TDIGEST);
				aggregation.setCompression(jsonObject.getInt("compression"));
			}
		}
	}

	@Reference
	private AggregationHelper _aggregationHelper;

	@Reference
	private Aggregations _aggregations;

	@Reference
	private SetterHelper _setterHelper;

}