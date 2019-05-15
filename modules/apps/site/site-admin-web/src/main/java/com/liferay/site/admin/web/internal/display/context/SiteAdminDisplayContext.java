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

package com.liferay.site.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.MembershipRequestConstants;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.service.MembershipRequestLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.persistence.constants.UserGroupFinderConstants;
import com.liferay.portlet.sitesadmin.search.SiteChecker;
import com.liferay.portlet.usersadmin.search.GroupSearch;
import com.liferay.site.admin.web.internal.servlet.taglib.util.SiteActionDropdownItemsProvider;
import com.liferay.site.constants.SiteWebKeys;
import com.liferay.site.util.GroupSearchProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 * @author Marco Leo
 */
public class SiteAdminDisplayContext {

	public SiteAdminDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_groupSearchProvider =
			(GroupSearchProvider)httpServletRequest.getAttribute(
				SiteWebKeys.GROUP_SEARCH_PROVIDER);
	}

	public List<DropdownItem> getActionDropdownItems(Group group)
		throws Exception {

		SiteActionDropdownItemsProvider siteActionDropdownItemsProvider =
			new SiteActionDropdownItemsProvider(
				group, _liferayPortletRequest, _liferayPortletResponse, this);

		return siteActionDropdownItemsProvider.getActionDropdownItems();
	}

	public List<BreadcrumbEntry> getBreadcrumbEntries() throws PortalException {
		List<BreadcrumbEntry> breadcrumbEntries = new ArrayList<>();

		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		breadcrumbEntry.setTitle(
			LanguageUtil.get(_httpServletRequest, "sites"));

		PortletURL mainURL = _liferayPortletResponse.createRenderURL();

		mainURL.setParameter("mvcPath", "/view.jsp");

		breadcrumbEntry.setURL(mainURL.toString());

		breadcrumbEntries.add(breadcrumbEntry);

		Group group = getGroup();

		if (group == null) {
			return breadcrumbEntries;
		}

		List<Group> ancestorGroups = group.getAncestors();

		Collections.reverse(ancestorGroups);

		for (Group ancestorGroup : ancestorGroups) {
			breadcrumbEntry = new BreadcrumbEntry();

			breadcrumbEntry.setTitle(ancestorGroup.getDescriptiveName());

			mainURL.setParameter(
				"groupId", String.valueOf(ancestorGroup.getGroupId()));

			breadcrumbEntry.setURL(mainURL.toString());

			breadcrumbEntries.add(breadcrumbEntry);
		}

		Group unescapedGroup = group.toUnescapedModel();

		breadcrumbEntry = new BreadcrumbEntry();

		breadcrumbEntry.setTitle(unescapedGroup.getDescriptiveName());

		mainURL.setParameter(
			"groupId", String.valueOf(unescapedGroup.getGroupId()));

		breadcrumbEntry.setURL(mainURL.toString());

		breadcrumbEntries.add(breadcrumbEntry);

		return breadcrumbEntries;
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle", "list");

		return _displayStyle;
	}

	public Group getGroup() throws PortalException {
		long groupId = getGroupId();

		if (groupId > 0) {
			_group = GroupServiceUtil.getGroup(groupId);
		}

		return _group;
	}

	public long getGroupId() {
		if (_groupId <= 0) {
			_groupId = ParamUtil.getLong(
				_httpServletRequest, "groupId",
				GroupConstants.DEFAULT_PARENT_GROUP_ID);
		}

		return _groupId;
	}

	public int getOrganizationsCount(Group group) {
		LinkedHashMap<String, Object> organizationParams =
			new LinkedHashMap<>();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		organizationParams.put("groupOrganization", group.getGroupId());
		organizationParams.put("organizationsGroups", group.getGroupId());

		return OrganizationLocalServiceUtil.searchCount(
			company.getCompanyId(),
			OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, null, null, null,
			null, organizationParams);
	}

	public int getPendingRequestsCount(Group group) {
		int pendingRequests = 0;

		if (group.getType() == GroupConstants.TYPE_SITE_RESTRICTED) {
			pendingRequests = MembershipRequestLocalServiceUtil.searchCount(
				group.getGroupId(), MembershipRequestConstants.STATUS_PENDING);
		}

		return pendingRequests;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setParameter("groupId", String.valueOf(getGroupId()));
		portletURL.setParameter("displayStyle", getDisplayStyle());

		return portletURL;
	}

	public GroupSearch getSearchContainer() throws PortalException {
		GroupSearch groupSearch = _groupSearchProvider.getGroupSearch(
			_liferayPortletRequest, getPortletURL());

		groupSearch.setId("sites");

		SiteChecker siteChecker = new SiteChecker(_liferayPortletResponse);

		StringBundler sb = new StringBundler(5);

		sb.append("^(?!.*");
		sb.append(_liferayPortletResponse.getNamespace());
		sb.append("redirect).*(groupId=");
		sb.append(getGroupId());
		sb.append(")");

		siteChecker.setRememberCheckBoxStateURLRegex(sb.toString());

		groupSearch.setRowChecker(siteChecker);

		return groupSearch;
	}

	public int getUserGroupsCount(Group group) {
		LinkedHashMap<String, Object> userGroupParams = new LinkedHashMap<>();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		userGroupParams.put(
			UserGroupFinderConstants.PARAM_KEY_USER_GROUPS_GROUPS,
			group.getGroupId());

		return UserGroupLocalServiceUtil.searchCount(
			company.getCompanyId(), null, userGroupParams);
	}

	public int getUsersCount(Group group) {
		LinkedHashMap<String, Object> userParams = new LinkedHashMap<>();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		userParams.put("inherit", Boolean.TRUE);
		userParams.put("usersGroups", group.getGroupId());

		return UserLocalServiceUtil.searchCount(
			company.getCompanyId(), null, WorkflowConstants.STATUS_APPROVED,
			userParams);
	}

	public boolean hasAddChildSitePermission(Group group)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!group.isCompany() &&
			(PortalPermissionUtil.contains(
				permissionChecker, ActionKeys.ADD_COMMUNITY) ||
			 GroupPermissionUtil.contains(
				 permissionChecker, group, ActionKeys.ADD_COMMUNITY))) {

			return true;
		}

		return false;
	}

	private String _displayStyle;
	private Group _group;
	private long _groupId;
	private final GroupSearchProvider _groupSearchProvider;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;

}