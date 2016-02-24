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

package com.liferay.shopping.web.portlet.action;

import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.shopping.model.ShoppingCategory;
import com.liferay.shopping.model.ShoppingCategoryConstants;
import com.liferay.shopping.model.ShoppingCoupon;
import com.liferay.shopping.model.ShoppingItem;
import com.liferay.shopping.model.ShoppingOrder;
import com.liferay.shopping.service.ShoppingCategoryServiceUtil;
import com.liferay.shopping.service.ShoppingCouponServiceUtil;
import com.liferay.shopping.service.ShoppingItemServiceUtil;
import com.liferay.shopping.service.ShoppingOrderServiceUtil;
import com.liferay.shopping.service.permission.ShoppingPermission;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class ActionUtil {

	public static void getCategory(HttpServletRequest request)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long categoryId = ParamUtil.getLong(request, "categoryId");

		ShoppingCategory category = null;

		if ((categoryId > 0) &&
			(categoryId !=
				ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID)) {

			category = ShoppingCategoryServiceUtil.getCategory(categoryId);
		}
		else {
			ShoppingPermission.check(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), ActionKeys.VIEW);
		}

		request.setAttribute(WebKeys.SHOPPING_CATEGORY, category);
	}

	public static void getCategory(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getCategory(request);
	}

	public static void getCoupon(HttpServletRequest request) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long couponId = ParamUtil.getLong(request, "couponId");

		ShoppingCoupon coupon = null;

		if (couponId > 0) {
			coupon = ShoppingCouponServiceUtil.getCoupon(
				themeDisplay.getScopeGroupId(), couponId);
		}

		request.setAttribute(WebKeys.SHOPPING_COUPON, coupon);
	}

	public static void getCoupon(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getCoupon(request);
	}

	public static void getItem(HttpServletRequest request) throws Exception {
		long itemId = ParamUtil.getLong(request, "itemId");

		ShoppingItem item = null;

		if (itemId > 0) {
			item = ShoppingItemServiceUtil.getItem(itemId);
		}

		request.setAttribute(WebKeys.SHOPPING_ITEM, item);
	}

	public static void getItem(PortletRequest portletRequest) throws Exception {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getItem(request);
	}

	public static void getOrder(HttpServletRequest request) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long orderId = ParamUtil.getLong(request, "orderId");

		ShoppingOrder order = null;

		if (orderId > 0) {
			order = ShoppingOrderServiceUtil.getOrder(
				themeDisplay.getScopeGroupId(), orderId);
		}

		request.setAttribute(WebKeys.SHOPPING_ORDER, order);
	}

	public static void getOrder(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getOrder(request);
	}

}