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

package com.liferay.commerce.inventory.internal.security.permission.resource;

import com.liferay.commerce.inventory.constants.CommerceInventoryConstants;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.permission.CommerceInventoryWarehousePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	enabled = false, immediate = true,
	property = "model.class.name=com.liferay.commerce.inventory.model.CommerceInventoryWarehouse",
	service = ModelResourcePermission.class
)
public class CommerceInventoryWarehouseModelResourcePermission
	implements ModelResourcePermission<CommerceInventoryWarehouse> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceInventoryWarehouse commerceInventoryWarehouse,
			String actionId)
		throws PortalException {

		commerceInventoryWarehousePermission.check(
			permissionChecker, commerceInventoryWarehouse, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			long commerceInventoryWarehouseId, String actionId)
		throws PortalException {

		commerceInventoryWarehousePermission.check(
			permissionChecker, commerceInventoryWarehouseId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceInventoryWarehouse commerceInventoryWarehouse,
			String actionId)
		throws PortalException {

		return commerceInventoryWarehousePermission.contains(
			permissionChecker, commerceInventoryWarehouse, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			long commerceInventoryWarehouseId, String actionId)
		throws PortalException {

		return commerceInventoryWarehousePermission.contains(
			permissionChecker, commerceInventoryWarehouseId, actionId);
	}

	@Override
	public String getModelName() {
		return CommerceInventoryWarehouse.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	protected CommerceInventoryWarehousePermission
		commerceInventoryWarehousePermission;

	@Reference(
		target = "(resource.name=" + CommerceInventoryConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}