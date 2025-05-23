/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.serdes.v1_0;

import com.liferay.headless.admin.site.client.dto.v1_0.CustomCSSViewport;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentViewport;
import com.liferay.headless.admin.site.client.dto.v1_0.RowPageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.RowViewport;
import com.liferay.headless.admin.site.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import jakarta.annotation.Generated;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class RowPageElementDefinitionSerDes {

	public static RowPageElementDefinition toDTO(String json) {
		RowPageElementDefinitionJSONParser rowPageElementDefinitionJSONParser =
			new RowPageElementDefinitionJSONParser();

		return rowPageElementDefinitionJSONParser.parseToDTO(json);
	}

	public static RowPageElementDefinition[] toDTOs(String json) {
		RowPageElementDefinitionJSONParser rowPageElementDefinitionJSONParser =
			new RowPageElementDefinitionJSONParser();

		return rowPageElementDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		RowPageElementDefinition rowPageElementDefinition) {

		if (rowPageElementDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (rowPageElementDefinition.getCssClasses() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"cssClasses\": ");

			sb.append("[");

			for (int i = 0; i < rowPageElementDefinition.getCssClasses().length;
				 i++) {

				sb.append(_toJSON(rowPageElementDefinition.getCssClasses()[i]));

				if ((i + 1) < rowPageElementDefinition.getCssClasses().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (rowPageElementDefinition.getCustomCSS() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customCSS\": ");

			sb.append("\"");

			sb.append(_escape(rowPageElementDefinition.getCustomCSS()));

			sb.append("\"");
		}

		if (rowPageElementDefinition.getCustomCSSViewports() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customCSSViewports\": ");

			sb.append("[");

			for (int i = 0;
				 i < rowPageElementDefinition.getCustomCSSViewports().length;
				 i++) {

				sb.append(
					String.valueOf(
						rowPageElementDefinition.getCustomCSSViewports()[i]));

				if ((i + 1) <
						rowPageElementDefinition.
							getCustomCSSViewports().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (rowPageElementDefinition.getFragmentStyle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragmentStyle\": ");

			sb.append(
				String.valueOf(rowPageElementDefinition.getFragmentStyle()));
		}

		if (rowPageElementDefinition.getFragmentViewports() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragmentViewports\": ");

			sb.append("[");

			for (int i = 0;
				 i < rowPageElementDefinition.getFragmentViewports().length;
				 i++) {

				sb.append(
					String.valueOf(
						rowPageElementDefinition.getFragmentViewports()[i]));

				if ((i + 1) <
						rowPageElementDefinition.
							getFragmentViewports().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (rowPageElementDefinition.getGutters() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"gutters\": ");

			sb.append(rowPageElementDefinition.getGutters());
		}

		if (rowPageElementDefinition.getIndexed() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"indexed\": ");

			sb.append(rowPageElementDefinition.getIndexed());
		}

		if (rowPageElementDefinition.getModulesPerRow() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"modulesPerRow\": ");

			sb.append(rowPageElementDefinition.getModulesPerRow());
		}

		if (rowPageElementDefinition.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(rowPageElementDefinition.getName()));

			sb.append("\"");
		}

		if (rowPageElementDefinition.getNumberOfColumns() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfColumns\": ");

			sb.append(rowPageElementDefinition.getNumberOfColumns());
		}

		if (rowPageElementDefinition.getReverseOrder() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"reverseOrder\": ");

			sb.append(rowPageElementDefinition.getReverseOrder());
		}

		if (rowPageElementDefinition.getRowViewports() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"rowViewports\": ");

			sb.append("[");

			for (int i = 0;
				 i < rowPageElementDefinition.getRowViewports().length; i++) {

				sb.append(
					String.valueOf(
						rowPageElementDefinition.getRowViewports()[i]));

				if ((i + 1) <
						rowPageElementDefinition.getRowViewports().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (rowPageElementDefinition.getVerticalAlignment() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"verticalAlignment\": ");

			sb.append("\"");

			sb.append(_escape(rowPageElementDefinition.getVerticalAlignment()));

			sb.append("\"");
		}

		if (rowPageElementDefinition.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(rowPageElementDefinition.getType());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		RowPageElementDefinitionJSONParser rowPageElementDefinitionJSONParser =
			new RowPageElementDefinitionJSONParser();

		return rowPageElementDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		RowPageElementDefinition rowPageElementDefinition) {

		if (rowPageElementDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (rowPageElementDefinition.getCssClasses() == null) {
			map.put("cssClasses", null);
		}
		else {
			map.put(
				"cssClasses",
				String.valueOf(rowPageElementDefinition.getCssClasses()));
		}

		if (rowPageElementDefinition.getCustomCSS() == null) {
			map.put("customCSS", null);
		}
		else {
			map.put(
				"customCSS",
				String.valueOf(rowPageElementDefinition.getCustomCSS()));
		}

		if (rowPageElementDefinition.getCustomCSSViewports() == null) {
			map.put("customCSSViewports", null);
		}
		else {
			map.put(
				"customCSSViewports",
				String.valueOf(
					rowPageElementDefinition.getCustomCSSViewports()));
		}

		if (rowPageElementDefinition.getFragmentStyle() == null) {
			map.put("fragmentStyle", null);
		}
		else {
			map.put(
				"fragmentStyle",
				String.valueOf(rowPageElementDefinition.getFragmentStyle()));
		}

		if (rowPageElementDefinition.getFragmentViewports() == null) {
			map.put("fragmentViewports", null);
		}
		else {
			map.put(
				"fragmentViewports",
				String.valueOf(
					rowPageElementDefinition.getFragmentViewports()));
		}

		if (rowPageElementDefinition.getGutters() == null) {
			map.put("gutters", null);
		}
		else {
			map.put(
				"gutters",
				String.valueOf(rowPageElementDefinition.getGutters()));
		}

		if (rowPageElementDefinition.getIndexed() == null) {
			map.put("indexed", null);
		}
		else {
			map.put(
				"indexed",
				String.valueOf(rowPageElementDefinition.getIndexed()));
		}

		if (rowPageElementDefinition.getModulesPerRow() == null) {
			map.put("modulesPerRow", null);
		}
		else {
			map.put(
				"modulesPerRow",
				String.valueOf(rowPageElementDefinition.getModulesPerRow()));
		}

		if (rowPageElementDefinition.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(rowPageElementDefinition.getName()));
		}

		if (rowPageElementDefinition.getNumberOfColumns() == null) {
			map.put("numberOfColumns", null);
		}
		else {
			map.put(
				"numberOfColumns",
				String.valueOf(rowPageElementDefinition.getNumberOfColumns()));
		}

		if (rowPageElementDefinition.getReverseOrder() == null) {
			map.put("reverseOrder", null);
		}
		else {
			map.put(
				"reverseOrder",
				String.valueOf(rowPageElementDefinition.getReverseOrder()));
		}

		if (rowPageElementDefinition.getRowViewports() == null) {
			map.put("rowViewports", null);
		}
		else {
			map.put(
				"rowViewports",
				String.valueOf(rowPageElementDefinition.getRowViewports()));
		}

		if (rowPageElementDefinition.getVerticalAlignment() == null) {
			map.put("verticalAlignment", null);
		}
		else {
			map.put(
				"verticalAlignment",
				String.valueOf(
					rowPageElementDefinition.getVerticalAlignment()));
		}

		if (rowPageElementDefinition.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(rowPageElementDefinition.getType()));
		}

		return map;
	}

	public static class RowPageElementDefinitionJSONParser
		extends BaseJSONParser<RowPageElementDefinition> {

		@Override
		protected RowPageElementDefinition createDTO() {
			return new RowPageElementDefinition();
		}

		@Override
		protected RowPageElementDefinition[] createDTOArray(int size) {
			return new RowPageElementDefinition[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "cssClasses")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "customCSS")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "customCSSViewports")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "fragmentStyle")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "fragmentViewports")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "gutters")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "indexed")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "modulesPerRow")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "numberOfColumns")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "reverseOrder")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "rowViewports")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "verticalAlignment")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			RowPageElementDefinition rowPageElementDefinition,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "cssClasses")) {
				if (jsonParserFieldValue != null) {
					rowPageElementDefinition.setCssClasses(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "customCSS")) {
				if (jsonParserFieldValue != null) {
					rowPageElementDefinition.setCustomCSS(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "customCSSViewports")) {

				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					CustomCSSViewport[] customCSSViewportsArray =
						new CustomCSSViewport[jsonParserFieldValues.length];

					for (int i = 0; i < customCSSViewportsArray.length; i++) {
						customCSSViewportsArray[i] =
							CustomCSSViewportSerDes.toDTO(
								(String)jsonParserFieldValues[i]);
					}

					rowPageElementDefinition.setCustomCSSViewports(
						customCSSViewportsArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fragmentStyle")) {
				if (jsonParserFieldValue != null) {
					rowPageElementDefinition.setFragmentStyle(
						FragmentStyleSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fragmentViewports")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					FragmentViewport[] fragmentViewportsArray =
						new FragmentViewport[jsonParserFieldValues.length];

					for (int i = 0; i < fragmentViewportsArray.length; i++) {
						fragmentViewportsArray[i] =
							FragmentViewportSerDes.toDTO(
								(String)jsonParserFieldValues[i]);
					}

					rowPageElementDefinition.setFragmentViewports(
						fragmentViewportsArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "gutters")) {
				if (jsonParserFieldValue != null) {
					rowPageElementDefinition.setGutters(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "indexed")) {
				if (jsonParserFieldValue != null) {
					rowPageElementDefinition.setIndexed(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "modulesPerRow")) {
				if (jsonParserFieldValue != null) {
					rowPageElementDefinition.setModulesPerRow(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					rowPageElementDefinition.setName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "numberOfColumns")) {
				if (jsonParserFieldValue != null) {
					rowPageElementDefinition.setNumberOfColumns(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "reverseOrder")) {
				if (jsonParserFieldValue != null) {
					rowPageElementDefinition.setReverseOrder(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "rowViewports")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					RowViewport[] rowViewportsArray =
						new RowViewport[jsonParserFieldValues.length];

					for (int i = 0; i < rowViewportsArray.length; i++) {
						rowViewportsArray[i] = RowViewportSerDes.toDTO(
							(String)jsonParserFieldValues[i]);
					}

					rowPageElementDefinition.setRowViewports(rowViewportsArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "verticalAlignment")) {
				if (jsonParserFieldValue != null) {
					rowPageElementDefinition.setVerticalAlignment(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					rowPageElementDefinition.setType(
						RowPageElementDefinition.Type.create(
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

			sb.append(_toJSON(value));

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _toJSON(Object value) {
		if (value == null) {
			return "null";
		}

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