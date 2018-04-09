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
import com.liferay.commerce.model.CommerceTaxCategory;
import com.liferay.commerce.service.CommerceCountryService;
import com.liferay.commerce.service.CommerceRegionService;
import com.liferay.commerce.service.CommerceTaxCategoryService;
import com.liferay.commerce.service.CommerceTaxMethodService;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel;
import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateAddressRelService;
import com.liferay.commerce.tax.engine.fixed.util.CommerceTaxEngineFixedUtil;
import com.liferay.commerce.tax.engine.fixed.web.internal.servlet.taglib.ui.CommerceTaxMethodAddressRateRelsScreenNavigationEntry;
import com.liferay.commerce.util.comparator.CommerceTaxCategoryCreateDateComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceTaxFixedRateAddressRelsDisplayContext
	extends BaseCommerceTaxFixedRateDisplayContext
		<CommerceTaxFixedRateAddressRel> {

	public CommerceTaxFixedRateAddressRelsDisplayContext(
		CommerceCountryService commerceCountryService,
		CommerceCurrencyService commerceCurrencyService,
		CommerceRegionService commerceRegionService,
		CommerceTaxCategoryService commerceTaxCategoryService,
		CommerceTaxMethodService commerceTaxMethodService,
		CommerceTaxFixedRateAddressRelService
			commerceTaxFixedRateAddressRelService,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(
			commerceCurrencyService, commerceTaxMethodService, renderRequest,
			renderResponse);

		_commerceCountryService = commerceCountryService;
		_commerceRegionService = commerceRegionService;
		_commerceTaxCategoryService = commerceTaxCategoryService;
		_commerceTaxFixedRateAddressRelService =
			commerceTaxFixedRateAddressRelService;
	}

	public List<CommerceTaxCategory> getAvailableCommerceTaxCategories() {
		return _commerceTaxCategoryService.getCommerceTaxCategories(
			commerceTaxFixedRateRequestHelper.getScopeGroupId(),
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new CommerceTaxCategoryCreateDateComparator());
	}

	public List<CommerceCountry> getCommerceCountries() {
		return _commerceCountryService.getCommerceCountries(
			commerceTaxFixedRateRequestHelper.getScopeGroupId(), true);
	}

	public long getCommerceCountryId() throws PortalException {
		long commerceCountryId = 0;

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			getCommerceTaxFixedRateAddressRel();

		if (commerceTaxFixedRateAddressRel != null) {
			commerceCountryId =
				commerceTaxFixedRateAddressRel.getCommerceCountryId();
		}

		return commerceCountryId;
	}

	public long getCommerceRegionId() throws PortalException {
		long commerceRegionId = 0;

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			getCommerceTaxFixedRateAddressRel();

		if (commerceTaxFixedRateAddressRel != null) {
			commerceRegionId =
				commerceTaxFixedRateAddressRel.getCommerceRegionId();
		}

		return commerceRegionId;
	}

	public List<CommerceRegion> getCommerceRegions() throws PortalException {
		return _commerceRegionService.getCommerceRegions(
			getCommerceCountryId(), true);
	}

	public CommerceTaxFixedRateAddressRel getCommerceTaxFixedRateAddressRel()
		throws PortalException {

		long commerceTaxFixedRateAddressRelId = ParamUtil.getLong(
			commerceTaxFixedRateRequestHelper.getRequest(),
			"commerceTaxFixedRateAddressRelId");

		return _commerceTaxFixedRateAddressRelService.
			fetchCommerceTaxFixedRateAddressRel(
				commerceTaxFixedRateAddressRelId);
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
			commerceTaxFixedRateRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, null);

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
					commerceTaxFixedRateRequestHelper.getScopeGroupId(),
					getCommerceTaxMethodId());

		searchContainer.setTotal(total);

		List<CommerceTaxFixedRateAddressRel> results =
			_commerceTaxFixedRateAddressRelService.
				getCommerceTaxMethodFixedRateAddressRels(
					commerceTaxFixedRateRequestHelper.getScopeGroupId(),
					getCommerceTaxMethodId(), searchContainer.getStart(),
					searchContainer.getEnd(), orderByComparator);

		searchContainer.setResults(results);

		return searchContainer;
	}

	private final CommerceCountryService _commerceCountryService;
	private final CommerceRegionService _commerceRegionService;
	private final CommerceTaxCategoryService _commerceTaxCategoryService;
	private final CommerceTaxFixedRateAddressRelService
		_commerceTaxFixedRateAddressRelService;

}