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
import java.util.List;
import java.util.Map;

/**
 * @author Mateus Santana
 */
public class DDMFormValuesConverterUtil {

	public static List<DDMFormFieldValue> addMissingDDMFormFieldValues(
		Collection<DDMFormField> ddmFormFields,
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValues) {

		List<DDMFormFieldValue> newDDMFormFieldValues = new ArrayList<>();

		ddmFormFields.forEach(
			ddmFormField -> {
				List<DDMFormFieldValue> nestedDDMFormFieldValues =
					new ArrayList<>();

				if (StringUtil.equals(
						ddmFormField.getType(),
						DDMFormFieldTypeConstants.FIELDSET)) {

					nestedDDMFormFieldValues.addAll(
						addMissingDDMFormFieldValues(
							ddmFormField.getNestedDDMFormFields(),
							ddmFormFieldValues));
				}

				boolean hasNestedDDMFormFieldValues = ListUtil.isNotEmpty(
					nestedDDMFormFieldValues);

				if (!ddmFormFieldValues.containsKey(ddmFormField.getName()) ||
					hasNestedDDMFormFieldValues) {

					_addNewMissedDDMFormFieldValue(
						ddmFormField, newDDMFormFieldValues,
						nestedDDMFormFieldValues);
				}
				else {
					newDDMFormFieldValues.addAll(
						ddmFormFieldValues.get(ddmFormField.getName()));
				}
			});

		return newDDMFormFieldValues;
	}

	private static void _addNewMissedDDMFormFieldValue(
		DDMFormField ddmFormField, List<DDMFormFieldValue> ddmFormFieldValues,
		List<DDMFormFieldValue> nestedDDMFormFieldValues) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue() {
			{
				setName(ddmFormField.getName());
			}
		};

		for (DDMFormFieldValue nestedDDMFormFieldValue :
				nestedDDMFormFieldValues) {

			ddmFormFieldValue.addNestedDDMFormFieldValue(
				nestedDDMFormFieldValue);
		}

		ddmFormFieldValues.add(ddmFormFieldValue);
	}

}