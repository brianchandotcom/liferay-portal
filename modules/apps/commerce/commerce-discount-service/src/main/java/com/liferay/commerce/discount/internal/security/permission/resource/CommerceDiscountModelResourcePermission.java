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

package com.liferay.commerce.discount.internal.security.permission.resource;

import com.liferay.commerce.discount.constants.CommerceDiscountConstants;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.permission.CommerceDiscountPermission;
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
	property = "model.class.name=com.liferay.commerce.discount.model.CommerceDiscount",
	service = ModelResourcePermission.class
)
public class CommerceDiscountModelResourcePermission
	implements ModelResourcePermission<CommerceDiscount> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceDiscount commerceDiscount, String actionId)
		throws PortalException {

		commerceDiscountPermission.check(
			permissionChecker, commerceDiscount, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commerceDiscountId,
			String actionId)
		throws PortalException {

		commerceDiscountPermission.check(
			permissionChecker, commerceDiscountId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceDiscount commerceDiscount, String actionId)
		throws PortalException {

		return commerceDiscountPermission.contains(
			permissionChecker, commerceDiscount, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commerceDiscountId,
			String actionId)
		throws PortalException {

		return commerceDiscountPermission.contains(
			permissionChecker, commerceDiscountId, actionId);
	}

	@Override
	public String getModelName() {
		return CommerceDiscount.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	protected CommerceDiscountPermission commerceDiscountPermission;

	@Reference(
		target = "(resource.name=" + CommerceDiscountConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}