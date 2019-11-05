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
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
public class BatchEngineImportTaskItemReaderUtil {

	public static <T> T convertValue(
			Class<T> itemClass, Map<String, Object> fieldNameValueMap)
		throws ReflectiveOperationException {

		T item = itemClass.newInstance();

		for (Map.Entry<String, Object> entry : fieldNameValueMap.entrySet()) {
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

	public static void handleMapField(
		String fieldName, Map<String, Object> fieldNameValueMap,
		int lastDelimiterIndex, String value) {

		String key = fieldName.substring(lastDelimiterIndex + 1);

		fieldName = fieldName.substring(0, lastDelimiterIndex);

		Map<String, String> valueMap =
			(Map<String, String>)fieldNameValueMap.get(fieldName);

		if (valueMap == null) {
			valueMap = new HashMap<>();

			fieldNameValueMap.put(fieldName, valueMap);
		}

		valueMap.put(key, value);
	}

	public static Map<String, Object> mapFieldNames(
		Map<String, ? extends Serializable> fieldNameMappingMap,
		Map<String, Object> fieldNameValueMap) {

		if (fieldNameMappingMap.isEmpty()) {
			return fieldNameValueMap;
		}

		Map<String, Object> targetFieldNameValueMap = new HashMap<>();

		for (Map.Entry<String, Object> entry : fieldNameValueMap.entrySet()) {
			String targetFieldName = (String)fieldNameMappingMap.get(
				entry.getKey());

			if (Validator.isNotNull(targetFieldName)) {
				targetFieldNameValueMap.put(targetFieldName, entry.getValue());
			}
		}

		return targetFieldNameValueMap;
	}

	private static final ObjectMapper _objectMapper = new ObjectMapper();

}