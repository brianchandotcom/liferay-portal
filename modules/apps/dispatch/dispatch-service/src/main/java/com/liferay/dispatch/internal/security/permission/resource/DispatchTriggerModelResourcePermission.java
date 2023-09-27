/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.internal.security.permission.resource;

import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matija Petanjek
 */
@Component(
	property = "model.class.name=com.liferay.dispatch.model.DispatchTrigger",
	service = ModelResourcePermission.class
)
public class DispatchTriggerModelResourcePermission
	implements ModelResourcePermission<DispatchTrigger> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			DispatchTrigger dispatchTrigger, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, dispatchTrigger, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, DispatchTrigger.class.getName(),
				dispatchTrigger.getDispatchTriggerId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, primaryKey, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, DispatchTrigger.class.getName(), primaryKey,
				actionId);
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, DispatchTrigger dispatchTrigger,
		String actionId) {

		if (permissionChecker.isCompanyAdmin(dispatchTrigger.getCompanyId())) {
			return true;
		}

		return permissionChecker.hasPermission(
			GroupConstants.DEFAULT_LIVE_GROUP_ID,
			DispatchTrigger.class.getName(),
			dispatchTrigger.getDispatchTriggerId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long dispatchTriggerId,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			_dispatchTriggerLocalService.getDispatchTrigger(dispatchTriggerId),
			actionId);
	}

	@Override
	public String getModelName() {
		return DispatchTrigger.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

}