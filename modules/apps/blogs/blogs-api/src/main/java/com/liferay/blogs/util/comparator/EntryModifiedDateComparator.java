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