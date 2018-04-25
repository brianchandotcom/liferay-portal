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

package com.liferay.commerce.discount.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceDiscountService}.
 *
 * @author Marco Leo
 * @see CommerceDiscountService
 * @generated
 */
@ProviderType
public class CommerceDiscountServiceWrapper implements CommerceDiscountService,
	ServiceWrapper<CommerceDiscountService> {
	public CommerceDiscountServiceWrapper(
		CommerceDiscountService commerceDiscountService) {
		_commerceDiscountService = commerceDiscountService;
	}

	@Override
	public com.liferay.commerce.discount.model.CommerceDiscount addCommerceDiscount(
		java.lang.String title, java.lang.String target, java.lang.String type,
		java.lang.String typeSettings, boolean useCouponCode,
		java.lang.String couponCode, java.lang.String limitationType,
		int limitationTimes, int numberOfUse, boolean cumulative,
		boolean usePercentage, java.math.BigDecimal level1,
		java.math.BigDecimal level2, java.math.BigDecimal level3,
		java.math.BigDecimal maximumDiscountAmount, boolean active,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountService.addCommerceDiscount(title, target,
			type, typeSettings, useCouponCode, couponCode, limitationType,
			limitationTimes, numberOfUse, cumulative, usePercentage, level1,
			level2, level3, maximumDiscountAmount, active, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	@Override
	public void deleteCommerceDiscount(long commerceDiscountId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceDiscountService.deleteCommerceDiscount(commerceDiscountId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceDiscountService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.discount.model.CommerceDiscount updateCommerceDiscount(
		long commerceDiscountId, java.lang.String title,
		java.lang.String target, java.lang.String type,
		java.lang.String typeSettings, boolean useCouponCode,
		java.lang.String couponCode, java.lang.String limitationType,
		int limitationTimes, int numberOfUse, boolean cumulative,
		boolean usePercentage, java.math.BigDecimal level1,
		java.math.BigDecimal level2, java.math.BigDecimal level3,
		java.math.BigDecimal maximumDiscountAmount, boolean active,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountService.updateCommerceDiscount(commerceDiscountId,
			title, target, type, typeSettings, useCouponCode, couponCode,
			limitationType, limitationTimes, numberOfUse, cumulative,
			usePercentage, level1, level2, level3, maximumDiscountAmount,
			active, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	@Override
	public CommerceDiscountService getWrappedService() {
		return _commerceDiscountService;
	}

	@Override
	public void setWrappedService(
		CommerceDiscountService commerceDiscountService) {
		_commerceDiscountService = commerceDiscountService;
	}

	private CommerceDiscountService _commerceDiscountService;
}