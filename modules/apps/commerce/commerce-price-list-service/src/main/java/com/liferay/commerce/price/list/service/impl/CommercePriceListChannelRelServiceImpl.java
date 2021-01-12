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
import com.liferay.commerce.price.list.model.CommercePriceListChannelRel;
import com.liferay.commerce.price.list.service.base.CommercePriceListChannelRelServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Riccardo Alberti
 * @see CommercePriceListChannelRelServiceBaseImpl
 */
public class CommercePriceListChannelRelServiceImpl
	extends CommercePriceListChannelRelServiceBaseImpl {

	@Override
	public CommercePriceListChannelRel addCommercePriceListChannelRel(
			long commercePriceListId, long commerceChannelId, int order,
			ServiceContext serviceContext)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.UPDATE);

		return commercePriceListChannelRelLocalService.
			addCommercePriceListChannelRel(
				commercePriceListId, commerceChannelId, order, serviceContext);
	}

	@Override
	public void deleteCommercePriceListChannelRel(
			long commercePriceListChannelRelId)
		throws PortalException {

		CommercePriceListChannelRel commercePriceListChannelRel =
			commercePriceListChannelRelLocalService.
				getCommercePriceListChannelRel(commercePriceListChannelRelId);

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(),
			commercePriceListChannelRel.getCommercePriceListId(),
			ActionKeys.UPDATE);

		commercePriceListChannelRelLocalService.
			deleteCommercePriceListChannelRel(commercePriceListChannelRel);
	}

	@Override
	public void deleteCommercePriceListChannelRelsByCommercePriceListId(
			long commercePriceListId)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.UPDATE);

		commercePriceListChannelRelLocalService.
			deleteCommercePriceListChannelRels(commercePriceListId);
	}

	@Override
	public CommercePriceListChannelRel fetchCommercePriceListChannelRel(
			long commerceChannelId, long commercePriceListId)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.VIEW);

		return commercePriceListChannelRelLocalService.
			fetchCommercePriceListChannelRel(
				commerceChannelId, commercePriceListId);
	}

	@Override
	public CommercePriceListChannelRel getCommercePriceListChannelRel(
			long commercePriceListChannelRelId)
		throws PortalException {

		CommercePriceListChannelRel commercePriceListChannelRel =
			commercePriceListChannelRelLocalService.
				getCommercePriceListChannelRel(commercePriceListChannelRelId);

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(),
			commercePriceListChannelRel.getCommercePriceListId(),
			ActionKeys.VIEW);

		return commercePriceListChannelRel;
	}

	@Override
	public List<CommercePriceListChannelRel> getCommercePriceListChannelRels(
			long commercePriceListId)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.VIEW);

		return commercePriceListChannelRelLocalService.
			getCommercePriceListChannelRels(commercePriceListId);
	}

	@Override
	public List<CommercePriceListChannelRel> getCommercePriceListChannelRels(
			long commercePriceListId, int start, int end,
			OrderByComparator<CommercePriceListChannelRel> orderByComparator)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.VIEW);

		return commercePriceListChannelRelLocalService.
			getCommercePriceListChannelRels(
				commercePriceListId, start, end, orderByComparator);
	}

	@Override
	public List<CommercePriceListChannelRel> getCommercePriceListChannelRels(
		long commercePriceListId, String name, int start, int end) {

		return commercePriceListChannelRelFinder.findByCommercePriceListId(
			commercePriceListId, name, start, end, true);
	}

	@Override
	public int getCommercePriceListChannelRelsCount(long commercePriceListId)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.VIEW);

		return commercePriceListChannelRelLocalService.
			getCommercePriceListChannelRelsCount(commercePriceListId);
	}

	@Override
	public int getCommercePriceListChannelRelsCount(
		long commercePriceListId, String name) {

		return commercePriceListChannelRelFinder.countByCommercePriceListId(
			commercePriceListId, name, true);
	}

	private static volatile ModelResourcePermission<CommercePriceList>
		_commercePriceListModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommercePriceListChannelRelServiceImpl.class,
				"_commercePriceListModelResourcePermission",
				CommercePriceList.class);

}