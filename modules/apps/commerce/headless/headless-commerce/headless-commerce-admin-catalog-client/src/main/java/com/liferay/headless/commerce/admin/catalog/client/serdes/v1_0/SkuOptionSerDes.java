/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.client.serdes.v1_0;

import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.SkuOption;
import com.liferay.headless.commerce.admin.catalog.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public class SkuOptionSerDes {

	public static SkuOption toDTO(String json) {
		SkuOptionJSONParser skuOptionJSONParser = new SkuOptionJSONParser();

		return skuOptionJSONParser.parseToDTO(json);
	}

	public static SkuOption[] toDTOs(String json) {
		SkuOptionJSONParser skuOptionJSONParser = new SkuOptionJSONParser();

		return skuOptionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SkuOption skuOption) {
		if (skuOption == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (skuOption.getKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"key\": ");

			sb.append("\"");

			sb.append(_escape(skuOption.getKey()));

			sb.append("\"");
		}

		if (skuOption.getOptionId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"optionId\": ");

			sb.append(skuOption.getOptionId());
		}

		if (skuOption.getOptionValueId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"optionValueId\": ");

			sb.append(skuOption.getOptionValueId());
		}

		if (skuOption.getValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"value\": ");

			sb.append("\"");

			sb.append(_escape(skuOption.getValue()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SkuOptionJSONParser skuOptionJSONParser = new SkuOptionJSONParser();

		return skuOptionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(SkuOption skuOption) {
		if (skuOption == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (skuOption.getKey() == null) {
			map.put("key", null);
		}
		else {
			map.put("key", String.valueOf(skuOption.getKey()));
		}

		if (skuOption.getOptionId() == null) {
			map.put("optionId", null);
		}
		else {
			map.put("optionId", String.valueOf(skuOption.getOptionId()));
		}

		if (skuOption.getOptionValueId() == null) {
			map.put("optionValueId", null);
		}
		else {
			map.put(
				"optionValueId", String.valueOf(skuOption.getOptionValueId()));
		}

		if (skuOption.getValue() == null) {
			map.put("value", null);
		}
		else {
			map.put("value", String.valueOf(skuOption.getValue()));
		}

		return map;
	}

	public static class SkuOptionJSONParser extends BaseJSONParser<SkuOption> {

		@Override
		protected SkuOption createDTO() {
			return new SkuOption();
		}

		@Override
		protected SkuOption[] createDTOArray(int size) {
			return new SkuOption[size];
		}

		@Override
		protected void setField(
			SkuOption skuOption, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "key")) {
				if (jsonParserFieldValue != null) {
					skuOption.setKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "optionId")) {
				if (jsonParserFieldValue != null) {
					skuOption.setOptionId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "optionValueId")) {
				if (jsonParserFieldValue != null) {
					skuOption.setOptionValueId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "value")) {
				if (jsonParserFieldValue != null) {
					skuOption.setValue((String)jsonParserFieldValue);
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