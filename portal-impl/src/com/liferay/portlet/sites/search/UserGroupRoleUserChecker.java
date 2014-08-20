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

package com.liferay.portlet.sites.search;

import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.security.membershippolicy.SiteMembershipPolicyUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;

import javax.portlet.RenderResponse;

/**
 * @author Jorge Ferrer
 */
public class UserGroupRoleUserChecker extends RowChecker {

	public UserGroupRoleUserChecker(
		RenderResponse renderResponse, Group group, Role role) {

		super(renderResponse);

		_group = group;
		_role = role;
	}

	@Override
	public boolean isChecked(Object obj) {
		User user = (User)obj;

		try {
			return UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				user.getUserId(), _group.getGroupId(), _role.getRoleId());
		}
		catch (Exception e) {
			_log.error(e, e);

			return false;
		}
	}

	@Override
	public boolean isDisabled(Object obj) {
		User user = (User)obj;

		try {
			if (isChecked(user)) {
				if (SiteMembershipPolicyUtil.isRoleRequired(
						user.getUserId(), _group.getGroupId(),
						_role.getRoleId())) {

					return true;
				}
			}
			else {
				if (!SiteMembershipPolicyUtil.isRoleAllowed(
						user.getUserId(), _group.getGroupId(),
						_role.getRoleId())) {

					return true;
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return super.isDisabled(obj);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserGroupRoleUserChecker.class);

	private final Group _group;
	private final Role _role;

}