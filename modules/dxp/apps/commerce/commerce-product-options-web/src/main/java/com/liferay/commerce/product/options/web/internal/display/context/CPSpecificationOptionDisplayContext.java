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
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.options.web.internal.portlet.action.ActionHelper;
import com.liferay.commerce.product.options.web.internal.util.CPOptionsPortletUtil;
import com.liferay.commerce.product.service.CPOptionCategoryService;
import com.liferay.commerce.product.service.CPSpecificationOptionService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Di Giorgi
 */
public class CPSpecificationOptionDisplayContext
	extends BaseCPOptionsDisplayContext<CPSpecificationOption> {

	public CPSpecificationOptionDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CPOptionCategoryService cpOptionCategoryService,
			CPSpecificationOptionService cpSpecificationOptionService)
		throws PortalException {

		super(
			actionHelper, httpServletRequest,
			CPSpecificationOption.class.getSimpleName());

		setDefaultOrderByCol("title");

		_cpOptionCategoryService = cpOptionCategoryService;
		_cpSpecificationOptionService = cpSpecificationOptionService;
	}

	public List<CPOptionCategory> getCPOptionCategories() {
		return _cpOptionCategoryService.getCPOptionCategories(
			getScopeGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public String getCPOptionCategoryTitle(
			CPSpecificationOption cpSpecificationOption)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long cpOptionCategoryId = cpSpecificationOption.getCPOptionCategoryId();

		CPOptionCategory cpOptionCategory =
			_cpOptionCategoryService.fetchCPOptionCategory(cpOptionCategoryId);

		if (cpOptionCategory != null) {
			return cpOptionCategory.getTitle(themeDisplay.getLocale());
		}

		return StringPool.BLANK;
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
			CPOptionsPortletUtil.getCPSpecificationOptionOrderByComparator(
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

	private final CPOptionCategoryService _cpOptionCategoryService;
	private final CPSpecificationOptionService _cpSpecificationOptionService;

}