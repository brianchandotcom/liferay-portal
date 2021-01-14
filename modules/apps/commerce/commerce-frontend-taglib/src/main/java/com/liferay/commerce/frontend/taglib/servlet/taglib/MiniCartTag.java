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

package com.liferay.commerce.frontend.taglib.servlet.taglib;

import com.liferay.commerce.configuration.CommercePriceConfiguration;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.SystemSettingsLocator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.util.HashMap;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Gianmarco Brunialti Masera
 */
public class MiniCartTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		CommerceContext commerceContext = (CommerceContext)request.getAttribute(
			CommerceWebKeys.COMMERCE_CONTEXT);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (Validator.isNull(_spritemap)) {
			_spritemap = themeDisplay.getPathThemeImages() + "/clay/icons.svg";
		}

		try {
			CommerceOrder commerceOrder = commerceContext.getCommerceOrder();

			if (commerceOrder != null) {
				_itemsQuantity = _getItemsQuantity(commerceOrder, request);
				_orderId = commerceOrder.getCommerceOrderId();

				PortletURL commerceCartPortletURL =
					_commerceOrderHttpHelper.getCommerceCartPortletURL(
						request, commerceOrder);

				if (commerceCartPortletURL != null) {
					_orderDetailURL = String.valueOf(commerceCartPortletURL);
				}
			}
			else {
				_orderDetailURL = StringPool.BLANK;
				_orderId = 0;
			}

			_checkoutURL = StringPool.BLANK;

			PortletURL commerceCheckoutPortletURL =
				_commerceOrderHttpHelper.getCommerceCheckoutPortletURL(request);

			if (commerceCheckoutPortletURL != null) {
				_checkoutURL = String.valueOf(commerceCheckoutPortletURL);
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			_checkoutURL = StringPool.BLANK;
			_itemsQuantity = 0;
			_orderDetailURL = StringPool.BLANK;
			_orderId = 0;
		}

		return super.doStartTag();
	}

	public HashMap<String, String> getLabels() {
		return _labels;
	}

	public String getSpritemap() {
		return _spritemap;
	}

	public HashMap<String, String> getViews() {
		return _views;
	}

	public boolean isDisplayTotalItemsQuantity() {
		return _displayTotalItemsQuantity;
	}

	public boolean isToggleable() {
		return _toggleable;
	}

	public void setDisplayTotalItemsQuantity(
		boolean displayTotalItemsQuantity) {

		_displayTotalItemsQuantity = displayTotalItemsQuantity;
	}

	public void setLabels(HashMap<String, String> labels) {
		_labels = labels;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		_configurationProvider = ServletContextUtil.getConfigurationProvider();
		_commerceOrderHttpHelper =
			ServletContextUtil.getCommerceOrderHttpHelper();
		servletContext = ServletContextUtil.getServletContext();
	}

	public void setSpritemap(String spritemap) {
		_spritemap = spritemap;
	}

	public void setToggleable(boolean toggleable) {
		_toggleable = toggleable;
	}

	public void setViews(HashMap<String, String> views) {
		_views = views;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_checkoutURL = null;
		_commerceOrderHttpHelper = null;
		_configurationProvider = null;
		_displayTotalItemsQuantity = false;
		_itemsQuantity = 0;
		_labels = new HashMap<>();
		_orderDetailURL = null;
		_orderId = 0;
		_spritemap = null;
		_toggleable = true;
		_views = new HashMap<>();
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-commerce:cart:cartViews", _views);
		httpServletRequest.setAttribute(
			"liferay-commerce:cart:checkoutURL", _checkoutURL);
		httpServletRequest.setAttribute(
			"liferay-commerce:cart:displayDiscountLevels",
			_isDisplayDiscountLevels());
		httpServletRequest.setAttribute(
			"liferay-commerce:cart:displayTotalItemsQuantity",
			_displayTotalItemsQuantity);
		httpServletRequest.setAttribute(
			"liferay-commerce:cart:itemsQuantity", _itemsQuantity);
		httpServletRequest.setAttribute(
			"liferay-commerce:cart:labels", _labels);
		httpServletRequest.setAttribute(
			"liferay-commerce:cart:orderDetailURL", _orderDetailURL);
		httpServletRequest.setAttribute(
			"liferay-commerce:cart:orderId", _orderId);
		httpServletRequest.setAttribute(
			"liferay-commerce:cart:spritemap", _spritemap);
		httpServletRequest.setAttribute(
			"liferay-commerce:cart:toggleable", _toggleable);
	}

	private int _getItemsQuantity(
			CommerceOrder commerceOrder, HttpServletRequest httpServletRequest)
		throws PortalException {

		if (_displayTotalItemsQuantity) {
			return _commerceOrderHttpHelper.getCommerceOrderItemsQuantity(
				httpServletRequest);
		}

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		return commerceOrderItems.size();
	}

	private boolean _isDisplayDiscountLevels() {
		try {
			CommercePriceConfiguration commercePriceConfiguration =
				_configurationProvider.getConfiguration(
					CommercePriceConfiguration.class,
					new SystemSettingsLocator(
						CommerceConstants.SERVICE_NAME_PRICE));

			return commercePriceConfiguration.displayDiscountLevels();
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException, configurationException);

			return false;
		}
	}

	private static final String _PAGE = "/mini_cart/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(MiniCartTag.class);

	private String _checkoutURL;
	private CommerceOrderHttpHelper _commerceOrderHttpHelper;
	private ConfigurationProvider _configurationProvider;
	private boolean _displayTotalItemsQuantity;
	private int _itemsQuantity;
	private HashMap<String, String> _labels = new HashMap<>();
	private String _orderDetailURL;
	private long _orderId;
	private String _spritemap;
	private boolean _toggleable = true;
	private HashMap<String, String> _views = new HashMap<>();

}