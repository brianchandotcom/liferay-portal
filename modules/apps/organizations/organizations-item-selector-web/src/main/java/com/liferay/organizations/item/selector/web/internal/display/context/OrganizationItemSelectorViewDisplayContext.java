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
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portlet.usersadmin.search.OrganizationSearch;
import com.liferay.portlet.usersadmin.search.OrganizationSearchTerms;
import com.liferay.users.admin.kernel.util.UsersAdmin;

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
		PortletURL portletURL) {

		_organizationLocalService = organizationLocalService;
		_usersAdmin = usersAdmin;
		_portletURL = portletURL;

		_renderRequest = (RenderRequest)httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
		_renderResponse = (RenderResponse)httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);
	}

	public String getOrderByCol() {
		return ParamUtil.getString(
			_renderRequest, SearchContainer.DEFAULT_ORDER_BY_COL_PARAM, "name");
	}

	public String getOrderByType() {
		return ParamUtil.getString(
			_renderRequest, SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM, "asc");
	}

	public SearchContainer<Organization> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new OrganizationSearch(_renderRequest, _portletURL);

		_searchContainer.setEmptyResultsMessage("no-organizations-were-found");
		_searchContainer.setOrderByCol(getOrderByCol());
		_searchContainer.setOrderByComparator(
			_usersAdmin.getOrganizationOrderByComparator(
				getOrderByCol(), getOrderByType()));
		_searchContainer.setOrderByType(getOrderByType());

		OrganizationSearchTerms organizationSearchTerms =
			(OrganizationSearchTerms)_searchContainer.getSearchTerms();

		_searchContainer.setResultsAndTotal(
			_organizationLocalService.searchOrganizations(
				CompanyThreadLocal.getCompanyId(),
				OrganizationConstants.ANY_PARENT_ORGANIZATION_ID,
				organizationSearchTerms.getKeywords(), null,
				_searchContainer.getStart(), _searchContainer.getEnd(),
				SortFactoryUtil.getSort(
					Organization.class, _searchContainer.getOrderByCol(),
					_searchContainer.getOrderByType())));

		_searchContainer.setRowChecker(
			new OrganizationItemSelectorChecker(
				_renderResponse, _getCheckedOrganizationIds()));

		return _searchContainer;
	}

	private long[] _getCheckedOrganizationIds() {
		return ParamUtil.getLongValues(
			_renderRequest, "checkedOrganizationIds");
	}

	private final OrganizationLocalService _organizationLocalService;
	private final PortletURL _portletURL;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<Organization> _searchContainer;
	private final UsersAdmin _usersAdmin;

}