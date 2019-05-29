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

package com.liferay.portal.kernel.security.membershippolicy;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
public abstract class BaseUserGroupMembershipPolicy
	implements UserGroupMembershipPolicy {

	@Override
	public boolean isMembershipAllowed(long userId, long userGroupId)
		throws PortalException {

		try {
			checkMembership(
				new long[] {userId}, new long[] {userGroupId}, null);
		}
		catch (Exception e) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isMembershipRequired(long userId, long userGroupId)
		throws PortalException {

		try {
			checkMembership(
				new long[] {userId}, null, new long[] {userGroupId});
		}
		catch (Exception e) {
			return true;
		}

		return false;
	}

	@Override
	public void verifyPolicy() throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			UserGroupLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			(UserGroup userGroup) -> verifyPolicy(userGroup));

		actionableDynamicQuery.performActions();
	}

	@Override
	public void verifyPolicy(UserGroup userGroup) throws PortalException {
		verifyPolicy(userGroup, null, null);
	}

}