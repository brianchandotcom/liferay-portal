/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util.comparator;

import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.User;

/**
 * @author Brian Wing Shun Chan
 */
public class UserLoginDateComparator extends OrderByComparator {

	public static final String ORDER_BY_ASC =
		"loginDate ASC, lastName ASC, firstName ASC, middleName ASC";

	public static final String ORDER_BY_DESC =
		"loginDate DESC, lastName DESC, firstName DESC, middleName DESC";

	public static final String[] ORDER_BY_FIELDS = {
		"loginDate", "lastName", "firstName", "middleName"
	};

	public UserLoginDateComparator() {
		this(false);
	}

	public UserLoginDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Object obj1, Object obj2) {
		User user1 = (User)obj1;
		User user2 = (User)obj2;

		int value = DateUtil.compareTo(
			user1.getLoginDate(), user2.getLoginDate());

		if (value == 0) {
			value = user1.getLastName().compareTo(user2.getLastName());
		}

		if (value == 0) {
			value = user1.getFirstName().compareTo(user2.getFirstName());
		}

		if (value == 0) {
			value = user1.getMiddleName().compareTo(user2.getMiddleName());
		}

		if (_ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}
		else {
			return ORDER_BY_DESC;
		}
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private boolean _ascending;

}