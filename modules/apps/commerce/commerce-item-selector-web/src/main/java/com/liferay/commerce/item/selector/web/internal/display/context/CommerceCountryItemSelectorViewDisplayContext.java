/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.item.selector.web.internal.display.context;

import com.liferay.commerce.item.selector.web.internal.search.CommerceCountryItemSelectorChecker;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
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
			cpRequestHelper.getRenderRequest(), getPortletURL(), null,
			"there-are-no-countries");

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(
			CommerceUtil.getCountryOrderByComparator(
				getOrderByCol(), getOrderByType()));
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setResultsAndTotal(
			() -> _countryService.getCompanyCountries(
				themeDisplay.getCompanyId(), true, searchContainer.getStart(),
				searchContainer.getEnd(),
				searchContainer.getOrderByComparator()),
			_countryService.getCompanyCountriesCount(
				themeDisplay.getCompanyId()));
		searchContainer.setRowChecker(
			new CommerceCountryItemSelectorChecker(
				cpRequestHelper.getRenderResponse(), _getCheckedCountryIds()));

		return searchContainer;
	}

	private long[] _getCheckedCountryIds() {
		return ParamUtil.getLongValues(
			cpRequestHelper.getRenderRequest(), "checkedCountryIds");
	}

	private final CountryService _countryService;

}