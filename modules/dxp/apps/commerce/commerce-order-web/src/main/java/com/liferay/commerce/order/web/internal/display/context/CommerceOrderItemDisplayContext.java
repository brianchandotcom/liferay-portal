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

import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.web.internal.portlet.action.ActionHelper;
import com.liferay.commerce.order.web.internal.util.CommerceOrderPortletUtil;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.util.CommercePriceFormatter;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderItemDisplayContext
	extends BaseCommerceOrderDisplayContext<CommerceOrderItem> {

	public CommerceOrderItemDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CommerceOrderItemLocalService commerceOrderItemLocalService,
			CommercePriceFormatter commercePriceFormatter,
			CPDefinitionHelper cpDefinitionHelper)
		throws PortalException {

		super(
			actionHelper, httpServletRequest,
			CommerceOrderItem.class.getSimpleName());

		_commerceOrderItemLocalService = commerceOrderItemLocalService;
		_commercePriceFormatter = commercePriceFormatter;
		_cpDefinitionHelper = cpDefinitionHelper;
	}

	public String getCPDefinitionURL(
			long cpDefinitionId, ThemeDisplay themeDisplay)
		throws PortalException {

		return _cpDefinitionHelper.getFriendlyURL(cpDefinitionId, themeDisplay);
	}

	public String getFormattedPrice(long commerceOrderItemId)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemLocalService.fetchCommerceOrderItem(
				commerceOrderItemId);

		return _commercePriceFormatter.format(
			httpServletRequest, commerceOrderItem.getPrice());
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "viewCommerceOrderItems");
		portletURL.setParameter(
			"commerceOrderId", String.valueOf(getCommerceOrderId()));

		return portletURL;
	}

	@Override
	public SearchContainer<CommerceOrderItem> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		OrderByComparator<CommerceOrderItem> orderByComparator =
			CommerceOrderPortletUtil.getCommerceOrderItemOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setEmptyResultsMessage("no-order-items-were-found");
		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		int total = _commerceOrderItemLocalService.getCommerceOrderItemsCount(
			getCommerceOrderId());

		searchContainer.setTotal(total);

		List<CommerceOrderItem> results =
			_commerceOrderItemLocalService.getCommerceOrderItems(
				getCommerceOrderId(), searchContainer.getStart(),
				searchContainer.getEnd(), orderByComparator);

		searchContainer.setResults(results);

		return searchContainer;
	}

	private final CommerceOrderItemLocalService _commerceOrderItemLocalService;
	private final CommercePriceFormatter _commercePriceFormatter;
	private final CPDefinitionHelper _cpDefinitionHelper;

}