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

package com.liferay.commerce.order.content.web.internal.display.context;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.price.CommercePriceFormatter;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderContentDisplayContext
	extends BaseCommerceOrderContentDisplayContext<CommerceOrder> {

	public CommerceOrderContentDisplayContext(
			HttpServletRequest httpServletRequest,
			CommerceOrderLocalService commerceOrderLocalService,
			CommercePriceFormatter commercePriceFormatter)
		throws ConfigurationException {

		super(httpServletRequest, commerceOrderLocalService);

		_commercePriceFormatter = commercePriceFormatter;
	}

	public String getCommerceOrderTotal(long commerceOrderId)
		throws PortalException {

		CommerceOrder commerceOrder =
			commerceOrderLocalService.fetchCommerceOrder(commerceOrderId);

		return _commercePriceFormatter.format(
			commerceOrder.getCommerceCurrency(), commerceOrder.getTotal());
	}

	@Override
	public SearchContainer<CommerceOrder> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		_searchContainer.setEmptyResultsMessage("no-orders-were-found");

		int total = commerceOrderLocalService.getCommerceOrdersCount(
			themeDisplay.getScopeGroupId(), themeDisplay.getUserId());

		_searchContainer.setTotal(total);

		List<CommerceOrder> results =
			commerceOrderLocalService.getCommerceOrders(
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
				_searchContainer.getStart(), _searchContainer.getEnd(), null);

		_searchContainer.setResults(results);

		return _searchContainer;
	}

	private final CommercePriceFormatter _commercePriceFormatter;
	private SearchContainer<CommerceOrder> _searchContainer;

}