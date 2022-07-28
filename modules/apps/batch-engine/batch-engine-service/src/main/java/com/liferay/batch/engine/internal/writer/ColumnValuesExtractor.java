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

package com.liferay.batch.engine.internal.writer;

import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.CSVUtil;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Shuyang Zhou
 * @author Igor Beslic
 */
public class ColumnValuesExtractor {

	public ColumnValuesExtractor(
		Map<String, Field> fieldMap, List<String> fieldNames) {

		_unsafeFunctions = new ArrayList<>(fieldNames.size());

		for (String fieldName : fieldNames) {
			_addUnsafeFunction(fieldMap, fieldName);
		}
	}

	public List<Object> extractValues(Object item)
		throws ReflectiveOperationException {

		List<Object> values = new ArrayList<>(_unsafeFunctions.size());

		for (UnsafeFunction<Object, Object, ReflectiveOperationException>
				unsafeFunction : _unsafeFunctions) {

			values.add(unsafeFunction.apply(item));
		}

		return values;
	}

	private void _addUnsafeFunction(
		Map<String, Field> fieldMap, String fieldName) {

		Field field = fieldMap.get(fieldName);

		if (field != null) {
			Class<?> fieldClass = field.getType();

			if (ItemClassIndexUtil.isSingleColumnAdoptableValue(fieldClass)) {
				_unsafeFunctions.add(
					item -> {
						if (field.get(item) == null) {
							return StringPool.BLANK;
						}

						return field.get(item);
					});

				return;
			}

			if (ItemClassIndexUtil.isSingleColumnAdoptableArray(fieldClass)) {
				_unsafeFunctions.add(
					item -> {
						if (field.get(item) == null) {
							return StringPool.BLANK;
						}

						return StringUtil.merge(
							(Object[])field.get(item), CSVUtil::encode,
							StringPool.COMMA);
					});

				return;
			}
		}

		int index = fieldName.indexOf(CharPool.UNDERLINE);

		if (index == -1) {
			Field propertiesField = fieldMap.get("properties");

			if (!ItemClassIndexUtil.isObjectEntryProperties(propertiesField)) {
				throw new IllegalArgumentException(
					"Invalid field name : " + fieldName);
			}

			_unsafeFunctions.add(
				item -> {
					Map<?, ?> map = (Map<?, ?>)propertiesField.get(item);

					Object value = map.get(fieldName);

					if (value == null) {
						return StringPool.BLANK;
					}

					if (ItemClassIndexUtil.isListEntry(value)) {
						return _getListEntryKey(value);
					}

					if (value instanceof String) {
						return CSVUtil.encode(value);
					}

					return value;
				});

			return;
		}

		String prefixFieldName = fieldName.substring(0, index);

		Field mapField = fieldMap.get(prefixFieldName);

		if (mapField == null) {
			throw new IllegalArgumentException(
				"Invalid field name : " + fieldName);
		}

		if (mapField.getType() != Map.class) {
			throw new IllegalArgumentException(
				"Invalid field name : " + fieldName + ", it is not Map type.");
		}

		String key = fieldName.substring(index + 1);

		_unsafeFunctions.add(
			item -> {
				Map<?, ?> map = (Map<?, ?>)mapField.get(item);

				Object value = map.get(key);

				if (value == null) {
					return StringPool.BLANK;
				}

				return value;
			});
	}

	private String _getListEntryKey(Object object) {
		ListEntry listEntry = (ListEntry)object;

		return listEntry.getKey();
	}

	private final List
		<UnsafeFunction<Object, Object, ReflectiveOperationException>>
			_unsafeFunctions;

}