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
public class AddToWishListTag extends IncludeTag {

	public long getCommerceAccountId() {
		return _commerceAccountId;
	}

	public long getCpDefinitionId() {
		return _cpDefinitionId;
	}

	public long getSkuId() {
		return _skuId;
	}

	public String getSpritemap() {
		return _spritemap;
	}

	public boolean isInWishList() {
		return _inWishList;
	}

	public boolean isLarge() {
		return _large;
	}

	@Override
	public void setAttributes(HttpServletRequest httpServletRequest) {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		setNamespacedAttribute(
			httpServletRequest, "commerceAccountId", _commerceAccountId);
		setNamespacedAttribute(
			httpServletRequest, "cpDefinitionId", _cpDefinitionId);
		setNamespacedAttribute(httpServletRequest, "inWishList", _inWishList);
		setNamespacedAttribute(httpServletRequest, "large", _large);
		setNamespacedAttribute(httpServletRequest, "skuId", _skuId);

		if (Validator.isNull(_spritemap)) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			_spritemap = themeDisplay.getPathThemeImages() + "/clay/icons.svg";
		}

		setNamespacedAttribute(httpServletRequest, "spritemap", _spritemap);
	}

	public void setCommerceAccountId(long commerceAccountId) {
		_commerceAccountId = commerceAccountId;
	}

	public void setCpDefinitionId(long cpDefinitionId) {
		_cpDefinitionId = cpDefinitionId;
	}

	public void setInWishList(boolean inWishList) {
		_inWishList = inWishList;
	}

	public void setLarge(boolean large) {
		_large = large;
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

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_commerceAccountId = 0;
		_cpDefinitionId = 0;
		_inWishList = false;
		_large = false;
		_skuId = 0;
		_spritemap = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	private static final String _ATTRIBUTE_NAMESPACE =
		"liferay-commerce:add-to-wish-list:";

	private static final String _PAGE = "/add_to_wish_list/page.jsp";

	private long _commerceAccountId;
	private long _cpDefinitionId;
	private boolean _inWishList;
	private boolean _large;
	private long _skuId;
	private String _spritemap;

}