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

package com.liferay.rest.booster.context;

/**
 * @author Zoltán Takács
 */
public class Pagination {

	public Pagination(int itemsPerPage, int pageNumber) {
		_itemsPerPage = itemsPerPage;
		_pageNumber = pageNumber;
	}

	public int getEndPosition() {
		return _pageNumber * _itemsPerPage;
	}

	public int getItemsPerPage() {
		return _itemsPerPage;
	}

	public int getPageNumber() {
		return _pageNumber;
	}

	public int getStartPosition() {
		return (_pageNumber - 1) * _itemsPerPage;
	}

	private final int _itemsPerPage;
	private final int _pageNumber;

}