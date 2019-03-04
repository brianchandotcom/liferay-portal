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

package com.liferay.portal.vulcan.pagination;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Alejandro Hernández
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 */
@JacksonXmlRootElement(localName = "page")
@JsonFilter("VulcanFilter")
public class Page<T> {

	public static <T> Page<T> of(Collection<T> items) {
		return new Page<>(items);
	}

	public static <T> Page<T> of(
		Collection<T> items, Pagination pagination, long totalCount) {

		return new Page<>(items, pagination, totalCount);
	}

	@JacksonXmlElementWrapper(localName = "items")
	@JacksonXmlProperty(localName = "item")
	public Collection<T> getItems() {
		return new ArrayList<>(_items);
	}

	@JsonProperty("pageSize")
	public long getItemsPerPage() {
		return _itemsPerPage;
	}

	public long getLastPageNumber() {
		if (_totalCount == 0) {
			return 1;
		}

		return -Math.floorDiv(-_totalCount, _itemsPerPage);
	}

	@JsonProperty("page")
	public long getPageNumber() {
		return _pageNumber;
	}

	public long getTotalCount() {
		return _totalCount;
	}

	public boolean hasNext() {
		if (getLastPageNumber() > _pageNumber) {
			return true;
		}

		return false;
	}

	public boolean hasPrevious() {
		if (_pageNumber > 1) {
			return true;
		}

		return false;
	}

	private Page(Collection<T> items) {
		_items = items;
		_itemsPerPage = items.size();
		_pageNumber = 1;

		_totalCount = _itemsPerPage;
	}

	private Page(Collection<T> items, Pagination pagination, long totalCount) {
		_items = items;
		_itemsPerPage = pagination.getItemsPerPage();
		_pageNumber = pagination.getPageNumber();
		_totalCount = totalCount;
	}

	private final Collection<T> _items;
	private final long _itemsPerPage;
	private final long _pageNumber;
	private final long _totalCount;

}