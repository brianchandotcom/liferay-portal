/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.item.selector.web.internal.display.context;

import com.liferay.commerce.country.CommerceCountryManager;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseService;
import com.liferay.commerce.item.selector.web.internal.search.CommerceInventoryWarehouseChecker;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

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

	public List<Country> getCountries() {
		return _commerceCountryManager.getWarehouseCountries(
			cpRequestHelper.getCompanyId(), false);
	}

	public long getCountryId() {
		return ParamUtil.getLong(
			cpRequestHelper.getRenderRequest(), "countryId", -1);
	}

	public String getCountryName() {
		Country country = _countryService.fetchCountry(getCountryId());

		if (country == null) {
			return StringPool.BLANK;
		}

		return country.getName(cpRequestHelper.getLocale());
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

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(
			CommerceUtil.getCommerceInventoryWarehouseOrderByComparator(
				getOrderByCol(), getOrderByType()));
		searchContainer.setOrderByType(getOrderByType());

		String a2 = country.getA2();

		if (_search && (country != null)) {
			searchContainer.setResultsAndTotal(
				() -> _commerceInventoryWarehouseService.search(
					cpRequestHelper.getCompanyId(), true, a2, getKeywords(),
					searchContainer.getStart(), searchContainer.getEnd(),
					CommerceUtil.getCommerceInventoryWarehouseSort(
						searchContainer.getOrderByCol(),
						searchContainer.getOrderByType())),
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
							searchContainer.getEnd(),
							searchContainer.getOrderByComparator()),
				_commerceInventoryWarehouseService.
					getCommerceInventoryWarehousesCount(
						cpRequestHelper.getCompanyId(), true, a2));
		}

		searchContainer.setRowChecker(
			new CommerceInventoryWarehouseChecker(
				cpRequestHelper.getRenderResponse(),
				_getCheckedCommerceInventoryWarehouseIds(),
				_getDisabledCommerceInventoryWarehouseIds()));
		searchContainer.setSearch(_search);

		return searchContainer;
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