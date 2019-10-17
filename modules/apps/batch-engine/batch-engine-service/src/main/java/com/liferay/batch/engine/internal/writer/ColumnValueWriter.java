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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;

import java.lang.reflect.Field;

import java.math.BigDecimal;
import java.math.BigInteger;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Ivica cardic
 */
public class ColumnValueWriter {

	public void write(Object item, Consumer<List<?>> consumer)
		throws IllegalAccessException {

		Map<String, Object> columnNameValueMap = _getColumnNameValueMap(item);

		Set<Map.Entry<String, Object>> entries = columnNameValueMap.entrySet();

		Stream<Map.Entry<String, Object>> stream = entries.stream();

		columnNameValueMap = stream.filter(
			entry -> _isFieldEligibleForWrite(item, entry.getKey())
		).flatMap(
			this::_getEntryStreams
		).collect(
			Collectors.toMap(
				Map.Entry::getKey,
				entry -> {
					if (entry.getValue() == null) {
						return StringPool.BLANK;
					}

					return entry.getValue();
				})
		);

		List<String> columnNames = new ArrayList<>(columnNameValueMap.keySet());

		columnNames.sort(Comparator.naturalOrder());

		if (!_firstLineWritten) {
			consumer.accept(columnNames);

			_firstLineWritten = true;
		}

		Set<Map.Entry<String, Object>> entrySet = columnNameValueMap.entrySet();

		Stream<Map.Entry<String, Object>> entryStream = entrySet.stream();

		List<Object> values = entryStream.sorted(
			Comparator.comparing(Map.Entry::getKey)
		).map(
			Map.Entry::getValue
		).collect(
			Collectors.toList()
		);

		consumer.accept(values);
	}

	private Map<String, Object> _getColumnNameValueMap(Object item)
		throws IllegalAccessException {

		Map<String, Object> columnNameValueMap = new HashMap<>();

		Class<?> itemClass = item.getClass();

		Field[] fields = itemClass.getDeclaredFields();

		for (Field field : fields) {
			field.setAccessible(true);

			String name = field.getName();

			if (name.charAt(0) == CharPool.UNDERLINE) {
				name = name.substring(1);
			}

			columnNameValueMap.put(name, field.get(item));
		}

		return columnNameValueMap;
	}

	private String _getEntryKey(String entryKey, String entryValueEntryKey) {
		return entryKey + StringPool.UNDERLINE + entryValueEntryKey;
	}

	private Stream<Map.Entry<String, Object>> _getEntryStreams(
		Map.Entry<String, Object> entry) {

		Object value = entry.getValue();

		if (value instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<String, Object> entryValueMap = (Map)value;

			@SuppressWarnings("unchecked")
			AbstractMap.SimpleImmutableEntry<String, Object>[]
				entryValueEntries =
					new AbstractMap.SimpleImmutableEntry[entryValueMap.size()];

			int index = 0;

			for (Map.Entry<String, Object> entryValueEntry :
					entryValueMap.entrySet()) {

				entryValueEntries[index++] =
					new AbstractMap.SimpleImmutableEntry<>(
						_getEntryKey(entry.getKey(), entryValueEntry.getKey()),
						entryValueEntry.getValue());
			}

			return Stream.of(entryValueEntries);
		}

		return Stream.of(entry);
	}

	private boolean _isFieldEligibleForWrite(Object item, String name) {
		Class<?> itemClass = item.getClass();

		Field field = null;

		try {
			field = itemClass.getDeclaredField(StringPool.UNDERLINE + name);
		}
		catch (NoSuchFieldException nfe) {
			try {
				itemClass.getDeclaredField(name);
			}
			catch (NoSuchFieldException nfe2) {
				throw new RuntimeException(nfe2);
			}
		}

		Class<?> valueClass = field.getType();

		if (valueClass.isPrimitive()) {
			return true;
		}

		if (_objectTypes.contains(valueClass)) {
			return true;
		}

		return false;
	}

	private static final List<Class<?>> _objectTypes = Arrays.asList(
		Boolean.class, BigDecimal.class, BigInteger.class, Byte.class,
		Date.class, Double.class, Float.class, Integer.class, Long.class,
		Map.class, String.class);

	private boolean _firstLineWritten;

}