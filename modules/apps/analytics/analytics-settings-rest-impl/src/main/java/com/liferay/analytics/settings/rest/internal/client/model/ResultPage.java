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

package com.liferay.analytics.settings.rest.internal.client.model;

import java.util.List;

/**
 * @author Riccardo Ferrari
 */
public class ResultPage<T> {

	public ResultPage(List<T> items, PageMetadata pageMetadata) {
		_items = items;

		_number = pageMetadata.getNumber();
		_size = pageMetadata.getSize();
		_totalElements = pageMetadata.getTotalElements();
		_totalPages = pageMetadata.getTotalPages();
	}

	public List<T> getItems() {
		return _items;
	}

	public int getNumber() {
		return _number;
	}

	public int getSize() {
		return _size;
	}

	public int getTotalElements() {
		return _totalElements;
	}

	public int getTotalPages() {
		return _totalPages;
	}

	private final List<T> _items;
	private final int _number;
	private final int _size;
	private final int _totalElements;
	private final int _totalPages;

}