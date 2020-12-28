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

package com.liferay.commerce.account.internal.security.permission.resource;

import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.permission.CommerceAccountGroupPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "model.class.name=com.liferay.commerce.account.model.CommerceAccountGroup",
	service = ModelResourcePermission.class
)
public class CommerceAccountGroupModelResourcePermission
	implements ModelResourcePermission<CommerceAccountGroup> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceAccountGroup commerceAccountGroup, String actionId)
		throws PortalException {

		commerceAccountGroupPermission.check(
			permissionChecker, commerceAccountGroup, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commerceAccountGroupId,
			String actionId)
		throws PortalException {

		commerceAccountGroupPermission.check(
			permissionChecker, commerceAccountGroupId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceAccountGroup commerceAccountGroup, String actionId)
		throws PortalException {

		return commerceAccountGroupPermission.contains(
			permissionChecker, commerceAccountGroup, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commerceAccountGroupId,
			String actionId)
		throws PortalException {

		return commerceAccountGroupPermission.contains(
			permissionChecker, commerceAccountGroupId, actionId);
	}

	@Override
	public String getModelName() {
		return CommerceAccountGroup.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	protected CommerceAccountGroupPermission commerceAccountGroupPermission;

	@Reference(
		target = "(resource.name=" + CommerceAccountConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}