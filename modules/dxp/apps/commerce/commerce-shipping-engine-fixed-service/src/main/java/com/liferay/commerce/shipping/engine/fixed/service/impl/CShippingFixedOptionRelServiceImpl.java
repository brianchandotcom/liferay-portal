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
import com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel;
import com.liferay.commerce.shipping.engine.fixed.service.base.CShippingFixedOptionRelServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CShippingFixedOptionRelServiceImpl
	extends CShippingFixedOptionRelServiceBaseImpl {

	@Override
	public CShippingFixedOptionRel addCShippingFixedOptionRel(
			long commerceShippingMethodId, long commerceShippingFixedOptionId,
			long commerceWarehouseId, long commerceCountryId,
			long commerceRegionId, String zip, double weightFrom,
			double weightTo, double fixedPrice, double rateUnitWeightPrice,
			double ratePercentage, ServiceContext serviceContext)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_SHIPPING_METHODS);

		return cShippingFixedOptionRelLocalService.addCShippingFixedOptionRel(
			commerceShippingMethodId, commerceShippingFixedOptionId,
			commerceWarehouseId, commerceCountryId, commerceRegionId, zip,
			weightFrom, weightTo, fixedPrice, rateUnitWeightPrice,
			ratePercentage, serviceContext);
	}

	@Override
	public void deleteCShippingFixedOptionRel(long cShippingFixedOptionRelId)
		throws PortalException {

		CShippingFixedOptionRel cShippingFixedOptionRel =
			cShippingFixedOptionRelLocalService.getCShippingFixedOptionRel(
				cShippingFixedOptionRelId);

		CommercePermission.check(
			getPermissionChecker(), cShippingFixedOptionRel.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_SHIPPING_METHODS);

		cShippingFixedOptionRelLocalService.deleteCShippingFixedOptionRel(
			cShippingFixedOptionRel);
	}

	@Override
	public CShippingFixedOptionRel fetchCShippingFixedOptionRel(
		long cShippingFixedOptionRelId) {

		return cShippingFixedOptionRelLocalService.fetchCShippingFixedOptionRel(
			cShippingFixedOptionRelId);
	}

	@Override
	public CShippingFixedOptionRel fetchCShippingFixedOptionRel(
		long commerceShippingFixedOptionId, long commerceCountryId,
		long commerceRegionId, String zip, double weight) {

		return cShippingFixedOptionRelLocalService.fetchCShippingFixedOptionRel(
			commerceShippingFixedOptionId, commerceCountryId, commerceRegionId,
			zip, weight);
	}

	@Override
	public List<CShippingFixedOptionRel>
		getCommerceShippingMethodFixedOptionRels(
			long commerceShippingMethodId, int start, int end) {

		return cShippingFixedOptionRelLocalService.
			getCommerceShippingMethodFixedOptionRels(
				commerceShippingMethodId, start, end);
	}

	@Override
	public List<CShippingFixedOptionRel>
		getCommerceShippingMethodFixedOptionRels(
			long commerceShippingMethodId, int start, int end,
			OrderByComparator<CShippingFixedOptionRel> orderByComparator) {

		return cShippingFixedOptionRelLocalService.
			getCommerceShippingMethodFixedOptionRels(
				commerceShippingMethodId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceShippingMethodFixedOptionRelsCount(
		long commerceShippingMethodId) {

		return cShippingFixedOptionRelLocalService.
			getCommerceShippingMethodFixedOptionRelsCount(
				commerceShippingMethodId);
	}

	@Override
	public List<CShippingFixedOptionRel> getCShippingFixedOptionRels(
		long commerceShippingFixedOptionId, int start, int end) {

		return cShippingFixedOptionRelLocalService.getCShippingFixedOptionRels(
			commerceShippingFixedOptionId, start, end);
	}

	@Override
	public List<CShippingFixedOptionRel> getCShippingFixedOptionRels(
		long commerceShippingFixedOptionId, int start, int end,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator) {

		return cShippingFixedOptionRelLocalService.getCShippingFixedOptionRels(
			commerceShippingFixedOptionId, start, end, orderByComparator);
	}

	@Override
	public int getCShippingFixedOptionRelsCount(
		long commerceShippingFixedOptionId) {

		return cShippingFixedOptionRelLocalService.
			getCShippingFixedOptionRelsCount(commerceShippingFixedOptionId);
	}

	@Override
	public CShippingFixedOptionRel updateCShippingFixedOptionRel(
			long cShippingFixedOptionRelId, long commerceWarehouseId,
			long commerceCountryId, long commerceRegionId, String zip,
			double weightFrom, double weightTo, double fixedPrice,
			double rateUnitWeightPrice, double ratePercentage)
		throws PortalException {

		CShippingFixedOptionRel cShippingFixedOptionRel =
			cShippingFixedOptionRelLocalService.getCShippingFixedOptionRel(
				cShippingFixedOptionRelId);

		CommercePermission.check(
			getPermissionChecker(), cShippingFixedOptionRel.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_SHIPPING_METHODS);

		return
			cShippingFixedOptionRelLocalService.updateCShippingFixedOptionRel(
				cShippingFixedOptionRelId, commerceWarehouseId,
				commerceCountryId, commerceRegionId, zip, weightFrom, weightTo,
				fixedPrice, rateUnitWeightPrice, ratePercentage);
	}

}