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

package com.liferay.change.tracking.web.internal.instance.lifecycle;

import com.liferay.change.tracking.constants.CTActionKeys;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class CTPortletPermissionPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		long companyId = company.getCompanyId();

		Role role = _roleLocalService.getRole(
			companyId, RoleConstants.PUBLICATIONS_USER);

		_resourcePermissionLocalService.addResourcePermission(
			role.getCompanyId(),
			_resourceActions.getPortletRootModelResource(
				CTPortletKeys.PUBLICATIONS),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(role.getCompanyId()), role.getRoleId(),
			CTActionKeys.ADD_PUBLICATION);
		_resourcePermissionLocalService.addResourcePermission(
			companyId, CTPortletKeys.PUBLICATIONS,
			ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
			role.getRoleId(), ActionKeys.ACCESS_IN_CONTROL_PANEL);
		_resourcePermissionLocalService.addResourcePermission(
			companyId, CTPortletKeys.PUBLICATIONS,
			ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
			role.getRoleId(), ActionKeys.VIEW);
	}

	@Reference(
		target = "(javax.portlet.name=" + CTPortletKeys.PUBLICATIONS + ")"
	)
	private Portlet _portlet;

	@Reference
	private ResourceActions _resourceActions;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}