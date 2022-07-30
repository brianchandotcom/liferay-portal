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

package com.liferay.commerce.tax.engine.fixed.service.impl;

import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel;
import com.liferay.commerce.tax.engine.fixed.service.base.CommerceTaxFixedRateAddressRelLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel",
	service = AopService.class
)
public class CommerceTaxFixedRateAddressRelLocalServiceImpl
	extends CommerceTaxFixedRateAddressRelLocalServiceBaseImpl {

	@Override
	public CommerceTaxFixedRateAddressRel addCommerceTaxFixedRateAddressRel(
			long userId, long groupId, long commerceTaxMethodId,
			long cpTaxCategoryId, long countryId, long regionId, String zip,
			double rate)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long commerceTaxFixedRateAddressRelId = counterLocalService.increment();

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			commerceTaxFixedRateAddressRelPersistence.create(
				commerceTaxFixedRateAddressRelId);

		commerceTaxFixedRateAddressRel.setGroupId(groupId);
		commerceTaxFixedRateAddressRel.setCompanyId(user.getCompanyId());
		commerceTaxFixedRateAddressRel.setUserId(user.getUserId());
		commerceTaxFixedRateAddressRel.setUserName(user.getFullName());
		commerceTaxFixedRateAddressRel.setCommerceTaxMethodId(
			commerceTaxMethodId);
		commerceTaxFixedRateAddressRel.setCPTaxCategoryId(cpTaxCategoryId);
		commerceTaxFixedRateAddressRel.setCountryId(countryId);
		commerceTaxFixedRateAddressRel.setRegionId(regionId);
		commerceTaxFixedRateAddressRel.setZip(zip);
		commerceTaxFixedRateAddressRel.setRate(rate);

		return commerceTaxFixedRateAddressRelPersistence.update(
			commerceTaxFixedRateAddressRel);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public CommerceTaxFixedRateAddressRel addCommerceTaxFixedRateAddressRel(
			long commerceTaxMethodId, long cpTaxCategoryId, long countryId,
			long regionId, String zip, double rate,
			ServiceContext serviceContext)
		throws PortalException {

		return commerceTaxFixedRateAddressRelLocalService.
			addCommerceTaxFixedRateAddressRel(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				commerceTaxMethodId, cpTaxCategoryId, countryId, regionId, zip,
				rate);
	}

	@Override
	public void deleteCommerceTaxFixedRateAddressRelsByCommerceTaxMethodId(
		long commerceTaxMethodId) {

		commerceTaxFixedRateAddressRelPersistence.removeByCommerceTaxMethodId(
			commerceTaxMethodId);
	}

	@Override
	public void deleteCommerceTaxFixedRateAddressRelsByCountryId(
		long countryId) {

		commerceTaxFixedRateAddressRelPersistence.removeByCountryId(countryId);
	}

	@Override
	public void deleteCommerceTaxFixedRateAddressRelsByCPTaxCategoryId(
		long cpTaxCategoryId) {

		commerceTaxFixedRateAddressRelPersistence.removeByCPTaxCategoryId(
			cpTaxCategoryId);
	}

	@Override
	public CommerceTaxFixedRateAddressRel fetchCommerceTaxFixedRateAddressRel(
		long commerceTaxMethodId, long cpTaxCategoryId, long countryId,
		long regionId, String zip) {

		return commerceTaxFixedRateAddressRelFinder.fetchByC_C_C_R_Z_First(
			commerceTaxMethodId, cpTaxCategoryId, countryId, regionId, zip);
	}

	@Override
	public CommerceTaxFixedRateAddressRel fetchCommerceTaxFixedRateAddressRel(
		long commerceTaxMethodId, long countryId, long regionId, String zip) {

		return commerceTaxFixedRateAddressRelFinder.fetchByC_C_R_Z_First(
			commerceTaxMethodId, countryId, regionId, zip);
	}

	@Override
	public List<CommerceTaxFixedRateAddressRel>
		getCommerceTaxFixedRateAddressRels(
			long cpTaxCategoryId, int start, int end) {

		return commerceTaxFixedRateAddressRelPersistence.findByCPTaxCategoryId(
			cpTaxCategoryId, start, end);
	}

	@Override
	public List<CommerceTaxFixedRateAddressRel>
		getCommerceTaxFixedRateAddressRels(
			long cpTaxCategoryId, int start, int end,
			OrderByComparator<CommerceTaxFixedRateAddressRel>
				orderByComparator) {

		return commerceTaxFixedRateAddressRelPersistence.findByCPTaxCategoryId(
			cpTaxCategoryId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceTaxFixedRateAddressRelsCount(long cpTaxCategoryId) {
		return commerceTaxFixedRateAddressRelPersistence.countByCPTaxCategoryId(
			cpTaxCategoryId);
	}

	@Override
	public List<CommerceTaxFixedRateAddressRel>
		getCommerceTaxMethodFixedRateAddressRels(
			long commerceTaxMethodId, int start, int end) {

		return commerceTaxFixedRateAddressRelPersistence.
			findByCommerceTaxMethodId(commerceTaxMethodId, start, end);
	}

	@Override
	public List<CommerceTaxFixedRateAddressRel>
		getCommerceTaxMethodFixedRateAddressRels(
			long commerceTaxMethodId, int start, int end,
			OrderByComparator<CommerceTaxFixedRateAddressRel>
				orderByComparator) {

		return commerceTaxFixedRateAddressRelPersistence.
			findByCommerceTaxMethodId(
				commerceTaxMethodId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceTaxMethodFixedRateAddressRelsCount(
		long commerceTaxMethodId) {

		return commerceTaxFixedRateAddressRelPersistence.
			countByCommerceTaxMethodId(commerceTaxMethodId);
	}

	@Override
	public CommerceTaxFixedRateAddressRel updateCommerceTaxFixedRateAddressRel(
			long commerceTaxFixedRateAddressRelId, long countryId,
			long regionId, String zip, double rate)
		throws PortalException {

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			commerceTaxFixedRateAddressRelPersistence.findByPrimaryKey(
				commerceTaxFixedRateAddressRelId);

		commerceTaxFixedRateAddressRel.setCountryId(countryId);
		commerceTaxFixedRateAddressRel.setRegionId(regionId);
		commerceTaxFixedRateAddressRel.setZip(zip);
		commerceTaxFixedRateAddressRel.setRate(rate);

		return commerceTaxFixedRateAddressRelPersistence.update(
			commerceTaxFixedRateAddressRel);
	}

}