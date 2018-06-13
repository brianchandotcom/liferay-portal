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

package com.liferay.commerce.product.internal.security.permission.resource;

import com.liferay.commerce.product.constants.CPActionKeys;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

/**
 * @author Alessio Antonio Rendina
 */
public class CPSpecificationOptionModelResourcePermissionLogic
	implements ModelResourcePermissionLogic<CPSpecificationOption> {

	public CPSpecificationOptionModelResourcePermissionLogic(
		PortletResourcePermission portletResourcePermission) {

		_portletResourcePermission = portletResourcePermission;
	}

	@Override
	public Boolean contains(
			PermissionChecker permissionChecker, String name,
			CPSpecificationOption cpSpecificationOption, String actionId)
		throws PortalException {

		if (permissionChecker.isCompanyAdmin(
				cpSpecificationOption.getCompanyId()) ||
			permissionChecker.isGroupAdmin(
				cpSpecificationOption.getGroupId())) {

			return true;
		}

		if (actionId.equals(ActionKeys.DELETE) ||
			actionId.equals(ActionKeys.UPDATE) ||
			actionId.equals(ActionKeys.VIEW)) {

			return _hasPermission(
				permissionChecker, cpSpecificationOption.getGroupId(),
				CPActionKeys.MANAGE_CATALOG);
		}

		return _hasPermission(
			permissionChecker, cpSpecificationOption.getGroupId(), actionId);
	}

	private boolean _hasPermission(
		PermissionChecker permissionChecker, long groupId,
		String... actionIds) {

		for (String actionId : actionIds) {
			if (_portletResourcePermission.contains(
					permissionChecker, groupId, actionId)) {

				return true;
			}
		}

		return false;
	}

	private final PortletResourcePermission _portletResourcePermission;

}