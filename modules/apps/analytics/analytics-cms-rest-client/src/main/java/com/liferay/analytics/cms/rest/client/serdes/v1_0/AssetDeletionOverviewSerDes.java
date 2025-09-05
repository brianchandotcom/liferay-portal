/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.cms.rest.client.serdes.v1_0;

import com.liferay.analytics.cms.rest.client.dto.v1_0.AssetDeletionOverview;
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
public class AssetDeletionOverviewSerDes {

	public static AssetDeletionOverview toDTO(String json) {
		AssetDeletionOverviewJSONParser assetDeletionOverviewJSONParser =
			new AssetDeletionOverviewJSONParser();

		return assetDeletionOverviewJSONParser.parseToDTO(json);
	}

	public static AssetDeletionOverview[] toDTOs(String json) {
		AssetDeletionOverviewJSONParser assetDeletionOverviewJSONParser =
			new AssetDeletionOverviewJSONParser();

		return assetDeletionOverviewJSONParser.parseToDTOs(json);
	}

	public static String toJSON(AssetDeletionOverview assetDeletionOverview) {
		if (assetDeletionOverview == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (assetDeletionOverview.getDeletionType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"deletionType\": ");

			sb.append("\"");

			sb.append(assetDeletionOverview.getDeletionType());

			sb.append("\"");
		}

		if (assetDeletionOverview.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(assetDeletionOverview.getId());
		}

		if (assetDeletionOverview.getMimeType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"mimeType\": ");

			sb.append("\"");

			sb.append(_escape(assetDeletionOverview.getMimeType()));

			sb.append("\"");
		}

		if (assetDeletionOverview.getTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			sb.append("\"");

			sb.append(_escape(assetDeletionOverview.getTitle()));

			sb.append("\"");
		}

		if (assetDeletionOverview.getUsages() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"usages\": ");

			sb.append(assetDeletionOverview.getUsages());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AssetDeletionOverviewJSONParser assetDeletionOverviewJSONParser =
			new AssetDeletionOverviewJSONParser();

		return assetDeletionOverviewJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		AssetDeletionOverview assetDeletionOverview) {

		if (assetDeletionOverview == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (assetDeletionOverview.getDeletionType() == null) {
			map.put("deletionType", null);
		}
		else {
			map.put(
				"deletionType",
				String.valueOf(assetDeletionOverview.getDeletionType()));
		}

		if (assetDeletionOverview.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(assetDeletionOverview.getId()));
		}

		if (assetDeletionOverview.getMimeType() == null) {
			map.put("mimeType", null);
		}
		else {
			map.put(
				"mimeType",
				String.valueOf(assetDeletionOverview.getMimeType()));
		}

		if (assetDeletionOverview.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put("title", String.valueOf(assetDeletionOverview.getTitle()));
		}

		if (assetDeletionOverview.getUsages() == null) {
			map.put("usages", null);
		}
		else {
			map.put(
				"usages", String.valueOf(assetDeletionOverview.getUsages()));
		}

		return map;
	}

	public static class AssetDeletionOverviewJSONParser
		extends BaseJSONParser<AssetDeletionOverview> {

		@Override
		protected AssetDeletionOverview createDTO() {
			return new AssetDeletionOverview();
		}

		@Override
		protected AssetDeletionOverview[] createDTOArray(int size) {
			return new AssetDeletionOverview[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "deletionType")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "mimeType")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "usages")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			AssetDeletionOverview assetDeletionOverview,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "deletionType")) {
				if (jsonParserFieldValue != null) {
					assetDeletionOverview.setDeletionType(
						AssetDeletionOverview.DeletionType.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					assetDeletionOverview.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "mimeType")) {
				if (jsonParserFieldValue != null) {
					assetDeletionOverview.setMimeType(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					assetDeletionOverview.setTitle(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "usages")) {
				if (jsonParserFieldValue != null) {
					assetDeletionOverview.setUsages(
						Integer.valueOf((String)jsonParserFieldValue));
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