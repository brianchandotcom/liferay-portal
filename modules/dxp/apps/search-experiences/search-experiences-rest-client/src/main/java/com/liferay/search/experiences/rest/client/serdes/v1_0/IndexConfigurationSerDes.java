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

import com.liferay.search.experiences.rest.client.dto.v1_0.IndexConfiguration;
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
public class IndexConfigurationSerDes {

	public static IndexConfiguration toDTO(String json) {
		IndexConfigurationJSONParser indexConfigurationJSONParser =
			new IndexConfigurationJSONParser();

		return indexConfigurationJSONParser.parseToDTO(json);
	}

	public static IndexConfiguration[] toDTOs(String json) {
		IndexConfigurationJSONParser indexConfigurationJSONParser =
			new IndexConfigurationJSONParser();

		return indexConfigurationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(IndexConfiguration indexConfiguration) {
		if (indexConfiguration == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (indexConfiguration.getExternal() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"external\": ");

			sb.append(indexConfiguration.getExternal());
		}

		if (indexConfiguration.getIndexName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"indexName\": ");

			sb.append("\"");

			sb.append(_escape(indexConfiguration.getIndexName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		IndexConfigurationJSONParser indexConfigurationJSONParser =
			new IndexConfigurationJSONParser();

		return indexConfigurationJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		IndexConfiguration indexConfiguration) {

		if (indexConfiguration == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (indexConfiguration.getExternal() == null) {
			map.put("external", null);
		}
		else {
			map.put(
				"external", String.valueOf(indexConfiguration.getExternal()));
		}

		if (indexConfiguration.getIndexName() == null) {
			map.put("indexName", null);
		}
		else {
			map.put(
				"indexName", String.valueOf(indexConfiguration.getIndexName()));
		}

		return map;
	}

	public static class IndexConfigurationJSONParser
		extends BaseJSONParser<IndexConfiguration> {

		@Override
		protected IndexConfiguration createDTO() {
			return new IndexConfiguration();
		}

		@Override
		protected IndexConfiguration[] createDTOArray(int size) {
			return new IndexConfiguration[size];
		}

		@Override
		protected void setField(
			IndexConfiguration indexConfiguration, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "external")) {
				if (jsonParserFieldValue != null) {
					indexConfiguration.setExternal(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "indexName")) {
				if (jsonParserFieldValue != null) {
					indexConfiguration.setIndexName(
						(String)jsonParserFieldValue);
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