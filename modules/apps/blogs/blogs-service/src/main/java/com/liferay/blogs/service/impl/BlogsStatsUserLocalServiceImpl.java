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

import com.liferay.blogs.model.BlogsStatsUser;
import com.liferay.blogs.service.base.BlogsStatsUserLocalServiceBaseImpl;
import com.liferay.blogs.util.comparator.StatsUserLastPostDateComparator;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.OrderByComparator;

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

	@Reference
	private GroupLocalService _groupLocalService;

}