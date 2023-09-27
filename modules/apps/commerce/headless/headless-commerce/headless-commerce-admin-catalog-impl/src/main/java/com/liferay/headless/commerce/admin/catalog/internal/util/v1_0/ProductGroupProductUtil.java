/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			cProduct =
				cProductLocalService.fetchCProductByExternalReferenceCode(
					productGroupProduct.getProductExternalReferenceCode(),
					serviceContext.getCompanyId());

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