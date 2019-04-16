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

package com.liferay.headless.form.client.serdes.v1_0;

import com.liferay.headless.form.client.dto.v1_0.FormRecordForm;
import com.liferay.headless.form.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class FormRecordFormSerDes {

	public static FormRecordForm toDTO(String json) {
		FormRecordFormJSONParser formRecordFormJSONParser =
			new FormRecordFormJSONParser();

		return formRecordFormJSONParser.parseToDTO(json);
	}

	public static FormRecordForm[] toDTOs(String json) {
		FormRecordFormJSONParser formRecordFormJSONParser =
			new FormRecordFormJSONParser();

		return formRecordFormJSONParser.parseToDTOs(json);
	}

	public static String toJSON(FormRecordForm formRecordForm) {
		if (formRecordForm == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"draft\": ");

		if (formRecordForm.getDraft() == null) {
			sb.append("null");
		}
		else {
			sb.append(formRecordForm.getDraft());
		}

		sb.append(", ");

		sb.append("\"fieldValues\": ");

		if (formRecordForm.getFieldValues() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(formRecordForm.getFieldValues());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(FormRecordForm formRecordForm) {
		if (formRecordForm == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		map.put("draft", String.valueOf(formRecordForm.getDraft()));

		map.put("fieldValues", String.valueOf(formRecordForm.getFieldValues()));

		return map;
	}

	private static class FormRecordFormJSONParser
		extends BaseJSONParser<FormRecordForm> {

		@Override
		protected FormRecordForm createDTO() {
			return new FormRecordForm();
		}

		@Override
		protected FormRecordForm[] createDTOArray(int size) {
			return new FormRecordForm[size];
		}

		@Override
		protected void setField(
			FormRecordForm formRecordForm, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "draft")) {
				if (jsonParserFieldValue != null) {
					formRecordForm.setDraft((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fieldValues")) {
				if (jsonParserFieldValue != null) {
					formRecordForm.setFieldValues((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}