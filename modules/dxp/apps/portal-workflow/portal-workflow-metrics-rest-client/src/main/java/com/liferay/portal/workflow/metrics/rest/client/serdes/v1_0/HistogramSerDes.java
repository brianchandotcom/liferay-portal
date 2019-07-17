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

package com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0;

import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Histogram;
import com.liferay.portal.workflow.metrics.rest.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class HistogramSerDes {

	public static Histogram toDTO(String json) {
		HistogramJSONParser histogramJSONParser = new HistogramJSONParser();

		return histogramJSONParser.parseToDTO(json);
	}

	public static Histogram[] toDTOs(String json) {
		HistogramJSONParser histogramJSONParser = new HistogramJSONParser();

		return histogramJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Histogram histogram) {
		if (histogram == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (histogram.getKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"key\": ");

			sb.append("\"");

			sb.append(_escape(histogram.getKey()));

			sb.append("\"");
		}

		if (histogram.getValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"value\": ");

			sb.append(histogram.getValue());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		HistogramJSONParser histogramJSONParser = new HistogramJSONParser();

		return histogramJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Histogram histogram) {
		if (histogram == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (histogram.getKey() == null) {
			map.put("key", null);
		}
		else {
			map.put("key", String.valueOf(histogram.getKey()));
		}

		if (histogram.getValue() == null) {
			map.put("value", null);
		}
		else {
			map.put("value", String.valueOf(histogram.getValue()));
		}

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		string = string.replace("\\", "\\\\");

		return string.replace("\"", "\\\"");
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
			sb.append("\"");
			sb.append(entry.getValue());
			sb.append("\"");

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static class HistogramJSONParser extends BaseJSONParser<Histogram> {

		@Override
		protected Histogram createDTO() {
			return new Histogram();
		}

		@Override
		protected Histogram[] createDTOArray(int size) {
			return new Histogram[size];
		}

		@Override
		protected void setField(
			Histogram histogram, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "key")) {
				if (jsonParserFieldValue != null) {
					histogram.setKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "value")) {
				if (jsonParserFieldValue != null) {
					histogram.setValue(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}