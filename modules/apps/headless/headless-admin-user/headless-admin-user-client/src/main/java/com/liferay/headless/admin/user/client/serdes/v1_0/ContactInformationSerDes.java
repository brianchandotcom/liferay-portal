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
import com.liferay.headless.admin.user.client.dto.v1_0.Email;
import com.liferay.headless.admin.user.client.dto.v1_0.Phone;
import com.liferay.headless.admin.user.client.dto.v1_0.PostalAddress;
import com.liferay.headless.admin.user.client.dto.v1_0.WebUrl;
import com.liferay.headless.admin.user.client.json.BaseJSONParser;

import java.util.Collection;
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
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"emails\": ");

		if (contactInformation.getEmails() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < contactInformation.getEmails().length; i++) {
				sb.append(contactInformation.getEmails()[i]);

				if ((i + 1) < contactInformation.getEmails().length) {
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
			sb.append(contactInformation.getFacebook());
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
			sb.append(contactInformation.getJabber());
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

				sb.append(contactInformation.getPostalAddresses()[i]);

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
			sb.append(contactInformation.getSkype());
		}

		sb.append(", ");

		sb.append("\"sms\": ");

		if (contactInformation.getSms() == null) {
			sb.append("null");
		}
		else {
			sb.append(contactInformation.getSms());
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

				sb.append(contactInformation.getTelephones()[i]);

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
			sb.append(contactInformation.getTwitter());
		}

		sb.append(", ");

		sb.append("\"webUrls\": ");

		if (contactInformation.getWebUrls() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < contactInformation.getWebUrls().length; i++) {
				sb.append(contactInformation.getWebUrls()[i]);

				if ((i + 1) < contactInformation.getWebUrls().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(
		Collection<ContactInformation> contactInformations) {

		if (contactInformations == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (ContactInformation contactInformation : contactInformations) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(contactInformation));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class ContactInformationJSONParser
		extends BaseJSONParser<ContactInformation> {

		protected ContactInformation createDTO() {
			return new ContactInformation();
		}

		protected ContactInformation[] createDTOArray(int size) {
			return new ContactInformation[size];
		}

		protected void setField(
			ContactInformation contactInformation, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "emails")) {
				if (jsonParserFieldValue != null) {
					contactInformation.setEmails(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> EmailSerDes.toDTO((String)object)
						).toArray(
							size -> new Email[size]
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