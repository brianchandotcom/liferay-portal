/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.CountryLocalServiceUtil;

/**
 * @author Tancredi Covioli
 */
public class CountryPermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, Country country,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, country, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, Country.class.getName(),
				country.getCountryId(), actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long countryId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, countryId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, Country.class.getName(), countryId,
				actionId);
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, Country country, String actionId) {

		if (permissionChecker.isCompanyAdmin(country.getCompanyId()) ||
			permissionChecker.isOmniadmin()) {

			return true;
		}

		return permissionChecker.hasPermission(
			null, Country.class.getName(), country.getCountryId(), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long countryId,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker, CountryLocalServiceUtil.getCountry(countryId),
			actionId);
	}

}