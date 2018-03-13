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

package com.liferay.commerce.tax.engine.fixed.web.internal.display.context;

import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.service.CommerceCountryService;
import com.liferay.commerce.service.CommerceRegionService;
import com.liferay.commerce.service.CommerceTaxMethodService;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel;
import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateAddressRelService;
import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateService;
import com.liferay.commerce.tax.engine.fixed.util.CommerceTaxEngineFixedUtil;
import com.liferay.commerce.tax.engine.fixed.web.internal.servlet.taglib.ui.CommerceTaxMethodAddressRateRelsScreenNavigationEntry;
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
public class CommerceTaxFixedRateAddressRelsDisplayContext
	extends BaseCommerceTaxFixedRateDisplayContext
		<CommerceTaxFixedRateAddressRel> {

	public CommerceTaxFixedRateAddressRelsDisplayContext(
		CommerceCountryService commerceCountryService,
		CommerceCurrencyService commerceCurrencyService,
		CommerceRegionService commerceRegionService,
		CommerceTaxFixedRateService commerceTaxFixedRateService,
		CommerceTaxMethodService commerceTaxMethodService,
		CommerceTaxFixedRateAddressRelService
			commerceTaxFixedRateAddressRelService,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(
			commerceCurrencyService, commerceTaxMethodService, renderRequest,
			renderResponse);

		_commerceCountryService = commerceCountryService;
		_commerceRegionService = commerceRegionService;
		_commerceTaxFixedRateService = commerceTaxFixedRateService;
		_commerceTaxFixedRateAddressRelService =
			commerceTaxFixedRateAddressRelService;
	}

	public List<CommerceCountry> getCommerceCountries() {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return _commerceCountryService.getCommerceCountries(
			themeDisplay.getScopeGroupId(), true);
	}

	public long getCommerceCountryId() {
		long commerceCountryId = 0;

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			getCommerceTaxFixedRateAddressRel();

		if (commerceTaxFixedRateAddressRel != null) {
			commerceCountryId =
				commerceTaxFixedRateAddressRel.getCommerceCountryId();
		}

		return commerceCountryId;
	}

	public long getCommerceRegionId() {
		long commerceRegionId = 0;

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			getCommerceTaxFixedRateAddressRel();

		if (commerceTaxFixedRateAddressRel != null) {
			commerceRegionId =
				commerceTaxFixedRateAddressRel.getCommerceRegionId();
		}

		return commerceRegionId;
	}

	public List<CommerceRegion> getCommerceRegions() {
		return _commerceRegionService.getCommerceRegions(
			getCommerceCountryId(), true);
	}

	public CommerceTaxFixedRateAddressRel getCommerceTaxFixedRateAddressRel() {
		long commerceTaxFixedRateAddressRelId = ParamUtil.getLong(
			renderRequest, "commerceTaxFixedRateAddressRelId");

		return _commerceTaxFixedRateAddressRelService.
			fetchCommerceTaxFixedRateAddressRel(
				commerceTaxFixedRateAddressRelId);
	}

	public List<CommerceTaxFixedRate> getCommerceTaxFixedRates()
		throws PortalException {

		return _commerceTaxFixedRateService.getCommerceTaxFixedRates(
			getCommerceTaxMethodId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	public String getScreenNavigationEntryKey() {
		return CommerceTaxMethodAddressRateRelsScreenNavigationEntry.ENTRY_KEY;
	}

	@Override
	public SearchContainer<CommerceTaxFixedRateAddressRel> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			renderRequest, getPortletURL(), null, null);

		searchContainer.setEmptyResultsMessage(
			"there-are-no-tax-rate-settings");

		OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator =
			CommerceTaxEngineFixedUtil.
				getCommerceTaxFixedRateAddressRelOrderByComparator(
					getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		int total =
			_commerceTaxFixedRateAddressRelService.
				getCommerceTaxMethodFixedRateAddressRelsCount(
					getCommerceTaxMethodId());

		searchContainer.setTotal(total);

		List<CommerceTaxFixedRateAddressRel> results =
			_commerceTaxFixedRateAddressRelService.
				getCommerceTaxMethodFixedRateAddressRels(
					getCommerceTaxMethodId(), searchContainer.getStart(),
					searchContainer.getEnd(), orderByComparator);

		searchContainer.setResults(results);

		return searchContainer;
	}

	public boolean isVisible() throws PortalException {
		List<CommerceTaxFixedRate> commerceTaxFixedRates =
			getCommerceTaxFixedRates();

		if (commerceTaxFixedRates.isEmpty()) {
			return false;
		}

		return true;
	}

	private final CommerceCountryService _commerceCountryService;
	private final CommerceRegionService _commerceRegionService;
	private final CommerceTaxFixedRateAddressRelService
		_commerceTaxFixedRateAddressRelService;
	private final CommerceTaxFixedRateService _commerceTaxFixedRateService;

}