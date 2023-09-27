/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.rest.client.serdes.v1_0;

import com.liferay.search.experiences.rest.client.dto.v1_0.FieldMappingInfo;
import com.liferay.search.experiences.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class FieldMappingInfoSerDes {

	public static FieldMappingInfo toDTO(String json) {
		FieldMappingInfoJSONParser fieldMappingInfoJSONParser =
			new FieldMappingInfoJSONParser();

		return fieldMappingInfoJSONParser.parseToDTO(json);
	}

	public static FieldMappingInfo[] toDTOs(String json) {
		FieldMappingInfoJSONParser fieldMappingInfoJSONParser =
			new FieldMappingInfoJSONParser();

		return fieldMappingInfoJSONParser.parseToDTOs(json);
	}

	public static String toJSON(FieldMappingInfo fieldMappingInfo) {
		if (fieldMappingInfo == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (fieldMappingInfo.getLanguageIdPosition() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"languageIdPosition\": ");

			sb.append(fieldMappingInfo.getLanguageIdPosition());
		}

		if (fieldMappingInfo.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(fieldMappingInfo.getName()));

			sb.append("\"");
		}

		if (fieldMappingInfo.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(_escape(fieldMappingInfo.getType()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FieldMappingInfoJSONParser fieldMappingInfoJSONParser =
			new FieldMappingInfoJSONParser();

		return fieldMappingInfoJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(FieldMappingInfo fieldMappingInfo) {
		if (fieldMappingInfo == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (fieldMappingInfo.getLanguageIdPosition() == null) {
			map.put("languageIdPosition", null);
		}
		else {
			map.put(
				"languageIdPosition",
				String.valueOf(fieldMappingInfo.getLanguageIdPosition()));
		}

		if (fieldMappingInfo.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(fieldMappingInfo.getName()));
		}

		if (fieldMappingInfo.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(fieldMappingInfo.getType()));
		}

		return map;
	}

	public static class FieldMappingInfoJSONParser
		extends BaseJSONParser<FieldMappingInfo> {

		@Override
		protected FieldMappingInfo createDTO() {
			return new FieldMappingInfo();
		}

		@Override
		protected FieldMappingInfo[] createDTOArray(int size) {
			return new FieldMappingInfo[size];
		}

		@Override
		protected void setField(
			FieldMappingInfo fieldMappingInfo, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "languageIdPosition")) {
				if (jsonParserFieldValue != null) {
					fieldMappingInfo.setLanguageIdPosition(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					fieldMappingInfo.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					fieldMappingInfo.setType((String)jsonParserFieldValue);
				}
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
			sb.append("\": ");

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
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}