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

package com.liferay.commerce.product.item.selector.web.internal.display.context;

import com.liferay.commerce.product.item.selector.web.internal.util.CPItemSelectorViewUtil;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.CPOptionService;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CPOptionItemSelectorViewDisplayContext
	extends BaseCPItemSelectorViewDisplayContext<CPOption> {

	public CPOptionItemSelectorViewDisplayContext(
		HttpServletRequest httpServletRequest, PortletURL portletURL,
		String itemSelectedEventName, CPOptionService cpOptionService) {

		super(
			httpServletRequest, portletURL, itemSelectedEventName,
			"CPOptionItemSelectorView");

		_cpOptionService = cpOptionService;
	}

	@Override
	public SearchContainer<CPOption> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		searchContainer.setEmptyResultsMessage("no-options-were-found");

		OrderByComparator<CPOption> orderByComparator =
			CPItemSelectorViewUtil.getCPOptionOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		if (isSearch()) {
			Sort sort = CPItemSelectorViewUtil.getCPOptionSort(
				getOrderByCol(), getOrderByType());

			BaseModelSearchResult<CPOption> cpOptionBaseModelSearchResult =
				_cpOptionService.searchCPOptions(
					themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
					getKeywords(), searchContainer.getStart(),
					searchContainer.getEnd(), sort);

			searchContainer.setTotal(cpOptionBaseModelSearchResult.getLength());
			searchContainer.setResults(
				cpOptionBaseModelSearchResult.getBaseModels());
		}
		else {
			int total = _cpOptionService.getCPOptionsCount(getScopeGroupId());

			searchContainer.setTotal(total);

			List<CPOption> results = _cpOptionService.getCPOptions(
				getScopeGroupId(), searchContainer.getStart(),
				searchContainer.getEnd(), orderByComparator);

			searchContainer.setResults(results);
		}

		return searchContainer;
	}

	private final CPOptionService _cpOptionService;

}