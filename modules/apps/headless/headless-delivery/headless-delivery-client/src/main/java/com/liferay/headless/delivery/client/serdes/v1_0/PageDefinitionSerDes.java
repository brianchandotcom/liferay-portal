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

import com.liferay.headless.delivery.client.dto.v1_0.PageDefinition;
import com.liferay.headless.delivery.client.dto.v1_0.PageElement;
import com.liferay.headless.delivery.client.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

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

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (pageDefinition.getFriendlyURLPath() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"friendlyURLPath\": ");

			sb.append("\"");

			sb.append(_escape(pageDefinition.getFriendlyURLPath()));

			sb.append("\"");
		}

		if (pageDefinition.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(pageDefinition.getId());
		}

		if (pageDefinition.getPageElements() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"pageElements\": ");

			sb.append("[");

			for (int i = 0; i < pageDefinition.getPageElements().length; i++) {
				sb.append(String.valueOf(pageDefinition.getPageElements()[i]));

				if ((i + 1) < pageDefinition.getPageElements().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (pageDefinition.getSettings() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"settings\": ");

			sb.append(String.valueOf(pageDefinition.getSettings()));
		}

		if (pageDefinition.getTaxonomyCategories() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxonomyCategories\": ");

			sb.append("[");

			for (int i = 0; i < pageDefinition.getTaxonomyCategories().length;
				 i++) {

				sb.append(
					String.valueOf(pageDefinition.getTaxonomyCategories()[i]));

				if ((i + 1) < pageDefinition.getTaxonomyCategories().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (pageDefinition.getTaxonomyCategoryIds() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxonomyCategoryIds\": ");

			sb.append("[");

			for (int i = 0; i < pageDefinition.getTaxonomyCategoryIds().length;
				 i++) {

				sb.append(pageDefinition.getTaxonomyCategoryIds()[i]);

				if ((i + 1) < pageDefinition.getTaxonomyCategoryIds().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (pageDefinition.getUuid() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"uuid\": ");

			sb.append("\"");

			sb.append(_escape(pageDefinition.getUuid()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
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

		if (pageDefinition.getFriendlyURLPath() == null) {
			map.put("friendlyURLPath", null);
		}
		else {
			map.put(
				"friendlyURLPath",
				String.valueOf(pageDefinition.getFriendlyURLPath()));
		}

		if (pageDefinition.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(pageDefinition.getId()));
		}

		if (pageDefinition.getPageElements() == null) {
			map.put("pageElements", null);
		}
		else {
			map.put(
				"pageElements",
				String.valueOf(pageDefinition.getPageElements()));
		}

		if (pageDefinition.getSettings() == null) {
			map.put("settings", null);
		}
		else {
			map.put("settings", String.valueOf(pageDefinition.getSettings()));
		}

		if (pageDefinition.getTaxonomyCategories() == null) {
			map.put("taxonomyCategories", null);
		}
		else {
			map.put(
				"taxonomyCategories",
				String.valueOf(pageDefinition.getTaxonomyCategories()));
		}

		if (pageDefinition.getTaxonomyCategoryIds() == null) {
			map.put("taxonomyCategoryIds", null);
		}
		else {
			map.put(
				"taxonomyCategoryIds",
				String.valueOf(pageDefinition.getTaxonomyCategoryIds()));
		}

		if (pageDefinition.getUuid() == null) {
			map.put("uuid", null);
		}
		else {
			map.put("uuid", String.valueOf(pageDefinition.getUuid()));
		}

		return map;
	}

	public static class PageDefinitionJSONParser
		extends BaseJSONParser<PageDefinition> {

		@Override
		protected PageDefinition createDTO() {
			return new PageDefinition();
		}

		@Override
		protected PageDefinition[] createDTOArray(int size) {
			return new PageDefinition[size];
		}

		@Override
		protected void setField(
			PageDefinition pageDefinition, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "friendlyURLPath")) {
				if (jsonParserFieldValue != null) {
					pageDefinition.setFriendlyURLPath(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					pageDefinition.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageElements")) {
				if (jsonParserFieldValue != null) {
					pageDefinition.setPageElements(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> PageElementSerDes.toDTO((String)object)
						).toArray(
							size -> new PageElement[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "settings")) {
				if (jsonParserFieldValue != null) {
					pageDefinition.setSettings(
						SettingsSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "taxonomyCategories")) {

				if (jsonParserFieldValue != null) {
					pageDefinition.setTaxonomyCategories(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> TaxonomyCategorySerDes.toDTO(
								(String)object)
						).toArray(
							size -> new TaxonomyCategory[size]
						));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "taxonomyCategoryIds")) {

				if (jsonParserFieldValue != null) {
					pageDefinition.setTaxonomyCategoryIds(
						toLongs((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "uuid")) {
				if (jsonParserFieldValue != null) {
					pageDefinition.setUuid((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
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
			sb.append("\":");

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
			else {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}