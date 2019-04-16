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

package com.liferay.headless.admin.workflow.client.serdes.v1_0;

import com.liferay.headless.admin.workflow.client.dto.v1_0.ObjectReviewed;
import com.liferay.headless.admin.workflow.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ObjectReviewedSerDes {

	public static ObjectReviewed toDTO(String json) {
		ObjectReviewedJSONParser objectReviewedJSONParser =
			new ObjectReviewedJSONParser();

		return objectReviewedJSONParser.parseToDTO(json);
	}

	public static ObjectReviewed[] toDTOs(String json) {
		ObjectReviewedJSONParser objectReviewedJSONParser =
			new ObjectReviewedJSONParser();

		return objectReviewedJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ObjectReviewed objectReviewed) {
		if (objectReviewed == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"id\": ");

		if (objectReviewed.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(objectReviewed.getId());
		}

		sb.append(", ");

		sb.append("\"resourceType\": ");

		if (objectReviewed.getResourceType() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(objectReviewed.getResourceType());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(ObjectReviewed objectReviewed) {
		if (objectReviewed == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		map.put("id", String.valueOf(objectReviewed.getId()));

		map.put(
			"resourceType", String.valueOf(objectReviewed.getResourceType()));

		return map;
	}

	private static class ObjectReviewedJSONParser
		extends BaseJSONParser<ObjectReviewed> {

		@Override
		protected ObjectReviewed createDTO() {
			return new ObjectReviewed();
		}

		@Override
		protected ObjectReviewed[] createDTOArray(int size) {
			return new ObjectReviewed[size];
		}

		@Override
		protected void setField(
			ObjectReviewed objectReviewed, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					objectReviewed.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "resourceType")) {
				if (jsonParserFieldValue != null) {
					objectReviewed.setResourceType(
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