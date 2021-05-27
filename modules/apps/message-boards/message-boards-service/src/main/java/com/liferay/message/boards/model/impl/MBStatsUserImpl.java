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