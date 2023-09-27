/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util.comparator;

import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Brian Wing Shun Chan
 */
public class RoleNameComparator extends OrderByComparator<Role> {

	public static final String ORDER_BY_ASC = "Role_.name ASC";

	public static final String ORDER_BY_DESC = "Role_.name DESC";

	public static final String[] ORDER_BY_FIELDS = {"name"};

	public RoleNameComparator() {
		this(false);
	}

	public RoleNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Role role1, Role role2) {
		String name1 = role1.getName();
		String name2 = role2.getName();

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