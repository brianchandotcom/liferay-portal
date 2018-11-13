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

package com.liferay.announcements.web.internal.display.context;

import com.liferay.announcements.constants.AnnouncementsPortletKeys;
import com.liferay.announcements.kernel.util.AnnouncementsUtil;
import com.liferay.announcements.web.internal.display.context.util.AnnouncementsRequestHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.service.permission.UserGroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.text.DateFormat;
import java.text.Format;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;

/**
 * @author Adolfo Pérez
 * @author Roberto Díaz
 */
public class DefaultAnnouncementsDisplayContext
	implements AnnouncementsDisplayContext {

	public DefaultAnnouncementsDisplayContext(
		AnnouncementsRequestHelper announcementsRequestHelper) {

		_announcementsRequestHelper = announcementsRequestHelper;
	}

	@Override
	public LinkedHashMap<Long, long[]> getAnnouncementScopes()
		throws PortalException {

		LinkedHashMap<Long, long[]> scopes = new LinkedHashMap<>();

		if (isCustomizeAnnouncementsDisplayed()) {
			long[] selectedScopeGroupIdsArray = GetterUtil.getLongValues(
				StringUtil.split(getSelectedScopeGroupIds()));
			long[] selectedScopeOrganizationIdsArray = GetterUtil.getLongValues(
				StringUtil.split(getSelectedScopeOrganizationIds()));
			long[] selectedScopeRoleIdsArray = GetterUtil.getLongValues(
				StringUtil.split(getSelectedScopeRoleIds()));
			long[] selectedScopeUserGroupIdsArray = GetterUtil.getLongValues(
				StringUtil.split(getSelectedScopeUserGroupIds()));

			if (selectedScopeGroupIdsArray.length != 0) {
				scopes.put(
					PortalUtil.getClassNameId(Group.class.getName()),
					selectedScopeGroupIdsArray);
			}

			if (selectedScopeOrganizationIdsArray.length != 0) {
				scopes.put(
					PortalUtil.getClassNameId(Organization.class.getName()),
					selectedScopeOrganizationIdsArray);
			}

			if (selectedScopeRoleIdsArray.length != 0) {
				scopes.put(
					PortalUtil.getClassNameId(Role.class.getName()),
					selectedScopeRoleIdsArray);
			}

			if (selectedScopeUserGroupIdsArray.length != 0) {
				scopes.put(
					PortalUtil.getClassNameId(UserGroup.class.getName()),
					selectedScopeUserGroupIdsArray);
			}
		}
		else {
			scopes = AnnouncementsUtil.getAnnouncementScopes(
				_announcementsRequestHelper.getUser());
		}

		scopes.put(0L, new long[] {0});

		return scopes;
	}

	@Override
	public Format getDateFormatDate() {
		ThemeDisplay themeDisplay =
			_announcementsRequestHelper.getThemeDisplay();

		return FastDateFormatFactoryUtil.getDate(
			DateFormat.FULL, themeDisplay.getLocale(),
			themeDisplay.getTimeZone());
	}

	@Override
	public List<Group> getGroups() throws PortalException {
		if (!isCustomizeAnnouncementsDisplayed() ||
			StringUtil.equals(
				_announcementsRequestHelper.getPortletId(),
				AnnouncementsPortletKeys.ANNOUNCEMENTS_ADMIN)) {

			return AnnouncementsUtil.getGroups(
				_announcementsRequestHelper.getThemeDisplay());
		}

		List<Group> selectedGroups = new ArrayList<>();

		String[] selectedScopeGroupIds = StringUtil.split(
			getSelectedScopeGroupIds());

		for (String selectedScopeGroupId : selectedScopeGroupIds) {
			long groupId = Long.valueOf(selectedScopeGroupId);

			if (GroupPermissionUtil.contains(
					_announcementsRequestHelper.getPermissionChecker(), groupId,
					ActionKeys.MANAGE_ANNOUNCEMENTS)) {

				selectedGroups.add(GroupLocalServiceUtil.getGroup(groupId));
			}
		}

		return selectedGroups;
	}

	@Override
	public List<Organization> getOrganizations() throws PortalException {
		if (!isCustomizeAnnouncementsDisplayed() ||
			StringUtil.equals(
				_announcementsRequestHelper.getPortletId(),
				AnnouncementsPortletKeys.ANNOUNCEMENTS_ADMIN)) {

			return AnnouncementsUtil.getOrganizations(
				_announcementsRequestHelper.getThemeDisplay());
		}

		List<Organization> selectedOrganizations = new ArrayList<>();

		String[] selectedScopeOrganizationIds = StringUtil.split(
			getSelectedScopeOrganizationIds());

		for (String selectedScopeOrganizationId :
				selectedScopeOrganizationIds) {

			long organizationId = Long.valueOf(selectedScopeOrganizationId);

			if (OrganizationPermissionUtil.contains(
					_announcementsRequestHelper.getPermissionChecker(),
					organizationId, ActionKeys.MANAGE_ANNOUNCEMENTS)) {

				selectedOrganizations.add(
					OrganizationLocalServiceUtil.getOrganization(
						organizationId));
			}
		}

		return selectedOrganizations;
	}

	@Override
	public int getPageDelta() {
		PortletPreferences portletPreferences =
			_announcementsRequestHelper.getPortletPreferences();

		return GetterUtil.getInteger(
			portletPreferences.getValue(
				"pageDelta", String.valueOf(SearchContainer.DEFAULT_DELTA)));
	}

	@Override
	public List<Role> getRoles() throws PortalException {
		if (!isCustomizeAnnouncementsDisplayed() ||
			StringUtil.equals(
				_announcementsRequestHelper.getPortletId(),
				AnnouncementsPortletKeys.ANNOUNCEMENTS_ADMIN)) {

			return AnnouncementsUtil.getRoles(
				_announcementsRequestHelper.getThemeDisplay());
		}

		List<Role> selectedRoles = new ArrayList<>();

		String[] selectedScopeRoleIds = StringUtil.split(
			getSelectedScopeRoleIds());

		for (String selectedScopeRoleId : selectedScopeRoleIds) {
			Role role = RoleLocalServiceUtil.getRole(
				Long.valueOf(selectedScopeRoleId));

			if (AnnouncementsUtil.hasManageAnnouncementsPermission(
					role, _announcementsRequestHelper.getPermissionChecker())) {

				selectedRoles.add(role);
			}
		}

		return selectedRoles;
	}

	@Override
	public String getTabs1Names() {
		return "unread,read";
	}

	@Override
	public String getTabs1PortletURL() {
		LiferayPortletResponse liferayPortletResponse =
			_announcementsRequestHelper.getLiferayPortletResponse();

		PortletURL tabs1URL = liferayPortletResponse.createRenderURL();

		tabs1URL.setParameter("mvcRenderCommandName", "/announcements/view");
		tabs1URL.setParameter("tabs1", _announcementsRequestHelper.getTabs1());

		return tabs1URL.toString();
	}

	@Override
	public List<UserGroup> getUserGroups() throws PortalException {
		if (!isCustomizeAnnouncementsDisplayed() ||
			StringUtil.equals(
				_announcementsRequestHelper.getPortletId(),
				AnnouncementsPortletKeys.ANNOUNCEMENTS_ADMIN)) {

			return AnnouncementsUtil.getUserGroups(
				_announcementsRequestHelper.getThemeDisplay());
		}

		List<UserGroup> selectedUserGroups = new ArrayList<>();

		String[] selectedScopeUserGroupIds = StringUtil.split(
			getSelectedScopeUserGroupIds());

		for (String selectedScopeUserGroupId : selectedScopeUserGroupIds) {
			long userGroupId = Long.valueOf(selectedScopeUserGroupId);

			if (UserGroupPermissionUtil.contains(
					_announcementsRequestHelper.getPermissionChecker(),
					userGroupId, ActionKeys.MANAGE_ANNOUNCEMENTS)) {

				selectedUserGroups.add(
					UserGroupLocalServiceUtil.getUserGroup(userGroupId));
			}
		}

		return selectedUserGroups;
	}

	@Override
	public UUID getUuid() {
		return _UUID;
	}

	@Override
	public boolean isCustomizeAnnouncementsDisplayed() {
		String portletName = _announcementsRequestHelper.getPortletName();

		if (portletName.equals(AnnouncementsPortletKeys.ALERTS)) {
			return false;
		}

		Group scopeGroup = _announcementsRequestHelper.getScopeGroup();

		return PrefsParamUtil.getBoolean(
			_announcementsRequestHelper.getPortletPreferences(),
			_announcementsRequestHelper.getRequest(),
			"customizeAnnouncementsDisplayed", !scopeGroup.isUser());
	}

	@Override
	public boolean isScopeGroupSelected(Group scopeGroup) {
		String selectedScopeGroupIds = getSelectedScopeGroupIds();

		return selectedScopeGroupIds.contains(
			String.valueOf(scopeGroup.getGroupId()));
	}

	@Override
	public boolean isScopeOrganizationSelected(Organization organization) {
		String selectedScopeOrganizationIds = getSelectedScopeOrganizationIds();

		return selectedScopeOrganizationIds.contains(
			String.valueOf(organization.getOrganizationId()));
	}

	@Override
	public boolean isScopeRoleSelected(Role role) {
		String selectedScopeRoleIds = getSelectedScopeRoleIds();

		return selectedScopeRoleIds.contains(String.valueOf(role.getRoleId()));
	}

	@Override
	public boolean isScopeUserGroupSelected(UserGroup userGroup) {
		String selectedScopeUserGroupIds = getSelectedScopeUserGroupIds();

		return selectedScopeUserGroupIds.contains(
			String.valueOf(userGroup.getUserGroupId()));
	}

	@Override
	public boolean isShowReadEntries() {
		String tabs1 = _announcementsRequestHelper.getTabs1();

		return tabs1.equals("read");
	}

	@Override
	public boolean isShowScopeName() {
		String mvcRenderCommandName = ParamUtil.getString(
			_announcementsRequestHelper.getRequest(), "mvcRenderCommandName");

		return mvcRenderCommandName.equals("/announcements/edit_entry");
	}

	@Override
	public boolean isTabs1Visible() {
		String portletName = _announcementsRequestHelper.getPortletName();

		ThemeDisplay themeDisplay =
			_announcementsRequestHelper.getThemeDisplay();

		try {
			if (!portletName.equals(AnnouncementsPortletKeys.ALERTS) ||
				(portletName.equals(AnnouncementsPortletKeys.ALERTS) &&
				 PortletPermissionUtil.hasControlPanelAccessPermission(
					 _announcementsRequestHelper.getPermissionChecker(),
					 themeDisplay.getScopeGroupId(),
					 AnnouncementsPortletKeys.ANNOUNCEMENTS_ADMIN))) {

				return true;
			}
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		return false;
	}

	protected String getSelectedScopeGroupIds() {
		Layout layout = _announcementsRequestHelper.getLayout();

		return PrefsParamUtil.getString(
			_announcementsRequestHelper.getPortletPreferences(),
			_announcementsRequestHelper.getRequest(), "selectedScopeGroupIds",
			String.valueOf(layout.getGroupId()));
	}

	protected String getSelectedScopeOrganizationIds() {
		return PrefsParamUtil.getString(
			_announcementsRequestHelper.getPortletPreferences(),
			_announcementsRequestHelper.getRequest(),
			"selectedScopeOrganizationIds", StringPool.BLANK);
	}

	protected String getSelectedScopeRoleIds() {
		return PrefsParamUtil.getString(
			_announcementsRequestHelper.getPortletPreferences(),
			_announcementsRequestHelper.getRequest(), "selectedScopeRoleIds",
			StringPool.BLANK);
	}

	protected String getSelectedScopeUserGroupIds() {
		return PrefsParamUtil.getString(
			_announcementsRequestHelper.getPortletPreferences(),
			_announcementsRequestHelper.getRequest(),
			"selectedScopeUserGroupIds", StringPool.BLANK);
	}

	private static final UUID _UUID = UUID.fromString(
		"CD705D0E-7DB4-430C-9492-F1FA25ACE02E");

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultAnnouncementsDisplayContext.class);

	private final AnnouncementsRequestHelper _announcementsRequestHelper;

}