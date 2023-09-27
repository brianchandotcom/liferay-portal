/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.pricing.client.serdes.v2_0;

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.DiscountRule;
import com.liferay.headless.commerce.admin.pricing.client.json.BaseJSONParser;

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
public class DiscountRuleSerDes {

	public static DiscountRule toDTO(String json) {
		DiscountRuleJSONParser discountRuleJSONParser =
			new DiscountRuleJSONParser();

		return discountRuleJSONParser.parseToDTO(json);
	}

	public static DiscountRule[] toDTOs(String json) {
		DiscountRuleJSONParser discountRuleJSONParser =
			new DiscountRuleJSONParser();

		return discountRuleJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DiscountRule discountRule) {
		if (discountRule == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (discountRule.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(discountRule.getActions()));
		}

		if (discountRule.getDiscountId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountId\": ");

			sb.append(discountRule.getDiscountId());
		}

		if (discountRule.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(discountRule.getId());
		}

		if (discountRule.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(discountRule.getName()));

			sb.append("\"");
		}

		if (discountRule.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(_escape(discountRule.getType()));

			sb.append("\"");
		}

		if (discountRule.getTypeSettings() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"typeSettings\": ");

			sb.append("\"");

			sb.append(_escape(discountRule.getTypeSettings()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DiscountRuleJSONParser discountRuleJSONParser =
			new DiscountRuleJSONParser();

		return discountRuleJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(DiscountRule discountRule) {
		if (discountRule == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (discountRule.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(discountRule.getActions()));
		}

		if (discountRule.getDiscountId() == null) {
			map.put("discountId", null);
		}
		else {
			map.put("discountId", String.valueOf(discountRule.getDiscountId()));
		}

		if (discountRule.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(discountRule.getId()));
		}

		if (discountRule.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(discountRule.getName()));
		}

		if (discountRule.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(discountRule.getType()));
		}

		if (discountRule.getTypeSettings() == null) {
			map.put("typeSettings", null);
		}
		else {
			map.put(
				"typeSettings", String.valueOf(discountRule.getTypeSettings()));
		}

		return map;
	}

	public static class DiscountRuleJSONParser
		extends BaseJSONParser<DiscountRule> {

		@Override
		protected DiscountRule createDTO() {
			return new DiscountRule();
		}

		@Override
		protected DiscountRule[] createDTOArray(int size) {
			return new DiscountRule[size];
		}

		@Override
		protected void setField(
			DiscountRule discountRule, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					discountRule.setActions(
						(Map)DiscountRuleSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "discountId")) {
				if (jsonParserFieldValue != null) {
					discountRule.setDiscountId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					discountRule.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					discountRule.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					discountRule.setType((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "typeSettings")) {
				if (jsonParserFieldValue != null) {
					discountRule.setTypeSettings((String)jsonParserFieldValue);
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