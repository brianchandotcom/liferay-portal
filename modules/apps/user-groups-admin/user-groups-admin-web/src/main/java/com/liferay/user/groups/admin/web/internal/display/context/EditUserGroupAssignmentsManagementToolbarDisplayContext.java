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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.UserGroupServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.usergroupsadmin.search.SetUserUserGroupChecker;
import com.liferay.portlet.usergroupsadmin.search.UnsetUserUserGroupChecker;
import com.liferay.portlet.usersadmin.search.UserSearch;
import com.liferay.portlet.usersadmin.search.UserSearchTerms;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class EditUserGroupAssignmentsManagementToolbarDisplayContext {

	public EditUserGroupAssignmentsManagementToolbarDisplayContext(
			HttpServletRequest httpServletRequest, RenderRequest renderRequest,
			RenderResponse renderResponse, String displayStyle, String mvcPath)
		throws PortalException {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_displayStyle = displayStyle;
		_mvcPath = mvcPath;

		long userGroupId = ParamUtil.getLong(httpServletRequest, "userGroupId");

		_userGroup = UserGroupServiceUtil.fetchUserGroup(userGroupId);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			() -> _hasAddUserGroupPermission(),
			dropdownItem -> {
				dropdownItem.putData("action", "removeUsers");
				dropdownItem.setIcon("minus-circle");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "remove"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.putData("action", "addUsers");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "add-users"));
			}
		).build();
	}

	public List<DropdownItem> getFilterDropdownItems() {
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					_getFilterNavigationDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(
						_httpServletRequest, "filter-by-navigation"));
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(_getOrderByDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "order-by"));
			}
		).build();
	}

	public String getKeywords() {
		if (Validator.isNull(_keywords)) {
			_keywords = ParamUtil.getString(_httpServletRequest, "keywords");
		}

		return _keywords;
	}

	public String getOrderByCol() {
		if (Validator.isNull(_orderByCol)) {
			_orderByCol = ParamUtil.getString(
				_httpServletRequest, "orderByCol", "first-name");
		}

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNull(_orderByType)) {
			_orderByType = ParamUtil.getString(
				_httpServletRequest, "orderByType", "asc");
		}

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", _mvcPath);
		portletURL.setParameter(
			"userGroupId", String.valueOf(_userGroup.getUserGroupId()));

		String redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		portletURL.setParameter("redirect", redirect);

		portletURL.setParameter("displayStyle", _displayStyle);

		if (Validator.isNotNull(getKeywords())) {
			portletURL.setParameter("keywords", getKeywords());
		}

		portletURL.setParameter("orderByCol", getOrderByCol());
		portletURL.setParameter("orderByType", getOrderByType());

		if (_userSearch != null) {
			portletURL.setParameter(
				_userSearch.getCurParam(),
				String.valueOf(_userSearch.getCur()));
			portletURL.setParameter(
				_userSearch.getDeltaParam(),
				String.valueOf(_userSearch.getDelta()));
		}

		return portletURL;
	}

	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		searchActionURL.setParameter(
			"redirect", PortalUtil.getCurrentURL(_httpServletRequest));

		return searchActionURL.toString();
	}

	public SearchContainer<User> getSearchContainer(
			LinkedHashMap<String, Object> userParams)
		throws Exception {

		if (_userSearch != null) {
			return _userSearch;
		}

		UserSearch userSearch = new UserSearch(_renderRequest, getPortletURL());

		if (_mvcPath.equals("/edit_user_group_assignments.jsp")) {
			userSearch.setRowChecker(
				new UnsetUserUserGroupChecker(_renderResponse, _userGroup));
		}
		else if (_mvcPath.equals("/select_user_group_users.jsp")) {
			userSearch.setRowChecker(
				new SetUserUserGroupChecker(_renderResponse, _userGroup));
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		UserSearchTerms searchTerms =
			(UserSearchTerms)userSearch.getSearchTerms();

		List<User> results = UserLocalServiceUtil.search(
			themeDisplay.getCompanyId(), searchTerms.getKeywords(),
			searchTerms.getStatus(), userParams, userSearch.getStart(),
			userSearch.getEnd(), userSearch.getOrderByComparator());

		int total = UserLocalServiceUtil.searchCount(
			themeDisplay.getCompanyId(), searchTerms.getKeywords(),
			searchTerms.getStatus(), userParams);

		userSearch.setResults(results);
		userSearch.setTotal(total);

		_userSearch = userSearch;

		return _userSearch;
	}

	public String getSortingURL() {
		PortletURL sortingURL = getPortletURL();

		sortingURL.setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	public List<ViewTypeItem> getViewTypeItems() {
		return new ViewTypeItemList(getPortletURL(), _displayStyle) {
			{
				addCardViewTypeItem();
				addListViewTypeItem();
				addTableViewTypeItem();
			}
		};
	}

	public boolean showCreationMenu() throws PortalException {
		return _hasAddUserGroupPermission();
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setActive(true);
				dropdownItem.setHref(StringPool.BLANK);
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "all"));
			}
		).build();
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setActive(
					Objects.equals(getOrderByCol(), "first-name"));
				dropdownItem.setHref(
					getPortletURL(), "orderByCol", "first-name");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "first-name"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setActive(
					Objects.equals(getOrderByCol(), "screen-name"));
				dropdownItem.setHref(
					getPortletURL(), "orderByCol", "screen-name");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "screen-name"));
			}
		).build();
	}

	private boolean _hasAddUserGroupPermission() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return PortalPermissionUtil.contains(
			themeDisplay.getPermissionChecker(), ActionKeys.ADD_USER_GROUP);
	}

	private final String _displayStyle;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private final String _mvcPath;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final UserGroup _userGroup;
	private UserSearch _userSearch;

}