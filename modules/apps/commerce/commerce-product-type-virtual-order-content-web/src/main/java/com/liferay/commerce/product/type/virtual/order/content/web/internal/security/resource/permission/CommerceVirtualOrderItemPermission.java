/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.order.content.web.internal.security.resource.permission;

import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CommerceVirtualOrderItemPermission.class)
public class CommerceVirtualOrderItemPermission {

	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceVirtualOrderItem commerceVirtualOrderItem, String actionId)
		throws PortalException {

		return _commerceVirtualOrderItemModelResourcePermission.contains(
			permissionChecker, commerceVirtualOrderItem, actionId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem)"
	)
	private ModelResourcePermission<CommerceVirtualOrderItem>
		_commerceVirtualOrderItemModelResourcePermission;

}