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

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.Option;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class OptionSerDes {

	public static Option toDTO(String json) {
		OptionJSONParser optionJSONParser = new OptionJSONParser();

		return optionJSONParser.parseToDTO(json);
	}

	public static Option[] toDTOs(String json) {
		OptionJSONParser optionJSONParser = new OptionJSONParser();

		return optionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Option option) {
		if (option == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (option.getLabel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"label\":");

			sb.append("\"");

			sb.append(_escape(option.getLabel()));

			sb.append("\"");
		}

		if (option.getValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"value\":");

			sb.append("\"");

			sb.append(_escape(option.getValue()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(Option option) {
		if (option == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (option.getLabel() == null) {
			map.put("label", null);
		}
		else {
			map.put("label", String.valueOf(option.getLabel()));
		}

		if (option.getValue() == null) {
			map.put("value", null);
		}
		else {
			map.put("value", String.valueOf(option.getValue()));
		}

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static class OptionJSONParser extends BaseJSONParser<Option> {

		@Override
		protected Option createDTO() {
			return new Option();
		}

		@Override
		protected Option[] createDTOArray(int size) {
			return new Option[size];
		}

		@Override
		protected void setField(
			Option option, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "label")) {
				if (jsonParserFieldValue != null) {
					option.setLabel((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "value")) {
				if (jsonParserFieldValue != null) {
					option.setValue((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}