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

package com.liferay.portal.vulcan.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import com.liferay.portal.vulcan.context.Pagination;

import java.util.Collection;

/**
 * @author Alejandro Hernández
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 */
@JacksonXmlRootElement(localName = "page")
public class Page<T> {

	public static <T> Page<T> of(Collection<T> items, int totalCount) {
		return new Page<>(items, totalCount);
	}

	public static <T> Page<T> of(
		Collection<T> items, Pagination pagination, int totalCount) {

		return new Page<>(items, pagination, totalCount);
	}

	@JacksonXmlElementWrapper(localName = "items")
	@JacksonXmlProperty(localName = "item")
	public Collection<T> getItems() {
		return _items;
	}

	public int getItemsPerPage() {
		return _itemsPerPage;
	}

	public int getLastPageNumber() {
		if (_totalCount == 0) {
			return 1;
		}

		return -Math.floorDiv(-_totalCount, _itemsPerPage);
	}

	public int getPageNumber() {
		return _pageNumber;
	}

	public int getTotalCount() {
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

	private Page(Collection<T> items, int totalCount) {
		_items = items;
		_itemsPerPage = items.size();
		_pageNumber = 1;
		_totalCount = totalCount;
	}

	private Page(Collection<T> items, Pagination pagination, int totalCount) {
		_items = items;
		_itemsPerPage = pagination.getItemsPerPage();
		_pageNumber = pagination.getPageNumber();
		_totalCount = totalCount;
	}

	private final Collection<T> _items;
	private final int _itemsPerPage;
	private final int _pageNumber;
	private final int _totalCount;

}