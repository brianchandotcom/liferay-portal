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

package com.liferay.portlet.trash.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.trash.model.TrashEntry;

/**
 * @author Sergio González
 */
public class EntryUserNameComparator extends OrderByComparator<TrashEntry> {

	public static final String ORDER_BY_ASC = "TrashEntry.userName ASC";

	public static final String ORDER_BY_DESC = "TrashEntry.userName DESC";

	public static final String[] ORDER_BY_FIELDS = {"userName"};

	public EntryUserNameComparator() {
		this(false);
	}

	public EntryUserNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(TrashEntry entry1, TrashEntry entry2) {
		String name1 = StringUtil.toLowerCase(entry1.getUserName());
		String name2 = StringUtil.toLowerCase(entry2.getUserName());

		int value = name1.compareTo(name2);

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

	private final boolean _ascending;

}