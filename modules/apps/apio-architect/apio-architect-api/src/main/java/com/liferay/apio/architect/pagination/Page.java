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

import aQute.bnd.annotation.ProviderType;

import com.liferay.apio.architect.operation.Operation;
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
@ProviderType
public class Page<T> {

	public Page(
		String resourceName, PageItems<T> pageItems, Pagination pagination,
		List<Operation> operations) {

		this(resourceName, pageItems, pagination, null, operations);
	}

	public Page(
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

	/**
	 * Returns the page's items.
	 *
	 * @return the page's items
	 */
	public Collection<T> getItems() {
		return _items;
	}

	/**
	 * Returns the number of items the user selected on the page.
	 *
	 * @return the number of items the user selected on the page
	 */
	public int getItemsPerPage() {
		return _itemsPerPage;
	}

	/**
	 * Returns the number of the collection's last page.
	 *
	 * @return the number of the collection's last page
	 */
	public int getLastPageNumber() {
		if (_totalCount == 0) {
			return 1;
		}

		return -Math.floorDiv(-_totalCount, _itemsPerPage);
	}

	/**
	 * Returns the list of operations for the page.
	 *
	 * @return the list of operations
	 */
	public List<Operation> getOperations() {
		return _operations;
	}

	/**
	 * Returns the page number in the collection.
	 *
	 * @return the page number in the collection
	 */
	public int getPageNumber() {
		return _pageNumber;
	}

	/**
	 * Returns the page path, if present. Returns {@code Optional#empty()}
	 * otherwise.
	 *
	 * @return the path, if present; {@code Optional#empty()} otherwise
	 */
	public Optional<Path> getPathOptional() {
		return Optional.ofNullable(_path);
	}

	/**
	 * Returns the resource's name.
	 *
	 * @return the resource's name
	 */
	public String getResourceName() {
		return _resourceName;
	}

	/**
	 * Returns the total number of elements in the collection.
	 *
	 * @return the total number of elements in the collection
	 */
	public int getTotalCount() {
		return _totalCount;
	}

	/**
	 * Returns {@code true} if another page follows this page in the collection.
	 *
	 * @return {@code true} if another page follows this page in the collection;
	 *         {@code false} otherwise
	 */
	public boolean hasNext() {
		if (getLastPageNumber() > _pageNumber) {
			return true;
		}

		return false;
	}

	/**
	 * Returns {@code true} if another page precedes this page in the
	 * collection.
	 *
	 * @return {@code true} if another page precedes this page in the
	 *         collection; {@code false} otherwise
	 */
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