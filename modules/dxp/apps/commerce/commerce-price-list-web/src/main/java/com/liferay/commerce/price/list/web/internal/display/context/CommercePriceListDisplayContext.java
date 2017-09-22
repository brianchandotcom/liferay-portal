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

package com.liferay.commerce.price.list.web.internal.display.context;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.currency.util.comparator.CommerceCurrencyPriorityComparator;
import com.liferay.commerce.model.CommercePriceList;
import com.liferay.commerce.price.list.web.internal.portlet.action.CommercePriceListActionHelper;
import com.liferay.commerce.service.CommercePriceListService;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CommercePriceListDisplayContext
	extends BaseCommercePriceListDisplayContext<CommercePriceList> {

	public CommercePriceListDisplayContext(
		CommercePriceListActionHelper commercePriceListActionHelper,
		CommerceCurrencyService commerceCurrencyService,
		CommercePriceListService commercePriceListService,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(commercePriceListActionHelper, renderRequest, renderResponse);

		_commerceCurrencyService = commerceCurrencyService;
		_commercePriceListService = commercePriceListService;
	}

	public List<CommerceCurrency> getCommerceCurrencies() {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return _commerceCurrencyService.getCommerceCurrencies(
			themeDisplay.getScopeGroupId(), true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new CommerceCurrencyPriorityComparator(true));
	}

	@Override
	public SearchContainer<CommercePriceList> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		searchContainer = new SearchContainer<>(
			renderRequest, getPortletURL(), null, "there-are-no-price-lists");

		OrderByComparator<CommercePriceList> orderByComparator =
			CommerceUtil.getCommercePriceListOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		if (isSearch()) {
			Sort sort = CommerceUtil.getCommercePriceListSort(
				getOrderByCol(), getOrderByType());

			BaseModelSearchResult<CommercePriceList>
				commercePriceListBaseModelSearchResult =
					_commercePriceListService.searchCommercePriceLists(
						themeDisplay.getCompanyId(),
						themeDisplay.getScopeGroupId(), getKeywords(),
						getStatus(), searchContainer.getStart(),
						searchContainer.getEnd(), sort);

			searchContainer.setTotal(
				commercePriceListBaseModelSearchResult.getLength());
			searchContainer.setResults(
				commercePriceListBaseModelSearchResult.getBaseModels());
		}
		else {
			int total = _commercePriceListService.getCommercePriceListsCount(
				themeDisplay.getScopeGroupId(), getStatus());

			searchContainer.setTotal(total);

			List<CommercePriceList> results =
				_commercePriceListService.getCommercePriceLists(
					themeDisplay.getScopeGroupId(), getStatus(),
					searchContainer.getStart(), searchContainer.getEnd(),
					orderByComparator);

			searchContainer.setResults(results);
		}

		return searchContainer;
	}

	public int getStatus() {
		if (_status != null) {
			return _status;
		}

		_status = ParamUtil.getInteger(
			renderRequest, "status", WorkflowConstants.STATUS_ANY);

		return _status;
	}

	private final CommerceCurrencyService _commerceCurrencyService;
	private final CommercePriceListService _commercePriceListService;
	private Integer _status;

}