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

package com.liferay.roles.admin.web.internal.search;

import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.permission.RolePermissionUtil;
import com.liferay.roles.admin.role.type.contributor.RoleTypeContributor;
import com.liferay.roles.admin.web.internal.role.type.contributor.util.RoleTypeContributorRetrieverUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Drew Brokke
 */
public class RoleChecker extends EmptyOnClickRowChecker {

	public RoleChecker(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		super(portletResponse);

		_portletRequest = portletRequest;
	}

	@Override
	public boolean isDisabled(Object obj) {
		Role role = (Role)obj;

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			if (role.isSystem() ||
				!RolePermissionUtil.contains(
					permissionChecker, role.getRoleId(), ActionKeys.DELETE)) {

				return true;
			}

			RoleTypeContributor roleTypeContributor =
				RoleTypeContributorRetrieverUtil.getCurrentRoleTypeContributor(
					_portletRequest);

			if ((roleTypeContributor != null) &&
				(!roleTypeContributor.isAllowDelete(role) ||
				 roleTypeContributor.isAutomaticallyAssigned(role))) {

				return true;
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return super.isDisabled(obj);
	}

	private static final Log _log = LogFactoryUtil.getLog(RoleChecker.class);

	private final PortletRequest _portletRequest;

}