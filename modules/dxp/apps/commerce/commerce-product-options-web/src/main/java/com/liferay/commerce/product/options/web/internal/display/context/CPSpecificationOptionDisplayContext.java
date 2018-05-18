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
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.options.web.internal.portlet.action.ActionHelper;
import com.liferay.commerce.product.options.web.internal.util.CPOptionsPortletUtil;
import com.liferay.commerce.product.service.CPOptionCategoryService;
import com.liferay.commerce.product.service.CPSpecificationOptionService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
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

		setDefaultOrderByCol("label");

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

		searchContainer.setEmptyResultsMessage(
			"no-specification-labels-were-found");

		OrderByComparator<CPSpecificationOption> orderByComparator =
			CPOptionsPortletUtil.getCPSpecificationOptionOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		List<CPSpecificationOption> results;
		int total;

		if (isSearch()) {
			Sort sort = CPOptionsPortletUtil.getCPSpecificationOptionSort(
				getOrderByCol(), getOrderByType());

			BaseModelSearchResult<CPSpecificationOption>
				cpSpecificationOptionBaseModelSearchResult =
					_cpSpecificationOptionService.searchCPSpecificationOptions(
						cpRequestHelper.getCompanyId(), getScopeGroupId(),
						getKeywords(), searchContainer.getStart(),
						searchContainer.getEnd(), sort);

			total = cpSpecificationOptionBaseModelSearchResult.getLength();
			results =
				cpSpecificationOptionBaseModelSearchResult.getBaseModels();
		}
		else {
			total =
				_cpSpecificationOptionService.getCPSpecificationOptionsCount(
					getScopeGroupId());

			results = _cpSpecificationOptionService.getCPSpecificationOptions(
				getScopeGroupId(), searchContainer.getStart(),
				searchContainer.getEnd(), orderByComparator);
		}

		searchContainer.setTotal(total);
		searchContainer.setResults(results);

		return searchContainer;
	}

	private final CPOptionCategoryService _cpOptionCategoryService;
	private final CPSpecificationOptionService _cpSpecificationOptionService;

}