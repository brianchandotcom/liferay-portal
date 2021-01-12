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