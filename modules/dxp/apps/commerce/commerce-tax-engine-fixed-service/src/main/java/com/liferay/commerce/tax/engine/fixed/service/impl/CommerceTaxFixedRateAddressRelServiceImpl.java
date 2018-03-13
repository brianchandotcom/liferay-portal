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

import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.service.permission.CommercePermission;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel;
import com.liferay.commerce.tax.engine.fixed.service.base.CommerceTaxFixedRateAddressRelServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceTaxFixedRateAddressRelServiceImpl
	extends CommerceTaxFixedRateAddressRelServiceBaseImpl {

	@Override
	public CommerceTaxFixedRateAddressRel addCommerceTaxFixedRateAddressRel(
			long commerceTaxMethodId, long commerceTaxFixedRateId,
			long commerceCountryId, long commerceRegionId, String zip,
			double rate, ServiceContext serviceContext)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_TAX_METHODS);

		return commerceTaxFixedRateAddressRelLocalService.
			addCommerceTaxFixedRateAddressRel(
				commerceTaxMethodId, commerceTaxFixedRateId, commerceCountryId,
				commerceRegionId, zip, rate, serviceContext);
	}

	@Override
	public void deleteCommerceTaxFixedRateAddressRel(
			long commerceTaxFixedRateAddressRelId)
		throws PortalException {

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			commerceTaxFixedRateAddressRelLocalService.
				getCommerceTaxFixedRateAddressRel(
					commerceTaxFixedRateAddressRelId);

		CommercePermission.check(
			getPermissionChecker(), commerceTaxFixedRateAddressRel.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_TAX_METHODS);

		commerceTaxFixedRateAddressRelLocalService.
			deleteCommerceTaxFixedRateAddressRel(
				commerceTaxFixedRateAddressRel);
	}

	@Override
	public CommerceTaxFixedRateAddressRel fetchCommerceTaxFixedRateAddressRel(
		long commerceTaxFixedRateAddressRelId) {

		return commerceTaxFixedRateAddressRelLocalService.
			fetchCommerceTaxFixedRateAddressRel(
				commerceTaxFixedRateAddressRelId);
	}

	@Override
	public CommerceTaxFixedRateAddressRel fetchCommerceTaxFixedRateAddressRel(
		long commerceTaxMethodId, long commerceCountryId, long commerceRegionId,
		String zip) {

		return commerceTaxFixedRateAddressRelLocalService.
			fetchCommerceTaxFixedRateAddressRel(
				commerceTaxMethodId, commerceCountryId, commerceRegionId, zip);
	}

	@Override
	public List<CommerceTaxFixedRateAddressRel>
		getCommerceTaxFixedRateAddressRels(
			long commerceTaxFixedRateId, int start, int end) {

		return commerceTaxFixedRateAddressRelLocalService.
			getCommerceTaxFixedRateAddressRels(
				commerceTaxFixedRateId, start, end);
	}

	@Override
	public List<CommerceTaxFixedRateAddressRel>
		getCommerceTaxFixedRateAddressRels(
			long commerceTaxFixedRateId, int start, int end,
			OrderByComparator<CommerceTaxFixedRateAddressRel>
				orderByComparator) {

		return commerceTaxFixedRateAddressRelLocalService.
			getCommerceTaxFixedRateAddressRels(
				commerceTaxFixedRateId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceTaxFixedRateAddressRelsCount(
		long commerceTaxFixedRateId) {

		return commerceTaxFixedRateAddressRelLocalService.
			getCommerceTaxFixedRateAddressRelsCount(commerceTaxFixedRateId);
	}

	@Override
	public List<CommerceTaxFixedRateAddressRel>
		getCommerceTaxMethodFixedRateAddressRels(
			long commerceTaxMethodId, int start, int end) {

		return commerceTaxFixedRateAddressRelLocalService.
			getCommerceTaxMethodFixedRateAddressRels(
				commerceTaxMethodId, start, end);
	}

	@Override
	public List<CommerceTaxFixedRateAddressRel>
		getCommerceTaxMethodFixedRateAddressRels(
			long commerceTaxMethodId, int start, int end,
			OrderByComparator<CommerceTaxFixedRateAddressRel>
				orderByComparator) {

		return commerceTaxFixedRateAddressRelLocalService.
			getCommerceTaxMethodFixedRateAddressRels(
				commerceTaxMethodId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceTaxMethodFixedRateAddressRelsCount(
		long commerceTaxMethodId) {

		return commerceTaxFixedRateAddressRelLocalService.
			getCommerceTaxMethodFixedRateAddressRelsCount(commerceTaxMethodId);
	}

	@Override
	public CommerceTaxFixedRateAddressRel updateCommerceTaxFixedRateAddressRel(
			long commerceTaxFixedRateAddressRelId, long commerceCountryId,
			long commerceRegionId, String zip, double rate)
		throws PortalException {

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			commerceTaxFixedRateAddressRelLocalService.
				getCommerceTaxFixedRateAddressRel(
					commerceTaxFixedRateAddressRelId);

		CommercePermission.check(
			getPermissionChecker(), commerceTaxFixedRateAddressRel.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_TAX_METHODS);

		return commerceTaxFixedRateAddressRelLocalService.
			updateCommerceTaxFixedRateAddressRel(
				commerceTaxFixedRateAddressRelId, commerceCountryId,
				commerceRegionId, zip, rate);
	}

}