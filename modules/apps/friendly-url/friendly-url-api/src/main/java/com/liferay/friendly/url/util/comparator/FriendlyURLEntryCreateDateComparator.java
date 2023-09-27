/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.util.comparator;

import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Sergio González
 */
public class FriendlyURLEntryCreateDateComparator
	extends OrderByComparator<FriendlyURLEntry> {

	public static final String ORDER_BY_ASC = "FriendlyURL.createDate ASC";

	public static final String ORDER_BY_DESC = "FriendlyURL.createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public FriendlyURLEntryCreateDateComparator() {
		this(true);
	}

	public FriendlyURLEntryCreateDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		FriendlyURLEntry friendlyURLEntry1,
		FriendlyURLEntry friendlyURLEntry2) {

		int value = DateUtil.compareTo(
			friendlyURLEntry1.getCreateDate(),
			friendlyURLEntry2.getCreateDate());

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