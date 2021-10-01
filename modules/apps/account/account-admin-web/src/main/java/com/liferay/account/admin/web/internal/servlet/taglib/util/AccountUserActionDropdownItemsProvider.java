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

package com.liferay.account.admin.web.internal.servlet.taglib.util;

import com.liferay.account.admin.web.internal.security.permission.resource.AccountEntryPermission;
import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.UserPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Objects;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Albert Lee
 */
public class AccountUserActionDropdownItemsProvider {

	public AccountUserActionDropdownItemsProvider(
		long accountEntryId, long accountUserId,
		PermissionChecker permissionChecker, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_accountEntryId = accountEntryId;
		_accountUserId = accountUserId;
		_permissionChecker = permissionChecker;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() throws Exception {
		return DropdownItemListBuilder.add(
			() -> {
				if (Objects.equals(
						PortalUtil.getPortletId(_httpServletRequest),
						AccountPortletKeys.ACCOUNT_ENTRIES_MANAGEMENT) &&
					UserPermissionUtil.contains(
						_permissionChecker, _accountUserId,
						ActionKeys.UPDATE)) {

					return true;
				}

				return false;
			},
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.createRenderURL(
						_renderResponse
					).setMVCPath(
						"/account_users_admin/edit_account_user.jsp"
					).setBackURL(
						_themeDisplay.getURLCurrent()
					).setParameter(
						"p_u_i_d", _accountUserId
					).buildString());
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "edit"));
			}
		).add(
			() -> AccountEntryPermission.contains(
				_permissionChecker, _accountEntryId, ActionKeys.MANAGE_USERS),
			dropdownItem -> {
				dropdownItem.putData("action", "assignRoleAccountUsers");
				dropdownItem.putData(
					"assignRoleAccountUsersURL",
					PortletURLBuilder.createRenderURL(
						_renderResponse
					).setMVCPath(
						"/account_entries_admin/select_account_roles.jsp"
					).setRedirect(
						_themeDisplay.getURLCurrent()
					).setParameter(
						"accountEntryId", _accountEntryId
					).setParameter(
						"accountUserIds", _accountUserId
					).setWindowState(
						LiferayWindowState.POP_UP
					).buildString());
				dropdownItem.putData(
					"editRoleAccountUsersURL",
					PortletURLBuilder.createActionURL(
						_renderResponse
					).setActionName(
						"/account_admin/set_user_account_roles"
					).setRedirect(
						_themeDisplay.getURLCurrent()
					).setParameter(
						"accountEntryId", _accountEntryId
					).setParameter(
						"accountUserId", _accountUserId
					).buildString());
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "assign-roles"));
			}
		).add(
			() -> AccountEntryPermission.contains(
				_permissionChecker, _accountEntryId, ActionKeys.MANAGE_USERS),
			dropdownItem -> {
				dropdownItem.putData("action", "removeAccountUsers");
				dropdownItem.putData(
					"removeAccountUsersURL",
					PortletURLBuilder.createActionURL(
						_renderResponse
					).setActionName(
						"/account_admin/remove_account_users"
					).setRedirect(
						_themeDisplay.getURLCurrent()
					).setParameter(
						"accountEntryId", _accountEntryId
					).setParameter(
						"accountUserIds", _accountUserId
					).buildString());
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "remove"));
			}
		).build();
	}

	private final long _accountEntryId;
	private final long _accountUserId;
	private final HttpServletRequest _httpServletRequest;
	private final PermissionChecker _permissionChecker;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}