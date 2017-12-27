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

package com.liferay.commerce.shipping.engine.fixed.web.internal.display.context;

import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.model.CommerceWarehouse;
import com.liferay.commerce.service.CommerceCountryService;
import com.liferay.commerce.service.CommerceRegionService;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.commerce.service.CommerceWarehouseService;
import com.liferay.commerce.shipping.engine.fixed.constants.CommerceShippingEngineFixedWebKeys;
import com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.service.CShippingFixedOptionRelService;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionService;
import com.liferay.commerce.shipping.engine.fixed.util.CommerceShippingEngineFixedUtil;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CShippingFixedOptionRelsDisplayContext
	extends BaseCommerceShippingFixedOptionDisplayContext
		<CShippingFixedOptionRel> {

	public CShippingFixedOptionRelsDisplayContext(
		CommerceCountryService commerceCountryService,
		CommerceRegionService commerceRegionService,
		CommerceShippingMethodService commerceShippingMethodService,
		CommerceShippingFixedOptionService commerceShippingFixedOptionService,
		CommerceWarehouseService commerceWarehouseService,
		CShippingFixedOptionRelService cShippingFixedOptionRelService,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(commerceShippingMethodService, renderRequest, renderResponse);

		_commerceCountryService = commerceCountryService;
		_commerceRegionService = commerceRegionService;
		_commerceShippingFixedOptionService =
			commerceShippingFixedOptionService;
		_commerceWarehouseService = commerceWarehouseService;
		_cShippingFixedOptionRelService = cShippingFixedOptionRelService;

		setDefaultOrderByCol("country");
	}

	public List<CommerceCountry> getCommerceCountries() {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return _commerceCountryService.getCommerceCountries(
			themeDisplay.getScopeGroupId(), true);
	}

	public long getCommerceCountryId() {
		long commerceCountryId = 0;

		CShippingFixedOptionRel cShippingFixedOptionRel =
			getCShippingFixedOptionRel();

		if (cShippingFixedOptionRel != null) {
			commerceCountryId = cShippingFixedOptionRel.getCommerceCountryId();
		}

		return commerceCountryId;
	}

	public long getCommerceRegionId() {
		long commerceRegionId = 0;

		CShippingFixedOptionRel cShippingFixedOptionRel =
			getCShippingFixedOptionRel();

		if (cShippingFixedOptionRel != null) {
			commerceRegionId = cShippingFixedOptionRel.getCommerceRegionId();
		}

		return commerceRegionId;
	}

	public List<CommerceRegion> getCommerceRegions() {
		return _commerceRegionService.getCommerceRegions(
			getCommerceCountryId(), true);
	}

	public List<CommerceShippingFixedOption> getCommerceShippingFixedOptions()
		throws PortalException {

		return
			_commerceShippingFixedOptionService.getCommerceShippingFixedOptions(
				getCommerceShippingMethodId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);
	}

	public List<CommerceWarehouse> getCommerceWarehouses() {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		OrderByComparator<CommerceWarehouse> orderByComparator =
			CommerceUtil.getCommerceWarehouseOrderByComparator("name", "asc");

		return _commerceWarehouseService.getCommerceWarehouses(
			themeDisplay.getScopeGroupId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, orderByComparator);
	}

	public CShippingFixedOptionRel getCShippingFixedOptionRel() {
		CShippingFixedOptionRel cShippingFixedOptionRel =
			(CShippingFixedOptionRel)renderRequest.getAttribute(
				CommerceShippingEngineFixedWebKeys.
					COMMERCE_SHIPPING_FIXED_OPTION_REL);

		if (cShippingFixedOptionRel != null) {
			return cShippingFixedOptionRel;
		}

		long cShippingFixedOptionRelId = ParamUtil.getLong(
			renderRequest, "cShippingFixedOptionRelId");

		if (cShippingFixedOptionRelId > 0) {
			cShippingFixedOptionRel =
				_cShippingFixedOptionRelService.fetchCShippingFixedOptionRel(
					cShippingFixedOptionRelId);
		}

		if (cShippingFixedOptionRel != null) {
			renderRequest.setAttribute(
				CommerceShippingEngineFixedWebKeys.
					COMMERCE_SHIPPING_FIXED_OPTION_REL,
				cShippingFixedOptionRel);
		}

		return cShippingFixedOptionRel;
	}

	@Override
	public String getScreenNavigationEntryKey() {
		return "shipping-option-settings";
	}

	@Override
	public SearchContainer<CShippingFixedOptionRel> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			renderRequest, getPortletURL(), null, null);

		searchContainer.setEmptyResultsMessage(
			"there-are-no-shipping-option-settings");

		OrderByComparator<CShippingFixedOptionRel> orderByComparator =
			CommerceShippingEngineFixedUtil.
				getCShippingFixedOptionRelOrderByComparator(
					getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		int total =
			_cShippingFixedOptionRelService.
				getCommerceShippingMethodFixedOptionRelsCount(
					getCommerceShippingMethodId());

		searchContainer.setTotal(total);

		List<CShippingFixedOptionRel> results =
			_cShippingFixedOptionRelService.
				getCommerceShippingMethodFixedOptionRels(
					getCommerceShippingMethodId(), searchContainer.getStart(),
					searchContainer.getEnd(), orderByComparator);

		searchContainer.setResults(results);

		return searchContainer;
	}

	public boolean isVisible() throws PortalException {
		List<CommerceShippingFixedOption> commerceShippingFixedOptions =
			getCommerceShippingFixedOptions();

		if (commerceShippingFixedOptions.isEmpty()) {
			return false;
		}

		return true;
	}

	private final CommerceCountryService _commerceCountryService;
	private final CommerceRegionService _commerceRegionService;
	private final CommerceShippingFixedOptionService
		_commerceShippingFixedOptionService;
	private final CommerceWarehouseService _commerceWarehouseService;
	private final CShippingFixedOptionRelService
		_cShippingFixedOptionRelService;

}