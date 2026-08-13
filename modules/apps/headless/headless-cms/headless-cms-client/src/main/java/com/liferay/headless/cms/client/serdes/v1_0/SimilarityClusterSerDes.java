/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.client.serdes.v1_0;

import com.liferay.headless.cms.client.dto.v1_0.SimilarityCluster;
import com.liferay.headless.cms.client.dto.v1_0.SimilarityClusterAsset;
import com.liferay.headless.cms.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Crescenzo Rega
 * @generated
 */
@Generated("")
public class SimilarityClusterSerDes {

	public static SimilarityCluster toDTO(String json) {
		SimilarityClusterJSONParser similarityClusterJSONParser =
			new SimilarityClusterJSONParser();

		return similarityClusterJSONParser.parseToDTO(json);
	}

	public static SimilarityCluster[] toDTOs(String json) {
		SimilarityClusterJSONParser similarityClusterJSONParser =
			new SimilarityClusterJSONParser();

		return similarityClusterJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SimilarityCluster similarityCluster) {
		if (similarityCluster == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (similarityCluster.getSimilarityClusterAssets() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"similarityClusterAssets\": ");

			sb.append("[");

			for (int i = 0;
				 i < similarityCluster.getSimilarityClusterAssets().length;
				 i++) {

				sb.append(
					String.valueOf(
						similarityCluster.getSimilarityClusterAssets()[i]));

				if ((i + 1) <
						similarityCluster.getSimilarityClusterAssets().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (similarityCluster.getSize() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"size\": ");

			sb.append(similarityCluster.getSize());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SimilarityClusterJSONParser similarityClusterJSONParser =
			new SimilarityClusterJSONParser();

		return similarityClusterJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		SimilarityCluster similarityCluster) {

		if (similarityCluster == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (similarityCluster.getSimilarityClusterAssets() == null) {
			map.put("similarityClusterAssets", null);
		}
		else {
			map.put(
				"similarityClusterAssets",
				String.valueOf(similarityCluster.getSimilarityClusterAssets()));
		}

		if (similarityCluster.getSize() == null) {
			map.put("size", null);
		}
		else {
			map.put("size", String.valueOf(similarityCluster.getSize()));
		}

		return map;
	}

	public static class SimilarityClusterJSONParser
		extends BaseJSONParser<SimilarityCluster> {

		@Override
		protected SimilarityCluster createDTO() {
			return new SimilarityCluster();
		}

		@Override
		protected SimilarityCluster[] createDTOArray(int size) {
			return new SimilarityCluster[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(
					jsonParserFieldName, "similarityClusterAssets")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "size")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			SimilarityCluster similarityCluster, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(
					jsonParserFieldName, "similarityClusterAssets")) {

				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					SimilarityClusterAsset[] similarityClusterAssetsArray =
						new SimilarityClusterAsset
							[jsonParserFieldValues.length];

					for (int i = 0; i < similarityClusterAssetsArray.length;
						 i++) {

						similarityClusterAssetsArray[i] =
							SimilarityClusterAssetSerDes.toDTO(
								(String)jsonParserFieldValues[i]);
					}

					similarityCluster.setSimilarityClusterAssets(
						similarityClusterAssetsArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "size")) {
				if (jsonParserFieldValue != null) {
					similarityCluster.setSize(
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
// LIFERAY-REST-BUILDER-HASH:-1026318494