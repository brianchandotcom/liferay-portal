/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.trash.util.comparator;

import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.trash.model.TrashEntry;

/**
 * @author Sergio González
 */
public class EntryCreateDateComparator extends OrderByComparator<TrashEntry> {

	public static final String ORDER_BY_ASC = "TrashEntry.createDate ASC";

	public static final String ORDER_BY_DESC = "TrashEntry.createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public static EntryCreateDateComparator getInstance(boolean ascending) {
		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(TrashEntry entry1, TrashEntry entry2) {
		int value = DateUtil.compareTo(
			entry1.getCreateDate(), entry2.getCreateDate());

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

	private EntryCreateDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final EntryCreateDateComparator _INSTANCE_ASCENDING =
		new EntryCreateDateComparator(true);

	private static final EntryCreateDateComparator _INSTANCE_DESCENDING =
		new EntryCreateDateComparator(false);

	private final boolean _ascending;

}