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

import com.liferay.search.experiences.rest.client.dto.v1_0.Highlight;
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
public class HighlightSerDes {

	public static Highlight toDTO(String json) {
		HighlightJSONParser highlightJSONParser = new HighlightJSONParser();

		return highlightJSONParser.parseToDTO(json);
	}

	public static Highlight[] toDTOs(String json) {
		HighlightJSONParser highlightJSONParser = new HighlightJSONParser();

		return highlightJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Highlight highlight) {
		if (highlight == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (highlight.getFields() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fields\": ");

			sb.append(_toJSON(highlight.getFields()));
		}

		if (highlight.getFragment_size() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragment_size\": ");

			sb.append(highlight.getFragment_size());
		}

		if (highlight.getNumber_of_fragments() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"number_of_fragments\": ");

			sb.append(highlight.getNumber_of_fragments());
		}

		if (highlight.getPost_tags() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"post_tags\": ");

			sb.append("[");

			for (int i = 0; i < highlight.getPost_tags().length; i++) {
				sb.append("\"");

				sb.append(_escape(highlight.getPost_tags()[i]));

				sb.append("\"");

				if ((i + 1) < highlight.getPost_tags().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (highlight.getPre_tags() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"pre_tags\": ");

			sb.append("[");

			for (int i = 0; i < highlight.getPre_tags().length; i++) {
				sb.append("\"");

				sb.append(_escape(highlight.getPre_tags()[i]));

				sb.append("\"");

				if ((i + 1) < highlight.getPre_tags().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (highlight.getRequire_field_match() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"require_field_match\": ");

			sb.append(highlight.getRequire_field_match());
		}

		if (highlight.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(_escape(highlight.getType()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		HighlightJSONParser highlightJSONParser = new HighlightJSONParser();

		return highlightJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Highlight highlight) {
		if (highlight == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (highlight.getFields() == null) {
			map.put("fields", null);
		}
		else {
			map.put("fields", String.valueOf(highlight.getFields()));
		}

		if (highlight.getFragment_size() == null) {
			map.put("fragment_size", null);
		}
		else {
			map.put(
				"fragment_size", String.valueOf(highlight.getFragment_size()));
		}

		if (highlight.getNumber_of_fragments() == null) {
			map.put("number_of_fragments", null);
		}
		else {
			map.put(
				"number_of_fragments",
				String.valueOf(highlight.getNumber_of_fragments()));
		}

		if (highlight.getPost_tags() == null) {
			map.put("post_tags", null);
		}
		else {
			map.put("post_tags", String.valueOf(highlight.getPost_tags()));
		}

		if (highlight.getPre_tags() == null) {
			map.put("pre_tags", null);
		}
		else {
			map.put("pre_tags", String.valueOf(highlight.getPre_tags()));
		}

		if (highlight.getRequire_field_match() == null) {
			map.put("require_field_match", null);
		}
		else {
			map.put(
				"require_field_match",
				String.valueOf(highlight.getRequire_field_match()));
		}

		if (highlight.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(highlight.getType()));
		}

		return map;
	}

	public static class HighlightJSONParser extends BaseJSONParser<Highlight> {

		@Override
		protected Highlight createDTO() {
			return new Highlight();
		}

		@Override
		protected Highlight[] createDTOArray(int size) {
			return new Highlight[size];
		}

		@Override
		protected void setField(
			Highlight highlight, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "fields")) {
				if (jsonParserFieldValue != null) {
					highlight.setFields(
						(Map)HighlightSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fragment_size")) {
				if (jsonParserFieldValue != null) {
					highlight.setFragment_size(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "number_of_fragments")) {

				if (jsonParserFieldValue != null) {
					highlight.setNumber_of_fragments(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "post_tags")) {
				if (jsonParserFieldValue != null) {
					highlight.setPost_tags(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pre_tags")) {
				if (jsonParserFieldValue != null) {
					highlight.setPre_tags(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "require_field_match")) {

				if (jsonParserFieldValue != null) {
					highlight.setRequire_field_match(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					highlight.setType((String)jsonParserFieldValue);
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