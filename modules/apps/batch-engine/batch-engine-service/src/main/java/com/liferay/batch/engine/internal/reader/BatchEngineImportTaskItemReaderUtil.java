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

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

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

			for (Field declaredField : itemClass.getDeclaredFields()) {
				if (name.equals(declaredField.getName()) ||
					Objects.equals(
						StringPool.UNDERLINE + name, declaredField.getName())) {

					field = declaredField;

					break;
				}
			}

			if (field != null) {
				field.setAccessible(true);

				field.set(
					item,
					_objectMapper.convertValue(
						entry.getValue(), field.getType()));

				continue;
			}

			for (Field declaredField : itemClass.getDeclaredFields()) {
				JsonAnySetter[] jsonAnySetters =
					declaredField.getAnnotationsByType(JsonAnySetter.class);

				if (jsonAnySetters.length > 0) {
					field = declaredField;

					break;
				}
			}

			if (field == null) {
				throw new NoSuchFieldException(entry.getKey());
			}

			field.setAccessible(true);

			Map<String, Object> map = (Map)field.get(item);

			map.put(entry.getKey(), entry.getValue());
		}

		return item;
	}

	public static Map<String, Object> mapFieldNames(
		Map<String, ? extends Serializable> fieldNameMappingMap,
		Map<String, Object> fieldNameValueMap) {

		if ((fieldNameMappingMap == null) || fieldNameMappingMap.isEmpty()) {
			return fieldNameValueMap;
		}

		Map<String, Object> targetFieldNameValueMap = new HashMap<>();

		for (Map.Entry<String, Object> entry : fieldNameValueMap.entrySet()) {
			String targetFieldName = (String)fieldNameMappingMap.get(
				entry.getKey());

			if (Validator.isNotNull(targetFieldName)) {
				Object object = targetFieldNameValueMap.get(targetFieldName);

				if ((object != null) && (object instanceof Map)) {
					Map<?, ?> map = (Map)object;

					map.putAll((Map)entry.getValue());
				}
				else {
					targetFieldNameValueMap.put(
						targetFieldName, entry.getValue());
				}
			}
		}

		return targetFieldNameValueMap;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchEngineImportTaskItemReaderUtil.class);

	private static final ObjectMapper _objectMapper = new ObjectMapper() {
		{
			SimpleModule simpleModule = new SimpleModule();

			simpleModule.addDeserializer(Map.class, new MapStdDeserializer());

			registerModule(simpleModule);
		}
	};

	private static class MapStdDeserializer
		extends StdDeserializer<Map<String, Object>> {

		public MapStdDeserializer() {
			this(Map.class);
		}

		@Override
		public Map<String, Object> deserialize(
				JsonParser jsonParser,
				DeserializationContext deserializationContext)
			throws IOException {

			try {
				return deserializationContext.readValue(
					jsonParser, LinkedHashMap.class);
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}

				Map<String, Object> map = new LinkedHashMap<>();

				String string = jsonParser.getValueAsString();

				for (String line : string.split(StringPool.RETURN_NEW_LINE)) {
					String[] lineParts = line.split(StringPool.COLON);

					map.put(lineParts[0], lineParts[1]);
				}

				return map;
			}
		}

		protected MapStdDeserializer(Class<?> clazz) {
			super(clazz);
		}

	}

}