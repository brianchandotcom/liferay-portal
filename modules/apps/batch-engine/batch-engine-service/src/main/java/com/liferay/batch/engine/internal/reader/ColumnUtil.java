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

package com.liferay.batch.engine.internal.reader;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.petra.string.StringPool;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
public class ColumnUtil {

	public static Object convertValue(
			Class<?> itemClass, Map<String, Object> columnNameValueMap)
		throws Exception {

		Object item = itemClass.newInstance();

		for (Map.Entry<String, Object> entry : columnNameValueMap.entrySet()) {
			String name = entry.getKey();

			Field field = null;

			try {
				field = itemClass.getDeclaredField(name);
			}
			catch (NoSuchFieldException nsfe) {
				field = itemClass.getDeclaredField(StringPool.UNDERLINE + name);
			}

			field.setAccessible(true);

			field.set(
				item,
				_objectMapper.convertValue(entry.getValue(), field.getType()));
		}

		return item;
	}

	public static void handleLocalizationColumn(
		String columnName, Map<String, Object> columnNameValueMap,
		int lastDelimiterIndex, String value) {

		String languageId = columnName.substring(lastDelimiterIndex + 1);

		columnName = columnName.substring(0, lastDelimiterIndex);

		Map<String, String> localizationMap =
			(Map<String, String>)columnNameValueMap.get(columnName);

		if (localizationMap == null) {
			localizationMap = new HashMap<>();

			columnNameValueMap.put(columnName, localizationMap);
		}

		localizationMap.put(languageId, value);
	}

	private static final ObjectMapper _objectMapper = new ObjectMapper();

}