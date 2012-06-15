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
import com.liferay.portal.security.permission.PermissionChecker;

/**
 * @author Jonathan Potter
 */
public class CompanyPermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, Company company,
			String actionId)
		throws PrincipalException {

		getCompanyPermission().check(permissionChecker, company, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, Company company,
			String actionId)
		throws PrincipalException {

		return getCompanyPermission().contains(
			permissionChecker, company, actionId);
	}

	public static CompanyPermission getCompanyPermission() {
		return _companyPermission;
	}

	public void setCompanyPermission(CompanyPermission companyPermission) {
		_companyPermission = companyPermission;
	}

	private static CompanyPermission _companyPermission;

}