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

package com.liferay.layout.internal.helper;

import com.liferay.info.pagination.Pagination;
import com.liferay.layout.helper.CollectionPaginationHelper;
import com.liferay.portal.util.PropsValues;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = CollectionPaginationHelper.class)
public class CollectionPaginationHelperImpl
	implements CollectionPaginationHelper {

	@Override
	public Pagination getPagination(
		int activePage, int count, boolean displayAllPages,
		boolean displayAllItems, int numberOfItems, int numberOfItemsPerPage,
		int numberOfPages, String paginationType) {

		int end = numberOfItems;
		int start = 0;

		if ((numberOfItemsPerPage <= 0) ||
			(numberOfItemsPerPage >
				PropsValues.SEARCH_CONTAINER_PAGE_MAX_DELTA)) {

			numberOfItemsPerPage = PropsValues.SEARCH_CONTAINER_PAGE_MAX_DELTA;
		}

		if (isPaginationEnabled(paginationType)) {
			int maxNumberOfItems = count;

			if (!displayAllPages && (numberOfPages > 0)) {
				maxNumberOfItems = numberOfPages * numberOfItemsPerPage;
			}

			end = Math.min(
				Math.min(activePage * numberOfItemsPerPage, maxNumberOfItems),
				count);

			start = (activePage - 1) * numberOfItemsPerPage;
		}
		else if (displayAllItems) {
			end = count;
		}

		return Pagination.of(end, start);
	}

	@Override
	public int getTotalNumberOfItems(
		int count, boolean displayAllPages, boolean displayAllItems,
		int numberOfItems, int numberOfItemsPerPage, int numberOfPages,
		String paginationType) {

		if (!isPaginationEnabled(paginationType)) {
			if (displayAllItems) {
				return count;
			}

			return Math.min(count, numberOfItems);
		}

		if (displayAllPages || (numberOfPages <= 0)) {
			return count;
		}

		if ((numberOfItemsPerPage <= 0) ||
			(numberOfItemsPerPage >
				PropsValues.SEARCH_CONTAINER_PAGE_MAX_DELTA)) {

			numberOfItemsPerPage = PropsValues.SEARCH_CONTAINER_PAGE_MAX_DELTA;
		}

		return Math.min(count, numberOfPages * numberOfItemsPerPage);
	}

	@Override
	public boolean isPaginationEnabled(String paginationType) {
		if (Objects.equals(
				paginationType,
				CollectionPaginationHelper.PAGINATION_TYPE_NUMERIC) ||
			Objects.equals(
				paginationType,
				CollectionPaginationHelper.PAGINATION_TYPE_REGULAR) ||
			Objects.equals(
				paginationType,
				CollectionPaginationHelper.PAGINATION_TYPE_SIMPLE)) {

			return true;
		}

		return false;
	}

}