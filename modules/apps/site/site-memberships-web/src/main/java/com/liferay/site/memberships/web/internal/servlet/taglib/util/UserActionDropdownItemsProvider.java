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
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.membershippolicy.SiteMembershipPolicyUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.function.Consumer;

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

		_request = PortalUtil.getHttpServletRequest(renderRequest);

		_themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() throws Exception {
		return new DropdownItemList() {
			{
				if (GroupPermissionUtil.contains(
						_themeDisplay.getPermissionChecker(),
						_themeDisplay.getScopeGroupId(),
						ActionKeys.ASSIGN_USER_ROLES)) {

					add(_getAssignSiteRolesActionConsumer());
				}

				if (GroupPermissionUtil.contains(
						_themeDisplay.getPermissionChecker(),
						_themeDisplay.getScopeGroupId(),
						ActionKeys.ASSIGN_MEMBERS) &&
					!SiteMembershipPolicyUtil.isMembershipProtected(
						_themeDisplay.getPermissionChecker(), _user.getUserId(),
						_themeDisplay.getScopeGroupId()) &&
					!SiteMembershipPolicyUtil.isMembershipRequired(
						_user.getUserId(), _themeDisplay.getScopeGroupId())) {

					add(_getDeleteGroupUsersActionConsumer());
				}
			}
		};
	}

	private Consumer<DropdownItem> _getAssignSiteRolesActionConsumer()
		throws Exception {

		PortletURL assignSiteRolesURL = _renderResponse.createRenderURL();

		assignSiteRolesURL.setParameter("mvcPath", "/users_roles.jsp");
		assignSiteRolesURL.setParameter(
			"p_u_i_d", String.valueOf(_user.getUserId()));
		assignSiteRolesURL.setParameter(
			"groupId", String.valueOf(_themeDisplay.getScopeGroupId()));
		assignSiteRolesURL.setWindowState(LiferayWindowState.POP_UP);

		PortletURL editUserGroupRoleURL = _renderResponse.createActionURL();

		editUserGroupRoleURL.setParameter(
			ActionRequest.ACTION_NAME, "editUserGroupRole");
		editUserGroupRoleURL.setParameter(
			"p_u_i_d", String.valueOf(_user.getUserId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "assignSiteRoles");
			dropdownItem.putData(
				"assignSiteRolesURL", assignSiteRolesURL.toString());
			dropdownItem.putData(
				"editUserGroupRoleURL", editUserGroupRoleURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_request, "assign-site-roles"));
		};
	}

	private Consumer<DropdownItem> _getDeleteGroupUsersActionConsumer() {
		PortletURL deleteGroupUsersURL = _renderResponse.createActionURL();

		deleteGroupUsersURL.setParameter(
			ActionRequest.ACTION_NAME, "deleteGroupUsers");
		deleteGroupUsersURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		deleteGroupUsersURL.setParameter(
			"groupId", String.valueOf(_themeDisplay.getScopeGroupId()));
		deleteGroupUsersURL.setParameter(
			"removeUserId", String.valueOf(_user.getUserId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "deleteGroupUsers");
			dropdownItem.putData(
				"deleteGroupUsersURL", deleteGroupUsersURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_request, "remove-membership"));
		};
	}

	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private final ThemeDisplay _themeDisplay;
	private final User _user;

}