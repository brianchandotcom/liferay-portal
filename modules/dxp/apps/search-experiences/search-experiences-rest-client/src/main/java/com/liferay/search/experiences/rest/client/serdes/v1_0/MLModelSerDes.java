/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.rest.client.serdes.v1_0;

import com.liferay.search.experiences.rest.client.dto.v1_0.MLModel;
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
public class MLModelSerDes {

	public static MLModel toDTO(String json) {
		MLModelJSONParser mlModelJSONParser = new MLModelJSONParser();

		return mlModelJSONParser.parseToDTO(json);
	}

	public static MLModel[] toDTOs(String json) {
		MLModelJSONParser mlModelJSONParser = new MLModelJSONParser();

		return mlModelJSONParser.parseToDTOs(json);
	}

	public static String toJSON(MLModel mlModel) {
		if (mlModel == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (mlModel.getModelId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"modelId\": ");

			sb.append("\"");

			sb.append(_escape(mlModel.getModelId()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		MLModelJSONParser mlModelJSONParser = new MLModelJSONParser();

		return mlModelJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(MLModel mlModel) {
		if (mlModel == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (mlModel.getModelId() == null) {
			map.put("modelId", null);
		}
		else {
			map.put("modelId", String.valueOf(mlModel.getModelId()));
		}

		return map;
	}

	public static class MLModelJSONParser extends BaseJSONParser<MLModel> {

		@Override
		protected MLModel createDTO() {
			return new MLModel();
		}

		@Override
		protected MLModel[] createDTOArray(int size) {
			return new MLModel[size];
		}

		@Override
		protected void setField(
			MLModel mlModel, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "modelId")) {
				if (jsonParserFieldValue != null) {
					mlModel.setModelId((String)jsonParserFieldValue);
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