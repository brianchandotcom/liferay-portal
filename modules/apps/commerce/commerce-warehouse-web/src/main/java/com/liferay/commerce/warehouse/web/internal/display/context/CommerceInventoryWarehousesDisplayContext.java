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

package com.liferay.commerce.warehouse.web.internal.display.context;

import com.liferay.commerce.country.CommerceCountryManager;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseService;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.service.CommerceChannelRelService;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.frontend.taglib.servlet.taglib.ManagementBarFilterItem;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class CommerceInventoryWarehousesDisplayContext {

	public CommerceInventoryWarehousesDisplayContext(
		CommerceChannelRelService commerceChannelRelService,
		CommerceChannelService commerceChannelService,
		CommerceCountryManager commerceCountryManager,
		CommerceInventoryWarehouseService commerceInventoryWarehouseService,
		CountryService countryService, HttpServletRequest httpServletRequest) {

		_commerceChannelRelService = commerceChannelRelService;
		_commerceChannelService = commerceChannelService;
		_commerceCountryManager = commerceCountryManager;
		_commerceInventoryWarehouseService = commerceInventoryWarehouseService;
		_countryService = countryService;

		_cpRequestHelper = new CPRequestHelper(httpServletRequest);
	}

	public long[] getCommerceChannelRelCommerceChannelIds()
		throws PortalException {

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			getCommerceInventoryWarehouse();

		if (commerceInventoryWarehouse == null) {
			return new long[0];
		}

		List<CommerceChannelRel> commerceChannelRels =
			_commerceChannelRelService.getCommerceChannelRels(
				CommerceInventoryWarehouse.class.getName(),
				commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
				null, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Stream<CommerceChannelRel> stream = commerceChannelRels.stream();

		return stream.mapToLong(
			CommerceChannelRel::getCommerceChannelId
		).toArray();
	}

	public List<CommerceChannel> getCommerceChannels() throws PortalException {
		return _commerceChannelService.getCommerceChannels(
			_cpRequestHelper.getCompanyId());
	}

	public CommerceInventoryWarehouse getCommerceInventoryWarehouse()
		throws PortalException {

		if (_commerceInventoryWarehouse != null) {
			return _commerceInventoryWarehouse;
		}

		long commerceInventoryWarehouseId = ParamUtil.getLong(
			_cpRequestHelper.getRenderRequest(),
			"commerceInventoryWarehouseId");

		if (commerceInventoryWarehouseId > 0) {
			_commerceInventoryWarehouse =
				_commerceInventoryWarehouseService.
					getCommerceInventoryWarehouse(commerceInventoryWarehouseId);
		}

		return _commerceInventoryWarehouse;
	}

	public Country getCountry(long countryId) throws PortalException {
		return _countryService.getCountry(countryId);
	}

	public Country getCountry(String countryTwoLettersIsoCode)
		throws PortalException {

		return _countryService.getCountryByA2(
			_cpRequestHelper.getCompanyId(), countryTwoLettersIsoCode);
	}

	public String getCountryTwoLettersIsoCode() {
		return ParamUtil.getString(
			_cpRequestHelper.getRenderRequest(), "countryTwoLettersISOCode",
			null);
	}

	public List<ManagementBarFilterItem> getManagementBarFilterItems()
		throws PortalException, PortletException {

		List<Country> countries = _commerceCountryManager.getWarehouseCountries(
			_cpRequestHelper.getCompanyId(), true);

		countries = ListUtil.unique(countries);

		List<ManagementBarFilterItem> managementBarFilterItems =
			new ArrayList<>(countries.size() + 1);

		managementBarFilterItems.add(_getManagementBarFilterItem(-1, "all"));

		for (Country country : countries) {
			managementBarFilterItems.add(
				_getManagementBarFilterItem(
					country.getCountryId(),
					country.getName(_cpRequestHelper.getLocale())));
		}

		return managementBarFilterItems;
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			_cpRequestHelper.getRenderRequest(),
			CPPortletKeys.COMMERCE_INVENTORY_WAREHOUSE, "name");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			_cpRequestHelper.getRenderRequest(),
			CPPortletKeys.COMMERCE_INVENTORY_WAREHOUSE, "asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		return PortletURLBuilder.createRenderURL(
			_cpRequestHelper.getRenderResponse()
		).setKeywords(
			_getKeywords()
		).setNavigation(
			_getNavigation()
		).setParameter(
			"countryTwoLettersISOCode", getCountryTwoLettersIsoCode()
		).setParameter(
			"delta",
			() -> {
				String delta = ParamUtil.getString(
					_cpRequestHelper.getRenderRequest(), "delta");

				if (Validator.isNotNull(delta)) {
					return delta;
				}

				return null;
			}
		).setParameter(
			"orderByCol", getOrderByCol()
		).setParameter(
			"orderByType", getOrderByType()
		).buildPortletURL();
	}

	public SearchContainer<CommerceInventoryWarehouse> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		Boolean active = null;
		String countryTwoLettersIsoCode = getCountryTwoLettersIsoCode();

		String emptyResultsMessage = "no-warehouses-were-found";
		boolean search = _isSearch();

		String navigation = _getNavigation();

		if (navigation.equals("active")) {
			active = Boolean.TRUE;
			emptyResultsMessage = "there-are-no-active-warehouses";
		}
		else if (navigation.equals("inactive")) {
			active = Boolean.FALSE;
			emptyResultsMessage = "there-are-no-inactive-warehouses";
		}

		if (Validator.isNotNull(countryTwoLettersIsoCode)) {
			emptyResultsMessage += "-in-x";

			Country country = getCountry(countryTwoLettersIsoCode);

			emptyResultsMessage = LanguageUtil.format(
				_cpRequestHelper.getRequest(), emptyResultsMessage,
				country.getTitle(_cpRequestHelper.getLocale()));
		}

		_searchContainer = new SearchContainer<>(
			_cpRequestHelper.getRenderRequest(), getPortletURL(), null,
			emptyResultsMessage);

		if (!search && hasManageCommerceInventoryWarehousePermission()) {
			_searchContainer.setEmptyResultsMessageCssClass(
				"taglib-empty-result-message-header-has-plus-btn");
		}

		_searchContainer.setOrderByCol(getOrderByCol());
		_searchContainer.setOrderByComparator(
			CommerceUtil.getCommerceInventoryWarehouseOrderByComparator(
				getOrderByCol(), getOrderByType()));
		_searchContainer.setOrderByType(getOrderByType());

		Boolean navigationActive = active;

		_searchContainer.setResultsAndTotal(
			() -> _commerceInventoryWarehouseService.search(
				_cpRequestHelper.getCompanyId(), navigationActive,
				countryTwoLettersIsoCode, _getKeywords(),
				_searchContainer.getStart(), _searchContainer.getEnd(),
				CommerceUtil.getCommerceInventoryWarehouseSort(
					_searchContainer.getOrderByCol(),
					_searchContainer.getOrderByType())),
			_commerceInventoryWarehouseService.
				searchCommerceInventoryWarehousesCount(
					_cpRequestHelper.getCompanyId(), navigationActive,
					countryTwoLettersIsoCode, _getKeywords()));

		_searchContainer.setSearch(search);

		return _searchContainer;
	}

	public boolean hasManageCommerceInventoryWarehousePermission() {
		return true;
	}

	private String _getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(
			_cpRequestHelper.getRenderRequest(), "keywords");

		return _keywords;
	}

	private ManagementBarFilterItem _getManagementBarFilterItem(
			long countryId, String label)
		throws PortalException, PortletException {

		boolean active = false;

		PortletURL portletURL = PortletURLUtil.clone(
			getPortletURL(), _cpRequestHelper.getRenderResponse());

		if (countryId > 0) {
			String countryTwoLettersIsoCode = getCountryTwoLettersIsoCode();
			Country country = getCountry(countryId);

			if (Validator.isNotNull(countryTwoLettersIsoCode) &&
				countryTwoLettersIsoCode.equals(country.getA2())) {

				active = true;
			}

			portletURL.setParameter(
				"countryTwoLettersISOCode", country.getA2());
		}
		else {
			portletURL.setParameter(
				"countryTwoLettersISOCode", StringPool.BLANK);
		}

		return new ManagementBarFilterItem(
			active, String.valueOf(countryId), label, portletURL.toString());
	}

	private String _getNavigation() {
		return ParamUtil.getString(
			_cpRequestHelper.getRenderRequest(), "navigation");
	}

	private boolean _isSearch() {
		if (Validator.isNotNull(_getKeywords())) {
			return true;
		}

		return false;
	}

	private final CommerceChannelRelService _commerceChannelRelService;
	private final CommerceChannelService _commerceChannelService;
	private final CommerceCountryManager _commerceCountryManager;
	private CommerceInventoryWarehouse _commerceInventoryWarehouse;
	private final CommerceInventoryWarehouseService
		_commerceInventoryWarehouseService;
	private final CountryService _countryService;
	private final CPRequestHelper _cpRequestHelper;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private SearchContainer<CommerceInventoryWarehouse> _searchContainer;

}