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

package com.liferay.commerce.frontend.internal.clay.data.set;

import java.util.List;

/**
 * @author Marco Leo
 */
public class ClayDataSetResponse {

	public ClayDataSetResponse(List<ClayDataSetDataRow> items, int totalItems) {
		_items = items;
		_totalItems = totalItems;
	}

	public List<ClayDataSetDataRow> getItems() {
		return _items;
	}

	public int getTotalItems() {
		return _totalItems;
	}

	private final List<ClayDataSetDataRow> _items;
	private final int _totalItems;

}