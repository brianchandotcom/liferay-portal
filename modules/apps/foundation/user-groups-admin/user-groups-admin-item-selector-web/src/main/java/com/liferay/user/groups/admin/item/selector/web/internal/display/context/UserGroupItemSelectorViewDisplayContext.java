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

package com.liferay.user.groups.admin.item.selector.web.internal.display.context;

import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portlet.usergroupsadmin.search.UserGroupSearch;
import com.liferay.user.groups.admin.item.selector.web.internal.search.UserGroupItemSelectorChecker;
import com.liferay.user.groups.admin.item.selector.web.internal.util.UserGroupsAdminItemSelectorViewUtil;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class UserGroupItemSelectorViewDisplayContext {

	public UserGroupItemSelectorViewDisplayContext(
		UserGroupLocalService userGroupLocalService,
		HttpServletRequest httpServletRequest, PortletURL portletURL,
		String itemSelectedEventName) {

		_userGroupLocalService = userGroupLocalService;
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

	public PortletURL getPortletURL() {
		return _portletURL;
	}

	public SearchContainer<UserGroup> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new UserGroupSearch(_renderRequest, getPortletURL());

		OrderByComparator<UserGroup> orderByComparator =
			UserGroupsAdminItemSelectorViewUtil.getUserGroupOrderByComparator(
				getOrderByCol(), getOrderByType());

		RowChecker rowChecker = new UserGroupItemSelectorChecker(
			_renderResponse, getCheckedUserGroupIds());

		_searchContainer.setOrderByCol(getOrderByCol());
		_searchContainer.setOrderByComparator(orderByComparator);
		_searchContainer.setOrderByType(getOrderByType());
		_searchContainer.setRowChecker(rowChecker);

		int total = _userGroupLocalService.getUserGroupsCount();

		_searchContainer.setTotal(total);

		List<UserGroup> results = _userGroupLocalService.getUserGroups(
			_searchContainer.getStart(), _searchContainer.getEnd());

		_searchContainer.setResults(results);

		return _searchContainer;
	}

	protected long[] getCheckedUserGroupIds() {
		return ParamUtil.getLongValues(_renderRequest, "checkedUserGroupIds");
	}

	private final HttpServletRequest _httpServletRequest;
	private final String _itemSelectedEventName;
	private final PortletURL _portletURL;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<UserGroup> _searchContainer;
	private final UserGroupLocalService _userGroupLocalService;

}