/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.sample.client.serdes.v1_0_0;

import com.example.sample.client.dto.v1_0_0.ExternalReferenceElement2;
import com.example.sample.client.dto.v1_0_0.UnreferencedSchemaComponent;
import com.example.sample.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author John Doe
 * @generated
 */
@Generated("")
public class UnreferencedSchemaComponentSerDes {

	public static UnreferencedSchemaComponent toDTO(String json) {
		UnreferencedSchemaComponentJSONParser
			unreferencedSchemaComponentJSONParser =
				new UnreferencedSchemaComponentJSONParser();

		return unreferencedSchemaComponentJSONParser.parseToDTO(json);
	}

	public static UnreferencedSchemaComponent[] toDTOs(String json) {
		UnreferencedSchemaComponentJSONParser
			unreferencedSchemaComponentJSONParser =
				new UnreferencedSchemaComponentJSONParser();

		return unreferencedSchemaComponentJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		UnreferencedSchemaComponent unreferencedSchemaComponent) {

		if (unreferencedSchemaComponent == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (unreferencedSchemaComponent.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(unreferencedSchemaComponent.getDescription()));

			sb.append("\"");
		}

		if (unreferencedSchemaComponent.getExternalReferenceElement2s() !=
				null) {

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceElement2s\": ");

			sb.append("[");

			for (int i = 0;
				 i < unreferencedSchemaComponent.
					 getExternalReferenceElement2s().length;
				 i++) {

				sb.append(
					unreferencedSchemaComponent.getExternalReferenceElement2s()
						[i]);

				if ((i + 1) < unreferencedSchemaComponent.
						getExternalReferenceElement2s().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (unreferencedSchemaComponent.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(unreferencedSchemaComponent.getId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		UnreferencedSchemaComponentJSONParser
			unreferencedSchemaComponentJSONParser =
				new UnreferencedSchemaComponentJSONParser();

		return unreferencedSchemaComponentJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		UnreferencedSchemaComponent unreferencedSchemaComponent) {

		if (unreferencedSchemaComponent == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (unreferencedSchemaComponent.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(unreferencedSchemaComponent.getDescription()));
		}

		if (unreferencedSchemaComponent.getExternalReferenceElement2s() ==
				null) {

			map.put("externalReferenceElement2s", null);
		}
		else {
			map.put(
				"externalReferenceElement2s",
				String.valueOf(
					unreferencedSchemaComponent.
						getExternalReferenceElement2s()));
		}

		if (unreferencedSchemaComponent.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(unreferencedSchemaComponent.getId()));
		}

		return map;
	}

	public static class UnreferencedSchemaComponentJSONParser
		extends BaseJSONParser<UnreferencedSchemaComponent> {

		@Override
		protected UnreferencedSchemaComponent createDTO() {
			return new UnreferencedSchemaComponent();
		}

		@Override
		protected UnreferencedSchemaComponent[] createDTOArray(int size) {
			return new UnreferencedSchemaComponent[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "description")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceElement2s")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			UnreferencedSchemaComponent unreferencedSchemaComponent,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					unreferencedSchemaComponent.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceElement2s")) {

				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					ExternalReferenceElement2[]
						externalReferenceElement2sArray =
							new ExternalReferenceElement2
								[jsonParserFieldValues.length];

					for (int i = 0; i < externalReferenceElement2sArray.length;
						 i++) {

						externalReferenceElement2sArray[i] =
							ExternalReferenceElement2SerDes.toDTO(
								(String)jsonParserFieldValues[i]);
					}

					unreferencedSchemaComponent.setExternalReferenceElement2s(
						externalReferenceElement2sArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					unreferencedSchemaComponent.setId(
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

			sb.append(_toJSON(value));

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _toJSON(Object value) {
		if (value instanceof Map) {
			return _toJSON((Map)value);
		}

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			StringBuilder sb = new StringBuilder("[");

			Object[] values = (Object[])value;

			for (int i = 0; i < values.length; i++) {
				sb.append(_toJSON(values[i]));

				if ((i + 1) < values.length) {
					sb.append(", ");
				}
			}

			sb.append("]");

			return sb.toString();
		}

		if (value instanceof String) {
			return "\"" + _escape(value) + "\"";
		}

		return String.valueOf(value);
	}

}