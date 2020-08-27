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

package com.liferay.commerce.order.content.web.internal.frontend.util;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.content.web.internal.frontend.CommerceOrderDataSetConstants;
import com.liferay.commerce.order.content.web.internal.model.Order;
import com.liferay.commerce.pricing.constants.CommercePricingConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletQName;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.text.DateFormat;
import java.text.Format;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderClayTableUtil {

	public static String getEditOrderURL(
			long commerceOrderId, HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		HttpServletRequest originalHttpServletRequest =
			PortalUtil.getOriginalServletRequest(httpServletRequest);

		PortletURL portletURL = PortletURLFactoryUtil.create(
			originalHttpServletRequest, portletDisplay.getId(),
			themeDisplay.getPlid(), PortletRequest.ACTION_PHASE);

		portletURL.setParameter(ActionRequest.ACTION_NAME, "editCommerceOrder");
		portletURL.setParameter(Constants.CMD, "setCurrent");
		portletURL.setParameter(
			"commerceOrderId", String.valueOf(commerceOrderId));

		String redirect = ParamUtil.getString(
			httpServletRequest, "currentUrl",
			PortalUtil.getCurrentURL(httpServletRequest));

		portletURL.setParameter("redirect", redirect);

		return portletURL.toString();
	}

	public static List<Order> getOrders(
			List<CommerceOrder> commerceOrders, ThemeDisplay themeDisplay,
			String priceDisplayType)
		throws PortalException {

		List<Order> orders = new ArrayList<>();

		for (CommerceOrder commerceOrder : commerceOrders) {
			String amount = StringPool.BLANK;

			CommerceMoney totalMoney = commerceOrder.getTotalMoney();

			if (priceDisplayType.equals(
					CommercePricingConstants.TAX_INCLUDED_IN_PRICE)) {

				totalMoney = commerceOrder.getTotalWithTaxAmountMoney();
			}

			if (totalMoney != null) {
				amount = totalMoney.format(themeDisplay.getLocale());
			}

			Format dateFormat = FastDateFormatFactoryUtil.getDate(
				DateFormat.MEDIUM, themeDisplay.getLocale(),
				themeDisplay.getTimeZone());

			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", themeDisplay.getLocale(),
				CommerceOrderClayTableUtil.class);

			String commerceOrderStatusLabel = LanguageUtil.get(
				resourceBundle,
				CommerceOrderConstants.getOrderStatusLabel(
					commerceOrder.getOrderStatus()));

			String workflowStatusLabel = LanguageUtil.get(
				resourceBundle,
				WorkflowConstants.getStatusLabel(commerceOrder.getStatus()));

			Date orderDate = commerceOrder.getCreateDate();

			if (commerceOrder.getOrderDate() != null) {
				orderDate = commerceOrder.getOrderDate();
			}

			orders.add(
				new Order(
					commerceOrder.getCommerceOrderId(),
					commerceOrder.getCommerceAccountName(),
					dateFormat.format(orderDate), commerceOrder.getUserName(),
					commerceOrderStatusLabel, workflowStatusLabel, amount));
		}

		return orders;
	}

	public static String getOrderViewDetailURL(
			long commerceOrderId, ThemeDisplay themeDisplay)
		throws PortalException {

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletURL portletURL = PortletURLFactoryUtil.create(
			themeDisplay.getRequest(), portletDisplay.getId(),
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		PortletURL backURL = portletURL;

		String pageSize = ParamUtil.getString(
			themeDisplay.getRequest(), "pageSize");

		String pageNumber = ParamUtil.getString(
			themeDisplay.getRequest(), "page");

		backURL.setParameter("itemsPerPage", pageSize);
		backURL.setParameter("pageNumber", pageNumber);
		backURL.setParameter(
			"tableName",
			CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PLACED_ORDERS);

		portletURL.setParameter(
			PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE + "backURL",
			backURL.toString());

		portletURL.setParameter(
			"mvcRenderCommandName", "viewCommerceOrderDetails");
		portletURL.setParameter(
			"commerceOrderId", String.valueOf(commerceOrderId));

		return portletURL.toString();
	}

	public static String getViewShipmentURL(
		long commerceOrderItemId, ThemeDisplay themeDisplay) {

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletURL portletURL = PortletURLFactoryUtil.create(
			themeDisplay.getRequest(), portletDisplay.getId(),
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "viewCommerceOrderShipments");
		portletURL.setParameter(
			"commerceOrderItemId", String.valueOf(commerceOrderItemId));

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
			_log.error(windowStateException, windowStateException);
		}

		portletURL.setParameter("backURL", portletURL.toString());

		return portletURL.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderClayTableUtil.class);

}