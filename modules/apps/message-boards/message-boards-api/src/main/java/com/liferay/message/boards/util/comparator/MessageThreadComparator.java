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
import com.liferay.portal.kernel.util.DateUtil;

import java.io.Serializable;

import java.util.Comparator;

/**
 * @author Brian Wing Shun Chan
 */
public class MessageThreadComparator
	implements Comparator<MBMessage>, Serializable {

	public MessageThreadComparator() {
		this(true);
	}

	public MessageThreadComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(MBMessage msg1, MBMessage msg2) {
		Long parentMessageId1 = Long.valueOf(msg1.getParentMessageId());
		Long parentMessageId2 = Long.valueOf(msg2.getParentMessageId());

		int value = parentMessageId1.compareTo(parentMessageId2);

		if (value == 0) {
			value = DateUtil.compareTo(
				msg1.getCreateDate(), msg2.getCreateDate());
		}

		if (value == 0) {
			Long messageId1 = Long.valueOf(msg1.getMessageId());
			Long messageId2 = Long.valueOf(msg2.getMessageId());

			value = messageId1.compareTo(messageId2);
		}

		if (_ascending) {
			return value;
		}

		return -value;
	}

	private final boolean _ascending;

}