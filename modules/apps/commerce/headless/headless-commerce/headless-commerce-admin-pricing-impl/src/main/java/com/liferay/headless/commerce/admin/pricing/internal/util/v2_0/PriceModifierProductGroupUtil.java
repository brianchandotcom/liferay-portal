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

import com.liferay.commerce.pricing.exception.NoSuchPricingClassException;
import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.commerce.pricing.model.CommercePriceModifierRel;
import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.service.CommercePriceModifierRelService;
import com.liferay.commerce.pricing.service.CommercePricingClassService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceModifierProductGroup;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Riccardo Alberti
 */
public class PriceModifierProductGroupUtil {

	public static CommercePriceModifierRel addCommercePriceModifierRel(
			CommercePricingClassService commercePricingClassService,
			CommercePriceModifierRelService commercePriceModifierRelService,
			PriceModifierProductGroup priceModifierProductGroup,
			CommercePriceModifier commercePriceModifier,
			ServiceContextHelper serviceContextHelper)
		throws PortalException {

		ServiceContext serviceContext =
			serviceContextHelper.getServiceContext();

		CommercePricingClass commercePricingClass;

		if (Validator.isNull(
				priceModifierProductGroup.
					getProductGroupExternalReferenceCode())) {

			commercePricingClass =
				commercePricingClassService.getCommercePricingClass(
					priceModifierProductGroup.getProductGroupId());
		}
		else {
			commercePricingClass =
				commercePricingClassService.fetchByExternalReferenceCode(
					serviceContext.getCompanyId(),
					priceModifierProductGroup.
						getProductGroupExternalReferenceCode());

			if (commercePricingClass == null) {
				String productGroupExternalReferenceCode =
					priceModifierProductGroup.
						getProductGroupExternalReferenceCode();

				throw new NoSuchPricingClassException(
					"Unable to find Product Group with " +
						"externalReferenceCode: " +
							productGroupExternalReferenceCode);
			}
		}

		return commercePriceModifierRelService.addCommercePriceModifierRel(
			commercePriceModifier.getCommercePriceModifierId(),
			CommercePricingClass.class.getName(),
			commercePricingClass.getCommercePricingClassId(), serviceContext);
	}

}