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
import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.commerce.pricing.model.CommercePriceModifierRel;
import com.liferay.commerce.pricing.service.CommercePriceModifierRelService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceModifierCategory;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Riccardo Alberti
 */
public class PriceModifierCategoryUtil {

	public static CommercePriceModifierRel addCommercePriceModifierRel(
			AssetCategoryLocalService assetCategoryLocalService,
			CommercePriceModifierRelService commercePriceModifierRelService,
			PriceModifierCategory priceModifierCategory,
			CommercePriceModifier commercePriceModifier,
			ServiceContextHelper serviceContextHelper)
		throws PortalException {

		ServiceContext serviceContext =
			serviceContextHelper.getServiceContext();

		AssetCategory assetCategory;

		if (Validator.isNull(
				priceModifierCategory.getCategoryExternalReferenceCode())) {

			assetCategory = assetCategoryLocalService.getCategory(
				priceModifierCategory.getCategoryId());
		}
		else {
			assetCategory =
				assetCategoryLocalService.fetchAssetCategoryByReferenceCode(
					serviceContext.getCompanyId(),
					priceModifierCategory.getCategoryExternalReferenceCode());

			if (assetCategory == null) {
				String categoryExternalReferenceCode =
					priceModifierCategory.getCategoryExternalReferenceCode();

				throw new NoSuchCategoryException(
					"Unable to find category with external reference code " +
						categoryExternalReferenceCode);
			}
		}

		return commercePriceModifierRelService.addCommercePriceModifierRel(
			commercePriceModifier.getCommercePriceModifierId(),
			AssetCategory.class.getName(), assetCategory.getCategoryId(),
			serviceContext);
	}

}