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

package com.liferay.organizations.item.selector.web.internal.display.context;

import com.liferay.organizations.item.selector.web.internal.search.OrganizationItemSelectorChecker;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portlet.usersadmin.search.OrganizationSearch;
import com.liferay.portlet.usersadmin.search.OrganizationSearchTerms;
import com.liferay.users.admin.kernel.util.UsersAdmin;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class OrganizationItemSelectorViewDisplayContext {

	public OrganizationItemSelectorViewDisplayContext(
		OrganizationLocalService organizationLocalService,
		UsersAdmin usersAdmin, HttpServletRequest httpServletRequest,
		PortletURL portletURL, String itemSelectedEventName) {

		_organizationLocalService = organizationLocalService;
		_usersAdmin = usersAdmin;
		_httpServletRequest = httpServletRequest;
		_portletURL = portletURL;
		_itemSelectedEventName = itemSelectedEventName;

		_renderRequest = (RenderRequest)httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
		_renderResponse = (RenderResponse)httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public String getOrderByCol() {
		return ParamUtil.getString(
			_renderRequest, SearchContainer.DEFAULT_ORDER_BY_COL_PARAM, "name");
	}

	public String getOrderByType() {
		return ParamUtil.getString(
			_renderRequest, SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM, "asc");
	}

	public String getPath(Organization organization) throws PortalException {
		List<Organization> organizations = new ArrayList<>();

		while (organization.getParentOrganizationId() !=
					OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

			organization = organization.getParentOrganization();

			organizations.add(organization);
		}

		if (organizations.isEmpty()) {
			return StringPool.BLANK;
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

	public PortletURL getPortletURL() {
		return _portletURL;
	}

	public SearchContainer<Organization> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new OrganizationSearch(
			_renderRequest, getPortletURL());

		_searchContainer.setEmptyResultsMessage("no-organizations-were-found");

		OrderByComparator<Organization> orderByComparator =
			_usersAdmin.getOrganizationOrderByComparator(
				getOrderByCol(), getOrderByType());

		RowChecker rowChecker = new OrganizationItemSelectorChecker(
			_renderResponse, getCheckedOrganizationIds());

		_searchContainer.setOrderByCol(getOrderByCol());
		_searchContainer.setOrderByComparator(orderByComparator);
		_searchContainer.setOrderByType(getOrderByType());
		_searchContainer.setRowChecker(rowChecker);

		OrganizationSearchTerms organizationSearchTerms =
			(OrganizationSearchTerms)_searchContainer.getSearchTerms();

		BaseModelSearchResult<Organization> organizationBaseModelSearchResult =
			_organizationLocalService.searchOrganizations(
				CompanyThreadLocal.getCompanyId(),
				OrganizationConstants.ANY_PARENT_ORGANIZATION_ID,
				organizationSearchTerms.getKeywords(), null,
				_searchContainer.getStart(), _searchContainer.getEnd(), null);

		_searchContainer.setTotal(
			organizationBaseModelSearchResult.getLength());

		_searchContainer.setResults(
			organizationBaseModelSearchResult.getBaseModels());

		return _searchContainer;
	}

	protected long[] getCheckedOrganizationIds() {
		return ParamUtil.getLongValues(
			_renderRequest, "checkedOrganizationIds");
	}

	private final HttpServletRequest _httpServletRequest;
	private final String _itemSelectedEventName;
	private final OrganizationLocalService _organizationLocalService;
	private final PortletURL _portletURL;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<Organization> _searchContainer;
	private final UsersAdmin _usersAdmin;

}