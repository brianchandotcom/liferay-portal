/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.BackgroundTask;

/**
 * @author Michael C. Han
 */
public class BackgroundTaskCreateDateComparator extends OrderByComparator {

	public static final String ORDER_BY_ASC =
		"createDate ASC, backgroundTaskId ASC";

	public static final String ORDER_BY_DESC =
		"createDate DESC, backgroundTaskId DESC";

	public static final String[] ORDER_BY_FIELDS =
		{"createDate", "backgroundTaskId"};

	public BackgroundTaskCreateDateComparator() {
		this(false);
	}

	public BackgroundTaskCreateDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Object obj1, Object obj2) {
		BackgroundTask backgroundTask1 = (BackgroundTask)obj1;
		BackgroundTask backgroundTask2 = (BackgroundTask)obj2;

		int value = backgroundTask1.getCreateDate().compareTo(
			backgroundTask2.getCreateDate());

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

	private boolean _ascending;

}