/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.address.web.internal.dao.search;

import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.permission.CountryPermissionUtil;

import jakarta.portlet.PortletResponse;

/**
 * @author Tancredi Covioli
 */
public class CountryRowChecker extends EmptyOnClickRowChecker {

	public CountryRowChecker(PortletResponse portletResponse) {
		super(portletResponse);
	}

	@Override
	public boolean isDisabled(Object object) {
		Country country = (Country)object;
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if ((country == null) ||
			(!CountryPermissionUtil.contains(
				permissionChecker, country, ActionKeys.DELETE) &&
			 !CountryPermissionUtil.contains(
				 permissionChecker, country, ActionKeys.UPDATE))) {

			return true;
		}

		return false;
	}

}