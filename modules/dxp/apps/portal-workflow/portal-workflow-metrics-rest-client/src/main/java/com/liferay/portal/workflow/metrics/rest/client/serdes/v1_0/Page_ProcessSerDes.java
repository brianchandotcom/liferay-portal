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

import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Page_Process;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class Page_ProcessSerDes {

	public static Page_Process toDTO(String json) {
		Page_ProcessJSONParser page_ProcessJSONParser =
			new Page_ProcessJSONParser();

		return page_ProcessJSONParser.parseToDTO(json);
	}

	public static Page_Process[] toDTOs(String json) {
		Page_ProcessJSONParser page_ProcessJSONParser =
			new Page_ProcessJSONParser();

		return page_ProcessJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Page_Process page_Process) {
		if (page_Process == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (page_Process.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < page_Process.getItems().length; i++) {
				sb.append(ProcessSerDes.toJSON(page_Process.getItems()[i]));

				if ((i + 1) < page_Process.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (page_Process.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(page_Process.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (page_Process.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(page_Process.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (page_Process.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(page_Process.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (page_Process.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(page_Process.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class Page_ProcessJSONParser
		extends BaseJSONParser<Page_Process> {

		protected Page_Process createDTO() {
			return new Page_Process();
		}

		protected Page_Process[] createDTOArray(int size) {
			return new Page_Process[size];
		}

		protected void setField(
			Page_Process page_Process, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					page_Process.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ProcessSerDes.toDTO((String)object)
						).toArray(
							size -> new Process[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					page_Process.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					page_Process.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					page_Process.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					page_Process.setTotalCount(
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