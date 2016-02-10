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

package com.liferay.portlet.rolesadmin.search;

import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;

import javax.portlet.RenderResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class OrganizationRoleChecker extends EmptyOnClickRowChecker {

	public OrganizationRoleChecker(RenderResponse renderResponse, Role role) {
		super(renderResponse);

		_role = role;
	}

	@Override
	public boolean isChecked(Object obj) {
		Organization organization = (Organization)obj;

		try {
			Group group = organization.getGroup();

			return GroupLocalServiceUtil.hasRoleGroup(
				_role.getRoleId(), group.getGroupId());
		}
		catch (Exception e) {
			_log.error(e, e);

			return false;
		}
	}

	@Override
	public boolean isDisabled(Object obj) {
		Organization organization = (Organization)obj;

		return isChecked(organization);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OrganizationRoleChecker.class);

	private final Role _role;

}