/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
					priceModifierProductGroup.
						getProductGroupExternalReferenceCode(),
					serviceContext.getCompanyId());

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