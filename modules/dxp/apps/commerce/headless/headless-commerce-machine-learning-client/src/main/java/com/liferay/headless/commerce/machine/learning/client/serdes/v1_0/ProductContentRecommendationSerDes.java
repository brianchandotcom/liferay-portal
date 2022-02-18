/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.headless.commerce.machine.learning.client.serdes.v1_0;

import com.liferay.headless.commerce.machine.learning.client.dto.v1_0.ProductContentRecommendation;
import com.liferay.headless.commerce.machine.learning.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Riccardo Ferrari
 * @generated
 */
@Generated("")
public class ProductContentRecommendationSerDes {

	public static ProductContentRecommendation toDTO(String json) {
		ProductContentRecommendationJSONParser
			productContentRecommendationJSONParser =
				new ProductContentRecommendationJSONParser();

		return productContentRecommendationJSONParser.parseToDTO(json);
	}

	public static ProductContentRecommendation[] toDTOs(String json) {
		ProductContentRecommendationJSONParser
			productContentRecommendationJSONParser =
				new ProductContentRecommendationJSONParser();

		return productContentRecommendationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		ProductContentRecommendation productContentRecommendation) {

		if (productContentRecommendation == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (productContentRecommendation.getCreateDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"createDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					productContentRecommendation.getCreateDate()));

			sb.append("\"");
		}

		if (productContentRecommendation.getJobId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"jobId\": ");

			sb.append("\"");

			sb.append(_escape(productContentRecommendation.getJobId()));

			sb.append("\"");
		}

		if (productContentRecommendation.getProductId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productId\": ");

			sb.append(productContentRecommendation.getProductId());
		}

		if (productContentRecommendation.getRank() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"rank\": ");

			sb.append(productContentRecommendation.getRank());
		}

		if (productContentRecommendation.getRecommendedProductId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"recommendedProductId\": ");

			sb.append(productContentRecommendation.getRecommendedProductId());
		}

		if (productContentRecommendation.getScore() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"score\": ");

			sb.append(productContentRecommendation.getScore());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ProductContentRecommendationJSONParser
			productContentRecommendationJSONParser =
				new ProductContentRecommendationJSONParser();

		return productContentRecommendationJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ProductContentRecommendation productContentRecommendation) {

		if (productContentRecommendation == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (productContentRecommendation.getCreateDate() == null) {
			map.put("createDate", null);
		}
		else {
			map.put(
				"createDate",
				liferayToJSONDateFormat.format(
					productContentRecommendation.getCreateDate()));
		}

		if (productContentRecommendation.getJobId() == null) {
			map.put("jobId", null);
		}
		else {
			map.put(
				"jobId",
				String.valueOf(productContentRecommendation.getJobId()));
		}

		if (productContentRecommendation.getProductId() == null) {
			map.put("productId", null);
		}
		else {
			map.put(
				"productId",
				String.valueOf(productContentRecommendation.getProductId()));
		}

		if (productContentRecommendation.getRank() == null) {
			map.put("rank", null);
		}
		else {
			map.put(
				"rank", String.valueOf(productContentRecommendation.getRank()));
		}

		if (productContentRecommendation.getRecommendedProductId() == null) {
			map.put("recommendedProductId", null);
		}
		else {
			map.put(
				"recommendedProductId",
				String.valueOf(
					productContentRecommendation.getRecommendedProductId()));
		}

		if (productContentRecommendation.getScore() == null) {
			map.put("score", null);
		}
		else {
			map.put(
				"score",
				String.valueOf(productContentRecommendation.getScore()));
		}

		return map;
	}

	public static class ProductContentRecommendationJSONParser
		extends BaseJSONParser<ProductContentRecommendation> {

		@Override
		protected ProductContentRecommendation createDTO() {
			return new ProductContentRecommendation();
		}

		@Override
		protected ProductContentRecommendation[] createDTOArray(int size) {
			return new ProductContentRecommendation[size];
		}

		@Override
		protected void setField(
			ProductContentRecommendation productContentRecommendation,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "createDate")) {
				if (jsonParserFieldValue != null) {
					productContentRecommendation.setCreateDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "jobId")) {
				if (jsonParserFieldValue != null) {
					productContentRecommendation.setJobId(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "productId")) {
				if (jsonParserFieldValue != null) {
					productContentRecommendation.setProductId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "rank")) {
				if (jsonParserFieldValue != null) {
					productContentRecommendation.setRank(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "recommendedProductId")) {

				if (jsonParserFieldValue != null) {
					productContentRecommendation.setRecommendedProductId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "score")) {
				if (jsonParserFieldValue != null) {
					productContentRecommendation.setScore(
						Float.valueOf((String)jsonParserFieldValue));
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