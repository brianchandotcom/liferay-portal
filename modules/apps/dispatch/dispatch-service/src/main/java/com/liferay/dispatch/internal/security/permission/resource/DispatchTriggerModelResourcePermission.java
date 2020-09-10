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
import com.liferay.dispatch.security.permission.DispatchTriggerPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matija Petanjek
 */
@Component(
	immediate = true,
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

		dispatchTriggerPermission.check(
			permissionChecker, dispatchTrigger, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		dispatchTriggerPermission.check(
			permissionChecker, primaryKey, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			DispatchTrigger dispatchTrigger, String actionId)
		throws PortalException {

		return dispatchTriggerPermission.contains(
			permissionChecker, dispatchTrigger, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long dispatchTriggerId,
			String actionId)
		throws PortalException {

		return dispatchTriggerPermission.contains(
			permissionChecker, dispatchTriggerId, actionId);
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
	protected DispatchTriggerPermission dispatchTriggerPermission;

}