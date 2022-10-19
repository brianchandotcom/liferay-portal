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

package com.liferay.change.tracking.web.internal.model.listener;

import com.liferay.change.tracking.constants.CTActionKeys;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = ModelListener.class)
public class RoleModelListener extends BaseModelListener<Role> {

	@Override
	public void onAfterCreate(Role role) throws ModelListenerException {
		if (!Objects.equals(role.getName(), RoleConstants.PUBLICATIONS_USER) ||
			!role.isSystem()) {

			return;
		}

		try {
			_resourcePermissionLocalService.addResourcePermission(
				role.getCompanyId(),
				_resourceActions.getPortletRootModelResource(
					CTPortletKeys.PUBLICATIONS),
				ResourceConstants.SCOPE_COMPANY,
				String.valueOf(role.getCompanyId()), role.getRoleId(),
				CTActionKeys.ADD_PUBLICATION);
			_resourcePermissionLocalService.addResourcePermission(
				role.getCompanyId(), CTPortletKeys.PUBLICATIONS,
				ResourceConstants.SCOPE_COMPANY,
				String.valueOf(role.getCompanyId()), role.getRoleId(),
				ActionKeys.ACCESS_IN_CONTROL_PANEL);
			_resourcePermissionLocalService.addResourcePermission(
				role.getCompanyId(), CTPortletKeys.PUBLICATIONS,
				ResourceConstants.SCOPE_COMPANY,
				String.valueOf(role.getCompanyId()), role.getRoleId(),
				ActionKeys.VIEW);
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	@Reference(
		target = "(javax.portlet.name=" + CTPortletKeys.PUBLICATIONS + ")"
	)
	private Portlet _portlet;

	@Reference
	private ResourceActions _resourceActions;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}