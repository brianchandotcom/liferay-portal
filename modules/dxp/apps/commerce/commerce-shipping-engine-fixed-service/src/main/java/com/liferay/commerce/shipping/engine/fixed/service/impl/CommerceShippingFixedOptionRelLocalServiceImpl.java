/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
			long commerceShippingMethodId, long commerceShippingFixedOptionId,
			long commerceWarehouseId, long commerceCountryId,
			long commerceRegionId, String zip, double weightFrom,
			double weightTo, BigDecimal fixedPrice,
			BigDecimal rateUnitWeightPrice, double ratePercentage,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

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
		commerceShippingFixedOptionRel.setCommerceWarehouseId(
			commerceWarehouseId);
		commerceShippingFixedOptionRel.setCommerceCountryId(commerceCountryId);
		commerceShippingFixedOptionRel.setCommerceRegionId(commerceRegionId);
		commerceShippingFixedOptionRel.setZip(zip);
		commerceShippingFixedOptionRel.setWeightFrom(weightFrom);
		commerceShippingFixedOptionRel.setWeightTo(weightTo);
		commerceShippingFixedOptionRel.setFixedPrice(fixedPrice);
		commerceShippingFixedOptionRel.setRateUnitWeightPrice(
			rateUnitWeightPrice);
		commerceShippingFixedOptionRel.setRatePercentage(ratePercentage);

		commerceShippingFixedOptionRelPersistence.update(
			commerceShippingFixedOptionRel);

		return commerceShippingFixedOptionRel;
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
		long commerceShippingFixedOptionId, long commerceCountryId,
		long commerceRegionId, String zip, double weight) {

		return commerceShippingFixedOptionRelFinder.fetchByC_C_C_Z_W_First(
			commerceShippingFixedOptionId, commerceCountryId, commerceRegionId,
			zip, weight);
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
			long commerceShippingFixedOptionRelId, long commerceWarehouseId,
			long commerceCountryId, long commerceRegionId, String zip,
			double weightFrom, double weightTo, BigDecimal fixedPrice,
			BigDecimal rateUnitWeightPrice, double ratePercentage)
		throws PortalException {

		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel =
			commerceShippingFixedOptionRelPersistence.findByPrimaryKey(
				commerceShippingFixedOptionRelId);

		commerceShippingFixedOptionRel.setCommerceWarehouseId(
			commerceWarehouseId);
		commerceShippingFixedOptionRel.setCommerceCountryId(commerceCountryId);
		commerceShippingFixedOptionRel.setCommerceRegionId(commerceRegionId);
		commerceShippingFixedOptionRel.setZip(zip);
		commerceShippingFixedOptionRel.setWeightFrom(weightFrom);
		commerceShippingFixedOptionRel.setWeightTo(weightTo);
		commerceShippingFixedOptionRel.setFixedPrice(fixedPrice);
		commerceShippingFixedOptionRel.setRateUnitWeightPrice(
			rateUnitWeightPrice);
		commerceShippingFixedOptionRel.setRatePercentage(ratePercentage);

		commerceShippingFixedOptionRelPersistence.update(
			commerceShippingFixedOptionRel);

		return commerceShippingFixedOptionRel;
	}

}