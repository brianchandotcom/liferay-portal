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

package com.liferay.address.web.internal.dao.search;

import com.liferay.address.web.internal.constants.AddressPortletKeys;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.service.CountryServiceUtil;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

/**
 * @author Pei-Jung Lan
 */
public class CountrySearchContainerFactory {

	public static SearchContainer<Country> create(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		Boolean active = null;
		String emptyResultsMessage = "there-are-no-countries";

		String navigation = ParamUtil.getString(
			liferayPortletRequest, "navigation", "all");

		if (navigation.equals("active")) {
			active = Boolean.TRUE;
			emptyResultsMessage = "there-are-no-active-countries";
		}
		else if (navigation.equals("inactive")) {
			active = Boolean.FALSE;
			emptyResultsMessage = "there-are-no-inactive-countries";
		}

		SearchContainer<Country> searchContainer = new SearchContainer(
			liferayPortletRequest,
			PortletURLUtil.getCurrent(
				liferayPortletRequest, liferayPortletResponse),
			null, emptyResultsMessage);

		searchContainer.setId("countrySearchContainer");
		searchContainer.setOrderByCol(
			SearchOrderByUtil.getOrderByCol(
				liferayPortletRequest,
				AddressPortletKeys.COUNTRIES_MANAGEMENT_ADMIN, "order-by-col",
				"priority"));
		searchContainer.setOrderByType(
			SearchOrderByUtil.getOrderByType(
				liferayPortletRequest,
				AddressPortletKeys.COUNTRIES_MANAGEMENT_ADMIN, "order-by-type",
				"asc"));

		String keywords = ParamUtil.getString(
			liferayPortletRequest, "keywords");

		if (Validator.isNotNull(keywords)) {
			searchContainer.setResultsAndTotal(
				CountryServiceUtil.searchCountries(
					PortalUtil.getCompanyId(liferayPortletRequest), active,
					keywords, searchContainer.getStart(),
					searchContainer.getEnd(),
					OrderByComparatorFactoryUtil.create(
						"Country", searchContainer.getOrderByCol(),
						Objects.equals(
							searchContainer.getOrderByType(), "asc"))));
		}
		else {
			if (active == null) {
				searchContainer.setResultsAndTotal(
					() -> CountryServiceUtil.getCompanyCountries(
						PortalUtil.getCompanyId(liferayPortletRequest),
						searchContainer.getStart(), searchContainer.getEnd(),
						searchContainer.getOrderByComparator()),
					CountryServiceUtil.getCompanyCountriesCount(
						PortalUtil.getCompanyId(liferayPortletRequest)));
			}
			else {
				boolean navigationActive = active;

				searchContainer.setResultsAndTotal(
					() -> CountryServiceUtil.getCompanyCountries(
						PortalUtil.getCompanyId(liferayPortletRequest),
						navigationActive, searchContainer.getStart(),
						searchContainer.getEnd(),
						searchContainer.getOrderByComparator()),
					CountryServiceUtil.getCompanyCountriesCount(
						PortalUtil.getCompanyId(liferayPortletRequest),
						navigationActive));
			}
		}

		searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(liferayPortletResponse));

		return searchContainer;
	}

}