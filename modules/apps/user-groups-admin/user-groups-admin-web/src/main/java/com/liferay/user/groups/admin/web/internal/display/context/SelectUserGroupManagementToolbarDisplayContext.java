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

package com.liferay.user.groups.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.usergroupsadmin.search.UserGroupDisplayTerms;
import com.liferay.portlet.usergroupsadmin.search.UserGroupSearch;
import com.liferay.users.admin.kernel.util.UsersAdminUtil;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class SelectUserGroupManagementToolbarDisplayContext {

	public SelectUserGroupManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public String getClearResultsURL() {
		return PortletURLBuilder.create(
			getPortletURL()
		).setKeywords(
			StringPool.BLANK
		).buildString();
	}

	public PortletURL getPortletURL() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCPath(
			"/select_user_group.jsp"
		).setKeywords(
			() -> {
				String[] keywords = ParamUtil.getStringValues(
					_httpServletRequest, "keywords");

				if (ArrayUtil.isNotEmpty(keywords)) {
					return keywords[keywords.length - 1];
				}

				return null;
			}
		).setParameter(
			"eventName",
			ParamUtil.getString(
				_httpServletRequest, "eventName",
				_renderResponse.getNamespace() + "selectUserGroup")
		).setParameter(
			"p_u_i_d",
			() -> {
				User selUser = _getSelectedUser();

				if (selUser != null) {
					return selUser.getUserId();
				}

				return null;
			}
		).buildPortletURL();
	}

	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		return searchActionURL.toString();
	}

	public SearchContainer<UserGroup> getSearchContainer(
			boolean filterManageableUserGroups)
		throws Exception {

		if (_userGroupSearch != null) {
			return _userGroupSearch;
		}

		UserGroupSearch userGroupSearch = new UserGroupSearch(
			_renderRequest, getPortletURL());

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		UserGroupDisplayTerms searchTerms =
			(UserGroupDisplayTerms)userGroupSearch.getSearchTerms();

		if (filterManageableUserGroups) {
			userGroupSearch.setResultsAndTotal(
				UsersAdminUtil.filterUserGroups(
					themeDisplay.getPermissionChecker(),
					UserGroupLocalServiceUtil.search(
						themeDisplay.getCompanyId(), searchTerms.getKeywords(),
						null, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
						userGroupSearch.getOrderByComparator())));
		}
		else {
			userGroupSearch.setResultsAndTotal(
				() -> UserGroupLocalServiceUtil.search(
					themeDisplay.getCompanyId(), searchTerms.getKeywords(),
					null, userGroupSearch.getStart(), userGroupSearch.getEnd(),
					userGroupSearch.getOrderByComparator()),
				UserGroupLocalServiceUtil.searchCount(
					themeDisplay.getCompanyId(), searchTerms.getKeywords(),
					null));
		}

		_userGroupSearch = userGroupSearch;

		return _userGroupSearch;
	}

	public List<ViewTypeItem> getViewTypeItems() {
		return new ViewTypeItemList(getPortletURL(), "list") {
			{
				addTableViewTypeItem();
			}
		};
	}

	private User _getSelectedUser() {
		try {
			return PortalUtil.getSelectedUser(_httpServletRequest);
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SelectUserGroupManagementToolbarDisplayContext.class);

	private final HttpServletRequest _httpServletRequest;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private UserGroupSearch _userGroupSearch;

}