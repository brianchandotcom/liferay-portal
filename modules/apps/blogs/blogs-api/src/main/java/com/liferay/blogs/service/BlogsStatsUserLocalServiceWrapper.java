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

package com.liferay.blogs.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link BlogsStatsUserLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see BlogsStatsUserLocalService
 * @generated
 */
public class BlogsStatsUserLocalServiceWrapper
	implements BlogsStatsUserLocalService,
			   ServiceWrapper<BlogsStatsUserLocalService> {

	public BlogsStatsUserLocalServiceWrapper() {
		this(null);
	}

	public BlogsStatsUserLocalServiceWrapper(
		BlogsStatsUserLocalService blogsStatsUserLocalService) {

		_blogsStatsUserLocalService = blogsStatsUserLocalService;
	}

	@Override
	public java.util.List<com.liferay.blogs.model.BlogsStatsUser>
		getGroupsStatsUsers(long companyId, long groupId, int start, int end) {

		return _blogsStatsUserLocalService.getGroupsStatsUsers(
			companyId, groupId, start, end);
	}

	@Override
	public java.util.List<com.liferay.blogs.model.BlogsStatsUser>
		getGroupStatsUsers(long groupId, int start, int end) {

		return _blogsStatsUserLocalService.getGroupStatsUsers(
			groupId, start, end);
	}

	@Override
	public java.util.List<com.liferay.blogs.model.BlogsStatsUser>
		getOrganizationStatsUsers(long organizationId, int start, int end) {

		return _blogsStatsUserLocalService.getOrganizationStatsUsers(
			organizationId, start, end);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _blogsStatsUserLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.blogs.model.BlogsStatsUser getStatsUser(
			long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _blogsStatsUserLocalService.getStatsUser(groupId, userId);
	}

	@Override
	public BlogsStatsUserLocalService getWrappedService() {
		return _blogsStatsUserLocalService;
	}

	@Override
	public void setWrappedService(
		BlogsStatsUserLocalService blogsStatsUserLocalService) {

		_blogsStatsUserLocalService = blogsStatsUserLocalService;
	}

	private BlogsStatsUserLocalService _blogsStatsUserLocalService;

}