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

import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Page_Task;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Task;
import com.liferay.portal.workflow.metrics.rest.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class Page_TaskSerDes {

	public static Page_Task toDTO(String json) {
		Page_TaskJSONParser page_TaskJSONParser = new Page_TaskJSONParser();

		return page_TaskJSONParser.parseToDTO(json);
	}

	public static Page_Task[] toDTOs(String json) {
		Page_TaskJSONParser page_TaskJSONParser = new Page_TaskJSONParser();

		return page_TaskJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Page_Task page_Task) {
		if (page_Task == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (page_Task.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < page_Task.getItems().length; i++) {
				sb.append(TaskSerDes.toJSON(page_Task.getItems()[i]));

				if ((i + 1) < page_Task.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (page_Task.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(page_Task.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (page_Task.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(page_Task.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (page_Task.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(page_Task.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (page_Task.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(page_Task.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class Page_TaskJSONParser extends BaseJSONParser<Page_Task> {

		protected Page_Task createDTO() {
			return new Page_Task();
		}

		protected Page_Task[] createDTOArray(int size) {
			return new Page_Task[size];
		}

		protected void setField(
			Page_Task page_Task, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					page_Task.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> TaskSerDes.toDTO((String)object)
						).toArray(
							size -> new Task[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					page_Task.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					page_Task.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					page_Task.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					page_Task.setTotalCount(
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