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

import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommercePriceListDiscountRel;
import com.liferay.commerce.price.list.service.base.CommercePriceListDiscountRelServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

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

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.UPDATE);

		return commercePriceListDiscountRelLocalService.
			addCommercePriceListDiscountRel(
				getUserId(), commercePriceListId, commerceDiscountId, order,
				serviceContext);
	}

	@Override
	public void deleteCommercePriceListDiscountRel(
			long commercePriceListDiscountRelId)
		throws PortalException {

		CommercePriceListDiscountRel commercePriceListDiscountRel =
			commercePriceListDiscountRelLocalService.
				getCommercePriceListDiscountRel(commercePriceListDiscountRelId);

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(),
			commercePriceListDiscountRel.getCommercePriceListId(),
			ActionKeys.UPDATE);

		commercePriceListDiscountRelLocalService.
			deleteCommercePriceListDiscountRel(commercePriceListDiscountRel);
	}

	@Override
	public CommercePriceListDiscountRel fetchCommercePriceListDiscountRel(
			long commercePriceListId, long commerceDiscountId)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.VIEW);

		return commercePriceListDiscountRelLocalService.
			fetchCommercePriceListDiscountRel(
				commercePriceListId, commerceDiscountId);
	}

	@Override
	public CommercePriceListDiscountRel getCommercePriceListDiscountRel(
			long commercePriceListDiscountRelId)
		throws PortalException {

		CommercePriceListDiscountRel commercePriceListDiscountRel =
			commercePriceListDiscountRelLocalService.
				getCommercePriceListDiscountRel(commercePriceListDiscountRelId);

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(),
			commercePriceListDiscountRel.getCommercePriceListId(),
			ActionKeys.VIEW);

		return commercePriceListDiscountRel;
	}

	@Override
	public List<CommercePriceListDiscountRel> getCommercePriceListDiscountRels(
			long commercePriceListId)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.VIEW);

		return commercePriceListDiscountRelLocalService.
			getCommercePriceListDiscountRels(commercePriceListId);
	}

	@Override
	public List<CommercePriceListDiscountRel> getCommercePriceListDiscountRels(
			long commercePriceListId, int start, int end,
			OrderByComparator<CommercePriceListDiscountRel> orderByComparator)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.VIEW);

		return commercePriceListDiscountRelLocalService.
			getCommercePriceListDiscountRels(
				commercePriceListId, start, end, orderByComparator);
	}

	@Override
	public int getCommercePriceListDiscountRelsCount(long commercePriceListId)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.VIEW);

		return commercePriceListDiscountRelLocalService.
			getCommercePriceListDiscountRelsCount(commercePriceListId);
	}

	private static volatile ModelResourcePermission<CommercePriceList>
		_commercePriceListModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommercePriceListDiscountRelServiceImpl.class,
				"_commercePriceListModelResourcePermission",
				CommercePriceList.class);

}