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
		List<DDMFormValuesFilter> ddmFormFieldValueFilters) {

		_ddmFormValues = ddmFormValues;
		_ddmFormFieldValueFilters = ddmFormFieldValueFilters;
	}

	@Override
	public List<DDMFormFieldValue> selectDDMFormFieldValues() {
		DDMFormValuesFilter firstDDMFormValuesFilter =
			_ddmFormFieldValueFilters.get(0);

		List<DDMFormFieldValue> ddmFormFieldValues =
			firstDDMFormValuesFilter.filter(_ddmFormValues);

		for (int i = 1; i < _ddmFormFieldValueFilters.size(); i++) {
			DDMFormValuesFilter currentDDMFormValuesFilter =
				_ddmFormFieldValueFilters.get(i);

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

	private final List<DDMFormValuesFilter> _ddmFormFieldValueFilters;
	private final DDMFormValues _ddmFormValues;

}