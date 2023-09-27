/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.util.comparator;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Roberto Díaz
 */
public class EntryModifiedDateComparator extends OrderByComparator<BlogsEntry> {

	public static final String ORDER_BY_ASC =
		"BlogsEntry.modifiedDate ASC, BlogsEntry.entryId ASC";

	public static final String[] ORDER_BY_CONDITION_FIELDS = {
		"modifiedDate", "entryId"
	};

	public static final String ORDER_BY_DESC =
		"BlogsEntry.modifiedDate DESC, BlogsEntry.entryId DESC";

	public static final String[] ORDER_BY_FIELDS = {"modifiedDate", "entryId"};

	public EntryModifiedDateComparator() {
		this(false);
	}

	public EntryModifiedDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(BlogsEntry entry1, BlogsEntry entry2) {
		int value = DateUtil.compareTo(
			entry1.getModifiedDate(), entry2.getModifiedDate());

		if (value == 0) {
			if (entry1.getEntryId() < entry2.getEntryId()) {
				value = -1;
			}
			else if (entry1.getEntryId() > entry2.getEntryId()) {
				value = 1;
			}
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
	public String[] getOrderByConditionFields() {
		return ORDER_BY_CONDITION_FIELDS;
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