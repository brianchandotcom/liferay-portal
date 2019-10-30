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

package com.liferay.segments.asah.rest.client.serdes.v1_0;

import com.liferay.segments.asah.rest.client.dto.v1_0.ExperimentRun;
import com.liferay.segments.asah.rest.client.dto.v1_0.ExperimentVariant;
import com.liferay.segments.asah.rest.client.json.BaseJSONParser;

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
public class ExperimentRunSerDes {

	public static ExperimentRun toDTO(String json) {
		ExperimentRunJSONParser experimentRunJSONParser =
			new ExperimentRunJSONParser();

		return experimentRunJSONParser.parseToDTO(json);
	}

	public static ExperimentRun[] toDTOs(String json) {
		ExperimentRunJSONParser experimentRunJSONParser =
			new ExperimentRunJSONParser();

		return experimentRunJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ExperimentRun experimentRun) {
		if (experimentRun == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (experimentRun.getConfidenceLevel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"confidenceLevel\": ");

			sb.append(experimentRun.getConfidenceLevel());
		}

		if (experimentRun.getExperimentVariants() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"experimentVariants\": ");

			sb.append("[");

			for (int i = 0; i < experimentRun.getExperimentVariants().length;
				 i++) {

				sb.append(
					String.valueOf(experimentRun.getExperimentVariants()[i]));

				if ((i + 1) < experimentRun.getExperimentVariants().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (experimentRun.getStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append("\"");

			sb.append(_escape(experimentRun.getStatus()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ExperimentRunJSONParser experimentRunJSONParser =
			new ExperimentRunJSONParser();

		return experimentRunJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ExperimentRun experimentRun) {
		if (experimentRun == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (experimentRun.getConfidenceLevel() == null) {
			map.put("confidenceLevel", null);
		}
		else {
			map.put(
				"confidenceLevel",
				String.valueOf(experimentRun.getConfidenceLevel()));
		}

		if (experimentRun.getExperimentVariants() == null) {
			map.put("experimentVariants", null);
		}
		else {
			map.put(
				"experimentVariants",
				String.valueOf(experimentRun.getExperimentVariants()));
		}

		if (experimentRun.getStatus() == null) {
			map.put("status", null);
		}
		else {
			map.put("status", String.valueOf(experimentRun.getStatus()));
		}

		return map;
	}

	public static class ExperimentRunJSONParser
		extends BaseJSONParser<ExperimentRun> {

		@Override
		protected ExperimentRun createDTO() {
			return new ExperimentRun();
		}

		@Override
		protected ExperimentRun[] createDTOArray(int size) {
			return new ExperimentRun[size];
		}

		@Override
		protected void setField(
			ExperimentRun experimentRun, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "confidenceLevel")) {
				if (jsonParserFieldValue != null) {
					experimentRun.setConfidenceLevel(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "experimentVariants")) {

				if (jsonParserFieldValue != null) {
					experimentRun.setExperimentVariants(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ExperimentVariantSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new ExperimentVariant[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					experimentRun.setStatus((String)jsonParserFieldValue);
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