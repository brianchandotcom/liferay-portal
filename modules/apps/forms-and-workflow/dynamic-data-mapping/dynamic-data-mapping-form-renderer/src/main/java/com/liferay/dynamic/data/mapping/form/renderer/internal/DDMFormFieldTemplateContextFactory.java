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

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRendererConstants;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.language.LanguageConstants;
import com.liferay.portal.kernel.language.LanguageUtil;
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
public class DDMFormFieldTemplateContextFactory {

	public DDMFormFieldTemplateContextFactory(
		Map<String, DDMFormField> ddmFormFieldsMap,
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult,
		List<DDMFormFieldValue> ddmFormFieldValues,
		DDMFormRenderingContext ddmFormRenderingContext) {

		_ddmFormFieldsMap = ddmFormFieldsMap;

		if (ddmFormFieldEvaluationResult == null) {
			ddmFormFieldEvaluationResult = new DDMFormFieldEvaluationResult(
				null, null);
		}

		_ddmFormFieldEvaluationResult = ddmFormFieldEvaluationResult;
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
		DDMFormFieldValue ddmFormFieldValue, int index,
		String parentDDMFormFieldParameterName) {

		Map<String, Object> ddmFormFieldTemplateContext =
			createDDMFormFieldTemplateContext();

		DDMFormField ddmFormField = _ddmFormFieldsMap.get(
			ddmFormFieldValue.getName());

		setDDMFormFieldTemplateContextDir(ddmFormFieldTemplateContext);
		setDDMFormFieldTemplateContextLocalizedValue(
			ddmFormFieldTemplateContext, "label", ddmFormField.getLabel());
		setDDMFormFieldTemplateContextLocalizedValue(
			ddmFormFieldTemplateContext, "tip", ddmFormField.getTip());

		String ddmFormFieldParameterName = getDDMFormFieldParameterName(
			ddmFormFieldValue.getName(), ddmFormFieldValue.getInstanceId(),
			index, parentDDMFormFieldParameterName);

		setDDMFormFieldTemplateContextName(
			ddmFormFieldTemplateContext, ddmFormFieldParameterName);
		setDDMFormFieldTemplateContextTemplateNamespace(
			ddmFormFieldTemplateContext, ddmFormField.getType());

		List<Object> nestedDDMFormFieldTemplateContext =
			createNestedDDMFormFieldTemplateContext(
				ddmFormFieldValue, ddmFormFieldParameterName);

		setDDMFormFieldTemplateContextNestedTemplateContexts(
			ddmFormFieldTemplateContext, nestedDDMFormFieldTemplateContext);
		setDDMFormFieldTemplateContextReadOnly(
			ddmFormFieldTemplateContext, ddmFormField.isReadOnly());
		setDDMFormFieldTemplateContextRequired(
			ddmFormFieldTemplateContext, ddmFormField.isRequired());
		setDDMFormFieldTemplateContextShowLabel(
			ddmFormFieldTemplateContext, ddmFormField.isShowLabel());
		setDDMFormFieldTemplateContextType(
			ddmFormFieldTemplateContext, ddmFormField.getType());
		setDDMFormFieldTemplateContextValid(ddmFormFieldTemplateContext);
		setDDMFormFieldTemplateContextValue(
			ddmFormFieldTemplateContext, ddmFormFieldValue.getValue());
		setDDMFormFieldTemplateContextVisible(ddmFormFieldTemplateContext);

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
			Object ddmFormFieldTemplateContext =
				createDDMFormFieldTemplateContext(
					ddmFormFieldValue, index++,
					parentDDMFormFieldParameterName);

			ddmFormFieldTemplateContexts.add(ddmFormFieldTemplateContext);
		}

		return ddmFormFieldTemplateContexts;
	}

	protected List<Object> createNestedDDMFormFieldTemplateContext(
		DDMFormFieldValue parentDDMFormFieldValue,
		String parentDDMFormFieldParameterName) {

		Map<String, List<DDMFormFieldValue>> nestedDDMFormFieldValuesMap =
			parentDDMFormFieldValue.getNestedDDMFormFieldValuesMap();

		List<Object> nestedDDMFormFieldTemplateContext = new ArrayList<>(
			nestedDDMFormFieldValuesMap.size());

		for (DDMFormFieldValue nestedDDMFormFieldValue :
				parentDDMFormFieldValue.getNestedDDMFormFieldValues()) {

			List<DDMFormFieldValue> nestedDDMFormFieldValues =
				nestedDDMFormFieldValuesMap.get(
					nestedDDMFormFieldValue.getName());

			nestedDDMFormFieldTemplateContext.addAll(
				createDDMFormFieldTemplateContexts(
					nestedDDMFormFieldValues, parentDDMFormFieldParameterName));
		}

		return nestedDDMFormFieldTemplateContext;
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

	protected BaseDDMFormFieldRenderer getBaseDDMFormFieldRenderer(
		String fieldType) {

		DDMFormFieldRenderer ddmFormFieldRenderer =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldRenderer(fieldType);

		if (ddmFormFieldRenderer == null) {
			return null;
		}

		if (ddmFormFieldRenderer instanceof BaseDDMFormFieldRenderer) {
			return (BaseDDMFormFieldRenderer)ddmFormFieldRenderer;
		}

		return null;
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

	protected void setDDMFormFieldTemplateContextDir(
		Map<String, Object> ddmFormFieldTemplateContext) {

		ddmFormFieldTemplateContext.put(
			"dir", LanguageUtil.get(_locale, LanguageConstants.KEY_DIR));
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
		List<Object> nestedDDMFormFieldTemplateContexts) {

		if (nestedDDMFormFieldTemplateContexts.isEmpty()) {
			return;
		}

		ddmFormFieldRenderingContext.put(
			"nestedFields", nestedDDMFormFieldTemplateContexts);
	}

	protected void setDDMFormFieldTemplateContextReadOnly(
		Map<String, Object> ddmFormFieldTemplateContext, boolean readOnly) {

		if (_ddmFormRenderingContext.isReadOnly()) {
			readOnly = true;
		}

		ddmFormFieldTemplateContext.put("readOnly", readOnly);
	}

	protected void setDDMFormFieldTemplateContextRequired(
		Map<String, Object> ddmFormFieldTemplateContext, boolean required) {

		ddmFormFieldTemplateContext.put("required", required);
	}

	protected void setDDMFormFieldTemplateContextShowLabel(
		Map<String, Object> ddmFormFieldTemplateContext, boolean showLabel) {

		ddmFormFieldTemplateContext.put("showLabel", showLabel);
	}

	protected void setDDMFormFieldTemplateContextTemplateNamespace(
		Map<String, Object> ddmFormFieldTemplateContext, String fieldType) {

		BaseDDMFormFieldRenderer baseDDMFormFieldRenderer =
			getBaseDDMFormFieldRenderer(fieldType);

		if (baseDDMFormFieldRenderer == null) {
			return;
		}

		ddmFormFieldTemplateContext.put(
			"templateNamespace",
			baseDDMFormFieldRenderer.getTemplateNamespace());
	}

	protected void setDDMFormFieldTemplateContextType(
		Map<String, Object> ddmFormFieldTemplateContext, String type) {

		ddmFormFieldTemplateContext.put("type", type);
	}

	protected void setDDMFormFieldTemplateContextValid(
		Map<String, Object> ddmFormFieldTemplateContext) {

		ddmFormFieldTemplateContext.put(
			"valid", _ddmFormFieldEvaluationResult.isValid());
		ddmFormFieldTemplateContext.put(
			"validationErrorMessage",
			_ddmFormFieldEvaluationResult.getErrorMessage());
	}

	protected void setDDMFormFieldTemplateContextValue(
		Map<String, Object> ddmFormFieldTemplateContext, Value value) {

		if (value == null) {
			return;
		}

		ddmFormFieldTemplateContext.put("value", value.getString(_locale));
	}

	protected void setDDMFormFieldTemplateContextVisible(
		Map<String, Object> ddmFormFieldTemplateContext) {

		ddmFormFieldTemplateContext.put(
			"visible", _ddmFormFieldEvaluationResult.isVisible());
	}

	protected void setDDMFormFieldTypeServicesTracker(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker) {

		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
	}

	private final DDMFormFieldEvaluationResult _ddmFormFieldEvaluationResult;
	private final Map<String, DDMFormField> _ddmFormFieldsMap;
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;
	private final List<DDMFormFieldValue> _ddmFormFieldValues;
	private final DDMFormRenderingContext _ddmFormRenderingContext;
	private final Locale _locale;

}