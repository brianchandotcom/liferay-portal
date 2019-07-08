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
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesMerger;
import com.liferay.portal.kernel.util.GetterUtil;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = DDMFormValuesMerger.class)
public class DDMFormValuesMergerImpl implements DDMFormValuesMerger {

	@Override
	public DDMFormValues merge(
		DDMFormValues newDDMFormValues, DDMFormValues existingDDMFormValues) {

		List<DDMFormFieldValue> mergedDDMFormFieldValues =
			mergeDDMFormFieldValues(
				newDDMFormValues.getDDMFormFieldValues(),
				existingDDMFormValues.getDDMFormFieldValues());

		existingDDMFormValues.setDDMFormFieldValues(mergedDDMFormFieldValues);

		return existingDDMFormValues;
	}

	protected DDMFormFieldValue getDDMFormFieldValueByName(
		List<DDMFormFieldValue> ddmFormFieldValues, String name) {

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			if (name.equals(ddmFormFieldValue.getName())) {
				return ddmFormFieldValue;
			}
		}

		return null;
	}

	protected List<DDMFormFieldValue> mergeDDMFormFieldValues(
		List<DDMFormFieldValue> newDDMFormFieldValues,
		List<DDMFormFieldValue> existingDDMFormFieldValues) {

		List<DDMFormFieldValue> mergedDDMFormFieldValues = new ArrayList<>(
			existingDDMFormFieldValues);

		for (DDMFormFieldValue newDDMFormFieldValue : newDDMFormFieldValues) {
			DDMFormValues ddmFormValues =
				newDDMFormFieldValue.getDDMFormValues();

			DDMForm ddmForm = ddmFormValues.getDDMForm();

			DDMFormFieldValue actualDDMFormFieldValue =
				getDDMFormFieldValueByName(
					existingDDMFormFieldValues, newDDMFormFieldValue.getName());

			if (actualDDMFormFieldValue != null) {
				List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

				Stream<DDMFormField> stream = ddmFormFields.stream();

				DDMFormField ddmFormField = stream.filter(
					p -> p.getName(
					).equals(
						newDDMFormFieldValue.getName()
					)
				).findFirst(
				).orElseGet(
					() -> null
				);

				mergeValue(
					newDDMFormFieldValue.getValue(),
					actualDDMFormFieldValue.getValue(), ddmFormField);

				List<DDMFormFieldValue> mergedNestedDDMFormFieldValues =
					mergeDDMFormFieldValues(
						newDDMFormFieldValue.getNestedDDMFormFieldValues(),
						actualDDMFormFieldValue.getNestedDDMFormFieldValues());

				newDDMFormFieldValue.setNestedDDMFormFields(
					mergedNestedDDMFormFieldValues);

				existingDDMFormFieldValues.remove(actualDDMFormFieldValue);
				mergedDDMFormFieldValues.remove(actualDDMFormFieldValue);
			}

			mergedDDMFormFieldValues.add(newDDMFormFieldValue);
		}

		return mergedDDMFormFieldValues;
	}

	protected void mergeValue(
		Value newValue, Value existingValue, DDMFormField ddmFormField) {

		if ((newValue == null) || (existingValue == null)) {
			return;
		}

		for (Locale locale : existingValue.getAvailableLocales()) {
			String value = newValue.getString(locale);

			String dataType = ddmFormField.getDataType();

			if (dataType.equals("double")) {
				DecimalFormat numberFormat = _getDecimalFormat(locale);

				newValue.addString(
					locale,
					numberFormat.format(
						GetterUtil.getDouble(
							value, newValue.getDefaultLocale())));
			}

			if (value == null) {
				newValue.addString(locale, existingValue.getString(locale));
			}
		}
	}

	private static DecimalFormat _getDecimalFormat(Locale locale) {
		DecimalFormat formatter = _decimalFormattersMap.get(locale);

		if (formatter == null) {
			formatter = (DecimalFormat)DecimalFormat.getInstance(locale);

			formatter.setGroupingUsed(false);
			formatter.setMaximumFractionDigits(Integer.MAX_VALUE);
			formatter.setParseBigDecimal(true);

			_decimalFormattersMap.put(locale, formatter);
		}

		return formatter;
	}

	private static final Map<Locale, DecimalFormat> _decimalFormattersMap =
		new ConcurrentHashMap<>();

}