/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.internal.target;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.commerce.discount.target.CommerceDiscountProductTarget;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.HashSet;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Joao Victor Alves
 */
@Component(service = CommerceDiscountProductTarget.class)
public class ApplyToCategoriesCommerceDiscountProductTarget
	extends BaseCommerceDiscountProductTarget {

	public static final String COMMERCE_DISCOUNT_TARGET_ASSET_CATEGORY_IDS =
		"commerce_discount_target_asset_category_ids";

	@Override
	public String getFieldName() {
		return COMMERCE_DISCOUNT_TARGET_ASSET_CATEGORY_IDS;
	}

	@Override
	public Filter getFilter(CPDefinition cpDefinition) {
		TermsFilter termsFilter = new TermsFilter(getFieldName());

		termsFilter.addValues(
			ArrayUtil.toStringArray(_getAssetCategoryIds(cpDefinition)));

		return termsFilter;
	}

	private long[] _getAssetCategoryIds(CPDefinition cpDefinition) {
		try {
			AssetEntry assetEntry = _assetEntryLocalService.getEntry(
				CPDefinition.class.getName(), cpDefinition.getCPDefinitionId());

			Set<AssetCategory> assetCategories = new HashSet<>();

			for (AssetCategory assetCategory : assetEntry.getCategories()) {
				assetCategories.add(assetCategory);
				assetCategories.addAll(assetCategory.getAncestors());
			}

			return TransformUtil.transformToLongArray(
				assetCategories, AssetCategory::getCategoryId);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return new long[0];
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ApplyToCategoriesCommerceDiscountProductTarget.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

}