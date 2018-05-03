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

package com.liferay.blogs.util.comparator;

import com.liferay.blogs.model.BlogsStatsUser;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Brian Wing Shun Chan
 */
public class StatsUserLastPostDateComparator
	extends OrderByComparator<BlogsStatsUser> {

	public static final String ORDER_BY_ASC = "BlogsStatsUser.lastPostDate ASC";

	public static final String ORDER_BY_DESC =
		"BlogsStatsUser.lastPostDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"lastPostDate"};

	public StatsUserLastPostDateComparator() {
		this(false);
	}

	public StatsUserLastPostDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(BlogsStatsUser statsUser1, BlogsStatsUser statsUser2) {
		int value = DateUtil.compareTo(
			statsUser1.getLastPostDate(), statsUser2.getLastPostDate());

		if (_ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}
		else {
			return ORDER_BY_DESC;
		}
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}