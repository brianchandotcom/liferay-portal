/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		Fields fields = new Fields();

		fields.put(
			new Field(
				ddmStructure.getStructureId(), DDMImpl.FIELDS_DISPLAY_NAME,
				StringPool.BLANK));

		for (DDMFormFieldValue ddmFormFieldValue :
				ddmFormValues.getDDMFormFieldValues()) {

			_addFields(
				ddmFormFieldAvailableLocales, ddmFormFieldsMap,
				ddmFormFieldValue, ddmStructure.getStructureId(),
				ddmFormValues.getDefaultLocale(), fields);
		}

		return fields;
	}

	private void _addField(
			DDMFormField ddmFormField,
			Map<String, Set<Locale>> ddmFormFieldAvailableLocales,
			DDMFormFieldValue ddmFormFieldValue, long ddmStructureId,
			Locale defaultLocale, Fields fields)
		throws PortalException {

		if ((ddmFormField == null) || ddmFormField.isTransient() ||
			(ddmFormFieldValue.getValue() == null)) {

			return;
		}

		Field field = _createField(
			ddmFormField, ddmFormFieldAvailableLocales, ddmFormFieldValue,
			ddmStructureId, defaultLocale);

		Field existingField = fields.get(field.getName());

		if (existingField == null) {
			fields.put(field);

			return;
		}

		for (Locale availableLocale : field.getAvailableLocales()) {
			existingField.addValues(
				availableLocale, field.getValues(availableLocale));
		}
	}

	private void _addFields(
			Map<String, Set<Locale>> ddmFormFieldAvailableLocales,
			Map<String, DDMFormField> ddmFormFieldsMap,
			DDMFormFieldValue ddmFormFieldValue, long ddmStructureId,
			Locale defaultLocale, Fields fields)
		throws PortalException {

		DDMFormField ddmFormField = ddmFormFieldsMap.get(
			ddmFormFieldValue.getName());

		_addField(
			ddmFormField, ddmFormFieldAvailableLocales, ddmFormFieldValue,
			ddmStructureId, defaultLocale, fields);

		_addFieldsDisplayValue(
			fields.get(DDMImpl.FIELDS_DISPLAY_NAME),
			StringBundler.concat(
				ddmFormFieldValue.getName(), DDMImpl.INSTANCE_SEPARATOR,
				ddmFormFieldValue.getInstanceId()));

		for (DDMFormFieldValue nestedDDMFormFieldValue :
				ddmFormFieldValue.getNestedDDMFormFieldValues()) {

			_addFields(
				ddmFormFieldAvailableLocales, ddmFormFieldsMap,
				nestedDDMFormFieldValue, ddmStructureId, defaultLocale, fields);
		}
	}

	private void _addFieldsDisplayValue(
		Field fieldsDisplayField, String fieldsDisplayValue) {

		String[] fieldsDisplayValues = StringUtil.split(
			(String)fieldsDisplayField.getValue());

		fieldsDisplayField.setValue(
			StringUtil.merge(
				ArrayUtil.append(fieldsDisplayValues, fieldsDisplayValue)));
	}

	private Field _createField(
			DDMFormField ddmFormField,
			Map<String, Set<Locale>> ddmFormFieldAvailableLocales,
			DDMFormFieldValue ddmFormFieldValue, long ddmStructureId,
			Locale defaultLocale)
		throws PortalException {

		Field field = new Field();

		field.setDDMStructureId(ddmStructureId);
		field.setDefaultLocale(defaultLocale);
		field.setName(ddmFormFieldValue.getName());

		Value value = ddmFormFieldValue.getValue();

		if (!value.isLocalized()) {
			field.addValue(
				defaultLocale,
				FieldConstants.getSerializable(
					defaultLocale, LocaleUtil.ROOT, ddmFormField.getDataType(),
					value.getString(LocaleUtil.ROOT)));

			return field;
		}

		for (Locale availableLocale :
				ddmFormFieldAvailableLocales.get(field.getName())) {

			field.addValue(
				availableLocale,
				FieldConstants.getSerializable(
					availableLocale, availableLocale,
					ddmFormField.getDataType(),
					value.getString(availableLocale)));
		}

		return field;
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

}