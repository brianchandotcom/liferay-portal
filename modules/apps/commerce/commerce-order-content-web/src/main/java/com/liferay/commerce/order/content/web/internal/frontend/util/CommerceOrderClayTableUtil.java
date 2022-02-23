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
import com.liferay.commerce.order.content.web.internal.frontend.constants.CommerceOrderDataSetConstants;
import com.liferay.commerce.order.content.web.internal.importer.type.CSVCommerceOrderImporterTypeImpl;
import com.liferay.commerce.order.content.web.internal.importer.type.CommerceOrdersCommerceOrderImporterTypeImpl;
import com.liferay.commerce.order.content.web.internal.importer.type.CommerceWishListsCommerceOrderImporterTypeImpl;
import com.liferay.commerce.order.content.web.internal.model.Order;
import com.liferay.commerce.order.content.web.internal.model.WishList;
import com.liferay.commerce.pricing.constants.CommercePricingConstants;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
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

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderClayTableUtil {

	public static String getCSVCommerceOrderPreviewURL(
		long fileEntryId, HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				PortalUtil.getOriginalServletRequest(httpServletRequest),
				portletDisplay.getId(), themeDisplay.getPlid(),
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/commerce_open_order_content/view_commerce_order_importer_type"
		).setParameter(
			"commerceOrderId",
			ParamUtil.getLong(httpServletRequest, "commerceOrderId")
		).setParameter(
			"commerceOrderImporterTypeKey", CSVCommerceOrderImporterTypeImpl.KEY
		).setParameter(
			"fileEntryId", fileEntryId
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	public static String getEditOrderURL(
			long commerceOrderId, HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				PortalUtil.getOriginalServletRequest(httpServletRequest),
				portletDisplay.getId(), themeDisplay.getPlid(),
				PortletRequest.ACTION_PHASE)
		).setActionName(
			"/commerce_open_order_content/edit_commerce_order"
		).setCMD(
			"setCurrent"
		).setRedirect(
			ParamUtil.getString(
				httpServletRequest, "currentUrl",
				PortalUtil.getCurrentURL(httpServletRequest))
		).setParameter(
			"commerceOrderId", commerceOrderId
		).buildString();
	}

	public static String getOrderCommerceOrderPreviewURL(
		Order order, HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				PortalUtil.getOriginalServletRequest(httpServletRequest),
				portletDisplay.getId(), themeDisplay.getPlid(),
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/commerce_open_order_content/view_commerce_order_importer_type"
		).setParameter(
			"commerceOrderId",
			ParamUtil.getLong(httpServletRequest, "commerceOrderId")
		).setParameter(
			"commerceOrderImporterTypeKey",
			CommerceOrdersCommerceOrderImporterTypeImpl.KEY
		).setParameter(
			"selectedCommerceOrderId", order.getOrderId()
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	public static List<Order> getOrders(
			List<CommerceOrder> commerceOrders, ThemeDisplay themeDisplay,
			String priceDisplayType)
		throws PortalException {

		List<Order> orders = new ArrayList<>();

		for (CommerceOrder commerceOrder : commerceOrders) {
			String amount = StringPool.BLANK;

			CommerceMoney totalCommerceMoney = commerceOrder.getTotalMoney();

			if (priceDisplayType.equals(
					CommercePricingConstants.TAX_INCLUDED_IN_PRICE)) {

				totalCommerceMoney = commerceOrder.getTotalWithTaxAmountMoney();
			}

			if (totalCommerceMoney != null) {
				amount = totalCommerceMoney.format(themeDisplay.getLocale());
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
					commerceOrder.getExternalReferenceCode(),
					commerceOrder.getCommerceOrderId(),
					commerceOrder.getCommerceAccountName(),
					dateFormat.format(orderDate), commerceOrder.getUserName(),
					commerceOrderStatusLabel,
					commerceOrder.getPurchaseOrderNumber(), workflowStatusLabel,
					amount));
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

		portletURL.setParameter(
			PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE + "backURL",
			PortletURLBuilder.create(
				portletURL
			).setParameter(
				"itemsPerPage",
				ParamUtil.getString(themeDisplay.getRequest(), "pageSize")
			).setParameter(
				"pageNumber",
				ParamUtil.getString(themeDisplay.getRequest(), "page")
			).setParameter(
				"tableName",
				CommerceOrderDataSetConstants.
					COMMERCE_DATA_SET_KEY_PLACED_ORDERS
			).buildString());

		portletURL.setParameter(
			"mvcRenderCommandName",
			"/commerce_order_content/view_commerce_order_details");
		portletURL.setParameter(
			"commerceOrderId", String.valueOf(commerceOrderId));

		return portletURL.toString();
	}

	public static String getViewShipmentURL(
		long commerceOrderItemId, ThemeDisplay themeDisplay) {

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletURL portletURL = PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				themeDisplay.getRequest(), portletDisplay.getId(),
				themeDisplay.getPlid(), PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/commerce_order_content/view_commerce_order_shipments"
		).setParameter(
			"commerceOrderItemId", commerceOrderItemId
		).buildPortletURL();

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
			_log.error(windowStateException);
		}

		portletURL.setParameter("backURL", portletURL.toString());

		return portletURL.toString();
	}

	public static String getWishListCommerceOrderPreviewURL(
		WishList wishList, HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				PortalUtil.getOriginalServletRequest(httpServletRequest),
				portletDisplay.getId(), themeDisplay.getPlid(),
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/commerce_open_order_content/view_commerce_order_importer_type"
		).setParameter(
			"commerceOrderId",
			ParamUtil.getLong(httpServletRequest, "commerceOrderId")
		).setParameter(
			"commerceOrderImporterTypeKey",
			CommerceWishListsCommerceOrderImporterTypeImpl.KEY
		).setParameter(
			"commerceWishListId", wishList.getWishListId()
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderClayTableUtil.class);

}