/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.model.impl;

import com.liferay.message.boards.model.MBStatsUser;

import java.util.Date;

/**
 * @author Preston Crary
 */
public class MBStatsUserImpl implements MBStatsUser {

	public MBStatsUserImpl(long userId, int messageCount, Date lastPostDate) {
		_userId = userId;
		_messageCount = messageCount;
		_lastPostDate = lastPostDate;
	}

	@Override
	public Date getLastPostDate() {
		return _lastPostDate;
	}

	@Override
	public int getMessageCount() {
		return _messageCount;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	private final Date _lastPostDate;
	private final int _messageCount;
	private final long _userId;

}