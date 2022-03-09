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

package com.liferay.layout.helper;

import com.liferay.info.pagination.Pagination;

/**
 * @author Eudaldo Alonso
 */
public interface CollectionPaginationHelper {

	public static final String PAGINATION_TYPE_NUMERIC = "numeric";

	public static final String PAGINATION_TYPE_REGULAR = "regular";

	public static final String PAGINATION_TYPE_SIMPLE = "simple";

	public Pagination getPagination(
		int activePage, int count, boolean displayAllPages,
		boolean displayAllItems, int numberOfItems, int numberOfItemsPerPage,
		int numberOfPages, String paginationType);

	public int getTotalNumberOfItems(
		int count, boolean displayAllPages, boolean displayAllItems,
		int numberOfItems, int numberOfItemsPerPage, int numberOfPages,
		String paginationType);

	public boolean isPaginationEnabled(String paginationType);

}