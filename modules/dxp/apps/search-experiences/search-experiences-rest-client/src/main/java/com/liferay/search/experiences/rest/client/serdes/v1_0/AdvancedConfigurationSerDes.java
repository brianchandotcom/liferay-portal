/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.rest.client.serdes.v1_0;

import com.liferay.search.experiences.rest.client.dto.v1_0.AdvancedConfiguration;
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
public class AdvancedConfigurationSerDes {

	public static AdvancedConfiguration toDTO(String json) {
		AdvancedConfigurationJSONParser advancedConfigurationJSONParser =
			new AdvancedConfigurationJSONParser();

		return advancedConfigurationJSONParser.parseToDTO(json);
	}

	public static AdvancedConfiguration[] toDTOs(String json) {
		AdvancedConfigurationJSONParser advancedConfigurationJSONParser =
			new AdvancedConfigurationJSONParser();

		return advancedConfigurationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(AdvancedConfiguration advancedConfiguration) {
		if (advancedConfiguration == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (advancedConfiguration.getSource() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"source\": ");

			sb.append(String.valueOf(advancedConfiguration.getSource()));
		}

		if (advancedConfiguration.getStored_fields() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"stored_fields\": ");

			sb.append("[");

			for (int i = 0; i < advancedConfiguration.getStored_fields().length;
				 i++) {

				sb.append("\"");

				sb.append(_escape(advancedConfiguration.getStored_fields()[i]));

				sb.append("\"");

				if ((i + 1) < advancedConfiguration.getStored_fields().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AdvancedConfigurationJSONParser advancedConfigurationJSONParser =
			new AdvancedConfigurationJSONParser();

		return advancedConfigurationJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		AdvancedConfiguration advancedConfiguration) {

		if (advancedConfiguration == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (advancedConfiguration.getSource() == null) {
			map.put("source", null);
		}
		else {
			map.put(
				"source", String.valueOf(advancedConfiguration.getSource()));
		}

		if (advancedConfiguration.getStored_fields() == null) {
			map.put("stored_fields", null);
		}
		else {
			map.put(
				"stored_fields",
				String.valueOf(advancedConfiguration.getStored_fields()));
		}

		return map;
	}

	public static class AdvancedConfigurationJSONParser
		extends BaseJSONParser<AdvancedConfiguration> {

		@Override
		protected AdvancedConfiguration createDTO() {
			return new AdvancedConfiguration();
		}

		@Override
		protected AdvancedConfiguration[] createDTOArray(int size) {
			return new AdvancedConfiguration[size];
		}

		@Override
		protected void setField(
			AdvancedConfiguration advancedConfiguration,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "source")) {
				if (jsonParserFieldValue != null) {
					advancedConfiguration.setSource(
						SourceSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "stored_fields")) {
				if (jsonParserFieldValue != null) {
					advancedConfiguration.setStored_fields(
						toStrings((Object[])jsonParserFieldValue));
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