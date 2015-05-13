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

package com.liferay.portlet.dynamicdatamapping.io;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldType;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypeProperty;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypePropertyJSONTransformer;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypeRegistryUtil;

import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * @author Marcellus Tavares
 */
public class DDMFormJSONSerializerImpl implements DDMFormJSONSerializer {

	@Override
	public String serialize(DDMForm ddmForm) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		addAvailableLanguageIds(jsonObject, ddmForm.getAvailableLocales());
		addDefaultLanguageId(jsonObject, ddmForm.getDefaultLocale());
		addFields(jsonObject, ddmForm.getDDMFormFields());

		return jsonObject.toString();
	}

	protected void addAvailableLanguageIds(
		JSONObject jsonObject, Set<Locale> availableLocales) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Locale availableLocale : availableLocales) {
			jsonArray.put(LocaleUtil.toLanguageId(availableLocale));
		}

		jsonObject.put("availableLanguageIds", jsonArray);
	}

	protected void addDefaultLanguageId(
		JSONObject jsonObject, Locale defaultLocale) {

		jsonObject.put(
			"defaultLanguageId", LocaleUtil.toLanguageId(defaultLocale));
	}

	protected void addFields(
		JSONObject jsonObject, List<DDMFormField> ddmFormFields) {

		jsonObject.put("fields", toJSONArray(ddmFormFields));
	}

	protected void addNestedFields(
		JSONObject jsonObject, List<DDMFormField> nestedDDMFormFields) {

		if (nestedDDMFormFields.isEmpty()) {
			return;
		}

		jsonObject.put("nestedFields", toJSONArray(nestedDDMFormFields));
	}

	protected void addOptionalProperties(
		JSONObject jsonObject, DDMFormField ddmFormField) {

		DDMFormFieldType ddmFormFieldType =
			DDMFormFieldTypeRegistryUtil.getDDMFormFieldType(
				ddmFormField.getType());

		for (DDMFormFieldTypeProperty ddmFormFieldTypeProperty :
				ddmFormFieldType.getOptionalProperties()) {

			addProperty(jsonObject, ddmFormField, ddmFormFieldTypeProperty);
		}
	}

	protected void addProperty(
		JSONObject jsonObject, DDMFormField ddmFormField,
		DDMFormFieldTypeProperty ddmFormFieldTypeProperty) {

		Object property = ddmFormField.getProperty(
			ddmFormFieldTypeProperty.getName());

		if (property == null) {
			return;
		}

		DDMFormFieldTypePropertyJSONTransformer
			ddmFormFieldTypePropertyJSONTransformer =
				ddmFormFieldTypeProperty.
					getDDMFormFieldTypePropertyJSONTransformer();

		addProperty(
			jsonObject, ddmFormFieldTypeProperty.getName(),
			ddmFormFieldTypePropertyJSONTransformer.toJSON(property));
	}

	protected void addProperty(
		JSONObject jsonObject, String propertyName, Object propertyValue) {

		if (propertyValue instanceof JSONArray) {
			jsonObject.put(propertyName, (JSONArray)propertyValue);
		}
		else if (propertyValue instanceof JSONObject) {
			jsonObject.put(propertyName, (JSONObject)propertyValue);
		}
		else {
			jsonObject.put(propertyName, propertyValue);
		}
	}

	protected void addRequiredProperties(
		JSONObject jsonObject, DDMFormField ddmFormField) {

		for (DDMFormFieldTypeProperty ddmFormFieldTypeProperty :
				DDMFormFieldType.REQUIRED_PROPERTIES) {

			addProperty(jsonObject, ddmFormField, ddmFormFieldTypeProperty);
		}
	}

	protected JSONArray toJSONArray(List<DDMFormField> ddmFormFields) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DDMFormField ddmFormField : ddmFormFields) {
			jsonArray.put(toJSONObject(ddmFormField));
		}

		return jsonArray;
	}

	protected JSONObject toJSONObject(DDMFormField ddmFormField) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		addOptionalProperties(jsonObject, ddmFormField);
		addRequiredProperties(jsonObject, ddmFormField);

		addNestedFields(jsonObject, ddmFormField.getNestedDDMFormFields());

		return jsonObject;
	}

}