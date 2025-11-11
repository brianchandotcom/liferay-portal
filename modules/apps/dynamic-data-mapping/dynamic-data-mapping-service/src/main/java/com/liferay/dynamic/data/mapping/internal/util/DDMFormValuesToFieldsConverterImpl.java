/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.util;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;

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

		DDMForm ddmForm = ddmStructure.getFullHierarchyDDMForm(false);

		List<DDMFormFieldValue> ddmFormFieldValues =
			DDMFormValuesConverterUtil.addMissingDDMFormFieldValues(
				ddmForm.getDDMFormFields(),
				ddmFormValues.getDDMFormFieldValuesMap(true));

		Fields fields = new Fields();

		StringBundler fieldDisplayNamesSB = new StringBundler(
			ddmFormFieldValues.size() * 4);

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			_addFields(
				ddmFormFieldValues, ddmForm, ddmFormFieldValue,
				ddmStructure.getStructureId(), ddmFormValues.getDefaultLocale(),
				fields, fieldDisplayNamesSB);
		}

		if (!ddmFormFieldValues.isEmpty()) {
			fieldDisplayNamesSB.setIndex(fieldDisplayNamesSB.index() - 1);
		}

		fields.put(
			new Field(
				ddmStructure.getStructureId(), DDMImpl.FIELDS_DISPLAY_NAME,
				fieldDisplayNamesSB.toString()));

		return fields;
	}

	private void _addField(
			DDMFormField ddmFormField,
			List<DDMFormFieldValue> ddmFormFieldValues,
			DDMFormFieldValue ddmFormFieldValue, long ddmStructureId,
			Locale defaultLocale, Fields fields)
		throws PortalException {

		if ((ddmFormField == null) || ddmFormField.isTransient() ||
			(ddmFormFieldValue.getValue() == null)) {

			return;
		}

		Field field = _createField(
			ddmFormField, ddmFormFieldValues, ddmFormFieldValue, ddmStructureId,
			defaultLocale);

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
			List<DDMFormFieldValue> ddmFormFieldValues, DDMForm ddmForm,
			DDMFormFieldValue ddmFormFieldValue, long ddmStructureId,
			Locale defaultLocale, Fields fields,
			StringBundler fieldDisplayNamesSB)
		throws PortalException {

		_addField(
			ddmForm.getDDMFormField(ddmFormFieldValue.getName(), true),
			ddmFormFieldValues, ddmFormFieldValue, ddmStructureId,
			defaultLocale, fields);

		fieldDisplayNamesSB.append(ddmFormFieldValue.getName());
		fieldDisplayNamesSB.append(DDMImpl.INSTANCE_SEPARATOR);
		fieldDisplayNamesSB.append(ddmFormFieldValue.getInstanceId());
		fieldDisplayNamesSB.append(StringPool.COMMA);

		for (DDMFormFieldValue nestedDDMFormFieldValue :
				ddmFormFieldValue.getNestedDDMFormFieldValues()) {

			_addFields(
				ddmFormFieldValues, ddmForm, nestedDDMFormFieldValue,
				ddmStructureId, defaultLocale, fields, fieldDisplayNamesSB);
		}
	}

	private Field _createField(
			DDMFormField ddmFormField,
			List<DDMFormFieldValue> ddmFormFieldValues,
			DDMFormFieldValue ddmFormFieldValue, long ddmStructureId,
			Locale defaultLocale)
		throws PortalException {

		Field field = new Field();

		field.setDDMStructureId(ddmStructureId);
		field.setDefaultLocale(defaultLocale);
		field.setName(ddmFormFieldValue.getName());

		Value value = ddmFormFieldValue.getValue();

		Set<Locale> availableLocales = _getAvailableLocales(
			ddmFormFieldValues, ddmFormField.getName());

		if (MapUtil.isEmpty(value.getValues())) {
			LocalizedValue predefinedValue = ddmFormField.getPredefinedValue();

			Map<Locale, String> predefinedValuesMap =
				predefinedValue.getValues();

			if (predefinedValuesMap.isEmpty()) {
				LocalizedValue localizedValue = new LocalizedValue(
					defaultLocale);

				localizedValue.addString(defaultLocale, StringPool.BLANK);

				ddmFormField.setPredefinedValue(localizedValue);
			}

			value = ddmFormField.getPredefinedValue();

			availableLocales.addAll(value.getAvailableLocales());
		}

		if (!value.isLocalized()) {
			field.addValue(
				defaultLocale,
				FieldConstants.getSerializable(
					defaultLocale, LocaleUtil.ROOT, ddmFormField.getDataType(),
					value.getString(LocaleUtil.ROOT)));

			return field;
		}

		for (Locale availableLocale : availableLocales) {
			field.addValue(
				availableLocale,
				FieldConstants.getSerializable(
					availableLocale, availableLocale,
					ddmFormField.getDataType(),
					value.getString(availableLocale)));
		}

		return field;
	}

	private Set<Locale> _getAvailableLocales(
		List<DDMFormFieldValue> ddmFormFieldValues, String name) {

		Set<Locale> availableLocales = new HashSet<>();

		for (DDMFormFieldValue ddmFormFieldValue :
				DDMFormValues.getDDMFormFieldValues(
					ddmFormFieldValues, name, true)) {

			Value value = ddmFormFieldValue.getValue();

			if (value == null) {
				continue;
			}

			availableLocales.addAll(value.getAvailableLocales());
		}

		return availableLocales;
	}

}