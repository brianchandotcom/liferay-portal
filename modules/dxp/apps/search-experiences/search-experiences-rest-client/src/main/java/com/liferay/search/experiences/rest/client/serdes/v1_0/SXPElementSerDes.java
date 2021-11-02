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

import com.liferay.search.experiences.rest.client.dto.v1_0.SXPElement;
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
public class SXPElementSerDes {

	public static SXPElement toDTO(String json) {
		SXPElementJSONParser sxpElementJSONParser = new SXPElementJSONParser();

		return sxpElementJSONParser.parseToDTO(json);
	}

	public static SXPElement[] toDTOs(String json) {
		SXPElementJSONParser sxpElementJSONParser = new SXPElementJSONParser();

		return sxpElementJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SXPElement sxpElement) {
		if (sxpElement == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (sxpElement.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(sxpElement.getDescription()));

			sb.append("\"");
		}

		if (sxpElement.getDescription_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description_i18n\": ");

			sb.append(_toJSON(sxpElement.getDescription_i18n()));
		}

		if (sxpElement.getElementDefinition() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"elementDefinition\": ");

			sb.append(String.valueOf(sxpElement.getElementDefinition()));
		}

		if (sxpElement.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(sxpElement.getId());
		}

		if (sxpElement.getTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			sb.append("\"");

			sb.append(_escape(sxpElement.getTitle()));

			sb.append("\"");
		}

		if (sxpElement.getTitle_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title_i18n\": ");

			sb.append(_toJSON(sxpElement.getTitle_i18n()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SXPElementJSONParser sxpElementJSONParser = new SXPElementJSONParser();

		return sxpElementJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(SXPElement sxpElement) {
		if (sxpElement == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (sxpElement.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put("description", String.valueOf(sxpElement.getDescription()));
		}

		if (sxpElement.getDescription_i18n() == null) {
			map.put("description_i18n", null);
		}
		else {
			map.put(
				"description_i18n",
				String.valueOf(sxpElement.getDescription_i18n()));
		}

		if (sxpElement.getElementDefinition() == null) {
			map.put("elementDefinition", null);
		}
		else {
			map.put(
				"elementDefinition",
				String.valueOf(sxpElement.getElementDefinition()));
		}

		if (sxpElement.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(sxpElement.getId()));
		}

		if (sxpElement.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put("title", String.valueOf(sxpElement.getTitle()));
		}

		if (sxpElement.getTitle_i18n() == null) {
			map.put("title_i18n", null);
		}
		else {
			map.put("title_i18n", String.valueOf(sxpElement.getTitle_i18n()));
		}

		return map;
	}

	public static class SXPElementJSONParser
		extends BaseJSONParser<SXPElement> {

		@Override
		protected SXPElement createDTO() {
			return new SXPElement();
		}

		@Override
		protected SXPElement[] createDTOArray(int size) {
			return new SXPElement[size];
		}

		@Override
		protected void setField(
			SXPElement sxpElement, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					sxpElement.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description_i18n")) {
				if (jsonParserFieldValue != null) {
					sxpElement.setDescription_i18n(
						(Map)SXPElementSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "elementDefinition")) {
				if (jsonParserFieldValue != null) {
					sxpElement.setElementDefinition(
						ElementDefinitionSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					sxpElement.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					sxpElement.setTitle((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title_i18n")) {
				if (jsonParserFieldValue != null) {
					sxpElement.setTitle_i18n(
						(Map)SXPElementSerDes.toMap(
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