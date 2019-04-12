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

package com.liferay.headless.admin.user.client.serdes.v1_0;

import com.liferay.headless.admin.user.client.dto.v1_0.Email;
import com.liferay.headless.admin.user.client.json.BaseJSONParser;

import java.util.Collection;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class EmailSerDes {

	public static Email toDTO(String json) {
		EmailJSONParser emailJSONParser = new EmailJSONParser();

		return emailJSONParser.parseToDTO(json);
	}

	public static Email[] toDTOs(String json) {
		EmailJSONParser emailJSONParser = new EmailJSONParser();

		return emailJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Email email) {
		if (email == null) {
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"email\": ");

		if (email.getEmail() == null) {
			sb.append("null");
		}
		else {
			sb.append(email.getEmail());
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (email.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(email.getId());
		}

		sb.append(", ");

		sb.append("\"primary\": ");

		if (email.getPrimary() == null) {
			sb.append("null");
		}
		else {
			sb.append(email.getPrimary());
		}

		sb.append(", ");

		sb.append("\"type\": ");

		if (email.getType() == null) {
			sb.append("null");
		}
		else {
			sb.append(email.getType());
		}

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(Collection<Email> emails) {
		if (emails == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (Email email : emails) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(email));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class EmailJSONParser extends BaseJSONParser<Email> {

		protected Email createDTO() {
			return new Email();
		}

		protected Email[] createDTOArray(int size) {
			return new Email[size];
		}

		protected void setField(
			Email email, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "email")) {
				if (jsonParserFieldValue != null) {
					email.setEmail((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					email.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "primary")) {
				if (jsonParserFieldValue != null) {
					email.setPrimary((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					email.setType((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}