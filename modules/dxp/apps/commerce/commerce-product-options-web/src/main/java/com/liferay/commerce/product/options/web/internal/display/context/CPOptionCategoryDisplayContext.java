/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.options.web.internal.display.context;

import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.options.web.internal.portlet.action.ActionHelper;
import com.liferay.commerce.product.options.web.internal.util.CPOptionsPortletUtil;
import com.liferay.commerce.product.service.CPOptionCategoryService;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CPOptionCategoryDisplayContext
	extends BaseCPOptionsDisplayContext<CPOptionCategory> {

	public CPOptionCategoryDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CPOptionCategoryService cpOptionCategoryService)
		throws PortalException {

		super(
			actionHelper, httpServletRequest,
			CPOptionCategory.class.getSimpleName());

		setDefaultOrderByCol("priority");

		_cpOptionCategoryService = cpOptionCategoryService;
	}

	@Override
	public SearchContainer<CPOptionCategory> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		searchContainer.setEmptyResultsMessage(
			"no-specification-groups-were-found");

		OrderByComparator<CPOptionCategory> orderByComparator =
			CPOptionsPortletUtil.getCPOptionCategoryOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		int total = _cpOptionCategoryService.getCPOptionCategoriesCount(
			getScopeGroupId());

		searchContainer.setTotal(total);

		List<CPOptionCategory> results =
			_cpOptionCategoryService.getCPOptionCategories(
				getScopeGroupId(), searchContainer.getStart(),
				searchContainer.getEnd(), orderByComparator);

		searchContainer.setResults(results);

		return searchContainer;
	}

	private final CPOptionCategoryService _cpOptionCategoryService;

}