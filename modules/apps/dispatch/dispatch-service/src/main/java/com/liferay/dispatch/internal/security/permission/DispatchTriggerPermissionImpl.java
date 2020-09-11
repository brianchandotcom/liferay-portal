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

package com.liferay.dispatch.internal.security.permission;

import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.security.permission.DispatchTriggerPermission;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matija Petanjek
 */
@Component(service = DispatchTriggerPermission.class)
public class DispatchTriggerPermissionImpl
	implements DispatchTriggerPermission {

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
			PermissionChecker permissionChecker, long dispatchTriggerId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, dispatchTriggerId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, DispatchTrigger.class.getName(),
				dispatchTriggerId, actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			DispatchTrigger dispatchTrigger, String actionId)
		throws PortalException {

		if (contains(
				permissionChecker, dispatchTrigger.getDispatchTriggerId(),
				actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long dispatchTriggerId,
			String actionId)
		throws PortalException {

		DispatchTrigger dispatchTrigger =
			_dispatchTriggerLocalService.fetchDispatchTrigger(
				dispatchTriggerId);

		if (dispatchTrigger == null) {
			return false;
		}

		return _contains(permissionChecker, dispatchTrigger, actionId);
	}

	private boolean _contains(
			PermissionChecker permissionChecker,
			DispatchTrigger dispatchTrigger, String actionId)
		throws PortalException {

		if (permissionChecker.hasOwnerPermission(
				dispatchTrigger.getCompanyId(), DispatchTrigger.class.getName(),
				dispatchTrigger.getDispatchTriggerId(),
				dispatchTrigger.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			0, DispatchTrigger.class.getName(),
			dispatchTrigger.getDispatchTriggerId(), actionId);
	}

	@Reference
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

}