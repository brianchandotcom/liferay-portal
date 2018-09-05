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

package com.liferay.dynamic.data.mapping.form.builder.internal.context;

import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormContextDeserializer;
import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormContextDeserializerRequest;
import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormContextVisitor;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.DDMFormRuleConverter;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.DDMFormRuleDeserializer;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.serializer.DDMFormRuleSerializerContext;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
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

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true,
	property = "dynamic.data.mapping.form.builder.context.deserializer.type=form",
	service = DDMFormContextDeserializer.class
)
public class DDMFormContextToDDMForm
	implements DDMFormContextDeserializer<DDMForm> {

	@Override
	public DDMForm deserialize(
			DDMFormContextDeserializerRequest ddmFormContextDeserializerRequest)
		throws PortalException {

		String serializedFormContext =
			ddmFormContextDeserializerRequest.getProperty(
				"serializedFormContext");

		if (Validator.isNull(serializedFormContext)) {
			throw new IllegalStateException(
				"The property \"serializedFormContext\" is required");
		}

		return deserialize(serializedFormContext);
	}

	protected LocalizedValue createLocalizedValue(
		JSONObject jsonObject, Locale defaultLocale) {

		LocalizedValue localizedValue = new LocalizedValue(defaultLocale);

		Iterator<String> keys = jsonObject.keys();

		while (keys.hasNext()) {
			String languageId = keys.next();

			localizedValue.addString(
				LocaleUtil.fromLanguageId(languageId),
				jsonObject.getString(languageId));
		}

		return localizedValue;
	}

	protected DDMForm deserialize(String serializedFormContext)
		throws PortalException {

		DDMForm ddmForm = new DDMForm();

		JSONObject jsonObject = jsonFactory.createJSONObject(
			serializedFormContext);

		setDDMFormAvailableLocales(
			jsonObject.getJSONArray("availableLanguageIds"), ddmForm);
		setDDMFormDefaultLocale(
			jsonObject.getString("defaultLanguageId"), ddmForm);
		setDDMFormFields(jsonObject.getJSONArray("pages"), ddmForm);
		setDDMFormRules(jsonObject.getJSONArray("rules"), ddmForm);
		setDDMFormSuccessPageSettings(
			jsonObject.getJSONObject("successPageSettings"), ddmForm);

		return ddmForm;
	}

	protected Set<Locale> getAvailableLocales(JSONArray jsonArray) {
		Set<Locale> availableLocales = new HashSet<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			availableLocales.add(
				LocaleUtil.fromLanguageId(jsonArray.getString(i)));
		}

		return availableLocales;
	}

	protected DDMFormFieldOptions getDDMFormFieldOptions(
		JSONObject jsonObject, Set<Locale> availableLocales,
		Locale defaultLocale) {

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		for (Locale availableLocale : availableLocales) {
			JSONArray jsonArray = jsonObject.getJSONArray(
				LocaleUtil.toLanguageId(availableLocale));

			if (jsonArray == null) {
				jsonArray = jsonObject.getJSONArray(defaultLanguageId);
			}

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject optionJSONObject = jsonArray.getJSONObject(i);

				ddmFormFieldOptions.addOptionLabel(
					optionJSONObject.getString("value"), availableLocale,
					optionJSONObject.getString("label"));
			}
		}

		ddmFormFieldOptions.setDefaultLocale(defaultLocale);

		return ddmFormFieldOptions;
	}

	protected DDMFormFieldOptions getDDMFormFieldOptions(
			String serializedValue, Set<Locale> availableLocales,
			Locale defaultLocale)
		throws PortalException {

		if (Validator.isNull(serializedValue)) {
			return new DDMFormFieldOptions();
		}

		JSONObject jsonObject = jsonFactory.createJSONObject(serializedValue);

		return getDDMFormFieldOptions(
			jsonObject, availableLocales, defaultLocale);
	}

	protected Object getDDMFormFieldPropertyValue(
			String serializedValue, boolean localizable, String dataType,
			String type, Set<Locale> availableLocales, Locale defaultLocale)
		throws PortalException {

		if (Objects.equals(dataType, "ddm-options")) {
			return getDDMFormFieldOptions(
				serializedValue, availableLocales, defaultLocale);
		}
		else if (Objects.equals(dataType, "boolean")) {
			return Boolean.valueOf(serializedValue);
		}
		else if (Objects.equals(type, "validation")) {
			return getDDMFormFieldValidation(serializedValue);
		}
		else if (localizable) {
			return getLocalizedValue(
				serializedValue, availableLocales, defaultLocale);
		}
		else if (Objects.equals(type, "select")) {
			return getDDMFormFieldSelectValue(serializedValue);
		}

		return serializedValue;
	}

	protected JSONArray getDDMFormFieldSelectValue(String serializedValue)
		throws PortalException {

		return jsonFactory.createJSONArray(serializedValue);
	}

	protected DDMFormFieldValidation getDDMFormFieldValidation(
			String serializedValue)
		throws PortalException {

		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		if (Validator.isNull(serializedValue)) {
			return ddmFormFieldValidation;
		}

		JSONObject jsonObject = jsonFactory.createJSONObject(serializedValue);

		ddmFormFieldValidation.setErrorMessage(
			jsonObject.getString("errorMessage"));
		ddmFormFieldValidation.setExpression(
			jsonObject.getString("expression"));

		return ddmFormFieldValidation;
	}

	protected List<com.liferay.dynamic.data.mapping.model.DDMFormRule>
			getDDMFormRules(
				DDMFormRuleSerializerContext ddmFormRuleSerializerContext,
				JSONArray jsonArray)
		throws PortalException {

		if ((jsonArray == null) || (jsonArray.length() == 0)) {
			return Collections.emptyList();
		}

		List<DDMFormRule> ddmFormRules = ddmFormRuleDeserializer.deserialize(
			jsonArray.toString());

		return ddmFormRuleConverter.convert(
			ddmFormRules, ddmFormRuleSerializerContext);
	}

	protected LocalizedValue getLocalizedValue(
			String serializedValue, Set<Locale> availableLocales,
			Locale defaultLocale)
		throws PortalException {

		LocalizedValue localizedValue = new LocalizedValue(defaultLocale);

		JSONObject jsonObject = jsonFactory.createJSONObject(serializedValue);

		String defaultValueString = jsonObject.getString(
			LocaleUtil.toLanguageId(defaultLocale));

		for (Locale availableLocale : availableLocales) {
			String valueString = jsonObject.getString(
				LocaleUtil.toLanguageId(availableLocale), defaultValueString);

			localizedValue.addString(availableLocale, valueString);
		}

		return localizedValue;
	}

	protected void setDDMFormAvailableLocales(
		JSONArray jsonArray, DDMForm ddmForm) {

		Set<Locale> availableLocales = getAvailableLocales(jsonArray);

		ddmForm.setAvailableLocales(availableLocales);
	}

	protected void setDDMFormDefaultLocale(
		String defaultLanguageId, DDMForm ddmForm) {

		Locale defaultLocale = LocaleUtil.fromLanguageId(defaultLanguageId);

		ddmForm.setDefaultLocale(defaultLocale);
	}

	protected void setDDMFormFields(JSONArray jsonArray, DDMForm ddmForm) {
		DDMFormContextVisitor ddmFormTemplateContextVisitor =
			new DDMFormContextVisitor(jsonArray);

		ddmFormTemplateContextVisitor.onVisitField(
			new Consumer<JSONObject>() {

				@Override
				public void accept(JSONObject jsonObject) {
					String name = jsonObject.getString("name");
					String type = jsonObject.getString("type");

					DDMFormField ddmFormField = new DDMFormField(name, type);

					setDDMFormFieldSettings(
						jsonObject.getJSONObject("settingsContext"), ddmForm,
						ddmFormField);

					ddmForm.addDDMFormField(ddmFormField);
				}

			});

		ddmFormTemplateContextVisitor.visit();
	}

	protected void setDDMFormFieldSettings(
		JSONObject jsonObject, DDMForm ddmForm, DDMFormField ddmFormField) {

		DDMFormContextVisitor ddmFormTemplateContextVisitor =
			new DDMFormContextVisitor(jsonObject.getJSONArray("pages"));

		ddmFormTemplateContextVisitor.onVisitField(
			new Consumer<JSONObject>() {

				@Override
				public void accept(JSONObject jsonObject) {
					boolean localizable = jsonObject.getBoolean(
						"localizable", false);

					String propertyName = jsonObject.getString("fieldName");

					String valueProperty = getValueProperty(localizable);

					String value = jsonObject.getString(valueProperty);

					String dataType = jsonObject.getString("dataType");
					String type = jsonObject.getString("type");

					try {
						Object propertyValue = getDDMFormFieldPropertyValue(
							value, localizable, dataType, type,
							ddmForm.getAvailableLocales(),
							ddmForm.getDefaultLocale());

						ddmFormField.setProperty(propertyName, propertyValue);
					}
					catch (PortalException pe) {
						_log.error(
							String.format(
								"Unable to set the property \"%s\" of the " +
									"field \"%s\"",
								propertyName, ddmFormField.getName()),
							pe);
					}
				}

				protected String getValueProperty(boolean localizable) {
					if (localizable) {
						return "localizedValue";
					}

					return "value";
				}

			});

		ddmFormTemplateContextVisitor.visit();
	}

	protected void setDDMFormRules(JSONArray jsonArray, DDMForm ddmForm)
		throws PortalException {

		DDMFormRuleSerializerContext ddmFormRuleSerializerContext =
			new DDMFormRuleSerializerContext();

		ddmFormRuleSerializerContext.addAttribute("form", ddmForm);

		List<com.liferay.dynamic.data.mapping.model.DDMFormRule> ddmFormRules =
			getDDMFormRules(ddmFormRuleSerializerContext, jsonArray);

		ddmForm.setDDMFormRules(ddmFormRules);
	}

	protected void setDDMFormSuccessPageSettings(
		JSONObject jsonObject, DDMForm ddmForm) {

		if (jsonObject == null) {
			return;
		}

		DDMFormSuccessPageSettings ddmFormSuccessPageSettings =
			new DDMFormSuccessPageSettings();

		Locale defaultLocale = ddmForm.getDefaultLocale();

		ddmFormSuccessPageSettings.setBody(
			createLocalizedValue(
				jsonObject.getJSONObject("body"), defaultLocale));

		ddmFormSuccessPageSettings.setTitle(
			createLocalizedValue(
				jsonObject.getJSONObject("title"), defaultLocale));

		ddmFormSuccessPageSettings.setEnabled(jsonObject.getBoolean("enabled"));

		ddmForm.setDDMFormSuccessPageSettings(ddmFormSuccessPageSettings);
	}

	@Reference
	protected DDMFormRuleConverter ddmFormRuleConverter;

	@Reference
	protected DDMFormRuleDeserializer ddmFormRuleDeserializer;

	@Reference
	protected JSONFactory jsonFactory;

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormContextToDDMForm.class);

}