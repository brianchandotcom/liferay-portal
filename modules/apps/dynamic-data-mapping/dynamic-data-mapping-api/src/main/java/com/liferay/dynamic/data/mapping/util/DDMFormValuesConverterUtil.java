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

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Mateus Santana
 */
public class DDMFormValuesConverterUtil {

	public static List<DDMFormFieldValue> addMissingDDMFormFieldValues(
		Collection<DDMFormField> ddmFormFields,
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValues) {

		List<DDMFormFieldValue> newDDMFormFieldValues = new ArrayList<>();

		for (DDMFormField ddmFormField : ddmFormFields) {
			String ddmFormFieldName = ddmFormField.getName();

			if (ddmFormFieldValues.containsKey(ddmFormFieldName)) {
				newDDMFormFieldValues.addAll(
					ddmFormFieldValues.get(ddmFormFieldName));
			}
			else {
				newDDMFormFieldValues.add(
					new DDMFormFieldValue() {
						{
							setInstanceId(StringUtil.randomString());
							setName(ddmFormFieldName);
						}
					});
			}

			if (!StringUtil.equals(
					ddmFormField.getType(),
					DDMFormFieldTypeConstants.FIELDSET)) {

				continue;
			}

			for (DDMFormFieldValue newDDMFormFieldValue :
					newDDMFormFieldValues) {

				if (!StringUtil.equals(
						newDDMFormFieldValue.getName(), ddmFormFieldName)) {

					continue;
				}

				_addMissingNestedDDMFormFieldValues(
					newDDMFormFieldValue,
					addMissingDDMFormFieldValues(
						ddmFormField.getNestedDDMFormFields(),
						ddmFormFieldValues));

				_removeExtraNestedDDMFormFieldValues(
					newDDMFormFieldValue,
					ddmFormField.getNestedDDMFormFields());
			}
		}

		return newDDMFormFieldValues;
	}

	private static void _addMissingNestedDDMFormFieldValues(
		DDMFormFieldValue ddmFormFieldValue,
		List<DDMFormFieldValue> nestedDDMFormFieldValues) {

		Set<String> nestedDDMFormFieldNames = _getDDMFormFieldNames(
			ddmFormFieldValue.getNestedDDMFormFieldValues());

		for (DDMFormFieldValue nestedDDMFormFieldValue :
				nestedDDMFormFieldValues) {

			if (nestedDDMFormFieldNames.contains(
					nestedDDMFormFieldValue.getName())) {

				continue;
			}

			ddmFormFieldValue.addNestedDDMFormFieldValue(
				nestedDDMFormFieldValue);
		}
	}

	private static Set<String> _getDDMFormFieldNames(
		List<DDMFormFieldValue> ddmFormFieldValues) {

		if (ListUtil.isEmpty(ddmFormFieldValues)) {
			return Collections.emptySet();
		}

		Stream<DDMFormFieldValue> stream = ddmFormFieldValues.stream();

		return stream.map(
			DDMFormFieldValue::getName
		).collect(
			Collectors.toSet()
		);
	}

	private static void _removeExtraNestedDDMFormFieldValues(
		DDMFormFieldValue ddmFormFieldValue,
		List<DDMFormField> nestedDDMFormFields) {

		Map<String, List<DDMFormFieldValue>> nestedDDMFormFieldValuesMap =
			ddmFormFieldValue.getNestedDDMFormFieldValuesMap();

		ddmFormFieldValue.setNestedDDMFormFields(new ArrayList<>());

		Stream<DDMFormField> stream = nestedDDMFormFields.stream();

		stream.map(
			DDMFormField::getName
		).map(
			nestedDDMFormFieldValuesMap::get
		).flatMap(
			List::stream
		).forEach(
			nestedDDMFormFieldValue ->
				ddmFormFieldValue.addNestedDDMFormFieldValue(
					nestedDDMFormFieldValue)
		);
	}

}