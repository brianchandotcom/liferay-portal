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

package com.liferay.portlet.softwarecatalog.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;
import com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalServiceUtil;

/**
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
@OSGiBeanProperties(
	property = {
		"model.class.name=" +
			"com.liferay.portlet.softwarecatalog.model.SCProductEntry"
	}
)
public class SCProductEntryPermission implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, long productEntryId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, productEntryId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, SCProductEntry.class.getName(),
				productEntryId, actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, SCProductEntry productEntry,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, productEntry, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, SCProductEntry.class.getName(),
				productEntry.getProductEntryId(), actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long productEntryId,
			String actionId)
		throws PortalException {

		SCProductEntry productEntry =
			SCProductEntryLocalServiceUtil.getProductEntry(productEntryId);

		return contains(permissionChecker, productEntry, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, SCProductEntry productEntry,
		String actionId) {

		if (permissionChecker.hasOwnerPermission(
				productEntry.getCompanyId(), SCProductEntry.class.getName(),
				productEntry.getProductEntryId(), productEntry.getUserId(),
				actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			productEntry.getGroupId(), SCProductEntry.class.getName(),
			productEntry.getProductEntryId(), actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		check(permissionChecker, primaryKey, actionId);
	}

}