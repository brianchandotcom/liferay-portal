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

package com.liferay.commerce.product.type.virtual.order.internal.security.permission.resource;

import com.liferay.commerce.product.type.virtual.order.constants.CommerceVirtualOrderConstants;
import com.liferay.commerce.product.type.virtual.order.constants.CommerceVirtualOrderPortletKeys;
import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.security.permission.resource.BasePortletResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.StagedPortletPermissionLogic;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "resource.name=" + CommerceVirtualOrderConstants.RESOURCE_NAME,
	service = PortletResourcePermission.class
)
public class CommerceVirtualOrderItemPortletResourcePermissionWrapper
	extends BasePortletResourcePermissionWrapper {

	@Override
	protected PortletResourcePermission doGetPortletResourcePermission() {
		return PortletResourcePermissionFactory.create(
			CommerceVirtualOrderConstants.RESOURCE_NAME,
			new StagedPortletPermissionLogic(
				_stagingPermission,
				CommerceVirtualOrderPortletKeys.
					COMMERCE_VIRTUAL_ORDER_ITEM_CONTENT));
	}

	@Reference
	private StagingPermission _stagingPermission;

}