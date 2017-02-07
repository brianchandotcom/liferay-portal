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

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationResult;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRendererConstants;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.language.LanguageConstants;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Marcellus Tavares
 */
public class DDMFormFieldTemplateContextFactory {

	public DDMFormFieldTemplateContextFactory(
		Map<String, DDMFormField> ddmFormFieldsMap,
		DDMFormEvaluationResult ddmFormEvaluationResult,
		List<DDMFormFieldValue> ddmFormFieldValues,
		DDMFormRenderingContext ddmFormRenderingContext) {

		_ddmFormFieldsMap = ddmFormFieldsMap;

		_ddmFormEvaluationResult = ddmFormEvaluationResult;
		_ddmFormFieldValues = ddmFormFieldValues;
		_ddmFormRenderingContext = ddmFormRenderingContext;

		_locale = ddmFormRenderingContext.getLocale();
	}

	public List<Object> create() {
		return createDDMFormFieldTemplateContexts(
			_ddmFormFieldValues, StringPool.BLANK);
	}

	protected DDMFormFieldRenderingContext
		createDDDMFormFieldRenderingContext(
			Map<String, Object> ddmFormFieldTemplateContext) {

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setHttpServletRequest(
			_ddmFormRenderingContext.getHttpServletRequest());
		ddmFormFieldRenderingContext.setHttpServletResponse(
			_ddmFormRenderingContext.getHttpServletResponse());
		ddmFormFieldRenderingContext.setLocale(_locale);
		ddmFormFieldRenderingContext.setPortletNamespace(
			_ddmFormRenderingContext.getPortletNamespace());
		ddmFormFieldRenderingContext.setProperties(ddmFormFieldTemplateContext);

		return ddmFormFieldRenderingContext;
	}

	protected Map<String, Object> createDDMFormFieldTemplateContext() {
		Map<String, Object> ddmFormFieldTemplateContext = new HashMap<>();

		ddmFormFieldTemplateContext.put("label", StringPool.BLANK);
		ddmFormFieldTemplateContext.put("value", StringPool.BLANK);

		return ddmFormFieldTemplateContext;
	}

	protected Map<String, Object> createDDMFormFieldTemplateContext(
		DDMFormFieldValue ddmFormFieldValue,
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult, int index,
		String parentDDMFormFieldParameterName) {

		Map<String, Object> ddmFormFieldTemplateContext =
			createDDMFormFieldTemplateContext();

		DDMFormField ddmFormField = _ddmFormFieldsMap.get(
			ddmFormFieldValue.getName());

		setDDMFormFieldTemplateContextDataType(
			ddmFormFieldTemplateContext, ddmFormField.getDataType());

		setDDMFormFieldTemplateContextDir(ddmFormFieldTemplateContext);
		setDDMFormFieldTemplateContextEvaluable(
			ddmFormFieldTemplateContext, ddmFormFieldEvaluationResult,
			ddmFormField.getProperty("evaluable"));
		setDDMFormFieldTemplateContextLocalizedValue(
			ddmFormFieldTemplateContext, "label", ddmFormField.getLabel());
		setDDMFormFieldTemplateContextLocalizedValue(
			ddmFormFieldTemplateContext, "tip", ddmFormField.getTip());

		String ddmFormFieldParameterName = getDDMFormFieldParameterName(
			ddmFormFieldValue.getName(), ddmFormFieldValue.getInstanceId(),
			index, parentDDMFormFieldParameterName);

		setDDMFormFieldTemplateContextName(
			ddmFormFieldTemplateContext, ddmFormFieldParameterName);

		Map<String, Object> nestedDDMFormFieldTemplateContext =
			createNestedDDMFormFieldTemplateContext(
				ddmFormFieldValue, ddmFormFieldParameterName);

		setDDMFormFieldTemplateContextNestedTemplateContexts(
			ddmFormFieldTemplateContext, nestedDDMFormFieldTemplateContext);

		setDDMFormFieldTemplateContextOptions(
			ddmFormFieldTemplateContext, ddmFormFieldEvaluationResult,
			ddmFormField.getDDMFormFieldOptions());
		setDDMFormFieldTemplateContextReadOnly(
			ddmFormFieldTemplateContext, ddmFormFieldEvaluationResult);
		setDDMFormFieldTemplateContextRepeatable(
			ddmFormFieldTemplateContext, ddmFormField.isRepeatable());
		setDDMFormFieldTemplateContextRequired(
			ddmFormFieldTemplateContext, ddmFormFieldEvaluationResult);
		setDDMFormFieldTemplateContextShowLabel(
			ddmFormFieldTemplateContext, ddmFormField.isShowLabel());
		setDDMFormFieldTemplateContextType(
			ddmFormFieldTemplateContext, ddmFormField.getType());
		setDDMFormFieldTemplateContextValid(
			ddmFormFieldEvaluationResult, ddmFormFieldTemplateContext);
		setDDMFormFieldTemplateContextValue(
			ddmFormFieldEvaluationResult, ddmFormFieldTemplateContext,
			ddmFormFieldValue.getValue());
		setDDMFormFieldTemplateContextVisible(
			ddmFormFieldTemplateContext, ddmFormFieldEvaluationResult);

		// Contributed template parameters

		setDDMFormFieldTemplateContextContributedParameters(
			ddmFormFieldTemplateContext, ddmFormField);

		return ddmFormFieldTemplateContext;
	}

	protected List<Object> createDDMFormFieldTemplateContexts(
		List<DDMFormFieldValue> ddmFormFieldValues,
		String parentDDMFormFieldParameterName) {

		List<Object> ddmFormFieldTemplateContexts = new ArrayList<>();

		int index = 0;

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
				_getDDMFormFieldEvaluationResult(ddmFormFieldValue);

			Object ddmFormFieldTemplateContext =
				createDDMFormFieldTemplateContext(
					ddmFormFieldValue, ddmFormFieldEvaluationResult, index++,
					parentDDMFormFieldParameterName);

			ddmFormFieldTemplateContexts.add(ddmFormFieldTemplateContext);
		}

		return ddmFormFieldTemplateContexts;
	}

	protected Map<String, Object> createNestedDDMFormFieldTemplateContext(
		DDMFormFieldValue parentDDMFormFieldValue,
		String parentDDMFormFieldParameterName) {

		Map<String, Object> nestedDDMFormFieldTemplateContext = new HashMap<>();

		Map<String, List<DDMFormFieldValue>> nestedDDMFormFieldValuesMap =
			parentDDMFormFieldValue.getNestedDDMFormFieldValuesMap();

		for (DDMFormFieldValue nestedDDMFormFieldValue :
				parentDDMFormFieldValue.getNestedDDMFormFieldValues()) {

			List<DDMFormFieldValue> nestedDDMFormFieldValues =
				nestedDDMFormFieldValuesMap.get(
					nestedDDMFormFieldValue.getName());

			nestedDDMFormFieldTemplateContext.put(
				nestedDDMFormFieldValue.getName(),
				createDDMFormFieldTemplateContexts(
					nestedDDMFormFieldValues, parentDDMFormFieldParameterName));
		}

		return nestedDDMFormFieldTemplateContext;
	}

	protected List<Map<String, String>> createOptions(
		DDMFormFieldOptions ddmFormFieldOptions) {

		List<Map<String, String>> list = new ArrayList<>();

		Map<String, LocalizedValue> options = ddmFormFieldOptions.getOptions();

		for (Entry<String, LocalizedValue> entry : options.entrySet()) {
			Map<String, String> option = new HashMap<>();

			LocalizedValue localizedValue = entry.getValue();

			option.put("label", localizedValue.getString(_locale));

			option.put("value", entry.getKey());

			list.add(option);
		}

		return list;
	}

	protected List<Map<String, String>> createOptions(
		List<KeyValuePair> keyValuePairs) {

		List<Map<String, String>> list = new ArrayList<>();

		for (KeyValuePair keyValuePair : keyValuePairs) {
			Map<String, String> option = new HashMap<>();

			option.put("label", keyValuePair.getValue());

			option.put("value", keyValuePair.getKey());

			list.add(option);
		}

		return list;
	}

	protected String getAffixedDDMFormFieldParameterName(
		String ddmFormFieldParameterName) {

		StringBundler sb = new StringBundler(5);

		sb.append(_ddmFormRenderingContext.getPortletNamespace());
		sb.append(DDMFormRendererConstants.DDM_FORM_FIELD_NAME_PREFIX);
		sb.append(ddmFormFieldParameterName);
		sb.append(
			DDMFormRendererConstants.DDM_FORM_FIELD_LANGUAGE_ID_SEPARATOR);
		sb.append(LocaleUtil.toLanguageId(_locale));

		return sb.toString();
	}

	protected String getDDMFormFieldParameterName(
		String ddmFormFieldName, String instanceId, int index,
		String parentDDMFormFieldParameterName) {

		StringBundler sb = new StringBundler(7);

		if (Validator.isNotNull(parentDDMFormFieldParameterName)) {
			sb.append(parentDDMFormFieldParameterName);
			sb.append(DDMFormRendererConstants.DDM_FORM_FIELDS_SEPARATOR);
		}

		sb.append(ddmFormFieldName);
		sb.append(DDMFormRendererConstants.DDM_FORM_FIELD_PARTS_SEPARATOR);
		sb.append(instanceId);
		sb.append(DDMFormRendererConstants.DDM_FORM_FIELD_PARTS_SEPARATOR);
		sb.append(index);

		return sb.toString();
	}

	protected void setDDMFormFieldTemplateContextContributedParameters(
		Map<String, Object> ddmFormFieldTemplateContext,
		DDMFormField ddmFormField) {

		DDMFormFieldTemplateContextContributor
			ddmFormFieldTemplateContextContributor =
				_ddmFormFieldTypeServicesTracker.
					getDDMFormFieldTemplateContextContributor(
						ddmFormField.getType());

		if (ddmFormFieldTemplateContextContributor == null) {
			return;
		}

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			createDDDMFormFieldRenderingContext(ddmFormFieldTemplateContext);

		Map<String, Object> contributedParameters =
			ddmFormFieldTemplateContextContributor.getParameters(
				ddmFormField, ddmFormFieldRenderingContext);

		if ((contributedParameters == null) ||
			contributedParameters.isEmpty()) {

			return;
		}

		ddmFormFieldTemplateContext.putAll(contributedParameters);
	}

	protected void setDDMFormFieldTemplateContextDataType(
		Map<String, Object> ddmFormFieldTemplateContext, String dataType) {

		ddmFormFieldTemplateContext.put("dataType", dataType);
	}

	protected void setDDMFormFieldTemplateContextDir(
		Map<String, Object> ddmFormFieldTemplateContext) {

		ddmFormFieldTemplateContext.put(
			"dir", LanguageUtil.get(_locale, LanguageConstants.KEY_DIR));
	}

	protected void setDDMFormFieldTemplateContextEvaluable(
		Map<String, Object> ddmFormFieldTemplateContext,
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult,
		Object evaluable) {

		if (ddmFormFieldEvaluationResult.isRequired()) {
			ddmFormFieldTemplateContext.put("evaluable", true);

			return;
		}

		if (evaluable == null) {
			return;
		}

		ddmFormFieldTemplateContext.put("evaluable", evaluable);
	}

	protected void setDDMFormFieldTemplateContextLocalizedValue(
		Map<String, Object> ddmFormFieldTemplateContext, String propertyName,
		LocalizedValue localizedValue) {

		Map<Locale, String> values = localizedValue.getValues();

		if (values.isEmpty()) {
			return;
		}

		ddmFormFieldTemplateContext.put(
			propertyName, localizedValue.getString(_locale));
	}

	protected void setDDMFormFieldTemplateContextName(
		Map<String, Object> ddmFormFieldTemplateContext,
		String ddmFormFieldParameterName) {

		String name = getAffixedDDMFormFieldParameterName(
			ddmFormFieldParameterName);

		ddmFormFieldTemplateContext.put("name", name);
	}

	protected void setDDMFormFieldTemplateContextNestedTemplateContexts(
		Map<String, Object> ddmFormFieldRenderingContext,
		Map<String, Object> nestedDDMFormFieldTemplateContexts) {

		if (nestedDDMFormFieldTemplateContexts.isEmpty()) {
			return;
		}

		ddmFormFieldRenderingContext.put(
			"nestedFields", nestedDDMFormFieldTemplateContexts);
	}

	protected void setDDMFormFieldTemplateContextOptions(
		Map<String, Object> ddmFormFieldTemplateContext,
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult,
		DDMFormFieldOptions ddmFormFieldOptions) {

		List<KeyValuePair> keyValuePairs =
			ddmFormFieldEvaluationResult.getProperty("options");

		if (ListUtil.isNotEmpty(keyValuePairs)) {
			ddmFormFieldTemplateContext.put(
				"options", createOptions(keyValuePairs));
		}
		else {
			ddmFormFieldTemplateContext.put(
				"options", createOptions(ddmFormFieldOptions));
		}
	}

	protected void setDDMFormFieldTemplateContextReadOnly(
		Map<String, Object> ddmFormFieldTemplateContext,
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult) {

		boolean readOnly = ddmFormFieldEvaluationResult.isReadOnly();

		if (_ddmFormRenderingContext.isReadOnly()) {
			readOnly = true;
		}

		ddmFormFieldTemplateContext.put("readOnly", readOnly);
	}

	protected void setDDMFormFieldTemplateContextRepeatable(
		Map<String, Object> ddmFormFieldTemplateContext, boolean repeatable) {

		ddmFormFieldTemplateContext.put("repeatable", repeatable);
	}

	protected void setDDMFormFieldTemplateContextRequired(
		Map<String, Object> ddmFormFieldTemplateContext,
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult) {

		ddmFormFieldTemplateContext.put(
			"required", ddmFormFieldEvaluationResult.isRequired());
	}

	protected void setDDMFormFieldTemplateContextShowLabel(
		Map<String, Object> ddmFormFieldTemplateContext, boolean showLabel) {

		ddmFormFieldTemplateContext.put("showLabel", showLabel);
	}

	protected void setDDMFormFieldTemplateContextType(
		Map<String, Object> ddmFormFieldTemplateContext, String type) {

		ddmFormFieldTemplateContext.put("type", type);
	}

	protected void setDDMFormFieldTemplateContextValid(
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult,
		Map<String, Object> ddmFormFieldTemplateContext) {

		ddmFormFieldTemplateContext.put(
			"errorMessage", ddmFormFieldEvaluationResult.getErrorMessage());
		ddmFormFieldTemplateContext.put(
			"valid", ddmFormFieldEvaluationResult.isValid());
	}

	protected void setDDMFormFieldTemplateContextValue(
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult,
		Map<String, Object> ddmFormFieldTemplateContext, Value value) {

		if (ddmFormFieldEvaluationResult.getValue() != null) {
			ddmFormFieldTemplateContext.put(
				"value",
				String.valueOf(ddmFormFieldEvaluationResult.getValue()));
		}
		else if (value != null) {
			ddmFormFieldTemplateContext.put("value", value.getString(_locale));
		}
	}

	protected void setDDMFormFieldTemplateContextVisible(
		Map<String, Object> ddmFormFieldTemplateContext,
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult) {

		ddmFormFieldTemplateContext.put(
			"visible", ddmFormFieldEvaluationResult.isVisible());
	}

	protected void setDDMFormFieldTypeServicesTracker(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker) {

		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
	}

	private DDMFormFieldEvaluationResult _getDDMFormFieldEvaluationResult(
		DDMFormFieldValue ddmFormFieldValue) {

		return _ddmFormEvaluationResult.geDDMFormFieldEvaluationResult(
			ddmFormFieldValue.getName(), ddmFormFieldValue.getInstanceId());
	}

	private final DDMFormEvaluationResult _ddmFormEvaluationResult;
	private final Map<String, DDMFormField> _ddmFormFieldsMap;
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;
	private final List<DDMFormFieldValue> _ddmFormFieldValues;
	private final DDMFormRenderingContext _ddmFormRenderingContext;
	private final Locale _locale;

}