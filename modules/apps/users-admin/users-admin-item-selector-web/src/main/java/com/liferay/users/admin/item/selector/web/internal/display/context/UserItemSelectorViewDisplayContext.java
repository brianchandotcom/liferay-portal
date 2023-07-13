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

package com.liferay.users.admin.item.selector.web.internal.display.context;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portlet.usersadmin.search.UserSearch;
import com.liferay.portlet.usersadmin.search.UserSearchTerms;
import com.liferay.users.admin.item.selector.web.internal.search.UserItemSelectorChecker;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class UserItemSelectorViewDisplayContext {

	public UserItemSelectorViewDisplayContext(
		HttpServletRequest httpServletRequest, PortletURL portletURL,
		UserLocalService userLocalService) {

		_portletURL = portletURL;
		_userLocalService = userLocalService;

		_portletRequest = (PortletRequest)httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
		_renderResponse = (RenderResponse)httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);
	}

	public SearchContainer<User> getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new UserSearch(_portletRequest, _portletURL);

		UserSearchTerms userSearchTerms =
			(UserSearchTerms)_searchContainer.getSearchTerms();

		_searchContainer.setResultsAndTotal(
			() -> _userLocalService.search(
				CompanyThreadLocal.getCompanyId(),
				userSearchTerms.getKeywords(), userSearchTerms.getStatus(),
				null, _searchContainer.getStart(), _searchContainer.getEnd(),
				_searchContainer.getOrderByComparator()),
			_userLocalService.searchCount(
				CompanyThreadLocal.getCompanyId(),
				userSearchTerms.getKeywords(), userSearchTerms.getStatus(),
				null));

		_searchContainer.setRowChecker(
			new UserItemSelectorChecker(
				_renderResponse, _getCheckedUserIds(),
				_isCheckedUseIdsEnable()));

		return _searchContainer;
	}

	private long[] _getCheckedUserIds() {
		return ParamUtil.getLongValues(_portletRequest, "checkedUserIds");
	}

	private boolean _isCheckedUseIdsEnable() {
		return ParamUtil.getBoolean(_portletRequest, "checkedUserIdsEnabled");
	}

	private final PortletRequest _portletRequest;
	private final PortletURL _portletURL;
	private final RenderResponse _renderResponse;
	private SearchContainer<User> _searchContainer;
	private final UserLocalService _userLocalService;

}