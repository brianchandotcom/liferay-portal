/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.options.web.internal.display.context;

import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.options.web.internal.portlet.action.helper.ActionHelper;
import com.liferay.commerce.product.options.web.internal.util.CPOptionsPortletUtil;
import com.liferay.commerce.product.service.CPOptionCategoryService;
import com.liferay.commerce.product.service.CPSpecificationOptionService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Di Giorgi
 */
public class CPSpecificationOptionDisplayContext
	extends BaseCPOptionsDisplayContext<CPSpecificationOption> {

	public CPSpecificationOptionDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CPOptionCategoryService cpOptionCategoryService,
			CPSpecificationOptionService cpSpecificationOptionService,
			PortletResourcePermission portletResourcePermission)
		throws PortalException {

		super(
			actionHelper, httpServletRequest,
			CPSpecificationOption.class.getSimpleName(),
			portletResourcePermission);

		_cpOptionCategoryService = cpOptionCategoryService;
		_cpSpecificationOptionService = cpSpecificationOptionService;

		setDefaultOrderByCol("label");
	}

	public List<CPOptionCategory> getCPOptionCategories()
		throws PortalException {

		BaseModelSearchResult<CPOptionCategory>
			cpOptionCategoryBaseModelSearchResult =
				_cpOptionCategoryService.searchCPOptionCategories(
					cpRequestHelper.getCompanyId(), null, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null);

		return cpOptionCategoryBaseModelSearchResult.getBaseModels();
	}

	public String getCPOptionCategoryTitle(
			CPSpecificationOption cpSpecificationOption)
		throws PortalException {

		CPOptionCategory cpOptionCategory =
			_cpOptionCategoryService.fetchCPOptionCategory(
				cpSpecificationOption.getCPOptionCategoryId());

		if (cpOptionCategory != null) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			return cpOptionCategory.getTitle(themeDisplay.getLocale());
		}

		return StringPool.BLANK;
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		return PortletURLBuilder.create(
			super.getPortletURL()
		).setNavigation(
			_getNavigation()
		).buildPortletURL();
	}

	@Override
	public SearchContainer<CPSpecificationOption> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null,
			"no-specification-labels-were-found");

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(
			CPOptionsPortletUtil.getCPSpecificationOptionOrderByComparator(
				getOrderByCol(), getOrderByType()));
		searchContainer.setOrderByType(getOrderByType());

		Boolean facetable = null;

		String navigation = _getNavigation();

		if (navigation.equals("no")) {
			facetable = false;
		}
		else if (navigation.equals("yes")) {
			facetable = true;
		}

		searchContainer.setResultsAndTotal(
			_cpSpecificationOptionService.searchCPSpecificationOptions(
				cpRequestHelper.getCompanyId(), facetable, getKeywords(),
				searchContainer.getStart(), searchContainer.getEnd(),
				CPOptionsPortletUtil.getCPSpecificationOptionSort(
					getOrderByCol(), getOrderByType())));
		searchContainer.setRowChecker(getRowChecker());

		return searchContainer;
	}

	private String _getNavigation() {
		return ParamUtil.getString(httpServletRequest, "navigation");
	}

	private final CPOptionCategoryService _cpOptionCategoryService;
	private final CPSpecificationOptionService _cpSpecificationOptionService;

}