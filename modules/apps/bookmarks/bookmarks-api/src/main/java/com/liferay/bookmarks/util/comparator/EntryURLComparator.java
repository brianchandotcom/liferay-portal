/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.util.comparator;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class EntryURLComparator extends OrderByComparator<BookmarksEntry> {

	public static final String ORDER_BY_ASC = "BookmarksEntry.url ASC";

	public static final String ORDER_BY_DESC = "BookmarksEntry.url DESC";

	public static final String[] ORDER_BY_FIELDS = {"url"};

	public static EntryURLComparator getInstance(boolean ascending) {
		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(BookmarksEntry entry1, BookmarksEntry entry2) {
		String url1 = StringUtil.toLowerCase(entry1.getUrl());
		String url2 = StringUtil.toLowerCase(entry2.getUrl());

		int value = url1.compareTo(url2);

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

	private EntryURLComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final EntryURLComparator _INSTANCE_ASCENDING =
		new EntryURLComparator(true);

	private static final EntryURLComparator _INSTANCE_DESCENDING =
		new EntryURLComparator(false);

	private final boolean _ascending;

}