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

package com.liferay.commerce.internal.security.permission.resource;

import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.permission.CommerceOrderTypePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false, immediate = true,
	property = "model.class.name=com.liferay.commerce.model.CommerceOrderType",
	service = ModelResourcePermission.class
)
public class CommerceOrderTypeModelResourcePermission
	implements ModelResourcePermission<CommerceOrderType> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceOrderType commerceOrderType, String actionId)
		throws PortalException {

		_commerceOrderTypePermission.check(
			permissionChecker, commerceOrderType, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commerceOrderTypeId,
			String actionId)
		throws PortalException {

		_commerceOrderTypePermission.check(
			permissionChecker, commerceOrderTypeId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceOrderType commerceOrderType, String actionId)
		throws PortalException {

		return _commerceOrderTypePermission.contains(
			permissionChecker, commerceOrderType, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commerceOrderTypeId,
			String actionId)
		throws PortalException {

		return _commerceOrderTypePermission.contains(
			permissionChecker, commerceOrderTypeId, actionId);
	}

	@Override
	public String getModelName() {
		return CommerceOrderType.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	private CommerceOrderTypePermission _commerceOrderTypePermission;

	@Reference(
		target = "(resource.name=" + CommerceConstants.RESOURCE_NAME_COMMERCE_ORDER_TYPE + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}