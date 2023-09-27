/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.rest.internal.client.pagination;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Riccardo Ferrari
 */
public class Page<T> {

	public static <T> Page<T> of(Collection<T> items) {
		return new Page<>(items);
	}

	public static <T> Page<T> of(
		Collection<T> items, Pagination pagination, long totalCount) {

		return new Page<>(items, pagination, totalCount);
	}

	public Collection<T> getItems() {
		return new ArrayList<>(_items);
	}

	public long getLastPage() {
		if ((_pageSize == 0) || (_totalCount == 0)) {
			return 1;
		}

		return -Math.floorDiv(-_totalCount, _pageSize);
	}

	public long getPage() {
		return _page;
	}

	public long getPageSize() {
		return _pageSize;
	}

	public long getTotalCount() {
		return _totalCount;
	}

	public boolean hasNext() {
		if (getLastPage() > _page) {
			return true;
		}

		return false;
	}

	public boolean hasPrevious() {
		if (_page > 1) {
			return true;
		}

		return false;
	}

	private Page(Collection<T> items) {
		_items = items;

		_page = 1;
		_pageSize = items.size();
		_totalCount = items.size();
	}

	private Page(Collection<T> items, Pagination pagination, long totalCount) {
		_items = items;

		if (pagination == null) {
			_page = 0;
			_pageSize = 0;
		}
		else {
			_page = pagination.getPage();
			_pageSize = pagination.getPageSize();
		}

		_totalCount = totalCount;
	}

	private final Collection<T> _items;
	private final long _page;
	private final long _pageSize;
	private final long _totalCount;

}