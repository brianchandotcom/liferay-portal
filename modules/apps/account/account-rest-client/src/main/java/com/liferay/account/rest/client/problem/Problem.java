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

package com.liferay.account.rest.client.problem;

import com.liferay.account.rest.client.json.BaseJSONParser;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Drew Brokke
 * @generated
 */
@Generated("")
public class Problem {

	public static Problem toDTO(String json) {
		ProblemJSONParser<Problem> problemJSONParser = new ProblemJSONParser();

		return problemJSONParser.parseToDTO(json);
	}

	public static class ProblemException extends Exception {

		private Problem _problem;

		public Problem getProblem() {
			return _problem;
		}

		public ProblemException(Problem problem) {
			_problem = problem;
		}

	}

	public String getDetail() {
		return detail;
	}

	public String getStatus() {
		return status;
	}

	public String getTitle() {
		return title;
	}

	public String getType() {
		return type;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (detail != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"detail\": \"");
			sb.append(detail);
			sb.append("\"");
		}

		if (status != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": \"");
			sb.append(status);
			sb.append("\"");
		}

		if (title != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": \"");
			sb.append(title);
			sb.append("\"");
		}

		if (type != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": \"");
			sb.append(type);
			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	protected String detail;
	protected String status;
	protected String title;
	protected String type;

	private static class ProblemJSONParser<T> extends BaseJSONParser<Problem> {

		@Override
		protected Problem createDTO() {
			return new Problem();
		}

		@Override
		protected Problem[] createDTOArray(int size) {
			return new Problem[size];
		}

		@Override
		protected void setField(
			Problem problem, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "detail")) {
				if (jsonParserFieldValue != null) {
					problem.setDetail((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					problem.setStatus((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					problem.setTitle((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					problem.setType((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}