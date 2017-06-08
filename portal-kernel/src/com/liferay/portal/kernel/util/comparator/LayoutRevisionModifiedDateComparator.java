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

package com.liferay.portal.kernel.util.comparator;

import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Akos Thurzo
 */
public class LayoutRevisionModifiedDateComparator
	extends OrderByComparator<LayoutRevision> {

	public static final String ORDER_BY_ASC = "LayoutRevision.modifiedDate ASC";

	public static final String ORDER_BY_DESC =
		"LayoutRevision.modifiedDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"modifiedDate"};

	public LayoutRevisionModifiedDateComparator() {
		this(false);
	}

	public LayoutRevisionModifiedDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		LayoutRevision layoutRevision1, LayoutRevision layoutRevision2) {

		int value = DateUtil.compareTo(
			layoutRevision1.getModifiedDate(),
			layoutRevision2.getModifiedDate());

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

	private final boolean _ascending;

}