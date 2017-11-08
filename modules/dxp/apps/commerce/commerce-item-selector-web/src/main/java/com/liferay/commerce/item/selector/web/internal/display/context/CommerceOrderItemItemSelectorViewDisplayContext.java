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

package com.liferay.commerce.item.selector.web.internal.display.context;

import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.util.CommercePriceFormatter;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderItemItemSelectorViewDisplayContext
	extends BaseCommerceItemSelectorViewDisplayContext<CommerceOrderItem> {

	public CommerceOrderItemItemSelectorViewDisplayContext(
		CommerceOrderItemLocalService commerceOrderItemLocalService,
		CommercePriceFormatter commercePriceFormatter,
		HttpServletRequest httpServletRequest, PortletURL portletURL,
		String itemSelectedEventName) {

		super(httpServletRequest, portletURL, itemSelectedEventName);

		_commerceOrderItemLocalService = commerceOrderItemLocalService;
		_commercePriceFormatter = commercePriceFormatter;

		setDefaultOrderByCol("create-date");
		setDefaultOrderByType("desc");
	}

	public String getFormattedPrice(long commerceOrderItemId)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemLocalService.fetchCommerceOrderItem(
				commerceOrderItemId);

		return _commercePriceFormatter.format(
			httpServletRequest, commerceOrderItem.getPrice());
	}

	public SearchContainer<CommerceOrderItem> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		searchContainer = new SearchContainer<>(
			cpRequestHelper.getRenderRequest(), getPortletURL(), null, null);

		searchContainer.setEmptyResultsMessage("no-order-items-were-found");

		RowChecker rowChecker = new EmptyOnClickRowChecker(
			cpRequestHelper.getRenderResponse());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(rowChecker);

		List<CommerceOrderItem> results =
			_commerceOrderItemLocalService.getCommerceOrderItems(
				themeDisplay.getScopeGroupId(), getCommerceAddressId(),
				searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);
		searchContainer.setTotal(results.size());

		return searchContainer;
	}

	protected long getCommerceAddressId() {
		return ParamUtil.getLong(httpServletRequest, "commerceAddressId", 0);
	}

	private final CommerceOrderItemLocalService _commerceOrderItemLocalService;
	private final CommercePriceFormatter _commercePriceFormatter;

}