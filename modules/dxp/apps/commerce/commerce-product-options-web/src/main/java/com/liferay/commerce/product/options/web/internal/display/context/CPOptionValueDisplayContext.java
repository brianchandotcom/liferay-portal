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

package com.liferay.commerce.product.options.web.internal.display.context;

import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.options.web.internal.portlet.action.ActionHelper;
import com.liferay.commerce.product.options.web.internal.util.CPOptionsPortletUtil;
import com.liferay.commerce.product.service.CPOptionValueService;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CPOptionValueDisplayContext
	extends BaseCPOptionsDisplayContext {

	public CPOptionValueDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CPOptionValueService cpOptionValueService)
		throws PortalException {

		super(
			actionHelper, httpServletRequest,
			"rowIdsCPOptionValue", "CPOptionValue");

		_cpOptionValueService = cpOptionValueService;

		setDefaultOrderByCol("priority");
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "viewProductOptionValues");
		portletURL.setParameter(
			"cpOptionId",
			String.valueOf(getCPOptionId()));

		return portletURL;
	}

	public SearchContainer getSearchContainer() throws PortalException {
		if (searchContainer != null) {
			return searchContainer;
		}

		SearchContainer<CPOptionValue> searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		searchContainer.setEmptyResultsMessage(
			"no-option-values-were-found");

		OrderByComparator<CPOptionValue> orderByComparator =
			CPOptionsPortletUtil.getCPOptionValueOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		int total =
			_cpOptionValueService.getCPOptionValuesCount(getCPOptionId());

		searchContainer.setTotal(total);

		List<CPOptionValue> results =
			_cpOptionValueService.getCPOptionValues(
				getCPOptionId(), searchContainer.getStart(),
				searchContainer.getEnd(), orderByComparator);

		searchContainer.setResults(results);

		this.searchContainer = searchContainer;

		return this.searchContainer;
	}

	private final CPOptionValueService _cpOptionValueService;

}