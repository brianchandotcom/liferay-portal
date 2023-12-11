/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.rest.client.serdes.v1_0;

import com.liferay.scim.rest.client.dto.v1_0.GroupSchema;
import com.liferay.scim.rest.client.dto.v1_0.MultiValuedAttribute;
import com.liferay.scim.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Olivér Kecskeméty
 * @generated
 */
@Generated("")
public class GroupSchemaSerDes {

	public static GroupSchema toDTO(String json) {
		GroupSchemaJSONParser groupSchemaJSONParser =
			new GroupSchemaJSONParser();

		return groupSchemaJSONParser.parseToDTO(json);
	}

	public static GroupSchema[] toDTOs(String json) {
		GroupSchemaJSONParser groupSchemaJSONParser =
			new GroupSchemaJSONParser();

		return groupSchemaJSONParser.parseToDTOs(json);
	}

	public static String toJSON(GroupSchema groupSchema) {
		if (groupSchema == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (groupSchema.getDisplayName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayName\": ");

			sb.append("\"");

			sb.append(_escape(groupSchema.getDisplayName()));

			sb.append("\"");
		}

		if (groupSchema.getExternalId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalId\": ");

			sb.append("\"");

			sb.append(_escape(groupSchema.getExternalId()));

			sb.append("\"");
		}

		if (groupSchema.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append("\"");

			sb.append(_escape(groupSchema.getId()));

			sb.append("\"");
		}

		if (groupSchema.getMembers() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"members\": ");

			sb.append("[");

			for (int i = 0; i < groupSchema.getMembers().length; i++) {
				sb.append(String.valueOf(groupSchema.getMembers()[i]));

				if ((i + 1) < groupSchema.getMembers().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (groupSchema.getMeta() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"meta\": ");

			sb.append(String.valueOf(groupSchema.getMeta()));
		}

		if (groupSchema.getSchemas() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"schemas\": ");

			sb.append("[");

			for (int i = 0; i < groupSchema.getSchemas().length; i++) {
				sb.append("\"");

				sb.append(_escape(groupSchema.getSchemas()[i]));

				sb.append("\"");

				if ((i + 1) < groupSchema.getSchemas().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		GroupSchemaJSONParser groupSchemaJSONParser =
			new GroupSchemaJSONParser();

		return groupSchemaJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(GroupSchema groupSchema) {
		if (groupSchema == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (groupSchema.getDisplayName() == null) {
			map.put("displayName", null);
		}
		else {
			map.put(
				"displayName", String.valueOf(groupSchema.getDisplayName()));
		}

		if (groupSchema.getExternalId() == null) {
			map.put("externalId", null);
		}
		else {
			map.put("externalId", String.valueOf(groupSchema.getExternalId()));
		}

		if (groupSchema.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(groupSchema.getId()));
		}

		if (groupSchema.getMembers() == null) {
			map.put("members", null);
		}
		else {
			map.put("members", String.valueOf(groupSchema.getMembers()));
		}

		if (groupSchema.getMeta() == null) {
			map.put("meta", null);
		}
		else {
			map.put("meta", String.valueOf(groupSchema.getMeta()));
		}

		if (groupSchema.getSchemas() == null) {
			map.put("schemas", null);
		}
		else {
			map.put("schemas", String.valueOf(groupSchema.getSchemas()));
		}

		return map;
	}

	public static class GroupSchemaJSONParser
		extends BaseJSONParser<GroupSchema> {

		@Override
		protected GroupSchema createDTO() {
			return new GroupSchema();
		}

		@Override
		protected GroupSchema[] createDTOArray(int size) {
			return new GroupSchema[size];
		}

		@Override
		protected void setField(
			GroupSchema groupSchema, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "displayName")) {
				if (jsonParserFieldValue != null) {
					groupSchema.setDisplayName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "externalId")) {
				if (jsonParserFieldValue != null) {
					groupSchema.setExternalId((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					groupSchema.setId((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "members")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					MultiValuedAttribute[] membersArray =
						new MultiValuedAttribute[jsonParserFieldValues.length];

					for (int i = 0; i < membersArray.length; i++) {
						membersArray[i] = MultiValuedAttributeSerDes.toDTO(
							(String)jsonParserFieldValues[i]);
					}

					groupSchema.setMembers(membersArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "meta")) {
				if (jsonParserFieldValue != null) {
					groupSchema.setMeta(
						MetaSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "schemas")) {
				if (jsonParserFieldValue != null) {
					groupSchema.setSchemas(
						toStrings((Object[])jsonParserFieldValue));
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