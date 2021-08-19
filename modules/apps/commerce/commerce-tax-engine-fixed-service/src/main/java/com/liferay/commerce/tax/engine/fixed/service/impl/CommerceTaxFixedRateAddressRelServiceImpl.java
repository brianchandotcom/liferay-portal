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
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel;
import com.liferay.commerce.tax.engine.fixed.service.base.CommerceTaxFixedRateAddressRelServiceBaseImpl;
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
public class CommerceTaxFixedRateAddressRelServiceImpl
	extends CommerceTaxFixedRateAddressRelServiceBaseImpl {

	@Override
	public CommerceTaxFixedRateAddressRel addCommerceTaxFixedRateAddressRel(
			long groupId, long commerceTaxMethodId, long cpTaxCategoryId,
			long countryId, long regionId, String zip, double rate)
		throws PortalException {

		_checkCommerceChannel(groupId);

		return commerceTaxFixedRateAddressRelLocalService.
			addCommerceTaxFixedRateAddressRel(
				getUserId(), groupId, commerceTaxMethodId, cpTaxCategoryId,
				countryId, regionId, zip, rate);
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

		return commerceTaxFixedRateAddressRelService.
			addCommerceTaxFixedRateAddressRel(
				serviceContext.getScopeGroupId(), commerceTaxMethodId,
				cpTaxCategoryId, countryId, regionId, zip, rate);
	}

	@Override
	public void deleteCommerceTaxFixedRateAddressRel(
			long commerceTaxFixedRateAddressRelId)
		throws PortalException {

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			commerceTaxFixedRateAddressRelLocalService.
				getCommerceTaxFixedRateAddressRel(
					commerceTaxFixedRateAddressRelId);

		_checkCommerceChannel(commerceTaxFixedRateAddressRel.getGroupId());

		commerceTaxFixedRateAddressRelLocalService.
			deleteCommerceTaxFixedRateAddressRel(
				commerceTaxFixedRateAddressRel);
	}

	@Override
	public CommerceTaxFixedRateAddressRel fetchCommerceTaxFixedRateAddressRel(
			long commerceTaxFixedRateAddressRelId)
		throws PortalException {

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			commerceTaxFixedRateAddressRelLocalService.
				fetchCommerceTaxFixedRateAddressRel(
					commerceTaxFixedRateAddressRelId);

		if (commerceTaxFixedRateAddressRel != null) {
			_checkCommerceChannel(commerceTaxFixedRateAddressRel.getGroupId());
		}

		return commerceTaxFixedRateAddressRel;
	}

	@Override
	public List<CommerceTaxFixedRateAddressRel>
			getCommerceTaxMethodFixedRateAddressRels(
				long groupId, long commerceTaxMethodId, int start, int end,
				OrderByComparator<CommerceTaxFixedRateAddressRel>
					orderByComparator)
		throws PortalException {

		_checkCommerceChannel(groupId);

		return commerceTaxFixedRateAddressRelLocalService.
			getCommerceTaxMethodFixedRateAddressRels(
				commerceTaxMethodId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceTaxMethodFixedRateAddressRelsCount(
			long groupId, long commerceTaxMethodId)
		throws PortalException {

		_checkCommerceChannel(groupId);

		return commerceTaxFixedRateAddressRelLocalService.
			getCommerceTaxMethodFixedRateAddressRelsCount(commerceTaxMethodId);
	}

	@Override
	public CommerceTaxFixedRateAddressRel updateCommerceTaxFixedRateAddressRel(
			long commerceTaxFixedRateAddressRelId, long countryId,
			long regionId, String zip, double rate)
		throws PortalException {

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			commerceTaxFixedRateAddressRelLocalService.
				getCommerceTaxFixedRateAddressRel(
					commerceTaxFixedRateAddressRelId);

		_checkCommerceChannel(commerceTaxFixedRateAddressRel.getGroupId());

		return commerceTaxFixedRateAddressRelLocalService.
			updateCommerceTaxFixedRateAddressRel(
				commerceTaxFixedRateAddressRelId, countryId, regionId, zip,
				rate);
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
				CommerceTaxFixedRateAddressRelServiceImpl.class,
				"_commerceChannelModelResourcePermission",
				CommerceChannel.class);

	@ServiceReference(type = CommerceChannelLocalService.class)
	private CommerceChannelLocalService _commerceChannelLocalService;

}