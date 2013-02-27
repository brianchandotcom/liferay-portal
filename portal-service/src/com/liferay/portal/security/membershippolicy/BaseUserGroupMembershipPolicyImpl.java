/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.membershippolicy;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.UserGroupLocalServiceUtil;

import java.util.List;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
public abstract class BaseUserGroupMembershipPolicyImpl
	implements UserGroupMembershipPolicy {

	public boolean isMembershipAllowed(long userId, long userGroupId)
		throws PortalException, SystemException {

		try {
			checkMembership(
				new long[] {userId}, new long[] {userGroupId}, null);
		}
		catch (Exception e) {
			return false;
		}

		return true;
	}

	public boolean isMembershipRequired(long userId, long userGroupId)
		throws PortalException, SystemException {

		try {
			checkMembership(
				new long[] {userId}, null, new long[] {userGroupId});
		}
		catch (Exception e) {
			return true;
		}

		return false;
	}

	public void verifyPolicy() throws PortalException, SystemException {
		int start = 0;
		int end = SEARCH_INTERVAL;

		int total = UserGroupLocalServiceUtil.getUserGroupsCount();

		while (start <= total) {
			List<UserGroup> userGroups =
				UserGroupLocalServiceUtil.getUserGroups(start, end);

			for (UserGroup userGroup : userGroups) {
				verifyPolicy(userGroup);
			}

			start = end;
			end += end;
		}
	}

	public void verifyPolicy(UserGroup userGroup)
		throws PortalException, SystemException {

		verifyPolicy(userGroup, null, null);
	}

}