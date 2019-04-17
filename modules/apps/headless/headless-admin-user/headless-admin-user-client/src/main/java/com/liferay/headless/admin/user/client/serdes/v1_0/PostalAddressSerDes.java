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

import java.util.HashMap;
import java.util.Map;
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
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (postalAddress.getAddressCountry() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"addressCountry\":");

			sb.append("\"");

			sb.append(postalAddress.getAddressCountry());

			sb.append("\"");
		}

		if (postalAddress.getAddressLocality() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"addressLocality\":");

			sb.append("\"");

			sb.append(postalAddress.getAddressLocality());

			sb.append("\"");
		}

		if (postalAddress.getAddressRegion() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"addressRegion\":");

			sb.append("\"");

			sb.append(postalAddress.getAddressRegion());

			sb.append("\"");
		}

		if (postalAddress.getAddressType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"addressType\":");

			sb.append("\"");

			sb.append(postalAddress.getAddressType());

			sb.append("\"");
		}

		if (postalAddress.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\":");

			sb.append(postalAddress.getId());
		}

		if (postalAddress.getPostalCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"postalCode\":");

			sb.append("\"");

			sb.append(postalAddress.getPostalCode());

			sb.append("\"");
		}

		if (postalAddress.getPrimary() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"primary\":");

			sb.append(postalAddress.getPrimary());
		}

		if (postalAddress.getStreetAddressLine1() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"streetAddressLine1\":");

			sb.append("\"");

			sb.append(postalAddress.getStreetAddressLine1());

			sb.append("\"");
		}

		if (postalAddress.getStreetAddressLine2() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"streetAddressLine2\":");

			sb.append("\"");

			sb.append(postalAddress.getStreetAddressLine2());

			sb.append("\"");
		}

		if (postalAddress.getStreetAddressLine3() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"streetAddressLine3\":");

			sb.append("\"");

			sb.append(postalAddress.getStreetAddressLine3());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(PostalAddress postalAddress) {
		if (postalAddress == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (postalAddress.getAddressCountry() == null) {
			map.put("addressCountry", null);
		}
		else {
			map.put(
				"addressCountry",
				String.valueOf(postalAddress.getAddressCountry()));
		}

		if (postalAddress.getAddressLocality() == null) {
			map.put("addressLocality", null);
		}
		else {
			map.put(
				"addressLocality",
				String.valueOf(postalAddress.getAddressLocality()));
		}

		if (postalAddress.getAddressRegion() == null) {
			map.put("addressRegion", null);
		}
		else {
			map.put(
				"addressRegion",
				String.valueOf(postalAddress.getAddressRegion()));
		}

		if (postalAddress.getAddressType() == null) {
			map.put("addressType", null);
		}
		else {
			map.put(
				"addressType", String.valueOf(postalAddress.getAddressType()));
		}

		if (postalAddress.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(postalAddress.getId()));
		}

		if (postalAddress.getPostalCode() == null) {
			map.put("postalCode", null);
		}
		else {
			map.put(
				"postalCode", String.valueOf(postalAddress.getPostalCode()));
		}

		if (postalAddress.getPrimary() == null) {
			map.put("primary", null);
		}
		else {
			map.put("primary", String.valueOf(postalAddress.getPrimary()));
		}

		if (postalAddress.getStreetAddressLine1() == null) {
			map.put("streetAddressLine1", null);
		}
		else {
			map.put(
				"streetAddressLine1",
				String.valueOf(postalAddress.getStreetAddressLine1()));
		}

		if (postalAddress.getStreetAddressLine2() == null) {
			map.put("streetAddressLine2", null);
		}
		else {
			map.put(
				"streetAddressLine2",
				String.valueOf(postalAddress.getStreetAddressLine2()));
		}

		if (postalAddress.getStreetAddressLine3() == null) {
			map.put("streetAddressLine3", null);
		}
		else {
			map.put(
				"streetAddressLine3",
				String.valueOf(postalAddress.getStreetAddressLine3()));
		}

		return map;
	}

	private static class PostalAddressJSONParser
		extends BaseJSONParser<PostalAddress> {

		@Override
		protected PostalAddress createDTO() {
			return new PostalAddress();
		}

		@Override
		protected PostalAddress[] createDTOArray(int size) {
			return new PostalAddress[size];
		}

		@Override
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