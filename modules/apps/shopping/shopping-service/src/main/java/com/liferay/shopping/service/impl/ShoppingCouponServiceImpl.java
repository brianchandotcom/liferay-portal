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

package com.liferay.shopping.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.shopping.model.ShoppingCoupon;
import com.liferay.shopping.service.base.ShoppingCouponServiceBaseImpl;
import com.liferay.shopping.service.permission.ShoppingPermission;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ShoppingCouponServiceImpl extends ShoppingCouponServiceBaseImpl {

	@Override
	public ShoppingCoupon addCoupon(
			String code, boolean autoCode, String name, String description,
			int startDateMonth, int startDateDay, int startDateYear,
			int startDateHour, int startDateMinute, int endDateMonth,
			int endDateDay, int endDateYear, int endDateHour, int endDateMinute,
			boolean neverExpire, boolean active, String limitCategories,
			String limitSkus, double minOrder, double discount,
			String discountType, ServiceContext serviceContext)
		throws PortalException {

		ShoppingPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.MANAGE_COUPONS);

		return shoppingCouponLocalService.addCoupon(
			getUserId(), code, autoCode, name, description, startDateMonth,
			startDateDay, startDateYear, startDateHour, startDateMinute,
			endDateMonth, endDateDay, endDateYear, endDateHour, endDateMinute,
			neverExpire, active, limitCategories, limitSkus, minOrder, discount,
			discountType, serviceContext);
	}

	@Override
	public void deleteCoupon(long groupId, long couponId)
		throws PortalException {

		ShoppingPermission.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_COUPONS);

		shoppingCouponLocalService.deleteCoupon(couponId);
	}

	@Override
	public ShoppingCoupon getCoupon(long groupId, long couponId)
		throws PortalException {

		ShoppingPermission.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_COUPONS);

		return shoppingCouponLocalService.getCoupon(couponId);
	}

	@Override
	public List<ShoppingCoupon> search(
			long groupId, long companyId, String code, boolean active,
			String discountType, boolean andOperator, int start, int end)
		throws PortalException {

		ShoppingPermission.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_COUPONS);

		return shoppingCouponLocalService.search(
			groupId, companyId, code, active, discountType, andOperator, start,
			end);
	}

	@Override
	public ShoppingCoupon updateCoupon(
			long couponId, String name, String description, int startDateMonth,
			int startDateDay, int startDateYear, int startDateHour,
			int startDateMinute, int endDateMonth, int endDateDay,
			int endDateYear, int endDateHour, int endDateMinute,
			boolean neverExpire, boolean active, String limitCategories,
			String limitSkus, double minOrder, double discount,
			String discountType, ServiceContext serviceContext)
		throws PortalException {

		ShoppingPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.MANAGE_COUPONS);

		return shoppingCouponLocalService.updateCoupon(
			getUserId(), couponId, name, description, startDateMonth,
			startDateDay, startDateYear, startDateHour, startDateMinute,
			endDateMonth, endDateDay, endDateYear, endDateHour, endDateMinute,
			neverExpire, active, limitCategories, limitSkus, minOrder, discount,
			discountType, serviceContext);
	}

}