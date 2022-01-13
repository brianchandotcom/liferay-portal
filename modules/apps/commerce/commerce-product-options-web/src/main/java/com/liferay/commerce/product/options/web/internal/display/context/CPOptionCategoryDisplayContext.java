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

import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.options.web.internal.portlet.action.helper.ActionHelper;
import com.liferay.commerce.product.options.web.internal.util.CPOptionsPortletUtil;
import com.liferay.commerce.product.service.CPOptionCategoryService;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CPOptionCategoryDisplayContext
	extends BaseCPOptionsDisplayContext<CPOptionCategory> {

	public CPOptionCategoryDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CPOptionCategoryService cpOptionCategoryService,
			PortletResourcePermission portletResourcePermission)
		throws PortalException {

		super(
			actionHelper, httpServletRequest,
			CPOptionCategory.class.getSimpleName(), portletResourcePermission);

		_cpOptionCategoryService = cpOptionCategoryService;

		setDefaultOrderByCol("priority");
	}

	@Override
	public SearchContainer<CPOptionCategory> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null,
			"no-specification-groups-were-found");

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(
			CPOptionsPortletUtil.getCPOptionCategoryOrderByComparator(
				getOrderByCol(), getOrderByType()));
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setResultsAndTotal(
			_cpOptionCategoryService.searchCPOptionCategories(
				cpRequestHelper.getCompanyId(), getKeywords(),
				searchContainer.getStart(), searchContainer.getEnd(),
				CPOptionsPortletUtil.getCPOptionCategorySort(
					getOrderByCol(), getOrderByType())));
		searchContainer.setRowChecker(getRowChecker());

		return searchContainer;
	}

	private final CPOptionCategoryService _cpOptionCategoryService;

}