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

package com.liferay.dynamic.data.mapping.io.internal;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * @author Marcellus Tavares
 */
public class JSONObjectToDDMFormFieldTransformer {

	public JSONObjectToDDMFormFieldTransformer(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		JSONFactory jsonFactory) {

		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
		_jsonFactory = jsonFactory;
	}

	public DDMFormField transform(JSONObject jsonObject)
		throws PortalException {

		DDMFormField ddmFormField = getDDMFormField(jsonObject);

		return ddmFormField;
	}

	protected void addOptionValueLabels(
		JSONObject jsonObject, DDMFormFieldOptions ddmFormFieldOptions,
		String optionValue) {

		Iterator<String> itr = jsonObject.keys();

		while (itr.hasNext()) {
			String languageId = itr.next();

			ddmFormFieldOptions.addOptionLabel(
				optionValue, LocaleUtil.fromLanguageId(languageId),
				jsonObject.getString(languageId));
		}
	}

	protected DDMFormFieldOptions deserializeDDMFormFieldOptions(
			String serializedDDMFormFieldProperty)
		throws PortalException {

		if (Validator.isNull(serializedDDMFormFieldProperty)) {
			return new DDMFormFieldOptions();
		}

		JSONArray jsonArray = _jsonFactory.createJSONArray(
			serializedDDMFormFieldProperty);

		return getDDMFormFieldOptions(jsonArray);
	}

	protected Object deserializeDDMFormFieldProperty(
			String serializedDDMFormFieldProperty,
			DDMFormField ddmFormFieldTypeSetting)
		throws PortalException {

		if (ddmFormFieldTypeSetting.isLocalizable()) {
			return deserializeLocalizedValue(serializedDDMFormFieldProperty);
		}

		String dataType = ddmFormFieldTypeSetting.getDataType();

		if (Objects.equals(dataType, "boolean")) {
			return Boolean.valueOf(serializedDDMFormFieldProperty);
		}
		else if (Objects.equals(dataType, "ddm-options")) {
			return deserializeDDMFormFieldOptions(
				serializedDDMFormFieldProperty);
		}
		else if (Objects.equals(
					 ddmFormFieldTypeSetting.getType(), "validation")) {

			return deserializeDDMFormFieldValidation(
				serializedDDMFormFieldProperty);
		}

		return serializedDDMFormFieldProperty;
	}

	protected DDMFormFieldValidation deserializeDDMFormFieldValidation(
			String serializedDDMFormFieldProperty)
		throws PortalException {

		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		if (Validator.isNull(serializedDDMFormFieldProperty)) {
			return ddmFormFieldValidation;
		}

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			serializedDDMFormFieldProperty);

		ddmFormFieldValidation.setExpression(
			jsonObject.getString("expression"));
		ddmFormFieldValidation.setErrorMessage(
			jsonObject.getString("errorMessage"));

		return ddmFormFieldValidation;
	}

	protected LocalizedValue deserializeLocalizedValue(
			String serializedDDMFormFieldProperty)
		throws PortalException {

		LocalizedValue localizedValue = new LocalizedValue();

		if (Validator.isNull(serializedDDMFormFieldProperty)) {
			return localizedValue;
		}

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			serializedDDMFormFieldProperty);

		Iterator<String> itr = jsonObject.keys();

		while (itr.hasNext()) {
			String languageId = itr.next();

			localizedValue.addString(
				LocaleUtil.fromLanguageId(languageId),
				jsonObject.getString(languageId));
		}

		return localizedValue;
	}

	protected DDMFormField getDDMFormField(JSONObject jsonObject)
		throws PortalException {

		String name = jsonObject.getString("name");
		String type = jsonObject.getString("type");

		DDMFormField ddmFormField = new DDMFormField(name, type);

		setDDMFormFieldProperties(jsonObject, ddmFormField);

		setNestedDDMFormField(
			jsonObject.getJSONArray("nestedFields"), ddmFormField);

		return ddmFormField;
	}

	protected DDMFormFieldOptions getDDMFormFieldOptions(JSONArray jsonArray) {
		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String value = jsonObject.getString("value");

			ddmFormFieldOptions.addOption(value);

			addOptionValueLabels(
				jsonObject.getJSONObject("label"), ddmFormFieldOptions, value);
		}

		return ddmFormFieldOptions;
	}

	protected List<DDMFormField> getDDMFormFields(JSONArray jsonArray)
		throws PortalException {

		List<DDMFormField> ddmFormFields = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			DDMFormField ddmFormField = getDDMFormField(
				jsonArray.getJSONObject(i));

			ddmFormFields.add(ddmFormField);
		}

		return ddmFormFields;
	}

	protected DDMForm getDDMFormFieldTypeSettingsDDMForm(String type) {
		DDMFormFieldType ddmFormFieldType =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldType(type);

		Class<? extends DDMFormFieldTypeSettings> ddmFormFieldTypeSettings =
			DefaultDDMFormFieldTypeSettings.class;

		if (ddmFormFieldType != null) {
			ddmFormFieldTypeSettings =
				ddmFormFieldType.getDDMFormFieldTypeSettings();
		}

		return DDMFormFactory.create(ddmFormFieldTypeSettings);
	}

	protected void setDDMFormFieldProperties(
			JSONObject jsonObject, DDMFormField ddmFormField)
		throws PortalException {

		DDMForm ddmFormFieldTypeSettingsDDMForm =
			getDDMFormFieldTypeSettingsDDMForm(ddmFormField.getType());

		for (DDMFormField ddmFormFieldTypeSetting :
				ddmFormFieldTypeSettingsDDMForm.getDDMFormFields()) {

			setDDMFormFieldProperty(
				jsonObject, ddmFormField, ddmFormFieldTypeSetting);
		}
	}

	protected void setDDMFormFieldProperty(
			JSONObject jsonObject, DDMFormField ddmFormField,
			DDMFormField ddmFormFieldTypeSetting)
		throws PortalException {

		String settingName = ddmFormFieldTypeSetting.getName();

		if (jsonObject.has(settingName)) {
			Object deserializedDDMFormFieldProperty =
				deserializeDDMFormFieldProperty(
					jsonObject.getString(settingName), ddmFormFieldTypeSetting);

			ddmFormField.setProperty(
				settingName, deserializedDDMFormFieldProperty);
		}
	}

	protected void setNestedDDMFormField(
			JSONArray jsonArray, DDMFormField ddmFormField)
		throws PortalException {

		if ((jsonArray == null) || (jsonArray.length() == 0)) {
			return;
		}

		List<DDMFormField> nestedDDMFormFields = getDDMFormFields(jsonArray);

		ddmFormField.setNestedDDMFormFields(nestedDDMFormFields);
	}

	private final DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;
	private final JSONFactory _jsonFactory;

}