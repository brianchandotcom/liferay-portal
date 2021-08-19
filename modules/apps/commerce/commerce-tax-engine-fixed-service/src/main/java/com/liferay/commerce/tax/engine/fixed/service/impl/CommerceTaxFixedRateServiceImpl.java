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

import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate;
import com.liferay.commerce.tax.engine.fixed.service.base.CommerceTaxFixedRateServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceTaxFixedRateServiceImpl
	extends CommerceTaxFixedRateServiceBaseImpl {

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public CommerceTaxFixedRate addCommerceTaxFixedRate(
			long commerceTaxMethodId, long cpTaxCategoryId, double rate,
			ServiceContext serviceContext)
		throws PortalException {

		return commerceTaxFixedRateService.addCommerceTaxFixedRate(
			serviceContext.getScopeGroupId(), commerceTaxMethodId,
			cpTaxCategoryId, rate);
	}

	@Override
	public CommerceTaxFixedRate addCommerceTaxFixedRate(
			long groupId, long commerceTaxMethodId, long cpTaxCategoryId,
			double rate)
		throws PortalException {

		_checkCommerceChannel(groupId);

		return commerceTaxFixedRateLocalService.addCommerceTaxFixedRate(
			getUserId(), groupId, commerceTaxMethodId, cpTaxCategoryId, rate);
	}

	@Override
	public void deleteCommerceTaxFixedRate(long commerceTaxFixedRateId)
		throws PortalException {

		CommerceTaxFixedRate commerceTaxFixedRate =
			commerceTaxFixedRateLocalService.getCommerceTaxFixedRate(
				commerceTaxFixedRateId);

		_checkCommerceChannel(commerceTaxFixedRate.getGroupId());

		commerceTaxFixedRateLocalService.deleteCommerceTaxFixedRate(
			commerceTaxFixedRate);
	}

	@Override
	public CommerceTaxFixedRate fetchCommerceTaxFixedRate(
			long commerceTaxFixedRateId)
		throws PortalException {

		CommerceTaxFixedRate commerceTaxFixedRate =
			commerceTaxFixedRateLocalService.fetchCommerceTaxFixedRate(
				commerceTaxFixedRateId);

		if (commerceTaxFixedRate != null) {
			_checkCommerceChannel(commerceTaxFixedRate.getGroupId());
		}

		return commerceTaxFixedRate;
	}

	@Override
	public CommerceTaxFixedRate fetchCommerceTaxFixedRate(
			long cpTaxCategoryId, long commerceTaxMethodId)
		throws PortalException {

		CommerceTaxFixedRate commerceTaxFixedRate =
			commerceTaxFixedRateLocalService.fetchCommerceTaxFixedRate(
				cpTaxCategoryId, commerceTaxMethodId);

		if (commerceTaxFixedRate != null) {
			_checkCommerceChannel(commerceTaxFixedRate.getGroupId());
		}

		return commerceTaxFixedRate;
	}

	@Override
	public List<CommerceTaxFixedRate> getCommerceTaxFixedRates(
			long groupId, long commerceTaxMethodId, int start, int end,
			OrderByComparator<CommerceTaxFixedRate> orderByComparator)
		throws PortalException {

		_checkCommerceChannel(groupId);

		return commerceTaxFixedRateLocalService.getCommerceTaxFixedRates(
			commerceTaxMethodId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceTaxFixedRatesCount(
			long groupId, long commerceTaxMethodId)
		throws PortalException {

		_checkCommerceChannel(groupId);

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

		_checkCommerceChannel(commerceTaxFixedRate.getGroupId());

		return commerceTaxFixedRateLocalService.updateCommerceTaxFixedRate(
			commerceTaxFixedRateId, rate);
	}

	private void _checkCommerceChannel(long groupId) throws PortalException {
		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannelByGroupId(groupId);

		_commerceChannelModelResourcePermission.check(
			getPermissionChecker(), commerceChannel, ActionKeys.UPDATE);
	}

	private static volatile ModelResourcePermission<CommerceChannel>
		_commerceChannelModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceTaxFixedRateServiceImpl.class,
				"_commerceChannelModelResourcePermission",
				CommerceChannel.class);

	@ServiceReference(type = CommerceChannelLocalService.class)
	private CommerceChannelLocalService _commerceChannelLocalService;

}