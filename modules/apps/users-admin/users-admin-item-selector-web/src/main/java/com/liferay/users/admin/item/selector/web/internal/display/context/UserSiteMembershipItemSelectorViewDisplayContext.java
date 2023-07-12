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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.usersadmin.search.UserSearch;
import com.liferay.portlet.usersadmin.search.UserSearchTerms;
import com.liferay.users.admin.item.selector.UserSiteMembershipItemSelectorCriterion;
import com.liferay.users.admin.item.selector.web.internal.search.UserSiteMembershipChecker;

import java.util.LinkedHashMap;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class UserSiteMembershipItemSelectorViewDisplayContext {

	public UserSiteMembershipItemSelectorViewDisplayContext(
		HttpServletRequest httpServletRequest, PortletURL portletURL,
		RenderRequest renderRequest, RenderResponse renderResponse,
		UserSiteMembershipItemSelectorCriterion
			userSiteMembershipItemSelectorCriterion) {

		_httpServletRequest = httpServletRequest;
		_portletURL = portletURL;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_userSiteMembershipItemSelectorCriterion =
			userSiteMembershipItemSelectorCriterion;
	}

	public SearchContainer<User> getUserSearchContainer() {
		if (_userSearch != null) {
			return _userSearch;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		UserSearch userSearch = new UserSearch(_renderRequest, _portletURL);

		Group group = GroupLocalServiceUtil.fetchGroup(
			_userSiteMembershipItemSelectorCriterion.getGroupId());

		UserSearchTerms searchTerms =
			(UserSearchTerms)userSearch.getSearchTerms();

		LinkedHashMap<String, Object> userParams = new LinkedHashMap<>();

		if (group.isLimitedToParentSiteMembers()) {
			userParams.put("inherit", Boolean.TRUE);
			userParams.put("usersGroups", group.getParentGroupId());
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			if (permissionChecker.isGroupAdmin(
					_userSiteMembershipItemSelectorCriterion.getGroupId())) {

				PermissionThreadLocal.setPermissionChecker(null);
			}

			userSearch.setResultsAndTotal(
				() -> UserLocalServiceUtil.search(
					themeDisplay.getCompanyId(), searchTerms.getKeywords(),
					searchTerms.getStatus(), userParams, userSearch.getStart(),
					userSearch.getEnd(), userSearch.getOrderByComparator()),
				UserLocalServiceUtil.searchCount(
					themeDisplay.getCompanyId(), searchTerms.getKeywords(),
					searchTerms.getStatus(), userParams));
			userSearch.setRowChecker(
				new UserSiteMembershipChecker(_renderResponse, group));

			_userSearch = userSearch;
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}

		return _userSearch;
	}

	private final HttpServletRequest _httpServletRequest;
	private final PortletURL _portletURL;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private UserSearch _userSearch;
	private final UserSiteMembershipItemSelectorCriterion
		_userSiteMembershipItemSelectorCriterion;

}