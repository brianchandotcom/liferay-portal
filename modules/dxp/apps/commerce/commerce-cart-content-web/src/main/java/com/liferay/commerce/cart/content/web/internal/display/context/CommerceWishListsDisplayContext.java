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

import com.liferay.commerce.cart.content.web.internal.display.context.util.CommerceCartContentRequestHelper;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.model.CommerceCartConstants;
import com.liferay.commerce.service.CommerceCartService;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

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

		_commerceCartService = commerceCartService;

		_commerceCartContentRequestHelper =
			new CommerceCartContentRequestHelper(httpServletRequest);
	}

	public CommerceCart getCommerceCart() {
		if (_commerceCart != null) {
			return _commerceCart;
		}

		long commerceCartId = ParamUtil.getLong(
			_commerceCartContentRequestHelper.getRequest(), "commerceCartId");

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
		HttpServletRequest httpServletRequest =
			_commerceCartContentRequestHelper.getRequest();
		LiferayPortletResponse liferayPortletResponse =
			_commerceCartContentRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		String redirect = ParamUtil.getString(httpServletRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		String delta = ParamUtil.getString(httpServletRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String deltaEntry = ParamUtil.getString(
			httpServletRequest, "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		String keywords = ParamUtil.getString(httpServletRequest, "keywords");

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		return portletURL;
	}

	public SearchContainer<CommerceCart> getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			_commerceCartContentRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, null);

		_searchContainer.setEmptyResultsMessage("no-wish-lists-were-found");

		OrderByComparator<CommerceCart> orderByComparator =
			CommerceUtil.getCommerceCartOrderByComparator(Field.NAME, "asc");

		_searchContainer.setOrderByComparator(orderByComparator);

		int total = _commerceCartService.getCommerceCartsCount(
			_commerceCartContentRequestHelper.getScopeGroupId(),
			CommerceCartConstants.TYPE_WISH_LIST);

		_searchContainer.setTotal(total);

		List<CommerceCart> results = _commerceCartService.getCommerceCarts(
			_commerceCartContentRequestHelper.getScopeGroupId(),
			CommerceCartConstants.TYPE_WISH_LIST, _searchContainer.getStart(),
			_searchContainer.getEnd(), orderByComparator);

		_searchContainer.setResults(results);

		return _searchContainer;
	}

	private CommerceCart _commerceCart;
	private final CommerceCartContentRequestHelper
		_commerceCartContentRequestHelper;
	private final CommerceCartService _commerceCartService;
	private SearchContainer<CommerceCart> _searchContainer;

}