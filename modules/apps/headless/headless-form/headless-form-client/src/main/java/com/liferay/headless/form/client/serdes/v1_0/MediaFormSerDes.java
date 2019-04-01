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

import com.liferay.headless.form.client.dto.v1_0.MediaForm;
import com.liferay.headless.form.client.json.BaseJSONParser;

import java.util.Collection;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class MediaFormSerDes {

	public static MediaForm toDTO(String json) {
		MediaFormJSONParser mediaFormJSONParser = new MediaFormJSONParser();

		return mediaFormJSONParser.parseToDTO(json);
	}

	public static MediaForm[] toDTOs(String json) {
		MediaFormJSONParser mediaFormJSONParser = new MediaFormJSONParser();

		return mediaFormJSONParser.parseToDTOs(json);
	}

	public static String toJSON(MediaForm mediaForm) {
		if (mediaForm == null) {
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"description\": ");

		sb.append("\"");
		sb.append(mediaForm.getDescription());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"folderId\": ");

		sb.append(mediaForm.getFolderId());
		sb.append(", ");

		sb.append("\"name\": ");

		sb.append("\"");
		sb.append(mediaForm.getName());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"title\": ");

		sb.append("\"");
		sb.append(mediaForm.getTitle());
		sb.append("\"");

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(Collection<MediaForm> mediaForms) {
		if (mediaForms == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (MediaForm mediaForm : mediaForms) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(mediaForm));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class MediaFormJSONParser extends BaseJSONParser<MediaForm> {

		protected MediaForm createDTO() {
			return new MediaForm();
		}

		protected MediaForm[] createDTOArray(int size) {
			return new MediaForm[size];
		}

		protected void setField(
			MediaForm mediaForm, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					mediaForm.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "folderId")) {
				if (jsonParserFieldValue != null) {
					mediaForm.setFolderId((Long)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					mediaForm.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					mediaForm.setTitle((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}