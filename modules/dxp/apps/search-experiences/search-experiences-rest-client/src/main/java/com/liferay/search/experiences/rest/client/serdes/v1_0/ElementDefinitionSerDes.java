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

import com.liferay.search.experiences.rest.client.dto.v1_0.ElementDefinition;
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
public class ElementDefinitionSerDes {

	public static ElementDefinition toDTO(String json) {
		ElementDefinitionJSONParser elementDefinitionJSONParser =
			new ElementDefinitionJSONParser();

		return elementDefinitionJSONParser.parseToDTO(json);
	}

	public static ElementDefinition[] toDTOs(String json) {
		ElementDefinitionJSONParser elementDefinitionJSONParser =
			new ElementDefinitionJSONParser();

		return elementDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ElementDefinition elementDefinition) {
		if (elementDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (elementDefinition.getCategory() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"category\": ");

			sb.append("\"");

			sb.append(_escape(elementDefinition.getCategory()));

			sb.append("\"");
		}

		if (elementDefinition.getIcon() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"icon\": ");

			sb.append("\"");

			sb.append(_escape(elementDefinition.getIcon()));

			sb.append("\"");
		}

		if (elementDefinition.getSxpBlueprint() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sxpBlueprint\": ");

			sb.append(String.valueOf(elementDefinition.getSxpBlueprint()));
		}

		if (elementDefinition.getUiConfiguration() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"uiConfiguration\": ");

			sb.append(String.valueOf(elementDefinition.getUiConfiguration()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ElementDefinitionJSONParser elementDefinitionJSONParser =
			new ElementDefinitionJSONParser();

		return elementDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ElementDefinition elementDefinition) {

		if (elementDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (elementDefinition.getCategory() == null) {
			map.put("category", null);
		}
		else {
			map.put(
				"category", String.valueOf(elementDefinition.getCategory()));
		}

		if (elementDefinition.getIcon() == null) {
			map.put("icon", null);
		}
		else {
			map.put("icon", String.valueOf(elementDefinition.getIcon()));
		}

		if (elementDefinition.getSxpBlueprint() == null) {
			map.put("sxpBlueprint", null);
		}
		else {
			map.put(
				"sxpBlueprint",
				String.valueOf(elementDefinition.getSxpBlueprint()));
		}

		if (elementDefinition.getUiConfiguration() == null) {
			map.put("uiConfiguration", null);
		}
		else {
			map.put(
				"uiConfiguration",
				String.valueOf(elementDefinition.getUiConfiguration()));
		}

		return map;
	}

	public static class ElementDefinitionJSONParser
		extends BaseJSONParser<ElementDefinition> {

		@Override
		protected ElementDefinition createDTO() {
			return new ElementDefinition();
		}

		@Override
		protected ElementDefinition[] createDTOArray(int size) {
			return new ElementDefinition[size];
		}

		@Override
		protected void setField(
			ElementDefinition elementDefinition, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "category")) {
				if (jsonParserFieldValue != null) {
					elementDefinition.setCategory((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "icon")) {
				if (jsonParserFieldValue != null) {
					elementDefinition.setIcon((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "sxpBlueprint")) {
				if (jsonParserFieldValue != null) {
					elementDefinition.setSxpBlueprint(
						SXPBlueprintSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "uiConfiguration")) {
				if (jsonParserFieldValue != null) {
					elementDefinition.setUiConfiguration(
						UiConfigurationSerDes.toDTO(
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