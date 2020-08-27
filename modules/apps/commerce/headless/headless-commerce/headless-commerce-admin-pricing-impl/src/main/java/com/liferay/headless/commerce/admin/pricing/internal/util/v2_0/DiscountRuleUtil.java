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

package com.liferay.headless.commerce.admin.pricing.internal.util.v2_0;

import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountRule;
import com.liferay.commerce.discount.service.CommerceDiscountRuleService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountRule;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alessio Antonio Rendina
 */
public class DiscountRuleUtil {

	public static CommerceDiscountRule addCommerceDiscountRule(
			CommerceDiscountRuleService commerceDiscountRuleService,
			DiscountRule discountRule, CommerceDiscount commerceDiscount,
			ServiceContextHelper serviceContextHelper)
		throws PortalException {

		return commerceDiscountRuleService.addCommerceDiscountRule(
			commerceDiscount.getCommerceDiscountId(), discountRule.getName(),
			discountRule.getType(), discountRule.getTypeSettings(),
			serviceContextHelper.getServiceContext());
	}

}