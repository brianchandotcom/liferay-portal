/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.serdes.v1_0;

import com.liferay.headless.admin.site.client.dto.v1_0.PageCollectionDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.PageCollectionItemDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.PageColumnDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.PageContainerDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.PageDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.PageDropZoneDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.PageFormDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.PageFormStepContainerDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.PageFormStepDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.PageFragmentCompositionInstanceDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.PageFragmentDropZoneDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.PageFragmentInstanceDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.PageRowDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.PageWidgetInstanceDefinition;
import com.liferay.headless.admin.site.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class PageDefinitionSerDes {

	public static PageDefinition toDTO(String json) {
		PageDefinitionJSONParser pageDefinitionJSONParser =
			new PageDefinitionJSONParser();

		return pageDefinitionJSONParser.parseToDTO(json);
	}

	public static PageDefinition[] toDTOs(String json) {
		PageDefinitionJSONParser pageDefinitionJSONParser =
			new PageDefinitionJSONParser();

		return pageDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(PageDefinition pageDefinition) {
		if (pageDefinition == null) {
			return "null";
		}

		PageDefinition.Type type = pageDefinition.getType();

		if (type != null) {
			String typeString = type.toString();

			if (typeString.equals("Collection")) {
				return PageCollectionDefinitionSerDes.toJSON(
					(PageCollectionDefinition)pageDefinition);
			}

			if (typeString.equals("CollectionItem")) {
				return PageCollectionItemDefinitionSerDes.toJSON(
					(PageCollectionItemDefinition)pageDefinition);
			}

			if (typeString.equals("Column")) {
				return PageColumnDefinitionSerDes.toJSON(
					(PageColumnDefinition)pageDefinition);
			}

			if (typeString.equals("Container")) {
				return PageContainerDefinitionSerDes.toJSON(
					(PageContainerDefinition)pageDefinition);
			}

			if (typeString.equals("DropZone")) {
				return PageDropZoneDefinitionSerDes.toJSON(
					(PageDropZoneDefinition)pageDefinition);
			}

			if (typeString.equals("Form")) {
				return PageFormDefinitionSerDes.toJSON(
					(PageFormDefinition)pageDefinition);
			}

			if (typeString.equals("FormStep")) {
				return PageFormStepDefinitionSerDes.toJSON(
					(PageFormStepDefinition)pageDefinition);
			}

			if (typeString.equals("FormStepContainer")) {
				return PageFormStepContainerDefinitionSerDes.toJSON(
					(PageFormStepContainerDefinition)pageDefinition);
			}

			if (typeString.equals("FragmentComposition")) {
				return PageFragmentCompositionInstanceDefinitionSerDes.toJSON(
					(PageFragmentCompositionInstanceDefinition)pageDefinition);
			}

			if (typeString.equals("FragmentDropZone")) {
				return PageFragmentDropZoneDefinitionSerDes.toJSON(
					(PageFragmentDropZoneDefinition)pageDefinition);
			}

			if (typeString.equals("Fragment")) {
				return PageFragmentInstanceDefinitionSerDes.toJSON(
					(PageFragmentInstanceDefinition)pageDefinition);
			}

			if (typeString.equals("Row")) {
				return PageRowDefinitionSerDes.toJSON(
					(PageRowDefinition)pageDefinition);
			}

			if (typeString.equals("Widget")) {
				return PageWidgetInstanceDefinitionSerDes.toJSON(
					(PageWidgetInstanceDefinition)pageDefinition);
			}

			throw new IllegalArgumentException("Unknown type " + typeString);
		}
		else {
			throw new IllegalArgumentException("Missing type parameter");
		}
	}

	public static Map<String, Object> toMap(String json) {
		PageDefinitionJSONParser pageDefinitionJSONParser =
			new PageDefinitionJSONParser();

		return pageDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(PageDefinition pageDefinition) {
		if (pageDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (pageDefinition.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(pageDefinition.getType()));
		}

		return map;
	}

	public static class PageDefinitionJSONParser
		extends BaseJSONParser<PageDefinition> {

		@Override
		protected PageDefinition createDTO() {
			return null;
		}

		@Override
		protected PageDefinition[] createDTOArray(int size) {
			return new PageDefinition[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "type")) {
				return false;
			}

			return false;
		}

		@Override
		public PageDefinition parseToDTO(String json) {
			Map<String, Object> jsonMap = parseToMap(json);

			Object type = jsonMap.get("type");

			if (type != null) {
				String typeString = type.toString();

				if (typeString.equals("Collection")) {
					return PageCollectionDefinition.toDTO(json);
				}

				if (typeString.equals("CollectionItem")) {
					return PageCollectionItemDefinition.toDTO(json);
				}

				if (typeString.equals("Column")) {
					return PageColumnDefinition.toDTO(json);
				}

				if (typeString.equals("Container")) {
					return PageContainerDefinition.toDTO(json);
				}

				if (typeString.equals("DropZone")) {
					return PageDropZoneDefinition.toDTO(json);
				}

				if (typeString.equals("Form")) {
					return PageFormDefinition.toDTO(json);
				}

				if (typeString.equals("FormStep")) {
					return PageFormStepDefinition.toDTO(json);
				}

				if (typeString.equals("FormStepContainer")) {
					return PageFormStepContainerDefinition.toDTO(json);
				}

				if (typeString.equals("FragmentComposition")) {
					return PageFragmentCompositionInstanceDefinition.toDTO(
						json);
				}

				if (typeString.equals("FragmentDropZone")) {
					return PageFragmentDropZoneDefinition.toDTO(json);
				}

				if (typeString.equals("Fragment")) {
					return PageFragmentInstanceDefinition.toDTO(json);
				}

				if (typeString.equals("Row")) {
					return PageRowDefinition.toDTO(json);
				}

				if (typeString.equals("Widget")) {
					return PageWidgetInstanceDefinition.toDTO(json);
				}

				throw new IllegalArgumentException(
					"Unknown type " + typeString);
			}
			else {
				throw new IllegalArgumentException("Missing type parameter");
			}
		}

		@Override
		protected void setField(
			PageDefinition pageDefinition, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					pageDefinition.setType(
						PageDefinition.Type.create(
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