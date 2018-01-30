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

import com.liferay.commerce.organization.web.internal.util.CommerceOrganizationPortletUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class CommerceOrganizationMembersDisplayContext
	extends BaseCommerceOrganizationDisplayContext {

	public CommerceOrganizationMembersDisplayContext(
		HttpServletRequest httpServletRequest,
		OrganizationService organizationService, Portal portal) {

		super(httpServletRequest, organizationService, portal);

		setDefaultOrderByCol("last-name");
	}

	public SearchContainer<User> getSearchContainer() throws PortalException {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			cpRequestHelper.getLiferayPortletRequest(), getPortletURL(), null,
			null);

		_searchContainer.setEmptyResultsMessage("no-users-were-found");

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		Organization organization = getCurrentOrganization();

		params.put("usersOrgs", organization.getOrganizationId());

		Sort sort = CommerceOrganizationPortletUtil.getUserSort(
			getOrderByCol(), getOrderByType());

		BaseModelSearchResult<User> userBaseModelSearchResult =
			UserLocalServiceUtil.searchUsers(
				cpRequestHelper.getCompanyId(), null, 0, params,
				_searchContainer.getStart(), _searchContainer.getEnd(), sort);

		_searchContainer.setTotal(userBaseModelSearchResult.getLength());
		_searchContainer.setResults(userBaseModelSearchResult.getBaseModels());

		return _searchContainer;
	}

	private SearchContainer<User> _searchContainer;

}