/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.engine.fixed.service.impl;

import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel;
import com.liferay.commerce.shipping.engine.fixed.service.base.CommerceShippingFixedOptionRelServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.math.BigDecimal;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommerceShippingFixedOptionRel"
	},
	service = AopService.class
)
public class CommerceShippingFixedOptionRelServiceImpl
	extends CommerceShippingFixedOptionRelServiceBaseImpl {

	@Override
	public CommerceShippingFixedOptionRel addCommerceShippingFixedOptionRel(
			long groupId, long commerceShippingMethodId,
			long commerceShippingFixedOptionId,
			long commerceInventoryWarehouseId, long countryId, long regionId,
			String zip, double weightFrom, double weightTo,
			BigDecimal fixedPrice, BigDecimal rateUnitWeightPrice,
			double ratePercentage)
		throws PortalException {

		_checkCommerceChannel(groupId);

		return commerceShippingFixedOptionRelLocalService.
			addCommerceShippingFixedOptionRel(
				getUserId(), groupId, commerceShippingMethodId,
				commerceShippingFixedOptionId, commerceInventoryWarehouseId,
				countryId, regionId, zip, weightFrom, weightTo, fixedPrice,
				rateUnitWeightPrice, ratePercentage);
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

		return commerceShippingFixedOptionRelService.
			addCommerceShippingFixedOptionRel(
				serviceContext.getScopeGroupId(), commerceShippingMethodId,
				commerceShippingFixedOptionId, commerceInventoryWarehouseId,
				countryId, regionId, zip, weightFrom, weightTo, fixedPrice,
				rateUnitWeightPrice, ratePercentage);
	}

	@Override
	public void deleteCommerceShippingFixedOptionRel(
			long commerceShippingFixedOptionRelId)
		throws PortalException {

		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel =
			commerceShippingFixedOptionRelLocalService.
				getCommerceShippingFixedOptionRel(
					commerceShippingFixedOptionRelId);

		_checkCommerceChannel(commerceShippingFixedOptionRel.getGroupId());

		commerceShippingFixedOptionRelLocalService.
			deleteCommerceShippingFixedOptionRel(
				commerceShippingFixedOptionRel);
	}

	@Override
	public CommerceShippingFixedOptionRel fetchCommerceShippingFixedOptionRel(
			long commerceShippingFixedOptionRelId)
		throws PortalException {

		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel =
			commerceShippingFixedOptionRelLocalService.
				fetchCommerceShippingFixedOptionRel(
					commerceShippingFixedOptionRelId);

		if (commerceShippingFixedOptionRel != null) {
			_checkCommerceChannel(commerceShippingFixedOptionRel.getGroupId());
		}

		return commerceShippingFixedOptionRel;
	}

	@Override
	public List<CommerceShippingFixedOptionRel>
			getCommerceShippingMethodFixedOptionRels(
				long commerceShippingMethodId, int start, int end,
				OrderByComparator<CommerceShippingFixedOptionRel>
					orderByComparator)
		throws PortalException {

		CommerceShippingMethod commerceShippingMethod =
			_commerceShippingMethodService.getCommerceShippingMethod(
				commerceShippingMethodId);

		return commerceShippingFixedOptionRelLocalService.
			getCommerceShippingMethodFixedOptionRels(
				commerceShippingMethod.getCommerceShippingMethodId(), start,
				end, orderByComparator);
	}

	@Override
	public int getCommerceShippingMethodFixedOptionRelsCount(
			long commerceShippingMethodId)
		throws PortalException {

		CommerceShippingMethod commerceShippingMethod =
			_commerceShippingMethodService.getCommerceShippingMethod(
				commerceShippingMethodId);

		return commerceShippingFixedOptionRelLocalService.
			getCommerceShippingMethodFixedOptionRelsCount(
				commerceShippingMethod.getCommerceShippingMethodId());
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
			commerceShippingFixedOptionRelLocalService.
				getCommerceShippingFixedOptionRel(
					commerceShippingFixedOptionRelId);

		_checkCommerceChannel(commerceShippingFixedOptionRel.getGroupId());

		return commerceShippingFixedOptionRelLocalService.
			updateCommerceShippingFixedOptionRel(
				commerceShippingFixedOptionRelId, commerceInventoryWarehouseId,
				countryId, regionId, zip, weightFrom, weightTo, fixedPrice,
				rateUnitWeightPrice, ratePercentage);
	}

	private void _checkCommerceChannel(long groupId) throws PortalException {
		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannelByGroupId(groupId);

		_commerceChannelModelResourcePermission.check(
			getPermissionChecker(), commerceChannel, ActionKeys.UPDATE);
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CommerceChannel)"
	)
	private ModelResourcePermission<CommerceChannel>
		_commerceChannelModelResourcePermission;

	@Reference
	private CommerceShippingMethodService _commerceShippingMethodService;

}