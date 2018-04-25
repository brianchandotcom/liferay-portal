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

import com.liferay.commerce.discount.constants.CommerceDiscountActionKeys;
import com.liferay.commerce.discount.constants.CommerceDiscountConstants;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.base.CommerceDiscountServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;

import java.math.BigDecimal;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceDiscountServiceImpl
	extends CommerceDiscountServiceBaseImpl {

	@Override
	public CommerceDiscount addCommerceDiscount(
			String title, String target, String type, String typeSettings,
			boolean useCouponCode, String couponCode, String limitationType,
			int limitationTimes, int numberOfUse, boolean cumulative,
			boolean usePercentage, BigDecimal level1, BigDecimal level2,
			BigDecimal level3, BigDecimal maximumDiscountAmount, boolean active,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceDiscountActionKeys.ADD_COMMERCE_DISCOUNT);

		return commerceDiscountLocalService.addCommerceDiscount(
			title, target, type, typeSettings, useCouponCode, couponCode,
			limitationType, limitationTimes, numberOfUse, cumulative,
			usePercentage, level1, level2, level3, maximumDiscountAmount,
			active, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	@Override
	public void deleteCommerceDiscount(long commerceDiscountId)
		throws PortalException {

		_commerceDiscountResourcePermission.check(
			getPermissionChecker(), commerceDiscountId, ActionKeys.DELETE);

		commerceDiscountLocalService.deleteCommerceDiscount(commerceDiscountId);
	}

	@Override
	public CommerceDiscount updateCommerceDiscount(
			long commerceDiscountId, String title, String target, String type,
			String typeSettings, boolean useCouponCode, String couponCode,
			String limitationType, int limitationTimes, int numberOfUse,
			boolean cumulative, boolean usePercentage, BigDecimal level1,
			BigDecimal level2, BigDecimal level3,
			BigDecimal maximumDiscountAmount, boolean active,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		_commerceDiscountResourcePermission.check(
			getPermissionChecker(), commerceDiscountId, ActionKeys.UPDATE);

		return commerceDiscountLocalService.updateCommerceDiscount(
			commerceDiscountId, title, target, type, typeSettings,
			useCouponCode, couponCode, limitationType, limitationTimes,
			numberOfUse, cumulative, usePercentage, level1, level2, level3,
			maximumDiscountAmount, active, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	private static volatile ModelResourcePermission<CommerceDiscount>
		_commerceDiscountResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceDiscountServiceImpl.class,
				"_commerceDiscountResourcePermission", CommerceDiscount.class);
	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				CommerceDiscountServiceImpl.class, "_portletResourcePermission",
				CommerceDiscountConstants.RESOURCE_NAME);

}