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
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.CPTaxCategoryService;
import com.liferay.commerce.service.CommerceTaxMethodService;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate;
import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateService;
import com.liferay.commerce.tax.engine.fixed.util.CommerceTaxEngineFixedUtil;
import com.liferay.commerce.tax.engine.fixed.web.internal.servlet.taglib.ui.CommerceTaxMethodFixedRatesScreenNavigationEntry;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Marco Leo
 */
public class CommerceTaxFixedRatesDisplayContext
	extends BaseCommerceTaxFixedRateDisplayContext<CPTaxCategory> {

	public CommerceTaxFixedRatesDisplayContext(
		CommerceCurrencyService commerceCurrencyService,
		CommerceTaxFixedRateService commerceTaxFixedRateService,
		CommerceTaxMethodService commerceTaxMethodService,
		CPTaxCategoryService cpTaxCategoryService, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		super(
			commerceCurrencyService, commerceTaxMethodService, renderRequest,
			renderResponse);

		_commerceTaxFixedRateService = commerceTaxFixedRateService;
		_cpTaxCategoryService = cpTaxCategoryService;
	}

	public CommerceTaxFixedRate getCommerceTaxFixedRate(long cpTaxCategoryId)
		throws PortalException {

		return _commerceTaxFixedRateService.fetchCommerceTaxFixedRateByCPTC_CTM(
			cpTaxCategoryId, getCommerceTaxMethodId());
	}

	@Override
	public String getScreenNavigationEntryKey() {
		return CommerceTaxMethodFixedRatesScreenNavigationEntry.ENTRY_KEY;
	}

	@Override
	public SearchContainer<CPTaxCategory> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			commerceTaxFixedRateRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, null);

		searchContainer.setEmptyResultsMessage("there-are-no-tax-categories");

		OrderByComparator<CPTaxCategory> orderByComparator =
			CommerceTaxEngineFixedUtil.getCPTaxCategoryOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());

		int total = _cpTaxCategoryService.getCPTaxCategoriesCount(
			commerceTaxFixedRateRequestHelper.getScopeGroupId());

		searchContainer.setTotal(total);

		List<CPTaxCategory> results = _cpTaxCategoryService.getCPTaxCategories(
			commerceTaxFixedRateRequestHelper.getScopeGroupId(),
			searchContainer.getStart(), searchContainer.getEnd(),
			orderByComparator);

		searchContainer.setResults(results);

		return searchContainer;
	}

	private final CommerceTaxFixedRateService _commerceTaxFixedRateService;
	private final CPTaxCategoryService _cpTaxCategoryService;

}