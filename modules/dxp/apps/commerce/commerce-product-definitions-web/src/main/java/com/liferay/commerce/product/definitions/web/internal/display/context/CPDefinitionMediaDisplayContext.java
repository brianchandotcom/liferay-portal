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
import com.liferay.commerce.product.model.CPDefinitionMedia;
import com.liferay.commerce.product.service.CPDefinitionMediaService;
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
 * @author Marco Leo
 */
public class CPDefinitionMediaDisplayContext
	extends BaseCPDefinitionsDisplayContext<CPDefinitionMedia> {

	public CPDefinitionMediaDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CPDefinitionMediaService cpDefinitionMediaService)
		throws PortalException {

		super(actionHelper, httpServletRequest, "CPDefinitionMedia");

		setDefaultOrderByCol("priority");

		_cpDefinitionMediaService = cpDefinitionMediaService;
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter("mvcRenderCommandName", "viewDefinitionMedias");
		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(getCPDefinitionId()));

		return portletURL;
	}

	@Override
	public SearchContainer<CPDefinitionMedia> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		SearchContainer<CPDefinitionMedia> searchContainer =
			new SearchContainer<>(
				liferayPortletRequest, getPortletURL(), null, null);

		OrderByComparator<CPDefinitionMedia> orderByComparator =
			CPDefinitionsPortletUtil.getCPDefinitionMediaOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setEmptyResultsMessage(
			"no-medias-were-found");

		searchContainer.setRowChecker(getRowChecker());

		if (isSearch()) {
			Sort sort = CPDefinitionsPortletUtil.getCPDefinitionMediaSort(
				getOrderByCol(), getOrderByType());

			BaseModelSearchResult<CPDefinitionMedia>
				cpInstanceBaseModelSearchResult =
					_cpDefinitionMediaService.searchCPDefinitionMedias(
						themeDisplay.getCompanyId(),
						themeDisplay.getScopeGroupId(), getCPDefinitionId(),
						getKeywords(), searchContainer.getStart(),
						searchContainer.getEnd(), sort);

			searchContainer.setTotal(
				cpInstanceBaseModelSearchResult.getLength());
			searchContainer.setResults(
				cpInstanceBaseModelSearchResult.getBaseModels());
		}
		else {
			int total = _cpDefinitionMediaService.getDefinitionMediasCount(
				getCPDefinitionId());

			searchContainer.setTotal(total);

			List<CPDefinitionMedia> results =
				_cpDefinitionMediaService.getDefinitionMedias(
					getCPDefinitionId(), searchContainer.getStart(),
					searchContainer.getEnd(), orderByComparator);

			searchContainer.setResults(results);
		}

		this.searchContainer = searchContainer;

		return this.searchContainer;
	}

	private final CPDefinitionMediaService _cpDefinitionMediaService;

}