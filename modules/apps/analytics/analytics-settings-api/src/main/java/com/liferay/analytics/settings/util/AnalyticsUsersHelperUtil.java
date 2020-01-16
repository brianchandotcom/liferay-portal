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

package com.liferay.analytics.settings.util;

import com.liferay.portal.kernel.model.User;

import java.util.List;

/**
 * @author Rachael Koestartyo
 */
public class AnalyticsUsersHelperUtil {

	public static List<User> getCompanyUsers(
		long companyId, int start, int end) {

		return _analyticsUsersHelper.getCompanyUsers(companyId, start, end);
	}

	public static long getCompanyUsersCount(long companyId) {
		return _analyticsUsersHelper.getCompanyUsersCount(companyId);
	}

	public static long getOrganizationsAndUserGroupsUsersCount(
		long[] organizationIds, long[] userGroupIds) {

		return _analyticsUsersHelper.getOrganizationsAndUserGroupsUsersCount(
			organizationIds, userGroupIds);
	}

	public static List<User> getOrganizationUsers(
		long organizationId, int start, int end) {

		return _analyticsUsersHelper.getOrganizationUsers(
			organizationId, start, end);
	}

	public static long getOrganizationUsersCount(long organizationId) {
		return _analyticsUsersHelper.getOrganizationUsersCount(organizationId);
	}

	public static List<User> getUserGroupUsers(
		long userGroupId, int start, int end) {

		return _analyticsUsersHelper.getUserGroupUsers(userGroupId, start, end);
	}

	public static long getUserGroupUsersCount(long userGroupId) {
		return _analyticsUsersHelper.getUserGroupUsersCount(userGroupId);
	}

	public static void setAnalyticsUsersHelper(
		AnalyticsUsersHelper analyticsUsersHelper) {

		if (_analyticsUsersHelper != null) {
			return;
		}

		_analyticsUsersHelper = analyticsUsersHelper;
	}

	private static AnalyticsUsersHelper _analyticsUsersHelper;

}