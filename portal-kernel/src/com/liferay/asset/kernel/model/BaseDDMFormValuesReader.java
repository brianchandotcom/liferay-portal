/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.model;

import com.liferay.dynamic.data.mapping.kernel.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseDDMFormValuesReader implements DDMFormValuesReader {

	@Override
	public List<DDMFormFieldValue> getDDMFormFieldValues(
			String ddmFormFieldType)
		throws PortalException {

		List<DDMFormFieldValue> filteredDDMFormFieldValues = new ArrayList<>();

		DDMFormValues ddmFormValues = getDDMFormValues();

		addDDMFormFieldValuesByType(
			ddmFormValues.getDDMFormFieldValues(), filteredDDMFormFieldValues,
			ddmFormFieldType);

		return filteredDDMFormFieldValues;
	}

	protected void addDDMFormFieldValuesByType(
		List<DDMFormFieldValue> ddmFormFieldValues,
		List<DDMFormFieldValue> filteredDDMFormFieldValues,
		String ddmFormFieldType) {

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			if (ddmFormFieldType.equals(ddmFormFieldValue.getType())) {
				filteredDDMFormFieldValues.add(ddmFormFieldValue);
			}

			addDDMFormFieldValuesByType(
				ddmFormFieldValue.getNestedDDMFormFieldValues(),
				filteredDDMFormFieldValues, ddmFormFieldType);
		}
	}

}