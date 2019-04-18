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

import com.liferay.headless.admin.workflow.client.dto.v1_0.ChangeTransition;
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
public class ChangeTransitionSerDes {

	public static ChangeTransition toDTO(String json) {
		ChangeTransitionJSONParser changeTransitionJSONParser =
			new ChangeTransitionJSONParser();

		return changeTransitionJSONParser.parseToDTO(json);
	}

	public static ChangeTransition[] toDTOs(String json) {
		ChangeTransitionJSONParser changeTransitionJSONParser =
			new ChangeTransitionJSONParser();

		return changeTransitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ChangeTransition changeTransition) {
		if (changeTransition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (changeTransition.getTransition() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"transition\":");

			sb.append("\"");

			sb.append(_escape(changeTransition.getTransition()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(ChangeTransition changeTransition) {
		if (changeTransition == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (changeTransition.getTransition() == null) {
			map.put("transition", null);
		}
		else {
			map.put(
				"transition", String.valueOf(changeTransition.getTransition()));
		}

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static class ChangeTransitionJSONParser
		extends BaseJSONParser<ChangeTransition> {

		@Override
		protected ChangeTransition createDTO() {
			return new ChangeTransition();
		}

		@Override
		protected ChangeTransition[] createDTOArray(int size) {
			return new ChangeTransition[size];
		}

		@Override
		protected void setField(
			ChangeTransition changeTransition, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "transition")) {
				if (jsonParserFieldValue != null) {
					changeTransition.setTransition(
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