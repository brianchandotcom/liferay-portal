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
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Eduardo García
 */
public class BackgroundTaskComparatorFactoryUtil {

	public static OrderByComparator<BackgroundTask>
		getBackgroundTaskOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<BackgroundTask> orderByComparator = null;

		if (orderByCol.equals("completion-date")) {
			orderByComparator = new BackgroundTaskCompletionDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("create-date")) {
			orderByComparator = new BackgroundTaskCreateDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator = new BackgroundTaskNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

}