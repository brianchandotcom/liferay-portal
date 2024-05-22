/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.background.task.internal.comparator;

import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Eduardo García
 */
public class BackgroundTaskCompletionDateComparator
	extends OrderByComparator<BackgroundTask> {

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
			return _ORDER_BY_ASC;
		}

		return _ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return _ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private static final String _ORDER_BY_ASC =
		"BackgroundTask.completionDate ASC";

	private static final String _ORDER_BY_DESC =
		"BackgroundTask.completionDate DESC";

	private static final String[] _ORDER_BY_FIELDS = {"completionDate"};

	private final boolean _ascending;

}