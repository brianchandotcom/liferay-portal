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
import com.liferay.commerce.model.CommerceTaxCategory;
import com.liferay.commerce.service.permission.CommercePermission;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate;
import com.liferay.commerce.tax.engine.fixed.service.base.CommerceTaxFixedRateServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceTaxFixedRateServiceImpl
	extends CommerceTaxFixedRateServiceBaseImpl {

	@Override
	public CommerceTaxFixedRate addCommerceTaxFixedRate(
			long commerceTaxMethodId, long commerceTaxCategoryId, double rate,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_TAX_METHODS);

		return commerceTaxFixedRateLocalService.addCommerceTaxFixedRate(
			commerceTaxMethodId, commerceTaxCategoryId, rate, serviceContext);
	}

	@Override
	public void deleteCommerceTaxFixedRate(long commerceTaxFixedRateId)
		throws PortalException {

		CommerceTaxFixedRate commerceTaxFixedRate =
			commerceTaxFixedRateLocalService.getCommerceTaxFixedRate(
				commerceTaxFixedRateId);

		CommercePermission.check(
			getPermissionChecker(), commerceTaxFixedRate.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_TAX_METHODS);

		commerceTaxFixedRateLocalService.deleteCommerceTaxFixedRate(
			commerceTaxFixedRate);
	}

	@Override
	public CommerceTaxFixedRate fetchCommerceTaxFixedRate(
		long commerceTaxFixedRateId) {

		return commerceTaxFixedRateLocalService.fetchCommerceTaxFixedRate(
			commerceTaxFixedRateId);
	}

	@Override
	public List<CommerceTaxCategory> getAvailableCommerceTaxCategories(
			long groupId)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), groupId,
			CommerceActionKeys.MANAGE_COMMERCE_TAX_METHODS);
		return
			commerceTaxFixedRateLocalService.getAvailableCommerceTaxCategories(
				groupId);
	}

	@Override
	public List<CommerceTaxFixedRate> getCommerceTaxFixedRates(
		long commerceTaxMethodId, int start, int end) {

		return commerceTaxFixedRateLocalService.getCommerceTaxFixedRates(
			commerceTaxMethodId, start, end);
	}

	@Override
	public List<CommerceTaxFixedRate> getCommerceTaxFixedRates(
		long commerceTaxMethodId, int start, int end,
		OrderByComparator<CommerceTaxFixedRate> orderByComparator) {

		return commerceTaxFixedRateLocalService.getCommerceTaxFixedRates(
			commerceTaxMethodId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceTaxFixedRatesCount(long commerceTaxMethodId) {
		return commerceTaxFixedRateLocalService.getCommerceTaxFixedRatesCount(
			commerceTaxMethodId);
	}

	@Override
	public CommerceTaxFixedRate updateCommerceTaxFixedRate(
			long commerceTaxFixedRateId, double rate)
		throws PortalException {

		CommerceTaxFixedRate commerceTaxFixedRate =
			commerceTaxFixedRateLocalService.getCommerceTaxFixedRate(
				commerceTaxFixedRateId);

		CommercePermission.check(
			getPermissionChecker(), commerceTaxFixedRate.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_TAX_METHODS);

		return commerceTaxFixedRateLocalService.updateCommerceTaxFixedRate(
			commerceTaxFixedRateId, rate);
	}

}