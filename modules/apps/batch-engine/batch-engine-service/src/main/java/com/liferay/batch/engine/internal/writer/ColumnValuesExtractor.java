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

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class ColumnValuesExtractor {

	public ColumnValuesExtractor(
		Map<String, Field> fieldMap, List<String> fieldNames) {

		List<UnsafeFunction<Object, Object, ReflectiveOperationException>>
			unsafeFunctions = new ArrayList<>(fieldNames.size());

		for (String fieldName : fieldNames) {
			Field field = fieldMap.get(fieldName);

			if (field != null) {
				unsafeFunctions.add(
					item -> {
						Object value = field.get(item);

						if (value == null) {
							return StringPool.BLANK;
						}

						return value;
					});

				continue;
			}

			int index = fieldName.indexOf(CharPool.UNDERLINE);

			if (index == -1) {
				throw new IllegalArgumentException(
					"Invalid field name : " + fieldName);
			}

			String prefixFieldName = fieldName.substring(0, index);

			Field mapField = fieldMap.get(prefixFieldName);

			if (mapField == null) {
				throw new IllegalArgumentException(
					"Invalid field name : " + fieldName);
			}

			if (mapField.getType() != Map.class) {
				throw new IllegalArgumentException(
					"Invalid field name : " + fieldName +
						", it is not Map type.");
			}

			String key = fieldName.substring(index + 1);

			unsafeFunctions.add(
				item -> {
					Map<?, ?> map = (Map<?, ?>)mapField.get(item);

					Object value = map.get(key);

					if (value == null) {
						return StringPool.BLANK;
					}

					return value;
				});
		}

		_unsafeFunctions = unsafeFunctions;
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

	private final List
		<UnsafeFunction<Object, Object, ReflectiveOperationException>>
			_unsafeFunctions;

}