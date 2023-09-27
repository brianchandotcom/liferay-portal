/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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