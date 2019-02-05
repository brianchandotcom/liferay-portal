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
public interface Pagination {

	/**
	 * Creates a new {@code Pagination} instance.
	 *
	 * @param  itemsPerPage the number of items per page
	 * @param  pageNumber the page number
	 * @return the {@code Pagination} instance
	 * @review
	 */
	public static Pagination of(int itemsPerPage, int pageNumber) {
		return new Pagination() {

			@Override
			public int getEndPosition() {
				return pageNumber * itemsPerPage;
			}

			@Override
			public int getItemsPerPage() {
				return itemsPerPage;
			}

			@Override
			public int getPageNumber() {
				return pageNumber;
			}

			@Override
			public int getStartPosition() {
				return (pageNumber - 1) * itemsPerPage;
			}

		};
	}

	/**
	 * The position of the requested page's last element.
	 *
	 * @review
	 */
	public int getEndPosition();

	/**
	 * The selected number of items per page.
	 *
	 * @review
	 */
	public int getItemsPerPage();

	/**
	 * The requested page's number.
	 *
	 * @review
	 */
	public int getPageNumber();

	/**
	 * The position of the requested page's first element.
	 *
	 * @review
	 */
	public int getStartPosition();

}