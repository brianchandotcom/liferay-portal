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

package com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0;

import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Index;
import com.liferay.portal.workflow.metrics.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class IndexSerDes {

	public static Index toDTO(String json) {
		IndexJSONParser indexJSONParser = new IndexJSONParser();

		return indexJSONParser.parseToDTO(json);
	}

	public static Index[] toDTOs(String json) {
		IndexJSONParser indexJSONParser = new IndexJSONParser();

		return indexJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Index index) {
		if (index == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (index.getGroup() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"group\": ");

			sb.append("\"");

			sb.append(index.getGroup());

			sb.append("\"");
		}

		if (index.getKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"key\": ");

			sb.append("\"");

			sb.append(_escape(index.getKey()));

			sb.append("\"");
		}

		if (index.getLabel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"label\": ");

			sb.append("\"");

			sb.append(_escape(index.getLabel()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		IndexJSONParser indexJSONParser = new IndexJSONParser();

		return indexJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Index index) {
		if (index == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (index.getGroup() == null) {
			map.put("group", null);
		}
		else {
			map.put("group", String.valueOf(index.getGroup()));
		}

		if (index.getKey() == null) {
			map.put("key", null);
		}
		else {
			map.put("key", String.valueOf(index.getKey()));
		}

		if (index.getLabel() == null) {
			map.put("label", null);
		}
		else {
			map.put("label", String.valueOf(index.getLabel()));
		}

		return map;
	}

	public static class IndexJSONParser extends BaseJSONParser<Index> {

		@Override
		protected Index createDTO() {
			return new Index();
		}

		@Override
		protected Index[] createDTOArray(int size) {
			return new Index[size];
		}

		@Override
		protected void setField(
			Index index, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "group")) {
				if (jsonParserFieldValue != null) {
					index.setGroup(
						Index.Group.create((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "key")) {
				if (jsonParserFieldValue != null) {
					index.setKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "label")) {
				if (jsonParserFieldValue != null) {
					index.setLabel((String)jsonParserFieldValue);
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