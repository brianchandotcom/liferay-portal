/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Marco Leo
 * @generated
 */
@ProviderType
public interface CommerceDiscountFinder {

	public int countByCommercePricingClassId(
		long commercePricingClassId, String title);

	public int countByCommercePricingClassId(
		long commercePricingClassId, String title, boolean inlineSQLHelper);

	public int countByValidCommerceDiscount(
		long commerceAccountId, long[] commerceAccountGroupIds,
		long commerceChannelId, long commerceDiscountId);

	public java.util.List<com.liferay.commerce.discount.model.CommerceDiscount>
		findByCommercePricingClassId(
			long commercePricingClassId, String title, int start, int end);

	public java.util.List<com.liferay.commerce.discount.model.CommerceDiscount>
		findByCommercePricingClassId(
			long commercePricingClassId, String title, int start, int end,
			boolean inlineSQLHelper);

	public java.util.List<com.liferay.commerce.discount.model.CommerceDiscount>
		findByUnqualifiedProduct(
			long companyId, long cpDefinitionId, long[] assetCategoryIds,
			long[] commercePricingClassIds);

	public java.util.List<com.liferay.commerce.discount.model.CommerceDiscount>
		findByUnqualifiedOrder(
			long companyId, String commerceDiscountTargetType);

	public java.util.List<com.liferay.commerce.discount.model.CommerceDiscount>
		findByA_C_C_Product(
			long commerceAccountId, long cpDefinitionId,
			long[] assetCategoryIds, long[] commercePricingClassIds);

	public java.util.List<com.liferay.commerce.discount.model.CommerceDiscount>
		findByA_C_C_Order(
			long commerceAccountId, String commerceDiscountTargetType);

	public java.util.List<com.liferay.commerce.discount.model.CommerceDiscount>
		findByAG_C_C_Product(
			long[] commerceAccountGroupIds, long cpDefinitionId,
			long[] assetCategoryIds, long[] commercePricingClassIds);

	public java.util.List<com.liferay.commerce.discount.model.CommerceDiscount>
		findByAG_C_C_Order(
			long[] commerceAccountGroupIds, String commerceDiscountTargetType);

	public java.util.List<com.liferay.commerce.discount.model.CommerceDiscount>
		findByC_C_C_Product(
			long commerceChannelId, long cpDefinitionId,
			long[] assetCategoryIds, long[] commercePricingClassIds);

	public java.util.List<com.liferay.commerce.discount.model.CommerceDiscount>
		findByC_C_C_Order(
			long commerceChannelId, String commerceDiscountTargetType);

	public java.util.List<com.liferay.commerce.discount.model.CommerceDiscount>
		findByA_C_C_C_Product(
			long commerceAccountId, long commerceChannelId, long cpDefinitionId,
			long[] assetCategoryIds, long[] commercePricingClassIds);

	public java.util.List<com.liferay.commerce.discount.model.CommerceDiscount>
		findByA_C_C_C_Order(
			long commerceAccountId, long commerceChannelId,
			String commerceDiscountTargetType);

	public java.util.List<com.liferay.commerce.discount.model.CommerceDiscount>
		findByAG_C_C_C_Product(
			long[] commerceAccountGroupIds, long commerceChannelId,
			long cpDefinitionId, long[] assetCategoryIds,
			long[] commercePricingClassIds);

	public java.util.List<com.liferay.commerce.discount.model.CommerceDiscount>
		findByAG_C_C_C_Order(
			long[] commerceAccountGroupIds, long commerceChannelId,
			String commerceDiscountTargetType);

	public java.util.List<com.liferay.commerce.discount.model.CommerceDiscount>
		findPriceListDiscountProduct(
			long[] commerceDiscountIds, long cpDefinitionId,
			long[] assetCategoryIds, long[] commercePricingClassIds);

}