/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.io;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesRegistry;
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

	@Override
	public DDMFormDeserializerDeserializeResponse deserialize(
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

			_setDDMFormAvailableLocales(
				jsonObject.getJSONArray("availableLanguageIds"), ddmForm);
			_setDDMFormDefaultLocale(
				jsonObject.getString("defaultLanguageId"), ddmForm);
			_setDDMFormFields(jsonObject.getJSONArray("fields"), ddmForm);

			_setDDMFormLocalizedValuesDefaultLocale(ddmForm);
			_setDDMFormRules(jsonObject.getJSONArray("rules"), ddmForm);
			_setDDMFormSuccessPageSettings(
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

	private LocalizedValue _deserializeLocalizedValue(
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

	private void _setDDMFormAvailableLocales(
		JSONArray jsonArray, DDMForm ddmForm) {

		Set<Locale> availableLocales = new HashSet<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			Locale availableLocale = LocaleUtil.fromLanguageId(
				jsonArray.getString(i));

			availableLocales.add(availableLocale);
		}

		ddmForm.setAvailableLocales(availableLocales);
	}

	private void _setDDMFormDefaultLocale(
		String defaultLanguageId, DDMForm ddmForm) {

		ddmForm.setDefaultLocale(LocaleUtil.fromLanguageId(defaultLanguageId));
	}

	private void _setDDMFormFieldLocalizedValueDefaultLocale(
		LocalizedValue localizedValue, Locale defaultLocale) {

		if (localizedValue == null) {
			return;
		}

		localizedValue.setDefaultLocale(defaultLocale);
	}

	private void _setDDMFormFieldLocalizedValuesDefaultLocale(
		DDMFormField ddmFormField, Locale defaultLocale) {

		_setDDMFormFieldLocalizedValueDefaultLocale(
			ddmFormField.getLabel(), defaultLocale);

		_setDDMFormFieldLocalizedValueDefaultLocale(
			ddmFormField.getPredefinedValue(), defaultLocale);

		_setDDMFormFieldLocalizedValueDefaultLocale(
			ddmFormField.getStyle(), defaultLocale);

		_setDDMFormFieldLocalizedValueDefaultLocale(
			ddmFormField.getTip(), defaultLocale);

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		if (ddmFormFieldOptions != null) {
			ddmFormFieldOptions.setDefaultLocale(defaultLocale);
		}

		for (DDMFormField nestedDDMFormField :
				ddmFormField.getNestedDDMFormFields()) {

			_setDDMFormFieldLocalizedValuesDefaultLocale(
				nestedDDMFormField, defaultLocale);
		}
	}

	private void _setDDMFormFields(JSONArray jsonArray, DDMForm ddmForm)
		throws PortalException {

		ddmForm.setDDMFormFields(
			DDMFormFieldDeserializerUtil.deserialize(
				_ddmFormFieldTypeServicesRegistry, jsonArray, _jsonFactory));
	}

	private void _setDDMFormLocalizedValuesDefaultLocale(DDMForm ddmForm) {
		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			_setDDMFormFieldLocalizedValuesDefaultLocale(
				ddmFormField, ddmForm.getDefaultLocale());
		}
	}

	private void _setDDMFormRules(JSONArray jsonArray, DDMForm ddmForm) {
		if ((jsonArray == null) || (jsonArray.length() == 0)) {
			return;
		}

		ddmForm.setDDMFormRules(
			DDMFormRuleJSONDeserializer.deserialize(jsonArray));
	}

	private void _setDDMFormSuccessPageSettings(
			JSONObject jsonObject, DDMForm ddmForm)
		throws PortalException {

		if (jsonObject == null) {
			return;
		}

		Locale defaultLocale = ddmForm.getDefaultLocale();

		DDMFormSuccessPageSettings ddmFormSuccessPageSettings =
			new DDMFormSuccessPageSettings(
				_deserializeLocalizedValue(
					jsonObject.getString("body"), defaultLocale),
				_deserializeLocalizedValue(
					jsonObject.getString("title"), defaultLocale),
				jsonObject.getBoolean("enabled"));

		ddmForm.setDDMFormSuccessPageSettings(ddmFormSuccessPageSettings);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormJSONDeserializer.class);

	@Reference
	private DDMFormFieldTypeServicesRegistry _ddmFormFieldTypeServicesRegistry;

	@Reference
	private JSONFactory _jsonFactory;

}