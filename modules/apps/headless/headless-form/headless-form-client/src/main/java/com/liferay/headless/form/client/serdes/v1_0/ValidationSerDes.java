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

package com.liferay.headless.form.client.serdes.v1_0;

import com.liferay.headless.form.client.dto.v1_0.Validation;
import com.liferay.headless.form.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ValidationSerDes {

	public static Validation toDTO(String json) {
		ValidationJSONParser validationJSONParser = new ValidationJSONParser();

		return validationJSONParser.parseToDTO(json);
	}

	public static Validation[] toDTOs(String json) {
		ValidationJSONParser validationJSONParser = new ValidationJSONParser();

		return validationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Validation validation) {
		if (validation == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (validation.getErrorMessage() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"errorMessage\":");

			sb.append("\"");

			sb.append(validation.getErrorMessage());

			sb.append("\"");
		}

		if (validation.getExpression() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"expression\":");

			sb.append("\"");

			sb.append(validation.getExpression());

			sb.append("\"");
		}

		if (validation.getId() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"id\":");

			sb.append(validation.getId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(Validation validation) {
		if (validation == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (validation.getErrorMessage() == null) {
			map.put("errorMessage", null);
		}
		else {
			map.put(
				"errorMessage", String.valueOf(validation.getErrorMessage()));
		}

		if (validation.getExpression() == null) {
			map.put("expression", null);
		}
		else {
			map.put("expression", String.valueOf(validation.getExpression()));
		}

		if (validation.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(validation.getId()));
		}

		return map;
	}

	private static class ValidationJSONParser
		extends BaseJSONParser<Validation> {

		@Override
		protected Validation createDTO() {
			return new Validation();
		}

		@Override
		protected Validation[] createDTOArray(int size) {
			return new Validation[size];
		}

		@Override
		protected void setField(
			Validation validation, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "errorMessage")) {
				if (jsonParserFieldValue != null) {
					validation.setErrorMessage((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "expression")) {
				if (jsonParserFieldValue != null) {
					validation.setExpression((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					validation.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}