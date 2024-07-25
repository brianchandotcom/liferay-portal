/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.util.comparator;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Brian Wing Shun Chan
 */
public class EntryPriorityComparator extends OrderByComparator<BookmarksEntry> {

	public static final String ORDER_BY_ASC = "BookmarksEntry.priority ASC";

	public static final String ORDER_BY_DESC = "BookmarksEntry.priority DESC";

	public static final String[] ORDER_BY_FIELDS = {"priority"};

	public static EntryPriorityComparator getInstance(boolean ascending) {
		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(BookmarksEntry entry1, BookmarksEntry entry2) {
		int value = 0;

		if (entry1.getPriority() < entry2.getPriority()) {
			value = -1;
		}
		else if (entry1.getPriority() > entry2.getPriority()) {
			value = 1;
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

	private EntryPriorityComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final EntryPriorityComparator _INSTANCE_ASCENDING =
		new EntryPriorityComparator(true);

	private static final EntryPriorityComparator _INSTANCE_DESCENDING =
		new EntryPriorityComparator(false);

	private final boolean _ascending;

}