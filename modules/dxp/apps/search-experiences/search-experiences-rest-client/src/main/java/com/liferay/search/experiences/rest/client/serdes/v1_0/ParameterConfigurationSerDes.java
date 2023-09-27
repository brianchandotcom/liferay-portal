/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.rest.client.serdes.v1_0;

import com.liferay.search.experiences.rest.client.dto.v1_0.ParameterConfiguration;
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
public class ParameterConfigurationSerDes {

	public static ParameterConfiguration toDTO(String json) {
		ParameterConfigurationJSONParser parameterConfigurationJSONParser =
			new ParameterConfigurationJSONParser();

		return parameterConfigurationJSONParser.parseToDTO(json);
	}

	public static ParameterConfiguration[] toDTOs(String json) {
		ParameterConfigurationJSONParser parameterConfigurationJSONParser =
			new ParameterConfigurationJSONParser();

		return parameterConfigurationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ParameterConfiguration parameterConfiguration) {
		if (parameterConfiguration == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (parameterConfiguration.getParameters() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"parameters\": ");

			sb.append(_toJSON(parameterConfiguration.getParameters()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ParameterConfigurationJSONParser parameterConfigurationJSONParser =
			new ParameterConfigurationJSONParser();

		return parameterConfigurationJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ParameterConfiguration parameterConfiguration) {

		if (parameterConfiguration == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (parameterConfiguration.getParameters() == null) {
			map.put("parameters", null);
		}
		else {
			map.put(
				"parameters",
				String.valueOf(parameterConfiguration.getParameters()));
		}

		return map;
	}

	public static class ParameterConfigurationJSONParser
		extends BaseJSONParser<ParameterConfiguration> {

		@Override
		protected ParameterConfiguration createDTO() {
			return new ParameterConfiguration();
		}

		@Override
		protected ParameterConfiguration[] createDTOArray(int size) {
			return new ParameterConfiguration[size];
		}

		@Override
		protected void setField(
			ParameterConfiguration parameterConfiguration,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "parameters")) {
				if (jsonParserFieldValue != null) {
					parameterConfiguration.setParameters(
						(Map)ParameterConfigurationSerDes.toMap(
							(String)jsonParserFieldValue));
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