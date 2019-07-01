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

package com.liferay.dynamic.data.mapping.form.web.internal.portlet.action.util;

import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormContextVisitor;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluator;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateRequest;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateResponse;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorFieldContextKey;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.web.internal.FormInstanceFieldSettingsException;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true, service = DDMFormInstanceFieldSettingsValidator.class
)
public class DDMFormInstanceFieldSettingsValidator {

	public void validate(PortletRequest portletRequest, DDMForm ddmForm)
		throws PortalException {

		Map<String, Set<String>> fieldNamePropertiesMap = evaluate(
			portletRequest, ddmForm);

		if (!fieldNamePropertiesMap.isEmpty()) {
			throw new FormInstanceFieldSettingsException.
				MustSetValidValueForProperties(fieldNamePropertiesMap);
		}
	}

	protected DDMFormValues createDDMFormFieldFormValues(
		JSONObject jsonObject, DDMForm fieldSettingsDDMForm,
		Set<Locale> availableLocales, Locale defaultLocale) {

		DDMFormValues fieldSettingsDDMFormValues = new DDMFormValues(
			fieldSettingsDDMForm);

		fieldSettingsDDMFormValues.setAvailableLocales(availableLocales);
		fieldSettingsDDMFormValues.setDefaultLocale(defaultLocale);

		DDMFormContextVisitor ddmFormContextVisitor = new DDMFormContextVisitor(
			jsonObject.getJSONArray("pages"));

		ddmFormContextVisitor.onVisitField(
			new Consumer<JSONObject>() {

				@Override
				public void accept(JSONObject jsonObject) {
					DDMFormFieldValue ddmFormFieldValue =
						new DDMFormFieldValue();

					ddmFormFieldValue.setDDMFormValues(
						fieldSettingsDDMFormValues);
					ddmFormFieldValue.setInstanceId(
						jsonObject.getString("instanceId"));
					ddmFormFieldValue.setName(
						jsonObject.getString("fieldName"));

					ddmFormFieldValue.setValue(getValue(jsonObject));

					fieldSettingsDDMFormValues.addDDMFormFieldValue(
						ddmFormFieldValue);
				}

				protected LocalizedValue getLocalizedValue(
					String serializedValue, Set<Locale> availableLocales,
					Locale defaultLocale) {

					LocalizedValue localizedValue = new LocalizedValue(
						defaultLocale);

					try {
						JSONObject jsonObject = _jsonFactory.createJSONObject(
							serializedValue);

						String defaultValueString = jsonObject.getString(
							LocaleUtil.toLanguageId(defaultLocale));

						for (Locale availableLocale : availableLocales) {
							String valueString = jsonObject.getString(
								LocaleUtil.toLanguageId(availableLocale),
								defaultValueString);

							localizedValue.addString(
								availableLocale, valueString);
						}
					}
					catch (Exception e) {
					}

					return localizedValue;
				}

				protected Value getValue(JSONObject jsonObject) {
					boolean localizable = jsonObject.getBoolean(
						"localizable", false);

					if (localizable) {
						return getLocalizedValue(
							jsonObject.getString("localizedValue"),
							availableLocales, defaultLocale);
					}

					return new UnlocalizedValue(jsonObject.getString("value"));
				}

			});

		ddmFormContextVisitor.visit();

		return fieldSettingsDDMFormValues;
	}

	protected DDMFormEvaluatorEvaluateResponse doEvaluate(
		PortletRequest portletRequest, DDMForm ddmForm,
		DDMFormValues ddmFormValues, Locale locale) {

		DDMFormEvaluatorEvaluateRequest.Builder builder =
			DDMFormEvaluatorEvaluateRequest.Builder.newBuilder(
				ddmForm, ddmFormValues, locale);

		builder.withCompanyId(
			_portal.getCompanyId(portletRequest)
		).withGroupId(
			ParamUtil.getLong(portletRequest, "groupId")
		).withUserId(
			_portal.getUserId(portletRequest)
		);

		return _ddmFormEvaluator.evaluate(builder.build());
	}

	protected Map<String, Set<String>> evaluate(
			PortletRequest portletRequest, DDMForm ddmForm)
		throws JSONException {

		Map<String, Set<String>> fieldNamePropertiesMap = new HashMap<>();

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		String serializedFormBuilderContext = ParamUtil.getString(
			portletRequest, "serializedFormBuilderContext");

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			serializedFormBuilderContext);

		DDMFormContextVisitor ddmFormContextVisitor = new DDMFormContextVisitor(
			jsonObject.getJSONArray("pages"));

		ddmFormContextVisitor.onVisitField(
			new Consumer<JSONObject>() {

				@Override
				public void accept(JSONObject jsonObject) {
					DDMFormField field = ddmFormFieldsMap.get(
						jsonObject.getString("fieldName"));

					DDMFormFieldType ddmFormFieldType =
						_ddmFormFieldTypeServicesTracker.getDDMFormFieldType(
							field.getType());

					DDMForm fieldDDMForm = DDMFormFactory.create(
						ddmFormFieldType.getDDMFormFieldTypeSettings());

					DDMFormValues fieldDDMFormValues =
						createDDMFormFieldFormValues(
							jsonObject.getJSONObject("settingsContext"),
							fieldDDMForm, ddmForm.getAvailableLocales(),
							ddmForm.getDefaultLocale());

					DDMFormEvaluatorEvaluateResponse
						ddmFormEvaluatorEvaluateResponse = doEvaluate(
							portletRequest, fieldDDMForm, fieldDDMFormValues,
							ddmForm.getDefaultLocale());

					Set<String> invalidDDMFormFields = getInvalidDDMFormFields(
						fieldDDMForm, ddmFormEvaluatorEvaluateResponse,
						fieldDDMForm.getDefaultLocale());

					if (!invalidDDMFormFields.isEmpty()) {
						fieldNamePropertiesMap.put(
							getFieldLabel(field, ddmForm.getDefaultLocale()),
							invalidDDMFormFields);
					}
				}

			});

		ddmFormContextVisitor.visit();

		return fieldNamePropertiesMap;
	}

	protected String getFieldLabel(DDMFormField ddmFormField, Locale locale) {
		LocalizedValue label = ddmFormField.getLabel();

		return label.getString(locale);
	}

	protected Set<String> getInvalidDDMFormFields(
		DDMForm fieldDDMForm,
		DDMFormEvaluatorEvaluateResponse ddmFormEvaluatorEvaluateResponse,
		Locale locale) {

		Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
			ddmFormFieldsPropertyChanges =
				ddmFormEvaluatorEvaluateResponse.
					getDDMFormFieldsPropertyChanges();

		if (MapUtil.isEmpty(ddmFormFieldsPropertyChanges)) {
			return Collections.emptySet();
		}

		Set<String> ddmFormFieldList = new HashSet<>();

		Map<String, DDMFormField> ddmFormFieldsMap =
			fieldDDMForm.getDDMFormFieldsMap(true);

		Set<Map.Entry<DDMFormEvaluatorFieldContextKey, Map<String, Object>>>
			entrySet = ddmFormFieldsPropertyChanges.entrySet();

		Stream<Map.Entry<DDMFormEvaluatorFieldContextKey, Map<String, Object>>>
			stream = entrySet.stream();

		stream.forEach(
			entry -> {
				if (!MapUtil.getBoolean(entry.getValue(), "valid", true)) {
					DDMFormEvaluatorFieldContextKey ddmFormFieldContextKey =
						entry.getKey();

					DDMFormField propertyFormField = ddmFormFieldsMap.get(
						ddmFormFieldContextKey.getName());

					ddmFormFieldList.add(
						getFieldLabel(propertyFormField, locale));
				}
			});

		return ddmFormFieldList;
	}

	@Reference
	private DDMFormEvaluator _ddmFormEvaluator;

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}