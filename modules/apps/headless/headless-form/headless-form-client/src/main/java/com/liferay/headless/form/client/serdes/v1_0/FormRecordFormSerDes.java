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

import java.util.Collection;
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
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"draft\": ");

		sb.append(formRecordForm.getDraft());
		sb.append(", ");

		sb.append("\"fieldValues\": ");

		sb.append("\"");
		sb.append(formRecordForm.getFieldValues());
		sb.append("\"");

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(Collection<FormRecordForm> formRecordForms) {
		if (formRecordForms == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (FormRecordForm formRecordForm : formRecordForms) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(formRecordForm));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class FormRecordFormJSONParser
		extends BaseJSONParser<FormRecordForm> {

		protected FormRecordForm createDTO() {
			return new FormRecordForm();
		}

		protected FormRecordForm[] createDTOArray(int size) {
			return new FormRecordForm[size];
		}

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