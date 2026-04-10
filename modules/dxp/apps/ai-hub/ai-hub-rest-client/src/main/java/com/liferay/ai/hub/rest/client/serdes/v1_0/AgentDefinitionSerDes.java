/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.client.serdes.v1_0;

import com.liferay.ai.hub.rest.client.dto.v1_0.AgentDefinition;
import com.liferay.ai.hub.rest.client.dto.v1_0.Variable;
import com.liferay.ai.hub.rest.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Feliphe Marinho
 * @generated
 */
@Generated("")
public class AgentDefinitionSerDes {

	public static AgentDefinition toDTO(String json) {
		AgentDefinitionJSONParser agentDefinitionJSONParser =
			new AgentDefinitionJSONParser();

		return agentDefinitionJSONParser.parseToDTO(json);
	}

	public static AgentDefinition[] toDTOs(String json) {
		AgentDefinitionJSONParser agentDefinitionJSONParser =
			new AgentDefinitionJSONParser();

		return agentDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(AgentDefinition agentDefinition) {
		if (agentDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (agentDefinition.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(agentDefinition.getActions()));
		}

		if (agentDefinition.getActive() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"active\": ");

			sb.append(agentDefinition.getActive());
		}

		if (agentDefinition.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(agentDefinition.getDescription()));

			sb.append("\"");
		}

		if (agentDefinition.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(agentDefinition.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (agentDefinition.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(agentDefinition.getId());
		}

		if (agentDefinition.getInputVariables() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"inputVariables\": ");

			sb.append("[");

			for (int i = 0; i < agentDefinition.getInputVariables().length;
				 i++) {

				sb.append(
					String.valueOf(agentDefinition.getInputVariables()[i]));

				if ((i + 1) < agentDefinition.getInputVariables().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (agentDefinition.getOutputVariable() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"outputVariable\": ");

			sb.append(String.valueOf(agentDefinition.getOutputVariable()));
		}

		if (agentDefinition.getStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append(String.valueOf(agentDefinition.getStatus()));
		}

		if (agentDefinition.getTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			sb.append("\"");

			sb.append(_escape(agentDefinition.getTitle()));

			sb.append("\"");
		}

		if (agentDefinition.getVersion() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"version\": ");

			sb.append(agentDefinition.getVersion());
		}

		if (agentDefinition.getWorkflowDefinitionName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"workflowDefinitionName\": ");

			sb.append("\"");

			sb.append(_escape(agentDefinition.getWorkflowDefinitionName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AgentDefinitionJSONParser agentDefinitionJSONParser =
			new AgentDefinitionJSONParser();

		return agentDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(AgentDefinition agentDefinition) {
		if (agentDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (agentDefinition.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(agentDefinition.getActions()));
		}

		if (agentDefinition.getActive() == null) {
			map.put("active", null);
		}
		else {
			map.put("active", String.valueOf(agentDefinition.getActive()));
		}

		if (agentDefinition.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(agentDefinition.getDescription()));
		}

		if (agentDefinition.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(agentDefinition.getExternalReferenceCode()));
		}

		if (agentDefinition.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(agentDefinition.getId()));
		}

		if (agentDefinition.getInputVariables() == null) {
			map.put("inputVariables", null);
		}
		else {
			map.put(
				"inputVariables",
				String.valueOf(agentDefinition.getInputVariables()));
		}

		if (agentDefinition.getOutputVariable() == null) {
			map.put("outputVariable", null);
		}
		else {
			map.put(
				"outputVariable",
				String.valueOf(agentDefinition.getOutputVariable()));
		}

		if (agentDefinition.getStatus() == null) {
			map.put("status", null);
		}
		else {
			map.put("status", String.valueOf(agentDefinition.getStatus()));
		}

		if (agentDefinition.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put("title", String.valueOf(agentDefinition.getTitle()));
		}

		if (agentDefinition.getVersion() == null) {
			map.put("version", null);
		}
		else {
			map.put("version", String.valueOf(agentDefinition.getVersion()));
		}

		if (agentDefinition.getWorkflowDefinitionName() == null) {
			map.put("workflowDefinitionName", null);
		}
		else {
			map.put(
				"workflowDefinitionName",
				String.valueOf(agentDefinition.getWorkflowDefinitionName()));
		}

		return map;
	}

	public static class AgentDefinitionJSONParser
		extends BaseJSONParser<AgentDefinition> {

		@Override
		protected AgentDefinition createDTO() {
			return new AgentDefinition();
		}

		@Override
		protected AgentDefinition[] createDTOArray(int size) {
			return new AgentDefinition[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "actions")) {
				return true;
			}
			else if (Objects.equals(jsonParserFieldName, "active")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "inputVariables")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "outputVariable")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "version")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "workflowDefinitionName")) {

				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			AgentDefinition agentDefinition, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					agentDefinition.setActions(
						(Map<String, Map<String, String>>)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "active")) {
				if (jsonParserFieldValue != null) {
					agentDefinition.setActive((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					agentDefinition.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					agentDefinition.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					agentDefinition.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "inputVariables")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					Variable[] inputVariablesArray =
						new Variable[jsonParserFieldValues.length];

					for (int i = 0; i < inputVariablesArray.length; i++) {
						inputVariablesArray[i] = VariableSerDes.toDTO(
							(String)jsonParserFieldValues[i]);
					}

					agentDefinition.setInputVariables(inputVariablesArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "outputVariable")) {
				if (jsonParserFieldValue != null) {
					agentDefinition.setOutputVariable(
						VariableSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					agentDefinition.setStatus(
						StatusSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					agentDefinition.setTitle((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "version")) {
				if (jsonParserFieldValue != null) {
					agentDefinition.setVersion(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "workflowDefinitionName")) {

				if (jsonParserFieldValue != null) {
					agentDefinition.setWorkflowDefinitionName(
						(String)jsonParserFieldValue);
				}
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\": ");

			Object value = entry.getValue();

			sb.append(_toJSON(value));

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _toJSON(Object value) {
		if (value == null) {
			return "null";
		}

		if (value instanceof Map) {
			return _toJSON((Map)value);
		}

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			StringBuilder sb = new StringBuilder("[");

			Object[] values = (Object[])value;

			for (int i = 0; i < values.length; i++) {
				sb.append(_toJSON(values[i]));

				if ((i + 1) < values.length) {
					sb.append(", ");
				}
			}

			sb.append("]");

			return sb.toString();
		}

		if (value instanceof String) {
			return "\"" + _escape(value) + "\"";
		}

		return String.valueOf(value);
	}

}
// LIFERAY-REST-BUILDER-HASH:-629848594