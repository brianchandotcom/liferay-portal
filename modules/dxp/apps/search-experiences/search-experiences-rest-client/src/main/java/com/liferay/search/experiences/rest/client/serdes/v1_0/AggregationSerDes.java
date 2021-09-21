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

package com.liferay.search.experiences.rest.client.serdes.v1_0;

import com.liferay.search.experiences.rest.client.dto.v1_0.Aggregation;
import com.liferay.search.experiences.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class AggregationSerDes {

	public static Aggregation toDTO(String json) {
		AggregationJSONParser aggregationJSONParser =
			new AggregationJSONParser();

		return aggregationJSONParser.parseToDTO(json);
	}

	public static Aggregation[] toDTOs(String json) {
		AggregationJSONParser aggregationJSONParser =
			new AggregationJSONParser();

		return aggregationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Aggregation aggregation) {
		if (aggregation == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (aggregation.getAggs() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"aggs\": ");

			sb.append(_toJSON(aggregation.getAggs()));
		}

		if (aggregation.getAvg() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"avg\": ");

			sb.append(String.valueOf(aggregation.getAvg()));
		}

		if (aggregation.getCardinality() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"cardinality\": ");

			sb.append(String.valueOf(aggregation.getCardinality()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AggregationJSONParser aggregationJSONParser =
			new AggregationJSONParser();

		return aggregationJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Aggregation aggregation) {
		if (aggregation == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (aggregation.getAggs() == null) {
			map.put("aggs", null);
		}
		else {
			map.put("aggs", String.valueOf(aggregation.getAggs()));
		}

		if (aggregation.getAvg() == null) {
			map.put("avg", null);
		}
		else {
			map.put("avg", String.valueOf(aggregation.getAvg()));
		}

		if (aggregation.getCardinality() == null) {
			map.put("cardinality", null);
		}
		else {
			map.put(
				"cardinality", String.valueOf(aggregation.getCardinality()));
		}

		return map;
	}

	public static class AggregationJSONParser
		extends BaseJSONParser<Aggregation> {

		@Override
		protected Aggregation createDTO() {
			return new Aggregation();
		}

		@Override
		protected Aggregation[] createDTOArray(int size) {
			return new Aggregation[size];
		}

		@Override
		protected void setField(
			Aggregation aggregation, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "aggs")) {
				if (jsonParserFieldValue != null) {
					aggregation.setAggs(
						(Map)AggregationSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "avg")) {
				if (jsonParserFieldValue != null) {
					aggregation.setAvg(
						AvgSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "cardinality")) {
				if (jsonParserFieldValue != null) {
					aggregation.setCardinality(
						CardinalitySerDes.toDTO((String)jsonParserFieldValue));
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