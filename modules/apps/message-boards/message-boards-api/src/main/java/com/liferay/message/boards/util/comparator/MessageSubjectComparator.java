/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.util.comparator;

import com.liferay.message.boards.model.MBMessage;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Javier de Arcos
 */
public class MessageSubjectComparator extends OrderByComparator<MBMessage> {

	public static final String ORDER_BY_ASC =
		"priority DESC, subject ASC, modifiedDate DESC";

	public static final String ORDER_BY_DESC =
		"priority DESC, subject DESC, modifiedDate DESC";

	public static final String[] ORDER_BY_FIELDS = {
		"priority", "subject", "modifiedDate"
	};

	public MessageSubjectComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(MBMessage message1, MBMessage message2) {
		String subject1 = StringUtil.toLowerCase(message1.getSubject());
		String subject2 = StringUtil.toLowerCase(message2.getSubject());

		int value = subject1.compareTo(subject2);

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