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

import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portlet.messageboards.model.MBStatsUser;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.impl.MBStatsUserImpl;
import com.liferay.portlet.messageboards.service.base.MBStatsUserLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class MBStatsUserLocalServiceImpl
	extends MBStatsUserLocalServiceBaseImpl {

	@Override
	public MBStatsUser addStatsUser(long groupId, long userId) {
		long statsUserId = counterLocalService.increment();

		MBStatsUser statsUser = mbStatsUserPersistence.create(statsUserId);

		statsUser.setGroupId(groupId);
		statsUser.setUserId(userId);

		try {
			mbStatsUserPersistence.update(statsUser);
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

	@Override
	public void deleteStatsUser(long statsUserId) throws PortalException {
		MBStatsUser statsUser = mbStatsUserPersistence.findByPrimaryKey(
			statsUserId);

		deleteStatsUser(statsUser);
	}

	@Override
	public void deleteStatsUser(MBStatsUser statsUser) {
		mbStatsUserPersistence.remove(statsUser);
	}

	@Override
	public void deleteStatsUsersByGroupId(long groupId) {
		List<MBStatsUser> statsUsers = mbStatsUserPersistence.findByGroupId(
			groupId);

		for (MBStatsUser statsUser : statsUsers) {
			deleteStatsUser(statsUser);
		}
	}

	@Override
	public void deleteStatsUsersByUserId(long userId) {
		List<MBStatsUser> statsUsers = mbStatsUserPersistence.findByUserId(
			userId);

		for (MBStatsUser statsUser : statsUsers) {
			deleteStatsUser(statsUser);
		}
	}

	@Override
	public Date getLastPostDateByUserId(long groupId, long userId) {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			MBThread.class, MBStatsUserImpl.TABLE_NAME,
			ClassLoaderUtil.getPortalClassLoader());

		Projection projection = ProjectionFactoryUtil.max("lastPostDate");

		dynamicQuery.setProjection(projection);

		Property property = PropertyFactoryUtil.forName("threadId");

		Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

		QueryDefinition<MBThread> queryDefinition =
			new QueryDefinition<MBThread>(WorkflowConstants.STATUS_IN_TRASH);

		List<MBThread> threads = mbThreadLocalService.getGroupThreads(
			groupId, queryDefinition);

		for (MBThread thread : threads) {
			disjunction.add(property.ne(thread.getThreadId()));
		}

		dynamicQuery.add(disjunction);

		List<Date> results = mbStatsUserLocalService.dynamicQuery(dynamicQuery);

		return results.get(0);
	}

	@Override
	public long getMessageCountByGroupId(long groupId) {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			MBStatsUser.class, MBStatsUserImpl.TABLE_NAME,
			ClassLoaderUtil.getPortalClassLoader());

		Projection projection = ProjectionFactoryUtil.sum("messageCount");

		dynamicQuery.setProjection(projection);

		Property property = PropertyFactoryUtil.forName("groupId");

		dynamicQuery.add(property.eq(groupId));

		List<Long> results = mbStatsUserLocalService.dynamicQuery(dynamicQuery);

		if (results.get(0) == null) {
			return 0;
		}

		return results.get(0);
	}

	@Override
	public long getMessageCountByUserId(long userId) {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			MBStatsUser.class, MBStatsUserImpl.TABLE_NAME,
			ClassLoaderUtil.getPortalClassLoader());

		Projection projection = ProjectionFactoryUtil.sum("messageCount");

		dynamicQuery.setProjection(projection);

		Property property = PropertyFactoryUtil.forName("userId");

		dynamicQuery.add(property.eq(userId));

		List<Long> results = mbStatsUserLocalService.dynamicQuery(dynamicQuery);

		if (results.get(0) == null) {
			return 0;
		}

		return results.get(0);
	}

	@Override
	public MBStatsUser getStatsUser(long groupId, long userId) {
		MBStatsUser statsUser = mbStatsUserPersistence.fetchByG_U(
			groupId, userId);

		if (statsUser == null) {
			statsUser = mbStatsUserLocalService.addStatsUser(groupId, userId);
		}

		return statsUser;
	}

	@Override
	public List<MBStatsUser> getStatsUsersByGroupId(
			long groupId, int start, int end)
		throws PortalException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		long defaultUserId = userLocalService.getDefaultUserId(
			group.getCompanyId());

		return mbStatsUserPersistence.findByG_NotU_NotM(
			groupId, defaultUserId, 0, start, end);
	}

	@Override
	public int getStatsUsersByGroupIdCount(long groupId)
		throws PortalException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		long defaultUserId = userLocalService.getDefaultUserId(
			group.getCompanyId());

		return mbStatsUserPersistence.countByG_NotU_NotM(
			groupId, defaultUserId, 0);
	}

	@Override
	public List<MBStatsUser> getStatsUsersByUserId(long userId) {
		return mbStatsUserPersistence.findByUserId(userId);
	}

	@Override
	public MBStatsUser updateStatsUser(long groupId, long userId) {
		return updateStatsUser(
			groupId, userId, getLastPostDateByUserId(groupId, userId));
	}

	@Override
	public MBStatsUser updateStatsUser(
		long groupId, long userId, Date lastPostDate) {

		int messageCount = mbMessagePersistence.countByG_U_S(
			groupId, userId, WorkflowConstants.STATUS_APPROVED);

		return updateStatsUser(groupId, userId, messageCount, lastPostDate);
	}

	@Override
	public MBStatsUser updateStatsUser(
		long groupId, long userId, int messageCount, Date lastPostDate) {

		MBStatsUser statsUser = getStatsUser(groupId, userId);

		statsUser.setMessageCount(messageCount);

		if (lastPostDate != null) {
			statsUser.setLastPostDate(lastPostDate);
		}

		mbStatsUserPersistence.update(statsUser);

		return statsUser;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MBStatsUserLocalServiceImpl.class);

}