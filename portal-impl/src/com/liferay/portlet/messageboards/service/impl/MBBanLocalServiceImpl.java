/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.messageboards.BannedUserException;
import com.liferay.portlet.messageboards.NoSuchBanException;
import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.service.base.MBBanLocalServiceBaseImpl;
import com.liferay.portlet.messageboards.util.MBUtil;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class MBBanLocalServiceImpl extends MBBanLocalServiceBaseImpl {

	@Override
	public MBBan addBan(
			long userId, long banUserId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();
		Date now = new Date();

		long banId = counterLocalService.increment();

		MBBan ban = mbBanPersistence.fetchByG_B(groupId, banUserId);

		if (ban == null) {
			ban = mbBanPersistence.create(banId);

			ban.setUuid(serviceContext.getUuid());
			ban.setGroupId(groupId);
			ban.setCompanyId(user.getCompanyId());
			ban.setUserId(user.getUserId());
			ban.setUserName(user.getFullName());
			ban.setCreateDate(serviceContext.getCreateDate(now));
			ban.setBanUserId(banUserId);
		}

		ban.setModifiedDate(serviceContext.getModifiedDate(now));

		mbBanPersistence.update(ban);

		return ban;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void checkBan(long groupId, long banUserId)
		throws PortalException, SystemException {

		if (hasBan(groupId, banUserId)) {
			throw new BannedUserException();
		}
	}

	@Override
	public void deleteBan(long banId) throws PortalException, SystemException {
		MBBan ban = mbBanPersistence.findByPrimaryKey(banId);

		deleteBan(ban);
	}

	@Override
	public void deleteBan(long banUserId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		long groupId = serviceContext.getScopeGroupId();

		try {
			MBBan ban = mbBanPersistence.findByG_B(groupId, banUserId);

			deleteBan(ban);
		}
		catch (NoSuchBanException nsbe) {
		}
	}

	@Override
	public void deleteBan(MBBan ban) throws PortalException, SystemException {
		mbBanPersistence.remove(ban);

		// System Event

		systemEventLocalService.addSystemEvent(
			ban.getGroupId(), MBBan.class.getName(), ban.getBanId(),
			ban.getUuid(), SystemEventConstants.TYPE_DELETE);
	}

	@Override
	public void deleteBansByBanUserId(long banUserId)
		throws PortalException, SystemException {

		List<MBBan> bans = mbBanPersistence.findByBanUserId(banUserId);

		for (MBBan ban : bans) {
			deleteBan(ban);
		}
	}

	@Override
	public void deleteBansByGroupId(long groupId)
		throws PortalException, SystemException {

		List<MBBan> bans = mbBanPersistence.findByGroupId(groupId);

		for (MBBan ban : bans) {
			deleteBan(ban);
		}
	}

	@Override
	public void expireBans() throws SystemException {
		if (PropsValues.MESSAGE_BOARDS_EXPIRE_BAN_INTERVAL <= 0) {
			return;
		}

		long now = System.currentTimeMillis();

		List<MBBan> bans = mbBanPersistence.findAll();

		for (MBBan ban : bans) {
			long unbanDate = MBUtil.getUnbanDate(
				ban, PropsValues.MESSAGE_BOARDS_EXPIRE_BAN_INTERVAL).getTime();

			if (now >= unbanDate) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Auto expiring ban " + ban.getBanId() + " on user " +
							ban.getBanUserId());
				}

				mbBanPersistence.remove(ban);
			}
		}
	}

	@Override
	public List<MBBan> getBans(long groupId, int start, int end)
		throws SystemException {

		return mbBanPersistence.findByGroupId(groupId, start, end);
	}

	@Override
	public int getBansCount(long groupId) throws SystemException {
		return mbBanPersistence.countByGroupId(groupId);
	}

	@Override
	public boolean hasBan(long groupId, long banUserId) throws SystemException {
		if (mbBanPersistence.fetchByG_B(groupId, banUserId) == null) {
			return false;
		}
		else {
			return true;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		MBBanLocalServiceImpl.class);

}