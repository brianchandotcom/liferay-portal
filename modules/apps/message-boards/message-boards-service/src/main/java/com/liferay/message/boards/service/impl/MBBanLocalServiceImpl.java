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

package com.liferay.message.boards.service.impl;

import com.liferay.message.boards.exception.BannedUserException;
import com.liferay.message.boards.model.MBBan;
import com.liferay.message.boards.service.base.MBBanLocalServiceBaseImpl;
import com.liferay.message.boards.util.MBUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.util.PropsValues;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class MBBanLocalServiceImpl extends MBBanLocalServiceBaseImpl {

	@Override
	public MBBan addBan(
			long userId, long banUserId, ServiceContext serviceContext)
		throws PortalException {

		User user = _userLocalService.getUser(userId);
		long groupId = serviceContext.getScopeGroupId();

		long banId = counterLocalService.increment();

		MBBan ban = mbBanPersistence.fetchByG_B(groupId, banUserId);

		if (ban == null) {
			Date now = new Date();

			ban = mbBanPersistence.create(banId);

			ban.setUuid(serviceContext.getUuid());
			ban.setGroupId(groupId);
			ban.setCompanyId(user.getCompanyId());
			ban.setUserId(user.getUserId());
			ban.setUserName(user.getFullName());
			ban.setCreateDate(serviceContext.getCreateDate(now));
			ban.setModifiedDate(serviceContext.getModifiedDate(now));
			ban.setBanUserId(banUserId);
		}

		mbBanPersistence.update(ban);

		return ban;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void checkBan(long groupId, long banUserId) throws PortalException {
		if (hasBan(groupId, banUserId)) {
			throw new BannedUserException("Banned user " + banUserId);
		}
	}

	@Override
	public void deleteBan(long banId) throws PortalException {
		MBBan ban = mbBanPersistence.findByPrimaryKey(banId);

		mbBanLocalService.deleteBan(ban);
	}

	@Override
	public void deleteBan(long banUserId, ServiceContext serviceContext) {
		long groupId = serviceContext.getScopeGroupId();

		MBBan ban = mbBanPersistence.fetchByG_B(groupId, banUserId);

		if (ban != null) {
			mbBanLocalService.deleteBan(ban);
		}
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public void deleteBan(MBBan ban) {
		mbBanPersistence.remove(ban);
	}

	@Override
	public void deleteBansByBanUserId(long banUserId) {
		List<MBBan> bans = mbBanPersistence.findByBanUserId(banUserId);

		for (MBBan ban : bans) {
			mbBanLocalService.deleteBan(ban);
		}
	}

	@Override
	public void deleteBansByGroupId(long groupId) {
		List<MBBan> bans = mbBanPersistence.findByGroupId(groupId);

		for (MBBan ban : bans) {
			mbBanLocalService.deleteBan(ban);
		}
	}

	@Override
	public void expireBans() {
		if (PropsValues.MESSAGE_BOARDS_EXPIRE_BAN_INTERVAL <= 0) {
			return;
		}

		long now = System.currentTimeMillis();

		List<MBBan> bans = mbBanPersistence.findAll();

		for (MBBan ban : bans) {
			Date unbanDate = MBUtil.getUnbanDate(
				ban, PropsValues.MESSAGE_BOARDS_EXPIRE_BAN_INTERVAL);

			long unbanTime = unbanDate.getTime();

			if (now >= unbanTime) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Auto expiring ban ", ban.getBanId(), " on user ",
							ban.getBanUserId()));
				}

				mbBanPersistence.remove(ban);
			}
		}
	}

	@Override
	public List<MBBan> getBans(long groupId, int start, int end) {
		return mbBanPersistence.findByGroupId(groupId, start, end);
	}

	@Override
	public int getBansCount(long groupId) {
		return mbBanPersistence.countByGroupId(groupId);
	}

	@Override
	public boolean hasBan(long groupId, long banUserId) {
		if (mbBanPersistence.fetchByG_B(groupId, banUserId) == null) {
			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MBBanLocalServiceImpl.class);

	@ServiceReference(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}