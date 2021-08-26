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
import com.liferay.commerce.pricing.service.CommercePriceModifierRelService;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CProductLocalService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceModifierProduct;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Riccardo Alberti
 */
public class PriceModifierProductUtil {

	public static CommercePriceModifierRel addCommercePriceModifierRel(
			CProductLocalService cProductLocalService,
			CommercePriceModifierRelService commercePriceModifierRelService,
			PriceModifierProduct priceModifierProduct,
			CommercePriceModifier commercePriceModifier,
			ServiceContextHelper serviceContextHelper)
		throws PortalException {

		ServiceContext serviceContext =
			serviceContextHelper.getServiceContext();

		CProduct cProduct;

		if (Validator.isNull(
				priceModifierProduct.getProductExternalReferenceCode())) {

			cProduct = cProductLocalService.getCProduct(
				priceModifierProduct.getProductId());
		}
		else {
			cProduct = cProductLocalService.fetchCProductByReferenceCode(
				serviceContext.getCompanyId(),
				priceModifierProduct.getProductExternalReferenceCode());

			if (cProduct == null) {
				throw new NoSuchPricingClassException(
					"Unable to find product with external reference code " +
						priceModifierProduct.getProductExternalReferenceCode());
			}
		}

		return commercePriceModifierRelService.addCommercePriceModifierRel(
			commercePriceModifier.getCommercePriceModifierId(),
			CPDefinition.class.getName(), cProduct.getPublishedCPDefinitionId(),
			serviceContext);
	}

}