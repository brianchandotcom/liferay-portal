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