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

package com.liferay.portlet.usersadmin.search;

import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;

import javax.portlet.RenderResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class GroupPermissionChecker extends RowChecker {

	public GroupPermissionChecker(
		RenderResponse renderResponse, Role role, String resourceName,
		String actionId) {

		super(renderResponse);

		_role = role;
		_resourceName = resourceName;
		_actionId = actionId;
	}

	@Override
	public boolean isChecked(Object obj) {
		Group group = (Group)obj;

		try {
			return PermissionLocalServiceUtil.hasRolePermission(
				_role.getRoleId(), group.getCompanyId(), _resourceName,
				ResourceConstants.SCOPE_GROUP,
				String.valueOf(group.getGroupId()), _actionId);
		}
		catch (Exception e) {
			_log.error(e, e);

			return false;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		GroupPermissionChecker.class);

	private String _actionId;
	private String _resourceName;
	private Role _role;

}