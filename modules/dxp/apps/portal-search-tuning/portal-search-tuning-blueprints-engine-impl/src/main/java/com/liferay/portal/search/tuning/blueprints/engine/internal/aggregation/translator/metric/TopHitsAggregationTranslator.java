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

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.metrics.TopHitsAggregation;
import com.liferay.portal.search.highlight.Highlight;
import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.script.ScriptField;
import com.liferay.portal.search.script.ScriptFieldBuilder;
import com.liferay.portal.search.script.Scripts;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.aggregation.metric.TopHitsAggregationBodyConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.engine.internal.aggregation.translator.BaseAggregationTranslator;
import com.liferay.portal.search.tuning.blueprints.engine.internal.util.HighlightHelper;
import com.liferay.portal.search.tuning.blueprints.engine.internal.util.ScriptHelper;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterData;
import com.liferay.portal.search.tuning.blueprints.engine.spi.aggregation.AggregationTranslator;
import com.liferay.portal.search.tuning.blueprints.engine.spi.aggregation.AggregationTranslatorFactory;
import com.liferay.portal.search.tuning.blueprints.message.Messages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=top_hits",
	service = AggregationTranslator.class
)
public class TopHitsAggregationTranslator
	extends BaseAggregationTranslator implements AggregationTranslator {

	@Override
	public Optional<Aggregation> translate(
		String aggregationName, JSONObject bodyJSONObject,
		ParameterData parameterData, Messages messages,
		AggregationTranslatorFactory aggregationTranslatorFactory) {

		TopHitsAggregation aggregation = _aggregations.topHits(aggregationName);

		_setDocValueFields(aggregation, bodyJSONObject);
		_setExplain(aggregation, bodyJSONObject);
		_setFrom(aggregation, bodyJSONObject);
		_setHighLight(aggregation, bodyJSONObject, parameterData, messages);
		_setScriptFields(aggregation, bodyJSONObject, messages);
		_setSize(aggregation, bodyJSONObject);
		_setSource(aggregation, bodyJSONObject);
		_setTrackScores(aggregation, bodyJSONObject);
		_setVersion(aggregation, bodyJSONObject);

		return Optional.of(aggregation);
	}

	private String _getScriptFieldName(JSONObject scriptFieldJSONObject) {
		Iterator<String> iterator = scriptFieldJSONObject.keys();

		return iterator.next();
	}

	private void _setDocValueFields(
		TopHitsAggregation aggregation, JSONObject bodyJSONObject) {

		if (!bodyJSONObject.has(
				TopHitsAggregationBodyConfigurationKeys.DOCVALUE_FIELDS.
					getJsonKey())) {

			return;
		}

		JSONArray fieldsJSONArray = bodyJSONObject.getJSONArray(
			TopHitsAggregationBodyConfigurationKeys.DOCVALUE_FIELDS.
				getJsonKey());

		aggregation.setSelectedFields(JSONUtil.toStringList(fieldsJSONArray));
	}

	private void _setExplain(
		TopHitsAggregation aggregation, JSONObject bodyJSONObject) {

		if (!bodyJSONObject.has(
				TopHitsAggregationBodyConfigurationKeys.EXPLAIN.getJsonKey())) {

			return;
		}

		aggregation.setVersion(
			bodyJSONObject.getBoolean(
				TopHitsAggregationBodyConfigurationKeys.EXPLAIN.getJsonKey()));
	}

	private void _setFrom(
		TopHitsAggregation aggregation, JSONObject bodyJSONObject) {

		if (!bodyJSONObject.has(
				TopHitsAggregationBodyConfigurationKeys.FROM.getJsonKey())) {

			return;
		}

		aggregation.setFrom(
			bodyJSONObject.getInt(
				TopHitsAggregationBodyConfigurationKeys.FROM.getJsonKey()));
	}

	private void _setHighLight(
		TopHitsAggregation aggregation, JSONObject bodyJSONObject,
		ParameterData parameterData, Messages messages) {

		if (!bodyJSONObject.has(
				TopHitsAggregationBodyConfigurationKeys.HIGHLIGHT.
					getJsonKey())) {

			return;
		}

		Optional<Highlight> optional = _highlightHelper.getHighlight(
			bodyJSONObject.getJSONObject(
				TopHitsAggregationBodyConfigurationKeys.HIGHLIGHT.getJsonKey()),
			parameterData, messages);

		if (optional.isPresent()) {
			aggregation.setHighlight(optional.get());
		}
	}

	private void _setScriptFields(
		TopHitsAggregation aggregation, JSONObject bodyJSONObject,
		Messages messages) {

		if (!bodyJSONObject.has(
				TopHitsAggregationBodyConfigurationKeys.SCRIPT_FIELDS.
					getJsonKey())) {

			return;
		}

		List<ScriptField> scriptFields = new ArrayList<>();

		JSONArray jsonArray = bodyJSONObject.getJSONArray(
			TopHitsAggregationBodyConfigurationKeys.SCRIPT_FIELDS.getJsonKey());

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject scriptFieldJSONObject = jsonArray.getJSONObject(i);

			String fieldName = _getScriptFieldName(scriptFieldJSONObject);

			JSONObject scriptJSONObject = scriptFieldJSONObject.getJSONObject(
				fieldName);

			Optional<Script> optional = _scriptHelper.getScript(
				scriptJSONObject, messages);

			if (optional.isPresent()) {
				ScriptFieldBuilder scriptFieldBuilder = _scripts.fieldBuilder();

				scriptFieldBuilder.script(optional.get());

				scriptFields.add(scriptFieldBuilder.build());
			}
		}

		aggregation.setScriptFields(scriptFields);
	}

	private void _setSize(
		TopHitsAggregation aggregation, JSONObject bodyJSONObject) {

		if (!bodyJSONObject.has(
				TopHitsAggregationBodyConfigurationKeys.SIZE.getJsonKey())) {

			return;
		}

		aggregation.setFrom(
			bodyJSONObject.getInt(
				TopHitsAggregationBodyConfigurationKeys.SIZE.getJsonKey()));
	}

	private void _setSource(
		TopHitsAggregation aggregation, JSONObject bodyJSONObject) {

		if (!bodyJSONObject.has(
				TopHitsAggregationBodyConfigurationKeys.SOURCE.getJsonKey())) {

			return;
		}

		Object object = bodyJSONObject.get(
			TopHitsAggregationBodyConfigurationKeys.SOURCE.getJsonKey());

		if (object instanceof JSONObject) {
			JSONObject jsonObject = (JSONObject)object;

			if (jsonObject.length() == 0) {
				return;
			}

			_setSourceIncludeExclude(aggregation, (JSONObject)object);
		}
		else {
			aggregation.setFetchSource(GetterUtil.getBoolean(object));
		}
	}

	private void _setSourceIncludeExclude(
		TopHitsAggregation aggregation, JSONObject jsonObject) {

		String[] excludesArray = null;

		JSONArray excludesJSONArray = jsonObject.getJSONArray("excludes");

		if (excludesJSONArray != null) {
			excludesArray = JSONUtil.toStringArray(excludesJSONArray);
		}

		String[] includesArray = null;

		JSONArray includesJSONArray = jsonObject.getJSONArray("includes");

		if (includesJSONArray != null) {
			includesArray = JSONUtil.toStringArray(includesJSONArray);
		}

		aggregation.setFetchSourceIncludeExclude(includesArray, excludesArray);
	}

	private void _setTrackScores(
		TopHitsAggregation aggregation, JSONObject bodyJSONObject) {

		if (!bodyJSONObject.has(
				TopHitsAggregationBodyConfigurationKeys.TRACK_SCORES.
					getJsonKey())) {

			return;
		}

		aggregation.setVersion(
			bodyJSONObject.getBoolean(
				TopHitsAggregationBodyConfigurationKeys.TRACK_SCORES.
					getJsonKey()));
	}

	private void _setVersion(
		TopHitsAggregation aggregation, JSONObject bodyJSONObject) {

		if (!bodyJSONObject.has(
				TopHitsAggregationBodyConfigurationKeys.VERSION.getJsonKey())) {

			return;
		}

		aggregation.setVersion(
			bodyJSONObject.getBoolean(
				TopHitsAggregationBodyConfigurationKeys.VERSION.getJsonKey()));
	}

	@Reference
	private Aggregations _aggregations;

	@Reference
	private HighlightHelper _highlightHelper;

	@Reference
	private ScriptHelper _scriptHelper;

	@Reference
	private Scripts _scripts;

}