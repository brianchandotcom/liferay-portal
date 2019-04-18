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

import java.util.HashMap;
import java.util.Map;
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
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (phone.getExtension() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"extension\":");

			sb.append("\"");

			sb.append(_escape(phone.getExtension()));

			sb.append("\"");
		}

		if (phone.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\":");

			sb.append(phone.getId());
		}

		if (phone.getPhoneNumber() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"phoneNumber\":");

			sb.append("\"");

			sb.append(_escape(phone.getPhoneNumber()));

			sb.append("\"");
		}

		if (phone.getPhoneType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"phoneType\":");

			sb.append("\"");

			sb.append(_escape(phone.getPhoneType()));

			sb.append("\"");
		}

		if (phone.getPrimary() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"primary\":");

			sb.append(phone.getPrimary());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(Phone phone) {
		if (phone == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (phone.getExtension() == null) {
			map.put("extension", null);
		}
		else {
			map.put("extension", String.valueOf(phone.getExtension()));
		}

		if (phone.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(phone.getId()));
		}

		if (phone.getPhoneNumber() == null) {
			map.put("phoneNumber", null);
		}
		else {
			map.put("phoneNumber", String.valueOf(phone.getPhoneNumber()));
		}

		if (phone.getPhoneType() == null) {
			map.put("phoneType", null);
		}
		else {
			map.put("phoneType", String.valueOf(phone.getPhoneType()));
		}

		if (phone.getPrimary() == null) {
			map.put("primary", null);
		}
		else {
			map.put("primary", String.valueOf(phone.getPrimary()));
		}

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static class PhoneJSONParser extends BaseJSONParser<Phone> {

		@Override
		protected Phone createDTO() {
			return new Phone();
		}

		@Override
		protected Phone[] createDTOArray(int size) {
			return new Phone[size];
		}

		@Override
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