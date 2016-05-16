/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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