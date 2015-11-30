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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Adolfo Pérez
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

		if (Validator.equals(getKey(), panelCategory.getKey())) {
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
	public boolean hasAccessPermission(
			PermissionChecker permissionChecker, Group group)
		throws PortalException {

		return true;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, getKey());
	}

	@Override
	public boolean include(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		return false;
	}

	@Override
	public boolean includeHeader(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		return false;
	}

}