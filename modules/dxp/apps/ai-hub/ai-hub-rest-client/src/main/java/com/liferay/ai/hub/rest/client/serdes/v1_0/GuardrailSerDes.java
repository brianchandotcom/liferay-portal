/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.client.serdes.v1_0;

import com.liferay.ai.hub.rest.client.dto.v1_0.Guardrail;
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
public class GuardrailSerDes {

	public static Guardrail toDTO(String json) {
		GuardrailJSONParser guardrailJSONParser = new GuardrailJSONParser();

		return guardrailJSONParser.parseToDTO(json);
	}

	public static Guardrail[] toDTOs(String json) {
		GuardrailJSONParser guardrailJSONParser = new GuardrailJSONParser();

		return guardrailJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Guardrail guardrail) {
		if (guardrail == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (guardrail.getActive() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"active\": ");

			sb.append(guardrail.getActive());
		}

		if (guardrail.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(guardrail.getDescription()));

			sb.append("\"");
		}

		if (guardrail.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(guardrail.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (guardrail.getGuardrailType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"guardrailType\": ");

			sb.append("\"");
			sb.append(guardrail.getGuardrailType());
			sb.append("\"");
		}

		if (guardrail.getLocation() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"location\": ");

			sb.append("\"");

			sb.append(_escape(guardrail.getLocation()));

			sb.append("\"");
		}

		if (guardrail.getMaliciousUriFilterEnabled() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"maliciousUriFilterEnabled\": ");

			sb.append(guardrail.getMaliciousUriFilterEnabled());
		}

		if (guardrail.getMultilanguageDetectionEnabled() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"multilanguageDetectionEnabled\": ");

			sb.append(guardrail.getMultilanguageDetectionEnabled());
		}

		if (guardrail.getPiAndJailbreakConfidenceLevel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"piAndJailbreakConfidenceLevel\": ");

			sb.append("\"");
			sb.append(guardrail.getPiAndJailbreakConfidenceLevel());
			sb.append("\"");
		}

		if (guardrail.getPiAndJailbreakFilterEnabled() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"piAndJailbreakFilterEnabled\": ");

			sb.append(guardrail.getPiAndJailbreakFilterEnabled());
		}

		if (guardrail.getRaiDangerousLevel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"raiDangerousLevel\": ");

			sb.append("\"");
			sb.append(guardrail.getRaiDangerousLevel());
			sb.append("\"");
		}

		if (guardrail.getRaiHarassmentLevel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"raiHarassmentLevel\": ");

			sb.append("\"");
			sb.append(guardrail.getRaiHarassmentLevel());
			sb.append("\"");
		}

		if (guardrail.getRaiHateSpeechLevel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"raiHateSpeechLevel\": ");

			sb.append("\"");
			sb.append(guardrail.getRaiHateSpeechLevel());
			sb.append("\"");
		}

		if (guardrail.getRaiSexuallyExplicitLevel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"raiSexuallyExplicitLevel\": ");

			sb.append("\"");
			sb.append(guardrail.getRaiSexuallyExplicitLevel());
			sb.append("\"");
		}

		if (guardrail.getSdpFilterEnabled() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sdpFilterEnabled\": ");

			sb.append(guardrail.getSdpFilterEnabled());
		}

		if (guardrail.getTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			sb.append("\"");

			sb.append(_escape(guardrail.getTitle()));

			sb.append("\"");
		}

		if (guardrail.getTitle_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title_i18n\": ");

			sb.append(_toJSON(guardrail.getTitle_i18n()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		GuardrailJSONParser guardrailJSONParser = new GuardrailJSONParser();

		return guardrailJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Guardrail guardrail) {
		if (guardrail == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (guardrail.getActive() == null) {
			map.put("active", null);
		}
		else {
			map.put("active", String.valueOf(guardrail.getActive()));
		}

		if (guardrail.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put("description", String.valueOf(guardrail.getDescription()));
		}

		if (guardrail.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(guardrail.getExternalReferenceCode()));
		}

		if (guardrail.getGuardrailType() == null) {
			map.put("guardrailType", null);
		}
		else {
			map.put(
				"guardrailType", String.valueOf(guardrail.getGuardrailType()));
		}

		if (guardrail.getLocation() == null) {
			map.put("location", null);
		}
		else {
			map.put("location", String.valueOf(guardrail.getLocation()));
		}

		if (guardrail.getMaliciousUriFilterEnabled() == null) {
			map.put("maliciousUriFilterEnabled", null);
		}
		else {
			map.put(
				"maliciousUriFilterEnabled",
				String.valueOf(guardrail.getMaliciousUriFilterEnabled()));
		}

		if (guardrail.getMultilanguageDetectionEnabled() == null) {
			map.put("multilanguageDetectionEnabled", null);
		}
		else {
			map.put(
				"multilanguageDetectionEnabled",
				String.valueOf(guardrail.getMultilanguageDetectionEnabled()));
		}

		if (guardrail.getPiAndJailbreakConfidenceLevel() == null) {
			map.put("piAndJailbreakConfidenceLevel", null);
		}
		else {
			map.put(
				"piAndJailbreakConfidenceLevel",
				String.valueOf(guardrail.getPiAndJailbreakConfidenceLevel()));
		}

		if (guardrail.getPiAndJailbreakFilterEnabled() == null) {
			map.put("piAndJailbreakFilterEnabled", null);
		}
		else {
			map.put(
				"piAndJailbreakFilterEnabled",
				String.valueOf(guardrail.getPiAndJailbreakFilterEnabled()));
		}

		if (guardrail.getRaiDangerousLevel() == null) {
			map.put("raiDangerousLevel", null);
		}
		else {
			map.put(
				"raiDangerousLevel",
				String.valueOf(guardrail.getRaiDangerousLevel()));
		}

		if (guardrail.getRaiHarassmentLevel() == null) {
			map.put("raiHarassmentLevel", null);
		}
		else {
			map.put(
				"raiHarassmentLevel",
				String.valueOf(guardrail.getRaiHarassmentLevel()));
		}

		if (guardrail.getRaiHateSpeechLevel() == null) {
			map.put("raiHateSpeechLevel", null);
		}
		else {
			map.put(
				"raiHateSpeechLevel",
				String.valueOf(guardrail.getRaiHateSpeechLevel()));
		}

		if (guardrail.getRaiSexuallyExplicitLevel() == null) {
			map.put("raiSexuallyExplicitLevel", null);
		}
		else {
			map.put(
				"raiSexuallyExplicitLevel",
				String.valueOf(guardrail.getRaiSexuallyExplicitLevel()));
		}

		if (guardrail.getSdpFilterEnabled() == null) {
			map.put("sdpFilterEnabled", null);
		}
		else {
			map.put(
				"sdpFilterEnabled",
				String.valueOf(guardrail.getSdpFilterEnabled()));
		}

		if (guardrail.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put("title", String.valueOf(guardrail.getTitle()));
		}

		if (guardrail.getTitle_i18n() == null) {
			map.put("title_i18n", null);
		}
		else {
			map.put("title_i18n", String.valueOf(guardrail.getTitle_i18n()));
		}

		return map;
	}

	public static class GuardrailJSONParser extends BaseJSONParser<Guardrail> {

		@Override
		protected Guardrail createDTO() {
			return new Guardrail();
		}

		@Override
		protected Guardrail[] createDTOArray(int size) {
			return new Guardrail[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "active")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "guardrailType")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "location")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "maliciousUriFilterEnabled")) {

				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "multilanguageDetectionEnabled")) {

				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "piAndJailbreakConfidenceLevel")) {

				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "piAndJailbreakFilterEnabled")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "raiDangerousLevel")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "raiHarassmentLevel")) {

				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "raiHateSpeechLevel")) {

				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "raiSexuallyExplicitLevel")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "sdpFilterEnabled")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "title_i18n")) {
				return true;
			}

			return false;
		}

		@Override
		protected void setField(
			Guardrail guardrail, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "active")) {
				if (jsonParserFieldValue != null) {
					guardrail.setActive((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					guardrail.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					guardrail.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "guardrailType")) {
				if (jsonParserFieldValue != null) {
					guardrail.setGuardrailType(
						Guardrail.GuardrailType.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "location")) {
				if (jsonParserFieldValue != null) {
					guardrail.setLocation((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "maliciousUriFilterEnabled")) {

				if (jsonParserFieldValue != null) {
					guardrail.setMaliciousUriFilterEnabled(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "multilanguageDetectionEnabled")) {

				if (jsonParserFieldValue != null) {
					guardrail.setMultilanguageDetectionEnabled(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "piAndJailbreakConfidenceLevel")) {

				if (jsonParserFieldValue != null) {
					guardrail.setPiAndJailbreakConfidenceLevel(
						Guardrail.PiAndJailbreakConfidenceLevel.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "piAndJailbreakFilterEnabled")) {

				if (jsonParserFieldValue != null) {
					guardrail.setPiAndJailbreakFilterEnabled(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "raiDangerousLevel")) {
				if (jsonParserFieldValue != null) {
					guardrail.setRaiDangerousLevel(
						Guardrail.RaiDangerousLevel.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "raiHarassmentLevel")) {

				if (jsonParserFieldValue != null) {
					guardrail.setRaiHarassmentLevel(
						Guardrail.RaiHarassmentLevel.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "raiHateSpeechLevel")) {

				if (jsonParserFieldValue != null) {
					guardrail.setRaiHateSpeechLevel(
						Guardrail.RaiHateSpeechLevel.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "raiSexuallyExplicitLevel")) {

				if (jsonParserFieldValue != null) {
					guardrail.setRaiSexuallyExplicitLevel(
						Guardrail.RaiSexuallyExplicitLevel.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "sdpFilterEnabled")) {
				if (jsonParserFieldValue != null) {
					guardrail.setSdpFilterEnabled(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					guardrail.setTitle((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title_i18n")) {
				if (jsonParserFieldValue != null) {
					guardrail.setTitle_i18n(
						(Map<String, String>)jsonParserFieldValue);
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
// LIFERAY-REST-BUILDER-HASH:-417826116