/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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