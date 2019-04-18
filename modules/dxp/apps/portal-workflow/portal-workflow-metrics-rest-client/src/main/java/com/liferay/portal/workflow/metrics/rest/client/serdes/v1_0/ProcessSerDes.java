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

import java.util.HashMap;
import java.util.Map;
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

		if (process.getDueAfterInstanceCount() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"dueAfterInstanceCount\":");

			sb.append(process.getDueAfterInstanceCount());
		}

		if (process.getDueInInstanceCount() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"dueInInstanceCount\":");

			sb.append(process.getDueInInstanceCount());
		}

		if (process.getId() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"id\":");

			sb.append(process.getId());
		}

		if (process.getInstanceCount() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"instanceCount\":");

			sb.append(process.getInstanceCount());
		}

		if (process.getOnTimeInstanceCount() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"onTimeInstanceCount\":");

			sb.append(process.getOnTimeInstanceCount());
		}

		if (process.getOverdueInstanceCount() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"overdueInstanceCount\":");

			sb.append(process.getOverdueInstanceCount());
		}

		if (process.getTitle() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"title\":");

			sb.append("\"");

			sb.append(process.getTitle());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(Process process) {
		if (process == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (process.getDueAfterInstanceCount() == null) {
			map.put("dueAfterInstanceCount", null);
		}
		else {
			map.put(
				"dueAfterInstanceCount",
				String.valueOf(process.getDueAfterInstanceCount()));
		}

		if (process.getDueInInstanceCount() == null) {
			map.put("dueInInstanceCount", null);
		}
		else {
			map.put(
				"dueInInstanceCount",
				String.valueOf(process.getDueInInstanceCount()));
		}

		if (process.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(process.getId()));
		}

		if (process.getInstanceCount() == null) {
			map.put("instanceCount", null);
		}
		else {
			map.put(
				"instanceCount", String.valueOf(process.getInstanceCount()));
		}

		if (process.getOnTimeInstanceCount() == null) {
			map.put("onTimeInstanceCount", null);
		}
		else {
			map.put(
				"onTimeInstanceCount",
				String.valueOf(process.getOnTimeInstanceCount()));
		}

		if (process.getOverdueInstanceCount() == null) {
			map.put("overdueInstanceCount", null);
		}
		else {
			map.put(
				"overdueInstanceCount",
				String.valueOf(process.getOverdueInstanceCount()));
		}

		if (process.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put("title", String.valueOf(process.getTitle()));
		}

		return map;
	}

	private static class ProcessJSONParser extends BaseJSONParser<Process> {

		@Override
		protected Process createDTO() {
			return new Process();
		}

		@Override
		protected Process[] createDTOArray(int size) {
			return new Process[size];
		}

		@Override
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