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

package com.liferay.headless.commerce.admin.catalog.internal.util.v1_0;

import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.model.CommercePricingClassCPDefinitionRel;
import com.liferay.commerce.pricing.service.CommercePricingClassCPDefinitionRelService;
import com.liferay.commerce.product.exception.NoSuchCProductException;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CProductLocalService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductGroupProduct;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Riccardo Alberti
 */
public class ProductGroupProductUtil {

	public static CommercePricingClassCPDefinitionRel
			addCommercePricingClassCPDefinitionRel(
				CProductLocalService cProductLocalService,
				CommercePricingClassCPDefinitionRelService
					commercePricingClassCPDefinitionRelService,
				ProductGroupProduct productGroupProduct,
				CommercePricingClass commercePricingClass,
				ServiceContextHelper serviceContextHelper)
		throws PortalException {

		ServiceContext serviceContext =
			serviceContextHelper.getServiceContext();

		CProduct cProduct;

		if (Validator.isNull(
				productGroupProduct.getProductExternalReferenceCode())) {

			cProduct = cProductLocalService.getCProduct(
				productGroupProduct.getProductId());
		}
		else {
			cProduct = cProductLocalService.fetchCProductByReferenceCode(
				serviceContext.getCompanyId(),
				productGroupProduct.getProductExternalReferenceCode());

			if (cProduct == null) {
				throw new NoSuchCProductException(
					"Unable to find product with external reference code " +
						productGroupProduct.getProductExternalReferenceCode());
			}
		}

		return commercePricingClassCPDefinitionRelService.
			addCommercePricingClassCPDefinitionRel(
				commercePricingClass.getCommercePricingClassId(),
				cProduct.getPublishedCPDefinitionId(), serviceContext);
	}

}