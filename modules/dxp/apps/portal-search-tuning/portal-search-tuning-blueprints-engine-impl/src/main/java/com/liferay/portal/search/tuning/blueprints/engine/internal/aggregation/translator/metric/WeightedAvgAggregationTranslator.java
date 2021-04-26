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
import com.liferay.portal.search.aggregation.metrics.WeightedAvgAggregation;
import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.aggregation.metric.WeightedAvgAggregationBodyConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.engine.internal.aggregation.translator.BaseAggregationTranslator;
import com.liferay.portal.search.tuning.blueprints.engine.internal.util.ScriptHelper;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterData;
import com.liferay.portal.search.tuning.blueprints.engine.spi.aggregation.AggregationTranslator;
import com.liferay.portal.search.tuning.blueprints.engine.spi.aggregation.AggregationTranslatorFactory;
import com.liferay.portal.search.tuning.blueprints.message.Message;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.message.Severity;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=weighted_avg",
	service = AggregationTranslator.class
)
public class WeightedAvgAggregationTranslator
	extends BaseAggregationTranslator implements AggregationTranslator {

	@Override
	public Optional<Aggregation> translate(
		String aggregationName, JSONObject bodyJSONObject,
		ParameterData parameterData, Messages messages,
		AggregationTranslatorFactory aggregationTranslatorFactory) {

		JSONObject valueJSONObject = _getValueJSONObject(
			bodyJSONObject, messages);

		JSONObject weightJSONObject = _getWeightJSONObject(
			bodyJSONObject, messages);

		if ((valueJSONObject == null) || (weightJSONObject == null)) {
			return Optional.empty();
		}

		WeightedAvgAggregation aggregation = _aggregations.weightedAvg(
			aggregationName, valueJSONObject.getString("field"),
			weightJSONObject.getString("field"));

		_setFormat(aggregation, bodyJSONObject);

		_setValueMissing(aggregation, valueJSONObject);

		_setValueScript(aggregation, valueJSONObject, messages);

		_setWeightMissing(aggregation, weightJSONObject);

		_setWeightScript(aggregation, weightJSONObject, messages);

		return Optional.of(aggregation);
	}

	private void _addValidationMessage(
		JSONObject bodyJSONObject, String message, String localizationKey,
		String rootProperty, Messages messages) {

		messages.addMessage(
			new Message.Builder().className(
				getClass().getName()
			).localizationKey(
				localizationKey
			).msg(
				message
			).rootObject(
				bodyJSONObject
			).rootProperty(
				rootProperty
			).severity(
				Severity.ERROR
			).build());
	}

	private JSONObject _getValueJSONObject(
		JSONObject bodyJSONObject, Messages messages) {

		if (!bodyJSONObject.has(
				WeightedAvgAggregationBodyConfigurationKeys.VALUE.
					getJsonKey())) {

			_addValidationMessage(
				bodyJSONObject, "Missing value object",
				"core.error.weighted-avg-value-object-missing",
				WeightedAvgAggregationBodyConfigurationKeys.VALUE.getJsonKey(),
				messages);

			return null;
		}

		JSONObject valueJSONObject = bodyJSONObject.getJSONObject(
			WeightedAvgAggregationBodyConfigurationKeys.VALUE.getJsonKey());

		if (!valueJSONObject.has("field")) {
			_addValidationMessage(
				bodyJSONObject, "Missing value field property",
				"core.error.weighted-avg-value-field-missing", "field",
				messages);

			return null;
		}

		return valueJSONObject;
	}

	private JSONObject _getWeightJSONObject(
		JSONObject bodyJSONObject, Messages messages) {

		if (!bodyJSONObject.has(
				WeightedAvgAggregationBodyConfigurationKeys.WEIGHT.
					getJsonKey())) {

			_addValidationMessage(
				bodyJSONObject, "Missing weight object",
				"core.error.weighted-avg-weight-object-missing",
				WeightedAvgAggregationBodyConfigurationKeys.VALUE.getJsonKey(),
				messages);

			return null;
		}

		JSONObject weightJSONObject = bodyJSONObject.getJSONObject(
			WeightedAvgAggregationBodyConfigurationKeys.WEIGHT.getJsonKey());

		if (!weightJSONObject.has("field")) {
			_addValidationMessage(
				bodyJSONObject, "Missing weight field property",
				"core.error.weighted-avg-value-field-missing", "field",
				messages);

			return null;
		}

		return weightJSONObject;
	}

	private void _setFormat(
		WeightedAvgAggregation aggregation, JSONObject bodyJSONObject) {

		if (!bodyJSONObject.has(
				WeightedAvgAggregationBodyConfigurationKeys.FORMAT.
					getJsonKey())) {

			return;
		}

		aggregation.setFormat(
			bodyJSONObject.getString(
				WeightedAvgAggregationBodyConfigurationKeys.FORMAT.
					getJsonKey()));
	}

	private void _setValueMissing(
		WeightedAvgAggregation aggregation, JSONObject valueJSONObject) {

		if (!valueJSONObject.has("missing")) {
			return;
		}

		aggregation.setWeightMissing(valueJSONObject.get("missing"));
	}

	private void _setValueScript(
		WeightedAvgAggregation aggregation, JSONObject valueJSONObject,
		Messages messages) {

		if (!valueJSONObject.has("script")) {
			return;
		}

		Optional<Script> optional = _scriptHelper.getScript(
			valueJSONObject.getJSONObject("script"), messages);

		if (optional.isPresent()) {
			aggregation.setValueScript(optional.get());
		}
	}

	private void _setWeightMissing(
		WeightedAvgAggregation aggregation, JSONObject weightJSONObject) {

		if (!weightJSONObject.has("missing")) {
			return;
		}

		aggregation.setWeightMissing(weightJSONObject.get("missing"));
	}

	private void _setWeightScript(
		WeightedAvgAggregation aggregation, JSONObject weightJSONObject,
		Messages messages) {

		if (!weightJSONObject.has("script")) {
			return;
		}

		Optional<Script> optional = _scriptHelper.getScript(
			weightJSONObject.getJSONObject("script"), messages);

		if (optional.isPresent()) {
			aggregation.setWeightScript(optional.get());
		}
	}

	@Reference
	private Aggregations _aggregations;

	@Reference
	private ScriptHelper _scriptHelper;

}