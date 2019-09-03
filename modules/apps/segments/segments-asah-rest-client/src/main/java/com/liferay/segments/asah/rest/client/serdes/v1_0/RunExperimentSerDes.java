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

package com.liferay.segments.asah.rest.client.serdes.v1_0;

import com.liferay.segments.asah.rest.client.dto.v1_0.ExperimentVariant;
import com.liferay.segments.asah.rest.client.dto.v1_0.RunExperiment;
import com.liferay.segments.asah.rest.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class RunExperimentSerDes {

	public static RunExperiment toDTO(String json) {
		RunExperimentJSONParser runExperimentJSONParser =
			new RunExperimentJSONParser();

		return runExperimentJSONParser.parseToDTO(json);
	}

	public static RunExperiment[] toDTOs(String json) {
		RunExperimentJSONParser runExperimentJSONParser =
			new RunExperimentJSONParser();

		return runExperimentJSONParser.parseToDTOs(json);
	}

	public static String toJSON(RunExperiment runExperiment) {
		if (runExperiment == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (runExperiment.getConfidenceLevel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"confidenceLevel\": ");

			sb.append(runExperiment.getConfidenceLevel());
		}

		if (runExperiment.getStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append("\"");

			sb.append(_escape(runExperiment.getStatus()));

			sb.append("\"");
		}

		if (runExperiment.getVariants() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"variants\": ");

			sb.append("[");

			for (int i = 0; i < runExperiment.getVariants().length; i++) {
				sb.append(String.valueOf(runExperiment.getVariants()[i]));

				if ((i + 1) < runExperiment.getVariants().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		RunExperimentJSONParser runExperimentJSONParser =
			new RunExperimentJSONParser();

		return runExperimentJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(RunExperiment runExperiment) {
		if (runExperiment == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (runExperiment.getConfidenceLevel() == null) {
			map.put("confidenceLevel", null);
		}
		else {
			map.put(
				"confidenceLevel",
				String.valueOf(runExperiment.getConfidenceLevel()));
		}

		if (runExperiment.getStatus() == null) {
			map.put("status", null);
		}
		else {
			map.put("status", String.valueOf(runExperiment.getStatus()));
		}

		if (runExperiment.getVariants() == null) {
			map.put("variants", null);
		}
		else {
			map.put("variants", String.valueOf(runExperiment.getVariants()));
		}

		return map;
	}

	public static class RunExperimentJSONParser
		extends BaseJSONParser<RunExperiment> {

		@Override
		protected RunExperiment createDTO() {
			return new RunExperiment();
		}

		@Override
		protected RunExperiment[] createDTOArray(int size) {
			return new RunExperiment[size];
		}

		@Override
		protected void setField(
			RunExperiment runExperiment, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "confidenceLevel")) {
				if (jsonParserFieldValue != null) {
					runExperiment.setConfidenceLevel(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					runExperiment.setStatus((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "variants")) {
				if (jsonParserFieldValue != null) {
					runExperiment.setVariants(
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