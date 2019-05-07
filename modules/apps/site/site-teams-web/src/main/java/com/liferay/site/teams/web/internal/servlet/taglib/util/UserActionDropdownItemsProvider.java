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

package com.liferay.site.teams.web.internal.servlet.taglib.util;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

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
		User user, long teamId, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_user = user;
		_teamId = teamId;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(_getDeleteTeamUsersUnsafeConsumer());
			}
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDeleteTeamUsersUnsafeConsumer() {

		PortletURL deleteTeamUsersURL = _renderResponse.createActionURL();

		deleteTeamUsersURL.setParameter(
			ActionRequest.ACTION_NAME, "deleteTeamUsers");
		deleteTeamUsersURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		deleteTeamUsersURL.setParameter("teamId", String.valueOf(_teamId));
		deleteTeamUsersURL.setParameter(
			"removeUserId", String.valueOf(_user.getUserId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "deleteTeamUsers");
			dropdownItem.putData(
				"deleteTeamUsersURL", deleteTeamUsersURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "delete"));
		};
	}

	private final HttpServletRequest _httpServletRequest;
	private final RenderResponse _renderResponse;
	private final long _teamId;
	private final ThemeDisplay _themeDisplay;
	private final User _user;

}