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
import com.liferay.portal.search.aggregation.metrics.ExtendedStatsAggregation;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.aggregation.metric.ExtendedStatsAggregationBodyConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.engine.internal.aggregation.translator.BaseAggregationTranslator;
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
	immediate = true, property = "name=extended_stats",
	service = AggregationTranslator.class
)
public class ExtendedStatsAggregationTranslator
	extends BaseAggregationTranslator implements AggregationTranslator {

	@Override
	public Optional<Aggregation> translate(
		String aggregationName, JSONObject bodyJSONObject,
		ParameterData parameterData, Messages messages,
		AggregationTranslatorFactory aggregationTranslatorFactory) {

		if (!validateField(bodyJSONObject, messages)) {
			return Optional.empty();
		}

		ExtendedStatsAggregation aggregation = _aggregations.extendedStats(
			aggregationName,
			bodyJSONObject.getString(
				ExtendedStatsAggregationBodyConfigurationKeys.FIELD.
					getJsonKey()));

		setMissing(aggregation, bodyJSONObject);

		_setSigma(aggregation, bodyJSONObject);

		setScript(
			aggregation, _aggregationScriptHelper, bodyJSONObject, messages);

		return Optional.of(aggregation);
	}

	private void _setSigma(
		ExtendedStatsAggregation aggregation, JSONObject bodyJSONObject) {

		if (!bodyJSONObject.has(
				ExtendedStatsAggregationBodyConfigurationKeys.SIGMA.
					getJsonKey())) {

			return;
		}

		aggregation.setSigma(
			bodyJSONObject.getInt(
				ExtendedStatsAggregationBodyConfigurationKeys.SIGMA.
					getJsonKey()));
	}

	@Reference
	private Aggregations _aggregations;

	@Reference
	private ScriptHelper _aggregationScriptHelper;

}