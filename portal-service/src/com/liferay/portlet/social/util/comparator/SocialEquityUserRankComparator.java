/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.social.model.SocialEquityUser;

/**
 * @author Zsolt Berentey
 */
public class SocialEquityUserRankComparator extends OrderByComparator {

	public static final String ORDER_BY_ASC = "SocialEquityUser.rank ASC";

	public static final String ORDER_BY_DESC = "SocialEquityUser.rank DESC";

	public static final String[] ORDER_BY_FIELDS = {"rank"};

	public SocialEquityUserRankComparator() {
		this(false);
	}

	public SocialEquityUserRankComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Object obj1, Object obj2) {
		SocialEquityUser socialEquityUser1 = (SocialEquityUser)obj1;
		SocialEquityUser socialEquityUser2 = (SocialEquityUser)obj2;

		int value = 0;

		if (socialEquityUser1.getRank() > socialEquityUser2.getRank()) {
			value = 1;
		}
		else if (socialEquityUser1.getRank() < socialEquityUser2.getRank()) {
			value = -1;
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
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private boolean _ascending;

}