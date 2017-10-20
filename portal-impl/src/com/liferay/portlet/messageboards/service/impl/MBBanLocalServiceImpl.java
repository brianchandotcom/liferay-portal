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

import com.liferay.message.boards.kernel.model.MBBan;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portlet.messageboards.service.base.MBBanLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @deprecated As of 7.0.0, replaced by {@link
 *             com.liferay.message.boards.service.impl.MBBanLocalServiceImpl}
 */
@Deprecated
public class MBBanLocalServiceImpl extends MBBanLocalServiceBaseImpl {

	@Override
	public MBBan addBan(
			long userId, long banUserId, ServiceContext serviceContext)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBBanLocalServiceImpl");
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void checkBan(long groupId, long banUserId) throws PortalException {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBBanLocalServiceImpl");
	}

	@Override
	public void deleteBan(long banId) throws PortalException {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBBanLocalServiceImpl");
	}

	@Override
	public void deleteBan(long banUserId, ServiceContext serviceContext) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBBanLocalServiceImpl");
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public void deleteBan(MBBan ban) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBBanLocalServiceImpl");
	}

	@Override
	public void deleteBansByBanUserId(long banUserId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBBanLocalServiceImpl");
	}

	@Override
	public void deleteBansByGroupId(long groupId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBBanLocalServiceImpl");
	}

	@Override
	public void expireBans() {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBBanLocalServiceImpl");
	}

	@Override
	public List<MBBan> getBans(long groupId, int start, int end) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBBanLocalServiceImpl");
	}

	@Override
	public int getBansCount(long groupId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBBanLocalServiceImpl");
	}

	@Override
	public boolean hasBan(long groupId, long banUserId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.message.boards.service.impl." +
					"MBBanLocalServiceImpl");
	}

}