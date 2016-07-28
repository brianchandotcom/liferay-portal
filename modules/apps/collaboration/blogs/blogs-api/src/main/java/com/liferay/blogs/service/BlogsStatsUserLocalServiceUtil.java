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

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for BlogsStatsUser. This utility wraps
 * {@link com.liferay.blogs.service.impl.BlogsStatsUserLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see BlogsStatsUserLocalService
 * @see com.liferay.blogs.service.base.BlogsStatsUserLocalServiceBaseImpl
 * @see com.liferay.blogs.service.impl.BlogsStatsUserLocalServiceImpl
 * @generated
 */
@ProviderType
public class BlogsStatsUserLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.blogs.service.impl.BlogsStatsUserLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.blogs.kernel.model.BlogsStatsUser getStatsUser(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getStatsUser(groupId, userId);
	}

	public static com.liferay.blogs.kernel.model.BlogsStatsUser updateStatsUser(
		long groupId, long userId, int ratingsTotalEntries,
		double ratingsTotalScore, double ratingsAverageScore)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateStatsUser(groupId, userId, ratingsTotalEntries,
			ratingsTotalScore, ratingsAverageScore);
	}

	public static int getCompanyStatsUsersCount(long companyId) {
		return getService().getCompanyStatsUsersCount(companyId);
	}

	public static int getGroupStatsUsersCount(long groupId) {
		return getService().getGroupStatsUsersCount(groupId);
	}

	public static int getOrganizationStatsUsersCount(long organizationId) {
		return getService().getOrganizationStatsUsersCount(organizationId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.blogs.kernel.model.BlogsStatsUser> getCompanyStatsUsers(
		long companyId, int start, int end) {
		return getService().getCompanyStatsUsers(companyId, start, end);
	}

	public static java.util.List<com.liferay.blogs.kernel.model.BlogsStatsUser> getCompanyStatsUsers(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.blogs.kernel.model.BlogsStatsUser> obc) {
		return getService().getCompanyStatsUsers(companyId, start, end, obc);
	}

	public static java.util.List<com.liferay.blogs.kernel.model.BlogsStatsUser> getGroupStatsUsers(
		long groupId, int start, int end) {
		return getService().getGroupStatsUsers(groupId, start, end);
	}

	public static java.util.List<com.liferay.blogs.kernel.model.BlogsStatsUser> getGroupStatsUsers(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.blogs.kernel.model.BlogsStatsUser> obc) {
		return getService().getGroupStatsUsers(groupId, start, end, obc);
	}

	public static java.util.List<com.liferay.blogs.kernel.model.BlogsStatsUser> getGroupsStatsUsers(
		long companyId, long groupId, int start, int end) {
		return getService().getGroupsStatsUsers(companyId, groupId, start, end);
	}

	public static java.util.List<com.liferay.blogs.kernel.model.BlogsStatsUser> getOrganizationStatsUsers(
		long organizationId, int start, int end) {
		return getService().getOrganizationStatsUsers(organizationId, start, end);
	}

	public static java.util.List<com.liferay.blogs.kernel.model.BlogsStatsUser> getOrganizationStatsUsers(
		long organizationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.blogs.kernel.model.BlogsStatsUser> obc) {
		return getService()
				   .getOrganizationStatsUsers(organizationId, start, end, obc);
	}

	public static void deleteStatsUser(
		com.liferay.blogs.kernel.model.BlogsStatsUser statsUsers) {
		getService().deleteStatsUser(statsUsers);
	}

	public static void deleteStatsUser(long statsUserId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteStatsUser(statsUserId);
	}

	public static void deleteStatsUserByGroupId(long groupId) {
		getService().deleteStatsUserByGroupId(groupId);
	}

	public static void deleteStatsUserByUserId(long userId) {
		getService().deleteStatsUserByUserId(userId);
	}

	public static void updateStatsUser(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().updateStatsUser(groupId, userId);
	}

	public static void updateStatsUser(long groupId, long userId,
		java.util.Date displayDate)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().updateStatsUser(groupId, userId, displayDate);
	}

	public static BlogsStatsUserLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<BlogsStatsUserLocalService, BlogsStatsUserLocalService> _serviceTracker =
		ServiceTrackerFactory.open(BlogsStatsUserLocalService.class);
}