/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.cms.rest.client.serdes.v1_0;

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
public class PerformanceTopAssetItemSerDes {

	public static PerformanceTopAssetItem toDTO(String json) {
		PerformanceTopAssetItemJSONParser performanceTopAssetItemJSONParser =
			new PerformanceTopAssetItemJSONParser();

		return performanceTopAssetItemJSONParser.parseToDTO(json);
	}

	public static PerformanceTopAssetItem[] toDTOs(String json) {
		PerformanceTopAssetItemJSONParser performanceTopAssetItemJSONParser =
			new PerformanceTopAssetItemJSONParser();

		return performanceTopAssetItemJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		PerformanceTopAssetItem performanceTopAssetItem) {

		if (performanceTopAssetItem == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (performanceTopAssetItem.getDownloads() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"downloads\": ");

			sb.append(performanceTopAssetItem.getDownloads());
		}

		if (performanceTopAssetItem.getEngagement() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"engagement\": ");

			sb.append(performanceTopAssetItem.getEngagement());
		}

		if (performanceTopAssetItem.getImpressions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"impressions\": ");

			sb.append(performanceTopAssetItem.getImpressions());
		}

		if (performanceTopAssetItem.getMimeType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"mimeType\": ");

			sb.append("\"");

			sb.append(_escape(performanceTopAssetItem.getMimeType()));

			sb.append("\"");
		}

		if (performanceTopAssetItem.getTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			sb.append("\"");

			sb.append(_escape(performanceTopAssetItem.getTitle()));

			sb.append("\"");
		}

		if (performanceTopAssetItem.getTrend() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"trend\": ");

			sb.append(String.valueOf(performanceTopAssetItem.getTrend()));
		}

		if (performanceTopAssetItem.getViews() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"views\": ");

			sb.append(performanceTopAssetItem.getViews());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PerformanceTopAssetItemJSONParser performanceTopAssetItemJSONParser =
			new PerformanceTopAssetItemJSONParser();

		return performanceTopAssetItemJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		PerformanceTopAssetItem performanceTopAssetItem) {

		if (performanceTopAssetItem == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (performanceTopAssetItem.getDownloads() == null) {
			map.put("downloads", null);
		}
		else {
			map.put(
				"downloads",
				String.valueOf(performanceTopAssetItem.getDownloads()));
		}

		if (performanceTopAssetItem.getEngagement() == null) {
			map.put("engagement", null);
		}
		else {
			map.put(
				"engagement",
				String.valueOf(performanceTopAssetItem.getEngagement()));
		}

		if (performanceTopAssetItem.getImpressions() == null) {
			map.put("impressions", null);
		}
		else {
			map.put(
				"impressions",
				String.valueOf(performanceTopAssetItem.getImpressions()));
		}

		if (performanceTopAssetItem.getMimeType() == null) {
			map.put("mimeType", null);
		}
		else {
			map.put(
				"mimeType",
				String.valueOf(performanceTopAssetItem.getMimeType()));
		}

		if (performanceTopAssetItem.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put(
				"title", String.valueOf(performanceTopAssetItem.getTitle()));
		}

		if (performanceTopAssetItem.getTrend() == null) {
			map.put("trend", null);
		}
		else {
			map.put(
				"trend", String.valueOf(performanceTopAssetItem.getTrend()));
		}

		if (performanceTopAssetItem.getViews() == null) {
			map.put("views", null);
		}
		else {
			map.put(
				"views", String.valueOf(performanceTopAssetItem.getViews()));
		}

		return map;
	}

	public static class PerformanceTopAssetItemJSONParser
		extends BaseJSONParser<PerformanceTopAssetItem> {

		@Override
		protected PerformanceTopAssetItem createDTO() {
			return new PerformanceTopAssetItem();
		}

		@Override
		protected PerformanceTopAssetItem[] createDTOArray(int size) {
			return new PerformanceTopAssetItem[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "downloads")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "engagement")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "impressions")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "mimeType")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "trend")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "views")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			PerformanceTopAssetItem performanceTopAssetItem,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "downloads")) {
				if (jsonParserFieldValue != null) {
					performanceTopAssetItem.setDownloads(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "engagement")) {
				if (jsonParserFieldValue != null) {
					performanceTopAssetItem.setEngagement(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "impressions")) {
				if (jsonParserFieldValue != null) {
					performanceTopAssetItem.setImpressions(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "mimeType")) {
				if (jsonParserFieldValue != null) {
					performanceTopAssetItem.setMimeType(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					performanceTopAssetItem.setTitle(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "trend")) {
				if (jsonParserFieldValue != null) {
					performanceTopAssetItem.setTrend(
						TrendSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "views")) {
				if (jsonParserFieldValue != null) {
					performanceTopAssetItem.setViews(
						Double.valueOf((String)jsonParserFieldValue));
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
// LIFERAY-REST-BUILDER-HASH:-303417879