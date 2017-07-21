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

package com.liferay.commerce.cart.display.context;

import com.liferay.commerce.cart.internal.portlet.action.ActionHelper;
import com.liferay.commerce.cart.internal.util.CommerceCartPortletUtil;
import com.liferay.commerce.cart.model.CommerceCartItem;
import com.liferay.commerce.cart.service.CommerceCartItemService;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceCartItemDisplayContext
	extends BaseCommerceCartDisplayContext<CommerceCartItem> {

	public CommerceCartItemDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CommerceCartItemService commerceCartItemService)
		throws PortalException {

		super(
			actionHelper, httpServletRequest,
			CommerceCartItem.class.getSimpleName());

		_commerceCartItemService = commerceCartItemService;
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter("mvcRenderCommandName", "viewCartItems");
		portletURL.setParameter(
			"commerceCartId", String.valueOf(getCommerceCartId()));

		String toolbarItem = ParamUtil.getString(
			httpServletRequest, "toolbarItem", "view-all-cart-items");

		portletURL.setParameter("toolbarItem", toolbarItem);

		return portletURL;
	}

	@Override
	public SearchContainer<CommerceCartItem> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		searchContainer.setEmptyResultsMessage("no-cart-items-were-found");

		OrderByComparator<CommerceCartItem> orderByComparator =
			CommerceCartPortletUtil.getCommerceCartItemOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		int total = _commerceCartItemService.getCommerceCartItemsCount(
			getCommerceCartId());

		searchContainer.setTotal(total);

		List<CommerceCartItem> results =
			_commerceCartItemService.getCommerceCartItems(
				getCommerceCartId(), searchContainer.getStart(),
				searchContainer.getEnd(), orderByComparator);

		searchContainer.setResults(results);

		return searchContainer;
	}

	private final CommerceCartItemService _commerceCartItemService;

}