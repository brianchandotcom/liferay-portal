/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.address.web.internal.dao.search;

import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.permission.CountryPermissionUtil;

import jakarta.portlet.PortletResponse;

/**
 * @author Tancredi Covioli
 */
public class RegionRowChecker extends EmptyOnClickRowChecker {

	public RegionRowChecker(PortletResponse portletResponse) {
		super(portletResponse);
	}

	@Override
	public boolean isDisabled(Object object) {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();
		Region region = (Region)object;

		try {
			return !CountryPermissionUtil.contains(
				permissionChecker, region.getCountryId(), ActionKeys.UPDATE);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RegionRowChecker.class);

}