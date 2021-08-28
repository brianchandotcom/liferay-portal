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

package com.liferay.commerce.discount.service.impl;

import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountOrderTypeRel;
import com.liferay.commerce.discount.service.base.CommerceDiscountOrderTypeRelServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Marco Leo
 */
public class CommerceDiscountOrderTypeRelServiceImpl
	extends CommerceDiscountOrderTypeRelServiceBaseImpl {

	@Override
	public CommerceDiscountOrderTypeRel addCommerceDiscountOrderTypeRel(
			long commerceDiscountId, long commerceOrderTypeId, int priority,
			ServiceContext serviceContext)
		throws PortalException {

		_commerceDiscountModelResourcePermission.check(
			getPermissionChecker(), commerceDiscountId, ActionKeys.UPDATE);

		return commerceDiscountOrderTypeRelLocalService.
			addCommerceDiscountOrderTypeRel(
				getUserId(), commerceDiscountId, commerceOrderTypeId, priority,
				serviceContext);
	}

	@Override
	public void deleteCommerceDiscountOrderTypeRel(
			long commerceDiscountOrderTypeRelId)
		throws PortalException {

		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel =
			commerceDiscountOrderTypeRelLocalService.
				getCommerceDiscountOrderTypeRel(commerceDiscountOrderTypeRelId);

		_commerceDiscountModelResourcePermission.check(
			getPermissionChecker(),
			commerceDiscountOrderTypeRel.getCommerceDiscountId(),
			ActionKeys.UPDATE);

		commerceDiscountOrderTypeRelLocalService.
			deleteCommerceDiscountOrderTypeRel(commerceDiscountOrderTypeRel);
	}

	@Override
	public CommerceDiscountOrderTypeRel fetchCommerceDiscountOrderTypeRel(
			long commerceDiscountId, long commerceOrderTypeId)
		throws PortalException {

		_commerceDiscountModelResourcePermission.check(
			getPermissionChecker(), commerceDiscountId, ActionKeys.VIEW);

		return commerceDiscountOrderTypeRelLocalService.
			fetchCommerceDiscountOrderTypeRel(
				commerceDiscountId, commerceOrderTypeId);
	}

	@Override
	public CommerceDiscountOrderTypeRel getCommerceDiscountOrderTypeRel(
			long commerceDiscountOrderTypeRelId)
		throws PortalException {

		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel =
			commerceDiscountOrderTypeRelLocalService.
				getCommerceDiscountOrderTypeRel(commerceDiscountOrderTypeRelId);

		_commerceDiscountModelResourcePermission.check(
			getPermissionChecker(),
			commerceDiscountOrderTypeRel.getCommerceDiscountId(),
			ActionKeys.VIEW);

		return commerceDiscountOrderTypeRel;
	}

	@Override
	public List<CommerceDiscountOrderTypeRel> getCommerceDiscountOrderTypeRels(
			long commerceDiscountId, String name, int start, int end,
			OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator)
		throws PortalException {

		_commerceDiscountModelResourcePermission.check(
			getPermissionChecker(), commerceDiscountId, ActionKeys.VIEW);

		return commerceDiscountOrderTypeRelLocalService.
			getCommerceDiscountOrderTypeRels(
				commerceDiscountId, name, start, end, orderByComparator);
	}

	@Override
	public int getCommerceDiscountOrderTypeRelsCount(
			long commerceDiscountId, String name)
		throws PortalException {

		_commerceDiscountModelResourcePermission.check(
			getPermissionChecker(), commerceDiscountId, ActionKeys.VIEW);

		return commerceDiscountOrderTypeRelLocalService.
			getCommerceDiscountOrderTypeRelsCount(commerceDiscountId, name);
	}

	private static volatile ModelResourcePermission<CommerceDiscount>
		_commerceDiscountModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceDiscountOrderTypeRelServiceImpl.class,
				"_commerceDiscountModelResourcePermission",
				CommerceDiscount.class);

}