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

import com.liferay.headless.delivery.client.dto.v1_0.ClassPKReference;
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
public class ClassPKReferenceSerDes {

	public static ClassPKReference toDTO(String json) {
		ClassPKReferenceJSONParser classPKReferenceJSONParser =
			new ClassPKReferenceJSONParser();

		return classPKReferenceJSONParser.parseToDTO(json);
	}

	public static ClassPKReference[] toDTOs(String json) {
		ClassPKReferenceJSONParser classPKReferenceJSONParser =
			new ClassPKReferenceJSONParser();

		return classPKReferenceJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ClassPKReference classPKReference) {
		if (classPKReference == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (classPKReference.getClassName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"className\": ");

			sb.append("\"");

			sb.append(_escape(classPKReference.getClassName()));

			sb.append("\"");
		}

		if (classPKReference.getClassPK() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"classPK\": ");

			sb.append(classPKReference.getClassPK());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ClassPKReferenceJSONParser classPKReferenceJSONParser =
			new ClassPKReferenceJSONParser();

		return classPKReferenceJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ClassPKReference classPKReference) {
		if (classPKReference == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (classPKReference.getClassName() == null) {
			map.put("className", null);
		}
		else {
			map.put(
				"className", String.valueOf(classPKReference.getClassName()));
		}

		if (classPKReference.getClassPK() == null) {
			map.put("classPK", null);
		}
		else {
			map.put("classPK", String.valueOf(classPKReference.getClassPK()));
		}

		return map;
	}

	public static class ClassPKReferenceJSONParser
		extends BaseJSONParser<ClassPKReference> {

		@Override
		protected ClassPKReference createDTO() {
			return new ClassPKReference();
		}

		@Override
		protected ClassPKReference[] createDTOArray(int size) {
			return new ClassPKReference[size];
		}

		@Override
		protected void setField(
			ClassPKReference classPKReference, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "className")) {
				if (jsonParserFieldValue != null) {
					classPKReference.setClassName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "classPK")) {
				if (jsonParserFieldValue != null) {
					classPKReference.setClassPK(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (jsonParserFieldName.equals("status")) {
				throw new IllegalArgumentException();
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
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}