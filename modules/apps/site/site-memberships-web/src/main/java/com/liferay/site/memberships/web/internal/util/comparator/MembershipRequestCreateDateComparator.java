/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.memberships.web.internal.util.comparator;

import com.liferay.portal.kernel.model.MembershipRequest;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Eudaldo Alonso
 */
public class MembershipRequestCreateDateComparator
	extends OrderByComparator<MembershipRequest> {

	public static final String ORDER_BY_ASC =
		"MembershipRequest.createDate ASC";

	public static final String ORDER_BY_DESC =
		"MembershipRequest.createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public static MembershipRequestCreateDateComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		MembershipRequest membershipRequest1,
		MembershipRequest membershipRequest2) {

		int value = DateUtil.compareTo(
			membershipRequest1.getCreateDate(),
			membershipRequest2.getCreateDate());

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

	private MembershipRequestCreateDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final MembershipRequestCreateDateComparator
		_INSTANCE_ASCENDING = new MembershipRequestCreateDateComparator(true);

	private static final MembershipRequestCreateDateComparator
		_INSTANCE_DESCENDING = new MembershipRequestCreateDateComparator(false);

	private final boolean _ascending;

}