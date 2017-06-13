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

package com.liferay.commerce.product.definitions.web.internal.display.context;

import com.liferay.commerce.product.definitions.web.display.context.BaseCPDefinitionsSearchContainerDisplayContext;
import com.liferay.commerce.product.definitions.web.internal.util.CPDefinitionsPortletUtil;
import com.liferay.commerce.product.definitions.web.portlet.action.ActionHelper;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.type.CPType;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 * @author Marco Leo
 */
public class CPDefinitionsDisplayContext
	extends BaseCPDefinitionsSearchContainerDisplayContext<CPDefinition> {

	public CPDefinitionsDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CPDefinitionService cpDefinitionService)
		throws PortalException {

		super(
			actionHelper, httpServletRequest,
			CPDefinition.class.getSimpleName());

		setDefaultOrderByType("desc");

		_cpDefinitionService = cpDefinitionService;
	}

	public String getNavigation() {
		return ParamUtil.getString(httpServletRequest, "navigation", "all");
	}

	public String[] getNavigationKeys() {
		List<String> navigationKeysList = new ArrayList<>();

		navigationKeysList.add("all");

		List<CPType> cpTypes = getCPTypes();

		for (CPType cpType : cpTypes) {
			navigationKeysList.add(cpType.getName());
		}

		String[] navigationKeys = new String[navigationKeysList.size()];

		navigationKeys = navigationKeysList.toArray(navigationKeys);

		return navigationKeys;
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		String navigation = ParamUtil.getString(
			httpServletRequest, "navigation");

		if (Validator.isNotNull(navigation)) {
			portletURL.setParameter("navigation", getNavigation());
		}

		return portletURL;
	}

	public String getProductTypeName() {
		String navigation = getNavigation();

		if (navigation.equals("all")) {
			return null;
		}

		return navigation;
	}

	@Override
	public SearchContainer<CPDefinition> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		OrderByComparator<CPDefinition> orderByComparator =
			CPDefinitionsPortletUtil.getCPDefinitionOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		if (isSearch()) {
			Sort sort = CPDefinitionsPortletUtil.getCPDefinitionSort(
				getOrderByCol(), getOrderByType());

			BaseModelSearchResult<CPDefinition> cpOptionBaseModelSearchResult =
				_cpDefinitionService.searchCPDefinitions(
					themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
					getKeywords(), getStatus(), searchContainer.getStart(),
					searchContainer.getEnd(), sort);

			searchContainer.setTotal(cpOptionBaseModelSearchResult.getLength());
			searchContainer.setResults(
				cpOptionBaseModelSearchResult.getBaseModels());
		}
		else {
			int total = _cpDefinitionService.getCPDefinitionsCount(
				getScopeGroupId(), getProductTypeName(),
				themeDisplay.getLanguageId(), getStatus());

			searchContainer.setTotal(total);

			List<CPDefinition> results = _cpDefinitionService.getCPDefinitions(
				getScopeGroupId(), getProductTypeName(),
				themeDisplay.getLanguageId(), getStatus(),
				searchContainer.getStart(), searchContainer.getEnd(),
				orderByComparator);

			searchContainer.setResults(results);
		}

		return searchContainer;
	}

	private final CPDefinitionService _cpDefinitionService;

}