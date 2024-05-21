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

	public static EntryUserNameComparator get(boolean ascending) {
		if (ascending) {
			return _ASCENDING;
		}

		return _DESCENDING;
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
			return _ORDER_BY_ASC;
		}

		return _ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return _ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private EntryUserNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final EntryUserNameComparator _ASCENDING =
		new EntryUserNameComparator(true);

	private static final EntryUserNameComparator _DESCENDING =
		new EntryUserNameComparator(false);

	private static final String _ORDER_BY_ASC = "TrashEntry.userName ASC";

	private static final String _ORDER_BY_DESC = "TrashEntry.userName DESC";

	private static final String[] _ORDER_BY_FIELDS = {"userName"};

	private final boolean _ascending;

}