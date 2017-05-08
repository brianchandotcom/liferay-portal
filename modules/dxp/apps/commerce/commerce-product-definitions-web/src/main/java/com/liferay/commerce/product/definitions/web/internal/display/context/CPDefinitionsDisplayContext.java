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

import com.liferay.commerce.product.definitions.web.internal.portlet.action.ActionHelper;
import com.liferay.commerce.product.definitions.web.internal.util.CPDefinitionsPortletUtil;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.CPTypeServicesTracker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 * @author Marco Leo
 */
public class CPDefinitionsDisplayContext
	extends BaseCPDefinitionsDisplayContext<CPDefinition> {

	public CPDefinitionsDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CPDefinitionService cpDefinitionService,
			CPTypeServicesTracker cpTypeServicesTracker)
		throws PortalException {

		super(actionHelper, httpServletRequest, "CPDefinition");

		_cpDefinitionService = cpDefinitionService;
		_cpTypeServicesTracker = cpTypeServicesTracker;
	}

	public CPType getCPType(String name) {
		return _cpTypeServicesTracker.getCPType(name);
	}

	public List<CPType> getCPTypes() {
		return _cpTypeServicesTracker.getCPTypes();
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

		SearchContainer<CPDefinition> searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		OrderByComparator<CPDefinition> orderByComparator =
			CPDefinitionsPortletUtil.getCPDefinitionOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		if (isSearch()) {
			boolean orderByAsc = false;

			if (Objects.equals(getOrderByType(), "asc")) {
				orderByAsc = true;
			}

			Sort sort = null;

			if (Objects.equals(getOrderByCol(), "name")) {
				sort = new Sort("name", Sort.STRING_TYPE, orderByAsc);
			}
			else if (Objects.equals(getOrderByCol(), "create-date")) {
				sort = new Sort(Field.CREATE_DATE, orderByAsc);
			}
			else if (Objects.equals(getOrderByCol(), "display-date")) {
				sort = new Sort("display-date", orderByAsc);
			}

			BaseModelSearchResult<CPDefinition> cpOptionBaseModelSearchResult =
				_cpDefinitionService.searchCPDefinitions(
					themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
					getKeywords(), searchContainer.getStart(),
					searchContainer.getEnd(), sort);

			searchContainer.setTotal(cpOptionBaseModelSearchResult.getLength());
			searchContainer.setResults(
				cpOptionBaseModelSearchResult.getBaseModels());
		}
		else {
			int total = _cpDefinitionService.getCPDefinitionsCount(
				getScopeGroupId(), WorkflowConstants.STATUS_ANY);

			searchContainer.setTotal(total);

			List<CPDefinition> results = _cpDefinitionService.getCPDefinitions(
				getScopeGroupId(), WorkflowConstants.STATUS_ANY,
				searchContainer.getStart(), searchContainer.getEnd(),
				orderByComparator);

			searchContainer.setResults(results);
		}

		this.searchContainer = searchContainer;

		return this.searchContainer;
	}

	private final CPDefinitionService _cpDefinitionService;
	private final CPTypeServicesTracker _cpTypeServicesTracker;

}