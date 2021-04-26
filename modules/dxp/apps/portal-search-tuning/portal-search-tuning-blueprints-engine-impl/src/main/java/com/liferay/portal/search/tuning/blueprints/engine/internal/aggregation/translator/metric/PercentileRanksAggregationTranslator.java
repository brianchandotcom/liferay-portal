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

package com.liferay.portal.search.tuning.blueprints.engine.internal.aggregation.translator.metric;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.metrics.PercentileRanksAggregation;
import com.liferay.portal.search.aggregation.metrics.PercentilesMethod;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.aggregation.metric.PercentileRanksAggregationBodyConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.engine.internal.aggregation.translator.BaseAggregationTranslator;
import com.liferay.portal.search.tuning.blueprints.engine.internal.util.BlueprintJSONUtil;
import com.liferay.portal.search.tuning.blueprints.engine.internal.util.ScriptHelper;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterData;
import com.liferay.portal.search.tuning.blueprints.engine.spi.aggregation.AggregationTranslator;
import com.liferay.portal.search.tuning.blueprints.engine.spi.aggregation.AggregationTranslatorFactory;
import com.liferay.portal.search.tuning.blueprints.message.Messages;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=percentile_ranks",
	service = AggregationTranslator.class
)
public class PercentileRanksAggregationTranslator
	extends BaseAggregationTranslator implements AggregationTranslator {

	@Override
	public Optional<Aggregation> translate(
		String aggregationName, JSONObject bodyJSONObject,
		ParameterData parameterData, Messages messages,
		AggregationTranslatorFactory aggregationTranslatorFactory) {

		double[] values = _getValues(bodyJSONObject);

		if ((values == null) || !validateField(bodyJSONObject, messages)) {
			return Optional.empty();
		}

		PercentileRanksAggregation aggregation = _aggregations.percentileRanks(
			aggregationName,
			bodyJSONObject.getString(
				PercentileRanksAggregationBodyConfigurationKeys.FIELD.
					getJsonKey()),
			values);

		if (bodyJSONObject.has(
				PercentileRanksAggregationBodyConfigurationKeys.HDR.
					getJsonKey())) {

			_setHDR(aggregation, bodyJSONObject);
		}
		else if (bodyJSONObject.has(
					PercentileRanksAggregationBodyConfigurationKeys.TDIGEST.
						getJsonKey())) {

			_setTDigest(aggregation, bodyJSONObject);
		}

		_setKeyed(aggregation, bodyJSONObject);

		setMissing(aggregation, bodyJSONObject);

		setScript(
			aggregation, _aggregationScriptHelper, bodyJSONObject, messages);

		return Optional.of(aggregation);
	}

	private double[] _getValues(JSONObject bodyJSONObject) {
		if (!bodyJSONObject.has(
				PercentileRanksAggregationBodyConfigurationKeys.VALUES.
					getJsonKey())) {

			return null;
		}

		return BlueprintJSONUtil.jsonArrayToDoubleArray(
			bodyJSONObject.getJSONArray(
				PercentileRanksAggregationBodyConfigurationKeys.VALUES.
					getJsonKey()));
	}

	private void _setHDR(
		PercentileRanksAggregation aggregation, JSONObject bodyJSONObject) {

		JSONObject hdrJSONObject = bodyJSONObject.getJSONObject(
			PercentileRanksAggregationBodyConfigurationKeys.HDR.getJsonKey());

		if (!hdrJSONObject.has("number_of_significant_value_digits")) {
			return;
		}

		aggregation.setPercentilesMethod(PercentilesMethod.HDR);
		aggregation.setHdrSignificantValueDigits(
			hdrJSONObject.getInt("number_of_significant_value_digits"));
	}

	private void _setKeyed(
		PercentileRanksAggregation aggregation, JSONObject bodyJSONObject) {

		if (!bodyJSONObject.has(
				PercentileRanksAggregationBodyConfigurationKeys.KEYED.
					getJsonKey())) {

			return;
		}

		aggregation.setKeyed(
			bodyJSONObject.getBoolean(
				PercentileRanksAggregationBodyConfigurationKeys.KEYED.
					getJsonKey()));
	}

	private void _setTDigest(
		PercentileRanksAggregation aggregation, JSONObject bodyJSONObject) {

		JSONObject tDigestJSONObject = bodyJSONObject.getJSONObject(
			PercentileRanksAggregationBodyConfigurationKeys.TDIGEST.
				getJsonKey());

		if (!tDigestJSONObject.has("compression")) {
			return;
		}

		aggregation.setPercentilesMethod(PercentilesMethod.TDIGEST);
		aggregation.setCompression(tDigestJSONObject.getInt("compression"));
	}

	@Reference
	private Aggregations _aggregations;

	@Reference
	private ScriptHelper _aggregationScriptHelper;

}