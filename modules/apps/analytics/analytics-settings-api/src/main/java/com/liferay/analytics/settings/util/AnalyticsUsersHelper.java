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
public interface AnalyticsUsersHelper {

	public List<User> getCompanyUsers(long companyId, int start, int end);

	public int getCompanyUsersCount(long companyId);

	public int getOrganizationsAndUserGroupsUsersCount(
		long[] organizationIds, long[] userGroupIds);

	public List<User> getOrganizationUsers(
		long organizationId, int start, int end);

	public int getOrganizationUsersCount(long organizationId);

	public List<User> getUserGroupUsers(long userGroupId, int start, int end);

	public int getUserGroupUsersCount(long userGroupId);

}