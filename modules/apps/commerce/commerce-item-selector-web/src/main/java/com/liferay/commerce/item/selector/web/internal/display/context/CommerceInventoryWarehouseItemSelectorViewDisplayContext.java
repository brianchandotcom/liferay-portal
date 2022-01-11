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

import com.liferay.commerce.country.CommerceCountryManager;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseService;
import com.liferay.commerce.item.selector.web.internal.search.CommerceInventoryWarehouseChecker;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.frontend.taglib.servlet.taglib.ManagementBarFilterItem;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class CommerceInventoryWarehouseItemSelectorViewDisplayContext
	extends BaseCommerceItemSelectorViewDisplayContext
		<CommerceInventoryWarehouse> {

	public CommerceInventoryWarehouseItemSelectorViewDisplayContext(
		CommerceCountryManager commerceCountryManager,
		CommerceInventoryWarehouseService commerceInventoryWarehouseService,
		CountryService countryService, HttpServletRequest httpServletRequest,
		PortletURL portletURL, String itemSelectedEventName, boolean search) {

		super(httpServletRequest, portletURL, itemSelectedEventName);

		_commerceCountryManager = commerceCountryManager;
		_commerceInventoryWarehouseService = commerceInventoryWarehouseService;
		_countryService = countryService;
		_search = search;
	}

	public long getCountryId() {
		return ParamUtil.getLong(
			cpRequestHelper.getRenderRequest(), "countryId", -1);
	}

	public List<ManagementBarFilterItem> getManagementBarFilterItems()
		throws PortalException, PortletException {

		List<Country> countries = _commerceCountryManager.getWarehouseCountries(
			cpRequestHelper.getCompanyId(), false);

		List<ManagementBarFilterItem> managementBarFilterItems =
			new ArrayList<>(countries.size() + 2);

		managementBarFilterItems.add(getManagementBarFilterItem(-1, "all"));
		managementBarFilterItems.add(getManagementBarFilterItem(0, "none"));

		for (Country country : countries) {
			managementBarFilterItems.add(
				getManagementBarFilterItem(
					country.getCountryId(),
					country.getName(cpRequestHelper.getLocale())));
		}

		return managementBarFilterItems;
	}

	@Override
	public PortletURL getPortletURL() {
		return PortletURLBuilder.create(
			super.getPortletURL()
		).setParameter(
			"countryId", getCountryId()
		).buildPortletURL();
	}

	@Override
	public SearchContainer<CommerceInventoryWarehouse> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		long countryId = getCountryId();

		String emptyResultsMessage = "there-are-no-warehouses";

		if (_search) {
			emptyResultsMessage = "no-warehouses-were-found";
		}

		Country country = null;

		if (countryId > 0) {
			emptyResultsMessage += "-in-x";

			country = _countryService.getCountry(countryId);

			Locale locale = cpRequestHelper.getLocale();

			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", locale, getClass());

			emptyResultsMessage = LanguageUtil.format(
				resourceBundle, emptyResultsMessage, country.getName(locale),
				false);
		}

		searchContainer = new SearchContainer<>(
			cpRequestHelper.getRenderRequest(), getPortletURL(), null,
			emptyResultsMessage);

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<CommerceInventoryWarehouse> orderByComparator =
			CommerceUtil.getCommerceInventoryWarehouseOrderByComparator(
				orderByCol, orderByType);

		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(orderByType);
		searchContainer.setRowChecker(
			new CommerceInventoryWarehouseChecker(
				cpRequestHelper.getRenderResponse(),
				_getCheckedCommerceInventoryWarehouseIds(),
				_getDisabledCommerceInventoryWarehouseIds()));
		searchContainer.setSearch(_search);

		String a2 = country.getA2();

		if (searchContainer.isSearch() && (country != null)) {
			searchContainer.setResultsAndTotal(
				() -> _commerceInventoryWarehouseService.search(
					cpRequestHelper.getCompanyId(), true, a2, getKeywords(),
					searchContainer.getStart(), searchContainer.getEnd(),
					CommerceUtil.getCommerceInventoryWarehouseSort(
						orderByCol, orderByType)),
				_commerceInventoryWarehouseService.
					searchCommerceInventoryWarehousesCount(
						cpRequestHelper.getCompanyId(), true, a2,
						getKeywords()));
		}
		else if (country != null) {
			searchContainer.setResultsAndTotal(
				() ->
					_commerceInventoryWarehouseService.
						getCommerceInventoryWarehouses(
							cpRequestHelper.getCompanyId(), true, a2,
							searchContainer.getStart(),
							searchContainer.getEnd(), orderByComparator),
				_commerceInventoryWarehouseService.
					getCommerceInventoryWarehousesCount(
						cpRequestHelper.getCompanyId(), true, a2));
		}

		return searchContainer;
	}

	protected ManagementBarFilterItem getManagementBarFilterItem(
			long countryId, String label)
		throws PortletException {

		boolean active = false;

		if (getCountryId() == countryId) {
			active = true;
		}

		return new ManagementBarFilterItem(
			active, String.valueOf(countryId), label,
			PortletURLBuilder.create(
				PortletURLUtil.clone(
					getPortletURL(), cpRequestHelper.getRenderResponse())
			).setParameter(
				"countryId", countryId
			).buildString());
	}

	private long[] _getCheckedCommerceInventoryWarehouseIds() {
		return ParamUtil.getLongValues(
			cpRequestHelper.getRenderRequest(),
			"checkedCommerceInventoryWarehouseIds");
	}

	private long[] _getDisabledCommerceInventoryWarehouseIds() {
		return ParamUtil.getLongValues(
			cpRequestHelper.getRenderRequest(),
			"disabledCommerceInventoryWarehouseIds");
	}

	private final CommerceCountryManager _commerceCountryManager;
	private final CommerceInventoryWarehouseService
		_commerceInventoryWarehouseService;
	private final CountryService _countryService;
	private final boolean _search;

}