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

package com.liferay.blogs.service.impl;

import com.liferay.blogs.exception.NoSuchStatsUserException;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.model.BlogsStatsUser;
import com.liferay.blogs.service.base.BlogsStatsUserLocalServiceBaseImpl;
import com.liferay.blogs.service.persistence.BlogsEntryPersistence;
import com.liferay.blogs.util.comparator.EntryDisplayDateComparator;
import com.liferay.blogs.util.comparator.StatsUserLastPostDateComparator;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Máté Thurzó
 */
@Component(
	property = "model.class.name=com.liferay.blogs.model.BlogsStatsUser",
	service = AopService.class
)
public class BlogsStatsUserLocalServiceImpl
	extends BlogsStatsUserLocalServiceBaseImpl {

	@Override
	public void deleteStatsUser(BlogsStatsUser statsUsers) {
		blogsStatsUserPersistence.remove(statsUsers);
	}

	@Override
	public BlogsStatsUser fetchStatsUser(long groupId, long userId) {
		return blogsStatsUserPersistence.fetchByG_U(groupId, userId);
	}

	@Override
	public List<BlogsStatsUser> getGroupsStatsUsers(
		long companyId, long groupId, int start, int end) {

		return blogsStatsUserFinder.findByGroupIds(
			companyId, groupId, start, end);
	}

	@Override
	public List<BlogsStatsUser> getGroupStatsUsers(
		long groupId, int start, int end) {

		return blogsStatsUserPersistence.findByG_NotE(
			groupId, 0, start, end, new StatsUserLastPostDateComparator());
	}

	@Override
	public List<BlogsStatsUser> getOrganizationStatsUsers(
		long organizationId, int start, int end,
		OrderByComparator<BlogsStatsUser> orderByComparator) {

		return blogsStatsUserFinder.findByOrganizationId(
			organizationId, start, end, orderByComparator);
	}

	@Override
	public BlogsStatsUser getStatsUser(long groupId, long userId)
		throws PortalException {

		BlogsStatsUser statsUser = blogsStatsUserPersistence.fetchByG_U(
			groupId, userId);

		if (statsUser == null) {
			Group group = _groupLocalService.getGroup(groupId);

			long statsUserId = counterLocalService.increment();

			statsUser = blogsStatsUserPersistence.create(statsUserId);

			statsUser.setGroupId(groupId);
			statsUser.setCompanyId(group.getCompanyId());
			statsUser.setUserId(userId);

			statsUser = blogsStatsUserPersistence.update(statsUser);
		}

		return statsUser;
	}

	@Override
	public void updateStatsUser(long groupId, long userId)
		throws PortalException {

		updateStatsUser(groupId, userId, null);
	}

	@Override
	public void updateStatsUser(long groupId, long userId, Date displayDate)
		throws PortalException {

		Date date = new Date();

		int entryCount = _blogsEntryPersistence.countByG_U_LtD_S(
			groupId, userId, date, WorkflowConstants.STATUS_APPROVED);

		if (entryCount == 0) {
			try {
				blogsStatsUserPersistence.removeByG_U(groupId, userId);
			}
			catch (NoSuchStatsUserException noSuchStatsUserException) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						noSuchStatsUserException, noSuchStatsUserException);
				}
			}

			return;
		}

		BlogsStatsUser statsUser = getStatsUser(groupId, userId);

		statsUser.setEntryCount(entryCount);

		BlogsEntry blogsEntry = _blogsEntryPersistence.findByG_U_LtD_S_First(
			groupId, userId, date, WorkflowConstants.STATUS_APPROVED,
			new EntryDisplayDateComparator());

		Date lastDisplayDate = blogsEntry.getDisplayDate();

		Date lastPostDate = statsUser.getLastPostDate();

		if ((displayDate != null) && displayDate.before(date)) {
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
		else if ((lastPostDate == null) ||
				 lastPostDate.before(lastDisplayDate)) {

			statsUser.setLastPostDate(lastDisplayDate);
		}

		blogsStatsUserPersistence.update(statsUser);
	}

	@Override
	public BlogsStatsUser updateStatsUser(
			long groupId, long userId, int ratingsTotalEntries,
			double ratingsTotalScore, double ratingsAverageScore)
		throws PortalException {

		BlogsStatsUser blogsStatsUser = getStatsUser(groupId, userId);

		blogsStatsUser.setRatingsTotalEntries(ratingsTotalEntries);
		blogsStatsUser.setRatingsTotalScore(ratingsTotalScore);
		blogsStatsUser.setRatingsAverageScore(ratingsAverageScore);

		return blogsStatsUserPersistence.update(blogsStatsUser);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BlogsStatsUserLocalServiceImpl.class);

	@Reference
	private BlogsEntryPersistence _blogsEntryPersistence;

	@Reference
	private GroupLocalService _groupLocalService;

}