/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			cProduct =
				cProductLocalService.fetchCProductByExternalReferenceCode(
					priceModifierProduct.getProductExternalReferenceCode(),
					serviceContext.getCompanyId());

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