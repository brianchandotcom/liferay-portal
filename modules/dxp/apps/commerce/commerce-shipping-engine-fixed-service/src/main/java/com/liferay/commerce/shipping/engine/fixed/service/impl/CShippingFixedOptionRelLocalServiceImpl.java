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

import com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel;
import com.liferay.commerce.shipping.engine.fixed.service.base.CShippingFixedOptionRelLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CShippingFixedOptionRelLocalServiceImpl
	extends CShippingFixedOptionRelLocalServiceBaseImpl {

	@Override
	public CShippingFixedOptionRel addCShippingFixedOptionRel(
			long commerceShippingMethodId, long commerceShippingFixedOptionId,
			long commerceWarehouseId, long commerceCountryId,
			long commerceRegionId, String zip, double weightFrom,
			double weightTo, double fixedPrice, double rateUnitWeightPrice,
			double ratePercentage, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long cShippingFixedOptionId = counterLocalService.increment();

		CShippingFixedOptionRel cShippingFixedOptionRel =
			cShippingFixedOptionRelPersistence.create(cShippingFixedOptionId);

		cShippingFixedOptionRel.setGroupId(groupId);
		cShippingFixedOptionRel.setCompanyId(user.getCompanyId());
		cShippingFixedOptionRel.setUserId(user.getUserId());
		cShippingFixedOptionRel.setUserName(user.getFullName());
		cShippingFixedOptionRel.setCommerceShippingMethodId(
			commerceShippingMethodId);
		cShippingFixedOptionRel.setCommerceShippingFixedOptionId(
			commerceShippingFixedOptionId);
		cShippingFixedOptionRel.setCommerceWarehouseId(commerceWarehouseId);
		cShippingFixedOptionRel.setCommerceCountryId(commerceCountryId);
		cShippingFixedOptionRel.setCommerceRegionId(commerceRegionId);
		cShippingFixedOptionRel.setZip(zip);
		cShippingFixedOptionRel.setWeightFrom(weightFrom);
		cShippingFixedOptionRel.setWeightTo(weightTo);
		cShippingFixedOptionRel.setFixedPrice(fixedPrice);
		cShippingFixedOptionRel.setRateUnitWeightPrice(rateUnitWeightPrice);
		cShippingFixedOptionRel.setRatePercentage(ratePercentage);

		cShippingFixedOptionRelPersistence.update(cShippingFixedOptionRel);

		return cShippingFixedOptionRel;
	}

	@Override
	public void deleteCShippingFixedOptionRels(
		long commerceShippingFixedOptionId) {

		cShippingFixedOptionRelPersistence.
			removeByCommerceShippingFixedOptionId(
				commerceShippingFixedOptionId);
	}

	@Override
	public CShippingFixedOptionRel fetchCShippingFixedOptionRel(
		long commerceShippingFixedOptionId, long commerceCountryId,
		long commerceRegionId, String zip, double weight) {

		return cShippingFixedOptionRelFinder.fetchByC_C_C_Z_W_First(
			commerceShippingFixedOptionId, commerceCountryId, commerceRegionId,
			zip, weight);
	}

	@Override
	public List<CShippingFixedOptionRel>
		getCommerceShippingMethodFixedOptionRels(
			long commerceShippingMethodId, int start, int end) {

		return cShippingFixedOptionRelPersistence.
			findByCommerceShippingFixedOptionId(
				commerceShippingMethodId, start, end);
	}

	@Override
	public List<CShippingFixedOptionRel>
		getCommerceShippingMethodFixedOptionRels(
			long commerceShippingMethodId, int start, int end,
			OrderByComparator<CShippingFixedOptionRel> orderByComparator) {

		return
			cShippingFixedOptionRelPersistence.findByCommerceShippingMethodId(
				commerceShippingMethodId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceShippingMethodFixedOptionRelsCount(
		long commerceShippingMethodId) {

		return
			cShippingFixedOptionRelPersistence.countByCommerceShippingMethodId(
				commerceShippingMethodId);
	}

	@Override
	public List<CShippingFixedOptionRel> getCShippingFixedOptionRels(
		long commerceShippingFixedOptionId, int start, int end) {

		return cShippingFixedOptionRelPersistence.
			findByCommerceShippingFixedOptionId(
				commerceShippingFixedOptionId, start, end);
	}

	@Override
	public List<CShippingFixedOptionRel> getCShippingFixedOptionRels(
		long commerceShippingFixedOptionId, int start, int end,
		OrderByComparator<CShippingFixedOptionRel> orderByComparator) {

		return cShippingFixedOptionRelPersistence.
			findByCommerceShippingFixedOptionId(
				commerceShippingFixedOptionId, start, end, orderByComparator);
	}

	@Override
	public int getCShippingFixedOptionRelsCount(
		long commerceShippingFixedOptionId) {

		return cShippingFixedOptionRelPersistence.
			countByCommerceShippingFixedOptionId(commerceShippingFixedOptionId);
	}

	@Override
	public CShippingFixedOptionRel updateCShippingFixedOptionRel(
			long cShippingFixedOptionRelId, long commerceWarehouseId,
			long commerceCountryId, long commerceRegionId, String zip,
			double weightFrom, double weightTo, double fixedPrice,
			double rateUnitWeightPrice, double ratePercentage)
		throws PortalException {

		CShippingFixedOptionRel cShippingFixedOptionRel =
			cShippingFixedOptionRelPersistence.findByPrimaryKey(
				cShippingFixedOptionRelId);

		cShippingFixedOptionRel.setCommerceWarehouseId(commerceWarehouseId);
		cShippingFixedOptionRel.setCommerceCountryId(commerceCountryId);
		cShippingFixedOptionRel.setCommerceRegionId(commerceRegionId);
		cShippingFixedOptionRel.setZip(zip);
		cShippingFixedOptionRel.setWeightFrom(weightFrom);
		cShippingFixedOptionRel.setWeightTo(weightTo);
		cShippingFixedOptionRel.setFixedPrice(fixedPrice);
		cShippingFixedOptionRel.setRateUnitWeightPrice(rateUnitWeightPrice);
		cShippingFixedOptionRel.setRatePercentage(ratePercentage);

		cShippingFixedOptionRelPersistence.update(cShippingFixedOptionRel);

		return cShippingFixedOptionRel;
	}

}