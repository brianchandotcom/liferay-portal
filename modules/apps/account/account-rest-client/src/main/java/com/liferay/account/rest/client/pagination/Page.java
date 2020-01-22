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

package com.liferay.account.rest.client.pagination;

import com.liferay.account.rest.client.json.BaseJSONParser;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Drew Brokke
 * @generated
 */
@Generated("")
public class Page<T> {

	public static <T> Page<T> of(
		String json, Function<String, T> toDTOFunction) {

		PageJSONParser pageJSONParser = new PageJSONParser(toDTOFunction);

		return (Page<T>)pageJSONParser.parseToDTO(json);
	}

	public Map<String, Map> getActions() {
		return _actions;
	}

	public Collection<T> getItems() {
		return _items;
	}

	public long getLastPage() {
		if (_totalCount == 0) {
			return 1;
		}

		return -Math.floorDiv(-_totalCount, _pageSize);
	}

	public long getPage() {
		return _page;
	}

	public long getPageSize() {
		return _pageSize;
	}

	public long getTotalCount() {
		return _totalCount;
	}

	public boolean hasNext() {
		if (getLastPage() > _page) {
			return true;
		}

		return false;
	}

	public boolean hasPrevious() {
		if (_page > 1) {
			return true;
		}

		return false;
	}

	public void setActions(Map<String, Map> actions) {
		_actions = actions;
	}

	public void setItems(Collection<T> items) {
		_items = items;
	}

	public void setPage(long page) {
		_page = page;
	}

	public void setPageSize(long pageSize) {
		_pageSize = pageSize;
	}

	public void setTotalCount(long totalCount) {
		_totalCount = totalCount;
	}

	public static class PageJSONParser<T> extends BaseJSONParser<Page> {

		public PageJSONParser() {
			_toDTOFunction = null;
		}

		public PageJSONParser(Function<String, T> toDTOFunction) {
			_toDTOFunction = toDTOFunction;
		}

		@Override
		protected Page createDTO() {
			return new Page();
		}

		@Override
		protected Page[] createDTOArray(int size) {
			return new Page[size];
		}

		@Override
		protected void setField(
			Page page, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					PageJSONParser pageJSONParser = new PageJSONParser(
						_toDTOFunction);

					page.setActions(
						pageJSONParser.parseToMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					page.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							string -> _toDTOFunction.apply(string)
						).collect(
							Collectors.toList()
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					page.setPage(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					page.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					page.setTotalCount(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

		private final Function<String, T> _toDTOFunction;

	}

	private Map<String, Map> _actions;
	private Collection<T> _items;
	private long _page;
	private long _pageSize;
	private long _totalCount;

}