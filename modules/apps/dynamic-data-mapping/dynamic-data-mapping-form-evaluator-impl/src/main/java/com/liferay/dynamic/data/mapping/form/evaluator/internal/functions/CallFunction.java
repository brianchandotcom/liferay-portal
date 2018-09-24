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

import com.liferay.dynamic.data.mapping.constants.DDMConstants;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	factory = DDMConstants.EXPRESSION_FUNCTION_FACTORY_NAME,
	service = {
		DDMExpressionFieldAccessorAware.class,
		DDMExpressionFunction.Function3.class, DDMExpressionObserverAware.class
	}
)
public class CallFunction
	implements DDMExpressionFunction.Function3<String, String, String, Boolean>,
			   DDMExpressionFieldAccessorAware, DDMExpressionObserverAware {

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
				builder = builder.withParameter(
					entry.getKey(), entry.getValue());
			}

			DDMDataProviderRequest ddmDataProviderRequest = builder.build();

			DDMDataProviderResponse ddmDataProviderResponse =
				ddmDataProviderInvoker.invoke(ddmDataProviderRequest);

			Map<String, String> resultMap = extractResults(resultMapExpression);

			setDDMFormFieldValues(ddmDataProviderResponse, resultMap);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return true;
	}

	@Override
	public String getName() {
		return "call";
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
			parameters.put(parameterName, getDDMFormFieldValue(parameterValue));
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
		catch (JSONException jsone) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsone, jsone);
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

		if (options.size() == 1) {
			KeyValuePair keyValuePair = options.get(0);

			JSONArray valueJSONArray = jsonFactory.createJSONArray();

			valueJSONArray.put(keyValuePair.getValue());

			builder = UpdateFieldPropertyRequest.Builder.newBuilder(
				field, "value", valueJSONArray);

			_ddmExpressionObserver.updateFieldProperty(builder.build());
		}
	}

	protected void setDDMFormFieldValue(String field, String value) {
		UpdateFieldPropertyRequest.Builder builder =
			UpdateFieldPropertyRequest.Builder.newBuilder(
				field, "value", value);

		_ddmExpressionObserver.updateFieldProperty(builder.build());
	}

	protected void setDDMFormFieldValues(
		DDMDataProviderResponse ddmDataProviderResponse,
		Map<String, String> resultMap) {

		for (Map.Entry<String, String> entry : resultMap.entrySet()) {
			String ddmFormFieldName = entry.getKey();
			String outputName = entry.getValue();

			if (!ddmDataProviderResponse.hasOutput(outputName)) {
				continue;
			}

			Optional<List<KeyValuePair>> options =
				ddmDataProviderResponse.getOutput(outputName, List.class);

			if (options.isPresent()) {
				setDDMFormFieldOptions(ddmFormFieldName, options.get());
			}
			else {
				Optional<String> value = ddmDataProviderResponse.getOutput(
					outputName, String.class);

				setDDMFormFieldValue(ddmFormFieldName, value.get());
			}
		}
	}

	@Reference
	protected DDMDataProviderInvoker ddmDataProviderInvoker;

	@Reference
	protected JSONFactory jsonFactory;

	private static final Log _log = LogFactoryUtil.getLog(CallFunction.class);

	private DDMExpressionFieldAccessor _ddmExpressionFieldAccessor;
	private DDMExpressionObserver _ddmExpressionObserver;

}