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

package com.liferay.layout.admin.web.internal.security.permission.resource;

import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = {})
public class LayoutUtilityPageEntryPermission {

	public static void check(
			PermissionChecker permissionChecker, long layoutUtilityPageEntryId,
			String actionId)
		throws PortalException {

		_layoutUtilityPageEntryModelResourcePermission.check(
			permissionChecker, layoutUtilityPageEntryId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker,
			LayoutUtilityPageEntry layoutUtilityPageEntry, String actionId)
		throws PortalException {

		return _layoutUtilityPageEntryModelResourcePermission.contains(
			permissionChecker, layoutUtilityPageEntry, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long layoutUtilityPageEntryId,
			String actionId)
		throws PortalException {

		return _layoutUtilityPageEntryModelResourcePermission.contains(
			permissionChecker, layoutUtilityPageEntryId, actionId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.layout.utility.page.model.LayoutUtilityPageEntry)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<LayoutUtilityPageEntry>
			modelResourcePermission) {

		_layoutUtilityPageEntryModelResourcePermission =
			modelResourcePermission;
	}

	private static ModelResourcePermission<LayoutUtilityPageEntry>
		_layoutUtilityPageEntryModelResourcePermission;

}