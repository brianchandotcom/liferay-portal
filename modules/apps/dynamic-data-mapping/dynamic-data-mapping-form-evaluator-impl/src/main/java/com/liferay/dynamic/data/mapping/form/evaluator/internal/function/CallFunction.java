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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderInvoker;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFieldAccessor;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFieldAccessorAware;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionObserver;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionObserverAware;
import com.liferay.dynamic.data.mapping.expression.GetFieldPropertyRequest;
import com.liferay.dynamic.data.mapping.expression.GetFieldPropertyResponse;
import com.liferay.dynamic.data.mapping.expression.UpdateFieldPropertyRequest;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Leonardo Barros
 */
public class CallFunction
	implements DDMExpressionFieldAccessorAware,
			   DDMExpressionFunction.Function3<String, String, String, Boolean>,
			   DDMExpressionObserverAware {

	public static final String NAME = "call";

	public CallFunction(
		DDMDataProviderInvoker ddmDataProviderInvoker,
		JSONFactory jsonFactory) {

		this.ddmDataProviderInvoker = ddmDataProviderInvoker;
		this.jsonFactory = jsonFactory;
	}

	@Override
	public Boolean apply(
		String ddmDataProviderInstanceUUID, String paramsExpression,
		String resultMapExpression) {

		if (_ddmExpressionFieldAccessor == null) {
			return false;
		}

		try {
			DDMDataProviderRequest.Builder builder =
				DDMDataProviderRequest.Builder.newBuilder();

			builder = builder.withDDMDataProviderId(
				ddmDataProviderInstanceUUID);

			Map<String, String> parameterMap = extractParameters(
				paramsExpression);

			for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
				if (Objects.equals(entry.getKey(), "locale")) {
					builder = builder.withLocale(
						LocaleUtil.fromLanguageId(entry.getValue()));

					continue;
				}

				builder = builder.withParameter(
					entry.getKey(), entry.getValue());
			}

			DDMDataProviderRequest ddmDataProviderRequest = builder.build();

			DDMDataProviderResponse ddmDataProviderResponse =
				ddmDataProviderInvoker.invoke(ddmDataProviderRequest);

			Map<String, String> resultMap = extractResults(resultMapExpression);

			setDDMFormFieldValues(ddmDataProviderResponse, resultMap);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return true;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void setDDMExpressionFieldAccessor(
		DDMExpressionFieldAccessor ddmExpressionFieldAccessor) {

		_ddmExpressionFieldAccessor = ddmExpressionFieldAccessor;
	}

	@Override
	public void setDDMExpressionObserver(
		DDMExpressionObserver ddmExpressionObserver) {

		_ddmExpressionObserver = ddmExpressionObserver;
	}

	protected void extractDDMFormFieldValue(
		String expression, Map<String, String> parameters) {

		String[] tokens = StringUtil.split(expression, CharPool.EQUAL);

		String parameterName = tokens[0];

		String parameterValue = StringPool.BLANK;

		if (tokens.length == 2) {
			parameterValue = tokens[1];
		}

		if (_ddmExpressionFieldAccessor.isField(parameterValue)) {
			parameterValue = getDDMFormFieldValue(parameterValue);
		}

		parameters.put(parameterName, parameterValue);
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

	protected String getDDMFormFieldValue(String ddmFormFieldName) {
		GetFieldPropertyRequest.Builder builder =
			GetFieldPropertyRequest.Builder.newBuilder(
				ddmFormFieldName, "value");

		GetFieldPropertyResponse getFieldPropertyResponse =
			_ddmExpressionFieldAccessor.getFieldProperty(builder.build());

		Object value = getFieldPropertyResponse.getValue();

		if (Validator.isNull(value)) {
			return StringPool.BLANK;
		}

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			Object[] valueArray = (Object[])value;

			if (ArrayUtil.isNotEmpty(valueArray)) {
				value = ((Object[])value)[0];
			}
		}

		try {
			JSONArray jsonArray = jsonFactory.createJSONArray(
				String.valueOf(value));

			return (String)jsonArray.get(0);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException, jsonException);
			}

			return String.valueOf(value);
		}
	}

	protected void setDDMFormFieldOptions(
		String field, List<KeyValuePair> options) {

		UpdateFieldPropertyRequest.Builder builder =
			UpdateFieldPropertyRequest.Builder.newBuilder(
				field, "options", options);

		_ddmExpressionObserver.updateFieldProperty(builder.build());
	}

	protected void setDDMFormFieldValue(String field, Object value) {
		UpdateFieldPropertyRequest.Builder builder =
			UpdateFieldPropertyRequest.Builder.newBuilder(
				field, "value", value);

		_ddmExpressionObserver.updateFieldProperty(builder.build());
	}

	protected void setDDMFormFieldValues(
		DDMDataProviderResponse ddmDataProviderResponse,
		Map<String, String> resultMap) {

		for (Map.Entry<String, String> entry : resultMap.entrySet()) {
			String outputName = entry.getValue();

			if (!ddmDataProviderResponse.hasOutput(outputName)) {
				continue;
			}

			String ddmFormFieldName = entry.getKey();

			Optional<List<KeyValuePair>> optionsOptional =
				ddmDataProviderResponse.getOutputOptional(
					outputName, List.class);

			if (optionsOptional.isPresent()) {
				setDDMFormFieldOptions(ddmFormFieldName, optionsOptional.get());
			}
			else {
				Optional<Object> valueOptional =
					ddmDataProviderResponse.getOutputOptional(
						outputName, String.class);

				if (!valueOptional.isPresent()) {
					valueOptional = ddmDataProviderResponse.getOutputOptional(
						outputName, Number.class);

					valueOptional = valueOptional.map(
						value -> new BigDecimal(value.toString()));
				}

				setDDMFormFieldValue(ddmFormFieldName, valueOptional.get());
			}
		}
	}

	protected DDMDataProviderInvoker ddmDataProviderInvoker;
	protected JSONFactory jsonFactory;

	private static final Log _log = LogFactoryUtil.getLog(CallFunction.class);

	private DDMExpressionFieldAccessor _ddmExpressionFieldAccessor;
	private DDMExpressionObserver _ddmExpressionObserver;

}