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

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderContext;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderException;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderOutputParametersSettings;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderParameterSettings;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderTracker;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class CallFunction extends BaseDDMFormRuleFunction {

	public CallFunction(
		DDMDataProviderTracker ddmDataProviderTracker,
		DDMDataProviderInstanceService ddmDataProviderInstanceService,
		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResults,
		DDMFormValuesJSONDeserializer ddmFormValuesJSONDeserializer,
		JSONFactory jsonFactory) {

		super(ddmFormFieldEvaluationResults);

		_ddmDataProviderTracker = ddmDataProviderTracker;
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
			DDMDataProvider ddmDataProvider =
				_ddmDataProviderTracker.getDDMDataProviderByInstanceId(
					ddmDataProviderInstanceUUID);

			DDMDataProviderContext ddmDataProviderContext = null;

			if (ddmDataProvider != null) {
				ddmDataProviderContext = new DDMDataProviderContext(null);
			}
			else {
				DDMDataProviderInstance ddmDataProviderInstance =
					_ddmDataProviderInstanceService.
						getDataProviderInstanceByUuid(
							ddmDataProviderInstanceUUID);

				ddmDataProvider = _ddmDataProviderTracker.getDDMDataProvider(
					ddmDataProviderInstance.getType());

				ddmDataProviderContext = createDDMDataProviderContext(
					ddmDataProvider, ddmDataProviderInstance);
			}

			addDDMDataProviderContextParameters(
				ddmDataProviderContext, paramsExpression);

			DDMDataProviderResponse ddmDataProviderResponse =
				executeDataProvider(ddmDataProvider, ddmDataProviderContext);

			Map<String, String[]> outputParameterNameToPathsMap =
				getOutputParameterNameToPathsMap(
					ddmDataProvider, ddmDataProviderContext);

			Map<String, Object> resultMap = extractResults(
				resultMapExpression, outputParameterNameToPathsMap);

			setDDMFormFieldValues(
				ddmDataProviderResponse, resultMap,
				outputParameterNameToPathsMap);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Error evaluating expression: " +
						ArrayUtil.toString(parameters, (String)null),
					e);
			}
		}

		return true;
	}

	protected void addDDMDataProviderContextParameters(
		DDMDataProviderContext ddmDataProviderContext,
		String paramsExpression) {

		Map<String, String> parameters = extractParameters(paramsExpression);

		if (!parameters.isEmpty()) {
			ddmDataProviderContext.addParameters(parameters);
		}
	}

	protected DDMDataProviderContext createDDMDataProviderContext(
			DDMDataProvider ddmDataProvider,
			DDMDataProviderInstance ddmDataProviderInstance)
		throws Exception {

		DDMForm ddmForm = DDMFormFactory.create(ddmDataProvider.getSettings());

		DDMFormValues ddmFormValues =
			_ddmFormValuesJSONDeserializer.deserialize(
				ddmForm, ddmDataProviderInstance.getDefinition());

		DDMDataProviderContext ddmDataProviderContext =
			new DDMDataProviderContext(ddmFormValues);

		return ddmDataProviderContext;
	}

	protected JSONObject createKeyValueMappingJSONObject(String[] paths) {
		JSONObject keyValueJSONObject = _jsonFactory.createJSONObject();

		keyValueJSONObject.put("key", paths[0]);
		keyValueJSONObject.put("value", paths[1]);

		return keyValueJSONObject;
	}

	protected Object createResultMapValue(
		String token, Map<String, String[]> outputParameterNameToPathsMap) {

		String[] paths = GetterUtil.getStringValues(
			outputParameterNameToPathsMap.get(token));

		if (isKeyValueMapping(paths)) {
			return createKeyValueMappingJSONObject(paths);
		}

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

	protected DDMDataProviderResponse executeDataProvider(
			DDMDataProvider ddmDataProvider,
			DDMDataProviderContext ddmDataProviderContext)
		throws DDMDataProviderException {

		DDMDataProviderRequest ddmDataProviderRequest =
			new DDMDataProviderRequest(ddmDataProviderContext);

		return ddmDataProvider.getData(ddmDataProviderRequest);
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

	protected Map<String, Object> extractResults(
		String expression,
		Map<String, String[]> outputParameterNameToPathsMap) {

		if (Validator.isNull(expression)) {
			return Collections.emptyMap();
		}

		Map<String, Object> results = new HashMap<>();

		String[] innerExpressions = StringUtil.split(
			expression, CharPool.SEMICOLON);

		for (String innerExpression : innerExpressions) {
			String[] tokens = StringUtil.split(innerExpression, CharPool.EQUAL);

			results.put(
				tokens[0],
				createResultMapValue(tokens[1], outputParameterNameToPathsMap));
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

	protected Map<String, String[]> getOutputParameterNameToPathsMap(
		DDMDataProvider ddmDataProvider,
		DDMDataProviderContext ddmDataProviderContext) {

		Map<String, String[]> outputParameterNameToPathsMap = new HashMap<>();

		if (Validator.isNull(ddmDataProviderContext.getDDMFormValues()) ||
			!ClassUtil.isSubclass(
				ddmDataProvider.getSettings(),
				DDMDataProviderParameterSettings.class)) {

			return outputParameterNameToPathsMap;
		}

		DDMDataProviderParameterSettings ddmDataProviderParameterSettings =
			(DDMDataProviderParameterSettings)
				ddmDataProviderContext.getSettingsInstance(
					ddmDataProvider.getSettings());

		for (DDMDataProviderOutputParametersSettings
				ddmDataProviderOutputParameterSetting :
					ddmDataProviderParameterSettings.outputParameters()) {

			String[] paths = StringUtil.split(
				ddmDataProviderOutputParameterSetting.outputParameterPath(),
				CharPool.SEMICOLON);

			if (isListOutputParameterWithOnePath(
					ddmDataProviderOutputParameterSetting, paths)) {

				paths = ArrayUtil.append(paths, paths[0]);
			}

			outputParameterNameToPathsMap.put(
				ddmDataProviderOutputParameterSetting.outputParameterName(),
				paths);
		}

		return outputParameterNameToPathsMap;
	}

	protected String getOutputParameterType(
		DDMDataProviderOutputParametersSettings
			ddmDataProviderOutputParametersSettings) {

		String outputParameterType =
			ddmDataProviderOutputParametersSettings.outputParameterType();

		try {
			JSONArray jsonArray = _jsonFactory.createJSONArray(
				outputParameterType);

			return jsonArray.getString(0);
		}
		catch (JSONException jsone) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsone, jsone);
			}
		}

		return outputParameterType;
	}

	protected String getPropertyName(
		String propertyName,
		Map<String, String[]> outputParameterNameToPathsMap) {

		if (outputParameterNameToPathsMap.containsKey(propertyName)) {
			String[] paths = outputParameterNameToPathsMap.get(propertyName);

			return paths[0];
		}

		return propertyName;
	}

	protected boolean isKeyValueMapping(String[] paths) {
		if (paths.length == 2) {
			return true;
		}

		return false;
	}

	protected boolean isListOutputParameterWithOnePath(
		DDMDataProviderOutputParametersSettings
			ddmDataProviderOutputParametersSettings, String[] paths) {

		String outputType = getOutputParameterType(
			ddmDataProviderOutputParametersSettings);

		if (outputType.equals("list") && (paths.length == 1)) {
			return true;
		}

		return false;
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

		if (options == null) {
			options = new ArrayList<>();

			ddmFormFieldEvaluationResult.setProperty("options", options);
		}

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
			ddmFormFieldEvaluationResult.setValue(value);
		}
	}

	protected void setDDMFormFieldValues(
		DDMDataProviderResponse ddmDataProviderResponse,
		Map<String, Object> resultMap,
		Map<String, String[]> outputParameterNameToPathsMap) {

		List<Map<Object, Object>> data = ddmDataProviderResponse.getData();

		if (ListUtil.isEmpty(data)) {
			return;
		}

		for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
			String ddmFormFieldName = entry.getKey();

			Object value = entry.getValue();

			if (value instanceof JSONObject) {
				JSONObject jsonObject = (JSONObject)value;

				setDDMFormFieldOptions(
					data, ddmFormFieldName, jsonObject.getString("key"),
					jsonObject.getString("value"));
			}
			else {
				String propertyName = getPropertyName(
					String.valueOf(value), outputParameterNameToPathsMap);

				setDDMFormFieldValue(
					data.get(0), ddmFormFieldName, propertyName);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(CallFunction.class);

	private final DDMDataProviderInstanceService
		_ddmDataProviderInstanceService;
	private final DDMDataProviderTracker _ddmDataProviderTracker;
	private final Map<String, List<DDMFormFieldEvaluationResult>>
		_ddmFormFieldEvaluationResults;
	private final DDMFormValuesJSONDeserializer _ddmFormValuesJSONDeserializer;
	private final JSONFactory _jsonFactory;

}