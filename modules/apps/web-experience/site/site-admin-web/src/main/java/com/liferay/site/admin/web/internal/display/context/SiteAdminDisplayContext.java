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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.MembershipRequestConstants;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.membershippolicy.SiteMembershipPolicyUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.service.MembershipRequestLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.persistence.constants.UserGroupFinderConstants;
import com.liferay.portlet.usersadmin.search.GroupSearch;
import com.liferay.site.admin.web.internal.constants.SiteAdminPortletKeys;
import com.liferay.site.constants.SiteWebKeys;
import com.liferay.site.model.SiteFriendlyURL;
import com.liferay.site.service.SiteFriendlyURLLocalServiceUtil;
import com.liferay.site.util.GroupSearchProvider;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 */
public class SiteAdminDisplayContext {

	public SiteAdminDisplayContext(
			HttpServletRequest request,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		_request = request;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_groupSearchProvider = (GroupSearchProvider)request.getAttribute(
			SiteWebKeys.GROUP_SEARCH_PROVIDER);
	}

	public int getChildSitesCount(Group group) {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return GroupLocalServiceUtil.getGroupsCount(
			themeDisplay.getCompanyId(), group.getGroupId(), true);
	}

	public PortletURL getCurrentURL() {
		PortletURL currentURL = PortletURLUtil.getCurrent(
			_liferayPortletRequest, _liferayPortletResponse);

		currentURL.setParameter("displayStyle", getDisplayStyle());

		return currentURL;
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_request);

		_displayStyle = portalPreferences.getValue(
			SiteAdminPortletKeys.SITE_ADMIN, "display-style", "list");

		return _displayStyle;
	}

	public String getFriendlyURLsXML(Group group) {
		List<SiteFriendlyURL> siteFriendlyURLs =
			SiteFriendlyURLLocalServiceUtil.getSiteFriendlyURLs(
				group.getCompanyId(), group.getGroupId());

		Map<Locale, String> siteFriendlyURLMap = new HashMap<>();

		for (SiteFriendlyURL siteFriendlyURL : siteFriendlyURLs) {
			siteFriendlyURLMap.put(
				LocaleUtil.fromLanguageId(siteFriendlyURL.getLanguageId()),
				siteFriendlyURL.getFriendlyURL());
		}

		return LocalizationUtil.updateLocalization(
			siteFriendlyURLMap, StringPool.BLANK, "FriendlyURL",
			LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()));
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
				_request, "groupId", GroupConstants.DEFAULT_PARENT_GROUP_ID);
		}

		return _groupId;
	}

	public String getKeywords() {
		if (_keywords == null) {
			_keywords = ParamUtil.getString(_request, "keywords");
		}

		return _keywords;
	}

	public int getOrganizationsCount() throws PortalException {
		return getOrganizationsCount(getGroup());
	}

	public int getOrganizationsCount(Group group) {
		LinkedHashMap<String, Object> organizationParams =
			new LinkedHashMap<>();

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		organizationParams.put("groupOrganization", group.getGroupId());
		organizationParams.put("organizationsGroups", group.getGroupId());

		return OrganizationLocalServiceUtil.searchCount(
			company.getCompanyId(),
			OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, null, null, null,
			null, organizationParams);
	}

	public int getPendingRequestsCount() throws PortalException {
		return getPendingRequestsCount(getGroup());
	}

	public int getPendingRequestsCount(Group group) throws PortalException {
		int pendingRequests = 0;

		if (group.getType() == GroupConstants.TYPE_SITE_RESTRICTED) {
			pendingRequests = MembershipRequestLocalServiceUtil.searchCount(
				group.getGroupId(), MembershipRequestConstants.STATUS_PENDING);
		}

		return pendingRequests;
	}

	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setParameter("groupId", String.valueOf(getGroupId()));
		portletURL.setParameter("displayStyle", getDisplayStyle());

		return portletURL;
	}

	public GroupSearch getSearchContainer() throws PortalException {
		return _groupSearchProvider.getGroupSearch(
			_liferayPortletRequest, getPortletURL());
	}

	public PortletURL getSearchURL() throws PortalException {
		PortletURL searchURL = _liferayPortletResponse.createRenderURL();

		searchURL.setParameter("groupId", String.valueOf(getGroupId()));
		searchURL.setParameter("displayStyle", getDisplayStyle());

		return searchURL;
	}

	public String getSiteFriendlyURL(Group group) throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String languageId = LocaleUtil.toLanguageId(themeDisplay.getLocale());

		SiteFriendlyURL siteFriendlyURL =
			SiteFriendlyURLLocalServiceUtil.fetchSiteFriendlyURL(
				themeDisplay.getCompanyId(), getGroupId(), languageId);

		if (siteFriendlyURL != null) {
			return siteFriendlyURL.getFriendlyURL();
		}

		return group.getFriendlyURL();
	}

	public int getUserGroupsCount(Group group) {
		LinkedHashMap<String, Object> userGroupParams = new LinkedHashMap<>();

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
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

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
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

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
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

	public boolean hasDeleteGroupPermission(Group group)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!group.isCompany() &&
			GroupPermissionUtil.contains(
				permissionChecker, group, ActionKeys.DELETE) &&
			!PortalUtil.isSystemGroup(group.getGroupKey())) {

			return true;
		}

		return false;
	}

	public boolean hasEditAssignmentsPermission(
			Group group, boolean organizationUser, boolean userGroupUser)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		User user = themeDisplay.getUser();

		if (!group.isCompany() &&
			!(organizationUser || userGroupUser) &&
			((group.getType() == GroupConstants.TYPE_SITE_OPEN) ||
				(group.getType() == GroupConstants.TYPE_SITE_RESTRICTED)) &&
			GroupLocalServiceUtil.hasUserGroup(
				user.getUserId(), group.getGroupId()) &&
			!SiteMembershipPolicyUtil.isMembershipRequired(
				user.getUserId(), group.getGroupId())) {

			return true;
		}

		return false;
	}

	private String _displayStyle;
	private Group _group;
	private long _groupId;
	private final GroupSearchProvider _groupSearchProvider;
	private String _keywords;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final HttpServletRequest _request;

}