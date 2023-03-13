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

import com.liferay.dynamic.data.mapping.form.renderer.constants.DDMFormRendererConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Marcos Martins
 * @author Rodrigo Paulino
 */
public class DDMFormValuesFactoryUtil {

	public static List<DDMFormFieldValue> getDDMFormFieldValues(
		Map<String, DDMFormFieldValue> ddmFormFieldValuesMap,
		List<DDMFormField> ddmFormFields) {

		int index = 0;

		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		for (DDMFormField ddmFormField : ddmFormFields) {
			for (String entryKey :
					_sort(
						_getEntryKeys(
							ddmFormFieldValuesMap, ddmFormField.getName(),
							StringPool.BLANK))) {

				DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValuesMap.get(
					entryKey);

				_setNestedDDMFormFieldValues(
					ddmFormFieldValuesMap,
					ddmFormField.getNestedDDMFormFields(), ddmFormFieldValue,
					entryKey);

				_setDDMFormFieldValueAtIndex(
					ddmFormFieldValues, ddmFormFieldValue, index++);
			}
		}

		return ddmFormFieldValues;
	}

	private static String _getEntryKeyPrefix(
		String parentEntryKey, String fieldNameFilter) {

		if (Validator.isNull(parentEntryKey)) {
			return StringPool.BLANK;
		}

		return StringBundler.concat(
			parentEntryKey, DDMFormRendererConstants.DDM_FORM_FIELDS_SEPARATOR,
			fieldNameFilter);
	}

	private static Set<String> _getEntryKeys(
		Map<String, DDMFormFieldValue> ddmFormFieldValuesMap, String fieldName,
		String parentEntryKey) {

		Set<String> entryKeys = new HashSet<>();

		for (Map.Entry<String, DDMFormFieldValue> entry :
				ddmFormFieldValuesMap.entrySet()) {

			DDMFormFieldValue ddmFormFieldValue = entry.getValue();

			String key = entry.getKey();

			if ((key.startsWith(
					_getEntryKeyPrefix(parentEntryKey, fieldName)) ||
				 key.startsWith(
					 fieldName +
						 DDMFormRendererConstants.
							 DDM_FORM_FIELD_PARTS_SEPARATOR)) &&
				Objects.equals(ddmFormFieldValue.getName(), fieldName)) {

				entryKeys.add(key);
			}
		}

		return entryKeys;
	}

	private static void _setDDMFormFieldValueAtIndex(
		List<DDMFormFieldValue> ddmFormFieldValues,
		DDMFormFieldValue ddmFormFieldValue, int index) {

		if (ddmFormFieldValues.size() < (index + 1)) {
			for (int i = ddmFormFieldValues.size(); i <= index; i++) {
				ddmFormFieldValues.add(new DDMFormFieldValue());
			}
		}

		ddmFormFieldValues.set(index, ddmFormFieldValue);
	}

	private static void _setNestedDDMFormFieldValues(
		Map<String, DDMFormFieldValue> ddmFormFieldValuesMap,
		List<DDMFormField> nestedDDMFormFields,
		DDMFormFieldValue parentDDMFormFieldValue, String parentEntryKey) {

		int index = 0;

		for (DDMFormField nestedDDMFormField : nestedDDMFormFields) {
			for (String entryKey :
					_sort(
						_getEntryKeys(
							ddmFormFieldValuesMap, nestedDDMFormField.getName(),
							parentEntryKey))) {

				DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValuesMap.get(
					entryKey);

				_setNestedDDMFormFieldValues(
					ddmFormFieldValuesMap,
					nestedDDMFormField.getNestedDDMFormFields(),
					ddmFormFieldValue, entryKey);

				_setDDMFormFieldValueAtIndex(
					parentDDMFormFieldValue.getNestedDDMFormFieldValues(),
					ddmFormFieldValue, index++);
			}
		}
	}

	private static Collection<String> _sort(Set<String> entryKeys) {
		Map<Integer, String> entryKeysMap = new TreeMap<>();

		for (String key : entryKeys) {
			entryKeysMap.put(
				GetterUtil.getInteger(
					DDMFormFieldParameterNameUtil.
						getLastDDMFormFieldParameterNameParts(key)
						[DDMFormFieldParameterNameUtil.
							DDM_FORM_FIELD_INDEX_INDEX]),
				key);
		}

		return entryKeysMap.values();
	}

}