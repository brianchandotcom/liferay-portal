/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.util.comparator;

import com.liferay.message.boards.model.MBMessage;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Javier de Arcos
 */
public class MessageModifiedDateComparator
	extends OrderByComparator<MBMessage> {

	public static final String ORDER_BY_ASC = "MBMessage.modifiedDate ASC";

	public static final String ORDER_BY_DESC = "MBMessage.modifiedDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"modifiedDate"};

	public static MessageModifiedDateComparator getInstance(boolean ascending) {
		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(MBMessage message1, MBMessage message2) {
		int value = DateUtil.compareTo(
			message1.getModifiedDate(), message2.getModifiedDate());

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

	private MessageModifiedDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final MessageModifiedDateComparator _INSTANCE_ASCENDING =
		new MessageModifiedDateComparator(true);

	private static final MessageModifiedDateComparator _INSTANCE_DESCENDING =
		new MessageModifiedDateComparator(false);

	private final boolean _ascending;

}