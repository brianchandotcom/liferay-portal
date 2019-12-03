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

package com.liferay.site.browser.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.membershippolicy.SiteMembershipPolicyUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.usersadmin.search.GroupSearch;
import com.liferay.portlet.usersadmin.search.GroupSearchTerms;
import com.liferay.sites.kernel.util.SitesUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class SiteBrowserDisplayContext {

	public SiteBrowserDisplayContext(
			HttpServletRequest httpServletRequest,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		_httpServletRequest = httpServletRequest;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_selUser = PortalUtil.getSelectedUser(_httpServletRequest);
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle", "list");

		return _displayStyle;
	}

	public String getEventName() {
		if (_eventName != null) {
			return _eventName;
		}

		_eventName = ParamUtil.getString(
			_httpServletRequest, "eventName",
			_liferayPortletResponse.getNamespace() + "selectSite");

		return _eventName;
	}

	public GroupSearch getGroupSearch() throws Exception {
		if (_groupSearch != null) {
			return _groupSearch;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		GroupSearch groupSearch = new GroupSearch(
			_liferayPortletRequest, getPortletURL());

		GroupSearchTerms groupSearchTerms =
			(GroupSearchTerms)groupSearch.getSearchTerms();

		List<Group> results = new ArrayList<>();

		int additionalSites = 0;
		int total = 0;

		boolean includeCompany = ParamUtil.getBoolean(
			_httpServletRequest, "includeCompany");
		boolean includeFormsSite = ParamUtil.getBoolean(
			_httpServletRequest, "includeFormsSite");
		boolean includeUserPersonalSite = ParamUtil.getBoolean(
			_httpServletRequest, "includeUserPersonalSite");

		long[] classNameIds = _CLASS_NAME_IDS;

		if (includeCompany) {
			classNameIds = ArrayUtil.append(
				classNameIds, PortalUtil.getClassNameId(Company.class));
		}

		if (includeFormsSite) {
			if (groupSearch.getStart() == 0) {
				Group formsSite = GroupLocalServiceUtil.getGroup(
					company.getCompanyId(), GroupConstants.FORMS);

				results.add(formsSite);
			}

			additionalSites++;
		}

		if (includeUserPersonalSite) {
			if (groupSearch.getStart() == 0) {
				Group userPersonalSite = GroupLocalServiceUtil.getGroup(
					company.getCompanyId(), GroupConstants.USER_PERSONAL_SITE);

				results.add(userPersonalSite);
			}

			additionalSites++;
		}

		String type = getType();

		if (type.equals("layoutScopes")) {
			total = GroupLocalServiceUtil.getGroupsCount(
				themeDisplay.getCompanyId(), Layout.class.getName(),
				_getGroupId());
		}
		else if (!type.equals("parent-sites")) {
			total = GroupLocalServiceUtil.searchCount(
				themeDisplay.getCompanyId(), classNameIds,
				groupSearchTerms.getKeywords(), _getGroupParams());
		}

		total += additionalSites;

		groupSearch.setTotal(total);

		int start = groupSearch.getStart();

		if (groupSearch.getStart() > additionalSites) {
			start = groupSearch.getStart() - additionalSites;
		}

		List<Group> groups = null;

		if (type.equals("layoutScopes")) {
			groups = GroupLocalServiceUtil.getGroups(
				company.getCompanyId(), Layout.class.getName(), _getGroupId(),
				start, groupSearch.getResultEnd() - additionalSites);

			groups = _filterLayoutGroups(groups, _isPrivateLayout());
		}
		else if (type.equals("parent-sites")) {
			Group group = GroupLocalServiceUtil.getGroup(_getGroupId());

			groups = group.getAncestors();

			String filter = _getFilter();

			if (Validator.isNotNull(filter)) {
				groups = _filterGroups(groups, filter);
			}

			total = groups.size();

			total += additionalSites;

			groupSearch.setTotal(total);
		}
		else {
			groups = GroupLocalServiceUtil.search(
				company.getCompanyId(), classNameIds,
				groupSearchTerms.getKeywords(), _getGroupParams(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				groupSearch.getOrderByComparator());

			groups = _filterGroups(groups, themeDisplay.getPermissionChecker());

			total = groups.size();

			total += additionalSites;

			groupSearch.setTotal(total);

			groups = groups.subList(
				start, groupSearch.getResultEnd() - additionalSites);
		}

		results.addAll(groups);

		groupSearch.setResults(results);

		_groupSearch = groupSearch;

		return _groupSearch;
	}

	public List<NavigationItem> getNavigationItems() {
		String[] types = _getTypes();

		if (types.length == 1) {
			return new NavigationItemList() {
				{
					add(
						navigationItem -> {
							navigationItem.setActive(true);
							navigationItem.setHref(
								_liferayPortletResponse.createRenderURL());
							navigationItem.setLabel(
								LanguageUtil.get(_httpServletRequest, "sites"));
						});
				}
			};
		}
		else if (types.length > 1) {
			return new NavigationItemList() {
				{
					for (String curType : types) {
						add(
							navigationItem -> {
								navigationItem.setActive(
									curType.equals(getType()));
								navigationItem.setHref(
									getPortletURL(), "type", curType);
								navigationItem.setLabel(curType);
							});
					}
				}
			};
		}

		return Collections.emptyList();
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		User selUser = null;

		try {
			selUser = PortalUtil.getSelectedUser(_httpServletRequest);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		if (selUser != null) {
			portletURL.setParameter(
				"p_u_i_d", String.valueOf(selUser.getUserId()));
		}

		boolean filterManageableGroups = ParamUtil.getBoolean(
			_httpServletRequest, "filterManageableGroups", true);
		boolean includeCompany = ParamUtil.getBoolean(
			_httpServletRequest, "includeCompany");
		boolean includeCurrentGroup = ParamUtil.getBoolean(
			_httpServletRequest, "includeCurrentGroup", true);
		boolean includeUserPersonalSite = ParamUtil.getBoolean(
			_httpServletRequest, "includeUserPersonalSite");
		String eventName = ParamUtil.getString(
			_httpServletRequest, "eventName",
			_liferayPortletResponse.getNamespace() + "selectSite");
		String target = ParamUtil.getString(_httpServletRequest, "target");

		portletURL.setParameter("groupId", String.valueOf(_getGroupId()));
		portletURL.setParameter("type", getType());
		portletURL.setParameter("types", _getTypes());
		portletURL.setParameter("displayStyle", getDisplayStyle());
		portletURL.setParameter("filter", _getFilter());
		portletURL.setParameter(
			"filterManageableGroups", String.valueOf(filterManageableGroups));
		portletURL.setParameter(
			"includeCompany", String.valueOf(includeCompany));
		portletURL.setParameter(
			"includeCurrentGroup", String.valueOf(includeCurrentGroup));
		portletURL.setParameter(
			"includeUserPersonalSite", String.valueOf(includeUserPersonalSite));
		portletURL.setParameter(
			"manualMembership", String.valueOf(_isManualMembership()));
		portletURL.setParameter("eventName", eventName);
		portletURL.setParameter("orderByCol", _getOrderByCol());
		portletURL.setParameter("orderByType", getOrderByType());
		portletURL.setParameter("target", target);

		return portletURL;
	}

	public User getSelUser() {
		return _selUser;
	}

	public String getTarget() {
		if (_target != null) {
			return _target;
		}

		_target = ParamUtil.getString(_httpServletRequest, "target");

		return _target;
	}

	public String getType() {
		if (_type != null) {
			return _type;
		}

		_type = ParamUtil.getString(_httpServletRequest, "type");

		if (Validator.isNull(_type)) {
			String[] types = _getTypes();

			_type = types[0];
		}

		return _type;
	}

	public boolean isShowLink(Group group) {
		try {
			long userId = 0;

			if (_selUser != null) {
				userId = _selUser.getUserId();
			}

			if (Validator.isNull(_getPuid()) ||
				SiteMembershipPolicyUtil.isMembershipAllowed(
					userId, group.getGroupId())) {

				return true;
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	private List<Group> _filterGroups(
			List<Group> groups, PermissionChecker permissionChecker)
		throws PortalException {

		boolean filterManageableGroups = ParamUtil.getBoolean(
			_httpServletRequest, "filterManageableGroups", true);

		List<Group> filteredGroups = new ArrayList<>();

		for (Group group : groups) {
			if (permissionChecker.isGroupAdmin(group.getGroupId()) ||
				(!filterManageableGroups &&
				 GroupPermissionUtil.contains(
					 permissionChecker, group.getGroupId(),
					 ActionKeys.ASSIGN_MEMBERS))) {

				filteredGroups.add(group);
			}
		}

		return filteredGroups;
	}

	private List<Group> _filterGroups(List<Group> groups, String filter) {
		List<Group> filteredGroups = new ArrayList<>();

		for (Group group : groups) {
			if (filter.equals("contentSharingWithChildrenEnabled") &&
				SitesUtil.isContentSharingWithChildrenEnabled(group)) {

				filteredGroups.add(group);
			}
		}

		return filteredGroups;
	}

	private List<Group> _filterLayoutGroups(
			List<Group> groups, Boolean privateLayout)
		throws Exception {

		if (privateLayout == null) {
			return groups;
		}

		List<Group> filteredGroups = new ArrayList<>();

		for (Group group : groups) {
			if (!group.isLayout()) {
				continue;
			}

			Layout layout = LayoutLocalServiceUtil.getLayout(
				group.getClassPK());

			if (layout.isPrivateLayout() == privateLayout) {
				filteredGroups.add(group);
			}
		}

		return filteredGroups;
	}

	private String _getFilter() {
		if (_filter != null) {
			return _filter;
		}

		_filter = ParamUtil.getString(_httpServletRequest, "filter");

		return _filter;
	}

	private long _getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		_groupId = ParamUtil.getLong(_httpServletRequest, "groupId");

		return _groupId;
	}

	private LinkedHashMap<String, Object> _getGroupParams()
		throws PortalException {

		if (_groupParams != null) {
			return _groupParams;
		}

		long groupId = ParamUtil.getLong(_httpServletRequest, "groupId");
		boolean includeCurrentGroup = ParamUtil.getBoolean(
			_httpServletRequest, "includeCurrentGroup", true);

		String type = getType();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		boolean filterManageableGroups = ParamUtil.getBoolean(
			_httpServletRequest, "filterManageableGroups", true);

		if (permissionChecker.isCompanyAdmin()) {
			filterManageableGroups = false;
		}

		_groupParams = LinkedHashMapBuilder.<String, Object>put(
			"active", Boolean.TRUE
		).build();

		if (_isManualMembership()) {
			_groupParams.put("manualMembership", Boolean.TRUE);
		}

		if (type.equals("child-sites")) {
			Group parentGroup = GroupLocalServiceUtil.getGroup(groupId);

			_groupParams.put("groupsTree", ListUtil.fromArray(parentGroup));
		}
		else if (filterManageableGroups) {
			User user = themeDisplay.getUser();

			_groupParams.put("usersGroups", user.getUserId());
		}

		_groupParams.put("site", Boolean.TRUE);

		if (!includeCurrentGroup && (groupId > 0)) {
			List<Long> excludedGroupIds = new ArrayList<>();

			Group group = GroupLocalServiceUtil.getGroup(groupId);

			if (group.isStagingGroup()) {
				excludedGroupIds.add(group.getLiveGroupId());
			}
			else {
				excludedGroupIds.add(groupId);
			}

			_groupParams.put("excludedGroupIds", excludedGroupIds);
		}

		return _groupParams;
	}

	private String _getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", "name");

		return _orderByCol;
	}

	private String _getPuid() {
		if (_puid != null) {
			return _puid;
		}

		_puid = ParamUtil.getString(_httpServletRequest, "p_u_i_d");

		return _puid;
	}

	private String[] _getTypes() {
		if (_types != null) {
			return _types;
		}

		_types = ParamUtil.getParameterValues(_httpServletRequest, "types");

		if (_types.length == 0) {
			_types = new String[] {"sites-that-i-administer"};
		}

		return _types;
	}

	private Boolean _isManualMembership() {
		if (_manualMembership != null) {
			return _manualMembership;
		}

		_manualMembership = ParamUtil.getBoolean(
			_httpServletRequest, "manualMembership");

		return _manualMembership;
	}

	private Boolean _isPrivateLayout() {
		if (_privateLayout != null) {
			return _privateLayout;
		}

		_privateLayout = ParamUtil.getBoolean(
			_httpServletRequest, "privateLayout");

		return _privateLayout;
	}

	private static final long[] _CLASS_NAME_IDS = {
		PortalUtil.getClassNameId(Group.class),
		PortalUtil.getClassNameId(Organization.class)
	};

	private static final Log _log = LogFactoryUtil.getLog(
		SiteBrowserDisplayContext.class);

	private String _displayStyle;
	private String _eventName;
	private String _filter;
	private Long _groupId;
	private LinkedHashMap<String, Object> _groupParams;
	private GroupSearch _groupSearch;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private Boolean _manualMembership;
	private String _orderByCol;
	private String _orderByType;
	private Boolean _privateLayout;
	private String _puid;
	private final User _selUser;
	private String _target;
	private String _type;
	private String[] _types;

}