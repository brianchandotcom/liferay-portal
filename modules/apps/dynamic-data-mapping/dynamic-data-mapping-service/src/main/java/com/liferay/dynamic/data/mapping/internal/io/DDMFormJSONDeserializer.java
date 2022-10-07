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

package com.liferay.dynamic.data.mapping.internal.io;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.internal.io.util.DDMFormFieldDeserializerUtil;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormSuccessPageSettings;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	property = "ddm.form.deserializer.type=json",
	service = DDMFormDeserializer.class
)
public class DDMFormJSONDeserializer implements DDMFormDeserializer {

	public static DDMFormDeserializerDeserializeResponse internalDeserialize(
		DDMFormDeserializerDeserializeRequest
			ddmFormDeserializerDeserializeRequest) {

		DDMForm ddmForm = new DDMForm();

		DDMFormDeserializerDeserializeResponse.Builder builder =
			DDMFormDeserializerDeserializeResponse.Builder.newBuilder(ddmForm);

		try {
			JSONObject jsonObject = _jsonFactory.createJSONObject(
				ddmFormDeserializerDeserializeRequest.getContent());

			if (Validator.isNotNull(
					jsonObject.getString("definitionSchemaVersion"))) {

				ddmForm.setDefinitionSchemaVersion(
					jsonObject.getString("definitionSchemaVersion"));
			}

			setDDMFormAvailableLocales(
				jsonObject.getJSONArray("availableLanguageIds"), ddmForm);
			setDDMFormDefaultLocale(
				jsonObject.getString("defaultLanguageId"), ddmForm);
			setDDMFormFields(jsonObject.getJSONArray("fields"), ddmForm);

			setDDMFormLocalizedValuesDefaultLocale(ddmForm);
			setDDMFormRules(jsonObject.getJSONArray("rules"), ddmForm);
			setDDMFormSuccessPageSettings(
				jsonObject.getJSONObject("successPage"), ddmForm);

			return builder.build();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			builder = builder.exception(exception);
		}

		return builder.build();
	}

	@Override
	public DDMFormDeserializerDeserializeResponse deserialize(
		DDMFormDeserializerDeserializeRequest
			ddmFormDeserializerDeserializeRequest) {

		return internalDeserialize(ddmFormDeserializerDeserializeRequest);
	}

	protected static LocalizedValue deserializeLocalizedValue(
			String value, Locale defaultLocale)
		throws PortalException {

		LocalizedValue localizedValue = new LocalizedValue(defaultLocale);

		if (Validator.isNull(value)) {
			return localizedValue;
		}

		JSONObject jsonObject = _jsonFactory.createJSONObject(value);

		Iterator<String> iterator = jsonObject.keys();

		while (iterator.hasNext()) {
			String languageId = iterator.next();

			localizedValue.addString(
				LocaleUtil.fromLanguageId(languageId),
				jsonObject.getString(languageId));
		}

		return localizedValue;
	}

	protected static Set<Locale> getAvailableLocales(JSONArray jsonArray) {
		Set<Locale> availableLocales = new HashSet<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			Locale availableLocale = LocaleUtil.fromLanguageId(
				jsonArray.getString(i));

			availableLocales.add(availableLocale);
		}

		return availableLocales;
	}

	protected static void setDDMFormAvailableLocales(
		JSONArray jsonArray, DDMForm ddmForm) {

		ddmForm.setAvailableLocales(getAvailableLocales(jsonArray));
	}

	protected static void setDDMFormDefaultLocale(
		String defaultLanguageId, DDMForm ddmForm) {

		ddmForm.setDefaultLocale(LocaleUtil.fromLanguageId(defaultLanguageId));
	}

	protected static void setDDMFormFieldLocalizedValueDefaultLocale(
		LocalizedValue localizedValue, Locale defaultLocale) {

		if (localizedValue == null) {
			return;
		}

		localizedValue.setDefaultLocale(defaultLocale);
	}

	protected static void setDDMFormFieldLocalizedValuesDefaultLocale(
		DDMFormField ddmFormField, Locale defaultLocale) {

		setDDMFormFieldLocalizedValueDefaultLocale(
			ddmFormField.getLabel(), defaultLocale);

		setDDMFormFieldLocalizedValueDefaultLocale(
			ddmFormField.getPredefinedValue(), defaultLocale);

		setDDMFormFieldLocalizedValueDefaultLocale(
			ddmFormField.getStyle(), defaultLocale);

		setDDMFormFieldLocalizedValueDefaultLocale(
			ddmFormField.getTip(), defaultLocale);

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		if (ddmFormFieldOptions != null) {
			ddmFormFieldOptions.setDefaultLocale(defaultLocale);
		}

		for (DDMFormField nestedDDMFormField :
				ddmFormField.getNestedDDMFormFields()) {

			setDDMFormFieldLocalizedValuesDefaultLocale(
				nestedDDMFormField, defaultLocale);
		}
	}

	protected static void setDDMFormFields(JSONArray jsonArray, DDMForm ddmForm)
		throws PortalException {

		ddmForm.setDDMFormFields(
			DDMFormFieldDeserializerUtil.deserialize(
				_ddmFormFieldTypeServicesTracker, jsonArray, _jsonFactory));
	}

	protected static void setDDMFormLocalizedValuesDefaultLocale(
		DDMForm ddmForm) {

		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			setDDMFormFieldLocalizedValuesDefaultLocale(
				ddmFormField, ddmForm.getDefaultLocale());
		}
	}

	protected static void setDDMFormRules(
		JSONArray jsonArray, DDMForm ddmForm) {

		if ((jsonArray == null) || (jsonArray.length() == 0)) {
			return;
		}

		ddmForm.setDDMFormRules(
			DDMFormRuleJSONDeserializer.deserialize(jsonArray));
	}

	protected static void setDDMFormSuccessPageSettings(
			JSONObject jsonObject, DDMForm ddmForm)
		throws PortalException {

		if (jsonObject == null) {
			return;
		}

		Locale defaultLocale = ddmForm.getDefaultLocale();

		DDMFormSuccessPageSettings ddmFormSuccessPageSettings =
			new DDMFormSuccessPageSettings(
				deserializeLocalizedValue(
					jsonObject.getString("body"), defaultLocale),
				deserializeLocalizedValue(
					jsonObject.getString("title"), defaultLocale),
				jsonObject.getBoolean("enabled"));

		ddmForm.setDDMFormSuccessPageSettings(ddmFormSuccessPageSettings);
	}

	@Reference(unbind = "-")
	protected void setDDMFormFieldTypeServicesTracker(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker) {

		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
	}

	@Reference(unbind = "-")
	protected void setJSONFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormJSONDeserializer.class);

	private static DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;
	private static JSONFactory _jsonFactory;

}