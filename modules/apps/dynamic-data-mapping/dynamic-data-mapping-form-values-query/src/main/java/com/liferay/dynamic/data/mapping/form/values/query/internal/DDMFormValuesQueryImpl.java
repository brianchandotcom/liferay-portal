/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.values.query.internal;

import com.liferay.dynamic.data.mapping.form.values.query.DDMFormValuesQuery;
import com.liferay.dynamic.data.mapping.form.values.query.internal.model.DDMFormValuesFilter;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adolfo Pérez
 * @author Marcellus Tavares
 */
public class DDMFormValuesQueryImpl implements DDMFormValuesQuery {

	public DDMFormValuesQueryImpl(
		DDMFormValues ddmFormValues,
		List<DDMFormValuesFilter> ddmFormValuesFilters) {

		_ddmFormValues = ddmFormValues;
		_ddmFormValuesFilters = ddmFormValuesFilters;
	}

	@Override
	public List<DDMFormFieldValue> selectDDMFormFieldValues() {
		DDMFormValuesFilter firstDDMFormValuesFilter =
			_ddmFormValuesFilters.get(0);

		List<DDMFormFieldValue> ddmFormFieldValues =
			firstDDMFormValuesFilter.filter(_ddmFormValues);

		for (int i = 1; i < _ddmFormValuesFilters.size(); i++) {
			DDMFormValuesFilter currentDDMFormValuesFilter =
				_ddmFormValuesFilters.get(i);

			List<DDMFormFieldValue> nestedDDMFormFieldValues =
				_getNestedDDMFormFieldValues(ddmFormFieldValues);

			ddmFormFieldValues = currentDDMFormValuesFilter.filter(
				nestedDDMFormFieldValues);
		}

		return ddmFormFieldValues;
	}

	@Override
	public DDMFormFieldValue selectSingleDDMFormFieldValue() {
		List<DDMFormFieldValue> ddmFormFieldValues = selectDDMFormFieldValues();

		if (ddmFormFieldValues.isEmpty()) {
			return null;
		}

		return ddmFormFieldValues.get(0);
	}

	private List<DDMFormFieldValue> _getNestedDDMFormFieldValues(
		List<DDMFormFieldValue> ddmFormFieldValues) {

		List<DDMFormFieldValue> nestedDDMFormFieldValues = new ArrayList<>();

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			nestedDDMFormFieldValues.addAll(
				ddmFormFieldValue.getNestedDDMFormFieldValues());
		}

		return nestedDDMFormFieldValues;
	}

	private final List<DDMFormValuesFilter> _ddmFormValuesFilters;
	private final DDMFormValues _ddmFormValues;

}