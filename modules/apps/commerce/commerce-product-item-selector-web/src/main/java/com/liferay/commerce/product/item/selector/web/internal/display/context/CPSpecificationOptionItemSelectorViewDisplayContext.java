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

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
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
			liferayPortletRequest, getPortletURL(), null,
			"no-specifications-were-found");

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(
			CPItemSelectorViewUtil.getCPSpecificationOptionOrderByComparator(
				getOrderByCol(), getOrderByType()));
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setResultsAndTotal(
			_cpSpecificationOptionService.searchCPSpecificationOptions(
				cpRequestHelper.getCompanyId(), null, getKeywords(),
				searchContainer.getStart(), searchContainer.getEnd(),
				CPItemSelectorViewUtil.getCPSpecificationOptionSort(
					getOrderByCol(), getOrderByType())));
		searchContainer.setRowChecker(getRowChecker());

		return searchContainer;
	}

	private final CPSpecificationOptionService _cpSpecificationOptionService;

}