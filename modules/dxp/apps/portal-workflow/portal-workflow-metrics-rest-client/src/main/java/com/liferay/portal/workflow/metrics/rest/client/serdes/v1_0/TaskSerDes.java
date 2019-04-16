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

import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Task;
import com.liferay.portal.workflow.metrics.rest.client.json.BaseJSONParser;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class TaskSerDes {

	public static Task toDTO(String json) {
		TaskJSONParser taskJSONParser = new TaskJSONParser();

		return taskJSONParser.parseToDTO(json);
	}

	public static Task[] toDTOs(String json) {
		TaskJSONParser taskJSONParser = new TaskJSONParser();

		return taskJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Task task) {
		if (task == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"instanceCount\": ");

		if (task.getInstanceCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(task.getInstanceCount());
		}

		sb.append(", ");

		sb.append("\"name\": ");

		if (task.getName() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(task.getName());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"onTimeInstanceCount\": ");

		if (task.getOnTimeInstanceCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(task.getOnTimeInstanceCount());
		}

		sb.append(", ");

		sb.append("\"overdueInstanceCount\": ");

		if (task.getOverdueInstanceCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(task.getOverdueInstanceCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class TaskJSONParser extends BaseJSONParser<Task> {

		protected Task createDTO() {
			return new Task();
		}

		protected Task[] createDTOArray(int size) {
			return new Task[size];
		}

		protected void setField(
			Task task, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "instanceCount")) {
				if (jsonParserFieldValue != null) {
					task.setInstanceCount(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					task.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "onTimeInstanceCount")) {

				if (jsonParserFieldValue != null) {
					task.setOnTimeInstanceCount(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "overdueInstanceCount")) {

				if (jsonParserFieldValue != null) {
					task.setOverdueInstanceCount(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}