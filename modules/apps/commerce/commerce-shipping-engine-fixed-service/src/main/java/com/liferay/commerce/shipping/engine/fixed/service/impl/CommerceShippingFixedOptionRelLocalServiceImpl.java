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

import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel;
import com.liferay.commerce.shipping.engine.fixed.service.base.CommerceShippingFixedOptionRelLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.math.BigDecimal;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShippingFixedOptionRelLocalServiceImpl
	extends CommerceShippingFixedOptionRelLocalServiceBaseImpl {

	@Override
	public CommerceShippingFixedOptionRel addCommerceShippingFixedOptionRel(
			long userId, long groupId, long commerceShippingMethodId,
			long commerceShippingFixedOptionId,
			long commerceInventoryWarehouseId, long countryId, long regionId,
			String zip, double weightFrom, double weightTo,
			BigDecimal fixedPrice, BigDecimal rateUnitWeightPrice,
			double ratePercentage)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long commerceShippingFixedOptionRelId = counterLocalService.increment();

		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel =
			commerceShippingFixedOptionRelPersistence.create(
				commerceShippingFixedOptionRelId);

		commerceShippingFixedOptionRel.setGroupId(groupId);
		commerceShippingFixedOptionRel.setCompanyId(user.getCompanyId());
		commerceShippingFixedOptionRel.setUserId(user.getUserId());
		commerceShippingFixedOptionRel.setUserName(user.getFullName());
		commerceShippingFixedOptionRel.setCommerceShippingMethodId(
			commerceShippingMethodId);
		commerceShippingFixedOptionRel.setCommerceShippingFixedOptionId(
			commerceShippingFixedOptionId);
		commerceShippingFixedOptionRel.setCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId);
		commerceShippingFixedOptionRel.setCountryId(countryId);
		commerceShippingFixedOptionRel.setRegionId(regionId);
		commerceShippingFixedOptionRel.setZip(zip);
		commerceShippingFixedOptionRel.setWeightFrom(weightFrom);
		commerceShippingFixedOptionRel.setWeightTo(weightTo);
		commerceShippingFixedOptionRel.setFixedPrice(fixedPrice);
		commerceShippingFixedOptionRel.setRateUnitWeightPrice(
			rateUnitWeightPrice);
		commerceShippingFixedOptionRel.setRatePercentage(ratePercentage);

		return commerceShippingFixedOptionRelPersistence.update(
			commerceShippingFixedOptionRel);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public CommerceShippingFixedOptionRel addCommerceShippingFixedOptionRel(
			long commerceShippingMethodId, long commerceShippingFixedOptionId,
			long commerceInventoryWarehouseId, long countryId, long regionId,
			String zip, double weightFrom, double weightTo,
			BigDecimal fixedPrice, BigDecimal rateUnitWeightPrice,
			double ratePercentage, ServiceContext serviceContext)
		throws PortalException {

		return commerceShippingFixedOptionRelLocalService.
			addCommerceShippingFixedOptionRel(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				commerceShippingMethodId, commerceShippingFixedOptionId,
				commerceInventoryWarehouseId, countryId, regionId, zip,
				weightFrom, weightTo, fixedPrice, rateUnitWeightPrice,
				ratePercentage);
	}

	@Override
	public void deleteCommerceShippingFixedOptionRels(
		long commerceShippingFixedOptionId) {

		commerceShippingFixedOptionRelPersistence.
			removeByCommerceShippingFixedOptionId(
				commerceShippingFixedOptionId);
	}

	@Override
	public CommerceShippingFixedOptionRel fetchCommerceShippingFixedOptionRel(
		long commerceShippingFixedOptionId, long countryId, long regionId,
		String zip, double weight) {

		return commerceShippingFixedOptionRelFinder.fetchByC_C_C_Z_W_First(
			commerceShippingFixedOptionId, countryId, regionId, zip, weight);
	}

	@Override
	public List<CommerceShippingFixedOptionRel>
		getCommerceShippingFixedOptionRels(
			long commerceShippingFixedOptionId, int start, int end) {

		return commerceShippingFixedOptionRelPersistence.
			findByCommerceShippingFixedOptionId(
				commerceShippingFixedOptionId, start, end);
	}

	@Override
	public List<CommerceShippingFixedOptionRel>
		getCommerceShippingFixedOptionRels(
			long commerceShippingFixedOptionId, int start, int end,
			OrderByComparator<CommerceShippingFixedOptionRel>
				orderByComparator) {

		return commerceShippingFixedOptionRelPersistence.
			findByCommerceShippingFixedOptionId(
				commerceShippingFixedOptionId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceShippingFixedOptionRelsCount(
		long commerceShippingFixedOptionId) {

		return commerceShippingFixedOptionRelPersistence.
			countByCommerceShippingFixedOptionId(commerceShippingFixedOptionId);
	}

	@Override
	public List<CommerceShippingFixedOptionRel>
		getCommerceShippingMethodFixedOptionRels(
			long commerceShippingMethodId, int start, int end) {

		return commerceShippingFixedOptionRelPersistence.
			findByCommerceShippingFixedOptionId(
				commerceShippingMethodId, start, end);
	}

	@Override
	public List<CommerceShippingFixedOptionRel>
		getCommerceShippingMethodFixedOptionRels(
			long commerceShippingMethodId, int start, int end,
			OrderByComparator<CommerceShippingFixedOptionRel>
				orderByComparator) {

		return commerceShippingFixedOptionRelPersistence.
			findByCommerceShippingMethodId(
				commerceShippingMethodId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceShippingMethodFixedOptionRelsCount(
		long commerceShippingMethodId) {

		return commerceShippingFixedOptionRelPersistence.
			countByCommerceShippingMethodId(commerceShippingMethodId);
	}

	@Override
	public CommerceShippingFixedOptionRel updateCommerceShippingFixedOptionRel(
			long commerceShippingFixedOptionRelId,
			long commerceInventoryWarehouseId, long countryId, long regionId,
			String zip, double weightFrom, double weightTo,
			BigDecimal fixedPrice, BigDecimal rateUnitWeightPrice,
			double ratePercentage)
		throws PortalException {

		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel =
			commerceShippingFixedOptionRelPersistence.findByPrimaryKey(
				commerceShippingFixedOptionRelId);

		commerceShippingFixedOptionRel.setCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId);
		commerceShippingFixedOptionRel.setCountryId(countryId);
		commerceShippingFixedOptionRel.setRegionId(regionId);
		commerceShippingFixedOptionRel.setZip(zip);
		commerceShippingFixedOptionRel.setWeightFrom(weightFrom);
		commerceShippingFixedOptionRel.setWeightTo(weightTo);
		commerceShippingFixedOptionRel.setFixedPrice(fixedPrice);
		commerceShippingFixedOptionRel.setRateUnitWeightPrice(
			rateUnitWeightPrice);
		commerceShippingFixedOptionRel.setRatePercentage(ratePercentage);

		return commerceShippingFixedOptionRelPersistence.update(
			commerceShippingFixedOptionRel);
	}

}