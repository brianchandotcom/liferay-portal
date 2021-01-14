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

import com.liferay.commerce.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Gianmarco Brunialti Masera
 */
public class AddToOrderTag extends IncludeTag {

	public boolean getBlock() {
		return _block;
	}

	public long getChannelId() {
		return _channelId;
	}

	public long getCommerceAccountId() {
		return _commerceAccountId;
	}

	public String getCurrencyCode() {
		return _currencyCode;
	}

	public String getOptions() {
		return _options;
	}

	public long getOrderId() {
		return _orderId;
	}

	public long getSkuId() {
		return _skuId;
	}

	public String getSpritemap() {
		return _spritemap;
	}

	public int getStockQuantity() {
		return _stockQuantity;
	}

	public boolean isDisabled() {
		return _disabled;
	}

	public boolean isInCart() {
		return _inCart;
	}

	public boolean isWillUpdate() {
		return _willUpdate;
	}

	@Override
	public void setAttributes(HttpServletRequest httpServletRequest) {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		setNamespacedAttribute(httpServletRequest, "block", _block);
		setNamespacedAttribute(
			httpServletRequest, "commerceAccountId", _commerceAccountId);
		setNamespacedAttribute(
			httpServletRequest, "currencyCode", _currencyCode);
		setNamespacedAttribute(httpServletRequest, "channelId", _channelId);
		setNamespacedAttribute(httpServletRequest, "disabled", _disabled);
		setNamespacedAttribute(httpServletRequest, "inCart", _inCart);
		setNamespacedAttribute(httpServletRequest, "options", _options);
		setNamespacedAttribute(httpServletRequest, "orderId", _orderId);
		setNamespacedAttribute(httpServletRequest, "skuId", _skuId);

		if (Validator.isNull(_spritemap)) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			_spritemap = themeDisplay.getPathThemeImages() + "/clay/icons.svg";
		}

		setNamespacedAttribute(httpServletRequest, "spritemap", _spritemap);
		setNamespacedAttribute(
			httpServletRequest, "stockQuantity", _stockQuantity);
		setNamespacedAttribute(httpServletRequest, "willUpdate", _willUpdate);
	}

	public void setBlock(boolean block) {
		_block = block;
	}

	public void setChannelId(long channelId) {
		_channelId = channelId;
	}

	public void setCommerceAccountId(long commerceAccountId) {
		_commerceAccountId = commerceAccountId;
	}

	public void setCurrencyCode(String currencyCode) {
		_currencyCode = currencyCode;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	public void setInCart(boolean inCart) {
		_inCart = inCart;
	}

	public void setOptions(String options) {
		_options = options;
	}

	public void setOrderId(long orderId) {
		_orderId = orderId;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setSkuId(long skuId) {
		_skuId = skuId;
	}

	public void setSpritemap(String spritemap) {
		_spritemap = spritemap;
	}

	public void setStockQuantity(int stockQuantity) {
		_stockQuantity = stockQuantity;
	}

	public void setWillUpdate(boolean willUpdate) {
		_willUpdate = willUpdate;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_block = false;
		_channelId = 0;
		_commerceAccountId = 0;
		_currencyCode = null;
		_disabled = false;
		_inCart = false;
		_options = null;
		_orderId = 0;
		_skuId = 0;
		_spritemap = null;
		_stockQuantity = 0;
		_willUpdate = false;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	private static final String _ATTRIBUTE_NAMESPACE =
		"liferay-commerce:add-to-order:";

	private static final String _PAGE = "/add_to_order/page.jsp";

	private boolean _block;
	private long _channelId;
	private long _commerceAccountId;
	private String _currencyCode;
	private boolean _disabled;
	private boolean _inCart;
	private String _options;
	private long _orderId;
	private long _skuId;
	private String _spritemap;
	private int _stockQuantity;
	private boolean _willUpdate;

}