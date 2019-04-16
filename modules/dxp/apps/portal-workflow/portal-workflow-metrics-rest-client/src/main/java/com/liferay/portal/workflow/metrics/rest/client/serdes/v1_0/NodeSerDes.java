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

		sb.append("\"id\": ");

		if (node.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(node.getId());
		}

		sb.append(", ");

		sb.append("\"initial\": ");

		if (node.getInitial() == null) {
			sb.append("null");
		}
		else {
			sb.append(node.getInitial());
		}

		sb.append(", ");

		sb.append("\"name\": ");

		if (node.getName() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(node.getName());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"terminal\": ");

		if (node.getTerminal() == null) {
			sb.append("null");
		}
		else {
			sb.append(node.getTerminal());
		}

		sb.append(", ");

		sb.append("\"type\": ");

		if (node.getType() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(node.getType());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	private static class NodeJSONParser extends BaseJSONParser<Node> {

		protected Node createDTO() {
			return new Node();
		}

		protected Node[] createDTOArray(int size) {
			return new Node[size];
		}

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