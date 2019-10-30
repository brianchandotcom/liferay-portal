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

package com.liferay.segments.asah.rest.client.pagination;

import com.liferay.segments.asah.rest.client.json.BaseJSONParser;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Page<T> {

	public static <T> Page<T> of(
		String json, Function<String, T> toDTOFunction) {

		PageJSONParser pageJSONParser = new PageJSONParser(toDTOFunction);

		return (Page<T>)pageJSONParser.parseToDTO(json);
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

	private Collection<T> _items;
	private long _page;
	private long _pageSize;
	private long _totalCount;

	private static class PageJSONParser<T> extends BaseJSONParser<Page> {

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

			if (Objects.equals(jsonParserFieldName, "items")) {
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

}