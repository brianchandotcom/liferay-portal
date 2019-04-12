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

package com.liferay.frontend.taglib.clay.servlet.taglib.data;

/**
 * @author Iván Zaera Avellón
 */
public class Pagination {

	/**
	 * Helper constant to ignore pagination in methods using it. This constant
	 * can be tested with == in implementations or simply used as a valid
	 * @review
	 */
	public static final Pagination OFF = new Pagination(Integer.MAX_VALUE, 1);

	/**
	 * Construct a Pagination object.
	 * @param pageSize items per page
	 * @param page page number (starting at 1)
	 * @review
	 */
	public Pagination(int pageSize, int page) {
		_pageSize = pageSize;
		_page = page;
	}

	/**
	 * Get the end index of the pagination interval. The end index is 1 position
	 * after the last shown item.
	 * @return
	 * @review
	 */
	public int getEnd() {
		return _page * _pageSize;
	}

	/**
	 * Get page number (starting at 1).
	 * @return
	 * @review
	 */
	public int getPage() {
		return _page;
	}

	public int getPageSize() {
		return _pageSize;
	}

	/**
	 * Get the start index of the pagination interval (starting at 0)
	 * @return
	 * @review
	 */
	public int getStart() {
		return (_page - 1) * _pageSize;
	}

	private final int _page;
	private final int _pageSize;

}