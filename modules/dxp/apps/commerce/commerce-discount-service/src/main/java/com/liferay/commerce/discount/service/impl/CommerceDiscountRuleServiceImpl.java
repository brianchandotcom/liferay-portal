/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.discount.service.impl;

import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountRule;
import com.liferay.commerce.discount.service.base.CommerceDiscountRuleServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

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
			getPermissionChecker(), commerceDiscountId, ActionKeys.UPDATE);

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
			commerceDiscountRule.getCommerceDiscountId(), ActionKeys.UPDATE);

		commerceDiscountRuleLocalService.deleteCommerceDiscountRule(
			commerceDiscountRule);
	}

	@Override
	public CommerceDiscountRule getCommerceDiscountRule(
			long commerceDiscountRuleId)
		throws PortalException {

		CommerceDiscountRule commerceDiscountRule =
			commerceDiscountRuleLocalService.getCommerceDiscountRule(
				commerceDiscountRuleId);

		_commerceDiscountResourcePermission.check(
			getPermissionChecker(),
			commerceDiscountRule.getCommerceDiscountId(), ActionKeys.UPDATE);

		return commerceDiscountRule;
	}

	@Override
	public List<CommerceDiscountRule> getCommerceDiscountRules(
			long commerceDiscountId, int start, int end,
			OrderByComparator<CommerceDiscountRule> orderByComparator)
		throws PortalException {

		_commerceDiscountResourcePermission.check(
			getPermissionChecker(), commerceDiscountId, ActionKeys.UPDATE);

		return commerceDiscountRuleLocalService.getCommerceDiscountRules(
			commerceDiscountId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceDiscountRulesCount(long commerceDiscountId)
		throws PortalException {

		_commerceDiscountResourcePermission.check(
			getPermissionChecker(), commerceDiscountId, ActionKeys.UPDATE);

		return commerceDiscountRuleLocalService.getCommerceDiscountRulesCount(
			commerceDiscountId);
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
			commerceDiscountRule.getCommerceDiscountId(), ActionKeys.UPDATE);

		return commerceDiscountRuleLocalService.updateCommerceDiscountRule(
			commerceDiscountRuleId, type, typeSettings);
	}

	private static volatile ModelResourcePermission<CommerceDiscount>
		_commerceDiscountResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceDiscountRuleServiceImpl.class,
				"_commerceDiscountResourcePermission", CommerceDiscount.class);

}