/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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