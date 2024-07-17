/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.util.comparator;

import com.liferay.commerce.model.CommerceAddressRestriction;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceAddressRestrictionCreateDateComparator
	extends OrderByComparator<CommerceAddressRestriction> {

	public static final String ORDER_BY_ASC = "createDate ASC";

	public static final String ORDER_BY_DESC = "createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public static CommerceAddressRestrictionCreateDateComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		CommerceAddressRestriction commerceAddressRestriction1,
		CommerceAddressRestriction commerceAddressRestriction2) {

		int value = DateUtil.compareTo(
			commerceAddressRestriction1.getCreateDate(),
			commerceAddressRestriction2.getCreateDate());

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

	private CommerceAddressRestrictionCreateDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final CommerceAddressRestrictionCreateDateComparator
		_INSTANCE_ASCENDING =
			new CommerceAddressRestrictionCreateDateComparator(true);

	private static final CommerceAddressRestrictionCreateDateComparator
		_INSTANCE_DESCENDING =
			new CommerceAddressRestrictionCreateDateComparator(false);

	private final boolean _ascending;

}