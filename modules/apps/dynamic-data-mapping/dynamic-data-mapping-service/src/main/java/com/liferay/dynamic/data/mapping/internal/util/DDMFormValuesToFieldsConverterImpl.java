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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = DDMFormValuesToFieldsConverter.class)
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

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmStructure.getFullHierarchyDDMFormFieldsMap(true);

		Fields ddmFields = _createDDMFields(ddmStructure);

		for (DDMFormFieldValue ddmFormFieldValue :
				ddmFormValues.getDDMFormFieldValues()) {

			_addDDMFields(
				ddmStructure.getStructureId(), ddmFormFieldsMap,
				ddmFormFieldValue, ddmFields, ddmFormValues.getDefaultLocale());
		}

		return ddmFields;
	}

	private void _addDDMField(
			long ddmStructureId, DDMFormField ddmFormField,
			DDMFormFieldValue ddmFormFieldValue, Fields ddmFields,
			Locale defaultLocale)
		throws PortalException {

		if ((ddmFormField == null) || ddmFormField.isTransient() ||
			(ddmFormFieldValue.getValue() == null)) {

			return;
		}

		Field ddmField = _createDDMField(
			ddmStructureId, ddmFormField, ddmFormFieldValue, defaultLocale);

		Field existingDDMField = ddmFields.get(ddmField.getName());

		if (existingDDMField == null) {
			ddmFields.put(ddmField);
		}
		else {
			_addDDMFieldValues(existingDDMField, ddmField);
		}
	}

	private void _addDDMFields(
			long ddmStructureId, Map<String, DDMFormField> ddmFormFieldsMap,
			DDMFormFieldValue ddmFormFieldValue, Fields ddmFields,
			Locale defaultLocale)
		throws PortalException {

		DDMFormField ddmFormField = ddmFormFieldsMap.get(
			ddmFormFieldValue.getName());

		_addDDMField(
			ddmStructureId, ddmFormField, ddmFormFieldValue, ddmFields,
			defaultLocale);

		_addFieldDisplayValue(
			ddmFields.get(DDMImpl.FIELDS_DISPLAY_NAME),
			_getFieldDisplayValue(ddmFormFieldValue));

		for (DDMFormFieldValue nestedDDMFormFieldValue :
				ddmFormFieldValue.getNestedDDMFormFieldValues()) {

			_addDDMFields(
				ddmStructureId, ddmFormFieldsMap, nestedDDMFormFieldValue,
				ddmFields, defaultLocale);
		}
	}

	private void _addDDMFieldValues(Field existingDDMField, Field newDDMField) {
		for (Locale availableLocale : newDDMField.getAvailableLocales()) {
			existingDDMField.addValues(
				availableLocale, newDDMField.getValues(availableLocale));
		}
	}

	private void _addFieldDisplayValue(
		Field ddmFieldsDisplayField, String fieldDisplayValue) {

		String[] fieldsDisplayValues = StringUtil.split(
			(String)ddmFieldsDisplayField.getValue());

		fieldsDisplayValues = ArrayUtil.append(
			fieldsDisplayValues, fieldDisplayValue);

		ddmFieldsDisplayField.setValue(StringUtil.merge(fieldsDisplayValues));
	}

	private Field _createDDMField(
			long ddmStructureId, DDMFormField ddmFormField,
			DDMFormFieldValue ddmFormFieldValue, Locale defaultLocale)
		throws PortalException {

		Field ddmField = new Field();

		ddmField.setDDMStructureId(ddmStructureId);
		ddmField.setDefaultLocale(defaultLocale);
		ddmField.setName(ddmFormFieldValue.getName());

		String type = ddmFormField.getDataType();

		_setDDMFieldValue(
			ddmField, type, ddmFormFieldValue.getValue(), defaultLocale);

		return ddmField;
	}

	private Fields _createDDMFields(DDMStructure ddmStructure) {
		Fields ddmFields = new Fields();

		Field fieldsDisplayField = new Field(
			ddmStructure.getStructureId(), DDMImpl.FIELDS_DISPLAY_NAME,
			StringPool.BLANK);

		ddmFields.put(fieldsDisplayField);

		return ddmFields;
	}

	private String _getFieldDisplayValue(DDMFormFieldValue ddmFormFieldValue) {
		String fieldName = ddmFormFieldValue.getName();

		return StringBundler.concat(
			fieldName, DDMImpl.INSTANCE_SEPARATOR,
			ddmFormFieldValue.getInstanceId());
	}

	private void _setDDMFieldLocalizedValue(
		Field ddmField, String type, Value value) {

		for (Locale availableLocale : value.getAvailableLocales()) {
			Serializable serializable = FieldConstants.getSerializable(
				availableLocale, availableLocale, type,
				value.getString(availableLocale));

			ddmField.addValue(availableLocale, serializable);
		}
	}

	private void _setDDMFieldUnlocalizedValue(
		Field ddmField, String type, Value value, Locale defaultLocale) {

		Serializable serializable = FieldConstants.getSerializable(
			defaultLocale, LocaleUtil.ROOT, type,
			value.getString(LocaleUtil.ROOT));

		ddmField.addValue(defaultLocale, serializable);
	}

	private void _setDDMFieldValue(
		Field ddmField, String type, Value value, Locale defaultLocale) {

		if (value.isLocalized()) {
			_setDDMFieldLocalizedValue(ddmField, type, value);
		}
		else {
			_setDDMFieldUnlocalizedValue(ddmField, type, value, defaultLocale);
		}
	}

}