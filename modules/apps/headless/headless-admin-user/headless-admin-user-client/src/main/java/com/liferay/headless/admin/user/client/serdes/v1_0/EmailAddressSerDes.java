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

import com.liferay.headless.admin.user.client.dto.v1_0.EmailAddress;
import com.liferay.headless.admin.user.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class EmailAddressSerDes {

	public static EmailAddress toDTO(String json) {
		EmailAddressJSONParser emailAddressJSONParser =
			new EmailAddressJSONParser();

		return emailAddressJSONParser.parseToDTO(json);
	}

	public static EmailAddress[] toDTOs(String json) {
		EmailAddressJSONParser emailAddressJSONParser =
			new EmailAddressJSONParser();

		return emailAddressJSONParser.parseToDTOs(json);
	}

	public static String toJSON(EmailAddress emailAddress) {
		if (emailAddress == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (emailAddress.getEmailAddress() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"emailAddress\":");

			sb.append("\"");

			sb.append(_escape(emailAddress.getEmailAddress()));

			sb.append("\"");
		}

		if (emailAddress.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\":");

			sb.append(emailAddress.getId());
		}

		if (emailAddress.getPrimary() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"primary\":");

			sb.append(emailAddress.getPrimary());
		}

		if (emailAddress.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\":");

			sb.append("\"");

			sb.append(_escape(emailAddress.getType()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(EmailAddress emailAddress) {
		if (emailAddress == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (emailAddress.getEmailAddress() == null) {
			map.put("emailAddress", null);
		}
		else {
			map.put(
				"emailAddress", String.valueOf(emailAddress.getEmailAddress()));
		}

		if (emailAddress.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(emailAddress.getId()));
		}

		if (emailAddress.getPrimary() == null) {
			map.put("primary", null);
		}
		else {
			map.put("primary", String.valueOf(emailAddress.getPrimary()));
		}

		if (emailAddress.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(emailAddress.getType()));
		}

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static class EmailAddressJSONParser
		extends BaseJSONParser<EmailAddress> {

		@Override
		protected EmailAddress createDTO() {
			return new EmailAddress();
		}

		@Override
		protected EmailAddress[] createDTOArray(int size) {
			return new EmailAddress[size];
		}

		@Override
		protected void setField(
			EmailAddress emailAddress, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "emailAddress")) {
				if (jsonParserFieldValue != null) {
					emailAddress.setEmailAddress((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					emailAddress.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "primary")) {
				if (jsonParserFieldValue != null) {
					emailAddress.setPrimary((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					emailAddress.setType((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}