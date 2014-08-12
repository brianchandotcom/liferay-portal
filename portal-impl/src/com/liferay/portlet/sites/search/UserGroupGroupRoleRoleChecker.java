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
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.UserGroupGroupRoleLocalServiceUtil;

import javax.portlet.RenderResponse;

/**
 * @author Brett Swaim
 */
public class UserGroupGroupRoleRoleChecker extends RowChecker {

	public UserGroupGroupRoleRoleChecker(
		RenderResponse renderResponse, UserGroup userGroup, Group group) {

		super(renderResponse);

		_userGroup = userGroup;
		_group = group;
	}

	@Override
	public boolean isChecked(Object obj) {
		Role role = (Role)obj;

		try {
			return UserGroupGroupRoleLocalServiceUtil.hasUserGroupGroupRole(
				_userGroup.getUserGroupId(), _group.getGroupId(),
				role.getRoleId());
		}
		catch (Exception e) {
			_log.error(e, e);

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserGroupGroupRoleRoleChecker.class);

	private final Group _group;
	private final UserGroup _userGroup;

}