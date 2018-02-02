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
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPSpecificationOptionService;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Di Giorgi
 */
public class CPSpecificationOptionItemSelectorViewDisplayContext
	extends BaseCPItemSelectorViewDisplayContext<CPSpecificationOption> {

	public CPSpecificationOptionItemSelectorViewDisplayContext(
		HttpServletRequest httpServletRequest, PortletURL portletURL,
		String itemSelectedEventName,
		CPSpecificationOptionService cpSpecificationOptionService) {

		super(
			httpServletRequest, portletURL, itemSelectedEventName,
			"CPSpecificationOptionItemSelectorView");

		_cpSpecificationOptionService = cpSpecificationOptionService;
	}

	@Override
	public SearchContainer<CPSpecificationOption> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		searchContainer.setEmptyResultsMessage("no-specifications-were-found");

		OrderByComparator<CPSpecificationOption> orderByComparator =
			CPItemSelectorViewUtil.getCPSpecificationOptionOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		int total =
			_cpSpecificationOptionService.getCPSpecificationOptionsCount(
				getScopeGroupId());

		searchContainer.setTotal(total);

		List<CPSpecificationOption> results =
			_cpSpecificationOptionService.getCPSpecificationOptions(
				getScopeGroupId(), searchContainer.getStart(),
				searchContainer.getEnd(), orderByComparator);

		searchContainer.setResults(results);

		return searchContainer;
	}

	private final CPSpecificationOptionService _cpSpecificationOptionService;

}