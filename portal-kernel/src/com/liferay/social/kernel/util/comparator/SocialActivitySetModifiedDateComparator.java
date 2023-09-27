/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.kernel.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.social.kernel.model.SocialActivitySet;

/**
 * @author Matthew Kong
 */
public class SocialActivitySetModifiedDateComparator
	extends OrderByComparator<SocialActivitySet> {

	public static final String ORDER_BY_ASC =
		"SocialActivitySet.modifiedDate ASC";

	public static final String ORDER_BY_DESC =
		"SocialActivitySet.modifiedDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"modifiedDate"};

	public SocialActivitySetModifiedDateComparator() {
		this(false);
	}

	public SocialActivitySetModifiedDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		SocialActivitySet activitySet1, SocialActivitySet activitySet2) {

		long modifiedDate1 = activitySet1.getModifiedDate();
		long modifiedDate2 = activitySet2.getModifiedDate();

		int value = 0;

		if (modifiedDate1 == modifiedDate2) {
			value = 0;
		}
		else if (modifiedDate1 < modifiedDate2) {
			value = -1;
		}
		else {
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

	private final boolean _ascending;

}