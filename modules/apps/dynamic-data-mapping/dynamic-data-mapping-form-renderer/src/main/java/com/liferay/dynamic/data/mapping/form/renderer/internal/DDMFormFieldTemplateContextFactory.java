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

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorFieldContextKey;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRendererConstants;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.internal.util.DDMFormTemplateContextFactoryUtil;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageConstants;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
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
		Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
			ddmFormFieldsPropertyChanges,
		List<DDMFormFieldValue> ddmFormFieldValues,
		DDMFormRenderingContext ddmFormRenderingContext, boolean pageEnabled) {

		_ddmFormFieldsMap = ddmFormFieldsMap;
		_ddmFormFieldsPropertyChanges = ddmFormFieldsPropertyChanges;
		_ddmFormFieldValues = ddmFormFieldValues;
		_ddmFormRenderingContext = ddmFormRenderingContext;
		_pageEnabled = pageEnabled;

		_locale = ddmFormRenderingContext.getLocale();
	}

	public List<Object> create() {
		return createDDMFormFieldTemplateContexts(
			_ddmFormFieldValues, StringPool.BLANK);
	}

	protected boolean addProperty(
		Map<String, Object> changedProperties, String propertyName) {

		if (_ddmFormRenderingContext.isReturnFullContext()) {
			return true;
		}

		if (changedProperties.containsKey(propertyName)) {
			return true;
		}

		return false;
	}

	protected DDMFormFieldRenderingContext createDDDMFormFieldRenderingContext(
		Map<String, Object> changedProperties,
		Map<String, Object> ddmFormFieldTemplateContext) {

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setReturnFullContext(
			_ddmFormRenderingContext.isReturnFullContext());
		ddmFormFieldRenderingContext.setHttpServletRequest(
			_ddmFormRenderingContext.getHttpServletRequest());
		ddmFormFieldRenderingContext.setHttpServletResponse(
			_ddmFormRenderingContext.getHttpServletResponse());
		ddmFormFieldRenderingContext.setLocale(_locale);
		ddmFormFieldRenderingContext.setPortletNamespace(
			_ddmFormRenderingContext.getPortletNamespace());
		ddmFormFieldRenderingContext.setProperties(ddmFormFieldTemplateContext);
		ddmFormFieldRenderingContext.setProperty(
			"changedProperties", changedProperties);
		ddmFormFieldRenderingContext.setProperty(
			"groupId", _ddmFormRenderingContext.getGroupId());
		ddmFormFieldRenderingContext.setViewMode(
			_ddmFormRenderingContext.isViewMode());

		return ddmFormFieldRenderingContext;
	}

	protected Map<String, Object> createDDMFormFieldTemplateContext(
		DDMFormField ddmFormField, String ddmFormFieldParameterName) {

		Map<String, Object> ddmFormFieldTemplateContext = new HashMap<>();

		setDDMFormFieldTemplateContextFieldName(
			ddmFormFieldTemplateContext, ddmFormField.getName());
		setDDMFormFieldTemplateContextLocalizedValue(
			ddmFormFieldTemplateContext, "label", ddmFormField.getLabel());
		setDDMFormFieldTemplateContextName(
			ddmFormFieldTemplateContext, ddmFormFieldParameterName);

		return ddmFormFieldTemplateContext;
	}

	protected Map<String, Object> createDDMFormFieldTemplateContext(
		DDMFormFieldValue ddmFormFieldValue,
		Map<String, Object> changedProperties, int index,
		String parentDDMFormFieldParameterName) {

		DDMFormField ddmFormField = _ddmFormFieldsMap.get(
			ddmFormFieldValue.getName());

		String ddmFormFieldParameterName = getDDMFormFieldParameterName(
			ddmFormFieldValue.getName(), ddmFormFieldValue.getInstanceId(),
			index, parentDDMFormFieldParameterName);

		Map<String, Object> ddmFormFieldTemplateContext =
			createDDMFormFieldTemplateContext(
				ddmFormField, ddmFormFieldParameterName);

		if (_ddmFormRenderingContext.isReturnFullContext()) {
			setProperties(
				ddmFormFieldTemplateContext, ddmFormField, ddmFormFieldValue,
				index, ddmFormFieldParameterName);
		}

		setPropertiesChangeableByRule(
			ddmFormFieldTemplateContext, changedProperties, ddmFormField,
			ddmFormFieldValue);

		setDDMFormFieldTemplateContextNestedTemplateContexts(
			ddmFormFieldTemplateContext,
			createNestedDDMFormFieldTemplateContext(
				ddmFormFieldValue, ddmFormFieldParameterName));

		// Contributed template parameters

		setDDMFormFieldTemplateContextContributedParameters(
			changedProperties, ddmFormFieldTemplateContext, ddmFormField);

		return ddmFormFieldTemplateContext;
	}

	protected List<Object> createDDMFormFieldTemplateContexts(
		List<DDMFormFieldValue> ddmFormFieldValues,
		String parentDDMFormFieldParameterName) {

		List<Object> ddmFormFieldTemplateContexts = new ArrayList<>();

		int index = 0;

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			Map<String, Object> changedProperties = getChangedProperties(
				ddmFormFieldValue);

			DDMFormField ddmFormField = _ddmFormFieldsMap.get(
				ddmFormFieldValue.getName());

			if (!_ddmFormRenderingContext.isReturnFullContext() &&
				changedProperties.isEmpty() && !ddmFormField.isRequired()) {

				continue;
			}

			Object ddmFormFieldTemplateContext =
				createDDMFormFieldTemplateContext(
					ddmFormFieldValue, changedProperties, index++,
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

		for (Map.Entry<String, LocalizedValue> entry : options.entrySet()) {
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

	protected Map<String, Object> getChangedProperties(
		DDMFormFieldValue ddmFormFieldValue) {

		Map<String, Object> changedProperties =
			_ddmFormFieldsPropertyChanges.get(
				new DDMFormEvaluatorFieldContextKey(
					ddmFormFieldValue.getName(),
					ddmFormFieldValue.getInstanceId()));

		if (changedProperties == null) {
			changedProperties = new HashMap<>();
		}

		if (!_pageEnabled) {
			changedProperties.put("required", false);
		}

		if (_ddmFormRenderingContext.isReadOnly()) {
			changedProperties.put("readOnly", true);
		}

		return changedProperties;
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
		Map<String, Object> changedProperties,
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
			createDDDMFormFieldRenderingContext(
				changedProperties, ddmFormFieldTemplateContext);

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
		DDMFormField ddmFormField, Map<String, Object> changedProperties,
		Object evaluable) {

		if (changedProperties.containsKey("required")) {
			ddmFormFieldTemplateContext.put("evaluable", true);

			return;
		}

		if (evaluable == null) {
			return;
		}

		ddmFormFieldTemplateContext.put("evaluable", evaluable);
	}

	protected void setDDMFormFieldTemplateContextFieldName(
		Map<String, Object> ddmFormFieldTemplateContext, String fieldName) {

		ddmFormFieldTemplateContext.put("fieldName", fieldName);
	}

	protected void setDDMFormFieldTemplateContextInstanceId(
		Map<String, Object> ddmFormFieldTemplateContext, String instanceId) {

		ddmFormFieldTemplateContext.put("instanceId", instanceId);
	}

	protected void setDDMFormFieldTemplateContextLocale(
		Map<String, Object> ddmFormFieldTemplateContext) {

		ddmFormFieldTemplateContext.put(
			"locale", LocaleUtil.toLanguageId(_locale));
	}

	protected void setDDMFormFieldTemplateContextLocalizable(
		Map<String, Object> ddmFormFieldTemplateContext, boolean localizable) {

		ddmFormFieldTemplateContext.put("localizable", localizable);
	}

	protected void setDDMFormFieldTemplateContextLocalizedValue(
		Map<String, Object> ddmFormFieldTemplateContext, String propertyName,
		LocalizedValue localizedValue) {

		String propertyValue = GetterUtil.getString(
			localizedValue.getString(_locale));

		if (_ddmFormRenderingContext.isViewMode()) {
			propertyValue = HtmlUtil.extractText(propertyValue);
		}

		ddmFormFieldTemplateContext.put(propertyName, propertyValue);
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
		Map<String, Object> changedProperties,
		DDMFormFieldOptions ddmFormFieldOptions) {

		List<KeyValuePair> keyValuePairs =
			(List<KeyValuePair>)changedProperties.get("options");

		if (keyValuePairs != null) {
			ddmFormFieldTemplateContext.put(
				"options", createOptions(keyValuePairs));
		}
		else if (addProperty(changedProperties, "options")) {
			ddmFormFieldTemplateContext.put(
				"options", createOptions(ddmFormFieldOptions));
		}
	}

	protected void setDDMFormFieldTemplateContextPathThemeImages(
		Map<String, Object> ddmFormFieldTemplateContext) {

		ddmFormFieldTemplateContext.put(
			"pathThemeImages",
			DDMFormTemplateContextFactoryUtil.getPathThemeImages(
				_ddmFormRenderingContext.getHttpServletRequest()));
	}

	protected void setDDMFormFieldTemplateContextReadOnly(
		Map<String, Object> ddmFormFieldTemplateContext,
		Map<String, Object> changedProperties, boolean defaultValue) {

		if (!addProperty(changedProperties, "readOnly")) {
			return;
		}

		boolean readOnly = MapUtil.getBoolean(
			changedProperties, "readOnly", defaultValue);

		ddmFormFieldTemplateContext.put("readOnly", readOnly);
	}

	protected void setDDMFormFieldTemplateContextRepeatable(
		Map<String, Object> ddmFormFieldTemplateContext, boolean repeatable) {

		ddmFormFieldTemplateContext.put("repeatable", repeatable);
	}

	protected void setDDMFormFieldTemplateContextRequired(
		Map<String, Object> ddmFormFieldTemplateContext,
		Map<String, Object> changedProperties, boolean defaultValue) {

		if (!addProperty(changedProperties, "required")) {
			return;
		}

		ddmFormFieldTemplateContext.put(
			"required",
			MapUtil.getBoolean(changedProperties, "required", defaultValue));
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
		Map<String, Object> changedProperties,
		Map<String, Object> ddmFormFieldTemplateContext, boolean defaultValue) {

		if (addProperty(changedProperties, "errorMessage")) {
			ddmFormFieldTemplateContext.put(
				"errorMessage",
				MapUtil.getString(changedProperties, "errorMessage"));
		}

		if (addProperty(changedProperties, "valid")) {
			ddmFormFieldTemplateContext.put(
				"valid",
				MapUtil.getBoolean(changedProperties, "valid", defaultValue));
		}
	}

	protected void setDDMFormFieldTemplateContextValidation(
		Map<String, Object> ddmFormFieldTemplateContext,
		Map<String, Object> changedProperties,
		DDMFormFieldValidation ddmFormFieldValidation) {

		if (ddmFormFieldValidation == null) {
			return;
		}

		Map<String, String> validation = new HashMap<>();

		validation.put(
			"dataType",
			GetterUtil.getString(
				changedProperties.get("validationDataType"),
				MapUtil.getString(changedProperties, "dataType")));
		validation.put(
			"errorMessage",
			GetterUtil.getString(ddmFormFieldValidation.getErrorMessage()));
		validation.put(
			"expression",
			GetterUtil.getString(ddmFormFieldValidation.getExpression()));

		ddmFormFieldTemplateContext.put("validation", validation);
	}

	protected void setDDMFormFieldTemplateContextValue(
		DDMFormField ddmFormField, Map<String, Object> changedProperties,
		Map<String, Object> ddmFormFieldTemplateContext, Value value) {

		if (changedProperties.get("value") != null) {
			ddmFormFieldTemplateContext.put(
				"value", changedProperties.get("value"));

			ddmFormFieldTemplateContext.put("valueChanged", true);
		}
		else if (value != null) {
			ddmFormFieldTemplateContext.put("value", value.getString(_locale));
		}
	}

	protected void setDDMFormFieldTemplateContextValueLocalizableValue(
		Map<String, Object> ddmFormFieldTemplateContext,
		DDMFormFieldValue ddmFormFieldValue) {

		if (ddmFormFieldValue == null) {
			return;
		}

		Value value = ddmFormFieldValue.getValue();

		if (!(value instanceof LocalizedValue)) {
			return;
		}

		DDMFormField ddmFormField = _ddmFormFieldsMap.get(
			ddmFormFieldValue.getName());

		DDMFormFieldValueAccessor<?> ddmFormFieldValueAccessor =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldValueAccessor(
				ddmFormField.getType());

		Map<String, Object> localizedValue = new HashMap<>();

		for (Locale availableLocale : value.getAvailableLocales()) {
			String languageId = LanguageUtil.getLanguageId(availableLocale);

			if (ddmFormFieldValueAccessor == null) {
				localizedValue.put(
					languageId, value.getString(availableLocale));
			}
			else {
				localizedValue.put(
					languageId,
					ddmFormFieldValueAccessor.getValue(
						ddmFormFieldValue, availableLocale));
			}
		}

		ddmFormFieldTemplateContext.put("localizedValue", localizedValue);
	}

	protected void setDDMFormFieldTemplateContextVisibilityExpression(
		Map<String, Object> ddmFormFieldTemplateContext,
		String visibilityExpression) {

		ddmFormFieldTemplateContext.put(
			"visibilityExpression", visibilityExpression);
	}

	protected void setDDMFormFieldTemplateContextVisible(
		Map<String, Object> ddmFormFieldTemplateContext,
		Map<String, Object> changedProperties, boolean defaultValue) {

		if (!addProperty(changedProperties, "visible")) {
			return;
		}

		ddmFormFieldTemplateContext.put(
			"visible",
			MapUtil.getBoolean(changedProperties, "visible", defaultValue));
	}

	protected void setDDMFormFieldTypeServicesTracker(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker) {

		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
	}

	protected void setProperties(
		Map<String, Object> ddmFormFieldTemplateContext,
		DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue,
		int index, String ddmFormFieldParameterName) {

		setDDMFormFieldTemplateContextDataType(
			ddmFormFieldTemplateContext, ddmFormField.getDataType());
		setDDMFormFieldTemplateContextDir(ddmFormFieldTemplateContext);
		setDDMFormFieldTemplateContextInstanceId(
			ddmFormFieldTemplateContext, ddmFormFieldValue.getInstanceId());
		setDDMFormFieldTemplateContextLocale(ddmFormFieldTemplateContext);
		setDDMFormFieldTemplateContextLocalizable(
			ddmFormFieldTemplateContext, ddmFormField.isLocalizable());
		setDDMFormFieldTemplateContextLocalizedValue(
			ddmFormFieldTemplateContext, "tip", ddmFormField.getTip());
		setDDMFormFieldTemplateContextPathThemeImages(
			ddmFormFieldTemplateContext);
		setDDMFormFieldTemplateContextRepeatable(
			ddmFormFieldTemplateContext, ddmFormField.isRepeatable());
		setDDMFormFieldTemplateContextShowLabel(
			ddmFormFieldTemplateContext, ddmFormField.isShowLabel());
		setDDMFormFieldTemplateContextType(
			ddmFormFieldTemplateContext, ddmFormField.getType());
		setDDMFormFieldTemplateContextVisibilityExpression(
			ddmFormFieldTemplateContext,
			ddmFormField.getVisibilityExpression());
	}

	protected void setPropertiesChangeableByRule(
		Map<String, Object> ddmFormFieldTemplateContext,
		Map<String, Object> changedProperties, DDMFormField ddmFormField,
		DDMFormFieldValue ddmFormFieldValue) {

		setDDMFormFieldTemplateContextEvaluable(
			ddmFormFieldTemplateContext, ddmFormField, changedProperties,
			ddmFormField.getProperty("evaluable"));
		setDDMFormFieldTemplateContextOptions(
			ddmFormFieldTemplateContext, changedProperties,
			ddmFormField.getDDMFormFieldOptions());
		setDDMFormFieldTemplateContextReadOnly(
			ddmFormFieldTemplateContext, changedProperties,
			ddmFormField.isReadOnly());
		setDDMFormFieldTemplateContextRequired(
			ddmFormFieldTemplateContext, changedProperties,
			ddmFormField.isRequired());
		setDDMFormFieldTemplateContextValid(
			changedProperties, ddmFormFieldTemplateContext, true);
		setDDMFormFieldTemplateContextValue(
			ddmFormField, changedProperties, ddmFormFieldTemplateContext,
			ddmFormFieldValue.getValue());
		setDDMFormFieldTemplateContextValueLocalizableValue(
			ddmFormFieldTemplateContext, ddmFormFieldValue);
		setDDMFormFieldTemplateContextValidation(
			ddmFormFieldTemplateContext, changedProperties,
			ddmFormField.getDDMFormFieldValidation());
		setDDMFormFieldTemplateContextVisible(
			ddmFormFieldTemplateContext, changedProperties, true);
	}

	private final Map<String, DDMFormField> _ddmFormFieldsMap;
	private final Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
		_ddmFormFieldsPropertyChanges;
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;
	private final List<DDMFormFieldValue> _ddmFormFieldValues;
	private final DDMFormRenderingContext _ddmFormRenderingContext;
	private final Locale _locale;
	private final boolean _pageEnabled;

}