/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.client.serdes.v1_0;

import com.liferay.ai.hub.rest.client.dto.v1_0.ProvisioningRequest;
import com.liferay.ai.hub.rest.client.dto.v1_0.UserAccount;
import com.liferay.ai.hub.rest.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Feliphe Marinho
 * @generated
 */
@Generated("")
public class ProvisioningRequestSerDes {

	public static ProvisioningRequest toDTO(String json) {
		ProvisioningRequestJSONParser provisioningRequestJSONParser =
			new ProvisioningRequestJSONParser();

		return provisioningRequestJSONParser.parseToDTO(json);
	}

	public static ProvisioningRequest[] toDTOs(String json) {
		ProvisioningRequestJSONParser provisioningRequestJSONParser =
			new ProvisioningRequestJSONParser();

		return provisioningRequestJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ProvisioningRequest provisioningRequest) {
		if (provisioningRequest == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (provisioningRequest.getAccountEntryExternalReferenceCode() !=
				null) {

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountEntryExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(
					provisioningRequest.
						getAccountEntryExternalReferenceCode()));

			sb.append("\"");
		}

		if (provisioningRequest.getAccountEntryId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountEntryId\": ");

			sb.append(provisioningRequest.getAccountEntryId());
		}

		if (provisioningRequest.getAccountEntryName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountEntryName\": ");

			sb.append("\"");

			sb.append(_escape(provisioningRequest.getAccountEntryName()));

			sb.append("\"");
		}

		if (provisioningRequest.getUserAccounts() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userAccounts\": ");

			sb.append("[");

			for (int i = 0; i < provisioningRequest.getUserAccounts().length;
				 i++) {

				sb.append(
					String.valueOf(provisioningRequest.getUserAccounts()[i]));

				if ((i + 1) < provisioningRequest.getUserAccounts().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ProvisioningRequestJSONParser provisioningRequestJSONParser =
			new ProvisioningRequestJSONParser();

		return provisioningRequestJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ProvisioningRequest provisioningRequest) {

		if (provisioningRequest == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (provisioningRequest.getAccountEntryExternalReferenceCode() ==
				null) {

			map.put("accountEntryExternalReferenceCode", null);
		}
		else {
			map.put(
				"accountEntryExternalReferenceCode",
				String.valueOf(
					provisioningRequest.
						getAccountEntryExternalReferenceCode()));
		}

		if (provisioningRequest.getAccountEntryId() == null) {
			map.put("accountEntryId", null);
		}
		else {
			map.put(
				"accountEntryId",
				String.valueOf(provisioningRequest.getAccountEntryId()));
		}

		if (provisioningRequest.getAccountEntryName() == null) {
			map.put("accountEntryName", null);
		}
		else {
			map.put(
				"accountEntryName",
				String.valueOf(provisioningRequest.getAccountEntryName()));
		}

		if (provisioningRequest.getUserAccounts() == null) {
			map.put("userAccounts", null);
		}
		else {
			map.put(
				"userAccounts",
				String.valueOf(provisioningRequest.getUserAccounts()));
		}

		return map;
	}

	public static class ProvisioningRequestJSONParser
		extends BaseJSONParser<ProvisioningRequest> {

		@Override
		protected ProvisioningRequest createDTO() {
			return new ProvisioningRequest();
		}

		@Override
		protected ProvisioningRequest[] createDTOArray(int size) {
			return new ProvisioningRequest[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(
					jsonParserFieldName, "accountEntryExternalReferenceCode")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "accountEntryId")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "accountEntryName")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "userAccounts")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			ProvisioningRequest provisioningRequest, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(
					jsonParserFieldName, "accountEntryExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					provisioningRequest.setAccountEntryExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "accountEntryId")) {
				if (jsonParserFieldValue != null) {
					provisioningRequest.setAccountEntryId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "accountEntryName")) {
				if (jsonParserFieldValue != null) {
					provisioningRequest.setAccountEntryName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "userAccounts")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					UserAccount[] userAccountsArray =
						new UserAccount[jsonParserFieldValues.length];

					for (int i = 0; i < userAccountsArray.length; i++) {
						userAccountsArray[i] = UserAccountSerDes.toDTO(
							(String)jsonParserFieldValues[i]);
					}

					provisioningRequest.setUserAccounts(userAccountsArray);
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

			sb.append(_toJSON(value));

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _toJSON(Object value) {
		if (value == null) {
			return "null";
		}

		if (value instanceof Map) {
			return _toJSON((Map)value);
		}

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			StringBuilder sb = new StringBuilder("[");

			Object[] values = (Object[])value;

			for (int i = 0; i < values.length; i++) {
				sb.append(_toJSON(values[i]));

				if ((i + 1) < values.length) {
					sb.append(", ");
				}
			}

			sb.append("]");

			return sb.toString();
		}

		if (value instanceof String) {
			return "\"" + _escape(value) + "\"";
		}

		return String.valueOf(value);
	}

}
// LIFERAY-REST-BUILDER-HASH:1878769754