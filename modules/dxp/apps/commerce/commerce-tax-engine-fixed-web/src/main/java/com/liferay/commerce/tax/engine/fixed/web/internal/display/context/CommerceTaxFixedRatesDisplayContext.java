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
import com.liferay.commerce.model.CommerceTaxCategory;
import com.liferay.commerce.service.CommerceTaxCategoryService;
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
	extends BaseCommerceTaxFixedRateDisplayContext<CommerceTaxCategory> {

	public CommerceTaxFixedRatesDisplayContext(
		CommerceCurrencyService commerceCurrencyService,
		CommerceTaxCategoryService commerceTaxCategoryService,
		CommerceTaxFixedRateService commerceTaxFixedRateService,
		CommerceTaxMethodService commerceTaxMethodService,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(
			commerceCurrencyService, commerceTaxMethodService, renderRequest,
			renderResponse);

		_commerceTaxCategoryService = commerceTaxCategoryService;
		_commerceTaxFixedRateService = commerceTaxFixedRateService;
	}

	public CommerceTaxFixedRate getCommerceTaxFixedRate(
			long commerceTaxCategoryId)
		throws PortalException {

		return _commerceTaxFixedRateService.fetchCommerceTaxFixedRateByCTC_CTM(
			commerceTaxCategoryId, getCommerceTaxMethodId());
	}

	@Override
	public String getScreenNavigationEntryKey() {
		return CommerceTaxMethodFixedRatesScreenNavigationEntry.ENTRY_KEY;
	}

	@Override
	public SearchContainer<CommerceTaxCategory> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			commerceTaxFixedRateRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, null);

		searchContainer.setEmptyResultsMessage("there-are-no-tax-categories");

		OrderByComparator<CommerceTaxCategory> orderByComparator =
			CommerceTaxEngineFixedUtil.getCommerceTaxCategoryOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());

		int total = _commerceTaxCategoryService.getCommerceTaxCategoriesCount(
			commerceTaxFixedRateRequestHelper.getScopeGroupId());

		searchContainer.setTotal(total);

		List<CommerceTaxCategory> results =
			_commerceTaxCategoryService.getCommerceTaxCategories(
				commerceTaxFixedRateRequestHelper.getScopeGroupId(),
				searchContainer.getStart(), searchContainer.getEnd(),
				orderByComparator);

		searchContainer.setResults(results);

		return searchContainer;
	}

	private final CommerceTaxCategoryService _commerceTaxCategoryService;
	private final CommerceTaxFixedRateService _commerceTaxFixedRateService;

}