/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.pricing.internal.util.v1_0;

import com.liferay.asset.kernel.exception.NoSuchCategoryException;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountRel;
import com.liferay.commerce.discount.service.CommerceDiscountRelService;
import com.liferay.headless.commerce.admin.pricing.dto.v1_0.DiscountCategory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Alessio Antonio Rendina
 */
public class DiscountCategoryUtil {

	public static CommerceDiscountRel addCommerceDiscountRel(
			long groupId, AssetCategoryLocalService assetCategoryLocalService,
			CommerceDiscountRelService commerceDiscountRelService,
			DiscountCategory discountCategory,
			CommerceDiscount commerceDiscount, ServiceContext serviceContext)
		throws PortalException {

		AssetCategory assetCategory;

		if (Validator.isNull(
				discountCategory.getCategoryExternalReferenceCode())) {

			assetCategory = assetCategoryLocalService.getCategory(
				discountCategory.getCategoryId());
		}
		else {
			assetCategory =
				assetCategoryLocalService.
					fetchAssetCategoryByExternalReferenceCode(
						discountCategory.getCategoryExternalReferenceCode(),
						groupId);

			if (assetCategory == null) {
				throw new NoSuchCategoryException(
					"Unable to find category with external reference code " +
						discountCategory.getCategoryExternalReferenceCode());
			}
		}

		return commerceDiscountRelService.addCommerceDiscountRel(
			commerceDiscount.getCommerceDiscountId(),
			AssetCategory.class.getName(), assetCategory.getCategoryId(), null,
			serviceContext);
	}

}