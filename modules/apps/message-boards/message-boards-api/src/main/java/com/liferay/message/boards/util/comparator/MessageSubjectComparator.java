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