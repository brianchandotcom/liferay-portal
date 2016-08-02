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

package com.liferay.portlet.blogs.service.impl;

import com.liferay.blogs.kernel.model.BlogsStatsUser;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.blogs.service.base.BlogsStatsUserLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Mate Thurzo
 * @deprecated As of 7.0.0, replaced by {@link
 *             com.liferay.blogs.service.impl.BlogsStatsUserLocalServiceImpl}
 */
@Deprecated
public class BlogsStatsUserLocalServiceImpl
	extends BlogsStatsUserLocalServiceBaseImpl {

	@Override
	public void deleteStatsUser(BlogsStatsUser statsUsers) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl." +
					"BlogsStatsUserLocalServiceImpl");
	}

	@Override
	public void deleteStatsUser(long statsUserId) throws PortalException {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl." +
					"BlogsStatsUserLocalServiceImpl");
	}

	@Override
	public void deleteStatsUserByGroupId(long groupId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl." +
					"BlogsStatsUserLocalServiceImpl");
	}

	@Override
	public void deleteStatsUserByUserId(long userId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl." +
					"BlogsStatsUserLocalServiceImpl");
	}

	@Override
	public List<BlogsStatsUser> getCompanyStatsUsers(
		long companyId, int start, int end) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl." +
					"BlogsStatsUserLocalServiceImpl");
	}

	@Override
	public List<BlogsStatsUser> getCompanyStatsUsers(
		long companyId, int start, int end,
		OrderByComparator<BlogsStatsUser> obc) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl." +
					"BlogsStatsUserLocalServiceImpl");
	}

	@Override
	public int getCompanyStatsUsersCount(long companyId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl." +
					"BlogsStatsUserLocalServiceImpl");
	}

	@Override
	public List<BlogsStatsUser> getGroupsStatsUsers(
		long companyId, long groupId, int start, int end) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl." +
					"BlogsStatsUserLocalServiceImpl");
	}

	@Override
	public List<BlogsStatsUser> getGroupStatsUsers(
		long groupId, int start, int end) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl." +
					"BlogsStatsUserLocalServiceImpl");
	}

	@Override
	public List<BlogsStatsUser> getGroupStatsUsers(
		long groupId, int start, int end,
		OrderByComparator<BlogsStatsUser> obc) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl." +
					"BlogsStatsUserLocalServiceImpl");
	}

	@Override
	public int getGroupStatsUsersCount(long groupId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl." +
					"BlogsStatsUserLocalServiceImpl");
	}

	@Override
	public List<BlogsStatsUser> getOrganizationStatsUsers(
		long organizationId, int start, int end) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl." +
					"BlogsStatsUserLocalServiceImpl");
	}

	@Override
	public List<BlogsStatsUser> getOrganizationStatsUsers(
		long organizationId, int start, int end,
		OrderByComparator<BlogsStatsUser> obc) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl." +
					"BlogsStatsUserLocalServiceImpl");
	}

	@Override
	public int getOrganizationStatsUsersCount(long organizationId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl." +
					"BlogsStatsUserLocalServiceImpl");
	}

	@Override
	public BlogsStatsUser getStatsUser(long groupId, long userId)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl." +
					"BlogsStatsUserLocalServiceImpl");
	}

	@Override
	public void updateStatsUser(long groupId, long userId)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl." +
					"BlogsStatsUserLocalServiceImpl");
	}

	@Override
	public void updateStatsUser(long groupId, long userId, Date displayDate)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl." +
					"BlogsStatsUserLocalServiceImpl");
	}

}