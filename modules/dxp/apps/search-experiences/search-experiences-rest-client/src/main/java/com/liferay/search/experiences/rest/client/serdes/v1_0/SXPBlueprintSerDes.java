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
import com.liferay.search.experiences.rest.client.dto.v1_0.SXPBlueprint;
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
public class SXPBlueprintSerDes {

	public static SXPBlueprint toDTO(String json) {
		SXPBlueprintJSONParser sxpBlueprintJSONParser =
			new SXPBlueprintJSONParser();

		return sxpBlueprintJSONParser.parseToDTO(json);
	}

	public static SXPBlueprint[] toDTOs(String json) {
		SXPBlueprintJSONParser sxpBlueprintJSONParser =
			new SXPBlueprintJSONParser();

		return sxpBlueprintJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SXPBlueprint sxpBlueprint) {
		if (sxpBlueprint == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (sxpBlueprint.getConfiguration() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"configuration\": ");

			sb.append(String.valueOf(sxpBlueprint.getConfiguration()));
		}

		if (sxpBlueprint.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(sxpBlueprint.getDescription()));

			sb.append("\"");
		}

		if (sxpBlueprint.getDescription_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description_i18n\": ");

			sb.append(_toJSON(sxpBlueprint.getDescription_i18n()));
		}

		if (sxpBlueprint.getElementDefinitions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"elementDefinitions\": ");

			sb.append("[");

			for (int i = 0; i < sxpBlueprint.getElementDefinitions().length;
				 i++) {

				sb.append(
					String.valueOf(sxpBlueprint.getElementDefinitions()[i]));

				if ((i + 1) < sxpBlueprint.getElementDefinitions().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (sxpBlueprint.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(sxpBlueprint.getId());
		}

		if (sxpBlueprint.getTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			sb.append("\"");

			sb.append(_escape(sxpBlueprint.getTitle()));

			sb.append("\"");
		}

		if (sxpBlueprint.getTitle_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title_i18n\": ");

			sb.append(_toJSON(sxpBlueprint.getTitle_i18n()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SXPBlueprintJSONParser sxpBlueprintJSONParser =
			new SXPBlueprintJSONParser();

		return sxpBlueprintJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(SXPBlueprint sxpBlueprint) {
		if (sxpBlueprint == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (sxpBlueprint.getConfiguration() == null) {
			map.put("configuration", null);
		}
		else {
			map.put(
				"configuration",
				String.valueOf(sxpBlueprint.getConfiguration()));
		}

		if (sxpBlueprint.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description", String.valueOf(sxpBlueprint.getDescription()));
		}

		if (sxpBlueprint.getDescription_i18n() == null) {
			map.put("description_i18n", null);
		}
		else {
			map.put(
				"description_i18n",
				String.valueOf(sxpBlueprint.getDescription_i18n()));
		}

		if (sxpBlueprint.getElementDefinitions() == null) {
			map.put("elementDefinitions", null);
		}
		else {
			map.put(
				"elementDefinitions",
				String.valueOf(sxpBlueprint.getElementDefinitions()));
		}

		if (sxpBlueprint.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(sxpBlueprint.getId()));
		}

		if (sxpBlueprint.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put("title", String.valueOf(sxpBlueprint.getTitle()));
		}

		if (sxpBlueprint.getTitle_i18n() == null) {
			map.put("title_i18n", null);
		}
		else {
			map.put("title_i18n", String.valueOf(sxpBlueprint.getTitle_i18n()));
		}

		return map;
	}

	public static class SXPBlueprintJSONParser
		extends BaseJSONParser<SXPBlueprint> {

		@Override
		protected SXPBlueprint createDTO() {
			return new SXPBlueprint();
		}

		@Override
		protected SXPBlueprint[] createDTOArray(int size) {
			return new SXPBlueprint[size];
		}

		@Override
		protected void setField(
			SXPBlueprint sxpBlueprint, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "configuration")) {
				if (jsonParserFieldValue != null) {
					sxpBlueprint.setConfiguration(
						ConfigurationSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					sxpBlueprint.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description_i18n")) {
				if (jsonParserFieldValue != null) {
					sxpBlueprint.setDescription_i18n(
						(Map)SXPBlueprintSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "elementDefinitions")) {

				if (jsonParserFieldValue != null) {
					sxpBlueprint.setElementDefinitions(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ElementDefinitionSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new ElementDefinition[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					sxpBlueprint.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					sxpBlueprint.setTitle((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title_i18n")) {
				if (jsonParserFieldValue != null) {
					sxpBlueprint.setTitle_i18n(
						(Map)SXPBlueprintSerDes.toMap(
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