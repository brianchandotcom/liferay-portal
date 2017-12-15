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

package com.liferay.apio.architect.pagination;

/**
 * Defines pagination for a collection endpoint. An instance of this class is
 * handed to resources that handle pagination parameters.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 * @see    Page
 */
public class Pagination {

	public Pagination(int itemsPerPage, int pageNumber) {
		_itemsPerPage = itemsPerPage;
		_pageNumber = pageNumber;
	}

	/**
	 * Returns the position of the requested page's last element.
	 *
	 * @return the position of the requested page's last element
	 */
	public int getEndPosition() {
		return _pageNumber * _itemsPerPage;
	}

	/**
	 * Returns the selected number of items per page.
	 *
	 * @return the selected number of items per page
	 */
	public int getItemsPerPage() {
		return _itemsPerPage;
	}

	/**
	 * Returns the requested page's number.
	 *
	 * @return the requested page's number
	 */
	public int getPageNumber() {
		return _pageNumber;
	}

	/**
	 * Returns the position of the requested page's first element.
	 *
	 * @return the position of the requested page's first element
	 */
	public int getStartPosition() {
		return (_pageNumber - 1) * _itemsPerPage;
	}

	private final int _itemsPerPage;
	private final int _pageNumber;

}