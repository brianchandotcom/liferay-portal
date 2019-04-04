/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.delivery.client.serdes.v1_0.page;

import com.liferay.headless.delivery.client.dto.v1_0.page.Page;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * @author Rubén Pulido
 */
public class PageSerDes {

	public static String toJSON(Collection<Page> pages) {
		if (pages == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (Page page : pages) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(page));
		}

		sb.append("]");

		return sb.toString();
	}

	public static <T> String toJSON(Page<T> page) {
		if (page == null) {
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		Collection items = page.getItems();

		if (items == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			List<T> itemsList = (List<T>)items;

			for (int i = 0; i < items.size(); i++) {
				sb.append(itemsList.get(i));

				if ((i + 1) < itemsList.size()) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		sb.append(page.getLastPage());
		sb.append(", ");

		sb.append("\"page\": ");

		sb.append(page.getPage());
		sb.append(", ");

		sb.append("\"pageSize\": ");

		sb.append(page.getPageSize());
		sb.append(", ");

		sb.append("\"totalCount\": ");

		sb.append(page.getTotalCount());

		sb.append("}");

		return sb.toString();
	}

	public static <T> Page<T> toPage(String json, Function<String, T> toDTO) {
		PageJSONParser pageJSONParser = new PageJSONParser(toDTO);

		return (Page<T>)pageJSONParser.parseToDTO(json);
	}

}