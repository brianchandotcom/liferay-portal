/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util.comparator;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Brian Wing Shun Chan
 */
public class GroupTypeComparator extends OrderByComparator<Group> {

	public static final String ORDER_BY_ASC = "groupType ASC, groupName ASC";

	public static final String ORDER_BY_DESC = "groupType DESC, groupName DESC";

	public static final String[] ORDER_BY_FIELDS = {"groupType", "groupName"};

	public GroupTypeComparator() {
		this(false);
	}

	public GroupTypeComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Group group1, Group group2) {
		int value = 0;

		if (group1.getType() > group2.getType()) {
			value = 1;
		}
		else if (group1.getType() < group2.getType()) {
			value = -1;
		}

		if (value == 0) {
			String name1 = group1.getName();
			String name2 = group2.getName();

			value = name1.compareTo(name2);
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

	private final boolean _ascending;

}