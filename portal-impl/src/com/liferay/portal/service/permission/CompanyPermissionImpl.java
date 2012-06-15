/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.permission;

import com.liferay.portal.model.Company;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;

/**
 * @author Jonathan Potter
 */
public class CompanyPermissionImpl implements CompanyPermission {

	public void check(
			PermissionChecker permissionChecker, Company company,
			String actionId)
		throws PrincipalException {

		if (!contains(permissionChecker, company, actionId)) {
			throw new PrincipalException();
		}
	}

	public boolean contains(
			PermissionChecker permissionChecker, Company company,
			String actionId)
		throws PrincipalException {

		if (actionId.equals(ActionKeys.UPDATE) &&
			!permissionChecker.isOmniadmin()) {

			return false;
		}
		else if (actionId.equals(ActionKeys.DELETE) &&
			!permissionChecker.isOmniadmin()) {

			return false;
		}

		return true;
	}

}