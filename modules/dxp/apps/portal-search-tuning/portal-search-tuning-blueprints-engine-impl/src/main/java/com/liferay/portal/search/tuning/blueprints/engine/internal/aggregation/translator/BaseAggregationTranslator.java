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

package com.liferay.portal.search.tuning.blueprints.engine.internal.aggregation.translator;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.FieldAggregation;
import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.aggregation.AggregationConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.engine.internal.util.ScriptHelper;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterData;
import com.liferay.portal.search.tuning.blueprints.engine.spi.aggregation.AggregationTranslator;
import com.liferay.portal.search.tuning.blueprints.engine.spi.aggregation.AggregationTranslatorFactory;
import com.liferay.portal.search.tuning.blueprints.message.Message;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.message.Severity;

import java.util.Optional;

/**
 * @author Petteri Karttunen
 */
public abstract class BaseAggregationTranslator {

	protected void setChildAggregations(
		Aggregation aggregation,
		AggregationTranslatorFactory aggregationTranslatorFactory,
		ParameterData parameterData, JSONObject bodyJSONObject,
		Messages messages) {

		if (!bodyJSONObject.has("aggs")) {
			return;
		}

		JSONArray childAggregationJSONArray = bodyJSONObject.getJSONArray(
			"aggs");

		for (int i = 0; i < childAggregationJSONArray.length(); i++) {
			JSONObject childAggregationJSONObject =
				childAggregationJSONArray.getJSONObject(i);

			try {
				Optional<Aggregation> aggregationOptional =
					_getChildAggregation(
						aggregationTranslatorFactory, parameterData,
						childAggregationJSONObject, messages);

				if (aggregationOptional.isPresent()) {
					aggregation.addChildAggregation(aggregationOptional.get());
				}
			}
			catch (IllegalArgumentException illegalArgumentException) {
				_handleChildAggregationException(
					illegalArgumentException, childAggregationJSONObject,
					messages);
			}
		}
	}

	protected void setMissing(
		FieldAggregation aggregation, JSONObject bodyJSONObject) {

		if (!bodyJSONObject.has("missing")) {
			return;
		}

		aggregation.setMissing(bodyJSONObject.getString("missing"));
	}

	protected void setScript(
		FieldAggregation fieldAggregation, ScriptHelper aggregationScriptHelper,
		JSONObject bodyJSONObject, Messages messages) {

		JSONObject scriptJSONObject = bodyJSONObject.getJSONObject("script");

		if ((scriptJSONObject == null) || (scriptJSONObject.length() == 0)) {
			return;
		}

		Optional<Script> scriptOptional = aggregationScriptHelper.getScript(
			scriptJSONObject, messages);

		if (scriptOptional.isPresent()) {
			fieldAggregation.setScript(scriptOptional.get());
		}
	}

	protected boolean validateField(
		JSONObject bodyJSONObject, Messages messages) {

		if (!bodyJSONObject.has("field")) {
			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"core.error.undefined-aggregation-field"
				).msg(
					"Aggregation field is not defined"
				).rootObject(
					bodyJSONObject
				).rootProperty(
					"field"
				).severity(
					Severity.ERROR
				).build());

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Aggregation field is not defined [ " + bodyJSONObject +
						"]");
			}

			return false;
		}

		return true;
	}

	private Optional<Aggregation> _getChildAggregation(
		AggregationTranslatorFactory aggregationTranslatorFactory,
		ParameterData parameterData, JSONObject childAggregationJSONObject,
		Messages messages) {

		String type = childAggregationJSONObject.getString(
			AggregationConfigurationKeys.TYPE.getJsonKey());

		String name = childAggregationJSONObject.getString(
			AggregationConfigurationKeys.NAME.getJsonKey());

		AggregationTranslator aggregationBuilder =
			aggregationTranslatorFactory.getTranslator(type);

		return aggregationBuilder.translate(
			name,
			childAggregationJSONObject.getJSONObject(
				AggregationConfigurationKeys.BODY.getJsonKey()),
			parameterData, messages, aggregationTranslatorFactory);
	}

	private void _handleChildAggregationException(
		IllegalArgumentException illegalArgumentException,
		JSONObject childAggregationJSONObject, Messages messages) {

		messages.addMessage(
			new Message.Builder().className(
				getClass().getName()
			).localizationKey(
				"core.error.invalid-aggregation-builder-type"
			).msg(
				illegalArgumentException.getMessage()
			).rootObject(
				childAggregationJSONObject
			).rootProperty(
				"aggs"
			).rootValue(
				childAggregationJSONObject.getString(
					AggregationConfigurationKeys.TYPE.getJsonKey())
			).severity(
				Severity.ERROR
			).throwable(
				illegalArgumentException
			).build());

		_log.error(
			illegalArgumentException.getMessage(), illegalArgumentException);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseAggregationTranslator.class);

}