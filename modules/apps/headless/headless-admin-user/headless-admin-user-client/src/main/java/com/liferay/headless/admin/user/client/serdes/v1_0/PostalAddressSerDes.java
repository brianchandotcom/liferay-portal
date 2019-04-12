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

import com.liferay.headless.admin.user.client.dto.v1_0.PostalAddress;
import com.liferay.headless.admin.user.client.json.BaseJSONParser;

import java.util.Collection;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class PostalAddressSerDes {

	public static PostalAddress toDTO(String json) {
		PostalAddressJSONParser postalAddressJSONParser =
			new PostalAddressJSONParser();

		return postalAddressJSONParser.parseToDTO(json);
	}

	public static PostalAddress[] toDTOs(String json) {
		PostalAddressJSONParser postalAddressJSONParser =
			new PostalAddressJSONParser();

		return postalAddressJSONParser.parseToDTOs(json);
	}

	public static String toJSON(PostalAddress postalAddress) {
		if (postalAddress == null) {
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"addressCountry\": ");

		if (postalAddress.getAddressCountry() == null) {
			sb.append("null");
		}
		else {
			sb.append(postalAddress.getAddressCountry());
		}

		sb.append(", ");

		sb.append("\"addressLocality\": ");

		if (postalAddress.getAddressLocality() == null) {
			sb.append("null");
		}
		else {
			sb.append(postalAddress.getAddressLocality());
		}

		sb.append(", ");

		sb.append("\"addressRegion\": ");

		if (postalAddress.getAddressRegion() == null) {
			sb.append("null");
		}
		else {
			sb.append(postalAddress.getAddressRegion());
		}

		sb.append(", ");

		sb.append("\"addressType\": ");

		if (postalAddress.getAddressType() == null) {
			sb.append("null");
		}
		else {
			sb.append(postalAddress.getAddressType());
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (postalAddress.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(postalAddress.getId());
		}

		sb.append(", ");

		sb.append("\"postalCode\": ");

		if (postalAddress.getPostalCode() == null) {
			sb.append("null");
		}
		else {
			sb.append(postalAddress.getPostalCode());
		}

		sb.append(", ");

		sb.append("\"primary\": ");

		if (postalAddress.getPrimary() == null) {
			sb.append("null");
		}
		else {
			sb.append(postalAddress.getPrimary());
		}

		sb.append(", ");

		sb.append("\"streetAddressLine1\": ");

		if (postalAddress.getStreetAddressLine1() == null) {
			sb.append("null");
		}
		else {
			sb.append(postalAddress.getStreetAddressLine1());
		}

		sb.append(", ");

		sb.append("\"streetAddressLine2\": ");

		if (postalAddress.getStreetAddressLine2() == null) {
			sb.append("null");
		}
		else {
			sb.append(postalAddress.getStreetAddressLine2());
		}

		sb.append(", ");

		sb.append("\"streetAddressLine3\": ");

		if (postalAddress.getStreetAddressLine3() == null) {
			sb.append("null");
		}
		else {
			sb.append(postalAddress.getStreetAddressLine3());
		}

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(Collection<PostalAddress> postalAddresses) {
		if (postalAddresses == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (PostalAddress postalAddress : postalAddresses) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(postalAddress));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class PostalAddressJSONParser
		extends BaseJSONParser<PostalAddress> {

		protected PostalAddress createDTO() {
			return new PostalAddress();
		}

		protected PostalAddress[] createDTOArray(int size) {
			return new PostalAddress[size];
		}

		protected void setField(
			PostalAddress postalAddress, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "addressCountry")) {
				if (jsonParserFieldValue != null) {
					postalAddress.setAddressCountry(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "addressLocality")) {
				if (jsonParserFieldValue != null) {
					postalAddress.setAddressLocality(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "addressRegion")) {
				if (jsonParserFieldValue != null) {
					postalAddress.setAddressRegion(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "addressType")) {
				if (jsonParserFieldValue != null) {
					postalAddress.setAddressType((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					postalAddress.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "postalCode")) {
				if (jsonParserFieldValue != null) {
					postalAddress.setPostalCode((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "primary")) {
				if (jsonParserFieldValue != null) {
					postalAddress.setPrimary((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "streetAddressLine1")) {

				if (jsonParserFieldValue != null) {
					postalAddress.setStreetAddressLine1(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "streetAddressLine2")) {

				if (jsonParserFieldValue != null) {
					postalAddress.setStreetAddressLine2(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "streetAddressLine3")) {

				if (jsonParserFieldValue != null) {
					postalAddress.setStreetAddressLine3(
						(String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}