/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util.comparator;

import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Brian Wing Shun Chan
 */
public class UserGroupIdComparator extends OrderByComparator<UserGroup> {

	public static final String ORDER_BY_ASC = "UserGroup.userGroupId ASC";

	public static final String ORDER_BY_DESC = "UserGroup.userGroupId DESC";

	public static final String[] ORDER_BY_FIELDS = {"userGroupId"};

	public static UserGroupIdComparator getInstance(boolean ascending) {
		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(UserGroup userGroup1, UserGroup userGroup2) {
		long userGroupId1 = userGroup1.getUserGroupId();
		long userGroupId2 = userGroup2.getUserGroupId();

		int value = 0;

		if (userGroupId1 < userGroupId2) {
			value = -1;
		}
		else if (userGroupId1 > userGroupId2) {
			value = 1;
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
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private UserGroupIdComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final UserGroupIdComparator _INSTANCE_ASCENDING =
		new UserGroupIdComparator(true);

	private static final UserGroupIdComparator _INSTANCE_DESCENDING =
		new UserGroupIdComparator(false);

	private final boolean _ascending;

}