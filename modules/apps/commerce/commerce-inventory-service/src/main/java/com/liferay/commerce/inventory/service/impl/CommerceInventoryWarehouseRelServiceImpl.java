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

package com.liferay.commerce.inventory.service.impl;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseRel;
import com.liferay.commerce.inventory.service.base.CommerceInventoryWarehouseRelServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Luca Pellizzon
 */
@Component(
	enabled = false,
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommerceInventoryWarehouseRel"
	},
	service = AopService.class
)
public class CommerceInventoryWarehouseRelServiceImpl
	extends CommerceInventoryWarehouseRelServiceBaseImpl {

	@Override
	public CommerceInventoryWarehouseRel addCommerceInventoryWarehouseRel(
			String className, long classPK, long commerceInventoryWarehouseId)
		throws PortalException {

		_commerceInventoryWarehouseModelResourcePermission.check(
			getPermissionChecker(), commerceInventoryWarehouseId,
			ActionKeys.UPDATE);

		return commerceInventoryWarehouseRelLocalService.
			addCommerceInventoryWarehouseRel(
				getUserId(), className, classPK, commerceInventoryWarehouseId);
	}

	@Override
	public void deleteCommerceInventoryWarehouseRel(
			long commerceInventoryWarehouseRelId)
		throws PortalException {

		CommerceInventoryWarehouseRel commerceInventoryWarehouseRel =
			commerceInventoryWarehouseRelLocalService.
				getCommerceInventoryWarehouseRel(
					commerceInventoryWarehouseRelId);

		_commerceInventoryWarehouseModelResourcePermission.check(
			getPermissionChecker(),
			commerceInventoryWarehouseRel.getCommerceInventoryWarehouseId(),
			ActionKeys.UPDATE);

		commerceInventoryWarehouseRelLocalService.
			deleteCommerceInventoryWarehouseRel(commerceInventoryWarehouseRel);
	}

	@Override
	public void deleteCommerceInventoryWarehouseRels(
			String className, long commerceInventoryWarehouseId)
		throws PortalException {

		_commerceInventoryWarehouseModelResourcePermission.check(
			getPermissionChecker(), commerceInventoryWarehouseId,
			ActionKeys.UPDATE);

		commerceInventoryWarehouseRelLocalService.
			deleteCommerceInventoryWarehouseRels(
				className, commerceInventoryWarehouseId);
	}

	@Override
	public void
			deleteCommerceInventoryWarehouseRelsByCommerceInventoryWarehouseId(
				long commerceInventoryWarehouseId)
		throws PortalException {

		_commerceInventoryWarehouseModelResourcePermission.check(
			getPermissionChecker(), commerceInventoryWarehouseId,
			ActionKeys.UPDATE);

		commerceInventoryWarehouseRelLocalService.
			deleteCommerceInventoryWarehouseRels(commerceInventoryWarehouseId);
	}

	@Override
	public CommerceInventoryWarehouseRel fetchCommerceInventoryWarehouseRel(
			String className, long classPK, long commerceInventoryWarehouseId)
		throws PortalException {

		_commerceInventoryWarehouseModelResourcePermission.check(
			getPermissionChecker(), commerceInventoryWarehouseId,
			ActionKeys.VIEW);

		return commerceInventoryWarehouseRelLocalService.
			fetchCommerceInventoryWarehouseRel(
				className, classPK, commerceInventoryWarehouseId);
	}

	@Override
	public CommerceInventoryWarehouseRel getCommerceInventoryWarehouseRel(
			long commerceInventoryWarehouseRelId)
		throws PortalException {

		CommerceInventoryWarehouseRel commerceInventoryWarehouseRel =
			commerceInventoryWarehouseRelLocalService.
				getCommerceInventoryWarehouseRel(
					commerceInventoryWarehouseRelId);

		_commerceInventoryWarehouseModelResourcePermission.check(
			getPermissionChecker(),
			commerceInventoryWarehouseRel.getCommerceInventoryWarehouseId(),
			ActionKeys.VIEW);

		return commerceInventoryWarehouseRel;
	}

	@Override
	public List<CommerceInventoryWarehouseRel>
			getCommerceInventoryWarehouseRels(long commerceInventoryWarehouseId)
		throws PortalException {

		_commerceInventoryWarehouseModelResourcePermission.check(
			getPermissionChecker(), commerceInventoryWarehouseId,
			ActionKeys.VIEW);

		return commerceInventoryWarehouseRelLocalService.
			getCommerceInventoryWarehouseRels(commerceInventoryWarehouseId);
	}

	@Override
	public List<CommerceInventoryWarehouseRel>
			getCommerceInventoryWarehouseRels(
				long commerceInventoryWarehouseId, int start, int end,
				OrderByComparator<CommerceInventoryWarehouseRel>
					orderByComparator)
		throws PortalException {

		_commerceInventoryWarehouseModelResourcePermission.check(
			getPermissionChecker(), commerceInventoryWarehouseId,
			ActionKeys.VIEW);

		return commerceInventoryWarehouseRelLocalService.
			getCommerceInventoryWarehouseRels(
				commerceInventoryWarehouseId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceInventoryWarehouseRelsCount(
			long commerceInventoryWarehouseId)
		throws PortalException {

		_commerceInventoryWarehouseModelResourcePermission.check(
			getPermissionChecker(), commerceInventoryWarehouseId,
			ActionKeys.VIEW);

		return commerceInventoryWarehouseRelLocalService.
			getCommerceInventoryWarehouseRelsCount(
				commerceInventoryWarehouseId);
	}

	@Override
	public List<CommerceInventoryWarehouseRel>
			getCommerceOrderTypeCommerceInventoryWarehouseRels(
				long commerceInventoryWarehouseId, String keywords, int start,
				int end)
		throws PortalException {

		_commerceInventoryWarehouseModelResourcePermission.check(
			getPermissionChecker(), commerceInventoryWarehouseId,
			ActionKeys.VIEW);

		return commerceInventoryWarehouseRelLocalService.
			getCommerceOrderTypeCommerceInventoryWarehouseRels(
				commerceInventoryWarehouseId, keywords, start, end);
	}

	@Override
	public int getCommerceOrderTypeCommerceInventoryWarehouseRelsCount(
			long commerceInventoryWarehouseId, String keywords)
		throws PortalException {

		_commerceInventoryWarehouseModelResourcePermission.check(
			getPermissionChecker(), commerceInventoryWarehouseId,
			ActionKeys.VIEW);

		return commerceInventoryWarehouseRelLocalService.
			getCommerceOrderTypeCommerceInventoryWarehouseRelsCount(
				commerceInventoryWarehouseId, keywords);
	}

	private static volatile ModelResourcePermission<CommerceInventoryWarehouse>
		_commerceInventoryWarehouseModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceInventoryWarehouseRelServiceImpl.class,
				"_commerceInventoryWarehouseModelResourcePermission",
				CommerceInventoryWarehouse.class);

}