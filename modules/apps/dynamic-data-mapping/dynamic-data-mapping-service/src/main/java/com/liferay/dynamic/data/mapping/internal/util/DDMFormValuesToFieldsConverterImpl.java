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

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.storage.constants.FieldConstants;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesConverterUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToFieldsConverter;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(service = DDMFormValuesToFieldsConverter.class)
public class DDMFormValuesToFieldsConverterImpl
	implements DDMFormValuesToFieldsConverter {

	@Override
	public Fields convert(
			DDMStructure ddmStructure, DDMFormValues ddmFormValues)
		throws PortalException {

		DDMForm ddmForm = ddmStructure.getDDMForm();

		ddmFormValues.setDDMFormFieldValues(
			DDMFormValuesConverterUtil.addMissingDDMFormFieldValues(
				ddmForm.getDDMFormFields(),
				ddmFormValues.getDDMFormFieldValuesMap(true)));

		Map<String, Set<Locale>> ddmFormFieldAvailableLocales =
			_getDDMFormFieldAvailableLocales(
				ddmFormValues.getDDMFormFieldValuesMap(true));

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmStructure.getFullHierarchyDDMFormFieldsMap(true);

		Fields fields = _createFields(ddmStructure);

		for (DDMFormFieldValue ddmFormFieldValue :
				ddmFormValues.getDDMFormFieldValues()) {

			_addFields(
				ddmFormFieldAvailableLocales, ddmStructure.getStructureId(),
				ddmFormFieldsMap, ddmFormFieldValue, fields,
				ddmFormValues.getDefaultLocale());
		}

		return fields;
	}

	private void _addField(
			Map<String, Set<Locale>> ddmFormFieldAvailableLocales,
			long ddmStructureId, DDMFormField ddmFormField,
			DDMFormFieldValue ddmFormFieldValue, Fields fields,
			Locale defaultLocale)
		throws PortalException {

		if ((ddmFormField == null) || ddmFormField.isTransient() ||
			(ddmFormFieldValue.getValue() == null)) {

			return;
		}

		Field field = _createField(
			ddmFormFieldAvailableLocales, ddmStructureId, ddmFormField,
			ddmFormFieldValue, defaultLocale);

		Field existingField = fields.get(field.getName());

		if (existingField == null) {
			fields.put(field);
		}
		else {
			_addFieldValues(existingField, field);
		}
	}

	private void _addFieldDisplayValue(
		Field fieldsDisplayField, String fieldsDisplayValue) {

		String[] fieldsDisplayValues = StringUtil.split(
			(String)fieldsDisplayField.getValue());

		fieldsDisplayValues = ArrayUtil.append(
			fieldsDisplayValues, fieldsDisplayValue);

		fieldsDisplayField.setValue(StringUtil.merge(fieldsDisplayValues));
	}

	private void _addFields(
			Map<String, Set<Locale>> ddmFormFieldAvailableLocales,
			long ddmStructureId, Map<String, DDMFormField> ddmFormFieldsMap,
			DDMFormFieldValue ddmFormFieldValue, Fields fields,
			Locale defaultLocale)
		throws PortalException {

		DDMFormField ddmFormField = ddmFormFieldsMap.get(
			ddmFormFieldValue.getName());

		_addField(
			ddmFormFieldAvailableLocales, ddmStructureId, ddmFormField,
			ddmFormFieldValue, fields, defaultLocale);

		_addFieldDisplayValue(
			fields.get(DDMImpl.FIELDS_DISPLAY_NAME),
			_getFieldDisplayValue(ddmFormFieldValue));

		for (DDMFormFieldValue nestedDDMFormFieldValue :
				ddmFormFieldValue.getNestedDDMFormFieldValues()) {

			_addFields(
				ddmFormFieldAvailableLocales, ddmStructureId, ddmFormFieldsMap,
				nestedDDMFormFieldValue, fields, defaultLocale);
		}
	}

	private void _addFieldValues(Field existingField, Field newField) {
		for (Locale availableLocale : newField.getAvailableLocales()) {
			existingField.addValues(
				availableLocale, newField.getValues(availableLocale));
		}
	}

	private Field _createField(
			Map<String, Set<Locale>> ddmFormFieldAvailableLocales,
			long ddmStructureId, DDMFormField ddmFormField,
			DDMFormFieldValue ddmFormFieldValue, Locale defaultLocale)
		throws PortalException {

		Field field = new Field();

		field.setDDMStructureId(ddmStructureId);
		field.setDefaultLocale(defaultLocale);
		field.setName(ddmFormFieldValue.getName());

		String type = ddmFormField.getDataType();

		_setFieldValue(
			field, ddmFormFieldAvailableLocales, type,
			ddmFormFieldValue.getValue(), defaultLocale);

		return field;
	}

	private Fields _createFields(DDMStructure ddmStructure) {
		Fields fields = new Fields();

		Field fieldsDisplayField = new Field(
			ddmStructure.getStructureId(), DDMImpl.FIELDS_DISPLAY_NAME,
			StringPool.BLANK);

		fields.put(fieldsDisplayField);

		return fields;
	}

	private Map<String, Set<Locale>> _getDDMFormFieldAvailableLocales(
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap) {

		Map<String, Set<Locale>> ddmFormFieldAvailableLocales = new HashMap<>();

		for (Map.Entry<String, List<DDMFormFieldValue>> entry :
				ddmFormFieldValuesMap.entrySet()) {

			if (ListUtil.isEmpty(entry.getValue())) {
				continue;
			}

			Set<Locale> availableLocales = new HashSet<>();

			for (DDMFormFieldValue ddmFormFieldValue : entry.getValue()) {
				Value value = ddmFormFieldValue.getValue();

				if (value == null) {
					continue;
				}

				availableLocales.addAll(value.getAvailableLocales());
			}

			ddmFormFieldAvailableLocales.put(entry.getKey(), availableLocales);
		}

		return ddmFormFieldAvailableLocales;
	}

	private String _getFieldDisplayValue(DDMFormFieldValue ddmFormFieldValue) {
		String fieldName = ddmFormFieldValue.getName();

		return StringBundler.concat(
			fieldName, DDMImpl.INSTANCE_SEPARATOR,
			ddmFormFieldValue.getInstanceId());
	}

	private void _setFieldLocalizedValue(
		Field field, Map<String, Set<Locale>> ddmFormFieldAvailableLocales,
		String type, Value value) {

		for (Locale availableLocale :
				ddmFormFieldAvailableLocales.get(field.getName())) {

			Serializable serializable = FieldConstants.getSerializable(
				availableLocale, availableLocale, type,
				value.getString(availableLocale));

			field.addValue(availableLocale, serializable);
		}
	}

	private void _setFieldUnlocalizedValue(
		Field field, String type, Value value, Locale defaultLocale) {

		Serializable serializable = FieldConstants.getSerializable(
			defaultLocale, LocaleUtil.ROOT, type,
			value.getString(LocaleUtil.ROOT));

		field.addValue(defaultLocale, serializable);
	}

	private void _setFieldValue(
		Field field, Map<String, Set<Locale>> ddmFormFieldAvailableLocales,
		String type, Value value, Locale defaultLocale) {

		if (value.isLocalized()) {
			_setFieldLocalizedValue(
				field, ddmFormFieldAvailableLocales, type, value);
		}
		else {
			_setFieldUnlocalizedValue(field, type, value, defaultLocale);
		}
	}

}