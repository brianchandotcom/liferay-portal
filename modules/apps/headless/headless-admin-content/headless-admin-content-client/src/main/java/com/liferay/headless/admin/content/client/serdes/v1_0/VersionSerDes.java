/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.content.client.serdes.v1_0;

import com.liferay.headless.admin.content.client.dto.v1_0.Version;
import com.liferay.headless.admin.content.client.json.BaseJSONParser;

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
public class VersionSerDes {

	public static Version toDTO(String json) {
		VersionJSONParser versionJSONParser = new VersionJSONParser();

		return versionJSONParser.parseToDTO(json);
	}

	public static Version[] toDTOs(String json) {
		VersionJSONParser versionJSONParser = new VersionJSONParser();

		return versionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Version version) {
		if (version == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (version.getNumber() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"number\": ");

			sb.append(version.getNumber());
		}

		if (version.getStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append(String.valueOf(version.getStatus()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		VersionJSONParser versionJSONParser = new VersionJSONParser();

		return versionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Version version) {
		if (version == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (version.getNumber() == null) {
			map.put("number", null);
		}
		else {
			map.put("number", String.valueOf(version.getNumber()));
		}

		if (version.getStatus() == null) {
			map.put("status", null);
		}
		else {
			map.put("status", String.valueOf(version.getStatus()));
		}

		return map;
	}

	public static class VersionJSONParser extends BaseJSONParser<Version> {

		@Override
		protected Version createDTO() {
			return new Version();
		}

		@Override
		protected Version[] createDTOArray(int size) {
			return new Version[size];
		}

		@Override
		protected void setField(
			Version version, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "number")) {
				if (jsonParserFieldValue != null) {
					version.setNumber(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					version.setStatus(
						StatusSerDes.toDTO((String)jsonParserFieldValue));
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