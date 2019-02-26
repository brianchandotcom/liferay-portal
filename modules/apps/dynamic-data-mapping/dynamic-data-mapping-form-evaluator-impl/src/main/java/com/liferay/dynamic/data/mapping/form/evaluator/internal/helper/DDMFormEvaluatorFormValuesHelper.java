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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.helper;

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorFieldContextKey;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Rafael Praxedes
 */
public class DDMFormEvaluatorFormValuesHelper {

	public DDMFormEvaluatorFormValuesHelper(DDMFormValues ddmFormValues) {
		createDDMFormFieldValuesMap(ddmFormValues);
	}

	public Set<DDMFormEvaluatorFieldContextKey> getDDMFormFieldContextKeySet(
		String fieldName) {

		Map<DDMFormEvaluatorFieldContextKey, DDMFormFieldValue> map =
			_ddmFormFieldValuesMap.getOrDefault(
				fieldName, Collections.emptyMap());

		return map.keySet();
	}

	public DDMFormFieldValue getDDMFormFieldValue(
		DDMFormEvaluatorFieldContextKey fieldContextKey) {

		Map<DDMFormEvaluatorFieldContextKey, DDMFormFieldValue> map =
			_ddmFormFieldValuesMap.getOrDefault(
				fieldContextKey.getName(), Collections.emptyMap());

		return map.get(fieldContextKey);
	}

	protected void createDDMFormFieldValuesMap(DDMFormValues ddmFormValues) {
		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			populateDDMFormFieldValues(ddmFormFieldValue);
		}
	}

	protected void populateDDMFormFieldValues(
		DDMFormFieldValue ddmFormFieldValue) {

		Map<DDMFormEvaluatorFieldContextKey, DDMFormFieldValue>
			ddmFormFieldValuesMap = _ddmFormFieldValuesMap.get(
				ddmFormFieldValue.getName());

		if (ddmFormFieldValuesMap == null) {
			ddmFormFieldValuesMap = new HashMap<>();

			_ddmFormFieldValuesMap.put(
				ddmFormFieldValue.getName(), ddmFormFieldValuesMap);
		}

		ddmFormFieldValuesMap.put(
			new DDMFormEvaluatorFieldContextKey(
				ddmFormFieldValue.getName(), ddmFormFieldValue.getInstanceId()),
			ddmFormFieldValue);

		for (DDMFormFieldValue nestedDDMFormFieldValue :
				ddmFormFieldValue.getNestedDDMFormFieldValues()) {

			populateDDMFormFieldValues(nestedDDMFormFieldValue);
		}
	}

	private final Map
		<String, Map<DDMFormEvaluatorFieldContextKey, DDMFormFieldValue>>
			_ddmFormFieldValuesMap = new HashMap<>();

}