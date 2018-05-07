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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.functions;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderInvoker;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponseOutput;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Leonardo Barros
 */
public class CallFunction extends BaseDDMFormRuleFunction {

	public CallFunction(
		DDMDataProviderInvoker ddmDataProviderInvoker,
		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResults,
		HttpServletRequest httpServletRequest, JSONFactory jsonFactory) {

		super(ddmFormFieldEvaluationResults);

		_ddmDataProviderInvoker = ddmDataProviderInvoker;
		_ddmFormFieldEvaluationResults = ddmFormFieldEvaluationResults;
		_httpServletRequest = httpServletRequest;
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
			DDMDataProviderRequest ddmDataProviderRequest =
				new DDMDataProviderRequest(
					ddmDataProviderInstanceUUID, _httpServletRequest);

			addDDMDataProviderRequestParameters(
				ddmDataProviderRequest, paramsExpression);

			DDMDataProviderResponse ddmDataProviderResponse =
				_ddmDataProviderInvoker.invoke(ddmDataProviderRequest);

			Map<String, String> resultMap = extractResults(resultMapExpression);

			setDDMFormFieldValues(ddmDataProviderResponse, resultMap);
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

	protected void addDDMDataProviderRequestParameters(
		DDMDataProviderRequest ddmDataProviderRequest,
		String paramsExpression) {

		Map<String, String> parameters = extractParameters(paramsExpression);

		if (!parameters.isEmpty()) {
			ddmDataProviderRequest.queryString(parameters);
		}
	}

	protected void extractDDMFormFieldValue(
		String expression, Map<String, String> parameters) {

		String[] tokens = StringUtil.split(expression, CharPool.EQUAL);

		String parameterName = tokens[0];

		String parameterValue = StringPool.BLANK;

		if (tokens.length == 2) {
			parameterValue = tokens[1];
		}

		if (_ddmFormFieldEvaluationResults.containsKey(parameterValue)) {
			String ddmFormFieldValue = getDDMFormFieldValue(parameterValue);

			parameters.put(parameterName, ddmFormFieldValue);
		}
		else {
			parameters.put(parameterName, parameterValue);
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

	protected Map<String, String> extractResults(String resultMapExpression) {
		if (Validator.isNull(resultMapExpression)) {
			return Collections.emptyMap();
		}

		Map<String, String> results = new HashMap<>();

		String[] innerExpressions = StringUtil.split(
			resultMapExpression, CharPool.SEMICOLON);

		for (String innerExpression : innerExpressions) {
			String[] tokens = StringUtil.split(innerExpression, CharPool.EQUAL);

			results.put(tokens[0], tokens[1]);
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

		try {
			JSONArray jsonArray = _jsonFactory.createJSONArray(
				String.valueOf(value));

			return (String)jsonArray.get(0);
		}
		catch (JSONException jsone) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsone, jsone);
			}

			return String.valueOf(value);
		}
	}

	protected void setDDMFormFieldOptions(
		String ddmFormFieldName, List<KeyValuePair> options) {

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			getDDMFormFieldEvaluationResult(ddmFormFieldName);

		if (ddmFormFieldEvaluationResult == null) {
			return;
		}

		ddmFormFieldEvaluationResult.setProperty("options", options);

		if (options.size() == 1) {
			KeyValuePair keyValuePair = options.get(0);

			JSONArray valueJSONArray = _jsonFactory.createJSONArray();

			valueJSONArray.put(keyValuePair.getValue());

			ddmFormFieldEvaluationResult.setValue(valueJSONArray);
		}
	}

	protected void setDDMFormFieldValue(String ddmFormFieldName, String value) {
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			getDDMFormFieldEvaluationResult(ddmFormFieldName);

		if (ddmFormFieldEvaluationResult != null) {
			ddmFormFieldEvaluationResult.setValue(value);

			ddmFormFieldEvaluationResult.setProperty("valueChanged", true);
		}
	}

	protected void setDDMFormFieldValues(
		DDMDataProviderResponse ddmDataProviderResponse,
		Map<String, String> resultMap) {

		for (Map.Entry<String, String> entry : resultMap.entrySet()) {
			String ddmFormFieldName = entry.getKey();
			String outputName = entry.getValue();

			DDMDataProviderResponseOutput ddmDataProviderResponseOutput =
				ddmDataProviderResponse.get(outputName);

			if (ddmDataProviderResponseOutput == null) {
				continue;
			}

			if (Objects.equals(
					ddmDataProviderResponseOutput.getType(), "list")) {

				List<KeyValuePair> options =
					ddmDataProviderResponseOutput.getValue(List.class);

				setDDMFormFieldOptions(ddmFormFieldName, options);
			}
			else {
				String value = ddmDataProviderResponseOutput.getValue(
					String.class);

				setDDMFormFieldValue(ddmFormFieldName, value);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(CallFunction.class);

	private final DDMDataProviderInvoker _ddmDataProviderInvoker;
	private final Map<String, List<DDMFormFieldEvaluationResult>>
		_ddmFormFieldEvaluationResults;
	private final HttpServletRequest _httpServletRequest;
	private final JSONFactory _jsonFactory;

}