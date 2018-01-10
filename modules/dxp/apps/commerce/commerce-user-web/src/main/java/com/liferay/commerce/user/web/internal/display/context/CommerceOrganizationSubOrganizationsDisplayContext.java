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

package com.liferay.commerce.user.web.internal.display.context;

import com.liferay.commerce.user.util.CommerceOrganizationHelper;
import com.liferay.commerce.user.web.internal.util.CommerceOrganizationPortletUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Marco Leo
 */
public class CommerceOrganizationSubOrganizationsDisplayContext extends BaseCommerceOrganizationDisplayContext {

	public CommerceOrganizationSubOrganizationsDisplayContext(
			HttpServletRequest httpServletRequest,
			CommerceOrganizationHelper commerceOrganizationHelper,
			OrganizationService organizationService) {

		super(httpServletRequest, organizationService);

		_commerceOrganizationHelper = commerceOrganizationHelper;
	}

	public SearchContainer<Organization> getSearchContainer()
			throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null,
			"no-organizations-were-found");

		Organization organization = getSiteOrganization();

		Sort sort = CommerceOrganizationPortletUtil.getOrganizationSort(
			"nameTreePath", getOrderByType());

		BaseModelSearchResult<Organization> organizationBaseModelSearchResult =
			_commerceOrganizationHelper.getSubOrnanization(
				organization.getOrganizationId(), null,
					_searchContainer.getStart(), _searchContainer.getEnd(),
					new Sort[] {sort});

		_searchContainer.setTotal(organizationBaseModelSearchResult.getLength());

		_searchContainer.setResults(organizationBaseModelSearchResult.getBaseModels());

		return _searchContainer;
	}

	public String getPath(Organization organization)
			throws PortalException {

		Organization topOrganization = getSiteOrganization();

		List<Organization> organizations = new ArrayList<>();

		while (organization != null) {

			if(organization.getOrganizationId() == topOrganization.getOrganizationId()){
				break;
			}

			organization = OrganizationLocalServiceUtil.fetchOrganization(
					organization.getParentOrganizationId());

			organizations.add(organization);
		}

		int size = organizations.size();

		StringBundler sb = new StringBundler(((size - 1) * 4) + 1);

		organization = organizations.get(size - 1);

		sb.append(organization.getName());

		for (int i = size - 2; i >= 0; i--) {
			organization = organizations.get(i);

			sb.append(StringPool.SPACE);
			sb.append(StringPool.GREATER_THAN);
			sb.append(StringPool.SPACE);
			sb.append(organization.getName());
		}

		return sb.toString();
	}

	private SearchContainer<Organization> _searchContainer;

	private final CommerceOrganizationHelper _commerceOrganizationHelper;

}