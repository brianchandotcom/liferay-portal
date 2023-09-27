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
public class GroupFriendlyURLComparator extends OrderByComparator<Group> {

	public static final String ORDER_BY_ASC = "groupFriendlyURL ASC";

	public static final String ORDER_BY_DESC = "groupFriendlyURL DESC";

	public static final String[] ORDER_BY_FIELDS = {"groupFriendlyURL"};

	public GroupFriendlyURLComparator() {
		this(false);
	}

	public GroupFriendlyURLComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Group group1, Group group2) {
		String friendlyURL1 = group1.getFriendlyURL();
		String friendlyURL2 = group2.getFriendlyURL();

		int value = friendlyURL1.compareTo(friendlyURL2);

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