/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.dynamic.data.mapping.form.evaluator.impl.internal.functions;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderConsumer;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderConsumerRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderConsumerResponse;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderConsumerTracker;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderContext;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class CallFunction extends BaseDDMFormRuleFunction {

	public CallFunction(
		DDMDataProviderConsumerTracker ddmDataProviderConsumerTracker,
		DDMDataProviderInstanceService ddmDataProviderInstanceService,
		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResults,
		DDMFormValuesJSONDeserializer ddmFormValuesJSONDeserializer,
		JSONFactory jsonFactory) {

		super(ddmFormFieldEvaluationResults);

		_ddmDataProviderConsumerTracker = ddmDataProviderConsumerTracker;
		_ddmDataProviderInstanceService = ddmDataProviderInstanceService;
		_ddmFormFieldEvaluationResults = ddmFormFieldEvaluationResults;
		_ddmFormValuesJSONDeserializer = ddmFormValuesJSONDeserializer;
		_jsonFactory = jsonFactory;
	}

	@Override
	public Object evaluate(Object... parameters) {
		if (parameters.length < 3) {
			throw new IllegalArgumentException(
				String.format(
					"Expected 3 parameters, received %d", parameters.length));
		}

		String ddmDataProviderInstanceUUID = String.valueOf(parameters[0]);
		String paramsExpression = String.valueOf(parameters[1]);
		String resultMapExpression = String.valueOf(parameters[2]);

		try {
			DDMDataProviderConsumerResponse ddmDataProviderConsumerResponse =
				executeDataProvider(
					ddmDataProviderInstanceUUID, paramsExpression);

			Map<String, Object> resultMap = extractResults(resultMapExpression);

			setDDMFormFieldValues(ddmDataProviderConsumerResponse, resultMap);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Error evaluating expression: " +
						ArrayUtil.toString(parameters, (String)null),
					e);
			}
		}

		return null;
	}

	protected void addDDMDataProviderContextParameters(
		DDMDataProviderContext ddmDataProviderContext,
		String paramsExpression) {

		Map<String, String> parameters = extractParameters(paramsExpression);

		if (!parameters.isEmpty()) {
			ddmDataProviderContext.addParameters(parameters);
		}
	}

	protected Object createResultMapValue(String token) {
		try {
			token = token.replace(StringPool.APOSTROPHE, StringPool.QUOTE);

			return _jsonFactory.createJSONObject(token);
		}
		catch (JSONException jsone) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsone, jsone);
			}

			return token;
		}
	}

	protected DDMDataProviderConsumerResponse executeDataProvider(
			String ddmDataProviderInstanceUUID, String paramsExpression)
		throws PortalException {

		DDMDataProviderInstance ddmDataProviderInstance =
			_ddmDataProviderInstanceService.getDataProviderInstanceByUuid(
				ddmDataProviderInstanceUUID);

		DDMDataProviderConsumer ddmDataProviderConsumer =
			_ddmDataProviderConsumerTracker.getDDMDataProviderConsumer(
				ddmDataProviderInstance.getType());

		DDMForm ddmForm = DDMFormFactory.create(
			ddmDataProviderConsumer.getSettings());

		DDMFormValues ddmFormValues =
			_ddmFormValuesJSONDeserializer.deserialize(
				ddmForm, ddmDataProviderInstance.getDefinition());

		DDMDataProviderContext ddmDataProviderContext =
			new DDMDataProviderContext(ddmFormValues);

		addDDMDataProviderContextParameters(
			ddmDataProviderContext, paramsExpression);

		DDMDataProviderConsumerRequest ddmDataProviderConsumerRequest =
			new DDMDataProviderConsumerRequest(ddmDataProviderContext);

		return ddmDataProviderConsumer.execute(ddmDataProviderConsumerRequest);
	}

	protected void extractDDMFormFieldValue(
		String expression, Map<String, String> parameters) {

		String[] tokens = StringUtil.split(expression, CharPool.EQUAL);

		if (_ddmFormFieldEvaluationResults.containsKey(tokens[1])) {
			String ddmFormFieldValue = getDDMFormFieldValue(tokens[1]);

			parameters.put(tokens[0], ddmFormFieldValue);
		}
		else {
			parameters.put(tokens[0], tokens[1]);
		}
	}

	protected Map<String, String> extractParameters(String expression) {
		if (Validator.isNull(expression)) {
			return Collections.emptyMap();
		}

		Map<String, String> parameters = new HashMap<>();

		String[] innerExpressions = StringUtil.split(
			expression, CharPool.SEMICOLON);

		if (innerExpressions.length == 0) {
			extractDDMFormFieldValue(expression, parameters);
		}
		else {
			for (String innerExpression : innerExpressions) {
				extractDDMFormFieldValue(innerExpression, parameters);
			}
		}

		return parameters;
	}

	protected Map<String, Object> extractResults(String expression) {
		if (Validator.isNull(expression)) {
			return Collections.emptyMap();
		}

		Map<String, Object> results = new HashMap<>();

		String[] innerExpressions = StringUtil.split(
			expression, CharPool.SEMICOLON);

		if (innerExpressions.length == 0) {
			String[] tokens = StringUtil.split(expression, CharPool.EQUAL);

			results.put(tokens[0], createResultMapValue(tokens[1]));
		}
		else {
			for (String innerExpression : innerExpressions) {
				String[] tokens = StringUtil.split(
					innerExpression, CharPool.EQUAL);

				results.put(tokens[0], createResultMapValue(tokens[1]));
			}
		}

		return results;
	}

	protected DDMFormFieldEvaluationResult getDDMFormFieldEvaluationResult(
		String ddmFormFieldName) {

		if (!_ddmFormFieldEvaluationResults.containsKey(ddmFormFieldName)) {
			return null;
		}

		List<DDMFormFieldEvaluationResult>
			ddmFormFieldInstancesEvaluationResults =
				_ddmFormFieldEvaluationResults.get(ddmFormFieldName);

		return ddmFormFieldInstancesEvaluationResults.get(0);
	}

	protected String getDDMFormFieldValue(String ddmFormFieldName) {
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			getDDMFormFieldEvaluationResult(ddmFormFieldName);

		if (ddmFormFieldEvaluationResult == null) {
			return StringPool.BLANK;
		}

		Object value = ddmFormFieldEvaluationResult.getProperty("value");

		if (Validator.isNull(value)) {
			return StringPool.BLANK;
		}

		return String.valueOf(value);
	}

	protected void setDDMFormFieldOptions(
		List<Map<Object, Object>> data, String ddmFormFieldName,
		String keyProperty, String valueProperty) {

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			getDDMFormFieldEvaluationResult(ddmFormFieldName);

		if (ddmFormFieldEvaluationResult == null) {
			return;
		}

		List<KeyValuePair> options = ddmFormFieldEvaluationResult.getProperty(
			"options");

		for (Map<Object, Object> dataMap : data) {
			Object key = dataMap.getOrDefault(keyProperty, StringPool.BLANK);

			Object value = dataMap.getOrDefault(
				valueProperty, StringPool.BLANK);

			KeyValuePair keyValuePair = new KeyValuePair(
				String.valueOf(key), String.valueOf(value));

			options.add(keyValuePair);
		}
	}

	protected void setDDMFormFieldValue(
		Map<Object, Object> data, String ddmFormFieldName,
		String propertyName) {

		if (!data.containsKey(propertyName)) {
			return;
		}

		Object value = data.get(propertyName);

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			getDDMFormFieldEvaluationResult(ddmFormFieldName);

		if (ddmFormFieldEvaluationResult != null) {
			ddmFormFieldEvaluationResult.setProperty("value", value);
		}
	}

	protected void setDDMFormFieldValues(
		DDMDataProviderConsumerResponse ddmDataProviderConsumerResponse,
		Map<String, Object> results) {

		List<Map<Object, Object>> data =
			ddmDataProviderConsumerResponse.getData();

		for (Map.Entry<String, Object> entry : results.entrySet()) {
			Object value = entry.getValue();

			if (value instanceof JSONObject) {
				JSONObject jsonObject = (JSONObject)value;

				setDDMFormFieldOptions(
					data, entry.getKey(), jsonObject.getString("key"),
					jsonObject.getString("value"));
			}
			else {
				setDDMFormFieldValue(
					data.get(0), entry.getKey(), String.valueOf(value));
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(CallFunction.class);

	private final DDMDataProviderConsumerTracker
		_ddmDataProviderConsumerTracker;
	private final DDMDataProviderInstanceService
		_ddmDataProviderInstanceService;
	private final Map<String, List<DDMFormFieldEvaluationResult>>
		_ddmFormFieldEvaluationResults;
	private final DDMFormValuesJSONDeserializer _ddmFormValuesJSONDeserializer;
	private final JSONFactory _jsonFactory;

}