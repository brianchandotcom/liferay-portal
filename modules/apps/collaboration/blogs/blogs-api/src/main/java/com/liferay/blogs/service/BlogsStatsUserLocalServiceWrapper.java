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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link BlogsStatsUserLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see BlogsStatsUserLocalService
 * @generated
 */
@ProviderType
public class BlogsStatsUserLocalServiceWrapper
	implements BlogsStatsUserLocalService,
		ServiceWrapper<BlogsStatsUserLocalService> {
	public BlogsStatsUserLocalServiceWrapper(
		BlogsStatsUserLocalService blogsStatsUserLocalService) {
		_blogsStatsUserLocalService = blogsStatsUserLocalService;
	}

	@Override
	public com.liferay.blogs.kernel.model.BlogsStatsUser getStatsUser(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsStatsUserLocalService.getStatsUser(groupId, userId);
	}

	@Override
	public com.liferay.blogs.kernel.model.BlogsStatsUser updateStatsUser(
		long groupId, long userId, int ratingsTotalEntries,
		double ratingsTotalScore, double ratingsAverageScore)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsStatsUserLocalService.updateStatsUser(groupId, userId,
			ratingsTotalEntries, ratingsTotalScore, ratingsAverageScore);
	}

	@Override
	public int getCompanyStatsUsersCount(long companyId) {
		return _blogsStatsUserLocalService.getCompanyStatsUsersCount(companyId);
	}

	@Override
	public int getGroupStatsUsersCount(long groupId) {
		return _blogsStatsUserLocalService.getGroupStatsUsersCount(groupId);
	}

	@Override
	public int getOrganizationStatsUsersCount(long organizationId) {
		return _blogsStatsUserLocalService.getOrganizationStatsUsersCount(organizationId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _blogsStatsUserLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.blogs.kernel.model.BlogsStatsUser> getCompanyStatsUsers(
		long companyId, int start, int end) {
		return _blogsStatsUserLocalService.getCompanyStatsUsers(companyId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.blogs.kernel.model.BlogsStatsUser> getCompanyStatsUsers(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.blogs.kernel.model.BlogsStatsUser> obc) {
		return _blogsStatsUserLocalService.getCompanyStatsUsers(companyId,
			start, end, obc);
	}

	@Override
	public java.util.List<com.liferay.blogs.kernel.model.BlogsStatsUser> getGroupStatsUsers(
		long groupId, int start, int end) {
		return _blogsStatsUserLocalService.getGroupStatsUsers(groupId, start,
			end);
	}

	@Override
	public java.util.List<com.liferay.blogs.kernel.model.BlogsStatsUser> getGroupStatsUsers(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.blogs.kernel.model.BlogsStatsUser> obc) {
		return _blogsStatsUserLocalService.getGroupStatsUsers(groupId, start,
			end, obc);
	}

	@Override
	public java.util.List<com.liferay.blogs.kernel.model.BlogsStatsUser> getGroupsStatsUsers(
		long companyId, long groupId, int start, int end) {
		return _blogsStatsUserLocalService.getGroupsStatsUsers(companyId,
			groupId, start, end);
	}

	@Override
	public java.util.List<com.liferay.blogs.kernel.model.BlogsStatsUser> getOrganizationStatsUsers(
		long organizationId, int start, int end) {
		return _blogsStatsUserLocalService.getOrganizationStatsUsers(organizationId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.blogs.kernel.model.BlogsStatsUser> getOrganizationStatsUsers(
		long organizationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.blogs.kernel.model.BlogsStatsUser> obc) {
		return _blogsStatsUserLocalService.getOrganizationStatsUsers(organizationId,
			start, end, obc);
	}

	@Override
	public void deleteStatsUser(
		com.liferay.blogs.kernel.model.BlogsStatsUser statsUsers) {
		_blogsStatsUserLocalService.deleteStatsUser(statsUsers);
	}

	@Override
	public void deleteStatsUser(long statsUserId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_blogsStatsUserLocalService.deleteStatsUser(statsUserId);
	}

	@Override
	public void deleteStatsUserByGroupId(long groupId) {
		_blogsStatsUserLocalService.deleteStatsUserByGroupId(groupId);
	}

	@Override
	public void deleteStatsUserByUserId(long userId) {
		_blogsStatsUserLocalService.deleteStatsUserByUserId(userId);
	}

	@Override
	public void updateStatsUser(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_blogsStatsUserLocalService.updateStatsUser(groupId, userId);
	}

	@Override
	public void updateStatsUser(long groupId, long userId,
		java.util.Date displayDate)
		throws com.liferay.portal.kernel.exception.PortalException {
		_blogsStatsUserLocalService.updateStatsUser(groupId, userId, displayDate);
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