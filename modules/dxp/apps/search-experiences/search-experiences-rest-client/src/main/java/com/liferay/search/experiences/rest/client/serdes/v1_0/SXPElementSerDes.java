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

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (sxpElement.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(sxpElement.getActions()));
		}

		if (sxpElement.getCreateDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"createDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(sxpElement.getCreateDate()));

			sb.append("\"");
		}

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

		if (sxpElement.getHidden() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"hidden\": ");

			sb.append(sxpElement.getHidden());
		}

		if (sxpElement.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(sxpElement.getId());
		}

		if (sxpElement.getModifiedDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"modifiedDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(sxpElement.getModifiedDate()));

			sb.append("\"");
		}

		if (sxpElement.getReadOnly() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"readOnly\": ");

			sb.append(sxpElement.getReadOnly());
		}

		if (sxpElement.getSchemaVersion() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"schemaVersion\": ");

			sb.append("\"");

			sb.append(_escape(sxpElement.getSchemaVersion()));

			sb.append("\"");
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

		if (sxpElement.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append(sxpElement.getType());
		}

		if (sxpElement.getUserName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userName\": ");

			sb.append("\"");

			sb.append(_escape(sxpElement.getUserName()));

			sb.append("\"");
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

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (sxpElement.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(sxpElement.getActions()));
		}

		if (sxpElement.getCreateDate() == null) {
			map.put("createDate", null);
		}
		else {
			map.put(
				"createDate",
				liferayToJSONDateFormat.format(sxpElement.getCreateDate()));
		}

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

		if (sxpElement.getHidden() == null) {
			map.put("hidden", null);
		}
		else {
			map.put("hidden", String.valueOf(sxpElement.getHidden()));
		}

		if (sxpElement.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(sxpElement.getId()));
		}

		if (sxpElement.getModifiedDate() == null) {
			map.put("modifiedDate", null);
		}
		else {
			map.put(
				"modifiedDate",
				liferayToJSONDateFormat.format(sxpElement.getModifiedDate()));
		}

		if (sxpElement.getReadOnly() == null) {
			map.put("readOnly", null);
		}
		else {
			map.put("readOnly", String.valueOf(sxpElement.getReadOnly()));
		}

		if (sxpElement.getSchemaVersion() == null) {
			map.put("schemaVersion", null);
		}
		else {
			map.put(
				"schemaVersion", String.valueOf(sxpElement.getSchemaVersion()));
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

		if (sxpElement.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(sxpElement.getType()));
		}

		if (sxpElement.getUserName() == null) {
			map.put("userName", null);
		}
		else {
			map.put("userName", String.valueOf(sxpElement.getUserName()));
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

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					sxpElement.setActions(
						(Map)SXPElementSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "createDate")) {
				if (jsonParserFieldValue != null) {
					sxpElement.setCreateDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
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
			else if (Objects.equals(jsonParserFieldName, "hidden")) {
				if (jsonParserFieldValue != null) {
					sxpElement.setHidden((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					sxpElement.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "modifiedDate")) {
				if (jsonParserFieldValue != null) {
					sxpElement.setModifiedDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "readOnly")) {
				if (jsonParserFieldValue != null) {
					sxpElement.setReadOnly((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "schemaVersion")) {
				if (jsonParserFieldValue != null) {
					sxpElement.setSchemaVersion((String)jsonParserFieldValue);
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
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					sxpElement.setType(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "userName")) {
				if (jsonParserFieldValue != null) {
					sxpElement.setUserName((String)jsonParserFieldValue);
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