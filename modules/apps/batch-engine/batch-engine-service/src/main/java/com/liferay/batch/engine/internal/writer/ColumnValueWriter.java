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

import com.liferay.petra.concurrent.ConcurrentReferenceKeyHashMap;
import com.liferay.petra.concurrent.ConcurrentReferenceValueHashMap;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;

import java.lang.ref.Reference;
import java.lang.reflect.Field;

import java.math.BigDecimal;
import java.math.BigInteger;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

/**
 * @author Ivica cardic
 */
public class ColumnValueWriter {

	public void write(Object item, Consumer<Collection<?>> consumer)
		throws IllegalAccessException {

		Map<String, Object> columnNameValueMap = _getColumnNameValueMap(item);

		if (!_firstLineWritten) {
			consumer.accept(columnNameValueMap.keySet());

			_firstLineWritten = true;
		}

		consumer.accept(columnNameValueMap.values());
	}

	private Map<String, Object> _getColumnNameValueMap(Object item)
		throws IllegalAccessException {

		Map<String, Field> fields = _fieldsMap.computeIfAbsent(
			item.getClass(),
			clazz -> {
				Map<String, Field> fieldMap = new HashMap<>();

				for (Field field : clazz.getDeclaredFields()) {
					Class<?> valueClass = field.getType();

					if (!valueClass.isPrimitive() &&
						!_objectTypes.contains(valueClass)) {

						continue;
					}

					field.setAccessible(true);

					String name = field.getName();

					if (name.charAt(0) == CharPool.UNDERLINE) {
						name = name.substring(1);
					}

					fieldMap.put(name, field);
				}

				return fieldMap;
			});

		Map<String, Object> columnNameValueMap = new TreeMap<>();

		for (Map.Entry<String, Field> entry : fields.entrySet()) {
			String key = entry.getKey();

			Field field = entry.getValue();

			Object value = field.get(item);

			if (value instanceof Map) {
				Map<String, Object> valueMap = (Map<String, Object>)value;

				if (valueMap.isEmpty()) {
					continue;
				}

				key = key.concat(StringPool.UNDERLINE);

				for (Map.Entry<String, Object> valueEntry :
						valueMap.entrySet()) {

					Object innerValue = valueEntry.getValue();

					if (innerValue == null) {
						innerValue = StringPool.BLANK;
					}

					columnNameValueMap.put(
						key.concat(valueEntry.getKey()), innerValue);
				}
			}
			else {
				if (value == null) {
					value = StringPool.BLANK;
				}

				columnNameValueMap.put(key, value);
			}
		}

		return columnNameValueMap;
	}

	private static final Map<Class<?>, Map<String, Field>> _fieldsMap =
		new ConcurrentReferenceKeyHashMap<>(
			new ConcurrentReferenceValueHashMap
				<Reference<Class<?>>, Map<String, Field>>(
					FinalizeManager.WEAK_REFERENCE_FACTORY),
			FinalizeManager.WEAK_REFERENCE_FACTORY);
	private static final List<Class<?>> _objectTypes = Arrays.asList(
		Boolean.class, BigDecimal.class, BigInteger.class, Byte.class,
		Date.class, Double.class, Float.class, Integer.class, Long.class,
		Map.class, String.class);

	private boolean _firstLineWritten;

}