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

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.MappedValue;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class MappedValueSerDes {

	public static MappedValue toDTO(String json) {
		MappedValueJSONParser mappedValueJSONParser =
			new MappedValueJSONParser();

		return mappedValueJSONParser.parseToDTO(json);
	}

	public static MappedValue[] toDTOs(String json) {
		MappedValueJSONParser mappedValueJSONParser =
			new MappedValueJSONParser();

		return mappedValueJSONParser.parseToDTOs(json);
	}

	public static String toJSON(MappedValue mappedValue) {
		if (mappedValue == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (mappedValue.getClassNameId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"classNameId\": ");

			sb.append(mappedValue.getClassNameId());
		}

		if (mappedValue.getClassPK() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"classPK\": ");

			sb.append(mappedValue.getClassPK());
		}

		if (mappedValue.getFieldId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fieldId\": ");

			sb.append("\"");

			sb.append(_escape(mappedValue.getFieldId()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		MappedValueJSONParser mappedValueJSONParser =
			new MappedValueJSONParser();

		return mappedValueJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(MappedValue mappedValue) {
		if (mappedValue == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (mappedValue.getClassNameId() == null) {
			map.put("classNameId", null);
		}
		else {
			map.put(
				"classNameId", String.valueOf(mappedValue.getClassNameId()));
		}

		if (mappedValue.getClassPK() == null) {
			map.put("classPK", null);
		}
		else {
			map.put("classPK", String.valueOf(mappedValue.getClassPK()));
		}

		if (mappedValue.getFieldId() == null) {
			map.put("fieldId", null);
		}
		else {
			map.put("fieldId", String.valueOf(mappedValue.getFieldId()));
		}

		return map;
	}

	public static class MappedValueJSONParser
		extends BaseJSONParser<MappedValue> {

		@Override
		protected MappedValue createDTO() {
			return new MappedValue();
		}

		@Override
		protected MappedValue[] createDTOArray(int size) {
			return new MappedValue[size];
		}

		@Override
		protected void setField(
			MappedValue mappedValue, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "classNameId")) {
				if (jsonParserFieldValue != null) {
					mappedValue.setClassNameId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "classPK")) {
				if (jsonParserFieldValue != null) {
					mappedValue.setClassPK(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fieldId")) {
				if (jsonParserFieldValue != null) {
					mappedValue.setFieldId((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\":");

			Object value = entry.getValue();

			Class<?> valueClass = value.getClass();

			if (value instanceof Map) {
				sb.append(_toJSON((Map)value));
			}
			else if (valueClass.isArray()) {
				Object[] values = (Object[])value;

				sb.append("[");

				for (int i = 0; i < values.length; i++) {
					sb.append("\"");
					sb.append(_escape(values[i]));
					sb.append("\"");

					if ((i + 1) < values.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}