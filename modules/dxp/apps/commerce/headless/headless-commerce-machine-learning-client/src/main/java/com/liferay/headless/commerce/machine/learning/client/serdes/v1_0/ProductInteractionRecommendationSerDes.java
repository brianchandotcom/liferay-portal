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

import com.liferay.headless.commerce.machine.learning.client.dto.v1_0.ProductInteractionRecommendation;
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
public class ProductInteractionRecommendationSerDes {

	public static ProductInteractionRecommendation toDTO(String json) {
		ProductInteractionRecommendationJSONParser
			productInteractionRecommendationJSONParser =
				new ProductInteractionRecommendationJSONParser();

		return productInteractionRecommendationJSONParser.parseToDTO(json);
	}

	public static ProductInteractionRecommendation[] toDTOs(String json) {
		ProductInteractionRecommendationJSONParser
			productInteractionRecommendationJSONParser =
				new ProductInteractionRecommendationJSONParser();

		return productInteractionRecommendationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		ProductInteractionRecommendation productInteractionRecommendation) {

		if (productInteractionRecommendation == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (productInteractionRecommendation.getCreateDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"createDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					productInteractionRecommendation.getCreateDate()));

			sb.append("\"");
		}

		if (productInteractionRecommendation.getJobId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"jobId\": ");

			sb.append("\"");

			sb.append(_escape(productInteractionRecommendation.getJobId()));

			sb.append("\"");
		}

		if (productInteractionRecommendation.getProductId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productId\": ");

			sb.append(productInteractionRecommendation.getProductId());
		}

		if (productInteractionRecommendation.getRank() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"rank\": ");

			sb.append(productInteractionRecommendation.getRank());
		}

		if (productInteractionRecommendation.getRecommendedProductId() !=
				null) {

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"recommendedProductId\": ");

			sb.append(
				productInteractionRecommendation.getRecommendedProductId());
		}

		if (productInteractionRecommendation.getScore() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"score\": ");

			sb.append(productInteractionRecommendation.getScore());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ProductInteractionRecommendationJSONParser
			productInteractionRecommendationJSONParser =
				new ProductInteractionRecommendationJSONParser();

		return productInteractionRecommendationJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ProductInteractionRecommendation productInteractionRecommendation) {

		if (productInteractionRecommendation == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (productInteractionRecommendation.getCreateDate() == null) {
			map.put("createDate", null);
		}
		else {
			map.put(
				"createDate",
				liferayToJSONDateFormat.format(
					productInteractionRecommendation.getCreateDate()));
		}

		if (productInteractionRecommendation.getJobId() == null) {
			map.put("jobId", null);
		}
		else {
			map.put(
				"jobId",
				String.valueOf(productInteractionRecommendation.getJobId()));
		}

		if (productInteractionRecommendation.getProductId() == null) {
			map.put("productId", null);
		}
		else {
			map.put(
				"productId",
				String.valueOf(
					productInteractionRecommendation.getProductId()));
		}

		if (productInteractionRecommendation.getRank() == null) {
			map.put("rank", null);
		}
		else {
			map.put(
				"rank",
				String.valueOf(productInteractionRecommendation.getRank()));
		}

		if (productInteractionRecommendation.getRecommendedProductId() ==
				null) {

			map.put("recommendedProductId", null);
		}
		else {
			map.put(
				"recommendedProductId",
				String.valueOf(
					productInteractionRecommendation.
						getRecommendedProductId()));
		}

		if (productInteractionRecommendation.getScore() == null) {
			map.put("score", null);
		}
		else {
			map.put(
				"score",
				String.valueOf(productInteractionRecommendation.getScore()));
		}

		return map;
	}

	public static class ProductInteractionRecommendationJSONParser
		extends BaseJSONParser<ProductInteractionRecommendation> {

		@Override
		protected ProductInteractionRecommendation createDTO() {
			return new ProductInteractionRecommendation();
		}

		@Override
		protected ProductInteractionRecommendation[] createDTOArray(int size) {
			return new ProductInteractionRecommendation[size];
		}

		@Override
		protected void setField(
			ProductInteractionRecommendation productInteractionRecommendation,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "createDate")) {
				if (jsonParserFieldValue != null) {
					productInteractionRecommendation.setCreateDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "jobId")) {
				if (jsonParserFieldValue != null) {
					productInteractionRecommendation.setJobId(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "productId")) {
				if (jsonParserFieldValue != null) {
					productInteractionRecommendation.setProductId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "rank")) {
				if (jsonParserFieldValue != null) {
					productInteractionRecommendation.setRank(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "recommendedProductId")) {

				if (jsonParserFieldValue != null) {
					productInteractionRecommendation.setRecommendedProductId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "score")) {
				if (jsonParserFieldValue != null) {
					productInteractionRecommendation.setScore(
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