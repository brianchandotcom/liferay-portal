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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.rules.functions;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderConsumer;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderConsumerRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderConsumerResponse;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderConsumerTracker;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderContext;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class CallFunction extends BasePropertyFunction {

	public CallFunction(
		DDMDataProviderConsumerTracker ddmDataProviderConsumerTracker,
		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResults,
		DDMFormValuesJSONDeserializer ddmFormValuesJSONDeserializer,
		JSONFactory jsonFactory) {

		super(ddmFormFieldEvaluationResults);

		_ddmDataProviderConsumerTracker = ddmDataProviderConsumerTracker;
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
				_executeDataProvider(
					ddmDataProviderInstanceUUID, paramsExpression);

			Map<String, Object> resultMap = _extractResultMap(
				resultMapExpression);

			_setDDMFormFieldValues(ddmDataProviderConsumerResponse, resultMap);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return null;
	}

	private void _addDDMDataProviderContextParameters(
		DDMDataProviderContext ddmDataProviderContext,
		String paramsExpression) {

		Map<String, String> parameters = _extractParameters(paramsExpression);

		if (parameters.size() == 0) {
			return;
		}

		ddmDataProviderContext.addParameters(parameters);
	}

	private Object _createResultMapValue(String token) {
		try {
			token = token.replace(StringPool.APOSTROPHE, StringPool.QUOTE);
			return _jsonFactory.createJSONObject(token);
		}
		catch (JSONException jsone) {
			return token;
		}
	}

	private DDMDataProviderConsumerResponse _executeDataProvider(
			String ddmDataProviderInstanceUUID, String paramsExpression)
		throws PortalException {

		DDMDataProviderInstance ddmDataProviderInstance =
			DDMDataProviderInstanceServiceUtil.findByUuid(
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

		_addDDMDataProviderContextParameters(
			ddmDataProviderContext, paramsExpression);

		DDMDataProviderConsumerRequest ddmDataProviderConsumerRequest =
			new DDMDataProviderConsumerRequest(ddmDataProviderContext);

		return ddmDataProviderConsumer.execute(ddmDataProviderConsumerRequest);
	}

	private void _extractDDMFormFieldValue(
		String expression, Map<String, String> paramsMap) {

		String[] tokens = StringUtil.split(expression, CharPool.EQUAL);

		if (_ddmFormFieldEvaluationResults.containsKey(tokens[1])) {
			String ddmFormFieldValue = _getDDMFormFieldValue(tokens[1]);
			paramsMap.put(tokens[0], ddmFormFieldValue);
		}
		else {
			paramsMap.put(tokens[0], tokens[1]);
		}
	}

	private Map<String, String> _extractParameters(String expression) {
		Map<String, String> paramsMap = new HashMap<>();

		if (Validator.isNull(expression)) {
			return paramsMap;
		}

		String[] innerExpressions = StringUtil.split(
			expression, CharPool.SEMICOLON);

		if (innerExpressions.length == 0) {
			_extractDDMFormFieldValue(expression, paramsMap);
		}
		else {
			for (String innerExpression : innerExpressions) {
				_extractDDMFormFieldValue(innerExpression, paramsMap);
			}
		}

		return paramsMap;
	}

	private Map<String, Object> _extractResultMap(String expression) {
		Map<String, Object> resultMap = new HashMap<>();

		if (Validator.isNull(expression)) {
			return resultMap;
		}

		String[] innerExpressions = StringUtil.split(
			expression, CharPool.SEMICOLON);

		if (innerExpressions.length == 0) {
			String[] tokens = StringUtil.split(expression, CharPool.EQUAL);
			resultMap.put(tokens[0], _createResultMapValue(tokens[1]));
		}
		else {
			for (String innerExpression : innerExpressions) {
				String[] tokens = StringUtil.split(
					innerExpression, CharPool.EQUAL);
				resultMap.put(tokens[0], _createResultMapValue(tokens[1]));
			}
		}

		return resultMap;
	}

	private DDMFormFieldEvaluationResult _getDDMFormFieldEvaluationResult(
		String ddmFormFieldName) {

		if (!_ddmFormFieldEvaluationResults.containsKey(ddmFormFieldName)) {
			return null;
		}

		List<DDMFormFieldEvaluationResult>
			ddmFormFieldInstancesEvaluationResults =
				_ddmFormFieldEvaluationResults.get(ddmFormFieldName);

		return ddmFormFieldInstancesEvaluationResults.get(0);
	}

	private String _getDDMFormFieldValue(String ddmFormFieldName) {
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			_getDDMFormFieldEvaluationResult(ddmFormFieldName);

		if (ddmFormFieldEvaluationResult == null) {
			return StringPool.BLANK;
		}

		Object value = ddmFormFieldEvaluationResult.getValue();

		if (Validator.isNull(value)) {
			return StringPool.BLANK;
		}

		return String.valueOf(value);
	}

	private void _setDDMFormFieldOptions(
		List<Map<Object, Object>> data, String ddmFormFieldName,
		String keyProperty, String valueProperty) {

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			_getDDMFormFieldEvaluationResult(ddmFormFieldName);

		if (ddmFormFieldEvaluationResult == null) {
			return;
		}

		List<KeyValuePair> options = ddmFormFieldEvaluationResult.getOptions();

		for (Map<Object, Object> dataMap : data) {
			Object key = dataMap.getOrDefault(keyProperty, StringPool.BLANK);
			Object value = dataMap.getOrDefault(
				valueProperty, StringPool.BLANK);
			options.add(
				new KeyValuePair(String.valueOf(key), String.valueOf(value)));
		}
	}

	private void _setDDMFormFieldValue(
		Map<Object, Object> dataMap, String ddmFormFieldName,
		String propertyName) {

		if (!dataMap.containsKey(propertyName)) {
			return;
		}

		Object value = dataMap.get(propertyName);

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			_getDDMFormFieldEvaluationResult(ddmFormFieldName);

		if (ddmFormFieldEvaluationResult != null) {
			ddmFormFieldEvaluationResult.setValue(value);
		}
	}

	private void _setDDMFormFieldValues(
		DDMDataProviderConsumerResponse ddmDataProviderConsumerResponse,
		Map<String, Object> resultMap) {

		List<Map<Object, Object>> data =
			ddmDataProviderConsumerResponse.getData();

		for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
			Object value = entry.getValue();

			if (value instanceof JSONObject) {
				JSONObject jsonObject = (JSONObject)value;

				_setDDMFormFieldOptions(
					data, entry.getKey(), jsonObject.getString("key"),
					jsonObject.getString("value"));
			}
			else {
				_setDDMFormFieldValue(
					data.get(0), entry.getKey(), String.valueOf(value));
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(CallFunction.class);

	private final DDMDataProviderConsumerTracker
		_ddmDataProviderConsumerTracker;
	private final Map<String, List<DDMFormFieldEvaluationResult>>
		_ddmFormFieldEvaluationResults;
	private final DDMFormValuesJSONDeserializer _ddmFormValuesJSONDeserializer;
	private final JSONFactory _jsonFactory;

}