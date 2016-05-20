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

package com.liferay.blogs.kernel.util.comparator;

import com.liferay.blogs.kernel.model.BlogsEntry;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Christopher Kian
 */
public class EntryIdComparator extends OrderByComparator {

	public static final String ORDER_BY_ASC = "BlogsEntry.entryId ASC";

	public static final String[] ORDER_BY_CONDITION_FIELDS = {"entryId"};

	public static final String ORDER_BY_DESC = " BlogsEntry.entryId DESC";

	public static final String[] ORDER_BY_FIELDS = {"entryId"};

	public EntryIdComparator() {
		this(false);
	}

	public EntryIdComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Object obj1, Object obj2) {
		BlogsEntry entry1 = (BlogsEntry)obj1;
		BlogsEntry entry2 = (BlogsEntry)obj2;

		int value = 0;

		if (entry1.getEntryId() < entry2.getEntryId()) {
			value = -1;
		}
		else if (entry1.getEntryId() > entry2.getEntryId()) {
			value = 1;
		}

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