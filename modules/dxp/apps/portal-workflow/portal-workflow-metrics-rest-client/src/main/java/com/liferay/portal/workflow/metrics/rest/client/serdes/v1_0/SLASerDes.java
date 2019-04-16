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

import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.SLA;
import com.liferay.portal.workflow.metrics.rest.client.json.BaseJSONParser;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class SLASerDes {

	public static SLA toDTO(String json) {
		SLAJSONParser slaJSONParser = new SLAJSONParser();

		return slaJSONParser.parseToDTO(json);
	}

	public static SLA[] toDTOs(String json) {
		SLAJSONParser slaJSONParser = new SLAJSONParser();

		return slaJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SLA sla) {
		if (sla == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"description\": ");

		if (sla.getDescription() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(sla.getDescription());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"duration\": ");

		if (sla.getDuration() == null) {
			sb.append("null");
		}
		else {
			sb.append(sla.getDuration());
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (sla.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(sla.getId());
		}

		sb.append(", ");

		sb.append("\"name\": ");

		if (sla.getName() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(sla.getName());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"pauseNodeKeys\": ");

		if (sla.getPauseNodeKeys() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < sla.getPauseNodeKeys().length; i++) {
				sb.append("\"");

				sb.append(sla.getPauseNodeKeys()[i]);

				sb.append("\"");

				if ((i + 1) < sla.getPauseNodeKeys().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"processId\": ");

		if (sla.getProcessId() == null) {
			sb.append("null");
		}
		else {
			sb.append(sla.getProcessId());
		}

		sb.append(", ");

		sb.append("\"startNodeKeys\": ");

		if (sla.getStartNodeKeys() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < sla.getStartNodeKeys().length; i++) {
				sb.append("\"");

				sb.append(sla.getStartNodeKeys()[i]);

				sb.append("\"");

				if ((i + 1) < sla.getStartNodeKeys().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"stopNodeKeys\": ");

		if (sla.getStopNodeKeys() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < sla.getStopNodeKeys().length; i++) {
				sb.append("\"");

				sb.append(sla.getStopNodeKeys()[i]);

				sb.append("\"");

				if ((i + 1) < sla.getStopNodeKeys().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	private static class SLAJSONParser extends BaseJSONParser<SLA> {

		protected SLA createDTO() {
			return new SLA();
		}

		protected SLA[] createDTOArray(int size) {
			return new SLA[size];
		}

		protected void setField(
			SLA sla, String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					sla.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "duration")) {
				if (jsonParserFieldValue != null) {
					sla.setDuration(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					sla.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					sla.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pauseNodeKeys")) {
				if (jsonParserFieldValue != null) {
					sla.setPauseNodeKeys(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "processId")) {
				if (jsonParserFieldValue != null) {
					sla.setProcessId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "startNodeKeys")) {
				if (jsonParserFieldValue != null) {
					sla.setStartNodeKeys(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "stopNodeKeys")) {
				if (jsonParserFieldValue != null) {
					sla.setStopNodeKeys(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}