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

package com.liferay.commerce.price.list.service.impl;

import com.liferay.commerce.price.list.constants.CommercePriceListActionKeys;
import com.liferay.commerce.price.list.model.CommercePriceListDiscountRel;
import com.liferay.commerce.price.list.service.base.CommercePriceListDiscountRelServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;

import java.util.List;

/**
 * @author Riccardo Alberti
 * @see CommercePriceListDiscountRelServiceBaseImpl
 */
public class CommercePriceListDiscountRelServiceImpl
	extends CommercePriceListDiscountRelServiceBaseImpl {

	@Override
	public CommercePriceListDiscountRel addCommercePriceListDiscountRel(
			long commercePriceListId, long commerceDiscountId, int order,
			ServiceContext serviceContext)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommercePriceListActionKeys.MANAGE_COMMERCE_PRICE_LISTS);

		return commercePriceListDiscountRelLocalService.
			addCommercePriceListDiscountRel(
				commercePriceListId, commerceDiscountId, order, serviceContext);
	}

	@Override
	public void deleteCommercePriceListDiscountRel(
			long commercePriceListDiscountRelId)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommercePriceListActionKeys.MANAGE_COMMERCE_PRICE_LISTS);

		commercePriceListDiscountRelLocalService.
			deleteCommercePriceListDiscountRel(commercePriceListDiscountRelId);
	}

	@Override
	public CommercePriceListDiscountRel fetchCommercePriceListDiscountRel(
			long commercePriceListId, long commerceDiscountId)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommercePriceListActionKeys.MANAGE_COMMERCE_PRICE_LISTS);

		return commercePriceListDiscountRelLocalService.
			fetchCommercePriceListDiscountRel(
				commercePriceListId, commerceDiscountId);
	}

	@Override
	public List<CommercePriceListDiscountRel> getCommercePriceListDiscountRels(
			long commercePriceListId)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommercePriceListActionKeys.MANAGE_COMMERCE_PRICE_LISTS);

		return commercePriceListDiscountRelLocalService.
			getCommercePriceListDiscountRels(commercePriceListId);
	}

}