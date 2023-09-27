/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Mateus Santana
 */
public class DDMFormValuesConverterUtil {

	public static List<DDMFormFieldValue> addMissingDDMFormFieldValues(
		Collection<DDMFormField> ddmFormFields,
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValues) {

		List<DDMFormFieldValue> newDDMFormFieldValuesList = new ArrayList<>();

		for (DDMFormField ddmFormField : ddmFormFields) {
			List<DDMFormFieldValue> ddmFormFieldValuesList =
				ddmFormFieldValues.get(ddmFormField.getName());

			if (ddmFormFieldValuesList == null) {
				DDMFormFieldValue ddmFormFieldValue =
					_createDefaultDDMFormFieldValue(ddmFormField);

				_populateNestedValues(
					ddmFormField, ddmFormFieldValue, ddmFormFieldValues);

				newDDMFormFieldValuesList.add(ddmFormFieldValue);
			}
			else {
				for (DDMFormFieldValue ddmFormFieldValue :
						ddmFormFieldValuesList) {

					_populateNestedValues(
						ddmFormField, ddmFormFieldValue, ddmFormFieldValues);

					newDDMFormFieldValuesList.add(ddmFormFieldValue);
				}
			}
		}

		return newDDMFormFieldValuesList;
	}

	private static DDMFormFieldValue _createDefaultDDMFormFieldValue(
		DDMFormField ddmFormField) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setInstanceId(StringUtil.randomString());
		ddmFormFieldValue.setName(ddmFormField.getName());

		if (ddmFormField.isLocalizable()) {
			ddmFormFieldValue.setValue(new LocalizedValue());
		}
		else {
			ddmFormFieldValue.setValue(new UnlocalizedValue((String)null));
		}

		return ddmFormFieldValue;
	}

	private static void _populateNestedValues(
		DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue,
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValues) {

		if (!StringUtil.equals(
				ddmFormField.getType(), DDMFormFieldTypeConstants.FIELDSET)) {

			return;
		}

		Set<String> currentNames = new HashSet<>();

		for (DDMFormFieldValue currentDDMFormFieldValue :
				ddmFormFieldValue.getNestedDDMFormFieldValues()) {

			currentNames.add(currentDDMFormFieldValue.getName());
		}

		Set<String> expectedNames = new HashSet<>();

		for (DDMFormField nestedDDMFormField :
				ddmFormField.getNestedDDMFormFields()) {

			expectedNames.add(nestedDDMFormField.getName());

			List<DDMFormFieldValue> nestedDDMFormFieldValueList =
				ddmFormFieldValues.get(nestedDDMFormField.getName());

			if (nestedDDMFormFieldValueList == null) {
				ddmFormFieldValue.addNestedDDMFormFieldValue(
					_createDefaultDDMFormFieldValue(nestedDDMFormField));
			}
			else {
				for (DDMFormFieldValue nestedDDMFormFieldValue :
						nestedDDMFormFieldValueList) {

					if (!currentNames.contains(
							nestedDDMFormFieldValue.getName())) {

						ddmFormFieldValue.addNestedDDMFormFieldValue(
							nestedDDMFormFieldValue);

						_populateNestedValues(
							nestedDDMFormField, nestedDDMFormFieldValue,
							ddmFormFieldValues);
					}
				}
			}
		}

		List<DDMFormFieldValue> currentDDMFormFieldValues =
			ddmFormFieldValue.getNestedDDMFormFieldValues();

		Iterator<DDMFormFieldValue> iterator =
			currentDDMFormFieldValues.iterator();

		while (iterator.hasNext()) {
			DDMFormFieldValue currentDDMFormFieldValue = iterator.next();

			if (!expectedNames.contains(currentDDMFormFieldValue.getName())) {
				iterator.remove();
			}
		}
	}

}