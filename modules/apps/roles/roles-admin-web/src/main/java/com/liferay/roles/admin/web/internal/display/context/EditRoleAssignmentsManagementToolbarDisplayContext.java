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

package com.liferay.roles.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownGroupItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.persistence.constants.UserGroupFinderConstants;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.rolesadmin.search.GroupRoleChecker;
import com.liferay.portlet.rolesadmin.search.OrganizationRoleChecker;
import com.liferay.portlet.rolesadmin.search.SetUserRoleChecker;
import com.liferay.portlet.rolesadmin.search.UnsetUserRoleChecker;
import com.liferay.portlet.rolesadmin.search.UserGroupRoleChecker;
import com.liferay.portlet.usergroupsadmin.search.UserGroupDisplayTerms;
import com.liferay.portlet.usergroupsadmin.search.UserGroupSearch;
import com.liferay.portlet.usersadmin.search.GroupSearch;
import com.liferay.portlet.usersadmin.search.GroupSearchTerms;
import com.liferay.portlet.usersadmin.search.OrganizationSearch;
import com.liferay.portlet.usersadmin.search.OrganizationSearchTerms;
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
public class EditRoleAssignmentsManagementToolbarDisplayContext {

	public EditRoleAssignmentsManagementToolbarDisplayContext(
			HttpServletRequest httpServletRequest, RenderRequest renderRequest,
			RenderResponse renderResponse, String displayStyle, String tabs3)
		throws PortalException {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_displayStyle = displayStyle;
		_tabs3 = tabs3;

		long roleId = ParamUtil.getLong(httpServletRequest, "roleId");

		_role = RoleServiceUtil.fetchRole(roleId);
	}

	public List<DropdownItem> getActionDropdownItems() throws Exception {
		SearchContainer searchContainer = getSearchContainer();

		if (Objects.equals(getTabs2(), "users") &&
			Objects.equals(_role.getName(), RoleConstants.ADMINISTRATOR) &&
			(searchContainer.getTotal() == 1)) {

			return null;
		}

		return DropdownItemList.of(
			() -> {
				DropdownItem dropdownItem = new DropdownItem();

				dropdownItem.setHref(
					StringBundler.concat(
						"javascript:", _renderResponse.getNamespace(),
						"unsetRoleAssignments();"));
				dropdownItem.setIcon("trash");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "delete"));
				dropdownItem.setQuickAction(true);

				return dropdownItem;
			});
	}

	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public List<DropdownItem> getFilterDropdownItems() {
		return DropdownItemList.of(
			() -> {
				DropdownGroupItem dropdownGroupItem = new DropdownGroupItem();

				dropdownGroupItem.setDropdownItems(
					DropdownItemList.of(
						() -> {
							DropdownItem dropdownItem = new DropdownItem();

							dropdownItem.setActive(true);
							dropdownItem.setHref(StringPool.BLANK);
							dropdownItem.setLabel(
								LanguageUtil.get(_httpServletRequest, "all"));

							return dropdownItem;
						}));
				dropdownGroupItem.setLabel(
					LanguageUtil.get(
						_httpServletRequest, "filter-by-navigation"));

				return dropdownGroupItem;
			},
			() -> {
				DropdownGroupItem dropdownGroupItem = new DropdownGroupItem();

				dropdownGroupItem.setDropdownItems(
					DropdownItemList.of(
						() -> {
							DropdownItem dropdownItem = new DropdownItem();

							dropdownItem.setActive(
								Objects.equals(getOrderByCol(), "name"));
							dropdownItem.setHref(
								getPortletURL(), "orderByCol", "name");
							dropdownItem.setLabel(
								LanguageUtil.get(_httpServletRequest, "name"));

							return dropdownItem;
						}));
				dropdownGroupItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "order-by"));

				return dropdownGroupItem;
			});
	}

	public SearchContainer getGroupSearchContainer() {
		GroupSearch groupSearch = new GroupSearch(
			_renderRequest, getPortletURL());

		if (_tabs3.equals("available")) {
			groupSearch.setRowChecker(
				new GroupRoleChecker(_renderResponse, _role));
		}
		else {
			groupSearch.setRowChecker(
				new EmptyOnClickRowChecker(_renderResponse));
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		LinkedHashMap<String, Object> groupParams = new LinkedHashMap<>();

		if (_tabs3.equals("current")) {
			groupParams.put("groupsRoles", Long.valueOf(_role.getRoleId()));
			groupParams.put("site", Boolean.TRUE);
		}

		GroupSearchTerms searchTerms =
			(GroupSearchTerms)groupSearch.getSearchTerms();

		List<Group> results = GroupLocalServiceUtil.search(
			themeDisplay.getCompanyId(), searchTerms.getKeywords(), groupParams,
			groupSearch.getStart(), groupSearch.getEnd(),
			groupSearch.getOrderByComparator());

		int total = GroupLocalServiceUtil.searchCount(
			themeDisplay.getCompanyId(), searchTerms.getKeywords(),
			groupParams);

		groupSearch.setResults(results);
		groupSearch.setTotal(total);

		return groupSearch;
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
				_httpServletRequest, "orderByCol", "name");
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

	public SearchContainer getOrganizationSearchContainer()
		throws PortalException {

		OrganizationSearch organizationSearch = new OrganizationSearch(
			_renderRequest, getPortletURL());

		if (_tabs3.equals("available")) {
			organizationSearch.setRowChecker(
				new OrganizationRoleChecker(_renderResponse, _role));
		}
		else {
			organizationSearch.setRowChecker(
				new EmptyOnClickRowChecker(_renderResponse));
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long parentOrganizationId =
			OrganizationConstants.ANY_PARENT_ORGANIZATION_ID;

		LinkedHashMap<String, Object> organizationParams =
			new LinkedHashMap<>();

		if (_tabs3.equals("current")) {
			organizationParams.put(
				"organizationsRoles", Long.valueOf(_role.getRoleId()));
		}

		OrganizationSearchTerms searchTerms =
			(OrganizationSearchTerms)organizationSearch.getSearchTerms();

		List<Organization> results = null;
		int total = 0;

		Indexer<?> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			Organization.class);

		if (!_tabs3.equals("current") && indexer.isIndexerEnabled() &&
			PropsValues.ORGANIZATIONS_SEARCH_WITH_INDEX) {

			organizationParams.put("expandoAttributes", getKeywords());

			Sort sort = SortFactoryUtil.getSort(
				Organization.class, organizationSearch.getOrderByCol(),
				organizationSearch.getOrderByType());

			BaseModelSearchResult<Organization> baseModelSearchResult =
				OrganizationLocalServiceUtil.searchOrganizations(
					themeDisplay.getCompanyId(), parentOrganizationId,
					searchTerms.getKeywords(), organizationParams,
					organizationSearch.getStart(), organizationSearch.getEnd(),
					sort);

			results = baseModelSearchResult.getBaseModels();
			total = baseModelSearchResult.getLength();
		}
		else {
			total = OrganizationLocalServiceUtil.searchCount(
				themeDisplay.getCompanyId(), parentOrganizationId,
				searchTerms.getKeywords(), searchTerms.getType(),
				searchTerms.getRegionIdObj(), searchTerms.getCountryIdObj(),
				organizationParams);

			results = OrganizationLocalServiceUtil.search(
				themeDisplay.getCompanyId(), parentOrganizationId,
				getKeywords(), searchTerms.getType(),
				searchTerms.getRegionIdObj(), searchTerms.getCountryIdObj(),
				organizationParams, organizationSearch.getStart(),
				organizationSearch.getEnd(),
				organizationSearch.getOrderByComparator());
		}

		organizationSearch.setResults(results);
		organizationSearch.setTotal(total);

		return organizationSearch;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		if (_tabs3.equals("current")) {
			portletURL.setParameter("mvcPath", "/edit_role_assignments.jsp");
		}
		else {
			portletURL.setParameter("mvcPath", "/select_assignees.jsp");
		}

		portletURL.setParameter("tabs1", "assignees");
		portletURL.setParameter("tabs2", getTabs2());
		portletURL.setParameter("tabs3", _tabs3);
		portletURL.setParameter("roleId", String.valueOf(_role.getRoleId()));

		String redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		portletURL.setParameter("redirect", redirect);

		portletURL.setParameter("displayStyle", _displayStyle);

		if (Validator.isNotNull(getKeywords())) {
			portletURL.setParameter("keywords", getKeywords());
		}

		portletURL.setParameter("orderByCol", getOrderByCol());
		portletURL.setParameter("orderByType", getOrderByType());

		if (_searchContainer != null) {
			portletURL.setParameter(
				_searchContainer.getCurParam(),
				String.valueOf(_searchContainer.getCur()));
			portletURL.setParameter(
				_searchContainer.getDeltaParam(),
				String.valueOf(_searchContainer.getDelta()));
		}

		return portletURL;
	}

	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		return searchActionURL.toString();
	}

	public SearchContainer getSearchContainer() throws Exception {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		if (Objects.equals(getTabs2(), "organizations")) {
			_searchContainer = getOrganizationSearchContainer();
		}
		else if (Objects.equals(getTabs2(), "sites")) {
			_searchContainer = getGroupSearchContainer();
		}
		else if (Objects.equals(getTabs2(), "user-groups")) {
			_searchContainer = getUserGroupSearchContainer();
		}
		else {
			_searchContainer = getUserSearchContainer();
		}

		return _searchContainer;
	}

	public String getSortingURL() {
		PortletURL sortingURL = getPortletURL();

		sortingURL.setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	public String getTabs2() {
		if (Validator.isNull(_tabs2)) {
			_tabs2 = ParamUtil.getString(_httpServletRequest, "tabs2", "users");
		}

		return _tabs2;
	}

	public SearchContainer getUserGroupSearchContainer() {
		UserGroupSearch userGroupSearch = new UserGroupSearch(
			_renderRequest, getPortletURL());

		if (_tabs3.equals("available")) {
			userGroupSearch.setRowChecker(
				new UserGroupRoleChecker(_renderResponse, _role));
		}
		else {
			userGroupSearch.setRowChecker(
				new EmptyOnClickRowChecker(_renderResponse));
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		LinkedHashMap<String, Object> userGroupParams = new LinkedHashMap<>();

		if (_tabs3.equals("current")) {
			userGroupParams.put(
				UserGroupFinderConstants.PARAM_KEY_USER_GROUPS_ROLES,
				Long.valueOf(_role.getRoleId()));
		}

		UserGroupDisplayTerms searchTerms =
			(UserGroupDisplayTerms)userGroupSearch.getSearchTerms();

		String keywords = searchTerms.getKeywords();

		if (Validator.isNotNull(keywords)) {
			userGroupParams.put("expandoAttributes", keywords);
		}

		List<UserGroup> results = UserGroupLocalServiceUtil.search(
			themeDisplay.getCompanyId(), keywords, userGroupParams,
			userGroupSearch.getStart(), userGroupSearch.getEnd(),
			userGroupSearch.getOrderByComparator());

		int total = UserGroupLocalServiceUtil.searchCount(
			themeDisplay.getCompanyId(), keywords, userGroupParams);

		userGroupSearch.setResults(results);
		userGroupSearch.setTotal(total);

		return userGroupSearch;
	}

	public SearchContainer getUserSearchContainer() {
		UserSearch userSearch = new UserSearch(_renderRequest, getPortletURL());

		if (_tabs3.equals("available")) {
			userSearch.setRowChecker(
				new SetUserRoleChecker(_renderResponse, _role));
		}
		else {
			userSearch.setRowChecker(
				new UnsetUserRoleChecker(_renderResponse, _role));
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		LinkedHashMap<String, Object> userParams = new LinkedHashMap<>();

		if (_tabs3.equals("current")) {
			userParams.put("usersRoles", Long.valueOf(_role.getRoleId()));
		}

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

		return userSearch;
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

	private final String _displayStyle;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final Role _role;
	private SearchContainer _searchContainer;
	private String _tabs2;
	private final String _tabs3;

}