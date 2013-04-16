/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portlet.social.model.SocialActivitySet;

/**
 * @author Matthew Kong
 */
public class SocialActivitySetModifiedDateComparator extends OrderByComparator {

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
	public int compare(Object obj1, Object obj2) {
		SocialActivitySet activitySet1 = (SocialActivitySet)obj1;
		SocialActivitySet activitySet2 = (SocialActivitySet)obj2;

		long time1 = activitySet1.getModifiedDate();
		long time2 = activitySet2.getModifiedDate();

		int value = 0;

		if (time1 == time2) {
			value = 0;
		}
		else if (time1 < time2) {
			value = -1;
		}
		else {
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
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private boolean _ascending;

}