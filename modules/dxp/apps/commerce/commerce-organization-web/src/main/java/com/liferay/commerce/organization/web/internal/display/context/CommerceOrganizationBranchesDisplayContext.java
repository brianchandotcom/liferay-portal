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

package com.liferay.commerce.organization.web.internal.display.context;

import com.liferay.commerce.organization.service.CommerceOrganizationService;
import com.liferay.commerce.organization.util.CommerceOrganizationHelper;
import com.liferay.commerce.organization.web.internal.util.CommerceOrganizationPortletUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.Portal;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class CommerceOrganizationBranchesDisplayContext
	extends BaseCommerceOrganizationDisplayContext {

	public CommerceOrganizationBranchesDisplayContext(
		CommerceOrganizationHelper commerceOrganizationHelper,
		CommerceOrganizationService commerceOrganizationService,
		HttpServletRequest httpServletRequest, Portal portal) {

		super(
			commerceOrganizationHelper, commerceOrganizationService,
			httpServletRequest, portal);

		setDefaultOrderByCol("nameTreePath");
	}

	public SearchContainer<Organization> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			cpRequestHelper.getLiferayPortletRequest(), getPortletURL(), null,
			"no-organizations-were-found");

		Organization organization = getCurrentOrganization();

		Sort sort = CommerceOrganizationPortletUtil.getOrganizationSort(
			getOrderByCol(), getOrderByType());

		BaseModelSearchResult<Organization> organizationBaseModelSearchResult =
			commerceOrganizationService.searchOrganizations(
				organization.getOrganizationId(), "branch", null,
				_searchContainer.getStart(), _searchContainer.getEnd(),
				new Sort[] {sort});

		_searchContainer.setTotal(
			organizationBaseModelSearchResult.getLength());

		_searchContainer.setResults(
			organizationBaseModelSearchResult.getBaseModels());

		return _searchContainer;
	}

	public boolean hasManageBranchesPermission() {
		return true;
	}

	private SearchContainer<Organization> _searchContainer;

}