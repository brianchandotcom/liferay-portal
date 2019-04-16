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

import com.liferay.headless.admin.user.client.dto.v1_0.ContactInformation;
import com.liferay.headless.admin.user.client.dto.v1_0.EmailAddress;
import com.liferay.headless.admin.user.client.dto.v1_0.Phone;
import com.liferay.headless.admin.user.client.dto.v1_0.PostalAddress;
import com.liferay.headless.admin.user.client.dto.v1_0.WebUrl;
import com.liferay.headless.admin.user.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ContactInformationSerDes {

	public static ContactInformation toDTO(String json) {
		ContactInformationJSONParser contactInformationJSONParser =
			new ContactInformationJSONParser();

		return contactInformationJSONParser.parseToDTO(json);
	}

	public static ContactInformation[] toDTOs(String json) {
		ContactInformationJSONParser contactInformationJSONParser =
			new ContactInformationJSONParser();

		return contactInformationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ContactInformation contactInformation) {
		if (contactInformation == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"emailAddresses\": ");

		if (contactInformation.getEmailAddresses() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < contactInformation.getEmailAddresses().length;
				 i++) {

				sb.append(
					EmailAddressSerDes.toJSON(
						contactInformation.getEmailAddresses()[i]));

				if ((i + 1) < contactInformation.getEmailAddresses().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"facebook\": ");

		if (contactInformation.getFacebook() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(contactInformation.getFacebook());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (contactInformation.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(contactInformation.getId());
		}

		sb.append(", ");

		sb.append("\"jabber\": ");

		if (contactInformation.getJabber() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(contactInformation.getJabber());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"postalAddresses\": ");

		if (contactInformation.getPostalAddresses() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < contactInformation.getPostalAddresses().length;
				 i++) {

				sb.append(
					PostalAddressSerDes.toJSON(
						contactInformation.getPostalAddresses()[i]));

				if ((i + 1) < contactInformation.getPostalAddresses().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"skype\": ");

		if (contactInformation.getSkype() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(contactInformation.getSkype());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"sms\": ");

		if (contactInformation.getSms() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(contactInformation.getSms());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"telephones\": ");

		if (contactInformation.getTelephones() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < contactInformation.getTelephones().length;
				 i++) {

				sb.append(
					PhoneSerDes.toJSON(contactInformation.getTelephones()[i]));

				if ((i + 1) < contactInformation.getTelephones().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"twitter\": ");

		if (contactInformation.getTwitter() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(contactInformation.getTwitter());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"webUrls\": ");

		if (contactInformation.getWebUrls() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < contactInformation.getWebUrls().length; i++) {
				sb.append(
					WebUrlSerDes.toJSON(contactInformation.getWebUrls()[i]));

				if ((i + 1) < contactInformation.getWebUrls().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(
		ContactInformation contactInformation) {

		if (contactInformation == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		map.put(
			"emailAddresses",
			String.valueOf(contactInformation.getEmailAddresses()));

		map.put("facebook", String.valueOf(contactInformation.getFacebook()));

		map.put("id", String.valueOf(contactInformation.getId()));

		map.put("jabber", String.valueOf(contactInformation.getJabber()));

		map.put(
			"postalAddresses",
			String.valueOf(contactInformation.getPostalAddresses()));

		map.put("skype", String.valueOf(contactInformation.getSkype()));

		map.put("sms", String.valueOf(contactInformation.getSms()));

		map.put(
			"telephones", String.valueOf(contactInformation.getTelephones()));

		map.put("twitter", String.valueOf(contactInformation.getTwitter()));

		map.put("webUrls", String.valueOf(contactInformation.getWebUrls()));

		return map;
	}

	private static class ContactInformationJSONParser
		extends BaseJSONParser<ContactInformation> {

		@Override
		protected ContactInformation createDTO() {
			return new ContactInformation();
		}

		@Override
		protected ContactInformation[] createDTOArray(int size) {
			return new ContactInformation[size];
		}

		@Override
		protected void setField(
			ContactInformation contactInformation, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "emailAddresses")) {
				if (jsonParserFieldValue != null) {
					contactInformation.setEmailAddresses(
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
					contactInformation.setFacebook(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					contactInformation.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "jabber")) {
				if (jsonParserFieldValue != null) {
					contactInformation.setJabber((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "postalAddresses")) {
				if (jsonParserFieldValue != null) {
					contactInformation.setPostalAddresses(
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
					contactInformation.setSkype((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "sms")) {
				if (jsonParserFieldValue != null) {
					contactInformation.setSms((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "telephones")) {
				if (jsonParserFieldValue != null) {
					contactInformation.setTelephones(
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
					contactInformation.setTwitter((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "webUrls")) {
				if (jsonParserFieldValue != null) {
					contactInformation.setWebUrls(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> WebUrlSerDes.toDTO((String)object)
						).toArray(
							size -> new WebUrl[size]
						));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}