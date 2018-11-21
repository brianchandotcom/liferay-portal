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

package com.liferay.sharing.util.comparator;

import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.sharing.model.SharingEntry;

/**
 * @author Sergio González
 */
public class SharingEntryModifiedDateComparator
	extends OrderByComparator<SharingEntry> {

	public static final String ORDER_BY_ASC =
		"SharingEntry.modifiedDate ASC, SharingEntry.sharingEntryId ASC";

	public static final String[] ORDER_BY_CONDITION_FIELDS = {
		"modifiedDate", "sharingEntryId"
	};

	public static final String ORDER_BY_DESC =
		"SharingEntry.modifiedDate DESC, SharingEntry.sharingEntryId DESC";

	public static final String[] ORDER_BY_FIELDS = {
		"modifiedDate", "sharingEntryId"
	};

	public SharingEntryModifiedDateComparator() {
		this(false);
	}

	public SharingEntryModifiedDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(SharingEntry sharingEntry1, SharingEntry sharingEntry2) {
		int value = DateUtil.compareTo(
			sharingEntry1.getModifiedDate(), sharingEntry2.getModifiedDate());

		if (value == 0) {
			if (sharingEntry1.getSharingEntryId() <
					sharingEntry2.getSharingEntryId()) {

				value = -1;
			}
			else if (sharingEntry1.getSharingEntryId() >
						sharingEntry2.getSharingEntryId()) {

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