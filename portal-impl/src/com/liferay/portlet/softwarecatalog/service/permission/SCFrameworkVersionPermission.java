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
import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;
import com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionLocalServiceUtil;

/**
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
@OSGiBeanProperties(
	property = {
		"model.class.name=" +
			"com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion"
	}
)
public class SCFrameworkVersionPermission
	implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, long frameworkVersionId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, frameworkVersionId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, SCFrameworkVersion.class.getName(),
				frameworkVersionId, actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker,
			SCFrameworkVersion frameworkVersion, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, frameworkVersion, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, SCFrameworkVersion.class.getName(),
				frameworkVersion.getFrameworkVersionId(), actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long frameworkVersionId,
			String actionId)
		throws PortalException {

		SCFrameworkVersion frameworkVersion =
			SCFrameworkVersionLocalServiceUtil.getFrameworkVersion(
				frameworkVersionId);

		return contains(permissionChecker, frameworkVersion, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker,
		SCFrameworkVersion frameworkVersion, String actionId) {

		if (permissionChecker.hasOwnerPermission(
				frameworkVersion.getCompanyId(),
				SCFrameworkVersion.class.getName(),
				frameworkVersion.getFrameworkVersionId(),
				frameworkVersion.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			frameworkVersion.getGroupId(), SCFrameworkVersion.class.getName(),
			frameworkVersion.getFrameworkVersionId(), actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		check(permissionChecker, primaryKey, actionId);
	}

}