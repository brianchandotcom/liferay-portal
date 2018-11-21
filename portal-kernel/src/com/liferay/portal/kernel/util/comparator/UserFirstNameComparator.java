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

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Brian Wing Shun Chan
 */
public class UserFirstNameComparator extends OrderByComparator<User> {

	public static final String ORDER_BY_ASC =
		"firstName ASC, middleName ASC, lastName ASC";

	public static final String ORDER_BY_DESC =
		"firstName DESC, middleName DESC, lastName DESC";

	public static final String[] ORDER_BY_FIELDS = {
		"firstName", "middleName", "lastName"
	};

	public UserFirstNameComparator() {
		this(false);
	}

	public UserFirstNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(User user1, User user2) {
		String firstName1 = user1.getFirstName();
		String firstName2 = user2.getFirstName();

		int value = firstName1.compareTo(firstName2);

		if (value == 0) {
			String middleName1 = user1.getMiddleName();
			String middleName2 = user2.getMiddleName();

			value = middleName1.compareTo(middleName2);
		}

		if (value == 0) {
			String lastName1 = user1.getLastName();
			String lastName2 = user2.getLastName();

			value = lastName1.compareTo(lastName2);
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