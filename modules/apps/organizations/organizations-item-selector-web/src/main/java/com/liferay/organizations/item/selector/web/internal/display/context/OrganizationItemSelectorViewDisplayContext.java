/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.organizations.item.selector.web.internal.display.context;

import com.liferay.organizations.item.selector.OrganizationItemSelectorCriterion;
import com.liferay.organizations.item.selector.web.internal.search.OrganizationItemSelectorChecker;
import com.liferay.organizations.search.OrganizationSearch;
import com.liferay.organizations.search.OrganizationSearchTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
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
		OrganizationItemSelectorCriterion organizationItemSelectorCriterion,
		OrganizationLocalService organizationLocalService,
		UsersAdmin usersAdmin, HttpServletRequest httpServletRequest,
		PortletURL portletURL) {

		_organizationItemSelectorCriterion = organizationItemSelectorCriterion;
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
			() -> _organizationLocalService.search(
				CompanyThreadLocal.getCompanyId(),
				OrganizationConstants.ANY_PARENT_ORGANIZATION_ID,
				organizationSearchTerms.getKeywords(), null, null, null, null,
				_searchContainer.getStart(), _searchContainer.getEnd(),
				_searchContainer.getOrderByComparator()),
			_organizationLocalService.searchCount(
				CompanyThreadLocal.getCompanyId(),
				OrganizationConstants.ANY_PARENT_ORGANIZATION_ID,
				organizationSearchTerms.getKeywords(), null, null, null, null));

		_searchContainer.setRowChecker(
			new OrganizationItemSelectorChecker(
				_renderResponse,
				_organizationItemSelectorCriterion.
					getSelectedOrganizationIds()));

		return _searchContainer;
	}

	private final OrganizationItemSelectorCriterion
		_organizationItemSelectorCriterion;
	private final OrganizationLocalService _organizationLocalService;
	private final PortletURL _portletURL;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<Organization> _searchContainer;
	private final UsersAdmin _usersAdmin;

}