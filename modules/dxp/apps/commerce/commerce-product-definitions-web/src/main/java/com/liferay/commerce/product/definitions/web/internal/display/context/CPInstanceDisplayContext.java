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
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 * @author Marco Leo
 */
public class CPInstanceDisplayContext
	extends BaseCPDefinitionsDisplayContext<CPInstance> {

	public CPInstanceDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CPInstanceService cpInstanceService)
		throws PortalException {

		super(actionHelper, httpServletRequest, "CPInstance");

		setDefaultOrderByCol("sku");

		_cpInstanceService = cpInstanceService;
	}

	public List<CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
			long cpDefinitionOptionValueRelId)
		throws PortalException {

		return actionHelper.getCPDefinitionOptionValueRels(
			cpDefinitionOptionValueRelId);
	}

	public CPInstance getCPInstance() throws PortalException {
		if (_cpInstance != null) {
			return _cpInstance;
		}

		_cpInstance = actionHelper.getCPInstance(
			cpRequestHelper.getRenderRequest());

		return _cpInstance;
	}

	public long getCPInstanceId() throws PortalException {
		CPInstance cpInstance = getCPInstance();

		if (cpInstance == null) {
			return 0;
		}

		return cpInstance.getCPInstanceId();
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter("mvcRenderCommandName", "viewProductInstances");
		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(getCPDefinitionId()));

		return portletURL;
	}

	@Override
	public SearchContainer<CPInstance> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		SearchContainer<CPInstance> searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		OrderByComparator<CPInstance> orderByComparator =
			CPDefinitionsPortletUtil.getCPInstanceOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setEmptyResultsMessage("no-skus-were-found");

		searchContainer.setRowChecker(getRowChecker());

		if (isSearch()) {
			Sort sort = CPDefinitionsPortletUtil.getCPInstanceSort(
				getOrderByCol(), getOrderByType());

			BaseModelSearchResult<CPInstance> cpInstanceBaseModelSearchResult =
				_cpInstanceService.searchCPOptions(
					themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
					getCPDefinitionId(), getKeywords(),
					searchContainer.getStart(), searchContainer.getEnd(), sort);

			searchContainer.setTotal(
				cpInstanceBaseModelSearchResult.getLength());
			searchContainer.setResults(
				cpInstanceBaseModelSearchResult.getBaseModels());
		}
		else {
			int total = _cpInstanceService.getCPInstancesCount(
				getCPDefinitionId());

			searchContainer.setTotal(total);

			List<CPInstance> results = _cpInstanceService.getCPInstances(
				getCPDefinitionId(), searchContainer.getStart(),
				searchContainer.getEnd(), orderByComparator);

			searchContainer.setResults(results);
		}

		this.searchContainer = searchContainer;

		return this.searchContainer;
	}

	public List<CPDefinitionOptionRel> getSkuContributorCPDefinitionOptionRels()
		throws PortalException {

		return actionHelper.getSkuContributorCPDefinitionOptionRels(
			getCPDefinitionId());
	}

	private CPInstance _cpInstance;
	private final CPInstanceService _cpInstanceService;

}