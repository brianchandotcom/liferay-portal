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

package com.liferay.commerce.discount.service.impl;

import com.liferay.commerce.discount.constants.CommerceDiscountActionKeys;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountRule;
import com.liferay.commerce.discount.service.base.CommerceDiscountRuleServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceDiscountRuleServiceImpl
	extends CommerceDiscountRuleServiceBaseImpl {

	@Override
	public CommerceDiscountRule addCommerceDiscountRule(
			long commerceDiscountId, String type, String typeSettings,
			ServiceContext serviceContext)
		throws PortalException {

		_commerceDiscountResourcePermission.check(
			getPermissionChecker(), commerceDiscountId,
			CommerceDiscountActionKeys.MANAGE_COMMERCE_DISCOUNT_RULES);

		return commerceDiscountRuleLocalService.addCommerceDiscountRule(
			commerceDiscountId, type, typeSettings, serviceContext);
	}

	@Override
	public void deleteCommerceDiscountRule(long commerceDiscountRuleId)
		throws PortalException {

		CommerceDiscountRule commerceDiscountRule =
			commerceDiscountRuleLocalService.getCommerceDiscountRule(
				commerceDiscountRuleId);

		_commerceDiscountResourcePermission.check(
			getPermissionChecker(),
			commerceDiscountRule.getCommerceDiscountId(),
			CommerceDiscountActionKeys.MANAGE_COMMERCE_DISCOUNT_RULES);

		commerceDiscountRuleLocalService.deleteCommerceDiscountRule(
			commerceDiscountRule);
	}

	@Override
	public CommerceDiscountRule updateCommerceDiscountRule(
			long commerceDiscountRuleId, String type, String typeSettings)
		throws PortalException {

		CommerceDiscountRule commerceDiscountRule =
			commerceDiscountRuleLocalService.getCommerceDiscountRule(
				commerceDiscountRuleId);

		_commerceDiscountResourcePermission.check(
			getPermissionChecker(),
			commerceDiscountRule.getCommerceDiscountId(),
			CommerceDiscountActionKeys.MANAGE_COMMERCE_DISCOUNT_RULES);

		return commerceDiscountRuleLocalService.updateCommerceDiscountRule(
			commerceDiscountRuleId, type, typeSettings);
	}

	private static volatile ModelResourcePermission<CommerceDiscount>
		_commerceDiscountResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceDiscountRuleServiceImpl.class,
				"_commerceDiscountResourcePermission", CommerceDiscount.class);

}