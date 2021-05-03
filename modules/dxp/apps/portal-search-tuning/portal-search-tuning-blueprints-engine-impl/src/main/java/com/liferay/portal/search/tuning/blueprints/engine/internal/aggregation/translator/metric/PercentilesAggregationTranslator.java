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
import com.liferay.portal.search.aggregation.metrics.PercentilesAggregation;
import com.liferay.portal.search.aggregation.metrics.PercentilesMethod;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.aggregation.metric.PercentilesAggregationBodyConfigurationKeys;
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
	immediate = true, property = "name=percentiles",
	service = AggregationTranslator.class
)
public class PercentilesAggregationTranslator
	extends BaseAggregationTranslator implements AggregationTranslator {

	@Override
	public Optional<Aggregation> translate(
		String aggregationName, JSONObject bodyJSONObject,
		ParameterData parameterData, Messages messages,
		AggregationTranslatorFactory aggregationTranslatorFactory) {

		if (!validateField(bodyJSONObject, messages)) {
			return Optional.empty();
		}

		PercentilesAggregation aggregation = _aggregations.percentiles(
			aggregationName,
			bodyJSONObject.getString(
				PercentilesAggregationBodyConfigurationKeys.FIELD.
					getJsonKey()));

		if (bodyJSONObject.has(
				PercentilesAggregationBodyConfigurationKeys.HDR.getJsonKey())) {

			_setHDR(aggregation, bodyJSONObject);
		}
		else if (bodyJSONObject.has(
					PercentilesAggregationBodyConfigurationKeys.TDIGEST.
						getJsonKey())) {

			_setTDigest(aggregation, bodyJSONObject);
		}

		_setKeyed(aggregation, bodyJSONObject);

		setMissing(aggregation, bodyJSONObject);

		_setPercents(aggregation, bodyJSONObject);

		setScript(
			aggregation, _aggregationScriptHelper, bodyJSONObject, messages);

		return Optional.of(aggregation);
	}

	private void _setHDR(
		PercentilesAggregation aggregation, JSONObject bodyJSONObject) {

		JSONObject hdrJSONObject = bodyJSONObject.getJSONObject(
			PercentilesAggregationBodyConfigurationKeys.HDR.getJsonKey());

		if (!hdrJSONObject.has("number_of_significant_value_digits")) {
			return;
		}

		aggregation.setPercentilesMethod(PercentilesMethod.HDR);
		aggregation.setHdrSignificantValueDigits(
			hdrJSONObject.getInt("number_of_significant_value_digits"));
	}

	private void _setKeyed(
		PercentilesAggregation aggregation, JSONObject bodyJSONObject) {

		if (!bodyJSONObject.has(
				PercentilesAggregationBodyConfigurationKeys.KEYED.
					getJsonKey())) {

			return;
		}

		aggregation.setKeyed(
			bodyJSONObject.getBoolean(
				PercentilesAggregationBodyConfigurationKeys.KEYED.
					getJsonKey()));
	}

	private void _setPercents(
		PercentilesAggregation aggregation, JSONObject bodyJSONObject) {

		if (!bodyJSONObject.has(
				PercentilesAggregationBodyConfigurationKeys.PERCENTS.
					getJsonKey())) {

			return;
		}

		aggregation.setPercents(
			BlueprintJSONUtil.jsonArrayToDoubleArray(
				bodyJSONObject.getJSONArray(
					PercentilesAggregationBodyConfigurationKeys.PERCENTS.
						getJsonKey())));
	}

	private void _setTDigest(
		PercentilesAggregation aggregation, JSONObject bodyJSONObject) {

		JSONObject tDigestJSONObject = bodyJSONObject.getJSONObject(
			PercentilesAggregationBodyConfigurationKeys.TDIGEST.getJsonKey());

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