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

package com.liferay.shopping.web.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.shopping.configuration.ShoppingGroupServiceOverriddenConfiguration;
import com.liferay.shopping.exception.NoSuchCartException;
import com.liferay.shopping.model.ShoppingCart;
import com.liferay.shopping.model.ShoppingCartItem;
import com.liferay.shopping.model.ShoppingCategory;
import com.liferay.shopping.model.ShoppingCoupon;
import com.liferay.shopping.model.ShoppingItem;
import com.liferay.shopping.model.ShoppingItemField;
import com.liferay.shopping.model.ShoppingOrder;
import com.liferay.shopping.model.ShoppingOrderConstants;
import com.liferay.shopping.service.ShoppingCartLocalServiceUtil;
import com.liferay.shopping.service.ShoppingCategoryLocalServiceUtil;

import java.text.NumberFormat;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Preston Crary
 */
public class ShoppingUtil extends com.liferay.shopping.util.ShoppingUtil {

	public static double calculateDiscountPercent(
			Map<ShoppingCartItem, Integer> items)
		throws PortalException {

		double discount = calculateDiscountSubtotal(
			items) / calculateSubtotal(items);

		if (Double.isNaN(discount) || Double.isInfinite(discount)) {
			discount = 0.0;
		}

		return discount;
	}

	public static double calculateDiscountPrice(ShoppingItem item) {
		return item.getPrice() * item.getDiscount();
	}

	public static double calculateTotal(
			Map<ShoppingCartItem, Integer> items, String stateId,
			ShoppingCoupon coupon, int altShipping, boolean insure)
		throws PortalException {

		double actualSubtotal = calculateActualSubtotal(items);
		double tax = calculateTax(items, stateId);
		double shipping = calculateAlternativeShipping(items, altShipping);

		double insurance = 0.0;

		if (insure) {
			insurance = calculateInsurance(items);
		}

		double couponDiscount = calculateCouponDiscount(items, stateId, coupon);

		double total =
			actualSubtotal + tax + shipping + insurance - couponDiscount;

		if (total < 0) {
			total = 0.0;
		}

		return total;
	}

	public static String getBreadcrumbs(
			long categoryId, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		ShoppingCategory category = null;

		try {
			category = ShoppingCategoryLocalServiceUtil.getCategory(categoryId);
		}
		catch (Exception e) {
		}

		return getBreadcrumbs(category, renderRequest, renderResponse);
	}

	public static String getBreadcrumbs(
			ShoppingCategory category, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		PortletURL categoriesURL = renderResponse.createRenderURL();

		WindowState windowState = renderRequest.getWindowState();

		if (windowState.equals(LiferayWindowState.POP_UP)) {
			categoriesURL.setParameter(
				"struts_action", "/shopping/select_category");
			categoriesURL.setWindowState(LiferayWindowState.POP_UP);
		}
		else {
			categoriesURL.setParameter("struts_action", "/shopping/view");
			categoriesURL.setParameter("tabs1", "categories");
			//categoriesURL.setWindowState(WindowState.MAXIMIZED);
		}

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		String categoriesLink = StringBundler.concat(
			"<a href=\"", categoriesURL.toString(), "\">",
			LanguageUtil.get(request, "categories"), "</a>");

		if (category == null) {
			return "<span class=\"first last\">" + categoriesLink + "</span>";
		}

		String breadcrumbs = StringPool.BLANK;

		if (category != null) {
			for (int i = 0;; i++) {
				category = category.toEscapedModel();

				PortletURL portletURL = renderResponse.createRenderURL();

				if (windowState.equals(LiferayWindowState.POP_UP)) {
					portletURL.setParameter(
						"struts_action", "/shopping/select_category");
					portletURL.setParameter(
						"categoryId", String.valueOf(category.getCategoryId()));
					portletURL.setWindowState(LiferayWindowState.POP_UP);
				}
				else {
					portletURL.setParameter("struts_action", "/shopping/view");
					portletURL.setParameter("tabs1", "categories");
					portletURL.setParameter(
						"categoryId", String.valueOf(category.getCategoryId()));
					//portletURL.setWindowState(WindowState.MAXIMIZED);
				}

				String categoryLink = StringBundler.concat(
					"<a href=\"", portletURL.toString(), "\">",
					category.getName(), "</a>");

				if (i == 0) {
					breadcrumbs =
						"<span class=\"last\">" + categoryLink + "</span>";
				}
				else {
					breadcrumbs = categoryLink + " &raquo; " + breadcrumbs;
				}

				if (category.isRoot()) {
					break;
				}

				category = ShoppingCategoryLocalServiceUtil.getCategory(
					category.getParentCategoryId());
			}
		}

		breadcrumbs = StringBundler.concat(
			"<span class=\"first\">", categoriesLink, " &raquo; </span>",
			breadcrumbs);

		return breadcrumbs;
	}

	public static ShoppingCart getCart(PortletRequest portletRequest)
		throws PortalException {

		PortletSession portletSession = portletRequest.getPortletSession();

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String sessionCartId =
			ShoppingCart.class.getName() + themeDisplay.getScopeGroupId();

		if (themeDisplay.isSignedIn()) {
			ShoppingCart cart = (ShoppingCart)portletSession.getAttribute(
				sessionCartId);

			if (cart != null) {
				portletSession.removeAttribute(sessionCartId);
			}

			if ((cart != null) && (cart.getItemsSize() > 0)) {
				cart = ShoppingCartLocalServiceUtil.updateCart(
					themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
					cart.getItemIds(), cart.getCouponCodes(),
					cart.getAltShipping(), cart.isInsure());
			}
			else {
				try {
					cart = ShoppingCartLocalServiceUtil.getCart(
						themeDisplay.getUserId(),
						themeDisplay.getScopeGroupId());
				}
				catch (NoSuchCartException nsce) {
					cart = _getCart(themeDisplay);

					cart = ShoppingCartLocalServiceUtil.updateCart(
						themeDisplay.getUserId(),
						themeDisplay.getScopeGroupId(), cart.getItemIds(),
						cart.getCouponCodes(), cart.getAltShipping(),
						cart.isInsure());
				}
			}

			return cart;
		}

		ShoppingCart cart = (ShoppingCart)portletSession.getAttribute(
			sessionCartId);

		if (cart == null) {
			cart = _getCart(themeDisplay);

			portletSession.setAttribute(sessionCartId, cart);
		}

		return cart;
	}

	public static Map<String, String> getEmailDefinitionTerms(
		PortletRequest portletRequest, String emailFromAddress,
		String emailFromName) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, String> definitionTerms = new LinkedHashMap<>();

		definitionTerms.put(
			"[$FROM_ADDRESS$]", HtmlUtil.escape(emailFromAddress));
		definitionTerms.put("[$FROM_NAME$]", HtmlUtil.escape(emailFromName));
		definitionTerms.put(
			"[$ORDER_BILLING_ADDRESS$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-order-billing-address"));
		definitionTerms.put(
			"[$ORDER_CURRENCY$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-order-currency"));
		definitionTerms.put(
			"[$ORDER_NUMBER$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-order-id"));
		definitionTerms.put(
			"[$ORDER_SHIPPING_ADDRESS$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-order-shipping-address"));
		definitionTerms.put(
			"[$ORDER_TOTAL$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-order-total"));

		Company company = themeDisplay.getCompany();

		definitionTerms.put("[$PORTAL_URL$]", company.getVirtualHostname());

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		definitionTerms.put(
			"[$PORTLET_NAME$]", HtmlUtil.escape(portletDisplay.getTitle()));

		definitionTerms.put(
			"[$TO_ADDRESS$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-address-of-the-email-recipient"));
		definitionTerms.put(
			"[$TO_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-name-of-the-email-recipient"));

		return definitionTerms;
	}

	public static String getPayPalNotifyURL(ThemeDisplay themeDisplay) {
		return themeDisplay.getPortalURL() + themeDisplay.getPathMain() +
			"/shopping/notify";
	}

	public static String getPayPalRedirectURL(
		ShoppingGroupServiceOverriddenConfiguration
			shoppingGroupServiceOverriddenConfiguration,
		ShoppingOrder order, double total, String returnURL, String notifyURL) {

		String payPalEmailAddress = URLCodec.encodeURL(
			shoppingGroupServiceOverriddenConfiguration.
				getPayPalEmailAddress());

		NumberFormat doubleFormat = NumberFormat.getNumberInstance(
			LocaleUtil.ENGLISH);

		doubleFormat.setMaximumFractionDigits(2);
		doubleFormat.setMinimumFractionDigits(2);

		String amount = doubleFormat.format(total);

		returnURL = URLCodec.encodeURL(returnURL);
		notifyURL = URLCodec.encodeURL(notifyURL);

		String firstName = URLCodec.encodeURL(order.getBillingFirstName());
		String lastName = URLCodec.encodeURL(order.getBillingLastName());
		String address1 = URLCodec.encodeURL(order.getBillingStreet());
		String city = URLCodec.encodeURL(order.getBillingCity());
		String state = URLCodec.encodeURL(order.getBillingState());
		String zip = URLCodec.encodeURL(order.getBillingZip());

		String currencyCode =
			shoppingGroupServiceOverriddenConfiguration.getCurrencyId();

		StringBundler sb = new StringBundler(29);

		sb.append("https://www.paypal.com/cgi-bin/webscr?cmd=_xclick&");
		sb.append("business=");
		sb.append(payPalEmailAddress);
		sb.append("&item_name=");
		sb.append(order.getNumber());
		sb.append("&item_number=");
		sb.append(order.getNumber());
		sb.append("&invoice=");
		sb.append(order.getNumber());
		sb.append("&amount=");
		sb.append(amount);
		sb.append("&return=");
		sb.append(returnURL);
		sb.append("&notify_url=");
		sb.append(notifyURL);
		sb.append("&first_name=");
		sb.append(firstName);
		sb.append("&last_name=");
		sb.append(lastName);
		sb.append("&address1=");
		sb.append(address1);
		sb.append("&city=");
		sb.append(city);
		sb.append("&state=");
		sb.append(state);
		sb.append("&zip=");
		sb.append(zip);
		sb.append("&no_note=1&currency_code=");
		sb.append(currencyCode);

		return sb.toString();
	}

	public static String getPayPalReturnURL(
		PortletURL portletURL, ShoppingOrder order) {

		portletURL.setParameter("struts_action", "/shopping/checkout");
		portletURL.setParameter(Constants.CMD, Constants.VIEW);
		portletURL.setParameter("orderId", String.valueOf(order.getOrderId()));

		return portletURL.toString();
	}

	public static String getPpPaymentStatus(
		ShoppingOrder order, HttpServletRequest request) {

		String ppPaymentStatus = order.getPpPaymentStatus();

		if (ppPaymentStatus.equals(ShoppingOrderConstants.STATUS_CHECKOUT)) {
			ppPaymentStatus = "checkout";
		}
		else {
			ppPaymentStatus = StringUtil.toLowerCase(ppPaymentStatus);
		}

		return LanguageUtil.get(request, HtmlUtil.escape(ppPaymentStatus));
	}

	public static String getPpPaymentStatus(String ppPaymentStatus) {
		if ((ppPaymentStatus == null) || (ppPaymentStatus.length() < 2) ||
			ppPaymentStatus.equals("checkout")) {

			return ShoppingOrderConstants.STATUS_CHECKOUT;
		}
		else {
			return Character.toUpperCase(ppPaymentStatus.charAt(0)) +
				ppPaymentStatus.substring(1);
		}
	}

	public static String hideCardNumber(String number) {
		if (number == null) {
			return null;
		}

		int numberLen = number.length();

		if (numberLen > 4) {
			StringBundler sb = new StringBundler(numberLen - 3);

			for (int i = 0; i < numberLen - 4; i++) {
				sb.append(StringPool.STAR);
			}

			sb.append(number.substring(numberLen - 4, numberLen));

			number = sb.toString();
		}

		return number;
	}

	public static boolean isInStock(ShoppingItem item) {
		if (item.isInfiniteStock()) {
			return true;
		}

		if (!item.isFields()) {
			if (item.getStockQuantity() > 0) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			String[] fieldsQuantities = item.getFieldsQuantitiesArray();

			for (int i = 0; i < fieldsQuantities.length; i++) {
				if (GetterUtil.getInteger(fieldsQuantities[i]) > 0) {
					return true;
				}
			}

			return false;
		}
	}

	public static boolean isInStock(
		ShoppingItem item, ShoppingItemField[] itemFields, String[] fieldsArray,
		Integer orderedQuantity) {

		if (item.isInfiniteStock()) {
			return true;
		}

		if (!item.isFields()) {
			int stockQuantity = item.getStockQuantity();

			if ((stockQuantity > 0) &&
				(stockQuantity >= orderedQuantity.intValue())) {

				return true;
			}
			else {
				return false;
			}
		}
		else {
			String[] fieldsQuantities = item.getFieldsQuantitiesArray();

			int stockQuantity = 0;

			if (fieldsQuantities.length > 0) {
				int rowPos = getFieldsQuantitiesPos(
					item, itemFields, fieldsArray);

				stockQuantity = GetterUtil.getInteger(fieldsQuantities[rowPos]);
			}

			try {
				if ((stockQuantity > 0) &&
					(stockQuantity >= orderedQuantity.intValue())) {

					return true;
				}
			}
			catch (Exception e) {
			}

			return false;
		}
	}

	private static ShoppingCart _getCart(ThemeDisplay themeDisplay) {
		ShoppingCart cart = ShoppingCartLocalServiceUtil.createShoppingCart(0);

		cart.setGroupId(themeDisplay.getScopeGroupId());
		cart.setCompanyId(themeDisplay.getCompanyId());
		cart.setUserId(themeDisplay.getUserId());
		cart.setItemIds(StringPool.BLANK);
		cart.setCouponCodes(StringPool.BLANK);
		cart.setAltShipping(0);
		cart.setInsure(false);

		return cart;
	}

}