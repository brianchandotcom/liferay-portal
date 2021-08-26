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
import com.liferay.commerce.discount.model.CommerceDiscountRel;
import com.liferay.commerce.discount.service.CommerceDiscountRelService;
import com.liferay.commerce.product.exception.NoSuchCProductException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CProductLocalService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountProduct;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Alessio Antonio Rendina
 */
public class DiscountProductUtil {

	public static CommerceDiscountRel addCommerceDiscountRel(
			CProductLocalService cProductLocalService,
			CommerceDiscountRelService commerceDiscountRelService,
			DiscountProduct discountProduct, CommerceDiscount commerceDiscount,
			ServiceContextHelper serviceContextHelper)
		throws PortalException {

		ServiceContext serviceContext =
			serviceContextHelper.getServiceContext();

		CProduct cProduct;

		if (Validator.isNull(
				discountProduct.getProductExternalReferenceCode())) {

			cProduct = cProductLocalService.getCProduct(
				discountProduct.getProductId());
		}
		else {
			cProduct = cProductLocalService.fetchCProductByReferenceCode(
				serviceContext.getCompanyId(),
				discountProduct.getProductExternalReferenceCode());

			if (cProduct == null) {
				throw new NoSuchCProductException(
					"Unable to find product with external reference code " +
						discountProduct.getProductExternalReferenceCode());
			}
		}

		return commerceDiscountRelService.addCommerceDiscountRel(
			commerceDiscount.getCommerceDiscountId(),
			CPDefinition.class.getName(), cProduct.getPublishedCPDefinitionId(),
			serviceContext);
	}

}