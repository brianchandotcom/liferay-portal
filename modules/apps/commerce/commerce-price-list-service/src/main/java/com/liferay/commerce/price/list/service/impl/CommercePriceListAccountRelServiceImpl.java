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
import com.liferay.commerce.price.list.model.CommercePriceListAccountRel;
import com.liferay.commerce.price.list.service.base.CommercePriceListAccountRelServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Ethan Bustad
 * @author Alessio Antonio Rendina
 */
public class CommercePriceListAccountRelServiceImpl
	extends CommercePriceListAccountRelServiceBaseImpl {

	@Override
	public CommercePriceListAccountRel addCommercePriceListAccountRel(
			long commercePriceListId, long commerceAccountId, int order,
			ServiceContext serviceContext)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.UPDATE);

		return commercePriceListAccountRelLocalService.
			addCommercePriceListAccountRel(
				getUserId(), commercePriceListId, commerceAccountId, order,
				serviceContext);
	}

	@Override
	public void deleteCommercePriceListAccountRel(
			long commercePriceListAccountRelId)
		throws PortalException {

		CommercePriceListAccountRel commercePriceListAccountRel =
			commercePriceListAccountRelLocalService.
				getCommercePriceListAccountRel(commercePriceListAccountRelId);

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(),
			commercePriceListAccountRel.getCommercePriceListId(),
			ActionKeys.UPDATE);

		commercePriceListAccountRelLocalService.
			deleteCommercePriceListAccountRel(commercePriceListAccountRel);
	}

	@Override
	public void deleteCommercePriceListAccountRelsByCommercePriceListId(
			long commercePriceListId)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.UPDATE);

		commercePriceListAccountRelLocalService.
			deleteCommercePriceListAccountRelsByCommercePriceListId(
				commercePriceListId);
	}

	@Override
	public CommercePriceListAccountRel fetchCommercePriceListAccountRel(
			long commercePriceListId, long commerceAccountId)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.VIEW);

		return commercePriceListAccountRelLocalService.
			fetchCommercePriceListAccountRel(
				commercePriceListId, commerceAccountId);
	}

	@Override
	public CommercePriceListAccountRel getCommercePriceListAccountRel(
			long commercePriceListAccountRelId)
		throws PortalException {

		CommercePriceListAccountRel commercePriceListAccountRel =
			commercePriceListAccountRelLocalService.
				getCommercePriceListAccountRel(commercePriceListAccountRelId);

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(),
			commercePriceListAccountRel.getCommercePriceListId(),
			ActionKeys.VIEW);

		return commercePriceListAccountRel;
	}

	@Override
	public List<CommercePriceListAccountRel> getCommercePriceListAccountRels(
			long commercePriceListId)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.VIEW);

		return commercePriceListAccountRelLocalService.
			getCommercePriceListAccountRels(commercePriceListId);
	}

	@Override
	public List<CommercePriceListAccountRel> getCommercePriceListAccountRels(
			long commercePriceListId, int start, int end,
			OrderByComparator<CommercePriceListAccountRel> orderByComparator)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.VIEW);

		return commercePriceListAccountRelLocalService.
			getCommercePriceListAccountRels(
				commercePriceListId, start, end, orderByComparator);
	}

	@Override
	public List<CommercePriceListAccountRel> getCommercePriceListAccountRels(
		long commercePriceListId, String name, int start, int end) {

		return commercePriceListAccountRelFinder.findByCommercePriceListId(
			commercePriceListId, name, start, end, true);
	}

	@Override
	public int getCommercePriceListAccountRelsCount(long commercePriceListId)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.VIEW);

		return commercePriceListAccountRelLocalService.
			getCommercePriceListAccountRelsCount(commercePriceListId);
	}

	@Override
	public int getCommercePriceListAccountRelsCount(
		long commercePriceListId, String name) {

		return commercePriceListAccountRelFinder.countByCommercePriceListId(
			commercePriceListId, name, true);
	}

	private static volatile ModelResourcePermission<CommercePriceList>
		_commercePriceListModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommercePriceListAccountRelServiceImpl.class,
				"_commercePriceListModelResourcePermission",
				CommercePriceList.class);

}