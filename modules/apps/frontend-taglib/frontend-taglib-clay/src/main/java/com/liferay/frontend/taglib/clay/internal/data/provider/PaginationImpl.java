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

package com.liferay.frontend.taglib.clay.internal.data.provider;

import com.liferay.frontend.taglib.clay.data.Pagination;

/**
 * @author Marco Leo
 */
public class PaginationImpl implements Pagination {

	public PaginationImpl(int pageSize, int page) {
		_pageSize = pageSize;
		_page = page;
	}

	@Override
	public int getEndPosition() {
		return _page * _pageSize;
	}

	@Override
	public int getPage() {
		return _page;
	}

	@Override
	public int getPageSize() {
		return _pageSize;
	}

	@Override
	public int getStartPosition() {
		return (_page - 1) * _pageSize;
	}

	private final int _page;
	private final int _pageSize;

}