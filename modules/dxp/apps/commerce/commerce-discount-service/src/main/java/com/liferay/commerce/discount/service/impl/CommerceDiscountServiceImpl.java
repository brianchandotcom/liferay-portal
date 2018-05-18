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
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.math.BigDecimal;

import java.util.List;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceDiscountServiceImpl
	extends CommerceDiscountServiceBaseImpl {

	@Override
	public CommerceDiscount addCommerceDiscount(
			String title, String target, boolean useCouponCode,
			String couponCode, boolean usePercentage,
			BigDecimal maximumDiscountAmount, BigDecimal level1,
			BigDecimal level2, BigDecimal level3, String limitationType,
			int limitationTimes, boolean cumulative, boolean active,
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
			title, target, useCouponCode, couponCode, usePercentage,
			maximumDiscountAmount, level1, level2, level3, limitationType,
			limitationTimes, cumulative, active, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public void deleteCommerceDiscount(long commerceDiscountId)
		throws PortalException {

		_commerceDiscountResourcePermission.check(
			getPermissionChecker(), commerceDiscountId, ActionKeys.DELETE);

		commerceDiscountLocalService.deleteCommerceDiscount(commerceDiscountId);
	}

	@Override
	public CommerceDiscount getCommerceDiscount(long commerceDiscountId)
		throws PortalException {

		_commerceDiscountResourcePermission.check(
			getPermissionChecker(), commerceDiscountId, ActionKeys.VIEW);

		return commerceDiscountLocalService.getCommerceDiscount(
			commerceDiscountId);
	}

	@Override
	public List<CommerceDiscount> getCommerceDiscounts(
			long groupId, int start, int end,
			OrderByComparator<CommerceDiscount> orderByComparator)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			CommerceDiscountActionKeys.VIEW_COMMERCE_DISCOUNTS);

		return commerceDiscountLocalService.getCommerceDiscounts(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceDiscountsCount(long groupId) throws PortalException {
		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			CommerceDiscountActionKeys.VIEW_COMMERCE_DISCOUNTS);

		return commerceDiscountLocalService.getCommerceDiscountsCount(groupId);
	}

	@Override
	public BaseModelSearchResult<CommerceDiscount> searchCommerceDiscounts(
			long companyId, long groupId, String keywords, int status,
			int start, int end, Sort sort)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			CommerceDiscountActionKeys.VIEW_COMMERCE_DISCOUNTS);

		return commerceDiscountLocalService.searchCommerceDiscounts(
			companyId, groupId, keywords, status, start, end, sort);
	}

	@Override
	public CommerceDiscount updateCommerceDiscount(
			long commerceDiscountId, String title, String target,
			boolean useCouponCode, String couponCode, boolean usePercentage,
			BigDecimal maximumDiscountAmount, BigDecimal level1,
			BigDecimal level2, BigDecimal level3, String limitationType,
			int limitationTimes, boolean cumulative, boolean active,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		_commerceDiscountResourcePermission.check(
			getPermissionChecker(), commerceDiscountId, ActionKeys.UPDATE);

		return commerceDiscountLocalService.updateCommerceDiscount(
			commerceDiscountId, title, target, useCouponCode, couponCode,
			usePercentage, maximumDiscountAmount, level1, level2, level3,
			limitationType, limitationTimes, cumulative, active,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
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