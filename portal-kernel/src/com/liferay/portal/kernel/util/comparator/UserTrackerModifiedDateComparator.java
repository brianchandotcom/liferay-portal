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

package com.liferay.portal.kernel.util.comparator;

import com.liferay.portal.kernel.model.UserTracker;
import com.liferay.portal.kernel.util.DateUtil;

import java.io.Serializable;

import java.util.Comparator;

/**
 * @author Brian Wing Shun Chan
 */
public class UserTrackerModifiedDateComparator
	implements Comparator<UserTracker>, Serializable {

	public UserTrackerModifiedDateComparator() {
		this(false);
	}

	public UserTrackerModifiedDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(UserTracker userTracker1, UserTracker userTracker2) {
		int value = DateUtil.compareTo(
			userTracker1.getModifiedDate(), userTracker2.getModifiedDate());

		if (_ascending) {
			return value;
		}

		return -value;
	}

	private final boolean _ascending;

}