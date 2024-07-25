/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.util.comparator;

import com.liferay.message.boards.model.MBMessage;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Javier de Arcos
 */
public class MessageURLSubjectComparator extends OrderByComparator<MBMessage> {

	public static final String ORDER_BY_ASC =
		"priority DESC, urlSubject ASC, modifiedDate DESC";

	public static final String ORDER_BY_DESC =
		"priority DESC, urlSubject DESC, modifiedDate DESC";

	public static final String[] ORDER_BY_FIELDS = {
		"priority", "urlSubject", "modifiedDate"
	};

	public static MessageURLSubjectComparator getInstance(boolean ascending) {
		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(MBMessage message1, MBMessage message2) {
		String urlSubject1 = message1.getUrlSubject();

		int value = urlSubject1.compareTo(message2.getUrlSubject());

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

	private MessageURLSubjectComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final MessageURLSubjectComparator _INSTANCE_ASCENDING =
		new MessageURLSubjectComparator(true);

	private static final MessageURLSubjectComparator _INSTANCE_DESCENDING =
		new MessageURLSubjectComparator(false);

	private final boolean _ascending;

}