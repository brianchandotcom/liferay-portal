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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.message.boards.kernel.model.MBStatsUser;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portlet.messageboards.service.base.MBStatsUserLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @deprecated As of 7.0.0, replaced by {@link
 *             com.liferay.message.boards.service.impl.MBStatsUserLocalServiceImpl}
 */
@Deprecated
public class MBStatsUserLocalServiceImpl
	extends MBStatsUserLocalServiceBaseImpl {

	@Override
	public MBStatsUser addStatsUser(long groupId, long userId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBStatsUserLocalServiceImpl");
	}

	@Override
	public void deleteStatsUser(long statsUserId) throws PortalException {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBStatsUserLocalServiceImpl");
	}

	@Override
	public void deleteStatsUser(MBStatsUser statsUser) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBStatsUserLocalServiceImpl");
	}

	@Override
	public void deleteStatsUsersByGroupId(long groupId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBStatsUserLocalServiceImpl");
	}

	@Override
	public void deleteStatsUsersByUserId(long userId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBStatsUserLocalServiceImpl");
	}

	@Override
	public Date getLastPostDateByUserId(long groupId, long userId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBStatsUserLocalServiceImpl");
	}

	@Override
	public long getMessageCountByGroupId(long groupId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBStatsUserLocalServiceImpl");
	}

	@Override
	public long getMessageCountByUserId(long userId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBStatsUserLocalServiceImpl");
	}

	@Override
	public MBStatsUser getStatsUser(long groupId, long userId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBStatsUserLocalServiceImpl");
	}

	@Override
	public List<MBStatsUser> getStatsUsersByGroupId(
			long groupId, int start, int end)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBStatsUserLocalServiceImpl");
	}

	@Override
	public int getStatsUsersByGroupIdCount(long groupId)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBStatsUserLocalServiceImpl");
	}

	@Override
	public List<MBStatsUser> getStatsUsersByUserId(long userId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBStatsUserLocalServiceImpl");
	}

	@Override
	public MBStatsUser updateStatsUser(long groupId, long userId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBStatsUserLocalServiceImpl");
	}

	@Override
	public MBStatsUser updateStatsUser(
		long groupId, long userId, Date lastPostDate) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBStatsUserLocalServiceImpl");
	}

	@Override
	public MBStatsUser updateStatsUser(
		long groupId, long userId, int messageCount, Date lastPostDate) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBStatsUserLocalServiceImpl");
	}

}