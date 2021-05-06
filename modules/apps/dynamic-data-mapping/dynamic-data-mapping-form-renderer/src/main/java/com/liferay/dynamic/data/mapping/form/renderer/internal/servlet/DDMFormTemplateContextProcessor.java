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

package com.liferay.dynamic.data.mapping.form.renderer.internal.servlet;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidationExpression;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Marcellus Tavares
 */
public class DDMFormTemplateContextProcessor {

	public DDMFormTemplateContextProcessor(
		JSONObject jsonObject, String languageId) {

		_jsonObject = jsonObject;

		_ddmForm = new DDMForm();
		_ddmFormLayout = new DDMFormLayout();
		_ddmFormValues = new DDMFormValues(_ddmForm);

		_locale = LocaleUtil.fromLanguageId(languageId);

		initModels();

		process();
	}

	public DDMForm getDDMForm() {
		return _ddmForm;
	}

	public long getDDMFormInstanceId() {
		return _ddmFormInstanceId;
	}

	public DDMFormLayout getDDMFormLayout() {
		return _ddmFormLayout;
	}

	public DDMFormValues getDDMFormValues() {
		return _ddmFormValues;
	}

	public long getGroupId() {
		return _groupId;
	}

	protected void addDDMFormDDMFormField(JSONObject jsonObject) {
		Map<String, DDMFormField> ddmFormFields = _ddmForm.getDDMFormFieldsMap(
			true);

		String fieldName = jsonObject.getString("fieldName");

		if (ddmFormFields.containsKey(fieldName)) {
			return;
		}

		_ddmForm.addDDMFormField(getDDMFormField(jsonObject));
	}

	protected void addDDMFormValuesDDMFormFieldValue(JSONObject jsonObject) {
		_ddmFormValues.addDDMFormFieldValue(getDDMFormFieldValue(jsonObject));
	}

	protected DDMFormField getDDMFormField(JSONObject jsonObject) {
		String name = jsonObject.getString("fieldName");
		String type = jsonObject.getString("type");

		DDMFormField ddmFormField = new DDMFormField(name, type);

		setDDMFormFieldConfirmationErrorMessage(
			jsonObject.getString("confirmationErrorMessage"), ddmFormField);
		setDDMFormFieldConfirmationLabel(
			jsonObject.getString("confirmationLabel"), ddmFormField);
		setDDMFormFieldCustomProperties(jsonObject, ddmFormField);
		setDDMFormFieldDataType(jsonObject.getString("dataType"), ddmFormField);
		setDDMFormFieldFieldName(
			jsonObject.getString("fieldName"), ddmFormField);
		setDDMFormFieldFieldReference(
			jsonObject.getString("fieldReference"), ddmFormField);
		setDDMFormFieldLabel(jsonObject.getString("label"), ddmFormField);
		setDDMFormFieldLocalizable(
			jsonObject.getBoolean("localizable", false), ddmFormField);
		setDDMFormFieldMultiple(
			jsonObject.getBoolean("multiple"), ddmFormField);
		setDDMFormFieldOptions(
			jsonObject.getJSONArray("options"), ddmFormField);
		setDDMFormFieldPlaceholder(
			jsonObject.getString("placeholder"), ddmFormField);
		setDDMFormFieldPropertyDDMStructureId(jsonObject, ddmFormField);
		setDDMFormFieldPropertyDDMStructureLayoutId(jsonObject, ddmFormField);
		setDDMFormFieldPropertyOptions(jsonObject, ddmFormField, "columns");
		setDDMFormFieldPropertyRows(jsonObject, ddmFormField);
		setDDMFormFieldPropertyUpgradedStructure(jsonObject, ddmFormField);
		setDDMFormFieldReadOnly(
			jsonObject.getBoolean("readOnly", false), ddmFormField);
		setDDMFormFieldRepeatable(
			jsonObject.getBoolean("repeatable", false), ddmFormField);
		setDDMFormFieldRequired(
			jsonObject.getBoolean("required", false), ddmFormField);
		setDDMFormFieldText(jsonObject.getString("text"), ddmFormField);
		setDDMFormFieldTooltip(jsonObject.getString("tooltip"), ddmFormField);
		setDDMFormFieldValid(
			jsonObject.getBoolean("valid", true), ddmFormField);
		setDDMFormFieldValidation(
			jsonObject.getJSONObject("validation"), ddmFormField);
		setDDMFormFieldVisibilityExpression(
			jsonObject.getString("visibilityExpression"), ddmFormField);

		setDDMFormFieldNestedFields(
			jsonObject.getJSONArray("nestedFields"), ddmFormField);

		return ddmFormField;
	}

	protected DDMFormFieldOptions getDDMFormFieldOptions(JSONArray jsonArray) {
		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String value = jsonObject.getString("value");

			ddmFormFieldOptions.addOptionLabel(
				value, _locale, jsonObject.getString("label"));
			ddmFormFieldOptions.addOptionReference(
				value, jsonObject.getString("reference"));
		}

		return ddmFormFieldOptions;
	}

	protected DDMFormFieldValue getDDMFormFieldValue(JSONObject jsonObject) {
		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setConfirmationValue(
			jsonObject.get("confirmationValue"));
		ddmFormFieldValue.setFieldReference(
			jsonObject.getString("fieldReference"));
		ddmFormFieldValue.setName(jsonObject.getString("fieldName"));
		ddmFormFieldValue.setInstanceId(jsonObject.getString("instanceId"));

		setDDMFormFieldValueValue(
			jsonObject.getString("value"),
			jsonObject.getBoolean("localizable", false), ddmFormFieldValue);

		setDDMFormFieldValueNestedFieldValues(
			jsonObject.getJSONArray("nestedFields"), ddmFormFieldValue);

		return ddmFormFieldValue;
	}

	protected DDMFormRule getDDMFormRule(JSONObject jsonObject) {
		List<String> actions = getDDMFormRuleActions(
			jsonObject.getJSONArray("actions"));

		return new DDMFormRule(actions, jsonObject.getString("condition"));
	}

	protected List<String> getDDMFormRuleActions(JSONArray jsonArray) {
		List<String> actions = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			actions.add(jsonArray.getString(i));
		}

		return actions;
	}

	protected List<DDMFormRule> getDDMFormRules(JSONArray jsonArray) {
		List<DDMFormRule> ddmFormRules = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			DDMFormRule ddmFormRule = getDDMFormRule(
				jsonArray.getJSONObject(i));

			ddmFormRules.add(ddmFormRule);
		}

		return ddmFormRules;
	}

	protected LocalizedValue getLocalizedValue(String value) {
		LocalizedValue localizedValue = new LocalizedValue(_locale);

		localizedValue.addString(_locale, value);

		return localizedValue;
	}

	protected void initModels() {
		setDDMFormDefaultLocale();
		setDDMFormInstanceId();
		setDDMFormRules();
		setDDMFormValuesAvailableLocales();
		setDDMFormValuesDefaultLocale();
		setGroupId();
	}

	protected void process() {
		_ddmFormLayout.setNextPage(_jsonObject.getInt("nextPage"));
		_ddmFormLayout.setPreviousPage(_jsonObject.getInt("previousPage"));

		traversePages(_jsonObject.getJSONArray("pages"));
	}

	protected void setDDMFormDefaultLocale() {
		_ddmForm.setDefaultLocale(_locale);
	}

	protected void setDDMFormFieldConfirmationErrorMessage(
		String confirmationErrorMessage, DDMFormField ddmFormField) {

		ddmFormField.setProperty(
			"confirmationErrorMessage",
			getLocalizedValue(GetterUtil.getString(confirmationErrorMessage)));
	}

	protected void setDDMFormFieldConfirmationLabel(
		String confirmationLabel, DDMFormField ddmFormField) {

		ddmFormField.setProperty(
			"confirmationLabel",
			getLocalizedValue(GetterUtil.getString(confirmationLabel)));
	}

	protected void setDDMFormFieldCustomProperties(
		JSONObject jsonObject, DDMFormField ddmFormField) {

		Iterator<String> iterator = jsonObject.keys();

		Map<String, Object> properties = ddmFormField.getProperties();

		while (iterator.hasNext()) {
			String key = iterator.next();

			if (!properties.containsKey(key) && !key.equals("dataSourceType")) {
				ddmFormField.setProperty(key, jsonObject.get(key));
			}
		}
	}

	protected void setDDMFormFieldDataType(
		String dataType, DDMFormField ddmFormField) {

		ddmFormField.setDataType(GetterUtil.getString(dataType));
	}

	protected void setDDMFormFieldFieldName(
		String fieldName, DDMFormField ddmFormField) {

		ddmFormField.setName(GetterUtil.getString(fieldName));
	}

	protected void setDDMFormFieldFieldReference(
		String fieldReference, DDMFormField ddmFormField) {

		ddmFormField.setFieldReference(GetterUtil.getString(fieldReference));
	}

	protected void setDDMFormFieldLabel(
		String label, DDMFormField ddmFormField) {

		ddmFormField.setLabel(getLocalizedValue(GetterUtil.getString(label)));
	}

	protected void setDDMFormFieldLocalizable(
		boolean localizable, DDMFormField ddmFormField) {

		ddmFormField.setLocalizable(localizable);
	}

	protected void setDDMFormFieldMultiple(
		boolean multiple, DDMFormField ddmFormField) {

		ddmFormField.setMultiple(multiple);
	}

	protected void setDDMFormFieldNestedFields(
		JSONArray jsonArray, DDMFormField ddmFormField) {

		if (jsonArray == null) {
			return;
		}

		for (int i = 0; i < jsonArray.length(); i++) {
			DDMFormField nestedDDMFormField = getDDMFormField(
				jsonArray.getJSONObject(i));

			ddmFormField.addNestedDDMFormField(nestedDDMFormField);
		}
	}

	protected void setDDMFormFieldOptions(
		JSONArray jsonArray, DDMFormField ddmFormField) {

		if (jsonArray == null) {
			return;
		}

		ddmFormField.setDDMFormFieldOptions(getDDMFormFieldOptions(jsonArray));
	}

	protected void setDDMFormFieldPlaceholder(
		String placeholder, DDMFormField ddmFormField) {

		ddmFormField.setProperty(
			"placeholder",
			getLocalizedValue(GetterUtil.getString(placeholder)));
	}

	protected void setDDMFormFieldPropertyDDMStructureId(
		JSONObject jsonObject, DDMFormField ddmFormField) {

		if (!Objects.equals(ddmFormField.getType(), "fieldset")) {
			return;
		}

		ddmFormField.setProperty(
			"ddmStructureId", jsonObject.getLong("ddmStructureId"));
	}

	protected void setDDMFormFieldPropertyDDMStructureLayoutId(
		JSONObject jsonObject, DDMFormField ddmFormField) {

		if (!Objects.equals(ddmFormField.getType(), "fieldset")) {
			return;
		}

		ddmFormField.setProperty(
			"ddmStructureLayoutId", jsonObject.getLong("ddmStructureLayoutId"));
	}

	protected void setDDMFormFieldPropertyFieldSetRows(
		JSONObject jsonObject, DDMFormField ddmFormField) {

		JSONArray jsonArray = jsonObject.getJSONArray("rows");

		if (jsonArray == null) {
			return;
		}

		ddmFormField.setProperty("rows", jsonArray.toString());
	}

	protected void setDDMFormFieldPropertyOptions(
		JSONObject jsonObject, DDMFormField ddmFormField, String property) {

		JSONArray jsonArray = jsonObject.getJSONArray(property);

		if (jsonArray == null) {
			return;
		}

		ddmFormField.setProperty(property, getDDMFormFieldOptions(jsonArray));
	}

	protected void setDDMFormFieldPropertyRows(
		JSONObject jsonObject, DDMFormField ddmFormField) {

		String type = jsonObject.getString("type");

		if (type.equals("grid")) {
			setDDMFormFieldPropertyOptions(jsonObject, ddmFormField, "rows");
		}
		else if (type.equals("fieldset")) {
			setDDMFormFieldPropertyFieldSetRows(jsonObject, ddmFormField);
		}
	}

	protected void setDDMFormFieldPropertyUpgradedStructure(
		JSONObject jsonObject, DDMFormField ddmFormField) {

		if (!Objects.equals(ddmFormField.getType(), "fieldset")) {
			return;
		}

		ddmFormField.setProperty(
			"upgradedStructure", jsonObject.getBoolean("upgradedStructure"));
	}

	protected void setDDMFormFieldReadOnly(
		boolean readOnly, DDMFormField ddmFormField) {

		ddmFormField.setReadOnly(readOnly);
	}

	protected void setDDMFormFieldRepeatable(
		boolean repeatable, DDMFormField ddmFormField) {

		ddmFormField.setRepeatable(repeatable);
	}

	protected void setDDMFormFieldRequired(
		boolean required, DDMFormField ddmFormField) {

		ddmFormField.setRequired(required);
	}

	protected void setDDMFormFieldText(String text, DDMFormField ddmFormField) {
		ddmFormField.setProperty(
			"text", getLocalizedValue(GetterUtil.getString(text)));
	}

	protected void setDDMFormFieldTooltip(
		String tooltip, DDMFormField ddmFormField) {

		ddmFormField.setProperty(
			"tooltip", getLocalizedValue(GetterUtil.getString(tooltip)));
	}

	protected void setDDMFormFieldValid(
		boolean valid, DDMFormField ddmFormField) {

		ddmFormField.setProperty("valid", valid);
	}

	protected void setDDMFormFieldValidation(
		JSONObject jsonObject, DDMFormField ddmFormField) {

		if ((jsonObject == null) || !jsonObject.has("expression")) {
			return;
		}

		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		ddmFormFieldValidation.setErrorMessageLocalizedValue(
			getLocalizedValue(jsonObject.getString("errorMessage")));

		JSONObject expressionJSONObject = jsonObject.getJSONObject(
			"expression");

		if (expressionJSONObject != null) {
			ddmFormFieldValidation.setDDMFormFieldValidationExpression(
				new DDMFormFieldValidationExpression() {
					{
						setName(expressionJSONObject.getString("name"));
						setValue(expressionJSONObject.getString("value"));
					}
				});
		}
		else {
			ddmFormFieldValidation.setDDMFormFieldValidationExpression(
				new DDMFormFieldValidationExpression() {
					{
						setValue(jsonObject.getString("expression"));
					}
				});
		}

		ddmFormFieldValidation.setParameterLocalizedValue(
			getLocalizedValue(jsonObject.getString("parameter")));

		ddmFormField.setDDMFormFieldValidation(ddmFormFieldValidation);
	}

	protected void setDDMFormFieldValueNestedFieldValues(
		JSONArray jsonArray, DDMFormFieldValue ddmFormFieldValue) {

		if (jsonArray == null) {
			return;
		}

		for (int i = 0; i < jsonArray.length(); i++) {
			DDMFormFieldValue nestedDDMFormFieldValue = getDDMFormFieldValue(
				jsonArray.getJSONObject(i));

			ddmFormFieldValue.addNestedDDMFormFieldValue(
				nestedDDMFormFieldValue);
		}
	}

	protected void setDDMFormFieldValueValue(
		String value, boolean localizable,
		DDMFormFieldValue ddmFormFieldValue) {

		if (localizable) {
			ddmFormFieldValue.setValue(getLocalizedValue(value));
		}
		else {
			ddmFormFieldValue.setValue(new UnlocalizedValue(value));
		}
	}

	protected void setDDMFormFieldVisibilityExpression(
		String visibilityExpression, DDMFormField ddmFormField) {

		ddmFormField.setVisibilityExpression(
			GetterUtil.getString(visibilityExpression));
	}

	protected void setDDMFormInstanceId() {
		_ddmFormInstanceId = _jsonObject.getLong("formId", 0);
	}

	protected void setDDMFormRules() {
		List<DDMFormRule> ddmFormRules = getDDMFormRules(
			_jsonObject.getJSONArray("rules"));

		_ddmForm.setDDMFormRules(ddmFormRules);
	}

	protected void setDDMFormValuesAvailableLocales() {
		_ddmFormValues.addAvailableLocale(_locale);
	}

	protected void setDDMFormValuesDefaultLocale() {
		_ddmFormValues.setDefaultLocale(_locale);
	}

	protected void setGroupId() {
		_groupId = _jsonObject.getLong("groupId", 0);
	}

	protected void traverseColumns(
		JSONArray jsonArray, DDMFormLayoutRow ddmFormLayoutRow) {

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			DDMFormLayoutColumn ddmFormLayoutColumn = new DDMFormLayoutColumn(
				jsonObject.getInt("size"));

			traverseFields(
				jsonObject.getJSONArray("fields"), ddmFormLayoutColumn);

			ddmFormLayoutRow.addDDMFormLayoutColumn(ddmFormLayoutColumn);
		}
	}

	protected void traverseFields(
		JSONArray jsonArray, DDMFormLayoutColumn ddmFormLayoutColumn) {

		Set<String> ddmFormFieldNames = new LinkedHashSet<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			addDDMFormDDMFormField(jsonObject);
			addDDMFormValuesDDMFormFieldValue(jsonObject);

			ddmFormFieldNames.add(jsonObject.getString("fieldName"));
		}

		ddmFormLayoutColumn.setDDMFormFieldNames(
			ListUtil.fromCollection(ddmFormFieldNames));
	}

	protected void traversePages(JSONArray jsonArray) {
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			DDMFormLayoutPage ddmFormLayoutPage = new DDMFormLayoutPage();

			ddmFormLayoutPage.setDescription(
				getLocalizedValue(jsonObject.getString("description")));
			ddmFormLayoutPage.setTitle(
				getLocalizedValue(jsonObject.getString("title")));

			traverseRows(jsonObject.getJSONArray("rows"), ddmFormLayoutPage);

			_ddmFormLayout.addDDMFormLayoutPage(ddmFormLayoutPage);
		}
	}

	protected void traverseRows(
		JSONArray jsonArray, DDMFormLayoutPage ddmFormLayoutPage) {

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			DDMFormLayoutRow ddmFormLayoutRow = new DDMFormLayoutRow();

			traverseColumns(
				jsonObject.getJSONArray("columns"), ddmFormLayoutRow);

			ddmFormLayoutPage.addDDMFormLayoutRow(ddmFormLayoutRow);
		}
	}

	private final DDMForm _ddmForm;
	private long _ddmFormInstanceId;
	private final DDMFormLayout _ddmFormLayout;
	private final DDMFormValues _ddmFormValues;
	private long _groupId;
	private final JSONObject _jsonObject;
	private final Locale _locale;

}