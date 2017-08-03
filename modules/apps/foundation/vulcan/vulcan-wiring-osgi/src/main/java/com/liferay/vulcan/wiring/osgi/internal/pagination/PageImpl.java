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

package com.liferay.vulcan.wiring.osgi.internal.pagination;

import com.liferay.vulcan.pagination.Page;

import java.util.Collection;

/**
 * @author Alejandro Hernández
 */
public class PageImpl<T> implements Page<T> {

	public PageImpl(
		Class<T> modelClass, Collection<T> items, int itemsPerPage,
		int pageNumber, int totalCount) {

		_modelClass = modelClass;
		_items = items;
		_itemsPerPage = itemsPerPage;
		_pageNumber = pageNumber;
		_totalCount = totalCount;
	}

	@Override
	public Collection<T> getItems() {
		return _items;
	}

	@Override
	public int getItemsPerPage() {
		return _itemsPerPage;
	}

	@Override
	public int getLastPageNumber() {
		return -Math.floorDiv(-_totalCount, _itemsPerPage);
	}

	@Override
	public Class<T> getModelClass() {
		return _modelClass;
	}

	@Override
	public int getPageNumber() {
		return _pageNumber;
	}

	@Override
	public int getTotalCount() {
		return _totalCount;
	}

	@Override
	public boolean hasNext() {
		if (getLastPageNumber() > _pageNumber) {
			return true;
		}

		return false;
	}

	@Override
	public boolean hasPrevious() {
		if (_pageNumber > 1) {
			return true;
		}

		return false;
	}

	private final Collection<T> _items;
	private final int _itemsPerPage;
	private final Class<T> _modelClass;
	private final int _pageNumber;
	private final int _totalCount;

}