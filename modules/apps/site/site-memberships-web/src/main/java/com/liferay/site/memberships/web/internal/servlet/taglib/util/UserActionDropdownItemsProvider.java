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

package com.liferay.site.memberships.web.internal.servlet.taglib.util;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.membershippolicy.SiteMembershipPolicyUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

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
public class UserActionDropdownItemsProvider {

	public UserActionDropdownItemsProvider(
		User user, RenderRequest renderRequest, RenderResponse renderResponse) {

		_user = user;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() throws Exception {
		return new DropdownItemList() {
			{
				if (GroupPermissionUtil.contains(
						_themeDisplay.getPermissionChecker(),
						_themeDisplay.getSiteGroupIdOrLiveGroupId(),
						ActionKeys.ASSIGN_USER_ROLES)) {

					add(_getAssignRolesActionUnsafeConsumer());
				}

				if (GroupPermissionUtil.contains(
						_themeDisplay.getPermissionChecker(),
						_themeDisplay.getSiteGroupIdOrLiveGroupId(),
						ActionKeys.ASSIGN_MEMBERS) &&
					!SiteMembershipPolicyUtil.isMembershipProtected(
						_themeDisplay.getPermissionChecker(), _user.getUserId(),
						_themeDisplay.getSiteGroupIdOrLiveGroupId()) &&
					!SiteMembershipPolicyUtil.isMembershipRequired(
						_user.getUserId(),
						_themeDisplay.getSiteGroupIdOrLiveGroupId())) {

					add(_getDeleteGroupUsersActionUnsafeConsumer());
				}
			}
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getAssignRolesActionUnsafeConsumer()
		throws Exception {

		PortletURL assignRolesURL = _renderResponse.createRenderURL();

		assignRolesURL.setParameter(
			"p_u_i_d", String.valueOf(_user.getUserId()));
		assignRolesURL.setParameter("mvcPath", "/users_roles.jsp");
		assignRolesURL.setParameter(
			"groupId",
			String.valueOf(_themeDisplay.getSiteGroupIdOrLiveGroupId()));

		Group group = _themeDisplay.getScopeGroup();

		if (!group.isSite() &&
			Objects.equals(group.getType(), GroupConstants.TYPE_DEPOT)) {

			assignRolesURL.setParameter(
				"roleType", String.valueOf(RoleConstants.TYPE_DEPOT));
		}

		assignRolesURL.setWindowState(LiferayWindowState.POP_UP);

		PortletURL editUserGroupRoleURL = _renderResponse.createActionURL();

		editUserGroupRoleURL.setParameter(
			ActionRequest.ACTION_NAME, "editUserGroupRole");
		editUserGroupRoleURL.setParameter(
			"p_u_i_d", String.valueOf(_user.getUserId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "assignRoles");
			dropdownItem.putData("assignRolesURL", assignRolesURL.toString());
			dropdownItem.putData(
				"editUserGroupRoleURL", editUserGroupRoleURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "assign-roles"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDeleteGroupUsersActionUnsafeConsumer() {

		PortletURL deleteGroupUsersURL = _renderResponse.createActionURL();

		deleteGroupUsersURL.setParameter(
			ActionRequest.ACTION_NAME, "deleteGroupUsers");
		deleteGroupUsersURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		deleteGroupUsersURL.setParameter(
			"groupId",
			String.valueOf(_themeDisplay.getSiteGroupIdOrLiveGroupId()));
		deleteGroupUsersURL.setParameter(
			"removeUserId", String.valueOf(_user.getUserId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "deleteGroupUsers");
			dropdownItem.putData(
				"deleteGroupUsersURL", deleteGroupUsersURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "remove-membership"));
		};
	}

	private final HttpServletRequest _httpServletRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;
	private final User _user;

}