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
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.softwarecatalog.model.SCLicense;
import com.liferay.portlet.softwarecatalog.service.SCLicenseLocalServiceUtil;

/**
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class SCLicensePermission {

	public static void check(
			PermissionChecker permissionChecker, long productEntryId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, productEntryId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, SCLicense.class.getName(), productEntryId,
				actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, SCLicense license,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, license, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, SCLicense.class.getName(),
				license.getLicenseId(), actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long licenseId,
			String actionId)
		throws PortalException {

		SCLicense license = SCLicenseLocalServiceUtil.getLicense(licenseId);

		return contains(permissionChecker, license, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, SCLicense license,
		String actionId) {

		return permissionChecker.hasPermission(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, SCLicense.class.getName(),
			license.getLicenseId(), actionId);
	}

}