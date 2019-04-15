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

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.Comment;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class CommentSerDes {

	public static Comment toDTO(String json) {
		CommentJSONParser commentJSONParser = new CommentJSONParser();

		return commentJSONParser.parseToDTO(json);
	}

	public static Comment[] toDTOs(String json) {
		CommentJSONParser commentJSONParser = new CommentJSONParser();

		return commentJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Comment comment) {
		if (comment == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		sb.append("\"creator\": ");

		sb.append(CreatorSerDes.toJSON(comment.getCreator()));
		sb.append(", ");

		sb.append("\"dateCreated\": ");

		if (comment.getDateCreated() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(comment.getDateCreated()));

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"dateModified\": ");

		if (comment.getDateModified() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(comment.getDateModified()));

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (comment.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(comment.getId());
		}

		sb.append(", ");

		sb.append("\"numberOfComments\": ");

		if (comment.getNumberOfComments() == null) {
			sb.append("null");
		}
		else {
			sb.append(comment.getNumberOfComments());
		}

		sb.append(", ");

		sb.append("\"text\": ");

		if (comment.getText() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(comment.getText());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	private static class CommentJSONParser extends BaseJSONParser<Comment> {

		protected Comment createDTO() {
			return new Comment();
		}

		protected Comment[] createDTOArray(int size) {
			return new Comment[size];
		}

		protected void setField(
			Comment comment, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					comment.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					comment.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					comment.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					comment.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "numberOfComments")) {
				if (jsonParserFieldValue != null) {
					comment.setNumberOfComments(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "text")) {
				if (jsonParserFieldValue != null) {
					comment.setText((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}