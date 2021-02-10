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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
			Collection<String> entryKeys = _sort(
				_getEntryKeys(
					ddmFormFieldValuesMap, ddmFormField.getName(),
					StringPool.BLANK));

			for (String entryKey : entryKeys) {
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
			Collection<String> entryKeys = _sort(
				_getEntryKeys(
					ddmFormFieldValuesMap, nestedDDMFormField.getName(),
					parentEntryKey));

			for (String entryKey : entryKeys) {
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
		Stream<String> entryKeysStream = entryKeys.stream();

		Map<Integer, String> entryKeysMap = entryKeysStream.collect(
			Collectors.toMap(
				key -> GetterUtil.getInteger(
					DDMFormFieldParameterNameUtil.
						getLastDDMFormFieldParameterNameParts(key)
						[DDMFormFieldParameterNameUtil.
							DDM_FORM_FIELD_INDEX_INDEX]),
				Function.identity()));

		Set<Map.Entry<Integer, String>> set = entryKeysMap.entrySet();

		Stream<Map.Entry<Integer, String>> stream = set.stream();

		entryKeysMap = stream.sorted(
			Map.Entry.comparingByKey()
		).collect(
			Collectors.toMap(
				Map.Entry::getKey, Map.Entry::getValue,
				(oldValue, newValue) -> oldValue, LinkedHashMap::new)
		);

		return entryKeysMap.values();
	}

}