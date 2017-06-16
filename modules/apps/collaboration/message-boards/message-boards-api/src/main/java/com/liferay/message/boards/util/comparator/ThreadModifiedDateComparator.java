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

import com.liferay.message.boards.kernel.model.MBThread;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Date;

/**
 * @author David Zhang
 */
public class ThreadModifiedDateComparator<T> extends OrderByComparator<T> {

	public static final String ORDER_BY_ASC =
		"modelCategory ASC, priority DESC, modifiedDate ASC, name ASC, " +
			"modelId ASC";

	public static final String ORDER_BY_DESC =
		"modelCategory ASC, priority DESC, modifiedDate DESC, name ASC, " +
			"modelId ASC";

	public static final String[] ORDER_BY_FIELDS =
		{"modelCategory", "priority", "modifiedDate", "name", "modelId"};

	public ThreadModifiedDateComparator() {
		this(false);
	}

	public ThreadModifiedDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(T t1, T t2) {
		Date modifiedDate1 = getModifiedDate(t1);
		Date modifiedDate2 = getModifiedDate(t2);

		int value = DateUtil.compareTo(modifiedDate1, modifiedDate2);

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

	protected Date getModifiedDate(Object obj) {
		if (obj instanceof MBThread) {
			MBThread mbThread = (MBThread)obj;

			return mbThread.getModifiedDate();
		}

		return null;
	}

	private final boolean _ascending;

}