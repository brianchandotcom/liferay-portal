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

import com.liferay.search.experiences.rest.client.dto.v1_0.ElementInstance;
import com.liferay.search.experiences.rest.client.dto.v1_0.SXPBlueprint;
import com.liferay.search.experiences.rest.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (sxpBlueprint.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(sxpBlueprint.getActions()));
		}

		if (sxpBlueprint.getConfiguration() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"configuration\": ");

			sb.append(String.valueOf(sxpBlueprint.getConfiguration()));
		}

		if (sxpBlueprint.getCreateDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"createDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(sxpBlueprint.getCreateDate()));

			sb.append("\"");
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

		if (sxpBlueprint.getElementInstances() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"elementInstances\": ");

			sb.append("[");

			for (int i = 0; i < sxpBlueprint.getElementInstances().length;
				 i++) {

				sb.append(
					String.valueOf(sxpBlueprint.getElementInstances()[i]));

				if ((i + 1) < sxpBlueprint.getElementInstances().length) {
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

		if (sxpBlueprint.getModifiedDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"modifiedDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(sxpBlueprint.getModifiedDate()));

			sb.append("\"");
		}

		if (sxpBlueprint.getSchemaVersion() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"schemaVersion\": ");

			sb.append("\"");

			sb.append(_escape(sxpBlueprint.getSchemaVersion()));

			sb.append("\"");
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

		if (sxpBlueprint.getUserName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userName\": ");

			sb.append("\"");

			sb.append(_escape(sxpBlueprint.getUserName()));

			sb.append("\"");
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

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (sxpBlueprint.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(sxpBlueprint.getActions()));
		}

		if (sxpBlueprint.getConfiguration() == null) {
			map.put("configuration", null);
		}
		else {
			map.put(
				"configuration",
				String.valueOf(sxpBlueprint.getConfiguration()));
		}

		if (sxpBlueprint.getCreateDate() == null) {
			map.put("createDate", null);
		}
		else {
			map.put(
				"createDate",
				liferayToJSONDateFormat.format(sxpBlueprint.getCreateDate()));
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

		if (sxpBlueprint.getElementInstances() == null) {
			map.put("elementInstances", null);
		}
		else {
			map.put(
				"elementInstances",
				String.valueOf(sxpBlueprint.getElementInstances()));
		}

		if (sxpBlueprint.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(sxpBlueprint.getId()));
		}

		if (sxpBlueprint.getModifiedDate() == null) {
			map.put("modifiedDate", null);
		}
		else {
			map.put(
				"modifiedDate",
				liferayToJSONDateFormat.format(sxpBlueprint.getModifiedDate()));
		}

		if (sxpBlueprint.getSchemaVersion() == null) {
			map.put("schemaVersion", null);
		}
		else {
			map.put(
				"schemaVersion",
				String.valueOf(sxpBlueprint.getSchemaVersion()));
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

		if (sxpBlueprint.getUserName() == null) {
			map.put("userName", null);
		}
		else {
			map.put("userName", String.valueOf(sxpBlueprint.getUserName()));
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

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					sxpBlueprint.setActions(
						(Map)SXPBlueprintSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "configuration")) {
				if (jsonParserFieldValue != null) {
					sxpBlueprint.setConfiguration(
						ConfigurationSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "createDate")) {
				if (jsonParserFieldValue != null) {
					sxpBlueprint.setCreateDate(
						toDate((String)jsonParserFieldValue));
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
			else if (Objects.equals(jsonParserFieldName, "elementInstances")) {
				if (jsonParserFieldValue != null) {
					sxpBlueprint.setElementInstances(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ElementInstanceSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new ElementInstance[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					sxpBlueprint.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "modifiedDate")) {
				if (jsonParserFieldValue != null) {
					sxpBlueprint.setModifiedDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "schemaVersion")) {
				if (jsonParserFieldValue != null) {
					sxpBlueprint.setSchemaVersion((String)jsonParserFieldValue);
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
			else if (Objects.equals(jsonParserFieldName, "userName")) {
				if (jsonParserFieldValue != null) {
					sxpBlueprint.setUserName((String)jsonParserFieldValue);
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