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
public class UserGroupNameComparator extends OrderByComparator<UserGroup> {

	public static final String ORDER_BY_ASC = "UserGroup.name ASC";

	public static final String ORDER_BY_DESC = "UserGroup.name DESC";

	public static final String[] ORDER_BY_FIELDS = {"name"};

	public UserGroupNameComparator() {
		this(false);
	}

	public UserGroupNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(UserGroup userGroup1, UserGroup userGroup2) {
		String name1 = userGroup1.getName();
		String name2 = userGroup2.getName();

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

	private final boolean _ascending;

}