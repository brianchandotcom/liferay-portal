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

import com.liferay.headless.delivery.client.dto.v1_0.FragmentViewport;
import com.liferay.headless.delivery.client.dto.v1_0.PageCollectionDefinition;
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
public class PageCollectionDefinitionSerDes {

	public static PageCollectionDefinition toDTO(String json) {
		PageCollectionDefinitionJSONParser pageCollectionDefinitionJSONParser =
			new PageCollectionDefinitionJSONParser();

		return pageCollectionDefinitionJSONParser.parseToDTO(json);
	}

	public static PageCollectionDefinition[] toDTOs(String json) {
		PageCollectionDefinitionJSONParser pageCollectionDefinitionJSONParser =
			new PageCollectionDefinitionJSONParser();

		return pageCollectionDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		PageCollectionDefinition pageCollectionDefinition) {

		if (pageCollectionDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (pageCollectionDefinition.getCollectionConfig() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"collectionConfig\": ");

			sb.append(
				String.valueOf(pageCollectionDefinition.getCollectionConfig()));
		}

		if (pageCollectionDefinition.getDisplayAllItems() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayAllItems\": ");

			sb.append(pageCollectionDefinition.getDisplayAllItems());
		}

		if (pageCollectionDefinition.getDisplayAllPages() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayAllPages\": ");

			sb.append(pageCollectionDefinition.getDisplayAllPages());
		}

		if (pageCollectionDefinition.getFragmentStyle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragmentStyle\": ");

			sb.append(
				String.valueOf(pageCollectionDefinition.getFragmentStyle()));
		}

		if (pageCollectionDefinition.getFragmentViewports() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragmentViewports\": ");

			sb.append("[");

			for (int i = 0;
				 i < pageCollectionDefinition.getFragmentViewports().length;
				 i++) {

				sb.append(
					String.valueOf(
						pageCollectionDefinition.getFragmentViewports()[i]));

				if ((i + 1) <
						pageCollectionDefinition.
							getFragmentViewports().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (pageCollectionDefinition.getListItemStyle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"listItemStyle\": ");

			sb.append("\"");

			sb.append(_escape(pageCollectionDefinition.getListItemStyle()));

			sb.append("\"");
		}

		if (pageCollectionDefinition.getListStyle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"listStyle\": ");

			sb.append("\"");

			sb.append(_escape(pageCollectionDefinition.getListStyle()));

			sb.append("\"");
		}

		if (pageCollectionDefinition.getNumberOfColumns() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfColumns\": ");

			sb.append(pageCollectionDefinition.getNumberOfColumns());
		}

		if (pageCollectionDefinition.getNumberOfItems() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfItems\": ");

			sb.append(pageCollectionDefinition.getNumberOfItems());
		}

		if (pageCollectionDefinition.getNumberOfItemsPerPage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfItemsPerPage\": ");

			sb.append(pageCollectionDefinition.getNumberOfItemsPerPage());
		}

		if (pageCollectionDefinition.getNumberOfPages() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfPages\": ");

			sb.append(pageCollectionDefinition.getNumberOfPages());
		}

		if (pageCollectionDefinition.getPaginationType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paginationType\": ");

			sb.append("\"");

			sb.append(pageCollectionDefinition.getPaginationType());

			sb.append("\"");
		}

		if (pageCollectionDefinition.getShowAllItems() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"showAllItems\": ");

			sb.append(pageCollectionDefinition.getShowAllItems());
		}

		if (pageCollectionDefinition.getTemplateKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"templateKey\": ");

			sb.append("\"");

			sb.append(_escape(pageCollectionDefinition.getTemplateKey()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PageCollectionDefinitionJSONParser pageCollectionDefinitionJSONParser =
			new PageCollectionDefinitionJSONParser();

		return pageCollectionDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		PageCollectionDefinition pageCollectionDefinition) {

		if (pageCollectionDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (pageCollectionDefinition.getCollectionConfig() == null) {
			map.put("collectionConfig", null);
		}
		else {
			map.put(
				"collectionConfig",
				String.valueOf(pageCollectionDefinition.getCollectionConfig()));
		}

		if (pageCollectionDefinition.getDisplayAllItems() == null) {
			map.put("displayAllItems", null);
		}
		else {
			map.put(
				"displayAllItems",
				String.valueOf(pageCollectionDefinition.getDisplayAllItems()));
		}

		if (pageCollectionDefinition.getDisplayAllPages() == null) {
			map.put("displayAllPages", null);
		}
		else {
			map.put(
				"displayAllPages",
				String.valueOf(pageCollectionDefinition.getDisplayAllPages()));
		}

		if (pageCollectionDefinition.getFragmentStyle() == null) {
			map.put("fragmentStyle", null);
		}
		else {
			map.put(
				"fragmentStyle",
				String.valueOf(pageCollectionDefinition.getFragmentStyle()));
		}

		if (pageCollectionDefinition.getFragmentViewports() == null) {
			map.put("fragmentViewports", null);
		}
		else {
			map.put(
				"fragmentViewports",
				String.valueOf(
					pageCollectionDefinition.getFragmentViewports()));
		}

		if (pageCollectionDefinition.getListItemStyle() == null) {
			map.put("listItemStyle", null);
		}
		else {
			map.put(
				"listItemStyle",
				String.valueOf(pageCollectionDefinition.getListItemStyle()));
		}

		if (pageCollectionDefinition.getListStyle() == null) {
			map.put("listStyle", null);
		}
		else {
			map.put(
				"listStyle",
				String.valueOf(pageCollectionDefinition.getListStyle()));
		}

		if (pageCollectionDefinition.getNumberOfColumns() == null) {
			map.put("numberOfColumns", null);
		}
		else {
			map.put(
				"numberOfColumns",
				String.valueOf(pageCollectionDefinition.getNumberOfColumns()));
		}

		if (pageCollectionDefinition.getNumberOfItems() == null) {
			map.put("numberOfItems", null);
		}
		else {
			map.put(
				"numberOfItems",
				String.valueOf(pageCollectionDefinition.getNumberOfItems()));
		}

		if (pageCollectionDefinition.getNumberOfItemsPerPage() == null) {
			map.put("numberOfItemsPerPage", null);
		}
		else {
			map.put(
				"numberOfItemsPerPage",
				String.valueOf(
					pageCollectionDefinition.getNumberOfItemsPerPage()));
		}

		if (pageCollectionDefinition.getNumberOfPages() == null) {
			map.put("numberOfPages", null);
		}
		else {
			map.put(
				"numberOfPages",
				String.valueOf(pageCollectionDefinition.getNumberOfPages()));
		}

		if (pageCollectionDefinition.getPaginationType() == null) {
			map.put("paginationType", null);
		}
		else {
			map.put(
				"paginationType",
				String.valueOf(pageCollectionDefinition.getPaginationType()));
		}

		if (pageCollectionDefinition.getShowAllItems() == null) {
			map.put("showAllItems", null);
		}
		else {
			map.put(
				"showAllItems",
				String.valueOf(pageCollectionDefinition.getShowAllItems()));
		}

		if (pageCollectionDefinition.getTemplateKey() == null) {
			map.put("templateKey", null);
		}
		else {
			map.put(
				"templateKey",
				String.valueOf(pageCollectionDefinition.getTemplateKey()));
		}

		return map;
	}

	public static class PageCollectionDefinitionJSONParser
		extends BaseJSONParser<PageCollectionDefinition> {

		@Override
		protected PageCollectionDefinition createDTO() {
			return new PageCollectionDefinition();
		}

		@Override
		protected PageCollectionDefinition[] createDTOArray(int size) {
			return new PageCollectionDefinition[size];
		}

		@Override
		protected void setField(
			PageCollectionDefinition pageCollectionDefinition,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "collectionConfig")) {
				if (jsonParserFieldValue != null) {
					pageCollectionDefinition.setCollectionConfig(
						CollectionConfigSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "displayAllItems")) {
				if (jsonParserFieldValue != null) {
					pageCollectionDefinition.setDisplayAllItems(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "displayAllPages")) {
				if (jsonParserFieldValue != null) {
					pageCollectionDefinition.setDisplayAllPages(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fragmentStyle")) {
				if (jsonParserFieldValue != null) {
					pageCollectionDefinition.setFragmentStyle(
						FragmentStyleSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fragmentViewports")) {
				if (jsonParserFieldValue != null) {
					pageCollectionDefinition.setFragmentViewports(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> FragmentViewportSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new FragmentViewport[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "listItemStyle")) {
				if (jsonParserFieldValue != null) {
					pageCollectionDefinition.setListItemStyle(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "listStyle")) {
				if (jsonParserFieldValue != null) {
					pageCollectionDefinition.setListStyle(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "numberOfColumns")) {
				if (jsonParserFieldValue != null) {
					pageCollectionDefinition.setNumberOfColumns(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "numberOfItems")) {
				if (jsonParserFieldValue != null) {
					pageCollectionDefinition.setNumberOfItems(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "numberOfItemsPerPage")) {

				if (jsonParserFieldValue != null) {
					pageCollectionDefinition.setNumberOfItemsPerPage(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "numberOfPages")) {
				if (jsonParserFieldValue != null) {
					pageCollectionDefinition.setNumberOfPages(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "paginationType")) {
				if (jsonParserFieldValue != null) {
					pageCollectionDefinition.setPaginationType(
						PageCollectionDefinition.PaginationType.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "showAllItems")) {
				if (jsonParserFieldValue != null) {
					pageCollectionDefinition.setShowAllItems(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "templateKey")) {
				if (jsonParserFieldValue != null) {
					pageCollectionDefinition.setTemplateKey(
						(String)jsonParserFieldValue);
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