/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.security.permission.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionLogic;
import com.liferay.portal.kernel.service.OrganizationLocalService;

import java.util.List;

/**
 * @author Pei-Jung Lan
 */
public class AccountPortletResourcePermissionLogic
	implements PortletResourcePermissionLogic {

	public AccountPortletResourcePermissionLogic(
		OrganizationLocalService organizationLocalService) {

		_organizationLocalService = organizationLocalService;
	}

	@Override
	public Boolean contains(
		PermissionChecker permissionChecker, String name, Group group,
		String actionId) {

		if (permissionChecker.hasPermission(group, name, 0, actionId)) {
			return true;
		}

		try {
			List<Organization> organizations =
				_organizationLocalService.getUserOrganizations(
					permissionChecker.getUserId(), true);

			for (Organization organization : organizations) {
				if (permissionChecker.hasPermission(
						organization.getGroupId(), name, 0, actionId)) {

					return true;
				}
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountPortletResourcePermissionLogic.class);

	private final OrganizationLocalService _organizationLocalService;

}