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

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationException;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationResult;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluator;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRendererConstants;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public class DDMFormRendererHelper {

	public DDMFormRendererHelper(
		DDMForm ddmForm, DDMFormRenderingContext ddmFormRenderingContext) {

		_ddmForm = ddmForm;
		_ddmFormFieldsMap = ddmForm.getDDMFormFieldsMap(true);
		_ddmFormRenderingContext = ddmFormRenderingContext;

		DDMFormValues ddmFormValues =
			ddmFormRenderingContext.getDDMFormValues();

		if (ddmFormValues == null) {
			DefaultDDMFormValuesFactory defaultDDMFormValuesFactory =
				new DefaultDDMFormValuesFactory(
					ddmForm, ddmFormRenderingContext.getLocale());

			_ddmFormValues = defaultDDMFormValuesFactory.create();
		}
		else {
			_ddmFormValues = ddmFormValues;
		}
	}

	public Map<String, List<Object>> getDDMFormFieldsTemplateContextMap() {
		Map<String, List<Object>> ddmFormFieldsTemplateContextMap =
			new HashMap<>();

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			_ddmFormValues.getDDMFormFieldValuesMap();

		for (Map.Entry<String, List<DDMFormFieldValue>> entry :
				ddmFormFieldValuesMap.entrySet()) {

			ddmFormFieldsTemplateContextMap.put(
				entry.getKey(),
				createRepeatableDDMFormFieldTemplateContexts(
					entry.getValue(), StringPool.BLANK));
		}

		return ddmFormFieldsTemplateContextMap;
	}

	protected Map<String, Object> createDDMFormFieldTemplateContext() {
		Map<String, Object> ddmFormFieldTemplateContext = new HashMap<>();

		ddmFormFieldTemplateContext.put("label", StringPool.BLANK);
		ddmFormFieldTemplateContext.put("name", StringPool.BLANK);
		ddmFormFieldTemplateContext.put(
			"readOnly", _ddmFormRenderingContext.isReadOnly());
		ddmFormFieldTemplateContext.put("value", StringPool.BLANK);

		return ddmFormFieldTemplateContext;
	}

	protected Object createDDMFormFieldTemplateContext(
		DDMFormFieldValue ddmFormFieldValue, int index,
		String parentDDMFormFieldParameterName) {

		String ddmFormFieldParameterName = getDDMFormFieldParameterName(
			ddmFormFieldValue.getName(), ddmFormFieldValue.getInstanceId(),
			index, parentDDMFormFieldParameterName);

		Map<String, List<DDMFormFieldValue>> nestedDDMFormFieldValuesMap =
			ddmFormFieldValue.getNestedDDMFormFieldValuesMap();

		List<Object> nestedDDMFormFieldValuesTemplateContexts = new ArrayList<>(
			nestedDDMFormFieldValuesMap.size());

		for (DDMFormFieldValue nestedDDMFormFieldValue :
				ddmFormFieldValue.getNestedDDMFormFieldValues()) {

			List<DDMFormFieldValue> nestedDDMFormFieldValues =
				nestedDDMFormFieldValuesMap.get(
					nestedDDMFormFieldValue.getName());

			nestedDDMFormFieldValuesTemplateContexts.addAll(
				createRepeatableDDMFormFieldTemplateContexts(
					nestedDDMFormFieldValues, ddmFormFieldParameterName));
		}

		Map<String, Object> ddmFormFieldTemplateContext =
			createDDMFormFieldTemplateContext();

		setDDMFormFieldTemplateContextNestedTemplateContexts(
			ddmFormFieldTemplateContext,
			nestedDDMFormFieldValuesTemplateContexts);
		setDDMFormFieldTemplateContextName(
			ddmFormFieldTemplateContext, ddmFormFieldParameterName);

		DDMFormField ddmFormField = _ddmFormFieldsMap.get(
			ddmFormFieldValue.getName());

		setDDMFormFieldTemplateContextLabel(
			ddmFormFieldTemplateContext, ddmFormField.getLabel());
		setDDMFormFieldTemplateContextRequired(
			ddmFormFieldTemplateContext, ddmFormField.isRequired());
		setDDMFormFieldTemplateContextTip(
			ddmFormFieldTemplateContext, ddmFormField.getTip());
		setDDMFormFieldTemplateContextValue(
			ddmFormFieldTemplateContext, ddmFormFieldValue.getValue());
		setDDMFormFieldTemplateContextVisible(
			ddmFormFieldTemplateContext, ddmFormField.getName());

		return ddmFormFieldTemplateContext;
	}

	protected Value createDefaultLocalizedValue(String defaultValueString) {
		Value value = new LocalizedValue(_ddmFormRenderingContext.getLocale());

		value.addString(
			_ddmFormRenderingContext.getLocale(), defaultValueString);

		return value;
	}

	protected Map<String, DDMFormFieldEvaluationResult>
		createInitialStateDDMFormFieldEvaluationResultsMap() {

		try {
			DDMFormEvaluationResult ddmFormEvaluationResult =
				_ddmFormEvaluator.evaluate(
					_ddmForm, _ddmFormValues,
					_ddmFormRenderingContext.getLocale());

			return ddmFormEvaluationResult.
				getDDMFormFieldEvaluationResultsMap();
		}
		catch (DDMFormEvaluationException ddmfee) {
			_log.error("Unable to evaluate the form", ddmfee);
		}

		return new HashMap<>();
	}

	protected List<Object> createRepeatableDDMFormFieldTemplateContexts(
		List<DDMFormFieldValue> ddmFormFieldValues,
		String parentDDMFormFieldParameterName) {

		List<Object> ddmFormFieldTemplateContexts = new ArrayList<>();

		int index = 0;

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			Object ddmFormFieldTemplateContext =
				createDDMFormFieldTemplateContext(
					ddmFormFieldValue, index++,
					parentDDMFormFieldParameterName);

			ddmFormFieldTemplateContexts.add(ddmFormFieldTemplateContext);
		}

		return ddmFormFieldTemplateContexts;
	}

	protected String getAffixedDDMFormFieldParameterName(
		String ddmFormFieldParameterName) {

		StringBundler sb = new StringBundler(5);

		sb.append(_ddmFormRenderingContext.getPortletNamespace());
		sb.append(DDMFormRendererConstants.DDM_FORM_FIELD_NAME_PREFIX);
		sb.append(ddmFormFieldParameterName);
		sb.append(
			DDMFormRendererConstants.DDM_FORM_FIELD_LANGUAGE_ID_SEPARATOR);
		sb.append(
			LocaleUtil.toLanguageId(_ddmFormRenderingContext.getLocale()));

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

	protected boolean isFieldVisible(String fieldName) {
		if (_ddmFormFieldEvaluationResultsMap == null) {
			_ddmFormFieldEvaluationResultsMap =
				createInitialStateDDMFormFieldEvaluationResultsMap();
		}

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			_ddmFormFieldEvaluationResultsMap.get(fieldName);

		if (ddmFormFieldEvaluationResult != null) {
			return ddmFormFieldEvaluationResult.isVisible();
		}

		return true;
	}

	protected void setDDMFormEvaluator(DDMFormEvaluator ddmFormEvaluator) {
		_ddmFormEvaluator = ddmFormEvaluator;
	}

	protected void setDDMFormFieldTemplateContextLabel(
		Map<String, Object> ddmFormFieldTemplateContext, LocalizedValue label) {

		Map<Locale, String> values = label.getValues();

		if (values.isEmpty()) {
			return;
		}

		ddmFormFieldTemplateContext.put(
			"label", label.getString(_ddmFormRenderingContext.getLocale()));
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
		List<Object> nestedDDMFormFieldValuesTemplateContexts) {

		ddmFormFieldRenderingContext.put(
			"nestedFields", nestedDDMFormFieldValuesTemplateContexts);
	}

	protected void setDDMFormFieldTemplateContextRequired(
		Map<String, Object> ddmFormFieldTemplateContext, boolean required) {

		ddmFormFieldTemplateContext.put("required", required);
	}

	protected void setDDMFormFieldTemplateContextTip(
		Map<String, Object> ddmFormFieldTemplateContext, LocalizedValue tip) {

		Map<Locale, String> values = tip.getValues();

		if (values.isEmpty()) {
			return;
		}

		ddmFormFieldTemplateContext.put(
			"tip", tip.getString(_ddmFormRenderingContext.getLocale()));
	}

	protected void setDDMFormFieldTemplateContextValue(
		Map<String, Object> ddmFormFieldTemplateContext, Value value) {

		if (value == null) {
			return;
		}

		ddmFormFieldTemplateContext.put(
			"value", value.getString(_ddmFormRenderingContext.getLocale()));
	}

	protected void setDDMFormFieldTemplateContextVisible(
		Map<String, Object> ddmFormFieldTemplateContext, String fieldName) {

		boolean visible = isFieldVisible(fieldName);

		ddmFormFieldTemplateContext.put("visible", visible);
	}

	protected void setDDMFormFieldTypeServicesTracker(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker) {

		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormRendererHelper.class);

	private final DDMForm _ddmForm;
	private DDMFormEvaluator _ddmFormEvaluator;
	private Map<String, DDMFormFieldEvaluationResult>
		_ddmFormFieldEvaluationResultsMap;
	private final Map<String, DDMFormField> _ddmFormFieldsMap;
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;
	private final DDMFormRenderingContext _ddmFormRenderingContext;
	private final DDMFormValues _ddmFormValues;

}