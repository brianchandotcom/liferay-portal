/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.patcher.service.impl;

import com.liferay.osb.patcher.constants.PatcherActionKeys;
import com.liferay.osb.patcher.constants.PatcherPortletKeys;
import com.liferay.osb.patcher.constants.PatcherRoleConstants;
import com.liferay.osb.patcher.service.base.PatcherFixServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.RoleLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=osbpatcher",
		"json.web.service.context.path=PatcherFix"
	},
	service = AopService.class
)
public class PatcherFixServiceImpl extends PatcherFixServiceBaseImpl {

	@Override
	public JSONObject checkPatcherFixesByPatcherProjectVersionName(
			String patcherProjectVersionName, String ticketList)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!_roleLocalService.hasUserRole(
				permissionChecker.getUserId(), permissionChecker.getCompanyId(),
				PatcherRoleConstants.PATCHER_USER, true)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, PatcherPortletKeys.PATCHER, 0,
				PatcherActionKeys.FIXES);
		}

		return patcherFixLocalService.
			checkPatcherFixesByPatcherProjectVersionName(
				patcherProjectVersionName, ticketList);
	}

	@Reference
	private RoleLocalService _roleLocalService;

}

// LIFERAY-SERVICE-BUILDER-HASH:1619952532