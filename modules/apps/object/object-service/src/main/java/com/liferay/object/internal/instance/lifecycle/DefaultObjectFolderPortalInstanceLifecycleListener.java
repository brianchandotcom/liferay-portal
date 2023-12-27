/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.instance.lifecycle;

import com.liferay.object.model.ObjectFolder;
import com.liferay.object.service.ObjectFolderLocalService;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class DefaultObjectFolderPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		ObjectFolder objectFolder =
			_objectFolderLocalService.getOrAddDefaultObjectFolder(
				company.getCompanyId());

		Role guestRole = _roleLocalService.getRole(
			company.getCompanyId(), RoleConstants.GUEST);

		_resourcePermissionLocalService.setResourcePermissions(
			company.getCompanyId(), ObjectFolder.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(objectFolder.getObjectFolderId()),
			guestRole.getRoleId(), new String[] {ActionKeys.VIEW});
	}

	@Reference
	private ObjectFolderLocalService _objectFolderLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}