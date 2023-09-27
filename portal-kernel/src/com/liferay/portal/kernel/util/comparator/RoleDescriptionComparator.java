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
public class RoleDescriptionComparator extends OrderByComparator<Role> {

	public static final String ORDER_BY_ASC =
		"Role_.description ASC, Role_.name ASC";

	public static final String ORDER_BY_DESC =
		"Role_.description DESC, Role_.name DESC";

	public static final String[] ORDER_BY_FIELDS = {"description", "name"};

	public RoleDescriptionComparator() {
		this(false);
	}

	public RoleDescriptionComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Role role1, Role role2) {
		String description1 = role1.getDescription();
		String description2 = role2.getDescription();

		int value = description1.compareTo(description2);

		if (value == 0) {
			String name1 = role1.getName();
			String name2 = role2.getName();

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