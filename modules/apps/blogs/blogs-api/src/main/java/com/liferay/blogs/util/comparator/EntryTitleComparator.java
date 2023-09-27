/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.util.comparator;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Sergio González
 */
public class EntryTitleComparator extends OrderByComparator<BlogsEntry> {

	public static final String ORDER_BY_ASC = "BlogsEntry.title ASC";

	public static final String ORDER_BY_DESC = "BlogsEntry.title DESC";

	public static final String[] ORDER_BY_FIELDS = {"title"};

	public EntryTitleComparator() {
		this(false);
	}

	public EntryTitleComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(BlogsEntry entry1, BlogsEntry entry2) {
		String title1 = entry1.getTitle();
		String title2 = entry2.getTitle();

		int value = title1.compareTo(title2);

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