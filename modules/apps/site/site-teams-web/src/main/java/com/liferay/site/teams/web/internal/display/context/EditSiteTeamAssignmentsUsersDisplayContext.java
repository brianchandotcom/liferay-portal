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

package com.liferay.site.teams.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.usersadmin.search.UserSearch;
import com.liferay.portlet.usersadmin.search.UserSearchTerms;
import com.liferay.site.teams.web.internal.constants.SiteTeamsPortletKeys;
import com.liferay.users.admin.kernel.util.UsersAdminUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class EditSiteTeamAssignmentsUsersDisplayContext
	extends EditSiteTeamAssignmentsDisplayContext {

	public EditSiteTeamAssignmentsUsersDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		HttpServletRequest request) {

		super(renderRequest, renderResponse, request);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData("action", "deleteUsers");
						dropdownItem.setIcon("times-circle");
						dropdownItem.setLabel(
							LanguageUtil.get(request, "delete"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	public String getClearResultsURL() {
		PortletURL clearResultsURL = getEditTeamAssignmentsURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(request);

		_displayStyle = portalPreferences.getValue(
			SiteTeamsPortletKeys.SITE_TEAMS, "display-style", "icon");

		return _displayStyle;
	}

	@Override
	public PortletURL getEditTeamAssignmentsURL() {
		PortletURL portletURL = super.getEditTeamAssignmentsURL();

		String keywords = getKeywords();

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		String orderByCol = getOrderByCol();

		if (Validator.isNotNull(orderByCol)) {
			portletURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = getOrderByType();

		if (Validator.isNotNull(orderByType)) {
			portletURL.setParameter("orderByType", orderByType);
		}

		return portletURL;
	}

	public List<DropdownItem> getFilterDropdownItems() {
		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getFilterNavigationDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(request, "filter-by-navigation"));
					});

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getOrderByDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(request, "order-by"));
					});
			}
		};
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(request, "keywords");

		return _keywords;
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(request, "orderByCol", "first-name");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(request, "orderByType", "asc");

		return _orderByType;
	}

	public String getSearchActionURL() {
		PortletURL searchActionURL = getEditTeamAssignmentsURL();

		return searchActionURL.toString();
	}

	public String getSortingURL() {
		PortletURL sortingURL = getEditTeamAssignmentsURL();

		sortingURL.setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	public int getTotalItems() {
		SearchContainer userSearchContainer = getUserSearchContainer();

		return userSearchContainer.getTotal();
	}

	public SearchContainer getUserSearchContainer() {
		if (_userSearchContainer != null) {
			return _userSearchContainer;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		SearchContainer userSearchContainer = new UserSearch(
			renderRequest, getEditTeamAssignmentsURL());

		OrderByComparator<User> orderByComparator =
			UsersAdminUtil.getUserOrderByComparator(
				getOrderByCol(), getOrderByType());

		userSearchContainer.setOrderByCol(getOrderByCol());
		userSearchContainer.setOrderByComparator(orderByComparator);
		userSearchContainer.setOrderByType(getOrderByType());

		userSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(renderResponse));

		UserSearchTerms searchTerms =
			(UserSearchTerms)userSearchContainer.getSearchTerms();

		LinkedHashMap<String, Object> userParams = new LinkedHashMap<>();

		userParams.put("inherit", Boolean.TRUE);
		userParams.put("usersTeams", getTeamId());

		int usersCount = UserLocalServiceUtil.searchCount(
			themeDisplay.getCompanyId(), searchTerms.getKeywords(),
			searchTerms.getStatus(), userParams);

		userSearchContainer.setTotal(usersCount);

		List<User> users = UserLocalServiceUtil.search(
			themeDisplay.getCompanyId(), searchTerms.getKeywords(),
			searchTerms.getStatus(), userParams, userSearchContainer.getStart(),
			userSearchContainer.getEnd(),
			userSearchContainer.getOrderByComparator());

		userSearchContainer.setResults(users);

		_userSearchContainer = userSearchContainer;

		return _userSearchContainer;
	}

	public List<ViewTypeItem> getViewTypeItems() {
		PortletURL portletURL = renderResponse.createActionURL();

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "changeDisplayStyle");
		portletURL.setParameter("redirect", PortalUtil.getCurrentURL(request));

		return new ViewTypeItemList(portletURL, getDisplayStyle()) {
			{
				addCardViewTypeItem();
				addListViewTypeItem();
				addTableViewTypeItem();
			}
		};
	}

	public boolean isDisabledManagementBar() {
		if (getTotalItems() <= 0) {
			return true;
		}

		return false;
	}

	public boolean isShowSearch() {
		if (getTotalItems() > 0) {
			return true;
		}

		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(true);
						dropdownItem.setHref(getEditTeamAssignmentsURL());
						dropdownItem.setLabel(LanguageUtil.get(request, "all"));
					});
			}
		};
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(getOrderByCol(), "first-name"));
						dropdownItem.setHref(
							getEditTeamAssignmentsURL(), "orderByCol",
							"first-name");
						dropdownItem.setLabel(
							LanguageUtil.get(request, "first-name"));
					});

				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(getOrderByCol(), "screen-name"));
						dropdownItem.setHref(
							getEditTeamAssignmentsURL(), "orderByCol",
							"screen-name");
						dropdownItem.setLabel(
							LanguageUtil.get(request, "screen-name"));
					});
			}
		};
	}

	private String _displayStyle;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private SearchContainer _userSearchContainer;

}