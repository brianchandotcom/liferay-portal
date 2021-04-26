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
import com.liferay.portal.search.aggregation.metrics.ScriptedMetricAggregation;
import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.aggregation.metric.ScriptedMetricAggregationBodyConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.engine.internal.aggregation.translator.BaseAggregationTranslator;
import com.liferay.portal.search.tuning.blueprints.engine.internal.util.ScriptHelper;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterData;
import com.liferay.portal.search.tuning.blueprints.engine.spi.aggregation.AggregationTranslator;
import com.liferay.portal.search.tuning.blueprints.engine.spi.aggregation.AggregationTranslatorFactory;
import com.liferay.portal.search.tuning.blueprints.message.Message;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.message.Severity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=scripted_metric",
	service = AggregationTranslator.class
)
public class ScriptedMetricAggregationTranslator
	extends BaseAggregationTranslator implements AggregationTranslator {

	@Override
	public Optional<Aggregation> translate(
		String aggregationName, JSONObject bodyJSONObject,
		ParameterData parameterData, Messages messages,
		AggregationTranslatorFactory aggregationTranslatorFactory) {

		Optional<Script> mapScriptOptional = _getScript(
			bodyJSONObject.getJSONObject(
				ScriptedMetricAggregationBodyConfigurationKeys.MAP_SCRIPT.
					getJsonKey()),
			messages);

		if (!mapScriptOptional.isPresent()) {
			_addValidationMessage(bodyJSONObject, messages);

			return Optional.empty();
		}

		ScriptedMetricAggregation aggregation = _aggregations.scriptedMetric(
			aggregationName);

		aggregation.setMapScript(mapScriptOptional.get());

		_setCombineScript(aggregation, bodyJSONObject, messages);

		_setInitScript(aggregation, bodyJSONObject, messages);

		_setParams(aggregation, bodyJSONObject);

		_setReduceScript(aggregation, bodyJSONObject, messages);

		return Optional.of(aggregation);
	}

	private void _addValidationMessage(
		JSONObject bodyJSONObject, Messages messages) {

		messages.addMessage(
			new Message.Builder().className(
				getClass().getName()
			).localizationKey(
				"core.error.invalid-or-missing-map-script"
			).msg(
				"Invalid or missing map script"
			).rootObject(
				bodyJSONObject
			).rootProperty(
				"field"
			).severity(
				Severity.ERROR
			).build());
	}

	private Optional<Script> _getScript(
		JSONObject scriptJSONObject, Messages messages) {

		if ((scriptJSONObject == null) || (scriptJSONObject.length() == 0)) {
			return Optional.empty();
		}

		return _aggregationScriptHelper.getScript(scriptJSONObject, messages);
	}

	private void _setCombineScript(
		ScriptedMetricAggregation aggregation, JSONObject bodyJSONObject,
		Messages messages) {

		Optional<Script> optional = _getScript(
			bodyJSONObject.getJSONObject(
				ScriptedMetricAggregationBodyConfigurationKeys.COMBINE_SCRIPT.
					getJsonKey()),
			messages);

		if (optional.isPresent()) {
			aggregation.setCombineScript(optional.get());
		}
	}

	private void _setInitScript(
		ScriptedMetricAggregation aggregation, JSONObject bodyJSONObject,
		Messages messages) {

		Optional<Script> optional = _getScript(
			bodyJSONObject.getJSONObject(
				ScriptedMetricAggregationBodyConfigurationKeys.INIT_SCRIPT.
					getJsonKey()),
			messages);

		if (optional.isPresent()) {
			aggregation.setInitScript(optional.get());
		}
	}

	private void _setParams(
		ScriptedMetricAggregation aggregation, JSONObject bodyJSONObject) {

		if (!bodyJSONObject.has(
				ScriptedMetricAggregationBodyConfigurationKeys.PARAMS.
					getJsonKey())) {

			return;
		}

		JSONObject paramsJSONObject = bodyJSONObject.getJSONObject(
			ScriptedMetricAggregationBodyConfigurationKeys.PARAMS.getJsonKey());

		Map<String, Object> params = new HashMap<>();

		Iterator<String> iterator = paramsJSONObject.keys();

		while (iterator.hasNext()) {
			String key = iterator.next();

			params.put(key, paramsJSONObject.get(key));
		}

		aggregation.setParameters(params);
	}

	private void _setReduceScript(
		ScriptedMetricAggregation aggregation, JSONObject bodyJSONObject,
		Messages messages) {

		Optional<Script> optional = _getScript(
			bodyJSONObject.getJSONObject(
				ScriptedMetricAggregationBodyConfigurationKeys.REDUCE_SCRIPT.
					getJsonKey()),
			messages);

		if (optional.isPresent()) {
			aggregation.setReduceScript(optional.get());
		}
	}

	@Reference
	private Aggregations _aggregations;

	@Reference
	private ScriptHelper _aggregationScriptHelper;

}