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

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.ContentStructureField;
import com.liferay.headless.delivery.client.dto.v1_0.Option;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ContentStructureFieldSerDes {

	public static ContentStructureField toDTO(String json) {
		ContentStructureFieldJSONParser contentStructureFieldJSONParser =
			new ContentStructureFieldJSONParser();

		return contentStructureFieldJSONParser.parseToDTO(json);
	}

	public static ContentStructureField[] toDTOs(String json) {
		ContentStructureFieldJSONParser contentStructureFieldJSONParser =
			new ContentStructureFieldJSONParser();

		return contentStructureFieldJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ContentStructureField contentStructureField) {
		if (contentStructureField == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"dataType\": ");

		if (contentStructureField.getDataType() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(contentStructureField.getDataType());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"inputControl\": ");

		if (contentStructureField.getInputControl() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(contentStructureField.getInputControl());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"label\": ");

		if (contentStructureField.getLabel() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(contentStructureField.getLabel());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"localizable\": ");

		if (contentStructureField.getLocalizable() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentStructureField.getLocalizable());
		}

		sb.append(", ");

		sb.append("\"multiple\": ");

		if (contentStructureField.getMultiple() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentStructureField.getMultiple());
		}

		sb.append(", ");

		sb.append("\"name\": ");

		if (contentStructureField.getName() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(contentStructureField.getName());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"nestedContentStructureFields\": ");

		if (contentStructureField.getNestedContentStructureFields() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0;
				 i < contentStructureField.
					 getNestedContentStructureFields().length;
				 i++) {

				sb.append(
					ContentStructureFieldSerDes.toJSON(
						contentStructureField.getNestedContentStructureFields()
							[i]));

				if ((i + 1) < contentStructureField.
						getNestedContentStructureFields().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"options\": ");

		if (contentStructureField.getOptions() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < contentStructureField.getOptions().length;
				 i++) {

				sb.append(
					OptionSerDes.toJSON(contentStructureField.getOptions()[i]));

				if ((i + 1) < contentStructureField.getOptions().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"predefinedValue\": ");

		if (contentStructureField.getPredefinedValue() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(contentStructureField.getPredefinedValue());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"repeatable\": ");

		if (contentStructureField.getRepeatable() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentStructureField.getRepeatable());
		}

		sb.append(", ");

		sb.append("\"required\": ");

		if (contentStructureField.getRequired() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentStructureField.getRequired());
		}

		sb.append(", ");

		sb.append("\"showLabel\": ");

		if (contentStructureField.getShowLabel() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentStructureField.getShowLabel());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(
		ContentStructureField contentStructureField) {

		if (contentStructureField == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		map.put(
			"dataType", String.valueOf(contentStructureField.getDataType()));

		map.put(
			"inputControl",
			String.valueOf(contentStructureField.getInputControl()));

		map.put("label", String.valueOf(contentStructureField.getLabel()));

		map.put(
			"localizable",
			String.valueOf(contentStructureField.getLocalizable()));

		map.put(
			"multiple", String.valueOf(contentStructureField.getMultiple()));

		map.put("name", String.valueOf(contentStructureField.getName()));

		map.put(
			"nestedContentStructureFields",
			String.valueOf(
				contentStructureField.getNestedContentStructureFields()));

		map.put("options", String.valueOf(contentStructureField.getOptions()));

		map.put(
			"predefinedValue",
			String.valueOf(contentStructureField.getPredefinedValue()));

		map.put(
			"repeatable",
			String.valueOf(contentStructureField.getRepeatable()));

		map.put(
			"required", String.valueOf(contentStructureField.getRequired()));

		map.put(
			"showLabel", String.valueOf(contentStructureField.getShowLabel()));

		return map;
	}

	private static class ContentStructureFieldJSONParser
		extends BaseJSONParser<ContentStructureField> {

		@Override
		protected ContentStructureField createDTO() {
			return new ContentStructureField();
		}

		@Override
		protected ContentStructureField[] createDTOArray(int size) {
			return new ContentStructureField[size];
		}

		@Override
		protected void setField(
			ContentStructureField contentStructureField,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dataType")) {
				if (jsonParserFieldValue != null) {
					contentStructureField.setDataType(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "inputControl")) {
				if (jsonParserFieldValue != null) {
					contentStructureField.setInputControl(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "label")) {
				if (jsonParserFieldValue != null) {
					contentStructureField.setLabel(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "localizable")) {
				if (jsonParserFieldValue != null) {
					contentStructureField.setLocalizable(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "multiple")) {
				if (jsonParserFieldValue != null) {
					contentStructureField.setMultiple(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					contentStructureField.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "nestedContentStructureFields")) {

				if (jsonParserFieldValue != null) {
					contentStructureField.setNestedContentStructureFields(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ContentStructureFieldSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new ContentStructureField[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "options")) {
				if (jsonParserFieldValue != null) {
					contentStructureField.setOptions(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> OptionSerDes.toDTO((String)object)
						).toArray(
							size -> new Option[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "predefinedValue")) {
				if (jsonParserFieldValue != null) {
					contentStructureField.setPredefinedValue(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "repeatable")) {
				if (jsonParserFieldValue != null) {
					contentStructureField.setRepeatable(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "required")) {
				if (jsonParserFieldValue != null) {
					contentStructureField.setRequired(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "showLabel")) {
				if (jsonParserFieldValue != null) {
					contentStructureField.setShowLabel(
						(Boolean)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}