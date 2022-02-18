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

package com.liferay.digital.signature.rest.client.serdes.v1_0;

import com.liferay.digital.signature.rest.client.dto.v1_0.DSDocument;
import com.liferay.digital.signature.rest.client.dto.v1_0.DSEnvelope;
import com.liferay.digital.signature.rest.client.dto.v1_0.DSRecipient;
import com.liferay.digital.signature.rest.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author José Abelenda
 * @generated
 */
@Generated("")
public class DSEnvelopeSerDes {

	public static DSEnvelope toDTO(String json) {
		DSEnvelopeJSONParser dsEnvelopeJSONParser = new DSEnvelopeJSONParser();

		return dsEnvelopeJSONParser.parseToDTO(json);
	}

	public static DSEnvelope[] toDTOs(String json) {
		DSEnvelopeJSONParser dsEnvelopeJSONParser = new DSEnvelopeJSONParser();

		return dsEnvelopeJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DSEnvelope dsEnvelope) {
		if (dsEnvelope == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (dsEnvelope.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(dsEnvelope.getDateCreated()));

			sb.append("\"");
		}

		if (dsEnvelope.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(dsEnvelope.getDateModified()));

			sb.append("\"");
		}

		if (dsEnvelope.getDsDocument() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dsDocument\": ");

			sb.append("[");

			for (int i = 0; i < dsEnvelope.getDsDocument().length; i++) {
				sb.append(String.valueOf(dsEnvelope.getDsDocument()[i]));

				if ((i + 1) < dsEnvelope.getDsDocument().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (dsEnvelope.getDsRecipient() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dsRecipient\": ");

			sb.append("[");

			for (int i = 0; i < dsEnvelope.getDsRecipient().length; i++) {
				sb.append(String.valueOf(dsEnvelope.getDsRecipient()[i]));

				if ((i + 1) < dsEnvelope.getDsRecipient().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (dsEnvelope.getEmailBlurb() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"emailBlurb\": ");

			sb.append("\"");

			sb.append(_escape(dsEnvelope.getEmailBlurb()));

			sb.append("\"");
		}

		if (dsEnvelope.getEmailSubject() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"emailSubject\": ");

			sb.append("\"");

			sb.append(_escape(dsEnvelope.getEmailSubject()));

			sb.append("\"");
		}

		if (dsEnvelope.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append("\"");

			sb.append(_escape(dsEnvelope.getId()));

			sb.append("\"");
		}

		if (dsEnvelope.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(dsEnvelope.getName()));

			sb.append("\"");
		}

		if (dsEnvelope.getSenderEmailAddress() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"senderEmailAddress\": ");

			sb.append("\"");

			sb.append(_escape(dsEnvelope.getSenderEmailAddress()));

			sb.append("\"");
		}

		if (dsEnvelope.getSiteId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"siteId\": ");

			sb.append(dsEnvelope.getSiteId());
		}

		if (dsEnvelope.getStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append("\"");

			sb.append(_escape(dsEnvelope.getStatus()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DSEnvelopeJSONParser dsEnvelopeJSONParser = new DSEnvelopeJSONParser();

		return dsEnvelopeJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(DSEnvelope dsEnvelope) {
		if (dsEnvelope == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (dsEnvelope.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(dsEnvelope.getDateCreated()));
		}

		if (dsEnvelope.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(dsEnvelope.getDateModified()));
		}

		if (dsEnvelope.getDsDocument() == null) {
			map.put("dsDocument", null);
		}
		else {
			map.put("dsDocument", String.valueOf(dsEnvelope.getDsDocument()));
		}

		if (dsEnvelope.getDsRecipient() == null) {
			map.put("dsRecipient", null);
		}
		else {
			map.put("dsRecipient", String.valueOf(dsEnvelope.getDsRecipient()));
		}

		if (dsEnvelope.getEmailBlurb() == null) {
			map.put("emailBlurb", null);
		}
		else {
			map.put("emailBlurb", String.valueOf(dsEnvelope.getEmailBlurb()));
		}

		if (dsEnvelope.getEmailSubject() == null) {
			map.put("emailSubject", null);
		}
		else {
			map.put(
				"emailSubject", String.valueOf(dsEnvelope.getEmailSubject()));
		}

		if (dsEnvelope.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(dsEnvelope.getId()));
		}

		if (dsEnvelope.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(dsEnvelope.getName()));
		}

		if (dsEnvelope.getSenderEmailAddress() == null) {
			map.put("senderEmailAddress", null);
		}
		else {
			map.put(
				"senderEmailAddress",
				String.valueOf(dsEnvelope.getSenderEmailAddress()));
		}

		if (dsEnvelope.getSiteId() == null) {
			map.put("siteId", null);
		}
		else {
			map.put("siteId", String.valueOf(dsEnvelope.getSiteId()));
		}

		if (dsEnvelope.getStatus() == null) {
			map.put("status", null);
		}
		else {
			map.put("status", String.valueOf(dsEnvelope.getStatus()));
		}

		return map;
	}

	public static class DSEnvelopeJSONParser
		extends BaseJSONParser<DSEnvelope> {

		@Override
		protected DSEnvelope createDTO() {
			return new DSEnvelope();
		}

		@Override
		protected DSEnvelope[] createDTOArray(int size) {
			return new DSEnvelope[size];
		}

		@Override
		protected void setField(
			DSEnvelope dsEnvelope, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					dsEnvelope.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					dsEnvelope.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dsDocument")) {
				if (jsonParserFieldValue != null) {
					dsEnvelope.setDsDocument(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> DSDocumentSerDes.toDTO((String)object)
						).toArray(
							size -> new DSDocument[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dsRecipient")) {
				if (jsonParserFieldValue != null) {
					dsEnvelope.setDsRecipient(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> DSRecipientSerDes.toDTO((String)object)
						).toArray(
							size -> new DSRecipient[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "emailBlurb")) {
				if (jsonParserFieldValue != null) {
					dsEnvelope.setEmailBlurb((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "emailSubject")) {
				if (jsonParserFieldValue != null) {
					dsEnvelope.setEmailSubject((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					dsEnvelope.setId((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					dsEnvelope.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "senderEmailAddress")) {

				if (jsonParserFieldValue != null) {
					dsEnvelope.setSenderEmailAddress(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteId")) {
				if (jsonParserFieldValue != null) {
					dsEnvelope.setSiteId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					dsEnvelope.setStatus((String)jsonParserFieldValue);
				}
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
			sb.append("\": ");

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
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}