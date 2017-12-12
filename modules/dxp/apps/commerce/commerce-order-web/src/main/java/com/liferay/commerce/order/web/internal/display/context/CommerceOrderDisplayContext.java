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

package com.liferay.commerce.order.web.internal.display.context;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.web.internal.portlet.action.ActionHelper;
import com.liferay.commerce.order.web.internal.util.CommerceOrderPortletUtil;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.util.CommercePriceFormatter;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderDisplayContext
	extends BaseCommerceOrderDisplayContext<CommerceOrder> {

	public CommerceOrderDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CommerceOrderService commerceOrderService,
			CommercePriceFormatter commercePriceFormatter)
		throws PortalException {

		super(
			actionHelper, httpServletRequest,
			CommerceOrder.class.getSimpleName());

		_commerceOrderService = commerceOrderService;
		_commercePriceFormatter = commercePriceFormatter;
	}

	public String getCommerceOrderTotal(long commerceOrderId)
		throws PortalException {

		CommerceOrder commerceOrder = _commerceOrderService.fetchCommerceOrder(
			commerceOrderId);

		return _commercePriceFormatter.format(
			httpServletRequest, commerceOrder.getTotal());
	}

	@Override
	public SearchContainer<CommerceOrder> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		OrderByComparator<CommerceOrder> orderByComparator =
			CommerceOrderPortletUtil.getCommerceOrderOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setEmptyResultsMessage("no-orders-were-found");
		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		int total = _commerceOrderService.getCommerceOrdersCount(
			themeDisplay.getScopeGroupId());

		searchContainer.setTotal(total);

		List<CommerceOrder> results = _commerceOrderService.getCommerceOrders(
			themeDisplay.getScopeGroupId(), searchContainer.getStart(),
			searchContainer.getEnd(), orderByComparator);

		searchContainer.setResults(results);

		return searchContainer;
	}

	private final CommerceOrderService _commerceOrderService;
	private final CommercePriceFormatter _commercePriceFormatter;

}