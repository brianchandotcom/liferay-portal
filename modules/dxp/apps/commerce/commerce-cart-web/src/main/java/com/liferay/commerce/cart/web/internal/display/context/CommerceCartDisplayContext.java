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

package com.liferay.commerce.cart.web.internal.display.context;

import com.liferay.commerce.cart.web.internal.portlet.action.ActionHelper;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.model.CommerceCartConstants;
import com.liferay.commerce.service.CommerceCartService;
import com.liferay.commerce.util.CommercePriceCalculator;
import com.liferay.commerce.util.CommercePriceFormatter;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
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
			CommerceCartService commerceCartService,
			CommercePriceCalculator commercePriceCalculator,
			CommercePriceFormatter commercePriceFormatter)
		throws PortalException {

		super(
			actionHelper, httpServletRequest,
			CommerceCart.class.getSimpleName());

		setDefaultOrderByCol("name");

		_commerceCartService = commerceCartService;
		_commercePriceCalculator = commercePriceCalculator;
		_commercePriceFormatter = commercePriceFormatter;
	}

	public String getCommerceCartSubtotal(CommerceCart commerceCart)
		throws PortalException {

		double subtotal = _commercePriceCalculator.getSubtotal(commerceCart);

		return _commercePriceFormatter.format(httpServletRequest, subtotal);
	}

	public List<NavigationItem> getNavigationItems() {
		List<NavigationItem> navigationItems = new ArrayList<>();

		PortletURL viewCartsURL = liferayPortletResponse.createRenderURL();
		PortletURL viewWishListsURL = liferayPortletResponse.createRenderURL();

		viewCartsURL.setParameter("toolbarItem", "view-all-carts");
		viewCartsURL.setParameter(
			"type", String.valueOf(CommerceCartConstants.TYPE_CART));

		viewWishListsURL.setParameter("toolbarItem", "view-all-wish-lists");
		viewWishListsURL.setParameter(
			"type", String.valueOf(CommerceCartConstants.TYPE_WISH_LIST));

		String toolbarItem = ParamUtil.getString(
			httpServletRequest, "toolbarItem", "view-all-carts");

		NavigationItem cartsNavigationItem = new NavigationItem();
		NavigationItem wishListsNavigationItem = new NavigationItem();

		cartsNavigationItem.setActive(toolbarItem.equals("view-all-carts"));
		cartsNavigationItem.setHref(viewCartsURL.toString());
		cartsNavigationItem.setLabel(
			LanguageUtil.get(httpServletRequest, "carts"));

		wishListsNavigationItem.setActive(
			toolbarItem.equals("view-all-wish-lists"));
		wishListsNavigationItem.setHref(viewWishListsURL.toString());
		wishListsNavigationItem.setLabel(
			LanguageUtil.get(httpServletRequest, "wish-lists"));

		navigationItems.add(cartsNavigationItem);
		navigationItems.add(wishListsNavigationItem);

		return navigationItems;
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

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		int type = getCommerceCartType();

		if (type == CommerceCartConstants.TYPE_CART) {
			searchContainer.setEmptyResultsMessage("no-carts-were-found");
		}
		else if (type == CommerceCartConstants.TYPE_WISH_LIST) {
			searchContainer.setEmptyResultsMessage("no-wish-lists-were-found");
		}

		OrderByComparator<CommerceCart> orderByComparator =
			CommerceUtil.getCommerceCartOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		int total = _commerceCartService.getCommerceCartsCount(
			themeDisplay.getScopeGroupId(), type);

		searchContainer.setTotal(total);

		List<CommerceCart> results = _commerceCartService.getCommerceCarts(
			themeDisplay.getScopeGroupId(), type, searchContainer.getStart(),
			searchContainer.getEnd(), orderByComparator);

		searchContainer.setResults(results);

		return searchContainer;
	}

	protected int getCommerceCartType() {
		return ParamUtil.getInteger(
			httpServletRequest, "type", CommerceCartConstants.TYPE_CART);
	}

	private final CommerceCartService _commerceCartService;
	private final CommercePriceCalculator _commercePriceCalculator;
	private final CommercePriceFormatter _commercePriceFormatter;

}