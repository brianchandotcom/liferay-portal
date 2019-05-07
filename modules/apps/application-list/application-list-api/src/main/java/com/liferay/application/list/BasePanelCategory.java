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

package com.liferay.application.list;

import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides a skeletal implementation of the {@link PanelCategory} to minimize
 * the effort required to implement this interface.
 *
 * <p>
 * To implement an application category, this class should be extended and
 * {@link #include(HttpServletRequest, HttpServletResponse)} and
 * #includeHeader(HttpServletRequest, HttpServletResponse)} should be
 * overridden. The <code>include</code> override method should return
 * <code>true</code> when the application view successfully renders and
 * <code>false</code> otherwise. The <code>includeHeader</code> override method
 * should return <code>true</code> when the category header successfully renders
 * and <code>false</code> otherwise.
 * </p>
 *
 * @author Adolfo Pérez
 * @see    PanelCategory
 */
public abstract class BasePanelCategory implements PanelCategory {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PanelCategory)) {
			return false;
		}

		PanelCategory panelCategory = (PanelCategory)obj;

		if (Objects.equals(getKey(), panelCategory.getKey())) {
			return true;
		}

		return false;
	}

	@Override
	public int getNotificationsCount(
		PanelCategoryHelper panelCategoryHelper,
		PermissionChecker permissionChecker, Group group, User user) {

		return panelCategoryHelper.getNotificationsCount(
			getKey(), permissionChecker, group, user);
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, getKey());
	}

	@Override
	public boolean include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		return false;
	}

	@Override
	public boolean includeHeader(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		return false;
	}

	@Override
	public boolean isActive(
		HttpServletRequest httpServletRequest,
		PanelCategoryHelper panelCategoryHelper, Group group) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String ppid = ParamUtil.getString(
			httpServletRequest, "selPpid", themeDisplay.getPpid());

		return panelCategoryHelper.containsPortlet(
			ppid, getKey(), themeDisplay.getPermissionChecker(), group);
	}

	@Override
	public boolean isPersistState() {
		return false;
	}

	@Override
	public boolean isShow(PermissionChecker permissionChecker, Group group)
		throws PortalException {

		return true;
	}

}