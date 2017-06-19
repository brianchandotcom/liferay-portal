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

import com.liferay.commerce.cart.constants.CCartConstants;
import com.liferay.commerce.cart.internal.portlet.action.ActionHelper;
import com.liferay.commerce.cart.internal.util.CCartPortletUtil;
import com.liferay.commerce.cart.model.CCart;
import com.liferay.commerce.cart.service.CCartLocalService;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CCartDisplayContext extends BaseCCartDisplayContext<CCart> {

	public CCartDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CCartLocalService cCartLocalService)
		throws PortalException {

		super(actionHelper, httpServletRequest, CCart.class.getSimpleName());

		setDefaultOrderByCol("title");

		_cCartLocalService = cCartLocalService;
	}

	@Override
	public SearchContainer<CCart> getSearchContainer() throws PortalException {
		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		if (getCCartType() == CCartConstants.C_CART_TYPE_CART) {
			searchContainer.setEmptyResultsMessage("no-carts-were-found");
		}
		else if (getCCartType() == CCartConstants.C_CART_TYPE_WISH_LIST) {
			searchContainer.setEmptyResultsMessage("no-wish-lists-were-found");
		}

		OrderByComparator<CCart> orderByComparator =
			CCartPortletUtil.getCCartOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		int total = _cCartLocalService.getCCartsCount(getCCartType());

		searchContainer.setTotal(total);

		List<CCart> results = _cCartLocalService.getCCarts(
			getCCartType(), searchContainer.getStart(),
			searchContainer.getEnd(), orderByComparator);

		searchContainer.setResults(results);

		return searchContainer;
	}

	private final CCartLocalService _cCartLocalService;

}