/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.util;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesRegistry;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesListMap =
			new HashMap<>();

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			List<DDMFormFieldValue> ddmFormFieldValuesList =
				ddmFormFieldValuesListMap.computeIfAbsent(
					ddmFormFieldValue.getName(), key -> new ArrayList<>());

			ddmFormFieldValuesList.add(ddmFormFieldValue);

			ddmFormFieldValue.populateNestedDDMFormFieldValuesMap(
				ddmFormFieldValuesListMap);
		}

		Fields fields = new Fields();

		StringBundler fieldDisplayNamesSB = new StringBundler(
			ddmFormFieldValues.size() * 4);

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			_addFields(
				ddmForm, ddmFormFieldValue, ddmFormFieldValuesListMap,
				ddmStructure.getStructureId(), ddmFormValues.getDefaultLocale(),
				fieldDisplayNamesSB, fields);
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
			DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue,
			Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesListMap,
			long ddmStructureId, Locale defaultLocale, Fields fields)
		throws PortalException {

		if ((ddmFormField == null) || ddmFormField.isTransient() ||
			(ddmFormFieldValue.getValue() == null)) {

			return;
		}

		Field field = _createField(
			ddmFormField, ddmFormFieldValuesListMap, ddmFormFieldValue,
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
			DDMForm ddmForm, DDMFormFieldValue ddmFormFieldValue,
			Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesListMap,
			long ddmStructureId, Locale defaultLocale,
			StringBundler fieldDisplayNamesSB, Fields fields)
		throws PortalException {

		_addField(
			ddmForm.getDDMFormField(ddmFormFieldValue.getName(), true),
			ddmFormFieldValue, ddmFormFieldValuesListMap, ddmStructureId,
			defaultLocale, fields);

		fieldDisplayNamesSB.append(ddmFormFieldValue.getName());
		fieldDisplayNamesSB.append(DDMImpl.INSTANCE_SEPARATOR);
		fieldDisplayNamesSB.append(ddmFormFieldValue.getInstanceId());
		fieldDisplayNamesSB.append(StringPool.COMMA);

		for (DDMFormFieldValue nestedDDMFormFieldValue :
				ddmFormFieldValue.getNestedDDMFormFieldValues()) {

			_addFields(
				ddmForm, nestedDDMFormFieldValue, ddmFormFieldValuesListMap,
				ddmStructureId, defaultLocale, fieldDisplayNamesSB, fields);
		}
	}

	private Field _createField(
			DDMFormField ddmFormField,
			Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesListMap,
			DDMFormFieldValue ddmFormFieldValue, long ddmStructureId,
			Locale defaultLocale)
		throws PortalException {

		Field field = new Field();

		field.setDDMStructureId(ddmStructureId);
		field.setDefaultLocale(defaultLocale);
		field.setName(ddmFormFieldValue.getName());

		boolean addValueLocales = false;

		Value value = ddmFormFieldValue.getValue();

		if (MapUtil.isEmpty(value.getValues())) {
			LocalizedValue predefinedValue = ddmFormField.getPredefinedValue();

			if (_isPredefinedValueEmpty(
					ddmFormField, predefinedValue.getValues())) {

				LocalizedValue localizedValue = new LocalizedValue(
					defaultLocale);

				localizedValue.addString(defaultLocale, StringPool.BLANK);

				ddmFormField.setPredefinedValue(localizedValue);
			}

			value = ddmFormField.getPredefinedValue();

			addValueLocales = true;
		}

		if (!value.isLocalized()) {
			field.addValue(
				defaultLocale,
				FieldConstants.getSerializable(
					defaultLocale, LocaleUtil.ROOT, ddmFormField.getDataType(),
					value.getString(LocaleUtil.ROOT)));

			return field;
		}

		Set<Locale> availableLocales = _getAvailableLocales(
			ddmFormFieldValuesListMap, ddmFormField.getName());

		if (addValueLocales) {
			availableLocales.addAll(value.getAvailableLocales());
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
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesListMap,
		String name) {

		Set<Locale> availableLocales = new HashSet<>();

		List<DDMFormFieldValue> ddmFormFieldValuesList =
			ddmFormFieldValuesListMap.get(name);

		if (ddmFormFieldValuesList == null) {
			return availableLocales;
		}

		List<DDMFormFieldValue> matchedDDMFormFieldValues = new ArrayList<>();

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValuesList) {
			Value value = ddmFormFieldValue.getValue();

			if (value == null) {
				continue;
			}

			availableLocales.addAll(value.getAvailableLocales());

			ddmFormFieldValue.populateNestedDDMFormFieldValues(
				name, matchedDDMFormFieldValues);
		}

		for (DDMFormFieldValue ddmFormFieldValue : matchedDDMFormFieldValues) {
			Value value = ddmFormFieldValue.getValue();

			if (value == null) {
				continue;
			}

			availableLocales.addAll(value.getAvailableLocales());
		}

		return availableLocales;
	}

	private boolean _isPredefinedValueEmpty(
		DDMFormField ddmFormField, Map<Locale, String> valuesMap) {

		if (MapUtil.isEmpty(valuesMap)) {
			return true;
		}

		DDMFormFieldType ddmFormFieldType =
			_ddmFormFieldTypeServicesRegistry.getDDMFormFieldType(
				ddmFormField.getType());

		for (String predefinedValue : valuesMap.values()) {
			if (!ddmFormFieldType.isPredefinedValueEmpty(predefinedValue)) {
				return false;
			}
		}

		return true;
	}

	@Reference
	private DDMFormFieldTypeServicesRegistry _ddmFormFieldTypeServicesRegistry;

}