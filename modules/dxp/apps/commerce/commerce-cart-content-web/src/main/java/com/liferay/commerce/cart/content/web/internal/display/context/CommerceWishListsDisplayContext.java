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

package com.liferay.commerce.cart.content.web.internal.display.context;

import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.model.CommerceCartConstants;
import com.liferay.commerce.product.display.context.util.CPRequestHelper;
import com.liferay.commerce.service.CommerceCartService;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceWishListsDisplayContext {

	public CommerceWishListsDisplayContext(
		HttpServletRequest httpServletRequest,
		CommerceCartService commerceCartService) {

		_httpServletRequest = httpServletRequest;
		_commerceCartService = commerceCartService;

		CPRequestHelper cpRequestHelper = new CPRequestHelper(
			httpServletRequest);

		_liferayPortletRequest = cpRequestHelper.getLiferayPortletRequest();
		_liferayPortletResponse = cpRequestHelper.getLiferayPortletResponse();
	}

	public CommerceCart getCommerceCart() {
		if (_commerceCart != null) {
			return _commerceCart;
		}

		long commerceCartId = ParamUtil.getLong(
			_httpServletRequest, "commerceCartId");

		_commerceCart = _commerceCartService.fetchCommerceCart(commerceCartId);

		return _commerceCart;
	}

	public long getCommerceCartId() {
		CommerceCart commerceCart = getCommerceCart();

		if (commerceCart == null) {
			return 0;
		}

		return commerceCart.getCommerceCartId();
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		String redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		String delta = ParamUtil.getString(_httpServletRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String deltaEntry = ParamUtil.getString(
			_httpServletRequest, "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		String keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		return portletURL;
	}

	public SearchContainer<CommerceCart> getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_searchContainer = new SearchContainer<>(
			_liferayPortletRequest, getPortletURL(), null, null);

		_searchContainer.setEmptyResultsMessage("no-wish-lists-were-found");

		OrderByComparator<CommerceCart> orderByComparator =
			CommerceUtil.getCommerceCartOrderByComparator(Field.NAME);

		_searchContainer.setOrderByComparator(orderByComparator);

		int total = _commerceCartService.getCommerceCartsCount(
			themeDisplay.getScopeGroupId(),
			CommerceCartConstants.TYPE_WISH_LIST);

		_searchContainer.setTotal(total);

		List<CommerceCart> results = _commerceCartService.getCommerceCarts(
			themeDisplay.getScopeGroupId(),
			CommerceCartConstants.TYPE_WISH_LIST, _searchContainer.getStart(),
			_searchContainer.getEnd(), orderByComparator);

		_searchContainer.setResults(results);

		return _searchContainer;
	}

	private CommerceCart _commerceCart;
	private final CommerceCartService _commerceCartService;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private SearchContainer<CommerceCart> _searchContainer;

}