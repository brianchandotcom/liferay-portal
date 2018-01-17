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

package com.liferay.dynamic.data.mapping.form.renderer.internal;

import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author Leonardo Barros
 * @author Rafael Praxedes
 */
public class DDMFormTemplateContextFactoryHelper {

	public DDMFormTemplateContextFactoryHelper(
		DDMDataProviderInstanceService ddmDataProviderInstanceService) {

		_ddmDataProviderInstanceService = ddmDataProviderInstanceService;
	}

	public Map<String, Map<String, Object>> getDataProviderSettings(
		DDMForm ddmForm) {

		Map<String, Map<String, Object>> map = new HashMap<>();

		extractDataProviderSettingsFromFieldSettings(ddmForm, map);

		extractDataProviderSettingsFromAutoFillActions(ddmForm, map);

		return map;
	}

	public Set<String> getEvaluableDDMFormFieldNames(DDMForm ddmForm) {
		Set<String> evaluableDDMFormFieldNames = new HashSet<>();

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		Set<String> ddmFormFieldNames = ddmFormFieldsMap.keySet();

		evaluableDDMFormFieldNames.addAll(
			getReferencedFieldNamesByDDMFormRules(
				ddmForm.getDDMFormRules(), ddmFormFieldNames));

		for (DDMFormField ddmFormField : ddmFormFieldsMap.values()) {
			if (isDDMFormFieldEvaluable(ddmFormField)) {
				evaluableDDMFormFieldNames.add(ddmFormField.getName());
			}

			String visibilityExpression =
				ddmFormField.getVisibilityExpression();

			evaluableDDMFormFieldNames.addAll(
				getReferencedFieldNamesByExpression(
					visibilityExpression, ddmFormFieldNames));
		}

		return evaluableDDMFormFieldNames;
	}

	protected Map<String, Object> createDataProviderSettingsMap(
		String dataProviderInstanceUUID, String outputParameterName,
		Map<String, String> inputParameters) {

		Map<String, Object> ddmDataProviderSettingsMap = new HashMap<>();

		ddmDataProviderSettingsMap.put(
			"dataProviderInstanceUUID", dataProviderInstanceUUID);

		ddmDataProviderSettingsMap.put("inputParameters", inputParameters);

		ddmDataProviderSettingsMap.put(
			"outputParameterName", outputParameterName);

		return ddmDataProviderSettingsMap;
	}

	protected Map<String, String> extractAutoFillParameters(String expression) {
		if (Validator.isNull(expression)) {
			return Collections.emptyMap();
		}

		String[] innerExpressions = StringUtil.split(
			expression, CharPool.SEMICOLON);

		Map<String, String> map = new HashMap<>();

		Stream<String> innerExpressionsStream = Stream.of(innerExpressions);

		innerExpressionsStream.forEach(
			innerExpression -> {
				String[] parts = StringUtil.split(
					innerExpression, CharPool.EQUAL);

				map.put(parts[0], parts[1]);
			});

		return map;
	}

	protected void extractDataProviderSettings(
		DDMFormField ddmFormField,
		Map<String, Map<String, Object>> dataProviderSettings) {

		long ddmDataProviderInstanceId = MapUtil.getLong(
			ddmFormField.getProperties(), "ddmDataProviderInstanceId");

		if (ddmDataProviderInstanceId == 0) {
			return;
		}

		try {
			DDMDataProviderInstance ddmDataProviderInstance =
				_ddmDataProviderInstanceService.getDataProviderInstance(
					ddmDataProviderInstanceId);

			String ddmDataProviderInstanceOutput = GetterUtil.getString(
				ddmFormField.getProperty("ddmDataProviderInstanceOutput"));

			dataProviderSettings.put(
				ddmFormField.getName(),
				createDataProviderSettingsMap(
					ddmDataProviderInstance.getUuid(),
					ddmDataProviderInstanceOutput, Collections.emptyMap()));
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}
	}

	protected void extractDataProviderSettings(
		String action, Map<String, DDMFormField> ddmFormFields,
		Map<String, Map<String, Object>> dataProviderSettings) {

		Matcher matcher = _callFunctionPattern.matcher(action);

		if (matcher.find()) {
			String dataProviderInstanceUUID = matcher.group(1);

			Map<String, String> inputParameters = extractAutoFillParameters(
				matcher.group(2));

			Map<String, String> outputParameters = extractAutoFillParameters(
				matcher.group(3));

			Set<Entry<String, String>> entrySet = outputParameters.entrySet();

			Stream<Entry<String, String>> outputParametersStream =
				entrySet.stream();

			outputParametersStream = outputParametersStream.filter(
				entry -> isSelectField(ddmFormFields.get(entry.getKey())));

			outputParametersStream.forEach(
				entry -> {
					dataProviderSettings.put(
						entry.getKey(),
						createDataProviderSettingsMap(
							dataProviderInstanceUUID, entry.getValue(),
							inputParameters));
				});
		}
	}

	protected void extractDataProviderSettingsFromAutoFillActions(
		DDMForm ddmForm,
		Map<String, Map<String, Object>> dataProviderSettings) {

		List<DDMFormRule> ddmFormRules = ddmForm.getDDMFormRules();

		Map<String, DDMFormField> ddmFormFields = ddmForm.getDDMFormFieldsMap(
			true);

		Stream<DDMFormRule> ddmFormRulesStream = ddmFormRules.stream();

		Stream<String> actionsStream = ddmFormRulesStream.flatMap(
			ddmFormRule -> {
				List<String> actions = ddmFormRule.getActions();

				return actions.stream();
			});

		actionsStream.filter(
			this::isAutofillAction
		).forEach(
			action -> extractDataProviderSettings(
				action, ddmFormFields, dataProviderSettings)
		);
	}

	protected void extractDataProviderSettingsFromFieldSettings(
		DDMForm ddmForm,
		Map<String, Map<String, Object>> dataProviderSettings) {

		Map<String, DDMFormField> ddmFormFields = ddmForm.getDDMFormFieldsMap(
			true);

		Collection<DDMFormField> ddmFormFieldsCollection =
			ddmFormFields.values();

		Stream<DDMFormField> ddmFormFieldsStream =
			ddmFormFieldsCollection.stream();

		ddmFormFieldsStream.filter(
			this::hasDataProviderSettings
		).forEach(
			ddmFormField -> extractDataProviderSettings(
				ddmFormField, dataProviderSettings)
		);
	}

	protected Set<String> getReferencedFieldNamesByDDMFormRules(
		List<DDMFormRule> ddmFormRules, Set<String> ddmFormFieldNames) {

		Set<String> referencedFieldNames = new HashSet<>();

		for (DDMFormRule ddmFormRule : ddmFormRules) {
			String condition = ddmFormRule.getCondition();

			referencedFieldNames.addAll(
				getReferencedFieldNamesByExpression(
					condition, ddmFormFieldNames));

			for (String action : ddmFormRule.getActions()) {
				referencedFieldNames.addAll(
					getReferencedFieldNamesByExpression(
						action, ddmFormFieldNames));
			}
		}

		return referencedFieldNames;
	}

	protected Set<String> getReferencedFieldNamesByExpression(
		String expression, Set<String> ddmFormFieldNames) {

		if (Validator.isNull(expression)) {
			return Collections.emptySet();
		}

		Set<String> referencedFieldNames = new HashSet<>();

		for (String ddmFormFieldName : ddmFormFieldNames) {
			if (expression.contains(ddmFormFieldName)) {
				referencedFieldNames.add(ddmFormFieldName);
			}
		}

		return referencedFieldNames;
	}

	protected boolean hasDataProviderSettings(DDMFormField ddmFormField) {
		if (MapUtil.getLong(
				ddmFormField.getProperties(),
				"ddmDataProviderInstanceId") > 0) {

			return true;
		}

		return false;
	}

	protected boolean isAutofillAction(String action) {
		Matcher matcher = _callFunctionPattern.matcher(action);

		return matcher.matches();
	}

	protected boolean isDDMFormFieldEvaluable(DDMFormField ddmFormField) {
		if (ddmFormField.isRequired()) {
			return true;
		}

		DDMFormFieldValidation ddmFormFieldValidation =
			ddmFormField.getDDMFormFieldValidation();

		if ((ddmFormFieldValidation != null) &&
			Validator.isNotNull(ddmFormFieldValidation.getExpression())) {

			return true;
		}

		return false;
	}

	protected boolean isSelectField(DDMFormField ddmFormField) {
		if (ddmFormField == null) {
			return false;
		}

		String type = ddmFormField.getType();

		return type.equals("select");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormTemplateContextFactoryHelper.class);

	private static final Pattern _callFunctionPattern = Pattern.compile(
		"call\\(\\s*\'([0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-" +
			"[0-9a-f]{12})\'\\s*,\\s*\'(.*)\'\\s*,\\s*\'(.*)\'\\s*\\)");

	private final DDMDataProviderInstanceService
		_ddmDataProviderInstanceService;

}