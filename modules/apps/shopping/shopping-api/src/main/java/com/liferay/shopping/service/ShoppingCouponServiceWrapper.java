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

package com.liferay.shopping.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ShoppingCouponService}.
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingCouponService
 * @generated
 */
@ProviderType
public class ShoppingCouponServiceWrapper implements ShoppingCouponService,
	ServiceWrapper<ShoppingCouponService> {
	public ShoppingCouponServiceWrapper(
		ShoppingCouponService shoppingCouponService) {
		_shoppingCouponService = shoppingCouponService;
	}

	@Override
	public com.liferay.shopping.model.ShoppingCoupon addCoupon(
		java.lang.String code, boolean autoCode, java.lang.String name,
		java.lang.String description, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int endDateMonth, int endDateDay, int endDateYear, int endDateHour,
		int endDateMinute, boolean neverExpire, boolean active,
		java.lang.String limitCategories, java.lang.String limitSkus,
		double minOrder, double discount, java.lang.String discountType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingCouponService.addCoupon(code, autoCode, name,
			description, startDateMonth, startDateDay, startDateYear,
			startDateHour, startDateMinute, endDateMonth, endDateDay,
			endDateYear, endDateHour, endDateMinute, neverExpire, active,
			limitCategories, limitSkus, minOrder, discount, discountType,
			serviceContext);
	}

	@Override
	public void deleteCoupon(long groupId, long couponId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_shoppingCouponService.deleteCoupon(groupId, couponId);
	}

	@Override
	public com.liferay.shopping.model.ShoppingCoupon getCoupon(long groupId,
		long couponId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingCouponService.getCoupon(groupId, couponId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _shoppingCouponService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.shopping.model.ShoppingCoupon> search(
		long groupId, long companyId, java.lang.String code, boolean active,
		java.lang.String discountType, boolean andOperator, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingCouponService.search(groupId, companyId, code, active,
			discountType, andOperator, start, end);
	}

	@Override
	public com.liferay.shopping.model.ShoppingCoupon updateCoupon(
		long couponId, java.lang.String name, java.lang.String description,
		int startDateMonth, int startDateDay, int startDateYear,
		int startDateHour, int startDateMinute, int endDateMonth,
		int endDateDay, int endDateYear, int endDateHour, int endDateMinute,
		boolean neverExpire, boolean active, java.lang.String limitCategories,
		java.lang.String limitSkus, double minOrder, double discount,
		java.lang.String discountType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingCouponService.updateCoupon(couponId, name, description,
			startDateMonth, startDateDay, startDateYear, startDateHour,
			startDateMinute, endDateMonth, endDateDay, endDateYear,
			endDateHour, endDateMinute, neverExpire, active, limitCategories,
			limitSkus, minOrder, discount, discountType, serviceContext);
	}

	@Override
	public ShoppingCouponService getWrappedService() {
		return _shoppingCouponService;
	}

	@Override
	public void setWrappedService(ShoppingCouponService shoppingCouponService) {
		_shoppingCouponService = shoppingCouponService;
	}

	private ShoppingCouponService _shoppingCouponService;
}