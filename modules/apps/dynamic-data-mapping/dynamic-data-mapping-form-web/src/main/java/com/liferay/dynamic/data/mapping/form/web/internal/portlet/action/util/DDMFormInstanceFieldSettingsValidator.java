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
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

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
					ddmFormFieldValue.setFieldReference(
						jsonObject.getString("fieldReference"));
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
					catch (Exception exception) {
						if (_log.isDebugEnabled()) {
							_log.debug(exception, exception);
						}
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

	protected Map<String, Set<String>> evaluate(
			PortletRequest portletRequest, DDMForm ddmForm)
		throws JSONException {

		Map<String, Set<String>> fieldNamePropertiesMap = new HashMap<>();

		String serializedFormBuilderContext = ParamUtil.getString(
			portletRequest, "serializedFormBuilderContext");

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			serializedFormBuilderContext);

		DDMFormContextVisitor ddmFormContextVisitor = new DDMFormContextVisitor(
			jsonObject.getJSONArray("pages"));

		ddmFormContextVisitor.onVisitField(
			new FieldJSONObjectConsumer(
				ddmForm, fieldNamePropertiesMap, portletRequest));

		ddmFormContextVisitor.visit();

		return fieldNamePropertiesMap;
	}

	protected DDMFormEvaluatorEvaluateResponse evaluate(
		PortletRequest portletRequest, DDMForm ddmForm,
		DDMFormValues ddmFormValues, Locale locale) {

		DDMFormEvaluatorEvaluateRequest.Builder builder =
			DDMFormEvaluatorEvaluateRequest.Builder.newBuilder(
				ddmForm, ddmFormValues, locale);

		builder.withCompanyId(
			_portal.getCompanyId(portletRequest)
		).withDDMFormInstanceId(
			ParamUtil.getLong(portletRequest, "formInstanceId")
		).withGroupId(
			ParamUtil.getLong(portletRequest, "groupId")
		).withUserId(
			_portal.getUserId(portletRequest)
		);

		return _ddmFormEvaluator.evaluate(builder.build());
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

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceFieldSettingsValidator.class);

	@Reference
	private DDMFormEvaluator _ddmFormEvaluator;

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

	private class FieldJSONObjectConsumer implements Consumer<JSONObject> {

		public FieldJSONObjectConsumer(
			DDMForm ddmForm, Map<String, Set<String>> fieldNamePropertiesMap,
			PortletRequest portletRequest) {

			_ddmForm = ddmForm;
			_fieldNamePropertiesMap = fieldNamePropertiesMap;
			_portletRequest = portletRequest;
		}

		@Override
		public void accept(JSONObject jsonObject) {
			_evaluateDDMFormField(jsonObject);
		}

		private void _evaluateDDMFormField(JSONObject jsonObject) {
			if (jsonObject.has("nestedFields")) {
				JSONArray nestedFieldsJSONArray = jsonObject.getJSONArray(
					"nestedFields");

				for (int i = 0; i < nestedFieldsJSONArray.length(); i++) {
					_evaluateDDMFormField(
						nestedFieldsJSONArray.getJSONObject(i));
				}
			}

			Map<String, DDMFormField> ddmFormFieldsMap =
				_ddmForm.getDDMFormFieldsMap(true);

			DDMFormField ddmFormField = ddmFormFieldsMap.get(
				jsonObject.getString("fieldName"));

			if (ddmFormField == null) {
				return;
			}

			DDMFormFieldType ddmFormFieldType =
				_ddmFormFieldTypeServicesTracker.getDDMFormFieldType(
					ddmFormField.getType());

			DDMForm ddmFormFieldTypeSettingsDDMForm = DDMFormFactory.create(
				ddmFormFieldType.getDDMFormFieldTypeSettings());

			if (StringUtil.equals(ddmFormField.getDataType(), "integer") &&
				GetterUtil.getBoolean(ddmFormField.getProperty("inputMask"))) {

				Map<String, DDMFormField>
					ddmFormFieldTypeSettingsDDMFormFieldsMap =
						ddmFormFieldTypeSettingsDDMForm.getDDMFormFieldsMap(
							false);

				DDMFormField predefinedValueDDMFormField =
					ddmFormFieldTypeSettingsDDMFormFieldsMap.get(
						"predefinedValue");

				predefinedValueDDMFormField.setDataType("integer");
				predefinedValueDDMFormField.setProperty("inputMask", true);
				predefinedValueDDMFormField.setProperty(
					"inputMaskFormat",
					ddmFormField.getProperty("inputMaskFormat"));
			}

			DDMFormValues ddmFormFieldTypeSettingsDDMFormValues =
				createDDMFormFieldFormValues(
					jsonObject.getJSONObject("settingsContext"),
					ddmFormFieldTypeSettingsDDMForm,
					_ddmForm.getAvailableLocales(),
					_ddmForm.getDefaultLocale());

			for (Locale availableLocale : _ddmForm.getAvailableLocales()) {
				DDMFormEvaluatorEvaluateResponse
					ddmFormEvaluatorEvaluateResponse = evaluate(
						_portletRequest, ddmFormFieldTypeSettingsDDMForm,
						ddmFormFieldTypeSettingsDDMFormValues, availableLocale);

				Set<String> invalidDDMFormFields = getInvalidDDMFormFields(
					ddmFormFieldTypeSettingsDDMForm,
					ddmFormEvaluatorEvaluateResponse,
					ddmFormFieldTypeSettingsDDMForm.getDefaultLocale());

				if (!invalidDDMFormFields.isEmpty()) {
					_fieldNamePropertiesMap.put(
						getFieldLabel(ddmFormField, availableLocale),
						invalidDDMFormFields);

					break;
				}
			}
		}

		private final DDMForm _ddmForm;
		private final Map<String, Set<String>> _fieldNamePropertiesMap;
		private final PortletRequest _portletRequest;

	}

}