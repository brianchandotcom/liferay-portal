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
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Brian Wing Shun Chan
 */
public class ThreadLastPostDateComparator extends OrderByComparator<MBThread> {

	public static final String ORDER_BY_ASC =
		"MBThread.lastPostDate ASC, MBThread.threadId ASC";

	public static final String[] ORDER_BY_CONDITION_FIELDS = {"lastPostDate"};

	public static final String ORDER_BY_DESC =
		"MBThread.lastPostDate DESC, MBThread.threadId DESC";

	public static final String[] ORDER_BY_FIELDS = {"lastPostDate", "threadId"};

	public ThreadLastPostDateComparator() {
		this(false);
	}

	public ThreadLastPostDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(MBThread thread1, MBThread thread2) {
		int value = DateUtil.compareTo(
			thread1.getLastPostDate(), thread2.getLastPostDate());

		if (value == 0) {
			if (thread1.getThreadId() < thread2.getThreadId()) {
				value = -1;
			}
			else if (thread1.getThreadId() > thread2.getThreadId()) {
				value = 1;
			}
		}

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
	public String[] getOrderByConditionFields() {
		return ORDER_BY_CONDITION_FIELDS;
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