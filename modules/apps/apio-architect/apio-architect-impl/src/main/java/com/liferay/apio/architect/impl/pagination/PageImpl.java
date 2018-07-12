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

package com.liferay.apio.architect.impl.pagination;

import com.liferay.apio.architect.operation.Operation;
import com.liferay.apio.architect.pagination.Page;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.uri.Path;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Represents a page in a collection. Writers can use instances of this
 * interface to create hypermedia representations.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 * @param  <T> the model's type
 */
public class PageImpl<T> implements Page<T> {

	public PageImpl(
		String resourceName, PageItems<T> pageItems, Pagination pagination,
		List<Operation> operations) {

		this(resourceName, pageItems, pagination, null, operations);
	}

	public PageImpl(
		String resourceName, PageItems<T> pageItems, Pagination pagination,
		Path path, List<Operation> operations) {

		_resourceName = resourceName;

		_items = pageItems.getItems();
		_itemsPerPage = pagination.getItemsPerPage();
		_pageNumber = pagination.getPageNumber();
		_totalCount = pageItems.getTotalCount();
		_path = path;
		_operations = operations;
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
		if (_totalCount == 0) {
			return 1;
		}

		return -Math.floorDiv(-_totalCount, _itemsPerPage);
	}

	@Override
	public List<Operation> getOperations() {
		return _operations;
	}

	@Override
	public int getPageNumber() {
		return _pageNumber;
	}

	@Override
	public Optional<Path> getPathOptional() {
		return Optional.ofNullable(_path);
	}

	@Override
	public String getResourceName() {
		return _resourceName;
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
	private final List<Operation> _operations;
	private final int _pageNumber;
	private final Path _path;
	private final String _resourceName;
	private final int _totalCount;

}