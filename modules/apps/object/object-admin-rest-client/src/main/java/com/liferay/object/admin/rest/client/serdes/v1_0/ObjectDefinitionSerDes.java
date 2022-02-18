/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.object.admin.rest.client.serdes.v1_0;

import com.liferay.object.admin.rest.client.dto.v1_0.ObjectAction;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectField;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectLayout;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectRelationship;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectView;
import com.liferay.object.admin.rest.client.json.BaseJSONParser;

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
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ObjectDefinitionSerDes {

	public static ObjectDefinition toDTO(String json) {
		ObjectDefinitionJSONParser objectDefinitionJSONParser =
			new ObjectDefinitionJSONParser();

		return objectDefinitionJSONParser.parseToDTO(json);
	}

	public static ObjectDefinition[] toDTOs(String json) {
		ObjectDefinitionJSONParser objectDefinitionJSONParser =
			new ObjectDefinitionJSONParser();

		return objectDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ObjectDefinition objectDefinition) {
		if (objectDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (objectDefinition.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(objectDefinition.getActions()));
		}

		if (objectDefinition.getActive() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"active\": ");

			sb.append(objectDefinition.getActive());
		}

		if (objectDefinition.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					objectDefinition.getDateCreated()));

			sb.append("\"");
		}

		if (objectDefinition.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					objectDefinition.getDateModified()));

			sb.append("\"");
		}

		if (objectDefinition.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(objectDefinition.getId());
		}

		if (objectDefinition.getLabel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"label\": ");

			sb.append(_toJSON(objectDefinition.getLabel()));
		}

		if (objectDefinition.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(objectDefinition.getName()));

			sb.append("\"");
		}

		if (objectDefinition.getObjectActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectActions\": ");

			sb.append("[");

			for (int i = 0; i < objectDefinition.getObjectActions().length;
				 i++) {

				sb.append(
					String.valueOf(objectDefinition.getObjectActions()[i]));

				if ((i + 1) < objectDefinition.getObjectActions().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (objectDefinition.getObjectFields() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectFields\": ");

			sb.append("[");

			for (int i = 0; i < objectDefinition.getObjectFields().length;
				 i++) {

				sb.append(
					String.valueOf(objectDefinition.getObjectFields()[i]));

				if ((i + 1) < objectDefinition.getObjectFields().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (objectDefinition.getObjectLayouts() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectLayouts\": ");

			sb.append("[");

			for (int i = 0; i < objectDefinition.getObjectLayouts().length;
				 i++) {

				sb.append(
					String.valueOf(objectDefinition.getObjectLayouts()[i]));

				if ((i + 1) < objectDefinition.getObjectLayouts().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (objectDefinition.getObjectRelationships() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectRelationships\": ");

			sb.append("[");

			for (int i = 0;
				 i < objectDefinition.getObjectRelationships().length; i++) {

				sb.append(
					String.valueOf(
						objectDefinition.getObjectRelationships()[i]));

				if ((i + 1) <
						objectDefinition.getObjectRelationships().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (objectDefinition.getObjectViews() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectViews\": ");

			sb.append("[");

			for (int i = 0; i < objectDefinition.getObjectViews().length; i++) {
				sb.append(String.valueOf(objectDefinition.getObjectViews()[i]));

				if ((i + 1) < objectDefinition.getObjectViews().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (objectDefinition.getPanelAppOrder() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"panelAppOrder\": ");

			sb.append("\"");

			sb.append(_escape(objectDefinition.getPanelAppOrder()));

			sb.append("\"");
		}

		if (objectDefinition.getPanelCategoryKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"panelCategoryKey\": ");

			sb.append("\"");

			sb.append(_escape(objectDefinition.getPanelCategoryKey()));

			sb.append("\"");
		}

		if (objectDefinition.getPluralLabel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"pluralLabel\": ");

			sb.append(_toJSON(objectDefinition.getPluralLabel()));
		}

		if (objectDefinition.getPortlet() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"portlet\": ");

			sb.append(objectDefinition.getPortlet());
		}

		if (objectDefinition.getScope() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"scope\": ");

			sb.append("\"");

			sb.append(_escape(objectDefinition.getScope()));

			sb.append("\"");
		}

		if (objectDefinition.getStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append(String.valueOf(objectDefinition.getStatus()));
		}

		if (objectDefinition.getSystem() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"system\": ");

			sb.append(objectDefinition.getSystem());
		}

		if (objectDefinition.getTitleObjectFieldId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"titleObjectFieldId\": ");

			sb.append(objectDefinition.getTitleObjectFieldId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ObjectDefinitionJSONParser objectDefinitionJSONParser =
			new ObjectDefinitionJSONParser();

		return objectDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ObjectDefinition objectDefinition) {
		if (objectDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (objectDefinition.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(objectDefinition.getActions()));
		}

		if (objectDefinition.getActive() == null) {
			map.put("active", null);
		}
		else {
			map.put("active", String.valueOf(objectDefinition.getActive()));
		}

		if (objectDefinition.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(
					objectDefinition.getDateCreated()));
		}

		if (objectDefinition.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(
					objectDefinition.getDateModified()));
		}

		if (objectDefinition.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(objectDefinition.getId()));
		}

		if (objectDefinition.getLabel() == null) {
			map.put("label", null);
		}
		else {
			map.put("label", String.valueOf(objectDefinition.getLabel()));
		}

		if (objectDefinition.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(objectDefinition.getName()));
		}

		if (objectDefinition.getObjectActions() == null) {
			map.put("objectActions", null);
		}
		else {
			map.put(
				"objectActions",
				String.valueOf(objectDefinition.getObjectActions()));
		}

		if (objectDefinition.getObjectFields() == null) {
			map.put("objectFields", null);
		}
		else {
			map.put(
				"objectFields",
				String.valueOf(objectDefinition.getObjectFields()));
		}

		if (objectDefinition.getObjectLayouts() == null) {
			map.put("objectLayouts", null);
		}
		else {
			map.put(
				"objectLayouts",
				String.valueOf(objectDefinition.getObjectLayouts()));
		}

		if (objectDefinition.getObjectRelationships() == null) {
			map.put("objectRelationships", null);
		}
		else {
			map.put(
				"objectRelationships",
				String.valueOf(objectDefinition.getObjectRelationships()));
		}

		if (objectDefinition.getObjectViews() == null) {
			map.put("objectViews", null);
		}
		else {
			map.put(
				"objectViews",
				String.valueOf(objectDefinition.getObjectViews()));
		}

		if (objectDefinition.getPanelAppOrder() == null) {
			map.put("panelAppOrder", null);
		}
		else {
			map.put(
				"panelAppOrder",
				String.valueOf(objectDefinition.getPanelAppOrder()));
		}

		if (objectDefinition.getPanelCategoryKey() == null) {
			map.put("panelCategoryKey", null);
		}
		else {
			map.put(
				"panelCategoryKey",
				String.valueOf(objectDefinition.getPanelCategoryKey()));
		}

		if (objectDefinition.getPluralLabel() == null) {
			map.put("pluralLabel", null);
		}
		else {
			map.put(
				"pluralLabel",
				String.valueOf(objectDefinition.getPluralLabel()));
		}

		if (objectDefinition.getPortlet() == null) {
			map.put("portlet", null);
		}
		else {
			map.put("portlet", String.valueOf(objectDefinition.getPortlet()));
		}

		if (objectDefinition.getScope() == null) {
			map.put("scope", null);
		}
		else {
			map.put("scope", String.valueOf(objectDefinition.getScope()));
		}

		if (objectDefinition.getStatus() == null) {
			map.put("status", null);
		}
		else {
			map.put("status", String.valueOf(objectDefinition.getStatus()));
		}

		if (objectDefinition.getSystem() == null) {
			map.put("system", null);
		}
		else {
			map.put("system", String.valueOf(objectDefinition.getSystem()));
		}

		if (objectDefinition.getTitleObjectFieldId() == null) {
			map.put("titleObjectFieldId", null);
		}
		else {
			map.put(
				"titleObjectFieldId",
				String.valueOf(objectDefinition.getTitleObjectFieldId()));
		}

		return map;
	}

	public static class ObjectDefinitionJSONParser
		extends BaseJSONParser<ObjectDefinition> {

		@Override
		protected ObjectDefinition createDTO() {
			return new ObjectDefinition();
		}

		@Override
		protected ObjectDefinition[] createDTOArray(int size) {
			return new ObjectDefinition[size];
		}

		@Override
		protected void setField(
			ObjectDefinition objectDefinition, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setActions(
						(Map)ObjectDefinitionSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "active")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setActive((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "label")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setLabel(
						(Map)ObjectDefinitionSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "objectActions")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setObjectActions(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ObjectActionSerDes.toDTO((String)object)
						).toArray(
							size -> new ObjectAction[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "objectFields")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setObjectFields(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ObjectFieldSerDes.toDTO((String)object)
						).toArray(
							size -> new ObjectField[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "objectLayouts")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setObjectLayouts(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ObjectLayoutSerDes.toDTO((String)object)
						).toArray(
							size -> new ObjectLayout[size]
						));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "objectRelationships")) {

				if (jsonParserFieldValue != null) {
					objectDefinition.setObjectRelationships(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ObjectRelationshipSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new ObjectRelationship[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "objectViews")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setObjectViews(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ObjectViewSerDes.toDTO((String)object)
						).toArray(
							size -> new ObjectView[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "panelAppOrder")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setPanelAppOrder(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "panelCategoryKey")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setPanelCategoryKey(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pluralLabel")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setPluralLabel(
						(Map)ObjectDefinitionSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "portlet")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setPortlet((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "scope")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setScope((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setStatus(
						StatusSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "system")) {
				if (jsonParserFieldValue != null) {
					objectDefinition.setSystem((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "titleObjectFieldId")) {

				if (jsonParserFieldValue != null) {
					objectDefinition.setTitleObjectFieldId(
						Long.valueOf((String)jsonParserFieldValue));
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