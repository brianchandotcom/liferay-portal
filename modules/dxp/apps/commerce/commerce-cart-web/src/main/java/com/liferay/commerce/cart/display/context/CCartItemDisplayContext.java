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
import com.liferay.commerce.cart.internal.util.CCartPortletUtil;
import com.liferay.commerce.cart.model.CCartItem;
import com.liferay.commerce.cart.service.CCartItemLocalService;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CCartItemDisplayContext
	extends BaseCCartDisplayContext<CCartItem> {

	public CCartItemDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CCartItemLocalService cCartItemLocalService,
			CPDefinitionLocalService cpDefinitionLocalService)
		throws PortalException {

		super(
			actionHelper, httpServletRequest, CCartItem.class.getSimpleName());

		setDefaultOrderByCol("title");

		_cCartItemLocalService = cCartItemLocalService;
		_cpDefinitionLocalService = cpDefinitionLocalService;
	}

	public CPDefinition getCPDefinition(long cpDefinitionId) {
		return _cpDefinitionLocalService.fetchCPDefinition(cpDefinitionId);
	}

	@Override
	public SearchContainer<CCartItem> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		searchContainer.setEmptyResultsMessage("no-cart-items-were-found");

		OrderByComparator<CCartItem> orderByComparator =
			CCartPortletUtil.getCCartItemOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		int total = _cCartItemLocalService.getCCartItemsCount(getCCartId());

		searchContainer.setTotal(total);

		List<CCartItem> results = _cCartItemLocalService.getCCartItems(
			getCCartId(), searchContainer.getStart(), searchContainer.getEnd(),
			orderByComparator);

		searchContainer.setResults(results);

		return searchContainer;
	}

	private final CCartItemLocalService _cCartItemLocalService;
	private final CPDefinitionLocalService _cpDefinitionLocalService;

}