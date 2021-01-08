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

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluator;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorFieldContextKey;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.constants.DDMFormRendererConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidationExpression;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.language.constants.LanguageConstants;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Marcellus Tavares
 */
public class DDMFormFieldTemplateContextFactory {

	public DDMFormFieldTemplateContextFactory(
		DDMFormEvaluator ddmFormEvaluator, String ddmFormFieldName,
		Map<String, DDMFormField> ddmFormFieldsMap,
		Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
			ddmFormFieldsPropertyChanges,
		List<DDMFormFieldValue> ddmFormFieldValues,
		DDMFormRenderingContext ddmFormRenderingContext,
		DDMStructureLayoutLocalService ddmStructureLayoutLocalService,
		DDMStructureLocalService ddmStructureLocalService,
		JSONFactory jsonFactory, boolean pageEnabled,
		DDMFormLayout parentDDMFormLayout) {

		_ddmFormEvaluator = ddmFormEvaluator;
		_ddmFormFieldName = ddmFormFieldName;
		_ddmFormFieldsMap = ddmFormFieldsMap;
		_ddmFormFieldsPropertyChanges = ddmFormFieldsPropertyChanges;
		_ddmFormFieldValues = ddmFormFieldValues;
		_ddmFormRenderingContext = ddmFormRenderingContext;
		_ddmStructureLayoutLocalService = ddmStructureLayoutLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
		_jsonFactory = jsonFactory;
		_pageEnabled = pageEnabled;
		_parentDDMFormLayout = parentDDMFormLayout;

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
		ddmFormFieldRenderingContext.setReturnFullContext(
			_ddmFormRenderingContext.isReturnFullContext());
		ddmFormFieldRenderingContext.setViewMode(
			_ddmFormRenderingContext.isViewMode());

		return ddmFormFieldRenderingContext;
	}

	protected Map<String, Object> createDDMFormFieldTemplateContext(
		DDMFormField ddmFormField, String ddmFormFieldParameterName) {

		Map<String, Object> ddmFormFieldTemplateContext =
			HashMapBuilder.<String, Object>put(
				"type", ddmFormField.getType()
			).build();

		setDDMFormFieldTemplateContextFieldName(
			ddmFormFieldTemplateContext, ddmFormField.getName());
		setDDMFormFieldTemplateContextFieldReference(
			ddmFormFieldTemplateContext, ddmFormField.getFieldReference());
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
				ddmFormFieldTemplateContext, ddmFormField, ddmFormFieldValue);
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

		if (ddmFormFieldValues == null) {
			ddmFormFieldTemplateContexts.add(
				createDDMFormFieldTemplateContext(
					new DDMFormFieldValue() {
						{
							setName(_ddmFormFieldName);
						}
					},
					new HashMap<>(), index++, parentDDMFormFieldParameterName));
		}
		else {
			for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
				Map<String, Object> changedProperties = getChangedProperties(
					ddmFormFieldValue);

				DDMFormField ddmFormField = _ddmFormFieldsMap.get(
					ddmFormFieldValue.getName());

				if (!_ddmFormRenderingContext.isReturnFullContext() &&
					changedProperties.isEmpty() && !ddmFormField.isRequired()) {

					continue;
				}

				ddmFormFieldTemplateContexts.add(
					createDDMFormFieldTemplateContext(
						ddmFormFieldValue, changedProperties, index++,
						parentDDMFormFieldParameterName));
			}
		}

		return ddmFormFieldTemplateContexts;
	}

	protected List<Object> createNestedDDMFormFieldTemplateContext(
		DDMFormFieldValue parentDDMFormFieldValue,
		String parentDDMFormFieldParameterName) {

		List<Object> nestedDDMFormFieldTemplateContext = new ArrayList<>();

		Map<String, List<DDMFormFieldValue>> nestedDDMFormFieldValuesMap =
			parentDDMFormFieldValue.getNestedDDMFormFieldValuesMap();

		List<DDMFormFieldValue> nestedDDMFormFieldValues =
			parentDDMFormFieldValue.getNestedDDMFormFieldValues();

		Stream<DDMFormFieldValue> nestedDDMFormFieldValuesStream =
			nestedDDMFormFieldValues.stream();

		nestedDDMFormFieldValuesStream.map(
			DDMFormFieldValue::getName
		).distinct(
		).map(
			nestedDDMFormFieldValuesMap::get
		).map(
			ddmFormFieldValues -> createDDMFormFieldTemplateContexts(
				ddmFormFieldValues, parentDDMFormFieldParameterName)
		).forEach(
			nestedDDMFormFieldTemplateContext::addAll
		);

		return nestedDDMFormFieldTemplateContext;
	}

	protected List<Map<String, String>> createOptions(
		DDMFormFieldOptions ddmFormFieldOptions) {

		List<Map<String, String>> list = new ArrayList<>();

		if (ddmFormFieldOptions == null) {
			return list;
		}

		Map<String, LocalizedValue> options = ddmFormFieldOptions.getOptions();

		Map<String, String> optionsReferences =
			ddmFormFieldOptions.getOptionsReferences();

		for (Map.Entry<String, LocalizedValue> entry : options.entrySet()) {
			Map<String, String> option = new HashMap<>();

			LocalizedValue localizedValue = entry.getValue();

			option.put("label", localizedValue.getString(_locale));

			option.put("reference", optionsReferences.get(entry.getKey()));
			option.put("value", entry.getKey());

			list.add(option);
		}

		return list;
	}

	protected List<Map<String, String>> createOptions(
		List<KeyValuePair> keyValuePairs) {

		List<Map<String, String>> list = new ArrayList<>();

		for (KeyValuePair keyValuePair : keyValuePairs) {
			list.add(
				HashMapBuilder.put(
					"label", keyValuePair.getValue()
				).put(
					"value", keyValuePair.getKey()
				).build());
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

		changedProperties.put("enabled", _pageEnabled);

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

		if (_isFieldSetField(ddmFormField)) {
			_setDDMFormFieldFieldSetTemplateContextContributedParameters(
				ddmFormField, ddmFormFieldRenderingContext);
		}

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

	protected void setDDMFormFieldTemplateContextEnabled(
		Map<String, Object> ddmFormFieldTemplateContext,
		Map<String, Object> changedProperties, boolean defaultValue) {

		if (!addProperty(changedProperties, "enabled")) {
			return;
		}

		ddmFormFieldTemplateContext.put(
			"enabled",
			MapUtil.getBoolean(changedProperties, "enabled", defaultValue));
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

	protected void setDDMFormFieldTemplateContextFieldReference(
		Map<String, Object> ddmFormFieldTemplateContext,
		String fieldReference) {

		ddmFormFieldTemplateContext.put("fieldReference", fieldReference);
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

		if (localizedValue == null) {
			return;
		}

		String propertyValue = GetterUtil.getString(
			localizedValue.getString(_locale));

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
		List<Object> nestedDDMFormFieldTemplateContexts) {

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

		LocalizedValue errorMessageLocalizedValue =
			ddmFormFieldValidation.getErrorMessageLocalizedValue();

		String errorMessage = StringPool.BLANK;

		if (errorMessageLocalizedValue != null) {
			errorMessage = GetterUtil.getString(
				errorMessageLocalizedValue.getString(_locale));
		}

		Map<String, Object> validation = HashMapBuilder.<String, Object>put(
			"dataType",
			GetterUtil.getString(
				changedProperties.get("validationDataType"),
				MapUtil.getString(changedProperties, "dataType"))
		).put(
			"errorMessage", errorMessage
		).put(
			"expression",
			() -> {
				DDMFormFieldValidationExpression
					ddmFormFieldValidationExpression =
						ddmFormFieldValidation.
							getDDMFormFieldValidationExpression();

				return HashMapBuilder.put(
					"name",
					GetterUtil.getString(
						ddmFormFieldValidationExpression.getName())
				).put(
					"value",
					GetterUtil.getString(
						ddmFormFieldValidationExpression.getValue())
				).build();
			}
		).put(
			"fieldName",
			GetterUtil.getString(changedProperties.get("validationFieldName"))
		).put(
			"parameter",
			() -> {
				LocalizedValue parameterLocalizedValue =
					ddmFormFieldValidation.getParameterLocalizedValue();

				if (parameterLocalizedValue != null) {
					return GetterUtil.getString(
						parameterLocalizedValue.getString(_locale));
				}

				return StringPool.BLANK;
			}
		).build();

		ddmFormFieldTemplateContext.put("validation", validation);
	}

	protected void setDDMFormFieldTemplateContextValue(
		Map<String, Object> changedProperties,
		Map<String, Object> ddmFormFieldTemplateContext, Value value) {

		if (Validator.isNotNull(changedProperties.get("value"))) {
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

		Map<String, Object> localizedValues = new HashMap<>();

		for (Locale availableLocale : value.getAvailableLocales()) {
			String languageId = LanguageUtil.getLanguageId(availableLocale);

			Object localizedValue = value.getString(availableLocale);

			if (ddmFormFieldValueAccessor != null) {
				Object ddmFormFieldValueAccessorValue =
					ddmFormFieldValueAccessor.getValue(
						ddmFormFieldValue, availableLocale);

				if (!(ddmFormFieldValueAccessorValue instanceof BigDecimal)) {
					localizedValue = ddmFormFieldValueAccessor.getValue(
						ddmFormFieldValue, availableLocale);
				}
			}

			localizedValues.put(languageId, localizedValue);
		}

		ddmFormFieldTemplateContext.put("localizedValue", localizedValues);
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
		DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue) {

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

		setDDMFormFieldTemplateContextEnabled(
			ddmFormFieldTemplateContext, changedProperties, true);
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
			changedProperties, ddmFormFieldTemplateContext,
			ddmFormFieldValue.getValue());
		setDDMFormFieldTemplateContextValueLocalizableValue(
			ddmFormFieldTemplateContext, ddmFormFieldValue);
		setDDMFormFieldTemplateContextValidation(
			ddmFormFieldTemplateContext, changedProperties,
			ddmFormField.getDDMFormFieldValidation());
		setDDMFormFieldTemplateContextVisible(
			ddmFormFieldTemplateContext, changedProperties, true);
	}

	private Stream<Map<String, Object>> _getColumnsStream(
		Map<String, Object> row) {

		if (!row.containsKey("columns")) {
			Stream.empty();
		}

		List<Map<String, Object>> columns = (List<Map<String, Object>>)row.get(
			"columns");

		return columns.stream();
	}

	private DDMFormLayout _getDDMFormLayout(long ddmStructureLayoutId) {
		try {
			return _ddmStructureLayoutLocalService.
				getStructureLayoutDDMFormLayout(
					_ddmStructureLayoutLocalService.getStructureLayout(
						ddmStructureLayoutId));
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}
		}

		return new DDMFormLayout();
	}

	private long _getDefaultDDMFormLayoutId(long ddmStructureId) {
		DDMStructure ddmStructure = _ddmStructureLocalService.fetchDDMStructure(
			ddmStructureId);

		return ddmStructure.getDefaultDDMStructureLayoutId();
	}

	private Stream<Map<String, Object>> _getFieldsStream(
		Map<String, Object> column) {

		if (!column.containsKey("fields")) {
			Stream.empty();
		}

		List<Map<String, Object>> fields =
			(List<Map<String, Object>>)column.get("fields");

		return fields.stream();
	}

	private List<Map<String, Object>> _getNestedFieldsContext(
		List<Object> pages) {

		if (ListUtil.isEmpty(pages)) {
			return new ArrayList<>();
		}

		Stream<Object> stream = pages.stream();

		return stream.flatMap(
			this::_getRowsStream
		).flatMap(
			this::_getColumnsStream
		).flatMap(
			this::_getFieldsStream
		).collect(
			Collectors.toList()
		);
	}

	private Stream<Map<String, Object>> _getRowsStream(Object page) {
		Map<String, Object> pageContext = (Map<String, Object>)page;

		if (!pageContext.containsKey("rows")) {
			Stream.empty();
		}

		List<Map<String, Object>> rows =
			(List<Map<String, Object>>)pageContext.get("rows");

		return rows.stream();
	}

	private boolean _isFieldSetField(DDMFormField ddmFormField) {
		if (StringUtil.equals(ddmFormField.getType(), "fieldset")) {
			return true;
		}

		return false;
	}

	private void _setDDMFormFieldFieldSetTemplateContextContributedParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		Map<String, Object> properties =
			ddmFormFieldRenderingContext.getProperties();

		if (Validator.isNotNull(ddmFormField.getProperty("ddmStructureId")) &&
			!properties.containsKey("nestedFields")) {

			long ddmStructureLayoutId = GetterUtil.getLong(
				ddmFormField.getProperty("ddmStructureLayoutId"));

			if (ddmStructureLayoutId == 0) {
				ddmStructureLayoutId = _getDefaultDDMFormLayoutId(
					GetterUtil.getLong(
						ddmFormField.getProperty("ddmStructureId")));
			}

			DDMFormLayout ddmFormLayout = _getDDMFormLayout(
				ddmStructureLayoutId);

			ddmFormLayout.setDDMFormRules(
				_parentDDMFormLayout.getDDMFormRules());

			String rows = MapUtil.getString(
				ddmFormField.getProperties(), "rows");

			if (Validator.isNotNull(rows)) {
				_updateDDMFormLayoutRows(ddmFormLayout, rows);
			}

			DDMFormPagesTemplateContextFactory
				ddmFormPagesTemplateContextFactory =
					new DDMFormPagesTemplateContextFactory(
						ddmFormField.getDDMForm(), ddmFormLayout,
						_ddmFormRenderingContext,
						_ddmStructureLayoutLocalService,
						_ddmStructureLocalService, _jsonFactory);

			ddmFormPagesTemplateContextFactory.setDDMFormEvaluator(
				_ddmFormEvaluator);
			ddmFormPagesTemplateContextFactory.
				setDDMFormFieldTypeServicesTracker(
					_ddmFormFieldTypeServicesTracker);

			ddmFormFieldRenderingContext.setProperty(
				"nestedFields",
				_getNestedFieldsContext(
					ddmFormPagesTemplateContextFactory.create()));
		}
	}

	private DDMFormLayoutColumn _toDDMFormLayoutColumn(JSONObject jsonObject) {
		DDMFormLayoutColumn ddmFormLayoutColumn = new DDMFormLayoutColumn();

		ddmFormLayoutColumn.setDDMFormFieldNames(
			JSONUtil.toStringList(jsonObject.getJSONArray("fields")));
		ddmFormLayoutColumn.setSize(jsonObject.getInt("size"));

		return ddmFormLayoutColumn;
	}

	private List<DDMFormLayoutColumn> _toDDMFormLayoutColumns(
		JSONArray jsonArray) {

		List<DDMFormLayoutColumn> ddmFormLayoutColumns = new ArrayList<>(
			jsonArray.length());

		for (int i = 0; i < jsonArray.length(); i++) {
			ddmFormLayoutColumns.add(
				_toDDMFormLayoutColumn(jsonArray.getJSONObject(i)));
		}

		return ddmFormLayoutColumns;
	}

	private DDMFormLayoutRow _toDDMFormLayoutRow(JSONObject jsonObject) {
		DDMFormLayoutRow ddmFormLayoutRow = new DDMFormLayoutRow();

		ddmFormLayoutRow.setDDMFormLayoutColumns(
			_toDDMFormLayoutColumns(jsonObject.getJSONArray("columns")));

		return ddmFormLayoutRow;
	}

	private List<DDMFormLayoutRow> _toDDMFormLayoutRows(JSONArray jsonArray) {
		List<DDMFormLayoutRow> ddmFormLayoutRows = new ArrayList<>(
			jsonArray.length());

		for (int i = 0; i < jsonArray.length(); i++) {
			ddmFormLayoutRows.add(
				_toDDMFormLayoutRow(jsonArray.getJSONObject(i)));
		}

		return ddmFormLayoutRows;
	}

	private void _updateDDMFormLayoutRows(
		DDMFormLayout ddmFormLayout, String rowsJSON) {

		try {
			DDMFormLayoutPage ddmFormLayoutPage =
				ddmFormLayout.getDDMFormLayoutPage(0);

			ddmFormLayoutPage.setDDMFormLayoutRows(
				_toDDMFormLayoutRows(_jsonFactory.createJSONArray(rowsJSON)));
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException, jsonException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormFieldTemplateContextFactory.class);

	private final DDMFormEvaluator _ddmFormEvaluator;
	private final String _ddmFormFieldName;
	private final Map<String, DDMFormField> _ddmFormFieldsMap;
	private final Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
		_ddmFormFieldsPropertyChanges;
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;
	private final List<DDMFormFieldValue> _ddmFormFieldValues;
	private final DDMFormRenderingContext _ddmFormRenderingContext;
	private final DDMStructureLayoutLocalService
		_ddmStructureLayoutLocalService;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final JSONFactory _jsonFactory;
	private final Locale _locale;
	private final boolean _pageEnabled;
	private final DDMFormLayout _parentDDMFormLayout;

}