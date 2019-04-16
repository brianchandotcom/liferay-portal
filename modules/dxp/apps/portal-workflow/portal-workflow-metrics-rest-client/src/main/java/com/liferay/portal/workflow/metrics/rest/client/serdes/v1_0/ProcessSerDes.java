/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0;

import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.client.json.BaseJSONParser;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class ProcessSerDes {

	public static Process toDTO(String json) {
		ProcessJSONParser processJSONParser = new ProcessJSONParser();

		return processJSONParser.parseToDTO(json);
	}

	public static Process[] toDTOs(String json) {
		ProcessJSONParser processJSONParser = new ProcessJSONParser();

		return processJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Process process) {
		if (process == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"dueAfterInstanceCount\": ");

		if (process.getDueAfterInstanceCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(process.getDueAfterInstanceCount());
		}

		sb.append(", ");

		sb.append("\"dueInInstanceCount\": ");

		if (process.getDueInInstanceCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(process.getDueInInstanceCount());
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (process.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(process.getId());
		}

		sb.append(", ");

		sb.append("\"instanceCount\": ");

		if (process.getInstanceCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(process.getInstanceCount());
		}

		sb.append(", ");

		sb.append("\"onTimeInstanceCount\": ");

		if (process.getOnTimeInstanceCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(process.getOnTimeInstanceCount());
		}

		sb.append(", ");

		sb.append("\"overdueInstanceCount\": ");

		if (process.getOverdueInstanceCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(process.getOverdueInstanceCount());
		}

		sb.append(", ");

		sb.append("\"title\": ");

		if (process.getTitle() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(process.getTitle());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	private static class ProcessJSONParser extends BaseJSONParser<Process> {

		protected Process createDTO() {
			return new Process();
		}

		protected Process[] createDTOArray(int size) {
			return new Process[size];
		}

		protected void setField(
			Process process, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dueAfterInstanceCount")) {
				if (jsonParserFieldValue != null) {
					process.setDueAfterInstanceCount(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "dueInInstanceCount")) {

				if (jsonParserFieldValue != null) {
					process.setDueInInstanceCount(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					process.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "instanceCount")) {
				if (jsonParserFieldValue != null) {
					process.setInstanceCount(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "onTimeInstanceCount")) {

				if (jsonParserFieldValue != null) {
					process.setOnTimeInstanceCount(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "overdueInstanceCount")) {

				if (jsonParserFieldValue != null) {
					process.setOverdueInstanceCount(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					process.setTitle((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}