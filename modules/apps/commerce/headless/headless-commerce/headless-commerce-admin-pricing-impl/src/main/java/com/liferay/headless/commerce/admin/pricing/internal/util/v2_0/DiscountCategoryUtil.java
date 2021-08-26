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

import com.liferay.asset.kernel.exception.NoSuchCategoryException;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountRel;
import com.liferay.commerce.discount.service.CommerceDiscountRelService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountCategory;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Riccardo Alberti
 */
public class DiscountCategoryUtil {

	public static CommerceDiscountRel addCommerceDiscountRel(
			AssetCategoryLocalService assetCategoryLocalService,
			CommerceDiscountRelService commerceDiscountRelService,
			DiscountCategory discountCategory,
			CommerceDiscount commerceDiscount,
			ServiceContextHelper serviceContextHelper)
		throws PortalException {

		ServiceContext serviceContext =
			serviceContextHelper.getServiceContext();

		AssetCategory assetCategory;

		if (Validator.isNull(
				discountCategory.getCategoryExternalReferenceCode())) {

			assetCategory = assetCategoryLocalService.getCategory(
				discountCategory.getCategoryId());
		}
		else {
			assetCategory =
				assetCategoryLocalService.fetchAssetCategoryByReferenceCode(
					serviceContext.getCompanyId(),
					discountCategory.getCategoryExternalReferenceCode());

			if (assetCategory == null) {
				throw new NoSuchCategoryException(
					"Unable to find category with external reference code " +
						discountCategory.getCategoryExternalReferenceCode());
			}
		}

		return commerceDiscountRelService.addCommerceDiscountRel(
			commerceDiscount.getCommerceDiscountId(),
			AssetCategory.class.getName(), assetCategory.getCategoryId(),
			serviceContext);
	}

}