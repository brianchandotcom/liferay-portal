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

package com.liferay.portlet.blogs.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portlet.blogs.NoSuchStatsUserException;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.model.BlogsStatsUser;
import com.liferay.portlet.blogs.service.base.BlogsStatsUserLocalServiceBaseImpl;
import com.liferay.portlet.blogs.util.comparator.EntryDisplayDateComparator;
import com.liferay.portlet.blogs.util.comparator.StatsUserLastPostDateComparator;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class BlogsStatsUserLocalServiceImpl
	extends BlogsStatsUserLocalServiceBaseImpl {

	public void deleteStatsUser(BlogsStatsUser statsUsers)
		throws SystemException {

		blogsStatsUserPersistence.remove(statsUsers);
	}

	public void deleteStatsUser(long statsUserId)
		throws PortalException, SystemException {

		BlogsStatsUser statsUsers = blogsStatsUserPersistence.findByPrimaryKey(
			statsUserId);

		deleteStatsUser(statsUsers);
	}

	public void deleteStatsUserByGroupId(long groupId)
		throws SystemException {

		List<BlogsStatsUser> statsUsers =
			blogsStatsUserPersistence.findByGroupId(groupId);

		for (BlogsStatsUser statsUser : statsUsers) {
			deleteStatsUser(statsUser);
		}
	}

	public void deleteStatsUserByUserId(long userId) throws SystemException {
		List<BlogsStatsUser> statsUsers =
			blogsStatsUserPersistence.findByUserId(userId);

		for (BlogsStatsUser statsUser : statsUsers) {
			deleteStatsUser(statsUser);
		}
	}

	public List<BlogsStatsUser> getCompanyStatsUsers(
			long companyId, int start, int end)
		throws SystemException {

		return blogsStatsUserPersistence.findByC_NotE(
			companyId, 0, start, end, new StatsUserLastPostDateComparator());
	}

	public List<BlogsStatsUser> getCompanyStatsUsers(
			long companyId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return blogsStatsUserPersistence.findByC_NotE(
			companyId, 0, start, end, obc);
	}

	public int getCompanyStatsUsersCount(long companyId)
		throws SystemException {

		return blogsStatsUserPersistence.countByC_NotE(companyId, 0);
	}

	public List<BlogsStatsUser> getGroupsStatsUsers(
			long companyId, long groupId, int start, int end)
		throws SystemException {

		return blogsStatsUserFinder.findByGroupIds(
			companyId, groupId, start, end);
	}

	public List<BlogsStatsUser> getGroupStatsUsers(
			long groupId, int start, int end)
		throws SystemException {

		return blogsStatsUserPersistence.findByG_NotE(
			groupId, 0, start, end, new StatsUserLastPostDateComparator());
	}

	public List<BlogsStatsUser> getGroupStatsUsers(
			long groupId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return blogsStatsUserPersistence.findByG_NotE(
			groupId, 0, start, end, obc);
	}

	public int getGroupStatsUsersCount(long groupId) throws SystemException {
		return blogsStatsUserPersistence.countByG_NotE(groupId, 0);
	}

	public List<BlogsStatsUser> getOrganizationStatsUsers(
			long organizationId, int start, int end)
		throws SystemException {

		return blogsStatsUserFinder.findByOrganizationId(
			organizationId, start, end, new StatsUserLastPostDateComparator());
	}

	public List<BlogsStatsUser> getOrganizationStatsUsers(
			long organizationId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return blogsStatsUserFinder.findByOrganizationId(
			organizationId, start, end, obc);
	}

	public int getOrganizationStatsUsersCount(long organizationId)
		throws SystemException {

		return blogsStatsUserFinder.countByOrganizationId(organizationId);
	}

	public BlogsStatsUser getStatsUser(long groupId, long userId)
		throws PortalException, SystemException {

		BlogsStatsUser statsUser = blogsStatsUserPersistence.fetchByG_U(
			groupId, userId);

		if (statsUser == null) {
			Group group = groupPersistence.findByPrimaryKey(groupId);

			long statsUserId = counterLocalService.increment();

			statsUser = blogsStatsUserPersistence.create(statsUserId);

			statsUser.setCompanyId(group.getCompanyId());
			statsUser.setGroupId(groupId);
			statsUser.setUserId(userId);

			blogsStatsUserPersistence.update(statsUser, false);
		}

		return statsUser;
	}

	public void updateStatsUser(long groupId, long userId)
		throws PortalException, SystemException {

		updateStatsUser(groupId, userId, null);
	}

	public void updateStatsUser(long groupId, long userId, Date displayDate)
		throws PortalException, SystemException {

		int entryCount = blogsEntryPersistence.countByG_U_S(
			groupId, userId, WorkflowConstants.STATUS_APPROVED);

		if (entryCount == 0) {
			try {
				blogsStatsUserPersistence.removeByG_U(groupId, userId);
			}
			catch (NoSuchStatsUserException nssue) {
			}

			return;
		}

		BlogsStatsUser statsUser = getStatsUser(groupId, userId);

		statsUser.setEntryCount(entryCount);

		BlogsEntry blogsEntry = blogsEntryPersistence.findByG_U_S_First(
			groupId, userId, WorkflowConstants.STATUS_APPROVED,
			new EntryDisplayDateComparator());

		Date lastDisplayDate = blogsEntry.getDisplayDate();

		Date lastPostDate = statsUser.getLastPostDate();

		if (displayDate != null) {
			if (lastPostDate == null) {
				statsUser.setLastPostDate(displayDate);
			}
			else if (displayDate.after(lastPostDate)) {
				statsUser.setLastPostDate(displayDate);
			}
			else if (lastDisplayDate.before(lastPostDate)) {
				statsUser.setLastPostDate(lastDisplayDate);
			}
		}
		else if (lastDisplayDate.before(lastPostDate)) {
			statsUser.setLastPostDate(lastDisplayDate);
		}

		blogsStatsUserPersistence.update(statsUser, false);
	}

}