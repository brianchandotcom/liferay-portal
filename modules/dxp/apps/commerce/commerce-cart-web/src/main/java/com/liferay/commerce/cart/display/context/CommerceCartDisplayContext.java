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

import com.liferay.commerce.cart.constants.CommerceCartConstants;
import com.liferay.commerce.cart.internal.portlet.action.ActionHelper;
import com.liferay.commerce.cart.internal.util.CommerceCartPortletUtil;
import com.liferay.commerce.cart.model.CommerceCart;
import com.liferay.commerce.cart.service.CommerceCartLocalService;
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
public class CommerceCartDisplayContext
	extends BaseCommerceCartDisplayContext<CommerceCart> {

	public CommerceCartDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CommerceCartLocalService commerceCartLocalService)
		throws PortalException {

		super(
			actionHelper, httpServletRequest,
			CommerceCart.class.getSimpleName());

		setDefaultOrderByCol("name");

		_commerceCartLocalService = commerceCartLocalService;
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		String toolbarItem = ParamUtil.getString(
			httpServletRequest, "toolbarItem", "view-all-carts");

		portletURL.setParameter("toolbarItem", toolbarItem);

		portletURL.setParameter("type", String.valueOf(getCommerceCartType()));

		return portletURL;
	}

	@Override
	public SearchContainer<CommerceCart> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		if (getCommerceCartType() ==
				CommerceCartConstants.COMMERCE_CART_TYPE_CART) {

			searchContainer.setEmptyResultsMessage("no-carts-were-found");
		}
		else if (getCommerceCartType() ==
					CommerceCartConstants.COMMERCE_CART_TYPE_WISH_LIST) {

			searchContainer.setEmptyResultsMessage("no-wish-lists-were-found");
		}

		OrderByComparator<CommerceCart> orderByComparator =
			CommerceCartPortletUtil.getCommerceCartOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		int total = _commerceCartLocalService.getCommerceCartsCount(
			getCommerceCartType());

		searchContainer.setTotal(total);

		List<CommerceCart> results = _commerceCartLocalService.getCommerceCarts(
			getCommerceCartType(), searchContainer.getStart(),
			searchContainer.getEnd(), orderByComparator);

		searchContainer.setResults(results);

		return searchContainer;
	}

	private final CommerceCartLocalService _commerceCartLocalService;

}