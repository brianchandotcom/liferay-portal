/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.announcements.web.internal.display.context;

import com.liferay.announcements.kernel.util.AnnouncementsUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
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
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;
	}

	@Override
	public String getCurrentDistributionScopeLabel() throws Exception {
		String distributionScope = ParamUtil.getString(
			_httpServletRequest, "distributionScope");

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

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (PortalPermissionUtil.contains(
				themeDisplay.getPermissionChecker(),
				ActionKeys.ADD_GENERAL_ANNOUNCEMENTS)) {

			distributionScopes.put("general", "0,0");
		}

		List<Group> groups = AnnouncementsUtil.getGroups(themeDisplay);

		for (Group group : groups) {
			distributionScopes.put(
				StringBundler.concat(
					group.getDescriptiveName(themeDisplay.getLocale()), " (",
					LanguageUtil.get(_httpServletRequest, "site"), ")"),
				PortalUtil.getClassNameId(Group.class) + StringPool.COMMA +
					group.getGroupId());
		}

		List<Organization> organizations = AnnouncementsUtil.getOrganizations(
			themeDisplay);

		for (Organization organization : organizations) {
			String name = StringBundler.concat(
				organization.getName(), " (",
				LanguageUtil.get(_httpServletRequest, "organization"), ")");

			distributionScopes.put(
				name,
				PortalUtil.getClassNameId(Organization.class) +
					StringPool.COMMA + organization.getOrganizationId());
		}

		List<Role> roles = AnnouncementsUtil.getRoles(themeDisplay);

		for (Role role : roles) {
			distributionScopes.put(
				StringBundler.concat(
					role.getDescriptiveName(), " (",
					LanguageUtil.get(_httpServletRequest, "role"), ")"),
				PortalUtil.getClassNameId(Role.class) + StringPool.COMMA +
					role.getRoleId());
		}

		List<UserGroup> userGroups = AnnouncementsUtil.getUserGroups(
			themeDisplay);

		for (UserGroup userGroup : userGroups) {
			distributionScopes.put(
				StringBundler.concat(
					userGroup.getName(), " (",
					LanguageUtil.get(_httpServletRequest, "user-group"), ")"),
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

	private final HttpServletRequest _httpServletRequest;

}