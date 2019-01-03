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

package com.liferay.background.task.kernel.util.comparator;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Eduardo García
 */
public class BackgroundTaskCompletionDateComparator
	extends OrderByComparator<BackgroundTask> {

	public static final String ORDER_BY_ASC =
		"BackgroundTask.completionDate ASC";

	public static final String ORDER_BY_DESC =
		"BackgroundTask.completionDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"completionDate"};

	public BackgroundTaskCompletionDateComparator() {
		this(false);
	}

	public BackgroundTaskCompletionDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		BackgroundTask backgroundTask1, BackgroundTask backgroundTask2) {

		int value = DateUtil.compareTo(
			backgroundTask1.getCompletionDate(),
			backgroundTask2.getCompletionDate());

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

	private final boolean _ascending;

}