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

import com.liferay.commerce.item.selector.web.internal.search.CommerceCountryItemSelectorChecker;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.service.CommerceCountryService;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceCountryItemSelectorViewDisplayContext
	extends BaseCommerceItemSelectorViewDisplayContext<CommerceCountry> {

	public CommerceCountryItemSelectorViewDisplayContext(
		CommerceCountryService commerceCountryService,
		HttpServletRequest httpServletRequest, PortletURL portletURL,
		String itemSelectedEventName) {

		super(httpServletRequest, portletURL, itemSelectedEventName);

		_commerceCountryService = commerceCountryService;

		setDefaultOrderByCol("priority");
		setDefaultOrderByType("asc");
	}

	@Override
	public PortletURL getPortletURL() {
		PortletURL portletURL = super.getPortletURL();

		String checkedCommerceCountryIds = StringUtil.merge(
			getCheckedCommerceCountryIds());

		portletURL.setParameter(
			"checkedCommerceCountryIds", checkedCommerceCountryIds);

		return portletURL;
	}

	public SearchContainer<CommerceCountry> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		searchContainer = new SearchContainer<>(
			cpRequestHelper.getRenderRequest(), getPortletURL(), null, null);

		searchContainer.setEmptyResultsMessage("there-are-no-countries");

		OrderByComparator<CommerceCountry> orderByComparator =
			CommerceUtil.getCommerceCountryOrderByComparator(
				getOrderByCol(), getOrderByType());

		RowChecker rowChecker = new CommerceCountryItemSelectorChecker(
			cpRequestHelper.getRenderResponse(),
			getCheckedCommerceCountryIds());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(rowChecker);

		int total = _commerceCountryService.getCommerceCountriesCount(
			themeDisplay.getScopeGroupId());

		searchContainer.setTotal(total);

		List<CommerceCountry> results =
			_commerceCountryService.getCommerceCountries(
				themeDisplay.getScopeGroupId(), true,
				searchContainer.getStart(), searchContainer.getEnd(),
				orderByComparator);

		searchContainer.setResults(results);

		return searchContainer;
	}

	protected long[] getCheckedCommerceCountryIds() {
		return ParamUtil.getLongValues(
			cpRequestHelper.getRenderRequest(), "checkedCommerceCountryIds");
	}

	private final CommerceCountryService _commerceCountryService;

}