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

package com.liferay.item.selector.taglib.internal.display.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class RepositoryEntryBrowserDisplayContext {

	public RepositoryEntryBrowserDisplayContext(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;
	}

	public String getGroupCssIcon(long groupId) throws PortalException {
		Group group = _getGroup(groupId);

		return group.getIconCssClass();
	}

	public String getGroupLabel(long groupId, Locale locale)
		throws PortalException {

		Group group = _getGroup(groupId);

		return group.getDescriptiveName(locale);
	}

	public boolean isSearchEverywhere() {
		if (_searchEverywhere != null) {
			return _searchEverywhere;
		}

		if (Objects.equals(
				ParamUtil.getString(_httpServletRequest, "scope"),
				"everywhere")) {

			_searchEverywhere = true;
		}
		else {
			_searchEverywhere = false;
		}

		return _searchEverywhere;
	}

	private Group _getGroup(long groupId) throws PortalException {
		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (group.isCompany()) {
			return group;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		GroupPermissionUtil.check(
			themeDisplay.getPermissionChecker(), group, ActionKeys.VIEW);

		return group;
	}

	private final HttpServletRequest _httpServletRequest;
	private Boolean _searchEverywhere;

}