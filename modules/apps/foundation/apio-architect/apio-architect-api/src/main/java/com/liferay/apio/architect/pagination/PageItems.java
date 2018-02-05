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

import java.util.Collection;

/**
 * Provides the information needed by {@link
 * com.liferay.apio.architect.endpoint.RootEndpoint} to construct a valid {@link
 * Page}.
 *
 * @author Alejandro Hernández
 * @param  <T> the model's type
 */
public class PageItems<T> {

	public PageItems(Collection<T> items, int totalCount) {
		_items = items;
		_totalCount = totalCount;
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
	 * Returns the total number of elements in the collection.
	 *
	 * @return the total number of elements in the collection
	 */
	public int getTotalCount() {
		return _totalCount;
	}

	private final Collection<T> _items;
	private final int _totalCount;

}