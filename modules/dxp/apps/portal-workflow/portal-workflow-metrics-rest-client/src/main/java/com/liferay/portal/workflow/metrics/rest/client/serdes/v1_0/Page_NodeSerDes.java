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
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Page_Node;
import com.liferay.portal.workflow.metrics.rest.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class Page_NodeSerDes {

	public static Page_Node toDTO(String json) {
		Page_NodeJSONParser page_NodeJSONParser = new Page_NodeJSONParser();

		return page_NodeJSONParser.parseToDTO(json);
	}

	public static Page_Node[] toDTOs(String json) {
		Page_NodeJSONParser page_NodeJSONParser = new Page_NodeJSONParser();

		return page_NodeJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Page_Node page_Node) {
		if (page_Node == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (page_Node.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < page_Node.getItems().length; i++) {
				sb.append(NodeSerDes.toJSON(page_Node.getItems()[i]));

				if ((i + 1) < page_Node.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (page_Node.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(page_Node.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (page_Node.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(page_Node.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (page_Node.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(page_Node.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (page_Node.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(page_Node.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class Page_NodeJSONParser extends BaseJSONParser<Page_Node> {

		protected Page_Node createDTO() {
			return new Page_Node();
		}

		protected Page_Node[] createDTOArray(int size) {
			return new Page_Node[size];
		}

		protected void setField(
			Page_Node page_Node, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					page_Node.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> NodeSerDes.toDTO((String)object)
						).toArray(
							size -> new Node[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					page_Node.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					page_Node.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					page_Node.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					page_Node.setTotalCount(
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