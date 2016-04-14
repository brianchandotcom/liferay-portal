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

import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Inácio Nery
 */
public class DDMFormValuesMerge {

	public static DDMFormValues mergeDDMFormValues(
		DDMFormValues newDDMFormValues, DDMFormValues existingDDMFormValues) {

		List<DDMFormFieldValue> mergeDDMFormFieldValues =
			mergeDDMFormFieldValues(
				newDDMFormValues.getDDMFormFieldValues(),
				existingDDMFormValues.getDDMFormFieldValues());

		existingDDMFormValues.setDDMFormFieldValues(mergeDDMFormFieldValues);

		return existingDDMFormValues;
	}

	protected static DDMFormFieldValue matchDDMFormFieldValue(
		DDMFormFieldValue expectedDDMFormFieldValue,
		List<DDMFormFieldValue> actualDDMFormFieldValues) {

		for (DDMFormFieldValue actualDDMFormFieldValue :
				actualDDMFormFieldValues) {

			if (Validator.equals(
					actualDDMFormFieldValue.getName(),
					expectedDDMFormFieldValue.getName())) {

				return actualDDMFormFieldValue;
			}
		}

		return null;
	}

	protected static List<DDMFormFieldValue> mergeDDMFormFieldValues(
		List<DDMFormFieldValue> newDDMFormFieldValues,
		List<DDMFormFieldValue> existingDDMFormFieldValues) {

		List<DDMFormFieldValue> mergedDDMFormFieldValues = new ArrayList<>(
			existingDDMFormFieldValues);

		for (DDMFormFieldValue newDDMFormFieldValue : newDDMFormFieldValues) {
			DDMFormFieldValue actualDDMFormFieldValue = matchDDMFormFieldValue(
				newDDMFormFieldValue, existingDDMFormFieldValues);

			if (actualDDMFormFieldValue != null) {
				mergeValue(
					newDDMFormFieldValue.getValue(),
					actualDDMFormFieldValue.getValue());

				List<DDMFormFieldValue> nestedDDMFormFieldValues =
					mergeDDMFormFieldValues(
						newDDMFormFieldValue.getNestedDDMFormFieldValues(),
						actualDDMFormFieldValue.getNestedDDMFormFieldValues());

				newDDMFormFieldValue.setNestedDDMFormFields(
					nestedDDMFormFieldValues);

				mergedDDMFormFieldValues.remove(actualDDMFormFieldValue);
			}

			mergedDDMFormFieldValues.add(newDDMFormFieldValue);
		}

		return mergedDDMFormFieldValues;
	}

	protected static void mergeValue(Value newValue, Value existingValue) {
		for (Locale locale : existingValue.getAvailableLocales()) {
			String value = newValue.getString(locale);

			if (value == null) {
				newValue.addString(locale, existingValue.getString(locale));
			}
		}
	}

}