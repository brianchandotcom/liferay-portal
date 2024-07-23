/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.notification.util.comparator;

import com.liferay.commerce.notification.model.CommerceNotificationAttachment;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceNotificationAttachmentCreateDateComparator
	extends OrderByComparator<CommerceNotificationAttachment> {

	public static final String ORDER_BY_ASC = "createDate ASC";

	public static final String ORDER_BY_DESC = "createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public static CommerceNotificationAttachmentCreateDateComparator
		getInstance(boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		CommerceNotificationAttachment commerceNotificationAttachment1,
		CommerceNotificationAttachment commerceNotificationAttachment2) {

		int value = DateUtil.compareTo(
			commerceNotificationAttachment1.getCreateDate(),
			commerceNotificationAttachment2.getCreateDate());

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

	private CommerceNotificationAttachmentCreateDateComparator(
		boolean ascending) {

		_ascending = ascending;
	}

	private static final CommerceNotificationAttachmentCreateDateComparator
		_INSTANCE_ASCENDING =
			new CommerceNotificationAttachmentCreateDateComparator(true);

	private static final CommerceNotificationAttachmentCreateDateComparator
		_INSTANCE_DESCENDING =
			new CommerceNotificationAttachmentCreateDateComparator(false);

	private final boolean _ascending;

}