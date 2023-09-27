/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.engine.fixed.service.impl;

import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel;
import com.liferay.commerce.shipping.engine.fixed.service.base.CommerceShippingFixedOptionRelLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.math.BigDecimal;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "model.class.name=com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel",
	service = AopService.class
)
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

		User user = _userLocalService.getUser(userId);

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

	@Reference
	private UserLocalService _userLocalService;

}