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

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class UserGroupGroupRoleFinderUtil {

	public static java.util.List
		<com.liferay.portal.kernel.model.UserGroupGroupRole>
			findByGroupRoleType(long groupId, int roleType) {

		return getFinder().findByGroupRoleType(groupId, roleType);
	}

	public static java.util.List
		<com.liferay.portal.kernel.model.UserGroupGroupRole>
			findByUserGroupsUsers(long userId) {

		return getFinder().findByUserGroupsUsers(userId);
	}

	public static java.util.List
		<com.liferay.portal.kernel.model.UserGroupGroupRole>
			findByUserGroupsUsers(long userId, long groupId) {

		return getFinder().findByUserGroupsUsers(userId, groupId);
	}

	public static UserGroupGroupRoleFinder getFinder() {
		if (_finder == null) {
			_finder = (UserGroupGroupRoleFinder)PortalBeanLocatorUtil.locate(
				UserGroupGroupRoleFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(UserGroupGroupRoleFinder finder) {
		_finder = finder;
	}

	private static UserGroupGroupRoleFinder _finder;

}