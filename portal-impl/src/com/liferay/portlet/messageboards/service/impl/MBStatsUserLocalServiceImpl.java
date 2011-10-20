/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portlet.messageboards.model.MBStatsUser;
import com.liferay.portlet.messageboards.model.impl.MBStatsUserImpl;
import com.liferay.portlet.messageboards.service.base.MBStatsUserLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class MBStatsUserLocalServiceImpl
	extends MBStatsUserLocalServiceBaseImpl {

	public MBStatsUser addStatsUser(long groupId, long userId)
		throws SystemException {

		long statsUserId = counterLocalService.increment();

		MBStatsUser statsUser = mbStatsUserPersistence.create(statsUserId);

		statsUser.setGroupId(groupId);
		statsUser.setUserId(userId);

		try {
			mbStatsUserPersistence.update(statsUser, false);
		}
		catch (SystemException se) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Add failed, fetch {groupId=" + groupId + ", userId=" +
						userId + "}");
			}

			statsUser = mbStatsUserPersistence.fetchByG_U(
				groupId, userId, false);

			if (statsUser == null) {
				throw se;
			}
		}

		return statsUser;
	}

	public void deleteStatsUser(long statsUserId)
		throws PortalException, SystemException {

		MBStatsUser statsUser = mbStatsUserPersistence.findByPrimaryKey(
			statsUserId);

		deleteStatsUser(statsUser);
	}

	public void deleteStatsUser(MBStatsUser statsUser) throws SystemException {
		mbStatsUserPersistence.remove(statsUser);
	}

	public void deleteStatsUsersByGroupId(long groupId) throws SystemException {
		List<MBStatsUser> statsUsers = mbStatsUserPersistence.findByGroupId(
			groupId);

		for (MBStatsUser statsUser : statsUsers) {
			deleteStatsUser(statsUser);
		}
	}

	public void deleteStatsUsersByUserId(long userId) throws SystemException {
		List<MBStatsUser> statsUsers = mbStatsUserPersistence.findByUserId(
			userId);

		for (MBStatsUser statsUser : statsUsers) {
			deleteStatsUser(statsUser);
		}
	}

	public long getMessageCountByUserId(long userId) throws SystemException {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			MBStatsUser.class, MBStatsUserImpl.TABLE_NAME,
			PortalClassLoaderUtil.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.sum("messageCount"));

		dynamicQuery.add(PropertyFactoryUtil.forName("userId").eq(userId));

		return dynamicQueryCount(dynamicQuery);
	}

	public MBStatsUser getStatsUser(long groupId, long userId)
		throws SystemException {

		MBStatsUser statsUser = mbStatsUserPersistence.fetchByG_U(
			groupId, userId);

		if (statsUser == null) {
			statsUser = mbStatsUserLocalService.addStatsUser(groupId, userId);
		}

		return statsUser;
	}

	public List<MBStatsUser> getStatsUsersByGroupId(
			long groupId, int start, int end)
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		long defaultUserId = userLocalService.getDefaultUserId(
			group.getCompanyId());

		return mbStatsUserPersistence.findByG_NotU_NotM(
			groupId, defaultUserId, 0, start, end);
	}

	public int getStatsUsersByGroupIdCount(long groupId)
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		long defaultUserId = userLocalService.getDefaultUserId(
			group.getCompanyId());

		return mbStatsUserPersistence.countByG_NotU_NotM(
			groupId, defaultUserId, 0);
	}

	public List<MBStatsUser> getStatsUsersByUserId(long userId)
		throws SystemException {

		return mbStatsUserPersistence.findByUserId(userId);
	}

	public MBStatsUser updateStatsUser(long groupId, long userId)
		throws SystemException {

		return updateStatsUser(groupId, userId, null);
	}

	public MBStatsUser updateStatsUser(
			long groupId, long userId, Date lastPostDate)
		throws SystemException {

		int messageCount = mbMessagePersistence.countByG_U(groupId, userId);

		MBStatsUser statsUser = getStatsUser(groupId, userId);

		statsUser.setMessageCount(messageCount);

		if (lastPostDate != null) {
			statsUser.setLastPostDate(lastPostDate);
		}

		mbStatsUserPersistence.update(statsUser, false);

		return statsUser;
	}

	private static Log _log = LogFactoryUtil.getLog(
		MBStatsUserLocalServiceImpl.class);

}