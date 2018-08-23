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

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueJSONDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true, property = "ddm.form.values.deserializer.type=json"
)
public class DDMFormValuesJSONDeserializer
	implements DDMFormValuesDeserializer {

	@Override
	public DDMFormValuesDeserializerDeserializeResponse deserialize(
		DDMFormValuesDeserializerDeserializeRequest
			ddmFormValuesDeserializerDeserializeRequest) {

		DDMFormValues ddmFormValues = null;

		try {
			String content =
				ddmFormValuesDeserializerDeserializeRequest.getContent();

			DDMForm ddmForm =
				ddmFormValuesDeserializerDeserializeRequest.getDDMForm();

			JSONObject jsonObject = _jsonFactory.createJSONObject(content);

			ddmFormValues = new DDMFormValues(ddmForm);

			setDDMFormValuesAvailableLocales(
				jsonObject.getJSONArray("availableLanguageIds"), ddmFormValues);
			setDDMFormValuesDefaultLocale(
				jsonObject.getString("defaultLanguageId"), ddmFormValues);
			setDDMFormFieldValues(
				jsonObject.getJSONArray("fieldValues"), ddmForm, ddmFormValues);
			setDDMFormLocalizedValuesDefaultLocale(ddmFormValues);
		}
		catch (JSONException jsone) {
			if (_log.isWarnEnabled()) {
				_log.warn(jsone, jsone);
			}
		}

		DDMFormValuesDeserializerDeserializeResponse.Builder builder =
			DDMFormValuesDeserializerDeserializeResponse.Builder.newBuilder(
				ddmFormValues);

		return builder.build();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addDDMFormFieldValueJSONDeserializer(
		DDMFormFieldValueJSONDeserializer ddmFormFieldValueJSONDeserializer,
		Map<String, Object> properties) {

		String type = MapUtil.getString(properties, "ddm.form.field.type.name");

		if (Validator.isNull(type)) {
			return;
		}

		_ddmFormFieldValueJSONDeserializers.put(
			type, ddmFormFieldValueJSONDeserializer);
	}

	protected Set<Locale> getAvailableLocales(JSONArray jsonArray) {
		Set<Locale> availableLocales = new HashSet<>();

		if (jsonArray == null) {
			return availableLocales;
		}

		for (int i = 0; i < jsonArray.length(); i++) {
			Locale availableLocale = LocaleUtil.fromLanguageId(
				jsonArray.getString(i));

			availableLocales.add(availableLocale);
		}

		return availableLocales;
	}

	protected DDMFormFieldValue getDDMFormFieldValue(
		JSONObject jsonObject, Map<String, DDMFormField> ddmFormFieldsMap) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setInstanceId(jsonObject.getString("instanceId"));
		ddmFormFieldValue.setName(jsonObject.getString("name"));

		setDDMFormFieldValueValue(
			jsonObject, ddmFormFieldsMap.get(jsonObject.getString("name")),
			ddmFormFieldValue);

		setNestedDDMFormFieldValues(
			jsonObject.getJSONArray("nestedFieldValues"), ddmFormFieldsMap,
			ddmFormFieldValue);

		return ddmFormFieldValue;
	}

	protected DDMFormFieldValueJSONDeserializer
		getDDMFormFieldValueJSONDeserializer(DDMFormField ddmFormField) {

		if (ddmFormField == null) {
			return null;
		}

		return _ddmFormFieldValueJSONDeserializers.get(ddmFormField.getType());
	}

	protected List<DDMFormFieldValue> getDDMFormFieldValues(
		JSONArray jsonArray, Map<String, DDMFormField> ddmFormFieldsMap) {

		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			DDMFormFieldValue ddmFormFieldValue = getDDMFormFieldValue(
				jsonArray.getJSONObject(i), ddmFormFieldsMap);

			ddmFormFieldValues.add(ddmFormFieldValue);
		}

		return ddmFormFieldValues;
	}

	protected LocalizedValue getLocalizedValue(JSONObject jsonObject) {
		LocalizedValue localizedValue = new LocalizedValue();

		Iterator<String> itr = jsonObject.keys();

		while (itr.hasNext()) {
			String languageId = itr.next();

			localizedValue.addString(
				LocaleUtil.fromLanguageId(languageId),
				jsonObject.getString(languageId));
		}

		return localizedValue;
	}

	protected Value getValue(DDMFormField ddmFormField, JSONObject jsonObject) {
		DDMFormFieldValueJSONDeserializer ddmFormFieldValueJSONDeserializer =
			getDDMFormFieldValueJSONDeserializer(ddmFormField);

		if (ddmFormFieldValueJSONDeserializer != null) {
			return ddmFormFieldValueJSONDeserializer.deserialize(
				ddmFormField, String.valueOf(jsonObject.get("value")));
		}

		JSONObject valueJSONObject = jsonObject.getJSONObject("value");

		if (isLocalized(valueJSONObject)) {
			return getLocalizedValue(valueJSONObject);
		}
		else {
			return new UnlocalizedValue(jsonObject.getString("value"));
		}
	}

	protected boolean isLocalized(JSONObject jsonObject) {
		if (jsonObject == null) {
			return false;
		}

		Iterator<String> keys = jsonObject.keys();

		while (keys.hasNext()) {
			String key = keys.next();

			if (!LanguageUtil.isAvailableLocale(key)) {
				return false;
			}
		}

		return true;
	}

	protected void removeDDMFormFieldValueJSONDeserializer(
		DDMFormFieldValueJSONDeserializer ddmFormFieldValueJSONDeserializer,
		Map<String, Objects> properties) {

		String type = MapUtil.getString(properties, "ddm.form.field.type.name");

		_ddmFormFieldValueJSONDeserializers.remove(type);
	}

	protected void setDDMFormFieldValueLocalizedValueDefaultLocale(
		DDMFormFieldValue ddmFormFieldValue, Locale defaultLocale) {

		Value value = ddmFormFieldValue.getValue();

		if ((value != null) && value.isLocalized()) {
			value.setDefaultLocale(defaultLocale);
		}

		for (DDMFormFieldValue nestedDDMFormFieldValue :
				ddmFormFieldValue.getNestedDDMFormFieldValues()) {

			setDDMFormFieldValueLocalizedValueDefaultLocale(
				nestedDDMFormFieldValue, defaultLocale);
		}
	}

	protected void setDDMFormFieldValues(
		JSONArray jsonArray, DDMForm ddmForm, DDMFormValues ddmFormValues) {

		List<DDMFormFieldValue> ddmFormFieldValues = getDDMFormFieldValues(
			jsonArray, ddmForm.getDDMFormFieldsMap(true));

		ddmFormValues.setDDMFormFieldValues(ddmFormFieldValues);
	}

	protected void setDDMFormFieldValueValue(
		JSONObject jsonObject, DDMFormField ddmFormField,
		DDMFormFieldValue ddmFormFieldValue) {

		String valueString = jsonObject.getString("value", null);

		if (valueString == null) {
			return;
		}

		Value value = getValue(ddmFormField, jsonObject);

		ddmFormFieldValue.setValue(value);
	}

	protected void setDDMFormLocalizedValuesDefaultLocale(
		DDMFormValues ddmFormValues) {

		for (DDMFormFieldValue ddmFormFieldValue :
				ddmFormValues.getDDMFormFieldValues()) {

			setDDMFormFieldValueLocalizedValueDefaultLocale(
				ddmFormFieldValue, ddmFormValues.getDefaultLocale());
		}
	}

	protected void setDDMFormValuesAvailableLocales(
		JSONArray jsonArray, DDMFormValues ddmFormValues) {

		Set<Locale> availableLocales = getAvailableLocales(jsonArray);

		ddmFormValues.setAvailableLocales(availableLocales);
	}

	protected void setDDMFormValuesDefaultLocale(
		String defaultLanguageId, DDMFormValues ddmFormValues) {

		Locale defaultLocale = LocaleUtil.fromLanguageId(defaultLanguageId);

		ddmFormValues.setDefaultLocale(defaultLocale);

		Set<Locale> availableLocales = ddmFormValues.getAvailableLocales();

		if ((availableLocales != null) &&
			!availableLocales.contains(defaultLocale)) {

			availableLocales.add(defaultLocale);
		}
	}

	@Reference(unbind = "-")
	protected void setJSONFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	protected void setNestedDDMFormFieldValues(
		JSONArray jsonArray, Map<String, DDMFormField> ddmFormFieldsMap,
		DDMFormFieldValue ddmFormFieldValue) {

		if ((jsonArray == null) || (jsonArray.length() == 0)) {
			return;
		}

		List<DDMFormFieldValue> nestedDDMFormFieldValues =
			getDDMFormFieldValues(jsonArray, ddmFormFieldsMap);

		ddmFormFieldValue.setNestedDDMFormFields(nestedDDMFormFieldValues);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormValuesJSONDeserializer.class);

	private final Map<String, DDMFormFieldValueJSONDeserializer>
		_ddmFormFieldValueJSONDeserializers = new ConcurrentHashMap<>();
	private JSONFactory _jsonFactory;

}