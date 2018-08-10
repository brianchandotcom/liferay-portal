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

package com.liferay.structured.content.apio.architect.util.test;

import com.liferay.apio.architect.pagination.Pagination;

/**
 * @author Julio Camarero
 */
public class PaginationTestUtil implements Pagination {

	public static Pagination of(int itemsPerPage, int pageNumber) {
		return new PaginationTestUtil(itemsPerPage, pageNumber);
	}

	@Override
	public int getEndPosition() {
		return _pageNumber * _itemsPerPage;
	}

	@Override
	public int getItemsPerPage() {
		return _itemsPerPage;
	}

	@Override
	public int getPageNumber() {
		return _pageNumber;
	}

	@Override
	public int getStartPosition() {
		return (_pageNumber - 1) * _itemsPerPage;
	}

	private PaginationTestUtil(int itemsPerPage, int pageNumber) {
		_itemsPerPage = itemsPerPage;
		_pageNumber = pageNumber;
	}

	private final int _itemsPerPage;
	private final int _pageNumber;

}