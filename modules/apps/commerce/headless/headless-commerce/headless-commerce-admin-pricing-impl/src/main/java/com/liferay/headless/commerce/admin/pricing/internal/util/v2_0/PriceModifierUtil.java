/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.pricing.internal.util.v2_0;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.commerce.pricing.model.CommercePriceModifierRel;
import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.service.CommercePriceModifierRelService;
import com.liferay.commerce.pricing.service.CommercePricingClassService;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CProductLocalService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceModifier;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceModifierCategory;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceModifierProduct;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceModifierProductGroup;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Riccardo Alberti
 */
public class PriceModifierUtil {

	public static void addOrUpdateCommercePriceModifierRels(
			long groupId, AssetCategoryLocalService assetCategoryLocalService,
			CommercePricingClassService commercePricingClassService,
			CProductLocalService cProductLocalService,
			CommercePriceModifierRelService commercePriceModifierRelService,
			PriceModifier priceModifier,
			CommercePriceModifier commercePriceModifier,
			ServiceContextHelper serviceContextHelper)
		throws PortalException {

		PriceModifierCategory[] priceModifierCategories =
			priceModifier.getPriceModifierCategories();

		if (priceModifierCategories != null) {
			for (PriceModifierCategory priceModifierCategory :
					priceModifierCategories) {

				CommercePriceModifierRel commercePriceModifierRel =
					commercePriceModifierRelService.
						fetchCommercePriceModifierRel(
							commercePriceModifier.getCommercePriceModifierId(),
							AssetCategory.class.getName(),
							priceModifierCategory.getCategoryId());

				if (commercePriceModifierRel != null) {
					continue;
				}

				PriceModifierCategoryUtil.addCommercePriceModifierRel(
					groupId, assetCategoryLocalService,
					commercePriceModifierRelService, priceModifierCategory,
					commercePriceModifier, serviceContextHelper);
			}
		}

		PriceModifierProductGroup[] priceModifierProductGroups =
			priceModifier.getPriceModifierProductGroups();

		if (priceModifierProductGroups != null) {
			for (PriceModifierProductGroup priceModifierProductGroup :
					priceModifierProductGroups) {

				CommercePriceModifierRel commercePriceModifierRel =
					commercePriceModifierRelService.
						fetchCommercePriceModifierRel(
							commercePriceModifier.getCommercePriceModifierId(),
							CommercePricingClass.class.getName(),
							priceModifierProductGroup.getProductGroupId());

				if (commercePriceModifierRel != null) {
					continue;
				}

				PriceModifierProductGroupUtil.addCommercePriceModifierRel(
					commercePricingClassService,
					commercePriceModifierRelService, priceModifierProductGroup,
					commercePriceModifier, serviceContextHelper);
			}
		}

		PriceModifierProduct[] priceModifierProducts =
			priceModifier.getPriceModifierProducts();

		if (priceModifierProducts != null) {
			for (PriceModifierProduct priceModifierProduct :
					priceModifierProducts) {

				CommercePriceModifierRel commercePriceModifierRel =
					commercePriceModifierRelService.
						fetchCommercePriceModifierRel(
							commercePriceModifier.getCommercePriceModifierId(),
							CPDefinition.class.getName(),
							priceModifierProduct.getProductId());

				if (commercePriceModifierRel != null) {
					continue;
				}

				PriceModifierProductUtil.addCommercePriceModifierRel(
					cProductLocalService, commercePriceModifierRelService,
					priceModifierProduct, commercePriceModifier,
					serviceContextHelper);
			}
		}
	}

}