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

import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.GenericError;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Page_GenericError;
import com.liferay.portal.workflow.metrics.rest.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class Page_GenericErrorSerDes {

	public static Page_GenericError toDTO(String json) {
		Page_GenericErrorJSONParser page_GenericErrorJSONParser =
			new Page_GenericErrorJSONParser();

		return page_GenericErrorJSONParser.parseToDTO(json);
	}

	public static Page_GenericError[] toDTOs(String json) {
		Page_GenericErrorJSONParser page_GenericErrorJSONParser =
			new Page_GenericErrorJSONParser();

		return page_GenericErrorJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Page_GenericError page_GenericError) {
		if (page_GenericError == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (page_GenericError.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < page_GenericError.getItems().length; i++) {
				sb.append(
					GenericErrorSerDes.toJSON(page_GenericError.getItems()[i]));

				if ((i + 1) < page_GenericError.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (page_GenericError.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(page_GenericError.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (page_GenericError.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(page_GenericError.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (page_GenericError.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(page_GenericError.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (page_GenericError.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(page_GenericError.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class Page_GenericErrorJSONParser
		extends BaseJSONParser<Page_GenericError> {

		protected Page_GenericError createDTO() {
			return new Page_GenericError();
		}

		protected Page_GenericError[] createDTOArray(int size) {
			return new Page_GenericError[size];
		}

		protected void setField(
			Page_GenericError page_GenericError, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					page_GenericError.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> GenericErrorSerDes.toDTO((String)object)
						).toArray(
							size -> new GenericError[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					page_GenericError.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					page_GenericError.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					page_GenericError.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					page_GenericError.setTotalCount(
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