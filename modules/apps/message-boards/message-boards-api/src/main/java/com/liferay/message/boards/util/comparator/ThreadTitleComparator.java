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

package com.liferay.message.boards.util.comparator;

import com.liferay.message.boards.model.MBThread;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Sergio González
 */
public class ThreadTitleComparator<T> extends OrderByComparator<T> {

	public static final String ORDER_BY_ASC =
		"priority DESC, title ASC, modifiedDate DESC";

	public static final String ORDER_BY_DESC =
		"priority DESC, title DESC, modifiedDate DESC";

	public static final String[] ORDER_BY_FIELDS =
		{"priority", "title", "modifiedDate"};

	public ThreadTitleComparator() {
		this(false);
	}

	public ThreadTitleComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(T t1, T t2) {
		String name1 = StringUtil.toLowerCase(getThreadTitle(t1));
		String name2 = StringUtil.toLowerCase(getThreadTitle(t2));

		int value = name1.compareTo(name2);

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}

		return ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	protected String getThreadTitle(Object obj) {
		if (obj instanceof MBThread) {
			MBThread mbThread = (MBThread)obj;

			return mbThread.getTitle();
		}

		return null;
	}

	private final boolean _ascending;

}