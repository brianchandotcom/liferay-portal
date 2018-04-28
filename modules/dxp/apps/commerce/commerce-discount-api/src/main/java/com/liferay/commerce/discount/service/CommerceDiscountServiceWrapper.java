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
		String title, String target, boolean useCouponCode, String couponCode,
		boolean usePercentage, java.math.BigDecimal maximumDiscountAmount,
		java.math.BigDecimal level1, java.math.BigDecimal level2,
		java.math.BigDecimal level3, String limitationType,
		int limitationTimes, boolean cumulative, boolean active,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountService.addCommerceDiscount(title, target,
			useCouponCode, couponCode, usePercentage, maximumDiscountAmount,
			level1, level2, level3, limitationType, limitationTimes,
			cumulative, active, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public void deleteCommerceDiscount(long commerceDiscountId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceDiscountService.deleteCommerceDiscount(commerceDiscountId);
	}

	@Override
	public com.liferay.commerce.discount.model.CommerceDiscount getCommerceDiscount(
		long commerceDiscountId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountService.getCommerceDiscount(commerceDiscountId);
	}

	@Override
	public java.util.List<com.liferay.commerce.discount.model.CommerceDiscount> getCommerceDiscounts(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.discount.model.CommerceDiscount> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountService.getCommerceDiscounts(groupId, start,
			end, orderByComparator);
	}

	@Override
	public int getCommerceDiscountsCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountService.getCommerceDiscountsCount(groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceDiscountService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.discount.model.CommerceDiscount> searchCommerceDiscounts(
		long companyId, long groupId, String keywords, int status, int start,
		int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountService.searchCommerceDiscounts(companyId,
			groupId, keywords, status, start, end, sort);
	}

	@Override
	public com.liferay.commerce.discount.model.CommerceDiscount updateCommerceDiscount(
		long commerceDiscountId, String title, String target,
		boolean useCouponCode, String couponCode, boolean usePercentage,
		java.math.BigDecimal maximumDiscountAmount,
		java.math.BigDecimal level1, java.math.BigDecimal level2,
		java.math.BigDecimal level3, String limitationType,
		int limitationTimes, boolean cumulative, boolean active,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountService.updateCommerceDiscount(commerceDiscountId,
			title, target, useCouponCode, couponCode, usePercentage,
			maximumDiscountAmount, level1, level2, level3, limitationType,
			limitationTimes, cumulative, active, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
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