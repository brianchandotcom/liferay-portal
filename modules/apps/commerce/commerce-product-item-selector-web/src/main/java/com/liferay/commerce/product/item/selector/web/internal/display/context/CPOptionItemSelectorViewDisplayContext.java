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

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null,
			"no-options-were-found");

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(
			CPItemSelectorViewUtil.getCPOptionOrderByComparator(
				getOrderByCol(), getOrderByType()));
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setResultsAndTotal(
			_cpOptionService.searchCPOptions(
				cpRequestHelper.getCompanyId(), getKeywords(),
				searchContainer.getStart(), searchContainer.getEnd(),
				CPItemSelectorViewUtil.getCPOptionSort(
					getOrderByCol(), getOrderByType())));
		searchContainer.setRowChecker(getRowChecker());

		return searchContainer;
	}

	private final CPOptionService _cpOptionService;

}