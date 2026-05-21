/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.util;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDMFieldsCounter;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.dynamic.data.mapping.util.NumericDDMFormFieldUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(service = FieldsToDDMFormValuesConverter.class)
public class FieldsToDDMFormValuesConverterImpl
	implements FieldsToDDMFormValuesConverter {

	@Override
	public DDMFormValues convert(DDMStructure ddmStructure, Fields fields)
		throws PortalException {

		DDMForm ddmForm = ddmStructure.getFullHierarchyDDMForm();

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		DDMFormValues ddmFormValues = createDDMFormValues(
			ddmForm, fields.getAvailableLocales(), fields.getDefaultLocale());

		DDMFieldsCounter ddmFieldsCounter = new DDMFieldsCounter();

		// Split the fields-display value once for the duration of this call.
		// _countDDMFieldRepetitions and _getDDMFieldInstanceId would otherwise
		// re-split the cumulative string per leaf, turning convert() into
		// O(L^2) in the leaf count.

		Field fieldsDisplayField = fields.get(DDMImpl.FIELDS_DISPLAY_NAME);

		String[] ddmFieldsDisplayValues = null;
		Map<String, List<String>> instanceIdsByFieldName = null;

		if (fieldsDisplayField != null) {
			String[] rawFieldsDisplayValues = splitFieldsDisplayValue(
				fieldsDisplayField);

			ddmFieldsDisplayValues = _toFilteredFieldNames(
				rawFieldsDisplayValues, ddmFormFieldsMap);
			instanceIdsByFieldName = _toInstanceIdsByFieldName(
				rawFieldsDisplayValues);
		}

		for (String fieldName :
				_getDDMFormFieldNames(ddmForm.getDDMFormFields())) {

			int repetitions = _countDDMFieldRepetitions(
				ddmFieldsDisplayValues, fields, fieldName, null, -1);

			for (int i = 0; i < repetitions; i++) {
				DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(
					fieldName, instanceIdsByFieldName, ddmFieldsCounter);

				DDMFormField ddmFormField = ddmFormFieldsMap.get(fieldName);

				if (ddmFormField != null) {
					ddmFormFieldValue.setFieldReference(
						ddmFormField.getFieldReference());
				}

				_setDDMFormFieldValueProperties(
					ddmFormFieldValue, ddmFormFieldsMap, fields,
					ddmFieldsDisplayValues, instanceIdsByFieldName,
					ddmFieldsCounter);

				ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
			}
		}

		return ddmFormValues;
	}

	protected DDMFormFieldValue createDDMFormFieldValue(
		String name, Map<String, List<String>> instanceIdsByFieldName,
		DDMFieldsCounter ddmFieldsCounter) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue(
			_getDDMFieldInstanceId(
				instanceIdsByFieldName, name, ddmFieldsCounter.get(name)));

		ddmFormFieldValue.setName(name);

		return ddmFormFieldValue;
	}

	protected DDMFormValues createDDMFormValues(
		DDMForm ddmForm, Set<Locale> availableLocales, Locale defaultLocale) {

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.setAvailableLocales(availableLocales);
		ddmFormValues.setDefaultLocale(defaultLocale);

		return ddmFormValues;
	}

	protected String[] splitFieldsDisplayValue(Field fieldsDisplayField) {
		String value = (String)fieldsDisplayField.getValue();

		return StringUtil.split(value);
	}

	private int _countDDMFieldRepetitions(
		String[] ddmFieldsDisplayValues, Fields ddmFields, String fieldName,
		String parentFieldName, int parentOffset) {

		if (ddmFieldsDisplayValues == null) {
			if (ddmFields.contains(fieldName)) {
				return 1;
			}

			return 0;
		}

		int offset = -1;

		int repetitions = 0;

		for (String fieldDisplayName : ddmFieldsDisplayValues) {
			if (offset > parentOffset) {
				break;
			}

			if (fieldDisplayName.equals(parentFieldName)) {
				offset++;
			}

			if (fieldDisplayName.equals(fieldName) &&
				(offset == parentOffset)) {

				repetitions++;
			}
		}

		return repetitions;
	}

	private String _getDDMFieldInstanceId(
		Map<String, List<String>> instanceIdsByFieldName, String fieldName,
		int index) {

		if (instanceIdsByFieldName == null) {
			return StringUtil.randomString();
		}

		List<String> instanceIds = instanceIdsByFieldName.get(fieldName);

		if ((instanceIds == null) || (index >= instanceIds.size())) {
			return null;
		}

		return instanceIds.get(index);
	}

	private String _getDDMFieldValueString(
		Field ddmField, Locale locale, int index) {

		Serializable fieldValue = ddmField.getValue(locale, index);

		if (fieldValue == null) {
			return StringPool.BLANK;
		}

		if (fieldValue instanceof Date) {
			Date valueDate = (Date)fieldValue;

			fieldValue = valueDate.getTime();
		}
		else if ((fieldValue instanceof Number) &&
				 !(fieldValue instanceof Integer)) {

			DecimalFormat decimalFormat =
				NumericDDMFormFieldUtil.getDecimalFormat(locale);

			Number number = (Number)fieldValue;

			if (number instanceof Double || number instanceof Float) {
				decimalFormat.setMaximumFractionDigits(Integer.MAX_VALUE);
				decimalFormat.setMinimumFractionDigits(1);
			}

			return decimalFormat.format(number.doubleValue());
		}

		return String.valueOf(fieldValue);
	}

	private List<String> _getDDMFormFieldNames(
		List<DDMFormField> ddmFormFields) {

		return TransformUtil.transform(
			ddmFormFields, ddmFormField -> ddmFormField.getName());
	}

	private void _setDDMFormFieldValueLocalizedValue(
		DDMFormFieldValue ddmFormFieldValue, Field ddmField, int index) {

		Value value = new LocalizedValue(ddmField.getDefaultLocale());

		for (Locale availableLocale : ddmField.getAvailableLocales()) {
			String valueString = _getDDMFieldValueString(
				ddmField, availableLocale, index);

			value.addString(availableLocale, valueString);
		}

		ddmFormFieldValue.setValue(value);
	}

	private void _setDDMFormFieldValueProperties(
			DDMFormFieldValue ddmFormFieldValue,
			Map<String, DDMFormField> ddmFormFieldsMap, Fields ddmFields,
			String[] ddmFieldsDisplayValues,
			Map<String, List<String>> instanceIdsByFieldName,
			DDMFieldsCounter ddmFieldsCounter)
		throws PortalException {

		_setNestedDDMFormFieldValues(
			ddmFormFieldValue, ddmFormFieldsMap, ddmFields,
			ddmFieldsDisplayValues, instanceIdsByFieldName, ddmFieldsCounter);

		_setDDMFormFieldValueValues(
			ddmFormFieldValue, ddmFormFieldsMap, ddmFields, ddmFieldsCounter);
	}

	private void _setDDMFormFieldValueUnlocalizedValue(
		DDMFormFieldValue ddmFormFieldValue, Field ddmField, int index) {

		String valueString = _getDDMFieldValueString(
			ddmField, ddmField.getDefaultLocale(), index);

		Value value = new UnlocalizedValue(valueString);

		ddmFormFieldValue.setValue(value);
	}

	private void _setDDMFormFieldValueValues(
			DDMFormFieldValue ddmFormFieldValue,
			Map<String, DDMFormField> ddmFormFieldMap, Fields ddmFields,
			DDMFieldsCounter ddmFieldsCounter)
		throws PortalException {

		String fieldName = ddmFormFieldValue.getName();

		DDMFormField ddmFormField = ddmFormFieldMap.get(fieldName);

		Field field = ddmFields.get(fieldName);

		if (!ddmFormField.isTransient() && (field != null)) {
			if (ddmFormField.isLocalizable()) {
				_setDDMFormFieldValueLocalizedValue(
					ddmFormFieldValue, field, ddmFieldsCounter.get(fieldName));
			}
			else {
				_setDDMFormFieldValueUnlocalizedValue(
					ddmFormFieldValue, field, ddmFieldsCounter.get(fieldName));
			}
		}

		ddmFieldsCounter.incrementKey(fieldName);
	}

	private void _setNestedDDMFormFieldValues(
			DDMFormFieldValue ddmFormFieldValue,
			Map<String, DDMFormField> ddmFormFieldsMap, Fields ddmFields,
			String[] ddmFieldsDisplayValues,
			Map<String, List<String>> instanceIdsByFieldName,
			DDMFieldsCounter ddmFieldsCounter)
		throws PortalException {

		String fieldName = ddmFormFieldValue.getName();

		int parentOffset = ddmFieldsCounter.get(fieldName);

		DDMFormField parentDDMFormField = ddmFormFieldsMap.get(fieldName);

		List<String> nestedFieldNames = _getDDMFormFieldNames(
			parentDDMFormField.getNestedDDMFormFields());

		for (String nestedFieldName : nestedFieldNames) {
			int repetitions = _countDDMFieldRepetitions(
				ddmFieldsDisplayValues, ddmFields, nestedFieldName, fieldName,
				parentOffset);

			for (int i = 0; i < repetitions; i++) {
				DDMFormFieldValue nestedDDMFormFieldValue =
					createDDMFormFieldValue(
						nestedFieldName, instanceIdsByFieldName,
						ddmFieldsCounter);

				DDMFormField nestedDDMFormField = ddmFormFieldsMap.get(
					nestedFieldName);

				if (nestedDDMFormField != null) {
					nestedDDMFormFieldValue.setFieldReference(
						nestedDDMFormField.getFieldReference());
				}

				_setDDMFormFieldValueProperties(
					nestedDDMFormFieldValue, ddmFormFieldsMap, ddmFields,
					ddmFieldsDisplayValues, instanceIdsByFieldName,
					ddmFieldsCounter);

				ddmFormFieldValue.addNestedDDMFormFieldValue(
					nestedDDMFormFieldValue);
			}
		}
	}

	private String[] _toFilteredFieldNames(
		String[] rawFieldsDisplayValues,
		Map<String, DDMFormField> ddmFormFieldsMap) {

		return TransformUtil.transform(
			rawFieldsDisplayValues,
			value -> {
				String fieldName = StringUtil.extractFirst(
					value, DDMImpl.INSTANCE_SEPARATOR);

				if (!ddmFormFieldsMap.containsKey(fieldName)) {
					return null;
				}

				return fieldName;
			},
			String.class);
	}

	private Map<String, List<String>> _toInstanceIdsByFieldName(
		String[] rawFieldsDisplayValues) {

		Map<String, List<String>> instanceIdsByFieldName = new HashMap<>();

		for (String value : rawFieldsDisplayValues) {
			String fieldName = StringUtil.extractFirst(
				value, DDMImpl.INSTANCE_SEPARATOR);
			String instanceId = StringUtil.extractLast(
				value, DDMImpl.INSTANCE_SEPARATOR);

			List<String> instanceIds = instanceIdsByFieldName.computeIfAbsent(
				fieldName, key -> new ArrayList<>());

			instanceIds.add(instanceId);
		}

		return instanceIdsByFieldName;
	}

}