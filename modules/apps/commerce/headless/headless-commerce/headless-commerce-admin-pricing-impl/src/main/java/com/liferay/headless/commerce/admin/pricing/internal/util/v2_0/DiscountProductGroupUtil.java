/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.pricing.internal.util.v2_0;

import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountRel;
import com.liferay.commerce.discount.service.CommerceDiscountRelService;
import com.liferay.commerce.pricing.exception.NoSuchPricingClassException;
import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.service.CommercePricingClassService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountProductGroup;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Alessio Antonio Rendina
 */
public class DiscountProductGroupUtil {

	public static CommerceDiscountRel addCommerceDiscountRel(
			CommercePricingClassService commercePricingClassService,
			CommerceDiscountRelService commerceDiscountRelService,
			DiscountProductGroup discountProductGroup,
			CommerceDiscount commerceDiscount,
			ServiceContextHelper serviceContextHelper)
		throws PortalException {

		ServiceContext serviceContext =
			serviceContextHelper.getServiceContext();

		CommercePricingClass commercePricingClass;

		if (Validator.isNull(
				discountProductGroup.getProductGroupExternalReferenceCode())) {

			commercePricingClass =
				commercePricingClassService.getCommercePricingClass(
					discountProductGroup.getProductGroupId());
		}
		else {
			commercePricingClass =
				commercePricingClassService.fetchByExternalReferenceCode(
					discountProductGroup.getProductGroupExternalReferenceCode(),
					serviceContext.getCompanyId());

			if (commercePricingClass == null) {
				String productGroupExternalReferenceCode =
					discountProductGroup.getProductGroupExternalReferenceCode();

				throw new NoSuchPricingClassException(
					"Unable to find product group with external reference " +
						"code " + productGroupExternalReferenceCode);
			}
		}

		return commerceDiscountRelService.addCommerceDiscountRel(
			commerceDiscount.getCommerceDiscountId(),
			CommercePricingClass.class.getName(),
			commercePricingClass.getCommercePricingClassId(), null,
			serviceContext);
	}

}