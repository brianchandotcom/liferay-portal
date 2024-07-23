/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.wish.list.util.comparator;

import com.liferay.commerce.wish.list.model.CommerceWishListItem;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceWishListItemCreateDateComparator
	extends OrderByComparator<CommerceWishListItem> {

	public static final String ORDER_BY_ASC = "createDate ASC";

	public static final String ORDER_BY_DESC = "createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public static CommerceWishListItemCreateDateComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		CommerceWishListItem commerceWishListItem1,
		CommerceWishListItem commerceWishListItem2) {

		int value = DateUtil.compareTo(
			commerceWishListItem1.getCreateDate(),
			commerceWishListItem2.getCreateDate());

		if (_ascending) {
			return value;
		}

		return Math.negateExact(value);
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

	private CommerceWishListItemCreateDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final CommerceWishListItemCreateDateComparator
		_INSTANCE_ASCENDING = new CommerceWishListItemCreateDateComparator(
			true);

	private static final CommerceWishListItemCreateDateComparator
		_INSTANCE_DESCENDING = new CommerceWishListItemCreateDateComparator(
			false);

	private final boolean _ascending;

}