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
import com.liferay.commerce.price.list.model.CommercePriceListOrderTypeRel;
import com.liferay.commerce.price.list.service.base.CommercePriceListOrderTypeRelServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommercePriceListOrderTypeRelServiceImpl
	extends CommercePriceListOrderTypeRelServiceBaseImpl {

	@Override
	public CommercePriceListOrderTypeRel addCommercePriceListOrderTypeRel(
			long commercePriceListId, long commerceOrderTypeId, int priority,
			ServiceContext serviceContext)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.UPDATE);

		return commercePriceListOrderTypeRelLocalService.
			addCommercePriceListOrderTypeRel(
				getUserId(), commercePriceListId, commerceOrderTypeId, priority,
				serviceContext);
	}

	@Override
	public void deleteCommercePriceListOrderTypeRel(
			long commercePriceListOrderTypeRelId)
		throws PortalException {

		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel =
			commercePriceListOrderTypeRelLocalService.
				getCommercePriceListOrderTypeRel(
					commercePriceListOrderTypeRelId);

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(),
			commercePriceListOrderTypeRel.getCommercePriceListId(),
			ActionKeys.UPDATE);

		commercePriceListOrderTypeRelLocalService.
			deleteCommercePriceListOrderTypeRel(commercePriceListOrderTypeRel);
	}

	@Override
	public CommercePriceListOrderTypeRel fetchCommercePriceListOrderTypeRel(
			long commercePriceListId, long commerceOrderTypeId)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.VIEW);

		return commercePriceListOrderTypeRelLocalService.
			fetchCommercePriceListOrderTypeRel(
				commercePriceListId, commerceOrderTypeId);
	}

	@Override
	public CommercePriceListOrderTypeRel getCommercePriceListOrderTypeRel(
			long commercePriceListOrderTypeRelId)
		throws PortalException {

		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel =
			commercePriceListOrderTypeRelLocalService.
				getCommercePriceListOrderTypeRel(
					commercePriceListOrderTypeRelId);

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(),
			commercePriceListOrderTypeRel.getCommercePriceListId(),
			ActionKeys.VIEW);

		return commercePriceListOrderTypeRel;
	}

	@Override
	public List<CommercePriceListOrderTypeRel>
			getCommercePriceListOrderTypeRels(
				long commercePriceListId, String name, int start, int end,
				OrderByComparator<CommercePriceListOrderTypeRel>
					orderByComparator)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.VIEW);

		return commercePriceListOrderTypeRelLocalService.
			getCommercePriceListOrderTypeRels(
				commercePriceListId, name, start, end, orderByComparator);
	}

	@Override
	public int getCommercePriceListOrderTypeRelsCount(
			long commercePriceListId, String name)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.VIEW);

		return commercePriceListOrderTypeRelLocalService.
			getCommercePriceListOrderTypeRelsCount(commercePriceListId, name);
	}

	private static volatile ModelResourcePermission<CommercePriceList>
		_commercePriceListModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommercePriceListOrderTypeRelServiceImpl.class,
				"_commercePriceListModelResourcePermission",
				CommercePriceList.class);

}