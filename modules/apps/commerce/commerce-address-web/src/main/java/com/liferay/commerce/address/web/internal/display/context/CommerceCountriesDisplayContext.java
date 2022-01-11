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

package com.liferay.commerce.address.web.internal.display.context;

import com.liferay.commerce.address.web.internal.display.context.helper.CommerceCountryRequestHelper;
import com.liferay.commerce.address.web.internal.portlet.action.helper.ActionHelper;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.service.CommerceChannelRelService;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.starter.CommerceRegionsStarter;
import com.liferay.commerce.starter.CommerceRegionsStarterRegistry;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.RegionServiceUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.stream.Stream;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceCountriesDisplayContext
	extends BaseCommerceCountriesDisplayContext<Country> {

	public CommerceCountriesDisplayContext(
		ActionHelper actionHelper,
		CommerceChannelRelService commerceChannelRelService,
		CommerceChannelService commerceChannelService,
		CommerceRegionsStarterRegistry commerceRegionsStarterRegistry,
		CountryService countryService,
		PortletResourcePermission portletResourcePermission,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(
			actionHelper, portletResourcePermission, renderRequest,
			renderResponse);

		_commerceChannelRelService = commerceChannelRelService;
		_commerceChannelService = commerceChannelService;
		_commerceRegionsStarterRegistry = commerceRegionsStarterRegistry;
		_countryService = countryService;

		_commerceCountryRequestHelper = new CommerceCountryRequestHelper(
			renderRequest);
	}

	public long[] getCommerceChannelRelCommerceChannelIds()
		throws PortalException {

		List<CommerceChannelRel> commerceChannelRels =
			_commerceChannelRelService.getCommerceChannelRels(
				Country.class.getName(), getCountryId(), null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Stream<CommerceChannelRel> stream = commerceChannelRels.stream();

		return stream.mapToLong(
			CommerceChannelRel::getCommerceChannelId
		).toArray();
	}

	public List<CommerceChannel> getCommerceChannels() throws PortalException {
		return _commerceChannelService.getCommerceChannels(
			_commerceCountryRequestHelper.getCompanyId());
	}

	public CommerceRegionsStarter getCommerceRegionsStarter()
		throws PortalException {

		Country country = getCountry();

		if (country == null) {
			return null;
		}

		return _commerceRegionsStarterRegistry.getCommerceRegionsStarter(
			String.valueOf(country.getNumber()));
	}

	@Override
	public SearchContainer<Country> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		Boolean active = null;
		String emptyResultsMessage = "there-are-no-countries";

		String navigation = getNavigation();

		if (navigation.equals("active")) {
			active = Boolean.TRUE;
			emptyResultsMessage = "there-are-no-active-countries";
		}
		else if (navigation.equals("inactive")) {
			active = Boolean.FALSE;
			emptyResultsMessage = "there-are-no-inactive-countries";
		}

		searchContainer = new SearchContainer<>(
			renderRequest, getPortletURL(), null, emptyResultsMessage);

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<Country> orderByComparator =
			CommerceUtil.getCountryOrderByComparator(orderByCol, orderByType);

		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(orderByType);
		searchContainer.setRowChecker(getRowChecker());

		if (_isSearch()) {
			searchContainer.setResultsAndTotal(
				_countryService.searchCountries(
					_commerceCountryRequestHelper.getCompanyId(), active,
					_getKeywords(), searchContainer.getStart(),
					searchContainer.getEnd(), orderByComparator));
		}
		else {
			if (active == null) {
				searchContainer.setResultsAndTotal(
					() -> _countryService.getCompanyCountries(
						_commerceCountryRequestHelper.getCompanyId(),
						searchContainer.getStart(), searchContainer.getEnd(),
						orderByComparator),
					_countryService.getCompanyCountriesCount(
						_commerceCountryRequestHelper.getCompanyId()));
			}
			else {
				boolean navigationActive = active;

				searchContainer.setResultsAndTotal(
					() -> _countryService.getCompanyCountries(
						_commerceCountryRequestHelper.getCompanyId(),
						navigationActive, searchContainer.getStart(),
						searchContainer.getEnd(), orderByComparator),
					_countryService.getCompanyCountriesCount(
						_commerceCountryRequestHelper.getCompanyId(),
						navigationActive));
			}
		}

		return searchContainer;
	}

	public boolean hasRegions(Country country) {
		List<Region> regions = RegionServiceUtil.getRegions(
			country.getCountryId());

		return !regions.isEmpty();
	}

	private String _getKeywords() {
		if (Validator.isNotNull(_keywords)) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(renderRequest, "keywords");

		return _keywords;
	}

	private boolean _isSearch() {
		if (Validator.isNotNull(_getKeywords())) {
			return true;
		}

		return false;
	}

	private final CommerceChannelRelService _commerceChannelRelService;
	private final CommerceChannelService _commerceChannelService;
	private final CommerceCountryRequestHelper _commerceCountryRequestHelper;
	private final CommerceRegionsStarterRegistry
		_commerceRegionsStarterRegistry;
	private final CountryService _countryService;
	private String _keywords;

}