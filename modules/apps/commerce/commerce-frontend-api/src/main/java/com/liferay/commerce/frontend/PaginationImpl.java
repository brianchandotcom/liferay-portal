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

package com.liferay.commerce.frontend;

/**
 * @author Marco Leo
 */
public class PaginationImpl implements Pagination {

	public PaginationImpl(int itemsPerPage, int pageNumber) {
		_itemsPerPage = itemsPerPage;
		_pageNumber = pageNumber;
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

	private final int _itemsPerPage;
	private final int _pageNumber;

}