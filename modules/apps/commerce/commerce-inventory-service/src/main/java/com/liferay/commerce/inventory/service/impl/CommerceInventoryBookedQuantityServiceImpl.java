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

package com.liferay.commerce.inventory.service.impl;

import com.liferay.commerce.inventory.constants.CommerceInventoryActionKeys;
import com.liferay.commerce.inventory.constants.CommerceInventoryConstants;
import com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantity;
import com.liferay.commerce.inventory.service.base.CommerceInventoryBookedQuantityServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommerceInventoryBookedQuantity"
	},
	service = AopService.class
)
public class CommerceInventoryBookedQuantityServiceImpl
	extends CommerceInventoryBookedQuantityServiceBaseImpl {

	@Override
	public List<CommerceInventoryBookedQuantity>
			getCommerceInventoryBookedQuantities(
				long companyId, String sku, int start, int end)
		throws PrincipalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryBookedQuantityLocalService.
			getCommerceInventoryBookedQuantities(companyId, sku, start, end);
	}

	@Override
	public int getCommerceInventoryBookedQuantitiesCount(
			long companyId, String sku)
		throws PrincipalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryBookedQuantityLocalService.
			getCommerceInventoryBookedQuantitiesCount(companyId, sku);
	}

	@Reference(
		target = "(resource.name=" + CommerceInventoryConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}