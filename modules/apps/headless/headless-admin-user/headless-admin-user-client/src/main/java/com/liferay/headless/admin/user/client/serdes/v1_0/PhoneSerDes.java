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

import com.liferay.headless.admin.user.client.dto.v1_0.Phone;
import com.liferay.headless.admin.user.client.json.BaseJSONParser;

import java.util.Collection;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class PhoneSerDes {

	public static Phone toDTO(String json) {
		PhoneJSONParser phoneJSONParser = new PhoneJSONParser();

		return phoneJSONParser.parseToDTO(json);
	}

	public static Phone[] toDTOs(String json) {
		PhoneJSONParser phoneJSONParser = new PhoneJSONParser();

		return phoneJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Phone phone) {
		if (phone == null) {
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"extension\": ");

		if (phone.getExtension() == null) {
			sb.append("null");
		}
		else {
			sb.append(phone.getExtension());
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (phone.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(phone.getId());
		}

		sb.append(", ");

		sb.append("\"phoneNumber\": ");

		if (phone.getPhoneNumber() == null) {
			sb.append("null");
		}
		else {
			sb.append(phone.getPhoneNumber());
		}

		sb.append(", ");

		sb.append("\"phoneType\": ");

		if (phone.getPhoneType() == null) {
			sb.append("null");
		}
		else {
			sb.append(phone.getPhoneType());
		}

		sb.append(", ");

		sb.append("\"primary\": ");

		if (phone.getPrimary() == null) {
			sb.append("null");
		}
		else {
			sb.append(phone.getPrimary());
		}

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(Collection<Phone> phones) {
		if (phones == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (Phone phone : phones) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(phone));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class PhoneJSONParser extends BaseJSONParser<Phone> {

		protected Phone createDTO() {
			return new Phone();
		}

		protected Phone[] createDTOArray(int size) {
			return new Phone[size];
		}

		protected void setField(
			Phone phone, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "extension")) {
				if (jsonParserFieldValue != null) {
					phone.setExtension((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					phone.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "phoneNumber")) {
				if (jsonParserFieldValue != null) {
					phone.setPhoneNumber((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "phoneType")) {
				if (jsonParserFieldValue != null) {
					phone.setPhoneType((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "primary")) {
				if (jsonParserFieldValue != null) {
					phone.setPrimary((Boolean)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}