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

package com.liferay.portal.vulcan.context;

/**
 * Defines pagination for a collection endpoint.
 *
 * @author Alejandro Hernández
 * @author Zoltán Takács
 * @see    com.liferay.portal.vulcan.dto.Page
 * @review
 */
public class Pagination {

	/**
	 * Creates a new {@code Pagination} instance.
	 *
	 * @param  itemsPerPage the number of items per page
	 * @param  pageNumber the page number
	 * @return the {@code Pagination} instance
	 * @review
	 */
	public static Pagination of(int itemsPerPage, int pageNumber) {
		return new Pagination(pageNumber, itemsPerPage);
	}

	/**
	 * The position of the requested page's last element.
	 *
	 * @review
	 */
	public int getEndPosition() {
		return _pageNumber * _itemsPerPage;
	}

	/**
	 * The selected number of items per page.
	 *
	 * @review
	 */
	public int getItemsPerPage() {
		return _itemsPerPage;
	}

	/**
	 * The requested page's number.
	 *
	 * @review
	 */
	public int getPageNumber() {
		return _pageNumber;
	}

	/**
	 * The position of the requested page's first element.
	 *
	 * @review
	 */
	public int getStartPosition() {
		return (_pageNumber - 1) * _itemsPerPage;
	}

	private Pagination(int pageNumber, int itemsPerPage) {
		_pageNumber = pageNumber;
		_itemsPerPage = itemsPerPage;
	}

	private final int _itemsPerPage;
	private final int _pageNumber;

}