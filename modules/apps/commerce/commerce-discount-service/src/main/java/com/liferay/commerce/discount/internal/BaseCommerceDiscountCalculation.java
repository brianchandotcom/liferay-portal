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

package com.liferay.commerce.discount.internal;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.discount.CommerceDiscountCalculation;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.CommerceDiscountLocalService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
public abstract class BaseCommerceDiscountCalculation
	implements CommerceDiscountCalculation {

	protected List<CommerceDiscount> getOrderCommerceDiscountByHierarchy(
			long companyId, CommerceAccount commerceAccount,
			long commerceChannelId, String commerceDiscountTargetType)
		throws PortalException {

		long commerceAccountId = 0;

		if (commerceAccount != null) {
			commerceAccountId = commerceAccount.getCommerceAccountId();
		}

		return _getOrderCommerceDiscountByHierarchy(
			companyId, commerceAccountId, commerceChannelId,
			commerceDiscountTargetType);
	}

	protected List<CommerceDiscount> getProductCommerceDiscountByHierarchy(
			long companyId, CommerceAccount commerceAccount,
			long commerceChannelId, long cpDefinitionId)
		throws PortalException {

		long commerceAccountId = 0;

		if (commerceAccount != null) {
			commerceAccountId = commerceAccount.getCommerceAccountId();
		}

		return _getProductCommerceDiscountByHierarchy(
			companyId, commerceAccountId, commerceChannelId, cpDefinitionId);
	}

	@Reference
	protected CommerceAccountHelper commerceAccountHelper;

	@Reference
	protected CommerceDiscountLocalService commerceDiscountLocalService;

	private List<CommerceDiscount> _getOrderCommerceDiscountByHierarchy(
			long companyId, long commerceAccountId, long commerceChannelId,
			String commerceDiscountTargetType)
		throws PortalException {

		List<CommerceDiscount> commerceDiscounts =
			commerceDiscountLocalService.getAccountAndChannelCommerceDiscounts(
				commerceAccountId, commerceChannelId,
				commerceDiscountTargetType);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			return commerceDiscounts;
		}

		commerceDiscounts =
			commerceDiscountLocalService.getAccountCommerceDiscounts(
				commerceAccountId, commerceDiscountTargetType);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			return commerceDiscounts;
		}

		long[] commerceAccountGroupIds =
			commerceAccountHelper.getCommerceAccountGroupIds(commerceAccountId);

		commerceDiscounts =
			commerceDiscountLocalService.
				getAccountGroupAndChannelCommerceDiscount(
					commerceAccountGroupIds, commerceChannelId,
					commerceDiscountTargetType);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			return commerceDiscounts;
		}

		commerceDiscounts =
			commerceDiscountLocalService.getAccountGroupCommerceDiscount(
				commerceAccountGroupIds, commerceDiscountTargetType);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			return commerceDiscounts;
		}

		commerceDiscounts =
			commerceDiscountLocalService.getChannelCommerceDiscounts(
				commerceChannelId, commerceDiscountTargetType);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			return commerceDiscounts;
		}

		return commerceDiscountLocalService.getUnqualifiedCommerceDiscounts(
			companyId, commerceDiscountTargetType);
	}

	private List<CommerceDiscount> _getProductCommerceDiscountByHierarchy(
			long companyId, long commerceAccountId, long commerceChannelId,
			long cpDefinitionId)
		throws PortalException {

		List<CommerceDiscount> commerceDiscounts =
			commerceDiscountLocalService.getAccountAndChannelCommerceDiscounts(
				commerceAccountId, commerceChannelId, cpDefinitionId);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			return commerceDiscounts;
		}

		commerceDiscounts =
			commerceDiscountLocalService.getAccountCommerceDiscounts(
				commerceAccountId, cpDefinitionId);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			return commerceDiscounts;
		}

		long[] commerceAccountGroupIds =
			commerceAccountHelper.getCommerceAccountGroupIds(commerceAccountId);

		commerceDiscounts =
			commerceDiscountLocalService.
				getAccountGroupAndChannelCommerceDiscount(
					commerceAccountGroupIds, commerceChannelId, cpDefinitionId);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			return commerceDiscounts;
		}

		commerceDiscounts =
			commerceDiscountLocalService.getAccountGroupCommerceDiscount(
				commerceAccountGroupIds, cpDefinitionId);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			return commerceDiscounts;
		}

		commerceDiscounts =
			commerceDiscountLocalService.getChannelCommerceDiscounts(
				commerceChannelId, cpDefinitionId);

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			return commerceDiscounts;
		}

		return commerceDiscountLocalService.getUnqualifiedCommerceDiscounts(
			companyId, cpDefinitionId);
	}

}