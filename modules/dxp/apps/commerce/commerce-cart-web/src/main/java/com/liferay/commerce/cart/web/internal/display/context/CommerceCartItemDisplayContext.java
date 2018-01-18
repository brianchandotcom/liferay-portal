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
import com.liferay.commerce.cart.web.internal.util.CommerceCartPortletUtil;
import com.liferay.commerce.model.CommerceCartItem;
import com.liferay.commerce.product.item.selector.criterion.CPInstanceItemSelectorCriterion;
import com.liferay.commerce.service.CommerceCartItemService;
import com.liferay.commerce.util.CommercePriceCalculator;
import com.liferay.commerce.util.CommercePriceFormatter;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
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
			CommerceCartItemService commerceCartItemService,
			CommercePriceCalculator commercePriceCalculator,
			CommercePriceFormatter commercePriceFormatter,
			ItemSelector itemSelector)
		throws PortalException {

		super(
			actionHelper, httpServletRequest,
			CommerceCartItem.class.getSimpleName());

		_commerceCartItemService = commerceCartItemService;
		_commercePriceCalculator = commercePriceCalculator;
		_commercePriceFormatter = commercePriceFormatter;
		_itemSelector = itemSelector;
	}

	public CommerceCartItem getCommerceCartItem() throws PortalException {
		if (_commerceCartItem != null) {
			return _commerceCartItem;
		}

		_commerceCartItem = actionHelper.getCommerceCartItem(
			cpRequestHelper.getRenderRequest());

		return _commerceCartItem;
	}

	public long getCommerceCartItemId() throws PortalException {
		CommerceCartItem commerceCartItem = getCommerceCartItem();

		if (commerceCartItem == null) {
			return 0;
		}

		return commerceCartItem.getCommerceCartItemId();
	}

	public String getFormattedPrice(CommerceCartItem commerceCartItem)
		throws PortalException {

		double price = _commercePriceCalculator.getPrice(commerceCartItem);

		return _commercePriceFormatter.format(httpServletRequest, price);
	}

	public String getItemSelectorUrl() throws PortalException {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				cpRequestHelper.getRenderRequest());

		CPInstanceItemSelectorCriterion cpInstanceItemSelectorCriterion =
			new CPInstanceItemSelectorCriterion();

		cpInstanceItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new UUIDItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "productInstancesSelectItem",
			cpInstanceItemSelectorCriterion);

		String checkedCPInstanceIds = StringUtil.merge(
			getCheckedCPInstanceIds());

		itemSelectorURL.setParameter(
			"checkedCPInstanceIds", checkedCPInstanceIds);

		return itemSelectorURL.toString();
	}

	public List<NavigationItem> getNavigationItems() throws PortalException {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "viewCommerceCartItems");
		portletURL.setParameter(
			"commerceCartId", String.valueOf(getCommerceCartId()));

		String cartToolbarItem = ParamUtil.getString(
			httpServletRequest, "cartToolbarItem", "view-all-carts");

		portletURL.setParameter("cartToolbarItem", cartToolbarItem);

		String toolbarItem = ParamUtil.getString(
			httpServletRequest, "toolbarItem", "view-all-cart-items");

		portletURL.setParameter("toolbarItem", toolbarItem);

		NavigationItem cartItemsNavigationItem = new NavigationItem();

		cartItemsNavigationItem.setActive(
			toolbarItem.equals("view-all-cart-items"));
		cartItemsNavigationItem.setHref(portletURL.toString());
		cartItemsNavigationItem.setLabel("cart-items");

		return Collections.singletonList(cartItemsNavigationItem);
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "viewCommerceCartItems");
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

	protected long[] getCheckedCPInstanceIds() throws PortalException {
		List<Long> cpInstanceIdsList = new ArrayList<>();

		List<CommerceCartItem> commerceCartItems = getCommerceCartItems();

		for (CommerceCartItem commerceCartItem : commerceCartItems) {
			cpInstanceIdsList.add(commerceCartItem.getCPInstanceId());
		}

		if (!cpInstanceIdsList.isEmpty()) {
			return ArrayUtil.toLongArray(cpInstanceIdsList);
		}

		return new long[0];
	}

	protected List<CommerceCartItem> getCommerceCartItems()
		throws PortalException {

		int total = _commerceCartItemService.getCommerceCartItemsCount(
			getCommerceCartId());

		return _commerceCartItemService.getCommerceCartItems(
			getCommerceCartId(), 0, total);
	}

	private CommerceCartItem _commerceCartItem;
	private final CommerceCartItemService _commerceCartItemService;
	private final CommercePriceCalculator _commercePriceCalculator;
	private final CommercePriceFormatter _commercePriceFormatter;
	private final ItemSelector _itemSelector;

}