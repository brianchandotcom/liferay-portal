/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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