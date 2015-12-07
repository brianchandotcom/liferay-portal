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

package com.liferay.portal.model.impl;

import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.permission.PermissionChecker;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Leonardo Barros
 */
public class ModificationDeniedLayoutTypeAccessPolicyImpl
	extends DefaultLayoutTypeAccessPolicyImpl {

	@Override
	public boolean isAddLayoutAllowed(
		PermissionChecker permissionChecker, Layout layout) {

		return false;
	}

	@Override
	public boolean isCustomizeLayoutAllowed(
		PermissionChecker permissionChecker, Layout layout) {

		return false;
	}

	@Override
	public boolean isDeleteLayoutAllowed(
		PermissionChecker permissionChecker, Layout layout) {

		return false;
	}

	@Override
	public boolean isUpdateLayoutAllowed(
		PermissionChecker permissionChecker, Layout layout) {

		return false;
	}

	@Override
	public boolean isViewLayoutAllowed(
		PermissionChecker permissionChecker, Layout layout) {

		return true;
	}

	@Override
	protected boolean hasAccessPermission(
		HttpServletRequest request, Layout layout, Portlet portlet) {

		return true;
	}

	@Override
	protected boolean isAccessAllowedToLayoutPortlet(
		HttpServletRequest request, Layout layout, Portlet portlet) {

		return true;
	}

}