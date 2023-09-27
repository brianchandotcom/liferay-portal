/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.internal.instance.lifecycle;

import com.liferay.depot.internal.util.DepotRoleUtil;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class DepotRolesPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company)
		throws PortalException {

		for (String name : DepotRoleUtil.DEPOT_ROLE_NAMES) {
			Role role = _getOrCreateRole(company.getCompanyId(), name);

			_resourceLocalService.addResources(
				company.getCompanyId(), 0, 0, Role.class.getName(),
				role.getRoleId(), false, false, false);
		}
	}

	private Role _getOrCreateRole(long companyId, String name)
		throws PortalException {

		Role role = _roleLocalService.fetchRole(companyId, name);

		if (role == null) {
			boolean addResource = PermissionThreadLocal.isAddResource();

			try {
				PermissionThreadLocal.setAddResource(false);

				User user = _userLocalService.getGuestUser(companyId);

				return _roleLocalService.addRole(
					user.getUserId(), null, 0, name, null,
					DepotRoleUtil.getDescriptionMap(_language, name),
					RoleConstants.TYPE_DEPOT, null, null);
			}
			finally {
				PermissionThreadLocal.setAddResource(addResource);
			}
		}

		return role;
	}

	@Reference
	private Language _language;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}