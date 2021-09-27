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
import com.liferay.portal.search.aggregation.metrics.ScriptedMetricAggregation;
import com.liferay.portal.search.script.Script;
import com.liferay.search.experiences.internal.blueprint.aggregation.AggregationWrapper;
import com.liferay.search.experiences.internal.blueprint.aggregation.translator.AggregationTranslator;
import com.liferay.search.experiences.internal.blueprint.aggregation.util.AggregationHelper;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterData;
import com.liferay.search.experiences.internal.blueprint.util.SetterHelper;

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
	implements AggregationTranslator {

	@Override
	public Optional<AggregationWrapper> translate(
		String aggregationName, JSONObject jsonObject,
		SXPParameterData sxpParameterData) {

		Optional<Script> mapScriptOptional = _aggregationHelper.getScript(
			jsonObject.get("map_script"));

		if (!mapScriptOptional.isPresent()) {
			return Optional.empty();
		}

		ScriptedMetricAggregation aggregation = _aggregations.scriptedMetric(
			aggregationName);

		aggregation.setMapScript(mapScriptOptional.get());

		_aggregationHelper.setScript(
			jsonObject, "combine_script", aggregation::setCombineScript);

		_aggregationHelper.setScript(
			jsonObject, "init_script", aggregation::setInitScript);

		_setParams(aggregation, jsonObject);

		_aggregationHelper.setScript(
			jsonObject, "reduce_script", aggregation::setReduceScript);

		return _aggregationHelper.wrap(aggregation);
	}

	private void _setParams(
		ScriptedMetricAggregation aggregation, JSONObject jsonObject) {

		JSONObject paramsJSONObject = jsonObject.getJSONObject("params");

		if (paramsJSONObject == null) {
			return;
		}

		Map<String, Object> params = new HashMap<>();

		Iterator<String> iterator = paramsJSONObject.keys();

		while (iterator.hasNext()) {
			String key = iterator.next();

			params.put(key, paramsJSONObject.get(key));
		}

		aggregation.setParameters(params);
	}

	@Reference
	private AggregationHelper _aggregationHelper;

	@Reference
	private Aggregations _aggregations;

	@Reference
	private SetterHelper _setterHelper;

}