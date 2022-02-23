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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DDMFormValuesToMapConverter.class)
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

		Stream<DDMFormFieldValue> ddmFormFieldValuesStream =
			ddmFormFieldValues.stream();

		ddmFormFieldValuesStream.filter(
			ddmFormFieldValue -> ddmFormFieldsMap.containsKey(
				ddmFormFieldValue.getName())
		).forEach(
			ddmFormFieldValue -> _addValues(
				ddmFormFieldsMap, ddmFormFieldValue, values)
		);

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
				_toLocalizedMap(ddmFormField.getType(), (LocalizedValue)value));
		}
		else {
			values.put("value", value.getString(value.getDefaultLocale()));
		}
	}

	private void _addValues(
		Map<String, DDMFormField> ddmFormFieldsMap,
		DDMFormFieldValue ddmFormFieldValue, Map<String, Object> values) {

		Map<String, Object> fieldInstanceValue =
			(Map<String, Object>)values.computeIfAbsent(
				_getDDMFormFieldValueInstanceKey(ddmFormFieldValue),
				k -> new LinkedHashMap<>());

		DDMFormField ddmFormField = ddmFormFieldsMap.get(
			ddmFormFieldValue.getName());

		if (ddmFormField == null) {
			return;
		}

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

	private Map<String, Object> _toLocalizedMap(
		String fieldType, LocalizedValue localizedValue) {

		Set<Locale> availableLocales = localizedValue.getAvailableLocales();

		Stream<Locale> stream = availableLocales.stream();

		if (fieldType.equals(DDMFormFieldTypeConstants.CHECKBOX_MULTIPLE) ||
			fieldType.equals(DDMFormFieldTypeConstants.SELECT)) {

			return stream.collect(
				Collectors.toMap(
					LanguageUtil::getLanguageId,
					locale -> _toStringList(locale, localizedValue)));
		}

		return stream.collect(
			Collectors.toMap(
				LanguageUtil::getLanguageId,
				locale -> GetterUtil.getString(
					localizedValue.getString(locale))));
	}

	private List<String> _toStringList(
		Locale locale, LocalizedValue localizedValue) {

		try {
			return JSONUtil.toStringList(
				JSONFactoryUtil.createJSONArray(
					localizedValue.getString(locale)));
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

}