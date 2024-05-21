/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.trash.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.trash.model.TrashEntry;

/**
 * @author Sergio González
 */
public class EntryTypeComparator extends OrderByComparator<TrashEntry> {

	public static EntryTypeComparator get(boolean ascending) {
		if (ascending) {
			return _ASCENDING;
		}

		return _DESCENDING;
	}

	@Override
	public int compare(TrashEntry entry1, TrashEntry entry2) {
		int value = 0;

		if (entry1.getClassNameId() > entry2.getClassNameId()) {
			value = 1;
		}
		else if (entry1.getClassNameId() < entry2.getClassNameId()) {
			value = -1;
		}

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

	private EntryTypeComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final EntryTypeComparator _ASCENDING =
		new EntryTypeComparator(true);

	private static final EntryTypeComparator _DESCENDING =
		new EntryTypeComparator(false);

	private static final String _ORDER_BY_ASC = "classNameId ASC";

	private static final String _ORDER_BY_DESC = "classNameId DESC";

	private static final String[] _ORDER_BY_FIELDS = {"classNameId"};

	private final boolean _ascending;

}