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

package com.liferay.redirect.web.internal.instance.lifecycle;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.redirect.model.RedirectEntry;
import com.liferay.redirect.service.RedirectEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author tototrinh
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class RedirectEntryPermissionPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		_initUserPersonalSitePermissions(company.getCompanyId());
	}

	private void _addResourcePermission(
			long companyId, String name, int scope, String primKey, long roleId,
			String actionId)
		throws Exception {

		ResourceAction resourceAction =
			_resourceActionLocalService.fetchResourceAction(name, actionId);

		if (resourceAction == null) {
			return;
		}

		ResourcePermission resourcePermission =
			_resourcePermissionLocalService.fetchResourcePermission(
				companyId, name, scope, primKey, roleId);

		if (resourcePermission != null) {
			return;
		}

		_resourcePermissionLocalService.setResourcePermissions(
			companyId, name, scope, primKey, roleId,
			new String[] {resourceAction.getActionId()});
	}

	private void _initUserPersonalSitePermissions(long companyId)
		throws Exception {

		Role powerUserRole = _roleLocalService.fetchRole(
			companyId, RoleConstants.POWER_USER);

		if (powerUserRole == null) {
			return;
		}

		Group userPersonalSiteGroup =
			_groupLocalService.fetchUserPersonalSiteGroup(companyId);

		if (userPersonalSiteGroup == null) {
			return;
		}

		long userPersonalSiteGroupId = userPersonalSiteGroup.getGroupId();

		_addResourcePermission(
			companyId, RedirectEntry.class.getName(),
			ResourceConstants.SCOPE_GROUP,
			String.valueOf(userPersonalSiteGroupId), powerUserRole.getRoleId(),
			ActionKeys.VIEW);
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private RedirectEntryLocalService _redirectEntryLocalService;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}