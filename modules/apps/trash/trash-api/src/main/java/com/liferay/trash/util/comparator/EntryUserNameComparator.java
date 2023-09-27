/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.trash.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.trash.model.TrashEntry;

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