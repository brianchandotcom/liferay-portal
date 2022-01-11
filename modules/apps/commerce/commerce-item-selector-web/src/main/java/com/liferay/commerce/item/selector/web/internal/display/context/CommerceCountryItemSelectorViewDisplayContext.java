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
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceCountryItemSelectorViewDisplayContext
	extends BaseCommerceItemSelectorViewDisplayContext<Country> {

	public CommerceCountryItemSelectorViewDisplayContext(
		CountryService countryService, HttpServletRequest httpServletRequest,
		PortletURL portletURL, String itemSelectedEventName) {

		super(httpServletRequest, portletURL, itemSelectedEventName);

		_countryService = countryService;

		setDefaultOrderByCol("priority");
		setDefaultOrderByType("asc");
	}

	@Override
	public PortletURL getPortletURL() {
		return PortletURLBuilder.create(
			super.getPortletURL()
		).setParameter(
			"checkedCountryIds", StringUtil.merge(_getCheckedCountryIds())
		).buildPortletURL();
	}

	@Override
	public SearchContainer<Country> getSearchContainer()
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

		searchContainer.setOrderByCol(getOrderByCol());

		OrderByComparator<Country> orderByComparator =
			CommerceUtil.getCountryOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByComparator(orderByComparator);

		searchContainer.setOrderByType(getOrderByType());

		RowChecker rowChecker = new CommerceCountryItemSelectorChecker(
			cpRequestHelper.getRenderResponse(), _getCheckedCountryIds());

		searchContainer.setRowChecker(rowChecker);

		searchContainer.setResultsAndTotal(
			() -> _countryService.getCompanyCountries(
				themeDisplay.getCompanyId(), true, searchContainer.getStart(),
				searchContainer.getEnd(), orderByComparator),
			_countryService.getCompanyCountriesCount(
				themeDisplay.getCompanyId()));

		return searchContainer;
	}

	private long[] _getCheckedCountryIds() {
		return ParamUtil.getLongValues(
			cpRequestHelper.getRenderRequest(), "checkedCountryIds");
	}

	private final CountryService _countryService;

}