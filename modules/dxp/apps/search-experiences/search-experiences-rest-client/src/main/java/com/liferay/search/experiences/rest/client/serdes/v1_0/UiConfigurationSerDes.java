/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.rest.client.serdes.v1_0;

import com.liferay.search.experiences.rest.client.dto.v1_0.FieldSet;
import com.liferay.search.experiences.rest.client.dto.v1_0.UiConfiguration;
import com.liferay.search.experiences.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class UiConfigurationSerDes {

	public static UiConfiguration toDTO(String json) {
		UiConfigurationJSONParser uiConfigurationJSONParser =
			new UiConfigurationJSONParser();

		return uiConfigurationJSONParser.parseToDTO(json);
	}

	public static UiConfiguration[] toDTOs(String json) {
		UiConfigurationJSONParser uiConfigurationJSONParser =
			new UiConfigurationJSONParser();

		return uiConfigurationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(UiConfiguration uiConfiguration) {
		if (uiConfiguration == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (uiConfiguration.getFieldSets() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fieldSets\": ");

			sb.append("[");

			for (int i = 0; i < uiConfiguration.getFieldSets().length; i++) {
				sb.append(String.valueOf(uiConfiguration.getFieldSets()[i]));

				if ((i + 1) < uiConfiguration.getFieldSets().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		UiConfigurationJSONParser uiConfigurationJSONParser =
			new UiConfigurationJSONParser();

		return uiConfigurationJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(UiConfiguration uiConfiguration) {
		if (uiConfiguration == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (uiConfiguration.getFieldSets() == null) {
			map.put("fieldSets", null);
		}
		else {
			map.put(
				"fieldSets", String.valueOf(uiConfiguration.getFieldSets()));
		}

		return map;
	}

	public static class UiConfigurationJSONParser
		extends BaseJSONParser<UiConfiguration> {

		@Override
		protected UiConfiguration createDTO() {
			return new UiConfiguration();
		}

		@Override
		protected UiConfiguration[] createDTOArray(int size) {
			return new UiConfiguration[size];
		}

		@Override
		protected void setField(
			UiConfiguration uiConfiguration, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "fieldSets")) {
				if (jsonParserFieldValue != null) {
					uiConfiguration.setFieldSets(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> FieldSetSerDes.toDTO((String)object)
						).toArray(
							size -> new FieldSet[size]
						));
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