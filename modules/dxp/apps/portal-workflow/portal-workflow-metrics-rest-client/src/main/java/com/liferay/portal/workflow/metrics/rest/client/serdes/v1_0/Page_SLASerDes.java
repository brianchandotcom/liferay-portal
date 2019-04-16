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

import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Page_SLA;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.SLA;
import com.liferay.portal.workflow.metrics.rest.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class Page_SLASerDes {

	public static Page_SLA toDTO(String json) {
		Page_SLAJSONParser page_SLAJSONParser = new Page_SLAJSONParser();

		return page_SLAJSONParser.parseToDTO(json);
	}

	public static Page_SLA[] toDTOs(String json) {
		Page_SLAJSONParser page_SLAJSONParser = new Page_SLAJSONParser();

		return page_SLAJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Page_SLA page_SLA) {
		if (page_SLA == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (page_SLA.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < page_SLA.getItems().length; i++) {
				sb.append(SLASerDes.toJSON(page_SLA.getItems()[i]));

				if ((i + 1) < page_SLA.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (page_SLA.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(page_SLA.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (page_SLA.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(page_SLA.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (page_SLA.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(page_SLA.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (page_SLA.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(page_SLA.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class Page_SLAJSONParser extends BaseJSONParser<Page_SLA> {

		protected Page_SLA createDTO() {
			return new Page_SLA();
		}

		protected Page_SLA[] createDTOArray(int size) {
			return new Page_SLA[size];
		}

		protected void setField(
			Page_SLA page_SLA, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					page_SLA.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> SLASerDes.toDTO((String)object)
						).toArray(
							size -> new SLA[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					page_SLA.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					page_SLA.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					page_SLA.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					page_SLA.setTotalCount(
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