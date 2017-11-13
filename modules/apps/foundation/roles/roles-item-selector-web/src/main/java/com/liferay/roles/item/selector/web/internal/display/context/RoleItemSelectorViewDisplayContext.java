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

package com.liferay.roles.item.selector.web.internal.display.context;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.rolesadmin.search.RoleSearch;
import com.liferay.portlet.rolesadmin.search.RoleSearchTerms;
import com.liferay.roles.item.selector.web.internal.search.RoleItemSelectorChecker;
import com.liferay.users.admin.kernel.util.UsersAdmin;

import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class RoleItemSelectorViewDisplayContext {

	public RoleItemSelectorViewDisplayContext(
		RoleLocalService roleLocalService, UsersAdmin usersAdmin,
		HttpServletRequest httpServletRequest, PortletURL portletURL,
		String itemSelectedEventName, int type) {

		_roleLocalService = roleLocalService;
		_usersAdmin = usersAdmin;
		_httpServletRequest = httpServletRequest;
		_portletURL = portletURL;
		_itemSelectedEventName = itemSelectedEventName;
		_type = type;

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

	public PortletURL getPortletURL() throws PortletException {
		PortletURL portletURL = PortletURLUtil.clone(
			_portletURL, _renderResponse);

		portletURL.setParameter(
			"keywords", ParamUtil.getString(_httpServletRequest, "keywords"));
		portletURL.setParameter("type", String.valueOf(getType()));

		return portletURL;
	}

	public SearchContainer<Role> getSearchContainer() throws Exception {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new RoleSearch(_renderRequest, getPortletURL());

		_searchContainer.setEmptyResultsMessage("no-roles-were-found");

		OrderByComparator<Role> orderByComparator =
			_usersAdmin.getRoleOrderByComparator(
				getOrderByCol(), getOrderByType());

		RowChecker rowChecker = new RoleItemSelectorChecker(
			_renderResponse, getCheckedRoleIds());

		_searchContainer.setOrderByCol(getOrderByCol());
		_searchContainer.setOrderByComparator(orderByComparator);
		_searchContainer.setOrderByType(getOrderByType());
		_searchContainer.setRowChecker(rowChecker);

		RoleSearchTerms searchTerms =
			(RoleSearchTerms)_searchContainer.getSearchTerms();

		searchTerms.setType(getType());

		List<Role> results = _roleLocalService.search(
			CompanyThreadLocal.getCompanyId(), searchTerms.getKeywords(),
			searchTerms.getTypesObj(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			_searchContainer.getOrderByComparator());

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!permissionChecker.isOmniadmin()) {
			results = _usersAdmin.filterRoles(permissionChecker, results);
		}

		_searchContainer.setTotal(results.size());

		_searchContainer.setResults(
			ListUtil.subList(
				results, _searchContainer.getStart(),
				_searchContainer.getEnd()));

		return _searchContainer;
	}

	public int getType() {
		return _type;
	}

	protected long[] getCheckedRoleIds() {
		return ParamUtil.getLongValues(_renderRequest, "checkedRoleIds");
	}

	private final HttpServletRequest _httpServletRequest;
	private final String _itemSelectedEventName;
	private final PortletURL _portletURL;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final RoleLocalService _roleLocalService;
	private SearchContainer<Role> _searchContainer;
	private final int _type;
	private final UsersAdmin _usersAdmin;

}