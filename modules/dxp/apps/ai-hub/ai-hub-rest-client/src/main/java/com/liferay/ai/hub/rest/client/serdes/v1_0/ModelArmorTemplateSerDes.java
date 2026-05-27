/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.client.serdes.v1_0;

import com.liferay.ai.hub.rest.client.dto.v1_0.ModelArmorTemplate;
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
public class ModelArmorTemplateSerDes {

	public static ModelArmorTemplate toDTO(String json) {
		ModelArmorTemplateJSONParser modelArmorTemplateJSONParser =
			new ModelArmorTemplateJSONParser();

		return modelArmorTemplateJSONParser.parseToDTO(json);
	}

	public static ModelArmorTemplate[] toDTOs(String json) {
		ModelArmorTemplateJSONParser modelArmorTemplateJSONParser =
			new ModelArmorTemplateJSONParser();

		return modelArmorTemplateJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ModelArmorTemplate modelArmorTemplate) {
		if (modelArmorTemplate == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (modelArmorTemplate.getActive() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"active\": ");

			sb.append(modelArmorTemplate.getActive());
		}

		if (modelArmorTemplate.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(modelArmorTemplate.getDescription()));

			sb.append("\"");
		}

		if (modelArmorTemplate.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(modelArmorTemplate.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (modelArmorTemplate.getGuardrailType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"guardrailType\": ");

			sb.append("\"");
			sb.append(modelArmorTemplate.getGuardrailType());
			sb.append("\"");
		}

		if (modelArmorTemplate.getLocation() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"location\": ");

			sb.append("\"");

			sb.append(_escape(modelArmorTemplate.getLocation()));

			sb.append("\"");
		}

		if (modelArmorTemplate.getMaliciousUriFilterEnabled() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"maliciousUriFilterEnabled\": ");

			sb.append(modelArmorTemplate.getMaliciousUriFilterEnabled());
		}

		if (modelArmorTemplate.getMultiLanguageDetectionEnabled() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"multiLanguageDetectionEnabled\": ");

			sb.append(modelArmorTemplate.getMultiLanguageDetectionEnabled());
		}

		if (modelArmorTemplate.getPiAndJailbreakConfidenceLevel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"piAndJailbreakConfidenceLevel\": ");

			sb.append("\"");
			sb.append(modelArmorTemplate.getPiAndJailbreakConfidenceLevel());
			sb.append("\"");
		}

		if (modelArmorTemplate.getPiAndJailbreakFilterEnabled() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"piAndJailbreakFilterEnabled\": ");

			sb.append(modelArmorTemplate.getPiAndJailbreakFilterEnabled());
		}

		if (modelArmorTemplate.getRaiDangerousLevel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"raiDangerousLevel\": ");

			sb.append("\"");
			sb.append(modelArmorTemplate.getRaiDangerousLevel());
			sb.append("\"");
		}

		if (modelArmorTemplate.getRaiHarassmentLevel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"raiHarassmentLevel\": ");

			sb.append("\"");
			sb.append(modelArmorTemplate.getRaiHarassmentLevel());
			sb.append("\"");
		}

		if (modelArmorTemplate.getRaiHateSpeechLevel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"raiHateSpeechLevel\": ");

			sb.append("\"");
			sb.append(modelArmorTemplate.getRaiHateSpeechLevel());
			sb.append("\"");
		}

		if (modelArmorTemplate.getRaiSexuallyExplicitLevel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"raiSexuallyExplicitLevel\": ");

			sb.append("\"");
			sb.append(modelArmorTemplate.getRaiSexuallyExplicitLevel());
			sb.append("\"");
		}

		if (modelArmorTemplate.getSdpFilterEnabled() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sdpFilterEnabled\": ");

			sb.append(modelArmorTemplate.getSdpFilterEnabled());
		}

		if (modelArmorTemplate.getTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			sb.append("\"");

			sb.append(_escape(modelArmorTemplate.getTitle()));

			sb.append("\"");
		}

		if (modelArmorTemplate.getTitle_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title_i18n\": ");

			sb.append(_toJSON(modelArmorTemplate.getTitle_i18n()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ModelArmorTemplateJSONParser modelArmorTemplateJSONParser =
			new ModelArmorTemplateJSONParser();

		return modelArmorTemplateJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ModelArmorTemplate modelArmorTemplate) {

		if (modelArmorTemplate == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (modelArmorTemplate.getActive() == null) {
			map.put("active", null);
		}
		else {
			map.put("active", String.valueOf(modelArmorTemplate.getActive()));
		}

		if (modelArmorTemplate.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(modelArmorTemplate.getDescription()));
		}

		if (modelArmorTemplate.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(modelArmorTemplate.getExternalReferenceCode()));
		}

		if (modelArmorTemplate.getGuardrailType() == null) {
			map.put("guardrailType", null);
		}
		else {
			map.put(
				"guardrailType",
				String.valueOf(modelArmorTemplate.getGuardrailType()));
		}

		if (modelArmorTemplate.getLocation() == null) {
			map.put("location", null);
		}
		else {
			map.put(
				"location", String.valueOf(modelArmorTemplate.getLocation()));
		}

		if (modelArmorTemplate.getMaliciousUriFilterEnabled() == null) {
			map.put("maliciousUriFilterEnabled", null);
		}
		else {
			map.put(
				"maliciousUriFilterEnabled",
				String.valueOf(
					modelArmorTemplate.getMaliciousUriFilterEnabled()));
		}

		if (modelArmorTemplate.getMultiLanguageDetectionEnabled() == null) {
			map.put("multiLanguageDetectionEnabled", null);
		}
		else {
			map.put(
				"multiLanguageDetectionEnabled",
				String.valueOf(
					modelArmorTemplate.getMultiLanguageDetectionEnabled()));
		}

		if (modelArmorTemplate.getPiAndJailbreakConfidenceLevel() == null) {
			map.put("piAndJailbreakConfidenceLevel", null);
		}
		else {
			map.put(
				"piAndJailbreakConfidenceLevel",
				String.valueOf(
					modelArmorTemplate.getPiAndJailbreakConfidenceLevel()));
		}

		if (modelArmorTemplate.getPiAndJailbreakFilterEnabled() == null) {
			map.put("piAndJailbreakFilterEnabled", null);
		}
		else {
			map.put(
				"piAndJailbreakFilterEnabled",
				String.valueOf(
					modelArmorTemplate.getPiAndJailbreakFilterEnabled()));
		}

		if (modelArmorTemplate.getRaiDangerousLevel() == null) {
			map.put("raiDangerousLevel", null);
		}
		else {
			map.put(
				"raiDangerousLevel",
				String.valueOf(modelArmorTemplate.getRaiDangerousLevel()));
		}

		if (modelArmorTemplate.getRaiHarassmentLevel() == null) {
			map.put("raiHarassmentLevel", null);
		}
		else {
			map.put(
				"raiHarassmentLevel",
				String.valueOf(modelArmorTemplate.getRaiHarassmentLevel()));
		}

		if (modelArmorTemplate.getRaiHateSpeechLevel() == null) {
			map.put("raiHateSpeechLevel", null);
		}
		else {
			map.put(
				"raiHateSpeechLevel",
				String.valueOf(modelArmorTemplate.getRaiHateSpeechLevel()));
		}

		if (modelArmorTemplate.getRaiSexuallyExplicitLevel() == null) {
			map.put("raiSexuallyExplicitLevel", null);
		}
		else {
			map.put(
				"raiSexuallyExplicitLevel",
				String.valueOf(
					modelArmorTemplate.getRaiSexuallyExplicitLevel()));
		}

		if (modelArmorTemplate.getSdpFilterEnabled() == null) {
			map.put("sdpFilterEnabled", null);
		}
		else {
			map.put(
				"sdpFilterEnabled",
				String.valueOf(modelArmorTemplate.getSdpFilterEnabled()));
		}

		if (modelArmorTemplate.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put("title", String.valueOf(modelArmorTemplate.getTitle()));
		}

		if (modelArmorTemplate.getTitle_i18n() == null) {
			map.put("title_i18n", null);
		}
		else {
			map.put(
				"title_i18n",
				String.valueOf(modelArmorTemplate.getTitle_i18n()));
		}

		return map;
	}

	public static class ModelArmorTemplateJSONParser
		extends BaseJSONParser<ModelArmorTemplate> {

		@Override
		protected ModelArmorTemplate createDTO() {
			return new ModelArmorTemplate();
		}

		@Override
		protected ModelArmorTemplate[] createDTOArray(int size) {
			return new ModelArmorTemplate[size];
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
						jsonParserFieldName, "multiLanguageDetectionEnabled")) {

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
			ModelArmorTemplate modelArmorTemplate, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "active")) {
				if (jsonParserFieldValue != null) {
					modelArmorTemplate.setActive((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					modelArmorTemplate.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					modelArmorTemplate.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "guardrailType")) {
				if (jsonParserFieldValue != null) {
					modelArmorTemplate.setGuardrailType(
						ModelArmorTemplate.GuardrailType.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "location")) {
				if (jsonParserFieldValue != null) {
					modelArmorTemplate.setLocation(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "maliciousUriFilterEnabled")) {

				if (jsonParserFieldValue != null) {
					modelArmorTemplate.setMaliciousUriFilterEnabled(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "multiLanguageDetectionEnabled")) {

				if (jsonParserFieldValue != null) {
					modelArmorTemplate.setMultiLanguageDetectionEnabled(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "piAndJailbreakConfidenceLevel")) {

				if (jsonParserFieldValue != null) {
					modelArmorTemplate.setPiAndJailbreakConfidenceLevel(
						ModelArmorTemplate.PiAndJailbreakConfidenceLevel.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "piAndJailbreakFilterEnabled")) {

				if (jsonParserFieldValue != null) {
					modelArmorTemplate.setPiAndJailbreakFilterEnabled(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "raiDangerousLevel")) {
				if (jsonParserFieldValue != null) {
					modelArmorTemplate.setRaiDangerousLevel(
						ModelArmorTemplate.RaiDangerousLevel.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "raiHarassmentLevel")) {

				if (jsonParserFieldValue != null) {
					modelArmorTemplate.setRaiHarassmentLevel(
						ModelArmorTemplate.RaiHarassmentLevel.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "raiHateSpeechLevel")) {

				if (jsonParserFieldValue != null) {
					modelArmorTemplate.setRaiHateSpeechLevel(
						ModelArmorTemplate.RaiHateSpeechLevel.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "raiSexuallyExplicitLevel")) {

				if (jsonParserFieldValue != null) {
					modelArmorTemplate.setRaiSexuallyExplicitLevel(
						ModelArmorTemplate.RaiSexuallyExplicitLevel.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "sdpFilterEnabled")) {
				if (jsonParserFieldValue != null) {
					modelArmorTemplate.setSdpFilterEnabled(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					modelArmorTemplate.setTitle((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title_i18n")) {
				if (jsonParserFieldValue != null) {
					modelArmorTemplate.setTitle_i18n(
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
// LIFERAY-REST-BUILDER-HASH:370537748