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

import java.util.Collection;
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
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"transition\": ");

		if (changeTransition.getTransition() == null) {
			sb.append("null");
		}
		else {
			sb.append(changeTransition.getTransition());
		}

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(
		Collection<ChangeTransition> changeTransitions) {

		if (changeTransitions == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (ChangeTransition changeTransition : changeTransitions) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(changeTransition));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class ChangeTransitionJSONParser
		extends BaseJSONParser<ChangeTransition> {

		protected ChangeTransition createDTO() {
			return new ChangeTransition();
		}

		protected ChangeTransition[] createDTOArray(int size) {
			return new ChangeTransition[size];
		}

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