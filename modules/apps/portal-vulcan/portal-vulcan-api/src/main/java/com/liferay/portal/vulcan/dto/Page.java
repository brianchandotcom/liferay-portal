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

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a page of a collection.
 *
 * @author Alejandro Hernández
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 * @param  <T> the resource's type
 * @review
 */
@JacksonXmlRootElement(localName = "page")
public class Page<T> {

	/**
	 * Creates a new {@code Page} wrapping the provided collection.
	 *
	 * <p>This method infers that the provided list represents the whole
	 * collection.
	 *
	 * @review
	 */
	public static <T> Page<T> of(Collection<T> items) {
		return new Page<>(items);
	}

	/**
	 * Creates a new {@code Page} wrapping the provided collection.
	 *
	 * @param  items the page's items
	 * @param  pagination the pagination information selected by the user
	 * @param  totalCount the total number of elements in the collection
	 * @return a {@code Page}, representing a view of a complete collection
	 * @review
	 */
	public static <T> Page<T> of(
		Collection<T> items, Pagination pagination, int totalCount) {

		return new Page<>(items, pagination, totalCount);
	}

	/**
	 * The page's items.
	 *
	 * @review
	 */
	@JacksonXmlElementWrapper(localName = "items")
	@JacksonXmlProperty(localName = "item")
	public Collection<T> getItems() {
		return new ArrayList<>(_items);
	}

	/**
	 * The number of items the user selected on the page.
	 *
	 * @review
	 */
	public int getItemsPerPage() {
		return _itemsPerPage;
	}

	/**
	 * The number of the collection's last page.
	 *
	 * @review
	 */
	public int getLastPageNumber() {
		if (_totalCount == 0) {
			return 1;
		}

		return -Math.floorDiv(-_totalCount, _itemsPerPage);
	}

	/**
	 * The actual page number in the collection.
	 *
	 * @review
	 */
	public int getPageNumber() {
		return _pageNumber;
	}

	/**
	 * The total number of elements in the collection.
	 *
	 * @review
	 */
	public int getTotalCount() {
		return _totalCount;
	}

	/**
	 * {@code true} if another page follows this page in the collection; {@code
	 * false} otherwise.
	 *
	 * @review
	 */
	public boolean hasNext() {
		if (getLastPageNumber() > _pageNumber) {
			return true;
		}

		return false;
	}

	/**
	 * {@code true} if another page precedes this page in the collection; {@code
	 * false} otherwise.
	 *
	 * @review
	 */
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