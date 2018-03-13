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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceTaxFixedRateAddressRelLocalServiceImpl
	extends CommerceTaxFixedRateAddressRelLocalServiceBaseImpl {

	@Override
	public CommerceTaxFixedRateAddressRel addCommerceTaxFixedRateAddressRel(
			long commerceTaxMethodId, long commerceTaxFixedRateId,
			long commerceCountryId, long commerceRegionId, String zip,
			double rate, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

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
		commerceTaxFixedRateAddressRel.setCommerceTaxFixedRateId(
			commerceTaxFixedRateId);
		commerceTaxFixedRateAddressRel.setCommerceCountryId(commerceCountryId);
		commerceTaxFixedRateAddressRel.setCommerceRegionId(commerceRegionId);
		commerceTaxFixedRateAddressRel.setZip(zip);
		commerceTaxFixedRateAddressRel.setRate(rate);

		commerceTaxFixedRateAddressRelPersistence.update(
			commerceTaxFixedRateAddressRel);

		return commerceTaxFixedRateAddressRel;
	}

	@Override
	public void deleteCommerceTaxFixedRateAddressRels(
		long commerceTaxMethodId) {

		commerceTaxFixedRateAddressRelPersistence.removeByCommerceTaxMethodId(
			commerceTaxMethodId);
	}

	@Override
	public CommerceTaxFixedRateAddressRel fetchCommerceTaxFixedRateAddressRel(
		long commerceTaxMethodId, long commerceCountryId, long commerceRegionId,
		String zip) {

		return commerceTaxFixedRateAddressRelFinder.fetchByC_C_C_Z_First(
			commerceTaxMethodId, commerceCountryId, commerceRegionId, zip);
	}

	@Override
	public List<CommerceTaxFixedRateAddressRel>
		getCommerceTaxFixedRateAddressRels(
			long commerceTaxFixedRateId, int start, int end) {

		return commerceTaxFixedRateAddressRelPersistence.
			findBycommerceTaxFixedRateId(commerceTaxFixedRateId, start, end);
	}

	@Override
	public List<CommerceTaxFixedRateAddressRel>
		getCommerceTaxFixedRateAddressRels(
			long commerceTaxFixedRateId, int start, int end,
			OrderByComparator<CommerceTaxFixedRateAddressRel>
				orderByComparator) {

		return commerceTaxFixedRateAddressRelPersistence.
			findBycommerceTaxFixedRateId(
				commerceTaxFixedRateId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceTaxFixedRateAddressRelsCount(
		long commerceTaxFixedRateId) {

		return commerceTaxFixedRateAddressRelPersistence.
			countBycommerceTaxFixedRateId(commerceTaxFixedRateId);
	}

	@Override
	public List<CommerceTaxFixedRateAddressRel>
		getCommerceTaxMethodFixedRateAddressRels(
			long commerceTaxMethodId, int start, int end) {

		return
			commerceTaxFixedRateAddressRelPersistence.findByCommerceTaxMethodId(
				commerceTaxMethodId, start, end);
	}

	@Override
	public List<CommerceTaxFixedRateAddressRel>
		getCommerceTaxMethodFixedRateAddressRels(
			long commerceTaxMethodId, int start, int end,
			OrderByComparator<CommerceTaxFixedRateAddressRel>
				orderByComparator) {

		return
			commerceTaxFixedRateAddressRelPersistence.findByCommerceTaxMethodId(
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
			long commerceTaxFixedRateAddressRelId, long commerceCountryId,
			long commerceRegionId, String zip, double rate)
		throws PortalException {

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			commerceTaxFixedRateAddressRelPersistence.findByPrimaryKey(
				commerceTaxFixedRateAddressRelId);

		commerceTaxFixedRateAddressRel.setCommerceCountryId(commerceCountryId);
		commerceTaxFixedRateAddressRel.setCommerceRegionId(commerceRegionId);
		commerceTaxFixedRateAddressRel.setZip(zip);
		commerceTaxFixedRateAddressRel.setRate(rate);

		commerceTaxFixedRateAddressRelPersistence.update(
			commerceTaxFixedRateAddressRel);

		return commerceTaxFixedRateAddressRel;
	}

}