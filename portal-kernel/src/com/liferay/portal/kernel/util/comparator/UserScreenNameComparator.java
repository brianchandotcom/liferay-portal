/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util.comparator;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Brian Wing Shun Chan
 */
public class UserScreenNameComparator extends OrderByComparator<User> {

	public static final String ORDER_BY_ASC = "screenName ASC";

	public static final String ORDER_BY_DESC = "screenName DESC";

	public static final String[] ORDER_BY_FIELDS = {"screenName"};

	public static UserScreenNameComparator getInstance(boolean ascending) {
		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(User user1, User user2) {
		String screenName1 = user1.getScreenName();
		String screenName2 = user2.getScreenName();

		int value = screenName1.compareTo(screenName2);

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

	private UserScreenNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final UserScreenNameComparator _INSTANCE_ASCENDING =
		new UserScreenNameComparator(true);

	private static final UserScreenNameComparator _INSTANCE_DESCENDING =
		new UserScreenNameComparator(false);

	private final boolean _ascending;

}