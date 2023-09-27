/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.util;

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesConverterUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToMapConverter;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(service = DDMFormValuesToMapConverter.class)
public class DDMFormValuesToMapConverterImpl
	implements DDMFormValuesToMapConverter {

	@Override
	public Map<String, Object> convert(
			DDMFormValues ddmFormValues, DDMStructure ddmStructure)
		throws PortalException {

		if (ddmFormValues == null) {
			return Collections.emptyMap();
		}

		DDMForm ddmForm = ddmStructure.getDDMForm();

		List<DDMFormFieldValue> ddmFormFieldValues =
			DDMFormValuesConverterUtil.addMissingDDMFormFieldValues(
				ddmForm.getDDMFormFields(),
				ddmFormValues.getDDMFormFieldValuesMap(true));

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmStructure.getFullHierarchyDDMFormFieldsMap(true);

		Map<String, Object> values = new LinkedHashMap<>(
			ddmFormFieldsMap.size());

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			if (!ddmFormFieldsMap.containsKey(ddmFormFieldValue.getName())) {
				continue;
			}

			_addValues(ddmFormFieldsMap, ddmFormFieldValue, values);
		}

		return values;
	}

	private void _addValue(
		DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue,
		Map<String, Object> values) {

		if (ddmFormField == null) {
			return;
		}

		Value value = ddmFormFieldValue.getValue();

		if (value == null) {
			return;
		}

		if (ddmFormField.isLocalizable()) {
			values.put(
				"value",
				_toLocalizedMap(
					ddmFormField.getType(), _getLocalizedValue(value)));
		}
		else {
			values.put("value", value.getString(value.getDefaultLocale()));
		}
	}

	private void _addValues(
		Map<String, DDMFormField> ddmFormFieldsMap,
		DDMFormFieldValue ddmFormFieldValue, Map<String, Object> values) {

		DDMFormField ddmFormField = ddmFormFieldsMap.get(
			ddmFormFieldValue.getName());

		if (ddmFormField == null) {
			return;
		}

		Map<String, Object> fieldInstanceValue =
			(Map<String, Object>)values.computeIfAbsent(
				_getDDMFormFieldValueInstanceKey(ddmFormFieldValue),
				k -> new LinkedHashMap<>());

		if (!Objects.equals(ddmFormField.getType(), "fieldset")) {
			_addValue(ddmFormField, ddmFormFieldValue, fieldInstanceValue);
		}

		if (ListUtil.isNotEmpty(
				ddmFormFieldValue.getNestedDDMFormFieldValues())) {

			Map<String, Object> nestedFieldInstanceValues =
				(Map<String, Object>)fieldInstanceValue.computeIfAbsent(
					"nestedValues", k -> new LinkedHashMap<>());

			for (DDMFormFieldValue nestedDDMFormFieldValue :
					ddmFormFieldValue.getNestedDDMFormFieldValues()) {

				_addValues(
					ddmFormFieldsMap, nestedDDMFormFieldValue,
					nestedFieldInstanceValues);
			}
		}
	}

	private String _getDDMFormFieldValueInstanceKey(
		DDMFormFieldValue ddmFormFieldValue) {

		return StringBundler.concat(
			ddmFormFieldValue.getName(), "_INSTANCE_",
			ddmFormFieldValue.getInstanceId());
	}

	private LocalizedValue _getLocalizedValue(Value value) {
		if (value == null) {
			return null;
		}

		if (value.isLocalized()) {
			return (LocalizedValue)value;
		}

		LocalizedValue localizedValue = new LocalizedValue(
			value.getDefaultLocale());

		Map<Locale, String> values = localizedValue.getValues();

		values.putAll(value.getValues());

		return localizedValue;
	}

	private Map<String, Object> _toLocalizedMap(
		String fieldType, LocalizedValue localizedValue) {

		Map<String, Object> localizedMap = new HashMap<>();

		Function<Locale, Object> function = locale -> GetterUtil.getString(
			localizedValue.getString(locale));

		if (fieldType.equals(DDMFormFieldTypeConstants.CHECKBOX_MULTIPLE) ||
			fieldType.equals(DDMFormFieldTypeConstants.SELECT)) {

			function = locale -> _toStringList(locale, localizedValue);
		}

		for (Locale locale : localizedValue.getAvailableLocales()) {
			localizedMap.put(
				_language.getLanguageId(locale), function.apply(locale));
		}

		return localizedMap;
	}

	private List<String> _toStringList(
		Locale locale, LocalizedValue localizedValue) {

		try {
			return JSONUtil.toStringList(
				_jsonFactory.createJSONArray(localizedValue.getString(locale)));
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			return Collections.emptyList();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormValuesToMapConverterImpl.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

}