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

import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Node;
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
public class NodeSerDes {

	public static Node toDTO(String json) {
		NodeJSONParser nodeJSONParser = new NodeJSONParser();

		return nodeJSONParser.parseToDTO(json);
	}

	public static Node[] toDTOs(String json) {
		NodeJSONParser nodeJSONParser = new NodeJSONParser();

		return nodeJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Node node) {
		if (node == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (node.getId() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"id\":");

			sb.append(node.getId());
		}

		if (node.getInitial() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"initial\":");

			sb.append(node.getInitial());
		}

		if (node.getName() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"name\":");

			sb.append("\"");

			sb.append(node.getName());

			sb.append("\"");
		}

		if (node.getTerminal() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"terminal\":");

			sb.append(node.getTerminal());
		}

		if (node.getType() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"type\":");

			sb.append("\"");

			sb.append(node.getType());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(Node node) {
		if (node == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (node.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(node.getId()));
		}

		if (node.getInitial() == null) {
			map.put("initial", null);
		}
		else {
			map.put("initial", String.valueOf(node.getInitial()));
		}

		if (node.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(node.getName()));
		}

		if (node.getTerminal() == null) {
			map.put("terminal", null);
		}
		else {
			map.put("terminal", String.valueOf(node.getTerminal()));
		}

		if (node.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(node.getType()));
		}

		return map;
	}

	private static class NodeJSONParser extends BaseJSONParser<Node> {

		@Override
		protected Node createDTO() {
			return new Node();
		}

		@Override
		protected Node[] createDTOArray(int size) {
			return new Node[size];
		}

		@Override
		protected void setField(
			Node node, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					node.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "initial")) {
				if (jsonParserFieldValue != null) {
					node.setInitial((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					node.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "terminal")) {
				if (jsonParserFieldValue != null) {
					node.setTerminal((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					node.setType((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}