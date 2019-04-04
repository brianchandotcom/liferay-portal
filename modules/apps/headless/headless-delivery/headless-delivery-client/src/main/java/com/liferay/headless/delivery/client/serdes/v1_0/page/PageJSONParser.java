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
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Rubén Pulido
 */
public class PageJSONParser<T> extends BaseJSONParser<Page> {

	protected PageJSONParser(Function<String, T> toDTO) {
		_toDTO = toDTO;
	}

	protected Page createDTO() {
		return new Page();
	}

	protected Page[] createDTOArray(int size) {
		return new Page[size];
	}

	protected void setField(
		Page page, String jsonParserFieldName, Object jsonParserFieldValue) {

		if (Objects.equals(jsonParserFieldName, "items")) {
			if (jsonParserFieldValue != null) {
				page.setItems(
					Stream.of(
						toStrings((Object[])jsonParserFieldValue)
					).map(
						s -> _toDTO.apply(s)
					).collect(
						Collectors.toList()
					));
			}
		}
		else if (Objects.equals(jsonParserFieldName, "lastPage")) {
			if (jsonParserFieldValue != null) {
				page.setLastPage(Long.valueOf((String)jsonParserFieldValue));
			}
		}
		else if (Objects.equals(jsonParserFieldName, "page")) {
			if (jsonParserFieldValue != null) {
				page.setPage(Long.valueOf((String)jsonParserFieldValue));
			}
		}
		else if (Objects.equals(jsonParserFieldName, "pageSize")) {
			if (jsonParserFieldValue != null) {
				page.setPageSize(Long.valueOf((String)jsonParserFieldValue));
			}
		}
		else if (Objects.equals(jsonParserFieldName, "totalCount")) {
			if (jsonParserFieldValue != null) {
				page.setTotalCount(Long.valueOf((String)jsonParserFieldValue));
			}
		}
		else {
			throw new IllegalArgumentException(
				"Unsupported field name " + jsonParserFieldName);
		}
	}

	private final Function<String, T> _toDTO;

}