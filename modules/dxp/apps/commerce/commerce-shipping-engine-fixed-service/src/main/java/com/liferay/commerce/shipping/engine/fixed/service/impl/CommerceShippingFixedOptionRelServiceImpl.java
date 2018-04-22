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

package com.liferay.commerce.shipping.engine.fixed.service.impl;

import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.service.permission.CommercePermission;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel;
import com.liferay.commerce.shipping.engine.fixed.service.base.CommerceShippingFixedOptionRelServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShippingFixedOptionRelServiceImpl
	extends CommerceShippingFixedOptionRelServiceBaseImpl {

	@Override
	public CommerceShippingFixedOptionRel addCommerceShippingFixedOptionRel(
			long commerceShippingMethodId, long commerceShippingFixedOptionId,
			long commerceWarehouseId, long commerceCountryId,
			long commerceRegionId, String zip, double weightFrom,
			double weightTo, BigDecimal fixedPrice,
			BigDecimal rateUnitWeightPrice, double ratePercentage,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_SHIPPING_METHODS);

		return commerceShippingFixedOptionRelLocalService.
			addCommerceShippingFixedOptionRel(
				commerceShippingMethodId, commerceShippingFixedOptionId,
				commerceWarehouseId, commerceCountryId, commerceRegionId, zip,
				weightFrom, weightTo, fixedPrice, rateUnitWeightPrice,
				ratePercentage, serviceContext);
	}

	@Override
	public void deleteCommerceShippingFixedOptionRel(
			long commerceShippingFixedOptionRelId)
		throws PortalException {

		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel =
			commerceShippingFixedOptionRelLocalService.
				getCommerceShippingFixedOptionRel(
					commerceShippingFixedOptionRelId);

		CommercePermission.check(
			getPermissionChecker(), commerceShippingFixedOptionRel.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_SHIPPING_METHODS);

		commerceShippingFixedOptionRelLocalService.
			deleteCommerceShippingFixedOptionRel(
				commerceShippingFixedOptionRel);
	}

	@Override
	public CommerceShippingFixedOptionRel fetchCommerceShippingFixedOptionRel(
		long commerceShippingFixedOptionRelId) {

		return commerceShippingFixedOptionRelLocalService.
			fetchCommerceShippingFixedOptionRel(
				commerceShippingFixedOptionRelId);
	}

	@Override
	public CommerceShippingFixedOptionRel fetchCommerceShippingFixedOptionRel(
		long commerceShippingFixedOptionId, long commerceCountryId,
		long commerceRegionId, String zip, double weight) {

		return commerceShippingFixedOptionRelLocalService.
			fetchCommerceShippingFixedOptionRel(
				commerceShippingFixedOptionId, commerceCountryId,
				commerceRegionId, zip, weight);
	}

	@Override
	public List<CommerceShippingFixedOptionRel>
		getCommerceShippingFixedOptionRels(
			long commerceShippingFixedOptionId, int start, int end) {

		return commerceShippingFixedOptionRelLocalService.
			getCommerceShippingFixedOptionRels(
				commerceShippingFixedOptionId, start, end);
	}

	@Override
	public List<CommerceShippingFixedOptionRel>
		getCommerceShippingFixedOptionRels(
			long commerceShippingFixedOptionId, int start, int end,
			OrderByComparator<CommerceShippingFixedOptionRel>
				orderByComparator) {

		return commerceShippingFixedOptionRelLocalService.
			getCommerceShippingFixedOptionRels(
				commerceShippingFixedOptionId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceShippingFixedOptionRelsCount(
		long commerceShippingFixedOptionId) {

		return commerceShippingFixedOptionRelLocalService.
			getCommerceShippingFixedOptionRelsCount(
				commerceShippingFixedOptionId);
	}

	@Override
	public List<CommerceShippingFixedOptionRel>
		getCommerceShippingMethodFixedOptionRels(
			long commerceShippingMethodId, int start, int end) {

		return commerceShippingFixedOptionRelLocalService.
			getCommerceShippingMethodFixedOptionRels(
				commerceShippingMethodId, start, end);
	}

	@Override
	public List<CommerceShippingFixedOptionRel>
		getCommerceShippingMethodFixedOptionRels(
			long commerceShippingMethodId, int start, int end,
			OrderByComparator<CommerceShippingFixedOptionRel>
				orderByComparator) {

		return commerceShippingFixedOptionRelLocalService.
			getCommerceShippingMethodFixedOptionRels(
				commerceShippingMethodId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceShippingMethodFixedOptionRelsCount(
		long commerceShippingMethodId) {

		return commerceShippingFixedOptionRelLocalService.
			getCommerceShippingMethodFixedOptionRelsCount(
				commerceShippingMethodId);
	}

	@Override
	public CommerceShippingFixedOptionRel updateCommerceShippingFixedOptionRel(
			long commerceShippingFixedOptionRelId, long commerceWarehouseId,
			long commerceCountryId, long commerceRegionId, String zip,
			double weightFrom, double weightTo, BigDecimal fixedPrice,
			BigDecimal rateUnitWeightPrice, double ratePercentage)
		throws PortalException {

		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel =
			commerceShippingFixedOptionRelLocalService.
				getCommerceShippingFixedOptionRel(
					commerceShippingFixedOptionRelId);

		CommercePermission.check(
			getPermissionChecker(), commerceShippingFixedOptionRel.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_SHIPPING_METHODS);

		return commerceShippingFixedOptionRelLocalService.
			updateCommerceShippingFixedOptionRel(
				commerceShippingFixedOptionRelId, commerceWarehouseId,
				commerceCountryId, commerceRegionId, zip, weightFrom, weightTo,
				fixedPrice, rateUnitWeightPrice, ratePercentage);
	}

}