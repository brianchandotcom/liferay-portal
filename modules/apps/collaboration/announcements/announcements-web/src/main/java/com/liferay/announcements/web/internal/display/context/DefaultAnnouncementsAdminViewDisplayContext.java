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

import com.liferay.announcements.kernel.util.AnnouncementsUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class DefaultAnnouncementsAdminViewDisplayContext
	implements AnnouncementsAdminViewDisplayContext {

	public DefaultAnnouncementsAdminViewDisplayContext(
		HttpServletRequest request) {

		_request = request;
	}

	@Override
	public String getCurrentDistributionScopeLabel() throws Exception {
		String distributionScope = ParamUtil.getString(
			_request, "distributionScope");

		if (Validator.isNotNull(distributionScope)) {
			Map<String, String> distributionScopes = getDistributionScopes();

			for (Map.Entry<String, String> entry :
					distributionScopes.entrySet()) {

				String value = entry.getValue();

				if (value.equals(distributionScope)) {
					return entry.getKey();
				}
			}
		}

		return "general";
	}

	@Override
	public Map<String, String> getDistributionScopes() throws Exception {
		Map<String, String> distributionScopes = new LinkedHashMap<>();

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (PortalPermissionUtil.contains(
				permissionChecker, ActionKeys.ADD_GENERAL_ANNOUNCEMENTS)) {

			distributionScopes.put("general", "0,0");
		}

		List<Group> groups = AnnouncementsUtil.getGroups(themeDisplay);

		for (Group group : groups) {
			distributionScopes.put(
				group.getDescriptiveName(themeDisplay.getLocale()) + " (" +
					LanguageUtil.get(_request, "site") + ")",
				PortalUtil.getClassNameId(Group.class) + StringPool.COMMA +
					group.getGroupId());
		}

		List<Organization> organizations = AnnouncementsUtil.getOrganizations(
			themeDisplay);

		for (Organization organization : organizations) {
			distributionScopes.put(
				organization.getName() + " (" +
					LanguageUtil.get(_request, "organization") + ")",
				PortalUtil.getClassNameId(Organization.class) +
					StringPool.COMMA + organization.getOrganizationId());
		}

		List<Role> roles = AnnouncementsUtil.getRoles(themeDisplay);

		for (Role role : roles) {
			distributionScopes.put(
				role.getDescriptiveName() + " (" +
					LanguageUtil.get(_request, "role") + ")",
				PortalUtil.getClassNameId(Role.class) + StringPool.COMMA +
					role.getRoleId());
		}

		List<UserGroup> userGroups = AnnouncementsUtil.getUserGroups(
			themeDisplay);

		for (UserGroup userGroup : userGroups) {
			distributionScopes.put(
				userGroup.getName() + " (" +
					LanguageUtil.get(_request, "user-group") + ")",
				PortalUtil.getClassNameId(UserGroup.class) + StringPool.COMMA +
					userGroup.getUserGroupId());
		}

		return distributionScopes;
	}

	@Override
	public UUID getUuid() {
		return _UUID;
	}

	private static final UUID _UUID = UUID.fromString(
		"14f20793-d4e2-4173-acd7-7f1c9cda9a36");

	private final HttpServletRequest _request;

}