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

package com.liferay.batch.planner.rest.client.pagination;

import javax.annotation.Generated;

/**
 * @author Matija Petanjek
 * @generated
 */
@Generated("")
public class Pagination {

	public static Pagination of(int page, int pageSize) {
		return new Pagination(page, pageSize);
	}

	public int getEndPosition() {
		if ((_page < 0) || (_pageSize < 0)) {
			return -1;
		}

		return _page * _pageSize;
	}

	public int getPage() {
		return _page;
	}

	public int getPageSize() {
		return _pageSize;
	}

	public int getStartPosition() {
		if ((_page < 0) || (_pageSize < 0)) {
			return -1;
		}

		return (_page - 1) * _pageSize;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{\"page\": ");

		sb.append(_page);
		sb.append(", \"pageSize\": ");
		sb.append(_pageSize);
		sb.append("}");

		return sb.toString();
	}

	private Pagination(int page, int pageSize) {
		_page = page;
		_pageSize = pageSize;
	}

	private final int _page;
	private final int _pageSize;

}