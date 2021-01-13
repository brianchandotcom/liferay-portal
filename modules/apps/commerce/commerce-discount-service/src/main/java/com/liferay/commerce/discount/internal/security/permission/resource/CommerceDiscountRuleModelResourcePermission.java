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

import com.liferay.commerce.discount.model.CommerceDiscountRule;
import com.liferay.commerce.discount.permission.CommerceDiscountPermission;
import com.liferay.commerce.discount.service.CommerceDiscountRuleLocalService;
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
	property = "model.class.name=com.liferay.commerce.discount.model.CommerceDiscountRule",
	service = ModelResourcePermission.class
)
public class CommerceDiscountRuleModelResourcePermission
	implements ModelResourcePermission<CommerceDiscountRule> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceDiscountRule commerceDiscountRule, String actionId)
		throws PortalException {

		commerceDiscountPermission.check(
			permissionChecker, commerceDiscountRule.getCommerceDiscountId(),
			actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commerceDiscountRuleId,
			String actionId)
		throws PortalException {

		CommerceDiscountRule commerceDiscountRule =
			commerceDiscountRuleLocalService.getCommerceDiscountRule(
				commerceDiscountRuleId);

		commerceDiscountPermission.check(
			permissionChecker, commerceDiscountRule.getCommerceDiscountId(),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceDiscountRule commerceDiscountRule, String actionId)
		throws PortalException {

		return commerceDiscountPermission.contains(
			permissionChecker, commerceDiscountRule.getCommerceDiscountId(),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commerceDiscountRuleId,
			String actionId)
		throws PortalException {

		CommerceDiscountRule commerceDiscountRule =
			commerceDiscountRuleLocalService.getCommerceDiscountRule(
				commerceDiscountRuleId);

		return commerceDiscountPermission.contains(
			permissionChecker, commerceDiscountRule.getCommerceDiscountId(),
			actionId);
	}

	@Override
	public String getModelName() {
		return CommerceDiscountRule.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	protected CommerceDiscountPermission commerceDiscountPermission;

	@Reference
	protected CommerceDiscountRuleLocalService commerceDiscountRuleLocalService;

}