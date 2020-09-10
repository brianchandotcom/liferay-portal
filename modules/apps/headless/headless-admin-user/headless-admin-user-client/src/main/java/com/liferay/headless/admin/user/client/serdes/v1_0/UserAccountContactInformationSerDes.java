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
import com.liferay.headless.admin.user.client.dto.v1_0.Phone;
import com.liferay.headless.admin.user.client.dto.v1_0.PostalAddress;
import com.liferay.headless.admin.user.client.dto.v1_0.UserAccountContactInformation;
import com.liferay.headless.admin.user.client.dto.v1_0.WebUrl;
import com.liferay.headless.admin.user.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class UserAccountContactInformationSerDes {

	public static UserAccountContactInformation toDTO(String json) {
		UserAccountContactInformationJSONParser
			userAccountContactInformationJSONParser =
				new UserAccountContactInformationJSONParser();

		return userAccountContactInformationJSONParser.parseToDTO(json);
	}

	public static UserAccountContactInformation[] toDTOs(String json) {
		UserAccountContactInformationJSONParser
			userAccountContactInformationJSONParser =
				new UserAccountContactInformationJSONParser();

		return userAccountContactInformationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		UserAccountContactInformation userAccountContactInformation) {

		if (userAccountContactInformation == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (userAccountContactInformation.getEmailAddresses() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"emailAddresses\": ");

			sb.append("[");

			for (int i = 0;
				 i < userAccountContactInformation.getEmailAddresses().length;
				 i++) {

				sb.append(
					String.valueOf(
						userAccountContactInformation.getEmailAddresses()[i]));

				if ((i + 1) <
						userAccountContactInformation.
							getEmailAddresses().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (userAccountContactInformation.getFacebook() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"facebook\": ");

			sb.append("\"");

			sb.append(_escape(userAccountContactInformation.getFacebook()));

			sb.append("\"");
		}

		if (userAccountContactInformation.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(userAccountContactInformation.getId());
		}

		if (userAccountContactInformation.getJabber() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"jabber\": ");

			sb.append("\"");

			sb.append(_escape(userAccountContactInformation.getJabber()));

			sb.append("\"");
		}

		if (userAccountContactInformation.getPostalAddresses() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"postalAddresses\": ");

			sb.append("[");

			for (int i = 0;
				 i < userAccountContactInformation.getPostalAddresses().length;
				 i++) {

				sb.append(
					String.valueOf(
						userAccountContactInformation.getPostalAddresses()[i]));

				if ((i + 1) <
						userAccountContactInformation.
							getPostalAddresses().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (userAccountContactInformation.getSkype() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"skype\": ");

			sb.append("\"");

			sb.append(_escape(userAccountContactInformation.getSkype()));

			sb.append("\"");
		}

		if (userAccountContactInformation.getSms() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sms\": ");

			sb.append("\"");

			sb.append(_escape(userAccountContactInformation.getSms()));

			sb.append("\"");
		}

		if (userAccountContactInformation.getTelephones() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"telephones\": ");

			sb.append("[");

			for (int i = 0;
				 i < userAccountContactInformation.getTelephones().length;
				 i++) {

				sb.append(
					String.valueOf(
						userAccountContactInformation.getTelephones()[i]));

				if ((i + 1) <
						userAccountContactInformation.getTelephones().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (userAccountContactInformation.getTwitter() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"twitter\": ");

			sb.append("\"");

			sb.append(_escape(userAccountContactInformation.getTwitter()));

			sb.append("\"");
		}

		if (userAccountContactInformation.getWebUrls() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"webUrls\": ");

			sb.append("[");

			for (int i = 0;
				 i < userAccountContactInformation.getWebUrls().length; i++) {

				sb.append(
					String.valueOf(
						userAccountContactInformation.getWebUrls()[i]));

				if ((i + 1) <
						userAccountContactInformation.getWebUrls().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		UserAccountContactInformationJSONParser
			userAccountContactInformationJSONParser =
				new UserAccountContactInformationJSONParser();

		return userAccountContactInformationJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		UserAccountContactInformation userAccountContactInformation) {

		if (userAccountContactInformation == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (userAccountContactInformation.getEmailAddresses() == null) {
			map.put("emailAddresses", null);
		}
		else {
			map.put(
				"emailAddresses",
				String.valueOf(
					userAccountContactInformation.getEmailAddresses()));
		}

		if (userAccountContactInformation.getFacebook() == null) {
			map.put("facebook", null);
		}
		else {
			map.put(
				"facebook",
				String.valueOf(userAccountContactInformation.getFacebook()));
		}

		if (userAccountContactInformation.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put(
				"id", String.valueOf(userAccountContactInformation.getId()));
		}

		if (userAccountContactInformation.getJabber() == null) {
			map.put("jabber", null);
		}
		else {
			map.put(
				"jabber",
				String.valueOf(userAccountContactInformation.getJabber()));
		}

		if (userAccountContactInformation.getPostalAddresses() == null) {
			map.put("postalAddresses", null);
		}
		else {
			map.put(
				"postalAddresses",
				String.valueOf(
					userAccountContactInformation.getPostalAddresses()));
		}

		if (userAccountContactInformation.getSkype() == null) {
			map.put("skype", null);
		}
		else {
			map.put(
				"skype",
				String.valueOf(userAccountContactInformation.getSkype()));
		}

		if (userAccountContactInformation.getSms() == null) {
			map.put("sms", null);
		}
		else {
			map.put(
				"sms", String.valueOf(userAccountContactInformation.getSms()));
		}

		if (userAccountContactInformation.getTelephones() == null) {
			map.put("telephones", null);
		}
		else {
			map.put(
				"telephones",
				String.valueOf(userAccountContactInformation.getTelephones()));
		}

		if (userAccountContactInformation.getTwitter() == null) {
			map.put("twitter", null);
		}
		else {
			map.put(
				"twitter",
				String.valueOf(userAccountContactInformation.getTwitter()));
		}

		if (userAccountContactInformation.getWebUrls() == null) {
			map.put("webUrls", null);
		}
		else {
			map.put(
				"webUrls",
				String.valueOf(userAccountContactInformation.getWebUrls()));
		}

		return map;
	}

	public static class UserAccountContactInformationJSONParser
		extends BaseJSONParser<UserAccountContactInformation> {

		@Override
		protected UserAccountContactInformation createDTO() {
			return new UserAccountContactInformation();
		}

		@Override
		protected UserAccountContactInformation[] createDTOArray(int size) {
			return new UserAccountContactInformation[size];
		}

		@Override
		protected void setField(
			UserAccountContactInformation userAccountContactInformation,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "emailAddresses")) {
				if (jsonParserFieldValue != null) {
					userAccountContactInformation.setEmailAddresses(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> EmailAddressSerDes.toDTO((String)object)
						).toArray(
							size -> new EmailAddress[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "facebook")) {
				if (jsonParserFieldValue != null) {
					userAccountContactInformation.setFacebook(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					userAccountContactInformation.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "jabber")) {
				if (jsonParserFieldValue != null) {
					userAccountContactInformation.setJabber(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "postalAddresses")) {
				if (jsonParserFieldValue != null) {
					userAccountContactInformation.setPostalAddresses(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> PostalAddressSerDes.toDTO((String)object)
						).toArray(
							size -> new PostalAddress[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "skype")) {
				if (jsonParserFieldValue != null) {
					userAccountContactInformation.setSkype(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "sms")) {
				if (jsonParserFieldValue != null) {
					userAccountContactInformation.setSms(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "telephones")) {
				if (jsonParserFieldValue != null) {
					userAccountContactInformation.setTelephones(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> PhoneSerDes.toDTO((String)object)
						).toArray(
							size -> new Phone[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "twitter")) {
				if (jsonParserFieldValue != null) {
					userAccountContactInformation.setTwitter(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "webUrls")) {
				if (jsonParserFieldValue != null) {
					userAccountContactInformation.setWebUrls(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> WebUrlSerDes.toDTO((String)object)
						).toArray(
							size -> new WebUrl[size]
						));
				}
			}
			else if (jsonParserFieldName.equals("status")) {
				throw new IllegalArgumentException();
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
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}