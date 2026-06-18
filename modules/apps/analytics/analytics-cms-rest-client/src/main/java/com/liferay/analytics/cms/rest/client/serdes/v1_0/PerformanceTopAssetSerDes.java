/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.cms.rest.client.serdes.v1_0;

import com.liferay.analytics.cms.rest.client.dto.v1_0.PerformanceTopAsset;
import com.liferay.analytics.cms.rest.client.dto.v1_0.PerformanceTopAssetItem;
import com.liferay.analytics.cms.rest.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Rachael Koestartyo
 * @generated
 */
@Generated("")
public class PerformanceTopAssetSerDes {

	public static PerformanceTopAsset toDTO(String json) {
		PerformanceTopAssetJSONParser performanceTopAssetJSONParser =
			new PerformanceTopAssetJSONParser();

		return performanceTopAssetJSONParser.parseToDTO(json);
	}

	public static PerformanceTopAsset[] toDTOs(String json) {
		PerformanceTopAssetJSONParser performanceTopAssetJSONParser =
			new PerformanceTopAssetJSONParser();

		return performanceTopAssetJSONParser.parseToDTOs(json);
	}

	public static String toJSON(PerformanceTopAsset performanceTopAsset) {
		if (performanceTopAsset == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (performanceTopAsset.getLastPage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"lastPage\": ");

			sb.append(performanceTopAsset.getLastPage());
		}

		if (performanceTopAsset.getPage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"page\": ");

			sb.append(performanceTopAsset.getPage());
		}

		if (performanceTopAsset.getPageSize() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"pageSize\": ");

			sb.append(performanceTopAsset.getPageSize());
		}

		if (performanceTopAsset.getPerformanceTopAssetItems() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"performanceTopAssetItems\": ");

			sb.append("[");

			for (int i = 0;
				 i < performanceTopAsset.getPerformanceTopAssetItems().length;
				 i++) {

				sb.append(
					String.valueOf(
						performanceTopAsset.getPerformanceTopAssetItems()[i]));

				if ((i + 1) <
						performanceTopAsset.
							getPerformanceTopAssetItems().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (performanceTopAsset.getTotalCount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalCount\": ");

			sb.append(performanceTopAsset.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PerformanceTopAssetJSONParser performanceTopAssetJSONParser =
			new PerformanceTopAssetJSONParser();

		return performanceTopAssetJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		PerformanceTopAsset performanceTopAsset) {

		if (performanceTopAsset == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (performanceTopAsset.getLastPage() == null) {
			map.put("lastPage", null);
		}
		else {
			map.put(
				"lastPage", String.valueOf(performanceTopAsset.getLastPage()));
		}

		if (performanceTopAsset.getPage() == null) {
			map.put("page", null);
		}
		else {
			map.put("page", String.valueOf(performanceTopAsset.getPage()));
		}

		if (performanceTopAsset.getPageSize() == null) {
			map.put("pageSize", null);
		}
		else {
			map.put(
				"pageSize", String.valueOf(performanceTopAsset.getPageSize()));
		}

		if (performanceTopAsset.getPerformanceTopAssetItems() == null) {
			map.put("performanceTopAssetItems", null);
		}
		else {
			map.put(
				"performanceTopAssetItems",
				String.valueOf(
					performanceTopAsset.getPerformanceTopAssetItems()));
		}

		if (performanceTopAsset.getTotalCount() == null) {
			map.put("totalCount", null);
		}
		else {
			map.put(
				"totalCount",
				String.valueOf(performanceTopAsset.getTotalCount()));
		}

		return map;
	}

	public static class PerformanceTopAssetJSONParser
		extends BaseJSONParser<PerformanceTopAsset> {

		@Override
		protected PerformanceTopAsset createDTO() {
			return new PerformanceTopAsset();
		}

		@Override
		protected PerformanceTopAsset[] createDTOArray(int size) {
			return new PerformanceTopAsset[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "lastPage")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "performanceTopAssetItems")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			PerformanceTopAsset performanceTopAsset, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					performanceTopAsset.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					performanceTopAsset.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					performanceTopAsset.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "performanceTopAssetItems")) {

				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					PerformanceTopAssetItem[] performanceTopAssetItemsArray =
						new PerformanceTopAssetItem
							[jsonParserFieldValues.length];

					for (int i = 0; i < performanceTopAssetItemsArray.length;
						 i++) {

						performanceTopAssetItemsArray[i] =
							PerformanceTopAssetItemSerDes.toDTO(
								(String)jsonParserFieldValues[i]);
					}

					performanceTopAsset.setPerformanceTopAssetItems(
						performanceTopAssetItemsArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					performanceTopAsset.setTotalCount(
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
// LIFERAY-REST-BUILDER-HASH:1444694026